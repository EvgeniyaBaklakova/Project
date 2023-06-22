package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.question.TagQuestion;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionDtoServiceImp extends PageDtoServiceImpl<QuestionDto> implements QuestionDtoService {
    private final TagDtoDao tagDtoDao;
    private final QuestionDtoDao questionDtoDao;

    public QuestionDtoServiceImp(TagDtoDao tagDtoDao, QuestionDtoDao questionDtoDao, Map<String, PageDtoDao<QuestionDto>> daoMap) {
        super(daoMap);
        this.tagDtoDao = tagDtoDao;
        this.questionDtoDao = questionDtoDao;
    }

    @Override
    public Optional<QuestionDto> getQuestionDtoById(Long id) {
        Optional<QuestionDto> questionDto = questionDtoDao.getQuestionDtoById(id);
        questionDto.ifPresent(dto -> dto.setListTagDto(tagDtoDao.getTagsByQuestionId(id)));
        return questionDto;
    }

    @Override
    @Transactional
    public List<UserProfileQuestionDto> getUserQuestions(Long id) {
        List<UserProfileQuestionDto> userProfileQuestionDtoList = questionDtoDao.getUserQuestions(id);
        return putTagToUserProfileQuestionDto(userProfileQuestionDtoList);

    }

    @Override
    @Transactional
    public List<UserProfileQuestionDto> getUserDeleteQuestions(Long id) {
        List<UserProfileQuestionDto> userProfileQuestionDtoList = questionDtoDao.getUserDeleteQuestions(id);
        return putTagToUserProfileQuestionDto(userProfileQuestionDtoList);
    }

    private List<UserProfileQuestionDto> putTagToUserProfileQuestionDto(List<UserProfileQuestionDto> userProfileQuestionDtoList) {
        List<Long> listQuestionsIds = userProfileQuestionDtoList.stream().map(UserProfileQuestionDto::getQuestionId)
                .collect(Collectors.toList());
        Map<Long, List<TagQuestion>> tagDtoDaoList = tagDtoDao.getTagsByQuestionsIds(listQuestionsIds)
                .stream().collect(Collectors.groupingBy(TagQuestion::getQuestionId));
        userProfileQuestionDtoList.forEach(allQuestionDto -> allQuestionDto.setTagDtoList(tagDtoDaoList.get(allQuestionDto
                .getQuestionId()).stream().map(TagQuestion::getTagDto).collect(Collectors.toList())));
        return userProfileQuestionDtoList;
    }

    @Override
    public PageDto<QuestionDto> getPageDto(PaginationData data) {
        PageDto<QuestionDto> pageDto = super.getPageDto(data);
        List<QuestionDto> questionDtoList = pageDto.getItems();

        pageDto.setItems(applyTagsToQuestions(questionDtoList));
        return pageDto;
    }

    private List<QuestionDto> applyTagsToQuestions(List<QuestionDto> questionDtoList) {
        List<Long> questionIds = questionDtoList.stream().map(QuestionDto::getId).collect(Collectors.toList());
        Map<Long, List<TagQuestion>> questionTagsMap = tagDtoDao.getTagsByQuestionsIds(questionIds).stream().collect(Collectors.groupingBy(TagQuestion::getQuestionId));

        return questionDtoList.stream()
                .map(questionDto -> {
                    List<TagQuestion> questionTags = questionTagsMap.getOrDefault(questionDto.getId(), Collections.emptyList());
                    List<TagDto> tagList = questionTags.stream()
                            .map(TagQuestion::getTagDto)
                            .collect(Collectors.toList());
                    questionDto.setListTagDto(tagList);
                    return questionDto;
                })
                .collect(Collectors.toList());
    }
}