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
public class TagIncludeSearchFilter implements SearchFilter {
    private final TagService tagService;
    private final Pattern pattern;

    public TagIncludeSearchFilter(TagService tagService) {
        this.tagService = tagService;
        pattern = Pattern.compile("\\[(.*?)\\]");
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

        StringBuilder sql = new StringBuilder("id IN (SELECT question_id FROM question_has_tag WHERE ");

        for (Tag tag : tags) {
            sql.append("tag_id = ").append(tag.getId()).append(" AND");
        }

        questionQuery.getStringBuilder().delete(questionQuery.getStringBuilder().length() - 3, questionQuery.getStringBuilder().length());
        questionQuery.getStringBuilder().append(")");

        questionQuery.setQuery(matcher.replaceAll(""));

        questionQuery.getOutput().append("include tags: ");
        for(Tag tag : tags) {
            questionQuery.getOutput().append(tag.getName()).append(", ");
        }
        questionQuery.getOutput().append("; ");

    }
}
