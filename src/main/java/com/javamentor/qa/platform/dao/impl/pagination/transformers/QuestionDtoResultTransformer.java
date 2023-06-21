package com.javamentor.qa.platform.dao.impl.pagination.transformers;

import com.javamentor.qa.platform.models.dto.question.QuestionDto;
import com.javamentor.qa.platform.models.dto.tag.TagDto;
import org.hibernate.transform.ResultTransformer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

public class QuestionDtoResultTransformer implements ResultTransformer {

    private Map<Long, QuestionDto> questionDtoMap = new LinkedHashMap<>();

    @Override
    public Object transformTuple(Object[] tuples, String[] aliases) {
        List<String> aliasList = Arrays.asList(aliases);
        Map<String, Object> tupleMap = aliasList.stream().collect(HashMap::new, (m, a) -> m.put(a, tuples[aliasList.indexOf(a)]), HashMap::putAll);

        Long questionId = (Long) tupleMap.get("q_id");
        String questionTitle = (String) tupleMap.get("q_title");
        Long authorId = (Long) tupleMap.get("user_id");
        Long authorRep = (Long) tupleMap.get("rep");
        String authorName = (String) tupleMap.get("u_name");
        String authorImage = (String) tupleMap.get("img");
        String questionDesc = (String) tupleMap.get("desc");
        Long viewCount = (Long) tupleMap.getOrDefault("vc", 0);
        Long answerCount = (Long) tupleMap.getOrDefault("ac", 0);
        Long valuableCount = (Long) tupleMap.getOrDefault("valc", 0);
        LocalDateTime persistDT = (LocalDateTime) tupleMap.get("pdt");
        LocalDateTime updateDT = (LocalDateTime) tupleMap.get("udt");

        QuestionDto questionDto = questionDtoMap.computeIfAbsent(questionId, id -> new QuestionDto(questionId, questionTitle, authorId, authorRep, authorName, authorImage, questionDesc, viewCount, answerCount, valuableCount, persistDT, updateDT, new ArrayList<>()));
        TagDto tagDto = new TagDto((Long) tupleMap.get("t_id"), (String) tupleMap.get("t_name"), (String) tupleMap.get("t_desc"), (LocalDateTime) tupleMap.get("t_dt"));
        questionDto.getListTagDto().add(tagDto);

        return questionDto;
    }

    @Override
    public List transformList(List list) {
        return new ArrayList<>(questionDtoMap.values());
    }
}
