package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.AllQuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AllQuestionDtoMapper.class)
public abstract class AllQuestionDtoMapper {

    @Mapping(target = "questionId", source = "id")
    @Mapping(target = "questionTitle", source = "title")
    @Mapping(target = "answerPersistDateTime", source = "persistDateTime")
    @Mapping(target = "tagDtoList", source = "tags")
    @Mapping(target = "countAnswer", expression = "java(question.getAnswers().size())")

    public abstract AllQuestionDto toAllQuestionDto(Question question);

}
