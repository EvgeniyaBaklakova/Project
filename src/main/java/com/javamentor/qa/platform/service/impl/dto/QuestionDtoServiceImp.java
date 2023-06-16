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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("unchecked")
public class QuestionDtoServiceImp extends PageDtoServiceImpl<QuestionDto> implements QuestionDtoService {
    private final TagDtoDao tagDtoDao;
    private final QuestionDtoDao questionDtoDao;

    public QuestionDtoServiceImp(TagDtoDao tagDtoDao,QuestionDtoDao questionDtoDao,Map<String, PageDtoDao<QuestionDto>> daoMap) {
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
    public PageDto<QuestionDto> getPageDto(PaginationData data) {
        PageDto<QuestionDto> pageDto = super.getPageDto(data);
        Map<String, Object> map = data.getProps();

        List<Long> trackedTagsId = (List<Long>) map.get("trackedTags");
        List<Long> ignoredTagsId = (List<Long>) map.get("ignoredTags");

        List<QuestionDto> questionDtoList = pageDto.getItems().stream().peek(questionDto -> {
            List<TagDto> tag = tagDtoDao.getTagsByQuestionId(questionDto.getId());
            questionDto.setListTagDto(tag);
        }).collect(Collectors.toList());

        if (trackedTagsId != null || ignoredTagsId != null) {
            questionDtoList = questionDtoList.stream().filter(questionDto -> {
                List<Long> questionTagsIds = questionDto.getListTagDto().stream().map(TagDto::getId).collect(Collectors.toList());
                if (ignoredTagsId != null && ignoredTagsId.stream().anyMatch(questionTagsIds::contains)) {
                    return false;
                } else return trackedTagsId == null || trackedTagsId.stream().anyMatch(questionTagsIds::contains);
            }).collect(Collectors.toList());

            pageDto.setTotalResultCount(questionDtoList.size());
            pageDto.setItems(questionDtoList);
            pageDto.setItemsOnPage(pageDto.getItems().size());
        }

        return pageDto;
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

