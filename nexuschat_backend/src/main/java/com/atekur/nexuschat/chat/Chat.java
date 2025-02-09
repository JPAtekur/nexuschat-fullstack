package com.atekur.nexuschat.chat;

import com.atekur.nexuschat.common.BaseAuditingEntity;
import com.atekur.nexuschat.message.Message;
import com.atekur.nexuschat.message.MessageState;
import com.atekur.nexuschat.message.MessageType;
import com.atekur.nexuschat.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.GenerationType.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat")
public class Chat extends BaseAuditingEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;
    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
    @OrderBy("createdDate DESC")
    private List<Message> messages;

    @Transient
    public String getChatName(final String senderId) {
        if (recipient.getId().equals(senderId)) {
            return sender.getFirstName() + " " + sender.getLastName();
        }
        return recipient.getFirstName() + " " + recipient.getLastName();
    }

    @Transient
    public long getUnreadMessages(final String senderId) {
        return messages
                .stream()
                .filter(m -> m.getReceiverId().equals(senderId))
                .filter(m -> m.getState() == MessageState.SENT)
                .count();
    }

    @Transient
    public String getLastMessage(){
        if (messages != null && !messages.isEmpty()){
            if (messages.get(0).getType() != MessageType.TEXT){
                return "Attachment";
            }
            return messages.get(0).getContent();
        }
        return null;
    }

    @Transient
    public LocalDateTime getLastMessageTime(){
        if (messages != null && !messages.isEmpty()){
            return messages.get(0).getCreatedDate();
        }
        return null;
    }

}
