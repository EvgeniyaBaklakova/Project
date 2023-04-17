package com.javamentor.qa.platform.models.dto.answer;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class AnswerDto {
    private Long id;
    private LocalDateTime persistDateTime;
    private LocalDateTime updateDateTime;
    private Question question;
    private User user;
    private String htmlBody;
    private Boolean isHelpful;
    private Boolean isDeleted;
    private Boolean isDeletedByModerator;
    private LocalDateTime dateAcceptTime;
    private List<CommentAnswer> commentAnswers;
    private List<VoteAnswer> voteAnswers = new ArrayList<>();
}
