//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import java.sql.Timestamp;

public class RecDataAvg {
  int id;
  int recID;
  int factorID;
  Timestamp dataTime;
  Double dataValue;
  String dataFlag;
  boolean isAlarm;
  double alarmValue;
  int alarmType;
  Double dataMin;
  Double dataMax;
  Double dataCou;

  public RecDataAvg() {
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getRecID() {
    return this.recID;
  }

  public void setRecID(int recID) {
    this.recID = recID;
  }

  public int getFactorID() {
    return this.factorID;
  }

  public void setFactorID(int factorID) {
    this.factorID = factorID;
  }

  public Timestamp getDataTime() {
    return this.dataTime;
  }

  public void setDataTime(Timestamp dataTime) {
    this.dataTime = dataTime;
  }

  public Double getDataValue() {
    return this.dataValue;
  }

  public void setDataValue(Double dataValue) {
    this.dataValue = dataValue;
  }

  public String getDataFlag() {
    return this.dataFlag;
  }

  public void setDataFlag(String dataFlag) {
    this.dataFlag = dataFlag;
  }

  public boolean isAlarm() {
    return this.isAlarm;
  }

  public void setAlarm(boolean alarm) {
    this.isAlarm = alarm;
  }

  public double getAlarmValue() {
    return this.alarmValue;
  }

  public void setAlarmValue(double alarmValue) {
    this.alarmValue = alarmValue;
  }

  public int getAlarmType() {
    return this.alarmType;
  }

  public void setAlarmType(int alarmType) {
    this.alarmType = alarmType;
  }

  public Double getDataMin() {
    return this.dataMin;
  }

  public void setDataMin(Double dataMin) {
    this.dataMin = dataMin;
  }

  public Double getDataMax() {
    return this.dataMax;
  }

  public void setDataMax(Double dataMax) {
    this.dataMax = dataMax;
  }

  public Double getDataCou() {
    return this.dataCou;
  }

  public void setDataCou(Double dataCou) {
    this.dataCou = dataCou;
  }
}
