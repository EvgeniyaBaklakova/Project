package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/question/{questionId}/answer")
@Api(value = "Контроллер для работы с Answer (ответ на вопрос)", tags = "AnswerController")
public class AnswerResourceController {

    private final AnswerService answerService;
    private final AnswerDtoService answerDtoService;
    private final ReputationService reputationService;
    private final VoteAnswerService voteAnswerService;

    @Autowired
    public AnswerResourceController(AnswerService answerService,AnswerDtoService answerDtoService,ReputationService reputationService,VoteAnswerService voteAnswerService) {
        this.answerService = answerService;
        this.answerDtoService = answerDtoService;
        this.reputationService = reputationService;
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
            @ApiResponse(code = 400, message = "Неверный запрос"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен")})
    @PostMapping("/{id}/upVote" )
    public ResponseEntity<Long> upVoteAnswer(@PathVariable("id") Long answerId, @AuthenticationPrincipal User user) {
        if (!voteAnswerService.hasUserAlreadyVoted(answerId, user.getId())) {
            answerService.upVoteAnswer(answerId, user.getId());
            reputationService.increaseAuthorReputation(answerService.getAnswerAuthorId(answerId));
        }
        return new ResponseEntity<>((reputationService.getAuthorReputation(answerService.getAnswerAuthorId(answerId)).longValue()), HttpStatus.OK);
    }

    @ApiOperation(value = "Проголосовать против ответа", tags ="Downvote answer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно проголосовали против ответа!"),
            @ApiResponse(code = 400, message = "Неверный запрос"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен")})
    @PostMapping("/{id}/downVote" )
    public ResponseEntity<Long> downVoteAnswer(@PathVariable("id") Long answerId, @AuthenticationPrincipal User user) {
        if (!voteAnswerService.hasUserAlreadyVoted(answerId,user.getId())) {
            answerService.downVoteAnswer(answerId,user.getId());
            reputationService.decreaseAuthorReputation(answerService.getAnswerAuthorId(answerId));
        }
        return new ResponseEntity<>((reputationService.getAuthorReputation(answerService.getAnswerAuthorId(answerId)).longValue()), HttpStatus.OK);
    }
}

