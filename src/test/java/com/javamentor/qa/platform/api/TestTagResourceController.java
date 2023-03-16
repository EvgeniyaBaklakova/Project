package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractTestApi;
import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class TestTagResourceController extends AbstractTestApi {

    @Test
    @Sql(scripts = "/script/TestTagResourceController/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestTagResourceController/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getUserDto() throws Exception {
        RelatedTagsDto t1 = new RelatedTagsDto(301L,"tag t1", 15L);
        RelatedTagsDto t2 = new RelatedTagsDto(303L,"tag t3", 14L);
        RelatedTagsDto t3 = new RelatedTagsDto(305L,"tag t5", 13L);
        RelatedTagsDto t4 = new RelatedTagsDto(307L,"tag t7", 12L);
        RelatedTagsDto t5 = new RelatedTagsDto(309L,"tag t9", 11L);
        RelatedTagsDto t6 = new RelatedTagsDto(311L,"tag t11", 10L);
        RelatedTagsDto t7 = new RelatedTagsDto(313L,"tag t13", 9L);
        RelatedTagsDto t8 = new RelatedTagsDto(315L,"tag t15", 8L);
        RelatedTagsDto t9 = new RelatedTagsDto(314L,"tag t14", 7L);
        RelatedTagsDto t10 = new RelatedTagsDto(312L,"tag t12", 6L);


        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/tag/related"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10))));
    }
}
