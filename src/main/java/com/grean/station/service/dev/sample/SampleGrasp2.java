//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.sample;

import com.grean.station.service.MonitorService;
import com.grean.station.utils.Utility;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SampleGrasp2 extends SampleService {
    private SampleGrasp2.QueryThread queryThread;

    public SampleGrasp2() {
    }

    public void startQuery() {
        this.queryThread = new SampleGrasp2.QueryThread();
        this.queryThread.start();
    }

    public void doQuery() {
        for(int i = 0; i < this.vaseSize; ++i) {
            int regAdderss = 210 + 10 * i;
            byte bRegHigh = (byte)(regAdderss / 256);
            byte bRegLow = (byte)(regAdderss % 256);
            byte[] bQuery = new byte[]{(byte)this.mAddress, 3, bRegHigh, bRegLow, 0, 10, 0, 0};
            int crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            bQuery[bQuery.length - 2] = tmp_bytes[2];
            bQuery[bQuery.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
            if (bRecv != null) {
                if (bRecv.length == 25 && bRecv[0] == this.mAddress && bRecv[1] == 3) {
                    this.setStatus(true);
                    if (i < this.vaseState.length) {
                        if (bRecv[4] == 1) {
                            this.vaseState[i] = 1;
                        } else {
                            this.vaseState[i] = 0;
                        }
                    }

                    Utility.logInfo("Grasp2 Sample: 查询" + i + "成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("Grasp2 Sample: 查询" + i + "失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else if (MonitorService.autoData) {
                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("Grasp2 Sample: 查询" + i + "失败，通道" + this.mChannel + "，无返回");
            }

            try {
                Thread.sleep(1000L);
            } catch (Exception var10) {
            }
        }

    }

    public int getEmptySampleVase() {
        for(int i = 0; i < this.vaseSize; ++i) {
            if (this.vaseState[i] == 0) {
                return i + 1;
            }
        }

        return 0;
    }

    private byte[] getSampleCmd(int vaseIndex) {
        byte[] bCmd = new byte[]{(byte)this.mAddress, 16, 0, 110, 0, 4, 8, 0, 1, 0, -56, 0, 0, 0, 0, 46, 61};
        if (vaseIndex == 0) {
            this.sampleVase = this.sampleVase % this.vaseSize + 1;
            bCmd[8] = (byte)this.sampleVase;
        } else {
            if (vaseIndex > this.vaseSize) {
                return null;
            }

            bCmd[8] = (byte)vaseIndex;
        }

        bCmd[9] = (byte)(this.sampleVolume / 256);
        bCmd[10] = (byte)(this.sampleVolume % 256);
        int crc_result = Utility.calcCrc16(bCmd, 0, bCmd.length - 2);
        byte[] tmp_bytes = Utility.getByteArray(crc_result);
        bCmd[bCmd.length - 2] = tmp_bytes[2];
        bCmd[bCmd.length - 1] = tmp_bytes[3];
        return bCmd;
    }

    public boolean doSample(String strQN) {
        int iResult = 1;
        boolean addVase = false;
        byte[] bCmd;
        if (strQN == null) {
            bCmd = this.getSampleCmd(0);
            addVase = true;
        } else if (strQN.length() > 2) {
            bCmd = this.getSampleCmd(0);
            addVase = true;
        } else {
            bCmd = this.getSampleCmd(Integer.parseInt(strQN));
        }

        if (bCmd == null) {
            return false;
        } else {
            this.mInterComm.clearRecv(2 * this.mChannel + 1);
            this.mInterComm.Send(bCmd);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel + 1, false, 2000);
            if (bRecv != null && bRecv.length == 8) {
                for(int i = 0; i < 6; ++i) {
                    if (bRecv[i] != bCmd[i]) {
                        iResult = 2;
                    }
                }
            } else {
                iResult = 2;
            }

            if (this.sampleExeListener != null && strQN != null && strQN.length() > 2) {
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                String strDataTime = df.format(new Date());
                String strVaseNo = this.sampleVase + "";
                this.sampleExeListener.OnExeEvent(strQN, 3015, iResult, strDataTime, strVaseNo);
            }

            switch(iResult) {
                case 1:
                    Utility.logInfo("Grasp2 Sample: 留样成功");
                    this.cfgSample.setBottle_id(this.sampleVase);
                    return true;
                default:
                    Utility.logInfo("Grasp2 Sample: 留样失败");
                    if (addVase) {
                        --this.sampleVase;
                    }

                    return false;
            }
        }
    }

    private byte[] getEmptyCmd(int vaseIndex) {
        byte[] bCmd = new byte[]{(byte)this.mAddress, 16, 0, 115, 0, 1, 2, 0, 1, 108, -109};
        if (vaseIndex == 0) {
            bCmd[8] = (byte)this.sampleVase;
        } else {
            if (vaseIndex > this.vaseSize) {
                return null;
            }

            bCmd[8] = (byte)vaseIndex;
        }

        int crc_result = Utility.calcCrc16(bCmd, 0, bCmd.length - 2);
        byte[] tmp_bytes = Utility.getByteArray(crc_result);
        bCmd[bCmd.length - 2] = tmp_bytes[2];
        bCmd[bCmd.length - 1] = tmp_bytes[3];
        return bCmd;
    }

    public boolean doEmpty(String strQN) {
        int iResult = 1;
        byte[] bCmd;
        if (strQN == null) {
            bCmd = this.getEmptyCmd(0);
        } else if (strQN.length() > 2) {
            bCmd = this.getEmptyCmd(0);
        } else {
            bCmd = this.getEmptyCmd(Integer.parseInt(strQN));
        }

        if (bCmd == null) {
            return false;
        } else {
            this.mInterComm.clearRecv(2 * this.mChannel + 1);
            this.mInterComm.Send(bCmd);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel + 1, false, 2000);
            if (bRecv != null && bRecv.length == 8) {
                for(int i = 0; i < 6; ++i) {
                    if (bRecv[i] != bCmd[i]) {
                        iResult = 2;
                    }
                }
            } else {
                iResult = 2;
            }

            switch(iResult) {
                case 1:
                    Utility.logInfo("Grasp2 Sample: 排空成功");
                    return true;
                default:
                    Utility.logInfo("Grasp2 Sample: 排空失败");
                    return false;
            }
        }
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
                    if (SampleGrasp2.this.mInterComm != null) {
                        SampleGrasp2.this.doQuery();
                    }

                    Thread.sleep(1000L);
                } catch (Exception var3) {
                    break;
                }
            }

        }
    }
}
