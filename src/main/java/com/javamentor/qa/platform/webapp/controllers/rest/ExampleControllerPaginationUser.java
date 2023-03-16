package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.user.UserDtoExample;
import com.javamentor.qa.platform.service.abstracts.dto.ExamplePaginationDtoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(tags = "UserPagination")
public class ExampleControllerPaginationUser {
    private final ExamplePaginationDtoService examplePaginationDtoService;

    public ExampleControllerPaginationUser(ExamplePaginationDtoService examplePaginationDtoService) {
        this.examplePaginationDtoService = examplePaginationDtoService;
    }


    @GetMapping("/example")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Страница отсутствует или не существует указанных элементов"),
            @ApiResponse(code = 403, message = "Недостаточно прав доступа"),
            @ApiResponse(code = 200, message = "Страница успешно найдена")
    })
    public ResponseEntity<PageDto<UserDtoExample>> getPageOfUsers(@RequestParam (defaultValue = "1") Integer pageNumber,
                                                                  @RequestParam(defaultValue = "10") Integer itemsOnPage) {

        return ResponseEntity.ok(examplePaginationDtoService.getPageDto("ExamplePaginationResponseUser",pageNumber, itemsOnPage));

    }
}
