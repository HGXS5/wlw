//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CheckRcvrData {
  int id;
  Timestamp time;
  String name;
  String unit;
  Double beforeValue;
  Double afterValue;
  Double motherValue;
  Double motherVolume;
  Double cupVolume;
  Double rcvrRate;
  String checkResult;
  String dataFlag;
  List<HistoryUpload> uploadList = new ArrayList();

  public CheckRcvrData() {
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

  public Double getBeforeValue() {
    return this.beforeValue;
  }

  public void setBeforeValue(Double beforeValue) {
    this.beforeValue = beforeValue;
  }

  public Double getAfterValue() {
    return this.afterValue;
  }

  public void setAfterValue(Double afterValue) {
    this.afterValue = afterValue;
  }

  public Double getMotherValue() {
    return this.motherValue;
  }

  public void setMotherValue(Double motherValue) {
    this.motherValue = motherValue;
  }

  public Double getMotherVolume() {
    return this.motherVolume;
  }

  public void setMotherVolume(Double motherVolume) {
    this.motherVolume = motherVolume;
  }

  public Double getCupVolume() {
    return this.cupVolume;
  }

  public void setCupVolume(Double cupVolume) {
    this.cupVolume = cupVolume;
  }

  public Double getRcvrRate() {
    return this.rcvrRate;
  }

  public void setRcvrRate(Double rcvrRate) {
    this.rcvrRate = rcvrRate;
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
