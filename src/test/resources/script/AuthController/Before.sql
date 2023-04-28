TRUNCATE TABLE role CASCADE;
TRUNCATE TABLE user_entity CASCADE;

INSERT INTO role (id, name)
VALUES (111, 'ROLE_USER');

-- password = 'test'
INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date, link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (101, 'Its me', 'Saint-Petersburg', 'test101@mail.ru', 'Alex Vasiliev', 'No link', false, true, now(), 'No link', 'No link', 'No link', 'Dragonfly',
        '$2a$12$axPyq0kTyPAYggs3zdoAQePKfcErnvZrwqPR.3ijEqj8qukox8Zly', now(), 111);
