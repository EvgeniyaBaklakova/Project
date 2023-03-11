package com.javamentor.qa.platform.repository;


import com.javamentor.qa.platform.models.entity.user.User;

public interface UserRepository {

    User getUserByLogin(String login);

}
