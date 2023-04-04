package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.TagDao;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl extends ReadWriteServiceImpl<Tag, Long> implements TagService {
    private final TagDao tagDao;

    public TagServiceImpl(TagDao tagDao) {
        super(tagDao);
        this.tagDao = tagDao;
    }

    @Override
    @Transactional
    public List<Tag> saveTags(List<Tag> questionTags) {
        List<String> names = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();

        for (Tag tag : questionTags) {
            names.add(tag.getName());
        }
        List<Tag> tagsFromDb = tagDao.getTagsByNames(names);
        questionTags.forEach(questionTag -> {
            boolean isExist = tagsFromDb.stream().anyMatch(tag -> tag.getName().equals(questionTag.getName()));
            if (isExist) {
                tags.add(questionTag);
            } else {
                tagDao.persist(questionTag);
                tags.add(questionTag);
            }
        });
        return tags;
    }
}
