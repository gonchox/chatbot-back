package com.chatbot.appchatbot.domain.service;

import com.chatbot.appchatbot.domain.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface MessageService {
    Page<Message> getAllMessagesByUserId(Long userId, Pageable pageable);

    Message getMessageByIdAndUserId(Long userId, Long messageId);


    ResponseEntity<?> deleteMessage(Long userId, Long messageId);

    Message updateMessage(Long userId, Long messageId, Message messageRequest);

    Message createMessage(Long userId, Message message);

    Message getMessageById(Long messageId);

    Page<Message> getAllMessages(Pageable pageable);
}
