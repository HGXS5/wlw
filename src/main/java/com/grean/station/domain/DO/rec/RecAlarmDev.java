//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import java.sql.Timestamp;

public class RecAlarmDev {
  int id;
  int factorID;
  Timestamp recTime;
  int alarmID;
  String alarmType;
  String alarmDesc;

  public RecAlarmDev() {
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getFactorID() {
    return this.factorID;
  }

  public void setFactorID(int factorID) {
    this.factorID = factorID;
  }

  public Timestamp getRecTime() {
    return this.recTime;
  }

  public void setRecTime(Timestamp recTime) {
    this.recTime = recTime;
  }

  public int getAlarmID() {
    return this.alarmID;
  }

  public void setAlarmID(int alarmID) {
    this.alarmID = alarmID;
  }

  public String getAlarmType() {
    return this.alarmType;
  }

  public void setAlarmType(String alarmType) {
    this.alarmType = alarmType;
  }

  public String getAlarmDesc() {
    return this.alarmDesc;
  }

  public void setAlarmDesc(String alarmDesc) {
    this.alarmDesc = alarmDesc;
  }
}
