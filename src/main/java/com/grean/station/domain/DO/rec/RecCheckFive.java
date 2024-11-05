//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import java.sql.Timestamp;

public class RecCheckFive {
  int id;
  int recID;
  int factorID;
  Timestamp recTime;
  Double curValue;
  double stdValue;
  double stdLevel;
  double aError;
  String aResult;
  String dataFlag;

  public RecCheckFive() {
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

  public Double getCurValue() {
    return this.curValue;
  }

  public void setCurValue(Double curValue) {
    this.curValue = curValue;
  }

  public double getStdValue() {
    return this.stdValue;
  }

  public void setStdValue(double stdValue) {
    this.stdValue = stdValue;
  }

  public double getStdLevel() {
    return this.stdLevel;
  }

  public void setStdLevel(double stdLevel) {
    this.stdLevel = stdLevel;
  }

  public double getaError() {
    return this.aError;
  }

  public void setaError(double aError) {
    this.aError = aError;
  }

  public String getaResult() {
    return this.aResult;
  }

  public void setaResult(String aResult) {
    this.aResult = aResult;
  }

  public String getDataFlag() {
    return this.dataFlag;
  }

  public void setDataFlag(String dataFlag) {
    this.dataFlag = dataFlag;
  }
}
