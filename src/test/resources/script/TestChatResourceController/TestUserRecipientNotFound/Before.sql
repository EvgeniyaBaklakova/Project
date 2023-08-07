TRUNCATE TABLE chat CASCADE;
TRUNCATE TABLE single_chat CASCADE;
TRUNCATE TABLE message CASCADE;
TRUNCATE TABLE user_entity CASCADE;
TRUNCATE TABLE role CASCADE;


INSERT INTO role (id, name)
VALUES (111, 'ROLE_USER');

INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (101, 'Its me', 'Saint-Petersburg', 'email101@mail.com', 'Alex Vasiliev', 'No link', false, true, now(),
        'No link',
        'No link', 'No link', 'Dragonfly',
        '$2a$12$T7v5H.rrJpHxINU6W09McOqhWLC1b6WObgr9GmKR3df22DlQ67sj6', now(), 111),

       (2, 'Its me', 'Saint-Petersburg', 'email102@mail.com', 'Alex Vasiliev', 'No link', false, true, now(), 'No link',
        'No link', 'No link', 'Dragonfly',
        '$2a$12$H3mSgAzuJ/PcFQbD8G5QeOFkaevQ.q.rqvpFjuawVt26oxW8atJFm', now(), 111);

INSERT INTO chat (id, title, persist_date, chat_type)
VALUES (110, 'first_chat', '2000-01-01', 0);

INSERT INTO single_chat (chat_id, use_two_id, user_one_id)
VALUES (110, 2, 101);

INSERT INTO message (id, last_redaction_date, message, persist_date, chat_id, user_sender_id)
VALUES (1, now(), 'Привет', now(), 110, 101);
