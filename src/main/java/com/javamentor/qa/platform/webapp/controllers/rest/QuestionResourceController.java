package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.QuestionViewed;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionViewedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionResourceController {

    QuestionService questionService;
    QuestionViewedService questionViewedService;

    @Autowired
    public QuestionResourceController(QuestionViewedService questionViewedService, QuestionService questionService) {
        this.questionService = questionService;
        this.questionViewedService = questionViewedService;
    }

    @PostMapping("api/user/question/{id}/view")
    public ResponseEntity addView(@PathVariable("id") long id, @AuthenticationPrincipal User user) {
        Question question = questionService.getById(id).orElse(null);
        if (!questionViewedService.isViewed(user.getId(), id)) {
            if (question != null) {
                questionViewedService.persist(new QuestionViewed(null, user, question, null));
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
