package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.dao.impl.pagination.TagPageDtoDaoByDateImpl;
import com.javamentor.qa.platform.dao.impl.pagination.TagPageDtoDaoByNameImpl;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.tag.IgnoredTagsDto;
import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.models.dto.tag.TagViewDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.question.IgnoredTag;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import com.javamentor.qa.platform.service.abstracts.model.IgnoredTagService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Api(value = "RelatedTagDto controller", tags = "Контроллер RelatedTagDto")
@RestController
@RequestMapping("api/user/tag")
public class TagResourceController {

    private final TagDtoService tagDtoService;
    private final TagService tagService;
    private final IgnoredTagService ignoredTagService;
    private final TrackedTagService trackedTagService;

    public TagResourceController(TagDtoService tagDtoService, TagService tagService,
                                 IgnoredTagService ignoredTagService, TrackedTagService trackedTagService) {
        this.tagDtoService = tagDtoService;
        this.tagService = tagService;
        this.ignoredTagService = ignoredTagService;
        this.trackedTagService = trackedTagService;
    }

    @ApiOperation(value = "Возвращает список из 10 RelatedTagDto с максимальным кол-вом Questions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список успешно получен"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен"),
            @ApiResponse(code = 404, message = "Ресурс, к которому вы пытались обратиться, не найден")
    })
    @GetMapping("/related")
    public List<RelatedTagsDto> getAllTenDto() {
        return tagDtoService.getAllTen();
    }

    @ApiOperation(value = "Сохраняет Tag в IgnoredTag для пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Тэг успешно сохранен"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен"),
            @ApiResponse(code = 404, message = "Ресурс, к которому вы пытались обратиться, не найден")
    })
    @PostMapping("/{id}/ignored")
    public ResponseEntity<?> addIgnoreTag(@AuthenticationPrincipal User user, @PathVariable("id") long id) {
        Optional<Tag> tag = tagService.getById(id);
        if (tag.isPresent()) {
            ignoredTagService.persist(new IgnoredTag(tag.get(), user, LocalDateTime.now()));
            return new ResponseEntity<>(tagDtoService.getIgnoredTag(user.getId(), id).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Тэга с таким id не существует", HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Сохраняет Tag в TrackedTag для пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Тэг успешно сохранен"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен"),
            @ApiResponse(code = 404, message = "Ресурс, к которому вы пытались обратиться, не найден")
    })
    @PostMapping("/{id}/tracked")
    public ResponseEntity<?> addTrackedTag(@AuthenticationPrincipal User user, @PathVariable("id") long id) {
        Optional<Tag> tag = tagService.getById(id);
        if (tag.isPresent()) {
            trackedTagService.persist(new TrackedTag(tag.get(), user, LocalDateTime.now()));
            return new ResponseEntity<>(tagDtoService.getTrackedTag(user.getId(), id).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Тэга с таким id не существует", HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Возвращает список игнорируемых тэгов для пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список успешно получен"),
            @ApiResponse(code = 401, message = "Вы не авторизованы для просмотра ресурса"),
            @ApiResponse(code = 403, message = "Доступ к ресурсу, к которому вы пытались обратиться, запрещен"),
            @ApiResponse(code = 404, message = "Ресурс, к которому вы пытались обратиться, не найден")
    })
    @GetMapping("/ignored")
    public ResponseEntity<List<IgnoredTagsDto>> getIgnoredTagsByUser() {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        List<IgnoredTagsDto> ignoredTagsDtos = tagDtoService.getIgnoredTags(userId);
        return new ResponseEntity<>(ignoredTagsDtos, HttpStatus.OK);
    }

    @GetMapping("/new")
    @ApiOperation(value = "Получение всех тэгов с пагинацией, отсортированных по дате добавления")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = TagViewDto.class),
            @ApiResponse(code = 400, message = "TagViewDto не найдены")
    })
    public ResponseEntity<PageDto<TagViewDto>> getAllTagsByData(@RequestParam(defaultValue = "1") Integer page,
                                                                @RequestParam(required = false, defaultValue = "10") Integer itemsOnPage) {
        PaginationData data = new PaginationData(page,itemsOnPage,
                TagPageDtoDaoByDateImpl.class.getSimpleName());
        return new ResponseEntity<>(tagDtoService.getPageDto(data),HttpStatus.OK);
    }

    @GetMapping("/name")
    @ApiOperation(value = "Получение всех тегов с пагинацией, отсортированных по имени")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = TagViewDto.class),
            @ApiResponse(code = 400, message = "TagDto не найдены")
    })
    public ResponseEntity<PageDto<TagViewDto>> getAllTagsByName(@RequestParam(defaultValue = "1")Integer page,
                                                            @RequestParam(required = false, defaultValue = "10") Integer items) {
        PaginationData data = new PaginationData(page,items,
                TagPageDtoDaoByNameImpl.class.getSimpleName());
        return new ResponseEntity<>(tagDtoService.getPageDto(data), HttpStatus.OK);
    }

}
