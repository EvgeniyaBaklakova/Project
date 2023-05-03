package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.BookMarks;
import com.javamentor.qa.platform.models.entity.user.User;

public interface BookMarksDao extends ReadWriteDao <BookMarks, Long>{
 void addBookmarks(User user, Long questionId);
}
