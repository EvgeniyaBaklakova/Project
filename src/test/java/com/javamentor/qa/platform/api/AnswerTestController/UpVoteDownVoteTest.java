package com.javamentor.qa.platform.api.AnswerTestController;

import com.javamentor.qa.platform.AbstractTestApi;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UpVoteDownVoteTest extends AbstractTestApi {

    @Test
    @Sql(scripts = "/script/AnswerResourceControllerTest/AnswerUpVoteDownVoteTest/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/AnswerResourceControllerTest/AnswerUpVoteDownVoteTest/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void upVoteTest() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/201/answer/301/upVote")
                        .header("Authorization", "Bearer " + getToken("email@mail.ru", "test")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(10)));
    }

    @Test
    @Sql(scripts = "/script/AnswerResourceControllerTest/AnswerUpVoteDownVoteTest/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/AnswerResourceControllerTest/AnswerUpVoteDownVoteTest/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void downVoteTest() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/201/answer/301/downVote")
                        .header("Authorization", "Bearer " + getToken("email@mail.ru", "test")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(-5)));
    }

    @Test
    @Sql(scripts = "/script/AnswerResourceControllerTest/AnswerUpVoteDownVoteTest/Before1.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/AnswerResourceControllerTest/AnswerUpVoteDownVoteTest/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void tryToVoteAnswerUpTwoTimes() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/201/answer/301/upVote")
                        .header("Authorization", "Bearer " + getToken("email@mail.ru", "test")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(10)));
    }

    @Test
    @Sql(scripts = "/script/AnswerResourceControllerTest/AnswerUpVoteDownVoteTest/Before2.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/AnswerResourceControllerTest/AnswerUpVoteDownVoteTest/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void tryToVoteAnswerDownTwoTimes() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/201/answer/301/downVote")
                        .header("Authorization", "Bearer " + getToken("email@mail.ru", "test")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(-5)));
    }

    @Test
    @Sql(scripts = "/script/AnswerResourceControllerTest/AnswerUpVoteDownVoteTest/Before2.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/AnswerResourceControllerTest/AnswerUpVoteDownVoteTest/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void tryToVoteAnswerUpAndDown() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/201/answer/301/upVote")
                        .header("Authorization", "Bearer " + getToken("email@mail.ru", "test")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(-5)));
    }

    public String getToken(String email, String password) {
        String token;
        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        try {
            String response = (this.mvc.perform(MockMvcRequestBuilders
                            .post("/api/auth/token").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(map)))
                    .andReturn().getResponse().getContentAsString());
            token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");
            return token;
        } catch (Exception ignored) {
        }
        return "";
    }
}
