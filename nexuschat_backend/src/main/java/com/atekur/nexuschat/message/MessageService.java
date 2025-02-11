package com.atekur.nexuschat.message;

import com.atekur.nexuschat.chat.Chat;
import com.atekur.nexuschat.chat.ChatRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private MessageRepository messageRepository;
    private ChatRepository chatRepository;
    private MessageMapper mapper;

    public void saveMessage(MessageRequest messageRequest) {
        Chat chat = chatRepository.findById(messageRequest.getChatId())
                .orElseThrow(() -> new EntityNotFoundException("Chat id " + messageRequest.getChatId() + "not found"));

        Message message = new Message();
        message.setContent(messageRequest.getContent());
        message.setChat(chat);
        message.setSenderId(messageRequest.getSenderId());
        message.setReceiverId(messageRequest.getReceiverId());
        message.setState(MessageState.SENT);
        message.setType(messageRequest.getType());

        messageRepository.save(message);

    }

    public List<MessageResponse> findChatMessages(String chatId){
        return messageRepository.findMessageByChatId(chatId)
                .stream()
                .map(mapper::toMessageResponse)
                .toList();
    }

}
