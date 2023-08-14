package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.GroupBookmark;
import com.javamentor.qa.platform.models.entity.user.User;

import java.util.List;

public interface GroupBookmarksDao extends ReadWriteDao<GroupBookmark, Long> {
    List<String> getGroupBookMarkByName(Long authUserId);
}
