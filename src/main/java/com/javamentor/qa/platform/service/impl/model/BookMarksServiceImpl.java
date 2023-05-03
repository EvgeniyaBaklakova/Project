package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.BookMarksDao;
import com.javamentor.qa.platform.models.entity.BookMarks;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.BookMarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookMarksServiceImpl extends ReadWriteServiceImpl<BookMarks, Long> implements BookMarksService {
    private final BookMarksDao bookMarksDao;

    @Autowired
    public BookMarksServiceImpl(BookMarksDao bookMarksDao) {
        super(bookMarksDao);
        this.bookMarksDao = bookMarksDao;
    }

    @Transactional
    @Override
    public void addBookMarks(User user, Long questionId) {
        bookMarksDao.addBookmarks(user, questionId);
    }

}
