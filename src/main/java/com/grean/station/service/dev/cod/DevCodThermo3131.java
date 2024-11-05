//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.cod;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Random;

public class DevCodThermo3131 extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevCodThermo3131() {
    }

    public void doQuery() {
        if (!this.onCmd) {
            int regLength = 2;
            this.mAddress = this.mCfgDev.getAddress().intValue();
            byte[] bQuery = new byte[]{(byte)this.mAddress, 3, 0, 2, 0, 2, 0, 0};
            int crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            bQuery[bQuery.length - 2] = tmp_bytes[2];
            bQuery[bQuery.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
            if (bRecv != null) {
                if (bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 3) {
                    float testResult = (float)Utility.getDword(bRecv, 3) / 1000.0F;
                    this.setParamTestVal((double)testResult, "N", (Timestamp)null);
                    this.setStatus(true);
                    Utility.logInfo("ThermoCod3131 Dev: 查询成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("ThermoCod3131 Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else if (MonitorService.autoData) {
                double dTmp = this.rand.nextDouble() * 10.0D;
                this.setParamTestVal(dTmp, "N", (Timestamp)null);
                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("ThermoCod3131 Dev: 查询失败，通道" + this.mChannel + "，无返回");
            }

        }
    }

    public void saveResult() {
    }
    @SuppressWarnings("FallThrough")
    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        this.initDevState(cmdType);
        if (MonitorService.autoData) {
            return true;
        } else {
            this.mCmdType = cmdType;
            byte[] bCmd;
            switch(cmdType) {
                case 8:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 0, 61, 0, 2, 0, 0};
                    break;
                case 9:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 0, 61, 0, 2, 0, 0};
                    break;
                case 10:
                    return false;
                case 11:
                    return false;
                case 12:
                    return false;
                case 13:
                    return false;
                default:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 0, 61, 0, 1, 0, 0};
            }

            this.onCmd = true;
            byte[] bCtrl = new byte[]{(byte)this.mAddress, 6, 0, 65, 0, 1, 0, 0};
            int crc_result = Utility.calcCrc16(bCtrl, 0, bCtrl.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            bCtrl[bCtrl.length - 2] = tmp_bytes[2];
            bCtrl[bCtrl.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel + 1);
            this.mInterComm.Send(bCtrl);

            try {
                Thread.sleep(1000L);
            } catch (Exception var10) {
            }

            crc_result = Utility.calcCrc16(bCmd, 0, bCmd.length - 2);
            tmp_bytes = Utility.getByteArray(crc_result);
            bCmd[bCmd.length - 2] = tmp_bytes[2];
            bCmd[bCmd.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel + 1);
            this.mInterComm.Send(bCmd);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel + 1, false, 2000);
            int iResult = this.getCmdResult("ThermoCod3131", bCmd, bRecv);
            if (cmdQN != null && this.devExeListener != null) {
                this.devExeListener.OnExeEvent(cmdQN, cmdType, iResult);
            }

            this.onCmd = false;
            return iResult == 1;
        }
    }

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                DevCodThermo3131.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
    }

    public void doStdCal(String strQN, String strParam) {
    }

    public void doInitCmd() {
    }

    public void doStopCmd() {
        this.initDevState(0);
    }

    public void doRsetCmd() {
    }

    public void doSetTime(String strQN, String strParam) {
    }
}