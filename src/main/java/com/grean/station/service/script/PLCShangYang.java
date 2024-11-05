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
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PLCShangYang {
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
    private byte[] QBytes = new byte[6];
    private int recvLines = 0;
    private boolean bReset = false;
    private PLCShangYang.AnalyzeThread analyzeThread;
    private PLCShangYang.QueryThread queryThread;
    private boolean analyzeExist = false;
    private int analyzeHeart = 0;

    public PLCShangYang() {
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

        if (this.mInterComm != null && this.mInterComm.isConnect()) {
            this.initCmd();
            this.analyzeThread = new PLCShangYang.AnalyzeThread(3000);
            this.queryThread = new PLCShangYang.QueryThread();
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

    private boolean initCmd() {
        byte[] stopCmd = new byte[]{(byte)this.mAddress, 16, 0, 0, 0, 1, 2, 0, 0, 0, 0};
        byte[] debugCmd = new byte[]{(byte)this.mAddress, 16, 0, 0, 0, 1, 2, 0, 3, 0, 0};
        int crc_result = Utility.calcCrc16(stopCmd, 0, 9);
        byte[] tmp_bytes1 = Utility.getByteArray(crc_result);
        stopCmd[9] = tmp_bytes1[2];
        stopCmd[10] = tmp_bytes1[3];
        crc_result = Utility.calcCrc16(debugCmd, 0, 9);
        byte[] tmp_bytes2 = Utility.getByteArray(crc_result);
        debugCmd[9] = tmp_bytes2[2];
        debugCmd[10] = tmp_bytes2[3];

        try {
            Thread.sleep(500L);
            this.mInterComm.Send(stopCmd);
            Thread.sleep(500L);
            this.mInterComm.Send(debugCmd);
            Thread.sleep(500L);
            this.mInterComm.clearRecv(this.mChannel);
            return true;
        } catch (Exception var7) {
            return false;
        }
    }

    public boolean doReset() {
        byte[] CmdSend = new byte[15];
        byte[] tmpByte = new byte[16];
        CmdSend[0] = (byte)this.mAddress;
        CmdSend[1] = 16;
        CmdSend[2] = 0;
        CmdSend[3] = 4;
        CmdSend[4] = 0;
        CmdSend[5] = 3;
        CmdSend[6] = 6;

        int crc_result;
        for(crc_result = 0; crc_result < this.defWordList.size(); ++crc_result) {
            if (((DefWord)this.defWordList.get(crc_result)).getType() == 1) {
                Utility.setBit(tmpByte, ((DefWord)this.defWordList.get(crc_result)).getAddress(), ((DefWord)this.defWordList.get(crc_result)).getDefvalue() > 0.0F);
            }
        }

        for(crc_result = 0; crc_result < 6; ++crc_result) {
            CmdSend[7 + crc_result] = tmpByte[crc_result];
        }

        crc_result = Utility.calcCrc16(CmdSend, 0, 13);
        byte[] tmp_bytes = Utility.getByteArray(crc_result);
        CmdSend[13] = tmp_bytes[2];
        CmdSend[14] = tmp_bytes[3];
        int iTmp = this.recvLines;
        this.mInterComm.Send(CmdSend);
        if (MonitorService.autoPlc) {
            return true;
        } else {
            boolean bResult = false;

            try {
                for(int i = 0; i < 40; ++i) {
                    Thread.sleep(50L);
                    if (iTmp != this.recvLines) {
                        bResult = true;
                        System.arraycopy(tmpByte, 0, this.QBytes, 0, 6);
                        break;
                    }
                }

                if (!bResult) {
                    this.analyzeExist = false;
                    Thread.sleep(5000L);
                }
            } catch (Exception var8) {
            }

            return bResult;
        }
    }

    public boolean doCmd(int iType, int iAddress) {
        byte[] CmdSend = new byte[15];
        byte[] tmpByte = new byte[6];
        if (this.mInterComm == null) {
            return false;
        } else {
            System.arraycopy(this.QBytes, 0, tmpByte, 0, 6);
            CmdSend[0] = (byte)this.mAddress;
            CmdSend[1] = 16;
            CmdSend[2] = 0;
            CmdSend[3] = 4;
            CmdSend[4] = 0;
            CmdSend[5] = 3;
            CmdSend[6] = 6;
            switch(iType) {
                case 0:
                    Utility.setBit(tmpByte, iAddress, false);
                    break;
                case 1:
                    Utility.setBit(tmpByte, iAddress, true);
            }

            int crc_result;
            for(crc_result = 0; crc_result < 6; ++crc_result) {
                CmdSend[7 + crc_result] = tmpByte[crc_result];
            }

            crc_result = Utility.calcCrc16(CmdSend, 0, 13);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            CmdSend[13] = tmp_bytes[2];
            CmdSend[14] = tmp_bytes[3];
            int iTmp = this.recvLines;
            this.mInterComm.Send(CmdSend);
            if (MonitorService.autoPlc) {
                return true;
            } else {
                boolean bResult = false;

                try {
                    for(int i = 0; i < 40; ++i) {
                        Thread.sleep(50L);
                        if (iTmp != this.recvLines) {
                            bResult = true;
                            System.arraycopy(tmpByte, 0, this.QBytes, 0, 6);
                            break;
                        }
                    }

                    if (!bResult) {
                        this.analyzeExist = false;
                        Thread.sleep(5000L);
                    }
                } catch (Exception var10) {
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
            byte[] bRecv = new byte[64];
            Deque<Byte> byteDeqeue = new ArrayDeque();
            super.run();
            PLCShangYang.this.analyzeExist = true;
            PLCShangYang.this.analyzeHeart = 0;
            if (this.analyzeDelay > 0) {
                try {
                    Thread.sleep((long)this.analyzeDelay);
                } catch (InterruptedException var14) {
                }
            }

            PLCShangYang.this.mInterComm.clearRecv(PLCShangYang.this.mChannel);

            while(!this.isInterrupted() && !MonitorService.restart && PLCShangYang.this.analyzeExist) {
                byte[] tmpRecv = PLCShangYang.this.mInterComm.Recv(PLCShangYang.this.mChannel);
                if (tmpRecv == null) {
                    try {
                        Thread.sleep(50L);
                        PLCShangYang.this.analyzeHeart++;
                        ++disCount;
                        if (disCount > 40) {
                            PLCShangYang.this.scriptService.setbConnect(false);
                            byteDeqeue.clear();
                        }
                    } catch (InterruptedException var13) {
                    }
                } else {
                    disCount = 0;
                    PLCShangYang.this.scriptService.setbConnect(true);

                    int i;
                    for(i = 0; i < tmpRecv.length; ++i) {
                        byteDeqeue.addLast(tmpRecv[i]);
                    }

                    boolean bCircle = true;

                    label149:
                    while(byteDeqeue.size() >= 8 && bCircle && PLCShangYang.this.analyzeExist) {
                        byte bTmp = (Byte)byteDeqeue.pollFirst();
                        if (bTmp == PLCShangYang.this.mAddress) {
                            bRecv[0] = bTmp;
                            bTmp = (Byte)byteDeqeue.pollFirst();
                            bRecv[1] = bTmp;
                            int crc_result;
                            int ix;
                            switch(bTmp) {
                                case 3:
                                    if (byteDeqeue.size() < 45) {
                                        byteDeqeue.addFirst(bRecv[1]);
                                        byteDeqeue.addFirst(bRecv[0]);
                                        bCircle = false;
                                    } else {
                                        for(ix = 0; ix < 45; ++ix) {
                                            bRecv[2 + ix] = (Byte)byteDeqeue.pollFirst();
                                        }

                                        crc_result = Utility.calcCrc16(bRecv, 0, 45);
                                        byte[] tmp_bytes3 = Utility.getByteArray(crc_result);
                                        int ixx;
                                        if (tmp_bytes3[2] == bRecv[45] && tmp_bytes3[3] == bRecv[46]) {
                                            int iTmp;
                                            for(ixx = 0; ixx < 6; ++ixx) {
                                                for(iTmp = 0; iTmp < 8; ++iTmp) {
                                                    PLCShangYang.this.QStatus[8 * ixx + iTmp] = ((bRecv[11 + ixx] & 255) >>> iTmp & 1) > 0;
                                                }

                                                PLCShangYang.this.QBytes[ixx] = bRecv[11 + ixx];
                                            }

                                            for(ixx = 0; ixx < 8; ++ixx) {
                                                iTmp = (bRecv[23 + 2 * ixx] & 255) * 256 + (bRecv[23 + 2 * ixx + 1] & 255);
                                                if (ixx == 0) {
                                                    PLCShangYang.this.AIValues[ixx] = (float)iTmp / 10000.0F;
                                                } else {
                                                    PLCShangYang.this.AIValues[ixx] = (float)iTmp / 100.0F;
                                                }
                                            }

                                            PLCShangYang.this.scriptService.updateVarList(2, PLCShangYang.this.QStatus, PLCShangYang.this.IStatus, PLCShangYang.this.AIValues);
                                            PLCShangYang.this.scriptService.updateVarList(4, PLCShangYang.this.QStatus, PLCShangYang.this.IStatus, PLCShangYang.this.AIValues);
                                        } else {
                                            for(ixx = 46; ixx > 0; --ixx) {
                                                byteDeqeue.addFirst(bRecv[ixx]);
                                            }
                                        }
                                    }
                                    break;
                                case 16:
                                    for(i = 0; i < 6; ++i) {
                                        bRecv[2 + i] = (Byte)byteDeqeue.pollFirst();
                                    }

                                    crc_result = Utility.calcCrc16(bRecv, 0, 6);
                                    byte[] tmp_bytes1 = Utility.getByteArray(crc_result);
                                    if (tmp_bytes1[2] == bRecv[6] && tmp_bytes1[3] == bRecv[7]) {
                                        PLCShangYang.this.recvLines++;
                                        break;
                                    } else {
                                        ix = 7;

                                        while(true) {
                                            if (ix <= 0) {
                                                continue label149;
                                            }

                                            byteDeqeue.addFirst(bRecv[ix]);
                                            --ix;
                                        }
                                    }
                                default:
                                    byteDeqeue.addFirst(bRecv[1]);
                            }
                        }
                    }

                    try {
                        Thread.sleep(50L);
                        PLCShangYang.this.analyzeHeart++;
                    } catch (InterruptedException var12) {
                        PLCShangYang.this.analyzeExist = false;
                    }
                }
            }

            PLCShangYang.this.analyzeExist = false;
        }
    }

    private class QueryThread extends Thread {
        private QueryThread() {
        }

        public void run() {
            super.run();
            byte[] cmdSend = new byte[]{(byte)PLCShangYang.this.mAddress, 3, 0, 0, 0, 21, 0, 0};
            int crc_result = Utility.calcCrc16(cmdSend, 0, 6);
            byte[] tmp_bytes2 = Utility.getByteArray(crc_result);
            cmdSend[6] = tmp_bytes2[2];
            cmdSend[7] = tmp_bytes2[3];

            try {
                Thread.sleep(4000L);
            } catch (InterruptedException var7) {
            }

            while(!this.isInterrupted() && !MonitorService.restart) {
                try {
                    int runCount = PLCShangYang.this.analyzeHeart;
                    Thread.sleep(1000L);
                    PLCShangYang.this.mInterComm.Send(cmdSend);
                    if (runCount == PLCShangYang.this.analyzeHeart) {
                        if (PLCShangYang.this.analyzeExist) {
                            PLCShangYang.this.analyzeExist = false;
                        }

                        Thread.sleep(500L);
                        PLCShangYang.this.analyzeThread = PLCShangYang.this.new AnalyzeThread(0);
                        PLCShangYang.this.analyzeThread.start();
                    }
                } catch (InterruptedException var6) {
                }
            }

        }
    }
}
