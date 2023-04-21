package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AllQuestionDao;
import com.javamentor.qa.platform.models.dto.user.AllQuestionDto;
import org.springframework.stereotype.Repository;

@Repository
public class AllQuestionDaoImpl extends ReadWriteDaoImpl<AllQuestionDto, Long> implements AllQuestionDao {
}
