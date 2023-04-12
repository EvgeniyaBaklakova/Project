package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.CommentDao;
import com.javamentor.qa.platform.models.entity.Comment;
import com.javamentor.qa.platform.models.entity.CommentType;
import com.javamentor.qa.platform.service.abstracts.model.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ReadWriteServiceImpl<Comment, Long> implements CommentService {
    private final CommentDao commentDao;

    public CommentServiceImpl(CommentDao commentDao) {
        super(commentDao);
        this.commentDao = commentDao;
    }

    @Override
    public void addComment(Comment comment) {
        if (comment.getText() != null) {
            comment.setCommentType(CommentType.QUESTION);
            commentDao.persist(comment);
        }
    }
}
