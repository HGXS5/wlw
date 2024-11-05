//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.http.model;

import java.util.List;

public class LinghuBody {
    String code;
    String message;
    List<LinghuData> monitorings;

    public LinghuBody() {
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<LinghuData> getMonitorings() {
        return this.monitorings;
    }

    public void setMonitorings(List<LinghuData> monitorings) {
        this.monitorings = monitorings;
    }
}
