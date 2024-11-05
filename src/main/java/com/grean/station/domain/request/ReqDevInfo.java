//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.util.ArrayList;
import java.util.List;

public class ReqDevInfo {
    String devName;
    String devMode;
    String devStatus;
    String devWarn;
    boolean runState;
    boolean faultState;
    List<ReqFactorInfo> factorInfoList = new ArrayList();
    List<ReqParamInfo> paramInfoList = new ArrayList();
    List<String> warnInfoList = new ArrayList();

    public ReqDevInfo() {
    }

    public String getDevName() {
        return this.devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevMode() {
        return this.devMode;
    }

    public void setDevMode(String devMode) {
        this.devMode = devMode;
    }

    public String getDevStatus() {
        return this.devStatus;
    }

    public void setDevStatus(String devStatus) {
        this.devStatus = devStatus;
    }

    public String getDevWarn() {
        return this.devWarn;
    }

    public void setDevWarn(String devWarn) {
        this.devWarn = devWarn;
    }

    public boolean isRunState() {
        return this.runState;
    }

    public void setRunState(boolean runState) {
        this.runState = runState;
    }

    public boolean isFaultState() {
        return this.faultState;
    }

    public void setFaultState(boolean faultState) {
        this.faultState = faultState;
    }

    public List<ReqFactorInfo> getFactorInfoList() {
        return this.factorInfoList;
    }

    public void setFactorInfoList(List<ReqFactorInfo> factorInfoList) {
        this.factorInfoList = factorInfoList;
    }

    public List<ReqParamInfo> getParamInfoList() {
        return this.paramInfoList;
    }

    public void setParamInfoList(List<ReqParamInfo> paramInfoList) {
        this.paramInfoList = paramInfoList;
    }

    public List<String> getWarnInfoList() {
        return this.warnInfoList;
    }

    public void setWarnInfoList(List<String> warnInfoList) {
        this.warnInfoList = warnInfoList;
    }
}
