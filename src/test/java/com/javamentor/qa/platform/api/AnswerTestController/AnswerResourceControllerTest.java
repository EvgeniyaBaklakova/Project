package com.javamentor.qa.platform.api.AnswerTestController;

import com.javamentor.qa.platform.AbstractTestApi;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
                 assertTrue (answerDeleteIdTest(102L));

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

    @Test
    @Sql(scripts = "/script/AnswerResourceControllerTest/AnswerGetAllTest/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/AnswerResourceControllerTest/AnswerGetAllTest/After.sql",
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
    @Sql(scripts = "/script/AnswerResourceControllerTest/AnswerGetAllTest/Before2.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/AnswerResourceControllerTest/AnswerGetAllTest/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllAnswersWOAnyTest() throws Exception {
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

    private String getToken(String email, String password) {
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
