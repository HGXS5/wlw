//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev;

import com.grean.station.domain.DO.cfg.CfgDevCmd;
import com.grean.station.domain.DO.cfg.CfgDevQuery;
import com.grean.station.domain.DO.cfg.CfgDevReg;
import com.grean.station.service.MonitorService;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.joda.time.DateTime;

public class UserModbusTcp extends DevService {
    private Random rand = new Random();
    private boolean onCmd = false;

    public UserModbusTcp() {
    }

    private byte[] getRecv(int waitTime, int recvChannel, boolean bQuery) {
        int bLen = 0;
        byte[] bRecv = new byte[1024];
        long tStart = System.currentTimeMillis();

        while(true) {
            long tNow = System.currentTimeMillis();
            if (tNow - tStart > (long)waitTime) {
                this.mInterComm.clearRecv(recvChannel);
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
                if (bLen > 10 && bRecv[6] == (byte)this.mAddress) {
                    if (bQuery) {
                        if (bRecv[7] != 3 && bRecv[7] != 4) {
                            bLen = 0;
                        } else if ((bRecv[5] & 255) + 6 == bLen && (bRecv[8] & 255) + 9 == bLen) {
                            break;
                        }
                    } else if (bRecv[7] != 5 && bRecv[7] != 6 && bRecv[7] != 15 && bRecv[7] != 16) {
                        bLen = 0;
                    } else if ((bRecv[5] & 255) + 6 == bLen && bLen == 12) {
                        break;
                    }
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var12) {
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
    void getQuery(CfgDevQuery cfgDevQuery) {
        Double dTmp = null;
        String strLog = "Modbus Dev: 自定义ModbusTcp协议查询";
        if (this.getFactorName() != null) {
            strLog = strLog + this.getFactorName();
        }

        String strTmp = cfgDevQuery.getQueryCmd();
        strTmp = strTmp.replaceAll(" ", "");
        byte[] bQuery = Utility.hexStringToBytes(strTmp);
        this.mInterComm.clearRecv(2 * this.mChannel);
        this.mInterComm.Send(bQuery);
        byte[] bRecv = this.getRecv(1000, 2 * this.mChannel, true);
        int i;
        CfgDevReg cfgDevReg;
        String var10;
        byte var11;
        byte[] bTmp;
        if (bRecv != null) {
            label189:
            for(i = 0; i < this.mRegList.size(); ++i) {
                cfgDevReg = (CfgDevReg)this.mRegList.get(i);
                if (cfgDevReg.getId() == cfgDevQuery.getQueryType()) {
                    int byteIndex = cfgDevQuery.getDataIndex();
                    var10 = cfgDevQuery.getDataType().toLowerCase();
                    var11 = -1;
                    switch(var10.hashCode()) {
                        case 103195:
                            if (var10.equals("hex")) {
                                var11 = 5;
                            }
                            break;
                        case 3052374:
                            if (var10.equals("char")) {
                                var11 = 4;
                            }
                            break;
                        case 3076014:
                            if (var10.equals("date")) {
                                var11 = 3;
                            }
                            break;
                        case 3655434:
                            if (var10.equals("word")) {
                                var11 = 1;
                            }
                            break;
                        case 96007534:
                            if (var10.equals("dword")) {
                                var11 = 0;
                            }
                            break;
                        case 97526364:
                            if (var10.equals("float")) {
                                var11 = 2;
                            }
                    }

                    byte var13;
                    String var16;
                    switch(var11) {
                        case 0:
                            if (byteIndex + 4 < bRecv.length) {
                                if (cfgDevReg.getType().toLowerCase().equals("float")) {
                                    var16 = cfgDevQuery.getDataFormat();
                                    var13 = -1;
                                    switch(var16.hashCode()) {
                                        case 710813158:
                                            if (var16.equals("大端交换")) {
                                                var13 = 1;
                                            }
                                            break;
                                        case 711030198:
                                            if (var16.equals("大端模式")) {
                                                var13 = 0;
                                            }
                                    }

                                    switch(var13) {
                                        case 0:
                                            dTmp = (double)Utility.getInt(bRecv, byteIndex) * cfgDevQuery.getUpdateA() + cfgDevQuery.getUpdateB();
                                            break;
                                        case 1:
                                            dTmp = (double)Utility.getDword(bRecv, byteIndex) * cfgDevQuery.getUpdateA() + cfgDevQuery.getUpdateB();
                                    }

                                    this.params[i] = dTmp;
                                } else {
                                    var16 = cfgDevQuery.getDataFormat();
                                    var13 = -1;
                                    switch(var16.hashCode()) {
                                        case 710813158:
                                            if (var16.equals("大端交换")) {
                                                var13 = 1;
                                            }
                                            break;
                                        case 711030198:
                                            if (var16.equals("大端模式")) {
                                                var13 = 0;
                                            }
                                    }

                                    switch(var13) {
                                        case 0:
                                            this.params[i] = Utility.getInt(bRecv, byteIndex);
                                            break label189;
                                        case 1:
                                            this.params[i] = Utility.getDword(bRecv, byteIndex);
                                    }
                                }
                            }
                            break label189;
                        case 1:
                            if (cfgDevReg.getType().toLowerCase().equals("float")) {
                                dTmp = (double)Utility.getShort(bRecv, byteIndex) * cfgDevQuery.getUpdateA() + cfgDevQuery.getUpdateB();
                                this.params[i] = dTmp;
                            } else {
                                this.params[i] = Utility.getShort(bRecv, byteIndex);
                            }
                            break label189;
                        case 2:
                            var16 = cfgDevQuery.getDataFormat();
                            var13 = -1;
                            switch(var16.hashCode()) {
                                case 2063422:
                                    if (var16.equals("BCD码")) {
                                        var13 = 4;
                                    }
                                    break;
                                case 710813158:
                                    if (var16.equals("大端交换")) {
                                        var13 = 2;
                                    }
                                    break;
                                case 711030198:
                                    if (var16.equals("大端模式")) {
                                        var13 = 0;
                                    }
                                    break;
                                case 732977662:
                                    if (var16.equals("小端交换")) {
                                        var13 = 3;
                                    }
                                    break;
                                case 733194702:
                                    if (var16.equals("小端模式")) {
                                        var13 = 1;
                                    }
                            }

                            switch(var13) {
                                case 0:
                                    dTmp = (double)Utility.getFloatBigEndian(bRecv, byteIndex) + 0.0D;
                                    break;
                                case 1:
                                    dTmp = (double)Utility.getFloatLittleEndian(bRecv, byteIndex) + 0.0D;
                                    break;
                                case 2:
                                    dTmp = (double)Utility.getFloatBigEndianSwap(bRecv, byteIndex) + 0.0D;
                                    break;
                                case 3:
                                    dTmp = (double)Utility.getFloatLittleEndianSwap(bRecv, byteIndex) + 0.0D;
                                    break;
                                case 4:
                                    Integer iTmp = Utility.getBCDVal(bRecv, byteIndex, cfgDevQuery.getDataLength());
                                    if (iTmp != null) {
                                        dTmp = iTmp.doubleValue();
                                    } else {
                                        dTmp = 0.0D;
                                    }
                            }

                            dTmp = dTmp * cfgDevQuery.getUpdateA() + cfgDevQuery.getUpdateB();
                            this.params[i] = dTmp;
                            break label189;
                        case 3:
                            try {
                                this.params[i] = Timestamp.valueOf(Utility.getBCDTime(bRecv, byteIndex));
                            } catch (Exception var15) {
                                this.params[i] = null;
                            }
                            break label189;
                        case 4:
                            this.params[i] = Utility.getString(bRecv, byteIndex, cfgDevQuery.getDataLength() * 2);
                            break label189;
                        case 5:
                            bTmp = new byte[cfgDevQuery.getDataLength() * 2];
                            System.arraycopy(bRecv, byteIndex, bTmp, 0, cfgDevQuery.getDataLength() * 2);
                            this.params[i] = Utility.bytesToHexStringNoSpace(bTmp, bTmp.length);
                        default:
                            break label189;
                    }
                }
            }

            this.setStatus(true);
            Utility.logInfo(strLog + "(" + cfgDevQuery.getQueryType() + ")成功");
        } else if (MonitorService.autoData) {
            this.setStatus(true);

            for(i = 0; i < this.mRegList.size(); ++i) {
                cfgDevReg = (CfgDevReg)this.mRegList.get(i);
                if (cfgDevReg.getId() == cfgDevQuery.getQueryType()) {
                    var10 = cfgDevQuery.getDataType().toLowerCase();
                    var11 = -1;
                    switch(var10.hashCode()) {
                        case 103195:
                            if (var10.equals("hex")) {
                                var11 = 5;
                            }
                            break;
                        case 3052374:
                            if (var10.equals("char")) {
                                var11 = 4;
                            }
                            break;
                        case 3076014:
                            if (var10.equals("date")) {
                                var11 = 3;
                            }
                            break;
                        case 3655434:
                            if (var10.equals("word")) {
                                var11 = 1;
                            }
                            break;
                        case 96007534:
                            if (var10.equals("dword")) {
                                var11 = 0;
                            }
                            break;
                        case 97526364:
                            if (var10.equals("float")) {
                                var11 = 2;
                            }
                    }

                    switch(var11) {
                        case 0:
                            if (cfgDevReg.getType().toLowerCase().equals("float")) {
                                this.params[i] = this.rand.nextDouble() * 10.0D;
                            } else {
                                this.params[i] = this.rand.nextInt(100);
                            }

                            return;
                        case 1:
                            if (cfgDevReg.getType().toLowerCase().equals("float")) {
                                this.params[i] = this.rand.nextDouble() * 10.0D;
                            } else {
                                this.params[i] = this.rand.nextInt(100);
                            }

                            return;
                        case 2:
                            this.params[i] = this.rand.nextDouble() * 10.0D;
                            return;
                        case 3:
                            this.params[i] = new Timestamp((new DateTime()).getMillis());
                            return;
                        case 4:
                            this.params[i] = "N";
                            return;
                        case 5:
                            bTmp = new byte[]{0, 17, 34, 51, 68, 85, 0, 17, 34, 51, 68, 85};
                            this.params[i] = Utility.bytesToHexStringNoSpace(bTmp, bTmp.length);
                            return;
                        default:
                            return;
                    }
                }
            }
        } else {
            this.setStatus(false);
            Utility.logInfo(strLog + "(" + cfgDevQuery.getQueryType() + ")失败");
        }

    }

    public void doQuery() {
        Iterator var1 = this.mCfgDev.getQueryList().iterator();

        while(true) {
            CfgDevQuery cfgDevQuery;
            do {
                do {
                    do {
                        do {
                            do {
                                do {
                                    do {
                                        if (!var1.hasNext()) {
                                            return;
                                        }

                                        cfgDevQuery = (CfgDevQuery)var1.next();
                                    } while(cfgDevQuery.getFactorid() != this.mCfgFactor.getId());
                                } while(this.mCmdType == 2 && cfgDevQuery.getQueryType() != 8);
                            } while(this.mCmdType == 3 && cfgDevQuery.getQueryType() != 14);
                        } while(this.mCmdType == 4 && cfgDevQuery.getQueryType() != 17);
                    } while(this.mCmdType == 5 && cfgDevQuery.getQueryType() != 11);
                } while(this.mCmdType == 6 && cfgDevQuery.getQueryType() != 23);
            } while(this.mCmdType == 7 && cfgDevQuery.getQueryType() != 20);

            if (this.onCmd) {
                return;
            }

            this.getQuery(cfgDevQuery);

            try {
                Thread.sleep(500L);
            } catch (Exception var4) {
            }
        }
    }

    public void saveResult() {
    }

    private List<CfgDevCmd> getCmdList(int cmdType) {
        List<CfgDevCmd> cmdList = new ArrayList();
        Iterator var3;
        CfgDevCmd cfgDevCmd;
        switch(cmdType) {
            case 1:
                var3 = this.mCfgDev.getMeaCmdList().iterator();

                while(true) {
                    do {
                        if (!var3.hasNext()) {
                            return cmdList;
                        }

                        cfgDevCmd = (CfgDevCmd)var3.next();
                    } while(cfgDevCmd.getFactorid() != this.mCfgFactor.getId() && cfgDevCmd.getFactorid() != 0);

                    cmdList.add(cfgDevCmd);
                }
            case 2:
                var3 = this.mCfgDev.getStdCmdList().iterator();

                while(true) {
                    do {
                        if (!var3.hasNext()) {
                            return cmdList;
                        }

                        cfgDevCmd = (CfgDevCmd)var3.next();
                    } while(cfgDevCmd.getFactorid() != this.mCfgFactor.getId() && cfgDevCmd.getFactorid() != 0);

                    cmdList.add(cfgDevCmd);
                }
            case 3:
                var3 = this.mCfgDev.getZeroCmdList().iterator();

                while(true) {
                    do {
                        if (!var3.hasNext()) {
                            return cmdList;
                        }

                        cfgDevCmd = (CfgDevCmd)var3.next();
                    } while(cfgDevCmd.getFactorid() != this.mCfgFactor.getId() && cfgDevCmd.getFactorid() != 0);

                    cmdList.add(cfgDevCmd);
                }
            case 4:
                var3 = this.mCfgDev.getSpanCmdList().iterator();

                while(true) {
                    do {
                        if (!var3.hasNext()) {
                            return cmdList;
                        }

                        cfgDevCmd = (CfgDevCmd)var3.next();
                    } while(cfgDevCmd.getFactorid() != this.mCfgFactor.getId() && cfgDevCmd.getFactorid() != 0);

                    cmdList.add(cfgDevCmd);
                }
            case 5:
                var3 = this.mCfgDev.getBlnkCmdList().iterator();

                while(true) {
                    do {
                        if (!var3.hasNext()) {
                            return cmdList;
                        }

                        cfgDevCmd = (CfgDevCmd)var3.next();
                    } while(cfgDevCmd.getFactorid() != this.mCfgFactor.getId() && cfgDevCmd.getFactorid() != 0);

                    cmdList.add(cfgDevCmd);
                }
            case 6:
                var3 = this.mCfgDev.getParCmdList().iterator();

                while(true) {
                    do {
                        if (!var3.hasNext()) {
                            return cmdList;
                        }

                        cfgDevCmd = (CfgDevCmd)var3.next();
                    } while(cfgDevCmd.getFactorid() != this.mCfgFactor.getId() && cfgDevCmd.getFactorid() != 0);

                    cmdList.add(cfgDevCmd);
                }
            case 7:
                var3 = this.mCfgDev.getRcvrCmdList().iterator();

                while(true) {
                    do {
                        if (!var3.hasNext()) {
                            return cmdList;
                        }

                        cfgDevCmd = (CfgDevCmd)var3.next();
                    } while(cfgDevCmd.getFactorid() != this.mCfgFactor.getId() && cfgDevCmd.getFactorid() != 0);

                    cmdList.add(cfgDevCmd);
                }
            case 8:
                var3 = this.mCfgDev.getBlnkCalList().iterator();

                while(true) {
                    do {
                        if (!var3.hasNext()) {
                            return cmdList;
                        }

                        cfgDevCmd = (CfgDevCmd)var3.next();
                    } while(cfgDevCmd.getFactorid() != this.mCfgFactor.getId() && cfgDevCmd.getFactorid() != 0);

                    cmdList.add(cfgDevCmd);
                }
            case 9:
                var3 = this.mCfgDev.getStdCalList().iterator();

                while(true) {
                    do {
                        if (!var3.hasNext()) {
                            return cmdList;
                        }

                        cfgDevCmd = (CfgDevCmd)var3.next();
                    } while(cfgDevCmd.getFactorid() != this.mCfgFactor.getId() && cfgDevCmd.getFactorid() != 0);

                    cmdList.add(cfgDevCmd);
                }
            default:
                return cmdList;
        }
    }
    @SuppressWarnings("FallThrough")
    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        int iResult = 1;
        List<CfgDevCmd> cmdList = this.getCmdList(cmdType);
        this.mCmdType = cmdType;
        this.onCmd = true;
        Iterator var6 = cmdList.iterator();

        while(var6.hasNext()) {
            CfgDevCmd cfgDevCmd = (CfgDevCmd)var6.next();
            String strTmp = cfgDevCmd.getCmdString();
            strTmp = strTmp.replaceAll(" ", "");
            byte[] bCmd = Utility.hexStringToBytes(strTmp);

            for(int iTimes = this.mCmdTimes; iTimes > 0; --iTimes) {
                this.mInterComm.clearRecv(2 * this.mChannel + 1);
                this.mInterComm.Send(bCmd);
                byte[] bRecv = this.getRecv(1000, 2 * this.mChannel + 1, false);
                iResult = this.getCmdResultTcp("User ModbusTcp", bCmd, bRecv);
                if (iResult == 1) {
                    break;
                }
            }

            try {
                Thread.sleep((long)(cfgDevCmd.getCmdDelay() * 1000));
            } catch (Exception var12) {
                this.onCmd = false;
            }
        }

        this.onCmd = false;
        if (cmdQN != null && this.devExeListener != null) {
            this.devExeListener.OnExeEvent(cmdQN, cmdType, iResult);
        }

        if (iResult == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                UserModbusTcp.this.doDevCmd(cmdType, cmdParam, cmdQN);
            }
        });
    }

    public void doMeaCmd() {
        this.doDevCmdThread(1, "", "");
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
    }

    public void doStopCmd() {
    }

    public void doRsetCmd() {
    }

    public void doSetTime(String strQN, String strParam) {
    }
}
