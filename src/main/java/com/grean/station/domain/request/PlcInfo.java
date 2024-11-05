//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.util.ArrayList;
import java.util.List;

public class PlcInfo {
  List<String> plc_interfacelist = new ArrayList();
  String plc_interface;
  int plc_address;
  List<String> plc_protocollist = new ArrayList();
  String plc_protocol;

  public PlcInfo() {
  }

  public String getPlc_interface() {
    return this.plc_interface;
  }

  public void setPlc_interface(String plc_interface) {
    this.plc_interface = plc_interface;
  }

  public int getPlc_address() {
    return this.plc_address;
  }

  public void setPlc_address(int plc_address) {
    this.plc_address = plc_address;
  }

  public List<String> getPlc_protocollist() {
    return this.plc_protocollist;
  }

  public void setPlc_protocollist(List<String> plc_protocollist) {
    this.plc_protocollist = plc_protocollist;
  }

  public String getPlc_protocol() {
    return this.plc_protocol;
  }

  public void setPlc_protocol(String plc_protocol) {
    this.plc_protocol = plc_protocol;
  }

  public List<String> getPlc_interfacelist() {
    return this.plc_interfacelist;
  }

  public void setPlc_interfacelist(List<String> plc_interfacelist) {
    this.plc_interfacelist = plc_interfacelist;
  }
}
