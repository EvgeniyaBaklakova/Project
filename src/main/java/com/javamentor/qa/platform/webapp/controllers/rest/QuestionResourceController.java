package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.QuestionViewed;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionViewedService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("api/user/question")
public class QuestionResourceController {

    QuestionService questionService;
    QuestionViewedService questionViewedService;
    UserService userService;
    @Autowired
    public QuestionResourceController(QuestionViewedService questionViewedService, QuestionService questionService, UserService userService) {
        this.questionService = questionService;
        this.questionViewedService = questionViewedService;
        this.userService = userService;
    }


    @PostMapping("/{id}/view")
    public ResponseEntity addView(@PathVariable("id") long id) {
        Optional<User> user  = userService.getById(1L);
        Optional<Question> question = questionService.getById(id);
        if(question.isPresent()) {
            if(user.isPresent()) {
                if (!questionViewedService.isViewed(user.get().getId(), id)) {
                    questionViewedService.persist(new QuestionViewed(user.get(), question.get(), LocalDateTime.now()));
                    return new ResponseEntity<>("View was saved", HttpStatus.OK);
                }
                return new ResponseEntity<>("Already viewed", HttpStatus.OK);
            } else return new ResponseEntity("Incorrect user id", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Incorrect question id", HttpStatus.NOT_FOUND);
    }
}
