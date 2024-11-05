DROP TABLE IF EXISTS `cfg_upload_com`;
CREATE TABLE `cfg_upload_com` (
  `id` int(11) NOT NULL,
  `name` varchar(40) NOT NULL,
  `port` int(11) NOT NULL,
  `protocol` varchar(20) NOT NULL,
  `st` varchar(10) NOT NULL DEFAULT '21',
  `mn` varchar(40) NOT NULL,
  `password` varchar(20) NOT NULL,
  `used` tinyint(1) NOT NULL,
  `period_heart` int(11) DEFAULT '300',
  `period_real` int(11) DEFAULT '600',
  `period_sys_state` int(11) DEFAULT '300',
  `period_dev_state` int(11) DEFAULT '300',
  `period_sys_log` int(11) DEFAULT '300',
  `period_dev_log` int(11) DEFAULT '300',
  `over_time` int(11) DEFAULT '5',
  `re_count` int(11) DEFAULT '3',
  `auto_upload` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `cfg_upload_com` VALUES ('1', '上传通道一', '1', 'HJ212', '21', '12345678', '123456', '1', '60', '60', '0', '0', '0', '0', '10', '0', '2024-08-23 09:50:45');
INSERT INTO `cfg_upload_com` VALUES ('2', '上传通道二', '2', 'HJ212', '21', '12345678', '123456', '0', '30', '600', '300', '300', '300', '300', '5', '3', '2024-08-23 09:50:45');
INSERT INTO `cfg_upload_com` VALUES ('3', '上传通道三', '3', 'HJ212', '21', '12345678', '123456', '0', '300', '600', '300', '300', '300', '300', '10', '1', '2024-08-23 09:50:45');
INSERT INTO `cfg_upload_com` VALUES ('4', '上传通道四', '4', 'HJ212', '21', '12345678', '123456', '0', '300', '600', '300', '300', '300', '300', '5', '0', '2024-08-23 09:50:45');
INSERT INTO `cfg_upload_com` VALUES ('5', '上传通道五', '5', 'HJ212', '21', '12345678', '123456', '0', '300', '600', '300', '300', '300', '300', '5', '0', '2024-08-23 09:50:45');
INSERT INTO `cfg_upload_com` VALUES ('6', '上传通道六', '6', 'HJ212', '21', '12345678', '123456', '0', '300', '600', '300', '300', '300', '300', '5', '0', '2024-08-23 09:50:45');
INSERT INTO `cfg_upload_com` VALUES ('7', '上传通道七', '7', 'HJ212', '21', '12345678', '123456', '0', '300', '600', '300', '300', '300', '300', '5', '0', '2024-08-23 09:50:45');
INSERT INTO `cfg_upload_com` VALUES ('8', '上传通道八', '8', 'HJ212', '21', '12345678', '123456', '0', '300', '600', '300', '300', '300', '300', '5', '0', '2024-08-23 09:50:45');
INSERT INTO `cfg_upload_com` VALUES ('9', '上传通道九', '9', 'HJ212', '21', '12345678', '123456', '0', '300', '600', '300', '300', '300', '300', '5', '0', '2024-08-23 09:50:45');
INSERT INTO `cfg_upload_com` VALUES ('10', '上传通道十', '10', 'HJ212', '21', '12345678', '123456', '0', '300', '600', '300', '300', '300', '300', '5', '0', '2024-08-23 09:50:45');
