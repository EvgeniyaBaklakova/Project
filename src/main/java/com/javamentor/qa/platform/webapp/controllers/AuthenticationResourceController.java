package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.AuthenticationDTO;
import com.javamentor.qa.platform.models.dto.UserDTO;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.security.util.JWTUtil;
import com.javamentor.qa.platform.service.userService.CustomUserDetailsService;
import com.javamentor.qa.platform.service.userService.RegistrationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/token")
public class AuthenticationResourceController {

    private final CustomUserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final RegistrationService registrationService;

    @Autowired
    public AuthenticationResourceController(CustomUserDetailsService userDetailsService, JWTUtil jwtUtil, ModelMapper modelMapper, RegistrationService registrationService) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.registrationService = registrationService;
    }

    @PostMapping
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getLogin(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect login or password!");
        }

        String token = jwtUtil.generateToken(authenticationDTO.getLogin());
        return Map.of("jwt-token", token);
    }

    public User convertToPerson(UserDTO userDTO) {
        return this.modelMapper.map(userDTO, User.class);
    }
}
