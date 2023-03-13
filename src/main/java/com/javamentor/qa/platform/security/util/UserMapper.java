package com.javamentor.qa.platform.security.util;

import com.javamentor.qa.platform.models.entity.user.User;

public class UserMapper {

    public static User userToPrincipal(User user) {

        UserPrincipal userPrincipal = new UserPrincipal();


        userPrincipal.setUsername(user.getUsername());
        userPrincipal.setPassword(user.getPassword());

        return userPrincipal;
    }
}