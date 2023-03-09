package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.user.UserDto;

public interface UserDtoService<E, K> {
    E getById(K id);
}
