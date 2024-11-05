//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.upload;

import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.rec.RecCheckFive;
import com.grean.station.domain.DO.rec.RecCheckPar;
import com.grean.station.domain.DO.rec.RecCheckRcvr;
import com.grean.station.domain.DO.rec.RecCheckStd;
import com.grean.station.domain.DO.rec.RecCheckZero;
import com.grean.station.domain.DO.rec.RecDataFactor;
import com.grean.station.domain.DO.rec.RecDataTime;
import com.grean.station.domain.DO.rec.RecLogDev;
import com.grean.station.domain.DO.rec.RecLogSys;
import com.grean.station.service.dev.DevExeListener;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.TimeHelper;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Upload212_CQ extends UploadInter {
    public Upload212_CQ() {
    }

    public byte[] getPowerAlarmPkg(String strQN, String strFlag, boolean powerAlarm) {
        return null;
    }

    public byte[] getBootTimePkg(String strQN, String strFlag) {
        return null;
    }

    public byte[] getHeartBeatPkg(String strQN, String strFlag) {
        String strSend = "QN=" + strQN + ";";
        strSend = strSend + "ST=21;CN=9015;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=1;CP=&&&&";
        return this.getSendBytes(strSend);
    }

    public byte[] getRsrvSamplePkg(String strQN, String strFlag, String strDataTime, String strVaseNo) {
        return null;
    }

    public byte[] getRealTimePkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return null;
    }

    public byte[] getMinDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return null;
    }

    public byte[] getHourDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecDataFactor> recDataList = this.mMonitor.getRecMapper().getRecDataHourListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=21;CN=2061;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=1;CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                CfgFactor cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if ((factorID <= 0 || factorID == cfgFactor.getId()) && cfgFactor.isUsed() && (cfgFactor.isParmType() || cfgFactor.isFiveType() || cfgFactor.isSysType())) {
                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecDataFactor)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Avg=" + ((RecDataFactor)recDataList.get(j)).getDataValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=N";
                            break;
                        }
                    }
                }
            }

            strSend = strSend + "&&";
            return this.getSendBytes(strSend);
        }
    }

    public byte[] getDayDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return new byte[0];
    }

    public byte[] getFiveDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecDataFactor> recDataList = this.mMonitor.getRecMapper().getRecDataFiveListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=21;CN=2061;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=1;CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                CfgFactor cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if ((factorID <= 0 || factorID == cfgFactor.getId()) && cfgFactor.isUsed() && cfgFactor.isFiveType()) {
                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecDataFactor)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Avg=" + ((RecDataFactor)recDataList.get(j)).getDataValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=N";
                            break;
                        }
                    }
                }
            }

            strSend = strSend + "&&";
            return this.getSendBytes(strSend);
        }
    }

    public byte[] getFiveStdCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecCheckFive> recDataList = this.mMonitor.getRecMapper().getRecCheckFiveDataListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=21;CN=2051;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=1;CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                CfgFactor cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if ((factorID <= 0 || factorID == cfgFactor.getId()) && cfgFactor.isUsed() && cfgFactor.isFiveType()) {
                    Iterator var9 = recDataList.iterator();

                    while(var9.hasNext()) {
                        RecCheckFive recCheckFive = (RecCheckFive)var9.next();
                        if (recCheckFive.getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Avg=" + recCheckFive.getCurValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=sc";
                            break;
                        }
                    }
                }
            }

            strSend = strSend + "&&";
            return this.getSendBytes(strSend);
        }
    }

    public byte[] getStdCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecCheckStd> recDataList = this.mMonitor.getRecMapper().getRecCheckStdDataListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=21;CN=2061;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=0;CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                CfgFactor cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if ((factorID <= 0 || factorID == cfgFactor.getId()) && cfgFactor.isUsed() && cfgFactor.isParmType()) {
                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecCheckStd)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Avg=" + ((RecCheckStd)recDataList.get(j)).getTestValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=sc";
                            break;
                        }
                    }
                }
            }

            strSend = strSend + "&&";
            return this.getSendBytes(strSend);
        }
    }

    public byte[] getZeroCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecCheckZero> recDataList = this.mMonitor.getRecMapper().getRecCheckZeroDataListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=21;CN=2061;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=0;CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                CfgFactor cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if ((factorID <= 0 || factorID == cfgFactor.getId()) && cfgFactor.isUsed() && cfgFactor.isParmType()) {
                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecCheckZero)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Avg=" + ((RecCheckZero)recDataList.get(j)).getCurValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=cz";
                            break;
                        }
                    }
                }
            }

            strSend = strSend + "&&";
            return this.getSendBytes(strSend);
        }
    }

    public byte[] getSpanCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecCheckZero> recDataList = this.mMonitor.getRecMapper().getRecCheckSpanDataListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=21;CN=2061;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=0;CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                CfgFactor cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if ((factorID <= 0 || factorID == cfgFactor.getId()) && cfgFactor.isUsed() && cfgFactor.isParmType()) {
                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecCheckZero)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Avg=" + ((RecCheckZero)recDataList.get(j)).getCurValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=cs";
                            break;
                        }
                    }
                }
            }

            strSend = strSend + "&&";
            return this.getSendBytes(strSend);
        }
    }

    public byte[] getRcvrCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecCheckRcvr> recDataList = this.mMonitor.getRecMapper().getRecCheckRcvrDataListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2063;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=0;CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                CfgFactor cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if ((factorID <= 0 || factorID == cfgFactor.getId()) && cfgFactor.isUsed() && cfgFactor.isParmType()) {
                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecCheckRcvr)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Check=" + ((RecCheckRcvr)recDataList.get(j)).getAfterValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-WaterTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(((RecCheckRcvr)recDataList.get(j)).getBeforeTime()) + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Water=" + ((RecCheckRcvr)recDataList.get(j)).getBeforeValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=" + ((RecCheckRcvr)recDataList.get(j)).getDataFlag();
                            break;
                        }
                    }
                }
            }

            strSend = strSend + "&&";
            return this.getSendBytes(strSend);
        }
    }

    public byte[] getParCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecCheckPar> recDataList = this.mMonitor.getRecMapper().getRecCheckParDataListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2064;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=0;CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                CfgFactor cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if ((factorID <= 0 || factorID == cfgFactor.getId()) && cfgFactor.isUsed() && cfgFactor.isParmType()) {
                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecCheckPar)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Check=" + ((RecCheckPar)recDataList.get(j)).getValue2nd() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-WaterTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(((RecCheckPar)recDataList.get(j)).getRecTime()) + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Water=" + ((RecCheckPar)recDataList.get(j)).getValue1st() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=" + ((RecCheckPar)recDataList.get(j)).getDataFlag();
                            break;
                        }
                    }
                }
            }

            strSend = strSend + "&&";
            return this.getSendBytes(strSend);
        }
    }

    public byte[] getLogSysPkg(String strQN, String strFlag, RecDataTime recDataTime) {
        List<RecLogSys> recLogSysList = this.mMonitor.getRecMapper().getRecLogSysListByTime(recDataTime);
        if (recLogSysList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=3020;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=0;CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < recLogSysList.size(); ++i) {
                strSend = strSend + ";PolId=w00000,i21001-Info=//" + ((RecLogSys)recLogSysList.get(i)).getLogDesc() + "//";
            }

            strSend = strSend + "&&";
            return this.getSendBytes(strSend);
        }
    }

    public byte[] getLogDevPkg(String strQN, String strFlag, RecDataTime recDataTime, String PolID) {
        List<RecLogDev> recLogDevList = this.mMonitor.getRecMapper().getRecLogDevListByTime(recDataTime);
        if (recLogDevList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=3020;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=0;CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());
            int i;
            CfgFactor cfgFactor;
            int j;
            if (PolID == null) {
                for(i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                    cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                    if (cfgFactor.isUsed() && (cfgFactor.isFiveType() || cfgFactor.isParmType())) {
                        for(j = 0; j < recLogDevList.size(); ++j) {
                            if (((RecLogDev)recLogDevList.get(j)).getFactorID() == cfgFactor.getId()) {
                                strSend = strSend + ";PolId=" + cfgFactor.getCodeGJ() + ",i11001-Info=//" + ((RecLogDev)recLogDevList.get(j)).getLogDesc() + "//";
                                break;
                            }
                        }
                    }
                }
            } else {
                for(i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                    cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                    if (cfgFactor.isUsed() && cfgFactor.getCodeGJ().equals(PolID)) {
                        for(j = 0; j < recLogDevList.size(); ++j) {
                            if (((RecLogDev)recLogDevList.get(j)).getFactorID() == cfgFactor.getId()) {
                                strSend = strSend + ";PolId=" + cfgFactor.getCodeGJ() + ",i11001-Info=//" + ((RecLogDev)recLogDevList.get(j)).getLogDesc() + "//";
                                break;
                            }
                        }
                    }
                }
            }

            strSend = strSend + "&&";
            return this.getSendBytes(strSend);
        }
    }
    @SuppressWarnings("FallThrough")
    public byte[] getStateSysPkg(String strQN, String strFlag, RecDataTime recDataTime, String InfoID) {
        String strSend = "QN=" + strQN + ";";
        strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=3020;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=0;CP=&&";
        strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());
        strSend = strSend + ";PolId=w00000";
        if (InfoID == null) {
            strSend = strSend + ",i22001-Info=0";
        } else {
            byte var7 = -1;
            switch(InfoID.hashCode()) {
                case -1241193176:
                    if (InfoID.equals("i22001")) {
                        var7 = 0;
                    }
                default:
                    switch(var7) {
                        case 0:
                            strSend = strSend + ",i22001-Info=0";
                    }
            }
        }

        strSend = strSend + "&&";
        return this.getSendBytes(strSend);
    }

    public byte[] getStateDevPkg(String strQN, String strFlag, RecDataTime recDataTime, String PolID, String InfoID) {
        String strSend = "QN=" + strQN + ";";
        strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=3020;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=0;CP=&&";
        strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());
        int i;
        CfgFactor cfgFactor;
        if (PolID == null) {
            for(i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if (cfgFactor.isUsed() && (cfgFactor.isFiveType() || cfgFactor.isParmType())) {
                    strSend = strSend + this.getDevState(cfgFactor, InfoID);
                }
            }
        } else {
            for(i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if (cfgFactor.isUsed() && cfgFactor.getCodeGJ().equals(PolID)) {
                    strSend = strSend + this.getDevState(cfgFactor, InfoID);
                    break;
                }
            }
        }

        strSend = strSend + "&&";
        return this.getSendBytes(strSend);
    }

    public byte[] getParamDevPkg(String strQN, String strFlag, RecDataTime recDataTime, String PolID, String InfoID) {
        String strSend = "QN=" + strQN + ";";
        strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=3020;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=0;CP=&&";
        strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());
        int i;
        CfgFactor cfgFactor;
        if (PolID == null) {
            for(i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if (cfgFactor.isUsed() && (cfgFactor.isFiveType() || cfgFactor.isParmType())) {
                    strSend = strSend + this.getDevParam(cfgFactor, InfoID);
                }
            }
        } else {
            for(i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if (cfgFactor.isUsed() && cfgFactor.getCodeGJ().equals(PolID)) {
                    strSend = strSend + this.getDevParam(cfgFactor, InfoID);
                    break;
                }
            }
        }

        strSend = strSend + "&&";
        return this.getSendBytes(strSend);
    }
    @SuppressWarnings("FallThrough")
    public List<byte[]> getResponse(byte[] bRecv) {
        int qnRtn;
        int exeRtn = 1;
        String strQN = this.getCmdValue(bRecv, "QN");
        String strCN = this.getCmdValue(bRecv, "CN");
        if (strQN != null && strCN != null) {
            List<byte[]> resultList = new ArrayList();
            qnRtn = this.checkRecv(bRecv);
            if (qnRtn == 0) {
                return null;
            } else {
                resultList.add(this.getBeginResponse(strQN, qnRtn + ""));
                if (qnRtn > 1) {
                    return null;
                } else {
                    String strBeginTime = this.getCmdValue(bRecv, "BeginTime");
                    String strEndTime = this.getCmdValue(bRecv, "EndTime");
                    byte var17 = -1;
                    switch(strCN.hashCode()) {
                        case 1507456:
                            if (strCN.equals("1012")) {
                                var17 = 7;
                            }
                            break;
                        case 1537246:
                            if (strCN.equals("2011")) {
                                var17 = 0;
                            }
                            break;
                        case 1537401:
                            if (strCN.equals("2061")) {
                                var17 = 1;
                            }
                            break;
                        case 1537402:
                            if (strCN.equals("2062")) {
                                var17 = 2;
                            }
                            break;
                        case 1537403:
                            if (strCN.equals("2063")) {
                                var17 = 3;
                            }
                            break;
                        case 1537404:
                            if (strCN.equals("2064")) {
                                var17 = 4;
                            }
                            break;
                        case 1537405:
                            if (strCN.equals("2065")) {
                                var17 = 5;
                            }
                            break;
                        case 1537406:
                            if (strCN.equals("2066")) {
                                var17 = 6;
                            }
                            break;
                        case 1567067:
                            if (strCN.equals("3020")) {
                                var17 = 8;
                            }
                            break;
                        case 1567253:
                            if (strCN.equals("3080")) {
                                var17 = 9;
                            }
                            break;
                        case 1567254:
                            if (strCN.equals("3081")) {
                                var17 = 10;
                            }
                            break;
                        case 1567255:
                            if (strCN.equals("3082")) {
                                var17 = 11;
                            }
                            break;
                        case 1567256:
                            if (strCN.equals("3083")) {
                                var17 = 12;
                            }
                            break;
                        case 1567257:
                            if (strCN.equals("3084")) {
                                var17 = 13;
                            }
                            break;
                        case 1567258:
                            if (strCN.equals("3085")) {
                                var17 = 14;
                            }
                            break;
                        case 1567259:
                            if (strCN.equals("3086")) {
                                var17 = 15;
                            }
                    }

                    byte[] bTmp;
                    String strTmp;
                    String strPolID;
                    List recTimeList;
                    Iterator var27;
                    RecDataTime recDataTime;
                    label339:
                    switch(var17) {
                        case 0:
                            if (strBeginTime != null && strEndTime != null) {
                                recTimeList = this.mMonitor.getRecMapper().getRecTimeMinListByTime(strBeginTime, strEndTime);
                                if (recTimeList != null) {
                                    var27 = recTimeList.iterator();

                                    while(var27.hasNext()) {
                                        recDataTime = (RecDataTime)var27.next();
                                        bTmp = this.getRealTimePkg(strQN, "8", recDataTime, 0);
                                        if (bTmp != null) {
                                            resultList.add(bTmp);
                                        }
                                    }
                                }
                            }
                            break;
                        case 1:
                            if (strBeginTime != null && strEndTime != null) {
                                recTimeList = this.mMonitor.getRecMapper().getRecTimeHourListByTime(strBeginTime, strEndTime);
                                if (recTimeList != null) {
                                    var27 = recTimeList.iterator();

                                    while(var27.hasNext()) {
                                        recDataTime = (RecDataTime)var27.next();
                                        bTmp = this.getHourDataPkg(strQN, "8", recDataTime, 0);
                                        if (bTmp != null) {
                                            resultList.add(bTmp);
                                        }
                                    }
                                }
                            }
                            break;
                        case 2:
                            if (strBeginTime != null && strEndTime != null) {
                                recTimeList = this.mMonitor.getRecMapper().getRecCheckStdTimeListByTime(strBeginTime, strEndTime);
                                if (recTimeList != null) {
                                    var27 = recTimeList.iterator();

                                    while(var27.hasNext()) {
                                        recDataTime = (RecDataTime)var27.next();
                                        bTmp = this.getStdCheckPkg(strQN, "8", recDataTime, 0);
                                        if (bTmp != null) {
                                            resultList.add(bTmp);
                                        }
                                    }
                                }
                            }
                            break;
                        case 3:
                            if (strBeginTime != null && strEndTime != null) {
                                recTimeList = this.mMonitor.getRecMapper().getRecCheckRcvrTimeListByTime(strBeginTime, strEndTime);
                                if (recTimeList != null) {
                                    var27 = recTimeList.iterator();

                                    while(var27.hasNext()) {
                                        recDataTime = (RecDataTime)var27.next();
                                        bTmp = this.getRcvrCheckPkg(strQN, "8", recDataTime, 0);
                                        if (bTmp != null) {
                                            resultList.add(bTmp);
                                        }
                                    }
                                }
                            }
                            break;
                        case 4:
                            if (strBeginTime != null && strEndTime != null) {
                                recTimeList = this.mMonitor.getRecMapper().getRecCheckParTimeListByTime(strBeginTime, strEndTime);
                                if (recTimeList != null) {
                                    var27 = recTimeList.iterator();

                                    while(var27.hasNext()) {
                                        recDataTime = (RecDataTime)var27.next();
                                        bTmp = this.getParCheckPkg(strQN, "8", recDataTime, 0);
                                        if (bTmp != null) {
                                            resultList.add(bTmp);
                                        }
                                    }
                                }
                            }
                            break;
                        case 5:
                            if (strBeginTime != null && strEndTime != null) {
                                recTimeList = this.mMonitor.getRecMapper().getRecCheckZeroTimeListByTime(strBeginTime, strEndTime);
                                if (recTimeList != null) {
                                    var27 = recTimeList.iterator();

                                    while(var27.hasNext()) {
                                        recDataTime = (RecDataTime)var27.next();
                                        bTmp = this.getZeroCheckPkg(strQN, "8", recDataTime, 0);
                                        if (bTmp != null) {
                                            resultList.add(bTmp);
                                        }
                                    }
                                }
                            }
                            break;
                        case 6:
                            if (strBeginTime != null && strEndTime != null) {
                                recTimeList = this.mMonitor.getRecMapper().getRecCheckSpanTimeListByTime(strBeginTime, strEndTime);
                                if (recTimeList != null) {
                                    var27 = recTimeList.iterator();

                                    while(var27.hasNext()) {
                                        recDataTime = (RecDataTime)var27.next();
                                        bTmp = this.getSpanCheckPkg(strQN, "8", recDataTime, 0);
                                        if (bTmp != null) {
                                            resultList.add(bTmp);
                                        }
                                    }
                                }
                            }
                            break;
                        case 7:
                            resultList.clear();
                            resultList.add(this.getBeginResponse2(strQN, "1"));
                            strTmp = this.getCmdValue(bRecv, "SystemTime");
                            Timestamp timestamp = TimeHelper.getDataTime_yyyyMMddHHmmss(strTmp);
                            String strDate = TimeHelper.getSQLDay(timestamp);
                            String strTime = TimeHelper.getSQLTime(timestamp);
                            TimeHelper.setDatetime(strDate, strTime);
                            resultList.add(this.getEndResponse2(strQN, "1"));
                            return resultList;
                        case 8:
                            List<String> strPolIDList = this.getCmdValueList(bRecv, "PolId");
                            List<String> strInfoIDList = this.getCmdValueList(bRecv, "InfoId");
                            if (strPolIDList.size() > 0 && strInfoIDList.size() > 0 && strPolIDList.size() == strInfoIDList.size()) {
                                int i = 0;

                                while(true) {
                                    if (i >= strPolIDList.size()) {
                                        break label339;
                                    }

                                    if (((String)strPolIDList.get(i)).equals("w00000")) {
                                        String var29 = ((String)strInfoIDList.get(i)).substring(0, 3);
                                        byte var30 = -1;
                                        switch(var29.hashCode()) {
                                            case 102504:
                                                if (var29.equals("i21")) {
                                                    var30 = 0;
                                                }
                                                break;
                                            case 102505:
                                                if (var29.equals("i22")) {
                                                    var30 = 1;
                                                }
                                        }

                                        label254:
                                        switch(var30) {
                                            case 0:
                                                if (strBeginTime == null || strEndTime == null) {
                                                    break;
                                                }

                                                recTimeList = this.mMonitor.getRecMapper().getRecLogSysTimeListByTime(strBeginTime, strEndTime);
                                                if (recTimeList == null) {
                                                    break;
                                                }

                                                int j = 0;

                                                while(true) {
                                                    if (j >= recTimeList.size()) {
                                                        break label254;
                                                    }

                                                    bTmp = this.getLogSysPkg(strQN, "8", (RecDataTime)recTimeList.get(j));
                                                    if (bTmp != null) {
                                                        resultList.add(bTmp);
                                                    }

                                                    ++j;
                                                }
                                            case 1:
                                                recDataTime = new RecDataTime();
                                                recDataTime.setRecTime(TimeHelper.getCurrentTimestamp());
                                                bTmp = this.getStateSysPkg(strQN, "8", recDataTime, (String)strInfoIDList.get(i));
                                                if (bTmp != null) {
                                                    resultList.add(bTmp);
                                                }
                                        }
                                    } else {
                                        String var23 = ((String)strInfoIDList.get(i)).substring(0, 3);
                                        byte var24 = -1;
                                        switch(var23.hashCode()) {
                                            case 102473:
                                                if (var23.equals("i11")) {
                                                    var24 = 0;
                                                }
                                                break;
                                            case 102474:
                                                if (var23.equals("i12")) {
                                                    var24 = 1;
                                                }
                                                break;
                                            case 102475:
                                                if (var23.equals("i13")) {
                                                    var24 = 2;
                                                }
                                        }

                                        label268:
                                        switch(var24) {
                                            case 0:
                                                if (strBeginTime == null || strEndTime == null) {
                                                    break;
                                                }

                                                recTimeList = this.mMonitor.getRecMapper().getRecLogDevTimeListByTime(strBeginTime, strEndTime);
                                                if (recTimeList == null) {
                                                    break;
                                                }

                                                int j = 0;

                                                while(true) {
                                                    if (j >= recTimeList.size()) {
                                                        break label268;
                                                    }

                                                    bTmp = this.getLogDevPkg(strQN, "8", (RecDataTime)recTimeList.get(j), (String)strPolIDList.get(i));
                                                    if (bTmp != null) {
                                                        resultList.add(bTmp);
                                                    }

                                                    ++j;
                                                }
                                            case 1:
                                                recDataTime = new RecDataTime();
                                                recDataTime.setRecTime(TimeHelper.getCurrentTimestamp());
                                                bTmp = this.getStateDevPkg(strQN, "8", recDataTime, (String)strPolIDList.get(i), (String)strInfoIDList.get(i));
                                                if (bTmp != null) {
                                                    resultList.add(bTmp);
                                                }
                                                break;
                                            case 2:
                                                recDataTime = new RecDataTime();
                                                recDataTime.setRecTime(TimeHelper.getCurrentTimestamp());
                                                bTmp = this.getParamDevPkg(strQN, "8", recDataTime, (String)strPolIDList.get(i), (String)strInfoIDList.get(i));
                                                if (bTmp != null) {
                                                    resultList.add(bTmp);
                                                }
                                        }
                                    }

                                    ++i;
                                }
                            } else {
                                exeRtn = 3;
                                break;
                            }
                        case 9:
                            strPolID = this.getCmdValue(bRecv, "PolId");
                            if (this.setDevCmd(2, strQN, strPolID, (String)null)) {
                                return resultList;
                            }

                            exeRtn = 2;
                            break;
                        case 10:
                            strPolID = this.getCmdValue(bRecv, "PolId");
                            strTmp = this.getCmdValue(bRecv, "Volume");
                            if (this.setDevCmd(7, strQN, strPolID, strTmp)) {
                                return resultList;
                            }

                            exeRtn = 2;
                            break;
                        case 11:
                            strPolID = this.getCmdValue(bRecv, "PolId");
                            if (this.setDevCmd(6, strQN, strPolID, (String)null)) {
                                return resultList;
                            }

                            exeRtn = 2;
                            break;
                        case 12:
                            strPolID = this.getCmdValue(bRecv, "PolId");
                            if (this.setDevCmd(3, strQN, strPolID, (String)null)) {
                                return resultList;
                            }

                            exeRtn = 2;
                            break;
                        case 13:
                            strPolID = this.getCmdValue(bRecv, "PolId");
                            if (this.setDevCmd(4, strQN, strPolID, (String)null)) {
                                return resultList;
                            }

                            exeRtn = 2;
                            break;
                        case 14:
                            strPolID = this.getCmdValue(bRecv, "PolId");
                            if (this.setDevCmd(8, strQN, strPolID, (String)null)) {
                                return resultList;
                            }

                            exeRtn = 2;
                            break;
                        case 15:
                            strPolID = this.getCmdValue(bRecv, "PolId");
                            if (this.setDevCmd(9, strQN, strPolID, (String)null)) {
                                return resultList;
                            }

                            exeRtn = 2;
                            break;
                        default:
                            return null;
                    }

                    resultList.add(this.getEndResponse(strQN, exeRtn + ""));
                    return resultList;
                }
            }
        } else {
            return null;
        }
    }

    private byte[] getBeginResponse(String strQN, String strRtn) {
        String strSend = "QN=" + strQN + ";";
        strSend = strSend + "ST=91;CN=9011;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=0;CP=&&QnRtn=" + strRtn + "&&";
        return this.getSendBytes(strSend);
    }

    private byte[] getEndResponse(String strQN, String strRtn) {
        String strSend = "QN=" + strQN + ";";
        strSend = strSend + "ST=91;CN=9012;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=0;CP=&&ExeRtn=" + strRtn + "&&";
        return this.getSendBytes(strSend);
    }

    private byte[] getBeginResponse2(String strQN, String strRtn) {
        String strSend = "ST=91;CN=9011;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=0;CP=&&QnRtn=" + strRtn + "&&";
        return this.getSendBytes(strSend);
    }

    private byte[] getEndResponse2(String strQN, String strRtn) {
        String strSend = "ST=91;CN=9012;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=0;CP=&&ExeRtn=" + strRtn + "&&";
        return this.getSendBytes(strSend);
    }
    @SuppressWarnings("FallThrough")
    private void sendExeResult(String cmdQN, int cmdID, int exeResult) {
        String strTmp = "##0000QN=" + cmdQN + ";";
        if (this.mInterComm.getRespond(strTmp.getBytes()) != null) {
            switch(cmdID) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 13:
                case 3015:
                    byte[] bSend = this.getEndResponse(cmdQN, exeResult + "");
                    if (bSend == null) {
                        return;
                    }

                    this.mInterComm.Send(bSend);
            }
        }

    }
    @SuppressWarnings("FallThrough")
    private boolean setDevCmd(int cmdType, String strQN, String factorCode, String strParam) {
        if (factorCode == null) {
            return false;
        } else {
            Iterator var5 = this.mMonitor.cfgFactorList.iterator();

            while(var5.hasNext()) {
                CfgFactor cfgFactor = (CfgFactor)var5.next();
                if (cfgFactor.getCodeGJ().equals(factorCode)) {
                    Iterator var7 = this.mMonitor.devServiceList.iterator();

                    DevService devService;
                    do {
                        if (!var7.hasNext()) {
                            return false;
                        }

                        devService = (DevService)var7.next();
                    } while(cfgFactor.getDevID() != devService.getDevID());

                    switch(cmdType) {
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                            if (!this.mMonitor.doDevCmd(cfgFactor.getDevID(), cmdType, "N")) {
                                return false;
                            }

                            this.sendExeResult(strQN, cmdType, 1);
                            break;
                        case 6:
                        case 7:
                            this.mMonitor.initScriptFactorList(cfgFactor.getId());
                            if (!this.mMonitor.startTest(cmdType, "N")) {
                                return false;
                            }

                            this.sendExeResult(strQN, cmdType, 1);
                            break;
                        case 8:
                            devService.doBlnkCal(strQN, strParam);
                            break;
                        case 9:
                            devService.doStdCal(strQN, strParam);
                        case 10:
                        case 11:
                        case 12:
                        default:
                            break;
                        case 13:
                            if (strParam == null) {
                                return false;
                            }

                            devService.doSetTime(strQN, strParam);
                    }

                    this.setDevExeListener(devService);
                    return true;
                }
            }

            return false;
        }
    }

    private void setDevExeListener(final DevService devService) {
        if (devService.getDevExeListener() == null) {
            devService.setDevExeListener(new DevExeListener() {
                public void OnExeEvent(String cmdQN, int cmdID, int exeResult) {
                    if (cmdQN.length() > 0) {
                        Upload212_CQ.this.sendExeResult(cmdQN, cmdID, exeResult);
                    }

                    if (exeResult == 1) {
                        Upload212_CQ.this.mMonitor.saveRecLogSys("系统日志", "启动" + devService.getDevName() + "成功");
                    } else {
                        Upload212_CQ.this.mMonitor.saveRecLogSys("系统日志", "启动" + devService.getDevName() + "失败");
                    }

                }
            });
        }

    }

    private byte[] getSendBytes(String sendStr) {
        try {
            byte[] bufTmp = sendStr.getBytes();
            int crc16 = Utility.crc16ANSI(bufTmp);
            String strTmp = "##" + String.format("%04d", bufTmp.length) + sendStr + String.format("%04x", crc16).toUpperCase() + "\r\n";
            return strTmp.getBytes();
        } catch (Exception var5) {
            return null;
        }
    }

    private String getCmdValue(byte[] bRecv, String cmdName) {
        String strRecv = new String(bRecv);
        int index = strRecv.indexOf(cmdName);
        if (index == -1) {
            return null;
        } else {
            int iLeft = strRecv.indexOf("=", index);
            if (iLeft == -1) {
                return null;
            } else {
                int iRight = strRecv.length();
                int iRight1 = strRecv.indexOf(",", index);
                if (iRight1 != -1 && iRight1 < iRight) {
                    iRight = iRight1;
                }

                int iRight2 = strRecv.indexOf(";", index);
                if (iRight2 != -1 && iRight2 < iRight) {
                    iRight = iRight2;
                }

                int iRight3 = strRecv.indexOf("&", index);
                if (iRight3 != -1 && iRight3 < iRight) {
                    iRight = iRight3;
                }

                return iRight == strRecv.length() ? null : strRecv.substring(iLeft + 1, iRight);
            }
        }
    }

    private List<String> getCmdValueList(byte[] bRecv, String cmdName) {
        String strRecv = new String(bRecv);
        int index = 0;

        ArrayList cmdValueList;
        for(cmdValueList = new ArrayList(); (index = strRecv.indexOf(cmdName, index)) != -1; index += cmdName.length()) {
            int iLeft = strRecv.indexOf("=", index);
            if (iLeft == -1) {
                break;
            }

            int iRight = strRecv.length();
            int iRight1 = strRecv.indexOf(",", index);
            if (iRight1 != -1 && iRight1 < iRight) {
                iRight = iRight1;
            }

            int iRight2 = strRecv.indexOf(";", index);
            if (iRight2 != -1 && iRight2 < iRight) {
                iRight = iRight2;
            }

            int iRight3 = strRecv.indexOf("&", index);
            if (iRight3 != -1 && iRight3 < iRight) {
                iRight = iRight3;
            }

            if (iRight == strRecv.length()) {
                break;
            }

            cmdValueList.add(strRecv.substring(iLeft + 1, iRight));
        }

        return cmdValueList;
    }

    private int checkRecv(byte[] bRecv) {
        String[] cnArray = new String[]{"1000", "1001", "1011", "1012", "1014", "1015", "1061", "1062", "1072", "2011", "2051", "2061", "2062", "2063", "2064", "2065", "2066", "3015", "3020", "3021", "3040", "3041", "3042", "3043", "3044", "3045", "3046", "3047", "3048", "3049", "3050", "3051", "3052", "3053", "3054", "3055", "3056", "3057", "3058", "3059", "3060", "3061", "3062", "3063", "3064", "3065", "3066", "3080", "3081", "3082", "3083", "3084", "3085", "3086"};
        if (bRecv.length > 26 && bRecv[0] == 35 && bRecv[1] == 35) {
            String strRecv = new String(bRecv);
            int iLen = Integer.parseInt(strRecv.substring(2, 6));
            if (iLen + 10 == bRecv.length) {
                byte[] bTmp = new byte[iLen];

                int crc16;
                for(crc16 = 0; crc16 < iLen; ++crc16) {
                    bTmp[crc16] = bRecv[6 + crc16];
                }

                crc16 = Utility.crc16ANSI(bTmp);
                String strTmp = String.format("%04x", crc16).toUpperCase();
                if (strRecv.substring(strRecv.length() - 4, strRecv.length()).equals(strTmp)) {
                    String strPW = this.getCmdValue(bRecv, "PW");
                    String strMN = this.getCmdValue(bRecv, "MN");
                    String strST = this.getCmdValue(bRecv, "ST");
                    String strFlag = this.getCmdValue(bRecv, "Flag");
                    String strQN = this.getCmdValue(bRecv, "QN");
                    String strCN = this.getCmdValue(bRecv, "CN");
                    if (strPW != null && strPW.equals(this.mUploadCfg.getPassword())) {
                        if (strMN != null && strMN.equals(this.mUploadCfg.getMn())) {
                            if (strST == null) {
                                return 5;
                            }

                            if (!strST.equals(this.mUploadCfg.getSt()) && !strST.equals("91")) {
                                return 5;
                            }

                            if (strFlag == null) {
                                return 6;
                            }

                            if (strQN != null && strQN.length() == 17) {
                                if (strCN == null) {
                                    return 8;
                                }

                                if (!strCN.equals("9013") && !strCN.equals("9014")) {
                                    boolean bCheck = false;

                                    for(int i = 0; i < cnArray.length; ++i) {
                                        if (strCN.equals(cnArray[i])) {
                                            bCheck = true;
                                            break;
                                        }
                                    }

                                    if (bCheck) {
                                        return 1;
                                    }

                                    return 8;
                                }

                                return 0;
                            }

                            return 7;
                        }

                        return 4;
                    }

                    return 3;
                }
            }
        }

        return 0;
    }
    @SuppressWarnings("FallThrough")
    private String getDevState(CfgFactor cfgFactor, String InfoID) {
        String strTmp = "";
        byte devState;
        int iTmp;
        int j;
        if (InfoID == null) {
            for(j = 0; j < this.mMonitor.devServiceList.size(); ++j) {
                if (cfgFactor.getDevID() == ((DevService)this.mMonitor.devServiceList.get(j)).getDevID()) {
                    strTmp = strTmp + ";PolId=" + cfgFactor.getCodeGJ();
                    iTmp = (Integer)((DevService)this.mMonitor.devServiceList.get(j)).getParam("DevState");
                    switch(iTmp) {
                        case 0:
                            devState = 2;
                            break;
                        case 1:
                            devState = 3;
                            break;
                        case 2:
                            devState = 11;
                            break;
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        default:
                            devState = 99;
                            break;
                        case 8:
                            devState = 7;
                            break;
                        case 9:
                            devState = 8;
                            break;
                        case 10:
                            devState = 5;
                    }

                    strTmp = strTmp + ",i12001-Info=" + devState;
                    break;
                }
            }
        } else {
            for(j = 0; j < this.mMonitor.devServiceList.size(); ++j) {
                if (cfgFactor.getDevID() == ((DevService)this.mMonitor.devServiceList.get(j)).getDevID()) {
                    strTmp = strTmp + ";PolId=" + cfgFactor.getCodeGJ();
                    byte var8 = -1;
                    switch(InfoID.hashCode()) {
                        case -1242116697:
                            if (InfoID.equals("i12001")) {
                                var8 = 0;
                            }
                        default:
                            switch(var8) {
                                case 0:
                                    iTmp = (Integer)((DevService)this.mMonitor.devServiceList.get(j)).getParam("DevState");
                                    switch(iTmp) {
                                        case 0:
                                            devState = 2;
                                            break;
                                        case 1:
                                            devState = 3;
                                            break;
                                        case 2:
                                            devState = 11;
                                            break;
                                        case 3:
                                        case 4:
                                        case 5:
                                        case 6:
                                        case 7:
                                        default:
                                            devState = 99;
                                            break;
                                        case 8:
                                            devState = 7;
                                            break;
                                        case 9:
                                            devState = 8;
                                            break;
                                        case 10:
                                            devState = 5;
                                    }

                                    strTmp = strTmp + ",i12001-Info=" + devState;
                                    return strTmp;
                                default:
                                    return strTmp;
                            }
                    }
                }
            }
        }

        return strTmp;
    }

    private String getDevParam(CfgFactor cfgFactor, String InfoID) {
        String strTmp = "";
        int j;
        if (InfoID == null) {
            for(j = 0; j < this.mMonitor.devServiceList.size(); ++j) {
                if (cfgFactor.getDevID() == ((DevService)this.mMonitor.devServiceList.get(j)).getDevID()) {
                    strTmp = strTmp + ";PolId=" + cfgFactor.getCodeGJ();
                    strTmp = strTmp + ",i13001-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("RangeTop");
                    strTmp = strTmp + ",i13002-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("MeaAccuracy");
                    strTmp = strTmp + ",i13003-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("MeaGap");
                    strTmp = strTmp + ",i13004-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("DigTemp");
                    strTmp = strTmp + ",i13005-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("DigTime");
                    strTmp = strTmp + ",i13006-Info=" + TimeHelper.formatDataTime_yyyyMMddHHmmss((Timestamp)((DevService)this.mMonitor.devServiceList.get(j)).getParam("BlnkCalTime"));
                    strTmp = strTmp + ",i13007-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("InterceptB");
                    strTmp = strTmp + ",i13008-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("SlopeK");
                    strTmp = strTmp + ",i13009-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("DetectionVal");
                    break;
                }
            }
        } else {
            for(j = 0; j < this.mMonitor.devServiceList.size(); ++j) {
                if (cfgFactor.getDevID() == ((DevService)this.mMonitor.devServiceList.get(j)).getDevID()) {
                    strTmp = strTmp + ";PolId=" + cfgFactor.getCodeGJ();
                    byte var6 = -1;
                    switch(InfoID.hashCode()) {
                        case -1242086906:
                            if (InfoID.equals("i13001")) {
                                var6 = 0;
                            }
                            break;
                        case -1242086905:
                            if (InfoID.equals("i13002")) {
                                var6 = 1;
                            }
                            break;
                        case -1242086904:
                            if (InfoID.equals("i13003")) {
                                var6 = 2;
                            }
                            break;
                        case -1242086903:
                            if (InfoID.equals("i13004")) {
                                var6 = 3;
                            }
                            break;
                        case -1242086902:
                            if (InfoID.equals("i13005")) {
                                var6 = 4;
                            }
                            break;
                        case -1242086901:
                            if (InfoID.equals("i13006")) {
                                var6 = 5;
                            }
                            break;
                        case -1242086900:
                            if (InfoID.equals("i13007")) {
                                var6 = 6;
                            }
                            break;
                        case -1242086899:
                            if (InfoID.equals("i13008")) {
                                var6 = 7;
                            }
                            break;
                        case -1242086898:
                            if (InfoID.equals("i13009")) {
                                var6 = 8;
                            }
                    }

                    switch(var6) {
                        case 0:
                            strTmp = strTmp + ",i13001-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("RangeTop");
                            return strTmp;
                        case 1:
                            strTmp = strTmp + ",i13002-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("MeaAccuracy");
                            return strTmp;
                        case 2:
                            strTmp = strTmp + ",i13003-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("MeaGap");
                            return strTmp;
                        case 3:
                            strTmp = strTmp + ",i13004-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("DigTemp");
                            return strTmp;
                        case 4:
                            strTmp = strTmp + ",i13005-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("DigTime");
                            return strTmp;
                        case 5:
                            strTmp = strTmp + ",i13006-Info=" + TimeHelper.formatDataTime_yyyyMMddHHmmss((Timestamp)((DevService)this.mMonitor.devServiceList.get(j)).getParam("BlnkCalTime"));
                            return strTmp;
                        case 6:
                            strTmp = strTmp + ",i13007-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("InterceptB");
                            return strTmp;
                        case 7:
                            strTmp = strTmp + ",i13008-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("SlopeK");
                            return strTmp;
                        case 8:
                            strTmp = strTmp + ",i13009-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("DetectionVal");
                            return strTmp;
                        default:
                            return strTmp;
                    }
                }
            }
        }

        return strTmp;
    }
}
