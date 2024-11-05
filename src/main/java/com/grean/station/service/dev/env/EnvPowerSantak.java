//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.env;

import com.grean.station.service.dev.DevService;
import com.grean.station.utils.TimeHelper;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;

public class EnvPowerSantak extends DevService {
    private boolean powerBackup;

    public EnvPowerSantak() {
    }

    public boolean isPowerBackup() {
        return this.powerBackup;
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
                if (bLen > 46 && bRecv[0] == 40 && bRecv[46] == 13) {
                    break;
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var12) {
                }
            }
        }

        if (bLen > 46 && bRecv[0] == 40 && bRecv[46] == 13) {
            byte[] bResult = new byte[bLen];
            System.arraycopy(bRecv, 0, bResult, 0, bLen);
            return bResult;
        } else {
            return null;
        }
    }

    public void doQuery() {
        byte[] bQuery = new byte[]{81, 49, 13};
        this.mInterComm.clearRecv(2 * this.mChannel);
        this.mInterComm.Send(bQuery);
        double dTmp = 0.0D;
        byte[] bRecv = this.getRecv(2000, 2 * this.mChannel, true);
        if (bRecv != null) {
            if (bRecv.length == 47 && bRecv[0] == 40 && bRecv[46] == 13) {
                String strTmp = Utility.getString(bRecv, 0, bRecv.length - 1);
                String[] strArray = strTmp.split(" ");
                int[] states = new int[8];
                if (strArray.length == 8) {
                    String strState = strArray[7];
                    if (strState.length() == 8) {
                        for(int i = 0; i < 8; ++i) {
                            states[i] = Integer.parseInt(strState.substring(i, i + 1));
                        }

                        if (states[0] == 1) {
                            this.powerBackup = true;
                        } else {
                            this.powerBackup = false;
                        }
                    }

                    dTmp = (double)Float.parseFloat(strArray[2]);
                    Timestamp timestamp = TimeHelper.getCurrentTimestamp();
                    this.setParamTestValAll(dTmp, "N", timestamp);
                }

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
