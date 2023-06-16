package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/question/{questionId}/answer")
@Api(value = "Контроллер для работы с Answer (ответ на вопрос)", tags = "AnswerController")
public class AnswerResourceController {

    private final AnswerService answerService;
    private final AnswerDtoService answerDtoService;
    private final VoteAnswerService voteAnswerService;

    @Autowired
    public AnswerResourceController(AnswerService answerService,AnswerDtoService answerDtoService,VoteAnswerService voteAnswerService) {
        this.answerService = answerService;
        this.answerDtoService = answerDtoService;
        this.voteAnswerService = voteAnswerService;
    }

    @DeleteMapping("/{answerId}")

    @ApiOperation(value = "Удаление ответа на вопрос", tags = "Answer Delete")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Answer успешно удален!"),
            @ApiResponse(code = 400, message = "Answer с таким ID не существует"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен")})
    public ResponseEntity<?> answerDelete(@PathVariable("answerId") Long answerId) {
        Optional<Answer> answer = answerService.getById(answerId);
        if (answer.isEmpty()) {
            return new ResponseEntity<>("Answer с таким ID не существует!",
                    HttpStatus.BAD_REQUEST);
        }
        answerService.deleteById(answerId);
        return new ResponseEntity<>("Answer с ID " + answerId + " успешно удален", HttpStatus.OK);
    }

    @ApiOperation(value = "Получение всех ответов на вопрос", tags = "Answer Delete")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список Answer успешно получен!"),
            @ApiResponse(code = 400, message = "Question с таким id не существует"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен")})
    @GetMapping
    public ResponseEntity<List<AnswerDto>> getAllAnswers(@PathVariable("questionId") Long questionId) {
        return new ResponseEntity<>(answerDtoService.getAllAnswers(questionId), HttpStatus.OK);
    }

    @ApiOperation(value = "Проголосовать за ответ", tags ="Upvote answer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно проголосовали за ответ!"),
            @ApiResponse(code = 400, message = "Ответа с таким ID не существует"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен")})
    @PostMapping("/{id}/upVote")
    public ResponseEntity<Long> upVoteAnswer(@PathVariable("id") Long answerId, @AuthenticationPrincipal User user) {
        voteAnswerService.upVoteAnswer(answerId, user.getId());
        return new ResponseEntity<>(voteAnswerService.totalVotesCount(answerId), HttpStatus.OK);
    }

    @ApiOperation(value = "Проголосовать против ответа", tags ="Downvote answer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно проголосовали против ответа!"),
            @ApiResponse(code = 400, message = "Ответа с таким ID не существует"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен")})
    @PostMapping("/{id}/downVote")
    public ResponseEntity<Long> downVoteAnswer(@PathVariable("id") Long answerId, @AuthenticationPrincipal User user) {
        voteAnswerService.downVoteAnswer(answerId, user.getId());
        return new ResponseEntity<>(voteAnswerService.totalVotesCount(answerId), HttpStatus.OK);
    }
}

