//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev;

import com.grean.station.service.MonitorService;
import java.sql.Timestamp;

public class DevZetian212 extends DevService {
    private boolean onCmd = false;
    private boolean cmdResult = false;
    private String recvString = "";
    private DevZetian212.QueryThread queryThread;

    public DevZetian212() {
    }
    @SuppressWarnings("FallThrough")
    public void doQuery() {
        byte[] bRecv = this.mInterComm.Recv(2 * this.mChannel);
        if (bRecv != null) {
            this.recvString = this.recvString + new String(bRecv);
            if (this.recvString.length() > 20 && this.recvString.substring(0, 2).equals("##") && this.recvString.substring(this.recvString.length() - 2, this.recvString.length()).equals("\r\n")) {
                int cnIndex;
                int cnType = 0;
                int startIndex;
                int stopIndex = -1;
                cnIndex = this.recvString.indexOf("CN=");
                if (cnIndex > -1) {
                    cnType = Integer.parseInt(this.recvString.substring(cnIndex + 3, cnIndex + 7));
                }

                switch(cnType) {
                    case 2011:
                        startIndex = this.recvString.indexOf("-Rtd=");
                        if (startIndex > -1) {
                            stopIndex = this.recvString.indexOf(",", startIndex);
                        }

                        if (startIndex > -1 && stopIndex > -1) {
                            float testResult = Float.parseFloat(this.recvString.substring(startIndex + 5, stopIndex));
                            this.setParamTestVal((double)testResult, "N", (Timestamp)null);
                        }
                    case 2013:
                    default:
                        break;
                    case 3011:
                        this.cmdResult = true;
                        break;
                    case 3012:
                        this.cmdResult = true;
                        break;
                    case 3015:
                        this.cmdResult = true;
                        break;
                    case 3016:
                        this.cmdResult = true;
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
        this.queryThread = new DevZetian212.QueryThread();
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
        int iResult = 4;
        this.initDevState(cmdType);
        if (MonitorService.autoData) {
            return true;
        } else {
            this.mCmdType = cmdType;
            String strCmd;
            switch(this.mCmdType) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                default:
                    strCmd = "##0058ST=32;CN=3012;PW=123456;MN=00000000000001;CP=&&DataTime=&&FB41\r\n";
                    break;
                case 8:
                case 9:
                    strCmd = "##0058ST=32;CN=3011;PW=123456;MN=00000000000001;CP=&&DataTime=&&F781\r\n";
                    break;
                case 10:
                    strCmd = "##0058ST=32;CN=3015;PW=123456;MN=00000000000001;CP=&&DataTime=&&E381\r\n";
                    break;
                case 11:
                    strCmd = "##0058ST=32;CN=3016;PW=123456;MN=00000000000001;CP=&&DataTime=&&EF41\r\n";
                    break;
                case 12:
                case 13:
                    return false;
            }

            this.onCmd = true;
            byte[] bCmd = strCmd.getBytes();
            int iTimes = this.mCmdTimes;

            for(this.cmdResult = false; iTimes > 0; --iTimes) {
                this.mInterComm.clearRecv(2 * this.mChannel + 1);
                this.mInterComm.Send(bCmd);

                try {
                    Thread.sleep(2000L);
                } catch (Exception var9) {
                }

                if (this.cmdResult) {
                    iResult = 1;
                    break;
                }
            }

            if (cmdQN != null && this.devExeListener != null) {
                this.devExeListener.OnExeEvent(cmdQN, cmdType, iResult);
            }

            this.onCmd = false;
            this.saveCmdResult("Zetian212", iResult);
            return iResult == 1;
        }
    }

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                DevZetian212.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
                    if (DevZetian212.this.mInterComm != null) {
                        DevZetian212.this.doQuery();
                    }

                    Thread.sleep(1000L);
                } catch (Exception var3) {
                    break;
                }
            }

        }
    }
}
