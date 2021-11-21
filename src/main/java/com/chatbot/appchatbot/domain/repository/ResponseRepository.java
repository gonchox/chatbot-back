package com.chatbot.appchatbot.domain.repository;

import com.chatbot.appchatbot.domain.model.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    Page<Response> findByMessageId(Long messageId, Pageable pageable);

    Optional<Response> findByResponse(String response);

    Optional<Response> findByIdAndMessageId(Long id, Long messageId);
}
