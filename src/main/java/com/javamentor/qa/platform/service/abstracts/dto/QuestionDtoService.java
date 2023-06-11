package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.dto.tag.IgnoredTagsDto;
import com.javamentor.qa.platform.models.dto.tag.TrackedTagsDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;

import java.util.List;
import java.util.Optional;

public interface QuestionDtoService extends PageDtoService<QuestionDto> {
    Optional<QuestionDto> getQuestionDtoById(Long id);
    PageDto<QuestionDto> getPageDto(PaginationData data);
    PageDto<QuestionDto> getPageDtoWithTags(PaginationData data, List<IgnoredTagsDto> ignoredTagsDtoList, List<TrackedTagsDto> trackedTagsDtoList);
    List<UserProfileQuestionDto> getUserQuestions(Long id);
    List<UserProfileQuestionDto> getUserDeleteQuestions(Long id);
}
