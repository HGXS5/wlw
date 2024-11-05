INSERT INTO `cfg_name` VALUES ('101', '协议', 'cod.DevCodHongkai', '鸿恺COD协议');
INSERT INTO `cfg_name` VALUES ('102', '协议', 'chlorophyll.DevChlorophyllBbe1', 'BBE藻类协议1');
INSERT INTO `cfg_name` VALUES ('103', '协议', 'chlorophyll.DevChlorophyllBbe2', 'BBE藻类协议2');
INSERT INTO `cfg_name` VALUES ('104', '协议', 'DevModbusYiwen', '广州怡文协议');

CREATE TABLE `cfg_reg_yiwen` (
  `id` int(11) NOT NULL,
  `recid` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `code` varchar(20) NOT NULL,
  `offset` int(11) NOT NULL,
  `length` int(11) NOT NULL,
  `type` varchar(20) NOT NULL,
  `paramName` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `cfg_reg_yiwen` VALUES ('1', '1', '因子编码', 'FactorCode', '4096', '2', 'DWORD', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('2', '2', '单位', 'FactorUnit', '4098', '1', 'WORD', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('3', '3', '标样参考值', 'StdReferVal', '4099', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('4', '4', '水样数据时间', 'MeaTestTime', '4101', '3', 'DATE', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('5', '5', '水样实测值', 'MeaTestVal', '4104', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('6', '6', '水样数据标识', 'MeaTestFlag', '4106', '6', 'CHAR', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('7', '7', '标样数据时间', 'StdTestTime', '4112', '3', 'DATE', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('8', '8', '标样实测值', 'StdTestVal', '4115', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('9', '9', '标样数据标识', 'StdTestFlag', '4117', '6', 'CHAR', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('10', '10', '空白数据时间', 'BlnkTestTime', '4123', '3', 'DATE', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('11', '11', '空白实测值', 'BlnkTestVal', '4126', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('12', '12', '空白数据标识', 'BlnkTestFlag', '4128', '6', 'CHAR', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('13', '13', '零点核查数据时间', 'ZeroTestTime', '4134', '3', 'DATE', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('14', '14', '零点核查实测值', 'ZeroTestVal', '4137', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('15', '15', '零点核查数据标识', 'ZeroTestFlag', '4139', '6', 'CHAR', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('16', '16', '跨度核查数据时间', 'SpanTestTime', '4145', '3', 'DATE', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('17', '17', '跨度核查实测值', 'SpanTestVal', '4148', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('18', '18', '跨度核查数据标识', 'SpanTestFlag', '4150', '6', 'CHAR', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('19', '19', '加标回收数据时间', 'RcvrTestTime', '4101', '3', 'DATE', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('20', '20', '加标回收实测值', 'RcvrTestVal', '4104', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('21', '21', '加标回收数据标识', 'RcvrTestFlag', '4106', '6', 'CHAR', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('22', '22', '平行样数据时间', 'ParTestTime', '4167', '3', 'DATE', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('23', '23', '平行样实测值', 'ParTestVal', '4170', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('24', '24', '平行样数据标识', 'ParTestFlag', '4172', '6', 'CHAR', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('25', '25', '系统时间', 'DevTime', '4224', '3', 'DATE', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('26', '26', '工作状态', 'DevState', '4227', '1', 'WORD', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('27', '27', '测量模式', 'DevMode', '4228', '1', 'WORD', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('28', '28', '告警代码', 'DevAlarm', '4229', '1', 'WORD', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('29', '29', '故障代码', 'DevFault', '4230', '1', 'WORD', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('30', '30', '日志代码', 'DevLog', '4231', '1', 'WORD', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('31', '31', '软件版本', 'DevVersion', '4232', '1', 'WORD', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('32', '32', '测量间隔', 'MeaGap', '4233', '1', 'WORD', 'i13003');
INSERT INTO `cfg_reg_yiwen` VALUES ('33', '33', '零点核查间隔', 'ZeroGap', '4234', '1', 'WORD', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('34', '34', '跨度核查间隔', 'SpanGap', '4235', '1', 'WORD', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('35', '35', '标样核查间隔', 'StdGap', '4236', '1', 'WORD', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('36', '36', '测量精度', 'MeaAccuracy', '4256', '1', 'WORD', 'i13002');
INSERT INTO `cfg_reg_yiwen` VALUES ('37', '37', '消解温度', 'DigTemp', '4257', '1', 'WORD', 'i13004');
INSERT INTO `cfg_reg_yiwen` VALUES ('38', '38', '消解时长', 'DigTime', '4258', '1', 'WORD', 'i13005');
INSERT INTO `cfg_reg_yiwen` VALUES ('39', '39', '量程下限', 'RangeBottom', '4259', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('40', '40', '量程上限', 'RangeTop', '4261', '2', 'FLOAT', 'i13001');
INSERT INTO `cfg_reg_yiwen` VALUES ('41', '41', '曲线斜率k', 'SlopeK', '4263', '2', 'FLOAT', 'i13008');
INSERT INTO `cfg_reg_yiwen` VALUES ('42', '42', '曲线截距b', 'InterceptB', '4265', '2', 'FLOAT', 'i13007');
INSERT INTO `cfg_reg_yiwen` VALUES ('43', '43', '标定日期', 'CalTime', '4267', '3', 'DATE', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('44', '44', '标液一浓度', 'StdVal1', '4270', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('45', '45', '标液一测量过程值', 'ProcVal1', '4272', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('46', '46', '标液二浓度', 'StdVal2', '4274', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('47', '47', '标液二测量过程值', 'ProcVal2', '4276', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('48', '48', '标液三浓度', 'StdVal3', '4278', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('49', '49', '标液三测量过程值', 'ProcVal3', '4280', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('50', '50', '标液四浓度', 'StdVal4', '4282', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('51', '51', '标液四测量过程值', 'ProcVal4', '4284', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('52', '52', '标液五浓度', 'StdVal4', '4286', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('53', '53', '标液五测量过程值', 'ProcVal4', '4288', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('54', '54', '线性相关系数', 'LinearFactor', '4290', '2', 'FLOAT', 'i13011');
INSERT INTO `cfg_reg_yiwen` VALUES ('55', '55', '试剂余量', 'ReagentLeft', '4292', '2', 'DWORD', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('56', '56', '测量滴定值或吸光度', 'MeaProcVal', '4294', '2', 'FLOAT', 'i13010');
INSERT INTO `cfg_reg_yiwen` VALUES ('57', '57', '空白校准时间', 'BlnkCalTime', '4296', '3', 'DATE', 'i13006');
INSERT INTO `cfg_reg_yiwen` VALUES ('58', '58', '标样校准时间', 'StdCalTime', '4299', '3', 'DATE', 'i13013');
INSERT INTO `cfg_reg_yiwen` VALUES ('59', '59', '检出限值', 'DetectionVal', '4302', '2', 'FLOAT', 'i13009');
INSERT INTO `cfg_reg_yiwen` VALUES ('60', '60', '校准系数', 'CalFactor', '4304', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('61', '61', '设备序列号', 'DevSerialNumber', '4306', '6', 'CHAR', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('62', '62', '二次多项式系数', 'BinomialFactor', '4312', '2', 'FLOAT', 'i13012');
INSERT INTO `cfg_reg_yiwen` VALUES ('63', '63', '空白标定过程值', 'BlnkCheckProcVal', '4314', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('64', '64', '空白校准过程值', 'BlnkCalProcVal', '4316', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('65', '65', '标样校准参考值', 'StdCalStdVal', '4318', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('66', '66', '标样校准过程值', 'StdCalProcVal', '4320', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('67', '67', '显色温度', 'ColorTemp', '4322', '1', 'WORD', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('68', '68', '显色时间', 'ColorTime', '4323', '1', 'WORD', null);
INSERT INTO `cfg_reg_yiwen` VALUES ('69', '69', '稀释倍数', 'DilutionFactor', '4324', '2', 'FLOAT', 'i13014');
INSERT INTO `cfg_reg_yiwen` VALUES ('70', '70', '三项式系数', 'TrinomialFactor', '4326', '2', 'FLOAT', 'i13015');
INSERT INTO `cfg_reg_yiwen` VALUES ('71', '71', '空白标定系数', 'BlnkCalFactor', '4328', '2', 'FLOAT', 'i13016');
INSERT INTO `cfg_reg_yiwen` VALUES ('72', '72', '量程系数', 'RangeFactor', '4330', '2', 'FLOAT', 'i13017');
