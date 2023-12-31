package com.javamentor.qa.platform.service.impl;

import com.javamentor.qa.platform.dao.abstracts.model.ChatDao;
import com.javamentor.qa.platform.models.entity.BookMarks;
import com.javamentor.qa.platform.models.entity.GroupBookmark;
import com.javamentor.qa.platform.models.entity.chat.*;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.BlockChatUserList;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.UserChatPin;
import com.javamentor.qa.platform.service.abstracts.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final SingleChatService singleChatService;
    private final GroupChatService groupChatService;
    private final UserChatPinService userChatPinService;
    private final PasswordEncoder passwordEncoder;
    private final BlockChatUserListService blockChatUserListService;
    private final GroupBookmarksService groupBookmarksService;
    private final BookMarksService bookMarksService;

    private final MessageService messageService;
    private final MessageStarService messageStarService;
    private final ChatDao chatDao;

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
                               BlockChatUserListService blockChatUserListService,
                               GroupBookmarksService groupBookmarksService,
                               BookMarksService bookMarksService,
                               MessageService messageService,
                               MessageStarService messageStarService,
                               ChatDao chatDao) {
        this.roleService = roleService;
        this.userService = userService;
        this.questionService = questionService;
        this.tagService = tagService;
        this.answerService = answerService;
        this.singleChatService = singleChatService;
        this.groupChatService = groupChatService;
        this.userChatPinService = userChatPinService;
        this.passwordEncoder = passwordEncoder;
        this.blockChatUserListService = blockChatUserListService;
        this.groupBookmarksService = groupBookmarksService;
        this.bookMarksService = bookMarksService;
        this.messageService = messageService;
        this.messageStarService = messageStarService;
        this.chatDao = chatDao;
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
            groupChat.setImage(
                    "https://variety.com/wp-content/uploads/2021/07/Rick-Astley-Never-Gonna-Give-You-Up.png");
            groupChat.setIsGlobal(false);
            groupChatList.add(groupChat);
        }
        GroupChat globalChat = new GroupChat();
        Set<User> globalChatUsers = new HashSet<>(users);
        globalChat.setUsers(globalChatUsers);

        globalChat.setImage(
                "https://variety.com/wp-content/uploads/2021/07/Rick-Astley-Never-Gonna-Give-You-Up.png");

        globalChat.setIsGlobal(true);
        groupChatList.add(globalChat);
        groupChatService.persistAll(groupChatList);

    }

    @Transactional
    public void initMessage() {
        List<SingleChat> singleChats = singleChatService.getAll();
        List<Message> messageList = new ArrayList<>();

        for (int i = 0; i < singleChats.size() / 2; i++) {

            Message message = new Message();
            message.setLastRedactionDate(LocalDateTime.now(Clock.systemDefaultZone()));
            message.setMessage("Message" + i);
            message.setPersistDate(LocalDateTime.now(Clock.systemDefaultZone()));
            message.setChat(singleChats.get(i).getChat());
            message.setUserSender(singleChats.get(i).getUserOne());
            messageList.add(message);
        }

        messageService.persistAll(messageList);

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
        List<User> users = userService.getAll();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int userIndex = random.nextInt(users.size());
            User userToBlock = users.get(userIndex);
            List<SingleChat> singleChats = singleChatService.getAll();
            int chatIndex = random.nextInt(singleChats.size());
            SingleChat singleChat = singleChats.get(chatIndex);
            BlockChatUserList blockChatUserList = new BlockChatUserList();
            blockChatUserList.setUser(singleChat.getUserOne());
            blockChatUserList.setIsBlocked(userToBlock);
            blockChatUserList.setPersistDate(LocalDateTime.now());
            blockChatUserListService.persistAll(blockChatUserList);
            users.remove(userIndex);
        }
    }

    public void initBookmarks() {
        for (int i = 1; i < 5; i++) {
            BookMarks bookMarks = new BookMarks();
            bookMarks.setUser(userService.getAll().get(i));
            bookMarks.setQuestion(questionService.getAll().get(i));
            bookMarksService.persistAll(bookMarks);
        }
    }

    public void initGroupBookmarks() {
        GroupBookmark groupBookmark1 = new GroupBookmark();
        groupBookmark1.setTitle("Title1");
        Set<BookMarks> bookMarksSet1 = new HashSet<>();
        bookMarksSet1.add(bookMarksService.getAll().get(0));
        groupBookmark1.setBookMarks(bookMarksSet1);
        groupBookmarksService.persistAll(groupBookmark1);

        GroupBookmark groupBookmark2 = new GroupBookmark();
        groupBookmark2.setTitle("Title2");
        Set<BookMarks> bookMarksSet2 = new HashSet<>();
        bookMarksSet2.add(bookMarksService.getAll().get(1));
        groupBookmark2.setBookMarks(bookMarksSet2);
        groupBookmarksService.persistAll(groupBookmark2);


        for (int i = 3; i < 6; i++) {
            GroupBookmark groupBookmark = new GroupBookmark();
            groupBookmark.setTitle("Title" + i);
            Set<BookMarks> bookMarksSet = new HashSet<>();
            for (int j = 0; j < 3; j++) {
                bookMarksSet.add(bookMarksService.getAll().get(j));
            }
            groupBookmark.setBookMarks(bookMarksSet);
            groupBookmarksService.persistAll(groupBookmark);
        }
    }

    @Transactional
    public void initMessageStar() {
        List<User> userList = userService.getAll();
        List<Message> allMessages = messageService.getAll();
        Random random = new Random();
        for (int i = 0; i < userList.size(); i++) {
            User userMessageStar = userList.get(random.nextInt(userList.size()));
            List<MessageStar> messageStarList = new ArrayList<>();
            for (int j = 1; j <= 3; j++) {
                Message message = allMessages.get(random.nextInt(allMessages.size()));
               /* boolean existUserInGroupChat = groupChats.stream()
                            .filter(groupChat -> groupChat.getChat().equals(message.getChat()))
                            .anyMatch(groupChat -> groupChat.getUsers().contains(userMessageStar));
                boolean existUserInSingleChat = singleChats.stream()
                            .filter(singleChat -> singleChat.getChat().getId().equals(message.getChat().getId()))
                            .anyMatch(singleChat -> singleChat.getUserOne().equals(userMessageStar)
                                    || singleChat.getUseTwo().equals(userMessageStar));*/
                Long userId = userMessageStar.getId();
                Long messageId = message.getId();
                    if (chatDao.existUser(userId, messageId)) {

                        MessageStar messageStar = new MessageStar();
                        messageStar.setUser(userMessageStar);
                        messageStar.setMessage(message);
                        messageStar.setPersistDate(LocalDateTime.now());
                        messageStarList.add(messageStar);
                    }
                }
                if (!messageStarList.isEmpty()) {
                    messageStarService.persistAll(messageStarList);
                }
        }
    }

}




