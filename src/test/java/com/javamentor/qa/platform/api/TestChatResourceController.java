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
    @ApiOperation(value = "Вывод всех MessageDto из SingleChat")
    @Sql(scripts = "/script/TestChatResourceController/TestGetMessageDtoByChatId/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestChatResourceController/TestGetMessageDtoByChatId/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getMessageDto() throws Exception {


        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/chat/55/single/message")
                        .header(AUTHORIZATION, getToken("test123@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(2)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(15)))
                .andExpect(jsonPath("$.items", hasSize(10)))


                .andExpect(jsonPath("$.items[0].id", Is.is(15)))
                .andExpect(jsonPath("$.items[0].message", Is.is("single_chat_message15")))
                .andExpect(jsonPath("$.items[0].nickname", Is.is("Marty")))
                .andExpect(jsonPath("$.items[0].userId", Is.is(123)))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("No link")))
                .andExpect(jsonPath("$.items[0].persistDateTime", Is.is("2023-01-15T00:00:00")))


                .andExpect(jsonPath("$.items[1].id", Is.is(14)))
                .andExpect(jsonPath("$.items[1].message", Is.is("single_chat_message14")))
                .andExpect(jsonPath("$.items[1].nickname", Is.is("Marty")))
                .andExpect(jsonPath("$.items[1].userId", Is.is(123)))
                .andExpect(jsonPath("$.items[1].imageLink", Is.is("No link")))
                .andExpect(jsonPath("$.items[1].persistDateTime", Is.is("2023-01-14T00:00:00")))


                .andExpect(jsonPath("$.items[2].id", Is.is(13)))
                .andExpect(jsonPath("$.items[2].message", Is.is("single_chat_message13")))
                .andExpect(jsonPath("$.items[2].nickname", Is.is("Dogmatix")))
                .andExpect(jsonPath("$.items[2].userId", Is.is(124)))
                .andExpect(jsonPath("$.items[2].imageLink", Is.is("No link")))
                .andExpect(jsonPath("$.items[2].persistDateTime", Is.is("2023-01-13T00:00:00")))

                .andExpect(jsonPath("$.items[3].id", Is.is(12)))
                .andExpect(jsonPath("$.items[3].message", Is.is("single_chat_message12")))
                .andExpect(jsonPath("$.items[3].nickname", Is.is("Marty")))
                .andExpect(jsonPath("$.items[3].userId", Is.is(123)))
                .andExpect(jsonPath("$.items[3].imageLink", Is.is("No link")))
                .andExpect(jsonPath("$.items[3].persistDateTime", Is.is("2023-01-12T00:00:00")))

                .andExpect(jsonPath("$.items[4].id", Is.is(11)))
                .andExpect(jsonPath("$.items[4].message", Is.is("single_chat_message11")))
                .andExpect(jsonPath("$.items[4].nickname", Is.is("Dogmatix")))
                .andExpect(jsonPath("$.items[4].userId", Is.is(124)))
                .andExpect(jsonPath("$.items[4].imageLink", Is.is("No link")))
                .andExpect(jsonPath("$.items[4].persistDateTime", Is.is("2023-01-11T00:00:00")))

                .andExpect(jsonPath("$.items[5].id", Is.is(10)))
                .andExpect(jsonPath("$.items[5].message", Is.is("single_chat_message10")))
                .andExpect(jsonPath("$.items[5].nickname", Is.is("Dogmatix")))
                .andExpect(jsonPath("$.items[5].userId", Is.is(124)))
                .andExpect(jsonPath("$.items[5].imageLink", Is.is("No link")))
                .andExpect(jsonPath("$.items[5].persistDateTime", Is.is("2023-01-10T00:00:00")))

                .andExpect(jsonPath("$.items[6].id", Is.is(9)))
                .andExpect(jsonPath("$.items[6].message", Is.is("single_chat_message9")))
                .andExpect(jsonPath("$.items[6].nickname", Is.is("Marty")))
                .andExpect(jsonPath("$.items[6].userId", Is.is(123)))
                .andExpect(jsonPath("$.items[6].imageLink", Is.is("No link")))
                .andExpect(jsonPath("$.items[6].persistDateTime", Is.is("2023-01-09T00:00:00")))

                .andExpect(jsonPath("$.items[7].id", Is.is(8)))
                .andExpect(jsonPath("$.items[7].message", Is.is("single_chat_message8")))
                .andExpect(jsonPath("$.items[7].nickname", Is.is("Dogmatix")))
                .andExpect(jsonPath("$.items[7].userId", Is.is(124)))
                .andExpect(jsonPath("$.items[7].imageLink", Is.is("No link")))
                .andExpect(jsonPath("$.items[7].persistDateTime", Is.is("2023-01-08T00:00:00")))

                .andExpect(jsonPath("$.items[8].id", Is.is(7)))
                .andExpect(jsonPath("$.items[8].message", Is.is("single_chat_message7")))
                .andExpect(jsonPath("$.items[8].nickname", Is.is("Marty")))
                .andExpect(jsonPath("$.items[8].userId", Is.is(123)))
                .andExpect(jsonPath("$.items[8].imageLink", Is.is("No link")))
                .andExpect(jsonPath("$.items[8].persistDateTime", Is.is("2023-01-07T00:00:00")))

                .andExpect(jsonPath("$.items[9].id", Is.is(6)))
                .andExpect(jsonPath("$.items[9].message", Is.is("single_chat_message6")))
                .andExpect(jsonPath("$.items[9].nickname", Is.is("Marty")))
                .andExpect(jsonPath("$.items[9].userId", Is.is(123)))
                .andExpect(jsonPath("$.items[9].imageLink", Is.is("No link")))
                .andExpect(jsonPath("$.items[9].persistDateTime", Is.is("2023-01-06T00:00:00")));

        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/chat/55/single/message?page=2&items=10")
                        .header(AUTHORIZATION, getToken("test123@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(2)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(2)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(15)))
                .andExpect(jsonPath("$.items", hasSize(5)))

                .andExpect(jsonPath("$.items[0].id", Is.is(5)))
                .andExpect(jsonPath("$.items[0].message", Is.is("single_chat_message5")))
                .andExpect(jsonPath("$.items[0].nickname", Is.is("Dogmatix")))
                .andExpect(jsonPath("$.items[0].userId", Is.is(124)))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("No link")))
                .andExpect(jsonPath("$.items[0].persistDateTime", Is.is("2023-01-05T00:00:00")))

                .andExpect(jsonPath("$.items[1].id", Is.is(4)))
                .andExpect(jsonPath("$.items[1].message", Is.is("single_chat_message4")))
                .andExpect(jsonPath("$.items[1].nickname", Is.is("Marty")))
                .andExpect(jsonPath("$.items[1].userId", Is.is(123)))
                .andExpect(jsonPath("$.items[1].imageLink", Is.is("No link")))
                .andExpect(jsonPath("$.items[1].persistDateTime", Is.is("2023-01-04T00:00:00")))


                .andExpect(jsonPath("$.items[2].id", Is.is(3)))
                .andExpect(jsonPath("$.items[2].message", Is.is("single_chat_message3")))
                .andExpect(jsonPath("$.items[2].nickname", Is.is("Dogmatix")))
                .andExpect(jsonPath("$.items[2].userId", Is.is(124)))
                .andExpect(jsonPath("$.items[2].imageLink", Is.is("No link")))
                .andExpect(jsonPath("$.items[2].persistDateTime", Is.is("2023-01-03T00:00:00")))


                .andExpect(jsonPath("$.items[3].id", Is.is(2)))
                .andExpect(jsonPath("$.items[3].message", Is.is("single_chat_message2")))
                .andExpect(jsonPath("$.items[3].nickname", Is.is("Dogmatix")))
                .andExpect(jsonPath("$.items[3].userId", Is.is(124)))
                .andExpect(jsonPath("$.items[3].imageLink", Is.is("No link")))
                .andExpect(jsonPath("$.items[3].persistDateTime", Is.is("2023-01-02T00:00:00")))


                .andExpect(jsonPath("$.items[4].id", Is.is(1)))
                .andExpect(jsonPath("$.items[4].message", Is.is("single_chat_message1")))
                .andExpect(jsonPath("$.items[4].nickname", Is.is("Marty")))
                .andExpect(jsonPath("$.items[4].userId", Is.is(123)))
                .andExpect(jsonPath("$.items[4].imageLink", Is.is("No link")))
                .andExpect(jsonPath("$.items[4].persistDateTime", Is.is("2023-01-01T00:00:00")));


        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/chat/55/single/message?items=2")
                        .header(AUTHORIZATION, getToken("test123@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(8)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(15)))
                .andExpect(jsonPath("$.items", hasSize(2)));


    }

}
