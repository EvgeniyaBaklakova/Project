package com.javamentor.qa.platform.repository;

import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUsersByEmail(String email);
}
