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

public class DevFiveYsi extends DevService {
    private Random rand = new Random();

    public DevFiveYsi() {
    }

    private byte[] getRecv(int waitTime, int recvChannel, boolean bQuery) {
        int bLen = 0;
        byte[] bRecv = new byte[128];
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
                if (bLen > 16 && bRecv[0] == 13 && bRecv[1] == 10 && bRecv[bLen - 2] == 13 && bRecv[bLen - 1] == 10) {
                    break;
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var12) {
                }
            }
        }

        if (bLen > 16 && bRecv[0] == 13 && bRecv[1] == 10 && bRecv[bLen - 2] == 13 && bRecv[bLen - 1] == 10) {
            byte[] bResult = new byte[bLen];
            System.arraycopy(bRecv, 0, bResult, 0, bLen);
            return bResult;
        } else {
            return null;
        }
    }

    public void doQuery() {
        byte[] bQuery = new byte[]{13, 100, 97, 116, 97, 13, 100, 97, 116, 97};
        this.mInterComm.clearRecv(2 * this.mChannel);
        this.mInterComm.Send(bQuery);
        byte[] bRecv = this.getRecv(2000, 2 * this.mChannel, true);
        double dTmp;
        if (bRecv != null) {
            String strTmp = new String(bRecv);
            strTmp = strTmp.replace("\r\n", " ");
            strTmp = strTmp.trim();
            String[] strArray = strTmp.split(" ");
            int iIndex;
            if (this.mCfgFactor.getDevAddress() > 0) {
                iIndex = 2 + this.mCfgFactor.getDevAddress();
            } else {
                String var8 = this.mCfgFactor.getName();
                byte var9 = -1;
                switch(var8.hashCode()) {
                    case 3544:
                        if (var8.equals("pH")) {
                            var9 = 0;
                        }
                        break;
                    case 78541:
                        if (var8.equals("ORP")) {
                            var9 = 5;
                        }
                        break;
                    case 886901:
                        if (var8.equals("水温")) {
                            var9 = 1;
                        }
                        break;
                    case 891548:
                        if (var8.equals("浊度")) {
                            var9 = 4;
                        }
                        break;
                    case 28358618:
                        if (var8.equals("溶解氧")) {
                            var9 = 2;
                        }
                        break;
                    case 29594368:
                        if (var8.equals("电导率")) {
                            var9 = 3;
                        }
                }

                switch(var9) {
                    case 0:
                        iIndex = 3;
                        break;
                    case 1:
                        iIndex = 4;
                        break;
                    case 2:
                        iIndex = 5;
                        break;
                    case 3:
                        iIndex = 6;
                        break;
                    case 4:
                        iIndex = 7;
                        break;
                    case 5:
                        iIndex = 8;
                        break;
                    default:
                        iIndex = 0;
                }
            }

            if (iIndex > 2 && iIndex < strArray.length) {
                dTmp = Double.parseDouble(strArray[iIndex]);
                this.setParamTestValAll(dTmp, "N", (Timestamp)null);
            }

            this.setStatus(true);
            Utility.logInfo("YSIFive Dev: 查询成功，通道" + this.mChannel);
        } else if (MonitorService.autoData) {
            dTmp = this.rand.nextDouble() * 10.0D;
            this.setParamTestValAll(dTmp, "N", (Timestamp)null);

            for(int i = 0; i < 8; ++i) {
                this.channelVals[i] = dTmp + (double)i * 0.5D;
            }

            this.setStatus(true);
        } else {
            this.setStatus(false);
            Utility.logInfo("YSIFive Dev: 查询失败，通道" + this.mChannel + "，无返回");
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
                DevFiveYsi.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
