package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractTestApi;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TestChatResourceController extends AbstractTestApi {
    @Test
    @Sql(scripts = "/script/TestChatResourceController/TestGetGroupChatDto/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/TestGetGroupChatDto/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getGroupChatDto() throws Exception {

        String USER_TOKEN = getToken("test101@mail.ru", "123");

        this.mvc.perform(get("/api/user/chat/group")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", Is.is(111)))
                .andExpect(jsonPath("$[0].chatName", Is.is("first_chat")))
                .andExpect(jsonPath("$[0].lastMessage", Is.is("last_message_111_chat")))
                .andExpect(jsonPath("$[0].image", Is.is("first_image")))
                .andExpect(jsonPath("$[0].persistDateTime", Is.is("2000-01-01T00:00:00")))
                .andExpect(jsonPath("$[1].id", Is.is(112)))
                .andExpect(jsonPath("$[1].chatName", Is.is("second_chat")))
                .andExpect(jsonPath("$[1].lastMessage", Is.is("last_message_112_chat")))
                .andExpect(jsonPath("$[1].image", Is.is("second_image")))
                .andExpect(jsonPath("$[1].persistDateTime", Is.is("2000-02-01T00:00:00")));
    }
}
