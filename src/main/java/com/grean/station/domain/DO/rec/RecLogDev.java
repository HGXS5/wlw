//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import java.sql.Timestamp;

public class RecLogDev {
  int id;
  int factorID;
  int recID;
  Timestamp recTime;
  int logID;
  String logType;
  String logDesc;

  public RecLogDev() {
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

  public int getLogID() {
    return this.logID;
  }

  public void setLogID(int logID) {
    this.logID = logID;
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
