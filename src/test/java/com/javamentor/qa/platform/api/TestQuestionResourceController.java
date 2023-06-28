package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractTestApi;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class TestQuestionResourceController extends AbstractTestApi {
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestViewAtFirstTime/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestViewAtFirstTime/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void viewAtFirstTime() throws Exception {
        Assertions.assertEquals(0, getViewsCount(101L));
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/101/view")
                        .header(AUTHORIZATION, getToken("email1@mail.com","123")))
                .andDo(print())
                .andExpect(status().isOk());
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/101/view")
                        .header(AUTHORIZATION, getToken("email2@mail.com","123")))
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertEquals(2, getViewsCount(101L));
    }
    private int getViewsCount(Long id) {
        BigInteger a = (BigInteger) em.createNativeQuery("SELECT COUNT(*) FROM question_viewed WHERE question_id =:id").setParameter("id", id).getSingleResult();
        return a.intValue();
    }
    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestWasAlreadyViewed/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestViewAtFirstTime/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void wasAlreadyViewed() throws Exception {
        Assertions.assertEquals(0, getViewsCount(101L));
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/101/view")
                        .header(AUTHORIZATION, getToken("email1@mail.com","test")))
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertEquals(getViewsCount(101L), 1);
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/101/view")
                        .header(AUTHORIZATION, getToken("email1@mail.com","test")))
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertEquals(1, getViewsCount(101L));
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionNotFound/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionNotFound/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void questionNotFound() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/2/view")
                .header(AUTHORIZATION, getToken("email1@mail.com","test")))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionAddingApiTest/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionAddingApiTest/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void addQuestionAllOkTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\" : \"email1@mail.com\", \"password\" : \"password\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");

        this.mvc.perform(MockMvcRequestBuilders
                        .post("/api/user/question")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"title\", \"description\": \"description\",\"tags\": [{\"name\": \"tag\"}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", Is.is("title")))
                .andExpect(jsonPath("$.authorId", Is.is(1)))
                .andExpect(jsonPath("$.authorName", Is.is("nick")))
                .andExpect(jsonPath("$.authorImage", Is.is("No link")))
                .andExpect(jsonPath("$.description", Is.is("description")))
                .andExpect(jsonPath("$.viewCount", Is.is(0)))
                .andExpect(jsonPath("$.countAnswer", Is.is(0)))
                .andExpect(jsonPath("$.countValuable", Is.is(0)))
                .andExpect(jsonPath("$.listTagDto[0].name", Is.is("tag")));
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionAddingApiTest/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionAddingApiTest/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void addQuestionEmptyOrNullTitleTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\" : \"email1@mail.com\", \"password\" : \"password\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");

        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": null, \"description\": \"description\",\"tags\": [{\"name\": \"tag\"}]}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionAddingApiTest/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionAddingApiTest/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void addQuestionEmptyOrNullDescriptionTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\" : \"email1@mail.com\", \"password\" : \"password\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");

        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"title\", \"description\": null,\"tags\": [{\"name\": \"tag\"}]}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionAddingApiTest/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionAddingApiTest/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void addQuestionNoTagsTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\" : \"email1@mail.com\", \"password\" : \"password\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");

        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"title\", \"description\": \"description\",\"tags\": []}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionAddingApiTest/Before2.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionAddingApiTest/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void addQuestionTagNotExistTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\" : \"email1@mail.com\", \"password\" : \"password\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");

        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"title\", \"description\": \"description\",\"tags\": [{\"name\": \"tag 1\"}]}"))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionAddingApiTest/Before2.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionAddingApiTest/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void addQuestionTagExistsTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\" : \"email1@mail.com\", \"password\" : \"password\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");

        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"title\", \"description\": \"description\",\"tags\": [{\"name\": \"tag\"}]}"))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionAddingApiTest/Before2.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionAddingApiTest/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void addQuestionTagExistsJoiningTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\" : \"email1@mail.com\", \"password\" : \"password\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");

        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"title\", \"description\": \"description\",\"tags\": [{\"name\": \"tag\"}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.listTagDto[0].name", Is.is("tag")));
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestCommentAddingApi/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestCommentAddingApi/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void addCommentAllOkTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\" : \"email1@mail.com\", \"password\" : \"password\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");

        this.mvc.perform(MockMvcRequestBuilders
                        .post("/api/user/question/1/comment")
                        .header(HttpHeaders.AUTHORIZATION,"Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"test message\""))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestCommentAddingApi/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestCommentAddingApi/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void addCommentNullTextTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\" : \"email1@mail.com\", \"password\" : \"password\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");

        this.mvc.perform(MockMvcRequestBuilders
                        .post("/api/user/question/1/comment")
                        .header(HttpHeaders.AUTHORIZATION,token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content((byte[]) null))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestCommentAddingApi/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestCommentAddingApi/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void addCommentNoQuestionTest() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\" : \"email1@mail.com\", \"password\" : \"password\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");

        this.mvc.perform(MockMvcRequestBuilders
                        .post("/api/user/question/1100/comment")
                        .header(HttpHeaders.AUTHORIZATION,token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"test message\""))
                .andExpect(status().is4xxClientError());
    }



    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionDtoGetById/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionDtoGetById/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void questionGetById() throws Exception {

        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/question/100")
                        .header(AUTHORIZATION,getToken("test100@mail.ru","123")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(100)))
                .andExpect(jsonPath("$.title", Is.is("title1")))
                .andExpect(jsonPath("$.authorId", Is.is(100)))
                .andExpect(jsonPath("$.authorReputation", Is.is(6)))
                .andExpect(jsonPath("$.authorName", Is.is("name1")))
                .andExpect(jsonPath("$.authorImage", Is.is("http://imagelink1.com")))
                .andExpect(jsonPath("$.description", Is.is("description1")))
                .andExpect(jsonPath("$.title", Is.is("title1")))
                .andExpect(jsonPath("$.viewCount", Is.is(2)))
                .andExpect(jsonPath("$.countAnswer", Is.is(3)))
                .andExpect(jsonPath("$.countValuable", Is.is(2)))
                .andExpect(jsonPath("$.persistDateTime", Is.is("2023-04-23T13:01:11.245126")))
                .andExpect(jsonPath("$.lastUpdateDateTime", Is.is("2023-04-23T13:01:11.245126")))
                .andExpect(jsonPath("$.listTagDto[0].id", Is.is(100)))
                .andExpect(jsonPath("$.listTagDto[0].name", Is.is("name1")))
                .andExpect(jsonPath("$.listTagDto[0].description", Is.is("description1")))
                .andExpect(jsonPath("$.listTagDto[1].id", Is.is(101)))
                .andExpect(jsonPath("$.listTagDto[1].name", Is.is("name2")))
                .andExpect(jsonPath("$.listTagDto[1].description", Is.is("description2")));

        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/question/104")
                        .header(AUTHORIZATION, getToken("test100@mail.ru","123")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(104)))
                .andExpect(jsonPath("$.title", Is.is("title5")))
                .andExpect(jsonPath("$.authorId", Is.is(104)))
                .andExpect(jsonPath("$.authorReputation", Is.is(0)))
                .andExpect(jsonPath("$.authorName", Is.is("name5")))
                .andExpect(jsonPath("$.authorImage", IsNull.nullValue()))
                .andExpect(jsonPath("$.description", Is.is("description5")))
                .andExpect(jsonPath("$.title", Is.is("title5")))
                .andExpect(jsonPath("$.viewCount", Is.is(0)))
                .andExpect(jsonPath("$.countAnswer", Is.is(0)))
                .andExpect(jsonPath("$.countValuable", Is.is(0)))
                .andExpect(jsonPath("$.persistDateTime", Is.is("2023-04-23T13:01:11.245126")))
                .andExpect(jsonPath("$.lastUpdateDateTime", Is.is("2023-04-23T13:01:11.245126")));
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionDtoGetById/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionDtoGetById/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void noQuestionGetById() throws Exception {

        this.mvc.perform(get("/api/user/question/{id}", 111)
                        .header(AUTHORIZATION, getToken("test100@mail.ru","123") ))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestAddQuestionToBookmarks/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestAddQuestionToBookmarks/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void addQuestionToBookmarks() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/102/bookmark")
                        .header("Authorization",getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is("Вопрос успешно добавлен в закладки")));
    }




























































































































































































































    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestVoteQuestion/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestVoteQuestion/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void upVoteForQuestion() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/104/upvote")      // 1st vote for question
                        .header("Authorization", getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(1)));

        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/100/upvote")      // vote already exists to Up
                .header("Authorization", getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("Пользователь уже проголосовал за: UP_VOTE")));

        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/101/upvote")       // vote for self question to Up
                        .header("Authorization", getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(1)));

        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/103/upvote")       // re-vote from Down to Up
                        .header("Authorization", getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(1)));


        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/105/upvote")       // Not found in question-database
                        .header("Authorization", getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("Такого вопроса не существует")));
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestVoteQuestion/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestVoteQuestion/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void downVoteForQuestion() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/104/downVote")      // 1st vote for question
                        .header("Authorization", getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(1)));

        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/103/downVote")      // vote already exists to Down
                        .header("Authorization", getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("Пользователь уже проголосовал за: DOWN_VOTE")));

        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/101/downVote")       // vote for self question to Down
                        .header("Authorization", getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(1)));

        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/100/downVote")       // re-vote from Up to Down
                        .header("Authorization", getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(3)));


        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/105/downVote")       // Not found in question-database
                        .header("Authorization", getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", Is.is("Такого вопроса не существует")));
    }
}