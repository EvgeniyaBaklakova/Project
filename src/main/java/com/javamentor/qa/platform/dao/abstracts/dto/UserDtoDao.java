package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserProfileDto;
import com.javamentor.qa.platform.models.dto.user.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserDtoDao {
    Optional<UserDto> getById(long id);
    List<UserProfileDto> getUserProfiles(Long id);
}
