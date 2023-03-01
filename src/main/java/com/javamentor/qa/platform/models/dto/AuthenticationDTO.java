package com.javamentor.qa.platform.models.dto;


import lombok.NonNull;

public class AuthenticationDTO {

    @NonNull
    private String login;
    @NonNull
    private String password;
}
