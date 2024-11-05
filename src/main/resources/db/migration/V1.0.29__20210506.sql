ALTER TABLE cfg_reg_five ADD COLUMN paramName varchar(16) DEFAULT NULL;
ALTER TABLE cfg_reg_flow ADD COLUMN paramName varchar(16) DEFAULT NULL;
ALTER TABLE cfg_reg_modbus ADD COLUMN paramName varchar(16) DEFAULT NULL;
ALTER TABLE cfg_upload_net ADD COLUMN auto_upload TIMESTAMP DEFAULT NOW();

CREATE TABLE `rec_dev_param` (
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `rec_dev_param_factor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rec_id` int(11) NOT NULL,
  `rec_time` datetime NOT NULL,
  `factor_id` int(11) NOT NULL,
  `param_name` varchar(16) NOT NULL,
  `param_value` varchar(32) DEFAULT NULL,
  `param_type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
