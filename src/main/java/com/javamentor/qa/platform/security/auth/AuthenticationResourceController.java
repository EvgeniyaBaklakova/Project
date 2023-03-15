package com.javamentor.qa.platform.security.auth;

import com.javamentor.qa.platform.models.dto.UserDTO;
import com.javamentor.qa.platform.security.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    final JWTTokenHelper tokenProvider;

    public AuthenticationResourceController(AuthenticationManager authenticationManager, JWTTokenHelper tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/token")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserDTO loginRequest) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken((String) authentication.getCredentials());
        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }
}