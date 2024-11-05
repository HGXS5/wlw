CREATE TABLE `cfg_reg_quality3` (
  `id` int(11) NOT NULL,
  `recid` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `code` varchar(20) NOT NULL,
  `offset` int(11) NOT NULL,
  `length` int(11) NOT NULL DEFAULT '1',
  `type` varchar(20) NOT NULL DEFAULT 'WORD',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `cfg_reg_quality3` VALUES ('1', '1', '状态', 'Status1', '12288', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('2', '2', '告警', 'Status2', '12289', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('3', '3', '保留', 'Status3', '12290', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('4', '4', '三通阀1', 'Status4', '12291', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('5', '5', '三通阀2', 'Status5', '12292', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('6', '6', '三通阀3', 'Status6', '12293', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('7', '7', '三通阀4', 'Status7', '12294', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('8', '8', '母液1', 'Status8', '12295', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('9', '9', '母液2', 'Status9', '12296', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('10', '10', '废液', 'Status10', '12297', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('11', '11', '水样', 'Status11', '12298', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('12', '12', '空气', 'Status12', '12299', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('13', '13', '纯水', 'Status13', '12300', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('14', '14', '备用', 'Status14', '12301', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('15', '15', '备用', 'Status15', '12302', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('16', '16', '备用', 'Status16', '12303', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('17', '17', '备用', 'Status17', '12304', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('18', '18', '备用', 'Status18', '12305', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('19', '19', '直流泵开关', 'Status19', '12306', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('20', '20', '直流泵方向', 'Status20', '12307', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('21', '21', '备用', 'Status21', '12308', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('22', '22', '备用', 'Status22', '12309', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('23', '23', '备用', 'Status23', '12310', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('24', '24', '备用', 'Status24', '12311', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('25', '25', '光耦1', 'Status25', '12312', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('26', '26', '光耦2', 'Status26', '12313', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('27', '27', '光耦3', 'Status27', '12314', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('28', '28', '光耦4', 'Status28', '12315', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('29', '29', '母液体积1', 'Param1', '16384', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('30', '30', '母液体积2', 'Param2', '16385', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('31', '31', '泵测试体积', 'Param3', '16386', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('32', '32', '泵测试时间', 'Param4', '16387', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('33', '33', '润洗体积', 'Param5', '16388', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('34', '34', '关阀延时', 'Param6', '16389', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('35', '35', '通信波特率', 'Param7', '16390', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('36', '36', '备用', 'Param8', '16391', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('37', '37', '泵测试体积', 'Param9', '16392', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('38', '38', '泵测试时间', 'Param10', '16393', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('39', '39', '模式设置', 'Param11', '16394', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('40', '40', '测量间隔', 'Param12', '16395', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('41', '41', '零核测量间隔', 'Param13', '16396', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('42', '42', '跨核测量间隔', 'Param14', '16397', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('43', '43', '标样测量间隔', 'Param15', '16398', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('44', '44', '水样通道号', 'Param16', '16404', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('45', '45', '质控高通道号', 'Param17', '16405', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('46', '46', '加标样通道号', 'Param18', '16406', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('47', '47', '质控中通道号', 'Param19', '16407', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('48', '48', '量核通道号', 'Param20', '16408', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('49', '49', '质控低通道号', 'Param21', '16409', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('50', '50', '零核通道号', 'Param22', '16410', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('51', '51', '空白样通道号', 'Param23', '16411', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('52', '52', '三通阀1', 'Output1', '20482', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('53', '53', '三通阀2', 'Output2', '20483', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('54', '54', '三通阀3', 'Output3', '20484', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('55', '55', '三通阀4', 'Output4', '20485', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('56', '56', '母液1', 'Output5', '20486', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('57', '57', '母液2', 'Output6', '20487', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('58', '58', '废液', 'Output7', '20488', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('59', '59', '水样', 'Output8', '20489', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('60', '60', '空气', 'Output9', '20490', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('61', '61', '纯水', 'Output10', '20491', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('62', '62', '备用', 'Output11', '20492', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('63', '63', '备用', 'Output12', '20493', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('64', '64', '备用', 'Output13', '20494', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('65', '65', '备用', 'Output14', '20495', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('66', '66', '备用', 'Output15', '20496', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('67', '67', '直流泵开关', 'Output16', '20497', '1', 'WORD');
INSERT INTO `cfg_reg_quality3` VALUES ('68', '68', '直流泵方向', 'Output17', '20498', '1', 'WORD');
