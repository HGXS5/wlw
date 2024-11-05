//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.sql.Timestamp;

public class AlarmDev {
  int id;
  Timestamp time;
  String devName;
  String factorName;
  int alarmCode;
  String alarmInfo;

  public AlarmDev() {
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

  public String getDevName() {
    return this.devName;
  }

  public void setDevName(String devName) {
    this.devName = devName;
  }

  public String getFactorName() {
    return this.factorName;
  }

  public void setFactorName(String factorName) {
    this.factorName = factorName;
  }

  public int getAlarmCode() {
    return this.alarmCode;
  }

  public void setAlarmCode(int alarmCode) {
    this.alarmCode = alarmCode;
  }

  public String getAlarmInfo() {
    return this.alarmInfo;
  }

  public void setAlarmInfo(String alarmInfo) {
    this.alarmInfo = alarmInfo;
  }
}
