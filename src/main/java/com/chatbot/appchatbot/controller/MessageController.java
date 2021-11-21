package com.chatbot.appchatbot.controller;

import com.chatbot.appchatbot.domain.model.Message;
import com.chatbot.appchatbot.domain.service.MessageService;
import com.chatbot.appchatbot.resource.MessageResource;
import com.chatbot.appchatbot.resource.SaveMessageResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "messages", description = "Messages API")
@RestController
@RequestMapping("/api")
@Transactional
public class MessageController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MessageService messageService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Messages returned", content = @Content(mediaType = "application/json"))
    })

    //@Operation(security={ @SecurityRequirement(name="Authorization") })
    @GetMapping("/users/{userId}/messages")
    public Page<MessageResource> getAllMessagesByUserId(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        List<MessageResource> messages = messageService.getAllMessagesByUserId(userId,pageable)
                .getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int count = messages.size();
        return new PageImpl<>(messages, pageable, count);
    }

   // @Operation(security={ @SecurityRequirement(name="Authorization") })
    @GetMapping("/messages/{id}")
    public MessageResource getMessageById(
            @Parameter(description="Message Id")
            @PathVariable(name = "id") Long messageId) {
        return convertToResource(messageService.getMessageById(messageId));
    }

   // @Operation(security={ @SecurityRequirement(name="Authorization") })
    @GetMapping("/users/{userId}/messages/{messageId}")
    public MessageResource getMessageByIdAndUserId(@PathVariable(name = "userId") Long userId,
                                             @PathVariable(name = "messageId") Long messageId) {
        return convertToResource(messageService.getMessageByIdAndUserId(userId, messageId));
    }

    //@Operation(security={ @SecurityRequirement(name="Authorization") })
    @PostMapping("/users/{userId}/messages")
    public MessageResource createMessage(@PathVariable(name = "userId") Long userId,
                                   @Valid @RequestBody SaveMessageResource resource) {
        return convertToResource(messageService.createMessage(userId, convertToEntity(resource)));

    }

    //@Operation(security={ @SecurityRequirement(name="Authorization") })
    @PutMapping("/users/{userId}/messages/{messageId}")
    public MessageResource updateMessage(@PathVariable(name = "userId") Long userId,
                                   @PathVariable(name = "messageId") Long messageId,
                                   @Valid @RequestBody SaveMessageResource resource) {
        return convertToResource(messageService.updateMessage(userId, messageId, convertToEntity(resource)));
    }

   // @Operation(security={ @SecurityRequirement(name="Authorization") })
    @DeleteMapping("/users/{userId}/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable(name = "userId") Long userId,
                                        @PathVariable(name = "messageId") Long messageId) {
        return messageService.deleteMessage(userId, messageId);
    }

    //@Operation(security={ @SecurityRequirement(name="Authorization") })
    @GetMapping("/messages")
    public Page<MessageResource> getAllMessages(
            @Parameter(description="Pageable Parameter")
                    Pageable pageable) {
        Page<Message> messagesPage = messageService.getAllMessages(pageable);
        List<MessageResource> resources = messagesPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<MessageResource>(resources,pageable , resources.size());
    }




    private Message convertToEntity(SaveMessageResource resource) {
        return mapper.map(resource, Message.class);
    }

    private MessageResource convertToResource(Message entity) {
        return mapper.map(entity, MessageResource.class);
    }
}
