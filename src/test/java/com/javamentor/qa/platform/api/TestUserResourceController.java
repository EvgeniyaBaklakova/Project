package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractTestApi;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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


//        mvc.perform(get("/api/user/vote")
//                        .header(AUTHORIZATION, USER_TOKEN)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("currentPageNumber", "2")
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                //.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.currentPageNumber").value("2"))
//                .andExpect(jsonPath("$.totalPageCount").value("2"))
//                .andExpect(jsonPath("$.totalResultCount").value("12"))
//                .andExpect(jsonPath("$.items[0].id").value(103))
//                .andExpect(jsonPath("$.items[0].email").value("user103@mail.ru"))
//                .andExpect(jsonPath("$.items[1].id").value(110))
//                .andExpect(jsonPath("$.items[1].email").value("user110@mail.ru"))
//                .andExpect(jsonPath("$.itemsOnPage").value("10"));
//
//        mvc.perform(get("/api/user/vote")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header(AUTHORIZATION, USER_TOKEN)
//                        .param("currentPageNumber", "3")
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                //.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.currentPageNumber").value("3"))
//                .andExpect(jsonPath("$.totalPageCount").value("2"))
//                .andExpect(jsonPath("$.totalResultCount").value("12"))
//                .andExpect(jsonPath("$.items").isEmpty())
//                .andExpect(jsonPath("$.itemsOnPage").value("10"));
//
//        mvc.perform(get("/api/user/vote")
//                        .header(AUTHORIZATION, USER_TOKEN)
//                )
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(status().reason("Required int parameter 'currentPageNumber' is not present"));
    }


}
