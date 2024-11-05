//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.tnp;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Random;

public class DevTnpDkk150 extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevTnpDkk150() {
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
                if (bLen > 40 && bRecv[0] == 68 && bRecv[1] == 32 && bRecv[bLen - 2] == 13 && bRecv[bLen - 1] == 10) {
                    break;
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var12) {
                }
            }
        }

        if (bLen > 40 && bRecv[0] == 68 && bRecv[1] == 32 && bRecv[bLen - 2] == 13 && bRecv[bLen - 1] == 10) {
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
            byte[] bQuery = new byte[]{68, 32, 32, 49, 49, 13, 10};
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(this.waitAnswer, 2 * this.mChannel, true);
            double dTmp;
            String var9;
            byte var10;
            if (bRecv != null) {
                if (bRecv.length > 40 && bRecv[0] == 68 && bRecv[1] == 32 && bRecv[bRecv.length - 2] == 13 && bRecv[bRecv.length - 1] == 10) {
                    String strTmp = Utility.getString(bRecv, 20, bRecv.length - 22);
                    var9 = this.mCfgFactor.getName();
                    var10 = -1;
                    switch(var9.hashCode()) {
                        case 791379:
                            if (var9.equals("总氮")) {
                                var10 = 1;
                            }
                            break;
                        case 794652:
                            if (var9.equals("总磷")) {
                                var10 = 0;
                            }
                    }

                    String strVal;
                    switch(var10) {
                        case 0:
                            strVal = strTmp.substring(9, 17);
                            if (strVal.contains(".")) {
                                dTmp = (double)Float.parseFloat(strVal);
                            } else {
                                dTmp = 0.0D;
                            }
                            break;
                        case 1:
                            strVal = strTmp.substring(0, 8);
                            if (strVal.contains(".")) {
                                dTmp = (double)Float.parseFloat(strVal);
                            } else {
                                dTmp = 0.0D;
                            }
                            break;
                        default:
                            dTmp = 0.0D;
                    }

                    this.setParamTestVal(dTmp, "N", (Timestamp)null);
                    this.setStatus(true);
                    Utility.logInfo("DkkTnp150 Dev: 查询成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("DkkTnp150 Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else if (MonitorService.autoData) {
                var9 = this.mCfgFactor.getName();
                var10 = -1;
                switch(var9.hashCode()) {
                    case 791379:
                        if (var9.equals("总氮")) {
                            var10 = 1;
                        }
                        break;
                    case 794652:
                        if (var9.equals("总磷")) {
                            var10 = 0;
                        }
                }

                switch(var10) {
                    case 0:
                        dTmp = this.rand.nextDouble() * this.mCfgFactor.getAlarmMax();
                        break;
                    case 1:
                        dTmp = this.rand.nextDouble() * this.mCfgFactor.getAlarmMax();
                        break;
                    default:
                        dTmp = 0.0D;
                }

                this.setParam("MeaAccuracy", 12);
                this.setParamTestVal(dTmp, "N", (Timestamp)null);
                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("DkkTnp150 Dev: 查询失败，通道" + this.mChannel + "，无返回");
            }

        }
    }

    public void saveResult() {
    }

    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        this.initDevState(cmdType);
        this.mCmdType = cmdType;
        return true;
    }

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                DevTnpDkk150.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
