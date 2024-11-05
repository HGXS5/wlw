//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.dao;

import com.grean.station.domain.DO.cfg.CfgCamera;
import com.grean.station.domain.DO.cfg.CfgComm206;
import com.grean.station.domain.DO.cfg.CfgDev;
import com.grean.station.domain.DO.cfg.CfgDevAlarm;
import com.grean.station.domain.DO.cfg.CfgDevCmd;
import com.grean.station.domain.DO.cfg.CfgDevFault;
import com.grean.station.domain.DO.cfg.CfgDevLog;
import com.grean.station.domain.DO.cfg.CfgDevParam;
import com.grean.station.domain.DO.cfg.CfgDevQuery;
import com.grean.station.domain.DO.cfg.CfgDevReg;
import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.cfg.CfgFactorCode;
import com.grean.station.domain.DO.cfg.CfgFive;
import com.grean.station.domain.DO.cfg.CfgFlow;
import com.grean.station.domain.DO.cfg.CfgLogGroup;
import com.grean.station.domain.DO.cfg.CfgModbus;
import com.grean.station.domain.DO.cfg.CfgModbusVal;
import com.grean.station.domain.DO.cfg.CfgName;
import com.grean.station.domain.DO.cfg.CfgNetClient;
import com.grean.station.domain.DO.cfg.CfgSample;
import com.grean.station.domain.DO.cfg.CfgScheduleDay;
import com.grean.station.domain.DO.cfg.CfgScheduleMonth;
import com.grean.station.domain.DO.cfg.CfgScheduleQuality;
import com.grean.station.domain.DO.cfg.CfgScheduleSample;
import com.grean.station.domain.DO.cfg.CfgScheduleWeek;
import com.grean.station.domain.DO.cfg.CfgScript;
import com.grean.station.domain.DO.cfg.CfgSerial;
import com.grean.station.domain.DO.cfg.CfgStation;
import com.grean.station.domain.DO.cfg.CfgUnit;
import com.grean.station.domain.DO.cfg.CfgUploadCom;
import com.grean.station.domain.DO.cfg.CfgUploadNet;
import com.grean.station.domain.DO.cfg.CfgUser;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CfgMapper {
  List<CfgSerial> getCfgSerialList();

  void updateCfgSerial(CfgSerial var1);

  List<CfgUnit> getCfgUnitList();

  List<CfgDev> getCfgDevList();

  void truncateCfgDev();

  void addCfgDev(CfgDev var1);

  void deleteCfgDev(CfgDev var1);

  void updateCfgDev(CfgDev var1);

  List<CfgDevQuery> getCfgDevQueryList();

  List<CfgDevQuery> getDevQueryList(CfgDev var1);

  void addCfgDevQuery(CfgDevQuery var1);

  void updateCfgDevQuery(CfgDevQuery var1);

  void deleteCfgDevQuery(int var1, int var2);

  List<CfgDevCmd> getCfgDevCmdList();

  void addCfgDevCmd(CfgDevCmd var1);

  void deleteCfgDevCmd(int var1, int var2);

  List<CfgDevParam> getCfgDevParamList();

  void addCfgDevParam(CfgDevParam var1);

  void deleteCfgDevParam(CfgDevParam var1);

  void truncateCfgDevParam();

  List<CfgDevLog> getCfgDevLogList();

  List<CfgDevAlarm> getCfgDevAlarmList();

  List<CfgDevFault> getCfgDevFaultList();

  List<CfgLogGroup> getCfgLogGroupList();

  List<CfgDevReg> getCfgRegModbusList();

  List<CfgDevReg> getCfgRegFiveList();

  List<CfgDevReg> getCfgRegQualityList();

  List<CfgDevReg> getCfgRegQualityList3();

  List<CfgDevReg> getCfgRegQualityList4();

  List<CfgDevReg> getCfgRegFlowList();

  List<CfgDevReg> getCfgRegYiwenList();

  List<CfgDevReg> getCfgRegYuxingList();

  List<CfgDevReg> getCfgDevRegList(@Param("regTableName") String var1);

  List<CfgFactorCode> getCfgFactorCodeList(@Param("codeTableName") String var1);

  List<CfgFactor> getAllCfgFactorList();

  void updateAllCfgFactor(CfgFactor var1);

  void updateAllCfgFactorStdVal(CfgFactor var1);

  void updateAllCfgFactorRcvrVal(CfgFactor var1);

  void updateAllCfgFactorSpanVal(CfgFactor var1);

  void updateAllCfgFactorErrorVal(CfgFactor var1);

  void updateAllCfgFactorDriftVal(CfgFactor var1);

  List<CfgFactor> getCfgFactorList();

  void truncateCfgFactor();

  void updateCfgFactor(CfgFactor var1);

  void addCfgFactor(CfgFactor var1);

  void deleteCfgFactor(CfgFactor var1);

  void updateCfgFactorStdVal(CfgFactor var1);

  void updateCfgFactorRcvrVal(CfgFactor var1);

  void updateCfgFactorSpanVal(CfgFactor var1);

  void updateCfgFactorErrorVal(CfgFactor var1);

  void updateCfgFactorDriftVal(CfgFactor var1);

  List<CfgNetClient> getCfgNetClientList();

  void updateCfgNetClient(CfgNetClient var1);

  List<CfgScheduleDay> getCfgScheduleDayList();

  void updateCfgScheduleDay(CfgScheduleDay var1);

  List<CfgScheduleWeek> getCfgScheduleWeekList();

  List<CfgScheduleMonth> getCfgScheduleMonthList();

  void insertCfgScheduleMonth(CfgScheduleMonth var1);

  void truncateCfgScheduleMonth();

  List<CfgScheduleSample> getCfgScheduleSampleList();

  void updateCfgScheduleSample(CfgScheduleSample var1);

  List<CfgScheduleQuality> getCfgScheduleQualityList();

  void updateCfgScheduleQuality(CfgScheduleQuality var1);

  List<CfgScript> getCfgScriptList();

  List<CfgUploadNet> getCfgUploadNetList();

  int updateCfgUploadNet(CfgUploadNet var1);

  List<CfgUploadCom> getCfgUploadComList();

  int updateCfgUploadCom(CfgUploadCom var1);

  List<CfgUser> getCfgUserList();

  CfgUser getCfgUserByName(String var1);

  void addCfgUser(CfgUser var1);

  void updateCfgUser(CfgUser var1);

  void updateCfgUserPassword(CfgUser var1);

  void updateCfgAccessPassword(CfgUser var1);

  void updateCfgUserLoginTime(CfgUser var1);

  void truncateCfgUser();

  void deleteCfgUser(CfgUser var1);

  CfgStation getCfgStation();

  void updateCfgStation(CfgStation var1);

  CfgSample getCfgSample();

  void updateCfgSample(CfgSample var1);

  List<CfgName> getCfgNameList();

  CfgCamera getCfgCamera();

  void updateCfgCamera(CfgCamera var1);

  List<CfgScheduleSample> getCfgScheduleFiveList();

  void updateCfgScheduleFive(CfgScheduleSample var1);

  CfgFive getCfgFive();

  void updateCfgFive(CfgFive var1);

  CfgFlow getCfgFlow();

  void updateCfgFlow(CfgFlow var1);

  List<CfgComm206> getCfgComm206List();

  List<CfgModbus> getCfgModbusList();

  List<CfgModbusVal> getCfgModbusValList();
}
