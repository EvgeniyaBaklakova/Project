INSERT INTO role (id, name)
VALUES (111, 'ROLE_USER');
INSERT INTO role (id, name)
VALUES (222, 'ROLE_ADMIN');

INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date, link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (101, 'Its me', 'Saint-Petersburg', 'test101@mail.ru', 'Alex Vasiliev', 'No link', false, true, now(), 'No link', 'No link', 'No link', 'Dragonfly',
        '$2a$12$JJHilheQZae2WMfLHwgElOtsFgMLqxve3.v1CyxVvyhiOl.l2feVK', now(), 111);
INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date, link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (102, 'Its not me', 'Saint-Petersburg', 'test102@mail.ru', 'NeAlex NeVasiliev', 'No link', false, true, now(), 'No link', 'No link', 'No link', 'NeDragonfly',
        '$2a$12$JJHilheQZae2WMfLHwgElOtsFgMLqxve3.v1CyxVvyhiOl.l2feV', now(), 111);

INSERT INTO chat(id, title, persist_date)
VALUES (3, 'first_chat', '2000-01-23'),
       (2, 'second_chat', '2000-02-01');


INSERT INTO group_chat(chat_id, image, is_global)
VALUES (3, 'first_image', true),
       (2, 'second_image', true);

INSERT INTO groupchat_has_users(chat_id, user_id)
VALUES (3, 101),
       (3, 102),
       (2, 102),
       (2, 101);

INSERT INTO message(id, message, last_redaction_date, persist_date, user_sender_id, chat_id)
VALUES (4, 'first_message', '2023-03-01', '2023-01-02', 101, 3),
       (2, 'last_message', '2023-03-01', '2023-01-03', 102, 3);