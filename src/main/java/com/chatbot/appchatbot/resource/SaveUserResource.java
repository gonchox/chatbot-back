package com.chatbot.appchatbot.resource;

import com.chatbot.appchatbot.domain.model.AuditModel;
import lombok.Data;

@Data
public class SaveUserResource extends AuditModel {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
}
