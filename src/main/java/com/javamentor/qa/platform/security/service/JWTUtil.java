package com.javamentor.qa.platform.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String email) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject(email)
                .withIssuedAt(new Date())
                .withIssuer("YOUR APPLICATION/PROJECT/COMPANY NAME")
                .withExpiresAt(new Date(System.currentTimeMillis() + 2592000000L))
                .sign(Algorithm.HMAC256(secret));
    }
}