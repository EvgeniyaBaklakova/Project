/*
 *  Table: role
 */
INSERT INTO role (id, name)
VALUES (101, 'ROLE_ADMIN'),
       (102, 'ROLE_USER');

/*
 *  Table: user_entity
 */
INSERT INTO user_entity (id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (101, 'I am test user', 'Moscow', 'user101@mail.ru', 'User 101', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_101', '$2a$12$2640K3SYLeFzXVZjLXOYO.od1JuDbPntVrnG1IkiNTNc4.3ZbhqvC', '2003-05-25', 102),

        (102, 'I am test user', 'Moscow', 'user102@mail.ru', 'User 102', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_102', '$2a$12$9dF54oXy1UTGPmuuxc1LUOWnkeETVoFuomzL.ip3YA1BqdLqP3.se', '2001-05-25', 101),

        (103, 'I am test user', 'Moscow', 'user103@mail.ru', 'User 103', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_103', '$2a$12$9dF54oXy1UTGPmuuxc1LUOWnkeETVoFuomzL.ip3YA1BqdLqP3.se', '2002-05-25', 101),

        (104, 'I am test user', 'Moscow', 'user104@mail.ru', 'User 104', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_104', '$2a$12$9dF54oXy1UTGPmuuxc1LUOWnkeETVoFuomzL.ip3YA1BqdLqP3.se', '2000-05-25', 101),

        (105, 'I am test user', 'Moscow', 'user105@mail.ru', 'User 105', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_105', '$2a$12$9dF54oXy1UTGPmuuxc1LUOWnkeETVoFuomzL.ip3YA1BqdLqP3.se', '2004-05-25', 101),

        (106, 'I am test user', 'Moscow', 'user106@mail.ru', 'User 106', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_106', '$2a$12$9dF54oXy1UTGPmuuxc1LUOWnkeETVoFuomzL.ip3YA1BqdLqP3.se', '2005-05-25', 102),

        (107, 'I am test user', 'Moscow', 'user107@mail.ru', 'User 107', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_107', '$2a$12$9dF54oXy1UTGPmuuxc1LUOWnkeETVoFuomzL.ip3YA1BqdLqP3.se', '2006-05-25', 102),

        (108, 'I am test user', 'Moscow', 'user108@mail.ru', 'User 108', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_108', '$2a$12$9dF54oXy1UTGPmuuxc1LUOWnkeETVoFuomzL.ip3YA1BqdLqP3.se', '2008-05-25', 102),

        (109, 'I am test user', 'Moscow', 'user109@mail.ru', 'User 109', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_109', '$2a$12$9dF54oXy1UTGPmuuxc1LUOWnkeETVoFuomzL.ip3YA1BqdLqP3.se', '2007-05-25', 102),

        (110, 'I am test user', 'Moscow', 'user110@mail.ru', 'User 110', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_110', '$2a$12$9dF54oXy1UTGPmuuxc1LUOWnkeETVoFuomzL.ip3YA1BqdLqP3.se', '2009-05-25', 102),

        (111, 'I am test user', 'Moscow', 'user111@mail.ru', 'User 111', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_102', '$2a$12$9dF54oXy1UTGPmuuxc1LUOWnkeETVoFuomzL.ip3YA1BqdLqP3.se', '2010-05-25', 101),

        (112, 'I am test user', 'Moscow', 'user112@mail.ru', 'User 112', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_103', '$2a$12$9dF54oXy1UTGPmuuxc1LUOWnkeETVoFuomzL.ip3YA1BqdLqP3.se', '2009-05-25', 101),

        (113, 'I am test user', 'Moscow', 'user113@mail.ru', 'User 113', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_104', '$2a$12$9dF54oXy1UTGPmuuxc1LUOWnkeETVoFuomzL.ip3YA1BqdLqP3.se', '2012-05-25', 101),

        (114, 'I am test user', 'Moscow', 'user114@mail.ru', 'User 114', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_105', '$2a$12$9dF54oXy1UTGPmuuxc1LUOWnkeETVoFuomzL.ip3YA1BqdLqP3.se', '2013-05-25', 101),

        (115, 'I am test user', 'Moscow', 'user115@mail.ru', 'User 115', '/images/noUserAvatar.png', false, true,
        DATE(NOW()),
        null, null, null, 'user_106', '$2a$12$9dF54oXy1UTGPmuuxc1LUOWnkeETVoFuomzL.ip3YA1BqdLqP3.se', '2014-05-25', 102),

        (116, 'I am test user', 'Moscow', 'user116@mail.ru', 'User 116', '/images/noUserAvatar.png', false, true,
        '2015-05-25',
        null, null, null, 'user_107', '$2a$12$9dF54oXy1UTGPmuuxc1LUOWnkeETVoFuomzL.ip3YA1BqdLqP3.se', '2015-05-25', 102),

        (117, 'I am test user', 'Moscow', 'user117@mail.ru', 'User 117', '/images/noUserAvatar.png', false, true,
        '2017-05-25',
        null, null, null, 'user_108', '$2a$12$9dF54oXy1UTGPmuuxc1LUOWnkeETVoFuomzL.ip3YA1BqdLqP3.se', '2020-05-25', 102),

        (118, 'I am test user', 'Moscow', 'user118@mail.ru', 'User 118', '/images/noUserAvatar.png', false, true,
        '2016-05-25',
        null, null, null, 'user_109', '$2a$12$9dF54oXy1UTGPmuuxc1LUOWnkeETVoFuomzL.ip3YA1BqdLqP3.se', '2018-05-25', 102),

        (119, 'I am test user', 'Moscow', 'user119@mail.ru', 'User 119', '/images/noUserAvatar.png', false, true,
        '2016-05-25',
        null, null, null, 'user_110', '$2a$12$9dF54oXy1UTGPmuuxc1LUOWnkeETVoFuomzL.ip3YA1BqdLqP3.se', '2019-05-25', 102),

        (120, 'I am test user', 'Moscow', 'user120@mail.ru', 'User 120', '/images/noUserAvatar.png', false, true,
        '2016-05-25',
        null, null, null, 'user_110', '$2a$12$9dF54oXy1UTGPmuuxc1LUOWnkeETVoFuomzL.ip3YA1BqdLqP3.se', '2021-05-24', 102),

        (121, 'I am test user', 'Moscow', 'user121@mail.ru', 'User 121', '/images/noUserAvatar.png', false, true,
        '2016-05-25',
        null, null, null, 'user_110', '$2a$12$9dF54oXy1UTGPmuuxc1LUOWnkeETVoFuomzL.ip3YA1BqdLqP3.se', '2021-05-25', 102);

/*
 *  Table: question
 */
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (101, 'What do you think about question 101?', false, DATE(NOW()), DATE(NOW()), 'Question 101', 101),
       (102, 'What do you think about question 102?', false, DATE(NOW()), DATE(NOW()), 'Question 102', 102),
       (103, 'What do you think about question 103?', false, DATE(NOW()), DATE(NOW()), 'Question 103', 103),
       (104, 'What do you think about question 104?', false, DATE(NOW()), DATE(NOW()), 'Question 104', 104);

/*
 *  Table: tag
 */
INSERT INTO tag (id, description, name, persist_date)
VALUES (101, 'Description of tag 1', 'vfOxMU1', '2022-11-12 22:09:06.639083'),
       (102, 'Description of tag 2', 'iThKcj2', '2022-11-12 22:09:06.639579'),
       (103, 'Description of tag 3', 'LTGDJP3', '2022-11-12 22:09:06.639579'),

       (104, 'Description of tag 4', 'vfOxMU4', '2022-11-12 22:09:06.639083'),
       (105, 'Description of tag 5', 'iThKcj5', '2022-11-12 22:09:06.639579'),
       (106, 'Description of tag 6', 'LTGDJP6', '2022-11-12 22:09:06.639579'),

       (107, 'Description of tag 7', 'vfOxMU7', '2022-11-12 22:09:06.639083'),
       (108, 'Description of tag 8', 'iThKcj8', '2022-11-12 22:09:06.639579'),
       (109, 'Description of tag 9', 'LTGDJP9', '2022-11-12 22:09:06.639579'),

       (110, 'Description of tag 10', 'vfOxMU10', '2022-11-12 22:09:06.639083'),
       (111, 'Description of tag 11', 'iThKcj11', '2022-11-12 22:09:06.639579'),
       (112, 'Description of tag 12', 'LTGDJP12', '2022-11-12 22:09:06.639579');

/*
 *  Table: question_has_tag
 */
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (101, 101),
       (101, 102),
       (101, 103),

       (102, 104),
       (102, 105),
       (102, 106),

       (103, 107),
       (103, 108),
       (103, 109),

       (104, 110),
       (104, 111),
       (104, 112);

/*
 *  Table: reputation
 */
INSERT INTO reputation (id, count, persist_date, type, answer_id, author_id, question_id, sender_id)
VALUES (101, 1000, DATE(NOW()), 2, null, 101, 101, null),
       (102, 900, DATE(NOW()), 3, null, 102, 102, null),
       (103, 8000, DATE(NOW()), 2, null, 103, 103, null),
       (104, 700, DATE(NOW()), 3, null, 104, 104, null),
       (105, 600, DATE(NOW()), 2, null, 105, 101, null),
       (106, 500, DATE(NOW()), 3, null, 106, 102, null),
       (107, 400, DATE(NOW()), 2, null, 107, 103, null),
       (108, 300, DATE(NOW()), 3, null, 108, 104, null),
       (109, 2000, DATE(NOW()), 2, null, 109, 101, null),
       (110, 100, DATE(NOW()), 3, null, 110, 102, null);