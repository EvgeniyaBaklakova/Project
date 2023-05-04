package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import com.javamentor.qa.platform.service.abstracts.model.CommentQuestionService;
import org.springframework.stereotype.Service;

@Service
public class CommentQuesionServiceImpl extends ReadWriteServiceImpl<CommentQuestion, Long> implements CommentQuestionService {

    public CommentQuesionServiceImpl(ReadWriteDao<CommentQuestion, Long> readWriteDao) {
        super(readWriteDao);
    }
}
