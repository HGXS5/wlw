//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.util.List;

public class ScheduleMonth {
    int id;
    boolean tag;
    List<ScheduleMonthData> data;

    public ScheduleMonth() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTag() {
        return this.tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    public List<ScheduleMonthData> getData() {
        return this.data;
    }

    public void setData(List<ScheduleMonthData> data) {
        this.data = data;
    }
}
