//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.script;

import com.grean.station.comm.CommInter;
import com.grean.station.domain.DO.cfg.CfgDev;
import com.grean.station.domain.DO.plc.DefWord;
import com.grean.station.service.MonitorService;
import com.grean.station.utils.Utility;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PLCHostlink3 {
    @Autowired
    ScriptService scriptService;
    private int mAddress;
    private int mChannel;
    private CommInter mInterComm = null;
    private CfgDev mCfgDev = null;
    public List<DefWord> defWordList;
    private boolean[] QStatus = new boolean[128];
    private boolean[] IStatus = new boolean[128];
    private float[] AIValues = new float[8];
    private float[] minAIVal = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
    private float[] maxAIVal = new float[]{1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F};
    private int recvLines = 0;
    private boolean bReset = false;
    private PLCHostlink3.AnalyzeThread analyzeThread;
    private PLCHostlink3.QueryThread queryThread;
    private boolean analyzeExist = false;
    private int analyzeHeart = 0;

    public PLCHostlink3() {
    }

    public void init(CommInter interComm, CfgDev cfgDev, float[] minAI, float[] maxAI) {
        this.mInterComm = interComm;
        this.mAddress = cfgDev.getAddress().intValue();
        this.mChannel = cfgDev.getSerial_channel();
        this.defWordList = this.scriptService.getDefWordList();

        for(int i = 0; i < this.AIValues.length; ++i) {
            this.minAIVal[i] = minAI[i];
            this.maxAIVal[i] = maxAI[i];
        }

        if (this.mInterComm != null && this.mInterComm.isConnect()) {
            this.doMode();
            this.analyzeThread = new PLCHostlink3.AnalyzeThread(3000);
            this.queryThread = new PLCHostlink3.QueryThread();
            this.analyzeThread.start();
            this.queryThread.start();
        }

    }

    public void free() {
        if (this.analyzeThread != null) {
            this.analyzeThread.interrupt();
        }

        if (this.queryThread != null) {
            this.queryThread.interrupt();
        }

    }

    public boolean doReset() {
        byte[] tmpByte = new byte[8];

        for(int i = 0; i < this.defWordList.size(); ++i) {
            if (((DefWord)this.defWordList.get(i)).getType() == 1) {
                Utility.setBit(tmpByte, ((DefWord)this.defWordList.get(i)).getAddress(), ((DefWord)this.defWordList.get(i)).getDefvalue() > 0.0F);
            }
        }

        String strCmd = "@" + String.format("%02d", this.mAddress) + "WR0010";

        for(int i = 0; i < tmpByte.length; ++i) {
            strCmd = strCmd + String.format("00%02x", tmpByte[i]);
        }

        strCmd = strCmd + Utility.xor8(strCmd.getBytes()) + "*\r";
        this.mInterComm.Send(strCmd.getBytes());
        return true;
    }

    public boolean doCmd(int iType, int iAddress) {
        if (this.mInterComm == null) {
            return false;
        } else {
            int iWrite = 0;
            int iReg = iAddress / 8;
            int iOffset = iAddress % 8;

            for(int i = 0; i < 8; ++i) {
                if (i == iOffset) {
                    if (iType == 1) {
                        iWrite += 1 << i;
                    }
                } else if (this.QStatus[8 * iReg + i]) {
                    iWrite += 1 << i;
                }
            }

            String strCmd = "@" + String.format("%02d", this.mAddress) + "WR";
            strCmd = strCmd + String.format("00%02d", 10 + iReg);
            strCmd = strCmd + String.format("00%02x", iWrite).toUpperCase();
            strCmd = strCmd + Utility.xor8(strCmd.getBytes()) + "*\r";
            this.mInterComm.Send(strCmd.getBytes());
            if (MonitorService.autoPlc) {
                return true;
            } else {
                boolean bResult = false;

                try {
                    for(int i = 0; i < 40; ++i) {
                        Thread.sleep(50L);
                        if (this.QStatus[iAddress] == iType > 0) {
                            bResult = true;
                            break;
                        }
                    }

                    if (!bResult) {
                        this.analyzeExist = false;
                        Thread.sleep(5000L);
                    }
                } catch (Exception var9) {
                }

                return bResult;
            }
        }
    }

    public boolean doCmd(int iType, String strTarget) {
        for(int i = 0; i < this.defWordList.size(); ++i) {
            if (((DefWord)this.defWordList.get(i)).getName().equals(strTarget) && ((DefWord)this.defWordList.get(i)).getType() == 1) {
                return this.doCmd(iType, ((DefWord)this.defWordList.get(i)).getAddress());
            }
        }

        return false;
    }

    public boolean doMode() {
        String strCmd = "@" + String.format("%02d", this.mAddress) + "WR00200004";
        strCmd = strCmd + Utility.xor8(strCmd.getBytes()) + "*\r";
        this.mInterComm.Send(strCmd.getBytes());
        return true;
    }

    private class AnalyzeThread extends Thread {
        public int analyzeDelay = 0;

        public AnalyzeThread(int delay) {
            this.analyzeDelay = delay;
        }

        public void run() {
            int disCount = 0;
            int recvLen = 0;
            byte[] bRecv = new byte[256];
            super.run();
            PLCHostlink3.this.analyzeExist = true;
            PLCHostlink3.this.analyzeHeart = 0;
            if (this.analyzeDelay > 0) {
                try {
                    Thread.sleep((long)this.analyzeDelay);
                } catch (InterruptedException var11) {
                }
            }

            PLCHostlink3.this.mInterComm.clearRecv(PLCHostlink3.this.mChannel);

            while(!this.isInterrupted() && !MonitorService.restart && PLCHostlink3.this.analyzeExist) {
                byte[] tmpRecv = PLCHostlink3.this.mInterComm.Recv(PLCHostlink3.this.mChannel);
                if (tmpRecv == null) {
                    try {
                        Thread.sleep(50L);
                        PLCHostlink3.this.analyzeHeart++;
                        ++disCount;
                        if (disCount > 40) {
                            recvLen = 0;
                            PLCHostlink3.this.scriptService.setbConnect(false);
                        }
                    } catch (InterruptedException var10) {
                    }
                } else {
                    disCount = 0;
                    PLCHostlink3.this.scriptService.setbConnect(true);

                    for(int i = 0; i < tmpRecv.length; ++i) {
                        if (recvLen >= 255) {
                            recvLen = 0;
                            break;
                        }

                        bRecv[recvLen] = tmpRecv[i];
                        ++recvLen;
                    }

                    if (recvLen > 8 && bRecv[0] == 64 && bRecv[recvLen - 1] == 13) {
                        int ix;
                        int j;
                        byte bTmp;
                        switch(recvLen) {
                            case 27:
                                ix = 0;

                                for(; ix < 4; ++ix) {
                                    j = Utility.toByte((char)bRecv[7 + 4 * ix]) * 4096 + Utility.toByte((char)bRecv[8 + 4 * ix]) * 256 + Utility.toByte((char)bRecv[9 + 4 * ix]) * 16 + Utility.toByte((char)bRecv[10 + 4 * ix]);
                                    if (j <= 6000) {
                                        PLCHostlink3.this.AIValues[ix] = (float)(j / 6000) * (PLCHostlink3.this.maxAIVal[ix] - PLCHostlink3.this.minAIVal[ix]) + PLCHostlink3.this.minAIVal[ix];
                                    } else if (j > 65280) {
                                        PLCHostlink3.this.AIValues[ix] = 0.0F;
                                    } else {
                                        PLCHostlink3.this.AIValues[ix] = PLCHostlink3.this.maxAIVal[ix];
                                    }
                                }

                                PLCHostlink3.this.scriptService.updateVarList(4, PLCHostlink3.this.QStatus, PLCHostlink3.this.IStatus, PLCHostlink3.this.AIValues);
                                break;
                            case 35:
                                for(ix = 0; ix < 6; ++ix) {
                                    bTmp = Utility.toByte((char)bRecv[10 + 4 * ix]);

                                    for(j = 0; j < 4; ++j) {
                                        PLCHostlink3.this.IStatus[ix * 8 + j] = (bTmp >> j & 255) % 2 > 0;
                                    }

                                    bTmp = Utility.toByte((char)bRecv[9 + 4 * ix]);

                                    for(j = 0; j < 4; ++j) {
                                        PLCHostlink3.this.IStatus[ix * 8 + 4 + j] = (bTmp >> j & 255) % 2 > 0;
                                    }
                                }

                                PLCHostlink3.this.scriptService.updateVarList(2, PLCHostlink3.this.QStatus, PLCHostlink3.this.IStatus, PLCHostlink3.this.AIValues);
                                break;
                            case 43:
                                for(ix = 0; ix < 8; ++ix) {
                                    bTmp = Utility.toByte((char)bRecv[10 + 4 * ix]);

                                    for(j = 0; j < 4; ++j) {
                                        PLCHostlink3.this.QStatus[ix * 8 + j] = (bTmp >> j & 255) % 2 > 0;
                                    }

                                    bTmp = Utility.toByte((char)bRecv[9 + 4 * ix]);

                                    for(j = 0; j < 4; ++j) {
                                        PLCHostlink3.this.QStatus[ix * 8 + 4 + j] = (bTmp >> j & 255) % 2 > 0;
                                    }
                                }

                                PLCHostlink3.this.scriptService.updateVarList(1, PLCHostlink3.this.QStatus, PLCHostlink3.this.IStatus, PLCHostlink3.this.AIValues);
                        }

                        recvLen = 0;
                    }

                    try {
                        Thread.sleep(50L);
                        PLCHostlink3.this.analyzeHeart++;
                    } catch (InterruptedException var9) {
                        PLCHostlink3.this.analyzeExist = false;
                    }
                }
            }

            PLCHostlink3.this.analyzeExist = false;
        }
    }

    private class QueryThread extends Thread {
        private QueryThread() {
        }

        public void run() {
            super.run();
            String strQSend = "@" + String.format("%02d", PLCHostlink3.this.mAddress) + "RR00100008";
            strQSend = strQSend + Utility.xor8(strQSend.getBytes()) + "*\r";

            try {
                Thread.sleep(4000L);
            } catch (InterruptedException var5) {
            }

            while(!this.isInterrupted() && !MonitorService.restart) {
                try {
                    int runCount = PLCHostlink3.this.analyzeHeart;
                    Thread.sleep(500L);
                    PLCHostlink3.this.mInterComm.Send(strQSend.getBytes());
                    Thread.sleep(500L);
                    if (runCount == PLCHostlink3.this.analyzeHeart) {
                        if (PLCHostlink3.this.analyzeExist) {
                            PLCHostlink3.this.analyzeExist = false;
                        }

                        Thread.sleep(500L);
                        PLCHostlink3.this.analyzeThread = PLCHostlink3.this.new AnalyzeThread(0);
                        PLCHostlink3.this.analyzeThread.start();
                    }
                } catch (InterruptedException var4) {
                }
            }

        }
    }
}
