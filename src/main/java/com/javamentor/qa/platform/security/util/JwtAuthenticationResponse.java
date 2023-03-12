package com.javamentor.qa.platform.security.util;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {

    private String username;
    private String password;
}
