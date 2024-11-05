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
public class PLCSchneider {
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

    public PLCSchneider() {
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

    }

    public void free() {
    }

    private byte[] getRecv(int waitTime, int recvChannel, boolean bQuery) {
        int bLen = 0;
        byte[] bRecv = new byte[128];
        Deque<Byte> byteDeqeue = new ArrayDeque();
        long tStart = System.currentTimeMillis();

        while(bLen == 0) {
            long tNow = System.currentTimeMillis();
            if (tNow - tStart > (long)waitTime) {
                break;
            }

            byte[] tmpRecv = this.mInterComm.Recv(recvChannel);
            if (tmpRecv == null) {
                try {
                    Thread.sleep(50L);
                } catch (InterruptedException var18) {
                }
            } else {
                int i;
                for(i = 0; i < tmpRecv.length; ++i) {
                    byteDeqeue.addLast(tmpRecv[i]);
                }

                boolean bCircle = true;

                while(byteDeqeue.size() >= 6 && bCircle) {
                    byte bTmp = (Byte)byteDeqeue.pollFirst();
                    if (bTmp == this.mAddress) {
                        bRecv[0] = bTmp;
                        bTmp = (Byte)byteDeqeue.pollFirst();
                        bRecv[1] = bTmp;
                        int crc_result;
                        byte[] tmp_bytes1;
                        switch(bTmp) {
                            case 1:
                                if (byteDeqeue.size() < 4) {
                                    byteDeqeue.addFirst(bRecv[1]);
                                    byteDeqeue.addFirst(bRecv[0]);
                                    bCircle = false;
                                } else {
                                    for(i = 0; i < 4; ++i) {
                                        bRecv[2 + i] = (Byte)byteDeqeue.pollFirst();
                                    }

                                    crc_result = Utility.calcCrc16(bRecv, 0, 4);
                                    tmp_bytes1 = Utility.getByteArray(crc_result);
                                    if (tmp_bytes1[2] == bRecv[4] && tmp_bytes1[3] == bRecv[5]) {
                                        bLen = 6;
                                        bCircle = false;
                                    } else {
                                        for(i = 5; i > 0; --i) {
                                            byteDeqeue.addFirst(bRecv[i]);
                                        }
                                    }
                                }
                                break;
                            case 5:
                                if (byteDeqeue.size() < 6) {
                                    byteDeqeue.addFirst(bRecv[1]);
                                    byteDeqeue.addFirst(bRecv[0]);
                                    bCircle = false;
                                } else {
                                    for(i = 0; i < 6; ++i) {
                                        bRecv[2 + i] = (Byte)byteDeqeue.pollFirst();
                                    }

                                    crc_result = Utility.calcCrc16(bRecv, 0, 6);
                                    tmp_bytes1 = Utility.getByteArray(crc_result);
                                    if (tmp_bytes1[2] == bRecv[6] && tmp_bytes1[3] == bRecv[7]) {
                                        bLen = 8;
                                        bCircle = false;
                                    } else {
                                        i = 7;

                                        while(true) {
                                            if (i <= 0) {
                                                break;
                                            }

                                            byteDeqeue.addFirst(bRecv[i]);
                                            --i;
                                        }
                                    }
                                }
                                break;
                            default:
                                byteDeqeue.addFirst(bRecv[1]);
                        }
                    }
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var17) {
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

    public boolean doReset() {
        byte[] CmdSend = new byte[]{(byte)this.mAddress, 5, 0, 105, -1, 0, 0, 0};
        int crc_result = Utility.calcCrc16(CmdSend, 0, 6);
        byte[] tmp_bytes = Utility.getByteArray(crc_result);
        CmdSend[6] = tmp_bytes[2];
        CmdSend[7] = tmp_bytes[3];
        this.mInterComm.clearRecv(2 * this.mChannel + 1);
        this.mInterComm.Send(CmdSend);
        if (MonitorService.autoPlc) {
            return true;
        } else {
            byte[] bRecvCmd = this.getRecv(5000, 2 * this.mChannel + 1, false);
            if (bRecvCmd != null && this.checkCmdResult(CmdSend, bRecvCmd)) {
                for(int i = 0; i < this.QStatus.length; ++i) {
                    this.QStatus[i] = false;
                }

                return true;
            } else {
                return false;
            }
        }
    }

    public boolean doCmd(int iType, int iAddress) {
        boolean bCheck = false;
        byte[] CmdSend = new byte[8];
        byte[] QuerySend = new byte[8];
        if (this.mInterComm == null) {
            return bCheck;
        } else {
            int crc_result;
            byte[] tmp_bytes;
            byte[] bRecvCmd;
            if (iAddress < 26) {
                CmdSend[0] = (byte)this.mAddress;
                CmdSend[1] = 5;
                QuerySend[0] = (byte)this.mAddress;
                QuerySend[1] = 1;
                switch(iType) {
                    case 0:
                        CmdSend[2] = 0;
                        CmdSend[3] = (byte)(4 * iAddress + 3);
                        CmdSend[4] = -1;
                        CmdSend[5] = 0;
                        QuerySend[2] = 0;
                        QuerySend[3] = (byte)(4 * iAddress + 4);
                        QuerySend[4] = 0;
                        QuerySend[5] = 1;
                        break;
                    case 1:
                        CmdSend[2] = 0;
                        CmdSend[3] = (byte)(4 * iAddress + 1);
                        CmdSend[4] = -1;
                        CmdSend[5] = 0;
                        QuerySend[2] = 0;
                        QuerySend[3] = (byte)(4 * iAddress + 2);
                        QuerySend[4] = 0;
                        QuerySend[5] = 1;
                }

                crc_result = Utility.calcCrc16(CmdSend, 0, 6);
                tmp_bytes = Utility.getByteArray(crc_result);
                CmdSend[6] = tmp_bytes[2];
                CmdSend[7] = tmp_bytes[3];
                this.mInterComm.clearRecv(2 * this.mChannel + 1);
                this.mInterComm.Send(CmdSend);
                if (MonitorService.autoPlc) {
                    return true;
                }

                bRecvCmd = this.getRecv(3000, 2 * this.mChannel + 1, false);
                if (bRecvCmd != null && this.checkCmdResult(CmdSend, bRecvCmd)) {
                    crc_result = Utility.calcCrc16(QuerySend, 0, 6);
                    tmp_bytes = Utility.getByteArray(crc_result);
                    QuerySend[6] = tmp_bytes[2];
                    QuerySend[7] = tmp_bytes[3];

                    for(int i = 0; i < 5; ++i) {
                        this.mInterComm.clearRecv(2 * this.mChannel + 1);
                        this.mInterComm.Send(QuerySend);
                        byte[] bRecvQuery = this.getRecv(3000, 2 * this.mChannel + 1, false);
                        if (bRecvQuery != null && bRecvQuery.length == 6 && bRecvQuery[3] == 1) {
                            bCheck = true;
                            break;
                        }

                        try {
                            Thread.sleep(1000L);
                        } catch (Exception var12) {
                        }
                    }
                }

                if (bCheck) {
                    switch(iType) {
                        case 0:
                            this.QStatus[iAddress] = false;
                            break;
                        case 1:
                            this.QStatus[iAddress] = true;
                    }
                } else {
                    this.doReset();
                }
            } else {
                CmdSend[0] = (byte)this.mAddress;
                CmdSend[1] = 5;
                CmdSend[2] = 0;
                CmdSend[3] = (byte)(112 + iAddress - 26);
                CmdSend[4] = -1;
                CmdSend[5] = 0;
                crc_result = Utility.calcCrc16(CmdSend, 0, 6);
                tmp_bytes = Utility.getByteArray(crc_result);
                CmdSend[6] = tmp_bytes[2];
                CmdSend[7] = tmp_bytes[3];
                this.mInterComm.clearRecv(2 * this.mChannel + 1);
                this.mInterComm.Send(CmdSend);
                if (MonitorService.autoPlc) {
                    return true;
                }

                bRecvCmd = this.getRecv(3000, 2 * this.mChannel + 1, false);
                if (bRecvCmd != null && this.checkCmdResult(CmdSend, bRecvCmd)) {
                    bCheck = true;
                }
            }

            return bCheck;
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

    public boolean checkCmdResult(byte[] bCmd, byte[] bRecv) {
        if (bRecv != null) {
            if (bRecv.length == 8) {
                for(int i = 0; i < 6; ++i) {
                    if (bRecv[i] != bCmd[i]) {
                        return false;
                    }
                }

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
