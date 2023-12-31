package com.javamentor.qa.platform.webapp.configs.initializer;

import com.javamentor.qa.platform.service.impl.TestDataInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingClass({"org.springframework.boot.test.context.SpringBootTest"})
public class TestEntityInit implements CommandLineRunner {

    private final TestDataInitService testDataInitService;

    @Autowired
    public TestEntityInit(TestDataInitService testDataInitService) {
        this.testDataInitService = testDataInitService;
    }

    @Override
    public void run(String... args) {
        testDataInitService.initRoles();
        testDataInitService.initUsers();
        testDataInitService.initTag();
        testDataInitService.initQuestion();
        testDataInitService.initAnswer();
        testDataInitService.initChat();
        testDataInitService.initMessage();
        testDataInitService.initUserChatPin();
        testDataInitService.initBlockChatUserList();
        testDataInitService.initBookmarks();
        testDataInitService.initGroupBookmarks();
        testDataInitService.initMessageStar();
    }
}
