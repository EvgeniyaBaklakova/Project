package com.javamentor.qa.platform.api.AnswerTestController;

import com.javamentor.qa.platform.AbstractTestApi;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookMarksControllerTest extends AbstractTestApi {

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/Before1.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

    public void answerDeleteIdFalse() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                        .post("/api/user/question/2/bookmark").header("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbDFAbWFpbC5jb20iLCJpYXQiOjE2ODE4MzUyMzgsImV4cCI6OTI0NjQ1NzgwMDB9.k9jrF8haSzBOolb6XWQ81_e0N0MisR7CWMa9ZHV4IFE" ))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    }
