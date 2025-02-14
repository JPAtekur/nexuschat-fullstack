package com.atekur.nexuschat.notification;

import com.atekur.nexuschat.message.MessageType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    private String chatId;
    private String senderId;
    private String receiverId;
    private String content;
    private String chatName;
    private MessageType type;
    private NotificationType notificationType;
    private byte[] media;
}
