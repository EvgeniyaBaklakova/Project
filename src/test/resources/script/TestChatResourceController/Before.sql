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
VALUES (55, 0, '2023-01-01', 'single_chat');

INSERT INTO single_chat(chat_id, use_two_id, user_one_id)
VALUES (55, 123, 124);


INSERT INTO message(id, last_redaction_date, message, persist_date, chat_id, user_sender_id)
VALUES (1, '2023-02-01', 'single_chat_message1', '2023-01-01', 55, 123),
       (2, '2023-03-01', 'single_chat_message2', '2023-01-02', 55, 124);

