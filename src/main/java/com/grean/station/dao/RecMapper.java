//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.dao;

import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.rec.RecAlarmData;
import com.grean.station.domain.DO.rec.RecAlarmDev;
import com.grean.station.domain.DO.rec.RecAlarmSys;
import com.grean.station.domain.DO.rec.RecCheckBlank;
import com.grean.station.domain.DO.rec.RecCheckFive;
import com.grean.station.domain.DO.rec.RecCheckPar;
import com.grean.station.domain.DO.rec.RecCheckRcvr;
import com.grean.station.domain.DO.rec.RecCheckStd;
import com.grean.station.domain.DO.rec.RecCheckZero;
import com.grean.station.domain.DO.rec.RecDataAvg;
import com.grean.station.domain.DO.rec.RecDataFactor;
import com.grean.station.domain.DO.rec.RecDataReg;
import com.grean.station.domain.DO.rec.RecDataTime;
import com.grean.station.domain.DO.rec.RecDevParam;
import com.grean.station.domain.DO.rec.RecFaultDev;
import com.grean.station.domain.DO.rec.RecFaultSys;
import com.grean.station.domain.DO.rec.RecInfo;
import com.grean.station.domain.DO.rec.RecInfoDoor;
import com.grean.station.domain.DO.rec.RecLogDev;
import com.grean.station.domain.DO.rec.RecLogPlatform;
import com.grean.station.domain.DO.rec.RecLogSample;
import com.grean.station.domain.DO.rec.RecLogSys;
import com.grean.station.domain.DO.rec.RecLogUser;
import com.grean.station.domain.DO.rec.RecSample;
import com.grean.station.domain.request.DateRange;
import java.sql.Timestamp;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecMapper {
  List<RecDataReg> getRecDataRegList();

  int addRecDataReg(RecDataReg var1);

  List<RecDataTime> getRecTimeMinList();

  List<RecDataTime> getRecTimeMinListByTime(String var1, String var2);

  List<RecDataTime> getRecTimeMinListByRange(DateRange var1);

  RecDataTime getLastRecTimeMin();

  RecDataTime getRecTimeMinByTime(Timestamp var1);

  int addRecTimeMin(RecDataTime var1);

  int updateRecTimeMin1(RecDataTime var1);

  int updateRecTimeMin2(RecDataTime var1);

  int updateRecTimeMin3(RecDataTime var1);

  int updateRecTimeMin4(RecDataTime var1);

  int updateRecTimeMin5(RecDataTime var1);

  int updateRecTimeMin6(RecDataTime var1);

  int updateRecTimeMin7(RecDataTime var1);

  int updateRecTimeMin8(RecDataTime var1);

  int updateRecTimeMin9(RecDataTime var1);

  int updateRecTimeMin10(RecDataTime var1);

  List<RecDataFactor> getRecDataMinList();

  List<RecDataFactor> getRecDataMinListByTime(RecDataTime var1);

  List<RecDataFactor> getRecDataMinListByRange(DateRange var1);

  int addRecDataMin(RecDataFactor var1);

  List<RecDataTime> getRecTimeMinAvgList();

  List<RecDataTime> getRecTimeMinAvgListByTime(String var1, String var2);

  List<RecDataTime> getRecTimeMinAvgListByRange(DateRange var1);

  RecDataTime getLastRecTimeMinAvg();

  RecDataTime getRecTimeMinAvgByTime(Timestamp var1);

  int addRecTimeMinAvg(RecDataTime var1);

  int updateRecTimeMinAvg1(RecDataTime var1);

  int updateRecTimeMinAvg2(RecDataTime var1);

  int updateRecTimeMinAvg3(RecDataTime var1);

  int updateRecTimeMinAvg4(RecDataTime var1);

  int updateRecTimeMinAvg5(RecDataTime var1);

  int updateRecTimeMinAvg6(RecDataTime var1);

  int updateRecTimeMinAvg7(RecDataTime var1);

  int updateRecTimeMinAvg8(RecDataTime var1);

  int updateRecTimeMinAvg9(RecDataTime var1);

  int updateRecTimeMinAvg10(RecDataTime var1);

  List<RecDataAvg> getRecDataMinAvgList();

  List<RecDataAvg> getRecDataMinAvgListByTime(RecDataTime var1);

  int addRecDataMinAvg(RecDataAvg var1);

  List<RecDataTime> getRecTimeHourList();

  List<RecDataTime> getRecTimeHourListByTime(String var1, String var2);

  List<RecDataTime> getRecTimeHourListByRange(DateRange var1);

  List<RecDataTime> getRecTimeRangeListByRange(DateRange var1);

  List<RecDataTime> getRecTimeFlowListByRange(DateRange var1);

  RecDataTime getLastRecTimeHour();

  RecDataTime getLastRecTimeFlow();

  RecDataTime getRecTimeHourByTime(Timestamp var1);

  RecDataTime getRecTimeRangeByTime(Timestamp var1);

  RecDataTime getRecTimeFlowByTime(Timestamp var1);

  RecDataTime getRecTimeParamByTime(Timestamp var1);

  int addRecTimeHour(RecDataTime var1);

  int addRecTimeRange(RecDataTime var1);

  int addRecTimeFlow(RecDataTime var1);

  int addRecTimeParam(RecDataTime var1);

  int updateRecTimeHour1(RecDataTime var1);

  int updateRecTimeHour2(RecDataTime var1);

  int updateRecTimeHour3(RecDataTime var1);

  int updateRecTimeHour4(RecDataTime var1);

  int updateRecTimeHour5(RecDataTime var1);

  int updateRecTimeHour6(RecDataTime var1);

  int updateRecTimeHour7(RecDataTime var1);

  int updateRecTimeHour8(RecDataTime var1);

  int updateRecTimeHour9(RecDataTime var1);

  int updateRecTimeHour10(RecDataTime var1);

  List<RecDataTime> getRecTimeHour1();

  List<RecDataTime> getRecTimeHour2();

  List<RecDataTime> getRecTimeHour3();

  List<RecDataTime> getRecTimeHour4();

  List<RecDataTime> getRecTimeHour5();

  List<RecDataTime> getRecTimeHour6();

  List<RecDataTime> getRecTimeHour7();

  List<RecDataTime> getRecTimeHour8();

  List<RecDataTime> getRecTimeHour9();

  List<RecDataTime> getRecTimeHour10();

  List<RecDataFactor> getRecDataHourList();

  List<RecDataFactor> getRecDataHourListByTime(RecDataTime var1);

  List<RecDataFactor> getRecDataHourListByRange(DateRange var1);

  List<RecDataFactor> getRecDataRangeListByTime(RecDataTime var1);

  List<RecDataFactor> getRecDataFlowListByTime(RecDataTime var1);

  List<RecDevParam> getRecDataParamListByTime(RecDataTime var1);

  List<RecDataFactor> getRecDataHourListByIdRange(int var1, int var2);

  List<RecDataFactor> getRecDataRangeListByIdRange(int var1, int var2);

  List<RecDataFactor> getRecDataFlowListByIdRange(int var1, int var2);

  List<RecDataFactor> getRecDataParamListByIdRange(int var1, int var2);

  List<RecDataFactor> getLastRecDataHour(int var1);

  int addRecDataHour(RecDataFactor var1);

  int addRecDataRange(RecDataFactor var1);

  int addRecDataFlow(RecDataFactor var1);

  int addRecDataParam(RecDevParam var1);

  List<RecDataTime> getRecTimeHourAvgList();

  List<RecDataTime> getRecTimeHourAvgListByTime(String var1, String var2);

  List<RecDataTime> getRecTimeHourAvgListByRange(DateRange var1);

  RecDataTime getLastRecTimeHourAvg();

  RecDataTime getRecTimeHourAvgByTime(Timestamp var1);

  int addRecTimeHourAvg(RecDataTime var1);

  int updateRecTimeHourAvg1(RecDataTime var1);

  int updateRecTimeHourAvg2(RecDataTime var1);

  int updateRecTimeHourAvg3(RecDataTime var1);

  int updateRecTimeHourAvg4(RecDataTime var1);

  int updateRecTimeHourAvg5(RecDataTime var1);

  int updateRecTimeHourAvg6(RecDataTime var1);

  int updateRecTimeHourAvg7(RecDataTime var1);

  int updateRecTimeHourAvg8(RecDataTime var1);

  int updateRecTimeHourAvg9(RecDataTime var1);

  int updateRecTimeHourAvg10(RecDataTime var1);

  List<RecDataAvg> getRecDataHourAvgList();

  List<RecDataAvg> getRecDataHourAvgListByTime(RecDataTime var1);

  List<RecDataFactor> getRecDataHourAvgListByRange(DateRange var1);

  int addRecDataHourAvg(RecDataAvg var1);

  List<RecDataTime> getRecTimeDayAvgList();

  List<RecDataTime> getRecTimeDayAvgListByTime(String var1, String var2);

  List<RecDataTime> getRecTimeDayAvgListByRange(DateRange var1);

  RecDataTime getLastRecTimeDayAvg();

  RecDataTime getRecTimeDayAvgByTime(Timestamp var1);

  int addRecTimeDayAvg(RecDataTime var1);

  int updateRecTimeDayAvg1(RecDataTime var1);

  int updateRecTimeDayAvg2(RecDataTime var1);

  int updateRecTimeDayAvg3(RecDataTime var1);

  int updateRecTimeDayAvg4(RecDataTime var1);

  int updateRecTimeDayAvg5(RecDataTime var1);

  int updateRecTimeDayAvg6(RecDataTime var1);

  int updateRecTimeDayAvg7(RecDataTime var1);

  int updateRecTimeDayAvg8(RecDataTime var1);

  int updateRecTimeDayAvg9(RecDataTime var1);

  int updateRecTimeDayAvg10(RecDataTime var1);

  List<RecDataAvg> getRecDataDayAvgList();

  List<RecDataAvg> getRecDataDayAvgListByTime(RecDataTime var1);

  List<RecDataFactor> getRecDataDayAvgListByRange(DateRange var1);

  int addRecDataDayAvg(RecDataAvg var1);

  List<RecDataTime> getRecTimeMonthAvgList();

  List<RecDataTime> getRecTimeMonthAvgListByTime(String var1, String var2);

  List<RecDataTime> getRecTimeMonthAvgListByRange(DateRange var1);

  RecDataTime getLastRecTimeMonthAvg();

  RecDataTime getRecTimeMonthAvgByTime(Timestamp var1);

  int addRecTimeMonthAvg(RecDataTime var1);

  int updateRecTimeMonthAvg1(RecDataTime var1);

  int updateRecTimeMonthAvg2(RecDataTime var1);

  int updateRecTimeMonthAvg3(RecDataTime var1);

  int updateRecTimeMonthAvg4(RecDataTime var1);

  int updateRecTimeMonthAvg5(RecDataTime var1);

  int updateRecTimeMonthAvg6(RecDataTime var1);

  int updateRecTimeMonthAvg7(RecDataTime var1);

  int updateRecTimeMonthAvg8(RecDataTime var1);

  int updateRecTimeMonthAvg9(RecDataTime var1);

  int updateRecTimeMonthAvg10(RecDataTime var1);

  List<RecDataAvg> getRecDataMonthAvgList();

  List<RecDataAvg> getRecDataMonthAvgListByTime(RecDataTime var1);

  List<RecDataFactor> getRecDataMonthAvgListByRange(DateRange var1);

  int addRecDataMonthAvg(RecDataAvg var1);

  List<RecDataTime> getRecTimeFiveList();

  List<RecDataTime> getRecTimeFiveListByTime(String var1, String var2);

  List<RecDataTime> getRecTimeFiveListByRange(DateRange var1);

  RecDataTime getLastRecTimeFive();

  RecDataTime getRecTimeFiveByTime(Timestamp var1);

  int addRecTimeFive(RecDataTime var1);

  int updateRecTimeFive1(RecDataTime var1);

  int updateRecTimeFive2(RecDataTime var1);

  int updateRecTimeFive3(RecDataTime var1);

  int updateRecTimeFive4(RecDataTime var1);

  int updateRecTimeFive5(RecDataTime var1);

  int updateRecTimeFive6(RecDataTime var1);

  int updateRecTimeFive7(RecDataTime var1);

  int updateRecTimeFive8(RecDataTime var1);

  int updateRecTimeFive9(RecDataTime var1);

  int updateRecTimeFive10(RecDataTime var1);

  List<RecDataFactor> getRecDataFiveList();

  List<RecDataFactor> getRecDataFiveListByTime(RecDataTime var1);

  List<RecDataFactor> getRecDataFiveListByIdRange(int var1, int var2);

  int addRecDataFive(RecDataFactor var1);

  List<RecDataTime> getRecCheckZeroTimeList();

  List<RecDataTime> getRecCheckZeroTimeListByTime(String var1, String var2);

  List<RecDataTime> getRecCheckZeroTimeListByRange(DateRange var1);

  RecDataTime getLastRecCheckZeroTime();

  RecDataTime getRecCheckZeroTimeByTime(Timestamp var1);

  int addRecCheckZeroTime(RecDataTime var1);

  int updateRecCheckZeroTime1(RecDataTime var1);

  int updateRecCheckZeroTime2(RecDataTime var1);

  int updateRecCheckZeroTime3(RecDataTime var1);

  int updateRecCheckZeroTime4(RecDataTime var1);

  int updateRecCheckZeroTime5(RecDataTime var1);

  int updateRecCheckZeroTime6(RecDataTime var1);

  int updateRecCheckZeroTime7(RecDataTime var1);

  int updateRecCheckZeroTime8(RecDataTime var1);

  int updateRecCheckZeroTime9(RecDataTime var1);

  int updateRecCheckZeroTime10(RecDataTime var1);

  List<RecCheckZero> getRecCheckZeroDataList();

  List<RecCheckZero> getRecCheckZeroDataListByTime(RecDataTime var1);

  RecCheckZero getLastRecCheckZeroData(CfgFactor var1);

  int addRecCheckZeroData(RecCheckZero var1);

  List<RecDataTime> getRecCheckSpanTimeList();

  List<RecDataTime> getRecCheckSpanTimeListByTime(String var1, String var2);

  List<RecDataTime> getRecCheckSpanTimeListByRange(DateRange var1);

  RecDataTime getLastRecCheckSpanTime();

  RecDataTime getRecCheckSpanTimeByTime(Timestamp var1);

  int addRecCheckSpanTime(RecDataTime var1);

  int updateRecCheckSpanTime1(RecDataTime var1);

  int updateRecCheckSpanTime2(RecDataTime var1);

  int updateRecCheckSpanTime3(RecDataTime var1);

  int updateRecCheckSpanTime4(RecDataTime var1);

  int updateRecCheckSpanTime5(RecDataTime var1);

  int updateRecCheckSpanTime6(RecDataTime var1);

  int updateRecCheckSpanTime7(RecDataTime var1);

  int updateRecCheckSpanTime8(RecDataTime var1);

  int updateRecCheckSpanTime9(RecDataTime var1);

  int updateRecCheckSpanTime10(RecDataTime var1);

  List<RecCheckZero> getRecCheckSpanDataList();

  List<RecCheckZero> getRecCheckSpanDataListByTime(RecDataTime var1);

  RecCheckZero getLastRecCheckSpanData(CfgFactor var1);

  int addRecCheckSpanData(RecCheckZero var1);

  List<RecDataTime> getRecCheckRcvrTimeList();

  List<RecDataTime> getRecCheckRcvrTimeListByTime(String var1, String var2);

  List<RecDataTime> getRecCheckRcvrTimeListByRange(DateRange var1);

  RecDataTime getLastRecCheckRcvrTime();

  RecDataTime getRecCheckRcvrTimeByTime(Timestamp var1);

  int addRecCheckRcvrTime(RecDataTime var1);

  int updateRecCheckRcvrTime1(RecDataTime var1);

  int updateRecCheckRcvrTime2(RecDataTime var1);

  int updateRecCheckRcvrTime3(RecDataTime var1);

  int updateRecCheckRcvrTime4(RecDataTime var1);

  int updateRecCheckRcvrTime5(RecDataTime var1);

  int updateRecCheckRcvrTime6(RecDataTime var1);

  int updateRecCheckRcvrTime7(RecDataTime var1);

  int updateRecCheckRcvrTime8(RecDataTime var1);

  int updateRecCheckRcvrTime9(RecDataTime var1);

  int updateRecCheckRcvrTime10(RecDataTime var1);

  List<RecCheckRcvr> getRecCheckRcvrDataList();

  List<RecCheckRcvr> getRecCheckRcvrDataListByTime(RecDataTime var1);

  int addRecCheckRcvrData(RecCheckRcvr var1);

  List<RecDataTime> getRecCheckStdTimeList();

  List<RecDataTime> getRecCheckStdTimeListByTime(String var1, String var2);

  List<RecDataTime> getRecCheckStdTimeListByRange(DateRange var1);

  RecDataTime getLastRecCheckStdTime();

  RecDataTime getRecCheckStdTimeByTime(Timestamp var1);

  int addRecCheckStdTime(RecDataTime var1);

  int updateRecCheckStdTime1(RecDataTime var1);

  int updateRecCheckStdTime2(RecDataTime var1);

  int updateRecCheckStdTime3(RecDataTime var1);

  int updateRecCheckStdTime4(RecDataTime var1);

  int updateRecCheckStdTime5(RecDataTime var1);

  int updateRecCheckStdTime6(RecDataTime var1);

  int updateRecCheckStdTime7(RecDataTime var1);

  int updateRecCheckStdTime8(RecDataTime var1);

  int updateRecCheckStdTime9(RecDataTime var1);

  int updateRecCheckStdTime10(RecDataTime var1);

  List<RecCheckStd> getRecCheckStdDataList();

  List<RecCheckStd> getRecCheckStdDataListByTime(RecDataTime var1);

  int addRecCheckStdData(RecCheckStd var1);

  List<RecDataTime> getRecCheckParTimeList();

  List<RecDataTime> getRecCheckParTimeListByTime(String var1, String var2);

  List<RecDataTime> getRecCheckParTimeListByRange(DateRange var1);

  RecDataTime getLastRecCheckParTime();

  RecDataTime getRecCheckParTimeByTime(Timestamp var1);

  int addRecCheckParTime(RecDataTime var1);

  int updateRecCheckParTime1(RecDataTime var1);

  int updateRecCheckParTime2(RecDataTime var1);

  int updateRecCheckParTime3(RecDataTime var1);

  int updateRecCheckParTime4(RecDataTime var1);

  int updateRecCheckParTime5(RecDataTime var1);

  int updateRecCheckParTime6(RecDataTime var1);

  int updateRecCheckParTime7(RecDataTime var1);

  int updateRecCheckParTime8(RecDataTime var1);

  int updateRecCheckParTime9(RecDataTime var1);

  int updateRecCheckParTime10(RecDataTime var1);

  List<RecCheckPar> getRecCheckParDataList();

  List<RecCheckPar> getRecCheckParDataListByTime(RecDataTime var1);

  int addRecCheckParData(RecCheckPar var1);

  List<RecDataTime> getRecCheckBlankTimeList();

  List<RecDataTime> getRecCheckBlankTimeListByTime(String var1, String var2);

  List<RecDataTime> getRecCheckBlankTimeListByRange(DateRange var1);

  RecDataTime getLastRecCheckBlankTime();

  RecDataTime getRecCheckBlankTimeByTime(Timestamp var1);

  int addRecCheckBlankTime(RecDataTime var1);

  int updateRecCheckBlankTime1(RecDataTime var1);

  int updateRecCheckBlankTime2(RecDataTime var1);

  int updateRecCheckBlankTime3(RecDataTime var1);

  int updateRecCheckBlankTime4(RecDataTime var1);

  int updateRecCheckBlankTime5(RecDataTime var1);

  int updateRecCheckBlankTime6(RecDataTime var1);

  int updateRecCheckBlankTime7(RecDataTime var1);

  int updateRecCheckBlankTime8(RecDataTime var1);

  int updateRecCheckBlankTime9(RecDataTime var1);

  int updateRecCheckBlankTime10(RecDataTime var1);

  List<RecCheckBlank> getRecCheckBlankDataList();

  List<RecCheckBlank> getRecCheckBlankDataListByTime(RecDataTime var1);

  RecCheckBlank getLastRecCheckBlankData(CfgFactor var1);

  int addRecCheckBlankData(RecCheckBlank var1);

  List<RecDataTime> getRecCheckFiveTimeList();

  List<RecDataTime> getRecCheckFiveTimeListByTime(String var1, String var2);

  List<RecDataTime> getRecCheckFiveTimeListByRange(DateRange var1);

  RecDataTime getLastRecCheckFiveTime();

  RecDataTime getRecCheckFiveTimeByTime(Timestamp var1);

  int addRecCheckFiveTime(RecDataTime var1);

  int updateRecCheckFiveTime1(RecDataTime var1);

  int updateRecCheckFiveTime2(RecDataTime var1);

  int updateRecCheckFiveTime3(RecDataTime var1);

  int updateRecCheckFiveTime4(RecDataTime var1);

  int updateRecCheckFiveTime5(RecDataTime var1);

  int updateRecCheckFiveTime6(RecDataTime var1);

  int updateRecCheckFiveTime7(RecDataTime var1);

  int updateRecCheckFiveTime8(RecDataTime var1);

  int updateRecCheckFiveTime9(RecDataTime var1);

  int updateRecCheckFiveTime10(RecDataTime var1);

  List<RecCheckFive> getRecCheckFiveDataList();

  List<RecCheckFive> getRecCheckFiveDataListByTime(RecDataTime var1);

  RecCheckFive getLastRecCheckFiveData(CfgFactor var1);

  int addRecCheckFiveData(RecCheckFive var1);

  List<RecDataTime> getRecLogDevTimeList();

  List<RecDataTime> getRecLogDevTimeListByTime(String var1, String var2);

  List<RecDataTime> getRecLogDevTimeListByRange(DateRange var1);

  RecDataTime getLastRecLogDevTime();

  RecDataTime getRecLogDevTimeByTime(Timestamp var1);

  int addRecLogDevTime(RecDataTime var1);

  int updateRecLogDevTime1(RecDataTime var1);

  int updateRecLogDevTime2(RecDataTime var1);

  int updateRecLogDevTime3(RecDataTime var1);

  int updateRecLogDevTime4(RecDataTime var1);

  int updateRecLogDevTime5(RecDataTime var1);

  int updateRecLogDevTime6(RecDataTime var1);

  int updateRecLogDevTime7(RecDataTime var1);

  int updateRecLogDevTime8(RecDataTime var1);

  int updateRecLogDevTime9(RecDataTime var1);

  int updateRecLogDevTime10(RecDataTime var1);

  List<RecLogDev> getRecLogDevList();

  List<RecLogDev> getRecLogDevListByTime(RecDataTime var1);

  List<RecLogDev> getRecLogDevListByRange(DateRange var1);

  RecLogDev getLastRecLogDev(int var1);

  int addRecLogDev(RecLogDev var1);

  List<RecAlarmDev> getRecAlarmDevList();

  List<RecAlarmDev> getRecAlarmDevListByRange(DateRange var1);

  RecAlarmDev getLastRecAlarmDev(int var1);

  int addRecAlarmDev(RecAlarmDev var1);

  List<RecFaultDev> getRecFaultDevList();

  RecFaultDev getLastRecFaultDev(int var1);

  int addRecFaultDev(RecFaultDev var1);

  List<RecDataTime> getRecLogSysTimeList();

  List<RecDataTime> getRecLogSysTimeListByTime(String var1, String var2);

  List<RecDataTime> getRecLogSysTimeListByRange(DateRange var1);

  RecDataTime getLastRecLogSysTime();

  RecDataTime getRecLogSysTimeByTime(Timestamp var1);

  int addRecLogSysTime(RecDataTime var1);

  int updateRecLogSysTime1(RecDataTime var1);

  int updateRecLogSysTime2(RecDataTime var1);

  int updateRecLogSysTime3(RecDataTime var1);

  int updateRecLogSysTime4(RecDataTime var1);

  int updateRecLogSysTime5(RecDataTime var1);

  int updateRecLogSysTime6(RecDataTime var1);

  int updateRecLogSysTime7(RecDataTime var1);

  int updateRecLogSysTime8(RecDataTime var1);

  int updateRecLogSysTime9(RecDataTime var1);

  int updateRecLogSysTime10(RecDataTime var1);

  List<RecLogSys> getRecLogSysList();

  List<RecLogSys> getRecLogSysListByTime(RecDataTime var1);

  List<RecLogSys> getRecLogSysListByRange(DateRange var1);

  int addRecLogSys(RecLogSys var1);

  List<RecLogUser> getRecLogUserList();

  List<RecLogUser> getRecLogUserListByRange(DateRange var1);

  int addRecLogUser(RecLogUser var1);

  List<RecInfoDoor> getRecLogAccessListByRange(DateRange var1);

  List<RecLogSample> getRecLogSampleList();

  List<RecLogSample> getRecLogSampleListByRange(DateRange var1);

  int addRecLogSample(RecLogSample var1);

  List<RecLogPlatform> getRecLogPlatformList();

  List<RecLogPlatform> getRecLogPlatformListByRange(DateRange var1);

  int addRecLogPlatform(RecLogPlatform var1);

  List<RecAlarmSys> getRecAlarmSysList();

  int addRecAlarmSys(RecAlarmSys var1);

  List<RecFaultSys> getRecFaultSysList();

  List<RecFaultSys> getRecFaultSysListByRange(DateRange var1);

  int addRecFaultSys(RecFaultSys var1);

  List<RecAlarmData> getRecAlarmDataList();

  List<RecAlarmData> getRecAlarmDataListByRange(DateRange var1);

  RecAlarmData getLastRecAlarmData(int var1);

  int addRecAlarmData(RecAlarmData var1);

  List<RecInfoDoor> getRecInfoDoorList();

  RecInfoDoor getLastRecInfoDoor();

  int addRecInfoDoor(RecInfoDoor var1);

  List<RecInfo> getRecInfoGasList();

  RecInfo getLastRecInfoGas();

  int addRecInfoGas(RecInfo var1);

  List<RecInfo> getRecInfoWaterList();

  RecInfo getLastRecInfoWater();

  int addRecInfoWater(RecInfo var1);

  List<RecSample> getRecSample();

  void updateRecSample(RecSample var1);

  void keepTableSize(@Param("tableName") String var1, @Param("recSize") int var2);

  List<RecDataTime> getRecNullSendTimeList(@Param("tableName") String var1, @Param("sendTime") String var2, @Param("recSize") int var3);
}
