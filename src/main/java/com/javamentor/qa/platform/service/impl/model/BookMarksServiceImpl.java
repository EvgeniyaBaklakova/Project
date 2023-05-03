package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.BookMarksDao;
import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.models.entity.BookMarks;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.BookMarksService;
import com.javamentor.qa.platform.webapp.controllers.util.QuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookMarksServiceImpl extends ReadWriteServiceImpl<BookMarks, Long> implements BookMarksService {
    private final BookMarksDao bookMarksDao;
    private final QuestionDao questionDao;

    @Autowired
    public BookMarksServiceImpl(BookMarksDao bookMarksDao, QuestionDao questionDao) {
        super(bookMarksDao);
        this.bookMarksDao = bookMarksDao;
        this.questionDao = questionDao;
    }

    @Transactional
    @Override
    public void addBookMarks(User user, Long questionId) {
        BookMarks bookMarks = new BookMarks();
        Optional<Question> question = questionDao.getById(questionId);
        if (question.isEmpty()) {
            throw new QuestionNotFoundException();
        }
        bookMarks.setUser(user);
        bookMarks.setQuestion(question.get());
        bookMarksDao.persist(bookMarks);
        }
    }


