package com.chatbot.appchatbot.resource;

import com.chatbot.appchatbot.domain.model.AuditModel;
import lombok.Data;

@Data
public class ResponseResource extends AuditModel {
    private Long id;
    private Long messageId;
    private String response;
}
