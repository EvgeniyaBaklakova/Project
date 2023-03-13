package com.javamentor.qa.platform.security.util;

import com.javamentor.qa.platform.models.entity.user.User;

public class UserMapper extends CustomUserDetails {

    public static User userToPrincipal(org.springframework.security.core.userdetails.User user) {

        UserPrincipal userPrincipal = new UserPrincipal();

        userPrincipal.setUsername(user.getUsername());
        userPrincipal.setPassword(user.getPassword());

        return userPrincipal;
    }
}