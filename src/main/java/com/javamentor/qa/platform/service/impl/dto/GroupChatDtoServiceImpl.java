package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.GroupChatDtoDao;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.service.abstracts.dto.GroupChatDtoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupChatDtoServiceImpl implements GroupChatDtoService {
    private final GroupChatDtoDao groupChatDtoDao;

    public GroupChatDtoServiceImpl(GroupChatDtoDao groupChatDtoDao) {
        this.groupChatDtoDao = groupChatDtoDao;
    }

    @Override
    public List<GroupChatDto> getAllGroupChatDtoByUserId(Long id) {
        return groupChatDtoDao.getByUserId(id);
    }
}
