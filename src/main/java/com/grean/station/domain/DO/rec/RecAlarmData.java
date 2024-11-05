//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import java.sql.Timestamp;

public class RecAlarmData {
  int id;
  int factorID;
  Timestamp recTime;
  double alarmValue;
  double alarmLimit;
  String alarmType;

  public RecAlarmData() {
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

  public double getAlarmValue() {
    return this.alarmValue;
  }

  public void setAlarmValue(double alarmValue) {
    this.alarmValue = alarmValue;
  }

  public double getAlarmLimit() {
    return this.alarmLimit;
  }

  public void setAlarmLimit(double alarmLimit) {
    this.alarmLimit = alarmLimit;
  }

  public String getAlarmType() {
    return this.alarmType;
  }

  public void setAlarmType(String alarmType) {
    this.alarmType = alarmType;
  }
}
