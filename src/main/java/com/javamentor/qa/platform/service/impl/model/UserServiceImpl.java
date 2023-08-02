package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.exception.UserNotFoundException;
import com.javamentor.qa.platform.models.dto.user.UserEditPasswordDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl extends ReadWriteServiceImpl<User, Long> implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        super(userDao);
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void changePassword(Long id, UserEditPasswordDto userEditPasswordDto) {
        User user = getById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id +  " not found"));
        user.setPassword(passwordEncoder.encode(userEditPasswordDto.getNewPassword()));
        update(user);
    }


    @Override
    public Optional<User> getByEmail(String email) {
        return userDao.getUserByEmail(email);
    }
}
