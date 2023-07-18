package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.dto.tag.FavoriteUserTagDto;
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
    private Long reputation;
    private Long countAnswer;
    private Long countQuestion;
    private Long countView;
    private List<FavoriteUserTagDto> favoriteUserTagDtoList;

    public UserProfileDto(Long id, Long reputation, Long countAnswer, Long countQuestion, Long countView) {
        this.id = id;
        this.reputation = reputation;
        this.countAnswer = countAnswer;
        this.countQuestion = countQuestion;
        this.countView = countView;
    }

    public UserProfileDto(UserProfileDto userProfileDto, List<FavoriteUserTagDto> favUserTagDtos) {
        this.id = userProfileDto.id;
        this.reputation = userProfileDto.reputation;
        this.countAnswer = userProfileDto.countAnswer;
        this.countQuestion = userProfileDto.countQuestion;
        this.countView = userProfileDto.countView;
        this.favoriteUserTagDtoList = favUserTagDtos;
    }
}
