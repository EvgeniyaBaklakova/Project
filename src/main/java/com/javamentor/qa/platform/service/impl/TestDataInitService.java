package com.javamentor.qa.platform.service.impl;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.abstracts.model.RoleService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TestDataInitService {

    private final UserService userService;

    private final RoleService roleService;

    private final QuestionService questionService;
    private final TagService tagService;
    private final AnswerService answerService;

    private final Role ROLE_USER = new Role("ROLE_USER");
    private final Role ROLE_ADMIN = new Role("ROLE_ADMIN");

    @Autowired
    public TestDataInitService(UserService userService, RoleService roleService, QuestionService questionService, TagService tagService, AnswerService answerService) {
        this.roleService = roleService;
        this.userService = userService;
        this.questionService = questionService;
        this.tagService = tagService;
        this.answerService = answerService;
    }

    public void initRoles() {
        roleService.persistAll(ROLE_USER, ROLE_ADMIN);
    }

    //Пароль юзеров password
    public void initUsers() {
        for (int i = 1; i <= 100; i = i + 2) {
            userService.persistAll(new User(null, "email" + i + "@mail.com", "$2y$12$Nkswy9kzGNJlhxscP8O1sefMN3xYUwoAJ/ynqRudb3YPSx/dFM9v6", "name",
                            null, true, false, "city", "link_site", "link_github",
                            "link_vk", "about", "image_link", null, "nick", ROLE_USER),
                    new User(null, "email" + (i + 1) + "@mail.com", "$2y$12$Nkswy9kzGNJlhxscP8O1sefMN3xYUwoAJ/ynqRudb3YPSx/dFM9v6", "name", null,
                            true, false, "city", "link_site", "link_github", "link_vk",
                            "about", "image_link", null, "nick", ROLE_ADMIN));
        }
    }

    public void initTag() {
        for (int i = 1; i < 50; i++) {
            Tag tag = new Tag();
            tag.setName("Name" + i);
            tag.setDescription("Description" + i);
            tag.setPersistDateTime(LocalDateTime.now(Clock.systemDefaultZone()));
            tagService.persistAll(tag);
        }
    }

    public void initQuestion() {
        // 1 tag
        Question question = new Question();
        question.setTitle("Title");
        question.setDescription("Description");
        question.setLastUpdateDateTime(LocalDateTime.now(Clock.systemDefaultZone()));
        question.setUser(userService.getAll().get(1));
        question.setIsDeleted(false);
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tagService.getAll().get(0));
        question.setTags(tagList);
        questionService.persistAll(question);

        //5 tags
        Question question2 = new Question();
        question2.setTitle("Title2");
        question2.setDescription("Description2");
        question2.setLastUpdateDateTime(LocalDateTime.now(Clock.systemDefaultZone()));
        question2.setUser(userService.getAll().get(5));
        question2.setIsDeleted(false);
        List<Tag> tagList2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tagList2.add(tagService.getAll().get(i));
        }
        question2.setTags(tagList2);
        questionService.persistAll(question2);

        //random tags
        for (int i = 3; i <= 5; i++) {
            int rand = (int) Math.random() * 7;
            Question question3 = new Question();
            question3.setTitle("Title" + i);
            question3.setDescription("Decription" + i);
            question3.setLastUpdateDateTime(LocalDateTime.now(Clock.systemDefaultZone()));
            question3.setIsDeleted(false);

            Set<Tag> tagSet = new HashSet<>();
            for (int k = 0; k <= rand; k++) {
                tagSet.add(tagService.getAll().get((int) Math.random() * 5));
            }
            List<Tag> tagList3 = new ArrayList<>(tagSet);

            question3.setTags(tagList3);
            question3.setUser(userService.getAll().get(i));
            questionService.persistAll(question3);
        }
    }

    public void initAnswer() {
        List<Question> questionList = questionService.getAll();
        //У первых трех вопросов нет ответа
        for (int i = 3; i < questionList.size(); i++) {
            Answer answer = new Answer();
            answer.setDateAcceptTime(LocalDateTime.now(Clock.systemDefaultZone()));
            answer.setHtmlBody("Html body" + i);
            answer.setIsDeleted(false);
            answer.setIsDeletedByModerator(false);
            answer.setIsHelpful(true);
            answer.setUpdateDateTime(LocalDateTime.now(Clock.systemDefaultZone()));
            answer.setPersistDateTime(LocalDateTime.now(Clock.systemDefaultZone()));
            answer.setUser(userService.getAll().get(i));
            answer.setQuestion(questionList.get(i));
            answerService.persistAll(answer);
        }
        //У четвертого вопроса 2 ответа
        Answer answer2 = new Answer();
        answer2.setDateAcceptTime(LocalDateTime.now(Clock.systemDefaultZone()));
        answer2.setHtmlBody("Html body1");
        answer2.setIsDeleted(false);
        answer2.setIsDeletedByModerator(false);
        answer2.setIsHelpful(true);
        answer2.setUpdateDateTime(LocalDateTime.now(Clock.systemDefaultZone()));
        answer2.setPersistDateTime(LocalDateTime.now(Clock.systemDefaultZone()));
        answer2.setUser(userService.getAll().get(1));
        answer2.setQuestion(questionList.get(3));
        answerService.persistAll(answer2);
    }
}
