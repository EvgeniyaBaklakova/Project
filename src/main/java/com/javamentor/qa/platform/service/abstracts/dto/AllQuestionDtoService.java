package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.AllQuestionDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface AllQuestionDtoService {
    List<AllQuestionDto> getAllQuestions(@AuthenticationPrincipal User user);
}
