//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.cfg;

import java.util.ArrayList;
import java.util.List;

public class CfgNetClient {
  int id;
  String name;
  String ip;
  int port;
  boolean used;
  boolean cmdType;
  int cmdDelay;
  public List<CfgDev> devList = new ArrayList();

  public CfgNetClient() {
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

  public String getIp() {
    return this.ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public int getPort() {
    return this.port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public boolean isUsed() {
    return this.used;
  }

  public void setUsed(boolean used) {
    this.used = used;
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
