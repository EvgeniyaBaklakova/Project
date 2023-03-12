package com.javamentor.qa.platform.security.auth;

import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.security.util.JWTUtil;
import com.javamentor.qa.platform.security.util.JwtAuthenticationResponse;
import com.javamentor.qa.platform.security.util.LoginCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationResourceController {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationResourceController(JWTUtil jwtUtil, AuthenticationManager authManager, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
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


//    @PostMapping("/token")
//    public Map<String, String> performLogin(@RequestBody LoginCredentials authenticationDTO) {
//        UsernamePasswordAuthenticationToken authInputToken =
//                new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(),
//                        authenticationDTO.getPassword());
//
//        try {
//            authManager.authenticate(authInputToken);
//        } catch (BadCredentialsException e) {
//            return Map.of("message", "Incorrect credentials!");
//        }
//
//        String token = jwtUtil.generateToken(authenticationDTO.getEmail());
//        return Map.of("jwt-token", token);
//    }


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

    @PostMapping("/token")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginCredentials loginCredentials) {
        var user = new User();
        String username = loginCredentials.getEmail();
        String password = passwordEncoder.encode(user.getPassword());
        Authentication authentication;
        try {
            authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );
        } catch (AuthenticationException e) {
//            logger.error("Invalid username/password supplied");
            throw new BadCredentialsException("Invalid username/password supplied");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.generateToken(String.valueOf(authentication));
        return ResponseEntity.ok(new JwtAuthenticationResponse());

    }

}



