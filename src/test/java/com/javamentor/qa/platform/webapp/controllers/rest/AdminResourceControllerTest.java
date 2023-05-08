package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.AbstractTestApi;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

class AdminResourceControllerTest extends AbstractTestApi {

    @Autowired
    protected UserService userService;

    @DisplayName("set isEnable as false")
    @Sql(value = "/script/AuthController/Before.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "/script/AuthController/After.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void testBlockUserController() {

        Optional<User> blockedUser = userService.getById(101L);
        blockedUser.get().setIsEnabled(false);
        userService.update(blockedUser.get());
        assertFalse(blockedUser.get().getIsEnabled());
    }
}
