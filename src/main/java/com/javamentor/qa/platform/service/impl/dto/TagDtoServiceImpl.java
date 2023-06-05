package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.tag.IgnoredTagsDto;
import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.models.dto.tag.TagViewDto;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TagDtoServiceImpl extends PageDtoServiceImpl<TagViewDto> implements TagDtoService {

    private final TagDtoDao tagDtoDao;

    public TagDtoServiceImpl(TagDtoDao tagDtoDao, Map<String, PageDtoDao<TagViewDto>> daoMap) {
        super(daoMap);
        this.tagDtoDao = tagDtoDao;
    }

    @Override
    public List<RelatedTagsDto> getAllTen() {
        return tagDtoDao.getAllTen();
    }

    @Override
    public Optional<TagDto> getIgnoredTag(Long userId, Long tagId) {
        return tagDtoDao.getIgnoredTag(userId, tagId);
    }

    @Override
    public Optional<TagDto> getTrackedTag(Long userId, Long tagId) {
        return  tagDtoDao.getTrackedTag(userId, tagId);
    }

    @Override
    public List<IgnoredTagsDto> getIgnoredTags(Long userId) {
        return tagDtoDao.getIgnoredTags(userId);
    }

    @Override
    public PageDto<TagViewDto> getPageDto(PaginationData properties) {
        var pageDto = super.getPageDto(properties);
        return pageDto;
    }
}
