//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.util.ArrayList;
import java.util.List;

public class DevInfo {
  List<String> devNameList = new ArrayList();
  List<String> devFactorList = new ArrayList();
  List<Double> devValueList = new ArrayList();
  List<String> devUnitList = new ArrayList();
  List<String> devParamNameList = new ArrayList();
  List<String> devParamValueList = new ArrayList();
  String devStatus = "未知";
  String devMode = "未知";
  String devWarn = "未知";
  String devFault = "未知";

  public DevInfo() {
  }

  public List<String> getDevNameList() {
    return this.devNameList;
  }

  public void setDevNameList(List<String> devNameList) {
    this.devNameList = devNameList;
  }

  public List<String> getDevFactorList() {
    return this.devFactorList;
  }

  public void setDevFactorList(List<String> devFactorList) {
    this.devFactorList = devFactorList;
  }

  public List<Double> getDevValueList() {
    return this.devValueList;
  }

  public void setDevValueList(List<Double> devValueList) {
    this.devValueList = devValueList;
  }

  public List<String> getDevUnitList() {
    return this.devUnitList;
  }

  public void setDevUnitList(List<String> devUnitList) {
    this.devUnitList = devUnitList;
  }

  public String getDevStatus() {
    return this.devStatus;
  }

  public void setDevStatus(String devStatus) {
    this.devStatus = devStatus;
  }

  public String getDevMode() {
    return this.devMode;
  }

  public void setDevMode(String devMode) {
    this.devMode = devMode;
  }

  public String getDevWarn() {
    return this.devWarn;
  }

  public void setDevWarn(String devWarn) {
    this.devWarn = devWarn;
  }

  public String getDevFault() {
    return this.devFault;
  }

  public void setDevFault(String devFault) {
    this.devFault = devFault;
  }

  public List<String> getDevParamNameList() {
    return this.devParamNameList;
  }

  public void setDevParamNameList(List<String> devParamNameList) {
    this.devParamNameList = devParamNameList;
  }

  public List<String> getDevParamValueList() {
    return this.devParamValueList;
  }

  public void setDevParamValueList(List<String> devParamValueList) {
    this.devParamValueList = devParamValueList;
  }
}
