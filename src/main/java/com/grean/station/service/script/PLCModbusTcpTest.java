//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.script;

import com.grean.station.comm.CommInter;
import com.grean.station.domain.DO.cfg.CfgDev;
import com.grean.station.domain.DO.cfg.CfgModbusVal;
import com.grean.station.domain.DO.plc.DefWord;
import com.grean.station.service.MonitorService;
import com.grean.station.utils.Utility;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PLCModbusTcpTest {
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
    private boolean bWrite = false;
    private PLCModbusTcpTest.AnalyzeThread analyzeThread;
    private PLCModbusTcpTest.QueryThread queryThread;
    private boolean analyzeExist = false;
    private int analyzeHeart = 0;

    public PLCModbusTcpTest() {
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
            this.analyzeThread = new PLCModbusTcpTest.AnalyzeThread(3000);
            this.queryThread = new PLCModbusTcpTest.QueryThread();
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
        this.mInterComm.clearSend();
        this.mInterComm.Send(CmdSend);
        if (MonitorService.autoPlc) {
            return true;
        } else {
            boolean bResult = false;

            try {
                for(i = 0; i < 10; ++i) {
                    Thread.sleep(50L);
                    if (iTmp != this.recvLines && this.bReset) {
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
            this.mInterComm.clearSend();
            this.mInterComm.Send(CmdSend);
            if (MonitorService.autoPlc) {
                return true;
            } else {
                boolean bResult = false;

                try {
                    for(int i = 0; i < 40; ++i) {
                        Thread.sleep(50L);
                        if (iTmp != this.recvLines && this.QStatus[iAddress] == iType > 0) {
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

    public boolean doWrite(List<CfgModbusVal> cfgModbusValList) {
        boolean bResult = false;
        int regStart = 250;
        int regEnd = 0;
        int regLen;
        Iterator var6 = cfgModbusValList.iterator();

        while(var6.hasNext()) {
            CfgModbusVal cfgModbusVal = (CfgModbusVal)var6.next();
            if (cfgModbusVal.getAddress() < regStart) {
                regStart = cfgModbusVal.getAddress();
            }

            if (cfgModbusVal.getAddress() + cfgModbusVal.getLength() > regEnd) {
                regEnd = cfgModbusVal.getAddress() + cfgModbusVal.getLength();
            }
        }

        regLen = regEnd - regStart;
        if (regLen == 0) {
            return bResult;
        } else {
            byte[] CmdSend = new byte[13 + 2 * regLen];
            CmdSend[0] = 0;
            CmdSend[1] = 0;
            CmdSend[2] = 0;
            CmdSend[3] = 0;
            CmdSend[4] = 0;
            CmdSend[5] = (byte)(7 + 2 * regLen);
            CmdSend[6] = (byte)this.mAddress;
            CmdSend[7] = 16;
            CmdSend[8] = (byte)(regStart / 256);
            CmdSend[9] = (byte)(regStart % 256);
            CmdSend[10] = (byte)(regLen / 256);
            CmdSend[11] = (byte)(regLen % 256);
            CmdSend[12] = (byte)(2 * regLen % 256);
            Iterator var8 = cfgModbusValList.iterator();

            while(var8.hasNext()) {
                CfgModbusVal cfgModbusVal = (CfgModbusVal)var8.next();
                if (cfgModbusVal.getValue() != null) {
                    byte[] bTmp = Utility.getByteArray(cfgModbusVal.getValue());
                    CmdSend[13 + (cfgModbusVal.getAddress() - regStart) * 2] = bTmp[0];
                    CmdSend[13 + (cfgModbusVal.getAddress() - regStart) * 2 + 1] = bTmp[1];
                    CmdSend[13 + (cfgModbusVal.getAddress() - regStart) * 2 + 2] = bTmp[2];
                    CmdSend[13 + (cfgModbusVal.getAddress() - regStart) * 2 + 3] = bTmp[3];
                }
            }

            this.bWrite = false;
            int iTmp = this.recvLines;
            this.mInterComm.clearSend();
            this.mInterComm.Send(CmdSend);
            if (MonitorService.autoPlc) {
                return true;
            } else {
                bResult = false;

                try {
                    for(int i = 0; i < 10; ++i) {
                        Thread.sleep(50L);
                        if (iTmp != this.recvLines && this.bWrite) {
                            bResult = true;
                            break;
                        }
                    }
                } catch (Exception var11) {
                }

                return bResult;
            }
        }
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
            PLCModbusTcpTest.this.analyzeExist = true;
            PLCModbusTcpTest.this.analyzeHeart = 0;
            if (this.analyzeDelay > 0) {
                try {
                    Thread.sleep((long)this.analyzeDelay);
                } catch (InterruptedException var7) {
                }
            }

            PLCModbusTcpTest.this.mInterComm.clearRecv(PLCModbusTcpTest.this.mChannel);

            while(!this.isInterrupted()) {
                if (MonitorService.restart) {
                    PLCModbusTcpTest.this.scriptService.logger.info("PLC ModbusTcp AnalyzeThread Restart Message");
                    break;
                }

                if (!PLCModbusTcpTest.this.analyzeExist) {
                    PLCModbusTcpTest.this.scriptService.logger.info("PLC ModbusTcp AnalyzeThread Exit Message");
                    break;
                }

                try {
                    byte[] bRecv = PLCModbusTcpTest.this.mInterComm.Recv(PLCModbusTcpTest.this.mChannel);
                    if (bRecv == null) {
                        Thread.sleep(50L);
                        PLCModbusTcpTest.this.analyzeHeart++;
                        ++disCount;
                        if (disCount > 40) {
                            PLCModbusTcpTest.this.scriptService.setbConnect(false);
                            bSize = 0;
                        }
                    } else {
                        disCount = 0;
                        PLCModbusTcpTest.this.scriptService.setbConnect(true);

                        int i;
                        for(i = 0; i < bRecv.length; ++i) {
                            bAnalyze[bSize] = bRecv[i];
                            ++bSize;
                            if (bSize > 250) {
                                bSize = 0;
                                break;
                            }
                        }

                        if (bSize >= 8) {
                            int iTmp;
                            switch(bAnalyze[7]) {
                                case 1:
                                    if (bSize > 24) {
                                        for(i = 0; i < 16; ++i) {
                                            for(iTmp = 0; iTmp < 8; ++iTmp) {
                                                PLCModbusTcpTest.this.QStatus[8 * i + iTmp] = ((bAnalyze[9 + i] & 255) >>> iTmp & 1) > 0;
                                            }
                                        }

                                        PLCModbusTcpTest.this.scriptService.updateVarList(bAnalyze[7], PLCModbusTcpTest.this.QStatus, PLCModbusTcpTest.this.IStatus, PLCModbusTcpTest.this.AIValues);
                                        bSize = 0;
                                    }
                                    break;
                                case 2:
                                    if (bSize > 24) {
                                        for(i = 0; i < 16; ++i) {
                                            for(iTmp = 0; iTmp < 8; ++iTmp) {
                                                PLCModbusTcpTest.this.IStatus[8 * i + iTmp] = ((bAnalyze[9 + i] & 255) >>> iTmp & 1) > 0;
                                            }
                                        }

                                        PLCModbusTcpTest.this.scriptService.updateVarList(bAnalyze[7], PLCModbusTcpTest.this.QStatus, PLCModbusTcpTest.this.IStatus, PLCModbusTcpTest.this.AIValues);
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
                                            iTmp = (bAnalyze[9 + 2 * i] & 255) * 256 + (bAnalyze[9 + 2 * i + 1] & 255);
                                            if (iTmp < 0) {
                                                PLCModbusTcpTest.this.AIValues[i] = 0.0F;
                                            } else {
                                                PLCModbusTcpTest.this.AIValues[i] = ((float)iTmp - 0.0F) / 27648.0F * (PLCModbusTcpTest.this.maxAIVal[i] - PLCModbusTcpTest.this.minAIVal[i]) + PLCModbusTcpTest.this.minAIVal[i];
                                            }
                                        }

                                        PLCModbusTcpTest.this.scriptService.updateVarList(bAnalyze[7], PLCModbusTcpTest.this.QStatus, PLCModbusTcpTest.this.IStatus, PLCModbusTcpTest.this.AIValues);
                                        bSize = 0;
                                    }
                                    break;
                                case 5:
                                    if (bSize > 10) {
                                        if (bAnalyze[10] == 0) {
                                            PLCModbusTcpTest.this.QStatus[bAnalyze[9] & 255] = false;
                                        } else {
                                            PLCModbusTcpTest.this.QStatus[bAnalyze[9] & 255] = true;
                                        }

                                        PLCModbusTcpTest.this.recvLines++;
                                        bSize = 0;
                                    }
                                    break;
                                case 15:
                                    PLCModbusTcpTest.this.bReset = true;
                                    PLCModbusTcpTest.this.recvLines++;
                                    bSize = 0;
                                    break;
                                case 16:
                                    PLCModbusTcpTest.this.bWrite = true;
                                    PLCModbusTcpTest.this.recvLines++;
                                    bSize = 0;
                            }

                            Thread.sleep(50L);
                            PLCModbusTcpTest.this.analyzeHeart++;
                        }
                    }
                } catch (InterruptedException var8) {
                    PLCModbusTcpTest.this.scriptService.logger.info("PLC ModbusTcp AnalyzeThread Exception: " + var8.toString());
                    PLCModbusTcpTest.this.analyzeExist = false;
                }
            }

            PLCModbusTcpTest.this.scriptService.logger.info("PLC ModbusTcp AnalyzeThread Exit");
            PLCModbusTcpTest.this.analyzeExist = false;
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
            cmdISend[6] = (byte)PLCModbusTcpTest.this.mAddress;
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
            cmdQSend[6] = (byte)PLCModbusTcpTest.this.mAddress;
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
            cmdAISend[6] = (byte)PLCModbusTcpTest.this.mAddress;
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
                    PLCModbusTcpTest.this.scriptService.logger.info("PLC ModbusTcp QueryThread Restart Message");
                    break;
                }

                try {
                    int runCount = PLCModbusTcpTest.this.analyzeHeart;
                    ++pkg_id;
                    byte[] tmp_bytes = Utility.getByteArray(pkg_id);
                    cmdISend[0] = tmp_bytes[2];
                    cmdISend[1] = tmp_bytes[3];
                    cmdQSend[0] = tmp_bytes[2];
                    cmdQSend[1] = tmp_bytes[3];
                    cmdAISend[0] = tmp_bytes[2];
                    cmdAISend[1] = tmp_bytes[3];
                    Thread.sleep(500L);
                    PLCModbusTcpTest.this.mInterComm.Send(cmdISend);
                    Thread.sleep(500L);
                    PLCModbusTcpTest.this.mInterComm.Send(cmdQSend);
                    Thread.sleep(500L);
                    PLCModbusTcpTest.this.mInterComm.Send(cmdAISend);
                    if (runCount == PLCModbusTcpTest.this.analyzeHeart) {
                        ++analyzeCount;
                    } else {
                        analyzeCount = 0;
                    }

                    if (analyzeCount > 10) {
                        if (PLCModbusTcpTest.this.analyzeExist) {
                            PLCModbusTcpTest.this.analyzeExist = false;
                        }

                        Thread.sleep(1000L);
                        analyzeCount = 0;
                        PLCModbusTcpTest.this.analyzeThread = PLCModbusTcpTest.this.new AnalyzeThread(0);
                        PLCModbusTcpTest.this.analyzeThread.start();
                        PLCModbusTcpTest.this.scriptService.logger.info("PLC ModbusTcp QueryThread New AnaylyzeThread");
                    }
                } catch (Exception var9) {
                    PLCModbusTcpTest.this.scriptService.logger.info("PLC ModbusTcp QueryThread Exception: " + var9.toString());
                }
            }

            PLCModbusTcpTest.this.scriptService.logger.info("PLC ModbusTcp QueryThread Exit");
        }
    }
}
