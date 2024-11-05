INSERT INTO `cfg_name` VALUES ('126', '协议', 'DevModbusYuxing', '宇星总站协议');

CREATE TABLE `cfg_reg_yuxing` (
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


INSERT INTO `cfg_reg_yuxing` VALUES ('1', '1', '因子编码', 'FactorCode', '4096', '2', 'DWORD', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('2', '2', '单位', 'FactorUnit', '4098', '1', 'WORD', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('3', '3', '标样参考值', 'StdReferVal', '4099', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('4', '4', '水样数据时间', 'MeaTestTime', '4101', '3', 'DATE', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('5', '5', '水样实测值', 'MeaTestVal', '4104', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('6', '6', '水样数据标识', 'MeaTestFlag', '4106', '6', 'CHAR', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('7', '7', '标样数据时间', 'StdTestTime', '4112', '3', 'DATE', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('8', '8', '标样实测值', 'StdTestVal', '4115', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('9', '9', '标样数据标识', 'StdTestFlag', '4117', '6', 'CHAR', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('10', '10', '空白数据时间', 'BlnkTestTime', '4123', '3', 'DATE', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('11', '11', '空白实测值', 'BlnkTestVal', '4126', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('12', '12', '空白数据标识', 'BlnkTestFlag', '4128', '6', 'CHAR', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('13', '13', '零点核查数据时间', 'ZeroTestTime', '4134', '3', 'DATE', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('14', '14', '零点核查实测值', 'ZeroTestVal', '4137', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('15', '15', '零点核查数据标识', 'ZeroTestFlag', '4139', '6', 'CHAR', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('16', '16', '跨度核查数据时间', 'SpanTestTime', '4145', '3', 'DATE', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('17', '17', '跨度核查实测值', 'SpanTestVal', '4148', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('18', '18', '跨度核查数据标识', 'SpanTestFlag', '4150', '6', 'CHAR', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('19', '19', '加标回收数据时间', 'RcvrTestTime', '4156', '3', 'DATE', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('20', '20', '加标回收实测值', 'RcvrTestVal', '4159', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('21', '21', '加标回收数据标识', 'RcvrTestFlag', '4161', '6', 'CHAR', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('22', '22', '平行样数据时间', 'ParTestTime', '4167', '3', 'DATE', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('23', '23', '平行样实测值', 'ParTestVal', '4170', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('24', '24', '平行样数据标识', 'ParTestFlag', '4172', '6', 'CHAR', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('25', '25', '系统时间', 'DevTime', '4224', '3', 'DATE', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('26', '26', '工作状态', 'DevState', '4227', '1', 'WORD', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('27', '27', '测量模式', 'DevMode', '4228', '1', 'WORD', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('28', '28', '告警代码', 'DevAlarm', '4229', '1', 'WORD', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('29', '29', '故障代码', 'DevFault', '4230', '1', 'WORD', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('30', '30', '日志代码', 'DevLog', '4231', '1', 'WORD', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('31', '31', '软件版本', 'DevVersion', '4232', '1', 'WORD', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('32', '32', '测量间隔', 'MeaGap', '4233', '1', 'WORD', 'i13003');
INSERT INTO `cfg_reg_yuxing` VALUES ('33', '33', '零点核查间隔', 'ZeroGap', '4234', '1', 'WORD', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('34', '34', '跨度核查间隔', 'SpanGap', '4235', '1', 'WORD', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('35', '35', '标样核查间隔', 'StdGap', '4236', '1', 'WORD', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('36', '36', '测量精度', 'MeaAccuracy', '4256', '1', 'WORD', 'i13002');
INSERT INTO `cfg_reg_yuxing` VALUES ('37', '37', '消解温度', 'DigTemp', '4257', '1', 'WORD', 'i13004');
INSERT INTO `cfg_reg_yuxing` VALUES ('38', '38', '消解时长', 'DigTime', '4258', '1', 'WORD', 'i13005');
INSERT INTO `cfg_reg_yuxing` VALUES ('39', '39', '量程下限', 'RangeBottom', '4259', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('40', '40', '量程上限', 'RangeTop', '4261', '2', 'FLOAT', 'i13001');
INSERT INTO `cfg_reg_yuxing` VALUES ('41', '41', '曲线斜率k', 'SlopeK', '4263', '2', 'FLOAT', 'i13008');
INSERT INTO `cfg_reg_yuxing` VALUES ('42', '42', '曲线截距b', 'InterceptB', '4265', '2', 'FLOAT', 'i13007');
INSERT INTO `cfg_reg_yuxing` VALUES ('43', '43', '标定日期', 'CalTime', '4267', '3', 'DATE', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('44', '44', '标液一浓度', 'StdVal1', '4270', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('45', '45', '标液一测量过程值', 'ProcVal1', '4272', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('46', '46', '标液二浓度', 'StdVal2', '4274', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('47', '47', '标液二测量过程值', 'ProcVal2', '4276', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('48', '48', '标液三浓度', 'StdVal3', '4278', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('49', '49', '标液三测量过程值', 'ProcVal3', '4280', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('50', '50', '标液四浓度', 'StdVal4', '4282', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('51', '51', '标液四测量过程值', 'ProcVal4', '4284', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('52', '52', '标液五浓度', 'StdVal4', '4286', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('53', '53', '标液五测量过程值', 'ProcVal4', '4288', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('54', '54', '线性相关系数', 'LinearFactor', '4290', '2', 'FLOAT', 'i13011');
INSERT INTO `cfg_reg_yuxing` VALUES ('55', '55', '试剂余量', 'ReagentLeft', '4292', '2', 'DWORD', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('56', '56', '测量滴定值或吸光度', 'MeaProcVal', '4294', '2', 'FLOAT', 'i13010');
INSERT INTO `cfg_reg_yuxing` VALUES ('57', '57', '空白校准时间', 'BlnkCalTime', '4296', '3', 'DATE', 'i13006');
INSERT INTO `cfg_reg_yuxing` VALUES ('58', '58', '标样校准时间', 'StdCalTime', '4299', '3', 'DATE', 'i13013');
INSERT INTO `cfg_reg_yuxing` VALUES ('59', '59', '检出限值', 'DetectionVal', '4302', '2', 'FLOAT', 'i13009');
INSERT INTO `cfg_reg_yuxing` VALUES ('60', '60', '校准系数', 'CalFactor', '4304', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('61', '61', '设备序列号', 'DevSerialNumber', '4306', '6', 'CHAR', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('62', '62', '二次多项式系数', 'BinomialFactor', '4312', '2', 'FLOAT', 'i13012');
INSERT INTO `cfg_reg_yuxing` VALUES ('63', '63', '空白标定过程值', 'BlnkCheckProcVal', '4314', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('64', '64', '空白校准过程值', 'BlnkCalProcVal', '4316', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('65', '65', '标样校准参考值', 'StdCalStdVal', '4318', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('66', '66', '标样校准过程值', 'StdCalProcVal', '4320', '2', 'FLOAT', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('67', '67', '显色温度', 'ColorTemp', '4322', '1', 'WORD', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('68', '68', '显色时间', 'ColorTime', '4323', '1', 'WORD', null);
INSERT INTO `cfg_reg_yuxing` VALUES ('69', '69', '稀释倍数', 'DilutionFactor', '4437', '2', 'WORD', 'i13014');
INSERT INTO `cfg_reg_yuxing` VALUES ('70', '70', '量程系数', 'RangeFactor', '4438', '2', 'WORD', 'i13017');