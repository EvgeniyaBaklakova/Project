truncate table role cascade;

INSERT INTO role (id, name)
VALUES (111, 'ROLE_USER'),
       (222, 'ROLE_ADMIN');

INSERT INTO user_entity (id, about, city, email, full_Name, image_link, is_deleted, is_enabled, last_redaction_date,
                         link_github, link_site, link_vk, nickname, password, persist_date, role_id)
VALUES (101, 'Its me', 'Saint-Petersburg', 'test101@mail.ru', 'Alex Vasiliev', 'first_user_image', false, true, now(), 'No link',
        'No link', 'No link', 'first',
        '$2a$12$JJHilheQZae2WMfLHwgElOtsFgMLqxve3.v1CyxVvyhiOl.l2feVK', now(), 111),
       (102, 'Its not me', 'Saint-Petersburg', 'test102@mail.ru', 'NeAlex NeVasiliev', 'second_user_image', false, true, now(),
        'No link', 'No link', 'No link', 'second',
        '$2a$12$JJHilheQZae2WMfLHwgElOtsFgMLqxve3.v1CyxVvyhiOl.l2feVK', now(), 111),
       (103, 'Its not me', 'Saint-Petersburg', 'test103@mail.ru', 'John Doe', 'third_user_image', false, true, now(), 'No link',
        'No link', 'No link', 'third',
        '$2a$12$JJHilheQZae2WMfLHwgElOtsFgMLqxve3.v1CyxVvyhiOl.l2feVK', now(), 111);

insert into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (201, 'java teg question description first', false, '2000-01-01', '2000-01-01', 'java question first', 101),
       (202, 'java teg question description second', false, '2000-01-01', '2000-01-01', 'java question second', 102),
       (203, 'java teg question description third', false, '2000-01-01', '2000-01-01', 'java third', 101),
       (204, 'spring teg question description first', false, '2000-01-01', '2000-01-01', 'spring first', 102),
       (205, 'spring teg question description second', false, '2000-01-01', '2000-01-01', 'spring second', 103),
       (206, 'spring teg question description third', false, '2000-01-01', '2000-01-01', 'spring third', 101),
       (207, 'java script teg question description first', false, '2000-01-01', '2000-01-01', 'java script first', 103),
       (208, 'java script teg question description second', false, '2000-01-01', '2000-01-01', 'java script second', 103),
       (209, 'java script teg question description third', false, '2000-01-01', '2000-01-01', 'java script third', 102),
       (210, 'fourth teg question description first', false, '2000-01-01', '2000-01-01', 'fourth first', 101),
       (211, 'fifth teg question description first', false, '2000-01-01', '2000-01-01', 'fifth first', 101),
       (212, 'sixth teg question description first', false, '2000-01-01', '2000-01-01', 'sixth first', 101),
       (213, 'seventh teg question description first', false, '2000-01-01', '2000-01-01', 'seventh first', 101),
       (214, 'eighth teg question description first', false, '2000-01-01', '2000-01-01', 'eighth first', 101),
       (215, 'ninth teg question description first', false, '2000-01-01', '2000-01-01', 'ninth first', 101),
       (216, 'tenth teg question description first', false, '2000-01-01', '2000-01-01', 'tenth first', 101),
       (217, 'eleventh teg question description first', false, '2000-01-01', '2000-01-01', 'eleventh first', 101),
       (218, 'twelfth teg question description first', false, '2000-01-01', '2000-01-01', 'twelfth first', 101),
       (219, 'twelfth teg question description second', false, '2000-01-01', '2000-01-01', 'twelfth first', 101),
       (220, 'twelfth teg question description third', false, '2000-01-01', '2000-01-01', 'twelfth third', 101);

insert into answer(id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date, update_date, question_id, user_id)
values (301, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 201, 102),
       (302, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 201, 103),
       (303, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 201, 101),
       (304, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 202, 103),
       (305, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 202, 101),
       (306, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 202, 101),
       (307, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 203, 102),
       (308, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 203, 103),
       (309, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 203, 102),
       (310, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 204, 103),
       (311, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 205, 101),
       (312, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 206, 102),
       (313, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 207, 101),
       (314, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 208, 102),
       (315, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 209, 103);

