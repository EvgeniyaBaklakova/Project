package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.GroupChatDtoDao;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class GroupChatDtoDaoImpl implements GroupChatDtoDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<GroupChatDto> getByUser(User user) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.chat.GroupChatDto" +
                "(c.id, c.title, (SELECT MAX(m.message) fROM Message m WHERE m.chat.id = c.id), (SELECT g.image FROM GroupChat g WHERE g.chat.id = c.id), c.persistDate)" +
                "FROM UserChatPin u " +
                "JOIN u.chat c " +
                "WHERE u.user = :user AND u.chat.chatType = 1";

        return entityManager.createQuery(hql, GroupChatDto.class)
                .setParameter("user", user)
                .getResultList();
    }
}
