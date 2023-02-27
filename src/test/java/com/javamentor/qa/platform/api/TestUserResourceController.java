package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractTestApi;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TestUserResourceController extends AbstractTestApi {

    @Test
    @Sql(scripts = "/script/TestUserResourceController/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getUserDto() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/100"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":100,\"email\":\"test100@mail.ru\",\"fullName\":\"Alex Vasiliev\",\"imageLink\",\"No link\",\"city\":\"Saint-Petersburg\",\"reputation\":666"))
                .andDo(print());
    }

    @Test
    @Sql(scripts = "/script/TestUserResourceController/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestUserResourceController/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getUserDtoNotFound() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/33"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{\"statusCode\":404,\"message\":\"User with id 33 not found\""))
                .andDo(print());
    }
}
