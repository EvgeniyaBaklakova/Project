package com.javamentor.qa.platform;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractTestApi {


    @Test
    @Sql(value = "/script/getUserByIdTest/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "/script/getUserByIdTest/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void getUserByIdTest() throws Exception {

        String USER_TOKEN = getToken("test101@mail.ru", "123");
        this.mvc.perform(MockMvcRequestBuilders.get("/api/test/101")
                        .header(AUTHORIZATION, USER_TOKEN))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Is.is(101)))
                .andExpect(jsonPath("$.email", Is.is("test101@mail.ru")))
                .andExpect(jsonPath("$.fullName", Is.is("Alex Vasiliev")))
                .andExpect(jsonPath("$.imageLink", Is.is("No link")))
                .andExpect(jsonPath("$.city", Is.is("Saint-Petersburg")));

    }

    @Test
    @Sql(scripts = "/script/TestUserResourceController/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void getCountAnswers() throws Exception {

        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/profile/question/week").
                        header("Authorization", "Bearer " + getToken("test100@mail.ru", "password")))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(2)));
    }


    public String getToken(String email, String password) {
        String token;
        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        try {
            String response = (this.mvc.perform(MockMvcRequestBuilders
                            .post("/api/auth/token").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(map)))
                    .andReturn().getResponse().getContentAsString());
            token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");
            return token;
        } catch (Exception e) {
        }
        return "";
    }

}



