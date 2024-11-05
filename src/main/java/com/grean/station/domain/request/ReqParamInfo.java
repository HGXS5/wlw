//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

public class ReqParamInfo {
    String paramName;
    String paramValue;
    String paramColor;

    public ReqParamInfo() {
    }

    public ReqParamInfo(String paramName, String paramValue, String paramColor) {
        this.paramName = paramName;
        this.paramValue = paramValue;
        this.paramColor = paramColor;
    }

    public String getParamName() {
        return this.paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return this.paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamColor() {
        return this.paramColor;
    }

    public void setParamColor(String paramColor) {
        this.paramColor = paramColor;
    }
}
