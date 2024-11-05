//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.http.model;

public class PlatformData {
    String begin;
    String end;
    String stcd;
    String[] showFactor = new String[]{"fe", "mn"};
    int start_page = 1;
    int limit = 100;

    public PlatformData() {
    }

    public String getBegin() {
        return this.begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStcd() {
        return this.stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public String[] getShowFactor() {
        return this.showFactor;
    }

    public void setShowFactor(String[] showFactor) {
        this.showFactor = showFactor;
    }

    public int getStart_page() {
        return this.start_page;
    }

    public void setStart_page(int start_page) {
        this.start_page = start_page;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
