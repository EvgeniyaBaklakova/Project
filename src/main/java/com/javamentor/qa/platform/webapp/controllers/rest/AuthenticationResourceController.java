package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.security.service.AuthDTO;
import com.javamentor.qa.platform.security.service.JWTUtil;
import com.javamentor.qa.platform.service.abstracts.model.SecurityService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationResourceController {

    private final SecurityService securityService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResourceController(SecurityService securityService,
                                            JWTUtil jwtUtil,
                                            AuthenticationManager authenticationManager) {
        this.securityService = securityService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/token")
    public Map<String, Object> loginHandler(@RequestBody AuthDTO body) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());
            authenticationManager.authenticate(authInputToken);
            String token = jwtUtil.generateToken(body.getEmail());
            return Collections.singletonMap("jwt-token", token);
        } catch (AuthenticationException authExc) {
            throw new RuntimeException("Invalid Login Credentials");
        }
    }

    @PatchMapping("/update/blockUser/{id}")
    public void blockingUser(@PathVariable Long id) {

        securityService.setIsEnableFalse(id);
    }
}