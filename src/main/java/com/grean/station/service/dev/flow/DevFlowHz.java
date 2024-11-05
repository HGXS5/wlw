//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.flow;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.util.Random;

public class DevFlowHz extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevFlowHz() {
    }

    public void doQuery() {
        if (!this.onCmd) {
            int regLength = 19;
            this.mAddress = this.mCfgDev.getAddress().intValue();
            byte[] bQuery = new byte[]{(byte)this.mAddress, 3, 0, 8, 0, 19, 0, 0};
            int crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            bQuery[bQuery.length - 2] = tmp_bytes[2];
            bQuery[bQuery.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
            if (bRecv != null) {
                if (bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 3) {
                    int iCount = Utility.getShort(bRecv, 3);
                    this.channelVals[0] = (double)Utility.getFloatLittleEndian(bRecv, 29) + 0.0D;
                    this.channelVals[1] = (double)Utility.getFloatLittleEndian(bRecv, 17) + 0.0D;
                    this.channelVals[2] = (double)Utility.getFloatLittleEndian(bRecv, 25) + 0.0D;
                    this.channelVals[3] = (double)((float)(iCount * 1000000) + Utility.getFloatLittleEndian(bRecv, 37)) + 0.0D;
                    this.setStatus(true);
                    Utility.logInfo("HzFlow Dev: 查询成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("HzFlow Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else if (MonitorService.autoData) {
                double dTmp = this.rand.nextDouble() * 10.0D;

                for(int i = 0; i < 8; ++i) {
                    this.channelVals[i] = dTmp + (double)i * 0.5D;
                }

                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("HzFlow Dev: 查询失败，通道" + this.mChannel + "，无返回");
            }

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

    public void saveResult() {
    }

    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        return false;
    }

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                DevFlowHz.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
        this.doDevCmdThread(8, strParam, strQN);
    }

    public void doStdCal(String strQN, String strParam) {
        this.doDevCmdThread(9, strParam, strQN);
    }

    public void doInitCmd() {
        this.doDevCmdThread(10, (String)null, (String)null);
    }

    public void doStopCmd() {
        this.doDevCmdThread(11, (String)null, (String)null);
    }

    public void doRsetCmd() {
        this.doDevCmdThread(12, (String)null, (String)null);
    }

    public void doSetTime(String strQN, String strParam) {
        this.doDevCmdThread(13, strParam, strQN);
    }
}
