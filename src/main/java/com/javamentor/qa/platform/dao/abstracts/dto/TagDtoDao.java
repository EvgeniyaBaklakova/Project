package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.tag.IgnoredTagsDto;
import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.models.dto.tag.UserProfileTagDto;
import com.javamentor.qa.platform.models.entity.question.TagQuestion;

import java.util.List;
import java.util.Optional;

public interface TagDtoDao {
    List<RelatedTagsDto> getAllTen();

    Optional<TagDto> getIgnoredTag(Long userId, Long tagId);

    Optional<TagDto> getTrackedTag(Long userId, Long tagId);

    List<IgnoredTagsDto> getIgnoredTags(Long userId);

    List<TagDto> getTagsByQuestionId(Long id);
    List<TagQuestion> getTagsByQuestionsIds(List<Long> id);

    List<UserProfileTagDto> getUserProfileTagDto(Long id);
}
