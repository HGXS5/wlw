//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import java.sql.Timestamp;

public class RecInfo {
  int id;
  Timestamp rec_time;
  String rec_info;

  public RecInfo() {
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

  public String getRec_info() {
    return this.rec_info;
  }

  public void setRec_info(String rec_info) {
    this.rec_info = rec_info;
  }
}
