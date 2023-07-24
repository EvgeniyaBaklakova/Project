package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;

import java.util.List;

public interface GroupChatDtoService {
    List<GroupChatDto> getAllGroupChatDtoByUserId(Long id);
}
