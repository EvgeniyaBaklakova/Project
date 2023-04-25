package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.UserDtoTest;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoTestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class UserResourceControllerTest {

    private final UserDtoTestService userDtoTestService;

    public UserResourceControllerTest(UserDtoTestService userDtoTestService) {
        this.userDtoTestService = userDtoTestService;
    }


    @GetMapping("user/{userId}")
    public ResponseEntity<UserDtoTest> getUserDto(@PathVariable("userId") long id) {
        return new ResponseEntity<>(userDtoTestService.getUserById(id), HttpStatus.OK);

    }



}
