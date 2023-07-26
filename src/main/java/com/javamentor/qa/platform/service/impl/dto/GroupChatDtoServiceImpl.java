package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.GroupChatDtoDao;
import com.javamentor.qa.platform.models.dto.chat.ChatDto;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.service.abstracts.dto.GroupChatDtoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupChatDtoServiceImpl implements GroupChatDtoService {
    private final GroupChatDtoDao groupChatDtoDao;

    public GroupChatDtoServiceImpl(GroupChatDtoDao groupChatDtoDao) {
        this.groupChatDtoDao = groupChatDtoDao;
    }

    @Override
    public List<GroupChatDto> getGroupChatDtoByUserId(Long id) {
        return groupChatDtoDao.getGroupChatDtoByUserId(id);
    }

    @Override
    public List<ChatDto> getChatDtoByChatName(String search, Long id) {
        List<ChatDto> result = new ArrayList<>();

        result.addAll(groupChatDtoDao.getChatDtoSingleByChatName(search, id));
        result.addAll(groupChatDtoDao.getChatDtoGroupByChatName(search, id));

        return result.stream().sorted(Comparator.comparing(ChatDto::getPersistDateTimeLastMessage)).collect(Collectors.toList());
    }
}
