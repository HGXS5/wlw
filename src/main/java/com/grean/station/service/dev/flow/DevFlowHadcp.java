//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.flow;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;

public class DevFlowHadcp extends DevService {
    private String recvString = "";
    private DevFlowHadcp.QueryThread queryThread;

    public DevFlowHadcp() {
    }

    public void doQuery() {
        byte[] bRecv = this.mInterComm.Recv(2 * this.mChannel);
        if (bRecv != null) {
            this.recvString = this.recvString + new String(bRecv);
            if (this.recvString.length() > 15 && this.recvString.substring(0, 5).equals("PRDIQ") && this.recvString.substring(this.recvString.length() - 2, this.recvString.length()).equals("\r\n")) {
                String[] strArray = this.recvString.split(",");

                for(int i = 0; i < strArray.length - 1 && i < this.channelVals.length; ++i) {
                    this.channelVals[i] = Double.parseDouble(strArray[i + 1]);
                }

                this.recvString = "";
            }

            this.setStatus(true);
        } else {
            this.setStatus(false);
        }

        if (this.recvString.length() > 256) {
            this.recvString = "";
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
        this.queryThread = new DevFlowHadcp.QueryThread();
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
                    if (DevFlowHadcp.this.mInterComm != null) {
                        DevFlowHadcp.this.doQuery();
                    }

                    Thread.sleep(500L);
                } catch (Exception var3) {
                    break;
                }
            }

        }
    }
}
