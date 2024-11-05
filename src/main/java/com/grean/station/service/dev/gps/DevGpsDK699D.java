//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.gps;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.CrcUtil;
import com.grean.station.utils.Utility;

public class DevGpsDK699D extends DevService {
    private DevGpsDK699D.QueryThread queryThread;
    private double dLat;
    private double dLong;
    private double dSpeed;

    public DevGpsDK699D() {
    }

    public void doQuery() {
    }

    private byte[] getAnswerPkg(byte[] reqPkg, int reqSize, byte reqCmd) {
        byte[] bPkg = new byte[]{120, 120, 5, 1, 0, 1, 0, 0, 13, 10};
        bPkg[3] = reqCmd;
        bPkg[4] = reqPkg[reqSize - 6];
        bPkg[5] = reqPkg[reqSize - 5];
        int crc_result = CrcUtil.getCrc16_X25(bPkg, 2, 4);
        byte[] tmp_bytes = Utility.getByteArray(crc_result);
        bPkg[6] = tmp_bytes[2];
        bPkg[7] = tmp_bytes[3];
        return bPkg;
    }

    private void getData(byte[] reqPkg, int reqSize, byte reqCmd) {
        this.dLat = (double)Utility.getInt(reqPkg, 11) / 1800000.0D;
        this.dLong = (double)Utility.getInt(reqPkg, 15) / 1800000.0D;
        this.dSpeed = (double)(reqPkg[19] & 255);
        this.channelVals[0] = this.dLat;
        this.channelVals[1] = this.dLong;
        this.channelVals[2] = this.dSpeed;
    }

    public void startQuery() {
        this.queryThread = new DevGpsDK699D.QueryThread();
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
            } catch (Exception var8) {
            }

            if (DevGpsDK699D.this.mInterComm != null) {
                while(!this.isInterrupted() && !MonitorService.restart) {
                    try {
                        byte[] tmpRecv = DevGpsDK699D.this.mInterComm.Recv(DevGpsDK699D.this.mChannel);
                        if (tmpRecv == null) {
                            Thread.sleep(50L);
                            ++disCount;
                            if (disCount > 200) {
                                recvLen = 0;
                                DevGpsDK699D.this.setStatus(false);
                            }
                        } else {
                            disCount = 0;
                            DevGpsDK699D.this.setStatus(true);

                            for(int i = 0; i < tmpRecv.length; ++i) {
                                bRecv[recvLen] = tmpRecv[i];
                                ++recvLen;
                                if (recvLen > 250) {
                                    recvLen = 0;
                                    break;
                                }
                            }

                            if (recvLen >= 8) {
                                if (bRecv[0] == 120 && bRecv[1] == 120) {
                                    if (recvLen == (bRecv[2] & 255) + 5 && bRecv[recvLen - 2] == 13 && bRecv[recvLen - 1] == 10) {
                                        int crc_result = CrcUtil.getCrc16_X25(bRecv, 2, recvLen - 6);
                                        byte[] tmp_bytes = Utility.getByteArray(crc_result);
                                        if (bRecv[recvLen - 4] == tmp_bytes[2] && bRecv[recvLen - 3] == tmp_bytes[3]) {
                                            int recvCmd = bRecv[3] & 255;
                                            switch(recvCmd) {
                                                case 1:
                                                    DevGpsDK699D.this.mInterComm.Send(DevGpsDK699D.this.getAnswerPkg(bRecv, recvLen, (byte)1));
                                                    break;
                                                case 19:
                                                    DevGpsDK699D.this.mInterComm.Send(DevGpsDK699D.this.getAnswerPkg(bRecv, recvLen, (byte)19));
                                                    break;
                                                case 34:
                                                    DevGpsDK699D.this.getData(bRecv, recvLen, (byte)34);
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
                    } catch (Exception var9) {
                        break;
                    }
                }

            }
        }
    }
}
