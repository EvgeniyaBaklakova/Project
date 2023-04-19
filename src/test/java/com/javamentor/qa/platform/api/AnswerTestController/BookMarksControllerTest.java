package com.javamentor.qa.platform.api.AnswerTestController;

import com.javamentor.qa.platform.AbstractTestApi;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookMarksControllerTest extends AbstractTestApi {

    @Test
    @Sql(scripts = "/script/BookMarksControllerTest/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/BookMarksControllerTest/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

    public void addQuestionToBookmarks() throws Exception {

        System.out.println("START TEST");

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\" : \"test101@mail.ru\", \"password\" : \"a\"}")) //тут вставляете ваши данные
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");
        System.out.println(token);


        this.mvc.perform(MockMvcRequestBuilders
                        .post("/api/user/question/101/bookmark")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}
