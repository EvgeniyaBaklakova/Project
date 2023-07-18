package com.javamentor.qa.platform.models.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteUserTagDto {
    private Integer tagId;
    private String name;
    private Long countMessage;
}
