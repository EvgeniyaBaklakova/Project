package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.RelatedTagsDtoDao;
import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RelatedTagsDtoDaoImpl implements RelatedTagsDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RelatedTagsDto> getAllTen() {
        String hql = "FROM Tag INNER JOIN Question";
        //String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto(t.id, t.name, COUNT(q.tags)) FROM Tag t INNER JOIN Question q";
        return entityManager.createQuery(hql).getResultList();
    }
}
