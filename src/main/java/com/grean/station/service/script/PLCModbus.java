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
public class PLCModbus {
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
    private PLCModbus.AnalyzeThread analyzeThread;
    private PLCModbus.QueryThread queryThread;
    private boolean analyzeExist = false;
    private int analyzeHeart = 0;

    public PLCModbus() {
    }

    public void init(CommInter interComm, CfgDev cfgDev, float[] minAI, float[] maxAI) {
        this.mInterComm = interComm;
        this.mCfgDev = cfgDev;
        this.mAddress = cfgDev.getAddress().intValue();
        this.mChannel = cfgDev.getSerial_channel();
        interComm.setSendDelay(100);
        this.defWordList = this.scriptService.getDefWordList();

        for(int i = 0; i < this.AIValues.length; ++i) {
            this.minAIVal[i] = minAI[i];
            this.maxAIVal[i] = maxAI[i];
        }

        if (this.mInterComm != null && this.mInterComm.isConnect()) {
            this.analyzeThread = new PLCModbus.AnalyzeThread(3000);
            this.queryThread = new PLCModbus.QueryThread();
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
        byte[] CmdSend = new byte[25];
        byte[] tmpByte = new byte[16];
        CmdSend[0] = (byte)this.mAddress;
        CmdSend[1] = 15;
        CmdSend[2] = 0;
        CmdSend[3] = 0;
        CmdSend[4] = 0;
        CmdSend[5] = -128;
        CmdSend[6] = 16;

        int crc_result;
        for(crc_result = 0; crc_result < this.defWordList.size(); ++crc_result) {
            if (((DefWord)this.defWordList.get(crc_result)).getType() == 1) {
                Utility.setBit(tmpByte, ((DefWord)this.defWordList.get(crc_result)).getAddress(), ((DefWord)this.defWordList.get(crc_result)).getDefvalue() > 0.0F);
            }
        }

        for(crc_result = 0; crc_result < 16; ++crc_result) {
            CmdSend[7 + crc_result] = tmpByte[crc_result];
        }

        crc_result = Utility.calcCrc16(CmdSend, 0, 23);
        byte[] tmp_bytes = Utility.getByteArray(crc_result);
        CmdSend[23] = tmp_bytes[2];
        CmdSend[24] = tmp_bytes[3];
        this.bReset = false;
        int iTmp = this.recvLines;
        this.scriptService.saveScriptLog("PLC Modbus doReset recvLines: " + this.recvLines);
        this.mInterComm.clearSend();
        this.mInterComm.clearRecv(this.mChannel);
        this.mInterComm.Send(CmdSend);
        if (MonitorService.autoPlc) {
            return true;
        } else {
            boolean bResult = false;

            try {
                for(int i = 0; i < 200; ++i) {
                    Thread.sleep(20L);
                    if (this.bReset) {
                        bResult = true;
                        break;
                    }
                }
            } catch (Exception var8) {
            }

            return bResult;
        }
    }

    public boolean doCmd(int iType, int iAddress) {
        byte[] CmdSend = new byte[8];
        if (this.mInterComm == null) {
            return false;
        } else {
            CmdSend[0] = (byte)this.mAddress;
            CmdSend[1] = 5;
            CmdSend[2] = 0;
            CmdSend[3] = (byte)iAddress;
            switch(iType) {
                case 0:
                    CmdSend[4] = 0;
                    CmdSend[5] = 0;
                    break;
                case 1:
                    CmdSend[4] = -1;
                    CmdSend[5] = 0;
            }

            int crc_result = Utility.calcCrc16(CmdSend, 0, 6);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            CmdSend[6] = tmp_bytes[2];
            CmdSend[7] = tmp_bytes[3];
            int iTmp = this.recvLines;
            this.scriptService.saveScriptLog("PLC Modbus doCmd recvLines: " + this.recvLines);
            this.mInterComm.clearSend();
            this.mInterComm.clearRecv(this.mChannel);
            this.mInterComm.Send(CmdSend);
            if (MonitorService.autoPlc) {
                return true;
            } else {
                boolean bResult = false;

                try {
                    for(int i = 0; i < 200; ++i) {
                        Thread.sleep(20L);
                        if (iTmp != this.recvLines) {
                            bResult = true;
                            break;
                        }
                    }
                } catch (Exception var9) {
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
            PLCModbus.this.analyzeExist = true;
            PLCModbus.this.analyzeHeart = 0;
            if (this.analyzeDelay > 0) {
                try {
                    Thread.sleep((long)this.analyzeDelay);
                } catch (InterruptedException var12) {
                }
            }

            PLCModbus.this.mInterComm.clearRecv(PLCModbus.this.mChannel);

            while(!this.isInterrupted() && !MonitorService.restart && PLCModbus.this.analyzeExist) {
                try {
                    byte[] tmpRecv = PLCModbus.this.mInterComm.Recv(PLCModbus.this.mChannel);
                    if (tmpRecv == null) {
                        Thread.sleep(20L);
                        PLCModbus.this.analyzeHeart++;
                        ++disCount;
                        if (disCount > 100) {
                            PLCModbus.this.scriptService.setbConnect(false);
                            byteDeqeue.clear();
                        }
                    } else {
                        disCount = 0;
                        PLCModbus.this.scriptService.setbConnect(true);

                        int i;
                        for(i = 0; i < tmpRecv.length; ++i) {
                            byteDeqeue.addLast(tmpRecv[i]);
                        }

                        boolean bCircle = true;

                        while(byteDeqeue.size() >= 8 && bCircle) {
                            int ix;
                            if (!PLCModbus.this.analyzeExist) {
                                PLCModbus.this.scriptService.saveScriptLog("PLC Modbus AnalyzeThread ByteDequeueSize: " + byteDeqeue.size());
                                Object[] bytes = byteDeqeue.toArray();
                                if (bytes != null) {
                                    for(ix = 0; ix < bytes.length; ++ix) {
                                        PLCModbus.this.scriptService.saveScriptLog("Deque Elements: " + bytes[ix]);
                                    }
                                }
                                break;
                            }

                            byte bTmp = (Byte)byteDeqeue.pollFirst();
                            if (bTmp == PLCModbus.this.mAddress) {
                                bRecv[0] = bTmp;
                                bTmp = (Byte)byteDeqeue.pollFirst();
                                bRecv[1] = bTmp;
                                int crc_result;
                                int ixx;
                                int j;
                                byte[] tmp_bytes3;
                                switch(bTmp) {
                                    case 1:
                                    case 2:
                                        if (byteDeqeue.size() < 19) {
                                            byteDeqeue.addFirst(bRecv[1]);
                                            byteDeqeue.addFirst(bRecv[0]);
                                            bCircle = false;
                                        } else {
                                            for(ix = 0; ix < 19; ++ix) {
                                                bRecv[2 + ix] = (Byte)byteDeqeue.pollFirst();
                                            }

                                            crc_result = Utility.calcCrc16(bRecv, 0, 19);
                                            tmp_bytes3 = Utility.getByteArray(crc_result);
                                            if (tmp_bytes3[2] == bRecv[19] && tmp_bytes3[3] == bRecv[20]) {
                                                for(ixx = 0; ixx < 16; ++ixx) {
                                                    for(j = 0; j < 8; ++j) {
                                                        if (bTmp == 1) {
                                                            PLCModbus.this.QStatus[8 * ixx + j] = ((bRecv[3 + ixx] & 255) >>> j & 1) > 0;
                                                        }

                                                        if (bTmp == 2) {
                                                            PLCModbus.this.IStatus[8 * ixx + j] = ((bRecv[3 + ixx] & 255) >>> j & 1) > 0;
                                                        }
                                                    }
                                                }

                                                PLCModbus.this.scriptService.updateVarList(bTmp, PLCModbus.this.QStatus, PLCModbus.this.IStatus, PLCModbus.this.AIValues);
                                            } else {
                                                for(ixx = 20; ixx > 0; --ixx) {
                                                    byteDeqeue.addFirst(bRecv[ixx]);
                                                }
                                            }
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
                                        byteDeqeue.addFirst(bRecv[1]);
                                        break;
                                    case 4:
                                        if (byteDeqeue.size() < 19) {
                                            byteDeqeue.addFirst(bRecv[1]);
                                            byteDeqeue.addFirst(bRecv[0]);
                                            bCircle = false;
                                        } else {
                                            for(ix = 0; ix < 19; ++ix) {
                                                bRecv[2 + ix] = (Byte)byteDeqeue.pollFirst();
                                            }

                                            crc_result = Utility.calcCrc16(bRecv, 0, 19);
                                            tmp_bytes3 = Utility.getByteArray(crc_result);
                                            if (tmp_bytes3[2] == bRecv[19] && tmp_bytes3[3] == bRecv[20]) {
                                                for(ixx = 0; ixx < 8; ++ixx) {
                                                    j = (bRecv[3 + 2 * ixx] & 255) * 256 + (bRecv[3 + 2 * ixx + 1] & 255);
                                                    if (j < 6400) {
                                                        PLCModbus.this.AIValues[ixx] = 0.0F;
                                                    } else {
                                                        PLCModbus.this.AIValues[ixx] = ((float)j - 6400.0F) / 25600.0F * (PLCModbus.this.maxAIVal[ixx] - PLCModbus.this.minAIVal[ixx]) + PLCModbus.this.minAIVal[ixx];
                                                    }
                                                }

                                                PLCModbus.this.scriptService.updateVarList(bTmp, PLCModbus.this.QStatus, PLCModbus.this.IStatus, PLCModbus.this.AIValues);
                                            } else {
                                                ixx = 20;

                                                while(true) {
                                                    if (ixx <= 0) {
                                                        break;
                                                    }

                                                    byteDeqeue.addFirst(bRecv[ixx]);
                                                    --ixx;
                                                }
                                            }
                                        }
                                        break;
                                    case 5:
                                    case 15:
                                        for(i = 0; i < 6; ++i) {
                                            bRecv[2 + i] = (Byte)byteDeqeue.pollFirst();
                                        }

                                        crc_result = Utility.calcCrc16(bRecv, 0, 6);
                                        byte[] tmp_bytes1 = Utility.getByteArray(crc_result);
                                        if (tmp_bytes1[2] == bRecv[6] && tmp_bytes1[3] == bRecv[7]) {
                                            if (bTmp == 5) {
                                                if (bRecv[4] == 0) {
                                                    PLCModbus.this.QStatus[bRecv[3] & 255] = false;
                                                } else {
                                                    PLCModbus.this.QStatus[bRecv[3] & 255] = true;
                                                }

                                                PLCModbus.this.recvLines++;
                                                PLCModbus.this.scriptService.saveScriptLog("PLC Modbus AnalyzeThread recvLines: " + PLCModbus.this.recvLines);
                                            }

                                            if (bTmp == 15) {
                                                PLCModbus.this.bReset = true;
                                            }
                                        } else {
                                            for(ix = 7; ix > 0; --ix) {
                                                byteDeqeue.addFirst(bRecv[ix]);
                                            }
                                        }
                                }
                            }
                        }

                        Thread.sleep(20L);
                        PLCModbus.this.analyzeHeart++;
                    }
                } catch (Exception var13) {
                    PLCModbus.this.scriptService.logger.info("PLC Modbus AnalyzeThread Exception: " + var13.toString());
                }
            }

            PLCModbus.this.scriptService.logger.info("PLC Modbus AnalyzeThread Exit");
        }
    }

    private class QueryThread extends Thread {
        private QueryThread() {
        }

        public void run() {
            super.run();
            byte[] cmdISend = new byte[8];
            byte[] cmdQSend = new byte[8];
            byte[] cmdAISend = new byte[8];
            cmdISend[0] = (byte)PLCModbus.this.mAddress;
            cmdISend[1] = 2;
            cmdISend[2] = 0;
            cmdISend[3] = 0;
            cmdISend[4] = 0;
            cmdISend[5] = -128;
            int crc_result = Utility.calcCrc16(cmdISend, 0, 6);
            byte[] tmp_bytes1 = Utility.getByteArray(crc_result);
            cmdISend[6] = tmp_bytes1[2];
            cmdISend[7] = tmp_bytes1[3];
            cmdQSend[0] = (byte)PLCModbus.this.mAddress;
            cmdQSend[1] = 1;
            cmdQSend[2] = 0;
            cmdQSend[3] = 0;
            cmdQSend[4] = 0;
            cmdQSend[5] = -128;
            crc_result = Utility.calcCrc16(cmdQSend, 0, 6);
            byte[] tmp_bytes2 = Utility.getByteArray(crc_result);
            cmdQSend[6] = tmp_bytes2[2];
            cmdQSend[7] = tmp_bytes2[3];
            cmdAISend[0] = (byte)PLCModbus.this.mAddress;
            cmdAISend[1] = 4;
            cmdAISend[2] = 0;
            cmdAISend[3] = 0;
            cmdAISend[4] = 0;
            cmdAISend[5] = 8;
            crc_result = Utility.calcCrc16(cmdAISend, 0, 6);
            byte[] tmp_bytes3 = Utility.getByteArray(crc_result);
            cmdAISend[6] = tmp_bytes3[2];
            cmdAISend[7] = tmp_bytes3[3];

            try {
                Thread.sleep(4000L);
            } catch (InterruptedException var12) {
            }

            int checkCount = 0;

            while(!this.isInterrupted() && !MonitorService.restart) {
                try {
                    int runCount = PLCModbus.this.analyzeHeart;
                    Thread.sleep(500L);
                    PLCModbus.this.mInterComm.Send(cmdISend);
                    Thread.sleep(500L);
                    PLCModbus.this.mInterComm.Send(cmdQSend);
                    Thread.sleep(500L);
                    PLCModbus.this.mInterComm.Send(cmdAISend);
                    if (runCount == PLCModbus.this.analyzeHeart) {
                        ++checkCount;
                        PLCModbus.this.scriptService.saveScriptLog("PLC Modbus AnalyzeHeart: " + PLCModbus.this.analyzeHeart);
                    } else {
                        checkCount = 0;
                    }

                    if (checkCount > 10) {
                        checkCount = 0;
                        PLCModbus.this.analyzeExist = false;
                        Thread.sleep(1000L);
                        PLCModbus.this.scriptService.logger.info("PLC Modbus AnalyzeThread Restart");
                        PLCModbus.this.analyzeThread = PLCModbus.this.new AnalyzeThread(0);
                        PLCModbus.this.analyzeThread.start();
                        Thread.sleep(1000L);
                    }
                } catch (InterruptedException var11) {
                }
            }

            PLCModbus.this.scriptService.logger.info("PLC Modbus QueryThread Exit");
        }
    }
}