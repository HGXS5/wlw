//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.cfg;

import java.sql.Timestamp;

public class CfgStation {
  int id;
  String station_name;
  String station_code;
  String station_address;
  Timestamp build_date;
  Double longitude;
  Double latitude;
  String station_owner;
  String station_builder;
  String station_operator;
  String teamviewer_id;
  String emergency_contact;
  String emergency_phone;
  Timestamp run_date;

  public CfgStation() {
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getStation_name() {
    return this.station_name;
  }

  public void setStation_name(String station_name) {
    this.station_name = station_name;
  }

  public String getStation_code() {
    return this.station_code;
  }

  public void setStation_code(String station_code) {
    this.station_code = station_code;
  }

  public String getStation_address() {
    return this.station_address;
  }

  public void setStation_address(String station_address) {
    this.station_address = station_address;
  }

  public Timestamp getBuild_date() {
    return this.build_date;
  }

  public void setBuild_date(Timestamp build_date) {
    this.build_date = build_date;
  }

  public Double getLongitude() {
    return this.longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getLatitude() {
    return this.latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public String getStation_owner() {
    return this.station_owner;
  }

  public void setStation_owner(String station_owner) {
    this.station_owner = station_owner;
  }

  public String getStation_builder() {
    return this.station_builder;
  }

  public void setStation_builder(String station_builder) {
    this.station_builder = station_builder;
  }

  public String getStation_operator() {
    return this.station_operator;
  }

  public void setStation_operator(String station_operator) {
    this.station_operator = station_operator;
  }

  public String getTeamviewer_id() {
    return this.teamviewer_id;
  }

  public void setTeamviewer_id(String teamviewer_id) {
    this.teamviewer_id = teamviewer_id;
  }

  public String getEmergency_contact() {
    return this.emergency_contact;
  }

  public void setEmergency_contact(String emergency_contact) {
    this.emergency_contact = emergency_contact;
  }

  public String getEmergency_phone() {
    return this.emergency_phone;
  }

  public void setEmergency_phone(String emergency_phone) {
    this.emergency_phone = emergency_phone;
  }

  public Timestamp getRun_date() {
    return this.run_date;
  }

  public void setRun_date(Timestamp run_date) {
    this.run_date = run_date;
  }
}
