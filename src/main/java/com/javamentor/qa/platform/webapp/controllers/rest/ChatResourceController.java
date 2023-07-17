package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.service.abstracts.dto.GroupChatDtoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Контроллер для работы с чатами пользователя")
@RestController
@RequestMapping("/api/user/chat")
public class ChatResourceController {
    private final GroupChatDtoService groupChatDtoService;

    public ChatResourceController(GroupChatDtoService groupChatDtoService) {
        this.groupChatDtoService = groupChatDtoService;
    }

    @ApiOperation(value = "Получение списка GroupChatDto (групповой чат)")
    @GetMapping("/group")
    public GroupChatDto getGroupChatDto(@RequestParam Long id) {
        return groupChatDtoService.getGroupChatDtoById(id);
    }
}