insert into comment(id, comment_type, last_redaction_date, persist_date, text, user_id)
values (401, '1', '2000-01-01', '2000-01-01', 'any question text', 101),
       (402, '1', '2000-01-02', '2000-01-02', 'any question text', 101),
       (403, '1', '2000-01-03', '2000-01-03', 'any question text', 101),
       (404, '1', '2000-01-04', '2000-01-04', 'any question text', 101),
       (405, '1', '2000-01-05', '2000-01-05', 'any question text', 101),
       (406, '1', '2000-01-06', '2000-01-06', 'any question text', 101),
       (407, '1', '2000-01-07', '2000-01-07', 'any question text', 101),
       (408, '1', '2000-01-08', '2000-01-08', 'any question text', 101),
       (409, '1', '2000-01-09', '2000-01-09', 'any question text', 101),
       (410, '1', '2000-01-10', '2000-01-10', 'any question text', 101),
       (411, '1', '2000-01-11', '2000-01-11', 'any question text', 101),
       (412, '1', '2000-01-12', '2000-01-12', 'any question text', 101),
       (413, '1', '2000-01-13', '2000-01-13', 'any question text', 101),
       (414, '1', '2000-01-14', '2000-01-14', 'any question text', 101),

       (415, '1', '2000-01-15', '2000-01-15', 'any question text', 102),
       (416, '1', '2000-01-16', '2000-01-16', 'any question text', 102),
       (417, '1', '2000-01-17', '2000-01-17', 'any question text', 102),
       (418, '1', '2000-01-18', '2000-01-18', 'any question text', 102),
       (419, '1', '2000-01-19', '2000-01-19', 'any question text', 102),
       (420, '1', '2000-01-20', '2000-01-20', 'any question text', 102),

       (421, '0', '2000-02-01', '2000-02-01', 'any answer text', 101),
       (422, '0', '2000-02-02', '2000-02-02', 'any answer text', 101),
       (423, '0', '2000-02-03', '2000-02-03', 'any answer text', 101),
       (424, '0', '2000-02-04', '2000-02-04', 'any answer text', 101),
       (425, '0', '2000-02-05', '2000-02-05', 'any answer text', 101),
       (426, '0', '2000-02-06', '2000-02-06', 'any answer text', 101),
       (427, '0', '2000-02-07', '2000-02-07', 'any answer text', 101),
       (428, '0', '2000-02-08', '2000-02-08', 'any answer text', 101),
       (429, '0', '2000-02-09', '2000-02-09', 'any answer text', 101),

       (430, '0', '2000-02-10', '2000-02-10', 'any answer text', 103),
       (431, '0', '2000-02-11', '2000-02-11', 'any answer text', 103),
       (432, '0', '2000-02-12', '2000-02-12', 'any answer text', 103),
       (433, '0', '2000-02-13', '2000-02-13', 'any answer text', 103),
       (434, '0', '2000-02-14', '2000-02-14', 'any answer text', 103),
       (435, '0', '2000-02-15', '2000-02-15', 'any answer text', 103);

insert into comment_question(comment_id, question_id)
values (401, 201),
       (402, 202),
       (403, 203),
       (404, 204),
       (405, 205),
       (406, 206),
       (407, 207),
       (408, 208),
       (409, 209),
       (410, 210),
       (411, 211),
       (412, 212),
       (413, 213),
       (414, 214),

       (415, 215),
       (416, 216),
       (417, 217),
       (418, 218),
       (419, 219),
       (420, 220);

insert into comment_answer(comment_id, answer_id)
values (421, 301),
       (422, 302),
       (423, 303),
       (424, 304),
       (425, 305),
       (426, 306),
       (427, 307),
       (428, 308),
       (429, 309),
       (430, 310),

       (431, 311),
       (432, 312),
       (433, 313),
       (434, 314),
       (435, 315);