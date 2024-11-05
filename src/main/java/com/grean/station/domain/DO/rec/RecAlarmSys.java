//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import java.sql.Timestamp;

public class RecAlarmSys {
  int id;
  Timestamp recTime;
  String alarmType;
  String alarmDesc;

  public RecAlarmSys() {
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Timestamp getRecTime() {
    return this.recTime;
  }

  public void setRecTime(Timestamp recTime) {
    this.recTime = recTime;
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
