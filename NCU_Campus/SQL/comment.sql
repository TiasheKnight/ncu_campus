drop table if exists comment;
CREATE TABLE comment (
	id int,
	user_id int,
	activity_id int,
	content text,
	time text
);

INSERT INTO comment VALUES (1,1,1,'這活動真好玩','12:22');
INSERT INTO comment VALUES (2,3,1,'這活動好累','13:33');
INSERT INTO comment VALUES (3,5,1,'真的不要再來了','14:44');
INSERT INTO comment VALUES (4,7,2,'推推，我推的活動','15:55');