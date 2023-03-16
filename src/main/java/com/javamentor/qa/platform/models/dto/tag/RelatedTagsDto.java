package com.javamentor.qa.platform.models.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RelatedTagsDto {
    private Long id;
    private String title;
    private Long countQuestion;
}
