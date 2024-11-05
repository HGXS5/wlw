//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.util.ArrayList;
import java.util.List;

public class ReportData {
    int id;
    String title;
    List<Double> dataList = new ArrayList();

    public ReportData() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Double> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<Double> dataList) {
        this.dataList = dataList;
    }
}
