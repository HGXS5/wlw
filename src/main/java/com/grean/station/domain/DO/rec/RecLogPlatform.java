//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.rec;

import com.grean.station.utils.TimeHelper;
import java.sql.Timestamp;

public class RecLogPlatform {
  int id;
  Timestamp recTime = TimeHelper.getCurrentTimestamp();
  int recPlatform;
  String recType = "平台反控";
  String recDesc;
  boolean recResult;

  public RecLogPlatform() {
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

  public int getRecPlatform() {
    return this.recPlatform;
  }

  public void setRecPlatform(int recPlatform) {
    this.recPlatform = recPlatform;
  }

  public String getRecType() {
    return this.recType;
  }

  public void setRecType(String recType) {
    this.recType = recType;
  }

  public String getRecDesc() {
    return this.recDesc;
  }

  public void setRecDesc(String recDesc) {
    this.recDesc = recDesc;
  }

  public boolean isRecResult() {
    return this.recResult;
  }

  public void setRecResult(boolean recResult) {
    this.recResult = recResult;
  }
}
