package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.chat.ChatDto;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.models.dto.chat.SingleChatDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.ChatDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.MessageDtoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import java.util.HashMap;
import java.util.Map;

@Api(value = "Контроллер для работы с чатами пользователя")
@RestController
@RequestMapping("/api/user/chat")
public class ChatResourceController {

    private final MessageDtoService messageDtoService;
    private final ChatDtoService chatDtoService;
    private final GroupChatService groupChatService;

    public ChatResourceController(ChatDtoService chatDtoService, GroupChatService groupChatService,
                                  MessageDtoService messageDtoService) {
        this.chatDtoService = chatDtoService;
        this.groupChatService = groupChatService;
        this.messageDtoService = messageDtoService;
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешный запрос!"),
            @ApiResponse(code = 201, message = "Пользователь добавлен!"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен"),
            @ApiResponse(code = 404, message = "Пользователь или чат не найдены!")})
    public ResponseEntity<HttpStatus> addUserToChat(@PathVariable("id") Long groupChatId, @RequestBody Long userId) {
        groupChatService.addUserByIdToGroupChat(groupChatId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{chatId}/single/message")
    @ApiOperation(value = "Получение всех MessageDto из SingleChat")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "MessageDto не найдены")
    })
    public ResponseEntity<?> getMessageDtoByChatId(@RequestParam(defaultValue = "1") Integer page,
                                                   @RequestParam(required = false, defaultValue = "10") Integer items,
                                                   @PathVariable("chatId") Long chatId) {

        Map<String, Object> props = new HashMap<>();
        props.put("chatId", chatId);
        PaginationData data = new PaginationData(page, items,
                MessageDtoDaoByPersistDateImpl.class.getSimpleName(), props);


        return new ResponseEntity<>(messageDtoService.getPageDto(data), HttpStatus.OK);
    }
}
