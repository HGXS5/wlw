//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.met;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.util.Random;

public class DevMetVaisalaWXT536 extends DevService {
    private DevMetVaisalaWXT536.QueryThread queryThread;
    private Random rand = new Random();

    public DevMetVaisalaWXT536() {
    }

    private byte[] getRecv(int waitTime, int recvChannel, boolean bQuery) {
        int bLen = 0;
        byte[] bRecv = new byte[1024];
        long tStart = System.currentTimeMillis();

        byte[] bResult;
        while(bLen < 512) {
            long tNow = System.currentTimeMillis();
            if (tNow - tStart > (long)waitTime) {
                break;
            }

            bResult = this.mInterComm.Recv(recvChannel);
            if (bResult == null) {
                try {
                    Thread.sleep(50L);
                } catch (InterruptedException var13) {
                }
            } else {
                System.arraycopy(bResult, 0, bRecv, bLen, bResult.length);
                bLen += bResult.length;
                if (bLen <= 5 || bRecv[0] == 36 && bRecv[1] == 87 && bRecv[2] == 73 && bRecv[3] == 88 && bRecv[4] == 68 && bRecv[5] == 82) {
                    if (bLen > 60 && bRecv[0] == 36 && bRecv[1] == 87 && bRecv[2] == 73 && bRecv[3] == 88 && bRecv[4] == 68 && bRecv[5] == 82 && bRecv[bLen - 2] == 13 && bRecv[bLen - 1] == 10) {
                        break;
                    }

                    try {
                        Thread.sleep(50L);
                    } catch (Exception var12) {
                    }
                } else {
                    bLen = 0;
                }
            }
        }

        if (bLen > 60 && bRecv[0] == 36 && bRecv[1] == 87 && bRecv[2] == 73 && bRecv[3] == 88 && bRecv[4] == 68 && bRecv[5] == 82 && bRecv[bLen - 2] == 13 && bRecv[bLen - 1] == 10) {
            String strCrc = Utility.xor8(bRecv, 1, bLen - 6);
            String strPkg = String.format("%c%c", bRecv[bLen - 4], bRecv[bLen - 3]);
            if (strCrc.equals(strPkg)) {
                bResult = new byte[bLen];
                System.arraycopy(bRecv, 0, bResult, 0, bLen);
                return bResult;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void startQuery() {
        this.queryThread = new DevMetVaisalaWXT536.QueryThread();
        this.queryThread.start();
    }

    public void stopQuery() {
        if (this.queryThread != null) {
            this.queryThread.interrupt();
        }

    }

    public void doQuery() {
        this.mInterComm.clearRecv(2 * this.mChannel);
        byte[] bRecv = this.getRecv(2000, 2 * this.mChannel, true);
        if (bRecv != null) {
            String strTmp = new String(bRecv);
            strTmp = strTmp.substring(0, strTmp.length() - 5);
            strTmp = strTmp.trim();
            String[] strArray = strTmp.split(",");
            if (strArray.length > 27) {
                this.channelVals[0] = Double.parseDouble(strArray[10]);
                this.channelVals[1] = Double.parseDouble(strArray[18]);
                this.channelVals[2] = Double.parseDouble(strArray[14]);
                this.channelVals[3] = Double.parseDouble(strArray[2]);
                this.channelVals[4] = Double.parseDouble(strArray[6]);
                this.channelVals[5] = Double.parseDouble(strArray[22]);
                this.channelVals[6] = Double.parseDouble(strArray[26]);
            }

            this.setStatus(true);
            Utility.logInfo("NMEA0183 Dev: 查询成功，通道" + this.mChannel);
        } else if (MonitorService.autoData) {
            double dTmp = this.rand.nextDouble() * 10.0D;

            for(int i = 0; i < 8; ++i) {
                this.channelVals[i] = dTmp + (double)i * 0.5D;
            }

            this.setStatus(true);
        } else {
            this.setStatus(false);
            Utility.logInfo("NMEA0183 Dev: 查询失败，通道" + this.mChannel + "，无返回");
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
                DevMetVaisalaWXT536.this.doDevCmd(cmdType, cmdParam, cmdQN);
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

    private class QueryThread extends Thread {
        private QueryThread() {
        }

        public void run() {
            super.run();

            try {
                Thread.sleep(3000L);
            } catch (Exception var2) {
            }

            while(!this.isInterrupted() && !MonitorService.restart) {
                try {
                    if (DevMetVaisalaWXT536.this.mInterComm != null) {
                        DevMetVaisalaWXT536.this.doQuery();
                    }

                    Thread.sleep(100L);
                } catch (Exception var3) {
                    break;
                }
            }

        }
    }
}
