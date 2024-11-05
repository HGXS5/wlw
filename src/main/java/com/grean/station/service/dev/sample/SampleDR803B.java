//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.sample;

import com.grean.station.service.MonitorService;
import com.grean.station.utils.Utility;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SampleDR803B extends SampleService {
    public SampleDR803B() {
    }

    public void doQuery() {
        byte[] bQuery = new byte[]{(byte)this.mAddress, 3, 0, 86, 0, 6, 0, 0};
        int crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
        byte[] tmp_bytes = Utility.getByteArray(crc_result);
        bQuery[bQuery.length - 2] = tmp_bytes[2];
        bQuery[bQuery.length - 1] = tmp_bytes[3];
        this.mInterComm.clearRecv(2 * this.mChannel);
        this.mInterComm.Send(bQuery);
        byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
        if (bRecv != null) {
            if (bRecv.length == 17 && bRecv[0] == this.mAddress && bRecv[1] == 3) {
                this.sampleVase = (bRecv[5] & 255) * 256 + (bRecv[6] & 255);
                this.vaseSize = (bRecv[9] & 255) * 256 + (bRecv[10] & 255);
                this.setStatus(true);
                Utility.logInfo("DR803B Sample: 查询成功，通道" + this.mChannel);
            } else {
                this.setStatus(false);
                Utility.logInfo("DR803B Sample: 查询失败，通道" + this.mChannel + "，返回格式异常");
            }
        } else if (MonitorService.autoData) {
            this.setStatus(true);
        } else {
            this.setStatus(false);
            Utility.logInfo("DR803B Sample: 查询失败，通道" + this.mChannel + "，无返回");
        }

    }

    public boolean doPrepare() {
        return super.doPrepare();
    }

    public int getSampleVase() {
        return this.sampleVase % this.vaseSize + 1;
    }

    public boolean doSample(String strQN) {
        byte[] bCmd = new byte[]{(byte)this.mAddress, 5, 0, 48, -1, 0, 0, 0};
        int iResult = 1;
        int crc_result = Utility.calcCrc16(bCmd, 0, bCmd.length - 2);
        byte[] tmp_bytes = Utility.getByteArray(crc_result);
        bCmd[bCmd.length - 2] = tmp_bytes[2];
        bCmd[bCmd.length - 1] = tmp_bytes[3];
        this.mInterComm.clearRecv(2 * this.mChannel + 1);
        this.mInterComm.Send(bCmd);
        byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel + 1, false, 2000);
        if (bRecv != null && bRecv.length == 8) {
            for(int i = 0; i < 6; ++i) {
                if (bRecv[i] != bCmd[i]) {
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
                Utility.logInfo("DR803B Sample: 留样成功");
                return true;
            default:
                Utility.logInfo("DR803B Sample: 留样失败");
                return false;
        }
    }
}
