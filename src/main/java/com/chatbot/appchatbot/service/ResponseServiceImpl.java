package com.chatbot.appchatbot.service;

import com.chatbot.appchatbot.domain.model.Message;
import com.chatbot.appchatbot.domain.model.Response;
import com.chatbot.appchatbot.domain.model.User;
import com.chatbot.appchatbot.domain.repository.MessageRepository;
import com.chatbot.appchatbot.domain.repository.ResponseRepository;
import com.chatbot.appchatbot.domain.service.ResponseService;
import com.chatbot.appchatbot.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseServiceImpl implements ResponseService {
    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Page<Response> getAllResponsesByMessageId(Long messageId, Pageable pageable) {
        return responseRepository.findByMessageId(messageId, pageable);
    }

    @Override
    public Response getResponseByIdAndMessageId(Long messageId, Long responseId) {
        return responseRepository.findByIdAndMessageId(responseId, messageId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Response not found with Id " + responseId +
                                " and MessageId " + messageId));
    }

    @Override
    public ResponseEntity<?> deleteResponse(Long messageId, Long responseId) {
        Response response = responseRepository.findById(responseId)
                .orElseThrow(() -> new ResourceNotFoundException("Response", "Id", responseId));
        responseRepository.delete(response);
        return ResponseEntity.ok().build();
    }

    @Override
    public Response updateResponse(Long messageId, Long responseId, Response responseRequest) {
        Response response = responseRepository.findById(responseId)
                .orElseThrow(() -> new ResourceNotFoundException("Response", "Id", responseId));
        response.setResponse(responseRequest.getResponse());
        return responseRepository.save(response);
    }

    @Override
    public Response createResponse(Long messageId, Response response) {
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new ResourceNotFoundException("Message", "Id", messageId));
        response.setMessage(message);
        return responseRepository.save(response);
    }

    @Override
    public Response getResponseById(Long responseId) {
        return responseRepository.findById(responseId)
                .orElseThrow(() -> new ResourceNotFoundException("Response", "Id", responseId));
    }

    @Override
    public Page<Response> getAllResponses(Pageable pageable) {
        return responseRepository.findAll(pageable);
    }
}
