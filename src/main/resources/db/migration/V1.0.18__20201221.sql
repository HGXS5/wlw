CREATE TABLE `cfg_flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `record` tinyint(1) DEFAULT '0',
  `record_circle` int(11) DEFAULT NULL,
  `upload` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `cfg_flow` VALUES ('1', '1', '10', '1');

CREATE TABLE `rec_data_flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rec_time` datetime NOT NULL,
  `send_time1` datetime DEFAULT NULL,
  `send_time2` datetime DEFAULT NULL,
  `send_time3` datetime DEFAULT NULL,
  `send_time4` datetime DEFAULT NULL,
  `send_time5` datetime DEFAULT NULL,
  `send_time6` datetime DEFAULT NULL,
  `send_time7` datetime DEFAULT NULL,
  `send_time8` datetime DEFAULT NULL,
  `send_time9` datetime DEFAULT NULL,
  `send_time10` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rec_data_flow_factor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rec_id` int(11) DEFAULT NULL,
  `factor_id` int(11) DEFAULT NULL,
  `d_time` datetime DEFAULT NULL,
  `d_value` double DEFAULT NULL,
  `d_flag` varchar(10) DEFAULT NULL,
  `is_alarm` tinyint(1) DEFAULT NULL,
  `alarm_value` double DEFAULT NULL,
  `alarm_type` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;