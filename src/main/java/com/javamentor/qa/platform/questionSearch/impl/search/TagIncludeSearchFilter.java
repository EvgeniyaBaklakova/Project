package com.javamentor.qa.platform.questionSearch.impl.search;

import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.questionSearch.QuestionQuery;
import com.javamentor.qa.platform.questionSearch.abstracts.SearchFilter;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(value = 1)
public class TagIncludeSearchFilter implements SearchFilter {
    private final TagService tagService;
    private final Pattern pattern;

    public TagIncludeSearchFilter(TagService tagService) {
        this.tagService = tagService;
        pattern = Pattern.compile("[^\\-]\\[(.*?)\\]");
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
            if (match != "") {
                names.add(match.trim().toLowerCase());
            }
        }

        StringBuilder hql = new StringBuilder("q.id IN (SELECT a.id FROM Question a JOIN a.tags b WHERE  ");
        hql.append(" b.name IN (");
        for (String tagName : names) {
            hql.append("'").append(tagName).append("', ");
        }
        hql.delete(hql.length() - 2, hql.length());
        hql.append(") GROUP BY a.id HAVING COUNT(DISTINCT b) = ").append(names.size());

        hql.append(") AND ");

        questionQuery.getStringBuilder().append(hql);
        questionQuery.setQuery(matcher.replaceAll(""));
        questionQuery.getOutput().append("include tags: ");
        for (String tag : names) {
            questionQuery.getOutput().append(tag).append(", ");
        }
        questionQuery.getOutput().delete(questionQuery.getOutput().length() - 2 , questionQuery.getOutput().length());
        questionQuery.getOutput().append("; ");

    }
}
