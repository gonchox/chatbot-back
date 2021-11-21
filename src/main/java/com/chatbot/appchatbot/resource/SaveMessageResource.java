package com.chatbot.appchatbot.resource;

import lombok.Data;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SaveMessageResource {
    @NotNull
    @NotBlank
    @Size(max = 100)
    private String message;

}
