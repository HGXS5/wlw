//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.cod;

import com.grean.station.comm.CommInter;
import com.grean.station.domain.DO.cfg.CfgDev;
import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import org.joda.time.DateTime;

public class DevCodGreen extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevCodGreen() {
    }

    public void init(CommInter interComm, CfgDev cfgDev, CfgFactor cfgFactor, List regList, int channel, int cmdTime) {
        super.init(interComm, cfgDev, cfgFactor, regList, channel, cmdTime);
        this.setParam("FactorCode", 1018);
        this.setParam("FactorUnit", 1);
    }

    public void doQuery() {
        if (!this.onCmd) {
            int regLength = 8;
            this.mAddress = this.mCfgDev.getAddress().intValue();
            byte[] bQuery = new byte[]{(byte)this.mAddress, 3, 0, 1, 0, 8, 0, 0};
            int crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            bQuery[bQuery.length - 2] = tmp_bytes[2];
            bQuery[bQuery.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
            if (bRecv != null) {
                if (bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 3) {
                    float testResult = Utility.getFloatBigEndian(bRecv, 3);
                    float testAbs = Utility.getFloatBigEndian(bRecv, 15);
                    this.setParamTestVal((double)testResult, "N", (Timestamp)null);
                    this.setStatus(true);
                    Utility.logInfo("GreenCod Dev: 查询成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("GreenCod Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else if (MonitorService.autoData) {
                double dTmp = this.rand.nextDouble() * 10.0D;
                this.setParamTestVal(dTmp, "N", (Timestamp)null);
                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("GreenCod Dev: 查询失败，通道" + this.mChannel + "，无返回");
            }

        }
    }

    public void saveResult() {
    }

    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        this.initDevState(cmdType);
        if (MonitorService.autoData) {
            return true;
        } else {
            this.mCmdType = cmdType;
            byte[] bCmd;
            int iYear;
            switch(cmdType) {
                case 3:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 0, 57, 0, 3, 0, 0};
                    break;
                case 4:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 0, 57, 0, 4, 0, 0};
                    break;
                case 5:
                case 6:
                case 7:
                default:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 0, 57, 0, 1, 0, 0};
                    break;
                case 8:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 0, 57, 0, 2, 0, 0};
                    break;
                case 9:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 0, 57, 0, 2, 0, 0};
                    break;
                case 10:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 0, 57, 0, 7, 0, 0};
                    break;
                case 11:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 0, 57, 0, 6, 0, 0};
                    break;
                case 12:
                    return false;
                case 13:
                    bCmd = new byte[]{(byte)this.mAddress, 16, 0, 70, 0, 6, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                    int iMon;
                    int iDay;
                    int iHour;
                    int iMin;
                    int iSec;
                    if (cmdParam != null && cmdParam.length() == 14) {
                        iYear = Integer.parseInt(cmdParam.substring(2, 4));
                        iMon = Integer.parseInt(cmdParam.substring(4, 6));
                        iDay = Integer.parseInt(cmdParam.substring(6, 8));
                        iHour = Integer.parseInt(cmdParam.substring(8, 10));
                        iMin = Integer.parseInt(cmdParam.substring(10, 12));
                        iSec = Integer.parseInt(cmdParam.substring(12, 14));
                    } else {
                        DateTime dateTime = new DateTime();
                        iYear = dateTime.getYear() % 100;
                        iMon = dateTime.getMonthOfYear();
                        iDay = dateTime.getDayOfMonth();
                        iHour = dateTime.getHourOfDay();
                        iMin = dateTime.getMinuteOfHour();
                        iSec = dateTime.getSecondOfMinute();
                    }

                    bCmd[7] = 32;
                    bCmd[8] = (byte)iYear;
                    bCmd[10] = (byte)iMon;
                    bCmd[12] = (byte)iDay;
                    bCmd[14] = (byte)iHour;
                    bCmd[16] = (byte)iMin;
                    bCmd[18] = (byte)iSec;
            }

            this.onCmd = true;
            iYear = Utility.calcCrc16(bCmd, 0, bCmd.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(iYear);
            bCmd[bCmd.length - 2] = tmp_bytes[2];
            bCmd[bCmd.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel + 1);
            this.mInterComm.Send(bCmd);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel + 1, false, 2000);
            int iResult = this.getCmdResult("GreenCod", bCmd, bRecv);
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
                DevCodGreen.this.doDevCmd(cmdType, cmdParam, cmdQN);
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