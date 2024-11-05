//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.chlorophyll;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.util.Random;

public class DevChlorophyllBbe1 extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevChlorophyllBbe1() {
    }

    private byte[] getRecv(int waitTime, int recvChannel, boolean bQuery) {
        int bLen = 0;
        byte[] bRecv = new byte[2000];
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
                if (bLen > 32 && bRecv[0] == 80 && bRecv[bLen - 2] == 13 && bRecv[bLen - 1] == 10) {
                    break;
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var12) {
                }
            }
        }

        if (bLen > 32 && bRecv[0] == 80 && bRecv[bLen - 2] == 13 && bRecv[bLen - 1] == 10) {
            byte[] bResult = new byte[bLen];
            System.arraycopy(bRecv, 0, bResult, 0, bLen);
            return bResult;
        } else {
            return null;
        }
    }

    public void doQuery() {
        if (!this.onCmd) {
            byte[] bQuery = new byte[]{100, 13};
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(this.waitAnswer, 2 * this.mChannel, true);
            if (bRecv != null) {
                String strTmp = new String(bRecv);
                String[] strarray = strTmp.split("\t|\b");

                for(int i = 0; i < strarray.length - 5 && i < 16; ++i) {
                    try {
                        this.channelVals[i] = Double.parseDouble(strarray[i + 5]);
                    } catch (Exception var11) {
                    }
                }

                this.setStatus(true);
                Utility.logInfo("BBEChlorophyll Dev: 查询成功，通道" + this.mChannel);
            } else if (MonitorService.autoData) {
                for(int i = 0; i < 16; ++i) {
                    try {
                        this.channelVals[i] = (double)i + 0.1D;
                    } catch (Exception var10) {
                    }
                }

                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("BBEChlorophyll Dev: 查询失败，通道" + this.mChannel + "，无返回");
            }

        }
    }

    public void saveResult() {
    }
    @SuppressWarnings("FallThrough")
    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        byte[] bStopCmd = new byte[]{112, 13};
        this.initDevState(cmdType);
        if (MonitorService.autoData) {
            return true;
        } else {
            this.mCmdType = cmdType;
            byte[] bCmd;
            switch(cmdType) {
                case 8:
                    return false;
                case 9:
                    return false;
                case 10:
                    return false;
                case 11:
                    bCmd = new byte[]{112, 13};
                    break;
                case 12:
                    return false;
                case 13:
                    return false;
                default:
                    bCmd = new byte[]{115, 13};
            }

            this.onCmd = true;

            try {
                this.mInterComm.Send(bStopCmd);

                for(int i = 0; i < 60; ++i) {
                    Thread.sleep(1000L);
                }

                this.mInterComm.clearRecv(2 * this.mChannel + 1);
                this.mInterComm.Send(bCmd);
            } catch (Exception var8) {
            }

            int iResult = 1;
            Utility.logInfo("BBEChlorophyll Dev: " + this.mCfgDev.getName() + Utility.getTaskName(this.mCmdType) + "成功");
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
                DevChlorophyllBbe1.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
