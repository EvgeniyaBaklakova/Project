package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService extends ReadWriteService<User, Long>{
    @Transactional
    void changePassword(Long id, String newPassword);
}
