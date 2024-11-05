//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev;

import com.grean.station.domain.DO.cfg.CfgDevReg;
import com.grean.station.service.MonitorService;
import com.grean.station.utils.TimeHelper;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import org.joda.time.DateTime;

public class DevModbus extends DevService {
    private Random rand = new Random();
    private boolean onCmd = false;

    public DevModbus() {
    }
    @SuppressWarnings("FallThrough")
    private byte[] getRecv(int waitTime, int recvChannel, boolean bQuery) {
        int bLen = 0;
        byte[] bRecv = new byte[1024];
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
                } catch (InterruptedException var19) {
                }
            } else {
                int iTmp;
                for(iTmp = 0; iTmp < tmpRecv.length; ++iTmp) {
                    byteDeqeue.addLast(tmpRecv[iTmp]);
                }

                boolean bCircle = true;

                while(byteDeqeue.size() >= 8 && bCircle) {
                    byte bTmp = (Byte)byteDeqeue.pollFirst();
                    if (bTmp == this.mAddress) {
                        bRecv[0] = bTmp;
                        bTmp = (Byte)byteDeqeue.pollFirst();
                        bRecv[1] = bTmp;
                        int crc_result;
                        int i;
                        if (bQuery) {
                            switch(bTmp) {
                                case 3:
                                    bTmp = (Byte)byteDeqeue.pollFirst();
                                    bRecv[2] = bTmp;
                                    iTmp = bTmp & 255;
                                    if (byteDeqeue.size() < iTmp + 2) {
                                        byteDeqeue.addFirst(bRecv[2]);
                                        byteDeqeue.addFirst(bRecv[1]);
                                        byteDeqeue.addFirst(bRecv[0]);
                                        bCircle = false;
                                    } else {
                                        for(i = 0; i < iTmp + 2; ++i) {
                                            bRecv[3 + i] = (Byte)byteDeqeue.pollFirst();
                                        }

                                        crc_result = Utility.calcCrc16(bRecv, 0, iTmp + 3);
                                        byte[] tmp_bytes = Utility.getByteArray(crc_result);
                                        if (tmp_bytes[2] == bRecv[iTmp + 3] && tmp_bytes[3] == bRecv[iTmp + 4]) {
                                            bLen = iTmp + 5;
                                            bCircle = false;
                                        } else {
                                            for(i = iTmp + 4; i > 0; --i) {
                                                byteDeqeue.addFirst(bRecv[i]);
                                            }
                                        }
                                    }
                                    break;
                                default:
                                    byteDeqeue.addFirst(bRecv[1]);
                            }
                        } else {
                            switch(bTmp) {
                                case 6:
                                case 16:
                                    for(iTmp = 0; iTmp < 6; ++iTmp) {
                                        bRecv[2 + iTmp] = (Byte)byteDeqeue.pollFirst();
                                    }

                                    crc_result = Utility.calcCrc16(bRecv, 0, 6);
                                    byte[] tmp_bytes = Utility.getByteArray(crc_result);
                                    if (bRecv[2] < 32 && tmp_bytes[2] == bRecv[6] && tmp_bytes[3] == bRecv[7]) {
                                        bLen = 8;
                                        bCircle = false;
                                    } else {
                                        for(i = 7; i > 0; --i) {
                                            byteDeqeue.addFirst(bRecv[i]);
                                        }
                                    }
                                    break;
                                default:
                                    byteDeqeue.addFirst(bRecv[1]);
                            }
                        }
                    }
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var18) {
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
    @SuppressWarnings("FallThrough")
    void getQuery(int offsetHigh, int offsetLow, int lengthHigh, int lengthLow, String queryTag) {
        byte[] bQuery = new byte[]{(byte)this.mAddress, 3, (byte)offsetHigh, (byte)offsetLow, (byte)lengthHigh, (byte)lengthLow, 0, 0};
        int crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
        byte[] tmp_bytes = Utility.getByteArray(crc_result);
        bQuery[bQuery.length - 2] = tmp_bytes[2];
        bQuery[bQuery.length - 1] = tmp_bytes[3];
        int regOffset = 256 * offsetHigh + offsetLow;
        int regLength = 256 * lengthHigh + lengthLow;
        this.mInterComm.clearRecv(2 * this.mChannel);

        try {
            Thread.sleep(30L);
        } catch (Exception var20) {
        }

        this.mInterComm.Send(bQuery);
        byte[] bRecv = this.getRecv(2000, 2 * this.mChannel, true);
        int regIndex;
        CfgDevReg tmpReg;
        int i;
        String var16;
        byte var17;
        byte[] bTmp;
        if (bRecv != null) {
            if (bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 3) {
                for(i = 0; i < this.mRegList.size(); ++i) {
                    tmpReg = (CfgDevReg)this.mRegList.get(i);
                    regIndex = tmpReg.getOffset() - regOffset;
                    int byteIndex = regIndex * 2 + 3;
                    if (regIndex >= 0 && regIndex + tmpReg.getLength() <= regLength) {
                        var16 = tmpReg.getType().toLowerCase();
                        var17 = -1;
                        switch(var16.hashCode()) {
                            case 103195:
                                if (var16.equals("hex")) {
                                    var17 = 5;
                                }
                                break;
                            case 3052374:
                                if (var16.equals("char")) {
                                    var17 = 4;
                                }
                                break;
                            case 3076014:
                                if (var16.equals("date")) {
                                    var17 = 3;
                                }
                                break;
                            case 3655434:
                                if (var16.equals("word")) {
                                    var17 = 1;
                                }
                                break;
                            case 96007534:
                                if (var16.equals("dword")) {
                                    var17 = 0;
                                }
                                break;
                            case 97526364:
                                if (var16.equals("float")) {
                                    var17 = 2;
                                }
                        }

                        switch(var17) {
                            case 0:
                                this.params[i] = Utility.getDword(bRecv, byteIndex);
                                break;
                            case 1:
                                this.params[i] = Utility.getShort(bRecv, byteIndex);
                                break;
                            case 2:
                                this.params[i] = Double.parseDouble(Utility.getFloatBigEndianSwap(bRecv, byteIndex) + "");
                                break;
                            case 3:
                                try {
                                    this.params[i] = Timestamp.valueOf(Utility.getBCDTime(bRecv, byteIndex));
                                } catch (Exception var19) {
                                    this.params[i] = null;
                                }
                                break;
                            case 4:
                                this.params[i] = Utility.getString(bRecv, byteIndex, tmpReg.getLength() * 2);
                                break;
                            case 5:
                                bTmp = new byte[tmpReg.getLength() * 2];
                                System.arraycopy(bRecv, byteIndex, bTmp, 0, tmpReg.getLength() * 2);
                                this.params[i] = Utility.bytesToHexStringNoSpace(bTmp, bTmp.length);
                        }
                    }
                }

                this.setStatus(true);
                Utility.logInfo("Modbus Dev: 查询" + queryTag + "成功，通道" + this.mChannel);
            } else {
                this.setStatus(false);
                Utility.logInfo("Modbus Dev: 查询" + queryTag + "失败，通道" + this.mChannel + "，返回格式异常");
            }
        } else if (MonitorService.autoData) {
            this.setStatus(true);

            for(i = 0; i < this.mRegList.size(); ++i) {
                tmpReg = (CfgDevReg)this.mRegList.get(i);
                regIndex = tmpReg.getOffset() - regOffset;
                if (regIndex >= 0 && regIndex + tmpReg.getLength() <= regLength) {
                    var16 = tmpReg.getType().toLowerCase();
                    var17 = -1;
                    switch(var16.hashCode()) {
                        case 103195:
                            if (var16.equals("hex")) {
                                var17 = 5;
                            }
                            break;
                        case 3052374:
                            if (var16.equals("char")) {
                                var17 = 4;
                            }
                            break;
                        case 3076014:
                            if (var16.equals("date")) {
                                var17 = 3;
                            }
                            break;
                        case 3655434:
                            if (var16.equals("word")) {
                                var17 = 1;
                            }
                            break;
                        case 96007534:
                            if (var16.equals("dword")) {
                                var17 = 0;
                            }
                            break;
                        case 97526364:
                            if (var16.equals("float")) {
                                var17 = 2;
                            }
                    }

                    switch(var17) {
                        case 0:
                            this.params[i] = this.rand.nextInt(100);
                            break;
                        case 1:
                            this.params[i] = this.rand.nextInt(12);
                            break;
                        case 2:
                            this.params[i] = Utility.getFormatDouble((double)(this.rand.nextFloat() * 10.0F), 3);
                            break;
                        case 3:
                            this.params[i] = new Timestamp((new DateTime()).getMillis());
                            break;
                        case 4:
                            this.params[i] = "N";
                            break;
                        case 5:
                            bTmp = new byte[]{0, 17, 34, 51, 68, 85, 0, 17, 34, 51, 68, 85};
                            this.params[i] = Utility.bytesToHexStringNoSpace(bTmp, bTmp.length);
                    }
                }
            }
        } else {
            this.setStatus(false);
            Utility.logInfo("Modbus Dev: 查询" + queryTag + "失败，通道" + this.mChannel + "，无返回");
        }

    }

    public void doQuery() {
        if (!this.onCmd) {
            this.getQuery(16, 0, 0, 82, this.mCfgDev.getName() + "测量数据区");

            try {
                Thread.sleep(500L);
            } catch (Exception var3) {
            }

            if (!this.onCmd) {
                this.getQuery(16, 128, 0, 13, this.mCfgDev.getName() + "状态告警区");

                try {
                    Thread.sleep(500L);
                } catch (Exception var2) {
                }

                if (!this.onCmd) {
                    this.getQuery(16, 160, 0, 76, this.mCfgDev.getName() + "关键参数区");
                }
            }
        }
    }

    public void saveResult() {
    }
    @SuppressWarnings("FallThrough")
    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        int iResult = 1;
        if (MonitorService.autoData) {
            return true;
        } else {
            byte[] bCmd;
            int i;
            if (cmdType < 13) {
                bCmd = new byte[]{(byte)this.mAddress, 16, 18, 0, 0, 1, 2, 0, (byte)cmdType, 0, 0};
            } else {
                label85:
                switch(cmdType) {
                    case 13:
                        bCmd = new byte[]{(byte)this.mAddress, 16, 18, 0, 0, 4, 8, 0, (byte)cmdType, 0, 0, 0, 0, 0, 0, 0, 0};
                        String sysTime;
                        if (cmdParam == null) {
                            sysTime = TimeHelper.getCurrentTimeString();
                        } else {
                            if (cmdParam.length() != 14) {
                                return false;
                            }

                            sysTime = cmdParam;
                        }

                        i = 2;

                        while(true) {
                            if (i >= 14) {
                                break label85;
                            }

                            try {
                                bCmd[8 + i / 2] = (byte)(Integer.parseInt(sysTime.substring(i, i + 1)) * 16 + Integer.parseInt(sysTime.substring(i + 1, i + 2)));
                            } catch (Exception var14) {
                                return false;
                            }

                            i += 2;
                        }
                    default:
                        bCmd = new byte[]{(byte)this.mAddress, 16, 18, 0, 0, 2, 4, 0, (byte)cmdType, 0, 0, 0, 0};

                        try {
                            i = Integer.parseInt(cmdParam);
                            bCmd[9] = (byte)(i / 256 & 255);
                            bCmd[10] = (byte)(i % 256 & 255);
                        } catch (Exception var13) {
                            return false;
                        }
                }
            }

            this.onCmd = true;
            i = Utility.calcCrc16(bCmd, 0, bCmd.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(i);
            bCmd[bCmd.length - 2] = tmp_bytes[2];
            bCmd[bCmd.length - 1] = tmp_bytes[3];

            for(int iTimes = this.mCmdTimes; iTimes > 0; --iTimes) {
                try {
                    Thread.sleep(100L);
                    this.mInterComm.clearRecv(2 * this.mChannel + 1);
                    Thread.sleep(100L);
                    this.mInterComm.Send(bCmd);
                } catch (Exception var12) {
                }

                for(i = 0; i < 6; ++i) {
                    byte[] bRecv = this.getRecv(500, 2 * this.mChannel + 1, false);
                    iResult = this.getCmdResult("Modbus", bCmd, bRecv);
                    if (iResult == 1) {
                        break;
                    }
                }

                if (iResult == 1) {
                    break;
                }
            }

            if (cmdQN != null && this.devExeListener != null) {
                this.devExeListener.OnExeEvent(cmdQN, cmdType, iResult);
            }

            this.onCmd = false;
            return iResult == 1;
        }
    }

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                DevModbus.this.doDevCmd(cmdType, cmdParam, cmdQN);
            }
        });
    }

    public void doMeaCmd() {
        this.doDevCmdThread(1, (String)null, (String)null);
    }

    public void doStdCmd(String strQN, String strParam) {
        this.doDevCmdThread(2, strParam, strQN);
    }

    public void doZeroCmd(String strQN, String strParam) {
        this.doDevCmdThread(3, strParam, strQN);
    }

    public void doSpanCmd(String strQN, String strParam) {
        this.doDevCmdThread(4, strParam, strQN);
    }

    public void doBlnkCmd(String strQN, String strParam) {
        this.doDevCmdThread(5, strParam, strQN);
    }

    public void doParCmd(String strQN, String strParam) {
        this.doDevCmdThread(6, strParam, strQN);
    }

    public void doRcvrCmd(String strQN, String strParam) {
        this.doDevCmdThread(7, strParam, strQN);
    }

    public void doBlnkCal(String strQN, String strParam) {
        this.doDevCmdThread(8, strParam, strQN);
    }

    public void doStdCal(String strQN, String strParam) {
        this.doDevCmdThread(9, strParam, strQN);
    }

    public void doInitCmd() {
        this.doDevCmdThread(10, (String)null, (String)null);
    }

    public void doStopCmd() {
        this.doDevCmdThread(11, (String)null, (String)null);
    }

    public void doRsetCmd() {
        this.doDevCmdThread(12, (String)null, (String)null);
    }

    public void doSetTime(String strQN, String strParam) {
        this.doDevCmdThread(13, strParam, strQN);
    }

    public void doSetBase(String strParam) {
        this.doDevCmdThread(100, strParam, (String)null);
    }
}
