package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.user.User;

public interface BookMarksService {
    void addBookMarks (User user, Long questionId);

}
