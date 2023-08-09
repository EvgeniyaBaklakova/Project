package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.GroupBookmarksDao;
import com.javamentor.qa.platform.models.entity.GroupBookmark;
import com.javamentor.qa.platform.service.abstracts.model.GroupBookmarksService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GroupBookmarksServiceImpl extends ReadWriteServiceImpl<GroupBookmark, Long> implements GroupBookmarksService {

    private final GroupBookmarksDao groupBookmarksDao;

    public GroupBookmarksServiceImpl(GroupBookmarksDao groupBookmarksDao) {
        super(groupBookmarksDao);
        this.groupBookmarksDao = groupBookmarksDao;
    }

    @Transactional
    @Override
    public List<String> getGroupBookMarkByName() {

        List<String> titles = new ArrayList<>();
        List<GroupBookmark> bookmarks = groupBookmarksDao.getAll();
        for (GroupBookmark bookmark : bookmarks) {
            String title = bookmark.getTitle();
            titles.add(title);
        }
        return titles;
    }
}
