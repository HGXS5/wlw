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

public class DevCodAvvor9000 extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevCodAvvor9000() {
    }

    public void doQuery() {
        if (!this.onCmd) {
            int regLength = 2;
            this.mAddress = this.mCfgDev.getAddress().intValue();
            byte[] bQuery = new byte[]{(byte)this.mAddress, 4, 0, 0, 0, 2, 0, 0};
            int crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            bQuery[bQuery.length - 2] = tmp_bytes[2];
            bQuery[bQuery.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, this.waitAnswer);
            if (bRecv != null) {
                if (bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 4) {
                    float testResult = Utility.getFloatBigEndianSwap(bRecv, 3);
                    this.setParamTestVal((double)testResult, "N", (Timestamp)null);
                    this.setStatus(true);
                    Utility.logInfo("AvvorCod9000 Dev: 查询成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("AvvorCod9000 Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else if (MonitorService.autoData) {
                double dTmp = this.rand.nextDouble() * this.mCfgFactor.getAlarmMax();
                this.setParamTestVal(dTmp, "N", (Timestamp)null);
                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("AvvorCod9000 Dev: 查询失败，通道" + this.mChannel + "，无返回");
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
            byte[] bCmd1;
            byte[] bCmd2;
            switch(cmdType) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                default:
                    bCmd1 = new byte[]{(byte)this.mAddress, 5, 0, 0, -1, 0, 0, 0};
                    bCmd2 = new byte[]{(byte)this.mAddress, 5, 0, 0, 0, 0, 0, 0};
                    break;
                case 8:
                    bCmd1 = new byte[]{(byte)this.mAddress, 5, 0, 2, -1, 0, 0, 0};
                    bCmd2 = new byte[]{(byte)this.mAddress, 5, 0, 2, 0, 0, 0, 0};
                    break;
                case 9:
                    bCmd1 = new byte[]{(byte)this.mAddress, 5, 0, 3, -1, 0, 0, 0};
                    bCmd2 = new byte[]{(byte)this.mAddress, 5, 0, 3, 0, 0, 0, 0};
                    break;
                case 10:
                    return false;
                case 11:
                    bCmd1 = new byte[]{(byte)this.mAddress, 5, 0, 1, -1, 0, 0, 0};
                    bCmd2 = new byte[]{(byte)this.mAddress, 5, 0, 1, 0, 0, 0, 0};
                    break;
                case 12:
                case 13:
                    return false;
            }

            this.onCmd = true;
            int crc_result = Utility.calcCrc16(bCmd1, 0, bCmd1.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            bCmd1[bCmd1.length - 2] = tmp_bytes[2];
            bCmd1[bCmd1.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel + 1);
            this.mInterComm.Send(bCmd1);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel + 1, false, this.waitAnswer);
            int iResult = this.getCmdResult("AvvorCod9000", bCmd1, bRecv);
            if (cmdQN != null && this.devExeListener != null) {
                this.devExeListener.OnExeEvent(cmdQN, cmdType, iResult);
            }

            crc_result = Utility.calcCrc16(bCmd2, 0, bCmd2.length - 2);
            tmp_bytes = Utility.getByteArray(crc_result);
            bCmd2[bCmd2.length - 2] = tmp_bytes[2];
            bCmd2[bCmd2.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel + 1);
            this.mInterComm.Send(bCmd2);
            this.onCmd = false;
            return iResult == 1;
        }
    }

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                DevCodAvvor9000.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
    }

    public void doStopCmd() {
        this.doDevCmdThread(11, (String)null, (String)null);
    }

    public void doRsetCmd() {
    }

    public void doSetTime(String strQN, String strParam) {
    }
}
