package com.javamentor.qa.platform.service.impl;

import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.RoleService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestDataInitService {

    private final UserService userService;

    private final RoleService roleService;

    private final Role ROLE_USER = new Role("ROLE_USER");
    private final Role ROLE_ADMIN = new Role("ROLE_ADMIN");

    @Autowired
    public TestDataInitService(UserService userService, RoleService roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    public void initRoles() {
        roleService.persistAll(ROLE_USER, ROLE_ADMIN);
    }

    public void initUsers() {
        for(int i = 1; i <= 100; i = i + 2) {
            userService.persistAll(new User(null, "email" + i + "@mail.com", "password", "name", null, true, false, "city", "link_site", "link_github", "link_vk", "about", "image_link", null, "nick", ROLE_USER),
                    new User(null, "email" + (i + 1) + "@mail.com", "password", "name", null, true, false, "city", "link_site", "link_github", "link_vk", "about", "image_link", null, "nick", ROLE_ADMIN));
        }
    }

}
