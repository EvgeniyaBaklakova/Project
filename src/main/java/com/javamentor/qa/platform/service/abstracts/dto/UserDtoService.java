package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserProfileDto;
import com.javamentor.qa.platform.models.dto.user.UserDto;

import java.util.List;

public interface UserDtoService extends PageDtoService<UserDto> {
    UserDto getById(long id);
    List<UserProfileDto> getUserProfiles(Long id);
}
