//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

public class DrawLegend {
  String legendName;
  Double legendValue;
  String legendColor;

  public DrawLegend(String legendName, Double legendValue, String legendColor) {
    this.legendName = legendName;
    this.legendValue = legendValue;
    this.legendColor = legendColor;
  }

  public String getLegendName() {
    return this.legendName;
  }

  public void setLegendName(String legendName) {
    this.legendName = legendName;
  }

  public Double getLegendValue() {
    return this.legendValue;
  }

  public void setLegendValue(Double legendValue) {
    this.legendValue = legendValue;
  }

  public String getLegendColor() {
    return this.legendColor;
  }

  public void setLegendColor(String legendColor) {
    this.legendColor = legendColor;
  }
}
