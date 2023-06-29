package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.GroupBookmarksDao;
import com.javamentor.qa.platform.models.entity.GroupBookmark;
import org.springframework.stereotype.Repository;

@Repository
public class GroupBookmarksDaoImpl extends ReadWriteDaoImpl<GroupBookmark, Long> implements GroupBookmarksDao {
}
