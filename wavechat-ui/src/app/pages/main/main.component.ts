import { Component } from '@angular/core';
import { ChatListComponent } from '../../components/chat-list/chat-list.component';
import { ChatResponse, MessageResponse } from '../../services/models';
import { ChatService, MensagemService } from '../../services/services';
import { KeycloakService } from '../../utils/keycloak/keycloak.service';

import { getMessages } from '../../services/fn/mensagem/get-messages';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-main',
  imports: [ChatListComponent, DatePipe],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss',
})
export class MainComponent {

  selectedChat: ChatResponse = {};
  chats: Array<ChatResponse> = [];
  chatMessages: Array<MessageResponse> = [];
  socketClient: any = null;
  messageContent: string = '';
  showEmojis = false;
  constructor(
    private chatService: ChatService,
    private keycloakService: KeycloakService,
    private messageService:MensagemService
  ) {}
  ngOnit() {
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
    this.messageService.getMessages(
      {
         'chat-id':chatId
      }
    ).subscribe(
      {
        next: (messages) => {
          this.chatMessages= messages;
        },
        error: (err) => {},
      }
    )
  }
  private setMessagesToSeen() {

  }
}
