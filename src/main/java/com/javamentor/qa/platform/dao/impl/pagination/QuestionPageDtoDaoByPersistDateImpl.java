package com.javamentor.qa.platform.dao.impl.pagination;

import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("QuestionPageDtoDaoByPersistDateImpl")
public class QuestionPageDtoDaoByPersistDateImpl extends QuestionPageDtoDaoAllImpl {
    @Override
    public List<QuestionDto> getItems(PaginationData properties) {
        String hql = getHqlToGetQuestions() + ' ' +
                "GROUP BY q.id, q.user.fullName, q.user.imageLink " +
                "ORDER BY q.persistDateTime DESC";
        return getQueryResult(properties, hql);
    }
}
