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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

    @PostMapping("api/user/question/{id}/view")
    public ResponseEntity addView(@PathVariable("id") long id, @RequestBody Long userId) {
        User user  = userService.getById(userId).orElse(null);
        Question question = questionService.getById(id).orElse(null);
        if (!questionViewedService.isViewed(user.getId(), id)) {
            if (question != null) {
                questionViewedService.persist(new QuestionViewed(null, user, question, null));
                System.out.println("acacia");
                return new ResponseEntity<>("View was saved", HttpStatus.OK);
            }
            return new ResponseEntity<>("Incorrect question id", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Already viewed", HttpStatus.OK);
    }
}
