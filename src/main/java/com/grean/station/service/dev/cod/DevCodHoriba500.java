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

public class DevCodHoriba500 extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevCodHoriba500() {
    }
    @SuppressWarnings("FallThrough")
    public void doQuery() {
        if (!this.onCmd) {
            int regLength = 19;
            this.mAddress = this.mCfgDev.getAddress().intValue();
            byte[] bQuery = new byte[]{(byte)this.mAddress, 4, 2, 4, 0, 19, 0, 0};
            int crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            bQuery[bQuery.length - 2] = tmp_bytes[2];
            bQuery[bQuery.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
            if (bRecv != null) {
                if (bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 4) {
                    float testResult = (float)(bRecv[15] * 256 + (bRecv[16] & 255)) / 100.0F;
                    this.setParamTestVal((double)testResult, "N", (Timestamp)null);
                    this.setStatus(true);
                    Utility.logInfo("HoribaCod500 Dev: 查询成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("HoribaCod500 Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else if (MonitorService.autoData) {
                double dTmp = this.rand.nextDouble() * 10.0D;
                this.setParamTestVal(dTmp, "N", (Timestamp)null);
                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("HoribaCod500 Dev: 查询失败，通道" + this.mChannel + "，无返回");
            }

            regLength = 21;
            bQuery = new byte[]{(byte)this.mAddress, 4, 1, 0, 0, 21, 0, 0};
            crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
            tmp_bytes = Utility.getByteArray(crc_result);
            bQuery[bQuery.length - 2] = tmp_bytes[2];
            bQuery[bQuery.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
            if (bRecv != null && bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 4) {
                switch(bRecv[12]) {
                    case 10:
                        this.addWarn(1, "泄露");
                        break;
                    case 15:
                        this.addWarn(1, "空气压力");
                        break;
                    case 29:
                        this.addWarn(1, "试剂A不足");
                        break;
                    case 30:
                        this.addWarn(1, "试剂B不足");
                        break;
                    case 31:
                        this.addWarn(1, "试剂C不足");
                        break;
                    default:
                        this.warnMap.remove(1);
                }

                switch(bRecv[14]) {
                    case 32:
                        this.addWarn(2, "试剂D不足");
                        break;
                    case 33:
                        this.addWarn(2, "试剂E不足");
                        break;
                    case 34:
                        this.addWarn(2, "废物槽装满");
                        break;
                    default:
                        this.warnMap.remove(2);
                }

                if (this.warnMap.size() > 0) {
                    this.setParam("DevAlarm", 200);
                }
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
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                default:
                    bCmd = new byte[]{(byte)this.mAddress, 16, 23, 0, 0, 2, 4, 0, -126, 0, 0, 0, 0};
                    break;
                case 8:
                case 9:
                    bCmd = new byte[]{(byte)this.mAddress, 16, 23, 0, 0, 2, 4, 0, 4, 0, 0, 0, 0};
                    break;
                case 10:
                    bCmd = new byte[]{(byte)this.mAddress, 16, 23, 0, 0, 2, 4, 0, 8, 0, 0, 0, 0};
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
            this.mInterComm.clearRecv(2 * this.mChannel + 1);
            this.mInterComm.Send(bCmd);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel + 1, false, 2000);
            int iResult = this.getCmdResult("HoribaCod500", bCmd, bRecv);
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
                DevCodHoriba500.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
