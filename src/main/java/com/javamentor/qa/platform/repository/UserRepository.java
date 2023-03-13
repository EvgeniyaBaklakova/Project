package com.javamentor.qa.platform.repository;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  {

    public Optional<User> getUserByLogin(String email);
}