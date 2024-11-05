CREATE TABLE `cfg_modbus_plc` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `address` int(11) NOT NULL DEFAULT '0',
  `length` int(11) NOT NULL DEFAULT '1',
  `type` varchar(20) NOT NULL DEFAULT 'FLOAT',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `cfg_modbus_plc` VALUES ('1', '水温', '0', '2', 'FLOAT');
INSERT INTO `cfg_modbus_plc` VALUES ('2', 'pH', '2', '2', 'FLOAT');
INSERT INTO `cfg_modbus_plc` VALUES ('3', '溶解氧', '4', '2', 'FLOAT');
INSERT INTO `cfg_modbus_plc` VALUES ('4', '浊度', '6', '2', 'FLOAT');
INSERT INTO `cfg_modbus_plc` VALUES ('5', '电导率', '8', '2', 'FLOAT');
INSERT INTO `cfg_modbus_plc` VALUES ('6', '氨氮', '10', '2', 'FLOAT');
INSERT INTO `cfg_modbus_plc` VALUES ('7', '总磷', '12', '2', 'FLOAT');
INSERT INTO `cfg_modbus_plc` VALUES ('8', '总氮', '14', '2', 'FLOAT');
INSERT INTO `cfg_modbus_plc` VALUES ('9', '高锰酸盐指数', '16', '2', 'FLOAT');

