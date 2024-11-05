//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CheckParData {
  int id;
  Timestamp time;
  String name;
  String unit;
  Double preValue;
  Double curValue;
  Double range;
  Double parRate;
  String checkResult;
  String dataFlag;
  List<HistoryUpload> uploadList = new ArrayList();

  public CheckParData() {
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

  public Double getRange() {
    return this.range;
  }

  public void setRange(Double range) {
    this.range = range;
  }

  public Double getParRate() {
    return this.parRate;
  }

  public void setParRate(Double parRate) {
    this.parRate = parRate;
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
