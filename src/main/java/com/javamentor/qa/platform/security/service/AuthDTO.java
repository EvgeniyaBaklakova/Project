package com.javamentor.qa.platform.security.service;


import lombok.Data;

@Data
public class AuthDTO {

    private String email;
    private String password;
    private boolean rememberMe;
}
