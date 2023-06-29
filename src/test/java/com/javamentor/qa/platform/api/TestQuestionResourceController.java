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

import static org.hamcrest.Matchers.hasSize;
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
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionDtoGettingApi/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionDtoGettingApi/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getAllQuestionDtoWithPagination() throws Exception {
        String token = getToken("test101@mail.ru", "password");

        this.mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/question")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        this.mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/question")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(1)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(4)))
                .andExpect(jsonPath("$.items.length()", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[0].title", Is.is("title2")))
                .andExpect(jsonPath("$.items[0].authorId", Is.is(101)))
                .andExpect(jsonPath("$.items[0].authorReputation", Is.is(4)))
                .andExpect(jsonPath("$.items[0].authorName", Is.is("name2")))
                .andExpect(jsonPath("$.items[0].authorImage", Is.is("http://imagelink2.com")))
                .andExpect(jsonPath("$.items[0].description", Is.is("description2")))
                .andExpect(jsonPath("$.items[0].viewCount", Is.is(2)))
                .andExpect(jsonPath("$.items[0].countAnswer", Is.is(3)))
                .andExpect(jsonPath("$.items[0].countValuable", Is.is(4)))

                .andExpect(jsonPath("$.items[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[1].title", Is.is("title3")))
                .andExpect(jsonPath("$.items[1].authorId", Is.is(102)))
                .andExpect(jsonPath("$.items[1].authorReputation", Is.is(5)))
                .andExpect(jsonPath("$.items[1].authorName", Is.is("name3")))
                .andExpect(jsonPath("$.items[1].authorImage", Is.is("http://imagelink3.com")))
                .andExpect(jsonPath("$.items[1].description", Is.is("description3")))
                .andExpect(jsonPath("$.items[1].viewCount", Is.is(2)))
                .andExpect(jsonPath("$.items[1].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$.items[1].countValuable", Is.is(0)))

                .andExpect(jsonPath("$.items[2].id", Is.is(103)))
                .andExpect(jsonPath("$.items[2].title", Is.is("title4")))
                .andExpect(jsonPath("$.items[2].authorId", Is.is(103)))
                .andExpect(jsonPath("$.items[2].authorReputation", Is.is(7)))
                .andExpect(jsonPath("$.items[2].authorName", Is.is("name4")))
                .andExpect(jsonPath("$.items[2].authorImage", Is.is("http://imagelink4.com")))
                .andExpect(jsonPath("$.items[2].description", Is.is("description4")))
                .andExpect(jsonPath("$.items[2].viewCount", Is.is(1)))
                .andExpect(jsonPath("$.items[2].countAnswer", Is.is(0)))
                .andExpect(jsonPath("$.items[2].countValuable", Is.is(0)))

                .andExpect(jsonPath("$.items[3].id", Is.is(104)))
                .andExpect(jsonPath("$.items[3].title", Is.is("title5")))
                .andExpect(jsonPath("$.items[3].authorId", Is.is(104)))
                .andExpect(jsonPath("$.items[3].authorReputation", Is.is(6)))
                .andExpect(jsonPath("$.items[3].authorName", Is.is("name5")))
                .andExpect(jsonPath("$.items[3].authorImage", Is.is("http://imagelink5.com")))
                .andExpect(jsonPath("$.items[3].description", Is.is("description5")))
                .andExpect(jsonPath("$.items[3].viewCount", Is.is(0)))
                .andExpect(jsonPath("$.items[3].countAnswer", Is.is(0)))
                .andExpect(jsonPath("$.items[3].countValuable", Is.is(0)));

        this.mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/question?page=2")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.currentPageNumber", Is.is(2)));

        this.mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/question?items=1")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.items.length()", Is.is(1)));

        this.mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/question?trackedTag=name10")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(1)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(1)))
                .andExpect(jsonPath("$.items.length()", Is.is(1)))

                .andExpect(jsonPath("$.items[0].id", Is.is(104)))
                .andExpect(jsonPath("$.items[0].title", Is.is("title5")))
                .andExpect(jsonPath("$.items[0].authorId", Is.is(104)))
                .andExpect(jsonPath("$.items[0].authorReputation", Is.is(6)))
                .andExpect(jsonPath("$.items[0].authorName", Is.is("name5")))
                .andExpect(jsonPath("$.items[0].authorImage", Is.is("http://imagelink5.com")))
                .andExpect(jsonPath("$.items[0].description", Is.is("description5")))
                .andExpect(jsonPath("$.items[0].viewCount", Is.is(0)))
                .andExpect(jsonPath("$.items[0].countAnswer", Is.is(0)))
                .andExpect(jsonPath("$.items[0].countValuable", Is.is(0)));

        this.mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/question?ignoredTag=name3")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(1)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(1)))
                .andExpect(jsonPath("$.items.length()", Is.is(1)))

                .andExpect(jsonPath("$.items[0].id", Is.is(104)))
                .andExpect(jsonPath("$.items[0].title", Is.is("title5")))
                .andExpect(jsonPath("$.items[0].authorId", Is.is(104)))
                .andExpect(jsonPath("$.items[0].authorReputation", Is.is(6)))
                .andExpect(jsonPath("$.items[0].authorName", Is.is("name5")))
                .andExpect(jsonPath("$.items[0].authorImage", Is.is("http://imagelink5.com")))
                .andExpect(jsonPath("$.items[0].description", Is.is("description5")))
                .andExpect(jsonPath("$.items[0].viewCount", Is.is(0)))
                .andExpect(jsonPath("$.items[0].countAnswer", Is.is(0)))
                .andExpect(jsonPath("$.items[0].countValuable", Is.is(0)));

        this.mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/question?trackedTag=name6&ignoredTag=name4")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(1)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(1)))
                .andExpect(jsonPath("$.items.length()", Is.is(1)))

                .andExpect(jsonPath("$.items[0].id", Is.is(102)))
                .andExpect(jsonPath("$.items[0].title", Is.is("title3")))
                .andExpect(jsonPath("$.items[0].authorId", Is.is(102)))
                .andExpect(jsonPath("$.items[0].authorReputation", Is.is(5)))
                .andExpect(jsonPath("$.items[0].authorName", Is.is("name3")))
                .andExpect(jsonPath("$.items[0].authorImage", Is.is("http://imagelink3.com")))
                .andExpect(jsonPath("$.items[0].description", Is.is("description3")))
                .andExpect(jsonPath("$.items[0].viewCount", Is.is(2)))
                .andExpect(jsonPath("$.items[0].countAnswer", Is.is(1)))
                .andExpect(jsonPath("$.items[0].countValuable", Is.is(0)));

    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestGetAllQuestionsWithoutAnswers/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestGetAllQuestionsWithoutAnswers/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getAllEmptyQuestionsTagsDoNotMatter() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/question/noAnswer")
                        .header(AUTHORIZATION, getToken("myemail@mail.ru", "test"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber").value(1))
                .andExpect(jsonPath("$.totalPageCount").value(1))
                .andExpect(jsonPath("$.totalResultCount").value(4))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items.length()").value(4))

                .andExpect(jsonPath("$.items[0].id").value(206))
                .andExpect(jsonPath("$.items[1].id").value(207))
                .andExpect(jsonPath("$.items[2].id").value(208))
                .andExpect(jsonPath("$.items[3].id").value(209))

                .andExpect(jsonPath("$.items[0].title").value("question6"))
                .andExpect(jsonPath("$.items[1].title").value("question7"))
                .andExpect(jsonPath("$.items[2].title").value("question8"))
                .andExpect(jsonPath("$.items[3].title").value("question9"))

                .andExpect(jsonPath("$.items[0].authorId").value(101))
                .andExpect(jsonPath("$.items[1].authorId").value(101))
                .andExpect(jsonPath("$.items[2].authorId").value(101))
                .andExpect(jsonPath("$.items[3].authorId").value(101))

                .andExpect(jsonPath("$.items[0].authorReputation").value(100500))
                .andExpect(jsonPath("$.items[1].authorReputation").value(100500))
                .andExpect(jsonPath("$.items[2].authorReputation").value(100500))
                .andExpect(jsonPath("$.items[3].authorReputation").value(100500))

                .andExpect(jsonPath("$.items[0].authorName").value("name"))
                .andExpect(jsonPath("$.items[1].authorName").value("name"))
                .andExpect(jsonPath("$.items[2].authorName").value("name"))
                .andExpect(jsonPath("$.items[3].authorName").value("name"))

                .andExpect(jsonPath("$.items[0].authorImage").value("link"))
                .andExpect(jsonPath("$.items[1].authorImage").value("link"))
                .andExpect(jsonPath("$.items[2].authorImage").value("link"))
                .andExpect(jsonPath("$.items[3].authorImage").value("link"))

                .andExpect(jsonPath("$.items[0].description").value("question6"))
                .andExpect(jsonPath("$.items[1].description").value("question7"))
                .andExpect(jsonPath("$.items[2].description").value("question8"))
                .andExpect(jsonPath("$.items[3].description").value("question9"))

                .andExpect(jsonPath("$.items[0].viewCount").value(0))
                .andExpect(jsonPath("$.items[1].viewCount").value(1))
                .andExpect(jsonPath("$.items[2].viewCount").value(0))
                .andExpect(jsonPath("$.items[3].viewCount").value(1))

                .andExpect(jsonPath("$.items[0].countAnswer").value(0))
                .andExpect(jsonPath("$.items[1].countAnswer").value(0))
                .andExpect(jsonPath("$.items[2].countAnswer").value(0))
                .andExpect(jsonPath("$.items[3].countAnswer").value(0))

                .andExpect(jsonPath("$.items[0].countValuable").value(0))
                .andExpect(jsonPath("$.items[1].countValuable").value(1))
                .andExpect(jsonPath("$.items[2].countValuable").value(0))
                .andExpect(jsonPath("$.items[3].countValuable").value(1))

                .andExpect(jsonPath("$.items[0].listTagDto").isArray())
                .andExpect(jsonPath("$.items[0].listTagDto.length()").value(2))
                .andExpect(jsonPath("$.items[0].listTagDto[0].id").value(403))
                .andExpect(jsonPath("$.items[0].listTagDto[1].id").value(404))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name").value("gradle"))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name").value("hibernate"))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description").value("gradle"))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description").value("hibernate"))

                .andExpect(jsonPath("$.items[1].listTagDto.length()").value(1))
                .andExpect(jsonPath("$.items[1].listTagDto[0].id").value(404))
                .andExpect(jsonPath("$.items[1].listTagDto[0].name").value("hibernate"))
                .andExpect(jsonPath("$.items[1].listTagDto[0].description").value("hibernate"))

                .andExpect(jsonPath("$.items[2].listTagDto.length()").value(2))
                .andExpect(jsonPath("$.items[2].listTagDto[0].id").value(404))
                .andExpect(jsonPath("$.items[2].listTagDto[1].id").value(405))
                .andExpect(jsonPath("$.items[2].listTagDto[0].name").value("hibernate"))
                .andExpect(jsonPath("$.items[2].listTagDto[1].name").value("mockito"))
                .andExpect(jsonPath("$.items[2].listTagDto[0].description").value("hibernate"))
                .andExpect(jsonPath("$.items[2].listTagDto[1].description").value("mockito"))

                .andExpect(jsonPath("$.items[3].listTagDto.length()").value(1))
                .andExpect(jsonPath("$.items[3].listTagDto[0].id").value(401))
                .andExpect(jsonPath("$.items[3].listTagDto[0].name").value("spring"))
                .andExpect(jsonPath("$.items[3].listTagDto[0].description").value("spring"))

                .andExpect(jsonPath("$.itemsOnPage").value(4));
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestGetAllQuestionsWithoutAnswers/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestGetAllQuestionsWithoutAnswers/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getAllEmptyQuestionsWithTrackedTags() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/question/noAnswer")
                        .header(AUTHORIZATION, getToken("myemail@mail.ru", "test"))
                        .param("trackedTag", "401", "402", "403")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(1)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(2)))
                .andExpect(jsonPath("$.items", hasSize(2)))
                .andExpect(jsonPath("$.items[0].id").value(206))
                .andExpect(jsonPath("$.items[1].id").value(209))

                .andExpect(jsonPath("$.items[0].title").value("question6"))
                .andExpect(jsonPath("$.items[1].title").value("question9"))

                .andExpect(jsonPath("$.items[0].authorId").value(101))
                .andExpect(jsonPath("$.items[1].authorId").value(101))

                .andExpect(jsonPath("$.items[0].authorReputation").value(100500))
                .andExpect(jsonPath("$.items[1].authorReputation").value(100500))

                .andExpect(jsonPath("$.items[0].authorName").value("name"))
                .andExpect(jsonPath("$.items[1].authorName").value("name"))

                .andExpect(jsonPath("$.items[0].authorImage").value("link"))
                .andExpect(jsonPath("$.items[1].authorImage").value("link"))

                .andExpect(jsonPath("$.items[0].description").value("question6"))
                .andExpect(jsonPath("$.items[1].description").value("question9"))

                .andExpect(jsonPath("$.items[0].viewCount").value(0))
                .andExpect(jsonPath("$.items[1].viewCount").value(1))

                .andExpect(jsonPath("$.items[0].countAnswer").value(0))
                .andExpect(jsonPath("$.items[1].countAnswer").value(0))

                .andExpect(jsonPath("$.items[0].countValuable").value(0))
                .andExpect(jsonPath("$.items[1].countValuable").value(1))

                .andExpect(jsonPath("$.items[0].listTagDto").isArray())
                .andExpect(jsonPath("$.items[0].listTagDto.length()").value(2))
                .andExpect(jsonPath("$.items[0].listTagDto[0].id").value(403))
                .andExpect(jsonPath("$.items[0].listTagDto[1].id").value(404))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name").value("gradle"))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name").value("hibernate"))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description").value("gradle"))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description").value("hibernate"))

                .andExpect(jsonPath("$.items[1].listTagDto").isArray())
                .andExpect(jsonPath("$.items[1].listTagDto.length()").value(1))
                .andExpect(jsonPath("$.items[1].listTagDto[0].id").value(401))
                .andExpect(jsonPath("$.items[1].listTagDto[0].name").value("spring"))
                .andExpect(jsonPath("$.items[1].listTagDto[0].description").value("spring"))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(2)));
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestGetAllQuestionsWithoutAnswers/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestGetAllQuestionsWithoutAnswers/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getAllEmptyQuestionsWithoutIgnoredTags() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/question/noAnswer")
                        .header(AUTHORIZATION, getToken("myemail@mail.ru", "test"))
                        .param("ignoredTag", "401", "402", "403")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(1)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(2)))
                .andExpect(jsonPath("$.items", hasSize(2)))

                .andExpect(jsonPath("$.items[0].id").value(207))
                .andExpect(jsonPath("$.items[1].id").value(208))

                .andExpect(jsonPath("$.items[0].title").value("question7"))
                .andExpect(jsonPath("$.items[1].title").value("question8"))

                .andExpect(jsonPath("$.items[0].authorId").value(101))
                .andExpect(jsonPath("$.items[1].authorId").value(101))

                .andExpect(jsonPath("$.items[0].authorReputation").value(100500))
                .andExpect(jsonPath("$.items[1].authorReputation").value(100500))

                .andExpect(jsonPath("$.items[0].authorName").value("name"))
                .andExpect(jsonPath("$.items[1].authorName").value("name"))

                .andExpect(jsonPath("$.items[0].authorImage").value("link"))
                .andExpect(jsonPath("$.items[1].authorImage").value("link"))

                .andExpect(jsonPath("$.items[0].description").value("question7"))
                .andExpect(jsonPath("$.items[1].description").value("question8"))

                .andExpect(jsonPath("$.items[0].viewCount").value(1))
                .andExpect(jsonPath("$.items[1].viewCount").value(0))

                .andExpect(jsonPath("$.items[0].countAnswer").value(0))
                .andExpect(jsonPath("$.items[1].countAnswer").value(0))

                .andExpect(jsonPath("$.items[0].countValuable").value(1))
                .andExpect(jsonPath("$.items[1].countValuable").value(0))

                .andExpect(jsonPath("$.items[0].listTagDto[0].id").value(404))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name").value("hibernate"))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description").value("hibernate"))

                .andExpect(jsonPath("$.items[1].listTagDto[0].id").value(404))
                .andExpect(jsonPath("$.items[1].listTagDto[0].name").value("hibernate"))
                .andExpect(jsonPath("$.items[1].listTagDto[0].description").value("hibernate"))

                .andExpect(jsonPath("$.items[1].listTagDto[1].id").value(405))
                .andExpect(jsonPath("$.items[1].listTagDto[1].name").value("mockito"))
                .andExpect(jsonPath("$.items[1].listTagDto[1].description").value("mockito"))

                .andExpect(jsonPath("$.itemsOnPage", Is.is(2)));
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestGetAllQuestionsWithoutAnswers/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestGetAllQuestionsWithoutAnswers/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getAllEmptyQuestionsWithTrackedTagsAndWithoutIgnoredTags() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/question/noAnswer")
                        .header(AUTHORIZATION, getToken("myemail@mail.ru", "test"))
                        .param("trackedTag", "401", "402", "404", "405")
                        .param("ignoredTag", "402", "403")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(1)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(3)))
                .andExpect(jsonPath("$.items", hasSize(3)))

                .andExpect(jsonPath("$.items[0].id").value(207))
                .andExpect(jsonPath("$.items[0].title").value("question7"))
                .andExpect(jsonPath("$.items[0].authorId").value(101))
                .andExpect(jsonPath("$.items[0].authorReputation").value(100500))
                .andExpect(jsonPath("$.items[0].authorName").value("name"))
                .andExpect(jsonPath("$.items[0].authorImage").value("link"))
                .andExpect(jsonPath("$.items[0].description").value("question7"))
                .andExpect(jsonPath("$.items[0].viewCount").value(1))
                .andExpect(jsonPath("$.items[0].countAnswer").value(0))
                .andExpect(jsonPath("$.items[0].countValuable").value(1))
                .andExpect(jsonPath("$.items[0].listTagDto[0].id").value(404))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name").value("hibernate"))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description").value("hibernate"))

                .andExpect(jsonPath("$.items[1].id").value(208))
                .andExpect(jsonPath("$.items[1].title").value("question8"))
                .andExpect(jsonPath("$.items[1].authorId").value(101))
                .andExpect(jsonPath("$.items[1].authorReputation").value(100500))
                .andExpect(jsonPath("$.items[1].authorName").value("name"))
                .andExpect(jsonPath("$.items[1].authorImage").value("link"))
                .andExpect(jsonPath("$.items[1].description").value("question8"))
                .andExpect(jsonPath("$.items[1].viewCount").value(0))
                .andExpect(jsonPath("$.items[1].countAnswer").value(0))
                .andExpect(jsonPath("$.items[1].countValuable").value(0))
                .andExpect(jsonPath("$.items[1].listTagDto[0].id").value(404))
                .andExpect(jsonPath("$.items[1].listTagDto[0].name").value("hibernate"))
                .andExpect(jsonPath("$.items[1].listTagDto[0].description").value("hibernate"))
                .andExpect(jsonPath("$.items[1].listTagDto[1].id").value(405))
                .andExpect(jsonPath("$.items[1].listTagDto[1].name").value("mockito"))
                .andExpect(jsonPath("$.items[1].listTagDto[1].description").value("mockito"))

                .andExpect(jsonPath("$.items[2].id").value(209))
                .andExpect(jsonPath("$.items[2].title").value("question9"))
                .andExpect(jsonPath("$.items[2].authorId").value(101))
                .andExpect(jsonPath("$.items[2].authorReputation").value(100500))
                .andExpect(jsonPath("$.items[2].authorName").value("name"))
                .andExpect(jsonPath("$.items[2].authorImage").value("link"))
                .andExpect(jsonPath("$.items[2].description").value("question9"))
                .andExpect(jsonPath("$.items[2].viewCount").value(1))
                .andExpect(jsonPath("$.items[2].countAnswer").value(0))
                .andExpect(jsonPath("$.items[2].countValuable").value(1))
                .andExpect(jsonPath("$.items[2].listTagDto[0].id").value(401))
                .andExpect(jsonPath("$.items[2].listTagDto[0].name").value("spring"))
                .andExpect(jsonPath("$.items[2].listTagDto[0].description").value("spring"))

                .andExpect(jsonPath("$.itemsOnPage", Is.is(3)));
    }
}