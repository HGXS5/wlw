//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.sample;

import com.grean.station.service.MonitorService;
import com.grean.station.utils.Utility;

public class SampleKeshengSB6000 extends SampleService {
    public SampleKeshengSB6000() {
    }

    private byte[] getRecv(int waitTime, int recvChannel, boolean bQuery) {
        int bLen = 0;
        byte[] bRecv = new byte[1024];
        long tStart = System.currentTimeMillis();

        while(bLen < 512) {
            long tNow = System.currentTimeMillis();
            if (tNow - tStart > (long)waitTime) {
                bLen = 0;
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
                if (bRecv[0] != -52) {
                    bLen = 0;
                } else {
                    if (bLen > 3 && bRecv[1] == -35 && bRecv[2] == -86 && bRecv[bLen - 1] == -69) {
                        break;
                    }

                    try {
                        Thread.sleep(50L);
                    } catch (Exception var12) {
                    }
                }
            }
        }

        if (bLen > 0) {
            byte[] bResult = new byte[bLen];
            System.arraycopy(bRecv, 0, bResult, 0, bLen);
            return bResult;
        } else {
            return null;
        }
    }

    public void doQuery() {
        byte[] bQuery = new byte[]{-86, 57, 48, 48, -69};
        this.mInterComm.clearRecv(2 * this.mChannel);
        this.mInterComm.Send(bQuery);
        byte[] bRecv = this.getRecv(5000, 2 * this.mChannel, true);
        if (bRecv != null) {
            if (bRecv[0] == -52 && bRecv[1] == -35 && bRecv[2] == -86 && bRecv[bRecv.length - 1] == -69) {
                this.sampleVase = (bRecv[3] - 48) * 10 + (bRecv[4] - 48);
                this.setStatus(true);
                Utility.logInfo("SB6000 Sample: 查询成功，通道" + this.mChannel);
            } else {
                this.setStatus(false);
                Utility.logInfo("SB6000 Sample: 查询失败，通道" + this.mChannel + "，返回格式异常");
            }
        } else if (MonitorService.autoData) {
            this.setStatus(true);
        } else {
            this.setStatus(false);
            Utility.logInfo("SB6000 Sample: 查询失败，通道" + this.mChannel + "，无返回");
        }

    }

    public boolean doPrepare() {
        return super.doPrepare();
    }

    public boolean doSample(String strQN) {
        byte[] bCmd = new byte[]{-86, 52, 48, 48, 49, 48, 48, 48, 49, -69};
        int bottleIndex;

        if (strQN == null) {
            this.sampleVase = this.sampleVase % this.vaseSize + 1;
            bottleIndex = this.sampleVase;
        } else {
            bottleIndex = Integer.parseInt(strQN);
        }

        int iVolume = this.sampleVolume;

        for(int i = 0; i < 5; ++i) {
            bCmd[6 - i] = (byte)(iVolume % 10 + 48);
            iVolume /= 10;
        }

        bCmd[7] = (byte)(bottleIndex / 10 + 48);
        bCmd[8] = (byte)(bottleIndex % 10 + 48);
        this.mInterComm.clearRecv(2 * this.mChannel + 1);
        this.mInterComm.Send(bCmd);
        byte[] bRecv = this.getRecv(5000, 2 * this.mChannel, false);
        if (bRecv != null) {
            if (bRecv[0] == -52 && bRecv[1] == -35 && bRecv[2] == -86 && bRecv[bRecv.length - 1] == -69) {
                Utility.logInfo("SB6000 Sample: 留样成功.");
                return true;
            } else {
                this.setStatus(false);
                Utility.logInfo("SB6000 Sample: 留样失败，返回格式异常");
                return false;
            }
        } else if (MonitorService.autoData) {
            this.setStatus(true);
            return true;
        } else {
            this.setStatus(false);
            Utility.logInfo("SB6000 Sample: 留样失败，无返回");
            return false;
        }
    }
}
