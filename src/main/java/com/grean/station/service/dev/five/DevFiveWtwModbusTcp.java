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

public class DevFiveWtwModbusTcp extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevFiveWtwModbusTcp() {
    }

    private byte[] getRecv(int waitTime, int recvChannel, boolean bQuery) {
        int bLen = 0;
        byte[] bRecv = new byte[1024];
        long tStart = System.currentTimeMillis();

        while(true) {
            long tNow = System.currentTimeMillis();
            if (tNow - tStart > (long)waitTime) {
                bLen = 0;
                break;
            }

            byte[] tmpRecv = this.mInterComm.Recv(recvChannel);
            if (tmpRecv == null) {
                try {
                    Thread.sleep(50L);
                } catch (InterruptedException var13) {
                }
            } else {
                System.arraycopy(tmpRecv, 0, bRecv, bLen, tmpRecv.length);
                bLen += tmpRecv.length;
                if (bLen > 10 && bRecv[6] == (byte)this.mAddress) {
                    if (bQuery) {
                        if (bRecv[7] == 3 || bRecv[7] == 4) {
                            break;
                        }

                        bLen = 0;
                    } else {
                        if (bRecv[7] == 5 || bRecv[7] == 6 || bRecv[7] == 15 || bRecv[7] == 16) {
                            break;
                        }

                        bLen = 0;
                    }
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var12) {
                }
            }
        }

        if (bLen > 0) {
            byte[] bResult = new byte[bLen];
            System.arraycopy(bRecv, 0, bResult, 0, bLen);
            return bResult;
        } else {
            return null;
        }
    }

    public void doQuery() {
        if (!this.onCmd) {
            int regLength = 40;
            this.mAddress = this.mCfgDev.getAddress().intValue();
            byte[] bQuery = new byte[]{4, 0, 0, 0, 0, 6, (byte)this.mAddress, 3, 0, 0, 0, 40};
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(this.waitAnswer, 2 * this.mChannel, true);
            double dTmp;
            int iIndex;
            if (bRecv != null) {
                if (bRecv.length == regLength * 2 + 9 && bRecv[6] == this.mAddress && bRecv[7] == 3) {
                    if (this.mCfgFactor.getDevAddress() > 0) {
                        iIndex = this.mCfgFactor.getDevAddress() * 4 + 5;
                    } else {
                        String var7 = this.mCfgFactor.getName();
                        byte var8 = -1;
                        switch(var7.hashCode()) {
                            case 3544:
                                if (var7.equals("pH")) {
                                    var8 = 0;
                                }
                                break;
                            case 886901:
                                if (var7.equals("水温")) {
                                    var8 = 1;
                                }
                                break;
                            case 891548:
                                if (var7.equals("浊度")) {
                                    var8 = 4;
                                }
                                break;
                            case 28358618:
                                if (var7.equals("溶解氧")) {
                                    var8 = 2;
                                }
                                break;
                            case 29594368:
                                if (var7.equals("电导率")) {
                                    var8 = 3;
                                }
                        }

                        switch(var8) {
                            case 0:
                                iIndex = 33;
                                break;
                            case 1:
                                iIndex = 21;
                                break;
                            case 2:
                                iIndex = 49;
                                break;
                            case 3:
                                iIndex = 17;
                                break;
                            case 4:
                                iIndex = 65;
                                break;
                            default:
                                iIndex = 6;
                        }
                    }

                    if (iIndex > 0 && iIndex < bRecv.length - 1) {
                        dTmp = (double)Utility.getFloatBigEndian(bRecv, iIndex);
                        if (this.mCfgFactor.getName().equals("电导率")) {
                            dTmp *= 1000.0D;
                        }

                        this.setParamTestValAll(dTmp, "N", (Timestamp)null);
                    }

                    this.setStatus(true);
                    Utility.logInfo("WtwFiveModbus Dev: 查询成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("WtwFiveModbus Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else if (MonitorService.autoData) {
                dTmp = this.rand.nextDouble() * this.mCfgFactor.getAlarmMax();
                this.setParamTestValAll(dTmp, "N", (Timestamp)null);

                for(iIndex = 0; iIndex < 8; ++iIndex) {
                    this.channelVals[iIndex] = dTmp + (double)iIndex * 0.5D;
                }

                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("WtwFiveModbus Dev: 查询失败，通道" + this.mChannel + "，无返回");
            }

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
                DevFiveWtwModbusTcp.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
        this.doDevCmd(8, strParam, strQN);
    }

    public void doStdCal(String strQN, String strParam) {
        this.doDevCmd(9, strParam, strQN);
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
