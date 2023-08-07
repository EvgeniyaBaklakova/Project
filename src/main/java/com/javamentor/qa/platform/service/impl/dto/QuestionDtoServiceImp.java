package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.models.dto.tag.UserProfileTagDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.question.TagQuestion;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public PageDto<QuestionDto> getPageDto(PaginationData properties) {
        PageDto<QuestionDto> pageDto = super.getPageDto(properties);
        List<QuestionDto> items = pageDto.getItems();
        List<Long> ids = new ArrayList<>();
        items.forEach(item -> ids.add(item.getId()));
        List<TagQuestion> tagQuestions = tagDtoDao.getTagsByQuestionsIds(ids);
        Map<Long, List<TagDto>> tagMap = tagQuestions.stream()
                .collect(Collectors.groupingBy(TagQuestion::getQuestionId,
                        Collectors.mapping(TagQuestion::getTagDto, Collectors.toList())));
        items.forEach(item -> item.setListTagDto(tagMap.get(item.getId())));
        pageDto.setItems(items);
        return pageDto;
    }

    @Override
    public List<UserProfileTagDto> getUserProfileTagDto(Long id) {
        return tagDtoDao.getUserProfileTagDto(id);
    }
}

