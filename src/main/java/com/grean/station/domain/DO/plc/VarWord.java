//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.plc;

public class VarWord {
  private int id;
  private String cnName;
  private String enName;
  private int type;
  private double defValue;
  private double curValue;

  public String toString() {
    return "VarWord{id=" + this.id + ", cnName='" + this.cnName + '\'' + ", enName='" + this.enName + '\'' + ", type=" + this.type + ", defValue=" + this.defValue + ", curValue=" + this.curValue + '}';
  }

  public VarWord() {
  }

  public VarWord(int varID, String varCnName, String varEnName, int varType, float varDefValue) {
    this.id = varID;
    this.cnName = varCnName;
    this.enName = varEnName;
    this.type = varType;
    this.defValue = (double)varDefValue;
    this.curValue = this.defValue;
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

  public int getType() {
    return this.type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public double getDefValue() {
    return this.defValue;
  }

  public void setDefValue(double defValue) {
    this.defValue = defValue;
  }

  public double getCurValue() {
    return this.curValue;
  }

  public void setCurValue(double curValue) {
    this.curValue = curValue;
  }
}
