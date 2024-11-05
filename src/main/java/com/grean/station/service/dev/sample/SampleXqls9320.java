//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.sample;

import com.grean.station.service.MonitorService;
import com.grean.station.utils.Utility;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SampleXqls9320 extends SampleService {
    public SampleXqls9320() {
    }

    private byte[] getRecv(int waitTime, int recvChannel, boolean bQuery) {
        int bLen = 0;
        byte[] bRecv = new byte[128];
        long tStart = System.currentTimeMillis();

        while(bLen < 100) {
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
                if (bRecv[0] != 62) {
                    bLen = 0;
                } else {
                    if (bLen >= 6 && bRecv[0] == 62 && bRecv[1] == 83 && bRecv[2] == 85 && bRecv[3] == 67 && bRecv[bLen - 2] == 13 && bRecv[bLen - 1] == 10) {
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
        byte[] bQuery = new byte[]{35, 82, 48, 51, 37, 13, 10};
        this.mInterComm.clearRecv(2 * this.mChannel);
        this.mInterComm.Send(bQuery);
        byte[] bRecv = this.getRecv(2000, 2 * this.mChannel, true);
        if (bRecv != null) {
            if (bRecv.length >= 64) {
                String strRecv = new String(bRecv);

                for(int i = 0; i < 12; ++i) {
                    String strTmp = strRecv.substring(5 + 5 * i, 9 + 5 * i);
                    this.vaseState[i] = Integer.parseInt(strTmp);
                }

                this.setStatus(true);
                Utility.logInfo("Xqls9320 Sample: 查询成功，通道" + this.mChannel);
            } else {
                this.setStatus(false);
                Utility.logInfo("Xqls9320 Sample: 查询失败，通道" + this.mChannel + "，返回格式异常");
            }
        } else if (MonitorService.autoData) {
            this.setStatus(true);
        } else {
            this.setStatus(false);
            Utility.logInfo("Xqls9320 Sample: 查询失败，通道" + this.mChannel + "，无返回");
        }

    }

    public boolean doPrepare() {
        return super.doPrepare();
    }

    public boolean doSample(String strQN) {
        byte[] bCmd = new byte[]{35, 87, 48, 49, 36, 49, 36, 48, 56, 48, 48, 36, 48, 49, 36, 48, 48, 48, 37, 13, 10};
        int iResult = 1;
        int iVol = this.sampleVolume;

        for(int i = 10; i > 6; --i) {
            bCmd[i] = (byte)(iVol % 10 + 48);
            iVol /= 10;
        }

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
                Utility.logInfo("Xqls9320 Sample: 留样成功");
                this.sampleVase = this.sampleVase % this.vaseSize + 1;
                return true;
            default:
                Utility.logInfo("Xqls9320 Sample: 留样失败");
                return false;
        }
    }
}
