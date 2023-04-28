package com.javamentor.qa.platform.webapp.controllers.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.javamentor.qa.platform.AbstractTestApi;
import com.javamentor.qa.platform.security.auth.AuthenticationResponse;
import com.javamentor.qa.platform.security.service.AuthDTO;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(value = "/script/AuthController/Before.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(value = "/script/AuthController/After.sql", executionPhase = AFTER_TEST_METHOD)
public class AuthControllerTest extends AbstractTestApi {



    @Autowired
    protected UserService userService;

    @Autowired
    protected AuthenticationResourceController authenticationResourceController;


    @Test
//    @WithMockUser
    public void helloAuthenticated() throws Exception {

        String email = "test101@mail.ru";
        String password = "test";
        AuthDTO authDTO = new AuthDTO();
        authDTO.setEmail(email);
        authDTO.setPassword(password);

        String requestBody = new ObjectMapper().writeValueAsString(authDTO);

        // Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

//         Assert
        String responseContent = result.getResponse().getContentAsString();
        assertNotNull(responseContent);
        AuthenticationResponse response = new ObjectMapper().readValue(responseContent, AuthenticationResponse.class);
        assertNotNull(response.getJwtToken());










//        mvc.perform(MockMvcRequestBuilders
//                .post("/api/auth/token")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"email\" : \"email101@mail.com\", \"password\" : \"test\"}"))
//                .andDo((ResultHandler) MockMvcRequestBuilders.get("/api/user/101"))
//                .andExpect(status().isOk());
    }

}
