CREATE TABLE `cfg_upload_com` (
  `id` int(11) NOT NULL,
  `name` varchar(40) NOT NULL,
  `port` int(11) NOT NULL,
  `protocol` varchar(20) NOT NULL,
  `st` varchar(10) NOT NULL DEFAULT '21',
  `mn` varchar(20) NOT NULL,
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `cfg_upload_com` VALUES ('1', '上传通道一', '1', 'JS212YCDF', '21', 'A100000_0114', '571323', '1', '30', '60', '0', '0', '0', '0', '10', '0');
INSERT INTO `cfg_upload_com` VALUES ('2', '上传通道二', '2', 'SL651', '21', '43112400012', '123456', '0', '30', '60', '300', '300', '300', '300', '5', '0');
INSERT INTO `cfg_upload_com` VALUES ('3', '上传通道三', '3', 'HJ212TEST', '21', '12345678', '123456', '0', '300', '600', '300', '300', '300', '300', '5', '0');
