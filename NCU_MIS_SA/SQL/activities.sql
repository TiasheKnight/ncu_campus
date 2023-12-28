drop table if exists activities;
CREATE TABLE activities (
	id int,
	activity_publish_type text,
	activity_title text,
	activity_type text,
	activity_location text,
	activity_publisher_id int,
	maximun_participant int,
	minimum_participant int,
	start_date text,
	start_time text,
	end_date text,
	end_time text,
	published_date text,
	published_time text,
	activity_detail text,
	participant_number int
);

INSERT INTO activities VALUES (1,'一般活動','傳到橋頭自然資 不管怎樣都愛你','校級活動','宵夜街口',7,6,2,'12月20日','19:00','12月23日','21:00','12月10日','20:00','資管傳情',3);
INSERT INTO activities VALUES (2,'一般活動','材不管棺材裡的祂','系級活動','遊藝館',1,4,2,'12月14日','18:00','12月16日','22:10','11月29日','20:28','資管鬼屋',3);
INSERT INTO activities VALUES (3,'緊急活動','管二105便當','系級活動','管二105',4,3,0,'12月29日','12:00','12月29日','15:00','12月29日','12:00','演講多的便當',3);
INSERT INTO activities VALUES (4,'緊急活動','機械場打球','校級活動','機械系館旁籃球場',6,7,3,'12月25日','19:00','12月25日','22:00','12月25日','18:30','打球缺人',5);