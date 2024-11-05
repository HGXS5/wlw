//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev;

import com.grean.station.service.MonitorService;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Random;

public class DevDctF12 extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevDctF12() {
    }

    private byte[] getRecv(int waitTime, int recvChannel, boolean bQuery) {
        int bLen = 0;
        byte[] bRecv = new byte[256];
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
                if (bLen >= 10 && bRecv[0] == 50 && bRecv[bLen - 1] == 51) {
                    break;
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var12) {
                }
            }
        }

        if (bLen >= 10 && bRecv[0] == 50 && bRecv[bLen - 1] == 51) {
            byte[] bResult = new byte[bLen];
            System.arraycopy(bRecv, 0, bResult, 0, bLen);
            return bResult;
        } else {
            return null;
        }
    }

    public void doQuery() {
        if (!this.onCmd) {
            this.mAddress = this.mCfgDev.getAddress().intValue();
            String strAddress = String.format("%02x", this.mAddress);
            byte[] bAddress = strAddress.getBytes();
            byte[] bQuery = new byte[]{2, bAddress[bAddress.length - 2], bAddress[bAddress.length - 1]};
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            double dTmp = 0.0D;
            byte[] bRecv = this.getRecv(this.waitAnswer, 2 * this.mChannel, true);
            if (bRecv != null) {
                if (bRecv.length >= 10 && bRecv[0] == 50 && bRecv[bRecv.length - 1] == 51) {
                    String strTmp = new String(bRecv);
                    float testResult = Float.parseFloat(strTmp.substring(3, 7));
                    int digitLen = Integer.parseInt(strTmp.substring(7, 8));
                    if (digitLen > 0) {
                        for(int i = 0; i < digitLen; ++i) {
                            testResult /= 10.0F;
                        }
                    }

                    testResult /= 1000.0F;
                    this.setParamTestVal((double)testResult, "N", (Timestamp)null);
                    this.setStatus(true);
                    Utility.logInfo("DctF12 Dev: 查询成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("DctF12 Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else if (MonitorService.autoData) {
                dTmp = this.rand.nextDouble() * 10.0D;

                for(int i = 0; i < 8; ++i) {
                    this.channelVals[i] = dTmp + (double)i * 0.5D;
                }

                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("DctF12 Dev: 查询失败，通道" + this.mChannel + "，无返回");
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
                DevDctF12.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
        this.doDevCmdThread(10, (String)null, (String)null);
    }

    public void doStopCmd() {
        this.initDevState(0);
        this.doDevCmdThread(11, (String)null, (String)null);
    }

    public void doRsetCmd() {
    }

    public void doSetTime(String strQN, String strParam) {
    }
}
