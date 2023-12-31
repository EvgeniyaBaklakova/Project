INSERT INTO "role"
("id", "name")
VALUES (100, 'ROLE_ADMIN'),
       (200, 'ROLE_USER');


INSERT INTO "user_entity"
("id", "about", "city", "email", "full_name", "image_link", "is_deleted",
 "is_enabled", "last_redaction_date", "link_github", "link_site", "link_vk",
 "nickname", "password", "persist_date", "role_id")
VALUES (100, 'about', 'Moscow', 'test100@mail.ru', 'name1', 'http://imagelink1.com', 'f', 't',
        '2023-04-23 15:17:18.280368', 'http://github.com/1', 'http://site1.com', 'http://vk.com/1',
        'nickname1', '$2a$12$PS0j8wIta1OEAa5llG5eZelRC4NEo7939r0ya2c/kw33sH01Y4DmS', '2023-04-23 15:17:18.280368', 200),
       (101, 'about', 'Minsk', 'test101@mail.ru', 'name2', 'http://imagelink2.com', 'f', 't',
        '2023-04-23 15:17:18.280368', 'http://github.com/4', 'http://site2.com', 'http://vk.com/2',
        'nickname2', '$2a$12$PS0j8wIta1OEAa5llG5eZelRC4NEo7939r0ya2c/kw33sH01Y4DmS', '2023-04-23 15:17:18.280368', 200),
       (102, 'about', 'Novgorod', 'test102@mail.ru', 'name3', 'http://imagelink3.com', 'f', 't',
        '2023-04-23 15:17:18.280368', 'http://github.com/3', 'http://site3.com', 'http://vk.com/3',
        'nickname3', '$2a$12$PS0j8wIta1OEAa5llG5eZelRC4NEo7939r0ya2c/kw33sH01Y4DmS', '2023-04-23 15:17:18.280368', 100),
       (103, 'about', 'Spb', 'test103@mail.ru', 'name4', 'http://imagelink4.com', 'f', 't',
        '2023-04-23 15:17:18.280368', 'http://github.com/4', 'http://site4.com', 'http://vk.com/4',
        'nickname4', '$2a$12$PS0j8wIta1OEAa5llG5eZelRC4NEo7939r0ya2c/kw33sH01Y4DmS', '2023-04-23 15:17:18.280368', 200),
       (104, 'about', 'Volgograd', 'test104@mail.ru', 'name5', null, 'f', 't',
        '2023-04-23 15:17:18.280368', 'http://github.com/5', 'http://site5.com', 'http://vk.com/5',
        'nickname5', '$2a$12$PS0j8wIta1OEAa5llG5eZelRC4NEo7939r0ya2c/kw33sH01Y4DmS', '2023-04-23 15:17:18.280368', 100);

INSERT INTO "question"
("id", "description", "is_deleted", "last_redaction_date", "persist_date", "title", "user_id")
VALUES (100, 'description1', 'false', '2023-04-23 13:01:11.245126', '2023-04-23 13:01:11.245126', 'title1', 104),
       (101, 'description2', 'false', '2023-04-23 13:01:11.245126', '2023-04-23 13:01:11.245126', 'title2', 101),
       (102, 'description3', 'false', '2023-04-23 13:01:11.245126', '2023-04-23 13:01:11.245126', 'title3', 102),
       (103, 'description4', 'true', '2023-04-23 13:01:11.245126', '2023-04-23 13:01:11.245126', 'title4', 103),
       (104, 'description5', 'false', '2023-04-23 13:01:11.245126', '2023-04-23 13:01:11.245126', 'title5', 104);

INSERT INTO "tag"
("id", "description", "name", "persist_date")
VALUES (100, 'description1', 'name1', '2023-04-23 13:01:11.245126'),
       (101, 'description2', 'name2', '2023-04-23 13:01:11.245126'),
       (102, 'description3', 'name3', '2023-04-23 13:01:11.245126'),
       (103, 'description4', 'name4', '2023-04-23 13:01:11.245126'),
       (104, 'description5', 'name5', '2023-04-23 13:01:11.245126'),
       (105, 'description5', 'name6', '2023-04-23 13:01:11.245126'),
       (106, 'description5', 'name7', '2023-04-23 13:01:11.245126'),
       (107, 'description5', 'name8', '2023-04-23 13:01:11.245126'),
       (108, 'description5', 'name9', '2023-04-23 13:01:11.245126'),
       (109, 'description5', 'name10','2023-04-23 13:01:11.245126');

INSERT INTO "question_has_tag"
("question_id", "tag_id")
VALUES (100,100),
       (100,101),
       (101,102),
       (101,103),
       (101,104),
       (102,105),
       (103,106),
       (103,107),
       (103,108),
       (103,109);

INSERT INTO "answer"
("id", "date_accept_time", "html_body", "is_deleted", "is_deleted_by_moderator", "is_helpful", "persist_date", "update_date", "question_id", "user_id" )
VALUES (100, '2023-04-23 13:01:11.245126', 'html_body1', 'false', 'false', 'true', '2023-04-23 13:01:11.245126', '2023-04-23 13:01:11.245126', 100, 102),
       (101, '2023-04-23 13:01:11.245126', 'html_body2', 'false', 'false', 'true', '2023-04-23 13:01:11.245126', '2023-04-23 13:01:11.245126', 101, 103),
       (102, '2023-04-23 13:01:11.245126', 'html_body3', 'false', 'false', 'true', '2023-04-23 13:01:11.245126', '2023-04-23 13:01:11.245126', 102, 102),
       (103, '2023-04-23 13:01:11.245126', 'html_body4', 'false', 'false', 'true', '2023-04-30 13:01:11.245126', '2023-04-23 13:01:11.245126', 103, 100),
       (104, '2023-04-23 13:01:11.245126', 'html_body5', 'false', 'false', 'true', '2023-05-01 13:01:11.245126', '2023-04-23 13:01:11.245126', 104, 100);

INSERT INTO "votes_on_questions"
("id", "persist_date", "vote", "question_id", "user_id")
VALUES (100, '2023-04-23 13:01:11.245126', 'up', 100, 101),
       (101, '2023-04-23 13:01:11.245126', 'up', 100, 103),
       (102, '2023-04-23 13:01:11.245126', 'up', 100, 102),
       (103, '2023-04-23 13:01:11.245126', 'down', 100, 101),
       (104, '2023-04-23 13:01:11.245126', 'up', 102, 100);

INSERT INTO "reputation"
("id", "count", "persist_date", "type", "answer_id", "author_id", "question_id", "sender_id")
VALUES (100, 1, '2023-04-23 15:21:03.527867', 2, 101, 100, 102, 104),
       (101, 2, '2023-04-23 15:21:03.527867', 2, 104, 100, 102, 102),
       (102, 3, '2023-04-23 15:21:03.527867', 2, 103, 100, 103, 103),
       (103, 4, '2023-04-23 15:21:03.527867', 2, 103, 101, 103, 101),
       (104, 5, '2023-04-23 15:21:03.527867', 2, 102, 102, 102, 100);

INSERT INTO "question_viewed"
("id", "persist_date", "question_id", "user_id")
VALUES (100, '2023-04-23 15:21:03.527867', 100, 101),
       (101, '2023-04-23 15:21:03.527867', 100, 104),
       (102, '2023-04-23 15:21:03.527867', 101, 103),
       (103, '2023-04-23 15:21:03.527867', 102, 103),
       (104, '2023-04-23 15:21:03.527867', 103, 102);
