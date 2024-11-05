//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.env;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;

public class EnvPowerTk extends DevService {
    private String recvString = "";
    private EnvPowerTk.QueryThread queryThread;

    public EnvPowerTk() {
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
                if (bLen == 17 && bRecv[16] == 0) {
                    break;
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var12) {
                }
            }
        }

        if (bLen == 17 && bRecv[16] == 0) {
            byte[] bResult = new byte[bLen];
            System.arraycopy(bRecv, 0, bResult, 0, bLen);
            return bResult;
        } else {
            return null;
        }
    }

    public void doQuery() {
        byte[] bRecv = this.getRecv(900, 2 * this.mChannel, true);
        if (bRecv != null && bRecv.length == 17) {
            byte bCrc = Utility.add8(bRecv, 0, 15);
            if (bCrc == bRecv[15]) {
                this.channelVals[0] = (double)(bRecv[0] & 255) + 0.0D;
                this.channelVals[1] = (double)((bRecv[2] & 255) * 256 + (bRecv[1] & 255)) / 100.0D;
                this.channelVals[2] = (double)((bRecv[6] & 255) * 256 * 256 * 256 + (bRecv[5] & 255) * 256 * 256 + (bRecv[4] & 255) * 256 + (bRecv[3] & 255)) / 1000.0D;
                this.channelVals[3] = (double)((bRecv[10] & 255) * 256 * 256 * 256 + (bRecv[9] & 255) * 256 * 256 + (bRecv[8] & 255) * 256 + (bRecv[7] & 255)) / 1000.0D;
                this.channelVals[4] = Utility.getFormatDouble((double)((bRecv[14] & 255) * 256 * 256 * 256 + (bRecv[13] & 255) * 256 * 256 + (bRecv[12] & 255) * 256 + (bRecv[11] & 255)) / 3600.0D, 2);
            }

            this.setStatus(true);
        } else {
            this.setStatus(false);
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

    public void startQuery() {
        this.queryThread = new EnvPowerTk.QueryThread();
        this.queryThread.start();
    }

    public void stopQuery() {
        if (this.queryThread != null) {
            this.queryThread.interrupt();
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

    private class QueryThread extends Thread {
        private QueryThread() {
        }

        public void run() {
            super.run();

            try {
                Thread.sleep(3000L);
            } catch (Exception var2) {
            }

            if (EnvPowerTk.this.mInterComm != null) {
                EnvPowerTk.this.mInterComm.clearRecv(2 * EnvPowerTk.this.mChannel);
            }

            while(!this.isInterrupted() && !MonitorService.restart) {
                try {
                    if (EnvPowerTk.this.mInterComm != null) {
                        EnvPowerTk.this.doQuery();
                    }

                    Thread.sleep(1000L);
                } catch (Exception var3) {
                    break;
                }
            }

        }
    }
}
