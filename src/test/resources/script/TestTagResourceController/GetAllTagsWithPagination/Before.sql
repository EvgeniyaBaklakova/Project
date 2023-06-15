TRUNCATE TABLE role CASCADE;
TRUNCATE TABLE tag CASCADE;

INSERT INTO role (id, name)
VALUES (111, 'ROLE_USER');

-- password = 'test'
INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date, link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (101, 'Its me', 'Saint-Petersburg', 'test101@mail.ru', 'Alex Vasiliev', 'No link', false, true, now(), 'No link', 'No link', 'No link', 'Dragonfly',
        '$2a$12$axPyq0kTyPAYggs3zdoAQePKfcErnvZrwqPR.3ijEqj8qukox8Zly', now(), 111);

INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (201, 'Question 1', false, now(), now(), 'Question q1', 101);
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (202, 'Question 2', false, now(), now(), 'Question q2', 101);
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (203, 'Question 3', false, now(), now(), 'Question q3', 101);
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (204, 'Question 4', false, now(), now(), 'Question q4', 101);
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (205, 'Question 5', false, now(), now(), 'Question q5', 101);
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (206, 'Question 6', false, now(), now() - interval '2 days', 'Question q6', 101);
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (207, 'Question 7', false, now(), now() - interval '2 days', 'Question q7', 101);
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (208, 'Question 8', false, now(), now() - interval '10 days', 'Question q8', 101);
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (209, 'Question 9', false, now(), now() - interval '11 days', 'Question q9', 101);
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (210, 'Question 10', false, now(), now() - interval '12 days', 'Question q10', 101);


INSERT INTO tag (id, description, name, persist_date)
VALUES (301, 'tag1', 'tag t1', now());
INSERT INTO tag (id, description, name, persist_date)
VALUES (302, 'tag2', 'tag t2', now() - interval '2 hours');
INSERT INTO tag (id, description, name, persist_date)
VALUES (303, 'tag3', 'tag t3', now() - interval '3 hours');
INSERT INTO tag (id, description, name, persist_date)
VALUES (304, 'tag4', 'tag t4', now() - interval '4 hours');
INSERT INTO tag (id, description, name, persist_date)
VALUES (305, 'tag5', 'tag t5', now() - interval '5 hours');


INSERT INTO question_has_tag (question_id, tag_id)
VALUES (201, 301);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (202, 301);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (203, 301);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (204, 301);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (205, 301);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (206, 301);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (207, 301);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (208, 301);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (209, 301);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (210, 301);


INSERT INTO question_has_tag (question_id, tag_id)
VALUES (201, 302);

INSERT INTO question_has_tag (question_id, tag_id)
VALUES (201, 303);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (202, 303);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (203, 303);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (208, 303);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (209, 303);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (210, 303);


INSERT INTO question_has_tag (question_id, tag_id)
VALUES (201, 304);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (210, 304);

INSERT INTO question_has_tag (question_id, tag_id)
VALUES (201, 305);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (202, 305);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (203, 305);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (204, 305);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (205, 305);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (206, 305);


