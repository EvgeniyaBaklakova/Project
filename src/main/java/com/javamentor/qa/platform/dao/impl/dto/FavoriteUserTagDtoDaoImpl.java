package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.FavoriteUserTagDtoDao;
import com.javamentor.qa.platform.models.dto.tag.FavoriteUserTagDto;
import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class FavoriteUserTagDtoDaoImpl implements FavoriteUserTagDtoDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<FavoriteUserTagDto> getFavoriteUserTags(Integer id) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.tag.FavoriteUserTagDto(" +
                "t.id, t.name, " +
                    "SUM((SELECT COUNT(a.id) AS countAnswer FROM Answer a WHERE a.user.id = t.id), " +
                        "(SELECT COUNT(q.id) AS countQuestion FROM Question q WHERE q.user.id = t.id))) " +
                "FROM Tag t WHERE t.id = :id";
        return entityManager.createQuery(hql, FavoriteUserTagDto.class).setParameter("id", id).getResultList();
    }
}
