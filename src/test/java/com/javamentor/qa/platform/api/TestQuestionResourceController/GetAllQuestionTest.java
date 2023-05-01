package com.javamentor.qa.platform.api.TestQuestionResourceController;

import com.javamentor.qa.platform.AbstractTestApi;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetAllQuestionTest  extends AbstractTestApi {

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/GetAllQuestionApiTest/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/GetAllQuestionApiTest/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

    public void getAllQuestion() throws Exception {

        System.out.println("START TEST");

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\" : \"test101@mail.ru\", \"password\" : \"password\"}")) //тут вставляете ваши данные
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");
        System.out.println(token);


        this.mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/profile/questions/")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
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
                .andExpect(jsonPath("$[0].tagDtoList[2].persistDateTime", Is.is("2023-04-23T13:01:11.245126")));;


    }

}

