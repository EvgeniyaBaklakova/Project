package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface QuestionDao extends ReadWriteDao<Question, Long>{
    Question getByTitle(String title);
    boolean isNotExistByTitle(String title);

    List<Question> getQuestionsByUserId(@AuthenticationPrincipal User user);
}
