package com.javamentor.qa.platform.questionSearch.impl;

import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.questionSearch.QuestionQuery;
import com.javamentor.qa.platform.questionSearch.abstracts.SearchFilter;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TagExcludeSearchFilter implements SearchFilter {
    private final TagService tagService;
    private final Pattern pattern;

    public TagExcludeSearchFilter(TagService tagService) {
        this.tagService = tagService;
        pattern = Pattern.compile("\\-\\[(.*?)\\]");
    }

    @Override
    public void doFilter(QuestionQuery questionQuery) {
        Matcher matcher = pattern.matcher(questionQuery.getQuery());

        if (!matcher.find()) {
            return;
        }
        matcher.reset();

        List<String> names = new ArrayList<>();
        while (matcher.find()) {
            String match = matcher.group(1);
            names.add(match.trim());
        }

        List<Tag> tags = tagService.getTagsByNames(names);

        StringBuilder hql = new StringBuilder("q.id NOT IN (SELECT a.id FROM Question a JOIN a.tags b WHERE  ");


        for (Tag tag : tags) {
            hql.append("b.id = ").append(tag.getId()).append(" AND");
        }

        hql.delete(hql.length() - 3, hql.length());
        hql.append(") AND ");

        questionQuery.setQuery(matcher.replaceAll(""));
        questionQuery.getStringBuilder().append(hql);

        questionQuery.getOutput().append("exclude tags: ");
        for (Tag tag : tags) {
            questionQuery.getOutput().append(tag.getName()).append(", ");
        }
        questionQuery.getOutput().append("; ");
    }
}
