drop table if exists message;
CREATE TABLE message (
	id int,
	user_id int,
	friend_user_id int,
	message_content text,
	message_time_tamp float
);

INSERT INTO message VALUES (1,1,7,'你好',12/10/23 22:57);
INSERT INTO message VALUES (2,1,5,'早安',11/30/23 16:39);
INSERT INTO message VALUES (3,3,6,'安安',11/29/23 19:09);
INSERT INTO message VALUES (4,3,5,'嗨唷',12/1/23 8:20);
INSERT INTO message VALUES (5,4,6,'台北雙魚28y',12/18/23 3:25);
INSERT INTO message VALUES (6,5,3,'走啊你不敢啦',12/15/23 3:33);
INSERT INTO message VALUES (7,5,1,'夜光閃亮亮復仇鬼',12/17/23 21:29);
INSERT INTO message VALUES (8,6,3,'好餓喔吃啥',12.967948717948719);
INSERT INTO message VALUES (9,6,4,'太誇張了吧',12/12/23 12:12);
INSERT INTO message VALUES (10,7,1,'要去傳情嗎',12/27/23 18:30);
