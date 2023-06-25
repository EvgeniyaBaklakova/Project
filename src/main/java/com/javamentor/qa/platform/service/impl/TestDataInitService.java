package com.javamentor.qa.platform.service.impl;

import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.BlockChatUserList;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.UserChatPin;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.GroupChatService;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.RoleService;
import com.javamentor.qa.platform.service.abstracts.model.SingleChatService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.abstracts.model.UserChatPinService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class TestDataInitService {

    private final UserService userService;
    private final RoleService roleService;
    private final QuestionService questionService;
    private final TagService tagService;
    private final AnswerService answerService;
    private final SingleChatService singleChatService;
    private final GroupChatService groupChatService;
    private final UserChatPinService userChatPinService;
    private final PasswordEncoder passwordEncoder;
    private final BlockChatUserList blockChatUserList;

    private final Role ROLE_USER = new Role("ROLE_USER");
    private final Role ROLE_ADMIN = new Role("ROLE_ADMIN");


    @Autowired
    public TestDataInitService(UserService userService,
                               RoleService roleService,
                               QuestionService questionService,
                               TagService tagService,
                               AnswerService answerService,
                               SingleChatService singleChatService,
                               GroupChatService groupChatService,
                               UserChatPinService userChatPinService,
                               PasswordEncoder passwordEncoder,
                               BlockChatUserList blockChatUserList) {
        this.roleService = roleService;
        this.userService = userService;
        this.questionService = questionService;
        this.tagService = tagService;
        this.answerService = answerService;
        this.singleChatService = singleChatService;
        this.groupChatService = groupChatService;
        this.userChatPinService = userChatPinService;
        this.passwordEncoder = passwordEncoder;
        this.blockChatUserList = blockChatUserList;
    }

    public void initRoles() {
        roleService.persistAll(ROLE_USER, ROLE_ADMIN);
    }

    public void initUsers() {

        String pass = passwordEncoder.encode("password");

        for (int i = 1; i <= 100; i = i + 2) {
            userService.persistAll(new User(null, "email" + i + "@mail.com", pass, "name",
                            null, true, false, "city", "link_site", "link_github",
                            "link_vk", "about", "image_link", null, "nick", ROLE_USER),
                    new User(null, "email" + (i + 1) + "@mail.com", pass, "name", null,
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

    public void initChat() {
        List<SingleChat> singleChatList = new ArrayList<>();
        List<User> users = userService.getAll();
        Random random = new Random();
        Set<String> uniqueUserPairs = new HashSet<>();
        for (int i = 0; i < 30; i++) {
            int one = random.nextInt(20);
            int two = random.nextInt(20);
            if (one == two) {
                continue;
            }
            String userPair = users.get(one).getId() + "-" + users.get(two).getId();
            String reverseUserPair = users.get(two).getId() + "-" + users.get(one).getId();
            if (!uniqueUserPairs.contains(userPair) && !uniqueUserPairs.contains(reverseUserPair)) {
                SingleChat singleChat = new SingleChat();
                singleChat.setUserOne(users.get(one));
                singleChat.setUseTwo(users.get(two));
                singleChatList.add(singleChat);
                uniqueUserPairs.add(userPair);
            }
        }
        singleChatService.persistAll(singleChatList);


        List<GroupChat> groupChatList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            GroupChat groupChat = new GroupChat();
            int count = random.nextInt(7) + 3;
            Set<User> group = new HashSet<>();
            for (int j = 0; j < count; j++) {
                group.add(users.get(random.nextInt(20)));
            }
            groupChat.setUsers(group);
            groupChat.setIsGlobal(false);
            groupChatList.add(groupChat);
        }
        GroupChat globalChat = new GroupChat();
        Set<User> globalChatUsers = new HashSet<>(users);
        globalChat.setUsers(globalChatUsers);
        globalChat.setIsGlobal(true);
        groupChatList.add(globalChat);
        groupChatService.persistAll(groupChatList);

    }

    @Transactional
    public void initUserChatPin() {
        List<UserChatPin> chatPins = new ArrayList<>();
        List<SingleChat> singleChats = singleChatService.getAll();

        GroupChat groupChat = groupChatService.getAll().get(0);

        for (int i = 0; i < singleChats.size() / 2; i++) {
            UserChatPin userChatPin = new UserChatPin();
            userChatPin.setChat(singleChats.get(i).getChat());
            userChatPin.setUser(singleChats.get(i).getUserOne());
            chatPins.add(userChatPin);
        }

        for (User user : groupChat.getUsers()) {
            UserChatPin userChatPin = new UserChatPin();
            userChatPin.setChat(groupChat.getChat());
            userChatPin.setUser(user);
            chatPins.add(userChatPin);
        }
        userChatPinService.persistAll(chatPins);
    }

    public void initBlockChatUserList() {
        for (int i = 1; i <= 5; i++) {
            BlockChatUserList blockChatUserList = new BlockChatUserList();
            blockChatUserList.setId(i);
            blockChatUserList.setPersistDate(LocalDateTime.now());

            User userProfile = userService.getById(i);
            User userBlocked = userService.getById(i + 1);
            blockChatUserList.setUser(userProfile);
            blockChatUserList.setIsBlocked(userBlocked);


            blockChatUserListService.save(blockChatUserList);
        }
    }
}
