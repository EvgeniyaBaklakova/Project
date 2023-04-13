package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.models.dto.question.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.entity.Comment;
import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.QuestionViewed;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.*;
import com.javamentor.qa.platform.webapp.converters.QuestionConverter;
import com.javamentor.qa.platform.webapp.converters.QuestionDtoConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Api(value = "QuestionResource controller", tags = "Контроллер QuestionResource")
@RestController
@RequestMapping("api/user/question")
public class QuestionResourceController {
    private final QuestionService questionService;
    private final QuestionViewedService questionViewedService;
    private final UserService userService;
    private final CommentService commentService;
    private final RoleService roleService;
    private final QuestionConverter questionConverter;
    private final QuestionDtoConverter questionDtoConverter;

    @Autowired
    public QuestionResourceController(QuestionViewedService questionViewedService,
                                      QuestionService questionService, UserService userService, CommentService commentService,
                                      RoleService roleService, QuestionConverter questionConverter, QuestionDtoConverter questionDtoConverter) {
        this.questionService = questionService;
        this.questionViewedService = questionViewedService;
        this.userService = userService;
        this.commentService = commentService;
        this.roleService = roleService;
        this.questionConverter = questionConverter;
        this.questionDtoConverter = questionDtoConverter;
    }

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
    public ResponseEntity<QuestionDto> addQuestion(@RequestBody @Valid QuestionCreateDto questionToCreate) {
        User user = userService.getById(1L).orElse(null); //TODO security
        Question questionEntity = questionConverter.questionCreateDtoToQuestion(questionToCreate, user);

        Question question = questionService.save(questionEntity);

        QuestionDto questionDto = questionDtoConverter.questionToQuestionDto(question, user);
        return new ResponseEntity<>(questionDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Добавляет комментарий к вопросу")
    @PostMapping("/{id}/comment")
    public ResponseEntity<Comment> addComment(@PathVariable("id") long id, @RequestBody @Valid Comment comment) {
        Question question = questionService.getById(id).get();
        User user = userService.getById(question.getUser().getId()).get();
        Role role = roleService.getById(user.getRole().getId()).get();
        user.setRole(role);

        Comment savedComment = commentService.addComment(comment, user);

        List<CommentQuestion> commentQuestionList = new ArrayList<>();
        commentQuestionList.add(new CommentQuestion(savedComment.getText(), user));

        question.setCommentQuestions(commentQuestionList);

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
}
