//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import java.sql.Timestamp;

public class RecCheckRcvr {
  int id;
  int recID;
  int factorID;
  Timestamp recTime;
  double beforeValue;
  Timestamp beforeTime;
  double afterValue;
  Timestamp afterTime;
  double motherValue;
  double motherVolume;
  double cupVolume;
  double rcvrRate;
  boolean result;
  String dataFlag;

  public RecCheckRcvr() {
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

  public Timestamp getRecTime() {
    return this.recTime;
  }

  public void setRecTime(Timestamp recTime) {
    this.recTime = recTime;
  }

  public double getBeforeValue() {
    return this.beforeValue;
  }

  public void setBeforeValue(double beforeValue) {
    this.beforeValue = beforeValue;
  }

  public Timestamp getBeforeTime() {
    return this.beforeTime;
  }

  public void setBeforeTime(Timestamp beforeTime) {
    this.beforeTime = beforeTime;
  }

  public double getAfterValue() {
    return this.afterValue;
  }

  public void setAfterValue(double afterValue) {
    this.afterValue = afterValue;
  }

  public Timestamp getAfterTime() {
    return this.afterTime;
  }

  public void setAfterTime(Timestamp afterTime) {
    this.afterTime = afterTime;
  }

  public double getMotherValue() {
    return this.motherValue;
  }

  public void setMotherValue(double motherValue) {
    this.motherValue = motherValue;
  }

  public double getMotherVolume() {
    return this.motherVolume;
  }

  public void setMotherVolume(double motherVolume) {
    this.motherVolume = motherVolume;
  }

  public double getCupVolume() {
    return this.cupVolume;
  }

  public void setCupVolume(double cupVolume) {
    this.cupVolume = cupVolume;
  }

  public double getRcvrRate() {
    return this.rcvrRate;
  }

  public void setRcvrRate(double rcvrRate) {
    this.rcvrRate = rcvrRate;
  }

  public boolean isResult() {
    return this.result;
  }

  public void setResult(boolean result) {
    this.result = result;
  }

  public String getDataFlag() {
    return this.dataFlag;
  }

  public void setDataFlag(String dataFlag) {
    this.dataFlag = dataFlag;
  }
}
