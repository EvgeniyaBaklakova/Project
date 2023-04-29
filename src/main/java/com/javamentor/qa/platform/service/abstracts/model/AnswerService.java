package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface AnswerService extends ReadWriteService<Answer, Long> {
    Long countAnswerOfWeek(@AuthenticationPrincipal User user);
}
