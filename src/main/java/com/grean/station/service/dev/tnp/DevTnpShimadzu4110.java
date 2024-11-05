//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.tnp;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.TimeHelper;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Random;

public class DevTnpShimadzu4110 extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevTnpShimadzu4110() {
    }
    @SuppressWarnings("FallThrough")
    public void doQuery() {
        if (!this.onCmd) {
            String var7 = this.mCfgFactor.getName();
            byte var8 = -1;
            switch(var7.hashCode()) {
                case 791379:
                    if (var7.equals("总氮")) {
                        var8 = 1;
                    }
                    break;
                case 794652:
                    if (var7.equals("总磷")) {
                        var8 = 0;
                    }
            }

            byte[] bQuery;
            byte[] tmp_bytes;
            int crc_result;
            switch(var8) {
                case 0:
                    bQuery = new byte[]{(byte)this.mAddress, 4, 0, 20, 0, 10, 0, 0};
                    crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
                    tmp_bytes = Utility.getByteArray(crc_result);
                    bQuery[bQuery.length - 2] = tmp_bytes[2];
                    bQuery[bQuery.length - 1] = tmp_bytes[3];
                    break;
                case 1:
                    bQuery = new byte[]{(byte)this.mAddress, 4, 0, 0, 0, 10, 0, 0};
                    crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
                    tmp_bytes = Utility.getByteArray(crc_result);
                    bQuery[bQuery.length - 2] = tmp_bytes[2];
                    bQuery[bQuery.length - 1] = tmp_bytes[3];
                    break;
                default:
                    return;
            }

            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
            if (bRecv != null) {
                if (bRecv.length == 25 && bRecv[0] == this.mAddress && bRecv[1] == 4) {
                    String strTime = String.format("20%02d-%02d-%02d %02d:%02d:%02d", bRecv[8], bRecv[10], bRecv[12], bRecv[14], bRecv[16], bRecv[18]);
                    Timestamp testTime = TimeHelper.parseDevTime(strTime);
                    float testResult = Utility.getFloatBigEndian(bRecv, 19);
                    this.setParamTestVal((double)testResult, "N", testTime);
                    this.setStatus(true);
                    Utility.logInfo("ShimadzuTnp4110 Dev: 查询成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("ShimadzuTnp4110 Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else if (MonitorService.autoData) {
                double dTmp = this.rand.nextDouble() * 10.0D;
                this.setParamTestVal(dTmp, "N", (Timestamp)null);
                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("ShimadzuTnp4110 Dev: 查询失败，通道" + this.mChannel + "，无返回");
            }

            byte[] bQueryState = new byte[]{(byte)this.mAddress, 4, 2, -128, 0, 1, 0, 0};
            crc_result = Utility.calcCrc16(bQueryState, 0, bQueryState.length - 2);
            tmp_bytes = Utility.getByteArray(crc_result);
            bQueryState[bQueryState.length - 2] = tmp_bytes[2];
            bQueryState[bQueryState.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQueryState);
            byte[] bRecvState = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
            if (bRecvState != null) {
                if (bRecvState.length == 7 && bRecvState[0] == this.mAddress && bRecvState[1] == 4) {
                    if (bRecvState[4] == 0) {
                        this.setParam("DevState", 0);
                    }

                    if (bRecvState[4] == 1) {
                        this.setParam("DevState", 1);
                    }

                    if (bRecvState[4] == 2) {
                        this.setParam("DevState", 9);
                    }

                    Utility.logInfo("ShimadzuTnp4110 Dev: 查询状态成功，通道" + this.mChannel);
                } else {
                    Utility.logInfo("ShimadzuTnp4110 Dev: 查询状态失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else {
                Utility.logInfo("ShimadzuTnp4110 Dev: 查询状态失败，通道" + this.mChannel);
            }

            byte[] bQueryWarn = new byte[]{(byte)this.mAddress, 4, 1, 44, 0, 10, 0, 0};
            crc_result = Utility.calcCrc16(bQueryWarn, 0, bQueryWarn.length - 2);
            tmp_bytes = Utility.getByteArray(crc_result);
            bQueryWarn[bQueryWarn.length - 2] = tmp_bytes[2];
            bQueryWarn[bQueryWarn.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQueryWarn);
            byte[] bRecvWarn = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
            if (bRecvWarn != null) {
                if (bRecvWarn.length == 25 && bRecvWarn[0] == this.mAddress && bRecvWarn[1] == 4) {
                    if (bRecvWarn[12] == 1) {
                        this.setParam("DevAlarm", 10);
                    } else if (bRecvWarn[20] != 1 && bRecvWarn[20] != 2 && bRecvWarn[20] != 4 && bRecvWarn[20] != 8 && bRecvWarn[20] != 16) {
                        if (bRecvWarn[20] == 32) {
                            this.setParam("DevAlarm", 4);
                        } else {
                            this.setParam("DevAlarm", 0);
                        }
                    } else {
                        this.setParam("DevAlarm", 1);
                    }

                    Utility.logInfo("ShimadzuTnp4110 Dev: 查询告警成功，通道" + this.mChannel);
                } else {
                    Utility.logInfo("ShimadzuTnp4110 Dev: 查询告警失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else {
                Utility.logInfo("ShimadzuTnp4110 Dev: 查询告警失败，通道" + this.mChannel);
            }

        }
    }

    public void saveResult() {
    }
    @SuppressWarnings("FallThrough")
    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        int iResult = 0;
        this.initDevState(cmdType);
        if (MonitorService.autoData) {
            return true;
        } else {
            this.mCmdType = cmdType;
            byte[] bCmd;
            switch(cmdType) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                default:
                    bCmd = new byte[]{(byte)this.mAddress, 16, 0, 0, 0, 1, 2, 0, 1, 0, 0};
                    break;
                case 8:
                    bCmd = new byte[]{(byte)this.mAddress, 16, 0, 30, 0, 1, 2, 0, 1, 0, 0};
                    break;
                case 9:
                    bCmd = new byte[]{(byte)this.mAddress, 16, 0, 30, 0, 1, 2, 0, 1, 0, 0};
                    break;
                case 10:
                    bCmd = new byte[]{(byte)this.mAddress, 16, 0, 40, 0, 1, 2, 0, 1, 0, 0};
                    break;
                case 11:
                case 12:
                case 13:
                    return false;
            }

            this.onCmd = true;
            int crc_result = Utility.calcCrc16(bCmd, 0, bCmd.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            bCmd[bCmd.length - 2] = tmp_bytes[2];
            bCmd[bCmd.length - 1] = tmp_bytes[3];

            for(int iTimes = this.mCmdTimes; iTimes > 0; --iTimes) {
                this.mInterComm.clearRecv(2 * this.mChannel + 1);
                this.mInterComm.Send(bCmd);
                byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel + 1, false, 2000);
                iResult = this.getCmdResult("ShimadzuTnp4110", bCmd, bRecv);
                if (iResult == 1) {
                    break;
                }
            }

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
                DevTnpShimadzu4110.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
        this.doDevCmdThread(10, (String)null, (String)null);
    }

    public void doStopCmd() {
        this.initDevState(0);
    }

    public void doRsetCmd() {
    }

    public void doSetTime(String strQN, String strParam) {
        if (!MonitorService.autoData) {
            this.onCmd = true;
            this.mCmdType = 13;
            byte[] bCmd = new byte[]{1, 16, 0, 20, 0, 7, 14, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            int iYear = Integer.parseInt(strParam.substring(0, 2));
            int iMon = Integer.parseInt(strParam.substring(2, 4));
            int iDay = Integer.parseInt(strParam.substring(4, 6));
            int iHour = Integer.parseInt(strParam.substring(6, 8));
            int iMin = Integer.parseInt(strParam.substring(8, 10));
            int iSec = Integer.parseInt(strParam.substring(10, 12));
            bCmd[10] = (byte)(iYear & 255);
            bCmd[12] = (byte)(iMon & 255);
            bCmd[14] = (byte)(iDay & 255);
            bCmd[16] = (byte)(iHour & 255);
            bCmd[18] = (byte)(iMin & 255);
            bCmd[20] = (byte)(iSec & 255);
            int crc_result = Utility.calcCrc16(bCmd, 0, bCmd.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            bCmd[bCmd.length - 2] = tmp_bytes[2];
            bCmd[bCmd.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel + 1);
            this.mInterComm.Send(bCmd);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel + 1, false, 2000);
            int iResult = this.getCmdResult("ShimadzuTnp4110", bCmd, bRecv);
            if (strQN != null && this.devExeListener != null) {
                this.devExeListener.OnExeEvent(strQN, this.mCmdType, iResult);
            }

            this.onCmd = false;
        }
    }
}
