//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.flow;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.util.Random;

public class DevFlowOtt extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevFlowOtt() {
    }

    private byte[] getRecv(int waitTime, int recvChannel, boolean bQuery) {
        int bLen = 0;
        byte[] bRecv = new byte[512];

        byte[] tmpRecv;
        while(true) {
            tmpRecv = this.mInterComm.Recv(recvChannel);
            if (tmpRecv == null) {
                break;
            }

            if (bLen + tmpRecv.length > 500) {
                bLen = 0;
                break;
            }

            System.arraycopy(tmpRecv, 0, bRecv, bLen, tmpRecv.length);
            bLen += tmpRecv.length;

            try {
                Thread.sleep(50L);
            } catch (Exception var8) {
            }
        }

        if (bLen > 0 && bQuery && bLen == 58 && bRecv[0] == -91 && bRecv[1] == 66 && bRecv[2] == 17 && bRecv[3] == 0 && bRecv[34] == -91 && bRecv[35] == 9 && bRecv[36] == 12 && bRecv[37] == 0) {
            tmpRecv = new byte[bLen];
            System.arraycopy(bRecv, 0, tmpRecv, 0, bLen);
            return tmpRecv;
        } else {
            return null;
        }
    }

    public void doQuery() {
        if (!this.onCmd) {
            byte[] bRecv = this.getRecv(2000, 2 * this.mChannel, true);
            int i;
            if (bRecv != null) {
                this.channelVals[0] = (double)((bRecv[11] & 255) * 256 + (bRecv[10] & 255)) / 1000.0D;
                int dischargeMSW = (bRecv[45] & 255) * 256 + (bRecv[44] & 255);
                int dischargeLSW = (bRecv[47] & 255) * 256 + (bRecv[46] & 255);
                this.channelVals[1] = (double)(dischargeMSW * 65536 + dischargeLSW) / 1000.0D;
                i = (bRecv[49] & 255) * 256 + (bRecv[48] & 255);
                int accDischargeMW = (bRecv[51] & 255) * 256 + (bRecv[50] & 255);
                int accDischargeLW = (bRecv[53] & 255) * 256 + (bRecv[52] & 255);
                this.channelVals[2] = ((double)i * 4.294967296E9D + (double)(accDischargeMW * 65536) + (double)accDischargeLW) / 1000.0D;
                this.setStatus(true);
                Utility.logInfo("OttFlow Dev: 查询成功，通道" + this.mChannel);
            } else if (MonitorService.autoData) {
                double dTmp = this.rand.nextDouble() * 10.0D;

                for(i = 0; i < 8; ++i) {
                    this.channelVals[i] = dTmp + (double)i * 0.5D;
                }

                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("OttFlow Dev: 查询失败，通道" + this.mChannel + "，无返回");
            }

        }
    }

    public void setStatus(boolean connected) {
        if (connected) {
            this.bConnect = true;
            this.disCount = 0;
        } else {
            ++this.disCount;
            if (this.disCount > 60) {
                this.bConnect = false;
            }
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
                DevFlowOtt.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
        this.doDevCmdThread(8, strParam, strQN);
    }

    public void doStdCal(String strQN, String strParam) {
        this.doDevCmdThread(9, strParam, strQN);
    }

    public void doInitCmd() {
        this.doDevCmdThread(10, (String)null, (String)null);
    }

    public void doStopCmd() {
        this.doDevCmdThread(11, (String)null, (String)null);
    }

    public void doRsetCmd() {
        this.doDevCmdThread(12, (String)null, (String)null);
    }

    public void doSetTime(String strQN, String strParam) {
        this.doDevCmdThread(13, strParam, strQN);
    }
}
