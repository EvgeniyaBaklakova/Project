package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.security.service.AuthDTO;
import com.javamentor.qa.platform.security.service.JWTUtil;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationResourceController {


    private final UserDao userDao;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthenticationResourceController(UserDao userDao, JWTUtil jwtUtil, AuthenticationManager authenticationManager, UserService userService) {
        this.userDao = userDao;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/token")
    public Map<String, Object> loginHandler(@RequestBody AuthDTO body) {
        try {
            // Creating the Authentication Token which will contain the credentials for authenticating
            // This token is used as input to the authentication process
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            // Authenticating the Login Credentials
            authenticationManager.authenticate(authInputToken);

            // If this point is reached it means Authentication was successful
            // Generate the JWT
            String token = jwtUtil.generateToken(body.getEmail());

            // Respond with the JWT
            return Collections.singletonMap("jwt-token", token);
        } catch (AuthenticationException authExc) {
            // Auhentication Failed
            throw new RuntimeException("Invalid Login Credentials");
        }
    }

    @PatchMapping("/update/blockUser/{id}")
    public void blockingUser(@PathVariable Long id) {

        var user = userDao.getUserById(id);
        user.get().setIsEnabled(false);
        userService.update(user);

    }


//    final AuthenticationManager authenticationManager;
//    final JwtProvider tokenProvider;
//    private final UserDao userDao;
//    private final UpdateUser updateUser;
//
//    @Autowired
//    public AuthenticationResourceController(AuthenticationManager authenticationManager, JwtProvider tokenProvider, UserDao userDao, UpdateUser updateUser) {
//        this.authenticationManager = authenticationManager;
//        this.tokenProvider = tokenProvider;
//        this.userDao = userDao;
//        this.updateUser = updateUser;
//    }

//    @PostMapping("/token")
//    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthDTO authDTO) throws InvalidKeySpecException, NoSuchAlgorithmException {
//        String username = authDTO.getEmail();
//        String password = authDTO.getPassword();
//        Authentication authentication;
//        try {
//            authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            username,
//                            password
//                    )
//            );
//        } catch (AuthenticationException e) {
//            throw new BadCredentialsException(String.format("User with email: \"%s\" is not found", username));
//        }
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = tokenProvider.generateToken((String) authentication.getCredentials());
//        return ResponseEntity.ok(new AuthenticationResponse(jwt));
//    }

//    @PatchMapping("/update/blockUser/{id}")
//    public void blockingUser(@PathVariable Long id) {
//
//        userDao.getById(id).get().setIsEnabled(false);
//    }
}