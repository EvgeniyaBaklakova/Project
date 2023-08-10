package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractTestApi;
import io.swagger.annotations.ApiOperation;
import org.hamcrest.Matchers;
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

public class TestProfileUserResourceController extends AbstractTestApi {

    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void getCountAnswers() throws Exception {

        this.mvc.perform(get("/api/user/profile/question/week").
                        header(AUTHORIZATION, getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(1)));
    }

    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void getCountAnswersZero() throws Exception {

        this.mvc.perform(get("/api/user/profile/question/week").
                        header(AUTHORIZATION, getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(0)));
    }

    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void getAllQuestion() throws Exception {

        this.mvc.perform(get("/api/user/profile/questions/").
                        header(AUTHORIZATION, getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].questionId", Is.is(101)))
                .andExpect(jsonPath("$[0].questionTitle", Is.is("title2")))
                .andExpect(jsonPath("$[0].answerPersistDateTime", Is.is("2023-04-23T13:01:11.245126")))
                .andExpect(jsonPath("$[0].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$[0].tagDtoList[0].id", Is.is(102)))
                .andExpect(jsonPath("$[0].tagDtoList[0].name", Is.is("name3")))
                .andExpect(jsonPath("$[0].tagDtoList[0].description", Is.is("description3")))
                .andExpect(jsonPath("$[0].tagDtoList[0].persistDateTime", Is.is("2023-04-23T13:01:11.245126")))
                .andExpect(jsonPath("$[0].tagDtoList[1].id", Is.is(103)))
                .andExpect(jsonPath("$[0].tagDtoList[1].name", Is.is("name4")))
                .andExpect(jsonPath("$[0].tagDtoList[1].description", Is.is("description4")))
                .andExpect(jsonPath("$[0].tagDtoList[1].persistDateTime", Is.is("2023-04-23T13:01:11.245126")))
                .andExpect(jsonPath("$[0].tagDtoList[2].id", Is.is(104)))
                .andExpect(jsonPath("$[0].tagDtoList[2].name", Is.is("name5")))
                .andExpect(jsonPath("$[0].tagDtoList[2].description", Is.is("description5")))
                .andExpect(jsonPath("$[0].tagDtoList[2].persistDateTime", Is.is("2023-04-23T13:01:11.245126")));

    }

    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void getAllQuestionNoQuestion() throws Exception {

        this.mvc.perform(get("/api/user/profile/questions/").
                        header(AUTHORIZATION, getToken("test100@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.empty()));
    }

    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void getAllDeleteQuestion() throws Exception {

        this.mvc.perform(get("/api/user/profile/delete/questions").
                        header(AUTHORIZATION, getToken("test103@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].questionId", Is.is(103)))
                .andExpect(jsonPath("$[0].questionTitle", Is.is("title4")))
                .andExpect(jsonPath("$[0].answerPersistDateTime", Is.is("2023-04-23T13:01:11.245126")))
                .andExpect(jsonPath("$[0].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$[0].tagDtoList[0].id", Is.is(106)))
                .andExpect(jsonPath("$[0].tagDtoList[0].name", Is.is("name7")))
                .andExpect(jsonPath("$[0].tagDtoList[0].description", Is.is("description5")))
                .andExpect(jsonPath("$[0].tagDtoList[0].persistDateTime", Is.is("2023-04-23T13:01:11.245126")))
                .andExpect(jsonPath("$[0].tagDtoList[1].id", Is.is(107)))
                .andExpect(jsonPath("$[0].tagDtoList[1].name", Is.is("name8")))
                .andExpect(jsonPath("$[0].tagDtoList[1].description", Is.is("description5")))
                .andExpect(jsonPath("$[0].tagDtoList[1].persistDateTime", Is.is("2023-04-23T13:01:11.245126")))
                .andExpect(jsonPath("$[0].tagDtoList[2].id", Is.is(108)))
                .andExpect(jsonPath("$[0].tagDtoList[2].name", Is.is("name9")))
                .andExpect(jsonPath("$[0].tagDtoList[2].description", Is.is("description5")))
                .andExpect(jsonPath("$[0].tagDtoList[2].persistDateTime", Is.is("2023-04-23T13:01:11.245126")))
                .andExpect(jsonPath("$[0].tagDtoList[3].id", Is.is(109)))
                .andExpect(jsonPath("$[0].tagDtoList[3].name", Is.is("name10")))
                .andExpect(jsonPath("$[0].tagDtoList[3].description", Is.is("description5")))
                .andExpect(jsonPath("$[0].tagDtoList[3].persistDateTime", Is.is("2023-04-23T13:01:11.245126")));
    }

    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void getAllDeleteQuestionNoQuestion() throws Exception {

        this.mvc.perform(get("/api/user/profile/delete/questions").
                        header(AUTHORIZATION, getToken("test100@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.empty()));
    }

    @Test
    @Sql(scripts = "/script/TestProfileUserResourceController/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void getUserProfile() throws Exception {

        this.mvc.perform(get("/api/user/profile").
                        header(AUTHORIZATION, getToken("test100@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(100)))
                .andExpect(jsonPath("$.reputation", Is.is(6)))
                .andExpect(jsonPath("$.countAnswer", Is.is(2)))
                .andExpect(jsonPath("$.countQuestion", Is.is(0)))
                .andExpect(jsonPath("$.countView", Is.is(0)));

        this.mvc.perform(get("/api/user/profile").
                        header(AUTHORIZATION, getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(101)))
                .andExpect(jsonPath("$.reputation", Is.is(4)))
                .andExpect(jsonPath("$.countAnswer", Is.is(0)))
                .andExpect(jsonPath("$.countQuestion", Is.is(1)))
                .andExpect(jsonPath("$.countView", Is.is(1)))
                .andExpect(jsonPath("$.tagDtoList[0].tagId", Is.is(102)))
                .andExpect(jsonPath("$.tagDtoList[0].name", Is.is("name3")))
                .andExpect(jsonPath("$.tagDtoList[0].countMessage", Is.is(2)));

        this.mvc.perform(get("/api/user/profile").
                        header(AUTHORIZATION, getToken("test102@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isForbidden());

        this.mvc.perform(get("/api/user/profile").
                        header(AUTHORIZATION, getToken("test103@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(103)))
                .andExpect(jsonPath("$.reputation", Matchers.nullValue()))
                .andExpect(jsonPath("$.countAnswer", Is.is(1)))
                .andExpect(jsonPath("$.countQuestion", Is.is(1)))
                .andExpect(jsonPath("$.countView", Is.is(2)))
                .andExpect(jsonPath("$.tagDtoList[0].tagId", Is.is(106)))
                .andExpect(jsonPath("$.tagDtoList[0].name", Is.is("name7")))
                .andExpect(jsonPath("$.tagDtoList[0].countMessage", Is.is(2)));
    }

    @Test
    @ApiOperation(
            value = "Проверка на возвращение List<UserProfileTagDto>" +

                    "tagName - имя тега отслеживаемого пользователем" +
                    "countVoteTag - количество голосов над всеми вопросами и ответами пользователя (положительные минус отрицательные)" +
                    "countAnswerQuestion - количество всех вопросов и ответов пользователя, под отслеживаемым тегом" +

                    "Количество возвращаемых тегов 10, сортировка по полю countAnswerQuestion"
    )
    @Sql(scripts = "/script/TestProfileUserResourceController/TestGetUserProfileTagDto/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/TestGetUserProfileTagDto/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getUserProfileTagDto() throws Exception {

        String USER_TOKEN = getToken("test101@mail.ru", "123");

        this.mvc.perform(get("/api/user/profile/tag")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].tagName", Is.is("java")))
                .andExpect(jsonPath("$[0].countVoteTag", Is.is(3)))
                .andExpect(jsonPath("$[0].countAnswerQuestion", Is.is(5)))

                .andExpect(jsonPath("$[1].tagName", Is.is("twelfth")))
                .andExpect(jsonPath("$[1].countVoteTag", Is.is(2)))
                .andExpect(jsonPath("$[1].countAnswerQuestion", Is.is(3)))

                .andExpect(jsonPath("$[2].tagName", Is.is("spring")))
                .andExpect(jsonPath("$[2].countVoteTag", Is.is(-4)))
                .andExpect(jsonPath("$[2].countAnswerQuestion", Is.is(2)))

                .andExpect(jsonPath("$.length()", Is.is(10)));
    }

    @Test
    @ApiOperation(value = "Проверка на возвращение List<UserProfileTagDto> " +
            "в котором у авторизованного пользователя нет ни вопросов ни ответов")
    @Sql(scripts = "/script/TestProfileUserResourceController/TestGetUserProfileTagDtoWithoutQuestionsAndAnswers/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/TestGetUserProfileTagDtoWithoutQuestionsAndAnswers/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getUserProfileTagDtoWithoutQuestionsAndAnswers() throws Exception {

        String USER_TOKEN = getToken("test101@mail.ru", "123");

        this.mvc.perform(get("/api/user/profile/tag")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @ApiOperation(value = "Проверка на возвращение пустого List<UserProfileTagDto>")
    @Sql(scripts = "/script/TestProfileUserResourceController/TestGetUserProfileTagDtoEmpty/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/TestGetUserProfileTagDtoEmpty/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getUserProfileTagDtoEmpty() throws Exception {

        String USER_TOKEN = getToken("test101@mail.ru", "123");

        this.mvc.perform(get("/api/user/profile/tag")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @ApiOperation(value = "Проверка на возвращение страницы по умолчанию (PageDto<UserProfileCommentDto>), " +
            "с пагинацией и сортировкой по дате")
    @Sql(scripts = "/script/TestProfileUserResourceController/TestGetUserProfileCommentDto/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/TestGetUserProfileCommentDto/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getUserProfileCommentDtoDefaultPage() throws Exception {

        String USER_TOKEN = getToken("test101@mail.ru", "123");

        this.mvc.perform(get("/api/user/profile/comment")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("totalPageCount", Is.is(3)))
                .andExpect(jsonPath("totalResultCount", Is.is(23)))

                .andExpect(jsonPath("items[0].id", Is.is(429)))
                .andExpect(jsonPath("items[0].comment", Is.is("any answer text")))
                .andExpect(jsonPath("items[0].persistDate", Is.is("2000-02-09T00:00:00")))
                .andExpect(jsonPath("items[0].questionId").isEmpty())
                .andExpect(jsonPath("items[0].answerId", Is.is(309)))
                .andExpect(jsonPath("items[0].commentType", Is.is("ANSWER")))

                .andExpect(jsonPath("items[1].id", Is.is(428)))
                .andExpect(jsonPath("items[1].comment", Is.is("any answer text")))
                .andExpect(jsonPath("items[1].persistDate", Is.is("2000-02-08T00:00:00")))
                .andExpect(jsonPath("items[1].questionId").isEmpty())
                .andExpect(jsonPath("items[1].answerId", Is.is(308)))
                .andExpect(jsonPath("items[1].commentType", Is.is("ANSWER")))

                .andExpect(jsonPath("items[2].id", Is.is(427)))
                .andExpect(jsonPath("items[2].comment", Is.is("any answer text")))
                .andExpect(jsonPath("items[2].persistDate", Is.is("2000-02-07T00:00:00")))
                .andExpect(jsonPath("items[2].questionId").isEmpty())
                .andExpect(jsonPath("items[2].answerId", Is.is(307)))
                .andExpect(jsonPath("items[2].commentType", Is.is("ANSWER")))

                .andExpect(jsonPath("items[3].id", Is.is(426)))
                .andExpect(jsonPath("items[3].comment", Is.is("any answer text")))
                .andExpect(jsonPath("items[3].persistDate", Is.is("2000-02-06T00:00:00")))
                .andExpect(jsonPath("items[3].questionId").isEmpty())
                .andExpect(jsonPath("items[3].answerId", Is.is(306)))
                .andExpect(jsonPath("items[3].commentType", Is.is("ANSWER")))

                .andExpect(jsonPath("items[4].id", Is.is(425)))
                .andExpect(jsonPath("items[4].comment", Is.is("any answer text")))
                .andExpect(jsonPath("items[4].persistDate", Is.is("2000-02-05T00:00:00")))
                .andExpect(jsonPath("items[4].questionId").isEmpty())
                .andExpect(jsonPath("items[4].answerId", Is.is(305)))
                .andExpect(jsonPath("items[4].commentType", Is.is("ANSWER")))

                .andExpect(jsonPath("items[5].id", Is.is(424)))
                .andExpect(jsonPath("items[5].comment", Is.is("any answer text")))
                .andExpect(jsonPath("items[5].persistDate", Is.is("2000-02-04T00:00:00")))
                .andExpect(jsonPath("items[5].questionId").isEmpty())
                .andExpect(jsonPath("items[5].answerId", Is.is(304)))
                .andExpect(jsonPath("items[5].commentType", Is.is("ANSWER")))

                .andExpect(jsonPath("items[6].id", Is.is(423)))
                .andExpect(jsonPath("items[6].comment", Is.is("any answer text")))
                .andExpect(jsonPath("items[6].persistDate", Is.is("2000-02-03T00:00:00")))
                .andExpect(jsonPath("items[6].questionId").isEmpty())
                .andExpect(jsonPath("items[6].answerId", Is.is(303)))
                .andExpect(jsonPath("items[6].commentType", Is.is("ANSWER")))

                .andExpect(jsonPath("items[7].id", Is.is(422)))
                .andExpect(jsonPath("items[7].comment", Is.is("any answer text")))
                .andExpect(jsonPath("items[7].persistDate", Is.is("2000-02-02T00:00:00")))
                .andExpect(jsonPath("items[7].questionId").isEmpty())
                .andExpect(jsonPath("items[7].answerId", Is.is(302)))
                .andExpect(jsonPath("items[7].commentType", Is.is("ANSWER")))

                .andExpect(jsonPath("items[8].id", Is.is(421)))
                .andExpect(jsonPath("items[8].comment", Is.is("any answer text")))
                .andExpect(jsonPath("items[8].persistDate", Is.is("2000-02-01T00:00:00")))
                .andExpect(jsonPath("items[8].questionId").isEmpty())
                .andExpect(jsonPath("items[8].answerId", Is.is(301)))
                .andExpect(jsonPath("items[8].commentType", Is.is("ANSWER")))

                .andExpect(jsonPath("items[9].id", Is.is(414)))
                .andExpect(jsonPath("items[9].comment", Is.is("any question text")))
                .andExpect(jsonPath("items[9].persistDate", Is.is("2000-01-14T00:00:00")))
                .andExpect(jsonPath("items[9].questionId", Is.is(214)))
                .andExpect(jsonPath("items[9].answerId").isEmpty())
                .andExpect(jsonPath("items[9].commentType", Is.is("QUESTION")))

                .andExpect(jsonPath("items.length()", Is.is(10)));
    }

    @Test
    @ApiOperation(value = "Проверка на возвращение последней страницы (PageDto<UserProfileCommentDto>), " +
            "с пагинацией и сортировкой по дате")
    @Sql(scripts = "/script/TestProfileUserResourceController/TestGetUserProfileCommentDto/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/TestGetUserProfileCommentDto/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getUserProfileCommentDtoLastPage() throws Exception {

        String USER_TOKEN = getToken("test101@mail.ru", "123");

        this.mvc.perform(get("/api/user/profile/comment")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .param("currentPage", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("currentPageNumber", Is.is(3)))
                .andExpect(jsonPath("totalPageCount", Is.is(3)))
                .andExpect(jsonPath("totalResultCount", Is.is(23)))

                .andExpect(jsonPath("items[0].id", Is.is(403)))
                .andExpect(jsonPath("items[0].comment", Is.is("any question text")))
                .andExpect(jsonPath("items[0].persistDate", Is.is("2000-01-03T00:00:00")))
                .andExpect(jsonPath("items[0].questionId", Is.is(203)))
                .andExpect(jsonPath("items[0].answerId").isEmpty())
                .andExpect(jsonPath("items[0].commentType", Is.is("QUESTION")))

                .andExpect(jsonPath("items[1].id", Is.is(402)))
                .andExpect(jsonPath("items[1].comment", Is.is("any question text")))
                .andExpect(jsonPath("items[1].persistDate", Is.is("2000-01-02T00:00:00")))
                .andExpect(jsonPath("items[1].questionId", Is.is(202)))
                .andExpect(jsonPath("items[1].answerId").isEmpty())
                .andExpect(jsonPath("items[1].commentType", Is.is("QUESTION")))

                .andExpect(jsonPath("items[2].id", Is.is(401)))
                .andExpect(jsonPath("items[2].comment", Is.is("any question text")))
                .andExpect(jsonPath("items[2].persistDate", Is.is("2000-01-01T00:00:00")))
                .andExpect(jsonPath("items[2].questionId", Is.is(201)))
                .andExpect(jsonPath("items[2].answerId").isEmpty())
                .andExpect(jsonPath("items[2].commentType", Is.is("QUESTION")))

                .andExpect(jsonPath("items.length()", Is.is(3)));
    }

    @Test
    @ApiOperation(value = "Проверка на возвращение пустого PageDto<UserProfileCommentDto>")
    @Sql(scripts = "/script/TestProfileUserResourceController/TestGetUserProfileCommentDtoEmpty/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestProfileUserResourceController/TestGetUserProfileCommentDtoEmpty/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getUserProfileCommentDtoEmpty() throws Exception {

        String USER_TOKEN = getToken("test101@mail.ru", "123");

        this.mvc.perform(get("/api/user/profile/comment")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("totalPageCount", Is.is(0)))
                .andExpect(jsonPath("totalResultCount", Is.is(0)))
                .andExpect(jsonPath("items").isEmpty());
    }
}