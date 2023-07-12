package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractTestApi;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestProfileUserResourceController extends AbstractTestApi {

    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void getCountAnswers() throws Exception {

        this.mvc.perform(get("/api/user/profile/question/week").
                        header(AUTHORIZATION, getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(1)));
    }

    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void getCountAnswersZero() throws Exception {

        this.mvc.perform(get("/api/user/profile/question/week").
                        header(AUTHORIZATION, getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(0)));
    }

    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void getAllQuestion() throws Exception {

        this.mvc.perform(get("/api/user/profile/questions/").
                        header(AUTHORIZATION, getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].questionId", Is.is(101)))
                .andExpect(jsonPath("$[0].questionTitle", Is.is("title2")))
                .andExpect(jsonPath("$[0].answerPersistDateTime", Is.is("2023-04-23T13:01:11.245126")))
                .andExpect(jsonPath("$[0].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$[0].tagDtoList[0].id", Is.is(102)))
                .andExpect(jsonPath("$[0].tagDtoList[0].name", Is.is("name3")))
                .andExpect(jsonPath("$[0].tagDtoList[0].description", Is.is("description3")))
                .andExpect(jsonPath("$[0].tagDtoList[0].persistDateTime", Is.is("2023-04-23T13:01:11.245126")))
                .andExpect(jsonPath("$[0].tagDtoList[1].id", Is.is(103)))
                .andExpect(jsonPath("$[0].tagDtoList[1].name", Is.is("name4")))
                .andExpect(jsonPath("$[0].tagDtoList[1].description", Is.is("description4")))
                .andExpect(jsonPath("$[0].tagDtoList[1].persistDateTime", Is.is("2023-04-23T13:01:11.245126")))
                .andExpect(jsonPath("$[0].tagDtoList[2].id", Is.is(104)))
                .andExpect(jsonPath("$[0].tagDtoList[2].name", Is.is("name5")))
                .andExpect(jsonPath("$[0].tagDtoList[2].description", Is.is("description5")))
                .andExpect(jsonPath("$[0].tagDtoList[2].persistDateTime", Is.is("2023-04-23T13:01:11.245126")));

    }

    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void getAllQuestionNoQuestion() throws Exception {

        this.mvc.perform(get("/api/user/profile/questions/").
                        header(AUTHORIZATION, getToken("test100@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.empty()));
    }

    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void getAllDeleteQuestion() throws Exception {

        this.mvc.perform(get("/api/user/profile/delete/questions").
                        header(AUTHORIZATION, getToken("test103@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].questionId", Is.is(103)))
                .andExpect(jsonPath("$[0].questionTitle", Is.is("title4")))
                .andExpect(jsonPath("$[0].answerPersistDateTime", Is.is("2023-04-23T13:01:11.245126")))
                .andExpect(jsonPath("$[0].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$[0].tagDtoList[0].id", Is.is(106)))
                .andExpect(jsonPath("$[0].tagDtoList[0].name", Is.is("name7")))
                .andExpect(jsonPath("$[0].tagDtoList[0].description", Is.is("description5")))
                .andExpect(jsonPath("$[0].tagDtoList[0].persistDateTime", Is.is("2023-04-23T13:01:11.245126")))
                .andExpect(jsonPath("$[0].tagDtoList[1].id", Is.is(107)))
                .andExpect(jsonPath("$[0].tagDtoList[1].name", Is.is("name8")))
                .andExpect(jsonPath("$[0].tagDtoList[1].description", Is.is("description5")))
                .andExpect(jsonPath("$[0].tagDtoList[1].persistDateTime", Is.is("2023-04-23T13:01:11.245126")))
                .andExpect(jsonPath("$[0].tagDtoList[2].id", Is.is(108)))
                .andExpect(jsonPath("$[0].tagDtoList[2].name", Is.is("name9")))
                .andExpect(jsonPath("$[0].tagDtoList[2].description", Is.is("description5")))
                .andExpect(jsonPath("$[0].tagDtoList[2].persistDateTime", Is.is("2023-04-23T13:01:11.245126")))
                .andExpect(jsonPath("$[0].tagDtoList[3].id", Is.is(109)))
                .andExpect(jsonPath("$[0].tagDtoList[3].name", Is.is("name10")))
                .andExpect(jsonPath("$[0].tagDtoList[3].description", Is.is("description5")))
                .andExpect(jsonPath("$[0].tagDtoList[3].persistDateTime", Is.is("2023-04-23T13:01:11.245126")));
    }

    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void getAllDeleteQuestionNoQuestion() throws Exception {

        this.mvc.perform(get("/api/user/profile/delete/questions").
                        header(AUTHORIZATION, getToken("test100@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.empty()));
    }

    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void getUserProfile() throws Exception {

        this.mvc.perform(get("/api/user/profile").
                        header(AUTHORIZATION, getToken("test100@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(100)))
                .andExpect(jsonPath("$.reputation", Is.is(6)))
                .andExpect(jsonPath("$.countAnswer", Is.is(2)))
                .andExpect(jsonPath("$.countQuestion", Is.is(0)))
                .andExpect(jsonPath("$.countView", Is.is(0)));

        this.mvc.perform(get("/api/user/profile").
                        header(AUTHORIZATION, getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(101)))
                .andExpect(jsonPath("$.reputation", Is.is(4)))
                .andExpect(jsonPath("$.countAnswer", Is.is(0)))
                .andExpect(jsonPath("$.countQuestion", Is.is(1)))
                .andExpect(jsonPath("$.countView", Is.is(1)));

        this.mvc.perform(get("/api/user/profile").
                        header(AUTHORIZATION, getToken("test102@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(102)))
                .andExpect(jsonPath("$.reputation", Is.is(5)))
                .andExpect(jsonPath("$.countAnswer", Is.is(2)))
                .andExpect(jsonPath("$.countQuestion", Is.is(1)))
                .andExpect(jsonPath("$.countView", Is.is(1)));
    }
}