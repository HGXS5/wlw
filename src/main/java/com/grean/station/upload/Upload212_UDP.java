//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.upload;

import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.plc.DefWord;
import com.grean.station.domain.DO.rec.RecCheckFive;
import com.grean.station.domain.DO.rec.RecCheckPar;
import com.grean.station.domain.DO.rec.RecCheckRcvr;
import com.grean.station.domain.DO.rec.RecCheckStd;
import com.grean.station.domain.DO.rec.RecCheckZero;
import com.grean.station.domain.DO.rec.RecDataAvg;
import com.grean.station.domain.DO.rec.RecDataFactor;
import com.grean.station.domain.DO.rec.RecDataTime;
import com.grean.station.domain.DO.rec.RecDevParam;
import com.grean.station.domain.DO.rec.RecLogDev;
import com.grean.station.domain.DO.rec.RecLogSys;
import com.grean.station.service.dev.DevExeListener;
import com.grean.station.service.dev.DevService;
import com.grean.station.service.dev.sample.SampleExeListener;
import com.grean.station.service.script.ScriptWord;
import com.grean.station.utils.TimeHelper;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Upload212_UDP extends UploadInter {
    public Upload212_UDP() {
    }

    public byte[] getBootTimePkg(String strQN, String strFlag) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String strSend = "QN=" + strQN + ";";
        strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2081;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
        strSend = strSend + "DataTime=" + df.format(new Date());
        strSend = strSend + "&&";
        return this.getSendBytes(strSend);
    }

    public byte[] getHeartBeatPkg(String strQN, String strFlag) {
        String strSend = "QN=" + strQN + ";";
        strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=9015;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&&&";
        return this.getSendBytes(strSend);
    }

    public byte[] getRsrvSamplePkg(String strQN, String strFlag, String strDataTime, String strVaseNo) {
        String strSend = "QN=" + strQN + ";";
        strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=3015;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
        strSend = strSend + "DataTime=" + strDataTime + ";VaseNo=" + strVaseNo;
        strSend = strSend + "&&";
        return this.getSendBytes(strSend);
    }

    private byte[] getPeriodRealPkg(String strQN, String strFlag) {
        String strSend = "QN=" + strQN + ";";
        strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=1061;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
        strSend = strSend + "RtdInterval=" + this.mUploadCfg.getPeriod_real() / 60;
        strSend = strSend + "&&";
        return this.getSendBytes(strSend);
    }

    private byte[] getSysTimePkg(String strQN, String strFlag) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String strSend = "QN=" + strQN + ";";
        strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=1014;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
        strSend = strSend + "SystemTime=" + df.format(new Date());
        strSend = strSend + "&&";
        return this.getSendBytes(strSend);
    }

    private byte[] getDevTimePkg(String strQN, String strFlag, String factorCode) {
        for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
            if (((CfgFactor)this.mMonitor.cfgFactorList.get(i)).getCodeGJ().equals(factorCode)) {
                for(int j = 0; j < this.mMonitor.devServiceList.size(); ++j) {
                    if (((CfgFactor)this.mMonitor.cfgFactorList.get(i)).getDevID() == ((DevService)this.mMonitor.devServiceList.get(j)).getDevID()) {
                        Timestamp timestamp = (Timestamp)((DevService)this.mMonitor.devServiceList.get(j)).getParam("DevTime");
                        String strSend = "QN=" + strQN + ";";
                        strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=1011;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
                        strSend = strSend + "PolId =" + factorCode + ";SystemTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(timestamp);
                        strSend = strSend + "&&";
                        return this.getSendBytes(strSend);
                    }
                }

                return null;
            }
        }

        return null;
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
                        Upload212_UDP.this.sendExeResult(cmdQN, cmdID, exeResult);
                    }

                    if (exeResult == 1) {
                        Upload212_UDP.this.mMonitor.saveRecLogSys("系统日志", "启动" + devService.getDevName() + "成功");
                    } else {
                        Upload212_UDP.this.mMonitor.saveRecLogSys("系统日志", "启动" + devService.getDevName() + "失败");
                    }

                }
            });
        }

    }

    private boolean setRsrvSample(String strQN) {
        if (this.mMonitor.sampleService != null) {
            this.mMonitor.sampleService.setSampleExeListener(new SampleExeListener() {
                public void OnExeEvent(String cmdQN, int cmdID, int exeResult, String strDataTime, String strVaseNo) {
                    if (exeResult == 1 && cmdID == 3015) {
                        byte[] bSend = Upload212_UDP.this.getRsrvSamplePkg(cmdQN, "8", strDataTime, strVaseNo);
                        Upload212_UDP.this.mInterComm.Send(bSend);
                    }

                    Upload212_UDP.this.sendExeResult(cmdQN, cmdID, exeResult);
                }
            });

            try {
                this.mMonitor.sampleService.doPrepare();
                Thread.sleep(1000L);
            } catch (Exception var3) {
            }

            if (this.mMonitor.sampleService.doSample(strQN)) {
                this.mMonitor.saveRecLogSample(this.mMonitor.sampleService.getSampleVase() + "", this.mMonitor.sampleService.getSampleVolume() + "", "手动留样", "留样成功");
                this.mMonitor.saveSampleRecord();
            } else {
                this.mMonitor.saveRecLogSample(this.mMonitor.sampleService.getSampleVase() + "", this.mMonitor.sampleService.getSampleVolume() + "", "手动留样", "留样失败");
            }

            return true;
        } else {
            return false;
        }
    }

    private byte[] getSysEnvironmentPkg(String strQN, String strFlag) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String strSend = "QN=" + strQN + ";";
        strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=3041;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
        strSend = strSend + "DataTime=" + df.format(new Date());
        strSend = strSend + ";Lng=" + this.getFactorVal("Lng");
        strSend = strSend + ";Lat=" + this.getFactorVal("Lat");
        strSend = strSend + ";Temp=" + this.getFactorVal("Temp");
        strSend = strSend + ";Hum=" + this.getFactorVal("Hum");
        strSend = strSend + ";Volt=" + this.getFactorVal("Volt");
        strSend = strSend + "&&";
        return this.getSendBytes(strSend);
    }

    private byte[] getSysStatePkg(String strQN, String strFlag) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String strSend = "QN=" + strQN + ";";
        strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=3040;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
        strSend = strSend + "RunMode=" + (int)ScriptWord.getInstance().getSysVar("RunMode");
        strSend = strSend + ";PumpState=" + (int)ScriptWord.getInstance().getSysVar("PumpState");
        strSend = strSend + ";SystemTask=" + (int)ScriptWord.getInstance().getSysVar("SystemTask");
        strSend = strSend + ";ValveCount=" + ScriptWord.getInstance().getValveCount();
        strSend = strSend + ";ValveStateList=" + this.getValveState();
        strSend = strSend + ";SandTime=" + (int)ScriptWord.getInstance().getSysVar("SandTime");
        strSend = strSend + ";SandCleanTime=" + (int)ScriptWord.getInstance().getSysVar("SandCleanTime");
        strSend = strSend + ";SandWaitTime=" + (int)ScriptWord.getInstance().getSysVar("SandWaitTime");
        strSend = strSend + ";MeasureWaitTime=" + (int)ScriptWord.getInstance().getSysVar("MeasureWaitTime");
        strSend = strSend + ";CleanOutPipeTime=" + (int)ScriptWord.getInstance().getSysVar("CleanOutPipeTime");
        strSend = strSend + ";CleanInPipeTime=" + (int)ScriptWord.getInstance().getSysVar("CleanInPipeTime");
        strSend = strSend + ";AirCleanTime=" + (int)ScriptWord.getInstance().getSysVar("AirCleanTime");
        strSend = strSend + ";AirCleanInterval=" + (int)ScriptWord.getInstance().getSysVar("AirCleanInterval");
        strSend = strSend + ";WcleanTime=" + (int)ScriptWord.getInstance().getSysVar("WcleanTime");
        strSend = strSend + ";WcleanInterval=" + (int)ScriptWord.getInstance().getSysVar("WcleanInterval");
        strSend = strSend + ";AlgClean=" + (int)ScriptWord.getInstance().getSysVar("AlgClean");
        strSend = strSend + ";RtdInterval=" + this.mUploadCfg.getPeriod_real() / 60;
        strSend = strSend + ";RunInterval=" + (int)ScriptWord.getInstance().getSysVar("RunInterval");
        strSend = strSend + ";SystemAlarm=" + this.getSysAlarm();
        strSend = strSend + ";SystemTime=" + df.format(new Date());
        strSend = strSend + "&&";
        return this.getSendBytes(strSend);
    }

    private Double getFactorVal(String factorCode) {
        for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
            if (((CfgFactor)this.mMonitor.cfgFactorList.get(i)).getCodeGJ().equals(factorCode)) {
                return ((CfgFactor)this.mMonitor.cfgFactorList.get(i)).getDataMea();
            }
        }

        return null;
    }

    private String getSysAlarm() {
        String strResult = "";
        int iResult = (int)ScriptWord.getInstance().getSysVar("SystemAlarm");
        if (iResult == 0) {
            strResult = "00";
            return strResult;
        } else {
            for(int i = 0; i < 3; ++i) {
                int iFlag = iResult % 10;
                iResult /= 10;
                if (iFlag == 1) {
                    strResult = strResult + String.format("%02d|", i + 1);
                }
            }

            return strResult.substring(0, strResult.length() - 1);
        }
    }

    private String getValveState() {
        String strResult = "";
        List<DefWord> defWordList = ScriptWord.getInstance().getDefWordList();
        boolean[] qStatus = this.mMonitor.getScriptService().getQStatus();

        for(int i = 0; i < defWordList.size(); ++i) {
            if (((DefWord)defWordList.get(i)).getType() == 1 && ((DefWord)defWordList.get(i)).isIsvalve()) {
                if (qStatus[i]) {
                    strResult = strResult + "1|";
                } else {
                    strResult = strResult + "0|";
                }
            }
        }

        return strResult.substring(0, strResult.length() - 1);
    }

    public byte[] getPowerAlarmPkg(String strQN, String strFlag, boolean powerAlarm) {
        String strSend = "QN=" + strQN + ";";
        strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=3040;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
        if (powerAlarm) {
            strSend = strSend + "SystemAlarm=01";
        } else {
            strSend = strSend + "SystemAlarm=00";
        }

        strSend = strSend + "&&";
        return this.getSendBytes(strSend);
    }

    public byte[] getRealTimePkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecDataFactor> recDataList = this.mMonitor.getRecMapper().getRecDataMinListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2011;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                CfgFactor cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if ((factorID <= 0 || factorID == cfgFactor.getId()) && cfgFactor.isUsed() && (cfgFactor.isFiveType() || cfgFactor.isParmType())) {
                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecDataFactor)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Rtd=" + ((RecDataFactor)recDataList.get(j)).getDataString() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=" + ((RecDataFactor)recDataList.get(j)).getDataFlag();
                            break;
                        }
                    }
                }
            }

            strSend = strSend + "&&";
            String exStr = "CN=2051;MN=" + this.mUploadCfg.getMn() + ";CP=&&BeginTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime()) + ",";
            return this.getSendBytes(strSend, exStr);
        }
    }

    public byte[] getMinDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecDataAvg> recDataList = this.mMonitor.getRecMapper().getRecDataMinAvgListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2051;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());
            Iterator var7 = this.mMonitor.cfgFactorList.iterator();

            while(true) {
                while(true) {
                    CfgFactor cfgFactor;
                    do {
                        do {
                            do {
                                if (!var7.hasNext()) {
                                    strSend = strSend + "&&";
                                    String exStr = "CN=2051;MN=" + this.mUploadCfg.getMn() + ";CP=&&BeginTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime()) + ",";
                                    return this.getSendBytes(strSend, exStr);
                                }

                                cfgFactor = (CfgFactor)var7.next();
                            } while(factorID > 0 && factorID != cfgFactor.getId());
                        } while(!cfgFactor.isUsed());
                    } while(!cfgFactor.isParmType() && !cfgFactor.isFiveType() && !cfgFactor.isSysType() && !cfgFactor.isSwType());

                    Iterator var9 = recDataList.iterator();

                    while(var9.hasNext()) {
                        RecDataAvg recDataAvg = (RecDataAvg)var9.next();
                        if (recDataAvg.getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Avg=" + recDataAvg.getDataValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=" + recDataAvg.getDataFlag();
                            break;
                        }
                    }
                }
            }
        }
    }

    public byte[] getHourDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecDataFactor> recDataList = this.mMonitor.getRecMapper().getRecDataHourListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2061;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                CfgFactor cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if ((factorID <= 0 || factorID == cfgFactor.getId()) && cfgFactor.isUsed() && cfgFactor.isUpload() && (cfgFactor.isParmType() || cfgFactor.isFiveType() || cfgFactor.isSysType() || cfgFactor.isSwType())) {
                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecDataFactor)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Avg=" + ((RecDataFactor)recDataList.get(j)).getDataString() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=" + ((RecDataFactor)recDataList.get(j)).getDataFlag();
                            break;
                        }
                    }
                }
            }

            strSend = strSend + "&&";
            String exStr = "CN=2051;MN=" + this.mUploadCfg.getMn() + ";CP=&&BeginTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime()) + ",";
            return this.getSendBytes(strSend, exStr);
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
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2061;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                CfgFactor cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if ((factorID <= 0 || factorID == cfgFactor.getId()) && cfgFactor.isUsed() && cfgFactor.isFiveType()) {
                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecDataFactor)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Avg=" + ((RecDataFactor)recDataList.get(j)).getDataString() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=" + ((RecDataFactor)recDataList.get(j)).getDataFlag();
                            break;
                        }
                    }
                }
            }

            strSend = strSend + "&&";
            String exStr = "CN=2051;MN=" + this.mUploadCfg.getMn() + ";CP=&&BeginTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime()) + ",";
            return this.getSendBytes(strSend, exStr);
        }
    }

    public byte[] getFiveStdCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecCheckFive> recDataList = this.mMonitor.getRecMapper().getRecCheckFiveDataListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2062;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                CfgFactor cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if ((factorID <= 0 || factorID == cfgFactor.getId()) && cfgFactor.isUsed() && cfgFactor.isFiveType()) {
                    Iterator var9 = recDataList.iterator();

                    while(var9.hasNext()) {
                        RecCheckFive recCheckFive = (RecCheckFive)var9.next();
                        if (recCheckFive.getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Check=" + recCheckFive.getCurValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-StandardValue=" + recCheckFive.getStdValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=" + recCheckFive.getDataFlag();
                            break;
                        }
                    }
                }
            }

            strSend = strSend + "&&";
            String exStr = "CN=2051;MN=" + this.mUploadCfg.getMn() + ";CP=&&BeginTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime()) + ",";
            return this.getSendBytes(strSend, exStr);
        }
    }

    public byte[] getStdCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecCheckStd> recDataList = this.mMonitor.getRecMapper().getRecCheckStdDataListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2062;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                CfgFactor cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if ((factorID <= 0 || factorID == cfgFactor.getId()) && cfgFactor.isUsed() && cfgFactor.isParmType()) {
                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecCheckStd)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Check=" + ((RecCheckStd)recDataList.get(j)).getTestValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-StandardValue=" + ((RecCheckStd)recDataList.get(j)).getStdValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=" + ((RecCheckStd)recDataList.get(j)).getDataFlag();
                            break;
                        }
                    }
                }
            }

            strSend = strSend + "&&";
            String exStr = "CN=2051;MN=" + this.mUploadCfg.getMn() + ";CP=&&BeginTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime()) + ",";
            return this.getSendBytes(strSend, exStr);
        }
    }

    public byte[] getZeroCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecCheckZero> recDataList = this.mMonitor.getRecMapper().getRecCheckZeroDataListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2065;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                CfgFactor cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if ((factorID <= 0 || factorID == cfgFactor.getId()) && cfgFactor.isUsed() && cfgFactor.isParmType()) {
                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecCheckZero)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Check=" + ((RecCheckZero)recDataList.get(j)).getCurValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-StandardValue=" + ((RecCheckZero)recDataList.get(j)).getStdValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-SpanValue=" + ((RecCheckZero)recDataList.get(j)).getSpanValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=" + ((RecCheckZero)recDataList.get(j)).getDataFlag();
                            break;
                        }
                    }
                }
            }

            strSend = strSend + "&&";
            String exStr = "CN=2051;MN=" + this.mUploadCfg.getMn() + ";CP=&&BeginTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime()) + ",";
            return this.getSendBytes(strSend, exStr);
        }
    }

    public byte[] getSpanCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecCheckZero> recDataList = this.mMonitor.getRecMapper().getRecCheckSpanDataListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2066;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                CfgFactor cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if ((factorID <= 0 || factorID == cfgFactor.getId()) && cfgFactor.isUsed() && cfgFactor.isParmType()) {
                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecCheckZero)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Check=" + ((RecCheckZero)recDataList.get(j)).getCurValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-StandardValue=" + ((RecCheckZero)recDataList.get(j)).getStdValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-SpanValue=" + ((RecCheckZero)recDataList.get(j)).getSpanValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=" + ((RecCheckZero)recDataList.get(j)).getDataFlag();
                            break;
                        }
                    }
                }
            }

            strSend = strSend + "&&";
            String exStr = "CN=2051;MN=" + this.mUploadCfg.getMn() + ";CP=&&BeginTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime()) + ",";
            return this.getSendBytes(strSend, exStr);
        }
    }

    public byte[] getRcvrCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecCheckRcvr> recDataList = this.mMonitor.getRecMapper().getRecCheckRcvrDataListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2063;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                CfgFactor cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if ((factorID <= 0 || factorID == cfgFactor.getId()) && cfgFactor.isUsed() && cfgFactor.isParmType()) {
                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecCheckRcvr)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Check=" + ((RecCheckRcvr)recDataList.get(j)).getAfterValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-WaterTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(((RecCheckRcvr)recDataList.get(j)).getBeforeTime()) + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Water=" + ((RecCheckRcvr)recDataList.get(j)).getBeforeValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Chroma=" + ((RecCheckRcvr)recDataList.get(j)).getMotherValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Volume=" + ((RecCheckRcvr)recDataList.get(j)).getMotherVolume() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-DVolume=" + ((RecCheckRcvr)recDataList.get(j)).getCupVolume() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=" + ((RecCheckRcvr)recDataList.get(j)).getDataFlag();
                            break;
                        }
                    }
                }
            }

            strSend = strSend + "&&";
            String exStr = "CN=2051;MN=" + this.mUploadCfg.getMn() + ";CP=&&BeginTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime()) + ",";
            return this.getSendBytes(strSend, exStr);
        }
    }

    public byte[] getParCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecCheckPar> recDataList = this.mMonitor.getRecMapper().getRecCheckParDataListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2064;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
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
            String exStr = "CN=2051;MN=" + this.mUploadCfg.getMn() + ";CP=&&BeginTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime()) + ",";
            return this.getSendBytes(strSend, exStr);
        }
    }

    public byte[] getLogSysPkg(String strQN, String strFlag, RecDataTime recDataTime) {
        List<RecLogSys> recLogSysList = this.mMonitor.getRecMapper().getRecLogSysListByTime(recDataTime);
        if (recLogSysList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=3020;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < recLogSysList.size(); ++i) {
                strSend = strSend + ";PolId=w00000,i21001-Info=//" + ((RecLogSys)recLogSysList.get(i)).getLogDesc() + "//";
            }

            strSend = strSend + "&&";
            return this.getSendBytes(strSend);
        }
    }

    public byte[] getLogDevPkg(String strQN, String strFlag, RecDataTime recDataTime, String PolID) {
        boolean bCheck = false;
        List<RecLogDev> recLogDevList = this.mMonitor.getRecMapper().getRecLogDevListByTime(recDataTime);
        if (recLogDevList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=3020;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());
            Iterator var8;
            CfgFactor cfgFactor;
            Iterator var10;
            RecLogDev recLogDev;
            if (PolID == null) {
                var8 = this.mMonitor.cfgFactorList.iterator();

                label74:
                while(true) {
                    while(true) {
                        do {
                            do {
                                if (!var8.hasNext()) {
                                    break label74;
                                }

                                cfgFactor = (CfgFactor)var8.next();
                            } while(!cfgFactor.isUsed());
                        } while(!cfgFactor.isFiveType() && !cfgFactor.isParmType());

                        var10 = recLogDevList.iterator();

                        while(var10.hasNext()) {
                            recLogDev = (RecLogDev)var10.next();
                            if (recLogDev.getFactorID() == cfgFactor.getId()) {
                                if (recLogDev.getLogDesc() != null) {
                                    bCheck = true;
                                    strSend = strSend + ";PolId=" + cfgFactor.getCodeGJ() + ",i11001-Info=//" + recLogDev.getLogDesc() + "//";
                                }
                                break;
                            }
                        }
                    }
                }
            } else {
                var8 = this.mMonitor.cfgFactorList.iterator();

                label51:
                while(var8.hasNext()) {
                    cfgFactor = (CfgFactor)var8.next();
                    if (cfgFactor.isUsed() && cfgFactor.getCodeGJ().equals(PolID)) {
                        var10 = recLogDevList.iterator();

                        do {
                            if (!var10.hasNext()) {
                                break label51;
                            }

                            recLogDev = (RecLogDev)var10.next();
                        } while(recLogDev.getFactorID() != cfgFactor.getId());

                        if (recLogDev.getLogDesc() != null) {
                            bCheck = true;
                            strSend = strSend + ";PolId=" + cfgFactor.getCodeGJ() + ",i11001-Info=//" + recLogDev.getLogDesc() + "//";
                        }
                        break;
                    }
                }
            }

            strSend = strSend + "&&";
            return bCheck ? this.getSendBytes(strSend) : null;
        }
    }

    public byte[] getStateSysPkg(String strQN, String strFlag, RecDataTime recDataTime, String InfoID) {
        String strSend = "QN=" + strQN + ";";
        strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=3020;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
        strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());
        strSend = strSend + ";PolId=w00000";
        if (InfoID == null) {
            strSend = strSend + ",i22001-Info=0";
            strSend = strSend + ",i22002-Info=0";
            strSend = strSend + ",i22003-Info=0";
        } else {
            byte var7 = -1;
            switch(InfoID.hashCode()) {
                case -1241193176:
                    if (InfoID.equals("i22001")) {
                        var7 = 0;
                    }
                    break;
                case -1241193175:
                    if (InfoID.equals("i22002")) {
                        var7 = 1;
                    }
                    break;
                case -1241193174:
                    if (InfoID.equals("i22003")) {
                        var7 = 2;
                    }
            }

            switch(var7) {
                case 0:
                    strSend = strSend + ",i22001-Info=0";
                    break;
                case 1:
                    strSend = strSend + ",i22002-Info=0";
                    break;
                case 2:
                    strSend = strSend + ",i22003-Info=0";
            }
        }

        strSend = strSend + "&&";
        return this.getSendBytes(strSend);
    }

    public byte[] getStateDevPkg(String strQN, String strFlag, RecDataTime recDataTime, String PolID, String InfoID) {
        String strSend = "QN=" + strQN + ";";
        strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=3020;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
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
        strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=3020;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=9;CP=&&";
        strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());
        int i;
        CfgFactor cfgFactor;
        if (PolID == null) {
            for(i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if (cfgFactor.isUsed()) {
                    if (recDataTime.getId() > 0) {
                        strSend = strSend + this.getDevParam(recDataTime, cfgFactor, InfoID, strFlag);
                    } else {
                        if (cfgFactor.isParmType()) {
                            strSend = strSend + this.getDevParam(cfgFactor, InfoID);
                        }

                        if (cfgFactor.isFiveType()) {
                            strSend = strSend + this.getDevParamFive(cfgFactor, InfoID);
                        }
                    }
                }
            }
        } else {
            for(i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if (cfgFactor.isUsed() && cfgFactor.getCodeGJ().equals(PolID)) {
                    if (recDataTime.getId() > 0) {
                        strSend = strSend + this.getDevParam(recDataTime, cfgFactor, InfoID, strFlag);
                    } else {
                        if (cfgFactor.isParmType()) {
                            strSend = strSend + this.getDevParam(cfgFactor, InfoID);
                        }

                        if (cfgFactor.isFiveType()) {
                            strSend = strSend + this.getDevParamFive(cfgFactor, InfoID);
                        }
                    }
                    break;
                }
            }
        }

        strSend = strSend + "&&";
        return this.getSendBytes(strSend);
    }

    private String getDevState(CfgFactor cfgFactor, String InfoID) {
        String strTmp = "";
        int j;
        if (InfoID == null) {
            for(j = 0; j < this.mMonitor.devServiceList.size(); ++j) {
                if (cfgFactor.getDevID() == ((DevService)this.mMonitor.devServiceList.get(j)).getDevID()) {
                    strTmp = strTmp + ";PolId=" + cfgFactor.getCodeGJ();
                    strTmp = strTmp + ",i12001-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("DevState");
                    if (((DevService)this.mMonitor.devServiceList.get(j)).isbConnect()) {
                        strTmp = strTmp + ",i12002-Info=0";
                    } else {
                        strTmp = strTmp + ",i12002-Info=1";
                    }

                    strTmp = strTmp + ",i12003-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("ReagentLeft");
                    strTmp = strTmp + ",i12031-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("DevAlarm");
                    break;
                }
            }
        } else {
            for(j = 0; j < this.mMonitor.devServiceList.size(); ++j) {
                if (cfgFactor.getDevID() == ((DevService)this.mMonitor.devServiceList.get(j)).getDevID()) {
                    strTmp = strTmp + ";PolId=" + cfgFactor.getCodeGJ();
                    byte var6 = -1;
                    switch(InfoID.hashCode()) {
                        case -1242116697:
                            if (InfoID.equals("i12001")) {
                                var6 = 0;
                            }
                            break;
                        case -1242116696:
                            if (InfoID.equals("i12002")) {
                                var6 = 1;
                            }
                            break;
                        case -1242116695:
                            if (InfoID.equals("i12003")) {
                                var6 = 2;
                            }
                            break;
                        case -1242116604:
                            if (InfoID.equals("i12031")) {
                                var6 = 3;
                            }
                    }

                    switch(var6) {
                        case 0:
                            strTmp = strTmp + ",i12001-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("DevState");
                            return strTmp;
                        case 1:
                            if (((DevService)this.mMonitor.devServiceList.get(j)).isbConnect()) {
                                strTmp = strTmp + ",i12002-Info=0";
                            } else {
                                strTmp = strTmp + ",i12002-Info=1";
                            }

                            return strTmp;
                        case 2:
                            strTmp = strTmp + ",i12003-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("ReagentLeft");
                            return strTmp;
                        case 3:
                            strTmp = strTmp + ",i12031-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("DevAlarm");
                            return strTmp;
                        default:
                            return strTmp;
                    }
                }
            }
        }

        return strTmp;
    }
    @SuppressWarnings("FallThrough")
    private String getDevParam(CfgFactor cfgFactor, String InfoID) {
        String strTmp = "";
        Double dTmp;
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
                    dTmp = (Double)((DevService)this.mMonitor.devServiceList.get(j)).getParam("InterceptB");
                    if (dTmp != null) {
                        strTmp = strTmp + ",i13007-Info=" + Utility.getLongDouble(dTmp);
                    }

                    dTmp = (Double)((DevService)this.mMonitor.devServiceList.get(j)).getParam("SlopeK");
                    if (dTmp != null) {
                        strTmp = strTmp + ",i13008-Info=" + Utility.getLongDouble(dTmp);
                    }

                    dTmp = (Double)((DevService)this.mMonitor.devServiceList.get(j)).getParam("DetectionVal");
                    if (dTmp != null) {
                        strTmp = strTmp + ",i13009-Info=" + Utility.getLongDouble(dTmp);
                    }

                    dTmp = (Double)((DevService)this.mMonitor.devServiceList.get(j)).getParam("MeaProcVal");
                    if (dTmp != null) {
                        strTmp = strTmp + ",i13010-Info=" + Utility.getLongDouble(dTmp);
                    }

                    dTmp = (Double)((DevService)this.mMonitor.devServiceList.get(j)).getParam("LinearFactor");
                    if (dTmp != null) {
                        strTmp = strTmp + ",i13011-Info=" + Utility.getLongDouble(dTmp);
                    }

                    dTmp = (Double)((DevService)this.mMonitor.devServiceList.get(j)).getParam("BinomialFactor");
                    if (dTmp != null) {
                        strTmp = strTmp + ",i13012-Info=" + Utility.getLongDouble(dTmp);
                    }

                    strTmp = strTmp + ",i13013-Info=" + TimeHelper.formatDataTime_yyyyMMddHHmmss((Timestamp)((DevService)this.mMonitor.devServiceList.get(j)).getParam("StdCalTime"));
                    dTmp = (Double)((DevService)this.mMonitor.devServiceList.get(j)).getParam("DilutionFactor");
                    if (dTmp != null) {
                        strTmp = strTmp + ",i13014-Info=" + Utility.getLongDouble(dTmp);
                    }

                    dTmp = (Double)((DevService)this.mMonitor.devServiceList.get(j)).getParam("TrinomialFactor");
                    if (dTmp != null) {
                        strTmp = strTmp + ",i13015-Info=" + Utility.getLongDouble(dTmp);
                    }

                    dTmp = (Double)((DevService)this.mMonitor.devServiceList.get(j)).getParam("BlnkCalFactor");
                    if (dTmp != null) {
                        strTmp = strTmp + ",i13016-Info=" + Utility.getLongDouble(dTmp);
                    }

                    dTmp = (Double)((DevService)this.mMonitor.devServiceList.get(j)).getParam("RangeFactor");
                    if (dTmp != null) {
                        strTmp = strTmp + ",i13017-Info=" + Utility.getLongDouble(dTmp);
                    }
                    break;
                }
            }
        } else {
            for(j = 0; j < this.mMonitor.devServiceList.size(); ++j) {
                if (cfgFactor.getDevID() == ((DevService)this.mMonitor.devServiceList.get(j)).getDevID()) {
                    strTmp = strTmp + ";PolId=" + cfgFactor.getCodeGJ();
                    byte var7 = -1;
                    switch(InfoID.hashCode()) {
                        case -1242086906:
                            if (InfoID.equals("i13001")) {
                                var7 = 0;
                            }
                            break;
                        case -1242086905:
                            if (InfoID.equals("i13002")) {
                                var7 = 1;
                            }
                            break;
                        case -1242086904:
                            if (InfoID.equals("i13003")) {
                                var7 = 2;
                            }
                            break;
                        case -1242086903:
                            if (InfoID.equals("i13004")) {
                                var7 = 3;
                            }
                            break;
                        case -1242086902:
                            if (InfoID.equals("i13005")) {
                                var7 = 4;
                            }
                            break;
                        case -1242086901:
                            if (InfoID.equals("i13006")) {
                                var7 = 5;
                            }
                            break;
                        case -1242086900:
                            if (InfoID.equals("i13007")) {
                                var7 = 6;
                            }
                            break;
                        case -1242086899:
                            if (InfoID.equals("i13008")) {
                                var7 = 7;
                            }
                            break;
                        case -1242086898:
                            if (InfoID.equals("i13009")) {
                                var7 = 8;
                            }
                        case -1242086897:
                        case -1242086896:
                        case -1242086895:
                        case -1242086894:
                        case -1242086893:
                        case -1242086892:
                        case -1242086891:
                        case -1242086890:
                        case -1242086889:
                        case -1242086888:
                        case -1242086887:
                        case -1242086886:
                        case -1242086885:
                        case -1242086884:
                        case -1242086883:
                        case -1242086882:
                        case -1242086881:
                        case -1242086880:
                        case -1242086879:
                        case -1242086878:
                        case -1242086877:
                        default:
                            break;
                        case -1242086876:
                            if (InfoID.equals("i13010")) {
                                var7 = 9;
                            }
                            break;
                        case -1242086875:
                            if (InfoID.equals("i13011")) {
                                var7 = 10;
                            }
                            break;
                        case -1242086874:
                            if (InfoID.equals("i13012")) {
                                var7 = 11;
                            }
                            break;
                        case -1242086873:
                            if (InfoID.equals("i13013")) {
                                var7 = 12;
                            }
                            break;
                        case -1242086872:
                            if (InfoID.equals("i13014")) {
                                var7 = 13;
                            }
                            break;
                        case -1242086871:
                            if (InfoID.equals("i13015")) {
                                var7 = 14;
                            }
                            break;
                        case -1242086870:
                            if (InfoID.equals("i13016")) {
                                var7 = 15;
                            }
                            break;
                        case -1242086869:
                            if (InfoID.equals("i13017")) {
                                var7 = 16;
                            }
                    }

                    switch(var7) {
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
                            dTmp = (Double)((DevService)this.mMonitor.devServiceList.get(j)).getParam("InterceptB");
                            strTmp = strTmp + ",i13007-Info=" + Utility.getLongDouble(dTmp);
                            return strTmp;
                        case 7:
                            dTmp = (Double)((DevService)this.mMonitor.devServiceList.get(j)).getParam("SlopeK");
                            strTmp = strTmp + ",i13008-Info=" + Utility.getLongDouble(dTmp);
                            return strTmp;
                        case 8:
                            dTmp = (Double)((DevService)this.mMonitor.devServiceList.get(j)).getParam("DetectionVal");
                            strTmp = strTmp + ",i13009-Info=" + Utility.getLongDouble(dTmp);
                            return strTmp;
                        case 9:
                            dTmp = (Double)((DevService)this.mMonitor.devServiceList.get(j)).getParam("MeaProcVal");
                            strTmp = strTmp + ",i13010-Info=" + Utility.getLongDouble(dTmp);
                            return strTmp;
                        case 10:
                            dTmp = (Double)((DevService)this.mMonitor.devServiceList.get(j)).getParam("LinearFactor");
                            strTmp = strTmp + ",i13011-Info=" + Utility.getLongDouble(dTmp);
                            return strTmp;
                        case 11:
                            dTmp = (Double)((DevService)this.mMonitor.devServiceList.get(j)).getParam("BinomialFactor");
                            strTmp = strTmp + ",i13012-Info=" + Utility.getLongDouble(dTmp);
                            return strTmp;
                        case 12:
                            strTmp = strTmp + ",i13013-Info=" + TimeHelper.formatDataTime_yyyyMMddHHmmss((Timestamp)((DevService)this.mMonitor.devServiceList.get(j)).getParam("StdCalTime"));
                            return strTmp;
                        case 13:
                            dTmp = (Double)((DevService)this.mMonitor.devServiceList.get(j)).getParam("DilutionFactor");
                            strTmp = strTmp + ",i13014-Info=" + Utility.getLongDouble(dTmp);
                            return strTmp;
                        case 14:
                            dTmp = (Double)((DevService)this.mMonitor.devServiceList.get(j)).getParam("TrinomialFactor");
                            strTmp = strTmp + ",i13015-Info=" + Utility.getLongDouble(dTmp);
                            return strTmp;
                        case 15:
                            dTmp = (Double)((DevService)this.mMonitor.devServiceList.get(j)).getParam("BlnkCalFactor");
                            strTmp = strTmp + ",i13016-Info=" + Utility.getLongDouble(dTmp);
                            return strTmp;
                        case 16:
                            dTmp = (Double)((DevService)this.mMonitor.devServiceList.get(j)).getParam("RangeFactor");
                            strTmp = strTmp + ",i13017-Info=" + Utility.getLongDouble(dTmp);
                            return strTmp;
                        default:
                            return strTmp;
                    }
                }
            }
        }

        return strTmp;
    }

    private String getDevParam(RecDataTime recDataTime, CfgFactor cfgFactor, String InfoID, String paramType) {
        String strTmp = "";
        List<RecDevParam> recDevParamList = this.mMonitor.getRecMapper().getRecDataParamListByTime(recDataTime);
        if (recDevParamList != null && recDevParamList.size() != 0) {
            Iterator var7;
            RecDevParam recDevParam;
            if (InfoID == null) {
                strTmp = strTmp + ";PolId=" + cfgFactor.getCodeGJ();
                var7 = recDevParamList.iterator();

                while(var7.hasNext()) {
                    recDevParam = (RecDevParam)var7.next();
                    if (recDevParam.getFactor_id() == cfgFactor.getId() && recDevParam.getParam_type() == Integer.parseInt(paramType)) {
                        strTmp = strTmp + "," + recDevParam.getParam_name() + "-Info=" + recDevParam.getParam_value();
                    }
                }
            } else {
                strTmp = strTmp + ";PolId=" + cfgFactor.getCodeGJ();
                var7 = recDevParamList.iterator();

                while(var7.hasNext()) {
                    recDevParam = (RecDevParam)var7.next();
                    if (recDevParam.getFactor_id() == cfgFactor.getId() && InfoID.equals(recDevParam.getParam_name()) && recDevParam.getParam_type() == Integer.parseInt(paramType)) {
                        strTmp = strTmp + "," + recDevParam.getParam_name() + "-Info=" + recDevParam.getParam_value();
                        break;
                    }
                }
            }

            return strTmp;
        } else {
            return "";
        }
    }
    @SuppressWarnings("FallThrough")
    private String getDevParamFive(CfgFactor cfgFactor, String InfoID) {
        String strTmp = "";
        int j;
        if (InfoID == null) {
            for(j = 0; j < this.mMonitor.devServiceList.size(); ++j) {
                if (cfgFactor.getDevID() == ((DevService)this.mMonitor.devServiceList.get(j)).getDevID()) {
                    strTmp = strTmp + ";PolId=" + cfgFactor.getCodeGJ();
                    strTmp = strTmp + ",i13020-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("PHMin");
                    strTmp = strTmp + ",i13021-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("PHMax");
                    strTmp = strTmp + ",i13022-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("DOXMin");
                    strTmp = strTmp + ",i13023-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("DOXMax");
                    strTmp = strTmp + ",i13024-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("CONDMin");
                    strTmp = strTmp + ",i13025-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("CONDMax");
                    strTmp = strTmp + ",i13026-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("TURBMin");
                    strTmp = strTmp + ",i13027-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("TURBMax");
                    strTmp = strTmp + ",i13028-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("PHPotential");
                    strTmp = strTmp + ",i13029-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("DOXPotential");
                    strTmp = strTmp + ",i13030-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("DOXFluorescence");
                    strTmp = strTmp + ",i13031-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("CONDPotential");
                    strTmp = strTmp + ",i13032-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("TURBLight");
                    break;
                }
            }
        } else {
            for(j = 0; j < this.mMonitor.devServiceList.size(); ++j) {
                if (cfgFactor.getDevID() == ((DevService)this.mMonitor.devServiceList.get(j)).getDevID()) {
                    strTmp = strTmp + ";PolId=" + cfgFactor.getCodeGJ();
                    byte var6 = -1;
                    switch(InfoID.hashCode()) {
                        case -1242086845:
                            if (InfoID.equals("i13020")) {
                                var6 = 0;
                            }
                            break;
                        case -1242086844:
                            if (InfoID.equals("i13021")) {
                                var6 = 1;
                            }
                            break;
                        case -1242086843:
                            if (InfoID.equals("i13022")) {
                                var6 = 2;
                            }
                            break;
                        case -1242086842:
                            if (InfoID.equals("i13023")) {
                                var6 = 3;
                            }
                            break;
                        case -1242086841:
                            if (InfoID.equals("i13024")) {
                                var6 = 4;
                            }
                            break;
                        case -1242086840:
                            if (InfoID.equals("i13025")) {
                                var6 = 5;
                            }
                            break;
                        case -1242086839:
                            if (InfoID.equals("i13026")) {
                                var6 = 6;
                            }
                            break;
                        case -1242086838:
                            if (InfoID.equals("i13027")) {
                                var6 = 7;
                            }
                            break;
                        case -1242086837:
                            if (InfoID.equals("i13028")) {
                                var6 = 8;
                            }
                            break;
                        case -1242086836:
                            if (InfoID.equals("i13029")) {
                                var6 = 9;
                            }
                        case -1242086835:
                        case -1242086834:
                        case -1242086833:
                        case -1242086832:
                        case -1242086831:
                        case -1242086830:
                        case -1242086829:
                        case -1242086828:
                        case -1242086827:
                        case -1242086826:
                        case -1242086825:
                        case -1242086824:
                        case -1242086823:
                        case -1242086822:
                        case -1242086821:
                        case -1242086820:
                        case -1242086819:
                        case -1242086818:
                        case -1242086817:
                        case -1242086816:
                        case -1242086815:
                        default:
                            break;
                        case -1242086814:
                            if (InfoID.equals("i13030")) {
                                var6 = 10;
                            }
                            break;
                        case -1242086813:
                            if (InfoID.equals("i13031")) {
                                var6 = 11;
                            }
                            break;
                        case -1242086812:
                            if (InfoID.equals("i13032")) {
                                var6 = 12;
                            }
                    }

                    switch(var6) {
                        case 0:
                            strTmp = strTmp + ",i13020-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("PHMin");
                            return strTmp;
                        case 1:
                            strTmp = strTmp + ",i13021-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("PHMax");
                            return strTmp;
                        case 2:
                            strTmp = strTmp + ",i13022-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("DOXMin");
                            return strTmp;
                        case 3:
                            strTmp = strTmp + ",i13023-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("DOXMax");
                            return strTmp;
                        case 4:
                            strTmp = strTmp + ",i13024-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("CONDMin");
                            return strTmp;
                        case 5:
                            strTmp = strTmp + ",i13025-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("CONDMax");
                            return strTmp;
                        case 6:
                            strTmp = strTmp + ",i13026-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("TURBMin");
                            return strTmp;
                        case 7:
                            strTmp = strTmp + ",i13027-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("TURBMax");
                            return strTmp;
                        case 8:
                            strTmp = strTmp + ",i13028-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("PHPotential");
                            return strTmp;
                        case 9:
                            strTmp = strTmp + ",i13029-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("DOXPotential");
                            return strTmp;
                        case 10:
                            strTmp = strTmp + ",i13030-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("DOXFluorescence");
                            return strTmp;
                        case 11:
                            strTmp = strTmp + ",i13031-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("CONDPotential");
                            return strTmp;
                        case 12:
                            strTmp = strTmp + ",i13032-Info=" + ((DevService)this.mMonitor.devServiceList.get(j)).getParam("TURBLight");
                            return strTmp;
                        default:
                            return strTmp;
                    }
                }
            }
        }

        return strTmp;
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

                                if (!strFlag.equals("8") && !strCN.equals("9013") && !strCN.equals("9014")) {
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
                    return resultList;
                } else {
                    String strBeginTime = this.getCmdValue(bRecv, "BeginTime");
                    String strEndTime = this.getCmdValue(bRecv, "EndTime");
                    byte var17 = -1;
                    switch(strCN.hashCode()) {
                        case 1507423:
                            if (strCN.equals("1000")) {
                                var17 = 7;
                            }
                            break;
                        case 1507455:
                            if (strCN.equals("1011")) {
                                var17 = 8;
                            }
                            break;
                        case 1507456:
                            if (strCN.equals("1012")) {
                                var17 = 9;
                            }
                            break;
                        case 1507458:
                            if (strCN.equals("1014")) {
                                var17 = 10;
                            }
                            break;
                        case 1507459:
                            if (strCN.equals("1015")) {
                                var17 = 11;
                            }
                            break;
                        case 1507610:
                            if (strCN.equals("1061")) {
                                var17 = 12;
                            }
                            break;
                        case 1507611:
                            if (strCN.equals("1062")) {
                                var17 = 13;
                            }
                            break;
                        case 1507642:
                            if (strCN.equals("1072")) {
                                var17 = 14;
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
                        case 1567041:
                            if (strCN.equals("3015")) {
                                var17 = 15;
                            }
                            break;
                        case 1567067:
                            if (strCN.equals("3020")) {
                                var17 = 16;
                            }
                            break;
                        case 1567068:
                            if (strCN.equals("3021")) {
                                var17 = 17;
                            }
                            break;
                        case 1567129:
                            if (strCN.equals("3040")) {
                                var17 = 18;
                            }
                            break;
                        case 1567130:
                            if (strCN.equals("3041")) {
                                var17 = 19;
                            }
                            break;
                        case 1567131:
                            if (strCN.equals("3042")) {
                                var17 = 20;
                            }
                            break;
                        case 1567132:
                            if (strCN.equals("3043")) {
                                var17 = 21;
                            }
                            break;
                        case 1567133:
                            if (strCN.equals("3044")) {
                                var17 = 22;
                            }
                            break;
                        case 1567134:
                            if (strCN.equals("3045")) {
                                var17 = 23;
                            }
                            break;
                        case 1567135:
                            if (strCN.equals("3046")) {
                                var17 = 24;
                            }
                            break;
                        case 1567136:
                            if (strCN.equals("3047")) {
                                var17 = 25;
                            }
                            break;
                        case 1567137:
                            if (strCN.equals("3048")) {
                                var17 = 26;
                            }
                            break;
                        case 1567138:
                            if (strCN.equals("3049")) {
                                var17 = 27;
                            }
                            break;
                        case 1567160:
                            if (strCN.equals("3050")) {
                                var17 = 28;
                            }
                            break;
                        case 1567161:
                            if (strCN.equals("3051")) {
                                var17 = 29;
                            }
                            break;
                        case 1567162:
                            if (strCN.equals("3052")) {
                                var17 = 30;
                            }
                            break;
                        case 1567163:
                            if (strCN.equals("3053")) {
                                var17 = 31;
                            }
                            break;
                        case 1567164:
                            if (strCN.equals("3054")) {
                                var17 = 32;
                            }
                            break;
                        case 1567165:
                            if (strCN.equals("3055")) {
                                var17 = 33;
                            }
                            break;
                        case 1567166:
                            if (strCN.equals("3056")) {
                                var17 = 34;
                            }
                            break;
                        case 1567167:
                            if (strCN.equals("3057")) {
                                var17 = 35;
                            }
                            break;
                        case 1567168:
                            if (strCN.equals("3058")) {
                                var17 = 36;
                            }
                            break;
                        case 1567169:
                            if (strCN.equals("3059")) {
                                var17 = 37;
                            }
                            break;
                        case 1567191:
                            if (strCN.equals("3060")) {
                                var17 = 38;
                            }
                            break;
                        case 1567192:
                            if (strCN.equals("3061")) {
                                var17 = 39;
                            }
                            break;
                        case 1567193:
                            if (strCN.equals("3062")) {
                                var17 = 40;
                            }
                            break;
                        case 1567194:
                            if (strCN.equals("3063")) {
                                var17 = 41;
                            }
                            break;
                        case 1567195:
                            if (strCN.equals("3064")) {
                                var17 = 42;
                            }
                            break;
                        case 1567196:
                            if (strCN.equals("3065")) {
                                var17 = 43;
                            }
                            break;
                        case 1567197:
                            if (strCN.equals("3066")) {
                                var17 = 44;
                            }
                            break;
                        case 1567253:
                            if (strCN.equals("3080")) {
                                var17 = 45;
                            }
                            break;
                        case 1567254:
                            if (strCN.equals("3081")) {
                                var17 = 46;
                            }
                            break;
                        case 1567255:
                            if (strCN.equals("3082")) {
                                var17 = 47;
                            }
                            break;
                        case 1567256:
                            if (strCN.equals("3083")) {
                                var17 = 48;
                            }
                            break;
                        case 1567257:
                            if (strCN.equals("3084")) {
                                var17 = 49;
                            }
                            break;
                        case 1567258:
                            if (strCN.equals("3085")) {
                                var17 = 50;
                            }
                            break;
                        case 1567259:
                            if (strCN.equals("3086")) {
                                var17 = 51;
                            }
                    }

                    byte[] bTmp;
                    String strTmp;
                    String strTime;
                    String strPolID;
                    List recTimeList;
                    Iterator var33;
                    RecDataTime recDataTime;
                    switch(var17) {
                        case 0:
                            if (strBeginTime != null && strEndTime != null) {
                                recTimeList = this.mMonitor.getRecMapper().getRecTimeMinListByTime(strBeginTime, strEndTime);
                                if (recTimeList != null) {
                                    var33 = recTimeList.iterator();

                                    while(var33.hasNext()) {
                                        recDataTime = (RecDataTime)var33.next();
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
                                    var33 = recTimeList.iterator();

                                    while(var33.hasNext()) {
                                        recDataTime = (RecDataTime)var33.next();
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
                                    var33 = recTimeList.iterator();

                                    while(var33.hasNext()) {
                                        recDataTime = (RecDataTime)var33.next();
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
                                    var33 = recTimeList.iterator();

                                    while(var33.hasNext()) {
                                        recDataTime = (RecDataTime)var33.next();
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
                                    var33 = recTimeList.iterator();

                                    while(var33.hasNext()) {
                                        recDataTime = (RecDataTime)var33.next();
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
                                    var33 = recTimeList.iterator();

                                    while(var33.hasNext()) {
                                        recDataTime = (RecDataTime)var33.next();
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
                                    var33 = recTimeList.iterator();

                                    while(var33.hasNext()) {
                                        recDataTime = (RecDataTime)var33.next();
                                        bTmp = this.getSpanCheckPkg(strQN, "8", recDataTime, 0);
                                        if (bTmp != null) {
                                            resultList.add(bTmp);
                                        }
                                    }
                                }
                            }
                            break;
                        case 7:
                            String strOverTime = this.getCmdValue(bRecv, "OverTime");
                            String strReCount = this.getCmdValue(bRecv, "ReCount");
                            if (strOverTime != null && strReCount != null) {
                                try {
                                    this.mOverTime = Integer.parseInt(strOverTime);
                                    this.mReCount = Integer.parseInt(strReCount);
                                    this.mUploadCfg.setOver_time(this.mOverTime);
                                    this.mUploadCfg.setRe_count(this.mReCount);
                                    this.mMonitor.getCfgMapper().updateCfgUploadNet(this.mUploadCfg);
                                } catch (Exception var30) {
                                }
                            }
                            break;
                        case 8:
                            strTmp = this.getCmdValue(bRecv, "PolId");
                            bTmp = this.getDevTimePkg(strQN, "8", strTmp);
                            if (bTmp != null) {
                                resultList.add(bTmp);
                            }
                            break;
                        case 9:
                            strPolID = this.getCmdValue(bRecv, "PolId");
                            strTime = this.getCmdValue(bRecv, "SystemTime");
                            if (this.setDevCmd(13, strQN, strPolID, strTime)) {
                                return resultList;
                            }

                            exeRtn = 2;
                            break;
                        case 10:
                            bTmp = this.getSysTimePkg(strQN, "8");
                            if (bTmp != null) {
                                resultList.add(bTmp);
                            }
                            break;
                        case 11:
                            strTmp = this.getCmdValue(bRecv, "SystemTime");
                            Timestamp timestamp = TimeHelper.getDataTime_yyyyMMddHHmmss(strTmp);
                            String strDate = TimeHelper.getSQLDay(timestamp);
                            strTime = TimeHelper.getSQLTime(timestamp);
                            TimeHelper.setDatetime(strDate, strTime);
                            break;
                        case 12:
                            bTmp = this.getPeriodRealPkg(strQN, "8");
                            if (bTmp != null) {
                                resultList.add(bTmp);
                            }
                            break;
                        case 13:
                            strTmp = this.getCmdValue(bRecv, "RtdInterval");
                            if (strTmp != null) {
                                try {
                                    this.mPeriodReal = Integer.parseInt(strTmp) * 60;
                                    this.mUploadCfg.setPeriod_real(this.mPeriodReal);
                                    this.mMonitor.getCfgMapper().updateCfgUploadNet(this.mUploadCfg);
                                } catch (Exception var29) {
                                }
                            }
                            break;
                        case 14:
                            strTmp = this.getCmdValue(bRecv, "NewPW");
                            if (strTmp != null) {
                                try {
                                    resultList.add(this.getEndResponse(strQN, exeRtn + ""));
                                    this.mUploadCfg.setPassword(strTmp);
                                    this.mMonitor.getCfgMapper().updateCfgUploadNet(this.mUploadCfg);
                                    return resultList;
                                } catch (Exception var31) {
                                }
                            }
                            break;
                        case 15:
                            if (this.setRsrvSample(strQN)) {
                                resultList.add(this.getEndResponse(strQN, exeRtn + ""));
                                return resultList;
                            }

                            exeRtn = 2;
                            break;
                        case 16:
                            List<String> strPolIDList = this.getCmdValueList(bRecv, "PolId");
                            List<String> strInfoIDList = this.getCmdValueList(bRecv, "InfoId");
                            if (strPolIDList.size() > 0 && strInfoIDList.size() > 0 && strPolIDList.size() == strInfoIDList.size()) {
                                label465:
                                for(int i = 0; i < strPolIDList.size(); ++i) {
                                    if (((String)strPolIDList.get(i)).equals("w00000")) {
                                        String var35 = ((String)strInfoIDList.get(i)).substring(0, 3);
                                        byte var36 = -1;
                                        switch(var35.hashCode()) {
                                            case 102504:
                                                if (var35.equals("i21")) {
                                                    var36 = 0;
                                                }
                                                break;
                                            case 102505:
                                                if (var35.equals("i22")) {
                                                    var36 = 1;
                                                }
                                        }

                                        switch(var36) {
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
                                                        continue label465;
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
                                        String var25 = ((String)strInfoIDList.get(i)).substring(0, 3);
                                        byte var26 = -1;
                                        switch(var25.hashCode()) {
                                            case 102473:
                                                if (var25.equals("i11")) {
                                                    var26 = 0;
                                                }
                                                break;
                                            case 102474:
                                                if (var25.equals("i12")) {
                                                    var26 = 1;
                                                }
                                                break;
                                            case 102475:
                                                if (var25.equals("i13")) {
                                                    var26 = 2;
                                                }
                                        }
                                        switch(var26) {
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
                                                        continue label465;
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
                                }
                            } else {
                                exeRtn = 3;
                            }
                        case 17:
                        case 36:
                        case 37:
                            break;
                        case 18:
                            bTmp = this.getSysStatePkg(strQN, "8");
                            if (bTmp != null) {
                                resultList.add(bTmp);
                            }
                            break;
                        case 19:
                            bTmp = this.getSysEnvironmentPkg(strQN, "8");
                            if (bTmp != null) {
                                resultList.add(bTmp);
                            }
                            break;
                        case 20:
                            strTmp = this.getCmdValue(bRecv, "RunMode");
                            ScriptWord.getInstance().setSysVar("RunMode", (double)Float.parseFloat(strTmp));
                            break;
                        case 21:
                            try {
                                Runtime.getRuntime().exec("shutdown -r -t 10");
                            } catch (Exception var28) {
                            }
                            break;
                        case 22:
                            this.mMonitor.initScriptFactorList((Integer)null);
                            this.mMonitor.startTest(1, "N");
                            break;
                        case 23:
                            this.mMonitor.stopTest();
                            break;
                        case 24:
                            strTmp = this.getCmdValue(bRecv, "PolId");
                            if (strTmp != null && strTmp.equals("w00000")) {
                                ScriptWord.getInstance().setSysVar("SystemTask", 1.0D);
                                this.mMonitor.initScriptFactorList((Integer)null);
                                this.mMonitor.startTest(11, "N");
                            }
                            break;
                        case 25:
                            this.mMonitor.clearAlarm();
                            break;
                        case 26:
                            this.mMonitor.doSysClean();
                            break;
                        case 27:
                            this.mMonitor.doOutPipeClean();
                            break;
                        case 28:
                            this.mMonitor.doInPipeClean();
                            break;
                        case 29:
                            this.mMonitor.doSandClean();
                            break;
                        case 30:
                            this.mMonitor.doAlgClean();
                            break;
                        case 31:
                            this.mMonitor.doFiveClean();
                            break;
                        case 32:
                            this.mMonitor.doFilterClean();
                            break;
                        case 33:
                            strTmp = this.getCmdValue(bRecv, "SandTime");
                            ScriptWord.getInstance().setSysVar("SandTime", (double)Float.parseFloat(strTmp));
                            break;
                        case 34:
                            strTmp = this.getCmdValue(bRecv, "RunInterval");
                            ScriptWord.getInstance().setSysVar("RunInterval", (double)Float.parseFloat(strTmp));
                            break;
                        case 35:
                            strTmp = this.getCmdValue(bRecv, "PumpState");
                            ScriptWord.getInstance().setSysVar("PumpState", (double)Float.parseFloat(strTmp));
                            break;
                        case 38:
                            strTmp = this.getCmdValue(bRecv, "Time");
                            ScriptWord.getInstance().setSysVar("PumpSampleTime", (double)Float.parseFloat(strTmp));
                            break;
                        case 39:
                            strTmp = this.getCmdValue(bRecv, "Time");
                            ScriptWord.getInstance().setSysVar("SystemSupplyTime", (double)Float.parseFloat(strTmp));
                            break;
                        case 40:
                            strTmp = this.getCmdValue(bRecv, "Time");
                            ScriptWord.getInstance().setSysVar("CleanOutPipeTime", (double)Float.parseFloat(strTmp));
                            break;
                        case 41:
                            strTmp = this.getCmdValue(bRecv, "Time");
                            ScriptWord.getInstance().setSysVar("CleanInPipeTime", (double)Float.parseFloat(strTmp));
                            break;
                        case 42:
                            strTmp = this.getCmdValue(bRecv, "Time");
                            ScriptWord.getInstance().setSysVar("WcleanTime", (double)Float.parseFloat(strTmp));
                            break;
                        case 43:
                            strTmp = this.getCmdValue(bRecv, "Time");
                            ScriptWord.getInstance().setSysVar("MeasureWaitTime", (double)Float.parseFloat(strTmp));
                            break;
                        case 44:
                            strTmp = this.getCmdValue(bRecv, "Time");
                            ScriptWord.getInstance().setSysVar("AddSupplyTime", (double)Float.parseFloat(strTmp));
                            break;
                        case 45:
                            strPolID = this.getCmdValue(bRecv, "PolId");
                            if (this.setDevCmd(2, strQN, strPolID, (String)null)) {
                                return resultList;
                            }

                            exeRtn = 2;
                            break;
                        case 46:
                            strPolID = this.getCmdValue(bRecv, "PolId");
                            strTmp = this.getCmdValue(bRecv, "Volume");
                            if (this.setDevCmd(7, strQN, strPolID, strTmp)) {
                                return resultList;
                            }

                            exeRtn = 2;
                            break;
                        case 47:
                            strPolID = this.getCmdValue(bRecv, "PolId");
                            if (this.setDevCmd(6, strQN, strPolID, (String)null)) {
                                return resultList;
                            }

                            exeRtn = 2;
                            break;
                        case 48:
                            strPolID = this.getCmdValue(bRecv, "PolId");
                            if (this.setDevCmd(3, strQN, strPolID, (String)null)) {
                                return resultList;
                            }

                            exeRtn = 2;
                            break;
                        case 49:
                            strPolID = this.getCmdValue(bRecv, "PolId");
                            if (this.setDevCmd(4, strQN, strPolID, (String)null)) {
                                return resultList;
                            }

                            exeRtn = 2;
                            break;
                        case 50:
                            strPolID = this.getCmdValue(bRecv, "PolId");
                            if (this.setDevCmd(8, strQN, strPolID, (String)null)) {
                                return resultList;
                            }

                            exeRtn = 2;
                            break;
                        case 51:
                            strPolID = this.getCmdValue(bRecv, "PolId");
                            if (this.setDevCmd(9, strQN, strPolID, (String)null)) {
                                return resultList;
                            }

                            exeRtn = 2;
                            break;
                        default:
                            exeRtn = 2;
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
        strSend = strSend + "ST=91;CN=9011;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=8;CP=&&QnRtn=" + strRtn + "&&";
        return this.getSendBytes(strSend);
    }

    private byte[] getEndResponse(String strQN, String strRtn) {
        String strSend = "QN=" + strQN + ";";
        strSend = strSend + "ST=91;CN=9012;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=8;CP=&&ExeRtn=" + strRtn + "&&";
        return this.getSendBytes(strSend);
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

    private byte[] getSendBytes(String sendStr, String exStr) {
        try {
            byte[] bufTmp = sendStr.getBytes();
            int crc16 = Utility.crc16ANSI(bufTmp);
            String strTmp = exStr + String.format("%04d", bufTmp.length) + sendStr + String.format("%04x", crc16).toUpperCase() + "\r\n";
            return strTmp.getBytes();
        } catch (Exception var6) {
            return null;
        }
    }

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

    public byte[] getFlowDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecDataFactor> recDataList = this.mMonitor.getRecMapper().getRecDataFlowListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2019;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                CfgFactor cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if (cfgFactor.isUsed() && cfgFactor.isSwType() && (factorID <= 0 || factorID == cfgFactor.getId())) {
                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecDataFactor)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Rtd=" + ((RecDataFactor)recDataList.get(j)).getDataString() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=" + ((RecDataFactor)recDataList.get(j)).getDataFlag();
                            break;
                        }
                    }
                }
            }

            strSend = strSend + "&&";
            return this.getSendBytes(strSend);
        }
    }
}
