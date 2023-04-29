package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface AnswerDao extends ReadWriteDao<Answer, Long> {

    Long countAnswerOfWeek(@AuthenticationPrincipal User user);


}
