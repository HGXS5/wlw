//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.cod;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Random;

public class DevCodDkk extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevCodDkk() {
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
                if (bLen + tmpRecv.length < 128) {
                    System.arraycopy(tmpRecv, 0, bRecv, bLen, tmpRecv.length);
                    bLen += tmpRecv.length;
                }

                if (bLen > 7 && bLen < 128 && bRecv[0] == 68 && bRecv[1] == 49 && bRecv[bLen - 2] == 13 && bRecv[bLen - 1] == 10) {
                    break;
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var12) {
                }
            }
        }

        if (bLen > 7 && bLen < 128 && bRecv[0] == 68 && bRecv[1] == 49 && bRecv[bLen - 2] == 13 && bRecv[bLen - 1] == 10) {
            byte[] bResult = new byte[bLen];
            System.arraycopy(bRecv, 0, bResult, 0, bLen);
            return bResult;
        } else {
            return null;
        }
    }

    public void doQuery() {
        if (!this.onCmd) {
            int regLength;
            this.mAddress = this.mCfgDev.getAddress().intValue();
            byte[] bQuery = new byte[]{68, 49, 32, 49, 49, 13, 10};
            Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(2000, 2 * this.mChannel, true);
            if (bRecv != null) {
                String strTmp = Utility.getString(bRecv, 5, bRecv.length - 7);
                String strTime = "20" + strTmp.substring(0, 2) + "-" + strTmp.substring(3, 5) + "-" + strTmp.substring(6, 8) + " " + strTmp.substring(9, 14) + ":00";
                String strVal = strTmp.substring(strTmp.length() - 8, strTmp.length());
                strVal = strVal.replaceAll(" ", "");
                float testResult = Float.parseFloat(strVal);
                this.setParamTestVal((double)testResult, "N", (Timestamp)null);
                this.setStatus(true);
                Utility.logInfo("DkkCod Dev: 查询成功，通道" + this.mChannel);
            } else if (MonitorService.autoData) {
                double dTmp = this.rand.nextDouble() * 10.0D;
                this.setParamTestVal(dTmp, "N", (Timestamp)null);
                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("DkkCod Dev: 查询失败，通道" + this.mChannel + "，无返回");
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
                DevCodDkk.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
