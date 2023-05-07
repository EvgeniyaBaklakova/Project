package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.dao.impl.pagination.UserPageDtoDaoByVoteImpl;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/user")

public class UserResourceController {

    private final UserDtoService userDtoService;

    public UserResourceController(UserDtoService userDtoService) {
        this.userDtoService = userDtoService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserDto(@PathVariable("userId") long id) {
        return new ResponseEntity<>(userDtoService.getById(id), HttpStatus.OK);

    }
    @GetMapping("/vote")
    @ApiOperation(value = "Получение всех UserDTO с пагинацией отсортированные по голосам")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = PageDto.class),
            @ApiResponse(code = 400, message = "UserDTO не найдены")
    })

    public ResponseEntity<PageDto<UserDto>> getUsersByVoteAnswer(@RequestParam(defaultValue = "1") Integer itemsOnPage,
                                                                 @RequestParam(required = false, defaultValue = "15") Integer items,
                                                                 @RequestParam(required = false) String filter) {
        PaginationData data = new PaginationData(itemsOnPage, items,
                UserPageDtoDaoByVoteImpl.class.getSimpleName(), filter);
        return new ResponseEntity<>(userDtoService.getPageDto(data), HttpStatus.OK);
    }
}
