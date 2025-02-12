package com.atekur.nexuschat.message;

import com.atekur.nexuschat.chat.Chat;
import com.atekur.nexuschat.chat.ChatConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(name = MessageConstants.FIND_MESSAGES_BY_CHAT_ID)
    List<Message> findMessageByChatId(String chatId);

    @Query(name = MessageConstants.SET_MESSAGES_TO_SEEN_BY_CHAT)
    @Modifying
    void setMessageToSeenByChatId(@Param("chatId") String chatId, @Param("newState") MessageState state);
}
