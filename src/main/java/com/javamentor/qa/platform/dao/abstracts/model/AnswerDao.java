package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;

import java.util.Map;
import java.util.Optional;

public interface AnswerDao extends ReadWriteDao<Answer, Long> {

    Long countAnswerOfWeek(Long id);

    Optional<Map<String, Object>> getAnswerAndAuthorId(Long answerId);

    Optional<Answer> getAnswerByAuthorId(Long authorId);
}
