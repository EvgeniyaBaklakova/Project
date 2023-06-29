TRUNCATE TABLE role CASCADE;
TRUNCATE TABLE question CASCADE;
TRUNCATE TABLE tag CASCADE;


INSERT INTO "role"
("id", "name")
VALUES (100, 'ROLE_ADMIN'),
       (200, 'ROLE_USER');


INSERT INTO "user_entity"
("id", "about", "city", "email", "full_name", "image_link", "is_deleted",
 "is_enabled", "last_redaction_date", "link_github", "link_site", "link_vk",
 "nickname", "password", "persist_date", "role_id")
VALUES (100, 'about', 'Volgograd', 'test100@mail.ru', 'name5', 'http://imagelink1.com', 'f', 't',
        '2023-04-23 15:17:18.280368', 'http://github.com/5', 'http://site5.com', 'http://vk.com/5',
        'nickname5', '$2a$12$Ppy0TPRaMYUDLEDPt1cztelNpu2.L73vyxZrI6qAQGXqDOUoYJ8Me', '2023-04-23 15:17:18.280368', 100),
       (101, 'about', 'Minsk', 'test101@mail.ru', 'name2', 'http://imagelink2.com', 'f', 't',
        '2023-04-23 15:17:18.280368', 'http://github.com/4', 'http://site2.com', 'http://vk.com/2',
        'nickname2', '$2a$12$Ppy0TPRaMYUDLEDPt1cztelNpu2.L73vyxZrI6qAQGXqDOUoYJ8Me', '2023-04-23 15:17:18.280368', 200),
       (102, 'about', 'Novgorod', 'test102@mail.ru', 'name3', 'http://imagelink3.com', 'f', 't',
        '2023-04-23 15:17:18.280368', 'http://github.com/3', 'http://site3.com', 'http://vk.com/3',
        'nickname3', '$2a$12$Ppy0TPRaMYUDLEDPt1cztelNpu2.L73vyxZrI6qAQGXqDOUoYJ8Me', '2023-04-23 15:17:18.280368', 100),
       (103, 'about', 'Spb', 'test103@mail.ru', 'name4', 'http://imagelink4.com', 'f', 't',
        '2023-04-23 15:17:18.280368', 'http://github.com/4', 'http://site4.com', 'http://vk.com/4',
        'nickname4', '$2a$12$Ppy0TPRaMYUDLEDPt1cztelNpu2.L73vyxZrI6qAQGXqDOUoYJ8Me', '2023-04-23 15:17:18.280368', 200),
       (104, 'about', 'Volgograd', 'test104@mail.ru', 'name5', 'http://imagelink5.com', 'f', 't',
        '2023-04-23 15:17:18.280368', 'http://github.com/5', 'http://site5.com', 'http://vk.com/5',
        'nickname5', '$2a$12$Ppy0TPRaMYUDLEDPt1cztelNpu2.L73vyxZrI6qAQGXqDOUoYJ8Me', '2023-04-23 15:17:18.280368', 100);

INSERT INTO "question"
("id", "description", "is_deleted", "last_redaction_date", "persist_date", "title", "user_id")
VALUES (101, 'description2', 'false', '2023-04-23 13:01:11.245126', '2023-04-23 13:01:11.245126', 'title2', 101),
       (102, 'description3', 'false', '2023-04-23 13:01:11.245126', '2023-04-23 13:01:11.245126', 'title3', 102),
       (103, 'description4', 'false', '2023-04-23 13:01:11.245126', '2023-04-23 13:01:11.245126', 'title4', 103),
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
VALUES (101,102),
       (101,103),
       (102,102),
       (102,105),
       (103,102),
       (103,107),
       (104,103),
       (104,109);

INSERT INTO "answer"
("id", "date_accept_time", "html_body", "is_deleted", "is_deleted_by_moderator", "is_helpful", "persist_date", "update_date", "question_id", "user_id" )
VALUES (100, '2023-04-23 13:01:11.245126', 'html_body1', 'false', 'false', 'true', '2023-04-23 13:01:11.245126', '2023-04-23 13:01:11.245126', 102, 101),
       (101, '2023-04-23 13:01:11.245126', 'html_body2', 'false', 'false', 'true', '2023-04-23 13:01:11.245126', '2023-04-23 13:01:11.245126', 101, 103),
       (102, '2023-04-23 13:01:11.245126', 'html_body3', 'false', 'false', 'true', '2023-04-23 13:01:11.245126', '2023-04-23 13:01:11.245126', 101, 102),
       (103, '2023-04-23 13:01:11.245126', 'html_body4', 'false', 'false', 'true', '2023-04-23 13:01:11.245126', '2023-04-23 13:01:11.245126', 101, 101);

INSERT INTO "votes_on_questions"
("id", "persist_date", "vote", "question_id", "user_id")
VALUES (100, '2023-04-23 13:01:11.245126', 'up', 101, 101),
       (101, '2023-04-23 13:01:11.245126', 'up', 101, 103),
       (102, '2023-04-23 13:01:11.245126', 'up', 101, 102),
       (103, '2023-04-23 13:01:11.245126', 'down', 101, 101);

INSERT INTO "reputation"
("id", "count", "persist_date", "type", "answer_id", "author_id", "question_id", "sender_id")
VALUES (100, 1, '2023-04-23 15:21:03.527867', 2, 101, 100, 102, 104),
       (101, 2, '2023-04-23 15:21:03.527867', 2, 101, 100, 102, 102),
       (102, 3, '2023-04-23 15:21:03.527867', 2, 103, 100, 103, 103),
       (103, 4, '2023-04-23 15:21:03.527867', 2, 103, 101, 103, 101),
       (104, 5, '2023-04-23 15:21:03.527867', 2, 102, 102, 102, 100),
       (105, 6, '2023-04-23 15:21:03.527867', 2, 102, 104, 103, 100),
       (106, 7, '2023-04-23 15:21:03.527867', 2, 102, 103, 104, 100);

INSERT INTO "question_viewed"
("id", "persist_date", "question_id", "user_id")
VALUES (100, '22023-04-23 15:21:03.527867', 101, 101),
       (101, '2023-04-23 15:21:03.527867', 102, 104),
       (102, '2023-04-23 15:21:03.527867', 101, 103),
       (103, '2023-04-23 15:21:03.527867', 102, 103),
       (104, '2023-04-23 15:21:03.527867', 103, 102);

