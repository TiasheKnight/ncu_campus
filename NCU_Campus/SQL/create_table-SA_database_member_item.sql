drop table if exists sa_database_member_item;
CREATE TABLE sa_database_member_item (
	id int,
	user_id int,
	item_id int,
	item_quantity int
);

INSERT INTO sa_database_member_item VALUES (1,1,1,2);
INSERT INTO sa_database_member_item VALUES (1,1,2,3);
INSERT INTO sa_database_member_item VALUES (1,3,3,4);
INSERT INTO sa_database_member_item VALUES (1,3,4,1);
INSERT INTO sa_database_member_item VALUES (1,4,2,2);
INSERT INTO sa_database_member_item VALUES (1,5,2,4);
INSERT INTO sa_database_member_item VALUES (1,5,3,3);
INSERT INTO sa_database_member_item VALUES (1,6,4,2);
INSERT INTO sa_database_member_item VALUES (1,6,1,1);
INSERT INTO sa_database_member_item VALUES (1,7,3,2);