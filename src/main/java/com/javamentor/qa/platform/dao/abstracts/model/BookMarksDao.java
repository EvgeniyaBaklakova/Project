package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.BookMarks;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface BookMarksDao extends ReadWriteDao <BookMarks, Long>{
 void addBookmarks(@AuthenticationPrincipal User user, Long questionId);
}
