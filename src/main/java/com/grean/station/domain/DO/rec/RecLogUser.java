//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import java.sql.Timestamp;

public class RecLogUser {
  int id;
  Timestamp recTime;
  String recUser;
  String recOperation;

  public RecLogUser() {
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

  public String getRecUser() {
    return this.recUser;
  }

  public void setRecUser(String recUser) {
    this.recUser = recUser;
  }

  public String getRecOperation() {
    return this.recOperation;
  }

  public void setRecOperation(String recOperation) {
    this.recOperation = recOperation;
  }
}
