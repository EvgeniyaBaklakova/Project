package com.javamentor.qa.platform.webapp.converter;

import com.javamentor.qa.platform.models.dto.question.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = QuestionConverter.class)
public abstract class QuestionConverter {
    @Mapping(source = "questionCreate.title", target = "title")
    @Mapping(source = "questionCreate.description", target = "description")
    @Mapping(source = "questionCreate.tags", target = "tags")

    @Mapping(source = "user.id", target = "id", ignore = true)
    @Mapping(source = "user", target = "user")

    public abstract Question questionCreateDtoToQuestion(QuestionCreateDto questionCreate, User user);

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
