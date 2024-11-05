//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.http.model;

import java.util.List;

public class BodyData {
    boolean success;
    List<WaterData> data;
    TotalData additional_data;
    String request_time;

    public BodyData() {
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<WaterData> getData() {
        return this.data;
    }

    public void setData(List<WaterData> data) {
        this.data = data;
    }

    public TotalData getAdditional_data() {
        return this.additional_data;
    }

    public void setAdditional_data(TotalData additional_data) {
        this.additional_data = additional_data;
    }

    public String getRequest_time() {
        return this.request_time;
    }

    public void setRequest_time(String request_time) {
        this.request_time = request_time;
    }
}
