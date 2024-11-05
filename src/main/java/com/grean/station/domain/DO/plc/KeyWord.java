//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.plc;

public class KeyWord {
  private int id;
  private String cnName;
  private String enName;

  public String toString() {
    return "KeyWord{id=" + this.id + ", cnName='" + this.cnName + '\'' + ", enName='" + this.enName + '\'' + '}';
  }

  public KeyWord() {
  }

  public KeyWord(int keyID, String keyCnName, String keyEnName) {
    this.id = keyID;
    this.cnName = keyCnName;
    this.enName = keyEnName;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCnName() {
    return this.cnName;
  }

  public void setCnName(String cnName) {
    this.cnName = cnName;
  }

  public String getEnName() {
    return this.enName;
  }

  public void setEnName(String enName) {
    this.enName = enName;
  }
}
