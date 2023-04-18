package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.BookMarksDao;
import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.models.entity.BookMarks;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.webapp.controllers.util.UserNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BookMarksDaoImpl extends ReadWriteDaoImpl<BookMarks, Long> implements BookMarksDao {
    private final QuestionDao questionDao;
    private final UserDao userDao;

    public BookMarksDaoImpl(QuestionDao questionDao, UserDao userDao) {
        this.questionDao = questionDao;
        this.userDao = userDao;
    }

    public void addBookmarks(String email, Long questionId) {
        BookMarks bookMarks = new BookMarks();
        Optional<User> user = userDao.getUserByEmail(email);
        if(user.isEmpty()){
            throw new UserNotFoundException();
        }
        Optional<Question> question = questionDao.getById(questionId);
        if(question.isEmpty()){
            throw new RuntimeException();
        }
        bookMarks.setUser(user.get());
        bookMarks.setQuestion(question.get());
        persist(bookMarks);
    };

}
