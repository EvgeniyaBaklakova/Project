package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.GroupChatDtoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "Контроллер для работы с чатами пользователя")
@RestController
@RequestMapping("/api/user/chat")
public class ChatResourceController {
    private final GroupChatDtoService groupChatDtoService;

    public ChatResourceController(GroupChatDtoService groupChatDtoService) {
        this.groupChatDtoService = groupChatDtoService;
    }

    @ApiOperation(value = "Получение всех групповых чатов")
    @GetMapping("/group")
    public ResponseEntity<List<GroupChatDto>> getGroupChatDto(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(groupChatDtoService.getAllGroupChatDtoByUserId(user), HttpStatus.OK);
    }
}
