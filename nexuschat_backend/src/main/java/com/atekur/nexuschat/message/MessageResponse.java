package com.atekur.nexuschat.message;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {

    private Long id;
    private String content;
    private String senderId;
    private String receiverId;
    private MessageType type;
    private MessageState state;
    private byte[] media;
    private LocalDateTime createdAt;
}
