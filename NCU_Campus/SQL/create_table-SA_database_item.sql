drop table if exists sa_database_item;
CREATE TABLE sa_database_item (
	id int,
	item_name text,
	item_price int,
	item_detail text,
	img int
);

INSERT INTO sa_database_item VALUES (1,'呼喚的號角',15,'可以兌換乙次發起緊急活動',NULL);
INSERT INTO sa_database_item VALUES (2,'竹蜻蜓',20,'可以兌換貳次發起緊急活動',NULL);
INSERT INTO sa_database_item VALUES (3,'翻譯蒟蒻',25,'可以兌換參次發起緊急活動',NULL);
INSERT INTO sa_database_item VALUES (4,'想像袋',30,'可以兌換肆次發起緊急活動',NULL);