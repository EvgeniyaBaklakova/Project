package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = QuestionDtoConverter.class)
public abstract class QuestionDtoConverter {
    public static final QuestionDtoConverter INSTANCE = Mappers.getMapper(QuestionDtoConverter.class);
    @Mapping(source = "question.id", target = "id")
    @Mapping(source = "question.title", target = "title")
    @Mapping(source = "question.description", target = "description")
    @Mapping(source = "question.tags", target = "listTagDto")

    @Mapping(source = "user.id", target = "authorId")
    @Mapping(source = "user.nickname", target = "authorName")
    @Mapping(source = "user.imageLink", target = "authorImage")

    @Mapping(source = "question.persistDateTime", target = "persistDateTime")
    @Mapping(source = "question.lastUpdateDateTime", target = "lastUpdateDateTime")
    public abstract QuestionDto questionToQuestionDto(Question question, User user);
}
