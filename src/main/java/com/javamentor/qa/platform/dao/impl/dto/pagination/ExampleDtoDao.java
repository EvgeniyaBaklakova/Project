package com.javamentor.qa.platform.dao.impl.dto.pagination;

import com.javamentor.qa.platform.dao.abstracts.dto.PageDtoDao;
import com.javamentor.qa.platform.models.dto.user.UserDtoExample;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Repository
public class ExampleDtoDao implements PageDtoDao<UserDtoExample> {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<UserDtoExample> getItems(Integer currentPage, Integer itemsOnPage, Map<String, Object> parameters) {
        int page = (int) parameters.get("currentPageNumber");
        int items = (int) parameters.get("itemsOnPage");
        Query query = entityManager.createQuery(
                "select new com.javamentor.qa.platform.models.dto.UserDtoExample (" +
                        "u.id, " +
                        "u.email, " +
                        "u.fullName, " +
                        "u.imageLink, " +
                        "u.city, " +
                        "coalesce(CAST(sum(rep.count) as Long), 0)) " +
                        "from User u left join Reputation rep on u.id = rep.author.id " +
                        "group by u.id,rep.author.id " +
                        "order by sum(rep.count) desc, u.id", UserDtoExample.class);
        query.setFirstResult((page -1) * itemsOnPage);
        query.setMaxResults(items);
        return query.getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> parameters) {
        Query queryTotal = entityManager.createQuery
                ("Select count(user.id) from User user");
        return Long.valueOf(((Long) queryTotal.getSingleResult()).intValue());
    }
}
