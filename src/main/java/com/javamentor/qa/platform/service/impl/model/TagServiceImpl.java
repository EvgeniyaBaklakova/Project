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
    public List<Tag> tagsToSet(List<Tag> questionTags) {
        List<String> names = new ArrayList<>();

        questionTags.forEach(tag -> names.add(tag.getName()));

        List<Tag> tagsFromDb = tagDao.getTagsByNames(names);
        List<Tag> tags = new ArrayList<>(tagsFromDb);

        return saveTags(questionTags, tagsFromDb, tags);
    }

    private List<Tag> saveTags(List<Tag> questionTags, List<Tag> tagsFromDb, List<Tag> tags) {
        questionTags.forEach(questionTag -> {
            boolean isExist = tagsFromDb
                    .stream()
                    .anyMatch(tag -> tag.getName().equals(questionTag.getName()));
            if (!isExist) {
                tagDao.persist(questionTag);
                tags.add(questionTag);
            }
        });
        return tags;
    }
}
