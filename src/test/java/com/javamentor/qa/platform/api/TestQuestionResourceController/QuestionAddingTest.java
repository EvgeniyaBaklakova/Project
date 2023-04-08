package com.javamentor.qa.platform.api.TestQuestionResourceController;

import com.javamentor.qa.platform.AbstractTestApi;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class QuestionAddingTest extends AbstractTestApi {

    @Test
    @Sql(scripts = "src/test/resources/script/TestQuestionResourceController/QuestionAddingApiTest/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "src/test/resources/script/TestQuestionResourceController/QuestionAddingApiTest/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void allOkTest() throws Exception {
        MvcResult result = this.mvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/token")
                        .content("{\"email\" : \"email@mail.com\", \"password\" : \"aaa\"}"))
                        .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        String token = response.replace(" {\"jwtToken\":\"} ", "").replace("\"}", "");

        this.mvc.perform(MockMvcRequestBuilders
                        .post("/api/user/question")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"hello\", \"description\": \"need help\",\"tags\": [{\"name\": \"tag\"}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", Is.is("hello")))
                .andExpect(jsonPath("$.authorId", Is.is(1)))
                .andExpect(jsonPath("$.authorName", Is.is("nick")))
                .andExpect(jsonPath("$.authorImage", Is.is("image_link")))
                .andExpect(jsonPath("$.description", Is.is("need help")))
                .andExpect(jsonPath("$.viewCount", Is.is(0)))
                .andExpect(jsonPath("$.countAnswer", Is.is(0)))
                .andExpect(jsonPath("$.countValuable", Is.is(0)))
                .andExpect(jsonPath("$.listTagDto[0].name", Is.is("tag")));
    }

    @Test
    @Sql(scripts = "script/TestQuestionResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void emptyOrNullTitleTest() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": null, \"description\": \"need help\",\"tags\": [{\"name\": \"tag\"}]}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = "script/TestQuestionResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void emptyOrNullDescriptionTest() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"hello\", \"description\": null,\"tags\": [{\"name\": \"tag\"}]}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = "script/TestQuestionResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void noTagsTest() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"hello\", \"description\": \"need help\",\"tags\": []}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = "script/TestQuestionResourceController/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "script/TestQuestionResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void tagNotExistTest() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"title\", \"description\": \"need help\",\"tags\": [{\"name\": \"tag 1\"}]}"))
                .andExpect(status().isOk());
    }
    @Test
    @Sql(scripts = "script/TestQuestionResourceController/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "script/TestQuestionResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void tagExistsTest() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"title\", \"description\": \"need help\",\"tags\": [{\"name\": \"tag\"}]}"))
                .andExpect(status().isOk());
    }
    @Test
    @Sql(scripts = "script/TestQuestionResourceController/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "script/TestQuestionResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void tagExistsJoiningTest() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"hello\", \"description\": \"need help\",\"tags\": [{\"name\": \"tag\"}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.listTagDto[0].name", Is.is("tag")));
    }
    @Test
    @Sql(scripts = "script/TestQuestionResourceController/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "script/TestQuestionResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void tagNotExistSavingTest() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"hi\", \"description\": \"hello\",\"tags\": [{\"name\": \"tag 1\"}]}"))
                .andExpect(status().isOk());
    }
}
