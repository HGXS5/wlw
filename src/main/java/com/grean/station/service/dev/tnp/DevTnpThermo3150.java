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

public class DevTnpThermo3150 extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevTnpThermo3150() {
    }
    @SuppressWarnings("FallThrough")
    public void doQuery() {
        if (!this.onCmd) {
            int regLength = 4;
            this.mAddress = this.mCfgDev.getAddress().intValue();
            byte[] bQuery = new byte[]{(byte) this.mAddress, 3, 0, 0, 0, 4, 0, 0};
            int crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            bQuery[bQuery.length - 2] = tmp_bytes[2];
            bQuery[bQuery.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, this.waitAnswer);
            if (bRecv != null) {
                if (bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 4) {
                    String var9 = this.mCfgFactor.getName();
                    byte var10 = -1;
                    switch (var9.hashCode()) {
                        case 791379:
                            if (var9.equals("总氮")) {
                                var10 = 1;
                            }
                            break;
                        case 794652:
                            if (var9.equals("总磷")) {
                                var10 = 0;
                            }
                    }

                    float testResult;
                    switch (var10) {
                        case 0:
                            testResult = Utility.getFloatBigEndian(bRecv, 7);
                            break;
                        case 1:
                            testResult = Utility.getFloatBigEndian(bRecv, 3);
                            break;
                        default:
                            testResult = 0.0F;
                            float var7 = 0.0F;
                    }

                    this.setParamTestVal((double) testResult, "N", (Timestamp) null);
                    this.setStatus(true);
                    Utility.logInfo("ThermoTnp3150 Dev: 查询成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("ThermoTnp3150 Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else if (MonitorService.autoData) {
                double dTmp = this.rand.nextDouble() * this.mCfgFactor.getAlarmMax();
                this.setParamTestVal(dTmp, "N", (Timestamp) null);
                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("ThermoTnp3150 Dev: 查询失败，通道" + this.mChannel + "，无返回");
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
            byte[] bCmd;
            this.mCmdType = cmdType;
            String var6;
            byte var7;
            label58:
            switch (cmdType) {
                case 8:
                    var6 = this.mCfgFactor.getName();
                    var7 = -1;
                    switch (var6.hashCode()) {
                        case 794652:
                            if (var6.equals("总磷")) {
                                var7 = 0;
                            }
                            // fall through
                        default:
                            switch (var7) {
                                case 0:
                                    bCmd = new byte[]{(byte) this.mAddress, 6, 0, 34, 0, 1, 0, 0};
                                    break label58;
                                default:
                                    return false;
                            }
                    }
                    // fall through
                case 9:
                    var6 = this.mCfgFactor.getName();
                    var7 = -1;
                    switch (var6.hashCode()) {
                        case 794652:
                            if (var6.equals("总磷")) {
                                var7 = 0;
                            }
                            // fall through
                        default:
                            switch (var7) {
                                case 0:
                                    bCmd = new byte[]{(byte) this.mAddress, 6, 0, 34, 0, 1, 0, 0};
                                    break label58;
                                default:
                                    return false;
                            }
                    }
                    // fall through
                case 10:
                    return false;
                case 11:
                    return false;
                case 12:
                    return false;
                case 13:
                    return false;
                default:
                    var6 = this.mCfgFactor.getName();
                    var7 = -1;
                    switch (var6.hashCode()) {
                        case 791379:
                            if (var6.equals("总氮")) {
                                var7 = 1;
                            }
                            break;
                        case 794652:
                            if (var6.equals("总磷")) {
                                var7 = 0;
                            }
                            // fall through
                    }

                    switch (var7) {
                        case 0:
                            bCmd = new byte[]{(byte) this.mAddress, 6, 0, 34, 0, 0, 0, 0};
                            break;
                        case 1:
                            bCmd = new byte[]{(byte) this.mAddress, 6, 0, 33, 0, 0, 0, 0};
                            break;
                        default:
                            return false;
                    }
            }

            this.onCmd = true;
            int crc_result = Utility.calcCrc16(bCmd, 0, bCmd.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            bCmd[bCmd.length - 2] = tmp_bytes[2];
            bCmd[bCmd.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel + 1);
            this.mInterComm.Send(bCmd);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel + 1, false, this.waitAnswer);
            int iResult = this.getCmdResult("ThermoTnp3150", bCmd, bRecv);
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
                DevTnpThermo3150.this.doDevCmd(cmdType, cmdParam, cmdQN);
            }
        });
    }

    public void doMeaCmd() {
        this.doDevCmdThread(1, (String) null, (String) null);
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
        this.doDevCmd(8, strParam, strQN);
    }

    public void doStdCal(String strQN, String strParam) {
        this.doDevCmd(9, strParam, strQN);
    }

    public void doInitCmd() {
        this.doDevCmdThread(10, (String) null, (String) null);
    }

    public void doStopCmd() {
        this.doDevCmdThread(11, (String) null, (String) null);
    }

    public void doRsetCmd() {
        this.doDevCmdThread(12, (String) null, (String) null);
    }

    public void doSetTime(String strQN, String strParam) {
        this.doDevCmdThread(13, strParam, strQN);
    }
}
