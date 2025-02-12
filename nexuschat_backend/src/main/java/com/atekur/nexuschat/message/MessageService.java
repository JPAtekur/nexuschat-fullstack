package com.atekur.nexuschat.message;

import com.atekur.nexuschat.chat.Chat;
import com.atekur.nexuschat.chat.ChatRepository;
import com.atekur.nexuschat.file.FileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private MessageRepository messageRepository;
    private ChatRepository chatRepository;
    private MessageMapper mapper;
    private FileService fileService;

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

    public List<MessageResponse> findChatMessages(String chatId) {
        return messageRepository.findMessageByChatId(chatId)
                .stream()
                .map(mapper::toMessageResponse)
                .toList();
    }

    @Transactional
    public void setMessageToSeen(String chatId, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat id " + chatId + "not found"));
        final String receiverId = getRecipientId(chat, authentication);

        messageRepository.setMessageToSeenByChatId(chatId, MessageState.SEEN);
    }

    public void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat id " + chatId + "not found"));

        final String senderId = getSenderId(chat, authentication);
        final String recipientId = getRecipientId(chat, authentication);

        final String filePath = fileService.saveFile(file, senderId);
        Message message = new Message();
        message.setChat(chat);
        message.setSenderId(senderId);
        message.setReceiverId(recipientId);
        message.setMediaFilePath(filePath);
        message.setState(MessageState.SENT);
        message.setType(MessageType.IMAGE);
        messageRepository.save(message);

    }

    private String getSenderId(Chat chat, Authentication authentication) {
        return chat.getSender().getId().equals(authentication.getName()) ?
                chat.getSender().getId() : chat.getRecipient().getId();
    }

    private String getRecipientId(Chat chat, Authentication authentication) {
        return chat.getSender().getId().equals(authentication.getName()) ?
                chat.getRecipient().getId() : chat.getSender().getId();
    }

}
