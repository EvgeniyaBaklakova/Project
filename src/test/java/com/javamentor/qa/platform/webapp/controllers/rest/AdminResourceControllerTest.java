package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.AbstractTestApi;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

class AdminResourceControllerTest extends AbstractTestApi {

    @Autowired
    protected UserService userService;
    @Autowired
    protected TestRestTemplate testRestTemplate;

    private final Long USER_ID = 102L;

    @DisplayName("set isEnable as false")
    @Sql(value = "/script/AuthController/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "/script/AuthController/After.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void testBlockUserController() {

        String url = "/api/admin/block?userId=" + USER_ID;
        testRestTemplate.postForEntity(url, null, Void.class);

        // Проверяем, что пользователь заблокирован
        User blockedUser = userService.getById(USER_ID).orElse(null);
        Assertions.assertNotNull(blockedUser);
        Assertions.assertFalse(blockedUser.getIsEnabled());
    }
}
