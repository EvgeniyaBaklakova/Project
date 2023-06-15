package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractTestApi;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.text.IsEmptyString.emptyString;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestUserResourceController extends AbstractTestApi {


    @Test
    @Sql(scripts = "/script/TestUserResourceController/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getUserDto() throws Exception {


        String USER_TOKEN = getToken("test101@mail.ru", "123");


        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/101")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(101)))
                .andExpect(jsonPath("$.email", Is.is("test101@mail.ru")))
                .andExpect(jsonPath("$.fullName", Is.is("Alex Vasiliev")))
                .andExpect(jsonPath("$.imageLink", Is.is("No link")))
                .andExpect(jsonPath("$.city", Is.is("Saint-Petersburg")))
                .andExpect(jsonPath("$.reputation", Is.is(0)));
    }

    @Test
    @Sql(scripts = "/script/TestUserResourceController/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getUserDtoNotFound() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/999"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with this id not found"));
    }


    @Test
    @Sql(value = {"/script/TestUserResourceController/getAllUserDtoSortDTO/Before.sql"}, executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = {"/script/TestUserResourceController/getAllUserDtoSortDTO/After.sql"}, executionPhase = AFTER_TEST_METHOD)
    public void getAllUserDtoSortDTOTest() throws Exception {


        String USER_TOKEN = getToken("user101@mail.ru", "123");


        mvc.perform(get("/api/user/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, USER_TOKEN)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(10)))
                .andExpect(jsonPath("$.items.length()", Is.is(10)))

                .andExpect(jsonPath("$.items[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[0].email", Is.is("user101@mail.ru")))
                .andExpect(jsonPath("$.items[0].fullName", Is.is("User 101")))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(1000)))


                .andExpect(jsonPath("$.items[9].id", Is.is(110)))
                .andExpect(jsonPath("$.items[9].email", Is.is("user110@mail.ru")))
                .andExpect(jsonPath("$.items[9].fullName", Is.is("User 110")))
                .andExpect(jsonPath("$.items[9].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[9].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[9].reputation", Is.is(100)));


        mvc.perform(get("/api/user/vote")
                        .header(AUTHORIZATION, USER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("currentPageNumber", "2")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPageNumber").value("1"))
                .andExpect(jsonPath("$.totalPageCount").value("1"))
                .andExpect(jsonPath("$.totalResultCount").value("10"))
                .andExpect(jsonPath("$.items[0].id").value(101))
                .andExpect(jsonPath("$.items[0].email").value("user101@mail.ru"))
                .andExpect(jsonPath("$.items[1].id").value(102))
                .andExpect(jsonPath("$.items[1].email").value("user102@mail.ru"))
                .andExpect(jsonPath("$.itemsOnPage").value("10"));

        mvc.perform(get("/api/user/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, USER_TOKEN)
                        .param("currentPageNumber", "2")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPageNumber").value("1"))
                .andExpect(jsonPath("$.totalPageCount").value("1"))
                .andExpect(jsonPath("$.totalResultCount").value("10"))
                .andExpect(jsonPath("$.itemsOnPage").value("10"));

        mvc.perform(get("/api/user/vote")
                        .header(AUTHORIZATION, USER_TOKEN)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test  // Проверка передачи всех верных данных
    @Sql(value = {"/script/TestUserResourceController/getUsersByVoteAsc_shouldFindAllData_whenExists/Before.sql"}, executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = {"/script/TestUserResourceController/getUsersByVoteAsc_shouldFindAllData_whenExists/After.sql"}, executionPhase = AFTER_TEST_METHOD)
    void getUsersByVoteAsc_shouldFindAllData_whenExists() throws Exception {

        String USER_TOKEN = getToken("user101@mail.ru", "123");

        mvc.perform(get("/api/user/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, USER_TOKEN)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(10)))
                .andExpect(jsonPath("$.items.length()", Is.is(10)))

                .andExpect(jsonPath("$.items[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[0].email", Is.is("user101@mail.ru")))
                .andExpect(jsonPath("$.items[0].fullName", Is.is("User 101")))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(1000)))

                .andExpect(jsonPath("$.items[0].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 1")))

                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 2")))

                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LTGDJP3")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 3")))

                .andExpect(jsonPath("$.items[9].id", Is.is(110)))
                .andExpect(jsonPath("$.items[9].email", Is.is("user110@mail.ru")))
                .andExpect(jsonPath("$.items[9].fullName", Is.is("User 110")))
                .andExpect(jsonPath("$.items[9].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[9].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[9].reputation", Is.is(100)))

                .andExpect(jsonPath("$.items[9].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[9].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[9].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.items[9].listTagDto[0].description", Is.is("Description of tag 1")))

                .andExpect(jsonPath("$.items[9].listTagDto[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[9].listTagDto[1].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items[9].listTagDto[1].description", Is.is("Description of tag 2")))

                .andExpect(jsonPath("$.items[9].listTagDto[2].id", Is.is(103)))
                .andExpect(jsonPath("$.items[9].listTagDto[2].name", Is.is("LTGDJP3")))
                .andExpect(jsonPath("$.items[9].listTagDto[2].description", Is.is("Description of tag 3")));
    }

    @Test
    @Sql(scripts = "/script/TestUserResourceController/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void getCountAnswers() throws Exception {

        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/profile/question/week").
                        header("Authorization", "Bearer " + getToken("test100@mail.ru", "password")))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(2)));
    }

    @Test
    @Sql(scripts = "/script/TestUserResourceController/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void getCountAnswersZero() throws Exception {

        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/profile/question/week").
                        header("Authorization", "Bearer " + getToken("test101@mail.ru", "password")))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Is.is(0)));
    }


    @Test
    @Sql(scripts = "/script/TestUserResourceController/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void getAllQuestion() throws Exception {

        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/profile/questions/").
                        header("Authorization", "Bearer " + getToken("test101@mail.ru", "password")))
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
    @Sql(scripts = "/script/TestUserResourceController/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void getAllQuestionNoQuestion() throws Exception {

        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/profile/questions/").
                        header("Authorization", "Bearer " + getToken("test100@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.empty()));


    }

    @Test
    @Sql(scripts = "/script/TestUserResourceController/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void getAllDeleteQuestion() throws Exception {

        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/profile/delete/questions").
                        header("Authorization", "Bearer " + getToken("test103@mail.ru", "password")))
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
    @Sql(scripts = "/script/TestUserResourceController/Before1.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/After1.sql", executionPhase = AFTER_TEST_METHOD)
    public void getAllDeleteQuestionNoQuestion() throws Exception {

        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/profile/delete/questions").
                        header("Authorization", "Bearer " + getToken("test100@mail.ru", "password")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.empty()));


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

    @Test
    @Sql(scripts = "/script/TestUserResourceController/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void changePasswordIsOk() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/edit/pass")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"newPass\": \"test\"}")
                        .header("Authorization", "Bearer " + getToken("test101@mail.ru", "123")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("success")));
    }


    @Test
    @Sql(scripts = "/script/TestUserResourceController/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void changePasswordIsForbidden() throws Exception {
    this.mvc.perform(MockMvcRequestBuilders.post("/api/user/edit/pass")
            .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"newPass\": \"test\"}")
                        .header("Authorization", "Bearer " + getToken("test103@mail.ru", "123")))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    @Test
    @Sql(scripts = "/script/TestUserResourceController/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void changePasswordIs4xxClientError() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/edit/pass")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"newPass\": \"test\"}")
                        .header("Authorization", "Bearer " + getToken("test105@mail.ru", "123")))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Sql(scripts = "/script/TestUserResourceController/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void changePasswordIsEmptyBody() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/edit/pass")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")
                        .header("Authorization", "Bearer " + getToken("test101@mail.ru", "123")))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(emptyString()));
    }
}
