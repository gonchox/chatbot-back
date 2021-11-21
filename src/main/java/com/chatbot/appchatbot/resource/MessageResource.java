package com.chatbot.appchatbot.resource;

import com.chatbot.appchatbot.domain.model.AuditModel;
import lombok.Data;

@Data
public class MessageResource extends AuditModel {
    private Long id;
    private Long userId;
    private String message;
}
