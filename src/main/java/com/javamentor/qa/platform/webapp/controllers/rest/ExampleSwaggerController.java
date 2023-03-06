package com.javamentor.qa.platform.webapp.controllers.rest;


import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "Test controller", tags = "Тестовый контроллер: Swagger's")
@RestController
@RequestMapping("/api")
public class ExampleSwaggerController {


    public static ReadWriteDaoImpl readWriteDao;



    @GetMapping()
    @ApiOperation(value = "Возвращает список всех пользователей")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список успешно получен"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен"),
            @ApiResponse(code = 404, message = "Ресурс, к которому вы пытались обратиться, не найден")
    }
    )
    List<User> findAll(){
        return readWriteDao.getAll();
    }

}
