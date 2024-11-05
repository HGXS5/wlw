//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.DO.cfg;

import java.util.Date;

public class CfgFive {
  int id;
  boolean is_schedule;
  boolean is_week;
  int week_day;
  Date start_date;
  Date end_date;
  boolean is_urgent;
  int urgent_circle;

  public CfgFive() {
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getWeek_day() {
    return this.week_day;
  }

  public void setWeek_day(int week_day) {
    this.week_day = week_day;
  }

  public Date getStart_date() {
    return this.start_date;
  }

  public void setStart_date(Date start_date) {
    this.start_date = start_date;
  }

  public Date getEnd_date() {
    return this.end_date;
  }

  public void setEnd_date(Date end_date) {
    this.end_date = end_date;
  }

  public boolean isIs_schedule() {
    return this.is_schedule;
  }

  public void setIs_schedule(boolean is_schedule) {
    this.is_schedule = is_schedule;
  }

  public boolean isIs_week() {
    return this.is_week;
  }

  public void setIs_week(boolean is_week) {
    this.is_week = is_week;
  }

  public boolean isIs_urgent() {
    return this.is_urgent;
  }

  public void setIs_urgent(boolean is_urgent) {
    this.is_urgent = is_urgent;
  }

  public int getUrgent_circle() {
    return this.urgent_circle;
  }

  public void setUrgent_circle(int urgent_circle) {
    this.urgent_circle = urgent_circle;
  }
}
