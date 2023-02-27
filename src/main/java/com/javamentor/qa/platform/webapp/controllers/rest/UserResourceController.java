package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.user.UserDto;
import com.javamentor.qa.platform.service.impl.dto.UserDtoService;
import com.javamentor.qa.platform.webapp.controllers.util.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
public class UserResourceController {

    private final UserDtoService userDtoService;

    public UserResourceController(UserDtoService userDtoService) {
        this.userDtoService = userDtoService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserDto(@PathVariable("userId") long id) {
        try {
            UserDto userDto = userDtoService.getById(id).orElseThrow();
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new UserNotFound(HttpStatus.NOT_FOUND.value(),
                    "User with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
    }
}
