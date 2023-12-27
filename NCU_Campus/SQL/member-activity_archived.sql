drop table if exists member_activity_archived;
CREATE TABLE member_activity_archived (
	id int,
	user_id int,
	activity_id int
);

INSERT INTO member_activity_archived VALUES (1,1,1);
INSERT INTO member_activity_archived VALUES (2,1,2);
INSERT INTO member_activity_archived VALUES (3,1,4);
INSERT INTO member_activity_archived VALUES (4,2,1);
INSERT INTO member_activity_archived VALUES (5,3,2);
INSERT INTO member_activity_archived VALUES (6,4,2);
INSERT INTO member_activity_archived VALUES (7,5,1);
INSERT INTO member_activity_archived VALUES (8,5,2);
INSERT INTO member_activity_archived VALUES (9,5,3);
INSERT INTO member_activity_archived VALUES (10,5,1);
INSERT INTO member_activity_archived VALUES (11,6,2);
INSERT INTO member_activity_archived VALUES (12,7,3);
