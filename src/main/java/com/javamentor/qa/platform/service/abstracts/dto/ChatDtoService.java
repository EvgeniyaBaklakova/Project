package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.chat.ChatDto;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.models.dto.chat.SingleChatDto;

import java.util.List;

public interface ChatDtoService {
    List<ChatDto> getChatDtoByChatName(String search, Long id);

    List<SingleChatDto> getSingleChatDtoByUserId(Long id);

    List<GroupChatDto> getGroupChatDtoByUserId(Long id);
}
