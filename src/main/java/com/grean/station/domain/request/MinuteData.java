//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.sql.Timestamp;

public class MinuteData {
  private Timestamp time;
  private Double dataCOD;
  private String flagCOD;
  private Double dataNH3N;
  private String flagNH3N;
  private Double dataPH;
  private String flagPH;
  private Double dataFLOW;
  private String flagFLOW;

  public MinuteData() {
  }

  public Timestamp getTime() {
    return this.time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public Double getDataCOD() {
    return this.dataCOD;
  }

  public void setDataCOD(Double dataCOD) {
    this.dataCOD = dataCOD;
  }

  public String getFlagCOD() {
    return this.flagCOD;
  }

  public void setFlagCOD(String flagCOD) {
    this.flagCOD = flagCOD;
  }

  public Double getDataNH3N() {
    return this.dataNH3N;
  }

  public void setDataNH3N(Double dataNH3N) {
    this.dataNH3N = dataNH3N;
  }

  public String getFlagNH3N() {
    return this.flagNH3N;
  }

  public void setFlagNH3N(String flagNH3N) {
    this.flagNH3N = flagNH3N;
  }

  public Double getDataPH() {
    return this.dataPH;
  }

  public void setDataPH(Double dataPH) {
    this.dataPH = dataPH;
  }

  public String getFlagPH() {
    return this.flagPH;
  }

  public void setFlagPH(String flagPH) {
    this.flagPH = flagPH;
  }

  public Double getDataFLOW() {
    return this.dataFLOW;
  }

  public void setDataFLOW(Double dataFLOW) {
    this.dataFLOW = dataFLOW;
  }

  public String getFlagFLOW() {
    return this.flagFLOW;
  }

  public void setFlagFLOW(String flagFLOW) {
    this.flagFLOW = flagFLOW;
  }
}
