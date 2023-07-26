package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractTestApi;
import io.swagger.annotations.ApiOperation;
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
                .andExpect(jsonPath("$[0].id", Is.is(111)))
                .andExpect(jsonPath("$[0].chatName", Is.is("first_chat")))
                .andExpect(jsonPath("$[0].lastMessage", Is.is("2023-01-03_last_message_first_chat")))
                .andExpect(jsonPath("$[0].image", Is.is("first_image")))
                .andExpect(jsonPath("$[0].persistDateTime", Is.is("2000-01-01T00:00:00")))

                .andExpect(jsonPath("$[1].id", Is.is(112)))
                .andExpect(jsonPath("$[1].chatName", Is.is("second_chat")))
                .andExpect(jsonPath("$[1].lastMessage", Is.is("2023-01-05_last_message_second_chat")))
                .andExpect(jsonPath("$[1].image", Is.is("second_image")))
                .andExpect(jsonPath("$[1].persistDateTime", Is.is("2000-02-01T00:00:00")))

                .andExpect(jsonPath("$.length()", Is.is(2)));
    }

    @Test
    @Sql(scripts = "/script/TestChatResourceController/TestGetGroupChatDtoEmpty/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/TestGetGroupChatDtoEmpty/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getGroupChatDtoEmpty() throws Exception {

        String USER_TOKEN = getToken("test101@mail.ru", "123");

        this.mvc.perform(get("/api/user/chat/group")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @ApiOperation(value = "Проверка на возвращение чатов (single и group) по имени. С сортировкой по дате")
    @Sql(scripts = "/script/TestChatResourceController/TestGetChatDtoByName/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/TestGetChatDtoByName/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getSingleAndGroupChatDtoByName() throws Exception {

        String USER_TOKEN = getToken("test101@mail.ru", "123");

        this.mvc.perform(get("/api/user/chat")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .param("chatName", "second")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", Is.is(112)))
                .andExpect(jsonPath("$[0].name", Is.is("second")))
                .andExpect(jsonPath("$[0].image", Is.is("second_image")))
                .andExpect(jsonPath("$[0].lastMessage", Is.is("2023-01-05_last_message_second_chat")))
                .andExpect(jsonPath("$[0].persistDateTimeLastMessage", Is.is("2023-01-05T00:00:00")))

                .andExpect(jsonPath("$[1].id", Is.is(114)))
                .andExpect(jsonPath("$[1].name", Is.is("second")))
                .andExpect(jsonPath("$[1].image", Is.is("No link")))
                .andExpect(jsonPath("$[1].lastMessage", Is.is("2023-02-11_last_message_four_chat")))
                .andExpect(jsonPath("$[1].persistDateTimeLastMessage", Is.is("2023-02-11T00:00:00")))

                .andExpect(jsonPath("$.length()", Is.is(2)));
    }

    @Test
    @ApiOperation(value = "Проверка на возвращение group чата по имени равному имени авторизованного пользователя")
    @Sql(scripts = "/script/TestChatResourceController/TestGetChatDtoByName/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/TestGetChatDtoByName/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getGroupChatDtoByName() throws Exception {

        String USER_TOKEN = getToken("test101@mail.ru", "123");

        this.mvc.perform(get("/api/user/chat")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .param("chatName", "first")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", Is.is(111)))
                .andExpect(jsonPath("$[0].name", Is.is("first")))
                .andExpect(jsonPath("$[0].image", Is.is("first_image")))
                .andExpect(jsonPath("$[0].lastMessage", Is.is("2023-01-03_last_message_first_chat")))
                .andExpect(jsonPath("$[0].persistDateTimeLastMessage", Is.is("2023-01-03T00:00:00")))

                .andExpect(jsonPath("$.length()", Is.is(1)));
    }

    @Test
    @ApiOperation(value = "Проверка на возвращение пустого массива")
    @Sql(scripts = "/script/TestChatResourceController/TestGetChatDtoByNameEmpty/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/TestGetChatDtoByNameEmpty/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getChatDtoByNameEmpty() throws Exception {

        String USER_TOKEN = getToken("test101@mail.ru", "123");

        this.mvc.perform(get("/api/user/chat")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .param("chatName", "null")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }
}