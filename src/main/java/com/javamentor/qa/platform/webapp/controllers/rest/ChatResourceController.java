package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.chat.ChatDto;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.models.dto.chat.SingleChatDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.ChatDtoService;
import com.javamentor.qa.platform.service.impl.model.GroupChatServiceImpl;
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
    private final ChatDtoService chatDtoService;
    private final GroupChatServiceImpl groupChatService;

    public ChatResourceController(ChatDtoService chatDtoService, GroupChatServiceImpl groupChatService) {
        this.chatDtoService = chatDtoService;
        this.groupChatService = groupChatService;
    }

    @GetMapping
    @ApiOperation(value = "Поиск чатов по имени")
    public ResponseEntity<List<ChatDto>> getChatDtoByChatName(@RequestParam String chatName,
                                                              @AuthenticationPrincipal User user) {
        List<ChatDto> result = chatDtoService.getChatDtoByChatName(chatName, user.getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/single")
    @ApiOperation(value = "Получение всех личных чатов")
    public ResponseEntity<List<SingleChatDto>> getSingleChatDto(@AuthenticationPrincipal User user) {
        List<SingleChatDto> result = chatDtoService.getSingleChatDtoByUserId(user.getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/group")
    @ApiOperation(value = "Получение всех групповых чатов")
    public ResponseEntity<List<GroupChatDto>> getGroupChatDto(@AuthenticationPrincipal User user) {
        List<GroupChatDto> result = chatDtoService.getGroupChatDtoByUserId(user.getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/group/{id}/join")
    @ApiOperation(value = "Добавление пользователя по id в групповой чат")
    public ResponseEntity<HttpStatus> addUserToChat(@PathVariable("id") Long groupChatId, @RequestBody Long userId) {
        groupChatService.addUserByIdToGroupChat(groupChatId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
