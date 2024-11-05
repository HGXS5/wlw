//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.cfg;

import java.sql.Timestamp;
import java.util.Map;
import org.apache.commons.collections4.map.HashedMap;

public class CfgFactor {
  int id;
  int devID;
  int devChannel;
  int devAddress;
  String name;
  String type;
  String unit;
  int decimals;
  double range;
  double alarmMax;
  double alarmMin;
  boolean used;
  String code;
  String codeGJ;
  String codeZJ;
  String codeSL;
  double level1;
  double level2;
  double level3;
  double level4;
  double level5;
  boolean nonNegative;
  int checkType;
  double unitParam = 1.0D;
  double spanVal;
  double zeroStdVal;
  double spanStdVal;
  double stdStdVal;
  double blnkStdVal;
  double rcvrMotherVal;
  double rcvrMotherVol;
  double rcvrDestVal;
  double rcvrCupVol;
  double rcvrRateMin;
  double rcvrRateMax;
  double rcvrMultiple;
  int rcvrType;
  double rcvrLimit;
  double zeroErrorMin;
  double zeroErrorMax;
  double zeroDriftMin;
  double zeroDriftMax;
  double spanErrorMin;
  double spanErrorMax;
  double spanDriftMin;
  double spanDriftMax;
  double stdErrorMin;
  double stdErrorMax;
  double stdDriftMin;
  double stdDriftMax;
  double blnkErrorMin;
  double blnkErrorMax;
  double blnkDriftMin;
  double blnkDriftMax;
  double parDriftMin;
  double parDriftMax;
  Double dataMea;
  Double dataStd;
  Double dataZero;
  Double dataSpan;
  Double dataBlnk;
  Double dataPar;
  Double dataRcvr;
  String flagMea;
  String flagStd;
  String flagZero;
  String flagSpan;
  String flagBlnk;
  String flagPar;
  String flagRcvr;
  Timestamp timeMea;
  Timestamp timeStd;
  Timestamp timeZero;
  Timestamp timeSpan;
  Timestamp timeBlnk;
  Timestamp timePar;
  Timestamp timeRcvr;
  Double dataAvg = 0.0D;
  int sizeAvg = 0;
  boolean checkAvg;
  Double dataTmp = 0.0D;
  int curLevel = 0;
  int avgLevel = 0;
  int tmpLevel = 0;
  boolean upload = true;
  double rangeMax = 0.0D;
  boolean rangeChange = false;
  public Map<String, String> paramMap = new HashedMap();

  public CfgFactor() {
  }

  public boolean isParmType() {
    return this.type != null && this.type.toUpperCase().equals("PARM");
  }

  public boolean isFiveType() {
    return this.type != null && this.type.toUpperCase().equals("FIVE");
  }

  public boolean isSysType() {
    return this.type != null && this.type.toUpperCase().equals("SYS");
  }

