package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.dao.impl.pagination.MessageDtoDaoByPersistDateImpl;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.service.abstracts.dto.MessageDtoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.javamentor.qa.platform.models.dto.chat.ChatDto;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.models.dto.chat.SingleChatDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.ChatDtoService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import java.util.HashMap;
import java.util.Map;




@RestController
@RequestMapping("/api/user/chat")
@Api(value = "Контроллер для работы с с чатами пользователя", tags = "Chat Controller")
public class ChatResourceController {
//todo
    private final MessageDtoService messageDtoService;
    private final ChatDtoService chatDtoService;

    @Autowired
    public ChatResourceController(ChatDtoService chatDtoService, MessageDtoService messageDtoService) {
        this.chatDtoService = chatDtoService;
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

    @GetMapping("{chat_id}/single/message")
    @ApiOperation(value = "Получение всех MessageDto из SingleChat")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "MessageDto не найдены")
    })
    public ResponseEntity<?> getMessageDtoByChatId(@RequestParam(defaultValue = "1") Integer page,
                                                                     @RequestParam(required = false, defaultValue = "10") Integer items,
                                                                     @PathVariable("chat_id") Long chatId) {

        Map<String, Object> props = new HashMap<>();
        props.put("chat_id", chatId);
        PaginationData data = new PaginationData(page, items,
                MessageDtoDaoByPersistDateImpl.class.getSimpleName(), props);


        return new ResponseEntity<>(messageDtoService.getPageDto(data), HttpStatus.OK);


    }


}
