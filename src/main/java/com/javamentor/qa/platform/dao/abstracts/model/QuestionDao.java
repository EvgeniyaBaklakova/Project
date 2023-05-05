package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Question;

public interface QuestionDao extends ReadWriteDao<Question, Long>{
    Question getByTitle(String title);
    boolean isNotExistByTitle(String title);

}
