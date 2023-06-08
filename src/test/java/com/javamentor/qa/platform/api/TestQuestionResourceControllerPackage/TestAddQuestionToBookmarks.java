package com.javamentor.qa.platform.api.TestQuestionResourceControllerPackage;

import com.javamentor.qa.platform.AbstractTestApi;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestAddQuestionToBookmarks extends AbstractTestApi {
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/AddQuestionToBookmarks/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/AddQuestionToBookmarks/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void addQuestionToBookmarks() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/102/bookmark").header("Authorization","Bearer " + getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is("Вопрос успешно добавлен в закладки")));
    }

    public String getToken(String email, String password) {
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
