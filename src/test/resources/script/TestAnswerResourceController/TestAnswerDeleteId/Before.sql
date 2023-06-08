TRUNCATE TABLE role CASCADE;
INSERT INTO role (id, name)
VALUES (100, 'ROLE_USER');
INSERT INTO role (id, name)
VALUES (101, 'ROLE_ADMIN');


INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date, link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (100,
        'Its me',
        'Minsk',
        'test101@mail.ru',
        'Ivan Ivanov',
        'No link',
        false,
        true,
        DATE(now()),
        'No link',
        'No link',
        'No link',
        'Dragonfly',
        '$2a$12$axPyq0kTyPAYggs3zdoAQePKfcErnvZrwqPR.3ijEqj8qukox8Zly',
        DATE(now()),
        100);

INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date, link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (101,
        'Its not me',
        'Moscow',
        'test102@mail.ru',
        'Petr Petrov',
        'No link',
        false,
        true,
        DATE(now()),
        'No link',
        'No link',
        'No link',
        'NeDragonfly',
        '$2a$12$axPyq0kTyPAYggs3zdoAQePKfcErnvZrwqPR.3ijEqj8qukox8Zly',
        DATE(now()),
        101);

INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (100,
        'Question',
        false,
        DATE(now()),
        DATE(now()),
        'Title',
        100);

INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (101,
        'Question',
        false,
        DATE(now()),
        DATE(now()),
        'Title',
        101);
INSERT INTO bookmarks (id, question_id, user_id)
VALUES(2,
       100,
       100);

INSERT INTO bookmarks (id, question_id, user_id)
VALUES(3,
       101,
       101);

INSERT INTO answer(id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date, update_date, question_id, user_id)
VALUES(100,
       DATE(now()),
       'text',
       false,
       false,
       false,
       DATE(now()),
       DATE(now()),
       100,
       100);

INSERT INTO answer(id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date, update_date, question_id, user_id)
VALUES(101,
       DATE(now()),
       'text',
       true,
       false,
       false,
       DATE(now()),
       DATE(now()),
       101,
       101);

