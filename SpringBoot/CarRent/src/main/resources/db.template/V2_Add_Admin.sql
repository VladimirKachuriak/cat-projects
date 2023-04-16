INSERT INTO springcar.user (login, firstname, lastname, email, password, active, phone_number, account)
VALUES ('admin', 'Igor', 'Tkach', 'carRent@com', 'admin',  true, '+123456', 0);
INSERT INTO springcar.user (login, firstname, lastname, email, password, active, phone_number, account)
VALUES ('user', 'Sasha', 'Malikov', 'user1@com', 'password',  true, '+123456', 0);

INSERT INTO springcar.user_role(user_id,roles)VALUES(1,'ADMIN');
INSERT INTO springcar.user_role(user_id,roles)VALUES(2,'USER');