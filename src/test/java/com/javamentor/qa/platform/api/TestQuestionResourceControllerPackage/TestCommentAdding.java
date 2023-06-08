package com.javamentor.qa.platform.api.TestQuestionResourceControllerPackage;

import com.javamentor.qa.platform.AbstractTestApi;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestCommentAdding extends AbstractTestApi {

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/CommentAddingApiTest/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/CommentAddingApiTest/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void allOkTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\" : \"email1@mail.com\", \"password\" : \"password\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");

        this.mvc.perform(MockMvcRequestBuilders
                .post("/api/user/question/1/comment")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"test message\""))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/CommentAddingApiTest/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/CommentAddingApiTest/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void nullTextTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\" : \"email1@mail.com\", \"password\" : \"password\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");

        this.mvc.perform(MockMvcRequestBuilders
                        .post("/api/user/question/1/comment")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content((byte[]) null))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/CommentAddingApiTest/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/CommentAddingApiTest/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void noQuestionTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\" : \"email1@mail.com\", \"password\" : \"password\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");

        this.mvc.perform(MockMvcRequestBuilders
                        .post("/api/user/question/1100/comment")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"test message\""))
                .andExpect(status().is4xxClientError());
    }
}
