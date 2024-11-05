//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.cl;

import com.grean.station.comm.CommInter;
import com.grean.station.domain.DO.cfg.CfgDev;
import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

public class DevClBLPowerMon extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();
    private String[] warnSet1 = new String[]{"CAN bus error", "Memory error", "Extension board communication error", "Photometer communication error", "Spectrometer error", "Voltammetry: communication error", "Main board: 5 V fault", "Main board: temperature warning"};
    private String[] warnSet2 = new String[]{"Liquid detector 1 error", "Liquid detector 2 error", "Liquid detector 3 error", "Liquid detector 4 error", "Liquid detector 5 error", "Liquid detector 6 error"};

    public DevClBLPowerMon() {
    }

    public void init(CommInter interComm, CfgDev cfgDev, CfgFactor cfgFactor, List regList, int channel, int cmdTime) {
        super.init(interComm, cfgDev, cfgFactor, regList, channel, cmdTime);
        this.setParam("FactorCode", 21022);
        this.setParam("FactorUnit", 1);
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
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
            if (bRecv != null) {
                if (bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 4) {
                    float testResult = Utility.getFloatLittleEndian(bRecv, 3);
                    this.setParamTestVal((double)testResult, "N", (Timestamp)null);
                    this.setStatus(true);
                    Utility.logInfo("BLPowerMonNh3n Dev: 查询成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("BLPowerMonNh3n Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else if (MonitorService.autoData) {
                double dTmp = this.rand.nextDouble() * 10.0D;
                this.setParamTestVal(dTmp, "N", (Timestamp)null);
                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("BLPowerMonNh3n Dev: 查询失败，通道" + this.mChannel + "，无返回");
            }

            bQuery = new byte[]{(byte)this.mAddress, 3, 0, 1, 0, 1, 0, 0};
            crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
            tmp_bytes = Utility.getByteArray(crc_result);
            bQuery[bQuery.length - 2] = tmp_bytes[2];
            bQuery[bQuery.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
            if (bRecv != null && bRecv.length == 7 && bRecv[0] == this.mAddress && bRecv[1] == 3) {
                int i;
                for(i = 0; i < this.warnSet1.length; ++i) {
                    if (Utility.getBit(bRecv, 3, i)) {
                        this.addWarn(i, this.warnSet1[i]);
                    } else {
                        this.warnMap.remove(i);
                    }
                }

                for(i = 0; i < this.warnSet2.length; ++i) {
                    if (Utility.getBit(bRecv, 4, i)) {
                        this.addWarn(10 + i, this.warnSet2[i]);
                    } else {
                        this.warnMap.remove(10 + i);
                    }
                }

                if (this.warnMap.size() > 0) {
                    this.setParam("DevAlarm", 200);
                }
            }

        }
    }

    public void saveResult() {
    }

    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        int iResult = 1;
        this.initDevState(cmdType);
        if (MonitorService.autoData) {
            return true;
        } else {
            this.mCmdType = cmdType;
            switch(cmdType) {
                case 8:
                    return false;
                case 9:
                    return false;
                case 10:
                    return false;
                case 11:
                    return false;
                case 12:
                    return false;
                case 13:
                    return false;
                default:
                    byte[] bCmd = new byte[]{(byte)this.mAddress, 5, 0, 0, -1, 0, 0, 0};
                    this.onCmd = true;
                    int crc_result = Utility.calcCrc16(bCmd, 0, bCmd.length - 2);
                    byte[] tmp_bytes = Utility.getByteArray(crc_result);
                    bCmd[bCmd.length - 2] = tmp_bytes[2];
                    bCmd[bCmd.length - 1] = tmp_bytes[3];

                    for(int iTimes = this.mCmdTimes; iTimes > 0; --iTimes) {
                        this.mInterComm.clearRecv(2 * this.mChannel + 1);
                        this.mInterComm.Send(bCmd);
                        byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel + 1, false, 2000);
                        iResult = this.getCmdResult("BLPowerMonNh3n", bCmd, bRecv);
                        if (iResult == 1) {
                            break;
                        }
                    }

                    if (cmdQN != null && this.devExeListener != null) {
                        this.devExeListener.OnExeEvent(cmdQN, cmdType, iResult);
                    }

                    try {
                        Thread.sleep(1000L);
                    } catch (Exception var10) {
                    }

                    bCmd = new byte[]{(byte)this.mAddress, 5, 0, 0, 0, 0, 0, 0};
                    crc_result = Utility.calcCrc16(bCmd, 0, bCmd.length - 2);
                    tmp_bytes = Utility.getByteArray(crc_result);
                    bCmd[bCmd.length - 2] = tmp_bytes[2];
                    bCmd[bCmd.length - 1] = tmp_bytes[3];
                    this.mInterComm.clearRecv(2 * this.mChannel + 1);
                    this.mInterComm.Send(bCmd);
                    this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel + 1, false, 2000);
                    this.onCmd = false;
                    return iResult == 1;
            }
        }
    }

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                DevClBLPowerMon.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
        this.doDevCmdThread(11, (String)null, (String)null);
    }

    public void doRsetCmd() {
        this.doDevCmdThread(12, (String)null, (String)null);
    }

    public void doSetTime(String strQN, String strParam) {
        this.doDevCmdThread(13, strParam, strQN);
    }
}
