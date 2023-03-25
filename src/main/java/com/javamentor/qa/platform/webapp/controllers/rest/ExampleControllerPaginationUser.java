package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.dao.impl.dto.Pagination.ExampleDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.service.abstracts.dto.Pagination.PaginationService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(tags = "UserPagination")
public class ExampleControllerPaginationUser {
    private final PaginationService paginationService;

    public ExampleControllerPaginationUser(PaginationService paginationService) {
        this.paginationService = paginationService;
    }


    @GetMapping("/example/{pageNumber}")
    @ApiOperation(value = "Получение страницы юзеров", response = ExampleDtoDao.class, responseContainer = "pageDto")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Страница отсутствует или не существует указанных элементов"),
            @ApiResponse(code = 403, message = "Недостаточно прав доступа"),
            @ApiResponse(code = 200, message = "Страница успешно найдена")
    })
    public ResponseEntity<PageDto> getPageOfUsers(
            @ApiParam(value = "номер страницы") @PathVariable Integer pageNumber,
            @ApiParam(value = "количество юзеров") @RequestParam(value = "itemsOnPage", required = false, defaultValue = "1") Integer itemsOnPage) {
        Map<String, Object> params = new HashMap<>();
        params.put("1", 1);


        return ResponseEntity.ok(paginationService.getPageDto( pageNumber, itemsOnPage, params));

    }
}
