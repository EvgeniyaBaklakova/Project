package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.impl.dto.UserDtoDao;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import com.javamentor.qa.platform.webapp.controllers.util.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDtoService {

    private final UserDtoDao userDtoDao;

    public UserDtoService(UserDtoDao userDtoDao) {
        this.userDtoDao = userDtoDao;
    }

    public UserDto getById(long id) {
        return userDtoDao.getById(id).orElseThrow(UserNotFoundException::new);
    }
}
