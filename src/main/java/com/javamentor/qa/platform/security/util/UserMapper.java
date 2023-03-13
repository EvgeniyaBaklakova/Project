package com.javamentor.qa.platform.security.util;

import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.stream.Collectors;

public class UserMapper extends CustomUserDetails {

    public static User userToPrincipal(User user) {

        UserPrincipal userPrincipal = new UserPrincipal();

        userPrincipal.setUsername(user.getUsername());
        userPrincipal.setPassword(user.getPassword());

        return userPrincipal;
    }
}