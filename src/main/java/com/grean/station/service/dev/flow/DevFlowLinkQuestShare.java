//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.flow;

import com.grean.station.service.MonitorService;
import com.grean.station.utils.Utility;
import java.util.Random;

public class DevFlowLinkQuestShare extends FlowService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevFlowLinkQuestShare() {
    }

    public void doQuery() {
        if (!this.onCmd) {
            int regLength = 20;
            this.mAddress = this.mCfgDev.getAddress().intValue();
            byte[] bQuery = new byte[]{(byte)this.mAddress, 3, 0, 0, 0, 20, 0, 0};
            int crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            bQuery[bQuery.length - 2] = tmp_bytes[2];
            bQuery[bQuery.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
            if (bRecv != null) {
                if (bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 3) {
                    this.channelVals[0] = (double)Utility.getShort(bRecv, 3) + 0.0D;
                    this.channelVals[1] = (double)((float)Utility.getShort(bRecv, 5) + Utility.getByte2Float(bRecv, 7, 2)) + 0.0D;
                    this.channelVals[2] = (double)((float)(bRecv[9] & 255) + Utility.getByte2Float(bRecv, 10, 2)) + 0.0D;
                    this.channelVals[3] = (double)Utility.getInt(bRecv, 12) + 0.0D;
                    this.channelVals[4] = (double)Utility.getShort(bRecv, 16) + 0.0D;
                    this.channelVals[5] = (double)Utility.getInt(bRecv, 18) + 0.0D;
                    this.channelVals[6] = (double)Utility.getShort(bRecv, 22) + 0.0D;
                    this.channelVals[7] = 0.0D;
                    this.channelVals[8] = (double)Utility.getShort(bRecv, 30) + 0.0D;
                    this.channelVals[9] = (double)Utility.getShort(bRecv, 32) + 0.0D;
                    if (Utility.getBit(bRecv, 34, 7)) {
                        this.channelVals[2] = -1.0D * this.channelVals[2];
                    }

                    this.channelVals[10] = (double)((float)Utility.getShort3(bRecv, 36) + Utility.getByte2Float(bRecv, 39, 1)) + 0.0D;
                    this.channelVals[11] = (double)((float)(bRecv[40] & 255) + Utility.getByte2Float(bRecv, 41, 1)) + 0.0D;
                    this.flowDeep = this.channelVals[0];
                    this.flowArea = this.channelVals[10];
                    if (this.flowShare != null && this.flowShare.getFlowArea() != null && this.flowShare.getFlowDeep() != null) {
                        this.channelVals[0] = this.flowShare.getFlowDeep();
                        this.channelVals[10] = this.flowShare.getFlowArea();
                        this.channelVals[1] = this.channelVals[2] * this.channelVals[10];
                        this.channelVals[12] = 300.0D * this.channelVals[2] * this.channelVals[10];
                    }

                    this.setStatus(true);
                    Utility.logInfo("LinkQuestFlow Dev: 查询成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("LinkQuestFlow Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else if (MonitorService.autoData) {
                double dTmp = this.rand.nextDouble() * 10.0D;

                for(int i = 0; i < 8; ++i) {
                    this.channelVals[i] = dTmp + (double)i * 0.5D;
                }

                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("LinkQuestFlow Dev: 查询失败，通道" + this.mChannel + "，无返回");
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
}
