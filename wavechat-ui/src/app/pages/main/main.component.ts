import { Component, ElementRef, ViewChild } from '@angular/core';
import { ChatListComponent } from '../../components/chat-list/chat-list.component';
import { ChatResponse, MessageResponse } from '../../services/models';
import { ChatService, MensagemService } from '../../services/services';
import { KeycloakService } from '../../utils/keycloak/keycloak.service';
import { PickerComponent } from '@ctrl/ngx-emoji-mart';
import { FormsModule } from '@angular/forms';

import { getMessages } from '../../services/fn/mensagem/get-messages';
import { DatePipe } from '@angular/common';
import { EmojiData } from '@ctrl/ngx-emoji-mart/ngx-emoji';
import { MessageRequest } from '../../services/models/message-request';
import * as Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import { Notification } from './models/notification';
@Component({
  selector: 'app-main',
  imports: [ChatListComponent, DatePipe, PickerComponent, FormsModule],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss',
})
export class MainComponent  {
  selectedChat: ChatResponse = {};
  chats: Array<ChatResponse> = [];
  chatMessages: Array<MessageResponse> = [];
  socketClient: any = null;
  messageContent: string = '';
  showEmojis = false;
  @ViewChild('scrollableDiv') scrollableDiv!: ElementRef<HTMLDivElement>;
  private notificationSubscription: any;
  constructor(
    private chatService: ChatService,
    private keycloakService: KeycloakService,
    private messageService: MensagemService
  ) {}
  ngOnDestroy(): void {
   if(this.socketClient !== null) {
      this.socketClient.disconnect();
      this.notificationSubscription.unsubcribe();
      this.socketClient = null;
   }
   
  }
  ngOnit() {
    this.initWebSocket();
    this.getAllChats();
  }
  logout() {
    console.log('logout');
    this.keycloakService.logout();
  }
  userProfile() {
    console.log('userProfile');
    this.keycloakService.accountManagement();
  }
  chatSelected(chatResponse: ChatResponse) {
    this.selectedChat = chatResponse;
    this.getAllChatMessages(chatResponse.id as string);
    this.setMessagesToSeen();
    this.selectedChat.unreadCount = 0;
  }
  isSelfMessage(message: MessageResponse): boolean {
    return message.senderId === this.keycloakService.userId;
  }
  private getAllChats() {
    this.chatService.getChatsByReceiver().subscribe({
      next: (res) => {
        this.chats = res;
      },
      error: (err) => {},
    });
  }
  private getAllChatMessages(chatId: string) {
    this.messageService
      .getMessages({
        'chat-id': chatId,
      })
      .subscribe({
        next: (messages) => {
          this.chatMessages = messages;
        },
        error: (err) => {},
      });
  }
  private setMessagesToSeen() {
    this.messageService
      .setMessageService({
        'chat-id': this.selectedChat.id as string,
      })
      .subscribe({
        next: (res) => {},
        error: (err) => {},
      });
  }
  uploadMedia(target: EventTarget | null) {
    const files = (target as HTMLInputElement).files;
    if (files && files.length > 0) {
      const file = files[0];
      const reader = new FileReader();
      reader.onload = (e) => {
        const media = (e.target as FileReader).result as string;
      };
      reader.readAsDataURL(file);
    }
  }
  onSelectEmojis(emojiSelected: any) {
    const emoji: EmojiData = emojiSelected.emoji;
    this.messageContent += emoji.native;
  }
  keyDown(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.sendMessage();
    }
  }
  onClick() {
    this.setMessagesToSeen();
  }
  sendMessage() {
    if (this.messageContent) {
      const messageRequest: MessageRequest = {
        chatId: this.selectedChat.id as string,
        senderId: this.getSenderId(),
        receiverId: this.getReceiverId(),
        content: this.messageContent,
        type: 'TEXT',
      };
      this.messageService
        .saveMessage({
          body: messageRequest,
        })
        .subscribe({
          next: (res) => {
            const message: MessageResponse = {
              senderId: this.getSenderId(),
              receiverId: this.getReceiverId(),
              content: this.messageContent,
              type: 'TEXT',
              state: 'SENT',
              createdAt: new Date().toString(),
            };
            this.selectedChat.lastMessage = this.messageContent;
            this.chatMessages.push(message);
            this.messageContent = '';
            this.showEmojis = false;
          },
          error: (err) => {},
        });
    }
  }
  private getSenderId(): string {
    if (this.selectedChat.senderId === this.keycloakService.userId) {
      return this.selectedChat.senderId as string;
    }
    return this.selectedChat.receiverId as string;
  }
  private getReceiverId(): string {
    if (this.selectedChat.senderId === this.keycloakService.userId) {
      return this.selectedChat.receiverId as string;
    }
    return this.selectedChat.senderId as string;
  }
  private initWebSocket() {
    if (this.keycloakService.keycloak.tokenParsed?.sub) {
      const ws = new SockJS('http://localhost:8080/ws');
      this.socketClient = Stomp.over(ws);
      const subUrl =
        '/user/${this.keycloakService.keycloak.tokenParsed.sub}/chat';
      this.socketClient.connect(
        { Authorization: 'Bearer' + this.keycloakService.keycloak.token },
        () => {
          this.notificationSubscription = this.socketClient.subscribe(
            subUrl,
            (message: any) => {
              const notification: Notification = JSON.parse(message.body);
              this.handleNotification(notification);
            },
            () => console.error('Erro ao conectar no websocket')
          );
        }
      );
    }
  }
  private handleNotification(notification: Notification) {
    if (!notification) return;
    if (this.selectedChat && this.selectedChat.id === notification.chatId) {
      switch (notification.type) {
        case 'MESSAGE':
        case 'IMAGE':
          const message: MessageResponse = {
            senderId: notification.senderId,
            receiverId: notification.receiverId,
            content: notification.content,
            type: notification.messageType,
            media: notification.media,
            createdAt: new Date().toString(),
          };
          if (notification.type === 'IMAGE') {
            this.selectedChat.lastMessage = 'Anexo';
          } else {
            this.selectedChat.lastMessage = notification.content;
          }
          this.chatMessages.push(message);
          break;
        case 'SEEN':
          this.chatMessages.forEach((m) => (m.state = 'SEEN'));
          break;
      }
    } else {
      const destChat = this.chats.find((c) => c.id === notification.chatId);
      if (destChat && notification.type !== 'SEEN') {
        if (notification.type === 'MESSAGE') {
          destChat.lastMessage = notification.content;
        } else if (notification.type === 'IMAGE') {
          destChat.lastMessage = 'Anexo';
        }
        destChat.lastMessageTime = new Date().toString();
        destChat.unreadCount! += 1;
      } else if (notification.type === 'MESSAGE') {
        const newChat: ChatResponse = {
          id: notification.chatId,
          senderId: notification.senderId,
          receiverId: notification.receiverId,
          lastMessage: notification.content,
          name: notification.chatName,
          unreadCount: 1,
          lastMessageTime: new Date().toString(),
        };
        this.chats.unshift(newChat);
      }
    }
  }

  private scrollToBottom() {
    if (this.scrollableDiv) {
      const div = this.scrollableDiv.nativeElement;
      div.scrollTop = div.scrollHeight;
    }
  }

  private extractFileFromTarget(target: EventTarget | null): File | null {
    const htmlInputTarget = target as HTMLInputElement;
    if (target === null || htmlInputTarget.files === null) {
      return null;
    }
    return htmlInputTarget.files[0];
  }
}
