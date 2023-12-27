drop table if exists member_organization;
CREATE TABLE member_organization (
	id int,
	user_id int,
	organization_id int,
	authority text
);

INSERT INTO member_organization VALUES (1,1,1,'founder');
INSERT INTO member_organization VALUES (2,1,2,'moderator');
INSERT INTO member_organization VALUES (3,1,3,'member');
INSERT INTO member_organization VALUES (4,2,1,'member');
INSERT INTO member_organization VALUES (5,2,3,'moderator');
INSERT INTO member_organization VALUES (6,3,1,'member');
INSERT INTO member_organization VALUES (7,3,2,'member');
INSERT INTO member_organization VALUES (8,4,1,'moderator');
INSERT INTO member_organization VALUES (9,4,2,'founder');
INSERT INTO member_organization VALUES (10,5,1,'member');
INSERT INTO member_organization VALUES (11,6,2,'member');
INSERT INTO member_organization VALUES (12,7,3,'founder');
