//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.sql.Timestamp;

public class ReqStdCheck {
    Timestamp runTime;
    String factorName;
    Double stdValue;
    Double testValue;
    String allowError;
    String curError;
    boolean runState;

    public ReqStdCheck() {
    }

    public Timestamp getRunTime() {
        return this.runTime;
    }

    public void setRunTime(Timestamp runTime) {
        this.runTime = runTime;
    }

    public String getFactorName() {
        return this.factorName;
    }

    public void setFactorName(String factorName) {
        this.factorName = factorName;
    }

    public Double getStdValue() {
        return this.stdValue;
    }

    public void setStdValue(Double stdValue) {
        this.stdValue = stdValue;
    }

    public Double getTestValue() {
        return this.testValue;
    }

    public void setTestValue(Double testValue) {
        this.testValue = testValue;
    }

    public String getAllowError() {
        return this.allowError;
    }

    public void setAllowError(String allowError) {
        this.allowError = allowError;
    }

    public String getCurError() {
        return this.curError;
    }

    public void setCurError(String curError) {
        this.curError = curError;
    }

    public boolean isRunState() {
        return this.runState;
    }

    public void setRunState(boolean runState) {
        this.runState = runState;
    }
}
