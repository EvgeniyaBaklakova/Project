package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.GroupChatDtoDao;
import com.javamentor.qa.platform.models.dto.chat.ChatDto;
import com.javamentor.qa.platform.models.dto.chat.GroupChatDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Override
    public List<ChatDto> getChatDtoByChatName(String chatName, User user) {
        String hqlSingle = "select new com.javamentor.qa.platform.models.dto.chat.ChatDto" +
                "(c.id, " +
                "(select u.nickname from User u where u.nickname = :chatName), " +
                "(select u.imageLink from User u where u.nickname = :chatName), " +
                "(select m.message from Message m where m.chat.id = c.id and m.persistDate = " +
                "(select max(m.persistDate) from m where m.chat.id = c.id)), " +
                "(select max(m.persistDate) from Message m where m.chat.id = c.id))" +
                "from SingleChat s " +
                "join s.chat c " +
                "where (s.userOne.id = :id or s.useTwo.id = :id) " +
                "and (s.useTwo.nickname = :chatName or s.userOne.nickname = :chatName)";

        List<ChatDto> singleChatList = (Objects.equals(user.getNickname(), chatName)) ? new ArrayList<>() :
                entityManager.createQuery(hqlSingle, ChatDto.class)
                        .setParameter("id", user.getId())
                        .setParameter("chatName", chatName)
                        .getResultList();

        String hqlGroup = "select new com.javamentor.qa.platform.models.dto.chat.ChatDto" +
                "(c.id, c.title, g.image, " +
                "(select m.message from Message m where m.chat.id = c.id and m.persistDate = " +
                "(select max(m.persistDate) from m where m.chat.id = c.id)), " +
                "(select max(m.persistDate) from Message m where m.chat.id = c.id))" +
                "from GroupChat g " +
                "join g.chat c " +
                "join g.users u " +
                "where u.id = :id " +
                "and g.chat.title = :chatName";

        List<ChatDto> groupChatList = entityManager.createQuery(hqlGroup, ChatDto.class)
                .setParameter("id", user.getId())
                .setParameter("chatName", chatName)
                .getResultList();

        List<ChatDto> result = new ArrayList<>();
        result.addAll(singleChatList);
        result.addAll(groupChatList);

        return result.stream().sorted(Comparator.comparing(ChatDto::getPersistDateTimeLastMessage)).collect(Collectors.toList());
    }
}
