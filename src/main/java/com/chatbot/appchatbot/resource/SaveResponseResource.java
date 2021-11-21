package com.chatbot.appchatbot.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SaveResponseResource {
    @NotNull
    @NotBlank
    @Size(max = 100)
    private String response;
}
