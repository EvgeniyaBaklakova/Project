package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.UserProfileDto;
import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.dto.tag.UserProfileTagDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/profile")
public class ProfileUserResourceController {

    private final UserDtoService userDtoService;
    private final AnswerService answerService;
    private final QuestionDtoService questionDtoService;

    public ProfileUserResourceController(UserDtoService userDtoService
            , AnswerService answerService
            , QuestionDtoService questionDtoService) {
        this.userDtoService = userDtoService;
        this.answerService = answerService;
        this.questionDtoService = questionDtoService;
    }

    @ApiOperation(value = "Возвращает список всех вопросов аутентифицированного пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Вопросы успешно получены"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен"),
            @ApiResponse(code = 404, message = "Ресурс, к которому вы пытались обратиться, не найден")
    })
    @GetMapping("/questions")
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
    @GetMapping("/delete/questions")
    public ResponseEntity<List<UserProfileQuestionDto>> getAllDeleteQuestion(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(questionDtoService.getUserDeleteQuestions(user.getId()), HttpStatus.OK);
    }

    @GetMapping(value = "/question/week")
    @ApiOperation(value = "Возвращает общее количество ответов за неделю которые написал аутентифицированный пользователь")
    public ResponseEntity<Long> getCountAnswers(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(answerService.countAnswerOfWeek(user.getId()));
    }

    @GetMapping
    @ApiOperation(value = "Возвращает информацию о пользователе по User id который был аутентифицирован")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Информация была успешно получена"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен"),
            @ApiResponse(code = 404, message = "Ресурс, к которому вы пытались обратиться, не найден")
    })
    public ResponseEntity<UserProfileDto> getProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userDtoService.getUserProfile(user.getId()));
    }

    @GetMapping("/tag")
    @ApiOperation(value = "Возвращает отслеживаемые теги пользователя, общее количество его вопросов и ответов, голоса под вопросами и ответами")
    public ResponseEntity<List<UserProfileTagDto>> getUserProfileTagDto(@AuthenticationPrincipal User user) {
        List<UserProfileTagDto> result = questionDtoService.getUserProfileTagDto(user.getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}