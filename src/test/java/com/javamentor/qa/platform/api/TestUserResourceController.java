package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractTestApi;
import com.javamentor.qa.platform.models.entity.user.User;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.TypedQuery;
import java.util.List;

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

        this.mvc.perform(get("/api/user/101")
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
                .andExpect(jsonPath("$.reputation", Is.is(20)));
    }

    @Test
    @Sql(scripts = "/script/TestUserResourceController/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getUserDtoNotFound() throws Exception {

        String USER_TOKEN = getToken("test101@mail.ru", "123");

        mvc.perform(get("/api/user/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, USER_TOKEN)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with this id not found"));
    }


    @Test
    @Sql(value = {"/script/TestUserResourceController/TestGetAllUserDtoSortByPersistDate/Before.sql"}, executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = {"/script/TestUserResourceController/TestGetAllUserDtoSortByPersistDate/After.sql"}, executionPhase = AFTER_TEST_METHOD)
    public void getAllUserDtoSortByPerstistDate() throws Exception {


        String USER_TOKEN = getToken("user101@mail.ru", "123");

        mvc.perform(get("/api/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, USER_TOKEN)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

        mvc.perform(get("/api/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, USER_TOKEN)
                        .param("page","4")
                        .param("itemsOnPage", "10")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(21)))
                .andExpect(jsonPath("$.items.length()", Is.is(0)));

        mvc.perform(get("/api/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, USER_TOKEN)
                        .param("page","3")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(21)))
                .andExpect(jsonPath("$.items.length()", Is.is(1)));

        mvc.perform(get("/api/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, USER_TOKEN)
                        .param("page","1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(21)))
                .andExpect(jsonPath("$.items.length()", Is.is(10)));


        String hql = "SELECT u From User u ORDER BY u.persistDateTime";
        TypedQuery<User> query = em.createQuery(hql, User.class);
        List<User> resultList = query.getResultList();

        mvc.perform(get("/api/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, USER_TOKEN)
                        .param("page","1")
                        .param("itemsOnPage", "21")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(21)))
                .andExpect(jsonPath("$.items.length()", Is.is(21)))

                .andExpect(jsonPath("$.items[0].id", Is.is(resultList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.items[4].id", Is.is(resultList.get(4).getId().intValue())))
                .andExpect(jsonPath("$.items[15].id", Is.is(resultList.get(15).getId().intValue())))
                .andExpect(jsonPath("$.items[20].id", Is.is(resultList.get(20).getId().intValue())));
    }

    @Test
    @Sql(value = {"/script/TestUserResourceController/getUsersByReputation/Before.sql"}, executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = {"/script/TestUserResourceController/getUsersByReputation/After.sql"}, executionPhase = AFTER_TEST_METHOD)
    public void getUsersByReputation() throws Exception {


        String USER_TOKEN = getToken("user101@mail.ru", "123");

        mvc.perform(get("/api/user/reputation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, USER_TOKEN)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

        mvc.perform(get("/api/user/reputation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, USER_TOKEN)
                        .param("page", "1")
                        .param("itemsOnPage", "10")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(21)))
                .andExpect(jsonPath("$.items.length()", Is.is(10)))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(100)))
                .andExpect(jsonPath("$.items[9].reputation", Is.is(8000)));
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

    @Test
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

                .andExpect(jsonPath("$.items[9].id", Is.is(110)))
                .andExpect(jsonPath("$.items[9].email", Is.is("user110@mail.ru")))
                .andExpect(jsonPath("$.items[9].fullName", Is.is("User 110")))
                .andExpect(jsonPath("$.items[9].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[9].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[9].reputation", Is.is(100)));

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
                        .header(AUTHORIZATION, getToken("test101@mail.ru", "123")))
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
                        .header(AUTHORIZATION, getToken("test103@mail.ru", "123")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @Sql(scripts = "/script/TestUserResourceController/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void changePasswordIs4xxClientError() throws Exception {

        String USER_TOKEN = getToken("test103@mail.ru", "123");

        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/edit/pass")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"newPass\": \"test\"}")
                        .header(AUTHORIZATION, USER_TOKEN))
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
                        .header(AUTHORIZATION, getToken("test101@mail.ru", "123")))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(emptyString()));
    }

}
