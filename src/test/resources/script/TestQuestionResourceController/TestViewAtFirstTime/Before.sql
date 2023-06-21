TRUNCATE TABLE role CASCADE;
TRUNCATE TABLE user_entity CASCADE;
TRUNCATE TABLE question CASCADE;

INSERT INTO role (id, name)
VALUES (101, 'ROLE_USER');

-- password = '123'
INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date, link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (101, 'Its me', 'Saint-Petersburg', 'email1@mail.com', 'Alex Vasiliev', 'No link', false, true, TIMESTAMP '2002-02-05', 'No link', 'No link', 'No link', 'Dragonfly',
        '$2a$12$s0Wza9gM5VR3jhmTm7p.m.5gZp8fqqEqnoruvChNqJIlQpE3y6NH.', null, 101);

INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date, link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (102, 'Its me', 'Saint-Petersburg', 'email2@mail.com', 'Alex Vasiliev', 'No link', false, true, TIMESTAMP '2002-02-05', 'No link', 'No link', 'No link', 'Dragonfly',
        '$2a$12$s0Wza9gM5VR3jhmTm7p.m.5gZp8fqqEqnoruvChNqJIlQpE3y6NH.', null, 101);

INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (101, 'Question 1', false, now(), now(), 'Question', 101);