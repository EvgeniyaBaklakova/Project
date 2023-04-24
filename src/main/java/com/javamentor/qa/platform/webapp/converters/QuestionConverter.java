package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.question.QuestionCreateDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = QuestionConverter.class)
public abstract class QuestionConverter {
    @Mapping(source = "questionCreate.title", target = "title")
    @Mapping(source = "questionCreate.description", target = "description")
    @Mapping(source = "questionCreate.tags", target = "tags")
    @Mapping(source = "user.id", target = "id", ignore = true)
    @Mapping(source = "user", target = "user")
    public abstract Question questionCreateDtoToQuestion(QuestionCreateDto questionCreate, User user);

}
