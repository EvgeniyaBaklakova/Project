TRUNCATE table role cascade ;
TRUNCATE table tag cascade ;

INSERT INTO role(id, name) values (111, 'ROLE_USER');

INSERT INTO user_entity(id, about, city, email, full_name, image_link, is_deleted, is_enabled, last_redaction_date, link_github, link_site, link_vk, nickname, password, persist_date, role_id)
values (101, 'user', 'mycity', 'myemail@mail.ru', 'name', 'link', false, true, now(), 'githublink', 'sitelink', 'vklink', 'nick', '$2a$12$Pk1RYUabjogz7ac4gGhDqur3RiKu9qKgaup3XtUW6k85wqQiWS8z6', now(), 111);

INSERT into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (201, 'hello and', false, now(), now(), 'question1', 101);
INSERT into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (202, 'hello and welcome', false, now(), now(), 'question2', 101);
INSERT into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (203, 'scratch', false, now(), now(), 'question3', 101);
INSERT into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (204, 'problem', false, now(), now(), 'question4', 101);
INSERT into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (205, 'java problem', false, now(), now(), 'no problem', 101);
INSERT into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (206, 'spring problem', false, now(), now(), 'question6', 101);
INSERT into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (207, 'mysql ex', false, now(), now(), 'question7', 101);
INSERT into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (208, 'three', false, now(), now(), 'java', 101);
INSERT into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (209, 'four', false, now(), now(), 'question9', 101);
INSERT into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (210, 'five and', false, now(), now(), 'question1', 101);
INSERT into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (211, 'bye and welcome', false, now(), now(), 'question2', 101);
INSERT into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (212, 'follow us', false, now(), now(), 'question3', 101);
INSERT into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (213, 'twitter', false, now(), now(), 'question4', 101);
INSERT into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (214, 'postgres problem', false, now(), now(), 'no problem', 101);
INSERT into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (215, 'spring zero problem', false, now(), now(), 'question6', 101);
INSERT into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (216, 'hello world', false, now(), now(), 'question7', 101);
INSERT into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (217, 'dont worry be happy', false, now(), now(), 'java', 101);
INSERT into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (218, 'do did does', false, now(), now(), 'question9', 101);


INSERT into answer(id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date, update_date, question_id, user_id)
values(301, now(), '', false, false, false, now(), now(), 201, 101);
INSERT into answer(id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date, update_date, question_id, user_id)
values(302, now(), '', false, false, false, now(), now(), 201, 101);
INSERT into answer(id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date, update_date, question_id, user_id)
values(303, now(), '', false, false, false, now(), now(), 201, 101);
INSERT into answer(id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date, update_date, question_id, user_id)
values(304, now(), '', false, false, false, now(), now(), 202, 101);
INSERT into answer(id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date, update_date, question_id, user_id)
values(305, now(), '', false, false, false, now(), now(), 202, 101);
INSERT into answer(id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date, update_date, question_id, user_id)
values(306, now(), '', false, false, false, now(), now(), 203, 101);
INSERT into answer(id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date, update_date, question_id, user_id)
values(307, now(), '', false, false, false, now(), now(), 203, 101);
INSERT into answer(id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date, update_date, question_id, user_id)
values(308, now(), '', false, false, false, now(), now(), 204, 101);
INSERT into answer(id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date, update_date, question_id, user_id)
values(309, now(), '', false, false, false, now(), now(), 205, 101);

insert into tag(id, description, name, persist_date) values (401, 'spring', 'spring', now());
insert into tag(id, description, name, persist_date) values (402, 'java', 'java', now());
insert into tag(id, description, name, persist_date) values (403, 'gradle', 'gradle', now());
insert into tag(id, description, name, persist_date) values (404, 'hibernate', 'hibernate', now());
insert into tag(id, description, name, persist_date) values (405, 'mockito', 'mockito', now());

insert into question_has_tag(question_id, tag_id) values (201, 401);
insert into question_has_tag(question_id, tag_id) values (201, 402);
insert into question_has_tag(question_id, tag_id) values (201, 403);
insert into question_has_tag(question_id, tag_id) values (206, 401);
insert into question_has_tag(question_id, tag_id) values (206, 403);
insert into question_has_tag(question_id, tag_id) values (206, 404);
insert into question_has_tag(question_id, tag_id) values (207, 404);
insert into question_has_tag(question_id, tag_id) values (208, 401);
insert into question_has_tag(question_id, tag_id) values (208, 405);
insert into question_has_tag(question_id, tag_id) values (209, 401);
insert into question_has_tag(question_id, tag_id) values (209, 405);
insert into question_has_tag(question_id, tag_id) values (210, 404);
insert into question_has_tag(question_id, tag_id) values (210, 401);
insert into question_has_tag(question_id, tag_id) values (210, 405);
insert into question_has_tag(question_id, tag_id) values (211, 401);
insert into question_has_tag(question_id, tag_id) values (211, 402);
insert into question_has_tag(question_id, tag_id) values (211, 403);
insert into question_has_tag(question_id, tag_id) values (212, 401);
insert into question_has_tag(question_id, tag_id) values (212, 402);
insert into question_has_tag(question_id, tag_id) values (213, 405);
insert into question_has_tag(question_id, tag_id) values (213, 404);
insert into question_has_tag(question_id, tag_id) values (214, 401);
insert into question_has_tag(question_id, tag_id) values (215, 405);
insert into question_has_tag(question_id, tag_id) values (215, 401);
insert into question_has_tag(question_id, tag_id) values (216, 405);



insert into reputation(id, count, persist_date, type, answer_id, author_id, question_id, sender_id)
VALUES (701, 100500, now(), 1, 301, 101, 201, null);

insert into question_viewed(id, persist_date, question_id, user_id) values (901, now(), 201, 101);
insert into question_viewed(id, persist_date, question_id, user_id) values (902, now(), 203, 101);
insert into question_viewed(id, persist_date, question_id, user_id) values (903, now(), 205, 101);
insert into question_viewed(id, persist_date, question_id, user_id) values (904, now(), 207, 101);
insert into question_viewed(id, persist_date, question_id, user_id) values (905, now(), 209, 101);

insert into votes_on_questions(id, persist_date, vote, question_id, user_id) values (1001, now(), 'UP_VOTE', 201, 101);
insert into votes_on_questions(id, persist_date, vote, question_id, user_id) values (1002, now(), 'UP_VOTE', 203, 101);
insert into votes_on_questions(id, persist_date, vote, question_id, user_id) values (1003, now(), 'UP_VOTE', 205, 101);
insert into votes_on_questions(id, persist_date, vote, question_id, user_id) values (1004, now(), 'UP_VOTE', 207, 101);
insert into votes_on_questions(id, persist_date, vote, question_id, user_id) values (1005, now(), 'UP_VOTE', 209, 101);