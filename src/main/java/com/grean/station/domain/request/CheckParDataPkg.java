//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.util.ArrayList;
import java.util.List;

public class CheckParDataPkg {
  List<CheckParData> historyData = new ArrayList();
  boolean dataFlag = false;

  public CheckParDataPkg() {
  }

  public List<CheckParData> getHistoryData() {
    return this.historyData;
  }

  public void setHistoryData(List<CheckParData> historyData) {
    this.historyData = historyData;
  }

  public boolean isDataFlag() {
    return this.dataFlag;
  }

  public void setDataFlag(boolean dataFlag) {
    this.dataFlag = dataFlag;
  }
}
