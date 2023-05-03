package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.models.dto.question.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.QuestionViewed;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.model.*;
import com.javamentor.qa.platform.webapp.converters.QuestionConverter;
import com.javamentor.qa.platform.webapp.converters.QuestionDtoConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;
@RequiredArgsConstructor
@Api(value = "QuestionResource controller", tags = "Контроллер QuestionResource")
@RestController
@RequestMapping("api/user/question")
public class QuestionResourceController {
    private final QuestionService questionService;
    private final QuestionDtoService questionDtoService;
    private final QuestionViewedService questionViewedService;
    private final UserService userService;
    private final CommentQuestionService commentQuestionService;
    private final QuestionConverter questionConverter;
    private final QuestionDtoConverter questionDtoConverter;
    private final BookMarksService bookMarksService;




    @PostMapping("/{id}/view")
    public ResponseEntity addView(@PathVariable("id") long id) {
        Optional<Question> question = questionService.getById(id);
        if (question.isEmpty()) {
            return new ResponseEntity<>("Incorrect question id", HttpStatus.NOT_FOUND);
        }
        User user = userService.getById(1L).get();
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

        QuestionDto questionDto = questionDtoConverter.questionToQuestionDto(question, user);
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
    @ApiOperation(value = "Добавление вопрома в закладки текущего аутентифицированного пользователя")
    public ResponseEntity<String> addQuestionToBookmarks(@PathVariable("id") Long id,
                                                         @AuthenticationPrincipal User user) {
        bookMarksService.addBookMarks(user, id);
        return ResponseEntity.ok("Вопрос успешно добавлен в закладки");
    }
}

