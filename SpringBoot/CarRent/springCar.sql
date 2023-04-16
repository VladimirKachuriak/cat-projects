DROP DATABASE IF EXISTS `springcar`;
CREATE SCHEMA `springCar` DEFAULT CHARACTER SET utf8;


CREATE TABLE `springcar`.`user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(255) DEFAULT NULL,
  `account` int NOT NULL,
  `active` bit(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `activation_code` varchar(255) DEFAULT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


CREATE TABLE `springcar`.`user_role`
(
    `user_id` int NOT NULL,
    `roles`   varchar(255) DEFAULT NULL,
     FOREIGN KEY (`user_id`) REFERENCES user(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `springcar`.`car` (
                       `id` int NOT NULL AUTO_INCREMENT,
                       `auto_class` varchar(255) DEFAULT NULL,
                       `brand` varchar(255) DEFAULT NULL,
                       `model` varchar(255) DEFAULT NULL,
                       `price` int NOT NULL,
                       `release_date` date DEFAULT NULL,
                       `state` varchar(255) DEFAULT NULL,
                       PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb3;

CREATE TABLE `springcar`.`orders`
(
    `id`                   int    NOT NULL AUTO_INCREMENT,
    `account`              int    NOT NULL,
    `account_damage`       int    NOT NULL,
    `end_date`             date         DEFAULT NULL,
    `message`              varchar(255) DEFAULT NULL,
    `passport_expire_date` date         DEFAULT NULL,
    `passport_serial`      varchar(255) DEFAULT NULL,
    `start_date`           date         DEFAULT NULL,
    `state`                varchar(255) DEFAULT NULL,
    `with_driver`          bit(1) NOT NULL,
    `car_id`               int          DEFAULT NULL,
    `user_id`              int          DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`car_id`) REFERENCES car(`id`),
    FOREIGN KEY (`user_id`) REFERENCES user(`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb3;

INSERT INTO springcar.user (login, firstname, lastname, email, password, active, phone_number, account)
VALUES ('admin', 'Igor', 'Tkach', 'carRent@com', '$2y$04$W2m5n9khF/uZeehP5v8rwuzCbeyml75B/9lywwrlWFvrtvrofAY2e',  true, '+123456', 0);
INSERT INTO springcar.user (login, firstname, lastname, email, password, active, phone_number, account)
VALUES ('user', 'Sasha', 'Malikov', 'user1@com', '$2y$04$yzfc1f8b063LHCwyqWvPhObuumiOipGeVCRV7YSIY7EII6So1nQ.a',  true, '+123456', 0);

INSERT INTO springcar.user_role(user_id,roles)VALUES(1,'ADMIN');
INSERT INTO springcar.user_role(user_id,roles)VALUES(2,'USER');

INSERT INTO springcar.car (brand, model, release_date, state, auto_class, price)
VALUES ('BMW', 'X5', '2017-05-11', 'AVAIL', 'C', 32);
INSERT INTO springcar.car (brand, model, release_date, state, auto_class, price)
VALUES ('BMW', 'X1', '2014-05-11', 'AVAIL', 'A', 22);
INSERT INTO springcar.car (brand, model, release_date, state, auto_class, price)
VALUES ('reno', 'logan', '2011-07-17', 'AVAIL', 'B', 15);
INSERT INTO springcar.car (brand, model, release_date, state, auto_class, price)
VALUES ('Mercedes', 'GLE', '2021-07-17', 'AVAIL', 'C', 75);


