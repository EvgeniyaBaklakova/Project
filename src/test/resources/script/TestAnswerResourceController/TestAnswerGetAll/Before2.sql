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