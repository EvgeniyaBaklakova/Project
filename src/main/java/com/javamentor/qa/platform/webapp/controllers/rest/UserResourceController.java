package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.user.UserDto;
import com.javamentor.qa.platform.service.impl.dto.UserDtoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
public class UserResourceController {

    private final UserDtoServiceImpl userDtoServiceImpl;

    public UserResourceController(UserDtoServiceImpl userDtoServiceImpl) {
        this.userDtoServiceImpl = userDtoServiceImpl;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserDto(@PathVariable("userId") long id) {
        return new ResponseEntity<>(userDtoServiceImpl.getById(id), HttpStatus.OK);

    }
}
