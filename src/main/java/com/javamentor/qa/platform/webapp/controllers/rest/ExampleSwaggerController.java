package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Api(value = "Test controller", tags = "Тестовый контроллер: Swagger's")
@RestController
@RequestMapping("/api")
public class ExampleSwaggerController {


    public static ReadWriteDaoImpl readWriteDao;
    private UserDto UserDto;
    private String name;


    @GetMapping("/users")
    @ApiOperation(value = "Возвращает список всех пользователей")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список успешно получен"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен"),
            @ApiResponse(code = 404, message = "Ресурс, к которому вы пытались обратиться, не найден")
    }
    )

    public ResponseEntity<UserDto> getAll() {
        return new ResponseEntity<UserDto>(HttpStatus.OK);
    }

}
