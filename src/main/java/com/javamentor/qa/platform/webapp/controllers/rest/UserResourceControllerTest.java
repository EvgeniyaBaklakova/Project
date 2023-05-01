package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.AllQuestionDto;
import com.javamentor.qa.platform.models.dto.UserDtoTest;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoTestService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/test")
public class UserResourceControllerTest {

    private final UserDtoTestService userDtoTestService;
    private final QuestionService questionService;




    @GetMapping("user/{userId}")
    public ResponseEntity<UserDtoTest> getUserDto(@PathVariable("userId") long id) {
        return new ResponseEntity<>(userDtoTestService.getUserById(id), HttpStatus.OK);

    }



    @GetMapping("/profile/questions")
    public ResponseEntity<AllQuestionDto> getAllQuestion(@AuthenticationPrincipal User user){
        return  ResponseEntity.ok((AllQuestionDto) questionService.getAllQuestions(user));
    }

}
