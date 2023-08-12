package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.GroupBookmarksDao;
import com.javamentor.qa.platform.models.entity.GroupBookmark;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.GroupBookmarksService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupBookmarksServiceImpl extends ReadWriteServiceImpl<GroupBookmark, Long> implements GroupBookmarksService {

    private final GroupBookmarksDao groupBookmarksDao;

    public GroupBookmarksServiceImpl(GroupBookmarksDao groupBookmarksDao) {
        super(groupBookmarksDao);
        this.groupBookmarksDao = groupBookmarksDao;
    }


    @Override
    public List<String> getGroupBookMarkByName(User authUser) {
        return groupBookmarksDao.getGroupBookMarkByName(authUser);
    }
}