  public boolean isSwType() {
    return this.type != null && this.type.toUpperCase().equals("SW");
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getDevID() {
    return this.devID;
  }

  public void setDevID(int devID) {
    this.devID = devID;
  }

  public int getDevChannel() {
    return this.devChannel;
  }

  public void setDevChannel(int devChannel) {
    this.devChannel = devChannel;
  }

  public int getDevAddress() {
    return this.devAddress;
  }

  public void setDevAddress(int devAddress) {
    this.devAddress = devAddress;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUnit() {
    return this.unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public int getDecimals() {
    return this.decimals;
  }

  public void setDecimals(int decimals) {
    this.decimals = decimals;
  }

  public double getRange() {
    return this.range;
  }

  public void setRange(double range) {
    this.range = range;
  }

  public double getAlarmMax() {
    return this.alarmMax;
  }

  public void setAlarmMax(double alarmMax) {
    this.alarmMax = alarmMax;
  }

  public double getAlarmMin() {
    return this.alarmMin;
  }

  public void setAlarmMin(double alarmMin) {
    this.alarmMin = alarmMin;
  }

  public boolean isUsed() {
    return this.used;
  }

  public void setUsed(boolean used) {
    this.used = used;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getCodeGJ() {
    return this.codeGJ;
  }

  public void setCodeGJ(String codeGJ) {
    this.codeGJ = codeGJ;
  }

  public String getCodeZJ() {
    return this.codeZJ;
  }

  public void setCodeZJ(String codeZJ) {
    this.codeZJ = codeZJ;
  }

  public String getCodeSL() {
    return this.codeSL;
  }

  public void setCodeSL(String codeSL) {
    this.codeSL = codeSL;
  }

  public double getSpanVal() {
    return this.spanVal;
  }

  public void setSpanVal(double spanVal) {
    this.spanVal = spanVal;
  }

  public double getStdStdVal() {
    return this.stdStdVal;
  }

  public void setStdStdVal(double stdStdVal) {
    this.stdStdVal = stdStdVal;
  }

  public double getBlnkStdVal() {
    return this.blnkStdVal;
  }

  public void setBlnkStdVal(double blnkStdVal) {
    this.blnkStdVal = blnkStdVal;
  }

  public double getZeroStdVal() {
    return this.zeroStdVal;
  }

  public void setZeroStdVal(double zeroStdVal) {
    this.zeroStdVal = zeroStdVal;
  }

  public double getSpanStdVal() {
    return this.spanStdVal;
  }

  public void setSpanStdVal(double spanStdVal) {
    this.spanStdVal = spanStdVal;
  }

  public double getZeroErrorMin() {
    return this.zeroErrorMin;
  }

  public void setZeroErrorMin(double zeroErrorMin) {
    this.zeroErrorMin = zeroErrorMin;
  }

  public double getZeroErrorMax() {
    return this.zeroErrorMax;
  }

  public void setZeroErrorMax(double zeroErrorMax) {
    this.zeroErrorMax = zeroErrorMax;
  }

  public double getZeroDriftMin() {
    return this.zeroDriftMin;
  }

  public void setZeroDriftMin(double zeroDriftMin) {
    this.zeroDriftMin = zeroDriftMin;
  }

  public double getZeroDriftMax() {
    return this.zeroDriftMax;
  }

  public void setZeroDriftMax(double zeroDriftMax) {
    this.zeroDriftMax = zeroDriftMax;
  }

  public double getSpanErrorMin() {
    return this.spanErrorMin;
  }

  public void setSpanErrorMin(double spanErrorMin) {
    this.spanErrorMin = spanErrorMin;
  }

  public double getSpanErrorMax() {
    return this.spanErrorMax;
  }

  public void setSpanErrorMax(double spanErrorMax) {
    this.spanErrorMax = spanErrorMax;
  }

  public double getSpanDriftMin() {
    return this.spanDriftMin;
  }

  public void setSpanDriftMin(double spanDriftMin) {
    this.spanDriftMin = spanDriftMin;
  }

  public double getSpanDriftMax() {
    return this.spanDriftMax;
  }

  public void setSpanDriftMax(double spanDriftMax) {
    this.spanDriftMax = spanDriftMax;
  }

  public double getRcvrMotherVal() {
    return this.rcvrMotherVal;
  }

  public void setRcvrMotherVal(double rcvrMotherVal) {
    this.rcvrMotherVal = rcvrMotherVal;
  }

  public double getRcvrMotherVol() {
    return this.rcvrMotherVol;
  }

  public void setRcvrMotherVol(double rcvrMotherVol) {
    this.rcvrMotherVol = rcvrMotherVol;
  }

  public double getRcvrDestVal() {
    return this.rcvrDestVal;
  }

  public void setRcvrDestVal(double rcvrDestVal) {
    this.rcvrDestVal = rcvrDestVal;
  }

  public double getRcvrCupVol() {
    return this.rcvrCupVol;
  }

  public void setRcvrCupVol(double rcvrCupVol) {
    this.rcvrCupVol = rcvrCupVol;
  }

  public double getRcvrRateMin() {
    return this.rcvrRateMin;
  }

  public void setRcvrRateMin(double rcvrRateMin) {
    this.rcvrRateMin = rcvrRateMin;
  }

  public double getRcvrRateMax() {
    return this.rcvrRateMax;
  }

  public void setRcvrRateMax(double rcvrRateMax) {
    this.rcvrRateMax = rcvrRateMax;
  }

  public double getStdErrorMin() {
    return this.stdErrorMin;
  }

  public void setStdErrorMin(double stdErrorMin) {
    this.stdErrorMin = stdErrorMin;
  }

  public double getStdErrorMax() {
    return this.stdErrorMax;
  }

  public void setStdErrorMax(double stdErrorMax) {
    this.stdErrorMax = stdErrorMax;
  }

  public double getStdDriftMin() {
    return this.stdDriftMin;
  }

  public void setStdDriftMin(double stdDriftMin) {
    this.stdDriftMin = stdDriftMin;
  }

  public double getStdDriftMax() {
    return this.stdDriftMax;
  }

  public void setStdDriftMax(double stdDriftMax) {
    this.stdDriftMax = stdDriftMax;
  }

  public double getBlnkErrorMin() {
    return this.blnkErrorMin;
  }

  public void setBlnkErrorMin(double blnkErrorMin) {
    this.blnkErrorMin = blnkErrorMin;
  }

  public double getBlnkErrorMax() {
    return this.blnkErrorMax;
  }

  public void setBlnkErrorMax(double blnkErrorMax) {
    this.blnkErrorMax = blnkErrorMax;
  }

  public double getBlnkDriftMin() {
    return this.blnkDriftMin;
  }

  public void setBlnkDriftMin(double blnkDriftMin) {
    this.blnkDriftMin = blnkDriftMin;
  }

  public double getBlnkDriftMax() {
    return this.blnkDriftMax;
  }

  public void setBlnkDriftMax(double blnkDriftMax) {
    this.blnkDriftMax = blnkDriftMax;
  }

  public double getParDriftMin() {
    return this.parDriftMin;
  }

  public void setParDriftMin(double parDriftMin) {
    this.parDriftMin = parDriftMin;
  }

  public double getParDriftMax() {
    return this.parDriftMax;
  }

  public void setParDriftMax(double parDriftMax) {
    this.parDriftMax = parDriftMax;
  }

  public double getRcvrLimit() {
    return this.rcvrLimit;
  }

  public void setRcvrLimit(double rcvrLimit) {
    this.rcvrLimit = rcvrLimit;
  }

  public int getDataLevel(String factorName, double factorValue) {

    byte iLevel;
    if (this.level1 == 0.0D && this.level2 == 0.0D && this.level3 == 0.0D) {
      iLevel = 0;
    } else if (factorName.equals("溶解氧")) {
      if (factorValue >= this.level1) {
        iLevel = 1;
      } else if (factorValue >= this.level2) {
        iLevel = 2;
      } else if (factorValue >= this.level3) {
        iLevel = 3;
      } else if (factorValue >= this.level4) {
        iLevel = 4;
      } else {
        iLevel = 5;
      }
    } else if (factorValue <= this.level1) {
      iLevel = 1;
    } else if (factorValue <= this.level2) {
      iLevel = 2;
    } else if (factorValue <= this.level3) {
      iLevel = 3;
    } else if (factorValue <= this.level4) {
      iLevel = 4;
    } else {
      iLevel = 5;
    }

    return iLevel;
  }

  public Double getDataMea() {
    return this.dataMea;
  }

  public void setDataMea(Double dataMea) {
    if (this.nonNegative) {
      if (dataMea != null && dataMea < 0.0D) {
        this.dataMea = 0.0D;
      } else {
        this.dataMea = dataMea;
      }
    } else {
      this.dataMea = dataMea;
    }

    if (dataMea != null && (this.type.toUpperCase().equals("FIVE") || this.type.toUpperCase().equals("PARM"))) {
      if (this.level1 == 0.0D && this.level2 == 0.0D && this.level3 == 0.0D) {
        this.curLevel = 0;
      } else {
        this.curLevel = this.getDataLevel(this.name, this.dataMea);
      }
    }

  }

  public Double getDataStd() {
    return this.dataStd;
  }

  public void setDataStd(Double dataStd) {
    this.dataStd = dataStd;
  }

  public Double getDataZero() {
    return this.dataZero;
  }

  public void setDataZero(Double dataZero) {
    this.dataZero = dataZero;
  }

  public Double getDataSpan() {
    return this.dataSpan;
  }

  public void setDataSpan(Double dataSpan) {
    this.dataSpan = dataSpan;
  }

  public Double getDataBlnk() {
    return this.dataBlnk;
  }

  public void setDataBlnk(Double dataBlnk) {
    this.dataBlnk = dataBlnk;
  }

  public Double getDataPar() {
    return this.dataPar;
  }

  public void setDataPar(Double dataPar) {
    this.dataPar = dataPar;
  }

  public Double getDataRcvr() {
    return this.dataRcvr;
  }

  public void setDataRcvr(Double dataRcvr) {
    this.dataRcvr = dataRcvr;
  }

  public String getFlagMea() {
    return this.flagMea;
  }

  public void setFlagMea(String flagMea) {
    this.flagMea = flagMea;
  }

  public String getFlagStd() {
    return this.flagStd;
  }

  public void setFlagStd(String flagStd) {
    this.flagStd = flagStd;
  }

  public String getFlagZero() {
    return this.flagZero;
  }

  public void setFlagZero(String flagZero) {
    this.flagZero = flagZero;
  }

  public String getFlagSpan() {
    return this.flagSpan;
  }

  public void setFlagSpan(String flagSpan) {
    this.flagSpan = flagSpan;
  }

  public String getFlagBlnk() {
    return this.flagBlnk;
  }

  public void setFlagBlnk(String flagBlnk) {
    this.flagBlnk = flagBlnk;
  }

  public String getFlagPar() {
    return this.flagPar;
  }

  public void setFlagPar(String flagPar) {
    this.flagPar = flagPar;
  }

  public String getFlagRcvr() {
    return this.flagRcvr;
  }

  public void setFlagRcvr(String flagRcvr) {
    this.flagRcvr = flagRcvr;
  }

  public Timestamp getTimeMea() {
    return this.timeMea;
  }

  public void setTimeMea(Timestamp timeMea) {
    this.timeMea = timeMea;
  }

  public Timestamp getTimeStd() {
    return this.timeStd;
  }

  public void setTimeStd(Timestamp timeStd) {
    this.timeStd = timeStd;
  }

  public Timestamp getTimeZero() {
    return this.timeZero;
  }

  public void setTimeZero(Timestamp timeZero) {
    this.timeZero = timeZero;
  }

  public Timestamp getTimeSpan() {
    return this.timeSpan;
  }

  public void setTimeSpan(Timestamp timeSpan) {
    this.timeSpan = timeSpan;
  }

  public Timestamp getTimeBlnk() {
    return this.timeBlnk;
  }

  public void setTimeBlnk(Timestamp timeBlnk) {
    this.timeBlnk = timeBlnk;
  }

  public Timestamp getTimePar() {
    return this.timePar;
  }

  public void setTimePar(Timestamp timePar) {
    this.timePar = timePar;
  }

  public Timestamp getTimeRcvr() {
    return this.timeRcvr;
  }

  public void setTimeRcvr(Timestamp timeRcvr) {
    this.timeRcvr = timeRcvr;
  }

  public double getLevel1() {
    return this.level1;
  }

  public void setLevel1(double level1) {
    this.level1 = level1;
  }

  public double getLevel2() {
    return this.level2;
  }

  public void setLevel2(double level2) {
    this.level2 = level2;
  }

  public double getLevel3() {
    return this.level3;
  }

  public void setLevel3(double level3) {
    this.level3 = level3;
  }

  public double getLevel4() {
    return this.level4;
  }

  public void setLevel4(double level4) {
    this.level4 = level4;
  }

  public double getLevel5() {
    return this.level5;
  }

  public void setLevel5(double level5) {
    this.level5 = level5;
  }

  public Double getDataAvg() {
    return this.dataAvg;
  }

  public void setDataAvg(Double dataAvg) {
    this.dataAvg = dataAvg;
    if (this.type.toUpperCase().equals("FIVE") || this.type.toUpperCase().equals("PARM")) {
      if (this.level1 == 0.0D && this.level2 == 0.0D && this.level3 == 0.0D) {
        this.avgLevel = 0;
      } else {
        this.avgLevel = this.getDataLevel(this.name, this.dataAvg);
      }
    }

  }

  public int getSizeAvg() {
    return this.sizeAvg;
  }

  public void setSizeAvg(int sizeAvg) {
    this.sizeAvg = sizeAvg;
  }

  public boolean isCheckAvg() {
    return this.checkAvg;
  }

  public void setCheckAvg(boolean checkAvg) {
    this.checkAvg = checkAvg;
  }

  public Double getDataTmp() {
    return this.dataTmp;
  }

  public void setDataTmp(Double dataTmp) {
    this.dataTmp = dataTmp;
    if (this.type.toUpperCase().equals("FIVE") || this.type.toUpperCase().equals("PARM")) {
      if (this.level1 == 0.0D && this.level2 == 0.0D && this.level3 == 0.0D) {
        this.tmpLevel = 0;
      } else {
        this.tmpLevel = this.getDataLevel(this.name, this.dataTmp);
      }
    }

  }

  public int getCurLevel() {
    return this.curLevel;
  }

  public void setCurLevel(int curLevel) {
    this.curLevel = curLevel;
  }

  public int getAvgLevel() {
    return this.avgLevel;
  }

  public void setAvgLevel(int avgLevel) {
    this.avgLevel = avgLevel;
  }

  public int getTmpLevel() {
    return this.tmpLevel;
  }

  public void setTmpLevel(int tmpLevel) {
    this.tmpLevel = tmpLevel;
  }

  public boolean isUpload() {
    return this.upload;
  }

  public void setUpload(boolean upload) {
    this.upload = upload;
  }

  public double getRangeMax() {
    return this.rangeMax;
  }

  public void setRangeMax(double rangeMax) {
    this.rangeMax = rangeMax;
  }

  public boolean isRangeChange() {
    return this.rangeChange;
  }

  public void setRangeChange(boolean rangeChange) {
    this.rangeChange = rangeChange;
  }

  public int getCheckType() {
    return this.checkType;
  }

  public void setCheckType(int checkType) {
    this.checkType = checkType;
  }

  public double getUnitParam() {
    return this.unitParam;
  }

  public void setUnitParam(double unitParam) {
    this.unitParam = unitParam;
  }

  public double getRcvrMultiple() {
    return this.rcvrMultiple;
  }

  public void setRcvrMultiple(double rcvrMultiple) {
    this.rcvrMultiple = rcvrMultiple;
  }

  public int getRcvrType() {
    return this.rcvrType;
  }

  public void setRcvrType(int rcvrType) {
    this.rcvrType = rcvrType;
  }

  public Map<String, String> getParamMap() {
    return this.paramMap;
  }

  public void setParamMap(Map<String, String> paramMap) {
    this.paramMap = paramMap;
  }

  public boolean isNonNegative() {
    return this.nonNegative;
  }

  public void setNonNegative(boolean nonNegative) {
    this.nonNegative = nonNegative;
  }
}
