//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.sample;

import com.grean.station.service.MonitorService;
import com.grean.station.utils.Utility;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SampleGrasp9320 extends SampleService {
    public SampleGrasp9320() {
    }

    public void doQuery() {
        byte[] bQuery = new byte[]{(byte)this.mAddress, 3, 0, 49, 0, 2, 0, 0};
        int crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
        byte[] tmp_bytes = Utility.getByteArray(crc_result);
        bQuery[bQuery.length - 2] = tmp_bytes[2];
        bQuery[bQuery.length - 1] = tmp_bytes[3];
        this.mInterComm.clearRecv(2 * this.mChannel);
        this.mInterComm.Send(bQuery);
        byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
        if (bRecv != null) {
            if (bRecv.length == 9 && bRecv[0] == this.mAddress && bRecv[1] == 3) {
                this.setStatus(true);
                Utility.logInfo("Grasp9320 Sample: 查询成功，通道" + this.mChannel);
            } else {
                this.setStatus(false);
                Utility.logInfo("Grasp9320 Sample: 查询失败，通道" + this.mChannel + "，返回格式异常");
            }
        } else if (MonitorService.autoData) {
            this.setStatus(true);
        } else {
            this.setStatus(false);
            Utility.logInfo("Grasp9320 Sample: 查询失败，通道" + this.mChannel + "，无返回");
        }

    }

    public boolean doSample(String strQN) {
        byte[] bSet = new byte[]{(byte)this.mAddress, 16, 0, 28, 0, 2, 4, 0, 1, 1, -12, 0, 0};
        int iResult = 1;
        this.sampleVase = this.sampleVase % this.vaseSize + 1;
        bSet[8] = (byte)this.sampleVase;
        bSet[9] = (byte)(this.sampleVolume / 256);
        bSet[10] = (byte)(this.sampleVolume % 256);
        int crc_result = Utility.calcCrc16(bSet, 0, bSet.length - 2);
        byte[] tmp_bytes = Utility.getByteArray(crc_result);
        bSet[bSet.length - 2] = tmp_bytes[2];
        bSet[bSet.length - 1] = tmp_bytes[3];
        this.mInterComm.clearRecv(2 * this.mChannel + 1);
        this.mInterComm.Send(bSet);
        byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel + 1, false, 2000);
        if (bRecv != null && bRecv.length == 8) {
            for(int i = 0; i < 6; ++i) {
                if (bRecv[i] != bSet[i]) {
                    iResult = 2;
                }
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
                Utility.logInfo("Grasp9320 Sample: 留样成功");
                this.cfgSample.setBottle_id(this.sampleVase);
                return true;
            default:
                Utility.logInfo("Grasp9320 Sample: 留样失败");
                --this.sampleVase;
                return false;
        }
    }

    public int getEmptySampleVase() {
        return 99;
    }
}
