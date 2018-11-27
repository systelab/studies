INSERT INTO users (id, user_surname, user_name, user_login, user_password, user_role) VALUES ('12345678123456781234567812345671', 'Chandler', 'Sharat', 'user','$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu', 'USER');
INSERT INTO users (id, user_surname, user_name, user_login, user_password, user_role) VALUES ('12345678123456781234567812345672', 'Reinhold', 'Mark', 'admin','$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu', 'ADMIN');
INSERT INTO users (id, user_surname, user_name, user_login, user_password, user_role) VALUES ('12345678123456781234567812345673', 'Systelab', 'Systelab', 'Systelab','$2a$10$9wXu9hshOrtZ7RopythgF.XP93XbKtISBJMpz4PFHG4zv6QjTGBzq', 'ADMIN');

INSERT INTO studytest (id, description) VALUES (1, 'INR');
INSERT INTO studytest (id, description) VALUES (2, 'PT');
INSERT INTO studytest (id, description) VALUES (3, 'APTT');

INSERT INTO instrument (id, description) VALUES (5, 'ACL TOP 1');
INSERT INTO instrument (id, description) VALUES (6, 'ACL TOP 2');
INSERT INTO instrument (id, description) VALUES (7, 'ACL TOP 3');

INSERT INTO MATERIAL  (ID, EXPIRATION_DATE, LOT_NUMBER, NAME, TYPE) Values  (2, TO_DATE('27/11/2018 11:07:54', 'DD/MM/YYYY HH24:MI:SS'), '1', 'material', 'material');