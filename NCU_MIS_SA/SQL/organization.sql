drop table if exists organization;
CREATE TABLE organization (
	id int,
	organization_name text,
	organization_detail text
);

INSERT INTO organization VALUES (1,'毛毛朋友幫','大家身上都會長一大堆毛');
INSERT INTO organization VALUES (2,'壞蛋聚在一起讀書會','從前從前有一群壞蛋喜歡一起讀微積分');
INSERT INTO organization VALUES (3,'刺破泡泡紅眼幫','喜歡吹泡泡跟次泡泡的人可以來參加');