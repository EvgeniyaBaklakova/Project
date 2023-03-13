package com.javamentor.qa.platform.auth;


import com.javamentor.qa.platform.models.entity.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/auth/token")
public class AuthenticationResourceController {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthenticationResourceController(JWTUtil jwtUtil, AuthenticationManager authenticationManager, ModelMapper modelMapper) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody PersonDTO personDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(personDTO.getUsername(), personDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(personDTO.getUsername());


        return ResponseEntity.ok(jwt);
    }


    public User convertToPerson(PersonDTO personDTO) {
        return this.modelMapper.map(personDTO, User.class);
    }
}
