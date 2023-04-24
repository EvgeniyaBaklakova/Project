package com.javamentor.qa.platform.models.dto.question;

import com.javamentor.qa.platform.models.dto.tag.TagDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuestionCreateDto {

    @NotNull(message = "title can't be null")
    @NotEmpty(message = "title can't be empty")
    private String title;

    @NotNull(message = "description can't be null")
    @NotEmpty(message = "description can't be empty")
    private String description;

    @NotNull(message = "list of tags can't be null, should be at least 1 tag")
    @NotEmpty(message = "list of tags can't be empty")
    private List<TagDto> tags;
}
