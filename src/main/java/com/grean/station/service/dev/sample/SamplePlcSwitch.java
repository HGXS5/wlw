//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.sample;

import com.grean.station.utils.Utility;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SamplePlcSwitch extends SampleService {
    public SamplePlcSwitch() {
    }

    public void doQuery() {
        this.setStatus(true);
        Utility.logInfo("PlcSwitch Sample: 查询成功");
    }

    public boolean doSample(String strQN) {
        int iResult = 1;
        this.sampleVase = this.sampleVase % this.vaseSize + 1;
        if (this.scriptService != null) {
            if (this.scriptService.doCmd(1, "采样器")) {
                try {
                    Thread.sleep(2000L);
                    this.scriptService.doCmd(0, "采样器");
                } catch (Exception var6) {
                }
            } else {
                iResult = 2;
            }
        } else {
            iResult = 2;
        }

        if (this.sampleExeListener != null && strQN != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String strDataTime = df.format(new Date());
            String strVaseNo = this.sampleVase + "";
            this.sampleExeListener.OnExeEvent(strQN, 3015, iResult, strDataTime, strVaseNo);
        }

        switch(iResult) {
            case 1:
                Utility.logInfo("PlcSwitch Sample: 留样成功");
                this.cfgSample.setBottle_id(this.sampleVase);
                return true;
            default:
                Utility.logInfo("PlcSwitch Sample: 留样失败");
                --this.sampleVase;
                return false;
        }
    }

    public int getEmptySampleVase() {
        return 99;
    }
}
