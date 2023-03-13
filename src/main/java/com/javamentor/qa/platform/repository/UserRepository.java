package com.javamentor.qa.platform.repository;

import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository  {

    public Optional<User> getUserByLogin(String email);
}