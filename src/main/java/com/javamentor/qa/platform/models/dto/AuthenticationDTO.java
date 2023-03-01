package com.javamentor.qa.platform.models.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationDTO {

    @NonNull
    private String login;
    @NonNull
    private String password;
}
