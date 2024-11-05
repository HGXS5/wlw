//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.five;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Random;

public class DevFiveHach extends DevService {
    private Random rand = new Random();

    public DevFiveHach() {
    }

    public void doQuery() {
        int regLength = 14;
        this.mAddress = this.mCfgDev.getAddress().intValue();
        byte[] bQuery = new byte[]{(byte)this.mAddress, 3, 0, 0, 0, 14, 0, 0};
        int crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
        byte[] tmp_bytes = Utility.getByteArray(crc_result);
        bQuery[bQuery.length - 2] = tmp_bytes[2];
        bQuery[bQuery.length - 1] = tmp_bytes[3];
        this.mInterComm.clearRecv(2 * this.mChannel);
        this.mInterComm.Send(bQuery);
        byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 1000);
        double dTmp;
        int iIndex;
        if (bRecv != null) {
            if (bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 3) {
                if (this.mCfgFactor.getDevAddress() > 0) {
                    iIndex = this.mCfgFactor.getDevAddress() * 4 - 1;
                } else {
                    String var9 = this.mCfgFactor.getName();
                    byte var10 = -1;
                    switch(var9.hashCode()) {
                        case 3544:
                            if (var9.equals("pH")) {
                                var10 = 0;
                            }
                            break;
                        case 78541:
                            if (var9.equals("ORP")) {
                                var10 = 5;
                            }
                            break;
                        case 886901:
                            if (var9.equals("水温")) {
                                var10 = 1;
                            }
                            break;
                        case 891548:
                            if (var9.equals("浊度")) {
                                var10 = 4;
                            }
                            break;
                        case 28358618:
                            if (var9.equals("溶解氧")) {
                                var10 = 2;
                            }
                            break;
                        case 29594368:
                            if (var9.equals("电导率")) {
                                var10 = 3;
                            }
                    }

                    switch(var10) {
                        case 0:
                            iIndex = 7;
                            break;
                        case 1:
                            iIndex = 11;
                            break;
                        case 2:
                            iIndex = 3;
                            break;
                        case 3:
                            iIndex = 15;
                            break;
                        case 4:
                            iIndex = 19;
                            break;
                        case 5:
                            iIndex = 23;
                            break;
                        default:
                            iIndex = 0;
                    }
                }

                if (iIndex > 0 && iIndex < bRecv.length - 3) {
                    dTmp = (double)Utility.getFloatBigEndian(bRecv, iIndex);
                    this.setParamTestValAll(dTmp, "N", (Timestamp)null);
                }

                this.setStatus(true);
                Utility.logInfo("HachFive Dev: 查询成功，通道" + this.mChannel);
            } else {
                this.setStatus(false);
                Utility.logInfo("HachFive Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
            }
        } else if (MonitorService.autoData) {
            dTmp = this.rand.nextDouble() * 10.0D;
            this.setParamTestValAll(dTmp, "N", (Timestamp)null);

            for(iIndex = 0; iIndex < 8; ++iIndex) {
                this.channelVals[iIndex] = dTmp + (double)iIndex * 0.5D;
            }

            this.setStatus(true);
        } else {
            this.setStatus(false);
            Utility.logInfo("HachFive Dev: 查询失败，通道" + this.mChannel + "，无返回");
        }

    }

    public void saveResult() {
    }

    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        return false;
    }

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                DevFiveHach.this.doDevCmd(cmdType, cmdParam, cmdQN);
            }
        });
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
