package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.tag.IgnoredTagsDto;
import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.models.dto.tag.TagViewDto;

import java.util.List;
import java.util.Optional;

public interface TagDtoService extends PageDtoService<TagViewDto> {
    List<RelatedTagsDto> getAllTen();

    Optional<TagDto> getIgnoredTag(Long userId, Long tagId);

    Optional<TagDto> getTrackedTag(Long userId, Long tagId);

    List<IgnoredTagsDto> getIgnoredTags(Long userId);
}
