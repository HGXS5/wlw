//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import java.sql.Timestamp;

public class RecCheckPar {
  int id;
  int recID;
  int factorID;
  Timestamp recTime;
  double value1st;
  double value2nd;
  double valueRange;
  double parRate;
  boolean result;
  String dataFlag;

  public RecCheckPar() {
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

  public double getValue1st() {
    return this.value1st;
  }

  public void setValue1st(double value1st) {
    this.value1st = value1st;
  }

  public double getValue2nd() {
    return this.value2nd;
  }

  public void setValue2nd(double value2nd) {
    this.value2nd = value2nd;
  }

  public double getValueRange() {
    return this.valueRange;
  }

  public void setValueRange(double valueRange) {
    this.valueRange = valueRange;
  }

  public double getParRate() {
    return this.parRate;
  }

  public void setParRate(double parRate) {
    this.parRate = parRate;
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
