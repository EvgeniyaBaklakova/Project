package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.dao.impl.pagination.CommentPageDtoDaoImpl;
import com.javamentor.qa.platform.exception.UserNotFoundException;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserProfileCommentDto;
import com.javamentor.qa.platform.models.dto.UserProfileDto;
import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.dto.tag.UserProfileTagDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.service.abstracts.dto.UserProfileCommentDtoService;
import com.javamentor.qa.platform.service.abstracts.model.GroupBookmarksService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/profile")
public class ProfileUserResourceController {

    private final UserDtoService userDtoService;
    private final AnswerService answerService;
    private final QuestionDtoService questionDtoService;
    private final UserProfileCommentDtoService userProfileCommentDtoService;
    private final GroupBookmarksService groupBookmarksService;

    private final UserDao userDao;
    public ProfileUserResourceController(UserDtoService userDtoService
            , AnswerService answerService
            , QuestionDtoService questionDtoService
            , UserProfileCommentDtoService userProfileCommentDtoService, GroupBookmarksService groupBookmarksService, GroupBookmarksService groupBookmarksService1, UserDao userDao) {
        this.userDtoService = userDtoService;
        this.answerService = answerService;
        this.questionDtoService = questionDtoService;
        this.userProfileCommentDtoService = userProfileCommentDtoService;
        this.groupBookmarksService = groupBookmarksService1;
        this.userDao = userDao;
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

    @GetMapping("/comment")
    @ApiOperation(value = "Возвращает все комментарии авторизованного пользователя с пагинацией и сортировкой по дате")
    public ResponseEntity<PageDto<UserProfileCommentDto>> getUserProfileCommentDto(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer items
    ) {
        Map<String, Object> props = new HashMap<>();
        props.put("userId", user.getId());

        PaginationData date = new PaginationData(currentPage, items, CommentPageDtoDaoImpl.class.getSimpleName(), props);
        return new ResponseEntity<>(userProfileCommentDtoService.getPageDto(date), HttpStatus.OK);
    }

    @GetMapping("/bookmark/group")
    @ApiOperation(value = "Получение наименований групп BookMarks пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Информация была успешно получена"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен"),
            @ApiResponse(code = 404, message = "Ресурс, к которому вы пытались обратиться, не найден")
    })
    public ResponseEntity<List<String>> getBookMarkGroup(@AuthenticationPrincipal User authUser) {
        Long authUserId = userDao.getById(authUser.getId()).orElseThrow().getId();
        return ResponseEntity.ok(groupBookmarksService.getGroupBookMarkByName(authUserId));
    }
}