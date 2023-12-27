drop table if exists item;
CREATE TABLE item (
	id int,
	item_name text,
	item_price int,
	item_detail text,
	img int
);

INSERT INTO item VALUES (1,'呼喚的號角',15,'可以兌換乙次發起緊急活動',NULL);
INSERT INTO item VALUES (2,'竹蜻蜓',20,'可以兌換貳次發起緊急活動',NULL);
INSERT INTO item VALUES (3,'翻譯蒟蒻',25,'可以兌換參次發起緊急活動',NULL);
INSERT INTO item VALUES (4,'想像袋',30,'可以兌換肆次發起緊急活動',NULL);
