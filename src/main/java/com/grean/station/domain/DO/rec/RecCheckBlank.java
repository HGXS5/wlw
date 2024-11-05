//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import java.sql.Timestamp;

public class RecCheckBlank {
  int id;
  int recID;
  int factorID;
  Timestamp recTime;
  double curValue;
  Double preValue;
  double rangeValue;
  double rError;
  String rResult;
  String dataFlag;

  public RecCheckBlank() {
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

  public double getCurValue() {
    return this.curValue;
  }

  public void setCurValue(double curValue) {
    this.curValue = curValue;
  }

  public Double getPreValue() {
    return this.preValue;
  }

  public void setPreValue(Double preValue) {
    this.preValue = preValue;
  }

  public double getRangeValue() {
    return this.rangeValue;
  }

  public void setRangeValue(double rangeValue) {
    this.rangeValue = rangeValue;
  }

  public double getrError() {
    return this.rError;
  }

  public void setrError(double rError) {
    this.rError = rError;
  }

  public String getrResult() {
    return this.rResult;
  }

  public void setrResult(String rResult) {
    this.rResult = rResult;
  }

  public String getDataFlag() {
    return this.dataFlag;
  }

  public void setDataFlag(String dataFlag) {
    this.dataFlag = dataFlag;
  }
}
