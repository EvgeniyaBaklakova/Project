package com.javamentor.qa.platform.questionSearch.impl.order;

import com.javamentor.qa.platform.questionSearch.QuestionQuery;
import com.javamentor.qa.platform.questionSearch.abstracts.OrderFilter;
import org.springframework.stereotype.Component;

@Component
public class VoteOrderSearchFilter implements OrderFilter {
    @Override
    public void doFilter(QuestionQuery questionQuery) {
         if (questionQuery.getOrder().equals("votes")) {
             questionQuery.getStringBuilder().append("  ORDER BY SUM(CASE WHEN vq.vote = 'UP_VOTE' THEN 1 WHEN vq.vote = 'DOWN_VOTE' THEN -1 ELSE 0 END) ");
         }
    }
}
