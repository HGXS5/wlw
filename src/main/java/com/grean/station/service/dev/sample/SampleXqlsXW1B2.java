//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.sample;

import com.grean.station.service.MonitorService;
import com.grean.station.utils.Utility;

public class SampleXqlsXW1B2 extends SampleService {
    private boolean onCmd = false;

    public SampleXqlsXW1B2() {
    }

    public void doQuery() {
        if (!this.onCmd) {
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
                    Utility.logInfo("XqlsXW1B2 Sample: 查询成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("XqlsXW1B2 Sample: 查询失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else if (MonitorService.autoData) {
                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("XqlsXW1B2 Sample: 查询失败，通道" + this.mChannel + "，无返回");
            }

        }
    }

    public boolean doPrepare() {
        return super.doPrepare();
    }

    public boolean doSample(String strQN) {
        this.onCmd = true;
        byte[] bCmd = new byte[]{(byte)this.mAddress, 6, 0, -94, 0, 1, 0, 0};
        int iResult = 1;
        int crc_result = Utility.calcCrc16(bCmd, 0, bCmd.length - 2);
        byte[] tmp_bytes = Utility.getByteArray(crc_result);
        bCmd[bCmd.length - 2] = tmp_bytes[2];
        bCmd[bCmd.length - 1] = tmp_bytes[3];

        for(int iTimes = this.mCmdTimes; iTimes > 0; --iTimes) {
            try {
                Thread.sleep(100L);
                this.mInterComm.clearRecv(2 * this.mChannel + 1);
                Thread.sleep(100L);
                this.mInterComm.Send(bCmd);
            } catch (Exception var9) {
            }

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

            if (iResult == 1) {
                break;
            }
        }

        this.onCmd = false;
        switch(iResult) {
            case 1:
                Utility.logInfo("XqlsXW1B2 Sample: 留样成功");
                return true;
            default:
                Utility.logInfo("XqlsXW1B2 Sample: 留样失败");
                return false;
        }
    }

    public boolean doEmpty(String strQN) {
        byte[] bCmd = new byte[]{(byte)this.mAddress, 6, 0, -60, 0, 1, 0, 0};
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
                Utility.logInfo("XqlsXW1B2 Sample: 排空成功");
                return true;
            default:
                Utility.logInfo("XqlsXW1B2 Sample: 排空失败");
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
                Utility.logInfo("XqlsXW1B2 Sample: 复位成功");
                return true;
            default:
                Utility.logInfo("XqlsXW1B2 Sample: 复位失败");
                return false;
        }
    }
}
