//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.nh3n;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import java.sql.Timestamp;

public class DevNh3nThermoAscii extends DevService {
    private boolean onCmd = false;
    private boolean cmdResult = false;
    private String recvString = "";
    private DevNh3nThermoAscii.QueryThread queryThread;

    public DevNh3nThermoAscii() {
    }

    public void doQuery() {
        byte[] bRecv = this.mInterComm.Recv(2 * this.mChannel);
        if (bRecv != null) {
            this.recvString = this.recvString + new String(bRecv);
            if (this.recvString.length() > 50 && this.recvString.contains("CONCENTRATION :") && this.recvString.contains("mg/L") && this.recvString.substring(this.recvString.length() - 2, this.recvString.length()).equals("\r\n")) {
                int startIndex = this.recvString.indexOf("CONCENTRATION :");
                int stopIndex = -1;
                if (startIndex > -1) {
                    stopIndex = this.recvString.indexOf("mg/L", startIndex);
                }

                if (startIndex > -1 && stopIndex > -1) {
                    float testResult = Float.parseFloat(this.recvString.substring(startIndex + 15, stopIndex));
                    this.setParamTestVal((double)testResult, "N", (Timestamp)null);
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
            if (this.disCount > 600) {
                this.bConnect = false;
            }
        }

    }

    public void startQuery() {
        this.queryThread = new DevNh3nThermoAscii.QueryThread();
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

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                DevNh3nThermoAscii.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
                    if (DevNh3nThermoAscii.this.mInterComm != null) {
                        DevNh3nThermoAscii.this.doQuery();
                    }

                    Thread.sleep(1000L);
                } catch (Exception var3) {
                    break;
                }
            }

        }
    }
}
