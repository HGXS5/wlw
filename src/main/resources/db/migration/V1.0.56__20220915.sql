INSERT INTO `cfg_name` VALUES ('120', '协议', 'five.DevFiveYsi2', 'YSI多参数协议');

CREATE TABLE `rec_log_platform` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rec_time` datetime NOT NULL,
  `rec_platform` int(11) NOT NULL,
  `rec_type` varchar(20) NOT NULL DEFAULT '平台反控',
  `rec_desc` varchar(40) DEFAULT NULL,
  `rec_result` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

