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

public class DevFiveYsi2 extends DevService {
    private Random rand = new Random();

    public DevFiveYsi2() {
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
                if (bLen > 24 && bRecv[0] == 13 && bRecv[1] == 10 && bRecv[bLen - 2] == 35 && bRecv[bLen - 1] == 32) {
                    break;
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var12) {
                }
            }
        }

        if (bLen > 24 && bRecv[0] == 13 && bRecv[1] == 10 && bRecv[bLen - 2] == 35 && bRecv[bLen - 1] == 32) {
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
            try {
                String strRecv = new String(bRecv);
                int index = strRecv.lastIndexOf("data");
                if (bRecv[index + 4] == 13 && bRecv[index + 5] == 10) {
                    index += 2;
                }

                byte[] bResult = new byte[bRecv.length - index - 4 - 4];
                System.arraycopy(bRecv, index + 4, bResult, 0, bRecv.length - index - 4 - 4);
                String strTmp = new String(bResult);
                strTmp = strTmp.trim();
                strTmp = strTmp.replaceAll("\\s+", " ");
                String[] strArray = strTmp.split(" ");
                int iIndex;
                if (this.mCfgFactor.getDevAddress() > 0) {
                    iIndex = this.mCfgFactor.getDevAddress() - 1;
                } else {
                    String var11 = this.mCfgFactor.getName();
                    byte var12 = -1;
                    switch(var11.hashCode()) {
                        case 3544:
                            if (var11.equals("pH")) {
                                var12 = 0;
                            }
                            break;
                        case 886022:
                            if (var11.equals("氨氮")) {
                                var12 = 6;
                            }
                            break;
                        case 886901:
                            if (var11.equals("水温")) {
                                var12 = 1;
                            }
                            break;
                        case 891548:
                            if (var11.equals("浊度")) {
                                var12 = 4;
                            }
                            break;
                        case 982891:
                            if (var11.equals("硝氨")) {
                                var12 = 5;
                            }
                            break;
                        case 28358618:
                            if (var11.equals("溶解氧")) {
                                var12 = 2;
                            }
                            break;
                        case 29594368:
                            if (var11.equals("电导率")) {
                                var12 = 3;
                            }
                    }

                    switch(var12) {
                        case 0:
                            iIndex = 2;
                            break;
                        case 1:
                            iIndex = 0;
                            break;
                        case 2:
                            iIndex = 5;
                            break;
                        case 3:
                            iIndex = 1;
                            break;
                        case 4:
                            iIndex = 3;
                            break;
                        case 5:
                            iIndex = 4;
                            break;
                        case 6:
                            iIndex = 6;
                            break;
                        default:
                            iIndex = 0;
                    }
                }

                if (iIndex >= 0 && iIndex < strArray.length) {
                    dTmp = Double.parseDouble(strArray[iIndex]);
                    this.setParamTestValAll(dTmp, "N", (Timestamp)null);
                }

                for(int i = 0; i < strArray.length; ++i) {
                    this.channelVals[i] = Double.parseDouble(strArray[i]);
                }

                this.setStatus(true);
                Utility.logInfo("YSIFive2 Dev: 查询成功，通道" + this.mChannel);
            } catch (Exception var13) {
                System.out.println(var13.toString());
            }
        } else if (MonitorService.autoData) {
            dTmp = this.rand.nextDouble() * 10.0D;
            this.setParamTestValAll(dTmp, "N", (Timestamp)null);

            for(int i = 0; i < 8; ++i) {
                this.channelVals[i] = dTmp + (double)i * 0.5D;
            }

            this.setStatus(true);
        } else {
            this.setStatus(false);
            Utility.logInfo("YSIFive2 Dev: 查询失败，通道" + this.mChannel + "，无返回");
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
                DevFiveYsi2.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
