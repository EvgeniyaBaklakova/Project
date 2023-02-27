package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.impl.dto.UserDtoDao;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import com.javamentor.qa.platform.service.abstracts.dto.ReadOnlyDtoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDtoService implements ReadOnlyDtoService<UserDto, Long> {

    private final UserDtoDao userDtoDao;

    public UserDtoService(UserDtoDao userDtoDao) {
        this.userDtoDao = userDtoDao;
    }

    @Override
    public Optional<UserDto> getById(Long id) {
        return userDtoDao.getById(id);
    }
}
