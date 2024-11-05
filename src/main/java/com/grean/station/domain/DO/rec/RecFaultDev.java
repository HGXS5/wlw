//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import java.sql.Timestamp;

public class RecFaultDev {
  int id;
  int factorID;
  Timestamp recTime;
  int faultID;
  String faultType;
  String faultDesc;

  public RecFaultDev() {
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

  public int getFaultID() {
    return this.faultID;
  }

  public void setFaultID(int faultID) {
    this.faultID = faultID;
  }

  public String getFaultType() {
    return this.faultType;
  }

  public void setFaultType(String faultType) {
    this.faultType = faultType;
  }

  public String getFaultDesc() {
    return this.faultDesc;
  }

  public void setFaultDesc(String faultDesc) {
    this.faultDesc = faultDesc;
  }
}
