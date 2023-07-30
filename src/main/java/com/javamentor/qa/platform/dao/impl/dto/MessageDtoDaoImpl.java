package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.MessageDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.MessageDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.Optional;

@Repository
public class MessageDtoDaoImpl implements MessageDtoDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<MessageDto> getMessageDtoByChatId(Long chatId) {

        TypedQuery query = entityManager.createQuery("SELECT NEW com.javamentor.qa.platform.models.dto.MessageDto(m.id, m.message, " +
                "u.nickname, " +
                "u.id," +
                "u.imageLink, " +
                "m.persistDate) FROM Message m " +
                "JOIN User u ON m.userSender.id = u.id " +
                "JOIN SingleChat sc ON m.chat.id = sc.chat.id  " +
                "WHERE m.chat.id=:chat_id", MessageDto.class).setParameter("chat_id", chatId);

        return SingleResultUtil.getSingleResultOrNull(query);

    }


}
