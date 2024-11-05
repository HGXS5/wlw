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
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PLCModbusTcp {
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
    private PLCModbusTcp.AnalyzeThread analyzeThread;
    private PLCModbusTcp.QueryThread queryThread;
    private boolean analyzeExist = false;
    private int analyzeHeart = 0;

    public PLCModbusTcp() {
    }

    public void init(CommInter interComm, CfgDev cfgDev, float[] minAI, float[] maxAI) {
        this.mInterComm = interComm;
        this.mCfgDev = cfgDev;
        this.mAddress = cfgDev.getAddress().intValue();
        this.mChannel = cfgDev.getSerial_channel();
        this.defWordList = this.scriptService.getDefWordList();

        for(int i = 0; i < this.AIValues.length; ++i) {
            this.minAIVal[i] = minAI[i];
            this.maxAIVal[i] = maxAI[i];
        }

        if (this.mInterComm != null) {
            this.analyzeThread = new PLCModbusTcp.AnalyzeThread(3000);
            this.queryThread = new PLCModbusTcp.QueryThread();
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
        byte[] CmdSend = new byte[29];
        byte[] tmpByte = new byte[16];
        CmdSend[0] = 0;
        CmdSend[1] = 0;
        CmdSend[2] = 0;
        CmdSend[3] = 0;
        CmdSend[4] = 0;
        CmdSend[5] = 23;
        CmdSend[6] = (byte)this.mAddress;
        CmdSend[7] = 15;
        CmdSend[8] = 0;
        CmdSend[9] = 0;
        CmdSend[10] = 0;
        CmdSend[11] = -128;
        CmdSend[12] = 16;

        int i;
        for(i = 0; i < this.defWordList.size(); ++i) {
            if (((DefWord)this.defWordList.get(i)).getType() == 1) {
                Utility.setBit(tmpByte, ((DefWord)this.defWordList.get(i)).getAddress(), ((DefWord)this.defWordList.get(i)).getDefvalue() > 0.0F);
            }
        }

        for(i = 0; i < 16; ++i) {
            CmdSend[13 + i] = tmpByte[i];
        }

        this.bReset = false;
        int iTmp = this.recvLines;
        this.scriptService.saveScriptLog("PLC ModbusTcp doReset recvLines: " + this.recvLines);
        this.mInterComm.clearSend();
        this.mInterComm.clearRecv(this.mChannel);
        this.mInterComm.Send(CmdSend);
        if (MonitorService.autoPlc) {
            return true;
        } else {
            boolean bResult = false;

            try {
                for(i = 0; i < 80; ++i) {
                    Thread.sleep(50L);
                    if (this.bReset) {
                        bResult = true;
                        break;
                    }
                }
            } catch (Exception var6) {
            }

            return bResult;
        }
    }

    public boolean doCmd(int iType, int iAddress) {
        byte[] CmdSend = new byte[12];
        if (this.mInterComm == null) {
            return false;
        } else {
            CmdSend[0] = 0;
            CmdSend[1] = 0;
            CmdSend[2] = 0;
            CmdSend[3] = 0;
            CmdSend[4] = 0;
            CmdSend[5] = 6;
            CmdSend[6] = (byte)this.mAddress;
            CmdSend[7] = 5;
            CmdSend[8] = 0;
            CmdSend[9] = (byte)iAddress;
            switch(iType) {
                case 0:
                    CmdSend[10] = 0;
                    CmdSend[11] = 0;
                    break;
                case 1:
                    CmdSend[10] = -1;
                    CmdSend[11] = 0;
            }

            int iTmp = this.recvLines;
            this.scriptService.saveScriptLog("PLC ModbusTcp doCmd recvLines: " + this.recvLines);
            this.mInterComm.clearSend();
            this.mInterComm.clearRecv(this.mChannel);
            this.mInterComm.Send(CmdSend);
            if (MonitorService.autoPlc) {
                return true;
            } else {
                boolean bResult = false;

                try {
                    for(int i = 0; i < 80; ++i) {
                        Thread.sleep(50L);
                        if (iTmp != this.recvLines) {
                            bResult = true;
                            break;
                        }
                    }
                } catch (Exception var7) {
                }

                return bResult;
            }
        }
    }

    public boolean doCmd(int iType, String strTarget) {
        Iterator var3 = this.defWordList.iterator();

        DefWord defWord;
        do {
            if (!var3.hasNext()) {
                return false;
            }

            defWord = (DefWord)var3.next();
        } while(!defWord.getName().equals(strTarget) || defWord.getType() != 1);

        return this.doCmd(iType, defWord.getAddress());
    }

    private class AnalyzeThread extends Thread {
        public int analyzeDelay = 0;

        public AnalyzeThread(int delay) {
            this.analyzeDelay = delay;
        }

        public void run() {
            int disCount = 0;
            int bSize = 0;
            byte[] bAnalyze = new byte[256];
            super.run();
            PLCModbusTcp.this.analyzeExist = true;
            PLCModbusTcp.this.analyzeHeart = 0;
            if (this.analyzeDelay > 0) {
                try {
                    Thread.sleep((long)this.analyzeDelay);
                } catch (InterruptedException var7) {
                }
            }

            PLCModbusTcp.this.mInterComm.clearRecv(PLCModbusTcp.this.mChannel);

            while(!this.isInterrupted()) {
                if (MonitorService.restart) {
                    PLCModbusTcp.this.scriptService.logger.info("PLC ModbusTcp AnalyzeThread Restart Message");
                    break;
                }

                if (!PLCModbusTcp.this.analyzeExist) {
                    PLCModbusTcp.this.scriptService.logger.info("PLC ModbusTcp AnalyzeThread Exit Message");
                    break;
                }

                try {
                    byte[] bRecv = PLCModbusTcp.this.mInterComm.Recv(PLCModbusTcp.this.mChannel);
                    if (bRecv == null) {
                        Thread.sleep(50L);
                        PLCModbusTcp.this.analyzeHeart++;
                        ++disCount;
                        if (disCount > 40) {
                            PLCModbusTcp.this.scriptService.setbConnect(false);
                            bSize = 0;
                        }
                    } else {
                        disCount = 0;
                        PLCModbusTcp.this.scriptService.setbConnect(true);

                        int i;
                        for(i = 0; i < bRecv.length; ++i) {
                            bAnalyze[bSize] = bRecv[i];
                            ++bSize;
                            if (bSize > 64) {
                                String strRecv = Utility.bytesToHexString(bAnalyze, bSize);
                                PLCModbusTcp.this.scriptService.saveScriptLog("PLC ModbusTcp AnalyzeThread Recv Size > 64: " + strRecv);
                                bSize = 0;
                                break;
                            }
                        }

                        String strRecvx;
                        if (bSize < 8) {
                            if (bSize > 0) {
                                strRecvx = Utility.bytesToHexString(bAnalyze, bSize);
                                PLCModbusTcp.this.scriptService.saveScriptLog("PLC ModbusTcp AnalyzeThread Recv Size < 8: " + strRecvx);
                            }
                        } else if (bAnalyze[6] != PLCModbusTcp.this.mAddress) {
                            strRecvx = Utility.bytesToHexString(bAnalyze, bSize);
                            PLCModbusTcp.this.scriptService.saveScriptLog("PLC ModbusTcp AnalyzeThread Recv Wrong Address: " + strRecvx);
                            bSize = 0;
                        } else {
                            int j;
                            switch(bAnalyze[7]) {
                                case 1:
                                    if (bSize > 24) {
                                        for(i = 0; i < 16; ++i) {
                                            for(j = 0; j < 8; ++j) {
                                                PLCModbusTcp.this.QStatus[8 * i + j] = ((bAnalyze[9 + i] & 255) >>> j & 1) > 0;
                                            }
                                        }

                                        PLCModbusTcp.this.scriptService.updateVarList(bAnalyze[7], PLCModbusTcp.this.QStatus, PLCModbusTcp.this.IStatus, PLCModbusTcp.this.AIValues);
                                        bSize = 0;
                                    }
                                    break;
                                case 2:
                                    if (bSize > 24) {
                                        for(i = 0; i < 16; ++i) {
                                            for(j = 0; j < 8; ++j) {
                                                PLCModbusTcp.this.IStatus[8 * i + j] = ((bAnalyze[9 + i] & 255) >>> j & 1) > 0;
                                            }
                                        }

                                        PLCModbusTcp.this.scriptService.updateVarList(bAnalyze[7], PLCModbusTcp.this.QStatus, PLCModbusTcp.this.IStatus, PLCModbusTcp.this.AIValues);
                                        bSize = 0;
                                    }
                                    break;
                                case 3:
                                case 6:
                                case 7:
                                case 8:
                                case 9:
                                case 10:
                                case 11:
                                case 12:
                                case 13:
                                case 14:
                                default:
                                    bSize = 0;
                                    break;
                                case 4:
                                    if (bSize > 23) {
                                        for(i = 0; i < 8; ++i) {
                                            j = bAnalyze[9 + 2 * i] * 256 + bAnalyze[9 + 2 * i + 1];
                                            PLCModbusTcp.this.AIValues[i] = ((float)j - 6400.0F) / 25600.0F * (PLCModbusTcp.this.maxAIVal[i] - PLCModbusTcp.this.minAIVal[i]) + PLCModbusTcp.this.minAIVal[i];
                                        }

                                        PLCModbusTcp.this.scriptService.updateVarList(bAnalyze[7], PLCModbusTcp.this.QStatus, PLCModbusTcp.this.IStatus, PLCModbusTcp.this.AIValues);
                                        bSize = 0;
                                    }
                                    break;
                                case 5:
                                    if (bSize > 10) {
                                        if (bAnalyze[10] == 0) {
                                            PLCModbusTcp.this.QStatus[bAnalyze[9] & 255] = false;
                                        } else {
                                            PLCModbusTcp.this.QStatus[bAnalyze[9] & 255] = true;
                                        }

                                        PLCModbusTcp.this.recvLines++;
                                        PLCModbusTcp.this.scriptService.saveScriptLog("PLC ModbusTcp AnalyzeThread 05 recvLines: " + PLCModbusTcp.this.recvLines);
                                        bSize = 0;
                                    }
                                    break;
                                case 15:
                                    PLCModbusTcp.this.bReset = true;
                                    PLCModbusTcp.this.recvLines++;
                                    PLCModbusTcp.this.scriptService.saveScriptLog("PLC ModbusTcp AnalyzeThread 0F recvLines: " + PLCModbusTcp.this.recvLines);
                                    bSize = 0;
                            }

                            Thread.sleep(50L);
                            PLCModbusTcp.this.analyzeHeart++;
                        }
                    }
                } catch (InterruptedException var8) {
                    PLCModbusTcp.this.scriptService.logger.info("PLC ModbusTcp AnalyzeThread Exception: " + var8.toString());
                    PLCModbusTcp.this.analyzeExist = false;
                }
            }

            PLCModbusTcp.this.scriptService.logger.info("PLC ModbusTcp AnalyzeThread Exit");
            PLCModbusTcp.this.analyzeExist = false;
        }
    }

    private class QueryThread extends Thread {
        private QueryThread() {
        }

        public void run() {
            super.run();
            int analyzeCount = 0;
            int pkg_id = 0;
            byte[] cmdISend = new byte[12];
            byte[] cmdQSend = new byte[12];
            byte[] cmdAISend = new byte[12];
            cmdISend[0] = 0;
            cmdISend[1] = 0;
            cmdISend[2] = 0;
            cmdISend[3] = 0;
            cmdISend[4] = 0;
            cmdISend[5] = 6;
            cmdISend[6] = (byte)PLCModbusTcp.this.mAddress;
            cmdISend[7] = 2;
            cmdISend[8] = 0;
            cmdISend[9] = 0;
            cmdISend[10] = 0;
            cmdISend[11] = -128;
            cmdQSend[0] = 0;
            cmdQSend[1] = 0;
            cmdQSend[2] = 0;
            cmdQSend[3] = 0;
            cmdQSend[4] = 0;
            cmdQSend[5] = 6;
            cmdQSend[6] = (byte)PLCModbusTcp.this.mAddress;
            cmdQSend[7] = 1;
            cmdQSend[8] = 0;
            cmdQSend[9] = 0;
            cmdQSend[10] = 0;
            cmdQSend[11] = -128;
            cmdAISend[0] = 0;
            cmdAISend[1] = 0;
            cmdAISend[2] = 0;
            cmdAISend[3] = 0;
            cmdAISend[4] = 0;
            cmdAISend[5] = 6;
            cmdAISend[6] = (byte)PLCModbusTcp.this.mAddress;
            cmdAISend[7] = 4;
            cmdAISend[8] = 0;
            cmdAISend[9] = 0;
            cmdAISend[10] = 0;
            cmdAISend[11] = 8;

            try {
                Thread.sleep(5000L);
            } catch (InterruptedException var10) {
            }

            while(!this.isInterrupted()) {
                if (MonitorService.restart) {
                    PLCModbusTcp.this.scriptService.logger.info("PLC ModbusTcp QueryThread Restart Message");
                    break;
                }

                try {
                    int runCount = PLCModbusTcp.this.analyzeHeart;
                    ++pkg_id;
                    byte[] tmp_bytes = Utility.getByteArray(pkg_id);
                    cmdISend[0] = tmp_bytes[2];
                    cmdISend[1] = tmp_bytes[3];
                    cmdQSend[0] = tmp_bytes[2];
                    cmdQSend[1] = tmp_bytes[3];
                    cmdAISend[0] = tmp_bytes[2];
                    cmdAISend[1] = tmp_bytes[3];
                    Thread.sleep(500L);
                    PLCModbusTcp.this.mInterComm.Send(cmdISend);
                    Thread.sleep(500L);
                    PLCModbusTcp.this.mInterComm.Send(cmdQSend);
                    Thread.sleep(500L);
                    PLCModbusTcp.this.mInterComm.Send(cmdAISend);
                    if (runCount == PLCModbusTcp.this.analyzeHeart) {
                        ++analyzeCount;
                    } else {
                        analyzeCount = 0;
                    }

                    if (analyzeCount > 10) {
                        if (PLCModbusTcp.this.analyzeExist) {
                            PLCModbusTcp.this.analyzeExist = false;
                        }

                        Thread.sleep(1000L);
                        analyzeCount = 0;
                        PLCModbusTcp.this.analyzeThread = PLCModbusTcp.this.new AnalyzeThread(0);
                        PLCModbusTcp.this.analyzeThread.start();
                        PLCModbusTcp.this.scriptService.logger.info("PLC ModbusTcp QueryThread New AnaylyzeThread");
                    }
                } catch (Exception var9) {
                    PLCModbusTcp.this.scriptService.logger.info("PLC ModbusTcp QueryThread Exception: " + var9.toString());
                }
            }

            PLCModbusTcp.this.scriptService.logger.info("PLC ModbusTcp QueryThread Exit");
        }
    }
}
