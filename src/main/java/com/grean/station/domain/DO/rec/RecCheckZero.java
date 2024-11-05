//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import java.sql.Timestamp;

public class RecCheckZero {
  int id;
  int recID;
  int factorID;
  Timestamp recTime;
  Double curValue;
  Double preValue;
  Double stdValue;
  Double spanValue;
  Double aError;
  Double rError;
  String aResult;
  String rResult;
  String dataFlag;

  public RecCheckZero() {
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

  public Double getPreValue() {
    return this.preValue;
  }

  public void setPreValue(Double preValue) {
    this.preValue = preValue;
  }

  public Double getStdValue() {
    return this.stdValue;
  }

  public void setStdValue(Double stdValue) {
    this.stdValue = stdValue;
  }

  public Double getSpanValue() {
    return this.spanValue;
  }

  public void setSpanValue(Double spanValue) {
    this.spanValue = spanValue;
  }

  public Double getaError() {
    return this.aError;
  }

  public void setaError(Double aError) {
    this.aError = aError;
  }

  public Double getrError() {
    return this.rError;
  }

  public void setrError(Double rError) {
    this.rError = rError;
  }

  public String getaResult() {
    return this.aResult;
  }

  public void setaResult(String aResult) {
    this.aResult = aResult;
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
