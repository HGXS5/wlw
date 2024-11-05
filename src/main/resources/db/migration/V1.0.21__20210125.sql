CREATE TABLE `cfg_reg_flow` (
  `id` int(11) NOT NULL,
  `recid` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `code` varchar(20) NOT NULL,
  `offset` int(11) NOT NULL,
  `length` int(11) NOT NULL DEFAULT '1',
  `type` varchar(20) NOT NULL DEFAULT 'WORD',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `cfg_reg_flow` VALUES ('1', '1', '前倾角', 'i33001', '15', '2', 'FLOAT');
INSERT INTO `cfg_reg_flow` VALUES ('2', '2', '斜倾角', 'i33002', '17', '2', 'FLOAT');
INSERT INTO `cfg_reg_flow` VALUES ('3', '3', '安装深度', 'i33003', '19', '2', 'FLOAT');
INSERT INTO `cfg_reg_flow` VALUES ('4', '4', '供电电压', 'i33004', '21', '2', 'FLOAT');
INSERT INTO `cfg_reg_flow` VALUES ('5', '5', '单元长度', 'i33005', '23', '1', 'FLOAT');
