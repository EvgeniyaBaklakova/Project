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
VALUES (206, 'Question 6', false, now(), now(), 'Question q6', 101);
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (207, 'Question 7', false, now(), now(), 'Question q7', 101);
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (208, 'Question 8', false, now(), now(), 'Question q8', 101);
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (209, 'Question 9', false, now(), now(), 'Question q9', 101);
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (210, 'Question 10', false, now(), now(), 'Question q10', 101);
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (211, 'Question 11', false, now(), now(), 'Question q11', 101);
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (212, 'Question 12', false, now(), now(), 'Question q12', 101);
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (213, 'Question 13', false, now(), now(), 'Question q13', 101);
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (214, 'Question 14', false, now(), now(), 'Question q14', 101);
INSERT INTO question (id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
VALUES (215, 'Question 15', false, now(), now(), 'Question q15', 101);

INSERT INTO tag (id, description, name, persist_date)
VALUES (301, 'tag1', 'tag t1', now());
INSERT INTO tag (id, description, name, persist_date)
VALUES (302, 'tag2', 'tag t2', now());
INSERT INTO tag (id, description, name, persist_date)
VALUES (303, 'tag3', 'tag t3', now());
INSERT INTO tag (id, description, name, persist_date)
VALUES (304, 'tag4', 'tag t4', now());
INSERT INTO tag (id, description, name, persist_date)
VALUES (305, 'tag5', 'tag t5', now());
INSERT INTO tag (id, description, name, persist_date)
VALUES (306, 'tag6', 'tag t6', now());
INSERT INTO tag (id, description, name, persist_date)
VALUES (307, 'tag7', 'tag t7', now());
INSERT INTO tag (id, description, name, persist_date)
VALUES (308, 'tag8', 'tag t8', now());
INSERT INTO tag (id, description, name, persist_date)
VALUES (309, 'tag9', 'tag t9', now());
INSERT INTO tag (id, description, name, persist_date)
VALUES (310, 'tag10', 'tag t10', now());
INSERT INTO tag (id, description, name, persist_date)
VALUES (311, 'tag11', 'tag t11', now());
INSERT INTO tag (id, description, name, persist_date)
VALUES (312, 'tag12', 'tag t12', now());
INSERT INTO tag (id, description, name, persist_date)
VALUES (313, 'tag13', 'tag t13', now());
INSERT INTO tag (id, description, name, persist_date)
VALUES (314, 'tag14', 'tag t14', now());
INSERT INTO tag (id, description, name, persist_date)
VALUES (315, 'tag15', 'tag t15', now());

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
VALUES (211, 301);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (212, 301);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (213, 301);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (214, 301);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (215, 301);

INSERT INTO question_has_tag (question_id, tag_id)
VALUES (201, 302);

INSERT INTO question_has_tag (question_id, tag_id)
VALUES (201, 303);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (202, 303);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (203, 303);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (204, 303);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (205, 303);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (206, 303);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (207, 303);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (208, 303);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (209, 303);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (210, 303);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (211, 303);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (212, 303);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (213, 303);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (214, 303);

INSERT INTO question_has_tag (question_id, tag_id)
VALUES (201, 304);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (202, 304);

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
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (207, 305);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (208, 305);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (209, 305);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (210, 305);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (211, 305);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (212, 305);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (213, 305);

INSERT INTO question_has_tag (question_id, tag_id)
VALUES (201, 306);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (202, 306);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (203, 306);

INSERT INTO question_has_tag (question_id, tag_id)
VALUES (201, 307);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (202, 307);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (203, 307);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (204, 307);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (205, 307);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (206, 307);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (207, 307);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (208, 307);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (209, 307);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (210, 307);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (211, 307);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (212, 307);

INSERT INTO question_has_tag (question_id, tag_id)
VALUES (201, 308);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (202, 308);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (203, 308);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (204, 308);

INSERT INTO question_has_tag (question_id, tag_id)
VALUES (201, 309);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (202, 309);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (203, 309);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (204, 309);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (205, 309);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (206, 309);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (207, 309);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (208, 309);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (209, 309);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (210, 309);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (211, 309);

INSERT INTO question_has_tag (question_id, tag_id)
VALUES (201, 310);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (202, 310);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (203, 310);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (204, 310);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (205, 310);

INSERT INTO question_has_tag (question_id, tag_id)
VALUES (201, 311);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (202, 311);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (203, 311);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (204, 311);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (205, 311);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (206, 311);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (207, 311);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (208, 311);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (209, 311);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (210, 311);

INSERT INTO question_has_tag (question_id, tag_id)
VALUES (201, 312);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (202, 312);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (203, 312);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (204, 312);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (205, 312);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (206, 312);

INSERT INTO question_has_tag (question_id, tag_id)
VALUES (201, 313);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (202, 313);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (203, 313);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (204, 313);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (205, 313);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (206, 313);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (207, 313);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (208, 313);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (209, 313);

INSERT INTO question_has_tag (question_id, tag_id)
VALUES (201, 314);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (202, 314);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (203, 314);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (204, 314);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (205, 314);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (206, 314);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (207, 314);

INSERT INTO question_has_tag (question_id, tag_id)
VALUES (201, 315);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (202, 315);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (203, 315);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (204, 315);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (205, 315);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (206, 315);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (207, 315);
INSERT INTO question_has_tag (question_id, tag_id)
VALUES (208, 315)