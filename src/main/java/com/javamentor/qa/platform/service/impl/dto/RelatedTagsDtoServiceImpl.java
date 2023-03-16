package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.RelatedTagsDtoDao;
import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import com.javamentor.qa.platform.service.abstracts.dto.RelatedTagsDtoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatedTagsDtoServiceImpl implements RelatedTagsDtoService {

    private final RelatedTagsDtoDao relatedTagsDtoDao;

    public RelatedTagsDtoServiceImpl(RelatedTagsDtoDao relatedTagsDtoDao) {
        this.relatedTagsDtoDao = relatedTagsDtoDao;
    }

    @Override
    public List<RelatedTagsDto> getAllTen() {
        return relatedTagsDtoDao.getAllTen();
    }
}
