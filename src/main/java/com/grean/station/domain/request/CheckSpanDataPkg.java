//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.util.ArrayList;
import java.util.List;

public class CheckSpanDataPkg {
  List<CheckSpanData> historyData = new ArrayList();
  boolean dataFlag = false;

  public CheckSpanDataPkg() {
  }

  public List<CheckSpanData> getHistoryData() {
    return this.historyData;
  }

  public void setHistoryData(List<CheckSpanData> historyData) {
    this.historyData = historyData;
  }

  public boolean isDataFlag() {
    return this.dataFlag;
  }

  public void setDataFlag(boolean dataFlag) {
    this.dataFlag = dataFlag;
  }
}
