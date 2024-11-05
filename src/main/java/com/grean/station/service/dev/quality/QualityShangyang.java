//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.quality;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;

public class QualityShangyang extends DevService {
    private boolean onCmd = false;

    public QualityShangyang() {
    }

    void getQuery(int offsetHigh, int offsetLow, int lengthHigh, int lengthLow, String queryTag) {
        if (!this.onCmd) {
            byte[] bQuery = new byte[]{(byte)this.mAddress, 3, (byte)offsetHigh, (byte)offsetLow, (byte)lengthHigh, (byte)lengthLow, 0, 0};
            int crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            bQuery[bQuery.length - 2] = tmp_bytes[2];
            bQuery[bQuery.length - 1] = tmp_bytes[3];
            int regOffset = 256 * offsetHigh + offsetLow;
            int regLength = 256 * lengthHigh + lengthLow;
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 1000);
            if (bRecv != null) {
                if (bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 3) {
                    this.setStatus(true);
                    Utility.logInfo("Shangyang Quality: 查询" + queryTag + "成功");
                } else {
                    this.setStatus(false);
                    Utility.logInfo("Shangyang Quality: 查询" + queryTag + "失败");
                }
            } else {
                this.setStatus(false);
                Utility.logInfo("Shangyang Quality: 查询" + queryTag + "失败");
            }

        }
    }

    public void doQuery() {
        try {
            int iWait = this.mAddress % 5;
            Thread.sleep((long)(iWait * 2000));
        } catch (Exception var2) {
        }

        this.getQuery(11, 184, 0, 10, this.mCfgDev.getName() + "状态区");
    }

    public void saveResult() {
    }

    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        int iResult = 1;
        if (MonitorService.autoData) {
            return true;
        } else {
            this.mCmdType = cmdType;
            this.onCmd = true;
            byte[] bCmd;
            int crc_result;
            byte[] bRecv;
            switch(cmdType) {
                case 3008:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 11, -64, 0, 3, 0, 0};
                    break;
                case 3100:
                case 3200:
                case 3300:
                case 3400:
                    if (cmdParam != null) {
                        crc_result = cmdType / 256;
                        int lowAdd = cmdType % 256;
                        float fVol = Float.parseFloat(cmdParam);
                        bRecv = Utility.getByteArray(fVol);
                        bCmd = new byte[]{(byte)this.mAddress, 16, (byte)crc_result, (byte)lowAdd, 0, 2, 4, bRecv[2], bRecv[3], bRecv[0], bRecv[1], 0, 0};
                    } else {
                        bCmd = new byte[]{0, 0};
                    }
                    break;
                default:
                    bCmd = new byte[]{0, 0};
            }

            if (bCmd.length > 2) {
                crc_result = Utility.calcCrc16(bCmd, 0, bCmd.length - 2);
                byte[] tmp_bytes = Utility.getByteArray(crc_result);
                bCmd[bCmd.length - 2] = tmp_bytes[2];
                bCmd[bCmd.length - 1] = tmp_bytes[3];

                for(int iTimes = this.mCmdTimes; iTimes > 0; --iTimes) {
                    this.mInterComm.clearRecv(2 * this.mChannel + 1);
                    this.mInterComm.Send(bCmd);
                    bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel + 1, false, 3000);
                    iResult = this.getCmdResult("QualityShangyang", bCmd, bRecv);
                    if (iResult == 1) {
                        break;
                    }
                }
            } else {
                iResult = 2;
            }

            this.onCmd = false;
            return iResult == 1;
        }
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
