//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.env;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.TimeHelper;
import java.sql.Timestamp;

public class EnvDoor extends DevService {
    private String accessPwd;
    private Timestamp accesssTime;
    private String tmpPwd = "";
    private EnvDoor.QueryThread queryThread;

    public EnvDoor() {
    }

    public String getAccessPwd() {
        return this.accessPwd;
    }

    public void setAccessPwd(String accessPwd) {
        this.accessPwd = accessPwd;
    }

    public Timestamp getAccesssTime() {
        return this.accesssTime;
    }

    public void setAccesssTime(Timestamp accesssTime) {
        this.accesssTime = accesssTime;
    }

    public void doQuery() {
        byte[] bRecv = this.mInterComm.Recv(2 * this.mChannel);
        if (bRecv != null) {
            for(int i = 0; i < bRecv.length; ++i) {
                if (bRecv[i] == 11) {
                    this.accesssTime = TimeHelper.getCurrentTimestamp();
                    this.accessPwd = this.tmpPwd;
                    this.tmpPwd = "";
                    break;
                }

                this.tmpPwd = this.tmpPwd + String.format("%d", bRecv[i]);
            }
        }

        this.setStatus(true);
    }

    public void startQuery() {
        this.queryThread = new EnvDoor.QueryThread();
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
                    if (EnvDoor.this.mInterComm != null) {
                        EnvDoor.this.doQuery();
                    }

                    Thread.sleep(100L);
                } catch (Exception var3) {
                    break;
                }
            }

        }
    }
}
