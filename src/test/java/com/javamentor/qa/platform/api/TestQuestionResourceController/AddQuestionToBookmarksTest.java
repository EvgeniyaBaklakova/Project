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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AddQuestionToBookmarksTest extends AbstractTestApi {
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/AddQuestionToBookmarks/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/AddQuestionToBookmarks/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void addQuestionToBookmarks() throws Exception {

        System.out.println("START TEST");

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\" : \"test100@mail.ru\", \"password\" : \"password\"}")) //тут вставляете ваши данные
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");
        System.out.println(token);


        this.mvc.perform(MockMvcRequestBuilders
                        .post("/api/user/question/102/bookmark")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is("Вопрос успешно добавлен в закладки")));
    }

}
