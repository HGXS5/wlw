//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev;

import com.grean.station.service.MonitorService;
import com.grean.station.utils.Utility;
import java.util.Random;

public class DevSysteaNc extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevSysteaNc() {
    }

    private byte[] getRecv(int waitTime, int recvChannel, boolean bQuery) {
        int bLen = 0;
        byte[] bRecv = new byte[256];
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
                if (bLen >= 110 && bRecv[bLen - 2] == 13 && bRecv[bLen - 1] == 10) {
                    break;
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var12) {
                }
            }
        }

        if (bLen >= 110 && bRecv[bLen - 2] == 13 && bRecv[bLen - 1] == 10) {
            byte[] bResult = new byte[bLen];
            System.arraycopy(bRecv, 0, bResult, 0, bLen);
            return bResult;
        } else {
            return null;
        }
    }

    public void doQuery() {
        if (!this.onCmd) {
            this.mAddress = this.mCfgDev.getAddress().intValue();
            byte[] bQuery = new byte[]{35, 64, 68, 48, 13, 10};
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            double dTmp = 0.0D;
            byte[] bRecv = this.getRecv(this.waitAnswer, 2 * this.mChannel, true);
            if (bRecv != null) {
                if (bRecv.length >= 110 && bRecv[bRecv.length - 2] == 13 && bRecv[bRecv.length - 1] == 10) {
                    String strTmp = new String(bRecv);
                    strTmp = strTmp.replace("\r\n", ",");
                    strTmp = strTmp.trim();
                    String[] strArray = strTmp.split(",");

                    for(int i = 0; i < 12 && i < strArray.length - 2; ++i) {
                        dTmp = (double)Float.parseFloat(strArray[i + 2]);
                        this.channelVals[i] = dTmp;
                    }

                    this.setStatus(true);
                    Utility.logInfo("SysteaNc Dev: 查询成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("Systea Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else if (MonitorService.autoData) {
                dTmp = this.rand.nextDouble() * 10.0D;

                for(int i = 0; i < 8; ++i) {
                    this.channelVals[i] = dTmp + (double)i * 0.5D;
                }

                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("SysteaNc Dev: 查询失败，通道" + this.mChannel + "，无返回");
            }

        }
    }

    public void saveResult() {
    }
    @SuppressWarnings("FallThrough")
    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        int iResult = 2;
        this.initDevState(cmdType);
        if (MonitorService.autoData) {
            return true;
        } else {
            this.onCmd = true;
            this.mCmdType = cmdType;
            byte[] bCmd;
            switch(cmdType) {
                case 8:
                case 9:
                    return false;
                case 10:
                    return false;
                case 11:
                    bCmd = new byte[]{35, 88, 13, 10};
                    break;
                case 12:
                    return false;
                case 13:
                    return false;
                default:
                    bCmd = new byte[]{35, 67, 13, 10};
            }

            this.mInterComm.clearRecv(2 * this.mChannel + 1);
            this.mInterComm.Send(bCmd);
            byte[] bRecv = this.getRecv(this.waitAnswer, 2 * this.mChannel + 1, false);
            if (bRecv != null) {
                if (bRecv[bRecv.length - 2] == 13 && bRecv[bRecv.length - 1] == 10) {
                    iResult = 1;
                }
            } else {
                iResult = 4;
            }

            if (cmdQN != null && this.devExeListener != null) {
                this.devExeListener.OnExeEvent(cmdQN, cmdType, iResult);
            }

            this.onCmd = false;
            this.saveCmdResult("SysteaNc", iResult);
            return iResult == 1;
        }
    }

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                DevSysteaNc.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
        this.doDevCmdThread(10, (String)null, (String)null);
    }

    public void doStopCmd() {
        this.initDevState(0);
        this.doDevCmdThread(11, (String)null, (String)null);
    }

    public void doRsetCmd() {
    }

    public void doSetTime(String strQN, String strParam) {
    }
}
