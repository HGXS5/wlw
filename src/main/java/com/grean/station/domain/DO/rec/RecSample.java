//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import java.sql.Timestamp;

public class RecSample {
  int id;
  Timestamp rec_time;
  Integer volume;
  String unit;

  public RecSample() {
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Timestamp getRec_time() {
    return this.rec_time;
  }

  public void setRec_time(Timestamp rec_time) {
    this.rec_time = rec_time;
  }

  public Integer getVolume() {
    return this.volume;
  }

  public void setVolume(Integer volume) {
    this.volume = volume;
  }

  public String getUnit() {
    return this.unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }
}
