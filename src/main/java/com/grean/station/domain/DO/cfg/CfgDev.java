//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.cfg;

import java.util.ArrayList;
import java.util.List;

public class CfgDev {
  int id;
  String name;
  String nick;
  String type;
  String protocol;
  Long address;
  int serial_id;
  int net_id;
  int related_id;
  int related_id2;
  int log_id;
  String reg_name;
  int serial_channel;
  String save_mea_tag;
  String save_zero_tag;
  String save_span_tag;
  String save_std_tag;
  String save_blnk_tag;
  String save_rcvr_tag;
  String save_parl_tag;
  String start_mea_tag;
  String start_zero_tag;
  String start_span_tag;
  String start_std_tag;
  String start_blnk_tag;
  String start_rcvr_tag;
  String start_parl_tag;
  boolean status;
  public List<CfgFactor> factorList = new ArrayList();
  public List<CfgDevQuery> queryList = new ArrayList();
  public List<CfgDevCmd> meaCmdList = new ArrayList();
  public List<CfgDevCmd> stdCmdList = new ArrayList();
  public List<CfgDevCmd> zeroCmdList = new ArrayList();
  public List<CfgDevCmd> spanCmdList = new ArrayList();
  public List<CfgDevCmd> blnkCmdList = new ArrayList();
  public List<CfgDevCmd> parCmdList = new ArrayList();
  public List<CfgDevCmd> rcvrCmdList = new ArrayList();
  public List<CfgDevCmd> blnkCalList = new ArrayList();
  public List<CfgDevCmd> stdCalList = new ArrayList();
  public List<CfgDevCmd> initCmdList = new ArrayList();
  public List<CfgDevCmd> stopCmdList = new ArrayList();
  public List<CfgDevCmd> rsetCmdList = new ArrayList();
  public List<CfgDevReg> devRegList;

