//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.util.List;

public class QualityInfo {
    String qualityName;
    String qualityType;
    List<QualityParamInfo> paramInfoList;
    List<ReqParamInfo> stateInfoList;
    List<ReqCmdInfo> cmdInfoList;
    List<ReqPointInfo> pointInfoList;

    public QualityInfo() {
    }

    public String getQualityName() {
        return this.qualityName;
    }

    public void setQualityName(String qualityName) {
        this.qualityName = qualityName;
    }

    public String getQualityType() {
        return this.qualityType;
    }

    public void setQualityType(String qualityType) {
        this.qualityType = qualityType;
    }

    public List<QualityParamInfo> getParamInfoList() {
        return this.paramInfoList;
    }

    public void setParamInfoList(List<QualityParamInfo> paramInfoList) {
        this.paramInfoList = paramInfoList;
    }

    public List<ReqParamInfo> getStateInfoList() {
        return this.stateInfoList;
    }

    public void setStateInfoList(List<ReqParamInfo> stateInfoList) {
        this.stateInfoList = stateInfoList;
    }

    public List<ReqCmdInfo> getCmdInfoList() {
        return this.cmdInfoList;
    }

    public void setCmdInfoList(List<ReqCmdInfo> cmdInfoList) {
        this.cmdInfoList = cmdInfoList;
    }

    public List<ReqPointInfo> getPointInfoList() {
        return this.pointInfoList;
    }

    public void setPointInfoList(List<ReqPointInfo> pointInfoList) {
        this.pointInfoList = pointInfoList;
    }
}
