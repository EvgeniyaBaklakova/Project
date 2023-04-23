package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.security.auth.AuthenticationResponse;
import com.javamentor.qa.platform.security.service.AuthDTO;
import com.javamentor.qa.platform.security.service.JWTUtil;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationResourceControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTUtil tokenProvider;

    @InjectMocks
    private AuthenticationResourceController authenticationResourceController;

    @Test
    public void testAuthenticateUser() throws InvalidKeySpecException, NoSuchAlgorithmException {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setEmail("test@test.com");
        authDTO.setPassword("test123");

        String jwtToken = "jwt.token.string";
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn(authDTO.getEmail());

        Mockito.when(authenticationManager.authenticate(Mockito.any()))
                .thenReturn(authentication);

        Mockito.when(tokenProvider.generateToken(Mockito.any()))
                .thenReturn(jwtToken);

        ResponseEntity<?> responseEntity = authenticationResourceController.authenticateUser(authDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        AuthenticationResponse authenticationResponse = (AuthenticationResponse) responseEntity.getBody();
        assertNotNull(authenticationResponse);
        assertEquals(jwtToken, authenticationResponse.getJwtToken());
    }

    @Test
    public void testBlockUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setIsEnabled(true);

        Mockito.when(userService.getById(userId)).thenReturn(Optional.of(user));

        authenticationResourceController.blockUser(userId);

        Mockito.verify(userService).update(user);
        assertFalse(user.getIsEnabled());
    }
}