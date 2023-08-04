package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractTestApi;
import com.javamentor.qa.platform.models.dto.chat.CreateSingleChatDto;
import io.swagger.annotations.ApiOperation;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Arrays;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TestChatResourceController extends AbstractTestApi {
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
                .andExpect(jsonPath("$[1].image", Is.is("second_user_image")))
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

    @Test
    @ApiOperation(value = "Проверка на возвращение single чатов авторизованного пользователя")
    @Sql(scripts = "/script/TestChatResourceController/TestGetSingleChatDto/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/TestGetSingleChatDto/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getSingleChatDto() throws Exception {

        String USER_TOKEN = getToken("test101@mail.ru", "123");

        this.mvc.perform(get("/api/user/chat/single")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", Is.is(113)))
                .andExpect(jsonPath("$[0].name", Is.is("third")))
                .andExpect(jsonPath("$[0].image", Is.is("third_user_image")))
                .andExpect(jsonPath("$[0].lastMessage", Is.is("2023-01-11_last_message_third_chat")))
                .andExpect(jsonPath("$[0].persistDateTimeLastMessage", Is.is("2023-01-11T00:00:00")))

                .andExpect(jsonPath("$[1].id", Is.is(114)))
                .andExpect(jsonPath("$[1].name", Is.is("second")))
                .andExpect(jsonPath("$[1].image", Is.is("second_user_image")))
                .andExpect(jsonPath("$[1].lastMessage", Is.is("2023-02-11_last_message_four_chat")))
                .andExpect(jsonPath("$[1].persistDateTimeLastMessage", Is.is("2023-02-11T00:00:00")))

                .andExpect(jsonPath("$.length()", Is.is(2)));
    }

    @Test
    @ApiOperation(value = "Проверка на добавление в групповой чат пользователя")
    @Sql(scripts = "/script/TestChatResourceController/TestTryAddUserToChat/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/TestTryAddUserToChat/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void tryAddUserToChat() throws Exception {

        String USER_TOKEN = getToken("email101@mail.com", "200");

        this.mvc.perform(post("/api/user/chat/group/110/join")
                        .content("101")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @ApiOperation(value = "Проверка на возвращение пустого массива")
    @Sql(scripts = "/script/TestChatResourceController/TestGetSingleChatDtoEmpty/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/TestGetSingleChatDtoEmpty/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getSingleChatDtoEmpty() throws Exception {

        String USER_TOKEN = getToken("test101@mail.ru", "123");

        this.mvc.perform(get("/api/user/chat/single")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @ApiOperation(value = "Проверка на возвращение group чатов авторизованного пользователя")
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
    @ApiOperation(value = "Проверка на возвращение пустого массива")
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
    @ApiOperation(value = "Пользователь существует в групповом чате")
    @Sql(scripts = "/script/TestChatResourceController/TestUserExistsToGroupChat/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/TestUserExistsToGroupChat/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void userExistsToGroupChat() throws Exception {

        String USER_TOKEN = getToken("email101@mail.com", "200");

        this.mvc.perform(post("/api/user/chat/group/110/join")
                        .content("101")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @ApiOperation(value = "Пользователь не найден")
    @Sql(scripts = "/script/TestChatResourceController/TestUserNotFound/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/TestUserNotFound/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void userNotFound() throws Exception {

        String USER_TOKEN = getToken("email101@mail.com", "200");

        this.mvc.perform(post("/api/user/chat/group/110/join")
                        .content("120")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    @ApiOperation(value = "Чат не существует")
    @Sql(scripts = "/script/TestChatResourceController/TestGroupChatNotFound/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/TestGroupChatNotFound/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void groupChatNotFound() throws Exception {

        String USER_TOKEN = getToken("email101@mail.com", "200");

        this.mvc.perform(post("/api/user/chat/group/113/join")
                        .content("101")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    @ApiOperation(value = "Получатель сообщения не найден")
    @Sql(scripts = "/script/TestChatResourceController/TestUserRecipientNotFound/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/TestUserRecipientNotFound/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void userRecipientNotFound() throws Exception {

        String USER_TOKEN = getToken("email101@mail.com", "200");

        this.mvc.perform(post("/api/user/chat/single")
                        .content("{\"userId\" : \"104\", \"message\" : \"привет\"}")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    @ApiOperation(value = "Создание singleChat и message")
    @Sql(scripts = "/script/TestChatResourceController/TestCreateSingleChatAndMessage/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/TestCreateSingleChatAndMessage/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void createSingleChatAndMessage() throws Exception {

        String USER_TOKEN = getToken("email101@mail.com", "200");

        this.mvc.perform(post("/api/user/chat/single")
                        .content("{\"userId\" : \"102\", \"message\" : \"привет\"}")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }
}
