truncate table role cascade;

insert into role(id, name) VALUES (101, 'ROLE_USER');

insert into user_entity(id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date, link_github, link_site, link_vk, nickname, password, persist_date, role_id)
values (111, 'user1', 'city1', 'email@mail.ru', 'name1', 'link1', false, true, now(), 'link1', 'link1', 'link1', 'nick1', '$2a$12$K0qq5P4VN44aZhwsh8sro.HE6zmooTzQvQGG/6XCr5TdUge3gPle2', now(), 101);
insert into user_entity(id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date, link_github, link_site, link_vk, nickname, password, persist_date, role_id)
values (112, 'user2', 'city2', 'email2', 'name2', 'link2', false, true, now(), 'link2', 'link2', 'link2', 'nick2', '$2a$12$K0qq5P4VN44aZhwsh8sro.HE6zmooTzQvQGG/6XCr5TdUge3gPle2', now(), 101);

insert into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (201, 'question1', false, now(), now(), 'question1', 112);

insert into answer(id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date, update_date, question_id, user_id)
values (301, now(), 'html', false, false, true, now(), now(), 201, 112);



