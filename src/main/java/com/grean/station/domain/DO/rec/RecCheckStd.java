//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import java.sql.Timestamp;

public class RecCheckStd {
  int id;
  int recID;
  int factorID;
  Timestamp recTime;
  double testValue;
  double stdValue;
  double aError;
  double rError;
  boolean aResult;
  boolean rResult;
  String dataFlag;

  public RecCheckStd() {
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

  public double getTestValue() {
    return this.testValue;
  }

  public void setTestValue(double testValue) {
    this.testValue = testValue;
  }

  public double getStdValue() {
    return this.stdValue;
  }

  public void setStdValue(double stdValue) {
    this.stdValue = stdValue;
  }

  public double getaError() {
    return this.aError;
  }

  public void setaError(double aError) {
    this.aError = aError;
  }

  public double getrError() {
    return this.rError;
  }

  public void setrError(double rError) {
    this.rError = rError;
  }

  public boolean isaResult() {
    return this.aResult;
  }

  public void setaResult(boolean aResult) {
    this.aResult = aResult;
  }

  public boolean isrResult() {
    return this.rResult;
  }

  public void setrResult(boolean rResult) {
    this.rResult = rResult;
  }

  public String getDataFlag() {
    return this.dataFlag;
  }

  public void setDataFlag(String dataFlag) {
    this.dataFlag = dataFlag;
  }
}
