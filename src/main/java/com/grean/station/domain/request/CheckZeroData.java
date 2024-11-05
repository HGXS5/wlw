//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.sql.Timestamp;

public class CheckZeroData {
  int id;
  Timestamp time;
  String name;
  String unit;
  Double preValue;
  Double curValue;
  Double stdValue;
  Double errorValue;
  String checkResult;

  public CheckZeroData() {
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Timestamp getTime() {
    return this.time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUnit() {
    return this.unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public Double getPreValue() {
    return this.preValue;
  }

  public void setPreValue(Double preValue) {
    this.preValue = preValue;
  }

  public Double getCurValue() {
    return this.curValue;
  }

  public void setCurValue(Double curValue) {
    this.curValue = curValue;
  }

  public Double getStdValue() {
    return this.stdValue;
  }

  public void setStdValue(Double stdValue) {
    this.stdValue = stdValue;
  }

  public Double getErrorValue() {
    return this.errorValue;
  }

  public void setErrorValue(Double errorValue) {
    this.errorValue = errorValue;
  }

  public String getCheckResult() {
    return this.checkResult;
  }

  public void setCheckResult(String checkResult) {
    this.checkResult = checkResult;
  }
}
