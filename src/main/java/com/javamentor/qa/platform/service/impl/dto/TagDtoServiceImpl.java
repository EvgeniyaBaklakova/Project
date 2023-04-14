package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagDtoServiceImpl implements TagDtoService {

    private final TagDtoDao tagDtoDao;

    public TagDtoServiceImpl(TagDtoDao tagDtoDao) {
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
}
