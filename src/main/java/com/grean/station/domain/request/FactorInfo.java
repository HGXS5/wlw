//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

public class FactorInfo {
  String factorName;
  String factorUnit;

  public FactorInfo(String strName, String strUnit) {
    this.factorName = strName;
    this.factorUnit = strUnit;
  }

  public String getFactorName() {
    return this.factorName;
  }

  public void setFactorName(String factorName) {
    this.factorName = factorName;
  }

  public String getFactorUnit() {
    return this.factorUnit;
  }

  public void setFactorUnit(String factorUnit) {
    this.factorUnit = factorUnit;
  }
}
