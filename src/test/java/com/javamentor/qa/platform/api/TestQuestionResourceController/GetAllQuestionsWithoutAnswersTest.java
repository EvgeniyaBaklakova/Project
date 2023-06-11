package com.javamentor.qa.platform.api.TestQuestionResourceController;

import com.javamentor.qa.platform.AbstractTestApi;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class GetAllQuestionsWithoutAnswersTest extends AbstractTestApi {

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/GetAllQuestionsWithoutAnswersTest/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/GetAllQuestionsWithoutAnswersTest/After.sql",
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
    @Sql(scripts = "/script/TestQuestionResourceController/GetAllQuestionsWithoutAnswersTest/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/GetAllQuestionsWithoutAnswersTest/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getAllEmptyQuestionsWithTrackedTags() throws Exception {
        String USER_TOKEN = getToken("myemail@mail.ru", "test");

        String jsonBody = "{\"trackedTagList\":[" +
                "{\"id\":401,\"title\":\"spring\"}," +
                "{\"id\":402,\"title\":\"maven\"}," +
                "{\"id\":403,\"title\":\"gradle\"}" +
                "]," +
                "\"ignoredTagList\": []}";

        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/question/noAnswer")
                        .header(AUTHORIZATION, "Bearer " + USER_TOKEN)
                        .content(jsonBody)
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
                .andExpect(jsonPath("$.items[*].countValuable", containsInAnyOrder(0, 0)))
                .andExpect(jsonPath("$.items[*].listTagDto[*].id", containsInAnyOrder(401, 403, 404))) // here too
                .andExpect(jsonPath("$.items[*].listTagDto[*].name", containsInAnyOrder("spring", "gradle", "hibernate"))) // and here
                .andExpect(jsonPath("$.items[*].listTagDto[*].description", containsInAnyOrder("spring", "gradle", "hibernate"))) // and here
                .andExpect(jsonPath("$.itemsOnPage", Is.is(2)));
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/GetAllQuestionsWithoutAnswersTest/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/GetAllQuestionsWithoutAnswersTest/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getAllEmptyQuestionsWithoutIgnoredTags() throws Exception {
        String USER_TOKEN = getToken("myemail@mail.ru", "test");

        String jsonBody = "{\"trackedTagList\":[], " +
                "\"ignoredTagList\":[" +
                "{\"id\":401,\"title\":\"spring\"}," +
                "{\"id\":402,\"title\":\"maven\"}," +
                "{\"id\":403,\"title\":\"gradle\"}" +
                "]}";

        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/question/noAnswer")
                        .header(AUTHORIZATION, "Bearer " + USER_TOKEN)
                        .content(jsonBody)
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
                .andExpect(jsonPath("$.items[*].countValuable", containsInAnyOrder(1, 1)))
                .andExpect(jsonPath("$.items[*].listTagDto[*].id", containsInAnyOrder(404, 404, 405))) // here too
                .andExpect(jsonPath("$.items[*].listTagDto[*].name", containsInAnyOrder("hibernate", "hibernate", "mockito"))) // and here
                .andExpect(jsonPath("$.items[*].listTagDto[*].description", containsInAnyOrder("hibernate", "hibernate", "mockito"))) // and here
                .andExpect(jsonPath("$.itemsOnPage", Is.is(2)));
    }

    @Test
    @Sql(scripts = "/script/TestQuestionResourceController/GetAllQuestionsWithoutAnswersTest/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestQuestionResourceController/GetAllQuestionsWithoutAnswersTest/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getAllEmptyQuestionsWithTrackedTagsAndWithoutIgnoredTags() throws Exception {
        String USER_TOKEN = getToken("myemail@mail.ru", "test");

        String jsonBody = "{\"trackedTagList\":[" +
                "{\"id\":401,\"title\":\"spring\"}," +
                "{\"id\":402,\"title\":\"maven\"}," +
                "{\"id\":404,\"title\":\"hibernate\"}," +
                "{\"id\":405,\"title\":\"mockito\"}" +
                "]," +
                "\"ignoredTagList\":[" +
                "{\"id\":402,\"title\":\"maven\"}," +
                "{\"id\":403,\"title\":\"gradle\"}" +
                "]}";

        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/question/noAnswer")
                        .header(AUTHORIZATION, "Bearer " + USER_TOKEN)
                        .content(jsonBody)
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
                .andExpect(jsonPath("$.items[*].listTagDto[*].id", containsInAnyOrder(401, 404, 404, 405))) // here too
                .andExpect(jsonPath("$.items[*].listTagDto[*].name", containsInAnyOrder("spring", "hibernate", "hibernate", "mockito"))) // and here
                .andExpect(jsonPath("$.items[*].listTagDto[*].description", containsInAnyOrder("spring","hibernate", "hibernate", "mockito"))) // and here
                .andExpect(jsonPath("$.itemsOnPage", Is.is(3)));
    }

    public String getToken(String email, String password) {
        String token;
        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        try {
            String response = (this.mvc.perform(MockMvcRequestBuilders
                            .post("/api/auth/token").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(map)))
                    .andReturn().getResponse().getContentAsString());
            token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");
            return token;
        } catch (Exception ignored) {
        }
        return "";
    }
}