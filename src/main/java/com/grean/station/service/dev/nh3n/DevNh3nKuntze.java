//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.nh3n;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Random;

public class DevNh3nKuntze extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevNh3nKuntze() {
    }

    private byte[] getRecv(int waitTime, int recvChannel, boolean bQuery) {
        int bLen = 0;
        byte[] bRecv = new byte[64];
        long tStart = System.currentTimeMillis();

        while(true) {
            long tNow = System.currentTimeMillis();
            if (tNow - tStart > (long)waitTime) {
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
                if (bLen >= 5) {
                    break;
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var12) {
                }
            }
        }

        if (bLen == 5 && bRecv[4] == 13) {
            byte[] bResult = new byte[bLen];
            System.arraycopy(bRecv, 0, bResult, 0, bLen);
            return bResult;
        } else {
            return null;
        }
    }

    public void doQuery() {
        if (!this.onCmd) {
            byte[] bQueryAddress = new byte[]{10, 48, 49, 13};
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQueryAddress);

            try {
                Thread.sleep(1000L);
            } catch (Exception var8) {
            }

            if (!this.onCmd) {
                byte[] bQuery = new byte[]{36, 48, 48, 13};
                this.mInterComm.clearRecv(2 * this.mChannel);
                this.mInterComm.Send(bQuery);
                byte[] bRecv = this.getRecv(2000, 2 * this.mChannel, true);
                double dTmp;
                if (bRecv == null) {
                    if (MonitorService.autoData) {
                        dTmp = this.rand.nextDouble() * 10.0D;
                        this.setParamTestVal(dTmp, "N", (Timestamp)null);
                        this.setStatus(true);
                    } else {
                        this.setStatus(false);
                        Utility.logInfo("KuntzeNh3n Dev: 查询失败，通道" + this.mChannel + "，无返回");
                    }
                } else {
                    int iTmp = 0;
                    int i = 0;

                    while(true) {
                        if (i >= 4) {
                            dTmp = (double)iTmp / 100.0D;
                            this.setParamTestVal(dTmp, "N", (Timestamp)null);
                            this.setStatus(true);
                            Utility.logInfo("KuntzeNh3n Dev: 查询成功，通道" + this.mChannel);
                            break;
                        }

                        if (bRecv[i] < 48 || bRecv[i] > 63) {
                            this.setStatus(false);
                            Utility.logInfo("KuntzeNh3n Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
                            return;
                        }

                        iTmp = iTmp * 16 + (bRecv[i] - 48);
                        ++i;
                    }
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
            switch(this.mCmdType) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                default:
                    byte[] bCmd = new byte[]{67, 48, 48, 49, 49, 13};
                    this.onCmd = true;
                    byte[] bQueryAddress = new byte[]{10, 48, 49, 13};
                    this.mInterComm.clearRecv(2 * this.mChannel + 1);
                    this.mInterComm.Send(bQueryAddress);

                    try {
                        Thread.sleep(1000L);
                    } catch (Exception var11) {
                    }

                    byte[] bQueryState = new byte[]{36, 49, 63, 13};
                    this.mInterComm.clearRecv(2 * this.mChannel + 1);
                    this.mInterComm.Send(bQueryState);

                    try {
                        Thread.sleep(1000L);
                    } catch (Exception var10) {
                    }

                    for(int iTimes = this.mCmdTimes; iTimes > 0; --iTimes) {
                        iResult = 1;
                        this.mInterComm.clearRecv(2 * this.mChannel + 1);
                        this.mInterComm.Send(bCmd);
                        byte[] bRecv = this.getRecv(2000, 2 * this.mChannel + 1, false);
                        if (bRecv != null) {
                            if (bRecv.length != 5 || bRecv[0] != 48 || bRecv[1] != 48 || bRecv[2] != 49 || bRecv[3] != 49 || bRecv[4] != 13) {
                                iResult = 2;
                            }
                        } else {
                            iResult = 4;
                        }

                        if (iResult == 1) {
                            break;
                        }
                    }

                    if (cmdQN != null && this.devExeListener != null) {
                        this.devExeListener.OnExeEvent(cmdQN, cmdType, iResult);
                    }

                    this.onCmd = false;
                    this.saveCmdResult("KuntzeNh3n", iResult);
                    return iResult == 1;
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                    return false;
            }
        }
    }

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                DevNh3nKuntze.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
