package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.security.auth.AuthenticationResponse;
import com.javamentor.qa.platform.security.service.AuthDTO;
import com.javamentor.qa.platform.security.service.JWTUtil;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthenticationResourceController {

    final UserService userService;
    final AuthenticationManager authenticationManager;
    final JWTUtil tokenProvider;

    @PostMapping("/auth/token")
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

    @PostMapping(value = "/admin/block")
    public void blockUser(@RequestParam Long userId) {
        Optional<User> user = userService.getById(userId);
        user.ifPresent(u -> {
                    u.setIsEnabled(false);
                    userService.update(u);
                }
        );
    }

    @GetMapping("/admin/{id}")
    public Optional<User> get(@PathVariable Long id) {
        return userService.getById(id);
    }
}