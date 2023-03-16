package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "RelatedTagDto controller", tags = "Контроллер RelatedTagDto")
@RestController
@RequestMapping("api/user/tag/related")
public class TagResourceController {

    private final TagDtoService tagDtoService;

    public TagResourceController(TagDtoService tagDtoService) {
        this.tagDtoService = tagDtoService;
    }

    @ApiOperation(value = "Возвращает список из 10 RelatedTagDto с максимальным кол-вом Questions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список успешно получен"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен"),
            @ApiResponse(code = 404, message = "Ресурс, к которому вы пытались обратиться, не найден")
    })
    @GetMapping
    public List<RelatedTagsDto> getAllTenDto() {
        return tagDtoService.getAllTen();
    }
}
