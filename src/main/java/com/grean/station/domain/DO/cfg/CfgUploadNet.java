//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.cfg;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class CfgUploadNet {
  int id;
  String name;
  String ip;
  int port;
  String protocol;
  String st;
  String mn;
  String password;
  boolean used;
  int period_heart;
  int period_real;
  int period_sys_state;
  int period_dev_state;
  int period_sys_log;
  int period_dev_log;
  int over_time;
  int re_count;
  Timestamp auto_upload;
  Map<Integer, String> protocolMap = new HashMap();

  public CfgUploadNet() {
    this.protocolMap.put(0, "HJ212");
    this.protocolMap.put(1, "SZY206");
    this.protocolMap.put(2, "SL651");
    this.protocolMap.put(3, "CQTEST");
    this.protocolMap.put(4, "ZJ212WATER");
    this.protocolMap.put(5, "ZJ212POLLUTION");
    this.protocolMap.put(6, "ZJ212GREAN");
    this.protocolMap.put(7, "GDYUJING");
    this.protocolMap.put(8, "SZY206SH");
    this.protocolMap.put(9, "ZJ212KH");
    this.protocolMap.put(10, "JSFTP");
    this.protocolMap.put(11, "JS212YC");
    this.protocolMap.put(12, "SD212BZ");
    this.protocolMap.put(13, "HEDA");
    this.protocolMap.put(14, "ZJ212WATERRESOURCE");
    this.protocolMap.put(15, "SZY206FJ");
    this.protocolMap.put(16, "SZY206HB");
    this.protocolMap.put(17, "HJ212TEST");
    this.protocolMap.put(18, "SX212SX");
    this.protocolMap.put(19, "HJ212HOUR");
    this.protocolMap.put(20, "JS212YCDF");
    this.protocolMap.put(21, "SZY206NSBD");
    this.protocolMap.put(22, "HJ212WH");
    this.protocolMap.put(23, "Modbus");
    this.protocolMap.put(24, "HJ212ZJ");
    this.protocolMap.put(25, "HJ212UDP");
    this.protocolMap.put(26, "HJ2122017");
    this.protocolMap.put(27, "QDFTP");
    this.protocolMap.put(28, "SD212QDSX");
    this.protocolMap.put(29, "HJ212ENCRYPTION");
    this.protocolMap.put(30, "NBUCMA");
    this.protocolMap.put(31, "HJ212HF");
    this.protocolMap.put(32, "DANJIANGKOU");
    this.protocolMap.put(33, "XAFTP");
    this.protocolMap.put(34, "HJ212JG");
    this.protocolMap.put(35, "ZJ212WATERRESOURCE2");
    this.protocolMap.put(36, "HJ212NSBD");
  }

  public Integer getMapKey(String mapValue) {
    Iterator var2 = this.protocolMap.entrySet().iterator();

    Entry entry;
    do {
      if (!var2.hasNext()) {
        return null;
      }

      entry = (Entry)var2.next();
    } while(!((String)entry.getValue()).equals(mapValue));

    return (Integer)entry.getKey();
  }

  public String getMapValue(int mapKey) {
    Iterator var2 = this.protocolMap.entrySet().iterator();

    Entry entry;
    do {
      if (!var2.hasNext()) {
        return null;
      }

      entry = (Entry)var2.next();
    } while((Integer)entry.getKey() != mapKey);

    return (String)entry.getValue();
  }

  public void reset() {
    this.protocol = "HJ212";
    this.st = "21";
    this.mn = "123456";
    this.password = "123456";
    this.used = true;
    this.period_heart = 60;
    this.period_real = 60;
    this.period_sys_state = 0;
    this.period_dev_state = 0;
    this.period_sys_log = 0;
    this.period_dev_log = 0;
    this.over_time = 5;
    this.re_count = 5;
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

  public String getIp() {
    return this.ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public int getPort() {
    return this.port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getProtocol() {
    return this.protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public String getSt() {
    return this.st;
  }

  public void setSt(String st) {
    this.st = st;
  }

  public String getMn() {
    return this.mn;
  }

  public void setMn(String mn) {
    this.mn = mn;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isUsed() {
    return this.used;
  }

  public void setUsed(boolean used) {
    this.used = used;
  }

  public int getPeriod_heart() {
    return this.period_heart;
  }

  public void setPeriod_heart(int period_heart) {
    this.period_heart = period_heart;
  }

  public int getPeriod_real() {
    return this.period_real;
  }

  public void setPeriod_real(int period_real) {
    this.period_real = period_real;
  }

  public int getOver_time() {
    return this.over_time;
  }

  public void setOver_time(int over_time) {
    this.over_time = over_time;
  }

  public int getRe_count() {
    return this.re_count;
  }

  public void setRe_count(int re_count) {
    this.re_count = re_count;
  }

  public int getPeriod_sys_state() {
    return this.period_sys_state;
  }

  public void setPeriod_sys_state(int period_sys_state) {
    this.period_sys_state = period_sys_state;
  }

  public int getPeriod_dev_state() {
    return this.period_dev_state;
  }

  public void setPeriod_dev_state(int period_dev_state) {
    this.period_dev_state = period_dev_state;
  }

  public int getPeriod_sys_log() {
    return this.period_sys_log;
  }

  public void setPeriod_sys_log(int period_sys_log) {
    this.period_sys_log = period_sys_log;
  }

  public int getPeriod_dev_log() {
    return this.period_dev_log;
  }

  public void setPeriod_dev_log(int period_dev_log) {
    this.period_dev_log = period_dev_log;
  }

  public Timestamp getAuto_upload() {
    return this.auto_upload;
  }

  public void setAuto_upload(Timestamp auto_upload) {
    this.auto_upload = auto_upload;
  }
}
