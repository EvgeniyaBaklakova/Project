package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.AllQuestionDto;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionDtoServiceImp implements QuestionDtoService {
    private final TagDtoDao tagDtoDao;
    private final QuestionDtoDao questionDtoDao;
    private final TagDtoDao tagDtoDa;

    public QuestionDtoServiceImp(TagDtoDao tagDtoDao, QuestionDtoDao questionDtoDao, TagDtoDao tagDtoDa) {
        this.tagDtoDao = tagDtoDao;
        this.questionDtoDao = questionDtoDao;
        this.tagDtoDa = tagDtoDa;
    }


    @Override
    public Optional<QuestionDto> getQuestionDtoById(Long id) {
        Optional<QuestionDto> questionDto = questionDtoDao.getQuestionDtoById(id);
        questionDto.ifPresent(dto -> dto.setListTagDto(tagDtoDao.getTagsByQuestionId(id)));
        return questionDto;
    }

    @Override
    @Transactional
    public List<AllQuestionDto> getAllQuestions(User user) {
        List<AllQuestionDto> allQuestionDtoList = questionDtoDao.getAllQuestions(user);
        List<Long> listQuestionsIds = allQuestionDtoList.stream().map(AllQuestionDto::getQuestionId).collect(Collectors.toList());
        List<TagDto> tagDtoDaoList = tagDtoDao.getTagsByQuestionsIds(listQuestionsIds);
        System.out.println(tagDtoDaoList);
    return questionDtoDao.getAllQuestions(user);

    }
}

