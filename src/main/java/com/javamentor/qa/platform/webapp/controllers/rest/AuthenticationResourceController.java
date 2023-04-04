package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.security.auth.AuthenticationResponse;
import com.javamentor.qa.platform.security.service.AuthDTO;
import com.javamentor.qa.platform.security.service.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationResourceController {

    final AuthenticationManager authenticationManager;
    final JwtProvider tokenProvider;
    private final UserDao userDao;

    @Autowired
    public AuthenticationResourceController(AuthenticationManager authenticationManager, JwtProvider tokenProvider, UserDao userDao) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userDao = userDao;
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

        String jwt = tokenProvider.generateToken((String) authentication.getCredentials());
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}