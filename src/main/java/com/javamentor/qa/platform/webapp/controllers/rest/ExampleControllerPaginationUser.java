package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.user.UserDtoExample;
import com.javamentor.qa.platform.service.impl.dto.ExamplePaginationDtoServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/exampleuserpagination")
@Api(tags = "UserPagination")
public class ExampleControllerPaginationUser {
    private final ExamplePaginationDtoServiceImpl examplePaginationDtoService;

    public ExampleControllerPaginationUser(@Qualifier("forExampleUserController") ExamplePaginationDtoServiceImpl examplePaginationDtoService) {
        this.examplePaginationDtoService = examplePaginationDtoService;
    }

    @GetMapping("/page/{pageNumber}")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Страница отсутствует или не существует указанных элементов"),
            @ApiResponse(code = 403, message = "Недостаточно прав доступа"),
            @ApiResponse(code = 200, message = "Страница успешно найдена")
    })
    public ResponseEntity<PageDto<UserDtoExample>> getPageOfUsers(@PathVariable Integer pageNumber,
                                                                  @RequestParam(required = false, defaultValue = "10") Integer itemsOnPage,
                                                                  @RequestParam(required = false, defaultValue = "User") String string) {
        Map<String, Object> params = new HashMap<>();
        params.put("Username", string);
        return ResponseEntity.ok(examplePaginationDtoService.getPageDtoWithParameters(pageNumber, itemsOnPage, params));

    }
}
