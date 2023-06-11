package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.dao.abstracts.pagination.QuestionDtoDaoWithoutAnswers;
import com.javamentor.qa.platform.exception.NoSuchDaoException;
import com.javamentor.qa.platform.exception.PageException;
import com.javamentor.qa.platform.models.dto.PageDto;
import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.dto.tag.IgnoredTagsDto;
import com.javamentor.qa.platform.models.dto.tag.TrackedTagsDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.question.TagQuestion;
import com.javamentor.qa.platform.service.abstracts.dto.QuestionDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionDtoServiceImp extends PageDtoServiceImpl<QuestionDto> implements QuestionDtoService {
    private final TagDtoDao tagDtoDao;
    private final QuestionDtoDao questionDtoDao;
    private final Map<String, PageDtoDao<QuestionDto>> daoMap;

    public QuestionDtoServiceImp(TagDtoDao tagDtoDao,QuestionDtoDao questionDtoDao,Map<String, PageDtoDao<QuestionDto>> daoMap) {
        super(daoMap);
        this.tagDtoDao = tagDtoDao;
        this.questionDtoDao = questionDtoDao;
        this.daoMap = daoMap;
    }

    @Override
    public Optional<QuestionDto> getQuestionDtoById(Long id) {
        Optional<QuestionDto> questionDto = questionDtoDao.getQuestionDtoById(id);
        questionDto.ifPresent(dto -> dto.setListTagDto(tagDtoDao.getTagsByQuestionId(id)));
        return questionDto;
    }

    public PageDto<QuestionDto> getPageDtoWithTags(PaginationData data,List<IgnoredTagsDto> ignoredTagsDtoList,List<TrackedTagsDto> trackedTagsDtoList) {
        if (!daoMap.containsKey(data.getDaoName())) {
            throw new NoSuchDaoException("There is no dao with name: " + data.getDaoName());
        }
        if (data.getCurrentPage() < 0) {
            throw new PageException("The number can`t be less than 0");
        }
        if (data.getItemsOnPage() < 0) {
            throw new PageException("The number can`t be less than 0");
        }
        QuestionDtoDaoWithoutAnswers<QuestionDto> currentDao = (QuestionDtoDaoWithoutAnswers<QuestionDto>) daoMap.get(data.getDaoName());
        PageDto<QuestionDto> pageDTO = new PageDto<>();
        List<QuestionDto> questionDtoList = currentDao.getItemsWithTags(data, ignoredTagsDtoList, trackedTagsDtoList);
        Long totalResultCount = currentDao.getTotalResultCountWithTags(data.getProps(), ignoredTagsDtoList, trackedTagsDtoList);
        pageDTO.setCurrentPageNumber(data.getCurrentPage());
        pageDTO.setItems(questionDtoList);
        pageDTO.setItemsOnPage(questionDtoList.size());
        pageDTO.setTotalResultCount(Math.toIntExact(totalResultCount));
        pageDTO.setTotalPageCount((int) Math.ceil((double) totalResultCount / data.getItemsOnPage()));
        return pageDTO;
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

}

