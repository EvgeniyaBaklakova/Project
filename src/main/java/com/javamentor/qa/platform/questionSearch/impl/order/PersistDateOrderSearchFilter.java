package com.javamentor.qa.platform.questionSearch.impl.order;

import com.javamentor.qa.platform.questionSearch.QuestionQuery;
import com.javamentor.qa.platform.questionSearch.abstracts.OrderFilter;
import org.springframework.stereotype.Component;

@Component
public class PersistDateOrderSearchFilter implements OrderFilter {
    @Override
    public void doFilter(QuestionQuery questionQuery) {
         if (questionQuery.getOrder().equals("newest")) {
             questionQuery.getStringBuilder().append(" ORDER BY q.persistDateTime ");
         }
    }
}
