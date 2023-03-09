package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.PaginationDao;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("allQuestions")
public class QuestionDtoPaginationDaoImpl implements PaginationDao<T> {

    Query query;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int getCountOfAllItems(Map<String, Object> parameters) {
        Long count = entityManager
                .createQuery("SELECT count(question) FROM Question question WHERE question.isDeleted = false", Long.class)
                .getSingleResult();
        return count.intValue();
    }

    @Override
    @SuppressWarnings("deprecation")
    public List<T> getItems(Map<String, Object> parameters) {

        return query.getResultList();
    }
}