package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractTestApi;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestAnswerResourceController extends AbstractTestApi {

    @Test
    @Sql(scripts = "/script/TestAnswerResourceController/TestAnswerDeleteId/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestAnswerResourceController/TestAnswerDeleteId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void answerDeleteIdFalse() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                        .delete("/api/user/question/101/answer/101"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
                 assertFalse(answerDeleteIdTest(101L),()-> "isDeleted = false! ответ не удален.");
    }

    @Test
    @Sql(scripts = "/script/TestAnswerResourceController/TestAnswerDeleteId/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestAnswerResourceController/TestAnswerDeleteId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void answerDeleteId() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                        .delete("/api/user/question/102/answer/102"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
                 Assertions.assertTrue(answerDeleteIdTest(102L));
    }

    @Test
    @Sql(scripts = "/script/TestAnswerResourceController/TestAnswerDeleteId/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestAnswerResourceController/TestAnswerDeleteId/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void answerDeleteId_request() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                        .delete("/api/user/question/102/answer/103"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Sql(scripts = "/script/TestAnswerResourceController/TestAnswerGetAll/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestAnswerResourceController/TestAnswerGetAll/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllAnswersTest() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/question/102/answer/").header("Authorization","Bearer " + getToken("test102@mail.ru", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Is.is(101)))
                .andExpect(jsonPath("$[1].id", Is.is(102)))
                .andExpect(jsonPath("$[0].userReputation", Is.is(20)))
                .andExpect(jsonPath("$[1].userReputation", Is.is(10)))
                .andExpect(jsonPath("$[0].countValuable", Is.is(0)))
                .andExpect(jsonPath("$[1].countValuable", Is.is(1)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Sql(scripts = "/script/TestAnswerResourceController/TestAnswerGetAll/Before1.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestAnswerResourceController/TestAnswerGetAll/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void countValuableTest() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/question/102/answer/").header("Authorization","Bearer " + getToken("test101@mail.ru", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Is.is(101)))
                .andExpect(jsonPath("$[0].userReputation", Is.is(30)))
                .andExpect(jsonPath("$[0].countValuable", Is.is(2)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Sql(scripts = "/script/TestAnswerResourceController/TestAnswerGetAll/Before2.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestAnswerResourceController/TestAnswerGetAll/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllAnswersWithoutAnyTest() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/question/102/answer/").header("Authorization","Bearer " + getToken("test102@mail.ru", "password")))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

      private boolean answerDeleteIdTest(Long answerId) {
        long count = (long) em.createQuery("SELECT Count(a) FROM Answer a  WHERE a.id =: id")
                .setParameter("id", answerId)
                .getSingleResult();
        return count > 0;
    }
}