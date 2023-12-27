drop table if exists member_activity;
CREATE TABLE member_activity (
	id int,
	user_id int,
	activity_id int
);

INSERT INTO member_activity VALUES (1,1,1);
INSERT INTO member_activity VALUES (2,1,2);
INSERT INTO member_activity VALUES (3,2,1);
INSERT INTO member_activity VALUES (4,2,2);
INSERT INTO member_activity VALUES (5,2,3);
INSERT INTO member_activity VALUES (6,3,1);
INSERT INTO member_activity VALUES (7,3,2);
INSERT INTO member_activity VALUES (8,3,3);
INSERT INTO member_activity VALUES (9,3,4);
INSERT INTO member_activity VALUES (10,4,1);
INSERT INTO member_activity VALUES (11,5,2);
INSERT INTO member_activity VALUES (12,6,2);
INSERT INTO member_activity VALUES (13,7,3);
INSERT INTO member_activity VALUES (14,7,4);