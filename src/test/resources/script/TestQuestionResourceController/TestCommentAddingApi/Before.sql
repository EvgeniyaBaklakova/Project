insert into role(id, name) VALUES (1, 'ROLE_USER');

INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date, link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (1, 'Its me', 'Saint-Petersburg', 'email1@mail.com', 'Alex Vasiliev', 'No link', false, true, now(), 'No link', 'No link', 'No link', 'Dragonfly',
        '$2a$12$9NHLrlqZFoSfe73LKB4Y4.2iOUDnMNfUoIq6i0ulr1uKaCKsOycBy', now(), 1);

insert into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (1, 'desc', false, now(), now(), 'title', 1);

