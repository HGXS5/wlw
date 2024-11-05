//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

public class ProtocolCmd {
  int key;
  String devName;
  int cmdType;
  int cmdIndex;
  String cmdString;
  String protoType;
  int cmdDelay;

  public ProtocolCmd() {
  }

  public int getKey() {
    return this.key;
  }

  public void setKey(int key) {
    this.key = key;
  }

  public String getDevName() {
    return this.devName;
  }

  public void setDevName(String devName) {
    this.devName = devName;
  }

  public int getCmdType() {
    return this.cmdType;
  }

  public void setCmdType(int cmdType) {
    this.cmdType = cmdType;
  }

  public int getCmdIndex() {
    return this.cmdIndex;
  }

  public void setCmdIndex(int cmdIndex) {
    this.cmdIndex = cmdIndex;
  }

  public String getCmdString() {
    return this.cmdString;
  }

  public void setCmdString(String cmdString) {
    this.cmdString = cmdString;
  }

  public String getProtoType() {
    return this.protoType;
  }

  public void setProtoType(String protoType) {
    this.protoType = protoType;
  }

  public int getCmdDelay() {
    return this.cmdDelay;
  }

  public void setCmdDelay(int cmdDelay) {
    this.cmdDelay = cmdDelay;
  }
}
