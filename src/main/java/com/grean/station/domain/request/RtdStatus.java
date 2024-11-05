//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.sql.Timestamp;

public class RtdStatus {
    String station_mode;
    String pump_mode;
    String current_pump;
    String current_status;
    Timestamp start_time;
    String pic_name1;
    String pic_name2;
    String warn_name;
    boolean warn_show;
    boolean warn_status;

    public RtdStatus() {
    }

    public String getStation_mode() {
        return this.station_mode;
    }

    public void setStation_mode(String station_mode) {
        this.station_mode = station_mode;
    }

    public String getPump_mode() {
        return this.pump_mode;
    }

    public void setPump_mode(String pump_mode) {
        this.pump_mode = pump_mode;
    }

    public String getCurrent_pump() {
        return this.current_pump;
    }

    public void setCurrent_pump(String current_pump) {
        this.current_pump = current_pump;
    }

    public String getCurrent_status() {
        return this.current_status;
    }

    public void setCurrent_status(String current_status) {
        this.current_status = current_status;
    }

    public Timestamp getStart_time() {
        return this.start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public String getPic_name1() {
        return this.pic_name1;
    }

    public void setPic_name1(String pic_name1) {
        this.pic_name1 = pic_name1;
    }

    public String getPic_name2() {
        return this.pic_name2;
    }

    public void setPic_name2(String pic_name2) {
        this.pic_name2 = pic_name2;
    }

    public String getWarn_name() {
        return this.warn_name;
    }

    public void setWarn_name(String warn_name) {
        this.warn_name = warn_name;
    }

    public boolean isWarn_show() {
        return this.warn_show;
    }

    public void setWarn_show(boolean warn_show) {
        this.warn_show = warn_show;
    }

    public boolean isWarn_status() {
        return this.warn_status;
    }

    public void setWarn_status(boolean warn_status) {
        this.warn_status = warn_status;
    }
}
