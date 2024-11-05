//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.script;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ScriptInfo {
    Timestamp runTime;
    int runCmd = 1;
    String saveType = "N";
    List<Integer> factorList = new ArrayList();
    List<Integer> factorList2 = new ArrayList();
    List<Boolean> cmdRunList = new ArrayList();

    public ScriptInfo() {
    }

    public Timestamp getRunTime() {
        return this.runTime;
    }

    public void setRunTime(Timestamp runTime) {
        this.runTime = runTime;
    }

    public int getRunCmd() {
        return this.runCmd;
    }

    public void setRunCmd(int runCmd) {
        this.runCmd = runCmd;
    }

    public String getSaveType() {
        return this.saveType;
    }

    public void setSaveType(String saveType) {
        this.saveType = saveType;
    }

    public List<Integer> getFactorList() {
        return this.factorList;
    }

    public void setFactorList(List<Integer> factorList) {
        this.factorList = factorList;
    }

    public List<Boolean> getCmdRunList() {
        return this.cmdRunList;
    }

    public void setCmdRunList(List<Boolean> cmdRunList) {
        this.cmdRunList = cmdRunList;
    }

    public boolean setCmdRunList(int cmdRunId) {
        if (cmdRunId < this.cmdRunList.size()) {
            this.cmdRunList.set(cmdRunId, true);
            return true;
        } else {
            return false;
        }
    }

    public List<Integer> getFactorList2() {
        return this.factorList2;
    }

    public void setFactorList2(List<Integer> factorList2) {
        this.factorList2 = factorList2;
    }
}
