//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.flow;

import com.grean.station.service.dev.DevService;

public class FlowService extends DevService {
    public Double flowDeep = null;
    public Double flowArea = null;
    public FlowService flowShare = null;

    public FlowService() {
    }

    public Double getFlowDeep() {
        return this.flowDeep;
    }

    public void setFlowDeep(Double flowDeep) {
        this.flowDeep = flowDeep;
    }

    public Double getFlowArea() {
        return this.flowArea;
    }

    public void setFlowArea(Double flowArea) {
        this.flowArea = flowArea;
    }

    public FlowService getFlowShare() {
        return this.flowShare;
    }

    public void setFlowShare(FlowService flowShare) {
        this.flowShare = flowShare;
    }

    public void doQuery() {
    }

    public void saveResult() {
    }

    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        return false;
    }

    public void doMeaCmd() {
    }

    public void doStdCmd(String strQN, String strParam) {
    }

    public void doZeroCmd(String strQN, String strParam) {
    }

    public void doSpanCmd(String strQN, String strParam) {
    }

    public void doBlnkCmd(String strQN, String strParam) {
    }

    public void doParCmd(String strQN, String strParam) {
    }

    public void doRcvrCmd(String strQN, String strParam) {
    }

    public void doBlnkCal(String strQN, String strParam) {
    }

    public void doStdCal(String strQN, String strParam) {
    }

    public void doInitCmd() {
    }

    public void doStopCmd() {
    }

    public void doRsetCmd() {
    }

    public void doSetTime(String strQN, String strParam) {
    }
}
