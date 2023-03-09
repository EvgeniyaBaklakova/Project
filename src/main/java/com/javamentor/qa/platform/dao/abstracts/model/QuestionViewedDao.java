package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.QuestionViewed;

import java.util.Optional;

public interface QuestionViewedDao extends ReadWriteDao<QuestionViewed, Long>{

    boolean isViewed(Long userId, Long questionId);

    Optional<QuestionViewed> getByUserQuestionIds(Long userId, Long questionId);

}
