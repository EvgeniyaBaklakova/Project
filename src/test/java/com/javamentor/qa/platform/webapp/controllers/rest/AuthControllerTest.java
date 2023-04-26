package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.AbstractTestApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthControllerTest extends AbstractTestApi {

    @Autowired
    AuthenticationResourceController controller;

    @Test
    public void test() throws Exception {
        assertThat(controller).isNotNull();
    }
}
