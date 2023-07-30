package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractTestApi;
import io.swagger.annotations.ApiOperation;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @ApiOperation(value = "Проверка на возвращение пустого массива")
    @Sql(scripts = "/script/TestChatResourceController/TestGetGroupChatDtoEmpty/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getMessageDto() throws Exception {


        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/chat/55/single/message")
                        .header(AUTHORIZATION, getToken("test123@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(1)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(2)))
                .andExpect(jsonPath("$.items", hasSize(2)))


                .andExpect(jsonPath("$.items[0].id", Is.is(1)))
                .andExpect(jsonPath("$.items[0].message", Is.is("single_chat_message1")))
                .andExpect(jsonPath("$.items[0].nickname", Is.is("Marty")))
                .andExpect(jsonPath("$.items[0].userId", Is.is(123)))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("No link")))
                .andExpect(jsonPath("$.items[0].persistDateTime", Is.is("2023-01-01T00:00:00")))


                .andExpect(jsonPath("$.items[1].id", Is.is(2)))
                .andExpect(jsonPath("$.items[1].message", Is.is("single_chat_message2")))
                .andExpect(jsonPath("$.items[1].nickname", Is.is("Dogmatix")))
                .andExpect(jsonPath("$.items[1].userId", Is.is(124)))
                .andExpect(jsonPath("$.items[1].imageLink", Is.is("No link")))
                .andExpect(jsonPath("$.items[1].persistDateTime", Is.is("2023-01-02T00:00:00")));


    }

}
