package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.AllQuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl extends ReadWriteServiceImpl<Question, Long> implements QuestionService {
    private final QuestionDao questionDao;
    private final TagService tagService;
    private final AllQuestionDtoMapper allQuestionDtoMapper;


    public QuestionServiceImpl(QuestionDao questionDao, TagService tagService) {
        super(questionDao);
        this.questionDao = questionDao;
        this.tagService = tagService;
        this.allQuestionDtoMapper = allQuestionDtoMapper;
    }

    @Override
    @Transactional
    public Question save(Question questionEntity) {
        List<Tag> tagList = tagService.tagsToSet(questionEntity.getTags());
        questionEntity.setTags(tagList);

        if (questionDao.isNotExistByTitle(questionEntity.getTitle())) {
            questionDao.persist(questionEntity);
        }

        return questionDao.getById(questionEntity.getId()).orElse(null);
        //достаём сохраненный вопрос из БД и возвращаем его
    }
}



