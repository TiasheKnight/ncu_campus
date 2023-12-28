drop table if exists friend;
CREATE TABLE friend (
	id int,
	user_id int,
	friend_user_id int
);

INSERT INTO friend VALUES (1,1,7);
INSERT INTO friend VALUES (2,1,5);
INSERT INTO friend VALUES (3,3,6);
INSERT INTO friend VALUES (4,3,5);
INSERT INTO friend VALUES (5,4,6);
INSERT INTO friend VALUES (6,5,3);
INSERT INTO friend VALUES (7,5,1);
INSERT INTO friend VALUES (8,6,3);
INSERT INTO friend VALUES (9,6,4);
INSERT INTO friend VALUES (10,7,1);