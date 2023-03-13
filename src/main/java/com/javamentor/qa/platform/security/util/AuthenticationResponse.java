package com.javamentor.qa.platform.security.util;

public class AuthenticationResponse {
    private String jwtToken;    
    public AuthenticationResponse(){}
    public AuthenticationResponse(String jwtToken) {        
        this.jwtToken = jwtToken;
    }
    public String getJwtToken() {
        return jwtToken;
    }
    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}