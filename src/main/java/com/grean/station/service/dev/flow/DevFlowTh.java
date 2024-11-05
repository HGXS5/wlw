//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.flow;

import com.grean.station.comm.CommInter;
import com.grean.station.domain.DO.cfg.CfgDev;
import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.http.PullService;
import com.grean.station.http.TokenService;
import com.grean.station.http.model.DataHandler;
import com.grean.station.http.model.ThConfig;
import com.grean.station.http.model.ThData;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DevFlowTh extends DevService {
    private static ExecutorService es = Executors.newFixedThreadPool(1);
    private ThConfig thConfig;
    private Map<String, String> map = new HashMap();

    public DevFlowTh() {
    }

    public ThConfig getThConfig() {
        return this.thConfig;
    }

    public void setThConfig(ThConfig thConfig) {
        this.thConfig = thConfig;
    }

    public void init(CommInter interComm, CfgDev cfgDev, CfgFactor cfgFactor, List regList, int channel, int cmdTime) {
        super.init(interComm, cfgDev, cfgFactor, regList, channel, cmdTime);
        this.map.put("c7", "流量");
        this.map.put("c8", "流速");
        this.map.put("c10", "液位");
        this.map.put("c13", "水温");
        this.map.put("c14", "中继温度");
        this.map.put("c15", "中继湿度");
        this.map.put("c20", "设备电量百分比");
        if (this.thConfig != null) {
            TokenService ts = new TokenService(this.thConfig.getUsername(), this.thConfig.getPassword());
            DataHandler dh = new DataHandler() {
                public boolean handleDatas(List<ThData> datas) {
                    Utility.logger.info("数据：[{}]", datas);
                    Iterator var2 = datas.iterator();

                    while(var2.hasNext()) {
                        ThData thData = (ThData)var2.next();
                        if (thData.placeId.equals(DevFlowTh.this.thConfig.getPlaceid()) && thData.equipId.equals(DevFlowTh.this.thConfig.getEquipid())) {
                            DevFlowTh.this.channelVals[0] = thData.c7;
                            DevFlowTh.this.channelVals[1] = thData.c8;
                            DevFlowTh.this.channelVals[2] = thData.c10;
                            DevFlowTh.this.channelVals[3] = thData.c13;
                            DevFlowTh.this.channelVals[4] = thData.c14;
                            DevFlowTh.this.channelVals[5] = thData.c15;
                            DevFlowTh.this.channelVals[6] = thData.c20;
                        }
                    }

                    DevFlowTh.this.setStatus(true);
                    return false;
                }
            };
            PullService pullService = new PullService(ts, dh);
            ExecutorService var10000 = es;
            Objects.requireNonNull(pullService);
            var10000.submit(pullService::start);
        }

    }

    public void doQuery() {
    }

    public void saveResult() {
    }

    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        return false;
    }

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                DevFlowTh.this.doDevCmd(cmdType, cmdParam, cmdQN);
            }
        });
    }

    public void doMeaCmd() {
        this.doDevCmdThread(1, (String)null, (String)null);
    }

    public void doStdCmd(String strQN, String strParam) {
        this.doDevCmdThread(2, strParam, strQN);
    }

    public void doZeroCmd(String strQN, String strParam) {
        this.doDevCmdThread(3, strParam, strQN);
    }

    public void doSpanCmd(String strQN, String strParam) {
        this.doDevCmdThread(4, strParam, strQN);
    }

    public void doBlnkCmd(String strQN, String strParam) {
        this.doDevCmdThread(5, strParam, strQN);
    }

    public void doParCmd(String strQN, String strParam) {
        this.doDevCmdThread(6, strParam, strQN);
    }

    public void doRcvrCmd(String strQN, String strParam) {
        this.doDevCmdThread(7, strParam, strQN);
    }

    public void doBlnkCal(String strQN, String strParam) {
        this.doDevCmdThread(8, strParam, strQN);
    }

    public void doStdCal(String strQN, String strParam) {
        this.doDevCmdThread(9, strParam, strQN);
    }

    public void doInitCmd() {
        this.doDevCmdThread(10, (String)null, (String)null);
    }

    public void doStopCmd() {
        this.doDevCmdThread(11, (String)null, (String)null);
    }

    public void doRsetCmd() {
        this.doDevCmdThread(12, (String)null, (String)null);
    }

    public void doSetTime(String strQN, String strParam) {
        this.doDevCmdThread(13, strParam, strQN);
    }
}
