//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.query;

import com.grean.station.dao.CfgMapper;
import com.grean.station.dao.PLCMapper;
import com.grean.station.dao.RecMapper;
import com.grean.station.domain.DO.cfg.CfgCamera;
import com.grean.station.domain.DO.cfg.CfgDev;
import com.grean.station.domain.DO.cfg.CfgDevCmd;
import com.grean.station.domain.DO.cfg.CfgDevParam;
import com.grean.station.domain.DO.cfg.CfgDevQuery;
import com.grean.station.domain.DO.cfg.CfgDevReg;
import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.cfg.CfgFive;
import com.grean.station.domain.DO.cfg.CfgFlow;
import com.grean.station.domain.DO.cfg.CfgNetClient;
import com.grean.station.domain.DO.cfg.CfgSample;
import com.grean.station.domain.DO.cfg.CfgScheduleDay;
import com.grean.station.domain.DO.cfg.CfgScheduleMonth;
import com.grean.station.domain.DO.cfg.CfgScheduleQuality;
import com.grean.station.domain.DO.cfg.CfgScheduleSample;
import com.grean.station.domain.DO.cfg.CfgSerial;
import com.grean.station.domain.DO.cfg.CfgStation;
import com.grean.station.domain.DO.cfg.CfgUnit;
import com.grean.station.domain.DO.cfg.CfgUploadCom;
import com.grean.station.domain.DO.cfg.CfgUploadNet;
import com.grean.station.domain.DO.cfg.CfgUser;
import com.grean.station.domain.DO.plc.DefWord;
import com.grean.station.domain.DO.plc.StatusWord;
import com.grean.station.domain.DO.plc.VarWord;
import com.grean.station.domain.DO.rec.RecSample;
import com.grean.station.domain.request.DevInterval;
import com.grean.station.domain.request.DevModel;
import com.grean.station.domain.request.DeviceParam;
import com.grean.station.domain.request.EncryptionCode;
import com.grean.station.domain.request.FactorInfo;
import com.grean.station.domain.request.LoginTime;
import com.grean.station.domain.request.PlcAIInfo;
import com.grean.station.domain.request.PlcInfo;
import com.grean.station.domain.request.PlcQInfo;
import com.grean.station.domain.request.ProtocolCmd;
import com.grean.station.domain.request.ProtocolInfo;
import com.grean.station.domain.request.ProtocolQuery;
import com.grean.station.domain.request.RcvrType;
import com.grean.station.domain.request.ReqDevFactor;
import com.grean.station.domain.request.ReqFiveCheck;
import com.grean.station.domain.request.ReqFiveInfo;
import com.grean.station.domain.request.ReqScheduleQuality;
import com.grean.station.domain.request.ReqStdCheck;
import com.grean.station.domain.request.ReqUserModbus;
import com.grean.station.domain.request.RunningInfo;
import com.grean.station.domain.request.SampleControl;
import com.grean.station.domain.request.SampleInfo;
import com.grean.station.domain.request.SampleState;
import com.grean.station.domain.request.SampleStatus;
import com.grean.station.domain.request.ScheduleDay;
import com.grean.station.domain.request.ScheduleFive;
import com.grean.station.domain.request.ScheduleMonth;
import com.grean.station.domain.request.ScheduleMonthData;
import com.grean.station.domain.request.ScheduleSample;
import com.grean.station.domain.request.SetComInfo;
import com.grean.station.domain.request.SetDeviceParam;
import com.grean.station.domain.request.SetDriftVal;
import com.grean.station.domain.request.SetErrorVal;
import com.grean.station.domain.request.SetFactorInfo;
import com.grean.station.domain.request.SetInstrumentInfo;
import com.grean.station.domain.request.SetNetInfo;
import com.grean.station.domain.request.SetRcvrVal;
import com.grean.station.domain.request.SetSpanVal;
import com.grean.station.domain.request.SetStdVal;
import com.grean.station.domain.request.SetUserInfo;
import com.grean.station.domain.request.TitleValue;
import com.grean.station.domain.request.UploadInfo;
import com.grean.station.domain.request.ValueLabelFormat;
import com.grean.station.domain.response.TokenUser;
import com.grean.station.service.MonitorService;
import com.grean.station.service.UserService;
import com.grean.station.service.dev.DevService;
import com.grean.station.service.dev.sample.SampleService;
import com.grean.station.service.script.ScriptService;
import com.grean.station.service.script.ScriptWord;
import com.grean.station.utils.CrcUtil;
import com.grean.station.utils.TimeHelper;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SettingService {
    @Autowired
    CfgMapper cfgMapper;
    @Autowired
    RecMapper recMapper;
    @Autowired
    PLCMapper plcMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MonitorService monitorService;
    @Autowired
    UserService userService;
    @Autowired
    ScriptService scriptService;
    List<ReqStdCheck> stdCheckList = new ArrayList();

    public SettingService() {
    }

    public CfgStation getStationInfo() {
        CfgStation cfgStation = this.cfgMapper.getCfgStation();
        return cfgStation;
    }

    public boolean setStationInfo(HttpServletRequest request, CfgStation cfgStation) {
        cfgStation.setId(1);
        this.cfgMapper.updateCfgStation(cfgStation);
        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 系统设置");
        return true;
    }

    public List<FactorInfo> getPlanFactor() {
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        List<FactorInfo> planFactorList = new ArrayList();
        Iterator var3 = cfgFactorList.iterator();

        while(true) {
            CfgFactor cfgFactor;
            do {
                do {
                    if (!var3.hasNext()) {
                        return planFactorList;
                    }

                    cfgFactor = (CfgFactor)var3.next();
                } while(!cfgFactor.isUsed());
            } while(!cfgFactor.isParmType() && !cfgFactor.isFiveType());

            FactorInfo factorInfo = new FactorInfo(cfgFactor.getName(), cfgFactor.getUnit());
            planFactorList.add(factorInfo);
        }
    }

    public List<ScheduleDay> getDayPlan() {
        List<ScheduleDay> dayPlanList = new ArrayList();
        List<CfgScheduleDay> cfgScheduleDayList = this.cfgMapper.getCfgScheduleDayList();

        for(int i = 0; i < cfgScheduleDayList.size(); ++i) {
            ScheduleDay scheduleDay = new ScheduleDay();
            scheduleDay.setHour(i);
            scheduleDay.setTaskCode(((CfgScheduleDay)cfgScheduleDayList.get(i)).getTask_code());
            scheduleDay.setTaskName(((CfgScheduleDay)cfgScheduleDayList.get(i)).getTask_name());
            scheduleDay.setStringFactorList(((CfgScheduleDay)cfgScheduleDayList.get(i)).getFactor_list());
            scheduleDay.setStringFactorList2(((CfgScheduleDay)cfgScheduleDayList.get(i)).getFactor_list2());
            dayPlanList.add(scheduleDay);
        }

        return dayPlanList;
    }

    public boolean setDayPlan(HttpServletRequest request, List<ScheduleDay> scheduleDayList) {
        for(int i = 0; i < scheduleDayList.size(); ++i) {
            CfgScheduleDay cfgScheduleDay = new CfgScheduleDay();
            cfgScheduleDay.setId(i + 1);
            cfgScheduleDay.setHour(i);
            cfgScheduleDay.setMin(0);
            if (((ScheduleDay)scheduleDayList.get(i)).getTaskName() == null) {
                cfgScheduleDay.setTask_code((Integer)null);
                cfgScheduleDay.setTask_name((String)null);
                cfgScheduleDay.setFactor_list((String)null);
                cfgScheduleDay.setFactor_list2((String)null);
            } else {
                cfgScheduleDay.setTask_code(((ScheduleDay)scheduleDayList.get(i)).getTaskCode());
                cfgScheduleDay.setTask_name(((ScheduleDay)scheduleDayList.get(i)).getTaskName());
                String strTmp = "";

                for(int j = 0; j < ((ScheduleDay)scheduleDayList.get(i)).getFactorList().size(); ++j) {
                    strTmp = strTmp + (String)((ScheduleDay)scheduleDayList.get(i)).getFactorList().get(j) + ";";
                }

                cfgScheduleDay.setFactor_list(strTmp);
                String strTmp2 = "";

                for(int j = 0; j < ((ScheduleDay)scheduleDayList.get(i)).getFactorList2().size(); ++j) {
                    strTmp2 = strTmp2 + (String)((ScheduleDay)scheduleDayList.get(i)).getFactorList2().get(j) + ";";
                }

                cfgScheduleDay.setFactor_list2(strTmp2);
            }

            this.cfgMapper.updateCfgScheduleDay(cfgScheduleDay);
        }

        this.monitorService.refreshSchedule();
        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 计划任务");
        return true;
    }

    public List<CfgScheduleMonth> getMonthPlan() {
        List<CfgScheduleMonth> cfgScheduleMonthList = this.cfgMapper.getCfgScheduleMonthList();
        return cfgScheduleMonthList;
    }

    public boolean setMonthPlan(List<ScheduleMonth> scheduleMonthList) {
        this.cfgMapper.truncateCfgScheduleMonth();
        Iterator var2 = scheduleMonthList.iterator();

        while(true) {
            ScheduleMonth scheduleMonth;
            do {
                if (!var2.hasNext()) {
                    this.monitorService.refreshSchedule();
                    return true;
                }

                scheduleMonth = (ScheduleMonth)var2.next();
            } while(scheduleMonth.getData() == null);

            Iterator var4 = scheduleMonth.getData().iterator();

            while(var4.hasNext()) {
                ScheduleMonthData scheduleMonthData = (ScheduleMonthData)var4.next();
                CfgScheduleMonth cfgScheduleMonth = new CfgScheduleMonth();
                cfgScheduleMonth.setDay(scheduleMonth.getId());
                cfgScheduleMonth.setHour(scheduleMonthData.getHour());
                cfgScheduleMonth.setMin(0);
                cfgScheduleMonth.setTask_name(scheduleMonthData.getTask());
                cfgScheduleMonth.setTask_code(Utility.getTaskID(scheduleMonthData.getTask()) + "");
                this.cfgMapper.insertCfgScheduleMonth(cfgScheduleMonth);
            }
        }
    }

    public List<CfgScheduleSample> getSamplePlan() {
        List<CfgScheduleSample> cfgScheduleSampleList = this.monitorService.cfgScheduleSampleList;
        return cfgScheduleSampleList;
    }

    public boolean setSamplePlan(HttpServletRequest request, List<ScheduleSample> scheduleSampleList) {
        List<CfgScheduleSample> cfgScheduleSampleList = this.monitorService.cfgScheduleSampleList;

        for(int i = 0; i < scheduleSampleList.size(); ++i) {
            if (((CfgScheduleSample)cfgScheduleSampleList.get(i)).isRun() != ((ScheduleSample)scheduleSampleList.get(i)).isChecked()) {
                ((CfgScheduleSample)cfgScheduleSampleList.get(i)).setRun(((ScheduleSample)scheduleSampleList.get(i)).isChecked());
                this.cfgMapper.updateCfgScheduleSample((CfgScheduleSample)cfgScheduleSampleList.get(i));
            }
        }

        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 留样设置");
        return true;
    }

    public SampleInfo getSample() {
        CfgSample cfgSample = this.cfgMapper.getCfgSample();
        SampleInfo sampleInfo = new SampleInfo();
        sampleInfo.setBottles(cfgSample.getBottles());
        sampleInfo.setVolume(cfgSample.getVolume());
        sampleInfo.setMode(cfgSample.getMode());
        sampleInfo.setBottle_id(cfgSample.getBottle_id());

        for(int i = 0; i < cfgSample.getBottles(); ++i) {
            SampleStatus sampleStatus = new SampleStatus();
            sampleStatus.setId(i + 1);
            sampleStatus.setName(i + 1 + "号瓶");
            sampleStatus.setStatus(false);
            if (this.monitorService.sampleService != null && this.monitorService.sampleService.getVaseState()[i] > 0) {
                sampleStatus.setStatus(true);
            }

            sampleInfo.getSampleStatusList().add(sampleStatus);
        }

        return sampleInfo;
    }

    public List<String> getSampleModes() {
        List<String> sampleModes = new ArrayList();
        sampleModes.add("定时留样");
        sampleModes.add("超标留样");
        sampleModes.add("同步留样");
        sampleModes.add("超标留样二");
        return sampleModes;
    }

    public boolean setSample(CfgSample cfgSample) {
        this.cfgMapper.updateCfgSample(cfgSample);
        if (this.monitorService.sampleService != null) {
            this.monitorService.sampleService.setCfgSample(cfgSample);
        }

        this.monitorService.cfgSample = cfgSample;
        return true;
    }

    public boolean doSampleQuantity() {
        if (this.monitorService.sampleService != null) {
            try {
                SampleService sampleService = this.monitorService.sampleService;
                sampleService.doPrepare();
                Thread.sleep(1000L);
                if (sampleService.doSample((String)null)) {
                    this.monitorService.saveRecLogSample(sampleService.getSampleVase() + "", sampleService.getSampleVolume() + "", "手动留样", "留样成功");
                    this.monitorService.saveSampleRecord();
                    return true;
                } else {
                    this.monitorService.saveRecLogSample(sampleService.getSampleVase() + "", sampleService.getSampleVolume() + "", "手动留样", "留样失败");
                    return false;
                }
            } catch (Exception var2) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean doSamplePlc() {
        boolean bResult = this.scriptService.doCmd(1, "采样器");
        (new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000L);
                    SettingService.this.scriptService.doCmd(0, "采样器");
                } catch (Exception var2) {
                }

            }
        })).start();
        return bResult;
    }

    public boolean doSampleEmpty() {
        if (this.monitorService.sampleService != null) {
            try {
                SampleService sampleService = this.monitorService.sampleService;
                if (sampleService.doEmpty((String)null)) {
                    this.monitorService.saveRecLogSample(sampleService.getSampleVase() + "", "-", "手动留样", "排空成功");
                    return true;
                } else {
                    this.monitorService.saveRecLogSample(sampleService.getSampleVase() + "", "-", "手动留样", "排空失败");
                    return false;
                }
            } catch (Exception var2) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean doControlSample(SampleControl sampleControl) {
        if (this.monitorService.sampleService != null) {
            try {
                SampleService sampleService = this.monitorService.sampleService;
                if (sampleService.doSample(sampleControl.getId() + "")) {
                    this.monitorService.saveRecLogSample(sampleControl.getId() + "", "-", "手动留样", "留样成功");
                    return true;
                } else {
                    this.monitorService.saveRecLogSample(sampleControl.getId() + "", "-", "手动留样", "留样失败");
                    return false;
                }
            } catch (Exception var3) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean doControlEmpty(SampleControl sampleControl) {
        if (this.monitorService.sampleService != null) {
            try {
                SampleService sampleService = this.monitorService.sampleService;
                if (sampleService.doEmpty(sampleControl.getId() + "")) {
                    this.monitorService.saveRecLogSample(sampleControl.getId() + "", "-", "手动留样", "排空成功");
                    return true;
                } else {
                    this.monitorService.saveRecLogSample(sampleControl.getId() + "", "-", "手动留样", "排空失败");
                    return false;
                }
            } catch (Exception var3) {
                return false;
            }
        } else {
            return false;
        }
    }

    public List<SampleState> doSampleState() {
        List<SampleState> sampleStateList = new ArrayList();
        List<RecSample> recSampleList = this.recMapper.getRecSample();
        Iterator var3 = recSampleList.iterator();

        while(var3.hasNext()) {
            RecSample recSample = (RecSample)var3.next();
            SampleState sampleState = new SampleState();
            sampleState.setBottleID(recSample.getId());
            sampleState.setSampleVolume(recSample.getVolume());
            sampleState.setSampleUnit(recSample.getUnit());
            if (recSample.getRec_time() != null) {
                sampleState.setSampleTime(TimeHelper.getOperationPostTime_Str(recSample.getRec_time()));
            } else {
                sampleState.setSampleTime((String)null);
            }

            sampleStateList.add(sampleState);
            if (sampleStateList.size() == this.monitorService.sampleService.getVaseSize()) {
                break;
            }
        }

        return sampleStateList;
    }

    public boolean doSampleReset() {
        if (this.monitorService.sampleService != null) {
            try {
                this.monitorService.resetSampleRecord();
                this.monitorService.sampleService.setSampleVase(0);
                return this.monitorService.sampleService.doReset();
            } catch (Exception var2) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean doScriptJump() {
        int iRunMode = (int)ScriptWord.getInstance().getSysVar("RunMode");
        if (iRunMode > 0 && this.monitorService.getScriptService().isOnScript()) {
            this.monitorService.getScriptService().setJumpScript(true);
            return true;
        } else {
            return false;
        }
    }

    public RunningInfo getRunning() {
        RunningInfo runningInfo = new RunningInfo();
        List<VarWord> varWordList = this.plcMapper.getPLCVarWordList();
        Iterator var3 = varWordList.iterator();

        while(var3.hasNext()) {
            VarWord varWord = (VarWord)var3.next();
            String var5 = varWord.getEnName();
            byte var6 = -1;
            switch(var5.hashCode()) {
                case -2126721985:
                    if (var5.equals("PumpSampleTime")) {
                        var6 = 4;
                    }
                    break;
                case -1963745881:
                    if (var5.equals("RcvrType")) {
                        var6 = 9;
                    }
                    break;
                case -1211637711:
                    if (var5.equals("PressLow")) {
                        var6 = 7;
                    }
                    break;
                case -1080507730:
                    if (var5.equals("RunMode")) {
                        var6 = 0;
                    }
                    break;
                case 187405779:
                    if (var5.equals("TurbHigh")) {
                        var6 = 10;
                    }
                    break;
                case 423233257:
                    if (var5.equals("PumpState")) {
                        var6 = 1;
                    }
                    break;
                case 426379119:
                    if (var5.equals("PumpWater")) {
                        var6 = 8;
                    }
                    break;
                case 1093811301:
                    if (var5.equals("PressHigh")) {
                        var6 = 6;
                    }
                    break;
                case 1676255714:
                    if (var5.equals("PumpType")) {
                        var6 = 2;
                    }
                    break;
                case 1676335545:
                    if (var5.equals("PumpWork")) {
                        var6 = 3;
                    }
                    break;
                case 1992303899:
                    if (var5.equals("PumpWarnTime")) {
                        var6 = 5;
                    }
            }

            switch(var6) {
                case 0:
                    runningInfo.setCurrent_station_mode((int)varWord.getCurValue());
                    break;
                case 1:
                    runningInfo.setCurrent_pump_work_mode((int)varWord.getCurValue());
                    break;
                case 2:
                    runningInfo.setCurrent_pump_type((int)varWord.getCurValue());
                    break;
                case 3:
                    runningInfo.setCurrent_pump((int)varWord.getCurValue());
                    break;
                case 4:
                    runningInfo.setPump_sample_time(varWord.getCurValue());
                    break;
                case 5:
                    runningInfo.setPump_alarm_time(varWord.getCurValue());
                    break;
                case 6:
                    runningInfo.setPresure_limit_high(varWord.getCurValue());
                    break;
                case 7:
                    runningInfo.setPresure_limit_low(varWord.getCurValue());
                    break;
                case 8:
                    if (varWord.getCurValue() > 0.0D) {
                        runningInfo.setPump_water(true);
                    } else {
                        runningInfo.setPump_water(false);
                    }
                    break;
                case 9:
                    runningInfo.setRcvr_type((int)varWord.getCurValue());
                    break;
                case 10:
                    runningInfo.setTurb_high((double)((int)varWord.getCurValue()));
                    break;
                default:
                    if (varWord.getType() == 8) {
                        TitleValue titleValue = new TitleValue();
                        titleValue.setTitle(varWord.getCnName());
                        titleValue.setValue(varWord.getCurValue());
                        runningInfo.getItemList().add(titleValue);
                    }
            }
        }

        return runningInfo;
    }

    public boolean setRunning(HttpServletRequest request, RunningInfo runningInfo) {
        List<VarWord> varWordList = this.plcMapper.getPLCVarWordList();
        Iterator var5 = varWordList.iterator();

        while(var5.hasNext()) {
            VarWord varWord = (VarWord)var5.next();
            boolean bSave = false;
            String var7 = varWord.getEnName();
            byte var8 = -1;
            switch(var7.hashCode()) {
                case -2126721985:
                    if (var7.equals("PumpSampleTime")) {
                        var8 = 4;
                    }
                    break;
                case -1963745881:
                    if (var7.equals("RcvrType")) {
                        var8 = 9;
                    }
                    break;
                case -1211637711:
                    if (var7.equals("PressLow")) {
                        var8 = 7;
                    }
                    break;
                case -1080507730:
                    if (var7.equals("RunMode")) {
                        var8 = 0;
                    }
                    break;
                case 187405779:
                    if (var7.equals("TurbHigh")) {
                        var8 = 10;
                    }
                    break;
                case 423233257:
                    if (var7.equals("PumpState")) {
                        var8 = 1;
                    }
                    break;
                case 426379119:
                    if (var7.equals("PumpWater")) {
                        var8 = 8;
                    }
                    break;
                case 1093811301:
                    if (var7.equals("PressHigh")) {
                        var8 = 6;
                    }
                    break;
                case 1676255714:
                    if (var7.equals("PumpType")) {
                        var8 = 2;
                    }
                    break;
                case 1676335545:
                    if (var7.equals("PumpWork")) {
                        var8 = 3;
                    }
                    break;
                case 1992303899:
                    if (var7.equals("PumpWarnTime")) {
                        var8 = 5;
                    }
            }

            switch(var8) {
                case 0:
                    if (varWord.getCurValue() != (double)runningInfo.getCurrent_station_mode()) {
                        bSave = true;
                        varWord.setCurValue((double)runningInfo.getCurrent_station_mode());
                    }
                    break;
                case 1:
                    if (varWord.getCurValue() != (double)runningInfo.getCurrent_pump_work_mode()) {
                        bSave = true;
                        varWord.setCurValue((double)runningInfo.getCurrent_pump_work_mode());
                    }
                    break;
                case 2:
                    if (varWord.getCurValue() != (double)runningInfo.getCurrent_pump_type()) {
                        bSave = true;
                        varWord.setCurValue((double)runningInfo.getCurrent_pump_type());
                    }
                    break;
                case 3:
                    if (varWord.getCurValue() != (double)runningInfo.getCurrent_pump()) {
                        bSave = true;
                        varWord.setCurValue((double)runningInfo.getCurrent_pump());
                    }
                    break;
                case 4:
                    if (varWord.getCurValue() != runningInfo.getPump_sample_time()) {
                        bSave = true;
                        varWord.setCurValue(runningInfo.getPump_sample_time());
                    }
                    break;
                case 5:
                    if (varWord.getCurValue() != runningInfo.getPump_alarm_time()) {
                        bSave = true;
                        varWord.setCurValue(runningInfo.getPump_alarm_time());
                    }
                    break;
                case 6:
                    if (varWord.getCurValue() != runningInfo.getPresure_limit_high()) {
                        bSave = true;
                        varWord.setCurValue(runningInfo.getPresure_limit_high());
                    }
                    break;
                case 7:
                    if (varWord.getCurValue() != runningInfo.getPresure_limit_low()) {
                        bSave = true;
                        varWord.setCurValue(runningInfo.getPresure_limit_low());
                    }
                    break;
                case 8:
                    if (varWord.getCurValue() > 0.0D && !runningInfo.isPump_water()) {
                        bSave = true;
                        varWord.setCurValue(0.0D);
                    }

                    if (varWord.getCurValue() == 0.0D && runningInfo.isPump_water()) {
                        bSave = true;
                        varWord.setCurValue(1.0D);
                    }
                    break;
                case 9:
                    if (varWord.getCurValue() != (double)runningInfo.getRcvr_type()) {
                        bSave = true;
                        varWord.setCurValue((double)runningInfo.getRcvr_type());
                    }
                    break;
                case 10:
                    if (varWord.getCurValue() != runningInfo.getTurb_high()) {
                        bSave = true;
                        varWord.setCurValue(runningInfo.getTurb_high());
                    }
                    break;
                default:
                    if (varWord.getType() == 8) {
                        Iterator var9 = runningInfo.getItemList().iterator();

                        while(var9.hasNext()) {
                            TitleValue titleValue = (TitleValue)var9.next();
                            if (varWord.getCnName().equals(titleValue.getTitle())) {
                                if (varWord.getCurValue() != titleValue.getValue()) {
                                    bSave = true;
                                    varWord.setCurValue(titleValue.getValue());
                                    varWord.setDefValue(titleValue.getValue());
                                }
                                break;
                            }
                        }
                    }
            }

            if (bSave) {
                this.plcMapper.updatePLCVarWord(varWord);
            }
        }

        this.scriptService.refreshVarList();
        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 运行设置");
        return true;
    }

    public List<String> getUploadProtocols() {
        List<String> uploadProtocols = new ArrayList();
        uploadProtocols.add("国家站协议");
        uploadProtocols.add("水资源协议");
        uploadProtocols.add("水利协议");
        uploadProtocols.add("重庆比测协议");
        uploadProtocols.add("浙江省地表水协议");
        uploadProtocols.add("浙江省污染源协议");
        uploadProtocols.add("绿洁平台浙江省协议");
        uploadProtocols.add("广东预警平台协议");
        uploadProtocols.add("上海水文协议");
        uploadProtocols.add("开化环保局协议");
        uploadProtocols.add("江苏省FTP协议");
        uploadProtocols.add("盐城市协议");
        uploadProtocols.add("山东滨州协议");
        uploadProtocols.add("和达上传协议");
        uploadProtocols.add("浙江省水源地协议");
        uploadProtocols.add("福建省水资源协议");
        uploadProtocols.add("湖北省水资源协议");
        uploadProtocols.add("国家站协议(测试)");
        uploadProtocols.add("陕西212协议");
        uploadProtocols.add("国家站协议(小时)");
        uploadProtocols.add("盐城大丰协义");
        uploadProtocols.add("南水北调水资源协议");
        uploadProtocols.add("武汉外沙湖协议");
        uploadProtocols.add("Modbus协议");
        uploadProtocols.add("国家站协议2");
        uploadProtocols.add("国家站UDP协议");
        uploadProtocols.add("Hj212-2017协议");
        uploadProtocols.add("青岛FTP协议");
        uploadProtocols.add("青岛三希协议");
        uploadProtocols.add("国家站加密协议");
        uploadProtocols.add("宁波UCMA加密协议");
        uploadProtocols.add("合肥市平台协议");
        uploadProtocols.add("丹江口协议");
        uploadProtocols.add("西安FTP协议");
        uploadProtocols.add("聚光智能化数据传输协议");
        uploadProtocols.add("浙江省水源地协议不传五参数");
        uploadProtocols.add("南水北调协议");
        return uploadProtocols;
    }

    public List<UploadInfo> getNetworkUpload() {
        List<UploadInfo> uploadInfoList = new ArrayList();
        List<CfgUploadNet> cfgUploadNetList = this.cfgMapper.getCfgUploadNetList();
        int protocalType;
        Iterator var4 = cfgUploadNetList.iterator();

        while(var4.hasNext()) {
            CfgUploadNet cfgUploadNet = (CfgUploadNet)var4.next();
            UploadInfo uploadInfo = new UploadInfo();
            protocalType = cfgUploadNet.getMapKey(cfgUploadNet.getProtocol());
            uploadInfo.setUsed(cfgUploadNet.isUsed());
            uploadInfo.setName(cfgUploadNet.getName());
            uploadInfo.setProtocal(protocalType);
            uploadInfo.setServer(cfgUploadNet.getIp());
            uploadInfo.setPort(cfgUploadNet.getPort());
            uploadInfo.setMn(cfgUploadNet.getMn());
            uploadInfo.setPassword(cfgUploadNet.getPassword());
            uploadInfo.setHeartbeat_rate(cfgUploadNet.getPeriod_heart());
            uploadInfo.setRealtime_rate(cfgUploadNet.getPeriod_real());
            uploadInfo.setSysstate_rate(cfgUploadNet.getPeriod_sys_state());
            uploadInfo.setDevstate_rate(cfgUploadNet.getPeriod_dev_state());
            uploadInfo.setSyslog_rate(cfgUploadNet.getPeriod_sys_log());
            uploadInfo.setDevlog_rate(cfgUploadNet.getPeriod_dev_log());
            uploadInfo.setOvertime(cfgUploadNet.getOver_time());
            uploadInfo.setRecount(cfgUploadNet.getRe_count());
            uploadInfoList.add(uploadInfo);
        }

        return uploadInfoList;
    }

    public boolean setNetworkUpload(HttpServletRequest request, List<UploadInfo> uploadInfoList) {
        List<CfgUploadNet> cfgUploadNetList = this.cfgMapper.getCfgUploadNetList();

        for(int i = 0; i < cfgUploadNetList.size() && i < uploadInfoList.size(); ++i) {
            ((CfgUploadNet)cfgUploadNetList.get(i)).setUsed(((UploadInfo)uploadInfoList.get(i)).isUsed());
            ((CfgUploadNet)cfgUploadNetList.get(i)).setName(((UploadInfo)uploadInfoList.get(i)).getName());
            ((CfgUploadNet)cfgUploadNetList.get(i)).setIp(((UploadInfo)uploadInfoList.get(i)).getServer());
            ((CfgUploadNet)cfgUploadNetList.get(i)).setPort(((UploadInfo)uploadInfoList.get(i)).getPort());
            ((CfgUploadNet)cfgUploadNetList.get(i)).setMn(((UploadInfo)uploadInfoList.get(i)).getMn());
            ((CfgUploadNet)cfgUploadNetList.get(i)).setPassword(((UploadInfo)uploadInfoList.get(i)).getPassword());
            ((CfgUploadNet)cfgUploadNetList.get(i)).setPeriod_heart(((UploadInfo)uploadInfoList.get(i)).getHeartbeat_rate());
            ((CfgUploadNet)cfgUploadNetList.get(i)).setPeriod_real(((UploadInfo)uploadInfoList.get(i)).getRealtime_rate());
            ((CfgUploadNet)cfgUploadNetList.get(i)).setPeriod_sys_state(((UploadInfo)uploadInfoList.get(i)).getSysstate_rate());
            ((CfgUploadNet)cfgUploadNetList.get(i)).setPeriod_dev_state(((UploadInfo)uploadInfoList.get(i)).getDevstate_rate());
            ((CfgUploadNet)cfgUploadNetList.get(i)).setPeriod_sys_log(((UploadInfo)uploadInfoList.get(i)).getSyslog_rate());
            ((CfgUploadNet)cfgUploadNetList.get(i)).setPeriod_dev_log(((UploadInfo)uploadInfoList.get(i)).getDevlog_rate());
            ((CfgUploadNet)cfgUploadNetList.get(i)).setOver_time(((UploadInfo)uploadInfoList.get(i)).getOvertime());
            ((CfgUploadNet)cfgUploadNetList.get(i)).setRe_count(((UploadInfo)uploadInfoList.get(i)).getRecount());
            ((CfgUploadNet)cfgUploadNetList.get(i)).setProtocol(((CfgUploadNet)cfgUploadNetList.get(i)).getMapValue(((UploadInfo)uploadInfoList.get(i)).getProtocal()));
            this.cfgMapper.updateCfgUploadNet((CfgUploadNet)cfgUploadNetList.get(i));
        }

        this.monitorService.refreshUpload();
        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 上传设置");
        return true;
    }

    public List<UploadInfo> getComUpload() {
        List<UploadInfo> uploadInfoList = new ArrayList();
        List<CfgUploadCom> cfgUploadComList = this.cfgMapper.getCfgUploadComList();
        int protocalType;
        Iterator var4 = cfgUploadComList.iterator();

        while(var4.hasNext()) {
            CfgUploadCom cfgUploadCom = (CfgUploadCom)var4.next();
            UploadInfo uploadInfo = new UploadInfo();
            protocalType = cfgUploadCom.getMapKey(cfgUploadCom.getProtocol());
            uploadInfo.setUsed(cfgUploadCom.isUsed());
            uploadInfo.setName(cfgUploadCom.getName());
            uploadInfo.setProtocal(protocalType);
            uploadInfo.setPort(cfgUploadCom.getPort());
            uploadInfo.setMn(cfgUploadCom.getMn());
            uploadInfo.setPassword(cfgUploadCom.getPassword());
            uploadInfo.setHeartbeat_rate(cfgUploadCom.getPeriod_heart());
            uploadInfo.setRealtime_rate(cfgUploadCom.getPeriod_real());
            uploadInfo.setSysstate_rate(cfgUploadCom.getPeriod_sys_state());
            uploadInfo.setDevstate_rate(cfgUploadCom.getPeriod_dev_state());
            uploadInfo.setSyslog_rate(cfgUploadCom.getPeriod_sys_log());
            uploadInfo.setDevlog_rate(cfgUploadCom.getPeriod_dev_log());
            uploadInfo.setOvertime(cfgUploadCom.getOver_time());
            uploadInfo.setRecount(cfgUploadCom.getRe_count());
            uploadInfoList.add(uploadInfo);
        }

        return uploadInfoList;
    }

    public boolean setComUpload(HttpServletRequest request, List<UploadInfo> uploadInfoList) {
        List<CfgUploadCom> cfgUploadComList = this.cfgMapper.getCfgUploadComList();

        for(int i = 0; i < cfgUploadComList.size() && i < uploadInfoList.size(); ++i) {
            ((CfgUploadCom)cfgUploadComList.get(i)).setUsed(((UploadInfo)uploadInfoList.get(i)).isUsed());
            ((CfgUploadCom)cfgUploadComList.get(i)).setName(((UploadInfo)uploadInfoList.get(i)).getName());
            ((CfgUploadCom)cfgUploadComList.get(i)).setPort(((UploadInfo)uploadInfoList.get(i)).getPort());
            ((CfgUploadCom)cfgUploadComList.get(i)).setMn(((UploadInfo)uploadInfoList.get(i)).getMn());
            ((CfgUploadCom)cfgUploadComList.get(i)).setPassword(((UploadInfo)uploadInfoList.get(i)).getPassword());
            ((CfgUploadCom)cfgUploadComList.get(i)).setPeriod_heart(((UploadInfo)uploadInfoList.get(i)).getHeartbeat_rate());
            ((CfgUploadCom)cfgUploadComList.get(i)).setPeriod_real(((UploadInfo)uploadInfoList.get(i)).getRealtime_rate());
            ((CfgUploadCom)cfgUploadComList.get(i)).setPeriod_sys_state(((UploadInfo)uploadInfoList.get(i)).getSysstate_rate());
            ((CfgUploadCom)cfgUploadComList.get(i)).setPeriod_dev_state(((UploadInfo)uploadInfoList.get(i)).getDevstate_rate());
            ((CfgUploadCom)cfgUploadComList.get(i)).setPeriod_sys_log(((UploadInfo)uploadInfoList.get(i)).getSyslog_rate());
            ((CfgUploadCom)cfgUploadComList.get(i)).setPeriod_dev_log(((UploadInfo)uploadInfoList.get(i)).getDevlog_rate());
            ((CfgUploadCom)cfgUploadComList.get(i)).setOver_time(((UploadInfo)uploadInfoList.get(i)).getOvertime());
            ((CfgUploadCom)cfgUploadComList.get(i)).setRe_count(((UploadInfo)uploadInfoList.get(i)).getRecount());
            ((CfgUploadCom)cfgUploadComList.get(i)).setProtocol(((CfgUploadCom)cfgUploadComList.get(i)).getMapValue(((UploadInfo)uploadInfoList.get(i)).getProtocal()));
            this.cfgMapper.updateCfgUploadCom((CfgUploadCom)cfgUploadComList.get(i));
        }

        this.monitorService.refreshUpload();
        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 上传设置");
        return true;
    }

    public List<SetStdVal> getStandardInfo() {
        List<SetStdVal> setStdValList = new ArrayList();
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        int iKey = 0;
        Iterator var4 = cfgFactorList.iterator();

        while(true) {
            CfgFactor cfgFactor;
            do {
                do {
                    if (!var4.hasNext()) {
                        return setStdValList;
                    }

                    cfgFactor = (CfgFactor)var4.next();
                } while(!cfgFactor.isUsed());
            } while(!cfgFactor.isFiveType() && !cfgFactor.isParmType());

            SetStdVal setStdVal = new SetStdVal();
            setStdVal.setKey(iKey);
            setStdVal.setFactor(cfgFactor.getName());
            setStdVal.setStdZero(cfgFactor.getZeroStdVal());
            setStdVal.setStdSpan(cfgFactor.getSpanStdVal());
            setStdVal.setStdStd(cfgFactor.getStdStdVal());
            setStdVal.setStdBlnk(cfgFactor.getBlnkStdVal());
            setStdValList.add(setStdVal);
            ++iKey;
        }
    }

    public boolean setStandardInfo(HttpServletRequest request, List<SetStdVal> setStdValList) {
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        Iterator var4 = setStdValList.iterator();

        while(true) {
            while(var4.hasNext()) {
                SetStdVal setStdVal = (SetStdVal)var4.next();
                Iterator var6 = cfgFactorList.iterator();

                while(var6.hasNext()) {
                    CfgFactor cfgFactor = (CfgFactor)var6.next();
                    if (cfgFactor.getName().equals(setStdVal.getFactor())) {
                        cfgFactor.setZeroStdVal(setStdVal.getStdZero());
                        cfgFactor.setSpanStdVal(setStdVal.getStdSpan());
                        cfgFactor.setStdStdVal(setStdVal.getStdStd());
                        cfgFactor.setBlnkStdVal(setStdVal.getStdBlnk());
                        this.cfgMapper.updateCfgFactorStdVal(cfgFactor);
                        this.cfgMapper.updateAllCfgFactorStdVal(cfgFactor);
                        break;
                    }
                }
            }

            this.monitorService.refreshQuality();
            TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
            this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 标液浓度设置");
            return true;
        }
    }

    public List<SetRcvrVal> getRecoveryInfo() {
        List<SetRcvrVal> setRcvrValList = new ArrayList();
        List<CfgFactor> cfgFactorList = this.monitorService.getCfgFactorList();
        int iKey = 0;
        Iterator var4 = cfgFactorList.iterator();

        while(var4.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor)var4.next();
            if (cfgFactor.isUsed() && cfgFactor.isParmType()) {
                SetRcvrVal setRcvrVal = new SetRcvrVal();
                setRcvrVal.setKey(iKey);
                setRcvrVal.setFactor(cfgFactor.getName());
                setRcvrVal.setMotherVal(cfgFactor.getRcvrMotherVal());
                setRcvrVal.setMotherVol(cfgFactor.getRcvrMotherVol());
                setRcvrVal.setConcVal(cfgFactor.getRcvrDestVal());
                setRcvrVal.setCupVol(cfgFactor.getRcvrCupVol());
                setRcvrVal.setRateMin(cfgFactor.getRcvrRateMin());
                setRcvrVal.setRateMax(cfgFactor.getRcvrRateMax());
                setRcvrVal.setMultiple(cfgFactor.getRcvrMultiple());
                setRcvrVal.setRcvrLimit(cfgFactor.getRcvrLimit());
                if (cfgFactor.getRcvrType() == 0) {
                    setRcvrVal.setRcvrType("固定加标");
                } else {
                    setRcvrVal.setRcvrType("动态加标");
                }

                setRcvrValList.add(setRcvrVal);
                ++iKey;
            }
        }

        return setRcvrValList;
    }

    public boolean setRecoveryInfo(HttpServletRequest request, List<SetRcvrVal> setRcvrValList) {
        List<CfgFactor> cfgFactorList = this.monitorService.getCfgFactorList();
        Iterator var4 = setRcvrValList.iterator();

        while(true) {
            while(var4.hasNext()) {
                SetRcvrVal setRcvrVal = (SetRcvrVal)var4.next();
                Iterator var6 = cfgFactorList.iterator();

                while(var6.hasNext()) {
                    CfgFactor cfgFactor = (CfgFactor)var6.next();
                    if (cfgFactor.getName().equals(setRcvrVal.getFactor())) {
                        cfgFactor.setRcvrMotherVal(setRcvrVal.getMotherVal());
                        cfgFactor.setRcvrMotherVol(setRcvrVal.getMotherVol());
                        cfgFactor.setRcvrDestVal(setRcvrVal.getConcVal());
                        cfgFactor.setRcvrCupVol(setRcvrVal.getCupVol());
                        cfgFactor.setRcvrRateMin(setRcvrVal.getRateMin());
                        cfgFactor.setRcvrRateMax(setRcvrVal.getRateMax());
                        if (cfgFactor.getRcvrType() == 0) {
                            cfgFactor.setRcvrMultiple(1.0D);
                        } else {
                            cfgFactor.setRcvrMultiple(setRcvrVal.getMultiple());
                        }

                        cfgFactor.setRcvrLimit(setRcvrVal.getRcvrLimit());
                        this.cfgMapper.updateCfgFactorRcvrVal(cfgFactor);
                        this.cfgMapper.updateAllCfgFactorRcvrVal(cfgFactor);
                        break;
                    }
                }
            }

            this.monitorService.refreshQuality();
            this.monitorService.setQuality(4000);
            TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
            this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 加标核查设置");
            return true;
        }
    }

    public List<SetSpanVal> getSpanInfo() {
        List<SetSpanVal> setSpanValList = new ArrayList();
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        int iKey = 0;
        Iterator var4 = cfgFactorList.iterator();

        while(var4.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor)var4.next();
            if (cfgFactor.isUsed() && cfgFactor.isParmType()) {
                SetSpanVal setSpanVal = new SetSpanVal();
                setSpanVal.setKey(iKey);
                setSpanVal.setFactor(cfgFactor.getName());
                setSpanVal.setSpanVal(cfgFactor.getSpanVal());
                setSpanValList.add(setSpanVal);
                ++iKey;
            }
        }

        return setSpanValList;
    }

    public boolean setSpanInfo(HttpServletRequest request, List<SetSpanVal> setSpanValList) {
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        Iterator var4 = setSpanValList.iterator();

        while(true) {
            while(var4.hasNext()) {
                SetSpanVal setSpanVal = (SetSpanVal)var4.next();
                Iterator var6 = cfgFactorList.iterator();

                while(var6.hasNext()) {
                    CfgFactor cfgFactor = (CfgFactor)var6.next();
                    if (cfgFactor.getName().equals(setSpanVal.getFactor())) {
                        cfgFactor.setSpanVal(setSpanVal.getSpanVal());
                        this.cfgMapper.updateCfgFactorSpanVal(cfgFactor);
                        this.cfgMapper.updateAllCfgFactorSpanVal(cfgFactor);
                        break;
                    }
                }
            }

            this.monitorService.refreshQuality();
            TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
            this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 跨度核查设置");
            return true;
        }
    }

    public List<SetErrorVal> getErrorInfo() {
        List<SetErrorVal> setErrorValList = new ArrayList();
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        int iKey = 0;
        Iterator var4 = cfgFactorList.iterator();

        while(true) {
            CfgFactor cfgFactor;
            do {
                do {
                    if (!var4.hasNext()) {
                        return setErrorValList;
                    }

                    cfgFactor = (CfgFactor)var4.next();
                } while(!cfgFactor.isUsed());
            } while(!cfgFactor.isFiveType() && !cfgFactor.isParmType());

            SetErrorVal setErrorVal = new SetErrorVal();
            setErrorVal.setKey(iKey);
            setErrorVal.setFactor(cfgFactor.getName());
            setErrorVal.setErrorZeroMin(cfgFactor.getZeroErrorMin());
            setErrorVal.setErrorZeroMax(cfgFactor.getZeroErrorMax());
            setErrorVal.setErrorSpanMin(cfgFactor.getSpanErrorMin());
            setErrorVal.setErrorSpanMax(cfgFactor.getSpanErrorMax());
            setErrorVal.setErrorStdMin(cfgFactor.getStdErrorMin());
            setErrorVal.setErrorStdMax(cfgFactor.getStdErrorMax());
            setErrorVal.setErrorBlnkMin(cfgFactor.getBlnkErrorMin());
            setErrorVal.setErrorBlnkMax(cfgFactor.getBlnkErrorMax());
            setErrorValList.add(setErrorVal);
            ++iKey;
        }
    }

    public boolean setErrorInfo(HttpServletRequest request, List<SetErrorVal> setErrorValList) {
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        Iterator var4 = setErrorValList.iterator();

        while(true) {
            while(var4.hasNext()) {
                SetErrorVal setErrorVal = (SetErrorVal)var4.next();
                Iterator var6 = cfgFactorList.iterator();

                while(var6.hasNext()) {
                    CfgFactor cfgFactor = (CfgFactor)var6.next();
                    if (cfgFactor.getName().equals(setErrorVal.getFactor())) {
                        cfgFactor.setZeroErrorMin(setErrorVal.getErrorZeroMin());
                        cfgFactor.setZeroErrorMax(setErrorVal.getErrorZeroMax());
                        cfgFactor.setSpanErrorMin(setErrorVal.getErrorSpanMin());
                        cfgFactor.setSpanErrorMax(setErrorVal.getErrorSpanMax());
                        cfgFactor.setStdErrorMin(setErrorVal.getErrorStdMin());
                        cfgFactor.setStdErrorMax(setErrorVal.getErrorStdMax());
                        cfgFactor.setBlnkErrorMin(setErrorVal.getErrorBlnkMin());
                        cfgFactor.setBlnkErrorMax(setErrorVal.getErrorBlnkMax());
                        this.cfgMapper.updateCfgFactorErrorVal(cfgFactor);
                        this.cfgMapper.updateAllCfgFactorErrorVal(cfgFactor);
                        break;
                    }
                }
            }

            this.monitorService.refreshQuality();
            TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
            this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 核查绝对误差设置");
            return true;
        }
    }

    public List<SetDriftVal> getDriftInfo() {
        List<SetDriftVal> setDriftValList = new ArrayList();
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        int iKey = 0;
        Iterator var4 = cfgFactorList.iterator();

        while(true) {
            CfgFactor cfgFactor;
            do {
                do {
                    if (!var4.hasNext()) {
                        return setDriftValList;
                    }

                    cfgFactor = (CfgFactor)var4.next();
                } while(!cfgFactor.isUsed());
            } while(!cfgFactor.isFiveType() && !cfgFactor.isParmType());

            SetDriftVal setDriftVal = new SetDriftVal();
            setDriftVal.setKey(iKey);
            setDriftVal.setFactor(cfgFactor.getName());
            setDriftVal.setDriftZeroMin(cfgFactor.getZeroDriftMin());
            setDriftVal.setDriftZeroMax(cfgFactor.getZeroDriftMax());
            setDriftVal.setDriftSpanMin(cfgFactor.getSpanDriftMin());
            setDriftVal.setDriftSpanMax(cfgFactor.getSpanDriftMax());
            setDriftVal.setDriftStdMin(cfgFactor.getStdDriftMin());
            setDriftVal.setDriftStdMax(cfgFactor.getStdDriftMax());
            setDriftVal.setDriftBlnkMin(cfgFactor.getBlnkDriftMin());
            setDriftVal.setDriftBlnkMax(cfgFactor.getBlnkDriftMax());
            setDriftVal.setDriftParMin(cfgFactor.getParDriftMin());
            setDriftVal.setDriftParMax(cfgFactor.getParDriftMax());
            setDriftValList.add(setDriftVal);
            ++iKey;
        }
    }

    public boolean setDriftInfo(HttpServletRequest request, List<SetDriftVal> setDriftValList) {
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        Iterator var4 = setDriftValList.iterator();

        while(true) {
            while(var4.hasNext()) {
                SetDriftVal setDriftVal = (SetDriftVal)var4.next();
                Iterator var6 = cfgFactorList.iterator();

                while(var6.hasNext()) {
                    CfgFactor cfgFactor = (CfgFactor)var6.next();
                    if (cfgFactor.getName().equals(setDriftVal.getFactor())) {
                        cfgFactor.setZeroDriftMin(setDriftVal.getDriftZeroMin());
                        cfgFactor.setZeroDriftMax(setDriftVal.getDriftZeroMax());
                        cfgFactor.setSpanDriftMin(setDriftVal.getDriftSpanMin());
                        cfgFactor.setSpanDriftMax(setDriftVal.getDriftSpanMax());
                        cfgFactor.setStdDriftMin(setDriftVal.getDriftStdMin());
                        cfgFactor.setStdDriftMax(setDriftVal.getDriftStdMax());
                        cfgFactor.setBlnkDriftMin(setDriftVal.getDriftBlnkMin());
                        cfgFactor.setBlnkDriftMax(setDriftVal.getDriftBlnkMax());
                        cfgFactor.setParDriftMin(setDriftVal.getDriftParMin());
                        cfgFactor.setParDriftMax(setDriftVal.getDriftParMax());
                        this.cfgMapper.updateCfgFactorDriftVal(cfgFactor);
                        this.cfgMapper.updateAllCfgFactorDriftVal(cfgFactor);
                        break;
                    }
                }
            }

            this.monitorService.refreshQuality();
            TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
            this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 核查相对误差设置");
            return true;
        }
    }

    public List<SetUserInfo> getUserInfo() {
        List<CfgUser> cfgUserList = this.cfgMapper.getCfgUserList();
        List<SetUserInfo> setUserInfoList = new ArrayList();
        int iKey = 0;

        for(Iterator var4 = cfgUserList.iterator(); var4.hasNext(); ++iKey) {
            CfgUser cfgUser = (CfgUser)var4.next();
            SetUserInfo setUserInfo = new SetUserInfo();
            setUserInfo.setKey(iKey);
            setUserInfo.setUser(cfgUser.getUser());
            setUserInfo.setNick(cfgUser.getNick());
            setUserInfo.setPassword(cfgUser.getPassword());
            setUserInfo.setGroup(cfgUser.getGroup_name());
            setUserInfo.setAdd_time(cfgUser.getAdd_time());
            setUserInfo.setUpdate_time(cfgUser.getUpdate_time());
            setUserInfo.setLogin_time(cfgUser.getLogin_time());
            setUserInfo.setAccess(cfgUser.getAccess());
            setUserInfo.setAccess_time(cfgUser.getAccess_time());
            setUserInfoList.add(setUserInfo);
        }

        return setUserInfoList;
    }

    public boolean setUserInfo(HttpServletRequest request, List<SetUserInfo> setUserInfoList) {
        List<CfgUser> cfgUserList = this.cfgMapper.getCfgUserList();
        Iterator var7 = setUserInfoList.iterator();

        boolean bCheck;
        Iterator var9;
        while(var7.hasNext()) {
            SetUserInfo setUserInfo = (SetUserInfo)var7.next();
            bCheck = false;
            var9 = cfgUserList.iterator();

            String strCode;
            String strAccess;
            while(var9.hasNext()) {
                CfgUser cfgUser = (CfgUser)var9.next();
                if (cfgUser.getUser().equals(setUserInfo.getUser())) {
                    bCheck = true;
                    if (setUserInfo.getPassword().length() == 0) {
                        strCode = this.passwordEncoder.encode("123456");
                    } else if (setUserInfo.getPassword().length() <= 20) {
                        strCode = this.passwordEncoder.encode(setUserInfo.getPassword());
                    } else {
                        strCode = setUserInfo.getPassword();
                    }

                    if (setUserInfo.getAccess().length() == 0) {
                        strAccess = "123456";
                    } else if (setUserInfo.getAccess().length() <= 20) {
                        strAccess = setUserInfo.getAccess();
                    } else {
                        strAccess = setUserInfo.getAccess();
                    }

                    cfgUser.setNick(setUserInfo.getNick());
                    cfgUser.setPassword(strCode);
                    cfgUser.setGroup_name(setUserInfo.getGroup());
                    cfgUser.setAdd_time(setUserInfo.getAdd_time());
                    cfgUser.setUpdate_time(setUserInfo.getUpdate_time());
                    cfgUser.setLogin_time(setUserInfo.getLogin_time());
                    cfgUser.setAccess(strAccess);
                    cfgUser.setAccess_time(setUserInfo.getAccess_time());
                    this.cfgMapper.updateCfgUser(cfgUser);
                    break;
                }
            }

            if (!bCheck) {
                CfgUser newUser = new CfgUser();
                strCode = this.passwordEncoder.encode(setUserInfo.getPassword());
                strAccess = setUserInfo.getAccess();
                newUser.setUser(setUserInfo.getUser());
                newUser.setNick(setUserInfo.getNick());
                newUser.setPassword(strCode);
                newUser.setGroup_name(setUserInfo.getGroup());
                newUser.setAdd_time(setUserInfo.getAdd_time());
                newUser.setUpdate_time(setUserInfo.getUpdate_time());
                newUser.setLogin_time(setUserInfo.getLogin_time());
                newUser.setAccess(strAccess);
                newUser.setAccess_time(setUserInfo.getAccess_time());
                this.cfgMapper.addCfgUser(newUser);
            }
        }

        var7 = cfgUserList.iterator();

        while(var7.hasNext()) {
            CfgUser cfgUser = (CfgUser)var7.next();
            bCheck = false;
            var9 = setUserInfoList.iterator();

            while(var9.hasNext()) {
                SetUserInfo setUserInfo = (SetUserInfo)var9.next();
                if (setUserInfo.getUser().equals(cfgUser.getUser())) {
                    bCheck = true;
                    break;
                }
            }

            if (!bCheck) {
                this.cfgMapper.deleteCfgUser(cfgUser);
            }
        }

        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 用户管理");
        return true;
    }

    public boolean updateLoginTime(LoginTime loginTime) {
        CfgUser cfgUser = this.cfgMapper.getCfgUserByName(loginTime.getUser());
        cfgUser.setLogin_time(loginTime.getLogin_time());
        this.cfgMapper.updateCfgUserLoginTime(cfgUser);
        return true;
    }

    public List<SetComInfo> getComInfo() {
        List<CfgSerial> cfgSerialList = this.cfgMapper.getCfgSerialList();
        List<CfgDev> cfgDevList = this.cfgMapper.getCfgDevList();
        List<SetComInfo> setComInfoList = new ArrayList();
        Iterator var5 = cfgSerialList.iterator();

        while(var5.hasNext()) {
            CfgSerial cfgSerial = (CfgSerial)var5.next();
            SetComInfo setComInfo = new SetComInfo();
            setComInfo.setKey(cfgSerial.getId() - 1);
            setComInfo.setName(cfgSerial.getName());
            setComInfo.setPort(cfgSerial.getPort());
            setComInfo.setBaudRate(cfgSerial.getBaudRate());
            setComInfo.setDataBits(cfgSerial.getDataBits());
            switch(cfgSerial.getStopBits()) {
                case 1:
                    setComInfo.setStopBits("1");
                    break;
                case 2:
                    setComInfo.setStopBits("1.5");
                    break;
                case 3:
                    setComInfo.setStopBits("2");
            }

            switch(cfgSerial.getParity()) {
                case 0:
                    setComInfo.setParity("无");
                    break;
                case 1:
                    setComInfo.setParity("奇校验");
                    break;
                case 2:
                    setComInfo.setParity("偶校验");
                    break;
                case 3:
                    setComInfo.setParity("Mark");
                    break;
                case 4:
                    setComInfo.setParity("空格校验");
            }

            setComInfo.setCmdType(cfgSerial.isCmdType() ? 1 : 0);
            setComInfo.setCmdDelay(cfgSerial.getCmdDelay());
            setComInfo.setUsed(cfgSerial.isUsed());
            setComInfo.setExpand(false);
            String strDes = "";
            Iterator var8 = cfgDevList.iterator();

            while(var8.hasNext()) {
                CfgDev cfgDev = (CfgDev)var8.next();
                if (cfgDev.getSerial_id() == cfgSerial.getId()) {
                    strDes = strDes + cfgDev.getNick() + "; ";
                }
            }

            if (strDes.length() == 0) {
                strDes = "无";
            }

            setComInfo.setDescription(strDes);
            setComInfoList.add(setComInfo);
        }

        return setComInfoList;
    }

    public boolean setComInfo(HttpServletRequest request, List<SetComInfo> setComInfoList) {
        List<CfgSerial> cfgSerialList = this.cfgMapper.getCfgSerialList();
        Iterator var4 = setComInfoList.iterator();

        while(true) {
            while(var4.hasNext()) {
                SetComInfo setComInfo = (SetComInfo)var4.next();
                Iterator var6 = cfgSerialList.iterator();

                while(var6.hasNext()) {
                    CfgSerial cfgSerial = (CfgSerial)var6.next();
                    if (cfgSerial.getName().equals(setComInfo.getName())) {
                        cfgSerial.setUsed(setComInfo.isUsed());
                        cfgSerial.setBaudRate(setComInfo.getBaudRate());
                        cfgSerial.setDataBits(setComInfo.getDataBits());
                        String var8 = setComInfo.getStopBits();
                        byte var9 = -1;
                        switch(var8.hashCode()) {
                            case 49:
                                if (var8.equals("1")) {
                                    var9 = 0;
                                }
                                break;
                            case 50:
                                if (var8.equals("2")) {
                                    var9 = 2;
                                }
                                break;
                            case 48568:
                                if (var8.equals("1.5")) {
                                    var9 = 1;
                                }
                        }

                        switch(var9) {
                            case 0:
                                cfgSerial.setStopBits(1);
                                break;
                            case 1:
                                cfgSerial.setStopBits(2);
                                break;
                            case 2:
                                cfgSerial.setStopBits(3);
                        }

                        var8 = setComInfo.getParity();
                        var9 = -1;
                        switch(var8.hashCode()) {
                            case 26080:
                                if (var8.equals("无")) {
                                    var9 = 0;
                                }
                                break;
                            case 2390765:
                                if (var8.equals("Mark")) {
                                    var9 = 3;
                                }
                                break;
                            case 20660609:
                                if (var8.equals("偶校验")) {
                                    var9 = 2;
                                }
                                break;
                            case 22829586:
                                if (var8.equals("奇校验")) {
                                    var9 = 1;
                                }
                                break;
                            case 960576269:
                                if (var8.equals("空格校验")) {
                                    var9 = 4;
                                }
                        }

                        switch(var9) {
                            case 0:
                                cfgSerial.setParity(0);
                                break;
                            case 1:
                                cfgSerial.setParity(1);
                                break;
                            case 2:
                                cfgSerial.setParity(2);
                                break;
                            case 3:
                                cfgSerial.setParity(3);
                                break;
                            case 4:
                                cfgSerial.setParity(4);
                        }

                        if (setComInfo.getCmdType() == 1) {
                            cfgSerial.setCmdType(true);
                        } else {
                            cfgSerial.setCmdType(false);
                        }

                        cfgSerial.setCmdDelay(setComInfo.getCmdDelay());
                        this.cfgMapper.updateCfgSerial(cfgSerial);
                        break;
                    }
                }
            }

            TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
            this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 串口设置");
            return true;
        }
    }

    public boolean activeComInfo(HttpServletRequest request, List<SetComInfo> setComInfoList) {
        this.setComInfo(request, setComInfoList);
        this.monitorService.restartService();
        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "应用 串口设置");
        return true;
    }

    public List<SetNetInfo> getNetInfo() {
        List<CfgNetClient> cfgNetClientList = this.cfgMapper.getCfgNetClientList();
        List<CfgDev> cfgDevList = this.cfgMapper.getCfgDevList();
        List<SetNetInfo> setNetInfoList = new ArrayList();
        Iterator var5 = cfgNetClientList.iterator();

        while(var5.hasNext()) {
            CfgNetClient cfgNetClient = (CfgNetClient)var5.next();
            SetNetInfo setNetInfo = new SetNetInfo();
            setNetInfo.setKey(cfgNetClient.getId() - 1);
            setNetInfo.setName(cfgNetClient.getName());
            setNetInfo.setIp(cfgNetClient.getIp());
            setNetInfo.setPort(cfgNetClient.getPort());
            setNetInfo.setUsed(cfgNetClient.isUsed());
            setNetInfo.setExpand(false);
            setNetInfo.setCmdType(cfgNetClient.isCmdType() ? 1 : 0);
            setNetInfo.setCmdDelay(cfgNetClient.getCmdDelay());
            String strDes = "";
            Iterator var8 = cfgDevList.iterator();

            while(var8.hasNext()) {
                CfgDev cfgDev = (CfgDev)var8.next();
                if (cfgDev.getNet_id() == cfgNetClient.getId()) {
                    strDes = strDes + cfgDev.getNick() + "; ";
                }
            }

            if (strDes.length() == 0) {
                strDes = "无";
            }

            setNetInfo.setDescription(strDes);
            setNetInfoList.add(setNetInfo);
        }

        return setNetInfoList;
    }

    public boolean setNetInfo(HttpServletRequest request, List<SetNetInfo> setNetInfoList) {
        List<CfgNetClient> cfgNetClientList = this.cfgMapper.getCfgNetClientList();
        Iterator var4 = setNetInfoList.iterator();

        while(true) {
            while(var4.hasNext()) {
                SetNetInfo setNetInfo = (SetNetInfo)var4.next();
                Iterator var6 = cfgNetClientList.iterator();

                while(var6.hasNext()) {
                    CfgNetClient cfgNetClient = (CfgNetClient)var6.next();
                    if (cfgNetClient.getName().equals(setNetInfo.getName())) {
                        cfgNetClient.setUsed(setNetInfo.isUsed());
                        cfgNetClient.setIp(setNetInfo.getIp());
                        cfgNetClient.setPort(setNetInfo.getPort());
                        if (setNetInfo.getCmdType() == 1) {
                            cfgNetClient.setCmdType(true);
                        } else {
                            cfgNetClient.setCmdType(false);
                        }

                        cfgNetClient.setCmdDelay(setNetInfo.getCmdDelay());
                        this.cfgMapper.updateCfgNetClient(cfgNetClient);
                        break;
                    }
                }
            }

            TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
            this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 网口设置");
            return true;
        }
    }

    public boolean activeNetInfo(HttpServletRequest request, List<SetNetInfo> setNetInfoList) {
        this.setNetInfo(request, setNetInfoList);
        this.monitorService.restartService();
        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "应用 网口设置");
        return true;
    }

    public List<String> getStateInfo() {
        List<StatusWord> stateWordList = ScriptWord.getInstance().getStatusWordList();
        List<String> stateList = new ArrayList();
        Iterator var3 = stateWordList.iterator();

        while(var3.hasNext()) {
            StatusWord statusWord = (StatusWord)var3.next();
            stateList.add(statusWord.getName());
        }

        return stateList;
    }

    public List<String> getDevTypeList() {
        return this.monitorService.getDevTypeList();
    }

    public List<String> getProtocolList() {
        return this.monitorService.getProtocolList();
    }

    public List<String> getInterfaceList() {
        List<String> interfaceList = new ArrayList();
        Iterator var2 = this.monitorService.getCfgSerialList().iterator();

        while(var2.hasNext()) {
            CfgSerial cfgSerial = (CfgSerial)var2.next();
            interfaceList.add(cfgSerial.getName());
        }

        var2 = this.monitorService.getCfgNetClientList().iterator();

        while(var2.hasNext()) {
            CfgNetClient cfgNetClient = (CfgNetClient)var2.next();
            interfaceList.add(cfgNetClient.getName());
        }

        return interfaceList;
    }

    public List<String> getRelateNameList() {
        List<String> relateNameList = new ArrayList();
        List<DefWord> defWordList = ScriptWord.getInstance().getDefWordList();
        Iterator var3 = defWordList.iterator();

        while(var3.hasNext()) {
            DefWord defWord = (DefWord)var3.next();
            if (defWord.getType() == 2) {
                relateNameList.add(defWord.getName());
            }
        }

        return relateNameList;
    }

    public List<SetInstrumentInfo> getInstrumentInfo() {
        List<CfgDev> cfgDevList = this.cfgMapper.getCfgDevList();
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        List<CfgSerial> cfgSerialList = this.cfgMapper.getCfgSerialList();
        List<CfgNetClient> cfgNetClientList = this.cfgMapper.getCfgNetClientList();
        List<SetInstrumentInfo> setInstrumentInfoList = new ArrayList();
        Iterator var7 = cfgDevList.iterator();

        while(true) {
            CfgDev cfgDev;
            do {
                if (!var7.hasNext()) {
                    return setInstrumentInfoList;
                }

                cfgDev = (CfgDev)var7.next();
            } while(cfgDev.getType().equals("PLC"));

            SetInstrumentInfo setInstrumentInfo = new SetInstrumentInfo();
            setInstrumentInfo.setKey(cfgDev.getId() + "");
            setInstrumentInfo.setName(cfgDev.getName());
            setInstrumentInfo.setNick(cfgDev.getNick());
            setInstrumentInfo.setType(this.monitorService.getChName(cfgDev.getType()));
            setInstrumentInfo.setProtocol(this.monitorService.getChName(cfgDev.getProtocol()));
            setInstrumentInfo.setSaveMeaTag(cfgDev.getSave_mea_tag());
            setInstrumentInfo.setSaveZeroTag(cfgDev.getSave_zero_tag());
            setInstrumentInfo.setSaveSpanTag(cfgDev.getSave_span_tag());
            setInstrumentInfo.setSaveStdTag(cfgDev.getSave_std_tag());
            setInstrumentInfo.setSaveBlnkTag(cfgDev.getSave_blnk_tag());
            setInstrumentInfo.setSaveRcvrTag(cfgDev.getSave_rcvr_tag());
            setInstrumentInfo.setSaveParalTag(cfgDev.getSave_parl_tag());
            setInstrumentInfo.setStartMeaTag(cfgDev.getStart_mea_tag());
            setInstrumentInfo.setStartZeroTag(cfgDev.getStart_zero_tag());
            setInstrumentInfo.setStartSpanTag(cfgDev.getStart_span_tag());
            setInstrumentInfo.setStartStdTag(cfgDev.getStart_std_tag());
            setInstrumentInfo.setStartBlnkTag(cfgDev.getStart_blnk_tag());
            setInstrumentInfo.setStartRcvrTag(cfgDev.getStart_rcvr_tag());
            setInstrumentInfo.setStartParalTag(cfgDev.getStart_parl_tag());
            setInstrumentInfo.setAddress(cfgDev.getAddress());
            setInstrumentInfo.setLogId(cfgDev.getLog_id());
            setInstrumentInfo.setRelateName(this.monitorService.getDevName(cfgDev.getRelated_id()));
            setInstrumentInfo.setRelateName2(this.monitorService.getDevName(cfgDev.getRelated_id2()));
            Iterator var10;
            if (cfgDev.getSerial_id() > 0) {
                var10 = cfgSerialList.iterator();

                while(var10.hasNext()) {
                    CfgSerial cfgSerial = (CfgSerial)var10.next();
                    if (cfgDev.getSerial_id() == cfgSerial.getId()) {
                        setInstrumentInfo.setCommunication(cfgSerial.getName());
                        break;
                    }
                }
            } else if (cfgDev.getNet_id() > 0) {
                var10 = cfgNetClientList.iterator();

                while(var10.hasNext()) {
                    CfgNetClient cfgNetClient = (CfgNetClient)var10.next();
                    if (cfgDev.getNet_id() == cfgNetClient.getId()) {
                        setInstrumentInfo.setCommunication(cfgNetClient.getName());
                        break;
                    }
                }
            }

            setInstrumentInfo.setExpand(false);
            String strDes = "";
            var10 = cfgFactorList.iterator();

            while(var10.hasNext()) {
                CfgFactor cfgFactor = (CfgFactor)var10.next();
                if (cfgFactor.getDevID() == cfgDev.getId()) {
                    strDes = strDes + cfgFactor.getName() + "; ";
                }
            }

            if (strDes.length() == 0) {
                strDes = "无";
            }

            setInstrumentInfo.setDescription(strDes);
            setInstrumentInfoList.add(setInstrumentInfo);
        }
    }

    public boolean setInstrumentInfo(HttpServletRequest request, List<SetInstrumentInfo> setInstrumentInfoList) {
        List<CfgDev> cfgDevList = this.cfgMapper.getCfgDevList();
        Iterator var5 = setInstrumentInfoList.iterator();

        boolean bExist;
        Iterator var7;
        while(var5.hasNext()) {
            SetInstrumentInfo setInstrumentInfo = (SetInstrumentInfo)var5.next();
            bExist = false;
            var7 = cfgDevList.iterator();

            while(var7.hasNext()) {
                CfgDev cfgDev = (CfgDev)var7.next();
                if (cfgDev.getName().equals(setInstrumentInfo.getName())) {
                    bExist = true;
                    cfgDev.setNick(setInstrumentInfo.getNick());
                    cfgDev.setSerial_id(this.monitorService.getSerialID(setInstrumentInfo.getCommunication()));
                    cfgDev.setNet_id(this.monitorService.getNetID(setInstrumentInfo.getCommunication()));
                    cfgDev.setType(this.monitorService.getEnName(setInstrumentInfo.getType()));
                    cfgDev.setProtocol(this.monitorService.getEnName(setInstrumentInfo.getProtocol()));
                    cfgDev.setSave_mea_tag(setInstrumentInfo.getSaveMeaTag());
                    cfgDev.setSave_zero_tag(setInstrumentInfo.getSaveZeroTag());
                    cfgDev.setSave_span_tag(setInstrumentInfo.getSaveSpanTag());
                    cfgDev.setSave_std_tag(setInstrumentInfo.getSaveStdTag());
                    cfgDev.setSave_blnk_tag(setInstrumentInfo.getSaveBlnkTag());
                    cfgDev.setSave_rcvr_tag(setInstrumentInfo.getSaveRcvrTag());
                    cfgDev.setSave_parl_tag(setInstrumentInfo.getSaveParalTag());
                    cfgDev.setStart_mea_tag(setInstrumentInfo.getStartMeaTag());
                    cfgDev.setStart_zero_tag(setInstrumentInfo.getStartZeroTag());
                    cfgDev.setStart_span_tag(setInstrumentInfo.getStartSpanTag());
                    cfgDev.setStart_std_tag(setInstrumentInfo.getStartStdTag());
                    cfgDev.setStart_blnk_tag(setInstrumentInfo.getStartBlnkTag());
                    cfgDev.setStart_rcvr_tag(setInstrumentInfo.getStartRcvrTag());
                    cfgDev.setStart_parl_tag(setInstrumentInfo.getStartParalTag());
                    cfgDev.setAddress(setInstrumentInfo.getAddress());
                    cfgDev.setLog_id(setInstrumentInfo.getLogId());
                    cfgDev.setRelated_id(this.monitorService.getDevID(setInstrumentInfo.getRelateName()));
                    cfgDev.setRelated_id2(this.monitorService.getDevID(setInstrumentInfo.getRelateName2()));
                    this.cfgMapper.updateCfgDev(cfgDev);
                    break;
                }
            }

            if (!bExist) {
                CfgDev addDev = new CfgDev();
                addDev.setName(setInstrumentInfo.getName());
                addDev.setNick(setInstrumentInfo.getNick());
                addDev.setSerial_id(this.monitorService.getSerialID(setInstrumentInfo.getCommunication()));
                addDev.setNet_id(this.monitorService.getNetID(setInstrumentInfo.getCommunication()));
                addDev.setType(this.monitorService.getEnName(setInstrumentInfo.getType()));
                addDev.setProtocol(this.monitorService.getEnName(setInstrumentInfo.getProtocol()));
                addDev.setSave_mea_tag(setInstrumentInfo.getSaveMeaTag());
                addDev.setSave_zero_tag(setInstrumentInfo.getSaveZeroTag());
                addDev.setSave_span_tag(setInstrumentInfo.getSaveSpanTag());
                addDev.setSave_std_tag(setInstrumentInfo.getSaveStdTag());
                addDev.setSave_blnk_tag(setInstrumentInfo.getSaveBlnkTag());
                addDev.setSave_rcvr_tag(setInstrumentInfo.getSaveRcvrTag());
                addDev.setSave_parl_tag(setInstrumentInfo.getSaveParalTag());
                addDev.setStart_mea_tag(setInstrumentInfo.getStartMeaTag());
                addDev.setStart_zero_tag(setInstrumentInfo.getStartZeroTag());
                addDev.setStart_span_tag(setInstrumentInfo.getStartSpanTag());
                addDev.setStart_std_tag(setInstrumentInfo.getStartStdTag());
                addDev.setStart_blnk_tag(setInstrumentInfo.getStartBlnkTag());
                addDev.setStart_rcvr_tag(setInstrumentInfo.getStartRcvrTag());
                addDev.setStart_parl_tag(setInstrumentInfo.getStartParalTag());
                addDev.setAddress(setInstrumentInfo.getAddress());
                addDev.setLog_id(setInstrumentInfo.getLogId());
                addDev.setRelated_id(this.monitorService.getDevID(setInstrumentInfo.getRelateName()));
                addDev.setRelated_id2(this.monitorService.getDevID(setInstrumentInfo.getRelateName2()));
                this.cfgMapper.addCfgDev(addDev);
            }
        }

        var5 = cfgDevList.iterator();

        while(true) {
            CfgDev cfgDev;
            do {
                if (!var5.hasNext()) {
                    TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
                    this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 仪器管理");
                    return true;
                }

                cfgDev = (CfgDev)var5.next();
            } while(cfgDev.getType().equals("PLC"));

            bExist = false;
            var7 = setInstrumentInfoList.iterator();

            while(var7.hasNext()) {
                SetInstrumentInfo setInstrumentInfo = (SetInstrumentInfo)var7.next();
                if (cfgDev.getName().equals(setInstrumentInfo.getName())) {
                    bExist = true;
                    break;
                }
            }

            if (!bExist) {
                this.cfgMapper.deleteCfgDev(cfgDev);
            }
        }
    }

    public boolean activeInstrumentInfo(HttpServletRequest request, List<SetInstrumentInfo> setInstrumentInfoList) {
        this.setInstrumentInfo(request, setInstrumentInfoList);
        this.monitorService.restartService();
        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "应用 仪器管理");
        return true;
    }

    public List<CfgUnit> getUnitList() {
        List<CfgUnit> cfgUnitList = this.cfgMapper.getCfgUnitList();
        return cfgUnitList;
    }

    public List<String> getDevList() {
        List<CfgDev> cfgDevList = this.cfgMapper.getCfgDevList();
        List<String> devList = new ArrayList();
        Iterator var4 = cfgDevList.iterator();

        while(var4.hasNext()) {
            CfgDev cfgDev = (CfgDev)var4.next();
            boolean bSame = false;
            Iterator var6 = devList.iterator();

            while(var6.hasNext()) {
                String strDev = (String)var6.next();
                if (strDev.equals(cfgDev.getName())) {
                    bSame = true;
                    break;
                }
            }

            if (!bSame) {
                devList.add(cfgDev.getName());
            }
        }

        return devList;
    }

    public List<SetFactorInfo> getFactorList() {
        List<CfgFactor> cfgFactorList = this.cfgMapper.getAllCfgFactorList();
        List<SetFactorInfo> setFactorInfoList = new ArrayList();
        Iterator var3 = cfgFactorList.iterator();

        while(var3.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor)var3.next();
            SetFactorInfo setFactorInfo = new SetFactorInfo();
            setFactorInfo.setKey(cfgFactor.getId());
            setFactorInfo.setUsed(cfgFactor.isUsed());
            setFactorInfo.setName(cfgFactor.getName());
            setFactorInfo.setUnit(cfgFactor.getUnit());
            setFactorInfo.setDecimals(cfgFactor.getDecimals());
            setFactorInfo.setTop(cfgFactor.getAlarmMax());
            setFactorInfo.setBottom(cfgFactor.getAlarmMin());
            setFactorInfo.setAddress(cfgFactor.getDevAddress());
            setFactorInfo.setLevel1(cfgFactor.getLevel1());
            setFactorInfo.setLevel2(cfgFactor.getLevel2());
            setFactorInfo.setLevel3(cfgFactor.getLevel3());
            setFactorInfo.setLevel4(cfgFactor.getLevel4());
            setFactorInfo.setLevel5(cfgFactor.getLevel5());
            setFactorInfo.setInstrument((String)null);
            setFactorInfoList.add(setFactorInfo);
        }

        return setFactorInfoList;
    }

    public List<SetFactorInfo> getFactorInfo() {
        List<CfgDev> cfgDevList = this.cfgMapper.getCfgDevList();
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        List<SetFactorInfo> setFactorInfoList = new ArrayList();
        Iterator var4 = cfgFactorList.iterator();

        while(var4.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor)var4.next();
            SetFactorInfo setFactorInfo = new SetFactorInfo();
            setFactorInfo.setKey(cfgFactor.getId());
            setFactorInfo.setUsed(cfgFactor.isUsed());
            setFactorInfo.setName(cfgFactor.getName());
            setFactorInfo.setUnit(cfgFactor.getUnit());
            setFactorInfo.setDecimals(cfgFactor.getDecimals());
            setFactorInfo.setTop(cfgFactor.getAlarmMax());
            setFactorInfo.setBottom(cfgFactor.getAlarmMin());
            setFactorInfo.setAddress(cfgFactor.getDevAddress());
            setFactorInfo.setChannel(cfgFactor.getDevChannel());
            setFactorInfo.setLevel1(cfgFactor.getLevel1());
            setFactorInfo.setLevel2(cfgFactor.getLevel2());
            setFactorInfo.setLevel3(cfgFactor.getLevel3());
            setFactorInfo.setLevel4(cfgFactor.getLevel4());
            setFactorInfo.setLevel5(cfgFactor.getLevel5());
            if (cfgFactor.getDevID() > 0) {
                Iterator var7 = cfgDevList.iterator();

                while(var7.hasNext()) {
                    CfgDev cfgDev = (CfgDev)var7.next();
                    if (cfgDev.getId() == cfgFactor.getDevID()) {
                        setFactorInfo.setInstrument(cfgDev.getName());
                        break;
                    }
                }
            }

            switch(cfgFactor.getCheckType()) {
                case 0:
                    setFactorInfo.setCheckType("相对误差");
                    break;
                default:
                    setFactorInfo.setCheckType("绝对误差");
            }

            setFactorInfo.setNonNegative(cfgFactor.isNonNegative());
            setFactorInfoList.add(setFactorInfo);
        }

        return setFactorInfoList;
    }
    @SuppressWarnings("FallThrough")
    public boolean setFactorInfo(HttpServletRequest request, List<SetFactorInfo> setFactorInfoList) {
        List<CfgDev> cfgDevList = this.cfgMapper.getCfgDevList();
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        List<CfgFactor> cfgAllFactorList = this.cfgMapper.getAllCfgFactorList();
        Iterator var7 = setFactorInfoList.iterator();

        while(true) {
            label100:
            while(true) {
                boolean bExist;
                SetFactorInfo setFactorInfo;
                Iterator var9;
                CfgFactor addFactor;
                Iterator var11;
                CfgDev cfgDev;
                String var16;
                byte var17;
                label92:
                do {
                    if (!var7.hasNext()) {
                        var7 = cfgFactorList.iterator();

                        while(var7.hasNext()) {
                            CfgFactor cfgFactor = (CfgFactor)var7.next();
                            bExist = false;
                            var9 = setFactorInfoList.iterator();

                            while(var9.hasNext()) {
                                 setFactorInfo = (SetFactorInfo)var9.next();
                                if (setFactorInfo.getName().equals(cfgFactor.getName())) {
                                    bExist = true;
                                    break;
                                }
                            }

                            if (!bExist) {
                                this.cfgMapper.deleteCfgFactor(cfgFactor);
                            }
                        }

                        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
                        this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 因子管理");
                        return true;
                    }

                    setFactorInfo = (SetFactorInfo)var7.next();
                    bExist = false;
                    var9 = cfgFactorList.iterator();

                    while(var9.hasNext()) {
                        addFactor = (CfgFactor)var9.next();
                        if (addFactor.getName().equals(setFactorInfo.getName())) {
                            bExist = true;
                            addFactor.setUsed(setFactorInfo.isUsed());
                            addFactor.setUnit(setFactorInfo.getUnit());
                            addFactor.setDecimals(setFactorInfo.getDecimals());
                            addFactor.setAlarmMax(setFactorInfo.getTop());
                            addFactor.setAlarmMin(setFactorInfo.getBottom());
                            addFactor.setDevChannel(setFactorInfo.getChannel());
                            addFactor.setLevel1(setFactorInfo.getLevel1());
                            addFactor.setLevel2(setFactorInfo.getLevel2());
                            addFactor.setLevel3(setFactorInfo.getLevel3());
                            addFactor.setLevel4(setFactorInfo.getLevel4());
                            addFactor.setLevel5(setFactorInfo.getLevel5());
                            addFactor.setDevID(0);
                            if (setFactorInfo.getInstrument() != null) {
                                var11 = cfgDevList.iterator();

                                while(var11.hasNext()) {
                                    cfgDev = (CfgDev)var11.next();
                                    if (cfgDev.getName().equals(setFactorInfo.getInstrument())) {
                                        addFactor.setDevID(cfgDev.getId());
                                        break;
                                    }
                                }
                            }

                            addFactor.setDevAddress(setFactorInfo.getAddress());
                            var16 = setFactorInfo.getCheckType();
                            var17 = -1;
                            switch(var16.hashCode()) {
                                case 931076000:
                                    if (var16.equals("相对误差")) {
                                        var17 = 0;
                                    }
                                default:
                                    switch(var17) {
                                        case 0:
                                            addFactor.setCheckType(0);
                                            break;
                                        default:
                                            addFactor.setCheckType(1);
                                    }

                                    addFactor.setNonNegative(setFactorInfo.isNonNegative());
                                    this.cfgMapper.updateCfgFactor(addFactor);
                                    continue label92;
                            }
                        }
                    }
                } while(bExist);

                var9 = cfgAllFactorList.iterator();

                while(var9.hasNext()) {
                    addFactor = (CfgFactor)var9.next();
                    if (addFactor.getName().equals(setFactorInfo.getName())) {
                        addFactor.setUsed(setFactorInfo.isUsed());
                        addFactor.setUnit(setFactorInfo.getUnit());
                        addFactor.setDecimals(setFactorInfo.getDecimals());
                        addFactor.setAlarmMax(setFactorInfo.getTop());
                        addFactor.setAlarmMin(setFactorInfo.getBottom());
                        addFactor.setDevChannel(setFactorInfo.getChannel());
                        addFactor.setLevel1(setFactorInfo.getLevel1());
                        addFactor.setLevel2(setFactorInfo.getLevel2());
                        addFactor.setLevel3(setFactorInfo.getLevel3());
                        addFactor.setLevel4(setFactorInfo.getLevel4());
                        addFactor.setLevel5(setFactorInfo.getLevel5());
                        addFactor.setDevID(0);
                        if (setFactorInfo.getInstrument() != null) {
                            var11 = cfgDevList.iterator();

                            while(var11.hasNext()) {
                                cfgDev = (CfgDev)var11.next();
                                if (cfgDev.getName().equals(setFactorInfo.getInstrument())) {
                                    addFactor.setDevID(cfgDev.getId());
                                    break;
                                }
                            }
                        }

                        addFactor.setDevAddress(setFactorInfo.getAddress());
                        var16 = setFactorInfo.getCheckType();
                        var17 = -1;
                        switch(var16.hashCode()) {
                            case 931076000:
                                if (var16.equals("相对误差")) {
                                    var17 = 0;
                                }
                            default:
                                switch(var17) {
                                    case 0:
                                        addFactor.setCheckType(0);
                                        break;
                                    default:
                                        addFactor.setCheckType(1);
                                }

                                addFactor.setNonNegative(setFactorInfo.isNonNegative());
                                this.cfgMapper.addCfgFactor(addFactor);
                                continue label100;
                        }
                    }
                }
            }
        }
    }

    public boolean activeFactorInfo(HttpServletRequest request, List<SetFactorInfo> setFactorInfoList) {
        this.setFactorInfo(request, setFactorInfoList);
        this.monitorService.restartService();
        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "应用 因子管理");
        return true;
    }

    public SetDeviceParam getDeviceParam() {
        List<CfgDev> cfgDevList = this.cfgMapper.getCfgDevList();
        List<CfgDevParam> cfgDevParamList = this.cfgMapper.getCfgDevParamList();
        List<DeviceParam> devParamList = new ArrayList();
        int id = 1;

        for(Iterator var5 = cfgDevParamList.iterator(); var5.hasNext(); ++id) {
            CfgDevParam cfgDevParam = (CfgDevParam)var5.next();
            DeviceParam deviceParam = new DeviceParam();
            deviceParam.setId(id);
            deviceParam.setParamName(cfgDevParam.getParam_name());
            deviceParam.setRangeMin(cfgDevParam.getRange_min());
            deviceParam.setRangeMax(cfgDevParam.getRange_max());
            deviceParam.setParamMin(cfgDevParam.getParam_min());
            deviceParam.setParamMax(cfgDevParam.getParam_max());
            Iterator var8 = cfgDevList.iterator();

            while(var8.hasNext()) {
                CfgDev cfgDev = (CfgDev)var8.next();
                if (cfgDev.getId() == cfgDevParam.getDev_id()) {
                    deviceParam.setDeviceName(cfgDev.getName());
                    break;
                }
            }

            devParamList.add(deviceParam);
        }

        SetDeviceParam setDeviceParam = new SetDeviceParam();
        int checkParam = (int)ScriptWord.getInstance().getSysVar("CheckParam");
        setDeviceParam.setCheckDeviceParam(checkParam > 0);
        setDeviceParam.setDeviceParamList(devParamList);
        return setDeviceParam;
    }

    public boolean setDeviceParam(HttpServletRequest request, SetDeviceParam setDeviceParam) {
        List<CfgDev> cfgDevList = this.cfgMapper.getCfgDevList();
        this.cfgMapper.truncateCfgDevParam();
        ScriptWord.getInstance().setSysVar("CheckParam", setDeviceParam.isCheckDeviceParam() ? 1.0D : 0.0D);
        Iterator var5 = setDeviceParam.getDeviceParamList().iterator();

        while(var5.hasNext()) {
            DeviceParam deviceParam = (DeviceParam)var5.next();
            int devId = 0;
            Iterator var7 = cfgDevList.iterator();

            while(var7.hasNext()) {
                CfgDev cfgDev = (CfgDev)var7.next();
                if (cfgDev.getName().equals(deviceParam.getDeviceName())) {
                    devId = cfgDev.getId();
                    break;
                }
            }

            CfgDevParam cfgDevParam = new CfgDevParam();
            cfgDevParam.setDev_id(devId);
            cfgDevParam.setParam_name(deviceParam.getParamName());
            cfgDevParam.setParam_min(deviceParam.getParamMin());
            cfgDevParam.setParam_max(deviceParam.getParamMax());
            cfgDevParam.setRange_min(deviceParam.getRangeMin());
            cfgDevParam.setRange_max(deviceParam.getRangeMax());
            this.cfgMapper.addCfgDevParam(cfgDevParam);
        }

        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 关键参数管理");
        this.monitorService.refreshCfg();
        return true;
    }

    public PlcInfo getPlcInfo() {
        List<CfgDev> cfgDevList = this.cfgMapper.getCfgDevList();
        List<CfgSerial> cfgSerialList = this.cfgMapper.getCfgSerialList();
        List<CfgNetClient> cfgNetClientList = this.cfgMapper.getCfgNetClientList();
        Iterator var4 = cfgDevList.iterator();

        CfgDev cfgDev;
        do {
            if (!var4.hasNext()) {
                return null;
            }

            cfgDev = (CfgDev)var4.next();
        } while(!cfgDev.getType().equals("PLC"));

        PlcInfo plcInfo = new PlcInfo();
        Iterator var7 = cfgSerialList.iterator();

        while(var7.hasNext()) {
            CfgSerial cfgSerial = (CfgSerial)var7.next();
            plcInfo.getPlc_interfacelist().add(cfgSerial.getName());
            if (cfgDev.getSerial_id() == cfgSerial.getId()) {
                plcInfo.setPlc_interface(cfgSerial.getName());
            }
        }

        var7 = cfgNetClientList.iterator();

        while(var7.hasNext()) {
            CfgNetClient cfgNetClient = (CfgNetClient)var7.next();
            plcInfo.getPlc_interfacelist().add(cfgNetClient.getName());
            if (cfgDev.getNet_id() == cfgNetClient.getId()) {
                plcInfo.setPlc_interface(cfgNetClient.getName());
            }
        }

        plcInfo.getPlc_protocollist().clear();
        plcInfo.getPlc_protocollist().add("plcModbus");
        plcInfo.getPlc_protocollist().add("plcHostlink");
        plcInfo.getPlc_protocollist().add("plcModbusTcp");
        plcInfo.getPlc_protocollist().add("plcShangYang");
        plcInfo.getPlc_protocollist().add("plcGrean");
        plcInfo.getPlc_protocollist().add("plcJuGuang");
        plcInfo.getPlc_protocollist().add("plcSchneider");
        plcInfo.getPlc_protocollist().add("plcModbusTcpTest");
        plcInfo.getPlc_protocollist().add("plcModbusNsbd");
        plcInfo.getPlc_protocollist().add("plcModbus1200");
        plcInfo.getPlc_protocollist().add("plcFanYiModbus");
        plcInfo.getPlc_protocollist().add("plcFanYiTcp");
        plcInfo.getPlc_protocollist().add("plcWeiQiModbus");
        plcInfo.getPlc_protocollist().add("plcHostlink2");
        plcInfo.getPlc_protocollist().add("plcHostlinkJX");
        plcInfo.setPlc_address(cfgDev.getAddress().intValue());
        plcInfo.setPlc_protocol(cfgDev.getProtocol());
        return plcInfo;
    }

    public boolean setPlcInfo(HttpServletRequest request, PlcInfo plcInfo) {
        List<CfgDev> cfgDevList = this.cfgMapper.getCfgDevList();
        List<CfgSerial> cfgSerialList = this.cfgMapper.getCfgSerialList();
        List<CfgNetClient> cfgNetClientList = this.cfgMapper.getCfgNetClientList();
        Iterator var6 = cfgDevList.iterator();

        while(var6.hasNext()) {
            CfgDev cfgDev = (CfgDev)var6.next();
            if (cfgDev.getType().equals("PLC")) {
                Iterator var8 = cfgSerialList.iterator();

                while(var8.hasNext()) {
                    CfgSerial cfgSerial = (CfgSerial)var8.next();
                    if (cfgSerial.getName().equals(plcInfo.getPlc_interface())) {
                        cfgDev.setSerial_id(cfgSerial.getId());
                        cfgDev.setNet_id(0);
                    }
                }

                var8 = cfgNetClientList.iterator();

                while(var8.hasNext()) {
                    CfgNetClient cfgNetClient = (CfgNetClient)var8.next();
                    if (cfgNetClient.getName().equals(plcInfo.getPlc_interface())) {
                        cfgDev.setNet_id(cfgNetClient.getId());
                        cfgDev.setSerial_id(0);
                    }
                }

                cfgDev.setAddress((long)plcInfo.getPlc_address());
                cfgDev.setProtocol(plcInfo.getPlc_protocol());
                this.cfgMapper.updateCfgDev(cfgDev);
                break;
            }
        }

        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 PLC管理");
        return true;
    }

    public List<PlcQInfo> getPlcQInfo() {
        List<DefWord> defWordList = this.plcMapper.getPLCDefWordList();
        List<PlcQInfo> plcQInfoList = new ArrayList();
        int iKey = 0;
        Iterator var4 = defWordList.iterator();

        while(var4.hasNext()) {
            DefWord defWord = (DefWord)var4.next();
            if (defWord.getType() == 1) {
                PlcQInfo plcQInfo = new PlcQInfo();
                plcQInfo.setKey(iKey);
                plcQInfo.setPname(defWord.getPname());
                plcQInfo.setName(defWord.getName());
                plcQInfo.setAddress(defWord.getAddress());
                if (defWord.getDefvalue() == 0.0F) {
                    plcQInfo.setDefvalue("关闭");
                } else {
                    plcQInfo.setDefvalue("开启");
                }

                plcQInfoList.add(plcQInfo);
                ++iKey;
            }
        }

        return plcQInfoList;
    }

    public boolean setPlcQInfo(HttpServletRequest request, List<PlcQInfo> plcQInfoList) {
        List<DefWord> defWordList = this.plcMapper.getPLCDefWordList();
        this.plcMapper.truncatePLCDefWord();
        int iKey = 1;

        Iterator var5;
        for(var5 = plcQInfoList.iterator(); var5.hasNext(); ++iKey) {
            PlcQInfo plcQInfo = (PlcQInfo)var5.next();
            DefWord defWord = new DefWord();
            defWord.setId(iKey);
            defWord.setName(plcQInfo.getName());
            defWord.setPname(plcQInfo.getPname());
            defWord.setAddress(plcQInfo.getAddress());
            defWord.setType(1);
            defWord.setIsvalve(true);
            if (plcQInfo.getDefvalue().equals("关闭")) {
                defWord.setDefvalue(0.0F);
                defWord.setCurvalue(0.0F);
            } else {
                defWord.setDefvalue(1.0F);
                defWord.setCurvalue(1.0F);
            }

            this.plcMapper.addPLCDefWord(defWord);
        }

        var5 = defWordList.iterator();

        while(var5.hasNext()) {
            DefWord defWord = (DefWord)var5.next();
            if (defWord.getType() != 1) {
                defWord.setId(iKey);
                this.plcMapper.addPLCDefWord(defWord);
                ++iKey;
            }
        }

        this.scriptService.refreshVarList();
        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 输出开关量设置");
        return true;
    }

    public List<PlcQInfo> getPlcIInfo() {
        List<DefWord> defWordList = this.plcMapper.getPLCDefWordList();
        List<PlcQInfo> plcQInfoList = new ArrayList();
        int iKey = 0;
        Iterator var4 = defWordList.iterator();

        while(var4.hasNext()) {
            DefWord defWord = (DefWord)var4.next();
            if (defWord.getType() == 2) {
                PlcQInfo plcQInfo = new PlcQInfo();
                plcQInfo.setKey(iKey);
                plcQInfo.setPname(defWord.getPname());
                plcQInfo.setName(defWord.getName());
                plcQInfo.setAddress(defWord.getAddress());
                plcQInfoList.add(plcQInfo);
                ++iKey;
            }
        }

        return plcQInfoList;
    }

    public boolean setPlcIInfo(HttpServletRequest request, List<PlcQInfo> plcQInfoList) {
        List<DefWord> defWordList = this.plcMapper.getPLCDefWordList();
        this.plcMapper.truncatePLCDefWord();
        int iKey = 1;
        Iterator var5 = defWordList.iterator();

        DefWord defWord;
        while(var5.hasNext()) {
            defWord = (DefWord)var5.next();
            if (defWord.getType() == 1) {
                defWord.setId(iKey);
                this.plcMapper.addPLCDefWord(defWord);
                ++iKey;
            }
        }

        for(var5 = plcQInfoList.iterator(); var5.hasNext(); ++iKey) {
            PlcQInfo plcQInfo = (PlcQInfo)var5.next();
            defWord = new DefWord();
            defWord.setId(iKey);
            defWord.setName(plcQInfo.getName());
            defWord.setPname(plcQInfo.getPname());
            defWord.setAddress(plcQInfo.getAddress());
            defWord.setType(2);
            defWord.setIsvalve(false);
            defWord.setDefvalue(0.0F);
            defWord.setCurvalue(0.0F);
            this.plcMapper.addPLCDefWord(defWord);
        }

        var5 = defWordList.iterator();

        while(var5.hasNext()) {
            defWord = (DefWord)var5.next();
            if (defWord.getType() == 4) {
                defWord.setId(iKey);
                this.plcMapper.addPLCDefWord(defWord);
                ++iKey;
            }
        }

        this.scriptService.refreshVarList();
        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 输入开关量设置");
        return true;
    }

    public List<PlcAIInfo> getPlcAIInfo() {
        List<DefWord> defWordList = this.plcMapper.getPLCDefWordList();
        List<PlcAIInfo> plcAIInfoList = new ArrayList();
        int iKey = 0;
        Iterator var4 = defWordList.iterator();

        while(var4.hasNext()) {
            DefWord defWord = (DefWord)var4.next();
            if (defWord.getType() == 4) {
                PlcAIInfo plcAIInfo = new PlcAIInfo();
                plcAIInfo.setKey(iKey);
                plcAIInfo.setPname(defWord.getPname());
                plcAIInfo.setName(defWord.getName());
                plcAIInfo.setAddress(defWord.getAddress());
                plcAIInfo.setLow(defWord.getDefvalue());
                plcAIInfo.setHigh(defWord.getCurvalue());
                plcAIInfoList.add(plcAIInfo);
                ++iKey;
            }
        }

        return plcAIInfoList;
    }

    public boolean setPlcAIInfo(HttpServletRequest request, List<PlcAIInfo> plcAIInfoList) {
        List<DefWord> defWordList = this.plcMapper.getPLCDefWordList();
        this.plcMapper.truncatePLCDefWord();
        int iKey = 1;
        Iterator var5 = defWordList.iterator();

        while(true) {
            DefWord defWord;
            do {
                if (!var5.hasNext()) {
                    for(var5 = plcAIInfoList.iterator(); var5.hasNext(); ++iKey) {
                        PlcAIInfo plcAIInfo = (PlcAIInfo)var5.next();
                        defWord = new DefWord();
                        defWord.setId(iKey);
                        defWord.setName(plcAIInfo.getName());
                        defWord.setPname(plcAIInfo.getPname());
                        defWord.setAddress(plcAIInfo.getAddress());
                        defWord.setType(4);
                        defWord.setIsvalve(false);
                        defWord.setDefvalue(plcAIInfo.getLow());
                        defWord.setCurvalue(plcAIInfo.getHigh());
                        this.plcMapper.addPLCDefWord(defWord);
                    }

                    this.scriptService.refreshVarList();
                    this.scriptService.refreshMinMax();
                    TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
                    this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 输入模拟量设置");
                    return true;
                }

                defWord = (DefWord)var5.next();
            } while(defWord.getType() != 1 && defWord.getType() != 2);

            defWord.setId(iKey);
            this.plcMapper.addPLCDefWord(defWord);
            ++iKey;
        }
    }

    public List<String> getDataFormatList() {
        List<String> dataFormatList = new ArrayList();
        dataFormatList.add("大端模式");
        dataFormatList.add("小端模式");
        dataFormatList.add("大端交换");
        dataFormatList.add("小端交换");
        dataFormatList.add("BCD码");
        return dataFormatList;
    }

    public ProtocolInfo getProtocolInfo(String protoType) {
        ProtocolInfo protocolInfo = new ProtocolInfo();
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        List<CfgDev> cfgDevList = this.cfgMapper.getCfgDevList();
        List<CfgDevReg> cfgDevRegList = this.cfgMapper.getCfgRegModbusList();
        Iterator var6 = cfgDevList.iterator();

        while(true) {
            CfgDev cfgDev;
            do {
                if (!var6.hasNext()) {
                    var6 = cfgDevRegList.iterator();

                    while(var6.hasNext()) {
                        CfgDevReg cfgDevReg = (CfgDevReg)var6.next();
                        ValueLabelFormat valueLabelFormat = new ValueLabelFormat(cfgDevReg.getId(), cfgDevReg.getName());
                        protocolInfo.getTypeList().add(valueLabelFormat);
                    }

                    for(int i = 1; i < 13; ++i) {
                        ValueLabelFormat valueLabelFormat = new ValueLabelFormat(i, Utility.getTaskName(i));
                        protocolInfo.getCmdList().add(valueLabelFormat);
                    }

                    return protocolInfo;
                }

                cfgDev = (CfgDev)var6.next();
            } while(!cfgDev.getProtocol().equals(protoType));

            ReqDevFactor reqDevFactor = new ReqDevFactor();
            reqDevFactor.setDevName(cfgDev.getName());
            Iterator var9 = cfgFactorList.iterator();

            while(var9.hasNext()) {
                CfgFactor cfgFactor = (CfgFactor)var9.next();
                if (cfgFactor.getDevID() == cfgDev.getId()) {
                    reqDevFactor.getFactorNameList().add(cfgFactor.getName());
                }
            }

            protocolInfo.getDevFactorList().add(reqDevFactor);
        }
    }

    public List<ProtocolQuery> getProtocolQuery(ReqUserModbus reqUserModbus) {
        List<ProtocolQuery> protocolQueryList = new ArrayList();
        List<CfgDevQuery> cfgDevQueryList = this.cfgMapper.getCfgDevQueryList();
        List<CfgDevReg> cfgDevRegList = this.cfgMapper.getCfgRegModbusList();
        int iKey = 0;
        int devID = this.monitorService.getDevID(reqUserModbus.getDevName());
        int factorID = this.monitorService.getFactorID(reqUserModbus.getFactorName());
        Iterator var8 = cfgDevQueryList.iterator();

        while(var8.hasNext()) {
            CfgDevQuery cfgDevQuery = (CfgDevQuery)var8.next();
            if (cfgDevQuery.getProtoType().equals(reqUserModbus.getProtocolName()) && cfgDevQuery.getDevid() == devID && cfgDevQuery.getFactorid() == factorID) {
                ProtocolQuery protocolQuery = new ProtocolQuery();
                protocolQuery.setKey(iKey);
                protocolQuery.setDevName(reqUserModbus.getDevName());
                protocolQuery.setFactorName(reqUserModbus.getFactorName());
                protocolQuery.setProtoType(reqUserModbus.getProtocolName());
                protocolQuery.setQueryType(((CfgDevReg)cfgDevRegList.get(cfgDevQuery.getQueryType() - 1)).getName());
                protocolQuery.setQueryCmd(cfgDevQuery.getQueryCmd());
                protocolQuery.setDataType(cfgDevQuery.getDataType());
                protocolQuery.setDataFormat(cfgDevQuery.getDataFormat());
                protocolQuery.setDataIndex(cfgDevQuery.getDataIndex());
                protocolQuery.setDataLength(cfgDevQuery.getDataLength());
                protocolQuery.setUpdateA(cfgDevQuery.getUpdateA());
                protocolQuery.setUpdateB(cfgDevQuery.getUpdateB());
                protocolQueryList.add(protocolQuery);
                ++iKey;
            }
        }

        return protocolQueryList;
    }

    public boolean setProtocolQuery(HttpServletRequest request, List<ProtocolQuery> protocolQueryList) {
        List<CfgDevReg> cfgDevRegList = this.cfgMapper.getCfgRegModbusList();
        List<CfgDevQuery> cfgDevQueryList = this.cfgMapper.getCfgDevQueryList();
        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 协议管理查询指令");
        if (protocolQueryList.size() <= 0) {
            return false;
        } else {
            int devID = this.monitorService.getDevID(((ProtocolQuery)protocolQueryList.get(0)).getDevName());
            int factorID = this.monitorService.getFactorID(((ProtocolQuery)protocolQueryList.get(0)).getFactorName());
            this.cfgMapper.deleteCfgDevQuery(devID, factorID);
            Iterator var8 = protocolQueryList.iterator();

            while(var8.hasNext()) {
                ProtocolQuery protocolQuery = (ProtocolQuery)var8.next();
                CfgDevQuery cfgDevQuery = new CfgDevQuery();
                cfgDevQuery.setDevid(devID);
                cfgDevQuery.setFactorid(factorID);
                cfgDevQuery.setProtoType(protocolQuery.getProtoType());
                Iterator var11 = cfgDevRegList.iterator();

                while(var11.hasNext()) {
                    CfgDevReg cfgDevReg = (CfgDevReg)var11.next();
                    if (cfgDevReg.getName().equals(protocolQuery.getQueryType())) {
                        cfgDevQuery.setQueryType(cfgDevReg.getId());
                        break;
                    }
                }

                cfgDevQuery.setQueryCmd(protocolQuery.getQueryCmd());
                cfgDevQuery.setDataType(protocolQuery.getDataType());
                cfgDevQuery.setDataFormat(protocolQuery.getDataFormat());
                cfgDevQuery.setDataIndex(protocolQuery.getDataIndex());
                cfgDevQuery.setDataLength(protocolQuery.getDataLength());
                cfgDevQuery.setUpdateA(protocolQuery.getUpdateA());
                cfgDevQuery.setUpdateB(protocolQuery.getUpdateB());
                this.cfgMapper.addCfgDevQuery(cfgDevQuery);
            }

            return true;
        }
    }

    public List<ProtocolCmd> getProtocolCmd(ReqUserModbus reqUserModbus) {
        List<ProtocolCmd> protocolCmdList = new ArrayList();
        List<CfgDevCmd> cfgDevCmdList = this.cfgMapper.getCfgDevCmdList();
        int iKey = 0;
        int devID = this.monitorService.getDevID(reqUserModbus.getDevName());
        int factorID = this.monitorService.getFactorID(reqUserModbus.getFactorName());
        Iterator var7 = cfgDevCmdList.iterator();

        while(var7.hasNext()) {
            CfgDevCmd cfgDevCmd = (CfgDevCmd)var7.next();
            if (cfgDevCmd.getProtoType().equals(reqUserModbus.getProtocolName()) && cfgDevCmd.getDevid() == devID && cfgDevCmd.getCmdType() == reqUserModbus.getCmdType()) {
                ProtocolCmd protocolCmd = new ProtocolCmd();
                protocolCmd.setKey(iKey);
                protocolCmd.setDevName(reqUserModbus.getDevName());
                protocolCmd.setProtoType(reqUserModbus.getProtocolName());
                protocolCmd.setCmdType(reqUserModbus.getCmdType());
                protocolCmd.setCmdIndex(cfgDevCmd.getCmdIndex());
                protocolCmd.setCmdString(cfgDevCmd.getCmdString());
                protocolCmd.setCmdDelay(cfgDevCmd.getCmdDelay());
                protocolCmdList.add(protocolCmd);
                ++iKey;
            }
        }

        return protocolCmdList;
    }

    public boolean setProtocolCmd(HttpServletRequest request, List<ProtocolCmd> protocolCmdList) {
        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 协议管理控制指令");
        if (protocolCmdList.size() <= 0) {
            return false;
        } else {
            int devID = this.monitorService.getDevID(((ProtocolCmd)protocolCmdList.get(0)).getDevName());
            int cmdType = ((ProtocolCmd)protocolCmdList.get(0)).getCmdType();
            this.cfgMapper.deleteCfgDevCmd(devID, cmdType);
            Iterator var6 = protocolCmdList.iterator();

            while(var6.hasNext()) {
                ProtocolCmd protocolCmd = (ProtocolCmd)var6.next();
                CfgDevCmd cfgDevCmd = new CfgDevCmd();
                cfgDevCmd.setDevid(devID);
                cfgDevCmd.setCmdType(cmdType);
                cfgDevCmd.setProtoType(protocolCmd.getProtoType());
                cfgDevCmd.setCmdIndex(protocolCmd.getCmdIndex());
                cfgDevCmd.setCmdString(protocolCmd.getCmdString());
                cfgDevCmd.setCmdDelay(protocolCmd.getCmdDelay());
                this.cfgMapper.addCfgDevCmd(cfgDevCmd);
            }

            return true;
        }
    }

    public List<ReqFiveCheck> getFiveCheck() {
        List<ReqFiveCheck> fiveCheckList = new ArrayList();
        Iterator var4 = this.monitorService.getCfgFactorList().iterator();

        while(var4.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor)var4.next();
            if (cfgFactor.isFiveType()) {
                ReqFiveCheck fiveCheck = new ReqFiveCheck();
                fiveCheck.setFactorName(cfgFactor.getName());
                fiveCheck.setStdValue(cfgFactor.getStdStdVal());
                fiveCheck.setTestValue(cfgFactor.getDataMea());
                String var7 = cfgFactor.getName();
                byte var8 = -1;
                switch(var7.hashCode()) {
                    case 3544:
                        if (var7.equals("pH")) {
                            var8 = 0;
                        }
                        break;
                    case 886901:
                        if (var7.equals("水温")) {
                            var8 = 1;
                        }
                        break;
                    case 891548:
                        if (var7.equals("浊度")) {
                            var8 = 3;
                        }
                        break;
                    case 28358618:
                        if (var7.equals("溶解氧")) {
                            var8 = 2;
                        }
                        break;
                    case 29594368:
                        if (var7.equals("电导率")) {
                            var8 = 4;
                        }
                }

                double dTmp;
                switch(var8) {
                    case 0:
                    case 1:
                    case 2:
                        fiveCheck.setAllowError(cfgFactor.getStdErrorMax() + "");
                        if (cfgFactor.getDataMea() != null) {
                            dTmp = cfgFactor.getDataMea() - cfgFactor.getStdStdVal();
                            fiveCheck.setCurError(String.format("%.3f", dTmp));
                        } else {
                            fiveCheck.setCurError((String)null);
                        }
                        break;
                    case 3:
                    case 4:
                        fiveCheck.setAllowError(cfgFactor.getStdDriftMax() + "%");
                        if (cfgFactor.getDataMea() != null) {
                            dTmp = (cfgFactor.getDataMea() - cfgFactor.getStdStdVal()) / cfgFactor.getStdStdVal() * 100.0D;
                            fiveCheck.setCurError(String.format("%.3f", dTmp) + "%");
                        } else {
                            fiveCheck.setCurError((String)null);
                        }
                        break;
                    default:
                        if (cfgFactor.getCheckType() == 0) {
                            fiveCheck.setAllowError(cfgFactor.getStdDriftMax() + "%");
                            if (cfgFactor.getDataMea() != null) {
                                dTmp = (cfgFactor.getDataMea() - cfgFactor.getStdStdVal()) / cfgFactor.getStdStdVal() * 100.0D;
                                fiveCheck.setCurError(String.format("%.3f", dTmp) + "%");
                            } else {
                                fiveCheck.setCurError((String)null);
                            }
                        } else {
                            fiveCheck.setAllowError(cfgFactor.getStdErrorMax() + "");
                            if (cfgFactor.getDataMea() != null) {
                                dTmp = cfgFactor.getDataMea() - cfgFactor.getStdStdVal();
                                fiveCheck.setCurError(String.format("%.3f", dTmp));
                            } else {
                                fiveCheck.setCurError((String)null);
                            }
                        }
                }

                fiveCheckList.add(fiveCheck);
            }
        }

        return fiveCheckList;
    }

    public boolean setFiveCheck(ReqFiveCheck fiveCheck) {
        return this.monitorService.saveRecCheckFive(fiveCheck);
    }

    public List<ReqStdCheck> getStdCheck() {
        double dTmp;
        Iterator var3;
        CfgFactor cfgFactor;
        if (this.stdCheckList.size() == 0) {
            var3 = this.monitorService.getCfgFactorList().iterator();

            while(true) {
                do {
                    if (!var3.hasNext()) {
                        return this.stdCheckList;
                    }

                    cfgFactor = (CfgFactor)var3.next();
                } while(!cfgFactor.isParmType());

                ReqStdCheck stdCheck = new ReqStdCheck();
                stdCheck.setFactorName(cfgFactor.getName());
                stdCheck.setStdValue(cfgFactor.getStdStdVal());
                stdCheck.setTestValue(cfgFactor.getDataStd());
                Iterator var6 = this.monitorService.getDevServiceList().iterator();

                while(var6.hasNext()) {
                    DevService devService = (DevService)var6.next();
                    if (cfgFactor.getId() == devService.getFactorID()) {
                        stdCheck.setRunState(devService.isRunState());
                        break;
                    }
                }

                if (cfgFactor.getCheckType() == 0) {
                    stdCheck.setAllowError(cfgFactor.getStdDriftMax() + "%");
                    if (cfgFactor.getDataStd() != null) {
                        dTmp = (cfgFactor.getDataStd() - cfgFactor.getStdStdVal()) / cfgFactor.getStdStdVal() * 100.0D;
                        stdCheck.setCurError(String.format("%.3f", dTmp) + "%");
                    } else {
                        stdCheck.setCurError((String)null);
                    }
                } else {
                    stdCheck.setAllowError(cfgFactor.getStdErrorMax() + "");
                    if (cfgFactor.getDataStd() != null) {
                        dTmp = cfgFactor.getDataStd() - cfgFactor.getStdStdVal();
                        stdCheck.setCurError(String.format("%.3f", dTmp));
                    } else {
                        stdCheck.setCurError((String)null);
                    }
                }

                this.stdCheckList.add(stdCheck);
            }
        } else {
            var3 = this.monitorService.getCfgFactorList().iterator();

            while(true) {
                while(var3.hasNext()) {
                    cfgFactor = (CfgFactor)var3.next();
                    Iterator var9 = this.stdCheckList.iterator();

                    while(var9.hasNext()) {
                        ReqStdCheck reqStdCheck = (ReqStdCheck)var9.next();
                        if (reqStdCheck.getFactorName().equals(cfgFactor.getName())) {
                            reqStdCheck.setStdValue(cfgFactor.getStdStdVal());
                            reqStdCheck.setTestValue(cfgFactor.getDataStd());
                            Iterator var11 = this.monitorService.getDevServiceList().iterator();

                            while(var11.hasNext()) {
                                DevService devService = (DevService)var11.next();
                                if (cfgFactor.getId() == devService.getFactorID()) {
                                    reqStdCheck.setRunState(devService.isRunState());
                                    break;
                                }
                            }

                            if (cfgFactor.getCheckType() == 0) {
                                reqStdCheck.setAllowError(cfgFactor.getStdDriftMax() + "%");
                                if (cfgFactor.getDataStd() != null) {
                                    dTmp = (cfgFactor.getDataStd() - cfgFactor.getStdStdVal()) / cfgFactor.getStdStdVal() * 100.0D;
                                    reqStdCheck.setCurError(String.format("%.3f", dTmp) + "%");
                                } else {
                                    reqStdCheck.setCurError((String)null);
                                }
                            } else {
                                reqStdCheck.setAllowError(cfgFactor.getStdErrorMax() + "");
                                if (cfgFactor.getDataStd() != null) {
                                    dTmp = cfgFactor.getDataStd() - cfgFactor.getStdStdVal();
                                    reqStdCheck.setCurError(String.format("%.3f", dTmp));
                                } else {
                                    reqStdCheck.setCurError((String)null);
                                }
                            }
                            break;
                        }
                    }
                }

                return this.stdCheckList;
            }
        }
    }

    public boolean setStdCheck(ReqStdCheck stdCheck) {
        return this.monitorService.saveRecCheckStd(stdCheck);
    }

    public boolean runStdCheck(ReqStdCheck stdCheck) {
        int devID = -1;
        boolean bResult = false;
        Iterator var4 = this.monitorService.getCfgFactorList().iterator();

        CfgFactor cfgFactor;
        while(var4.hasNext()) {
            cfgFactor = (CfgFactor)var4.next();
            if (cfgFactor.getName().equals(stdCheck.getFactorName())) {
                devID = cfgFactor.getDevID();
                bResult = this.monitorService.doDevCmd(devID, 2, "hd");
                break;
            }
        }

        if (devID > 0 && bResult) {
            var4 = this.monitorService.getCfgFactorList().iterator();

            while(true) {
                while(true) {
                    do {
                        if (!var4.hasNext()) {
                            return bResult;
                        }

                        cfgFactor = (CfgFactor)var4.next();
                    } while(cfgFactor.getDevID() != devID);

                    Iterator var6 = this.stdCheckList.iterator();

                    while(var6.hasNext()) {
                        ReqStdCheck reqStdCheck = (ReqStdCheck)var6.next();
                        if (reqStdCheck.getFactorName().equals(cfgFactor.getName())) {
                            reqStdCheck.setRunTime(TimeHelper.getFormatSecondTimestamp());
                            break;
                        }
                    }
                }
            }
        } else {
            return bResult;
        }
    }

    public boolean stopStdCheck(ReqStdCheck stdCheck) {
        Iterator var3 = this.monitorService.getCfgFactorList().iterator();

        while(var3.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor)var3.next();
            if (cfgFactor.getName().equals(stdCheck.getFactorName())) {
                int devID = cfgFactor.getDevID();
                this.monitorService.doDevCmd(devID, 11, "hd");
                break;
            }
        }

        return true;
    }

    public boolean setDevModel(DevModel devModel) {
        int curDevID = 0;
        List<CfgDev> cfgDevList = this.cfgMapper.getCfgDevList();
        Iterator var4 = cfgDevList.iterator();

        while(var4.hasNext()) {
            CfgDev cfgDev = (CfgDev)var4.next();
            if (cfgDev.getName().equals(devModel.getDevName())) {
                curDevID = cfgDev.getId();
                break;
            }
        }

        if (curDevID > 0) {
            String var6 = devModel.getDevMode();
            byte var7 = -1;
            switch(var6.hashCode()) {
                case 664786878:
                    if (var6.equals("受控模式")) {
                        var7 = 3;
                    }
                    break;
                case 669959941:
                    if (var6.equals("周期模式")) {
                        var7 = 1;
                    }
                    break;
                case 727197101:
                    if (var6.equals("定点模式")) {
                        var7 = 2;
                    }
                    break;
                case 770832267:
                    if (var6.equals("手动模式")) {
                        var7 = 4;
                    }
                    break;
                case 1129294877:
                    if (var6.equals("连续模式")) {
                        var7 = 0;
                    }
            }

            switch(var7) {
                case 0:
                    return this.monitorService.doDevCmdParam(curDevID, 14, 1);
                case 1:
                    return this.monitorService.doDevCmdParam(curDevID, 14, 2);
                case 2:
                    return this.monitorService.doDevCmdParam(curDevID, 14, 3);
                case 3:
                    return this.monitorService.doDevCmdParam(curDevID, 14, 4);
                case 4:
                    return this.monitorService.doDevCmdParam(curDevID, 14, 5);
                default:
                    return false;
            }
        } else {
            return true;
        }
    }

    public boolean setDevInterval(DevInterval devInterval) {
        int curDevID = 0;
        List<CfgDev> cfgDevList = this.cfgMapper.getCfgDevList();
        Iterator var4 = cfgDevList.iterator();

        while(var4.hasNext()) {
            CfgDev cfgDev = (CfgDev)var4.next();
            if (cfgDev.getName().equals(devInterval.getDevName())) {
                curDevID = cfgDev.getId();
                break;
            }
        }

        if (curDevID > 0) {
            switch(devInterval.getIntervalType()) {
                case 1:
                    return this.monitorService.doDevCmdParam(curDevID, 15, devInterval.getIntervalVal());
                case 2:
                    return this.monitorService.doDevCmdParam(curDevID, 16, devInterval.getIntervalVal());
                case 3:
                    return this.monitorService.doDevCmdParam(curDevID, 17, devInterval.getIntervalVal());
                case 4:
                    return this.monitorService.doDevCmdParam(curDevID, 18, devInterval.getIntervalVal());
                default:
                    return false;
            }
        } else {
            return true;
        }
    }

    public List<String> getServerNetworkList() {
        List<String> serverList = new ArrayList();
        Iterator var2 = this.monitorService.cfgUploadNetList.iterator();

        while(var2.hasNext()) {
            CfgUploadNet cfgUploadNet = (CfgUploadNet)var2.next();
            if (cfgUploadNet.isUsed()) {
                serverList.add(cfgUploadNet.getName());
            }
        }

        return serverList;
    }

    public List<String> getServerComList() {
        List<String> serverList = new ArrayList();
        Iterator var2 = this.monitorService.cfgUploadComList.iterator();

        while(var2.hasNext()) {
            CfgUploadCom cfgUploadCom = (CfgUploadCom)var2.next();
            if (cfgUploadCom.isUsed()) {
                serverList.add(cfgUploadCom.getName());
            }
        }

        return serverList;
    }

    public List<RcvrType> getRcvrType() {
        List<RcvrType> rcvrTypeList = new ArrayList();
        RcvrType rcvrType1 = new RcvrType();
        rcvrType1.setName("固定加标");
        rcvrType1.setSelect(true);
        rcvrTypeList.add(rcvrType1);
        RcvrType rcvrType2 = new RcvrType();
        rcvrType2.setName("动态加标");
        rcvrType2.setSelect(false);
        rcvrTypeList.add(rcvrType2);
        Iterator var4 = this.monitorService.getCfgFactorList().iterator();

        while(var4.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor)var4.next();
            if (cfgFactor.isUsed() && cfgFactor.isParmType()) {
                switch(cfgFactor.getRcvrType()) {
                    case 0:
                        rcvrType1.setSelect(true);
                        rcvrType2.setSelect(false);
                        return rcvrTypeList;
                    case 1:
                        rcvrType1.setSelect(false);
                        rcvrType2.setSelect(true);
                        return rcvrTypeList;
                    default:
                        return rcvrTypeList;
                }
            }
        }

        return rcvrTypeList;
    }

    public boolean setRcvrType(HttpServletRequest request, List<RcvrType> rcvrTypeList) {
        Iterator var3;
        CfgFactor cfgFactor;
        if (((RcvrType)rcvrTypeList.get(0)).isSelect()) {
            var3 = this.monitorService.getCfgFactorList().iterator();

            while(var3.hasNext()) {
                cfgFactor = (CfgFactor)var3.next();
                cfgFactor.setRcvrType(0);
            }
        } else {
            var3 = this.monitorService.getCfgFactorList().iterator();

            while(var3.hasNext()) {
                cfgFactor = (CfgFactor)var3.next();
                cfgFactor.setRcvrType(1);
            }
        }

        return true;
    }

    public CfgCamera getCameraInfo() {
        CfgCamera cfgCamera = this.cfgMapper.getCfgCamera();
        return cfgCamera;
    }

    public boolean setCameraInfo(HttpServletRequest request, CfgCamera cfgCamera) {
        cfgCamera.setId(1);
        this.cfgMapper.updateCfgCamera(cfgCamera);
        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 监控设置");
        return true;
    }

    public CfgCamera loadCameraInfo() {
        CfgCamera cfgCamera = this.cfgMapper.getCfgCamera();
        Utility.openIEBrowser(cfgCamera.getIp());
        return cfgCamera;
    }

    public CfgFive getFive() {
        CfgFive cfgFive = this.cfgMapper.getCfgFive();
        return cfgFive;
    }

    public boolean setFive(CfgFive cfgFive) {
        this.cfgMapper.updateCfgFive(cfgFive);
        return true;
    }

    public CfgFlow getFlow() {
        CfgFlow cfgFlow = this.cfgMapper.getCfgFlow();
        return cfgFlow;
    }

    public boolean setFlow(HttpServletRequest request, CfgFlow cfgFlow) {
        this.cfgMapper.updateCfgFlow(cfgFlow);
        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 水文设置");
        return true;
    }

    public ReqFiveInfo getFiveInfo() {
        ReqFiveInfo reqFiveInfo = new ReqFiveInfo();
        List<CfgScheduleSample> cfgScheduleFiveList = this.cfgMapper.getCfgScheduleFiveList();
        List<ScheduleFive> scheduleFiveList = new ArrayList();

        for(int i = 0; i < cfgScheduleFiveList.size(); ++i) {
            ScheduleFive scheduleFive = new ScheduleFive();
            scheduleFive.setLabel(String.format("%02d时", i));
            scheduleFive.setValue(i);
            scheduleFive.setChecked(((CfgScheduleSample)cfgScheduleFiveList.get(i)).isRun());
            scheduleFiveList.add(scheduleFive);
        }

        reqFiveInfo.setScheduleFiveList(scheduleFiveList);
        CfgFive cfgFive = this.getFive();
        reqFiveInfo.setSchedule(cfgFive.isIs_schedule());
        reqFiveInfo.setWeek(cfgFive.isIs_week());
        reqFiveInfo.setWeekDay(cfgFive.getWeek_day());
        reqFiveInfo.setStartDate(cfgFive.getStart_date());
        reqFiveInfo.setEndDate(cfgFive.getEnd_date());
        reqFiveInfo.setUrgent(cfgFive.isIs_urgent());
        reqFiveInfo.setUrgentCircle(cfgFive.getUrgent_circle());
        return reqFiveInfo;
    }

    public boolean setFiveInfo(HttpServletRequest request, ReqFiveInfo reqFiveInfo) {
        List<CfgScheduleSample> cfgScheduleFiveList = this.cfgMapper.getCfgScheduleFiveList();

        for(int i = 0; i < cfgScheduleFiveList.size(); ++i) {
            if (((CfgScheduleSample)cfgScheduleFiveList.get(i)).isRun() != ((ScheduleFive)reqFiveInfo.getScheduleFiveList().get(i)).isChecked()) {
                ((CfgScheduleSample)cfgScheduleFiveList.get(i)).setRun(((ScheduleFive)reqFiveInfo.getScheduleFiveList().get(i)).isChecked());
                this.cfgMapper.updateCfgScheduleFive((CfgScheduleSample)cfgScheduleFiveList.get(i));
            }
        }

        CfgFive cfgFive = new CfgFive();
        cfgFive.setId(1);
        cfgFive.setIs_schedule(reqFiveInfo.isSchedule());
        cfgFive.setIs_week(reqFiveInfo.isWeek());
        cfgFive.setWeek_day(reqFiveInfo.getWeekDay());
        cfgFive.setStart_date(reqFiveInfo.getStartDate());
        cfgFive.setEnd_date(reqFiveInfo.getEndDate());
        cfgFive.setIs_urgent(reqFiveInfo.isUrgent());
        cfgFive.setUrgent_circle(reqFiveInfo.getUrgentCircle());
        this.setFive(cfgFive);
        this.monitorService.setCheckFiveInfo(reqFiveInfo);
        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 五参设置");
        return true;
    }

    public ReqScheduleQuality getScheduleQuality() {
        ReqScheduleQuality reqScheduleQuality = new ReqScheduleQuality();
        List<CfgScheduleQuality> cfgScheduleQualityList = this.cfgMapper.getCfgScheduleQualityList();
        Iterator var3 = cfgScheduleQualityList.iterator();

        while(var3.hasNext()) {
            CfgScheduleQuality cfgScheduleQuality = (CfgScheduleQuality)var3.next();
            DateTime dateTime = new DateTime(2022, 1, 1, cfgScheduleQuality.getHour(), cfgScheduleQuality.getMin(), cfgScheduleQuality.getSec());
            String var6 = cfgScheduleQuality.getName();
            byte var7 = -1;
            switch(var6.hashCode()) {
                case 1106386155:
                    if (var6.equals("跨度核查")) {
                        var7 = 1;
                    }
                    break;
                case 1179888240:
                    if (var6.equals("零点核查")) {
                        var7 = 0;
                    }
            }

            switch(var7) {
                case 0:
                    reqScheduleQuality.setZeroSwitch(cfgScheduleQuality.isRun());
                    reqScheduleQuality.setZeroTime(new Timestamp(dateTime.getMillis()));
                    break;
                case 1:
                    reqScheduleQuality.setSpanSwitch(cfgScheduleQuality.isRun());
                    reqScheduleQuality.setSpanTime(new Timestamp(dateTime.getMillis()));
            }
        }

        return reqScheduleQuality;
    }

    public boolean setScheduleQuality(HttpServletRequest request, ReqScheduleQuality reqScheduleQuality) {
        CfgScheduleQuality cfgScheduleZero = new CfgScheduleQuality();
        cfgScheduleZero.setId(1);
        cfgScheduleZero.setName("零点核查");
        cfgScheduleZero.setRun(reqScheduleQuality.isZeroSwitch());
        cfgScheduleZero.setHour((new DateTime(reqScheduleQuality.getZeroTime().getTime())).getHourOfDay());
        cfgScheduleZero.setMin((new DateTime(reqScheduleQuality.getZeroTime().getTime())).getMinuteOfHour());
        cfgScheduleZero.setSec((new DateTime(reqScheduleQuality.getZeroTime().getTime())).getSecondOfMinute());
        this.cfgMapper.updateCfgScheduleQuality(cfgScheduleZero);
        CfgScheduleQuality cfgScheduleSpan = new CfgScheduleQuality();
        cfgScheduleSpan.setId(2);
        cfgScheduleSpan.setName("跨度核查");
        cfgScheduleSpan.setRun(reqScheduleQuality.isSpanSwitch());
        cfgScheduleSpan.setHour((new DateTime(reqScheduleQuality.getSpanTime().getTime())).getHourOfDay());
        cfgScheduleSpan.setMin((new DateTime(reqScheduleQuality.getSpanTime().getTime())).getMinuteOfHour());
        cfgScheduleSpan.setSec((new DateTime(reqScheduleQuality.getSpanTime().getTime())).getSecondOfMinute());
        this.cfgMapper.updateCfgScheduleQuality(cfgScheduleSpan);
        List<CfgScheduleQuality> cfgScheduleQualityList = this.cfgMapper.getCfgScheduleQualityList();
        this.monitorService.setCfgScheduleQualityList(cfgScheduleQualityList);
        TokenUser tokenUser = this.userService.getTokenUser(request.getHeader("token"));
        this.monitorService.saveRecLogUser(tokenUser.getName(), "保存 定时质控设置");
        return true;
    }

    public EncryptionCode getEncryptionCode(String protoType) {
        EncryptionCode encryptionCode = new EncryptionCode();
        encryptionCode.setEnCode(CrcUtil.getMD5(protoType, "abc123", 6));
        return encryptionCode;
    }
}
