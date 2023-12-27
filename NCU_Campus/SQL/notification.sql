drop table if exists notification;
CREATE TABLE notification (
	id int,
	activity_id int,
	user_id int,
	notification_title text,
	notification_content text
);

INSERT INTO notification VALUES (1,2,7,'繳費通知','請麻煩於12/10錢繳交$200/人報名費！逾期不受理');
INSERT INTO notification VALUES (2,2,5,'繳費通知','請麻煩於12/10錢繳交$200/人報名費！逾期不受理');
INSERT INTO notification VALUES (3,2,6,'繳費通知','請麻煩於12/10錢繳交$200/人報名費！逾期不受理');
INSERT INTO notification VALUES (4,3,2,'限時10分鐘','請於報名後10分鐘內盡速前往105取餐，否則將讓給其他人');
INSERT INTO notification VALUES (5,3,3,'限時10分鐘','請於報名後10分鐘內盡速前往105取餐，否則將讓給其他人');
INSERT INTO notification VALUES (6,3,6,'限時10分鐘','請於報名後10分鐘內盡速前往105取餐，否則將讓給其他人');
INSERT INTO notification VALUES (7,4,1,'打球缺人','如題，快來一起流汗！！');
INSERT INTO notification VALUES (8,4,2,'打球缺人','如題，快來一起流汗！！');
INSERT INTO notification VALUES (9,4,5,'打球缺人','如題，快來一起流汗！！');
INSERT INTO notification VALUES (10,4,4,'打球缺人','如題，快來一起流汗！！');
INSERT INTO notification VALUES (11,4,7,'打球缺人','如題，快來一起流汗！！');
INSERT INTO notification VALUES (12,1,1,'新宣傳影片出爐囉','快來看我們拍的愛情片吧~~');
INSERT INTO notification VALUES (13,1,5,'新宣傳影片出爐囉','快來看我們拍的愛情片吧~~');
INSERT INTO notification VALUES (14,1,4,'新宣傳影片出爐囉','快來看我們拍的愛情片吧~~');
