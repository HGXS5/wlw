//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.response;

import java.util.List;

public class EChartStackVO {
    private String name;
    private String type = "line";
    private boolean connectNulls = true;
    private List<Double> data;

    public EChartStackVO() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isConnectNulls() {
        return this.connectNulls;
    }

    public void setConnectNulls(boolean connectNulls) {
        this.connectNulls = connectNulls;
    }

    public List<Double> getData() {
        return this.data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }
}
