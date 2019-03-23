CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `is_admin` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author` int(11) NOT NULL,
  `title` text NOT NULL,
  `text` text NOT NULL,
  `time` datetime NOT NULL,
  `vote` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `c1_idx` (`author`),
  CONSTRAINT `c1` FOREIGN KEY (`author`) REFERENCES `user` (`id`) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) NOT NULL,
  `author` varchar(45) NOT NULL,
  `text` text NOT NULL,
  `time` datetime NOT NULL,
  `edited` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `q1_idx` (`question_id`),
  CONSTRAINT `q1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `question_tag` (
  `question_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`question_id`,`tag_id`),
  KEY `t1_idx` (`tag_id`),
  CONSTRAINT `qt` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `question_vote` (
  `question_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `vote` tinyint(1) NOT NULL,
  PRIMARY KEY (`question_id`,`user_id`),
  KEY `uv_idx` (`user_id`),
  CONSTRAINT `qv` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `uv` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `answer_vote` (
  `answer_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `vote` tinyint(1) NOT NULL,
  PRIMARY KEY (`answer_id`,`user_id`),
  KEY `uav_idx` (`user_id`),
  CONSTRAINT `av` FOREIGN KEY (`answer_id`) REFERENCES `answer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ua` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE
);