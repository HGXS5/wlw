#增加Modbus定义表

CREATE TABLE `cfg_modbus` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `address` int(11) NOT NULL DEFAULT '0',
  `length` int(11) NOT NULL DEFAULT '1',
  `type` varchar(20) NOT NULL DEFAULT 'FLOAT',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `cfg_modbus` VALUES ('1', '时间', '0', '3', 'BCD');
INSERT INTO `cfg_modbus` VALUES ('2', '水温', '3', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('3', 'pH', '5', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('4', '溶解氧', '7', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('5', '浊度', '9', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('6', '电导率', '11', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('7', '叶绿素a', '13', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('8', '藻密度', '15', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('9', '蓝绿藻', '17', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('10', '氨氮', '19', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('11', '总磷', '21', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('12', '总氮', '23', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('13', '高锰酸盐指数', '25', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('14', '化学需氧量', '27', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('15', '总有机碳', '29', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('16', '温度', '31', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('17', '湿度', '33', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('18', '水压', '35', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('19', '综合生物毒性（发光菌）', '37', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('20', '铜', '39', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('21', '锌', '41', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('22', '氟化物', '43', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('23', '硒', '45', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('24', '砷', '47', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('25', '汞', '49', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('26', '镉', '51', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('27', '铬', '53', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('28', '六价铬', '55', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('29', '铅', '57', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('30', '氰化物', '59', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('31', '挥发酚', '61', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('32', '铁', '63', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('33', '锰', '65', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('34', '水位', '67', '2', 'FLOAT');
INSERT INTO `cfg_modbus` VALUES ('35', '流速', '69', '2', 'FLOAT');
