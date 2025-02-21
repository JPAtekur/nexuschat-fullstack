import { Component, input, InputSignal } from '@angular/core';
import { ChatResponse } from '../../services/models';

@Component({
  selector: 'app-chat-list',
  imports: [],
  templateUrl: './chat-list.component.html',
  styleUrl: './chat-list.component.scss'
})
export class ChatListComponent {
  
  wrapMessage(lastMessage: string | undefined): string {
    if(lastMessage && lastMessage.length <= 20) {
      return lastMessage;
    }
    return lastMessage?.substring(0, 17) + '...';
  }

  chatClicked(_t16: ChatResponse) {
    throw new Error('Method not implemented.');
  }

  searchContact() {
    throw new Error('Method not implemented.');
  }

  chats: InputSignal<ChatResponse[]> = input<ChatResponse[]>([]);
  searchNewContact = false;

}
