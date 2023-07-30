package com.javamentor.qa.platform.dao.impl.pagination;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.MessageDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

@Repository("MessageDtoDaoByPersistDateImpl")
public class MessageDtoDaoByPersistDateImpl implements PageDtoDao<MessageDto> {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<MessageDto> getItems(PaginationData properties) {

        int items = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * items;
        Long chatId = (Long) properties.getProps().get("chat_id");


        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.MessageDto(m.id, m.message, " +
                "u.nickname, " +
                "u.id," +
                "u.imageLink, " +
                "m.persistDate) FROM Message m " +
                "JOIN User u ON m.userSender.id = u.id  " +
                "JOIN SingleChat sc ON m.chat.id = sc.chat.id " +
                "WHERE m.chat.id=:chat_id";

        TypedQuery<MessageDto> query = entityManager.createQuery(hql, MessageDto.class)
                .setParameter("chat_id", chatId)
                .setMaxResults(items)
                .setFirstResult(offset);
        return query.getResultList();


    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {

        return  entityManager
                .createQuery("SELECT count(m.id) FROM Message m " +
                        "JOIN User u ON m.userSender.id = u.id " +
                        "JOIN SingleChat sc ON m.chat.id = sc.chat.id",
                        Long.class)
                .getSingleResult();
    }
}
