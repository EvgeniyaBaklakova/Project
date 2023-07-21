package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.GroupChatDtoDao;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class GroupChatDtoDaoImpl implements GroupChatDtoDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GroupChatDto> getByUserId(Long id) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.chat.GroupChatDto" +
                "(c.id, c.title, (SELECT m.message FROM Message m where m.chat.id = c.id and m.persistDate = " +
                "(select max(m.persistDate) from m where m.chat.id = c.id)), g.image, c.persistDate)" +
                "FROM GroupChat g " +
                "JOIN g.chat c " +
                "JOIN g.users u " +
                "WHERE u.id = :id";

        return entityManager.createQuery(hql, GroupChatDto.class)
                .setParameter("id", id)
                .getResultList();
    }
}
