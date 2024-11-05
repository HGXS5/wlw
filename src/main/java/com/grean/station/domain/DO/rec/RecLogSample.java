//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import java.sql.Timestamp;

public class RecLogSample {
  int id;
  Timestamp recTime;
  String recBottle;
  String recVolumn;
  String recType;
  String recNote;

  public RecLogSample() {
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Timestamp getRecTime() {
    return this.recTime;
  }

  public void setRecTime(Timestamp recTime) {
    this.recTime = recTime;
  }

  public String getRecBottle() {
    return this.recBottle;
  }

  public void setRecBottle(String recBottle) {
    this.recBottle = recBottle;
  }

  public String getRecVolumn() {
    return this.recVolumn;
  }

  public void setRecVolumn(String recVolumn) {
    this.recVolumn = recVolumn;
  }

  public String getRecType() {
    return this.recType;
  }

  public void setRecType(String recType) {
    this.recType = recType;
  }

  public String getRecNote() {
    return this.recNote;
  }

  public void setRecNote(String recNote) {
    this.recNote = recNote;
  }
}
