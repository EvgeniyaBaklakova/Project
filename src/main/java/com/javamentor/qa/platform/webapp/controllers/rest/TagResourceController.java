package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import com.javamentor.qa.platform.service.abstracts.dto.RelatedTagsDtoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/user/tag/related")
public class TagResourceController {

    private final RelatedTagsDtoService relatedTagsDtoService;

    public TagResourceController(RelatedTagsDtoService relatedTagsDtoService) {
        this.relatedTagsDtoService = relatedTagsDtoService;
    }

    @GetMapping()
    public List<RelatedTagsDto> getAllTenDto() {
        return relatedTagsDtoService.getAllTen();
    }
}
