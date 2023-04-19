package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.security.auth.AuthenticationResponse;
import com.javamentor.qa.platform.security.service.AuthDTO;
import com.javamentor.qa.platform.security.service.JwtProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationResourceController {

    final AuthenticationManager authenticationManager;
    final JwtProvider tokenProvider;

    public AuthenticationResourceController(AuthenticationManager authenticationManager, JwtProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/token")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthDTO authDTO) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String username = authDTO.getEmail();
        String password = authDTO.getPassword();
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(String.format("User with email: \"%s\" is not found", username));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication.getName());
        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }
}