package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.UserDtoTest;
import com.javamentor.qa.platform.models.dto.AllQuestionDto;
import com.javamentor.qa.platform.service.abstracts.dto.AllQuestionDtoService;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoTestService;
import com.javamentor.qa.platform.webapp.controllers.util.DecodeJwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class UserResourceControllerTest {

    private final UserDtoTestService userDtoTestService;
    private final AllQuestionDtoService allQuestionDtoService;

    public UserResourceControllerTest(UserDtoTestService userDtoTestService, AllQuestionDtoService allQuestionDtoService) {
        this.userDtoTestService = userDtoTestService;
        this.allQuestionDtoService = allQuestionDtoService;
    }


    @GetMapping("user/{userId}")
    public ResponseEntity<UserDtoTest> getUserDto(@PathVariable("userId") long id) {
        return new ResponseEntity<>(userDtoTestService.getUserById(id), HttpStatus.OK);

    }



    @GetMapping("/profile/questions")
    public ResponseEntity<AllQuestionDto> getAllQuestion(@RequestHeader("Authorization") String token){
        return  ResponseEntity.ok((AllQuestionDto) allQuestionDtoService.getAllQuestions(DecodeJwtTokenUtil.decodeJwtToken(token)));
    }

}
