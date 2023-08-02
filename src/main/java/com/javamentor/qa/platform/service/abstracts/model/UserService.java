package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.dto.user.UserEditPasswordDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserService extends ReadWriteService<User, Long>{
    @Transactional
    void changePassword(Long id, UserEditPasswordDto userEditPasswordDto);

    Optional<User> getByEmail(String email);
}
