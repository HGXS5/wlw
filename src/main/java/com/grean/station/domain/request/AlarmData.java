//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.sql.Timestamp;

public class AlarmData {
  int id;
  Timestamp time;
  String factorName;
  Double testValue;
  Double alarmValue;
  String alarmType;

  public AlarmData() {
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Timestamp getTime() {
    return this.time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public String getFactorName() {
    return this.factorName;
  }

  public void setFactorName(String factorName) {
    this.factorName = factorName;
  }

  public Double getTestValue() {
    return this.testValue;
  }

  public void setTestValue(Double testValue) {
    this.testValue = testValue;
  }

  public Double getAlarmValue() {
    return this.alarmValue;
  }

  public void setAlarmValue(Double alarmValue) {
    this.alarmValue = alarmValue;
  }

  public String getAlarmType() {
    return this.alarmType;
  }

  public void setAlarmType(String alarmType) {
    this.alarmType = alarmType;
  }
}
