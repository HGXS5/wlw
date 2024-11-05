//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.tox;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Random;

public class DevToxGrean extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevToxGrean() {
    }

    public void doQuery() {
        if (!this.onCmd) {
            int regLength = 2;
            this.mAddress = this.mCfgDev.getAddress().intValue();
            byte[] bQuery1 = new byte[]{(byte)this.mAddress, 4, 1, 0, 0, 2, 0, 0};
            int crc_result = Utility.calcCrc16(bQuery1, 0, bQuery1.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            bQuery1[bQuery1.length - 2] = tmp_bytes[2];
            bQuery1[bQuery1.length - 1] = tmp_bytes[3];
            byte[] bQuery2 = new byte[]{(byte)this.mAddress, 4, 0, 12, 0, 2, 0, 0};
            crc_result = Utility.calcCrc16(bQuery2, 0, bQuery2.length - 2);
            tmp_bytes = Utility.getByteArray(crc_result);
            bQuery2[bQuery2.length - 2] = tmp_bytes[2];
            bQuery2[bQuery2.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel);
            if (this.mCmdType != 2) {
                this.mInterComm.Send(bQuery1);
            } else {
                this.mInterComm.Send(bQuery2);
            }

            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
            if (bRecv != null) {
                if (bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 4) {
                    float testResult;
                    if (this.mCmdType != 2) {
                        testResult = Utility.getFloatBigEndian(bRecv, 3);
                        if (Utility.isJcgy()) {
                            this.setParamTestVal((double)testResult, "iic", (Timestamp)null);
                        } else {
                            this.setParamTestVal((double)testResult, "N", (Timestamp)null);
                        }
                    } else {
                        int iFlag = Utility.getShort(bRecv, 3);
                        int iVal = Utility.getShort(bRecv, 5);
                        if (iFlag >= 0) {
                            testResult = (float)iVal / 100.0F;
                        } else {
                            testResult = (float)(-1 * iVal) / 100.0F;
                        }

                        this.setParamTestVal((double)testResult, "ii", (Timestamp)null);
                    }

                    this.setStatus(true);
                    Utility.logInfo("GreanTox Dev: 查询成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("GreanTox Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else if (MonitorService.autoData) {
                double dTmp = this.rand.nextDouble() * 10.0D;
                if (this.mCmdType != 2) {
                    if (Utility.isJcgy()) {
                        this.setParamTestVal(dTmp, "iic", (Timestamp)null);
                    } else {
                        this.setParamTestVal(dTmp, "N", (Timestamp)null);
                    }
                } else {
                    this.setParamTestVal(dTmp, "ii", (Timestamp)null);
                }

                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("GreanTox Dev: 查询失败，通道" + this.mChannel + "，无返回");
            }

            byte[] bQueryState = new byte[]{(byte)this.mAddress, 4, 0, -76, 0, 1, 0, 0};
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

                    Utility.logInfo("GreanTox Dev: 查询状态成功，通道" + this.mChannel);
                } else {
                    Utility.logInfo("GreanTox Dev: 查询状态失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else {
                Utility.logInfo("GreanTox Dev: 查询状态失败，通道" + this.mChannel);
            }

        }
    }

    public void saveResult() {
    }
    @SuppressWarnings("FallThrough")
    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        this.initDevState(cmdType);
        this.mCmdType = cmdType;
        if (MonitorService.autoData) {
            return true;
        } else {
            byte[] bCmd;
            switch(cmdType) {
                case 2:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 0, 0, 0, 2, 0, 0};
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                default:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 0, 0, 0, 1, 0, 0};
                    break;
                case 8:
                    return false;
                case 9:
                    return false;
                case 10:
                    return false;
                case 11:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 0, 0, 0, 4, 0, 0};
                    break;
                case 12:
                    return false;
                case 13:
                    return false;
            }

            this.onCmd = true;
            int crc_result = Utility.calcCrc16(bCmd, 0, bCmd.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            bCmd[bCmd.length - 2] = tmp_bytes[2];
            bCmd[bCmd.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel + 1);
            this.mInterComm.Send(bCmd);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel + 1, false, 2000);
            int iResult = this.getCmdResult("GreanTox", bCmd, bRecv);
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
                DevToxGrean.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
        this.doDevCmdThread(11, (String)null, (String)null);
    }

    public void doRsetCmd() {
    }

    public void doSetTime(String strQN, String strParam) {
    }
}
