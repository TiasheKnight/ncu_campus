drop table if exists followship;
CREATE TABLE followship (
	id int,
	follower_user_id int,
	followed_user_id int
);

INSERT INTO followship VALUES (1,1,3);
INSERT INTO followship VALUES (2,1,4);
INSERT INTO followship VALUES (3,1,5);
INSERT INTO followship VALUES (4,1,6);
INSERT INTO followship VALUES (5,3,1);
INSERT INTO followship VALUES (6,3,4);
INSERT INTO followship VALUES (7,4,7);
INSERT INTO followship VALUES (8,5,1);
INSERT INTO followship VALUES (9,5,3);
INSERT INTO followship VALUES (10,5,4);
INSERT INTO followship VALUES (11,6,1);
INSERT INTO followship VALUES (12,6,3);
INSERT INTO followship VALUES (13,6,4);
INSERT INTO followship VALUES (14,6,5);
INSERT INTO followship VALUES (15,7,1);