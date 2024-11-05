//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.display;

import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import java.util.List;

public class DisplayService extends DevService {
    List<CfgFactor> factorList;
    MonitorService monitorService;
    int showPage = 0;

    public DisplayService() {
    }

    public void setFactorList(List<CfgFactor> factorList) {
        this.factorList = factorList;
    }

    public void setMonitorService(MonitorService monitorService) {
        this.monitorService = monitorService;
        this.factorList = monitorService.getCfgMapper().getCfgFactorList();

        for(int i = 0; i < this.factorList.size(); ++i) {
            if (!((CfgFactor)this.factorList.get(i)).isUsed() || !((CfgFactor)this.factorList.get(i)).isParmType() && !((CfgFactor)this.factorList.get(i)).isFiveType()) {
                this.factorList.remove(i);
                --i;
            }
        }

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
