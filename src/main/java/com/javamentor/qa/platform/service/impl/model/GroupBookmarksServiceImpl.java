package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.GroupBookmarksDao;
import com.javamentor.qa.platform.models.entity.GroupBookmark;
import com.javamentor.qa.platform.service.abstracts.model.GroupBookmarksService;
import org.springframework.stereotype.Service;

@Service
public class GroupBookmarksServiceImpl extends ReadWriteServiceImpl<GroupBookmark, Long> implements GroupBookmarksService {
    public GroupBookmarksServiceImpl(GroupBookmarksDao groupBookmarksDao) {
        super(groupBookmarksDao);
    }
}
