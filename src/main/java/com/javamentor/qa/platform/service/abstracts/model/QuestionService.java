package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.dto.AllQuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface QuestionService extends ReadWriteService<Question, Long> {
    Question save(Question questionEntity);

}
