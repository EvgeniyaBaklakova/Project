package com.javamentor.qa.platform.dao.abstracts.pagination;

import com.javamentor.qa.platform.models.dto.tag.IgnoredTagsDto;
import com.javamentor.qa.platform.models.dto.tag.TrackedTagsDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;

import java.util.List;
import java.util.Map;

public interface QuestionDtoDaoWithoutAnswers<QuestionDto> extends PageDtoDao<QuestionDto>{
    List<QuestionDto> getItemsWithTags(PaginationData properties, List<IgnoredTagsDto> ignoredTagsDtoList, List<TrackedTagsDto> trackedTagsDtoList);
    Long getTotalResultCountWithTags(Map<String, Object> properties, List<IgnoredTagsDto> ignoredTagsDtoList, List<TrackedTagsDto> trackedTagsDtoList);
}