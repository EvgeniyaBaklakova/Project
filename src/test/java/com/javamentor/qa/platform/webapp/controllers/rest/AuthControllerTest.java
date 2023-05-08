package com.javamentor.qa.platform.webapp.controllers.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.javamentor.qa.platform.AbstractTestApi;
import com.javamentor.qa.platform.security.auth.AuthenticationResponse;
import com.javamentor.qa.platform.security.service.AuthDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;


public class AuthControllerTest extends AbstractTestApi {

    @Sql(value = "/script/AuthController/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "/script/AuthController/After.sql", executionPhase = AFTER_TEST_METHOD)
    @DisplayName("\uD83D\uDC80")
    @Test
    public void helloAuthenticated() throws Exception {

        String email = "test101@mail.ru";
        String password = "test";

        AuthDTO authDTO = new AuthDTO();
        authDTO.setEmail(email);
        authDTO.setPassword(password);

        String requestBody = new ObjectMapper().writeValueAsString(authDTO);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andReturn();

        String jwtToken = result.getResponse().getContentAsString();
        assertNotNull(jwtToken);
        AuthenticationResponse response = new ObjectMapper().readValue(jwtToken, AuthenticationResponse.class);
        assertNotNull(response.getJwtToken());
    }
}
