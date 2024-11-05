//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.heavy;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Random;

public class DevHeavyOva7000Old extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevHeavyOva7000Old() {
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
    @SuppressWarnings("FallThrough")
    public void doQuery() {
        if (!this.onCmd) {
            int regLength = 16;
            this.mAddress = this.mCfgDev.getAddress().intValue();
            byte[] bQuery = new byte[]{3, 0, 0, 0, 0, 6, (byte)this.mAddress, 3, 0, 1, 0, 16};
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(this.waitAnswer, 2 * this.mChannel, true);
            double dTmp;
            int iIndex;
            if (bRecv != null) {
                if (bRecv.length == regLength * 2 + 9 && bRecv[6] == this.mAddress && bRecv[7] == 3) {
                    if (this.mCfgFactor.getDevAddress() > 0) {
                        iIndex = this.mCfgFactor.getDevAddress() * 2 + 7;
                    } else {
                        String var7 = this.mCfgFactor.getName();
                        byte var8 = -1;
                        switch(var7.hashCode()) {
                            case 30775:
                                if (var7.equals("砷")) {
                                    var8 = 0;
                                }
                                break;
                            case 38085:
                                if (var7.equals("铅")) {
                                    var8 = 2;
                                }
                                break;
                            case 38108:
                                if (var7.equals("铜")) {
                                    var8 = 3;
                                }
                                break;
                            case 38192:
                                if (var7.equals("锰")) {
                                    var8 = 4;
                                }
                                break;
                            case 38217:
                                if (var7.equals("镉")) {
                                    var8 = 1;
                                }
                                break;
                            case 38221:
                                if (var7.equals("镍")) {
                                    var8 = 5;
                                }
                        }

                        switch(var8) {
                            case 0:
                                iIndex = 9;
                                break;
                            case 1:
                                iIndex = 11;
                                break;
                            case 2:
                                iIndex = 13;
                                break;
                            case 3:
                                iIndex = 15;
                                break;
                            case 4:
                                iIndex = 17;
                                break;
                            case 5:
                                iIndex = 19;
                                break;
                            default:
                                iIndex = 0;
                        }
                    }

                    if (iIndex > 0 && iIndex < bRecv.length - 1) {
                        dTmp = (double)((float)Utility.getShort(bRecv, iIndex) / 100000.0F);
                        this.setParamTestValAll(dTmp, "N", (Timestamp)null);
                    }

                    this.setStatus(true);
                    Utility.logInfo("Ova7000HeavyOld Dev: 查询成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("Ova7000HeavyOld Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
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
                Utility.logInfo("Ova7000HeavyOld Dev: 查询失败，通道" + this.mChannel + "，无返回");
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
                    byte[] bCmd = new byte[]{5, 0, 0, 0, 0, 6, (byte)this.mAddress, 5, 0, 1, -1, 0};
                    this.onCmd = true;
                    this.mInterComm.clearRecv(2 * this.mChannel + 1);
                    this.mInterComm.Send(bCmd);
                    byte[] bRecv = this.getRecv(this.waitAnswer, 2 * this.mChannel + 1, false);
                    int iResult = this.getCmdResultTcp("Ova7000HeavyOld", bCmd, bRecv);
                    if (cmdQN != null && this.devExeListener != null) {
                        this.devExeListener.OnExeEvent(cmdQN, cmdType, iResult);
                    }

                    this.onCmd = false;
                    return iResult == 1;
            }
        }
    }

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                DevHeavyOva7000Old.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
