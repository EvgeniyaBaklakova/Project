package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.ChatDtoDao;
import com.javamentor.qa.platform.models.dto.chat.ChatDto;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.models.dto.chat.SingleChatDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ChatDtoDaoImpl implements ChatDtoDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ChatDto> getChatDtoSingleByChatName(String chatName, Long id) {
        String hqlSingle = "SELECT NEW com.javamentor.qa.platform.models.dto.chat.ChatDto" +
                "(c.id, " +
                "(SELECT u.nickname from User u WHERE u.nickname = :chatName), " +
                "(SELECT u.imageLink from User u WHERE u.nickname = :chatName), " +
                "(SELECT m.message from Message m WHERE m.chat.id = c.id AND m.persistDate = " +
                "(SELECT MAX(m.persistDate) FROM m WHERE m.chat.id = c.id)), " +
                "(SELECT MAX(m.persistDate) FROM Message m WHERE m.chat.id = c.id))" +
                "FROM SingleChat s " +
                "JOIN s.chat c " +
                "WHERE (SELECT u.nickname FROM User u WHERE u.id = :id) <> :chatName " +
                "AND (s.useTwo.nickname = :chatName OR s.userOne.nickname = :chatName) " +
                "AND (s.userOne.id = :id OR s.useTwo.id = :id)";

        return entityManager.createQuery(hqlSingle, ChatDto.class)
                .setParameter("id", id)
                .setParameter("chatName", chatName)
                .getResultList();
    }

    @Override
    public List<ChatDto> getChatDtoGroupByChatName(String chatName, Long id) {
        String hqlGroup = "SELECT NEW com.javamentor.qa.platform.models.dto.chat.ChatDto" +
                "(c.id, c.title, g.image, " +
                "(SELECT m.message FROM Message m WHERE m.chat.id = c.id AND m.persistDate = " +
                "(SELECT MAX(m.persistDate) FROM m WHERE m.chat.id = c.id)), " +
                "(SELECT MAX(m.persistDate) FROM Message m WHERE m.chat.id = c.id))" +
                "FROM GroupChat g " +
                "JOIN g.chat c " +
                "JOIN g.users u " +
                "WHERE u.id = :id " +
                "AND g.chat.title = :chatName";

        return entityManager.createQuery(hqlGroup, ChatDto.class)
                .setParameter("id", id)
                .setParameter("chatName", chatName)
                .getResultList();
    }

    @Override
    public List<SingleChatDto> getSingleChatDtoByUserId(Long id) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.chat.SingleChatDto" +
                "(c.id, " +
                "(SELECT u.nickname from User u WHERE (u.id = s.userOne.id OR u.id = s.useTwo.id) AND u.id <> :id), " +
                "(SELECT u.imageLink from User u WHERE (u.id = s.userOne.id OR u.id = s.useTwo.id) AND u.id <> :id), " +
                "(SELECT m.message from Message m WHERE m.chat.id = c.id AND m.persistDate = " +
                "(SELECT MAX(m.persistDate) FROM m WHERE m.chat.id = c.id)), " +
                "(SELECT MAX(m.persistDate) FROM Message m WHERE m.chat.id = c.id))" +
                "FROM SingleChat s " +
                "JOIN s.chat c " +
                "WHERE (s.userOne.id = :id OR s.useTwo.id = :id)";

        return entityManager.createQuery(hql, SingleChatDto.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<GroupChatDto> getGroupChatDtoByUserId(Long id) {
        String hql = "SELECT NEW com.javamentor.qa.platform.models.dto.chat.GroupChatDto" +
                "(c.id, c.title, g.image, (SELECT m.message FROM Message m WHERE m.chat.id = c.id AND m.persistDate = " +
                "(SELECT MAX(m.persistDate) FROM m WHERE m.chat.id = c.id)), c.persistDate)" +
                "FROM GroupChat g " +
                "JOIN g.chat c " +
                "JOIN g.users u " +
                "WHERE u.id = :id";

        return entityManager.createQuery(hql, GroupChatDto.class)
                .setParameter("id", id)
                .getResultList();
    }
}
