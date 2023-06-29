package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.BookMarks;
import com.javamentor.qa.platform.models.entity.GroupBookmark;
import com.javamentor.qa.platform.models.entity.user.User;

public interface BookMarksService extends ReadWriteService<BookMarks, Long> {
    void addBookMarks (User user, Long questionId);

}
