package com.javamentor.qa.platform.repository;

import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public interface UserRepository  {

    public Optional<User> getUserByLogin(String email);
}