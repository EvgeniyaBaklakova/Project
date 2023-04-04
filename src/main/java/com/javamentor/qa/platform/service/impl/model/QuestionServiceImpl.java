package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.exception.ApiRequestException;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionServiceImpl extends ReadWriteServiceImpl<Question, Long> implements QuestionService {
    private final QuestionDao questionDao;
    private final TagService tagService;

    public QuestionServiceImpl(QuestionDao questionDao, TagService tagService) {
        super(questionDao);
        this.questionDao = questionDao;
        this.tagService = tagService;
    }

    @Override
    @Transactional
    public Question save(Question questionEntity) {
        List<Tag> tagList = tagService.saveTags(questionEntity.getTags());
        questionEntity.setTags(tagList);

        if (questionDao.isNotExistByTitle(questionEntity.getTitle())) {
            questionDao.persist(questionEntity);
        } else {
            throw new ApiRequestException("Вопрос с таким названием уже существует");
        }

        return questionDao.getByTitle(questionEntity.getTitle());
        //достаём сохраненный вопрос из БД и возвращаем его
    }
}
