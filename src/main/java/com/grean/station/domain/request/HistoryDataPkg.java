//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.util.ArrayList;
import java.util.List;

public class HistoryDataPkg {
  List<HistoryData> historyData = new ArrayList();
  boolean dataFlag = false;

  public HistoryDataPkg() {
  }

  public List<HistoryData> getHistoryData() {
    return this.historyData;
  }

  public void setHistoryData(List<HistoryData> historyData) {
    this.historyData = historyData;
  }

  public boolean isDataFlag() {
    return this.dataFlag;
  }

  public void setDataFlag(boolean dataFlag) {
    this.dataFlag = dataFlag;
  }
}
