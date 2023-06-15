package com.javamentor.qa.platform.models.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagViewDto {
    private Long id;
    private String title;
    private String description;
    private Long questionCount;
    private Long questionCountOneDay;
    private Long questionCountWeekDay;
}
