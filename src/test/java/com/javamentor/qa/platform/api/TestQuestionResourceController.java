package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractTestApi;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsInAnyOrder;
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
    @Sql(scripts = "/script/TestQuestionResourceController/TestViewPage/Before1.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestViewPage/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void viewAtFirstTime() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/1/view"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestViewPage/Before2.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestViewPage/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void wasAlreadyViewed() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/1/view"))
                .andDo(print())
                .andExpect(status().isOk());
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
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
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
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"test message\""))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestViewPage/Before1.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestViewPage/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void questionNotFound() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/2/view"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionDtoGetById/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestQuestionDtoGetById/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void questionGetById() throws Exception {

        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/question/100")
                        .header(AUTHORIZATION,"Bearer " + getToken("test100@mail.ru","123")))
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

        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/question/104").header(AUTHORIZATION,"Bearer " + getToken("test100@mail.ru","123")))
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

        this.mvc.perform(get("/api/user/question/{id}", 111).header(AUTHORIZATION,"Bearer " + getToken("test100@mail.ru","123") ))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestAddQuestionToBookmarks/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestAddQuestionToBookmarks/After.sql", executionPhase = AFTER_TEST_METHOD)
    public void addQuestionToBookmarks() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/question/102/bookmark").header("Authorization","Bearer " + getToken("test101@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is("Вопрос успешно добавлен в закладки")));
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestGetAllQuestionsWithoutAnswers/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestGetAllQuestionsWithoutAnswers/After.sql",
            executionPhase = AFTER_TEST_METHOD)

    public void getAllEmptyQuestionsTagsDoNotMatter() throws Exception {
        String USER_TOKEN = getToken("myemail@mail.ru", "test");

        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/question/noAnswer")
                        .header(AUTHORIZATION, "Bearer " + USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(1)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(4)))
                .andExpect(jsonPath("$.items", hasSize(4)))
                .andExpect(jsonPath("$.items[*].id", containsInAnyOrder(206, 207, 208, 209)))
                .andExpect(jsonPath("$.items[*].title", containsInAnyOrder("question6", "question7", "question8", "question9")))
                .andExpect(jsonPath("$.items[*].authorId", containsInAnyOrder(101, 101, 101, 101)))
                .andExpect(jsonPath("$.items[*].authorReputation", containsInAnyOrder(100500, 100500, 100500, 100500)))
                .andExpect(jsonPath("$.items[*].authorName", containsInAnyOrder("name", "name", "name", "name")))
                .andExpect(jsonPath("$.items[*].authorImage", containsInAnyOrder("link", "link", "link", "link")))
                .andExpect(jsonPath("$.items[*].description", containsInAnyOrder("question6", "question7", "question8", "question9")))
                .andExpect(jsonPath("$.items[*].viewCount", containsInAnyOrder(0, 0, 1, 1)))
                .andExpect(jsonPath("$.items[*].countAnswer", containsInAnyOrder(0, 0, 0, 0)))
                .andExpect(jsonPath("$.items[*].countValuable", containsInAnyOrder(0, 1, 1, 0)))
                .andExpect(jsonPath("$.items[*].listTagDto[*].id", containsInAnyOrder(403, 404, 405, 401, 404, 404)))
                .andExpect(jsonPath("$.items[*].listTagDto[*].name", containsInAnyOrder("spring", "gradle", "hibernate", "hibernate", "hibernate", "mockito")))
                .andExpect(jsonPath("$.items[*].listTagDto[*].description", containsInAnyOrder("spring", "gradle", "hibernate", "hibernate", "hibernate", "mockito")))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(4)));
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestGetAllQuestionsWithoutAnswers/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestGetAllQuestionsWithoutAnswers/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getAllEmptyQuestionsWithTrackedTags() throws Exception {
        String USER_TOKEN = getToken("myemail@mail.ru", "test");

        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/question/noAnswer")
                        .header(AUTHORIZATION, "Bearer " + USER_TOKEN)
                        .param("trackedTag", "401", "402", "403")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(1)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(2)))
                .andExpect(jsonPath("$.items", hasSize(2)))
                .andExpect(jsonPath("$.items[*].id", containsInAnyOrder(206, 209)))
                .andExpect(jsonPath("$.items[*].title", containsInAnyOrder("question6", "question9")))
                .andExpect(jsonPath("$.items[*].authorId", containsInAnyOrder(101, 101)))
                .andExpect(jsonPath("$.items[*].authorReputation", containsInAnyOrder(100500, 100500)))
                .andExpect(jsonPath("$.items[*].authorName", containsInAnyOrder("name", "name")))
                .andExpect(jsonPath("$.items[*].authorImage", containsInAnyOrder("link", "link")))
                .andExpect(jsonPath("$.items[*].description", containsInAnyOrder("question6", "question9")))
                .andExpect(jsonPath("$.items[*].viewCount", containsInAnyOrder(0, 1)))
                .andExpect(jsonPath("$.items[*].countAnswer", containsInAnyOrder(0, 0)))
                .andExpect(jsonPath("$.items[*].countValuable", containsInAnyOrder(0, 1)))
                .andExpect(jsonPath("$.items[*].listTagDto[*].id", containsInAnyOrder(401, 403, 404)))
                .andExpect(jsonPath("$.items[*].listTagDto[*].name", containsInAnyOrder("spring", "gradle", "hibernate")))
                .andExpect(jsonPath("$.items[*].listTagDto[*].description", containsInAnyOrder("spring", "gradle", "hibernate")))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(2)));
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestGetAllQuestionsWithoutAnswers/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestGetAllQuestionsWithoutAnswers/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getAllEmptyQuestionsWithoutIgnoredTags() throws Exception {
        String USER_TOKEN = getToken("myemail@mail.ru", "test");

        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/question/noAnswer")
                        .header(AUTHORIZATION, "Bearer " + USER_TOKEN)
                        .param("ignoredTag", "401", "402", "403")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currentPageNumber", Is.is(1)))
                .andExpect(jsonPath("$.totalPageCount", Is.is(1)))
                .andExpect(jsonPath("$.totalResultCount", Is.is(2)))
                .andExpect(jsonPath("$.items", hasSize(2)))
                .andExpect(jsonPath("$.items[*].id", containsInAnyOrder(207, 208)))
                .andExpect(jsonPath("$.items[*].title", containsInAnyOrder("question7", "question8")))
                .andExpect(jsonPath("$.items[*].authorId", containsInAnyOrder(101, 101)))
                .andExpect(jsonPath("$.items[*].authorReputation", containsInAnyOrder(100500, 100500)))
                .andExpect(jsonPath("$.items[*].authorName", containsInAnyOrder("name", "name")))
                .andExpect(jsonPath("$.items[*].authorImage", containsInAnyOrder("link", "link")))
                .andExpect(jsonPath("$.items[*].description", containsInAnyOrder("question7", "question8")))
                .andExpect(jsonPath("$.items[*].viewCount", containsInAnyOrder(0, 1)))
                .andExpect(jsonPath("$.items[*].countAnswer", containsInAnyOrder(0, 0)))
                .andExpect(jsonPath("$.items[*].countValuable", containsInAnyOrder(0, 1)))
                .andExpect(jsonPath("$.items[*].listTagDto[*].id", containsInAnyOrder(404, 404, 405)))
                .andExpect(jsonPath("$.items[*].listTagDto[*].name", containsInAnyOrder("hibernate", "hibernate", "mockito")))
                .andExpect(jsonPath("$.items[*].listTagDto[*].description", containsInAnyOrder("hibernate", "hibernate", "mockito")))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(2)));
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/TestGetAllQuestionsWithoutAnswers/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/TestGetAllQuestionsWithoutAnswers/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getAllEmptyQuestionsWithTrackedTagsAndWithoutIgnoredTags() throws Exception {
        String USER_TOKEN = getToken("myemail@mail.ru", "test");

        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/question/noAnswer")
                        .header(AUTHORIZATION, "Bearer " + USER_TOKEN)
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
                .andExpect(jsonPath("$.items[*].id", containsInAnyOrder(207, 208, 209)))
                .andExpect(jsonPath("$.items[*].title", containsInAnyOrder("question7", "question8", "question9")))
                .andExpect(jsonPath("$.items[*].authorId", containsInAnyOrder(101, 101, 101)))
                .andExpect(jsonPath("$.items[*].authorReputation", containsInAnyOrder(100500, 100500, 100500)))
                .andExpect(jsonPath("$.items[*].authorName", containsInAnyOrder("name", "name", "name")))
                .andExpect(jsonPath("$.items[*].authorImage", containsInAnyOrder("link", "link", "link")))
                .andExpect(jsonPath("$.items[*].description", containsInAnyOrder("question7", "question8", "question9")))
                .andExpect(jsonPath("$.items[*].viewCount", containsInAnyOrder( 0, 1, 1)))
                .andExpect(jsonPath("$.items[*].countAnswer", containsInAnyOrder(0, 0, 0)))
                .andExpect(jsonPath("$.items[*].countValuable", containsInAnyOrder( 0, 1, 1)))
                .andExpect(jsonPath("$.items[*].listTagDto[*].id", containsInAnyOrder(401, 404, 404, 405)))
                .andExpect(jsonPath("$.items[*].listTagDto[*].name", containsInAnyOrder("spring", "hibernate", "hibernate", "mockito")))
                .andExpect(jsonPath("$.items[*].listTagDto[*].description", containsInAnyOrder("spring","hibernate", "hibernate", "mockito")))
                .andExpect(jsonPath("$.itemsOnPage", Is.is(3)));
    }
}