//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.tnp;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Random;

public class DevTnpDkk160 extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevTnpDkk160() {
    }
    @SuppressWarnings("FallThrough")
    public void doQuery() {
        if (!this.onCmd) {
            int regLength = 4;
            this.mAddress = this.mCfgDev.getAddress().intValue();
            byte[] bQuery = new byte[]{(byte)this.mAddress, 3, 0, 0, 0, 4, 0, 0};
            int crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            bQuery[bQuery.length - 2] = tmp_bytes[2];
            bQuery[bQuery.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
            double dTmp;
            if (bRecv != null) {
                if (bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 3) {
                    String var8 = this.mCfgFactor.getName();
                    byte var9 = -1;
                    switch(var8.hashCode()) {
                        case 791379:
                            if (var8.equals("总氮")) {
                                var9 = 1;
                            }
                            break;
                        case 794652:
                            if (var8.equals("总磷")) {
                                var9 = 0;
                            }
                    }

                    switch(var9) {
                        case 0:
                            dTmp = (double)Utility.getFloatBigEndian(bRecv, 7);
                            break;
                        case 1:
                            dTmp = (double)Utility.getFloatBigEndian(bRecv, 3);
                            break;
                        default:
                            dTmp = 0.0D;
                    }

                    this.setParamTestVal(dTmp, "N", (Timestamp)null);
                    this.setStatus(true);
                    Utility.logInfo("DkkTnp160 Dev: 查询成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("DkkTnp160 Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else if (MonitorService.autoData) {
                dTmp = this.rand.nextDouble() * 10.0D;
                this.setParamTestVal(dTmp, "N", (Timestamp)null);
                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("DkkTnp160 Dev: 查询失败，通道" + this.mChannel + "，无返回");
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
            switch(cmdType) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                default:
                    byte[] bCmd = new byte[]{(byte)this.mAddress, 6, 0, 80, 0, 1, 0, 0};
                    this.onCmd = true;
                    int crc_result = Utility.calcCrc16(bCmd, 0, bCmd.length - 2);
                    byte[] tmp_bytes = Utility.getByteArray(crc_result);
                    bCmd[bCmd.length - 2] = tmp_bytes[2];
                    bCmd[bCmd.length - 1] = tmp_bytes[3];
                    this.mInterComm.clearRecv(2 * this.mChannel + 1);
                    this.mInterComm.Send(bCmd);
                    byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel + 1, false, 2000);
                    int iResult = this.getCmdResult("DkkTnp160", bCmd, bRecv);
                    if (cmdQN != null && this.devExeListener != null) {
                        this.devExeListener.OnExeEvent(cmdQN, cmdType, iResult);
                    }

                    this.onCmd = false;
                    if (iResult == 1) {
                        return true;
                    }

                    return false;
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                    return false;
            }
        }
    }

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                DevTnpDkk160.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
