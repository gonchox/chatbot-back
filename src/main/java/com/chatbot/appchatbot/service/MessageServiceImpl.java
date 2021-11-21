package com.chatbot.appchatbot.service;

import com.chatbot.appchatbot.domain.model.Message;
import com.chatbot.appchatbot.domain.model.User;
import com.chatbot.appchatbot.domain.repository.MessageRepository;
import com.chatbot.appchatbot.domain.repository.UserRepository;
import com.chatbot.appchatbot.domain.service.MessageService;
import com.chatbot.appchatbot.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Page<Message> getAllMessagesByUserId(Long userId, Pageable pageable) {
        return messageRepository.findByUserId(userId, pageable);
    }

    @Override
    public Message getMessageByIdAndUserId(Long userId, Long messageId) {
        return messageRepository.findByIdAndUserId(messageId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Message not found with Id " + messageId +
                                " and UserId " + userId));
    }

    @Override
    public ResponseEntity<?> deleteMessage(Long userId, Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message", "Id", messageId));
        messageRepository.delete(message);
        return ResponseEntity.ok().build();
    }

    @Override
    public Message updateMessage(Long userId, Long messageId, Message messageRequest) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message", "Id", messageId));
        message.setMessage(messageRequest.getMessage());
        return messageRepository.save(message);
    }

    @Override
    public Message createMessage(Long userId, Message message) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        message.setUser(user);
        return messageRepository.save(message);
    }

    @Override
    public Message getMessageById(Long messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message", "Id", messageId));
    }

    @Override
    public Page<Message> getAllMessages(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

}
