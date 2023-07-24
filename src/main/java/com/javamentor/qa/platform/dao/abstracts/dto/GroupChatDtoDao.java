package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;

import java.util.List;

public interface GroupChatDtoDao {
    List<GroupChatDto> getByUserId(Long id);
}
