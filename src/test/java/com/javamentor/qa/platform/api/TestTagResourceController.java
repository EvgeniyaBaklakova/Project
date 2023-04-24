package com.javamentor.qa.platform.api;

import com.javamentor.qa.platform.AbstractTestApi;
import com.javamentor.qa.platform.models.dto.tag.IgnoredTagsDto;
import com.javamentor.qa.platform.models.dto.tag.RelatedTagsDto;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TestTagResourceController extends AbstractTestApi {

    @Test
    @Sql(scripts = "/script/TestTagResourceController/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestTagResourceController/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getUserDto() throws Exception {
        RelatedTagsDto t1 = new RelatedTagsDto(301L, "tag t1", 15L);
        RelatedTagsDto t2 = new RelatedTagsDto(303L, "tag t3", 14L);
        RelatedTagsDto t3 = new RelatedTagsDto(305L, "tag t5", 13L);
        RelatedTagsDto t4 = new RelatedTagsDto(307L, "tag t7", 12L);
        RelatedTagsDto t5 = new RelatedTagsDto(309L, "tag t9", 11L);
        RelatedTagsDto t6 = new RelatedTagsDto(311L, "tag t11", 10L);
        RelatedTagsDto t7 = new RelatedTagsDto(313L, "tag t13", 9L);
        RelatedTagsDto t8 = new RelatedTagsDto(315L, "tag t15", 8L);
        RelatedTagsDto t9 = new RelatedTagsDto(314L, "tag t14", 7L);
        RelatedTagsDto t10 = new RelatedTagsDto(312L, "tag t12", 6L);


        this.mvc.perform(MockMvcRequestBuilders.get("/api/user/tag/related").header("Authorization","Bearer " + getToken("test101@mail.ru", "test")))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10))));
    }

    @Test
    @Sql(scripts = "/script/TestTagResourceController/AddTrackedIgnoredTagsTest/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestTagResourceController/AddTrackedIgnoredTagsTest/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void saveAsTracked() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/tag/301/tracked").header("Authorization","Bearer " + getToken("test101@mail.ru", "test")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Is.is(301)))
                .andExpect(jsonPath("$.name", Is.is("tag t1")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Sql(scripts = "/script/TestTagResourceController/AddTrackedIgnoredTagsTest/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestTagResourceController/AddTrackedIgnoredTagsTest/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void saveAsIgnored() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/api/user/tag/301/ignored").header("Authorization","Bearer " + getToken("test101@mail.ru", "test")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Is.is(301)))
                .andExpect(jsonPath("$.name", Is.is("tag t1")))
                .andDo(MockMvcResultHandlers.print());
    }

    private String getToken(String email, String password) {
        String token;
        Map<String,String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        try {
            String response = (this.mvc.perform(MockMvcRequestBuilders
                            .post("/api/auth/token").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(map)))
                    .andReturn().getResponse().getContentAsString());
            token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");
            return token;
        } catch (Exception e) {
        }
        return "";
    }

    @Test
    @Sql(scripts = "/script/TestTagResourceController/Before.sql",
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "/script/TestTagResourceController/After.sql",
            executionPhase = AFTER_TEST_METHOD)
    public void getIgnoredTags() throws Exception {
        IgnoredTagsDto it1 = new IgnoredTagsDto(401L, "tag 11");
        IgnoredTagsDto it2 = new IgnoredTagsDto(402L, "tag 12");
        IgnoredTagsDto it3 = new IgnoredTagsDto(403L, "tag 13");
        IgnoredTagsDto it4 = new IgnoredTagsDto(404L, "tag 14");
        IgnoredTagsDto it5 = new IgnoredTagsDto(405L, "tag 15");
        IgnoredTagsDto it6 = new IgnoredTagsDto(406L, "tag 16");
        IgnoredTagsDto it7 = new IgnoredTagsDto(407L, "tag 17");
        IgnoredTagsDto it8 = new IgnoredTagsDto(408L, "tag 18");
        IgnoredTagsDto it9 = new IgnoredTagsDto(409L, "tag 19");
        IgnoredTagsDto it10 = new IgnoredTagsDto(410L, "tag 20");

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\" : \"test101@mail.ru\", \"password\" : \"test\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String token = response.replace("{\"jwtToken\":\"", "").replace("\"}", "");

        this.mvc.perform(MockMvcRequestBuilders
                .get("/api/user/tag/ignored")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Arrays.asList(it1, it2, it3, it4, it5, it6, it7, it8, it9, it10))))
                .andExpect(status().isOk());
    }
}
