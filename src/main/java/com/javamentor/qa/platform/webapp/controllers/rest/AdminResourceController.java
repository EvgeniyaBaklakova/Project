package com.javamentor.qa.platform.webapp.controllers.rest;



import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Api(value = "admin resource controller", tags = "блокировка пользователя по id")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminResourceController {

    private final UserService userService;

    @ApiOperation(value = "set isEnable as false", tags = "blocking user")
    @PostMapping(value = "/block")
    public void blockUser(@RequestParam Long userId) {
        Optional<User> user = userService.getById(userId);
        user.ifPresent(u -> {
                    u.setIsEnabled(false);
                    userService.update(u);
                }
        );
    }
}