package com.chatbot.appchatbot.domain.service;

import com.chatbot.appchatbot.domain.model.Message;
import com.chatbot.appchatbot.domain.model.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ResponseService {
    Page<Response> getAllResponsesByMessageId(Long userId, Pageable pageable);

    Response getResponseByIdAndMessageId(Long userId, Long messageId);

   // Page<Response> getAllResponsesByResponseId(Long responseId, Pageable pageable);

    ResponseEntity<?> deleteResponse(Long userId, Long messageId);

    Response updateResponse(Long messageId, Long responseId, Response responseRequest);

    Response createResponse(Long messageId, Response response);

    Response getResponseById(Long responseId);

    Page<Response> getAllResponses(Pageable pageable);
}
