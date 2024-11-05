//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev;

import com.grean.station.comm.CommInter;
import com.grean.station.domain.DO.cfg.CfgDev;
import com.grean.station.domain.DO.cfg.CfgDevReg;
import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.service.MonitorService;
import com.grean.station.utils.TimeHelper;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.collections4.map.HashedMap;

public abstract class DevService {
    private DevService.QueryThread queryThread;
    private DevService.ReadThread readThread;
    public Object[] params = null;
    public Double[] channelVals = new Double[16];
    public Timestamp[] channelTime = new Timestamp[16];
    public boolean bConnect = false;
    public int disCount = 0;
    public DevExeListener devExeListener = null;
    public CommInter mInterComm = null;
    public CfgDev mCfgDev = null;
    public CfgFactor mCfgFactor = null;
    public List mRegList = null;
    public int mAddress;
    public int mChannel;
    public int mCmdType = 1;
    public int mCmdTimes = 3;
    public int waitTime = 0;
    public boolean waitExit = true;
    public String waitStatus = "";
    public int waitAnswer = 2000;
    public boolean runState = false;
    public boolean[] faultState = new boolean[12];
    public int warnState = 0;
    public Map<Integer, String> warnMap = new HashMap();
    public Map<String, String> paramMap = new HashedMap();
    public Queue<String> warnQueue = new ArrayBlockingQueue(240);
    public ExecutorService fixedThreadPool = Executors.newFixedThreadPool(8);

    public DevService() {
    }

    public int getmCmdType() {
        return this.mCmdType;
    }

    public void setmCmdType(int mCmdType) {
        this.mCmdType = mCmdType;
    }

    public boolean isRunState() {
        return this.runState;
    }

    public void setRunState(boolean runState) {
        this.runState = runState;
    }

    public boolean isFaultState() {
        for(int i = 0; i < this.faultState.length; ++i) {
            if (this.faultState[i]) {
                return true;
            }
        }

        return false;
    }

    public boolean getFaultState(int index) {
        return index < this.faultState.length ? this.faultState[index] : false;
    }

    public void setFaultState(boolean[] faultState) {
        for(int i = 0; i < this.faultState.length && i < faultState.length; ++i) {
            this.faultState[i] = faultState[i];
        }

    }

    public void resetFaultState() {
        for(int i = 0; i < this.faultState.length; ++i) {
            this.faultState[i] = false;
        }

    }

    public void init(CommInter interComm, CfgDev cfgDev, CfgFactor cfgFactor, List regList, int channel, int cmdTime) {
        this.mInterComm = interComm;
        this.mCfgDev = cfgDev;
        this.mCfgFactor = cfgFactor;
        this.mRegList = regList;
        this.mChannel = channel;
        this.mCmdTimes = cmdTime;
        if (this.mCfgDev != null) {
            if (this.mCfgDev.getAddress() > 256L) {
                this.mAddress = 256;
            } else {
                this.mAddress = this.mCfgDev.getAddress().intValue();
            }
        }

        if (this.mCfgFactor != null && this.mAddress < 256) {
            this.mAddress = this.mCfgFactor.getDevAddress();
        }

        if (this.mRegList != null) {
            this.params = new Object[this.mRegList.size()];
            this.initParamVal();
        }

    }

    public DevExeListener getDevExeListener() {
        return this.devExeListener;
    }

    public void setDevExeListener(DevExeListener devExeListener) {
        this.devExeListener = devExeListener;
    }

    public abstract void doQuery();

    public abstract void saveResult();

    public abstract boolean doDevCmd(int var1, String var2, String var3);

    public abstract void doMeaCmd();

    public abstract void doStdCmd(String var1, String var2);

    public abstract void doZeroCmd(String var1, String var2);

    public abstract void doSpanCmd(String var1, String var2);

    public abstract void doBlnkCmd(String var1, String var2);

    public abstract void doParCmd(String var1, String var2);

    public abstract void doRcvrCmd(String var1, String var2);

    public abstract void doBlnkCal(String var1, String var2);

    public abstract void doStdCal(String var1, String var2);

    public abstract void doInitCmd();

    public abstract void doStopCmd();

    public abstract void doRsetCmd();

    public abstract void doSetTime(String var1, String var2);

    public void doSetBase(String strParam) {
    }

    public List getmRegList() {
        return this.mRegList;
    }

    public void setmRegList(List mRegList) {
        this.mRegList = mRegList;
    }

