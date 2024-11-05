//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CheckStdData {
  int id;
  Timestamp time;
  String name;
  String unit;
  Double testValue;
  String dataFlag;
  Double stdValue;
  Double errorValue;
  String checkResult;
  List<HistoryUpload> uploadList = new ArrayList();

  public CheckStdData() {
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

  public Double getTestValue() {
    return this.testValue;
  }

  public void setTestValue(Double testValue) {
    this.testValue = testValue;
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

  public String getDataFlag() {
    return this.dataFlag;
  }

  public void setDataFlag(String dataFlag) {
    this.dataFlag = dataFlag;
  }

  public List<HistoryUpload> getUploadList() {
    return this.uploadList;
  }

  public void setUploadList(List<HistoryUpload> uploadList) {
    this.uploadList = uploadList;
  }
}
