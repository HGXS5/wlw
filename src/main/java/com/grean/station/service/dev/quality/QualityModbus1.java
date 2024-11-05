//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.quality;

import com.grean.station.domain.DO.cfg.CfgDevReg;
import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.util.Random;

public class QualityModbus1 extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public QualityModbus1() {
    }
    @SuppressWarnings("FallThrough")
    void getQuery(int offsetHigh, int offsetLow, int lengthHigh, int lengthLow, String queryTag) {
        if (!this.onCmd) {
            byte[] bQuery = new byte[]{(byte)this.mAddress, 4, (byte)offsetHigh, (byte)offsetLow, (byte)lengthHigh, (byte)lengthLow, 0, 0};
            int crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            bQuery[bQuery.length - 2] = tmp_bytes[2];
            bQuery[bQuery.length - 1] = tmp_bytes[3];
            int regOffset = 256 * offsetHigh + offsetLow;
            int regLength = 256 * lengthHigh + lengthLow;
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 1000);
            int regIndex;
            CfgDevReg tmpReg;
            int i;
            String var16;
            byte var17;
            if (bRecv != null) {
                if (bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 4) {
                    for(i = 0; i < this.mRegList.size(); ++i) {
                        tmpReg = (CfgDevReg)this.mRegList.get(i);
                        regIndex = tmpReg.getOffset() - regOffset;
                        int byteIndex = regIndex * 2 + 3;
                        if (regIndex >= 0 && regIndex + tmpReg.getLength() <= regLength) {
                            var16 = tmpReg.getType().toLowerCase();
                            var17 = -1;
                            switch(var16.hashCode()) {
                                case 3655434:
                                    if (var16.equals("word")) {
                                        var17 = 0;
                                    }
                                default:
                                    switch(var17) {
                                        case 0:
                                            this.params[i] = Utility.getShort(bRecv, byteIndex);
                                    }
                            }
                        }
                    }

                    this.setStatus(true);
                    Utility.logInfo("Modbus Quality1: 查询" + queryTag + "成功");
                } else {
                    this.setStatus(false);
                    Utility.logInfo("Modbus Quality1: 查询" + queryTag + "失败");
                }
            } else if (MonitorService.autoData) {
                this.setStatus(true);

                for(i = 0; i < this.mRegList.size(); ++i) {
                    tmpReg = (CfgDevReg)this.mRegList.get(i);
                    regIndex = tmpReg.getOffset() - regOffset;
                    if (regIndex >= 0 && regIndex + tmpReg.getLength() <= regLength) {
                        var16 = tmpReg.getType().toLowerCase();
                        var17 = -1;
                        switch(var16.hashCode()) {
                            case 3655434:
                                if (var16.equals("word")) {
                                    var17 = 0;
                                }
                            default:
                                switch(var17) {
                                    case 0:
                                        this.params[i] = this.rand.nextInt(2);
                                }
                        }
                    }
                }
            } else {
                this.setStatus(false);
                Utility.logInfo("Modbus Quality1: 查询" + queryTag + "失败");
            }

        }
    }

    public void doQuery() {
        try {
            int iWait = this.mAddress % 5;
            Thread.sleep((long)(iWait * 2000));
        } catch (Exception var3) {
        }

        this.getQuery(48, 0, 0, 23, this.mCfgDev.getName() + "状态区");

        try {
            Thread.sleep(500L);
        } catch (Exception var2) {
        }

        this.getQuery(64, 0, 0, 8, this.mCfgDev.getName() + "关键参数区");
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
            int iState;
            switch(cmdType) {
                case 1:
                case 5001:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 80, 0, 0, 1, 0, 0};
                    break;
                case 2:
                case 5002:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 80, 0, 0, 2, 0, 0};
                    break;
                case 3:
                case 5007:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 80, 0, 0, 7, 0, 0};
                    break;
                case 4:
                case 5008:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 80, 0, 0, 8, 0, 0};
                    break;
                case 5:
                case 5009:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 80, 0, 0, 9, 0, 0};
                    break;
                case 7:
                case 5003:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 80, 0, 0, 3, 0, 0};
                    break;
                case 11:
                case 5000:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 80, 0, 0, 0, 0, 0};
                    break;
                case 4000:
                case 4001:
                case 4002:
                case 4003:
                case 4004:
                case 4005:
                case 4006:
                case 4007:
                    if (cmdParam != null) {
                        crc_result = cmdType - 4000;
                        iState = Integer.parseInt(cmdParam);
                        byte[] tmp_bytes = Utility.getByteArray(iState);
                        bCmd = new byte[]{(byte)this.mAddress, 6, 64, (byte)crc_result, tmp_bytes[2], tmp_bytes[3], 0, 0};
                    } else {
                        bCmd = new byte[]{0, 0};
                    }
                    break;
                case 5004:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 80, 0, 0, 4, 0, 0};
                    break;
                case 5005:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 80, 0, 0, 5, 0, 0};
                    break;
                case 5006:
                    bCmd = new byte[]{(byte)this.mAddress, 6, 80, 0, 0, 6, 0, 0};
                    break;
                case 6001:
                case 6002:
                case 6003:
                case 6004:
                case 6005:
                case 6006:
                case 6007:
                case 6008:
                case 6009:
                case 6010:
                case 6011:
                case 6012:
                case 6013:
                case 6014:
                case 6015:
                case 6016:
                    if (cmdParam != null) {
                        crc_result = cmdType - 5999;
                        iState = Integer.parseInt(cmdParam);
                        bCmd = new byte[]{(byte)this.mAddress, 6, 80, (byte)crc_result, 0, (byte)iState, 0, 0};
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
                    byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel + 1, false, 3000);
                    iResult = this.getCmdResult("QualityModbus1", bCmd, bRecv);
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
