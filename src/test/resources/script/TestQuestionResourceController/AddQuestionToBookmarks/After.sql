TRUNCATE TABLE bookmarks CASCADE;

DELETE FROM bookmarks where id BETWEEN 100 and 104;

DELETE FROM reputation where id BETWEEN 100 and 104;

DELETE FROM answer where id BETWEEN 100 and 104;

DELETE FROM question_viewed where id BETWEEN 100 and 104;

DELETE FROM question_has_tag where question_id BETWEEN 100 and 104;

DELETE FROM votes_on_questions where id BETWEEN 100 and 104;

DELETE FROM question where id BETWEEN 100 and 104;

DELETE FROM user_entity where id BETWEEN 100 and 104;

DELETE FROM tag where id BETWEEN 100 and 109;

DELETE FROM "role"where id BETWEEN 100 and 200;

