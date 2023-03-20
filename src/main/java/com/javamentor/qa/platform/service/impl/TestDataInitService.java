package com.javamentor.qa.platform.service.impl;

import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.service.abstracts.model.RoleService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.stereotype.Service;

@Service
public class TestDataInitService {
    public TestDataInitService() {

        final UserService userService;

        final RoleService roleService;

        final Role ROLE_USER = new Role("ROLE_USER");
        final Role ROLE_ADMIN = new Role("ROLE_ADMIN");

    }
}
