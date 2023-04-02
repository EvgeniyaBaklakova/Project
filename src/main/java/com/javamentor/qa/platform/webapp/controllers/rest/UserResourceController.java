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
import org.springframework.web.bind.annotation.*;


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
    @ApiOperation(value = "Постраничное получение списка пользователей отсортированных по сумме голосов", response = PageDto.class, responseContainer = "UserDto")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Возвращает PageDto с вложенным массивом UserDto согласно текущей страницы" +
                            "и количеству запрашиваемых пользователей"),
            @ApiResponse(code = 403, message = "Доступ запрещён"),
            @ApiResponse(code = 400, message = "Неверная нумерация страниц")
    })
    @GetMapping( "/vote")
    public ResponseEntity<PageDto<UserDto>> getUsersByVoteAsc(@RequestParam(defaultValue = "1") Integer page,
                                                              @RequestParam(required = false, defaultValue = "10") Integer items,
                                                              @RequestParam(required = false) String filter) {
        PaginationData data = new PaginationData(page, items,
                UserPageDtoDaoByVoteImpl.class.getSimpleName(), filter);
        return new ResponseEntity<>(userDtoService.getPageDto(data), HttpStatus.OK);
    }
}
