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
    public List<FavoriteUserTagDto> getUserFavoriteTags(Integer id) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.tag.FavoriteUserTagDto(" +
                "q.id, q.title, q.persistDateTime," +
                    "(SELECT COUNT(a.id) AS countAnswer " +
                    "FROM Answer a " +
                    "WHERE a.question.id = q.id)) " +
                "FROM Question q WHERE user.id = :id";
        return entityManager.createQuery(hql, UserProfileQuestionDto.class).setParameter("id", id).getResultList();
    }
}
