//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev;

import com.grean.station.service.MonitorService;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Random;

public class AdamDcon extends DevService {
    private Random rand = new Random();

    public AdamDcon() {
    }

    private byte[] getRecv(int waitTime, int recvChannel, boolean bQuery) {
        byte[] bRecv = new byte[128];
        int recvLen = 0;
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
                if (recvLen + tmpRecv.length < 128) {
                    System.arraycopy(tmpRecv, 0, bRecv, recvLen, tmpRecv.length);
                    recvLen += tmpRecv.length;
                }

                if (recvLen >= 58 && bRecv[0] == 62 && bRecv[recvLen - 1] == 13) {
                    break;
                }

                try {
                    Thread.sleep(50L);
                } catch (InterruptedException var12) {
                }
            }
        }

        if (recvLen >= 58 && bRecv[0] == 62 && bRecv[recvLen - 1] == 13) {
            byte[] bResult = new byte[recvLen];
            System.arraycopy(bRecv, 0, bResult, 0, recvLen);
            return bResult;
        } else {
            return null;
        }
    }

    public void doQuery() {
        this.mAddress = this.mCfgDev.getAddress().intValue();
        byte[] bQuery = new byte[]{35, 48, 49, 13};
        bQuery[1] = (byte)(48 + this.mAddress / 10 % 10);
        bQuery[2] = (byte)(48 + this.mAddress % 10);
        this.mInterComm.clearRecv(2 * this.mChannel);
        this.mInterComm.Send(bQuery);
        byte[] bRecv = this.getRecv(2000, 2 * this.mChannel, true);
        if (bRecv != null) {
            int iIndex;
            if (this.mCfgFactor.getDevAddress() > 0 && this.mCfgFactor.getDevAddress() < 9) {
                iIndex = 1 + 7 * (this.mCfgFactor.getDevAddress() - 1);
            } else {
                iIndex = 1;
            }

            String strTmp = Utility.getString(bRecv, iIndex, 7);
            float testResult = (Float.parseFloat(strTmp) - 1.0F) * (float)this.mCfgFactor.getRange() / 4.0F;
            this.setParamTestValAll((double)testResult, "N", (Timestamp)null);
            this.setStatus(true);
            Utility.logInfo("AdamDcon Dev: 查询成功，通道" + this.mChannel);
        } else if (MonitorService.autoData) {
            double dTmp = this.rand.nextDouble() * 10.0D;
            this.setParamTestVal(dTmp, "N", (Timestamp)null);
            this.setStatus(true);
        } else {
            this.setStatus(false);
            Utility.logInfo("AdamDcon Dev: 查询失败，通道" + this.mChannel + "，无返回");
        }

    }

    public void saveResult() {
    }

    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        return false;
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
