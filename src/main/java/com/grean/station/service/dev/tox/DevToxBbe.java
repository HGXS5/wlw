//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.tox;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.util.Random;

public class DevToxBbe extends DevService {
    private DevToxBbe.QueryThread queryThread;
    private Random rand = new Random();

    public DevToxBbe() {
    }

    private byte[] getRecv(int waitTime, int recvChannel, boolean bQuery) {
        int bLen = 0;
        byte[] bRecv = new byte[2000];
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
                if (bLen > 1000) {
                    bLen = 0;
                    break;
                }

                System.arraycopy(tmpRecv, 0, bRecv, bLen, tmpRecv.length);
                bLen += tmpRecv.length;
                if (bLen > 80 && bRecv[0] == 80) {
                    break;
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var12) {
                }
            }
        }

        if (bLen > 80 && bRecv[0] == 80) {
            byte[] bResult = new byte[bLen];
            System.arraycopy(bRecv, 0, bResult, 0, bLen);
            return bResult;
        } else {
            return null;
        }
    }

    public void doQuery() {
        this.mAddress = this.mCfgDev.getAddress().intValue();
        byte[] bQuery = new byte[]{100};
        this.mInterComm.clearRecv(2 * this.mChannel);
        this.mInterComm.Send(bQuery);
        byte[] bRecv = this.getRecv(this.waitAnswer, 2 * this.mChannel, true);
        if (bRecv != null) {
            try {
                String strTmp = new String(bRecv);
                String[] strarray = strTmp.split("\t");
                if (strarray.length > 10) {
                    this.channelVals[0] = Double.parseDouble(strarray[5]);
                    this.channelVals[1] = Double.parseDouble(strarray[6]);
                }

                this.setStatus(true);
                Utility.logInfo("ToxBbe Dev: 查询成功，通道" + this.mChannel);
            } catch (Exception var5) {
            }
        } else if (MonitorService.autoData) {
            this.channelVals[0] = this.rand.nextDouble() * this.mCfgFactor.getAlarmMax();
            this.channelVals[1] = this.rand.nextDouble() * this.mCfgFactor.getAlarmMax();
            this.setStatus(true);
        } else {
            this.setStatus(false);
            Utility.logInfo("ToxBbe Dev: 查询失败，通道" + this.mChannel + "，无返回");
        }

    }

    public void startQuery() {
        this.queryThread = new DevToxBbe.QueryThread();
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

            while(!this.isInterrupted() && !MonitorService.restart) {
                try {
                    if (DevToxBbe.this.mInterComm != null) {
                        DevToxBbe.this.doQuery();
                    }

                    for(int i = 0; i < 300; ++i) {
                        Thread.sleep(100L);
                        if (MonitorService.restart) {
                            break;
                        }
                    }
                } catch (Exception var3) {
                    break;
                }
            }

        }
    }
}
