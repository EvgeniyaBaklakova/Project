TRUNCATE TABLE role CASCADE;
TRUNCATE TABLE user_entity CASCADE;
TRUNCATE TABLE question CASCADE;

INSERT INTO role (id, name)
VALUES (1, 'ROLE_USER');

-- password = 'test'
INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date, link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (1, 'Its me', 'Saint-Petersburg', 'email1@mail.com', 'Alex Vasiliev', 'No link', false, true, TIMESTAMP '2002-02-05', 'No link', 'No link', 'No link', 'Dragonfly',
        '$2a$12$mKo19e3YixX2BBKv8TinaeZi.BNqWUW5A7lWwIYMk/3gFLy3KJViO', null, 1);

INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (1, 'Question 1', false, now(), now(), 'Question', 1);

INSERT INTO question_viewed(id, user_id, question_id, persist_date)
VALUES (1, 1, 1, now());