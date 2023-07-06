package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.dto.UserProfileDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserService extends ReadWriteService<User, Long>{
    @Transactional
    void changePassword(Long id, String newPassword);

    Optional<User> getByEmail(String email);

    UserProfileDto getUserProfile(Long userId);
}
