package com.atekur.nexuschat.message;

import com.atekur.nexuschat.file.FileUtils;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageMapper {

    public MessageResponse toMessageResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .state(message.getState())
                .type(message.getType())
                .createdAt(message.getCreatedDate())
                .media(FileUtils.readFileFromLocation(message.getMediaFilePath()))
                .build();
    }
}
