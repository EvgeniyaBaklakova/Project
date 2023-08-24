package com.javamentor.qa.platform.dao.impl.model;


import com.javamentor.qa.platform.dao.abstracts.model.ChatDao;
import com.javamentor.qa.platform.dao.abstracts.model.MessageStarDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.MessageStar;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class ChatDaoImpl extends ReadWriteDaoImpl<Chat, Long> implements ChatDao {

    @PersistenceContext
    private EntityManager entityManager;

    public boolean existUser(Long userId, Long messageId) {
        Query query = (Query) entityManager.createQuery("FROM Message m, GroupChat gc, SingleChat sc join fetch m.chat join fetch m.id where m.id = :messageId" +
                        " if m.chat.chatType = 'GROUP', join fetch gc.users where gc.users.user.id = :userId" +
                        " else if m.chat.chatType = 'SINGLE' join fetch sc.userOne uo join fetch sc.useTwo ut where sc.userOne.id = :userId or sc.useTwo.id = :userId")
                .setParameter("userId", userId);
        return query.getSingleResult() != null;
    }
/*    public boolean existUserInGroupChat (Long userId) {
        Query query = (Query) entityManager.createQuery("SELECT gc.users FROM GroupChat gc where gc.users in :userMessageStar")
                .setParameter("userMessageStar", userMessageStar);
        return query != null;
    }

    public boolean existUserInSinglChat (Long userId) {
        Query query = (Query) entityManager.createQuery("SELECT sc FROM SingleChat sc join fetch sc.userOne uo join fetch sc.useTwo ut" +
                        " where sc.userOne.id = :userId or sc.useTwo.id = :userId")
                .setParameter("userId", userId);
        return query != null;
    }*/
}