package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;

import java.util.List;
import java.util.Optional;

public interface TagDtoService {
    List<RelatedTagsDto> getAllTen();

    Optional<TagDto> getIgnoredTag(Long userId, Long tagId);

    Optional<TagDto> getTrackedTag(Long userId, Long tagId);
}
