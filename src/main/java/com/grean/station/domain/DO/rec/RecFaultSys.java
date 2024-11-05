//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import java.sql.Timestamp;

public class RecFaultSys {
  int id;
  Timestamp recTime;
  String faultType;
  String faultDesc;

  public RecFaultSys() {
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
