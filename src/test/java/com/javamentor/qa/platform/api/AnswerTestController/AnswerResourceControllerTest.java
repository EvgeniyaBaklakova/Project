package com.javamentor.qa.platform.api.AnswerTestController;

import com.javamentor.qa.platform.AbstractTestApi;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AnswerResourceControllerTest extends AbstractTestApi {

    @Test
    @Sql(scripts = "/script/AnswerResourceControllerTest/AnswerDeleteIdTest/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/AnswerResourceControllerTest/AnswerDeleteIdTest/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

    public void answerDeleteIdFalse() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                        .delete("/api/user/question/101/answer/101"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
                 assertFalse(answerDeleteIdTest(101L),()-> "isDeleted = false! ответ не удален.");

    }

    @Test
    @Sql(scripts = "/script/AnswerResourceControllerTest/AnswerDeleteIdTest/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/AnswerResourceControllerTest/AnswerDeleteIdTest/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void answerDeleteId() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                        .delete("/api/user/question/102/answer/102"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
                 assertFalse(answerDeleteIdTest(102L),()-> "isDeleted = false! ответ не удален.");
    }

    @Test
    @Sql(scripts = "/script/AnswerResourceControllerTest/AnswerDeleteIdTest/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/AnswerResourceControllerTest/AnswerDeleteIdTest/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

    public void answerDeleteId_request() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                        .delete("/api/user/question/102/answer/103"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

    }

    public boolean answerDeleteIdTest(Long answerId) {
        long count = (long) em.createQuery("SELECT Count(DISTINCT a) FROM Answer a WHERE a.id =: id")
                .setParameter("id", answerId)
                .getSingleResult();
        return count > 0;
    }
}
