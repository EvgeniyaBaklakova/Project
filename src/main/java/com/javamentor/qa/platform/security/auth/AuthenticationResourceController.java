package com.javamentor.qa.platform.security.auth;

import com.javamentor.qa.platform.security.util.JWTUtil;
import com.javamentor.qa.platform.security.util.LoginCredentials;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationResourceController {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;

    @Autowired
    public AuthenticationResourceController(JWTUtil jwtUtil, AuthenticationManager authManager) {
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
    }

//        @PostMapping("/token")
//    public ResponseEntity<?> performLogin(@RequestBody LoginCredentials authenticationDTO) {
//        UsernamePasswordAuthenticationToken authInputToken =
//                new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(),
//                        authenticationDTO.getPassword());
//
//        try {
//            authManager.authenticate(authInputToken);
//        } catch (BadCredentialsException e) {
//            return (ResponseEntity<?>) ResponseEntity.badRequest();
//        }
//
//        String token = jwtUtil.generateToken(authenticationDTO.getEmail());
//        return ResponseEntity.ok(token);
//    }


    @PostMapping("/token")
    public Map<String, String> performLogin(@RequestBody LoginCredentials authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(),
                        authenticationDTO.getPassword());

        try {
            authManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }

        String token = jwtUtil.generateToken(authenticationDTO.getEmail());
        return Map.of("jwt-token", token);
    }


//    @PostMapping("/token")
//    public ResponseEntity<?> loginHandler(@RequestBody LoginCredentials body) {
//        try {
//            UsernamePasswordAuthenticationToken authInputToken =
//                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());
//
//            authManager.authenticate(authInputToken);
//
//            String token = jwtUtil.generateToken(body.getEmail());
//
//            return ResponseEntity.ok().body(token);
//        } catch (AuthenticationException authExc) {
//            throw new RuntimeException("Invalid Login Credentials");
//        }


}



