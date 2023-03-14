TRUNCATE TABLE role CASCADE;

INSERT INTO role (id, name)
VALUES (111, 'ROLE_USER');

-- password = 'test'
INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date, link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (101, 'Its me', 'Saint-Petersburg', 'test101@mail.ru', 'Alex Vasiliev', 'No link', false, true, now(), 'No link', 'No link', 'No link', 'Dragonfly',
        '$2a$12$axPyq0kTyPAYggs3zdoAQePKfcErnvZrwqPR.3ijEqj8qukox8Zly', now(), 111);
INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date, link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (102, 'Its not me', 'Saint-Petersburg', 'test102@mail.ru', 'NeAlex NeVasiliev', 'No link', false, true, now(), 'No link', 'No link', 'No link', 'NeDragonfly',
        '$2a$12$axPyq0kTyPAYggs3zdoAQePKfcErnvZrwqPR.3ijEqj8qukox8Zly', now(), 111);

INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (222, 'Question 1', false, now(), now(), 'Question', 101);

INSERT INTO answer (id, date_accept_time, html_Body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date, update_date, question_id, user_id)
VALUES (333, now(), 'comment', false, false, true, now(), now(), 222, 101);

INSERT INTO reputation (id, count, persist_date, type, answer_id, author_id, question_id, sender_id)
VALUES (444, 10, now(), 1, 333, 101, 222, 102);
INSERT INTO reputation (id, count, persist_date, type, answer_id, author_id, question_id, sender_id)
VALUES (555, 10, now(), 1, 333, 101, 222, 102);