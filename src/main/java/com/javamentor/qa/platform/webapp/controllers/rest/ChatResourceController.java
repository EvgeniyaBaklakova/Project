package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.dao.abstracts.model.GroupChatDao;
import com.javamentor.qa.platform.models.dto.chat.ChatDto;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.GroupChatDtoService;
import com.javamentor.qa.platform.service.abstracts.model.GroupChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Контроллер для работы с чатами пользователя")
@RestController
@RequestMapping("/api/user/chat")
public class ChatResourceController {
    private final GroupChatDtoService groupChatDtoService;
    private final GroupChatService groupChatService;

    public ChatResourceController(GroupChatDtoService groupChatDtoService, GroupChatDao groupChatDao, GroupChatService groupChatService) {
        this.groupChatDtoService = groupChatDtoService;
        this.groupChatService = groupChatService;
    }

    @GetMapping
    @ApiOperation(value = "Поиск чатов по имени")
    public ResponseEntity<List<ChatDto>> getChatDtoByChatName(@RequestParam String chatName,
                                                              @AuthenticationPrincipal User user) {
        List<ChatDto> result = groupChatDtoService.getChatDtoByChatName(chatName, user.getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/group")
    @ApiOperation(value = "Получение всех групповых чатов")
    public ResponseEntity<List<GroupChatDto>> getGroupChatDto(@AuthenticationPrincipal User user) {
        List<GroupChatDto> result = groupChatDtoService.getGroupChatDtoByUserId(user.getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/group/{id}/join")
    @ApiOperation(value = "Добавление пользователя по id в групповой чат")
    public void addUserToChat(@PathVariable("id") Long groupChatId, @RequestBody Long userId) {
        groupChatService.addUserByIdToGroupChat(groupChatId, userId);
    }
}
