package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.UserProfileDto;
import com.javamentor.qa.platform.models.dto.tag.FavoriteUserTagDto;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDtoDaoImpl implements UserDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserDto> getById(long id) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.user.UserDto" +
                "(u.id, u.email, u.fullName, u.imageLink, u.city, " +
                "CAST((SELECT SUM(r.count) FROM Reputation r WHERE r.author.id = u.id) AS java.lang.Long)) " +
                "FROM User u " +
                "WHERE u.id = :id";

        TypedQuery<UserDto> query = entityManager.createQuery(hql, UserDto.class).setParameter("id", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public UserProfileDto getUserProfile(long id) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.UserProfileDto" +
                "(u.id, CAST((SELECT SUM(r.count) FROM Reputation r WHERE r.author.id = u.id) AS java.lang.Long), " +

                "(SELECT COUNT(a.id) AS countAnswer FROM Answer a WHERE a.user.id = u.id), " +
                "(SELECT COUNT(q.id) AS countQuestion FROM Question q WHERE q.user.id = u.id), " +
                "(SELECT COUNT(qv.id) FROM QuestionViewed qv " +
                    "JOIN Question q ON qv.question.id = q.id " +
                    "JOIN Answer a ON a.question.id = q.id " +
                    "WHERE qv.user.id = u.id)) " +

                "FROM User u WHERE u.id = :id";

        UserProfileDto upd = entityManager.createQuery(hql, UserProfileDto.class).setParameter("id", id)
                .getSingleResult();
        upd.setTagDtoList(getFavoriteUserTags(id));
        return upd;
    }

    private List<FavoriteUserTagDto> getFavoriteUserTags(Long id) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.tag.FavoriteUserTagDto(" +
                "CAST(t.id AS java.lang.Integer), t.name, " +
                "CAST(" +
                "(" +
                "(SELECT COUNT(q.id) AS countMessage FROM t.questions q) + " +
                "(SELECT(SELECT COUNT(a.id) FROM q.answers a) AS countMessage FROM t.questions q)" +
                ")" +
                "AS java.lang.Long) as countMessage" +
                ") " +

                "FROM Tag t " +
                "JOIN User u ON u.id = (SELECT q.user.id FROM t.questions q) " +
                "WHERE u.id = :id " +
                "ORDER BY countMessage DESC";

        return entityManager.createQuery(hql, FavoriteUserTagDto.class)
                .setParameter("id", id)
                .setMaxResults(15).getResultList();
    }
}