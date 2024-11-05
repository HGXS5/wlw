//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.flow;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.util.Random;

public class DevFlowxx extends DevService {
    private Random rand = new Random();

    public DevFlowxx() {
    }

    private byte[] getRecv(int waitTime, int recvChannel, boolean bQuery) {
        int bLen = 0;
        byte[] bRecv = new byte[1024];
        long tStart = System.currentTimeMillis();

        while(bLen < 512) {
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
                if (bRecv[0] != 50 && bRecv[1] != 48) {
                    bLen = 0;
                } else {
                    if (bLen > 500 && bRecv[bLen - 2] == 13 && bRecv[bLen - 1] == 10) {
                        break;
                    }

                    try {
                        Thread.sleep(50L);
                    } catch (Exception var12) {
                    }
                }
            }
        }

        if (bLen > 0) {
            byte[] bResult = new byte[bLen];
            System.arraycopy(bRecv, 0, bResult, 0, bLen);
            return bResult;
        } else {
            return null;
        }
    }

    public void doQuery() {
        this.mInterComm.clearRecv(2 * this.mChannel);
        byte[] bRecv = new byte[]{50, 48, 49, 57, 32, 48, 53, 32, 49, 48, 32, 49, 54, 32, 49, 51, 32, 52, 49, 32, 32, 32, 45, 50, 52, 56, 32, 32, 32, 32, 45, 51, 56, 32, 32, 32, 32, 32, 45, 57, 32, 32, 32, 52, 32, 32, 32, 49, 32, 32, 57, 49, 32, 49, 52, 52, 32, 49, 52, 50, 32, 49, 51, 57};
        String strTmp = new String(bRecv);
        if (bRecv != null) {
            String[] strarray = strTmp.split("\\s+");
            this.channelVals[0] = (double)Float.parseFloat(strarray[6]) / 100.0D;
            this.channelVals[1] = (double)Float.parseFloat(strarray[7]) / 100.0D;
            this.setStatus(true);
            Utility.logInfo("HachFive Dev: 查询成功，通道" + this.mChannel);
        } else if (MonitorService.autoData) {
            double dTmp = this.rand.nextDouble() * 10.0D;

            for(int i = 0; i < 8; ++i) {
                this.channelVals[i] = dTmp + (double)i * 0.5D;
            }

            this.setStatus(true);
        } else {
            this.setStatus(false);
            Utility.logInfo("Modbus Dev: 查询失败，通道" + this.mChannel);
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
                DevFlowxx.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
