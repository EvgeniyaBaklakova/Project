TRUNCATE TABLE chat CASCADE;
TRUNCATE TABLE role CASCADE;

INSERT INTO role (id, name)
VALUES (100, 'ROLE_USER');

INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (123, 'about', 'USA', 'test123@mail.ru', 'Martin Eden', 'No link', false, true, now(), 'No link',
        'No link', 'No link', 'Marty',
        '$2a$12$1D4yyooUkkhMoAEfrDsfZeqI.Th60hBmidMM39h4uYyiXiFnSWsMm', now(), 100),
       (124, 'about', 'France', 'test124@mail.ru', 'Asterix Le Gaulois', 'No link', false, true, now(),
        'No link', 'No link', 'No link', 'Dogmatix',
        '$2a$12$1D4yyooUkkhMoAEfrDsfZeqI.Th60hBmidMM39h4uYyiXiFnSWsMm', now(), 100);


INSERT INTO chat(id, chat_type, persist_date, title)
VALUES (55, 0, '2023-01-01', 'single_chat1');

INSERT INTO single_chat(chat_id, use_two_id, user_one_id)
VALUES (55, 123, 124);



INSERT INTO message(id, last_redaction_date, message, persist_date, chat_id, user_sender_id)
VALUES (1, '2023-02-01', 'single_chat_message1', '2023-01-01', 55, 123),
       (2, '2023-03-01', 'single_chat_message2', '2023-01-02', 55, 124),
       (3, '2023-04-01', 'single_chat_message3', '2023-01-03', 55, 124),
       (4, '2023-05-01', 'single_chat_message4', '2023-01-04', 55, 123),
       (5, '2023-06-01', 'single_chat_message5', '2023-01-05', 55, 124),
       (6, '2023-07-01', 'single_chat_message6', '2023-01-06', 55, 123),
       (7, '2023-08-01', 'single_chat_message7', '2023-01-07', 55, 123),
       (8, '2023-09-01', 'single_chat_message8', '2023-01-08', 55, 124),
       (9, '2023-10-01', 'single_chat_message9', '2023-01-09', 55, 123),
       (10, '2023-11-01', 'single_chat_message10', '2023-01-10', 55, 124),
       (11, '2023-12-01', 'single_chat_message11', '2023-01-11', 55, 124),
       (12, '2023-02-11', 'single_chat_message12', '2023-01-12', 55, 123),
       (13, '2023-02-12', 'single_chat_message13', '2023-01-13', 55, 124),
       (14, '2023-02-13', 'single_chat_message14', '2023-01-14', 55, 123),
       (15, '2023-02-14', 'single_chat_message15', '2023-01-15', 55, 123);

