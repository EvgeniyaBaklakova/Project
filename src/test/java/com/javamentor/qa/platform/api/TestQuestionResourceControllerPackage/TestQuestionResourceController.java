package com.javamentor.qa.platform.api.TestQuestionResourceControllerPackage;

import com.javamentor.qa.platform.AbstractTestApi;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class TestQuestionResourceController extends AbstractTestApi {
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/Before1.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void viewAtFirstTime() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/1/view"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/Before2.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void wasAlreadyViewed() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/1/view"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/Before1.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void questionNotFound() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/2/view"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/QuestionDtoGetById/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/QuestionDtoGetById/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void questionGetById() throws Exception {

        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/question/100")
                        .header(AUTHORIZATION,"Bearer " + getToken("test100@mail.ru","123")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(100)))
                .andExpect(jsonPath("$.title", Is.is("title1")))
                .andExpect(jsonPath("$.authorId", Is.is(100)))
                .andExpect(jsonPath("$.authorReputation", Is.is(6)))
                .andExpect(jsonPath("$.authorName", Is.is("name1")))
                .andExpect(jsonPath("$.authorImage", Is.is("http://imagelink1.com")))
                .andExpect(jsonPath("$.description", Is.is("description1")))
                .andExpect(jsonPath("$.title", Is.is("title1")))
                .andExpect(jsonPath("$.viewCount", Is.is(2)))
                .andExpect(jsonPath("$.countAnswer", Is.is(3)))
                .andExpect(jsonPath("$.countValuable", Is.is(2)))
                .andExpect(jsonPath("$.persistDateTime", Is.is("2023-04-23T13:01:11.245126")))
                .andExpect(jsonPath("$.lastUpdateDateTime", Is.is("2023-04-23T13:01:11.245126")))
                .andExpect(jsonPath("$.listTagDto[0].id", Is.is(100)))
                .andExpect(jsonPath("$.listTagDto[0].name", Is.is("name1")))
                .andExpect(jsonPath("$.listTagDto[0].description", Is.is("description1")))
                .andExpect(jsonPath("$.listTagDto[1].id", Is.is(101)))
                .andExpect(jsonPath("$.listTagDto[1].name", Is.is("name2")))
                .andExpect(jsonPath("$.listTagDto[1].description", Is.is("description2")));


        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/question/104").header(AUTHORIZATION,"Bearer " + getToken("test100@mail.ru","123")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(104)))
                .andExpect(jsonPath("$.title", Is.is("title5")))
                .andExpect(jsonPath("$.authorId", Is.is(104)))
                .andExpect(jsonPath("$.authorReputation", Is.is(0)))
                .andExpect(jsonPath("$.authorName", Is.is("name5")))
                .andExpect(jsonPath("$.authorImage", IsNull.nullValue()))
                .andExpect(jsonPath("$.description", Is.is("description5")))
                .andExpect(jsonPath("$.title", Is.is("title5")))
                .andExpect(jsonPath("$.viewCount", Is.is(0)))
                .andExpect(jsonPath("$.countAnswer", Is.is(0)))
                .andExpect(jsonPath("$.countValuable", Is.is(0)))
                .andExpect(jsonPath("$.persistDateTime", Is.is("2023-04-23T13:01:11.245126")))
                .andExpect(jsonPath("$.lastUpdateDateTime", Is.is("2023-04-23T13:01:11.245126")));


    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/QuestionDtoGetById/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/QuestionDtoGetById/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void noQuestionGetById() throws Exception {

        this.mvc.perform(get("/api/user/question/{id}", 111).header(AUTHORIZATION,"Bearer " + getToken("test100@mail.ru","123") ))
                .andDo(print())
                .andExpect(status().isBadRequest());


    }
    public String getToken(String email, String password) {
        String token;
        Map<String,String> map = new HashMap<>();
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