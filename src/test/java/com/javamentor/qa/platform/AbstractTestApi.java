package com.javamentor.qa.platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.junit5.api.DBRider;
import com.javamentor.qa.platform.security.service.AuthDTO;
import com.javamentor.qa.platform.webapp.configs.JmApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DBRider
@SpringBootTest(classes = JmApplication.class)
@TestPropertySource(properties = {"spring.config.location = src/test/resources/application-test.properties"})
@DBUnit(caseSensitiveTableNames = true, cacheConnection = false, allowEmptyFields = true)
@AutoConfigureMockMvc
public abstract class AbstractTestApi {

    @PersistenceContext
    protected EntityManager em;
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;

    private AuthDTO setUserAuth(String email, String password){
        AuthDTO authenticationRequest = new AuthDTO();
        authenticationRequest.setPassword(password);
        authenticationRequest.setEmail(email);
        return authenticationRequest;
    }

    public String getToken(String email, String password) throws Exception {
        String USER_TOKEN = mvc.perform(
                        post("/api/auth/token/")
                                .content(new ObjectMapper().writeValueAsString(this.setUserAuth(email,password)))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        USER_TOKEN = "Bearer " + USER_TOKEN.substring(USER_TOKEN.indexOf(":") + 2, USER_TOKEN.length() - 2);
        return  USER_TOKEN;
    }
}
