package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractTestApi;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class TestUserResourceController extends AbstractTestApi {

    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;

    public TestUserResourceController(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }


    @Test
    @Sql(scripts = "/script/TestUserResourceController/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getUserDto() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/101"))
                .andDo(print())
                .andExpect(status().isOk())
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
        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/999"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with this id not found"));
    }
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldFindAllData_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldFindAllData_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUsersByVoteAsc_shouldFindAllData_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/vote")
                .contentType(MediaType.APPLICATION_JSON)
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
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldSortedByVoteAsc_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldSortedByVoteAsc_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUsersByVoteAsc_shouldSortedByVoteAsc_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/vote")
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(10)))
                .andExpect(jsonPath("$.items.length()", Is.is(10)))

                .andExpect(jsonPath("$.items[0].id", Is.is(109)))
                .andExpect(jsonPath("$.items[1].id", Is.is(110)))
                .andExpect(jsonPath("$.items[2].id", Is.is(102)))
                .andExpect(jsonPath("$.items[3].id", Is.is(103)))
                .andExpect(jsonPath("$.items[4].id", Is.is(104)))
                .andExpect(jsonPath("$.items[5].id", Is.is(105)))
                .andExpect(jsonPath("$.items[6].id", Is.is(106)))
                .andExpect(jsonPath("$.items[7].id", Is.is(107)))
                .andExpect(jsonPath("$.items[8].id", Is.is(108)))
                .andExpect(jsonPath("$.items[9].id", Is.is(101)));
    }

    // Проверка filter nickname.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldFilterNickname_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldFilterNickname_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUsersByVoteAsc_shouldFilterNickname_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/vote")
                        .param("filter", "user_108")
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(1)))
                .andExpect(jsonPath("$.items.length()", Is.is(1)))

                .andExpect(jsonPath("$.items[0].id", Is.is(108)))
                .andExpect(jsonPath("$.items[0].email", Is.is("user108@mail.ru")))
                .andExpect(jsonPath("$.items[0].fullName", Is.is("User 108")))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(300)))

                .andExpect(jsonPath("$.items[0].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(104)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU4")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 4")))

                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(105)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj5")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 5")))

                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(106)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LTGDJP6")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 6")));
    }

    // Проверка filter email.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldFilterEmail_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldFilterEmail_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUsersByVoteAsc_shouldFilterEmail_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/vote")
                        .param("filter", "user110@mail.ru")
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(1)))
                .andExpect(jsonPath("$.items.length()", Is.is(1)))

                .andExpect(jsonPath("$.items[0].id", Is.is(110)))
                .andExpect(jsonPath("$.items[0].email", Is.is("user110@mail.ru")))
                .andExpect(jsonPath("$.items[0].fullName", Is.is("User 110")))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(100)))

                .andExpect(jsonPath("$.items[0].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 1")))

                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 2")))

                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LTGDJP3")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 3")));
    }

    // Проверка filter fullName.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldFilterFullName_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldFilterFullName_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUsersByVoteAsc_shouldFilterFullName_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/vote")
                        .param("filter", "User 103")
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(1)))
                .andExpect(jsonPath("$.items.length()", Is.is(1)))

                .andExpect(jsonPath("$.items[0].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].email", Is.is("user103@mail.ru")))
                .andExpect(jsonPath("$.items[0].fullName", Is.is("User 103")))
                .andExpect(jsonPath("$.items[0].imageLink", Is.is("/images/noUserAvatar.png")))
                .andExpect(jsonPath("$.items[0].city", Is.is("Moscow")))
                .andExpect(jsonPath("$.items[0].reputation", Is.is(8000)))

                .andExpect(jsonPath("$.items[0].listTagDto.length()", Is.is(3)))

                .andExpect(jsonPath("$.items[0].listTagDto[0].id", Is.is(101)))
                .andExpect(jsonPath("$.items[0].listTagDto[0].name", Is.is("vfOxMU1")))
                .andExpect(jsonPath("$.items[0].listTagDto[0].description", Is.is("Description of tag 1")))

                .andExpect(jsonPath("$.items[0].listTagDto[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[0].listTagDto[1].name", Is.is("iThKcj2")))
                .andExpect(jsonPath("$.items[0].listTagDto[1].description", Is.is("Description of tag 2")))

                .andExpect(jsonPath("$.items[0].listTagDto[2].id", Is.is(103)))
                .andExpect(jsonPath("$.items[0].listTagDto[2].name", Is.is("LTGDJP3")))
                .andExpect(jsonPath("$.items[0].listTagDto[2].description", Is.is("Description of tag 3")));
    }

    // Проверка сортировки по сумме голосов с filter (nickname) несколько значений.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldPaginationFilterNickname_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldPaginationFilterNickname_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUsersByVoteAsc_shouldPaginationFilterNickname_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/vote")
                        .param("filter", "admin_")
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(4)))
                .andExpect(jsonPath("$.items.length()", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(103)))
                .andExpect(jsonPath("$.items[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[2].id", Is.is(105)))
                .andExpect(jsonPath("$.items[3].id", Is.is(104)));
    }

    // Проверка сортировки по сумме голосов с filter (email) несколько значений.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldPaginationFilterEmail_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldPaginationFilterEmail_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUsersByVoteAsc_shouldPaginationFilterEmail_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/vote")
                        .param("filter", "adminmail")
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(4)))
                .andExpect(jsonPath("$.items.length()", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(103)))
                .andExpect(jsonPath("$.items[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[2].id", Is.is(105)))
                .andExpect(jsonPath("$.items[3].id", Is.is(104)));
    }

    // Проверка сортировки по сумме голосов с filter (fullName) несколько значений.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldPaginationFilterFullName_whenExists/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldPaginationFilterFullName_whenExists/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUsersByVoteAsc_shouldPaginationFilterFullName_whenExists() throws Exception {

        mockMvc.perform(get("/api/user/vote")
                        .param("filter", "Admin ")
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResultCount", Is.is(4)))
                .andExpect(jsonPath("$.items.length()", Is.is(4)))

                .andExpect(jsonPath("$.items[0].id", Is.is(103)))
                .andExpect(jsonPath("$.items[1].id", Is.is(102)))
                .andExpect(jsonPath("$.items[2].id", Is.is(105)))
                .andExpect(jsonPath("$.items[3].id", Is.is(104)));
    }

    // Пользователь передает не существующее значение в filter.
    @Test
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldPaginationEmpty_whenFilterEmpty/Before.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/getUsersByVoteAsc_shouldPaginationEmpty_whenFilterEmpty/After.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getUsersByVoteAsc_shouldPaginationEmpty_whenFilterEmpty() throws Exception {

        mockMvc.perform(get("/api/user/vote")
                        .param("filter", "Иван Иванов")
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.totalResultCount", Is.is(0)))
                .andExpect(jsonPath("$.items.length()", Is.is(0)));
    }

}
