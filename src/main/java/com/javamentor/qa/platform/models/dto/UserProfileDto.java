package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private Long id;
    private Integer reputation;
    private Long countAnswer;
    private Long countQuestion;
    private Long countView;
    private List<UserTagFavoriteDto> userTagFavoriteDtoList;
}
