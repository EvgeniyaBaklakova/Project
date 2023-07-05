package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.tag.FavoriteUserTagDto;

import java.util.List;

public interface FavoriteUserTagDtoDao {
    List<FavoriteUserTagDto> getUserFavoriteTags(Integer id);
}
