//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.gps;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;

public class DevGpsGN150 extends DevService {
    private DevGpsGN150.QueryThread queryThread;
    private double dLat;
    private double dLong;
    private double dSpeed;

    public DevGpsGN150() {
    }

    public void doQuery() {
    }

    public void startQuery() {
        this.queryThread = new DevGpsGN150.QueryThread();
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
        @SuppressWarnings("FallThrough")
        public void run() {
            int disCount = 0;
            int recvLen = 0;
            byte[] bRecv = new byte[256];
            super.run();

            try {
                Thread.sleep(3000L);
            } catch (Exception var12) {
            }

            if (DevGpsGN150.this.mInterComm != null) {
                while(!this.isInterrupted() && !MonitorService.restart) {
                    try {
                        byte[] tmpRecv = DevGpsGN150.this.mInterComm.Recv(DevGpsGN150.this.mChannel);
                        if (tmpRecv == null) {
                            Thread.sleep(50L);
                            ++disCount;
                            if (disCount > 200) {
                                recvLen = 0;
                                DevGpsGN150.this.setStatus(false);
                            }
                        } else {
                            disCount = 0;
                            DevGpsGN150.this.setStatus(true);

                            for(int i = 0; i < tmpRecv.length; ++i) {
                                bRecv[recvLen] = tmpRecv[i];
                                ++recvLen;
                                if (recvLen > 250) {
                                    recvLen = 0;
                                    break;
                                }
                            }

                            if (recvLen >= 8) {
                                if (bRecv[0] == 36) {
                                    if (bRecv[recvLen - 5] == 42 && bRecv[recvLen - 2] == 13 && bRecv[recvLen - 1] == 10) {
                                        String recvCheckSum = String.format("%c%c", bRecv[recvLen - 4], bRecv[recvLen - 3]);
                                        String calCheckSum = Utility.xor8(bRecv, 1, recvLen - 6);
                                        if (recvCheckSum.equals(calCheckSum)) {
                                            String strRecv = new String(bRecv, 0, recvLen);
                                            String[] strValue = strRecv.split(",");
                                            if (strValue.length > 3) {
                                                String var10 = strValue[0];
                                                byte var11 = -1;
                                                switch(var10.hashCode()) {
                                                    case 1098673370:
                                                        if (var10.equals("$GPGLL")) {
                                                            var11 = 0;
                                                        }
                                                    default:
                                                        switch(var11) {
                                                            case 0:
                                                                DevGpsGN150.this.channelVals[0] = (double)Integer.parseInt(strValue[1].substring(0, 2)) + Double.parseDouble(strValue[1].substring(2)) / 60.0D;
                                                                DevGpsGN150.this.channelVals[1] = (double)Integer.parseInt(strValue[3].substring(0, 3)) + Double.parseDouble(strValue[3].substring(3)) / 60.0D;
                                                        }
                                                }
                                            }
                                        }

                                        recvLen = 0;
                                    }
                                } else {
                                    recvLen = 0;
                                }
                            }

                            Thread.sleep(50L);
                        }
                    } catch (Exception var13) {
                        break;
                    }
                }

            }
        }
    }
}
