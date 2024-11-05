//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.util.ArrayList;
import java.util.List;

public class RunningInfo {
    int current_station_mode;
    int current_pump_work_mode;
    int current_pump_type;
    int current_pump;
    boolean pump_water;
    double pump_alarm_time;
    double pump_sample_time;
    double presure_limit_high;
    double presure_limit_low;
    int rcvr_type;
    double turb_high;
    List<TitleValue> itemList = new ArrayList();

    public RunningInfo() {
    }

    public int getCurrent_station_mode() {
        return this.current_station_mode;
    }

    public void setCurrent_station_mode(int current_station_mode) {
        this.current_station_mode = current_station_mode;
    }

    public int getCurrent_pump_work_mode() {
        return this.current_pump_work_mode;
    }

    public void setCurrent_pump_work_mode(int current_pump_work_mode) {
        this.current_pump_work_mode = current_pump_work_mode;
    }

    public int getCurrent_pump_type() {
        return this.current_pump_type;
    }

    public void setCurrent_pump_type(int current_pump_type) {
        this.current_pump_type = current_pump_type;
    }

    public int getCurrent_pump() {
        return this.current_pump;
    }

    public void setCurrent_pump(int current_pump) {
        this.current_pump = current_pump;
    }

    public double getPump_alarm_time() {
        return this.pump_alarm_time;
    }

    public void setPump_alarm_time(double pump_alarm_time) {
        this.pump_alarm_time = pump_alarm_time;
    }

    public double getPump_sample_time() {
        return this.pump_sample_time;
    }

    public void setPump_sample_time(double pump_sample_time) {
        this.pump_sample_time = pump_sample_time;
    }

    public double getPresure_limit_high() {
        return this.presure_limit_high;
    }

    public void setPresure_limit_high(double presure_limit_high) {
        this.presure_limit_high = presure_limit_high;
    }

    public double getPresure_limit_low() {
        return this.presure_limit_low;
    }

    public void setPresure_limit_low(double presure_limit_low) {
        this.presure_limit_low = presure_limit_low;
    }

    public boolean isPump_water() {
        return this.pump_water;
    }

    public void setPump_water(boolean pump_water) {
        this.pump_water = pump_water;
    }

    public int getRcvr_type() {
        return this.rcvr_type;
    }

    public void setRcvr_type(int rcvr_type) {
        this.rcvr_type = rcvr_type;
    }

    public double getTurb_high() {
        return this.turb_high;
    }

    public void setTurb_high(double turb_high) {
        this.turb_high = turb_high;
    }

    public List<TitleValue> getItemList() {
        return this.itemList;
    }

    public void setItemList(List<TitleValue> itemList) {
        this.itemList = itemList;
    }
}
