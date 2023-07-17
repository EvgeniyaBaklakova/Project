package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.GroupChatDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class GroupChatDtoDaoImpl implements GroupChatDtoDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<GroupChatDto> getById(Long id) {

        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.chat.GroupChatDto" +
                "(c.id, c.title, (SELECT MAX(m.message) fROM Message m WHERE m.chat.id = c.id), g.image, c.persistDate)" +
                "FROM GroupChat g JOIN g.chat c WHERE g.id = :id";

        TypedQuery<GroupChatDto> query = entityManager.createQuery(hql, GroupChatDto.class)
                .setParameter("id", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
