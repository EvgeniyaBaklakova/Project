package com.javamentor.qa.platform.dao.impl.pagination;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.user.UserDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("UserPageDtoDaoByVoteImpl")
@SuppressWarnings("unchecked")
public class UserPageDtoDaoByVoteImpl implements PageDtoDao<UserDto> {

    @PersistenceContext
    private EntityManager entityManager;
    private String filter;

    @Override
    public List<UserDto> getItems(PaginationData properties) {
//        int itemsOnPageParam = (int) properties.getItemsOnPage();
//        int itemsPositionParam = (int) properties.getCurrentPage() * itemsOnPageParam - itemsOnPageParam;
//
//        return entityManager.createQuery(
//                        "SELECT new com.javamentor.qa.platform.models.dto.user.UserDto" +
//                                "(u.id, u.email, u.fullName,  u.imageLink, u.city," +
//                                "cast(coalesce((" +
//                                "       select sum(r.count) from Reputation r " +
//                                "       where r.author.id=u.id)," +
//                                "0) as integer )) " +
//                                "FROM User u " +
//                                "LEFT JOIN VoteQuestion vq1 " +
//                                "WITH vq1.vote = :up AND vq1.user.id = u.id " +
//
//                                "LEFT JOIN VoteQuestion vq2 " +
//                                "WITH vq2.vote = :down AND vq2.user.id = u.id " +
//
//                                "LEFT JOIN VoteAnswer va1 " +
//                                "WITH va1.vote = :up AND va1.user.id = u.id " +
//
//                                "LEFT JOIN  VoteAnswer va2 " +
//                                "WITH va2.vote = :down AND va2.user.id = u.id " +
//                                "GROUP BY u.id ORDER BY (count(vq1)-count(vq2)+count(va1)-count(va2)) DESC ",
//                        UserDto.class)
//                .setParameter("up", VoteType.UP_VOTE)
//                .setParameter("down", VoteType.DOWN_VOTE)
//                .setMaxResults(itemsOnPageParam)
//                .setFirstResult(itemsPositionParam)
//                .getResultList();
        filter = properties.getFilter();
        int itemsOnPage = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage()-1) * itemsOnPage;
        String hql = "SELECT u.id, u.email, u.fullName, u.imageLink, u.city, " +
                "SUM(CASE WHEN r.count = NULL THEN 0 ELSE r.count END) AS reputation," +
                "SUM(CASE WHEN (r.type = :voteAns OR r.type = :voteQuest) AND r.count > 0 THEN 1" +
                "WHEN (r.type = :voteAns OR r.type = :voteQuest) AND r.count < 0 THEN -1 ELSE 0 END) AS voteOrder " +
                "FROM User u LEFT JOIN Reputation r ON r.author.id = u.id ";

        if (filter != null) {
            hql += " where (upper(u.nickname) like upper(:filter) " +
                    "or upper(u.email) like upper(:filter) " +
                    "or upper(u.fullName) like upper(:filter)) " +
                    "GROUP BY u.id order by voteOrder DESC, u.id ASC";
            return entityManager.createQuery(hql)
                    .setParameter("filter", "%" + filter + "%")
                    .setParameter("voteAns", ReputationType.VoteAnswer)
                    .setParameter("voteQuest", ReputationType.VoteQuestion)
                    .setFirstResult(offset)
                    .setMaxResults(itemsOnPage)
                    .unwrap(org.hibernate.query.Query.class)
                    .setResultTransformer(new ResultTransformer() {
                        @Override
                        public UserDto transformTuple(Object[] objects, String[] strings) {
                            return new UserDto(
                                    (Long) objects[0],
                                    (String) objects[1],
                                    (String) objects[2],
                                    (String) objects[3],
                                    (String) objects[4],
                                    (Long) objects[5]);
                        }

                        @Override
                        public List transformList(List list) {
                            return list;
                        }
                    })
                    .getResultList();
        }
        hql += " GROUP BY u.id ORDER BY voteOrder DESC, u.id ASC";
        return entityManager.createQuery(hql)
                .setParameter("voteAns", ReputationType.VoteAnswer)
                .setParameter("voteQuest", ReputationType.VoteQuestion)
                .setFirstResult(offset)
                .setMaxResults(itemsOnPage)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public UserDto transformTuple(Object[] objects, String[] strings) {
                        return new UserDto(
                                (Long) objects[0],
                                (String) objects[1],
                                (String) objects[2],
                                (String) objects[3],
                                (String) objects[4],
                                (Long) objects[5]);
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                })
                .getResultList();

    }

    @Override
    public Long getTotalResultCount(Map<String,Object> properties) {
//        Query query = entityManager.createQuery("SELECT COUNT(p) FROM User p ");
//        return Long.valueOf(query.getSingleResult().toString());
        if (filter != null) {
            return (Long) entityManager
                    .createQuery("select count(u.id) from User u " +
                            "where u.isDeleted = false  " +
                            "and (upper(u.nickname) like upper(:filter) " +
                            "or upper(u.email) like upper(:filter) " +
                            "or upper(u.fullName) like upper(:filter))")
                    .setParameter("filter", "%" + filter + "%")
                    .getSingleResult();
        }
        return (Long) entityManager
                .createQuery("select count(u.id) from User u where u.isDeleted = false")
                .getSingleResult();
    }
}
