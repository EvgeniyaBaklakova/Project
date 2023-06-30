package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.dao.impl.pagination.QuestionPageDtoDaoBySearchImpl;
import com.javamentor.qa.platform.dao.impl.pagination.UserPageDtoDaoByPersistDateImpl;
import com.javamentor.qa.platform.exception.UserNotFoundException;
import com.javamentor.qa.platform.models.dto.question.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.QuestionViewed;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.model.*;
import com.javamentor.qa.platform.service.impl.model.QuestionSearchServiceImpl;
import com.javamentor.qa.platform.webapp.converter.QuestionConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Api(value = "QuestionResource controller", tags = "Контроллер QuestionResource")
@RestController
@RequestMapping("api/user/question")
public class QuestionResourceController {
    private final BookMarksService bookMarksService;
    private final QuestionService questionService;
    private final QuestionDtoService questionDtoService;
    private final QuestionViewedService questionViewedService;
    private final UserService userService;
    private final CommentQuestionService commentQuestionService;
    private final QuestionConverter questionConverter;

    @Autowired
    public QuestionResourceController(BookMarksService bookMarksService, QuestionViewedService questionViewedService,
                                      QuestionService questionService, UserService userService, CommentQuestionService commentQuestionService,
                                      QuestionConverter questionConverter, QuestionDtoService questionDtoService
                                      ) {
        this.bookMarksService = bookMarksService;
        this.questionService = questionService;
        this.questionDtoService = questionDtoService;
        this.questionViewedService = questionViewedService;
        this.userService = userService;
        this.commentQuestionService = commentQuestionService;
        this.questionConverter = questionConverter;
    }

    @PostMapping("/{id}/view")
    public ResponseEntity addView(@PathVariable("id") long id, @AuthenticationPrincipal User user) {
        Optional<Question> question = questionService.getById(id);
        if (question.isEmpty()) {
            return new ResponseEntity<>("Incorrect question id", HttpStatus.NOT_FOUND);
        }
        if (!questionViewedService.isViewed(user.getId(), id)) {
            questionViewedService.persist(new QuestionViewed(user, question.get(), LocalDateTime.now()));
            return new ResponseEntity<>("View was saved", HttpStatus.OK);
        }
        return new ResponseEntity<>("Already viewed", HttpStatus.OK);
    }
    @ApiOperation(value = "Добавляет новый Question и возвращает QuestionDto")
    @PostMapping
    public ResponseEntity<QuestionDto> addQuestion(@RequestBody @Valid QuestionCreateDto questionToCreate,
                                                   @AuthenticationPrincipal User user) {
        Question questionEntity = questionConverter.questionCreateDtoToQuestion(questionToCreate, user);

        Question question = questionService.save(questionEntity);

        QuestionDto questionDto = questionConverter.questionToQuestionDto(question, user);
        return new ResponseEntity<>(questionDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Добавляет комментарий к вопросу")
    @PostMapping("/{id}/comment")
    public ResponseEntity<HttpStatus> addComment(@PathVariable("id") long id, @RequestBody String text) {
        Question question = questionService.getById(id).orElse(null);
        if (question == null || text == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CommentQuestion comment = new CommentQuestion(text, question.getUser());
        comment.setQuestion(question);
        commentQuestionService.persist(comment);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получение QuestionDto по Question id", tags = {"Получение QuestionDto"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "QuestionDto успешно получено"),
            @ApiResponse(code = 400, message = "Вопрос с таким ID не найден"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен")})

    public ResponseEntity<?> getQuestionDtoById(@PathVariable Long id) {

        if (questionDtoService.getQuestionDtoById(id).isEmpty()) {
            return new ResponseEntity<>("Вопроса с ID " + id + " не существует!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(questionDtoService.getQuestionDtoById(id), HttpStatus.OK);

    }

    @PostMapping("/{id}/bookmark")
    @ApiOperation(value = "Добавление вопроcа в закладки текущего аутентифицированного пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Вопрос успешно добавлен"),
            @ApiResponse(code = 400, message = "Вопрос с таким ID не найден"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен")})
    public ResponseEntity<String> addQuestionToBookmarks(@PathVariable("id") Long id,
                                                         @AuthenticationPrincipal User user) {
        bookMarksService.addBookMarks(user, id);
        return ResponseEntity.ok("Вопрос успешно добавлен в закладки");
    }

    @PostMapping("/search")
    public ResponseEntity<?> getSearchReuslt(@RequestParam Integer page,
                                             @RequestParam Integer itemsOnPage,
                                             @RequestParam String query,
                                             @RequestParam String order) {
        PaginationData data = new PaginationData(page, itemsOnPage, QuestionPageDtoDaoBySearchImpl.class.getSimpleName(), query + order);
        return new ResponseEntity<>(questionDtoService.getPageDto(data), HttpStatus.OK);


    }
}
