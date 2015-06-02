CREATE TABLE `messages` (
    `id` INT UNSIGNED NOT NULL,
    `text` VARCHAR(500) NOT NULL,
    `date` DATETIME NOT NULL,
    'user' VARCHAR(20) UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
) ENGINE=INNODB CHARACTER SET utf8 COLLATE utf8_general_ci