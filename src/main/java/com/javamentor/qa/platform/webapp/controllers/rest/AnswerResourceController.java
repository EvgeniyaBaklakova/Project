package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/question/{questionId}/answer")
@Api(value = "Контроллер для работы с Answer (ответ на вопрос)", tags = "AnswerController")
public class AnswerResourceController {

    private final AnswerService answerService;

    private final AnswerDtoService answerDtoService;

    @Autowired
    public AnswerResourceController(AnswerService answerService, AnswerDtoService answerDtoService) {
        this.answerService = answerService;
        this.answerDtoService = answerDtoService;
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
}

