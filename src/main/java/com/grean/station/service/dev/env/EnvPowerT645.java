//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.env;

import com.grean.station.service.dev.DevService;
import com.grean.station.utils.TimeHelper;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;

public class EnvPowerT645 extends DevService {
    public EnvPowerT645() {
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
                if (bLen >= 24 && bRecv[0] == -2 && bRecv[1] == -2 && bRecv[2] == -2 && bRecv[3] == -2 && bRecv[bLen - 1] == 22) {
                    break;
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var12) {
                }
            }
        }

        if (bLen >= 24 && bRecv[0] == -2 && bRecv[1] == -2 && bRecv[2] == -2 && bRecv[3] == -2 && bRecv[bLen - 1] == 22) {
            byte[] bResult = new byte[bLen];
            System.arraycopy(bRecv, 0, bResult, 0, bLen);
            return bResult;
        } else {
            return null;
        }
    }

    public void doQuery() {
        byte[] bQuery = new byte[]{-2, -2, -2, -2, 104, 38, 97, 57, 0, 0, 0, 104, 17, 4, 51, 51, 52, 51, 114, 22};
        long addTmp = this.mCfgDev.getAddress();

        for(int i = 10; i > 4; --i) {
            long lTmp = addTmp % 100L;
            addTmp /= 100L;
            bQuery[i] = Utility.getBCDVal((int)lTmp);
        }

        bQuery[18] = Utility.add8(bQuery, 4, bQuery.length - 6);
        this.mInterComm.clearRecv(2 * this.mChannel);
        this.mInterComm.Send(bQuery);
        double dTmp = 0.0D;
        byte[] bRecv = this.getRecv(2000, 2 * this.mChannel, true);
        if (bRecv != null) {
            byte bCrc = Utility.add8(bRecv, 4, bRecv.length - 6);
            if (bCrc == bRecv[bRecv.length - 2]) {
                int iResult = 0;
                byte bTmp;

                for(int i = 3; i < 7; ++i) {
                    bTmp = bRecv[bRecv.length - i];
                    bTmp = (byte)((bTmp & 255) - 51);
                    iResult = iResult * 100 + Utility.getBCDVal(bTmp);
                }

                dTmp = (double)iResult / 100.0D;
                Timestamp timestamp = TimeHelper.getCurrentTimestamp();
                this.setParamTestValAll(dTmp, "N", timestamp);
                this.setStatus(true);
                Utility.logInfo("SantakPower Dev: 查询成功，通道" + this.mChannel);
            } else {
                this.setStatus(false);
                Utility.logInfo("SantakPower Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
            }
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
