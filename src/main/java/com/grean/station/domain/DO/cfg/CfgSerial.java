//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.cfg;

import java.util.ArrayList;
import java.util.List;

public class CfgSerial {
  int id;
  String name;
  int port;
  int baudRate;
  int dataBits;
  int stopBits;
  int parity;
  int flowControl;
  boolean used;
  boolean cmdType;
  int cmdDelay;
  public List<CfgDev> devList = new ArrayList();

  public CfgSerial() {
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPort() {
    return this.port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public int getBaudRate() {
    return this.baudRate;
  }

  public void setBaudRate(int baudRate) {
    this.baudRate = baudRate;
  }

  public int getDataBits() {
    return this.dataBits;
  }

  public void setDataBits(int dataBits) {
    this.dataBits = dataBits;
  }

  public int getStopBits() {
    return this.stopBits;
  }

  public void setStopBits(int stopBits) {
    this.stopBits = stopBits;
  }

  public int getParity() {
    return this.parity;
  }

  public void setParity(int parity) {
    this.parity = parity;
  }

  public boolean isUsed() {
    return this.used;
  }

  public void setUsed(boolean used) {
    this.used = used;
  }

  public int getFlowControl() {
    return this.flowControl;
  }

  public void setFlowControl(int flowControl) {
    this.flowControl = flowControl;
  }

  public boolean isCmdType() {
    return this.cmdType;
  }

  public void setCmdType(boolean cmdType) {
    this.cmdType = cmdType;
  }

  public int getCmdDelay() {
    return this.cmdDelay;
  }

  public void setCmdDelay(int cmdDelay) {
    this.cmdDelay = cmdDelay;
  }

  public List<CfgDev> getDevList() {
    return this.devList;
  }

  public void setDevList(List<CfgDev> devList) {
    this.devList = devList;
  }
}
