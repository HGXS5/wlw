//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import java.sql.Timestamp;

public class RecLogSys {
  int id;
  int recID;
  Timestamp recTime;
  String logType;
  String logDesc;

  public RecLogSys() {
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

  public Timestamp getRecTime() {
    return this.recTime;
  }

  public void setRecTime(Timestamp recTime) {
    this.recTime = recTime;
  }

  public String getLogType() {
    return this.logType;
  }

  public void setLogType(String logType) {
    this.logType = logType;
  }

  public String getLogDesc() {
    return this.logDesc;
  }

  public void setLogDesc(String logDesc) {
    this.logDesc = logDesc;
  }
}
