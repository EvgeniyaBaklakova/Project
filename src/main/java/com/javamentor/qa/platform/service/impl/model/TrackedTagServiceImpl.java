package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.service.abstracts.model.TrackedTagService;
import org.springframework.stereotype.Service;

@Service
public class TrackedTagServiceimpl extends ReadWriteServiceImpl<TrackedTag, Long> implements TrackedTagService {
    public TrackedTagServiceimpl(ReadWriteDao<TrackedTag, Long> readWriteDao) {
        super(readWriteDao);
    }
}
