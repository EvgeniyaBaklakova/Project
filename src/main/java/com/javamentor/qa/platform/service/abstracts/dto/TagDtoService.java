package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;

import java.util.List;

public interface TagDtoService {
    List<RelatedTagsDto> getAllTen();
}
