//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.sample;

import com.grean.station.service.MonitorService;
import com.grean.station.utils.Utility;

public class SampleXqls9330 extends SampleService {
    public SampleXqls9330() {
    }

    public void doQuery() {
        byte[] bQuery = new byte[]{(byte)this.mAddress, 3, 0, 9, 0, 24, 0, 0};
        int crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
        byte[] tmp_bytes = Utility.getByteArray(crc_result);
        bQuery[bQuery.length - 2] = tmp_bytes[2];
        bQuery[bQuery.length - 1] = tmp_bytes[3];
        this.mInterComm.clearRecv(2 * this.mChannel);
        this.mInterComm.Send(bQuery);
        byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
        if (bRecv != null) {
            if (bRecv.length == 53 && bRecv[0] == this.mAddress && bRecv[1] == 3) {
                for(int i = 0; i < 24; ++i) {
                    this.vaseState[i] = Utility.getShort(bRecv, 3 + 2 * i);
                }

                this.setStatus(true);
                Utility.logInfo("Xqls9330 Sample: 查询成功，通道" + this.mChannel);
            } else {
                this.setStatus(false);
                Utility.logInfo("Xqls9330 Sample: 查询失败，通道" + this.mChannel + "，返回格式异常");
            }
        } else if (MonitorService.autoData) {
            this.setStatus(true);
        } else {
            this.setStatus(false);
            Utility.logInfo("Xqls9330 Sample: 查询失败，通道" + this.mChannel + "，无返回");
        }

    }

    public boolean doPrepare() {
        return super.doPrepare();
    }

    public boolean doSample(String strQN) {
        byte[] bCmd = new byte[]{(byte)this.mAddress, 16, 0, -96, 0, 3, 6, 1, -12, 0, 1, 0, 1, 0, 0};
        int bottleIndex;
        if (strQN == null) {
            this.sampleVase = this.sampleVase % this.vaseSize + 1;
            bottleIndex = this.sampleVase;
        } else {
            bottleIndex = Integer.parseInt(strQN);
        }

        int iResult = 1;
        bCmd[7] = (byte)(this.sampleVolume / 256);
        bCmd[8] = (byte)(this.sampleVolume % 256);
        bCmd[9] = (byte)(bottleIndex / 256);
        bCmd[10] = (byte)(bottleIndex % 256);
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

        switch(iResult) {
            case 1:
                Utility.logInfo("Xqls9330 Sample: 留样成功");
                return true;
            default:
                Utility.logInfo("Xqls9330 Sample: 留样失败");
                return false;
        }
    }

    public boolean doEmpty(String strQN) {
        byte[] bCmd = new byte[]{(byte)this.mAddress, 16, 0, -62, 0, 3, 6, 0, 1, 0, 1, 0, 1, 0, 0};
        if (strQN == null) {
            bCmd[7] = 0;
            bCmd[8] = 1;
            bCmd[9] = 0;
            bCmd[10] = 24;
        } else {
            int bottleIndex = Integer.parseInt(strQN);
            bCmd[7] = (byte)(bottleIndex / 256);
            bCmd[8] = (byte)(bottleIndex % 256);
            bCmd[9] = 0;
            bCmd[10] = 1;
        }

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

        switch(iResult) {
            case 1:
                Utility.logInfo("Xqls9330 Sample: 排空成功");
                return true;
            default:
                Utility.logInfo("Xqls9330 Sample: 排空失败");
                return false;
        }
    }

    public boolean doReset() {
        byte[] bCmd = new byte[]{(byte)this.mAddress, 6, 0, -83, 0, 1, 0, 0};
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

        switch(iResult) {
            case 1:
                Utility.logInfo("Xqls9330 Sample: 复位成功");
                return true;
            default:
                Utility.logInfo("Xqls9330 Sample: 复位失败");
                return false;
        }
    }
}
