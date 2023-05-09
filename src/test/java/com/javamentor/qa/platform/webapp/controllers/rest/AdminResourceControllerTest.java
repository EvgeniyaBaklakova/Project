package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.AbstractTestApi;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


class AdminResourceControllerTest extends AbstractTestApi {

    @Autowired
    private UserService userService;

    private final Long USER_ID = 101L;

    @DisplayName("set isEnable as false")
    @Sql(value = "/script/AuthController/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "/script/AuthController/After.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void testBlockUserController() throws Exception {

        String url = "/api/admin/block?userId=101";

        mvc.perform(post(url));
        User blockedUser = userService.getById(USER_ID).orElse(null);
        userService.update(blockedUser);
        Assertions.assertNotNull(blockedUser);
        Assertions.assertFalse(blockedUser.getIsEnabled());
    }
}
