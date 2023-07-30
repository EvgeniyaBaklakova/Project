TRUNCATE TABLE chat CASCADE;
TRUNCATE TABLE role CASCADE;

INSERT INTO role (id, name)
VALUES (111, 'ROLE_USER'),
       (222, 'ROLE_ADMIN');

INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (101, 'Its me', 'Saint-Petersburg', 'test101@mail.ru', 'Alex Vasiliev', 'No link', false, true, now(), 'No link',
        'No link', 'No link', 'Dragonfly',
        '$2a$12$JJHilheQZae2WMfLHwgElOtsFgMLqxve3.v1CyxVvyhiOl.l2feVK', now(), 111),
       (102, 'Its not me', 'Saint-Petersburg', 'test102@mail.ru', 'NeAlex NeVasiliev', 'No link', false, true, now(),
        'No link', 'No link', 'No link', 'NeDragonfly',
        '$2a$12$JJHilheQZae2WMfLHwgElOtsFgMLqxve3.v1CyxVvyhiOl.l2feV', now(), 111),
       (103, 'Its not me', 'Saint-Petersburg', 'test103@mail.ru', 'John Doe', 'No link', false, true, now(), 'No link',
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
       (112, 102),
       (112, 103);

INSERT INTO message(id, message, last_redaction_date, persist_date, user_sender_id, chat_id)
VALUES (11, '2023-01-01_message', '2023-02-01', '2023-01-01', 101, 111),
       (12, '2023-01-02_message', '2023-03-01', '2023-01-02', 102, 111),
       (13, '2023-01-03_last_message_first_chat', '2023-04-01', '2023-01-03', 102, 111),
       (14, '2023-01-01_message', '2023-02-01', '2023-01-01', 102, 112),
       (15, '2023-01-02_message', '2023-03-01', '2023-01-02', 101, 112),
       (16, '2023-01-03_message', '2023-04-01', '2023-01-03', 103, 112),
       (17, '2023-01-04_message', '2023-05-01', '2023-01-04', 103, 112),
       (18, '2023-01-05_last_message_second_chat', '2023-06-05', '2023-01-05', 102, 112),
       (19, '2023-01-10_message', '2023-11-01', '2023-01-10', 103, 113),
       (20, '2023-01-11_last_message_third_chat', '2023-12-01', '2023-01-11', 101, 113);