package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserProfileDto;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.webapp.controllers.util.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserDtoServiceImpl extends PageDtoServiceImpl<UserDto> implements UserDtoService {

    private final UserDtoDao userDtoDao;

    public UserDtoServiceImpl(UserDtoDao userDtoDao, Map<String, PageDtoDao<UserDto>> daoMap) {
        super(daoMap);
        this.userDtoDao = userDtoDao;
    }


    @Override
    public UserDto getById(long id) {
        return userDtoDao.getById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserProfileDto getUserProfile(Long id) {
        return userDtoDao.getUserProfile(id);
    }

    @Override
    public PageDto<UserDto> getPageDto(PaginationData properties) {
        var pageDto = super.getPageDto(properties);
        return pageDto;
    }
}