  public CfgDev() {
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNick() {
    return this.nick;
  }

  public void setNick(String nick) {
    this.nick = nick;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getProtocol() {
    return this.protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public String getReg_name() {
    return this.reg_name;
  }

  public void setReg_name(String reg_name) {
    this.reg_name = reg_name;
  }

  public Long getAddress() {
    return this.address;
  }

  public void setAddress(Long address) {
    this.address = address;
  }

  public int getSerial_id() {
    return this.serial_id;
  }

  public void setSerial_id(int serial_id) {
    this.serial_id = serial_id;
  }

  public int getSerial_channel() {
    return this.serial_channel;
  }

  public void setSerial_channel(int serial_channel) {
    if (serial_channel < 8) {
      this.serial_channel = serial_channel;
    }

  }

  public int getNet_id() {
    return this.net_id;
  }

  public void setNet_id(int net_id) {
    this.net_id = net_id;
  }

  public int getRelated_id() {
    return this.related_id;
  }

  public void setRelated_id(int related_id) {
    this.related_id = related_id;
  }

  public int getRelated_id2() {
    return this.related_id2;
  }

  public void setRelated_id2(int related_id2) {
    this.related_id2 = related_id2;
  }

  public int getLog_id() {
    return this.log_id;
  }

  public void setLog_id(int log_id) {
    this.log_id = log_id;
  }

  public String getSave_mea_tag() {
    return this.save_mea_tag;
  }

  public void setSave_mea_tag(String save_mea_tag) {
    this.save_mea_tag = save_mea_tag;
  }

  public String getSave_zero_tag() {
    return this.save_zero_tag;
  }

  public void setSave_zero_tag(String save_zero_tag) {
    this.save_zero_tag = save_zero_tag;
  }

  public String getSave_span_tag() {
    return this.save_span_tag;
  }

  public void setSave_span_tag(String save_span_tag) {
    this.save_span_tag = save_span_tag;
  }

  public String getSave_std_tag() {
    return this.save_std_tag;
  }

  public void setSave_std_tag(String save_std_tag) {
    this.save_std_tag = save_std_tag;
  }

  public String getSave_blnk_tag() {
    return this.save_blnk_tag;
  }

  public void setSave_blnk_tag(String save_blnk_tag) {
    this.save_blnk_tag = save_blnk_tag;
  }

  public String getSave_rcvr_tag() {
    return this.save_rcvr_tag;
  }

  public void setSave_rcvr_tag(String save_rcvr_tag) {
    this.save_rcvr_tag = save_rcvr_tag;
  }

  public String getSave_parl_tag() {
    return this.save_parl_tag;
  }

  public void setSave_parl_tag(String save_parl_tag) {
    this.save_parl_tag = save_parl_tag;
  }

  public String getStart_mea_tag() {
    return this.start_mea_tag;
  }

  public void setStart_mea_tag(String start_mea_tag) {
    this.start_mea_tag = start_mea_tag;
  }

  public String getStart_zero_tag() {
    return this.start_zero_tag;
  }

  public void setStart_zero_tag(String start_zero_tag) {
    this.start_zero_tag = start_zero_tag;
  }

  public String getStart_span_tag() {
    return this.start_span_tag;
  }

  public void setStart_span_tag(String start_span_tag) {
    this.start_span_tag = start_span_tag;
  }

  public String getStart_std_tag() {
    return this.start_std_tag;
  }

  public void setStart_std_tag(String start_std_tag) {
    this.start_std_tag = start_std_tag;
  }

  public String getStart_blnk_tag() {
    return this.start_blnk_tag;
  }

  public void setStart_blnk_tag(String start_blnk_tag) {
    this.start_blnk_tag = start_blnk_tag;
  }

  public String getStart_rcvr_tag() {
    return this.start_rcvr_tag;
  }

  public void setStart_rcvr_tag(String start_rcvr_tag) {
    this.start_rcvr_tag = start_rcvr_tag;
  }

  public String getStart_parl_tag() {
    return this.start_parl_tag;
  }

  public void setStart_parl_tag(String start_parl_tag) {
    this.start_parl_tag = start_parl_tag;
  }

  public List<CfgFactor> getFactorList() {
    return this.factorList;
  }

  public void setFactorList(List<CfgFactor> factorList) {
    this.factorList = factorList;
  }

  public List<CfgDevQuery> getQueryList() {
    return this.queryList;
  }

  public void setQueryList(List<CfgDevQuery> queryList) {
    this.queryList = queryList;
  }

  public List<CfgDevCmd> getMeaCmdList() {
    return this.meaCmdList;
  }

  public void setMeaCmdList(List<CfgDevCmd> meaCmdList) {
    this.meaCmdList = meaCmdList;
  }

  public List<CfgDevCmd> getStdCmdList() {
    return this.stdCmdList;
  }

  public void setStdCmdList(List<CfgDevCmd> stdCmdList) {
    this.stdCmdList = stdCmdList;
  }

  public List<CfgDevCmd> getZeroCmdList() {
    return this.zeroCmdList;
  }

  public void setZeroCmdList(List<CfgDevCmd> zeroCmdList) {
    this.zeroCmdList = zeroCmdList;
  }

  public List<CfgDevCmd> getSpanCmdList() {
    return this.spanCmdList;
  }

  public void setSpanCmdList(List<CfgDevCmd> spanCmdList) {
    this.spanCmdList = spanCmdList;
  }

  public List<CfgDevCmd> getBlnkCmdList() {
    return this.blnkCmdList;
  }

  public void setBlnkCmdList(List<CfgDevCmd> blnkCmdList) {
    this.blnkCmdList = blnkCmdList;
  }

  public List<CfgDevCmd> getParCmdList() {
    return this.parCmdList;
  }

  public void setParCmdList(List<CfgDevCmd> parCmdList) {
    this.parCmdList = parCmdList;
  }

  public List<CfgDevCmd> getRcvrCmdList() {
    return this.rcvrCmdList;
  }

  public void setRcvrCmdList(List<CfgDevCmd> rcvrCmdList) {
    this.rcvrCmdList = rcvrCmdList;
  }

  public List<CfgDevCmd> getBlnkCalList() {
    return this.blnkCalList;
  }

  public void setBlnkCalList(List<CfgDevCmd> blnkCalList) {
    this.blnkCalList = blnkCalList;
  }

  public List<CfgDevCmd> getStdCalList() {
    return this.stdCalList;
  }

  public void setStdCalList(List<CfgDevCmd> stdCalList) {
    this.stdCalList = stdCalList;
  }

  public List<CfgDevCmd> getInitCmdList() {
    return this.initCmdList;
  }

  public void setInitCmdList(List<CfgDevCmd> initCmdList) {
    this.initCmdList = initCmdList;
  }

  public List<CfgDevCmd> getStopCmdList() {
    return this.stopCmdList;
  }

  public void setStopCmdList(List<CfgDevCmd> stopCmdList) {
    this.stopCmdList = stopCmdList;
  }

  public List<CfgDevCmd> getRsetCmdList() {
    return this.rsetCmdList;
  }

  public void setRsetCmdList(List<CfgDevCmd> rsetCmdList) {
    this.rsetCmdList = rsetCmdList;
  }

  public boolean isStatus() {
    return this.status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public List<CfgDevReg> getDevRegList() {
    return this.devRegList;
  }

  public void setDevRegList(List<CfgDevReg> devRegList) {
    this.devRegList = devRegList;
  }
}
