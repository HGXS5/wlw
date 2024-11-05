//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.util.Date;
import java.util.List;

public class ReqFiveInfo {
    boolean schedule;
    List<ScheduleFive> scheduleFiveList;
    boolean week;
    int weekDay;
    Date startDate;
    Date endDate;
    boolean urgent;
    int urgentCircle;

    public ReqFiveInfo() {
    }

    public List<ScheduleFive> getScheduleFiveList() {
        return this.scheduleFiveList;
    }

    public void setScheduleFiveList(List<ScheduleFive> scheduleFiveList) {
        this.scheduleFiveList = scheduleFiveList;
    }

    public int getWeekDay() {
        return this.weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isSchedule() {
        return this.schedule;
    }

    public void setSchedule(boolean schedule) {
        this.schedule = schedule;
    }

    public boolean isWeek() {
        return this.week;
    }

    public void setWeek(boolean week) {
        this.week = week;
    }

    public boolean isUrgent() {
        return this.urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public int getUrgentCircle() {
        return this.urgentCircle;
    }

    public void setUrgentCircle(int urgentCircle) {
        this.urgentCircle = urgentCircle;
    }
}
