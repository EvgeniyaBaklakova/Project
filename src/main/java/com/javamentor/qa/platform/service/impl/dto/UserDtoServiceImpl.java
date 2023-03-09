package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.impl.dto.UserDtoDaoImpl;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.webapp.controllers.util.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDtoServiceImpl implements UserDtoService<UserDto, Long> {

    private final UserDtoDaoImpl userDtoDaoImpl;

    public UserDtoServiceImpl(UserDtoDaoImpl userDtoDaoImpl) {
        this.userDtoDaoImpl = userDtoDaoImpl;
    }

    @Override
    public UserDto getById(Long id) {
        return userDtoDaoImpl.getById(id).orElseThrow(UserNotFoundException::new);
    }
}
