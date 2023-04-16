CREATE TABLE `user`
(
    `id`       int    NOT NULL AUTO_INCREMENT,
    `active`   bit(1) NOT NULL,
    `login`    varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `account`  int    NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `user_role`
(
    `user_id` int NOT NULL,
    `roles`   varchar(255) DEFAULT NULL,
    KEY       `FK859n2jvi8ivhui0rl0esws6o` (`user_id`),
    CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `car` (
                       `id` int NOT NULL AUTO_INCREMENT,
                       `auto_class` varchar(255) DEFAULT NULL,
                       `brand` varchar(255) DEFAULT NULL,
                       `model` varchar(255) DEFAULT NULL,
                       `price` int NOT NULL,
                       `release_date` date DEFAULT NULL,
                       `state` varchar(255) DEFAULT NULL,
                       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `orders`
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
    KEY                    `FK8ptpdp8fxan4etjis2wkqg1ea` (`car_id`),
    KEY                    `FKel9kyl84ego2otj2accfd8mr7` (`user_id`),
    CONSTRAINT `FK8ptpdp8fxan4etjis2wkqg1ea` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`),
    CONSTRAINT `FKel9kyl84ego2otj2accfd8mr7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
