drop table if exists sa_database_member_activity_archived;
CREATE TABLE sa_database_member_activity_archived (
	id int,
	user_id int,
	activity_id int
);

INSERT INTO sa_database_member_activity_archived VALUES (1,1,1);
INSERT INTO sa_database_member_activity_archived VALUES (2,1,2);
INSERT INTO sa_database_member_activity_archived VALUES (3,1,4);
INSERT INTO sa_database_member_activity_archived VALUES (4,2,1);
INSERT INTO sa_database_member_activity_archived VALUES (5,3,2);
INSERT INTO sa_database_member_activity_archived VALUES (6,4,2);
INSERT INTO sa_database_member_activity_archived VALUES (7,5,1);
INSERT INTO sa_database_member_activity_archived VALUES (8,5,2);
INSERT INTO sa_database_member_activity_archived VALUES (9,5,3);
INSERT INTO sa_database_member_activity_archived VALUES (10,5,1);
INSERT INTO sa_database_member_activity_archived VALUES (11,6,2);
INSERT INTO sa_database_member_activity_archived VALUES (12,7,3);