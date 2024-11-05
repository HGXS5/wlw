CREATE TABLE `cfg_schedule_quality` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) NOT NULL,
  `hour` int(11) NOT NULL,
  `min` int(11) NOT NULL,
  `sec` int(11) NOT NULL DEFAULT '0',
  `run` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `cfg_schedule_quality` VALUES ('1', '零点核查', '3', '40', '0', '0');
INSERT INTO `cfg_schedule_quality` VALUES ('2', '跨度核查', '5', '40', '0', '0');