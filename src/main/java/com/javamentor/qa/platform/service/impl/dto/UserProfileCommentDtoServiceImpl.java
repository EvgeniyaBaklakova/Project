package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserProfileCommentDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.service.abstracts.dto.UserProfileCommentDtoService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserProfileCommentDtoServiceImpl extends PageDtoServiceImpl<UserProfileCommentDto> implements UserProfileCommentDtoService {

    public UserProfileCommentDtoServiceImpl(Map<String, PageDtoDao<UserProfileCommentDto>> daoMap) {
        super(daoMap);
    }

    @Override
    public PageDto<UserProfileCommentDto> getPageDto(PaginationData properties) {
        return super.getPageDto(properties);
    }
}
