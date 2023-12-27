drop table if exists sa_database_report;
CREATE TABLE sa_database_report (
	id int,
	user_id int,
	activity_id int
);

INSERT INTO sa_database_report VALUES (1,1,1);
INSERT INTO sa_database_report VALUES (2,2,1);
INSERT INTO sa_database_report VALUES (3,3,2);
INSERT INTO sa_database_report VALUES (4,5,3);
INSERT INTO sa_database_report VALUES (5,7,4);