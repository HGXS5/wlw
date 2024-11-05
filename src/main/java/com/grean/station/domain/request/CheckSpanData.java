//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CheckSpanData {
  int id;
  Timestamp time;
  String name;
  String unit;
  Double preValue;
  Double curValue;
  String dataFlag;
  Double stdValue;
  Double spanValue;
  Double aErrorValue;
  Double rErrorValue;
  String aResult;
  String rResult;
  List<HistoryUpload> uploadList = new ArrayList();

  public CheckSpanData() {
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

  public Double getSpanValue() {
    return this.spanValue;
  }

  public void setSpanValue(Double spanValue) {
    this.spanValue = spanValue;
  }

  public Double getaErrorValue() {
    return this.aErrorValue;
  }

  public void setaErrorValue(Double aErrorValue) {
    this.aErrorValue = aErrorValue;
  }

  public Double getrErrorValue() {
    return this.rErrorValue;
  }

  public void setrErrorValue(Double rErrorValue) {
    this.rErrorValue = rErrorValue;
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

  public List<HistoryUpload> getUploadList() {
    return this.uploadList;
  }

  public void setUploadList(List<HistoryUpload> uploadList) {
    this.uploadList = uploadList;
  }
}
