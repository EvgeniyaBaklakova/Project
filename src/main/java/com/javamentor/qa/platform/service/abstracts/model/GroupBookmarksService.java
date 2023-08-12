package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.GroupBookmark;
import com.javamentor.qa.platform.models.entity.user.User;

import java.util.List;

public interface GroupBookmarksService extends ReadWriteService<GroupBookmark, Long> {

    List<String> getGroupBookMarkByName(User authUser);

}
