//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.util.ArrayList;
import java.util.List;

public class ProtocolInfo {
  List<ReqDevFactor> devFactorList = new ArrayList();
  List<ValueLabelFormat> typeList = new ArrayList();
  List<ValueLabelFormat> cmdList = new ArrayList();

  public ProtocolInfo() {
  }

  public List<ReqDevFactor> getDevFactorList() {
    return this.devFactorList;
  }

  public void setDevFactorList(List<ReqDevFactor> devFactorList) {
    this.devFactorList = devFactorList;
  }

  public List<ValueLabelFormat> getTypeList() {
    return this.typeList;
  }

  public void setTypeList(List<ValueLabelFormat> typeList) {
    this.typeList = typeList;
  }

  public List<ValueLabelFormat> getCmdList() {
    return this.cmdList;
  }

  public void setCmdList(List<ValueLabelFormat> cmdList) {
    this.cmdList = cmdList;
  }
}
