//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.sample;

import com.grean.station.service.MonitorService;
import com.grean.station.utils.Utility;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SampleHengdaZSCVIIE extends SampleService {
    public SampleHengdaZSCVIIE() {
    }

    private byte[] getRecv(int waitTime, int recvChannel, boolean bQuery) {
        int bLen = 0;
        byte[] bRecv = new byte[1024];
        long tStart = System.currentTimeMillis();

        while(bLen < 512) {
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
                if (bRecv[0] != -52) {
                    bLen = 0;
                } else {
                    if (bLen >= 6 && bRecv[bLen - 1] == -69 || bLen >= 2 && bRecv[bLen - 1] == -35) {
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
        byte[] bQuery = new byte[]{-86, -128, -69};
        this.mInterComm.clearRecv(2 * this.mChannel);
        this.mInterComm.Send(bQuery);
        byte[] bRecv = this.getRecv(1000, 2 * this.mChannel, true);
        if (bRecv != null) {
            if (bRecv.length == 6 && bRecv[0] == -52 && bRecv[1] == -35 && bRecv[2] == -86 && bRecv[5] == -69) {
                this.setStatus(true);
                Utility.logInfo("HengdaZSCVIIE Sample: 查询成功，通道" + this.mChannel);
            } else {
                this.setStatus(false);
                Utility.logInfo("HengdaZSCVIIE Sample: 查询失败，通道" + this.mChannel + "，返回格式异常");
            }
        } else if (MonitorService.autoData) {
            this.setStatus(true);
        } else {
            this.setStatus(false);
            Utility.logInfo("HengdaZSCVIIE Sample: 查询失败，通道" + this.mChannel + "，无返回");
        }

    }

    public boolean doPrepare() {
        return super.doPrepare();
    }

    public boolean doSample(String strQN) {
        byte[] bCmd = new byte[]{-86, 52, 48, 48, 53, 48, 48, 48, 50, -69};
        int iResult = 1;
        int iVol = this.sampleVolume;

        for(int i = 6; i > 1; --i) {
            bCmd[i] = (byte)(iVol % 10 + 48);
            iVol /= 10;
        }

        bCmd[7] = (byte)(this.sampleVase / 10 + 48);
        bCmd[8] = (byte)(this.sampleVase % 10 + 48);
        this.mInterComm.clearRecv(2 * this.mChannel + 1);
        this.mInterComm.Send(bCmd);
        if (this.sampleExeListener != null && strQN != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String strDataTime = df.format(new Date());
            String strVaseNo = this.sampleVase + "";
            this.sampleExeListener.OnExeEvent(strQN, 3015, iResult, strDataTime, strVaseNo);
        }

        switch(iResult) {
            case 1:
                Utility.logInfo("HengdaZSCVIIE Sample: 留样成功");
                this.sampleVase = this.sampleVase % this.vaseSize + 1;
                return true;
            default:
                Utility.logInfo("HengdaZSCVIIE Sample: 留样失败");
                return false;
        }
    }
}
