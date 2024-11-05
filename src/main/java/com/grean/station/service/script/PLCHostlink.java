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
public class PLCHostlink {
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
    private PLCHostlink.AnalyzeThread analyzeThread;
    private PLCHostlink.QueryThread queryThread;
    private boolean analyzeExist = false;
    private int analyzeHeart = 0;

    public PLCHostlink() {
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
            this.analyzeThread = new PLCHostlink.AnalyzeThread(3000);
            this.queryThread = new PLCHostlink.QueryThread();
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

        String strCmd = "@" + String.format("%02d", this.mAddress) + "WR0100";

        int iTmp;
        for(iTmp = 0; iTmp < tmpByte.length; ++iTmp) {
            strCmd = strCmd + String.format("00%02x", tmpByte[iTmp]);
        }

        strCmd = strCmd + Utility.xor8(strCmd.getBytes()) + "*\r";
        iTmp = this.recvLines;
        this.scriptService.saveScriptLog("PLC Hostlink doReset recvLines: " + this.recvLines);
        this.mInterComm.clearSend();
        this.mInterComm.clearRecv(this.mChannel);
        this.mInterComm.Send(strCmd.getBytes());

        try {
            for(int i = 0; i < 80; ++i) {
                Thread.sleep(50L);
                if (iTmp != this.recvLines) {
                    return true;
                }
            }
        } catch (Exception var5) {
        }

        return false;
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
            strCmd = strCmd + String.format("01%02d", iReg);
            strCmd = strCmd + String.format("00%02x", iWrite).toUpperCase();
            strCmd = strCmd + Utility.xor8(strCmd.getBytes()) + "*\r";
            int iTmp = this.recvLines;
            this.scriptService.saveScriptLog("PLC Hostlink doCmd recvLines: " + this.recvLines);
            this.mInterComm.clearSend();
            this.mInterComm.clearRecv(this.mChannel);
            this.mInterComm.Send(strCmd.getBytes());
            if (MonitorService.autoPlc) {
                return true;
            } else {
                boolean bResult = false;

                try {
                    for(int i = 0; i < 80; ++i) {
                        Thread.sleep(50L);
                        if (this.QStatus[iAddress] == iType > 0) {
                            bResult = true;
                            break;
                        }
                    }
                } catch (Exception var10) {
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
            PLCHostlink.this.analyzeExist = true;
            PLCHostlink.this.analyzeHeart = 0;
            if (this.analyzeDelay > 0) {
                try {
                    Thread.sleep((long)this.analyzeDelay);
                } catch (InterruptedException var12) {
                }
            }

            PLCHostlink.this.mInterComm.clearRecv(PLCHostlink.this.mChannel);

            while(!this.isInterrupted() && !MonitorService.restart && PLCHostlink.this.analyzeExist) {
                byte[] tmpRecv = PLCHostlink.this.mInterComm.Recv(PLCHostlink.this.mChannel);
                if (tmpRecv == null) {
                    try {
                        Thread.sleep(50L);
                        PLCHostlink.this.analyzeHeart++;
                        ++disCount;
                        if (disCount > 40) {
                            recvLen = 0;
                            PLCHostlink.this.scriptService.setbConnect(false);
                        }
                    } catch (InterruptedException var11) {
                    }
                } else {
                    disCount = 0;
                    PLCHostlink.this.scriptService.setbConnect(true);

                    for(int i = 0; i < tmpRecv.length; ++i) {
                        if (recvLen >= 64) {
                            recvLen = 0;
                            break;
                        }

                        bRecv[recvLen] = tmpRecv[i];
                        ++recvLen;
                    }

                    if (recvLen > 8 && bRecv[0] == 64 && bRecv[recvLen - 1] == 13) {
                        String strRecv = Utility.bytesToHexString(bRecv, recvLen);
                        PLCHostlink.this.scriptService.saveScriptLog("PLC Hostlink AnalyzeThread recvs: " + strRecv);
                        int ix;
                        int j;
                        byte bTmp;
                        switch(recvLen) {
                            case 27:
                                ix = 0;

                                for(; ix < 4; ++ix) {
                                    j = Utility.toByte((char)bRecv[7 + 4 * ix]) * 4096 + Utility.toByte((char)bRecv[8 + 4 * ix]) * 256 + Utility.toByte((char)bRecv[9 + 4 * ix]) * 16 + Utility.toByte((char)bRecv[10 + 4 * ix]);
                                    if (j <= 6000) {
                                        PLCHostlink.this.AIValues[ix] = (float)(j / 6000) * (PLCHostlink.this.maxAIVal[ix] - PLCHostlink.this.minAIVal[ix]) + PLCHostlink.this.minAIVal[ix];
                                    } else if (j > 65280) {
                                        PLCHostlink.this.AIValues[ix] = 0.0F;
                                    } else {
                                        PLCHostlink.this.AIValues[ix] = PLCHostlink.this.maxAIVal[ix];
                                    }
                                }

                                PLCHostlink.this.scriptService.updateVarList(4, PLCHostlink.this.QStatus, PLCHostlink.this.IStatus, PLCHostlink.this.AIValues);
                                break;
                            case 35:
                                for(ix = 0; ix < 6; ++ix) {
                                    bTmp = Utility.toByte((char)bRecv[10 + 4 * ix]);

                                    for(j = 0; j < 4; ++j) {
                                        PLCHostlink.this.IStatus[ix * 8 + j] = (bTmp >> j & 255) % 2 > 0;
                                    }

                                    bTmp = Utility.toByte((char)bRecv[9 + 4 * ix]);

                                    for(j = 0; j < 4; ++j) {
                                        PLCHostlink.this.IStatus[ix * 8 + 4 + j] = (bTmp >> j & 255) % 2 > 0;
                                    }
                                }

                                PLCHostlink.this.scriptService.updateVarList(2, PLCHostlink.this.QStatus, PLCHostlink.this.IStatus, PLCHostlink.this.AIValues);
                                break;
                            case 43:
                                for(ix = 0; ix < 8; ++ix) {
                                    bTmp = Utility.toByte((char)bRecv[10 + 4 * ix]);

                                    for(j = 0; j < 4; ++j) {
                                        PLCHostlink.this.QStatus[ix * 8 + j] = (bTmp >> j & 255) % 2 > 0;
                                    }

                                    bTmp = Utility.toByte((char)bRecv[9 + 4 * ix]);

                                    for(j = 0; j < 4; ++j) {
                                        PLCHostlink.this.QStatus[ix * 8 + 4 + j] = (bTmp >> j & 255) % 2 > 0;
                                    }
                                }

                                PLCHostlink.this.scriptService.updateVarList(1, PLCHostlink.this.QStatus, PLCHostlink.this.IStatus, PLCHostlink.this.AIValues);
                                break;
                            default:
                                PLCHostlink.this.recvLines++;
                                PLCHostlink.this.scriptService.saveScriptLog("PLC Hostlink AnalyzeThread recvLines: " + PLCHostlink.this.recvLines);
                        }

                        recvLen = 0;
                    }

                    try {
                        Thread.sleep(50L);
                        PLCHostlink.this.analyzeHeart++;
                    } catch (InterruptedException var10) {
                        PLCHostlink.this.scriptService.logger.info("PLC Hostlink AnalyzeThread Exception: " + var10.toString());
                    }
                }
            }

            PLCHostlink.this.scriptService.logger.info("PLC Hostlink AnalyzeThread Exit");
        }
    }

    private class QueryThread extends Thread {
        private QueryThread() {
        }

        public void run() {
            super.run();
            String strISend = "@" + String.format("%02d", PLCHostlink.this.mAddress) + "RR00000006";
            strISend = strISend + Utility.xor8(strISend.getBytes()) + "*\r";
            String strQSend = "@" + String.format("%02d", PLCHostlink.this.mAddress) + "RR01000008";
            strQSend = strQSend + Utility.xor8(strQSend.getBytes()) + "*\r";
            String strAISend = "@" + String.format("%02d", PLCHostlink.this.mAddress) + "RR02000004";
            strAISend = strAISend + Utility.xor8(strAISend.getBytes()) + "*\r";

            try {
                Thread.sleep(4000L);
            } catch (InterruptedException var8) {
            }

            int checkCount = 0;

            while(!this.isInterrupted() && !MonitorService.restart) {
                try {
                    int runCount = PLCHostlink.this.analyzeHeart;
                    Thread.sleep(500L);
                    PLCHostlink.this.mInterComm.Send(strISend.getBytes());
                    Thread.sleep(500L);
                    PLCHostlink.this.mInterComm.Send(strQSend.getBytes());
                    Thread.sleep(500L);
                    PLCHostlink.this.mInterComm.Send(strAISend.getBytes());
                    if (runCount == PLCHostlink.this.analyzeHeart) {
                        ++checkCount;
                        PLCHostlink.this.scriptService.saveScriptLog("PLC Hostlink AnalyzeHeart: " + PLCHostlink.this.analyzeHeart);
                    } else {
                        checkCount = 0;
                    }

                    if (checkCount > 10) {
                        checkCount = 0;
                        PLCHostlink.this.analyzeExist = false;
                        Thread.sleep(1000L);
                        PLCHostlink.this.scriptService.logger.info("PLC Hostlink AnalyzeThread Restart");
                        PLCHostlink.this.analyzeThread = PLCHostlink.this.new AnalyzeThread(0);
                        PLCHostlink.this.analyzeThread.start();
                        Thread.sleep(1000L);
                    }
                } catch (InterruptedException var7) {
                }
            }

            PLCHostlink.this.scriptService.logger.info("PLC Hostlink QueryThread Exit");
        }
    }
}
