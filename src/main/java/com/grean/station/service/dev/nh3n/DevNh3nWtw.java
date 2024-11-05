//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.nh3n;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.service.script.ScriptWord;
import com.grean.station.utils.TimeHelper;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Random;

public class DevNh3nWtw extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevNh3nWtw() {
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
                if (bLen > 4 && bRecv[0] == 13 && bRecv[1] == 10 && bRecv[bLen - 2] == 13 && bRecv[bLen - 1] == 10 || bLen >= 14 && (bRecv[0] & 255) == 161) {
                    break;
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var12) {
                }
            }
        }

        if ((bLen <= 4 || bRecv[0] != 13 || bRecv[1] != 10 || bRecv[bLen - 2] != 13 || bRecv[bLen - 1] != 10) && (bLen < 14 || (bRecv[0] & 255) != 161)) {
            return null;
        } else {
            byte[] bResult = new byte[bLen];
            System.arraycopy(bRecv, 0, bResult, 0, bLen);
            return bResult;
        }
    }

    public void doQuery() {
        if (!this.onCmd) {
            byte[] bQuery = new byte[]{-31, 11, 84, 49, 80, 66, 71, 67, 79, 78, 67, 58, -52};
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            double dTmp = 0.0D;
            byte[] bRecv = this.getRecv(this.waitAnswer, 2 * this.mChannel, true);
            Timestamp timestamp;
            if (bRecv != null) {
                int index;
                String strTmp;
                String strTime;
                String strVal;
                if (bRecv.length > 20 && bRecv[0] == 13 && bRecv[1] == 10 && bRecv[bRecv.length - 2] == 13 && bRecv[bRecv.length - 1] == 10) {
                    strTmp = Utility.getString(bRecv, 2, bRecv.length - 4);
                    strTime = "20" + strTmp.substring(6, 8) + "-" + strTmp.substring(3, 5) + "-" + strTmp.substring(0, 2) + " " + strTmp.substring(9, 17);
                    strVal = strTmp.substring(18, strTmp.length());
                    index = strVal.indexOf(" ", 0);
                    if (index != -1) {
                        dTmp = (double)Float.parseFloat(strVal.substring(0, index));
                    }

                    timestamp = TimeHelper.parseDevTime(strTime);
                    this.setParamTestVal(dTmp, "N", timestamp);
                    this.setStatus(true);
                    Utility.logInfo("WtwNh3n Dev: 查询成功，通道" + this.mChannel);
                } else if (bRecv.length > 38 && (bRecv[0] & 255) == 161 && bRecv[1] == 37) {
                    strTmp = Utility.getString(bRecv, 2, bRecv.length - 6);
                    strTime = "20" + strTmp.substring(6, 8) + "-" + strTmp.substring(3, 5) + "-" + strTmp.substring(0, 2) + " " + strTmp.substring(9, 17);
                    strVal = strTmp.substring(18, strTmp.length());
                    index = strVal.indexOf(" ", 0);
                    if (index != -1) {
                        dTmp = (double)Float.parseFloat(strVal.substring(0, index));
                    }

                    timestamp = TimeHelper.parseDevTime(strTime);
                    this.setParamTestVal(dTmp, "N", timestamp);
                    this.setStatus(true);
                    Utility.logInfo("WtwNh3n Dev: 查询成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("WtwNh3n Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else if (MonitorService.autoData) {
                timestamp = TimeHelper.parseDevTime("2019-01-01 10:12:13");
                dTmp = this.rand.nextDouble() * this.mCfgFactor.getAlarmMax();
                this.setParamTestVal(dTmp, "N", timestamp);
                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("WtwNh3n Dev: 查询失败，通道" + this.mChannel + "，无返回");
            }

        }
    }

    public void saveResult() {
    }

    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        int iResult = 2;
        this.initDevState(cmdType);
        if (MonitorService.autoData) {
            return true;
        } else {
            this.onCmd = true;
            this.mCmdType = cmdType;
            byte[] bCmdStop = new byte[]{-31, 10, 84, 49, 80, 66, 83, 84, 79, 80, 4, -118};
            byte[] bCmd;
            switch(cmdType) {
                case 8:
                case 9:
                    bCmd = new byte[]{-31, 9, 84, 49, 67, 67, 65, 76, 73, 5, -21};
                    this.fixedThreadPool.execute(new Runnable() {
                        public void run() {
                            try {
                                for(int i = 0; i < 2700; ++i) {
                                    Thread.sleep(1000L);
                                }

                                DevNh3nWtw.this.doDevCmd(11, (String)null, (String)null);
                            } catch (Exception var2) {
                            }

                        }
                    });
                    break;
                case 10:
                    bCmd = new byte[]{-31, 9, 84, 49, 67, 76, 69, 65, 78, 5, -21};
                    break;
                case 11:
                    bCmd = new byte[]{-31, 10, 84, 49, 80, 66, 83, 84, 79, 80, 4, -118};
                    break;
                case 12:
                    return false;
                case 13:
                    return false;
                default:
                    bCmd = new byte[]{-31, 9, 84, 49, 80, 66, 82, 85, 78, 74, -24};
                    this.fixedThreadPool.execute(new Runnable() {
                        public void run() {
                            try {
                                int iWait = (int)ScriptWord.getInstance().getSysVar("MeasureStopTime");

                                for(int i = 0; i < iWait; ++i) {
                                    Thread.sleep(1000L);
                                }

                                DevNh3nWtw.this.doDevCmd(11, (String)null, (String)null);
                            } catch (Exception var3) {
                            }

                        }
                    });
            }

            this.mInterComm.clearRecv(2 * this.mChannel + 1);
            this.mInterComm.Send(bCmdStop);

            try {
                Thread.sleep(2000L);
            } catch (Exception var8) {
            }

            this.mInterComm.clearRecv(2 * this.mChannel + 1);
            this.mInterComm.Send(bCmd);
            byte[] bRecv = this.getRecv(this.waitAnswer, 2 * this.mChannel + 1, false);
            if (bRecv != null) {
                if (bRecv[0] == 13 && bRecv[1] == 10 && bRecv[bRecv.length - 2] == 13 && bRecv[bRecv.length - 1] == 10 || bRecv.length == 14 && (bRecv[0] & 255) == 161 && bRecv[1] == 12) {
                    iResult = 1;
                }
            } else {
                iResult = 4;
            }

            if (cmdQN != null && this.devExeListener != null) {
                this.devExeListener.OnExeEvent(cmdQN, cmdType, iResult);
            }

            this.onCmd = false;
            this.saveCmdResult("WtwNh3n", iResult);
            return iResult == 1;
        }
    }

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                DevNh3nWtw.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
