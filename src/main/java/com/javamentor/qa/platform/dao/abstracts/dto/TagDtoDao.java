package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;

import java.util.List;

public interface TagDtoDao {
    List<RelatedTagsDto> getAllTen();
}
