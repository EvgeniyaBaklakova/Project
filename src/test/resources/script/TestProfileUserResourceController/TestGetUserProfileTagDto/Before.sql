truncate table role cascade;
truncate table tag cascade;

INSERT INTO role (id, name)
VALUES (111, 'ROLE_USER');

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

insert into tag(id, description, name, persist_date)
values (201, 'java teg description', 'java', '2000-01-01'),
       (202, 'spring teg description', 'spring', '2000-01-01'),
       (203, 'java script teg description', 'java script', '2000-01-01'),

       (204, 'fourth teg description', 'fourth', '2000-01-01'),
       (205, 'fifth teg description', 'fifth', '2000-01-01'),
       (206, 'sixth teg description', 'sixth', '2000-01-01'),
       (207, 'seventh teg description', 'seventh', '2000-01-01'),
       (208, 'eighth teg description', 'eighth', '2000-01-01'),
       (209, 'ninth teg description', 'ninth', '2000-01-01'),
       (210, 'tenth teg description', 'tenth', '2000-01-01'),
       (211, 'eleventh teg description', 'eleventh', '2000-01-01'),
       (212, 'twelfth teg description', 'twelfth', '2000-01-01');

insert into question(id, description, is_deleted, last_redaction_date, persist_date, title, user_id)
values (401, 'java teg question description first', false, '2000-01-01', '2000-01-01', 'java question first', 101),
       (402, 'java teg question description second', false, '2000-01-01', '2000-01-01', 'java question second', 102),
       (403, 'java teg question description third', false, '2000-01-01', '2000-01-01', 'java third', 101),

       (404, 'spring teg question description first', false, '2000-01-01', '2000-01-01', 'spring first', 102),
       (405, 'spring teg question description second', false, '2000-01-01', '2000-01-01', 'spring second', 103),
       (406, 'spring teg question description third', false, '2000-01-01', '2000-01-01', 'spring third', 101),

       (407, 'java script teg question description first', false, '2000-01-01', '2000-01-01', 'java script first', 103),
       (408, 'java script teg question description second', false, '2000-01-01', '2000-01-01', 'java script second', 103),
       (409, 'java script teg question description third', false, '2000-01-01', '2000-01-01', 'java script third', 102),

       (410, 'fourth teg question description first', false, '2000-01-01', '2000-01-01', 'fourth first', 101),
       (411, 'fifth teg question description first', false, '2000-01-01', '2000-01-01', 'fifth first', 101),
       (412, 'sixth teg question description first', false, '2000-01-01', '2000-01-01', 'sixth first', 101),
       (413, 'seventh teg question description first', false, '2000-01-01', '2000-01-01', 'seventh first', 101),
       (414, 'eighth teg question description first', false, '2000-01-01', '2000-01-01', 'eighth first', 101),
       (415, 'ninth teg question description first', false, '2000-01-01', '2000-01-01', 'ninth first', 101),
       (416, 'tenth teg question description first', false, '2000-01-01', '2000-01-01', 'tenth first', 101),
       (417, 'eleventh teg question description first', false, '2000-01-01', '2000-01-01', 'eleventh first', 101),
       (418, 'twelfth teg question description first', false, '2000-01-01', '2000-01-01', 'twelfth first', 101),
       (419, 'twelfth teg question description second', false, '2000-01-01', '2000-01-01', 'twelfth first', 101),
       (420, 'twelfth teg question description third', false, '2000-01-01', '2000-01-01', 'twelfth third', 101);

insert into votes_on_questions(id, persist_date, vote, question_id, user_id)
values (501, '2000-01-01', 'UP_VOTE', 401, 102),
       (502, '2000-01-01', 'UP_VOTE', 401, 103),
       (503, '2000-01-01', 'UP_VOTE', 403, 102),
       (504, '2000-01-01', 'DOWN_VOTE', 403, 103),
       (511, '2000-01-01', 'UP_VOTE', 402, 101),
       (512, '2000-01-01', 'DOWN_VOTE', 402, 102),
       (513, '2000-01-01', 'DOWN_VOTE', 402, 103),

       (505, '2000-01-01', 'DOWN_VOTE', 406, 102),
       (506, '2000-01-01', 'DOWN_VOTE', 406, 103),

       (507, '2000-01-01', 'UP_VOTE', 418, 103),
       (508, '2000-01-01', 'UP_VOTE', 418, 102);

insert into question_has_tag(question_id, tag_id)
values (401, 201),
       (402, 201),
       (403, 201),

       (404, 202),
       (405, 202),
       (406, 202),

       (407, 203),
       (408, 203),
       (409, 203),

       (410, 204),
       (411, 205),
       (412, 206),
       (413, 207),
       (414, 208),
       (415, 209),
       (416, 210),
       (417, 211),
       (418, 212),
       (419, 212),
       (420, 212);

insert into answer(id, date_accept_time, html_body, is_deleted, is_deleted_by_moderator, is_helpful, persist_date, update_date, question_id, user_id)
values (601, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 401, 102),
       (610, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 401, 103),
       (611, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 401, 101),
       (602, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 402, 103),
       (620, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 402, 101),
       (621, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 402, 101),
       (603, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 403, 102),
       (630, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 403, 103),
       (631, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 403, 102),

       (604, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 404, 103),
       (605, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 405, 101),
       (606, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 406, 102),

       (607, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 407, 101),
       (608, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 408, 102),
       (609, '2000-01-01', 'html body', false, false, false, '2000-01-01', '2000-01-01', 409, 103);

insert into votes_on_answers(id, persist_date, vote, answer_id, user_id)
values (501, '2000-01-01', 'UP_VOTE', 611, 102),
       (502, '2000-01-01', 'UP_VOTE', 621, 103),
       (503, '2000-01-01', 'DOWN_VOTE', 621, 102),

       (511, '2000-01-01', 'UP_VOTE', 604, 102),
       (512, '2000-01-01', 'UP_VOTE', 604, 102),
       (513, '2000-01-01', 'DOWN_VOTE', 604, 102),
       (504, '2000-01-01', 'DOWN_VOTE', 605, 102),
       (505, '2000-01-01', 'DOWN_VOTE', 605, 103),

       (506, '2000-01-01', 'DOWN_VOTE', 607, 102),
       (507, '2000-01-01', 'DOWN_VOTE', 607, 103),
       (508, '2000-01-01', 'UP_VOTE', 607, 101);