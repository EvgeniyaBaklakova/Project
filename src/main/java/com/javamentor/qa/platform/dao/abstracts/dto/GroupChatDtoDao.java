package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;

import java.util.Optional;

public interface GroupChatDtoDao {
    Optional<GroupChatDto> getById(Long id);
}
