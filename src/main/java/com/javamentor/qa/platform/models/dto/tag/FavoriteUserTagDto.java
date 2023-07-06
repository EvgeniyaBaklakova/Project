package com.javamentor.qa.platform.models.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteUserTagDto {
    Integer tagId;
    String name;
    Long countMessage;

    public static final Comparator<FavoriteUserTagDto>
            COMPARE_BY_COUNT_MESSAGE = (x, y) -> (int)(y.getCountMessage() - x.getCountMessage());
}
