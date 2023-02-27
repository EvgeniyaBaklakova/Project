TRUNCATE TABLE role CASCADE;

INSERT INTO role (id, name)
VALUES (100, 'ROLE_USER');

-- password = 'test'
INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date, link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (100, 'Its me', 'Saint-Petersburg', 'test100@mail.ru', 'Alex Vasiliev', 'No link', false, true, now(), 'No link', 'No link', 'No link', 'Dragonfly',
        '$2a$12$axPyq0kTyPAYggs3zdoAQePKfcErnvZrwqPR.3ijEqj8qukox8Zly', now(), 100);

INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (100, 'Question 1', false, now(), now(), 'Question', 100);

INSERT INTO answer (id, date_accept_time, html_Body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date, update_date, question_id, user_id)
VALUES (100, now(), 'comment', false, false, true, now(), now(), 100, 100);

INSERT INTO reputation (id, count, persist_date, type, answer_id, author_id, question_id, sender_id)
VALUES (100, 666, now(), 1, 100, 100, 100, 100);