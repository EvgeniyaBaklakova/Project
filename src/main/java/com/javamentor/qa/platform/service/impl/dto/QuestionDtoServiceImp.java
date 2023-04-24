package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionDtoServiceImp implements QuestionDtoService {
    private final TagDtoDao tagDtoDao;
    private final QuestionDtoDao questionDtoDao;

    public QuestionDtoServiceImp(TagDtoDao tagDtoDao, QuestionDtoDao questionDtoDao) {
        this.tagDtoDao = tagDtoDao;
        this.questionDtoDao = questionDtoDao;
    }


    @Override
    public Optional<QuestionDto> getQuestionDtoById(Long id) {
        Optional<QuestionDto> questionDto = questionDtoDao.getQuestionDtoById(id);
        questionDto.ifPresent(dto -> dto.setListTagDto(tagDtoDao.getTagsByQuestionId(id)));
        return questionDto;
    }
}