    public void startQuery() {
        this.queryThread = new DevService.QueryThread();
        this.queryThread.start();
    }

    public void stopQuery() {
        if (this.queryThread != null) {
            this.queryThread.interrupt();
        }

    }

    public void startRead() {
        this.readThread = new DevService.ReadThread();
        this.readThread.start();
    }

    public Object getParam(int index) {
        return this.params != null && index >= 0 && index < this.params.length ? this.params[index] : null;
    }

    public Object getParam(String strParam) {
        if (this.mRegList != null) {
            for(int i = 0; i < this.mRegList.size(); ++i) {
                CfgDevReg cfgDevReg = (CfgDevReg)this.mRegList.get(i);
                if (cfgDevReg.getCode().equals(strParam)) {
                    return this.params[i];
                }
            }

            return null;
        } else {
            return null;
        }
    }
    @SuppressWarnings("FallThrough")
    public String getParamVal(String paramName) {
        if (this.mRegList != null) {
            for(int i = 0; i < this.mRegList.size(); ++i) {
                CfgDevReg cfgDevReg = (CfgDevReg)this.mRegList.get(i);
                if (cfgDevReg.getParamName() != null && cfgDevReg.getParamName().equals(paramName)) {
                    if (this.params[i] == null) {
                        return null;
                    }

                    String var4 = cfgDevReg.getType().toLowerCase();
                    byte var5 = -1;
                    switch(var4.hashCode()) {
                        case 3052374:
                            if (var4.equals("char")) {
                                var5 = 3;
                            }
                            break;
                        case 3076014:
                            if (var4.equals("date")) {
                                var5 = 2;
                            }
                            break;
                        case 3655434:
                            if (var4.equals("word")) {
                                var5 = 0;
                            }
                            break;
                        case 96007534:
                            if (var4.equals("dword")) {
                                var5 = 1;
                            }
                    }

                    switch(var5) {
                        case 0:
                        case 1:
                            return String.format("%d", (Integer)this.params[i]);
                        case 2:
                            return TimeHelper.formatDataTime_yyyyMMddHHmmss((Timestamp)this.params[i]);
                        case 3:
                            return (String)this.params[i];
                        default:
                            return String.format("%f", (Double)this.params[i]);
                    }
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public boolean setParam(String strParam, Object valueParam) {
        if (this.mRegList == null) {
            return false;
        } else {
            for(int i = 0; i < this.mRegList.size(); ++i) {
                CfgDevReg cfgDevReg = (CfgDevReg)this.mRegList.get(i);
                if (cfgDevReg.getCode().equals(strParam)) {
                    this.params[i] = valueParam;
                    return true;
                }
            }

            return false;
        }
    }

    public Double getChannelVals(int channelID) {
        return channelID > 0 && channelID <= this.channelVals.length ? this.channelVals[channelID - 1] : null;
    }

    public Timestamp getChannelTime(int channelID) {
        return channelID > 0 && channelID <= this.channelTime.length ? this.channelTime[channelID - 1] : null;
    }

    public Object[] getParams(int paramSize) {
        Object[] objects = new Object[paramSize];
        if (this.mRegList != null) {
            for(int i = 0; i < paramSize; ++i) {
                for(int j = 0; j < this.mRegList.size(); ++j) {
                    CfgDevReg cfgDevReg = (CfgDevReg)this.mRegList.get(j);
                    if (cfgDevReg.getRecid() == i + 1) {
                        objects[i] = this.params[j];
                        break;
                    }
                }
            }
        }

        return objects;
    }

    public int getDevID() {
        return this.mCfgDev != null ? this.mCfgDev.getId() : 0;
    }

    public int getFactorID() {
        return this.mCfgFactor != null ? this.mCfgFactor.getId() : 0;
    }

    public int getRelatedID() {
        return this.mCfgDev != null ? this.mCfgDev.getRelated_id() : 0;
    }

    public int getRelatedID2() {
        return this.mCfgDev != null ? this.mCfgDev.getRelated_id2() : 0;
    }

    public String getDevType() {
        return this.mCfgDev != null ? this.mCfgDev.getType() : "DUNNO";
    }

    public String getDevName() {
        return this.mCfgDev != null ? this.mCfgDev.getNick() : "";
    }

    public String getDevProtocol() {
        return this.mCfgDev != null ? this.mCfgDev.getProtocol() : "";
    }
    @SuppressWarnings("FallThrough")
    public String getDevSaveTag(int cmdType) {
        if (this.mCfgDev != null) {
            switch(cmdType) {
                case 1:
                    return this.mCfgDev.getSave_mea_tag();
                case 2:
                    return this.mCfgDev.getSave_std_tag();
                case 3:
                    return this.mCfgDev.getSave_zero_tag();
                case 4:
                    return this.mCfgDev.getSave_span_tag();
                case 5:
                    return this.mCfgDev.getSave_blnk_tag();
                case 6:
                    return this.mCfgDev.getSave_parl_tag();
                case 7:
                    return this.mCfgDev.getSave_rcvr_tag();
                default:
                    return null;
            }
        } else {
            return null;
        }
    }
    @SuppressWarnings("FallThrough")
    public String getDevStartTag(int cmdType) {
        if (this.mCfgDev != null) {
            switch(cmdType) {
                case 1:
                    return this.mCfgDev.getStart_mea_tag();
                case 2:
                    return this.mCfgDev.getStart_std_tag();
                case 3:
                    return this.mCfgDev.getStart_zero_tag();
                case 4:
                    return this.mCfgDev.getStart_span_tag();
                case 5:
                    return this.mCfgDev.getStart_blnk_tag();
                case 6:
                    return this.mCfgDev.getStart_parl_tag();
                case 7:
                    return this.mCfgDev.getStart_rcvr_tag();
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    public int getStartTagCmd(String startTag) {
        if (this.mCfgDev != null) {
            if (this.mCfgDev.getStart_mea_tag() != null && this.mCfgDev.getStart_mea_tag().equalsIgnoreCase(startTag)) {
                return 1;
            } else if (this.mCfgDev.getStart_std_tag() != null && this.mCfgDev.getStart_std_tag().equalsIgnoreCase(startTag)) {
                return 2;
            } else if (this.mCfgDev.getStart_zero_tag() != null && this.mCfgDev.getStart_zero_tag().equalsIgnoreCase(startTag)) {
                return 3;
            } else if (this.mCfgDev.getStart_span_tag() != null && this.mCfgDev.getStart_span_tag().equalsIgnoreCase(startTag)) {
                return 4;
            } else if (this.mCfgDev.getStart_blnk_tag() != null && this.mCfgDev.getStart_blnk_tag().equalsIgnoreCase(startTag)) {
                return 5;
            } else if (this.mCfgDev.getStart_parl_tag() != null && this.mCfgDev.getStart_parl_tag().equalsIgnoreCase(startTag)) {
                return 6;
            } else {
                return this.mCfgDev.getStart_rcvr_tag() != null && this.mCfgDev.getStart_rcvr_tag().equalsIgnoreCase(startTag) ? 7 : 0;
            }
        } else {
            return 0;
        }
    }

    public String getFactorName() {
        return this.mCfgFactor != null ? this.mCfgFactor.getName() : null;
    }

    public void setStatus(boolean connected) {
        if (connected) {
            this.bConnect = true;
            this.disCount = 0;
        } else {
            ++this.disCount;
            if (this.disCount > 5) {
                this.bConnect = false;
            }
        }

    }

    public boolean isbConnect() {
        return this.bConnect;
    }

    public void setbConnect(boolean bConnect) {
        this.bConnect = bConnect;
    }

    public int getWaitTime() {
        return this.waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public boolean isWaitExit() {
        return this.waitExit;
    }

    public void setWaitExit(boolean waitExit) {
        this.waitExit = waitExit;
    }

    public String getWaitStatus() {
        return this.waitStatus;
    }

    public void setWaitStatus(String waitStatus) {
        this.waitStatus = waitStatus;
    }
    @SuppressWarnings("FallThrough")
    public byte[] getRecv(CommInter commInter, int recvAddress, int recvChannel, boolean bQuery, int waitTime) {
        int bLen = 0;
        byte[] bRecv = new byte[1024];
        Deque<Byte> byteDeqeue = new ArrayDeque();
        long tStart = System.currentTimeMillis();

        while(bLen == 0) {
            long tNow = System.currentTimeMillis();
            if (tNow - tStart > (long)waitTime) {
                break;
            }

            byte[] tmpRecv = commInter.Recv(recvChannel);
            if (tmpRecv == null) {
                try {
                    Thread.sleep(50L);
                } catch (InterruptedException var21) {
                }
            } else {
                int iTmp;
                for(iTmp = 0; iTmp < tmpRecv.length; ++iTmp) {
                    byteDeqeue.addLast(tmpRecv[iTmp]);
                }

                boolean bCircle = true;

                while(byteDeqeue.size() >= 7 && bCircle) {
                    tNow = System.currentTimeMillis();
                    if (tNow - tStart > (long)waitTime) {
                        break;
                    }

                    byte bTmp = (Byte)byteDeqeue.pollFirst();
                    if ((bTmp & 255) == recvAddress) {
                        bRecv[0] = bTmp;
                        bTmp = (Byte)byteDeqeue.pollFirst();
                        bRecv[1] = bTmp;
                        int crc_result;
                        int i;
                        if (bQuery) {
                            switch(bTmp) {
                                case 3:
                                case 4:
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
                                case 5:
                                case 6:
                                case 15:
                                case 16:
                                    if (byteDeqeue.size() < 6) {
                                        byteDeqeue.addFirst(bRecv[1]);
                                        byteDeqeue.addFirst(bRecv[0]);
                                        bCircle = false;
                                    } else {
                                        for(iTmp = 0; iTmp < 6; ++iTmp) {
                                            bRecv[2 + iTmp] = (Byte)byteDeqeue.pollFirst();
                                        }

                                        crc_result = Utility.calcCrc16(bRecv, 0, 6);
                                        byte[] tmp_bytes = Utility.getByteArray(crc_result);
                                        if (tmp_bytes[2] == bRecv[6] && tmp_bytes[3] == bRecv[7]) {
                                            bLen = 8;
                                            bCircle = false;
                                        } else {
                                            for(i = 7; i > 0; --i) {
                                                byteDeqeue.addFirst(bRecv[i]);
                                            }
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
                } catch (Exception var20) {
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

    public void initParamVal() {
        this.setParam("DevState", 0);
        this.setParam("DevMode", 4);
        this.setParam("DevAlarm", 0);
        this.setParam("DevFault", 0);
        this.setParam("DevLog", 0);
    }
    @SuppressWarnings("FallThrough")
    public void initDevState(int devCmd) {
        switch(devCmd) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                this.setParam("DevState", devCmd);
                break;
            default:
                this.setParam("DevState", 0);
        }

    }
    @SuppressWarnings("FallThrough")
    public void setParamTestVal(double testVal, String testFlag, Timestamp testTime) {
        switch(this.mCmdType) {
            case 1:
                this.setParam("MeaTestVal", testVal);
                this.setParam("MeaTestFlag", testFlag);
                this.setParam("MeaTestTime", testTime);
                break;
            case 2:
                this.setParam("StdTestVal", testVal);
                this.setParam("StdTestFlag", testFlag);
                this.setParam("StdTestTime", testTime);
                break;
            case 3:
                this.setParam("ZeroTestVal", testVal);
                this.setParam("ZeroTestFlag", testFlag);
                this.setParam("ZeroTestTime", testTime);
                break;
            case 4:
                this.setParam("SpanTestVal", testVal);
                this.setParam("SpanTestFlag", testFlag);
                this.setParam("SpanTestTime", testTime);
                break;
            case 5:
                this.setParam("BlnkTestVal", testVal);
                this.setParam("BlnkTestFlag", testFlag);
                this.setParam("BlnkTestTime", testTime);
                break;
            case 6:
                this.setParam("ParTestVal", testVal);
                this.setParam("ParTestFlag", testFlag);
                this.setParam("ParTestTime", testTime);
                break;
            case 7:
                this.setParam("RcvrTestVal", testVal);
                this.setParam("RcvrTestFlag", testFlag);
                this.setParam("RcvrTestTime", testTime);
        }

    }

    public void setParamTestValAll(double testVal, String testFlag, Timestamp testTime) {
        this.setParam("MeaTestVal", testVal);
        this.setParam("MeaTestFlag", testFlag);
        this.setParam("MeaTestTime", testTime);
        this.setParam("StdTestVal", testVal);
        this.setParam("StdTestFlag", testFlag);
        this.setParam("StdTestTime", testTime);
        this.setParam("ZeroTestVal", testVal);
        this.setParam("ZeroTestFlag", testFlag);
        this.setParam("ZeroTestTime", testTime);
        this.setParam("SpanTestVal", testVal);
        this.setParam("SpanTestFlag", testFlag);
        this.setParam("SpanTestTime", testTime);
        this.setParam("BlnkTestVal", testVal);
        this.setParam("BlnkTestFlag", testFlag);
        this.setParam("BlnkTestTime", testTime);
        this.setParam("ParTestVal", testVal);
        this.setParam("ParTestFlag", testFlag);
        this.setParam("ParTestTime", testTime);
        this.setParam("RcvrTestVal", testVal);
        this.setParam("RcvrTestFlag", testFlag);
        this.setParam("RcvrTestTime", testTime);
    }

    public int getCmdResult(String protocolName, byte[] bCmd, byte[] bRecv) {
        int cmdResult = 1;
        if (bRecv != null) {
            if (bRecv.length == 8) {
                for(int i = 0; i < 6; ++i) {
                    if (bRecv[i] != bCmd[i]) {
                        cmdResult = 2;
                        break;
                    }
                }
            } else {
                cmdResult = 5;
            }
        } else {
            cmdResult = 4;
        }

        this.saveCmdResult(protocolName, cmdResult);
        return cmdResult;
    }

    public int getCmdResultTcp(String protocolName, byte[] bCmd, byte[] bRecv) {
        int cmdResult = 1;
        if (bRecv != null) {
            if (bRecv.length == 12) {
                for(int i = 0; i < bRecv.length; ++i) {
                    if (bRecv[i] != bCmd[i]) {
                        cmdResult = 2;
                        break;
                    }
                }
            } else {
                cmdResult = 5;
            }
        } else {
            cmdResult = 4;
        }

        this.saveCmdResult(protocolName, cmdResult);
        return cmdResult;
    }
    @SuppressWarnings("FallThrough")
    public void saveCmdResult(String protocolName, int cmdResult) {
        switch(cmdResult) {
            case 1:
                Utility.logInfo(protocolName + " Dev: " + this.mCfgDev.getName() + Utility.getTaskName(this.mCmdType) + "成功");
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 100:
                Utility.logInfo(protocolName + " Dev: " + this.mCfgDev.getName() + Utility.getTaskName(this.mCmdType) + "失败");
        }

    }

    public List<String> getWarnList() {
        List<String> warnList = new ArrayList();
        Iterator var2 = this.warnMap.entrySet().iterator();

        while(var2.hasNext()) {
            Entry<Integer, String> entry = (Entry)var2.next();
            int key = (Integer)entry.getKey();
            String value = (String)entry.getValue();
            warnList.add(value);
        }

        return warnList;
    }

    public void refreshParam() {
        if (this.mRegList != null) {
            for(int i = 0; i < this.mRegList.size(); ++i) {
                CfgDevReg cfgDevReg = (CfgDevReg)this.mRegList.get(i);
                if (cfgDevReg.getParamName() != null) {
                    this.paramMap.put(cfgDevReg.getParamName(), this.getParamVal(cfgDevReg.getParamName()));
                }
            }
        }

    }

    public Map<String, String> getParamMap() {
        return this.paramMap;
    }

    public Queue<String> getWarnQueue() {
        return this.warnQueue;
    }

    public String getWarnInfo() {
        return (String)this.warnQueue.poll();
    }

    public void addWarn(int key, String value) {
        boolean bCheck = false;
        Iterator var4 = this.warnMap.entrySet().iterator();

        while(var4.hasNext()) {
            Entry<Integer, String> entry = (Entry)var4.next();
            if ((Integer)entry.getKey() == key) {
                bCheck = true;
                break;
            }
        }

        if (!bCheck) {
            this.warnMap.put(key, value);
            this.warnQueue.offer(value);
        }

    }

    public int getWarnState() {
        return this.warnState;
    }

    public void setWarnState(int warnState) {
        this.warnState = warnState;
    }

    private class ReadThread extends Thread {
        private ReadThread() {
        }

        public void run() {
            super.run();

            try {
                Thread.sleep(3000L);
            } catch (Exception var2) {
            }

            while(!this.isInterrupted() && !MonitorService.restart) {
                try {
                    DevService.this.fixedThreadPool.execute(new Runnable() {
                        public void run() {
                            DevService.this.doQuery();
                        }
                    });

                    for(int i = 0; i < 20; ++i) {
                        if (MonitorService.restart) {
                            return;
                        }

                        Thread.sleep(1000L);
                    }
                } catch (Exception var3) {
                    break;
                }
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
                    if (DevService.this.mInterComm != null) {
                        DevService.this.fixedThreadPool.execute(new Runnable() {
                            public void run() {
                                DevService.this.doQuery();
                            }
                        });
                    }

                    for(int i = 0; i < 10; ++i) {
                        if (MonitorService.restart) {
                            return;
                        }

                        Thread.sleep(1000L);
                    }
                } catch (Exception var3) {
                    break;
                }
            }

        }
    }
}
