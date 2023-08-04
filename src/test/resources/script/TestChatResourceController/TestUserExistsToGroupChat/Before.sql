TRUNCATE TABLE chat CASCADE;
TRUNCATE TABLE role CASCADE;


INSERT INTO role (id, name)
VALUES (111, 'ROLE_USER');

INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (101, 'Its me', 'Saint-Petersburg', 'email101@mail.com', 'Alex Vasiliev', 'No link', false, true, now(),
        'No link',
        'No link', 'No link', 'Dragonfly',
        '$2a$12$T7v5H.rrJpHxINU6W09McOqhWLC1b6WObgr9GmKR3df22DlQ67sj6', now(), 111);


INSERT INTO chat(id, title, persist_date, chat_type)
VALUES (110, 'first_chat', '2000-01-01', 1);

INSERT INTO group_chat(chat_id, image, is_global)
VALUES (110, 'first_image', false);

INSERT INTO groupchat_has_users(chat_id, user_id)
VALUES (110, 101);
