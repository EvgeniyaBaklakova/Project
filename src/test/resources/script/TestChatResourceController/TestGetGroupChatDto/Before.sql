TRUNCATE TABLE chat CASCADE;
TRUNCATE TABLE role CASCADE;

INSERT INTO role (id, name)
VALUES (111, 'ROLE_USER');
INSERT INTO role (id, name)
VALUES (222, 'ROLE_ADMIN');

INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (101, 'Its me', 'Saint-Petersburg', 'test101@mail.ru', 'Alex Vasiliev', 'No link', false, true, now(), 'No link',
        'No link', 'No link', 'Dragonfly',
        '$2a$12$JJHilheQZae2WMfLHwgElOtsFgMLqxve3.v1CyxVvyhiOl.l2feVK', now(), 111);
INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (102, 'Its not me', 'Saint-Petersburg', 'test102@mail.ru', 'NeAlex NeVasiliev', 'No link', false, true, now(),
        'No link', 'No link', 'No link', 'NeDragonfly',
        '$2a$12$JJHilheQZae2WMfLHwgElOtsFgMLqxve3.v1CyxVvyhiOl.l2feV', now(), 111);
INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (103, 'Its not me', 'Saint-Petersburg', 'test103@mail.ru', 'John Doe', 'No link', false, true, now(), 'No link',
        'No link', 'No link', 'NeDragonfly',
        '$2a$12$JJHilheQZae2WMfLHwgElOtsFgMLqxve3.v1CyxVvyhiOl.l2feVK', now(), 222);

INSERT INTO chat(id, title, persist_date, chat_type)
VALUES (111, 'first_chat', '2000-01-01', 1),
       (112, 'second_chat', '2000-02-01', 1),
       (113, 'third_chat', '2000-03-01', 0);

INSERT INTO group_chat(chat_id, image, is_global)
VALUES (111, 'first_image', false),
       (112, 'second_image', false);

INSERT INTO groupchat_has_users(chat_id, user_id)
VALUES (111, 101),
       (111, 102),
       (112, 101),
       (112, 103);

INSERT INTO message(id, message, last_redaction_date, persist_date, user_sender_id, chat_id)
VALUES (11, 'first_message_111_chat', '2023-03-01', '2023-01-02', 101, 111),
       (12, 'second_message', '2023-03-01', '2023-01-03', 103, 113),
       (13, 'last_message_111_chat', '2023-03-01', '2023-01-03', 102, 111),
       (14, 'first_message_112_chat', '2023-03-01', '2023-01-03', 102, 112),
       (15, 'last_message_112_chat', '2023-03-01', '2023-01-03', 101, 112);

INSERT INTO user_chat_pin(id, persist_date, chat_id, user_id)
VALUES (201, '2000-01-01', 111, 101),
       (202, '2000-01-01', 111, 102),
       (203, '2000-01-01', 112, 102),
       (204, '2000-01-01', 112, 101),
       (205, '2000-01-01', 113, 101),
       (206, '2000-01-01', 113, 103);