package com.chatbot.appchatbot.resource;

import com.chatbot.appchatbot.domain.model.AuditModel;
import lombok.Data;

@Data
public class UserResource extends AuditModel {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
}
