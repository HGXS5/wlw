//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

public class ReqPointInfo {
    String pointName;
    int pointIndex;
    boolean pointState;

    public ReqPointInfo() {
    }

    public ReqPointInfo(String name, int index, boolean state) {
        this.pointName = name;
        this.pointIndex = index;
        this.pointState = state;
    }

    public String getPointName() {
        return this.pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public int getPointIndex() {
        return this.pointIndex;
    }

    public void setPointIndex(int pointIndex) {
        this.pointIndex = pointIndex;
    }

    public boolean isPointState() {
        return this.pointState;
    }

    public void setPointState(boolean pointState) {
        this.pointState = pointState;
    }
}
