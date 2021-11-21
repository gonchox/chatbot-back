package com.chatbot.appchatbot.controller;

import com.chatbot.appchatbot.domain.model.Message;
import com.chatbot.appchatbot.domain.model.Response;
import com.chatbot.appchatbot.domain.service.MessageService;
import com.chatbot.appchatbot.domain.service.ResponseService;
import com.chatbot.appchatbot.resource.MessageResource;
import com.chatbot.appchatbot.resource.ResponseResource;
import com.chatbot.appchatbot.resource.SaveMessageResource;
import com.chatbot.appchatbot.resource.SaveResponseResource;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

@Tag(name = "responses", description = "Responses API")
@RestController
@RequestMapping("/api")
@Transactional
public class ResponseController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ResponseService responseService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Responses returned", content = @Content(mediaType = "application/json"))
    })

    //@Operation(security={ @SecurityRequirement(name="Authorization") })
    @GetMapping("/messages/{messageId}/responses")
    public Page<ResponseResource> getAllResponsesByMessageId(@PathVariable(name = "messageId") Long messageId, Pageable pageable) {
        List<ResponseResource> responses = responseService.getAllResponsesByMessageId(messageId,pageable)
                .getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int count = responses.size();
        return new PageImpl<>(responses, pageable, count);
    }

    // @Operation(security={ @SecurityRequirement(name="Authorization") })
    @GetMapping("/responses/{id}")
    public ResponseResource getResponseById(
            @Parameter(description="ResponseId")
            @PathVariable(name = "id") Long responseId) {
        return convertToResource(responseService.getResponseById(responseId));
    }

    // @Operation(security={ @SecurityRequirement(name="Authorization") })
    @GetMapping("/messages/{messageId}/responses/{responseId}")
    public ResponseResource getResponseByIdAndMessageId(@PathVariable(name = "messageId") Long messageId,
                                                   @PathVariable(name = "responseId") Long responseId) {
        return convertToResource(responseService.getResponseByIdAndMessageId(messageId, responseId));
    }

    //@Operation(security={ @SecurityRequirement(name="Authorization") })
    @PostMapping("/messages/{messageId}/responses")
    public ResponseResource createResponse(@PathVariable(name = "messageId") Long messageId,
                                         @Valid @RequestBody SaveResponseResource resource) {
        return convertToResource(responseService.createResponse(messageId, convertToEntity(resource)));

    }

    //@Operation(security={ @SecurityRequirement(name="Authorization") })
    @PutMapping("/messages/{messageId}/responses/{responseId}")
    public ResponseResource updateResponse(@PathVariable(name = "messageId") Long messageId,
                                         @PathVariable(name = "responseId") Long responseId,
                                         @Valid @RequestBody SaveResponseResource resource) {
        return convertToResource(responseService.updateResponse(messageId, responseId, convertToEntity(resource)));
    }

    // @Operation(security={ @SecurityRequirement(name="Authorization") })
    @DeleteMapping("/messages/{messageId}/responses/{responseId}")
    public ResponseEntity<?> deleteResponse(@PathVariable(name = "messageId") Long messageId,
                                           @PathVariable(name = "responseId") Long responseId) {
        return responseService.deleteResponse(messageId, responseId);
    }

    //@Operation(security={ @SecurityRequirement(name="Authorization") })
    @GetMapping("/responses")
    public Page<ResponseResource> getAllResponses(
            @Parameter(description="Pageable Parameter")
                    Pageable pageable) {
        Page<Response> responsesPage = responseService.getAllResponses(pageable);
        List<ResponseResource> resources = responsesPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<ResponseResource>(resources,pageable , resources.size());
    }




    private Response convertToEntity(SaveResponseResource resource) {
        return mapper.map(resource, Response.class);
    }

    private ResponseResource convertToResource(Response entity) {
        return mapper.map(entity, ResponseResource.class);
    }
}
