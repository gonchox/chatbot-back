package com.chatbot.appchatbot.domain.repository;

import com.chatbot.appchatbot.domain.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findByUserId(Long userId, Pageable pageable);

    Optional<Message> findByMessage(String message);

    Optional<Message> findByIdAndUserId(Long id, Long userId);
}
