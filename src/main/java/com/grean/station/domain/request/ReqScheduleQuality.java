//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.sql.Timestamp;

public class ReqScheduleQuality {
    boolean zeroSwitch;
    boolean spanSwitch;
    Timestamp zeroTime;
    Timestamp spanTime;

    public ReqScheduleQuality() {
    }

    public boolean isZeroSwitch() {
        return this.zeroSwitch;
    }

    public void setZeroSwitch(boolean zeroSwitch) {
        this.zeroSwitch = zeroSwitch;
    }

    public boolean isSpanSwitch() {
        return this.spanSwitch;
    }

    public void setSpanSwitch(boolean spanSwitch) {
        this.spanSwitch = spanSwitch;
    }

    public Timestamp getZeroTime() {
        return this.zeroTime;
    }

    public void setZeroTime(Timestamp zeroTime) {
        this.zeroTime = zeroTime;
    }

    public Timestamp getSpanTime() {
        return this.spanTime;
    }

    public void setSpanTime(Timestamp spanTime) {
        this.spanTime = spanTime;
    }
}
