package com.javamentor.qa.platform.api.TestQuestionResourceController;

import com.javamentor.qa.platform.AbstractTestApi;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentAddingTest extends AbstractTestApi {

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/CommentAddingApiTest/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/CommentAddingApiTest/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void allOkTest() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                .post("/api/user/question/1/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"text\":\"test message\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", Is.is("test message")));
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/CommentAddingApiTest/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/CommentAddingApiTest/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void nullTextTest() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                        .post("/api/user/question/1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\": null}"))
                .andExpect(status().is4xxClientError());
    }
}
