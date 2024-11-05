//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

public class QualityParamInfo {
    String paramName;
    String paramValue;
    String paramUnit;

    public QualityParamInfo() {
    }

    public QualityParamInfo(String paramName, String paramValue, String paramUnit) {
        this.paramName = paramName;
        this.paramValue = paramValue;
        this.paramUnit = paramUnit;
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

    public String getParamUnit() {
        return this.paramUnit;
    }

    public void setParamUnit(String paramUnit) {
        this.paramUnit = paramUnit;
    }
}
