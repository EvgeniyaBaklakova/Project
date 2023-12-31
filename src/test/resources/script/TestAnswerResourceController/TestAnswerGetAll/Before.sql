TRUNCATE TABLE role CASCADE;
INSERT INTO role (id, name)
VALUES (101, 'ROLE_USER');


INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date, link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (101,
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
        '$2y$12$Nkswy9kzGNJlhxscP8O1sefMN3xYUwoAJ/ynqRudb3YPSx/dFM9v6',
        DATE(now()),
        101);

INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date, link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (102,
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
        '$2y$12$Nkswy9kzGNJlhxscP8O1sefMN3xYUwoAJ/ynqRudb3YPSx/dFM9v6',
        DATE(now()),
        101);

INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (102,
        'Question',
        false,
        DATE(now()),
        DATE(now()),
        'Title',
        101);

INSERT INTO answer(id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date, update_date, question_id, user_id)
VALUES(101,
       DATE(now()),
       'text',
       false,
       false,
       false,
       DATE(now()),
       DATE(now()),
       102,
       101);

INSERT INTO answer(id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date, update_date, question_id, user_id)
VALUES(102,
       DATE(now()),
       'text',
       true,
       false,
       false,
       DATE(now()),
       DATE(now()),
       102,
       102);

INSERT INTO reputation (id, count, persist_date, type, answer_id, author_id, question_id, sender_id)
VALUES (444, 10, now(), 1, 101, 101, 102, 102);

INSERT INTO reputation (id, count, persist_date, type, answer_id, author_id, question_id, sender_id)
VALUES (555, 10, now(), 1, 101, 101, 102, 102);

INSERT INTO reputation (id, count, persist_date, type, answer_id, author_id, question_id, sender_id)
VALUES (666, 10, now(), 1, 102, 101, 102, 102);

INSERT INTO votes_on_answers (id, persist_date, vote, answer_id, user_id)
VALUES (1, now(), 'UP_VOTE', 102, 102), (2, now(), 'DOWN_VOTE', 102, 102)