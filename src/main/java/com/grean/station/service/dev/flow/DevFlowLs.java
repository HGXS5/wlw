//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.flow;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Random;

public class DevFlowLs extends DevService {
    private DevFlowLs.QueryThread queryThread;
    private Random rand = new Random();

    public DevFlowLs() {
    }

    public void doQuery() {
        int regLength = 23;
        this.mAddress = this.mCfgDev.getAddress().intValue();
        byte[] bQuery = new byte[]{(byte)this.mAddress, 3, 0, 1, 0, 23, 0, 0};
        int crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
        byte[] tmp_bytes = Utility.getByteArray(crc_result);
        bQuery[bQuery.length - 2] = tmp_bytes[2];
        bQuery[bQuery.length - 1] = tmp_bytes[3];
        this.mInterComm.clearRecv(2 * this.mChannel);
        this.mInterComm.Send(bQuery);
        byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, this.waitAnswer);
        if (bRecv != null) {
            if (bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 3) {
                int i;
                float fTmp;
                for(i = 0; i < 7; ++i) {
                    fTmp = Utility.getFloatBigEndian(bRecv, 3 + 4 * i);
                    this.channelVals[i] = Double.parseDouble(fTmp + "");
                }

                for(i = 0; i < 4; ++i) {
                    fTmp = Utility.getFloatBigEndian(bRecv, 3 + 4 * (7 + i));
                    this.params[i] = Double.parseDouble(fTmp + "");
                }

                this.params[4] = Double.parseDouble(Utility.getShort(bRecv, 47) + "");
                this.setStatus(true);
                Utility.logInfo("FlowLs Dev: 查询成功，通道" + this.mChannel);
            } else {
                this.setStatus(false);
                Utility.logInfo("FlowLs Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
            }
        } else if (MonitorService.autoData) {
            double dTmp = this.rand.nextDouble() * this.mCfgFactor.getAlarmMax();
            this.setParamTestVal(dTmp, "N", (Timestamp)null);
            this.setStatus(true);
        } else {
            this.setStatus(false);
            Utility.logInfo("FlowLs Dev: 查询失败，通道" + this.mChannel + "，无返回");
        }

    }

    public void setStatus(boolean connected) {
        if (connected) {
            this.bConnect = true;
            this.disCount = 0;
        } else {
            ++this.disCount;
            if (this.disCount > 2) {
                this.bConnect = false;
            }
        }

    }

    public void startQuery() {
        this.queryThread = new DevFlowLs.QueryThread();
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
                    if (DevFlowLs.this.mInterComm != null) {
                        DevFlowLs.this.doQuery();
                    }

                    for(int i = 0; i < 3000; ++i) {
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
