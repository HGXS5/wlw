INSERT INTO `cfg_name` VALUES ('118', '协议', 'QualityModbus4', '四参数质控协议');

CREATE TABLE `cfg_reg_quality4` (
  `id` int(11) NOT NULL,
  `recid` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `code` varchar(20) NOT NULL,
  `offset` int(11) NOT NULL,
  `length` int(11) NOT NULL DEFAULT '1',
  `type` varchar(20) NOT NULL DEFAULT 'WORD',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `cfg_reg_quality4` VALUES ('1', '1', '状态', 'Status1', '12288', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('2', '2', 'COD液位', 'Status2', '12289', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('3', '3', '总氮液位', 'Status3', '12290', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('4', '4', '总磷液位', 'Status4', '12291', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('5', '5', '氨氮液位', 'Status5', '12292', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('6', '6', '备用', 'Status6', '12293', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('7', '7', '备用', 'Status7', '12294', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('8', '8', '备用', 'Status8', '12295', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('9', '9', '备用', 'Status9', '12296', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('10', '10', '备用', 'Status10', '12297', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('11', '11', '备用', 'Status11', '12298', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('12', '12', '备用', 'Status12', '12299', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('13', '13', '备用', 'Status13', '12300', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('14', '14', '备用', 'Status14', '12301', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('15', '15', '备用', 'Status15', '12302', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('16', '16', '备用', 'Status16', '12303', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('17', '17', '设备地址', 'Param1', '16384', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('18', '18', 'COD母液', 'Param2', '16385', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('19', '19', '总氮母液', 'Param3', '16386', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('20', '20', '总磷母液', 'Param4', '16387', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('21', '21', '氨氮母液', 'Param5', '16388', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('22', '22', '润洗时间', 'Param6', '16389', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('23', '23', '进样时间', 'Param7', '16390', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('24', '24', '混匀时间', 'Param8', '16391', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('25', '25', '抽样时间', 'Param9', '16392', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('26', '26', '清洗时间', 'Param10', '16393', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('27', '27', '排空时间', 'Param11', '16394', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('28', '28', '复位次数', 'Param12', '16395', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('29', '29', '清洗阀', 'Output1', '20481', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('30', '30', '空气阀', 'Output2', '20482', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('31', '31', '采样泵', 'Output3', '20483', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('32', '32', '备用4', 'Output4', '20484', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('33', '33', '排空阀1', 'Output5', '20485', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('34', '34', '排空阀2', 'Output6', '20486', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('35', '35', '排空阀3', 'Output7', '20487', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('36', '36', '排空阀4', 'Output8', '20488', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('37', '37', '进液阀1', 'Output9', '20489', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('38', '38', '进液阀2', 'Output10', '20490', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('39', '39', '进液阀3', 'Output11', '20491', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('40', '40', '进液阀4', 'Output12', '20492', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('41', '41', '排液阀1', 'Output13', '20493', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('42', '42', '排液阀2', 'Output14', '20494', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('43', '43', '排液阀3', 'Output15', '20495', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('44', '44', '排液阀4', 'Output16', '20496', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('45', '45', '除挂阀1', 'Output17', '20497', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('46', '46', '除挂阀2', 'Output18', '20498', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('47', '47', '除挂阀3', 'Output19', '20499', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('48', '48', '除挂阀4', 'Output20', '20500', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('49', '49', '备用21', 'Output21', '20501', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('50', '50', '备用22', 'Output22', '20502', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('51', '51', '备用23', 'Output23', '20503', '1', 'WORD');
INSERT INTO `cfg_reg_quality4` VALUES ('52', '52', '备用24', 'Output24', '20504', '1', 'WORD');
