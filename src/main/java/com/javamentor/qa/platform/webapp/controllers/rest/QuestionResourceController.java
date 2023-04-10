package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.models.dto.question.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.QuestionViewed;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionViewedService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
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
import java.util.Optional;

@Api(value = "QuestionResource controller", tags = "Контроллер QuestionResource")
@RestController
@RequestMapping("api/user/question")
public class QuestionResourceController {
    private final QuestionService questionService;

    private final QuestionViewedService questionViewedService;

    private final UserService userService;

    @Autowired
    public QuestionResourceController(QuestionViewedService questionViewedService,
                                      QuestionService questionService, UserService userService) {
        this.questionService = questionService;
        this.questionViewedService = questionViewedService;
        this.userService = userService;
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
        Question questionEntity = QuestionConverter.INSTANCE.questionCreateDtoToQuestion(questionToCreate, user);

        Question questionFromDb = questionService.save(questionEntity);

        QuestionDto questionDto = QuestionDtoConverter.INSTANCE.questionToQuestionDto(questionFromDb, user);
        return new ResponseEntity<>(questionDto, HttpStatus.OK);
    }
}
