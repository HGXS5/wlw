//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import java.sql.Timestamp;

public class RecDevParam {
  int id;
  int rec_id;
  Timestamp rec_time;
  int factor_id;
  String param_name;
  String param_value;
  int param_type;

  public RecDevParam() {
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getRec_id() {
    return this.rec_id;
  }

  public void setRec_id(int rec_id) {
    this.rec_id = rec_id;
  }

  public Timestamp getRec_time() {
    return this.rec_time;
  }

  public void setRec_time(Timestamp rec_time) {
    this.rec_time = rec_time;
  }

  public int getFactor_id() {
    return this.factor_id;
  }

  public void setFactor_id(int factor_id) {
    this.factor_id = factor_id;
  }

  public String getParam_name() {
    return this.param_name;
  }

  public void setParam_name(String param_name) {
    this.param_name = param_name;
  }

  public String getParam_value() {
    return this.param_value;
  }

  public void setParam_value(String param_value) {
    this.param_value = param_value;
  }

  public int getParam_type() {
    return this.param_type;
  }

  public void setParam_type(int param_type) {
    this.param_type = param_type;
  }
}
