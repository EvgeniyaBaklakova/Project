package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.chat.MessageStar;
import com.javamentor.qa.platform.service.abstracts.model.MessageStarService;
import org.springframework.stereotype.Service;


@Service
public class MessageStarServiceImpl extends ReadWriteServiceImpl<MessageStar, Long> implements MessageStarService {

    public MessageStarServiceImpl(ReadWriteDao<MessageStar, Long> readWriteDao) {
        super(readWriteDao);
    }
}
