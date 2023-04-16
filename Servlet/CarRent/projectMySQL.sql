DROP DATABASE IF EXISTS `carrent`;
CREATE SCHEMA `carRent` DEFAULT CHARACTER SET utf8;

CREATE TABLE `carrent`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(20) NOT NULL,
  `firstname` VARCHAR(20) NOT NULL,
  `lastname` VARCHAR(20) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `role` VARCHAR(15) NOT NULL,
  `status` VARCHAR(10) NOT NULL,
  `phone_number` VARCHAR(45) NOT NULL,
  `account` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (login));

CREATE TABLE `carrent`.`cars` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `brand` VARCHAR(45) NOT NULL,
  `model` VARCHAR(45) NOT NULL,
  `release_date` DATE NOT NULL,
  `state` VARCHAR(45) NOT NULL,
  `auto_class` VARCHAR(45) NOT NULL,
  `price` INT NOT NULL,
  PRIMARY KEY (`id`));

create table `carrent`.`orders`
(
      id INT NOT NULL AUTO_INCREMENT,
	idCar int not null,
	idUser int not null,
	startDate DATE not null,
	endDate DATE not null,
	withDriver BOOL not null,
	account int not null,
      accountDamage int not null,
	serial VARCHAR(20) not null,
	expire DATE not null,
	state VARCHAR(20) not null,
      message VARCHAR(100) null,
      PRIMARY KEY (id),
      FOREIGN KEY (idUser) REFERENCES users(id),
      FOREIGN KEY (idCar) REFERENCES cars(id)
);



INSERT INTO carrent.users (login, firstname, lastname, email, password, role, status, phone_number, account)
VALUES ('admin', 'Igor', 'Tkach', 'carRent@com', 'admin', 'ADMIN', 'ACTIVE', '+123456', 0);
INSERT INTO carrent.users (login, firstname, lastname, email, password, role, status, phone_number, account)
VALUES ('user', 'Sasha', 'Malikov', 'user1@com', 'password', 'USER', 'ACTIVE', '+123456', 0);


INSERT INTO carrent.cars (brand, model, release_date, state, auto_class, price)
VALUES ('BMW', 'X5', '2017-05-11', 'AVAIL', 'C', 32);
INSERT INTO carrent.cars (brand, model, release_date, state, auto_class, price)
VALUES ('BMW', 'X1', '2014-05-11', 'AVAIL', 'A', 22);
INSERT INTO carrent.cars (brand, model, release_date, state, auto_class, price)
VALUES ('reno', 'logan', '2011-07-17', 'AVAIL', 'B', 15);
INSERT INTO carrent.cars (brand, model, release_date, state, auto_class, price)
VALUES ('Mercedes', 'GLE', '2021-07-17', 'AVAIL', 'C', 75);



INSERT INTO carrent.orders (idCar, idUser, startDate, endDate, withDriver, account, accountDamage, serial, expire,
                            state, message)
VALUES (1, 2, '2022-05-21', '2022-05-25', 1, 3, 3, '3', '2022-05-25', 'FINISH', 'thanks for order');








