package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.Comment;
import com.javamentor.qa.platform.models.entity.user.User;

public interface CommentService extends ReadWriteService<Comment, Long> {

    Comment addComment(Comment comment, User user);
}
