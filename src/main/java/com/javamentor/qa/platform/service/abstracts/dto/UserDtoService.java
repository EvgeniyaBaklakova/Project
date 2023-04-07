package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import javassist.NotFoundException;

import java.util.HashMap;

public interface UserDtoService extends PageDtoService<UserDto> {
    UserDto getById(long id);
}
