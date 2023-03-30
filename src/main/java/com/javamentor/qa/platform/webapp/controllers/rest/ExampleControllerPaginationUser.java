package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.dao.impl.pagination.ExampleDtoDao;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.service.abstracts.dto.ExamplePaginationDtoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(tags = "UserPagination")
public class ExampleControllerPaginationUser {
    private final ExamplePaginationDtoService examplePaginationDtoService;

    public ExampleControllerPaginationUser(ExamplePaginationDtoService examplePaginationDtoService) {
        this.examplePaginationDtoService = examplePaginationDtoService;
    }


    @GetMapping("/example")
    @ApiOperation(value = "Получение страницы юзеров", response = ExampleDtoDao.class, responseContainer = "pageDto")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Страница отсутствует или не существует указанных элементов"),
            @ApiResponse(code = 403, message = "Недостаточно прав доступа"),
            @ApiResponse(code = 200, message = "Страница успешно найдена")
    })
    public ResponseEntity<?> getPageOfUsers(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int items) {
        PaginationData data = new PaginationData(currentPage,
                items,
                ExampleDtoDao.class.getSimpleName());


        return new ResponseEntity(examplePaginationDtoService.getPageDto(data), HttpStatus.OK);

    }
}
