import { Component, input, InputSignal, output } from '@angular/core';
import { ChatResponse, UserResponse } from '../../services/models';
import { ChatService } from '../../services/services/chat.service';
import { DatePipe } from '@angular/common';
import { UsuarioService } from '../../services/services/usuario.service';
import { KeycloakService } from '../../utils/keycloak/keycloak.service';

@Component({
  selector: 'app-chat-list',
  imports: [DatePipe],
  templateUrl: './chat-list.component.html',
  styleUrl: './chat-list.component.scss',
})
export class ChatListComponent {
  chats: InputSignal<ChatResponse[]> = input<ChatResponse[]>([]);
  searchNewContact = false;
  chatSelected = output<ChatResponse>();
  contacts: Array<UserResponse> = [];
  constructor(
    private chatService: ChatService,
    private userService: UsuarioService,
    private keycloakService: KeycloakService
  ) {}
  searchContact() {
    console.log('searchContact');

    this.userService.getAllUsers().subscribe({
      next: (res) => {
        this.searchNewContact = true;
        this.contacts = res;
      },
      error: (err) => {},
    });
  }
  logout() {}
  chatClicked(chat: ChatResponse) {
    this.chatSelected.emit(chat);
  }
  selectContact(contact: UserResponse) {
    this.chatService.createChat(
      {
        'sender-id': this.keycloakService.userId as string,
        'receiver-id':contact.id as string,
      }
    ).subscribe({
      next: (res) => {
       const chat:ChatResponse = {
        id:res.response,
        name:contact.firstName + ' ' + contact.lastName,
        isRecipientOnline: contact.isOnline,
        lastMessageTime: contact.lastSeen,
        senderId: this.keycloakService.userId ,
        receiverId: contact.id ,
       }
       this.chats().unshift(chat);
       this.searchNewContact = false;
       this.chatSelected.emit(chat);
      },
      error: (err) => {},
    });
  }
  wrapMessage(lastMessage: string | undefined): string {
    if (lastMessage && lastMessage.length <= 20) {
      return lastMessage;
    }
    return lastMessage?.substring(0, 17) + '...';
  }
}
