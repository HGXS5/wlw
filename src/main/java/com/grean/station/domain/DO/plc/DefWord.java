//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.plc;

public class DefWord {
  private int id;
  private String name;
  private String pname;
  private int type;
  private int address;
  private float defvalue;
  private float curvalue;
  private boolean isvalve;

  public String toString() {
    return "DefWord{id=" + this.id + ", name='" + this.name + '\'' + ", pname='" + this.pname + '\'' + ", type=" + this.type + ", address=" + this.address + ", defvalue=" + this.defvalue + ", curvalue=" + this.curvalue + '}';
  }

  public DefWord() {
  }

  public DefWord(int defID, String defName, String defPName, int defType, int defAddress, float defVal) {
    this.id = defID;
    this.name = defName;
    this.pname = defPName;
    this.type = defType;
    this.address = defAddress;
    this.defvalue = defVal;
    this.curvalue = defVal;
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

  public String getPname() {
    return this.pname;
  }

  public void setPname(String pname) {
    this.pname = pname;
  }

  public int getType() {
    return this.type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getAddress() {
    return this.address;
  }

  public void setAddress(int address) {
    this.address = address;
  }

  public float getDefvalue() {
    return this.defvalue;
  }

  public void setDefvalue(float defvalue) {
    this.defvalue = defvalue;
  }

  public float getCurvalue() {
    return this.curvalue;
  }

  public void setCurvalue(float curvalue) {
    this.curvalue = curvalue;
  }

  public boolean isIsvalve() {
    return this.isvalve;
  }

  public void setIsvalve(boolean isvalve) {
    this.isvalve = isvalve;
  }
}
