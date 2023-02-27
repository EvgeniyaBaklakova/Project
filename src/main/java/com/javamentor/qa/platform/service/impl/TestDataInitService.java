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

    @Autowired
    public TestDataInitService(UserService userService, RoleService roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    public void init(){
        Role role1 = new Role("Role_Test_1");
        Role role2 = new Role("Role_Test_2");
        roleService.persistAll(role1, role2);
        userService.persist(new User(null, "email", "password", "name", null, true, false, "city", "link_site", "link_github", "link_vk", "about", "image_link", null, "nick", role1));
        userService.persist(new User(null, "email", "password", "name", null, true, false, "city", "link_site", "link_github", "link_vk", "about", "image_link", null, "nick", role1));
        userService.persist(new User(null, "email", "password", "name", null, true, false, "city", "link_site", "link_github", "link_vk", "about", "image_link", null, "nick", role2));
        userService.persist(new User(null, "email", "password", "name", null, true, false, "city", "link_site", "link_github", "link_vk", "about", "image_link", null, "nick", role1));
        userService.persist(new User(null, "email", "password", "name", null, true, false, "city", "link_site", "link_github", "link_vk", "about", "image_link", null, "nick", role2));
    }
}
