package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.exception.NoSuchDaoException;
import com.javamentor.qa.platform.exception.PageException;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.question.TagQuestion;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionDtoServiceImp implements QuestionDtoService {
    private final TagDtoDao tagDtoDao;
    private final QuestionDtoDao questionDtoDao;

    private final Map<String, PageDtoDao<QuestionDto>> daoMap;


    public QuestionDtoServiceImp(TagDtoDao tagDtoDao, QuestionDtoDao questionDtoDao, Map<String, PageDtoDao<QuestionDto>> daoMap) {
        this.daoMap = daoMap;
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
        if (!daoMap.containsKey(properties.getDaoName())) {
            throw new NoSuchDaoException("There is no dao with name: " + properties.getDaoName());
        }
        if (properties.getCurrentPage() < 0) {
            throw new PageException("The number can`t be less than 0");
        }
        if (properties.getItemsOnPage() < 0) {
            throw new PageException("The number can`t be less than 0");
        }
        PageDtoDao<QuestionDto> currentDao = daoMap.get(properties.getDaoName());
        List<QuestionDto> items = currentDao.getItems(properties);
        List<Long> ids = new ArrayList<>();

        for (QuestionDto questionDto:items
             ) {
            ids.add(questionDto.getId());
        }

        MultiValueMap tagMap = new MultiValueMap();

        List<TagQuestion> tagQuestions = tagDtoDao.getTagsByQuestionsIds(ids);

        for (TagQuestion tagQuestion:tagQuestions
             ) {
                tagMap.put(tagQuestion.getQuestionId(), tagQuestion.getTagDto());
        }

        for (QuestionDto questionDto:items
        ) {
            questionDto.setListTagDto((List<TagDto>) tagMap.get(questionDto.getId()));
        }

        PageDto<QuestionDto> pageDTO = new PageDto<>();
        pageDTO.setCurrentPageNumber(properties.getCurrentPage());
        pageDTO.setItems(items);
        pageDTO.setItemsOnPage(pageDTO.getItems().size());
        pageDTO.setTotalResultCount(Math.toIntExact((Long) currentDao.getTotalResultCount(properties.getProps())));
        pageDTO.setTotalPageCount((int) Math.ceil((double) pageDTO.getTotalResultCount() / properties.getItemsOnPage()));
        return pageDTO;
    }


}

