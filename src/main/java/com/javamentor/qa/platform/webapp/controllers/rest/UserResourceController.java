package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.dao.impl.pagination.UserPageDtoDaoByPersistDateImpl;
import com.javamentor.qa.platform.dao.impl.pagination.UserPageDtoDaoByVoteImpl;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/user")

public class UserResourceController {

    private final UserDtoService userDtoService;
    private final UserService userService;
    private final AnswerService answerService;
    private final QuestionDtoService questionDtoService;

    public UserResourceController(UserDtoService userDtoService, UserService userService, AnswerService answerService, QuestionDtoService questionDtoService) {
        this.userDtoService = userDtoService;
        this.userService = userService;
        this.answerService = answerService;
        this.questionDtoService = questionDtoService;
    }

    @GetMapping("/new")
    @ApiOperation(value = "Получение всех UserDTO с пагинацией отсортированные по дате регистрации")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = PageDto.class),
            @ApiResponse(code = 400, message = "UserDTO не найдены")
    })
    public ResponseEntity<PageDto<UserDto>> getUsersByPersistDate(@RequestParam Integer page,
                                                                  @RequestParam(required = false, defaultValue = "10") Integer itemsOnPage) {
        PaginationData data = new PaginationData(page, itemsOnPage, UserPageDtoDaoByPersistDateImpl.class.getSimpleName());
        return new ResponseEntity<>(userDtoService.getPageDto(data), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserDto(@PathVariable("userId") long id) {
        return new ResponseEntity<>(userDtoService.getById(id), HttpStatus.OK);

    }

    @GetMapping("/vote")
    @ApiOperation(value = "Получение всех UserDTO с пагинацией отсортированные по голосам")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = PageDto.class),
            @ApiResponse(code = 400, message = "UserDTO не найдены")
    })

    public ResponseEntity<PageDto<UserDto>> getUsersByVoteAnswer(@RequestParam(defaultValue = "1") Integer itemsOnPage,
                                                                 @RequestParam(required = false, defaultValue = "15") Integer items,
                                                                 @RequestParam(required = false) String filter) {
        PaginationData data = new PaginationData(itemsOnPage, items,
                UserPageDtoDaoByVoteImpl.class.getSimpleName(), filter);
        return new ResponseEntity<>(userDtoService.getPageDto(data), HttpStatus.OK);
    }

    @GetMapping(value = "/profile/question/week")
    @ApiOperation(value = "Возвращает общее количество ответов за неделю которые написал аутентифицированный пользователь")
    public ResponseEntity<Long> getCountAnswers(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(answerService.countAnswerOfWeek(user.getId()));
    }


    @ApiOperation(value = "Возвращает список всех вопросов аутентифицированного пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Вопросы успешно получены"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен"),
            @ApiResponse(code = 404, message = "Ресурс, к которому вы пытались обратиться, не найден")
    })
    @GetMapping("/profile/questions")
    public ResponseEntity<List<UserProfileQuestionDto>> getAllQuestion(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(questionDtoService.getUserQuestions(user.getId()), HttpStatus.OK);
    }

    @ApiOperation(value = "Возвращает список всех удаленных вопросов аутентифицированного пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Вопросы успешно получены"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен"),
            @ApiResponse(code = 404, message = "Ресурс, к которому вы пытались обратиться, не найден")
    })
    @GetMapping("/profile/delete/questions")
    public ResponseEntity<List<UserProfileQuestionDto>> getAllDeleteQuestion(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(questionDtoService.getUserDeleteQuestions(user.getId()), HttpStatus.OK);
    }

    @ApiOperation(value = "Осуществляет смену пароля пользователя, переданного в теле запроса в не зашифрованном виде")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пароль успешно изменен"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен"),
            @ApiResponse(code = 404, message = "Ресурс, к которому вы пытались обратиться, не найден")
    })
    @PostMapping("/edit/pass")
    public ResponseEntity<Map<String,String>> changePassword(@RequestBody String newPass) {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
            userService.changePassword(user.getId(), newPass);
        return new ResponseEntity<>(Map.of("status", "success"), HttpStatus.OK);
    }
}
