//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.query;

import com.grean.station.dao.PLCMapper;
import com.grean.station.domain.DO.cfg.CfgDev;
import com.grean.station.domain.DO.cfg.CfgDevAlarm;
import com.grean.station.domain.DO.cfg.CfgDevReg;
import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.plc.StatusWord;
import com.grean.station.domain.DO.plc.VarWord;
import com.grean.station.domain.request.ReqDevInfo;
import com.grean.station.domain.request.ReqFactorInfo;
import com.grean.station.domain.request.ReqParamInfo;
import com.grean.station.domain.request.ResetTimer;
import com.grean.station.domain.request.RtdData;
import com.grean.station.domain.request.RtdStatus;
import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.service.dev.flow.DevFlowLs;
import com.grean.station.service.script.ScriptService;
import com.grean.station.service.script.ScriptWord;
import com.grean.station.utils.TimeHelper;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RealTimeService {
    @Autowired
    PLCMapper plcMapper;
    @Autowired
    MonitorService monitorService;
    @Autowired
    ScriptService scriptService;
    @Value("${warn_input:}")
    private String warn_input;
    @Value("${warn_show:false}")
    private Boolean warn_show;

    public RealTimeService() {
    }

    public List<RtdData> getClientRtdData() {
        List<RtdData> rtdDataList = new ArrayList();
        Iterator var3 = this.monitorService.getCfgFactorList().iterator();

        while(var3.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor)var3.next();
            if (cfgFactor.isUsed()) {
                String var5 = cfgFactor.getType().toUpperCase();
                byte var6 = -1;
                switch(var5.hashCode()) {
                    case 2660:
                        if (var5.equals("SW")) {
                            var6 = 1;
                        }
                        break;
                    case 79303:
                        if (var5.equals("PLC")) {
                            var6 = 3;
                        }
                        break;
                    case 82605:
                        if (var5.equals("SYS")) {
                            var6 = 2;
                        }
                        break;
                    case 2158258:
                        if (var5.equals("FIVE")) {
                            var6 = 0;
                        }
                        break;
                    case 2448364:
                        if (var5.equals("PARM")) {
                            var6 = 4;
                        }
                }

                RtdData rtdData;
                switch(var6) {
                    case 0:
                    case 1:
                        rtdData = new RtdData();
                        rtdData.setName(cfgFactor.getName());
                        if (cfgFactor.getDataMea() == null) {
                            rtdData.setValue(0.0D);
                        } else {
                            rtdData.setValue(cfgFactor.getDataMea());
                        }

                        rtdData.setUnit(cfgFactor.getUnit());
                        rtdData.setType(1);
                        rtdData.setFormatValue(String.format("%." + cfgFactor.getDecimals() + "f", rtdData.getValue()));
                        rtdDataList.add(rtdData);
                        break;
                    case 2:
                        rtdData = new RtdData();
                        rtdData.setName(cfgFactor.getName());
                        if (cfgFactor.getDataMea() == null) {
                            rtdData.setValue(0.0D);
                        } else {
                            rtdData.setValue(cfgFactor.getDataMea());
                        }

                        rtdData.setUnit(cfgFactor.getUnit());
                        rtdData.setType(2);
                        rtdData.setFormatValue(String.format("%." + cfgFactor.getDecimals() + "f", rtdData.getValue()));
                        rtdDataList.add(rtdData);
                        break;
                    case 3:
                    case 4:
                        rtdData = new RtdData();
                        rtdData.setName(cfgFactor.getName());
                        switch(this.monitorService.getScriptRunCmd()) {
                            case 2:
                                rtdData.setValue(cfgFactor.getDataStd());
                                break;
                            case 3:
                                rtdData.setValue(cfgFactor.getDataZero());
                                break;
                            case 4:
                                rtdData.setValue(cfgFactor.getDataSpan());
                                break;
                            case 5:
                                rtdData.setValue(cfgFactor.getDataBlnk());
                                break;
                            case 6:
                                rtdData.setValue(cfgFactor.getDataPar());
                                break;
                            case 7:
                                rtdData.setValue(cfgFactor.getDataRcvr());
                                break;
                            default:
                                if (cfgFactor.getDataMea() == null) {
                                    rtdData.setValue(0.0D);
                                } else {
                                    rtdData.setValue(cfgFactor.getDataMea());
                                }
                        }

                        rtdData.setUnit(cfgFactor.getUnit());
                        rtdData.setType(1);
                        rtdData.setFormatValue(String.format("%." + cfgFactor.getDecimals() + "f", rtdData.getValue()));
                        rtdDataList.add(rtdData);
                }
            }
        }

        return rtdDataList;
    }
    @SuppressWarnings("FallThrough")
    public RtdStatus getClientRtdStatus() {
        RtdStatus rtdStatus = new RtdStatus();
        List<VarWord> varWordList = this.plcMapper.getPLCVarWordList();
        Iterator var3 = varWordList.iterator();

        while(var3.hasNext()) {
            VarWord varWord = (VarWord)var3.next();
            String var5 = varWord.getEnName();
            byte var6 = -1;
            switch(var5.hashCode()) {
                case -1080507730:
                    if (var5.equals("RunMode")) {
                        var6 = 0;
                    }
                    break;
                case 423233257:
                    if (var5.equals("PumpState")) {
                        var6 = 1;
                    }
                    break;
                case 1676335545:
                    if (var5.equals("PumpWork")) {
                        var6 = 2;
                    }
            }

            switch(var6) {
                case 0:
                    switch((int)varWord.getCurValue()) {
                        case 0:
                            rtdStatus.setStation_mode("维护模式");
                            continue;
                        case 1:
                            rtdStatus.setStation_mode("常规模式");
                            continue;
                        case 2:
                            rtdStatus.setStation_mode("应急模式");
                            continue;
                        case 3:
                            rtdStatus.setStation_mode("质控模式");
                        default:
                            continue;
                    }
                case 1:
                    switch((int)varWord.getCurValue()) {
                        case 1:
                            rtdStatus.setPump_mode("只用泵一");
                            continue;
                        case 2:
                            rtdStatus.setPump_mode("只用泵二");
                            continue;
                        case 3:
                            rtdStatus.setPump_mode("双泵交替");
                        default:
                            continue;
                    }
                case 2:
                    switch((int)varWord.getCurValue()) {
                        case 1:
                            rtdStatus.setCurrent_pump("泵一");
                            break;
                        case 2:
                            rtdStatus.setCurrent_pump("泵二");
                    }
            }
        }

        rtdStatus.setCurrent_status(this.monitorService.getSysStatus());
        rtdStatus.setStart_time(this.monitorService.getSysRunTime());
        StatusWord statusWord = ScriptWord.getInstance().getStatusWord(this.monitorService.getSysStatus());
        if (statusWord == null) {
            rtdStatus.setPic_name1("00待机.bmp");
            rtdStatus.setPic_name2("00待机.bmp");
        } else {
            rtdStatus.setPic_name1(statusWord.getPic1());
            rtdStatus.setPic_name2(statusWord.getPic2());
        }

        rtdStatus.setWarn_show(this.warn_show);
        rtdStatus.setWarn_name(this.scriptService.getIName(this.warn_input));
        rtdStatus.setWarn_status(this.scriptService.getIStatus(this.warn_input));
        return rtdStatus;
    }

    public List<ReqDevInfo> getClientRtdInfo() {
        List<ReqDevInfo> rtdInfoList = new ArrayList();
        Iterator var3 = this.monitorService.getCfgDevList().iterator();

        label210:
        while(true) {
            CfgDev cfgDev;
            do {
                if (!var3.hasNext()) {
                    return rtdInfoList;
                }

                cfgDev = (CfgDev)var3.next();
            } while(!cfgDev.getType().toUpperCase().equals("FIVE") && !cfgDev.getType().toUpperCase().equals("PARM"));

            ReqDevInfo devInfo = new ReqDevInfo();
            devInfo.setDevName(cfgDev.getName());
            Iterator var6 = this.monitorService.getCfgFactorList().iterator();

            while(true) {
                CfgFactor cfgFactor;
                do {
                    if (!var6.hasNext()) {
                        var6 = this.monitorService.getDevServiceList().iterator();

                        label207:
                        while(var6.hasNext()) {
                            DevService devService = (DevService)var6.next();
                            if (devService.getDevID() == cfgDev.getId() && devService.getmRegList() != null) {
                                Integer iTmp = (Integer)devService.getParam("DevState");
                                if (iTmp != null) {
                                    devInfo.setDevStatus(Utility.getTaskName(iTmp));
                                } else {
                                    devInfo.setDevStatus((String)null);
                                }

                                iTmp = (Integer)devService.getParam("DevMode");
                                if (iTmp != null) {
                                    devInfo.setDevMode(Utility.getDevMode(iTmp));
                                } else {
                                    devInfo.setDevMode((String)null);
                                }

                                iTmp = (Integer)devService.getParam("DevAlarm");
                                if (iTmp != null) {
                                    boolean devAlarm = false;
                                    Iterator var9 = this.monitorService.getCfgDevAlarmList().iterator();

                                    CfgDevAlarm cfgDevAlarm;
                                    while(var9.hasNext()) {
                                        cfgDevAlarm = (CfgDevAlarm)var9.next();
                                        if (cfgDevAlarm.getDev_id() == cfgDev.getId() && cfgDevAlarm.getAlarm_id() == iTmp) {
                                            devInfo.setDevWarn(cfgDevAlarm.getAlarm_desc());
                                            devAlarm = true;
                                            break;
                                        }
                                    }

                                    if (!devAlarm) {
                                        var9 = this.monitorService.getCfgDevAlarmList().iterator();

                                        while(var9.hasNext()) {
                                            cfgDevAlarm = (CfgDevAlarm)var9.next();
                                            if (cfgDevAlarm.getDev_id() == 0 && cfgDevAlarm.getAlarm_id() == iTmp) {
                                                devInfo.setDevWarn(cfgDevAlarm.getAlarm_desc());
                                                break;
                                            }
                                        }
                                    }
                                }

                                if (devInfo.getDevWarn() != null) {
                                    if (devInfo.getDevWarn().equals("自定义告警")) {
                                        devInfo.setWarnInfoList(devService.getWarnList());
                                    } else {
                                        devInfo.getWarnInfoList().add(devInfo.getDevWarn());
                                    }
                                }

                                devInfo.setRunState(devService.isRunState());
                                devInfo.setFaultState(devService.isFaultState());
                                ReqParamInfo paramInfo;
                                int i;
                                CfgDevReg cfgDevReg;
                                if (devService.getClass().equals(DevFlowLs.class)) {
                                    i = 0;

                                    while(true) {
                                        if (i >= devService.getmRegList().size()) {
                                            break label207;
                                        }

                                        cfgDevReg = (CfgDevReg)devService.getmRegList().get(i);
                                        paramInfo = new ReqParamInfo();
                                        paramInfo.setParamName(cfgDevReg.getName());
                                        Double oValue = (Double)devService.getParam(i);
                                        if (oValue == null) {
                                            paramInfo.setParamValue("");
                                        } else {
                                            paramInfo.setParamValue(Double.toString(oValue));
                                        }

                                        devInfo.getParamInfoList().add(paramInfo);
                                        ++i;
                                    }
                                }

                                i = 35;

                                while(true) {
                                    if (i >= devService.getmRegList().size()) {
                                        break label207;
                                    }

                                    cfgDevReg = (CfgDevReg)devService.getmRegList().get(i);
                                    paramInfo = new ReqParamInfo();
                                    paramInfo.setParamName(cfgDevReg.getName());
                                    Object oValue = devService.getParam(cfgDevReg.getCode());
                                    if (oValue == null) {
                                        paramInfo.setParamValue((String)null);
                                    } else {
                                        String var13 = cfgDevReg.getType().toLowerCase();
                                        byte var14 = -1;
                                        switch(var13.hashCode()) {
                                            case 103195:
                                                if (var13.equals("hex")) {
                                                    var14 = 5;
                                                }
                                                break;
                                            case 3052374:
                                                if (var13.equals("char")) {
                                                    var14 = 4;
                                                }
                                                break;
                                            case 3076014:
                                                if (var13.equals("date")) {
                                                    var14 = 3;
                                                }
                                                break;
                                            case 3655434:
                                                if (var13.equals("word")) {
                                                    var14 = 1;
                                                }
                                                break;
                                            case 96007534:
                                                if (var13.equals("dword")) {
                                                    var14 = 0;
                                                }
                                                break;
                                            case 97526364:
                                                if (var13.equals("float")) {
                                                    var14 = 2;
                                                }
                                        }

                                        switch(var14) {
                                            case 0:
                                            case 1:
                                                paramInfo.setParamValue(Integer.toString((Integer)oValue));
                                                break;
                                            case 2:
                                                paramInfo.setParamValue(Double.toString((Double)oValue));
                                                break;
                                            case 3:
                                                paramInfo.setParamValue(TimeHelper.formatDataTime_yyyy_MM_dd_HH_mm_ss((Timestamp)oValue));
                                                break;
                                            case 4:
                                                paramInfo.setParamValue((String)oValue);
                                                break;
                                            case 5:
                                                paramInfo.setParamValue((String)oValue);
                                        }
                                    }

                                    devInfo.getParamInfoList().add(paramInfo);
                                    ++i;
                                }
                            }
                        }

                        rtdInfoList.add(devInfo);
                        continue label210;
                    }

                    cfgFactor = (CfgFactor)var6.next();
                } while(cfgFactor.getDevID() != cfgDev.getId());

                ReqFactorInfo factorInfo = new ReqFactorInfo();
                factorInfo.setFactorName(cfgFactor.getName());
                factorInfo.setFactorUnit(cfgFactor.getUnit());
                factorInfo.setFactorValue(cfgFactor.getDataMea());
                if (cfgFactor.isFiveType()) {
                    factorInfo.setFactorValue(cfgFactor.getDataMea());
                } else {
                    switch(this.monitorService.getScriptRunCmd()) {
                        case 2:
                            factorInfo.setFactorValue(cfgFactor.getDataStd());
                            break;
                        case 3:
                            factorInfo.setFactorValue(cfgFactor.getDataZero());
                            break;
                        case 4:
                            factorInfo.setFactorValue(cfgFactor.getDataSpan());
                            break;
                        case 5:
                            factorInfo.setFactorValue(cfgFactor.getDataBlnk());
                            break;
                        case 6:
                            factorInfo.setFactorValue(cfgFactor.getDataPar());
                            break;
                        case 7:
                            factorInfo.setFactorValue(cfgFactor.getDataRcvr());
                            break;
                        default:
                            factorInfo.setFactorValue(cfgFactor.getDataMea());
                    }
                }

                if (cfgFactor.getLevel1() == 0.0D && cfgFactor.getLevel2() == 0.0D && cfgFactor.getLevel3() == 0.0D) {
                    factorInfo.setFactorType("-");
                } else if (factorInfo.getFactorValue() == null) {
                    factorInfo.setFactorType("-");
                } else if (factorInfo.getFactorName().equals("溶解氧")) {
                    if (factorInfo.getFactorValue() >= cfgFactor.getLevel1()) {
                        factorInfo.setFactorType("Ⅰ");
                    } else if (factorInfo.getFactorValue() >= cfgFactor.getLevel2()) {
                        factorInfo.setFactorType("Ⅱ");
                    } else if (factorInfo.getFactorValue() >= cfgFactor.getLevel3()) {
                        factorInfo.setFactorType("Ⅲ");
                    } else if (factorInfo.getFactorValue() >= cfgFactor.getLevel4()) {
                        factorInfo.setFactorType("Ⅳ");
                    } else {
                        factorInfo.setFactorType("Ⅴ");
                    }
                } else if (factorInfo.getFactorValue() <= cfgFactor.getLevel1()) {
                    factorInfo.setFactorType("Ⅰ");
                } else if (factorInfo.getFactorValue() <= cfgFactor.getLevel2()) {
                    factorInfo.setFactorType("Ⅱ");
                } else if (factorInfo.getFactorValue() <= cfgFactor.getLevel3()) {
                    factorInfo.setFactorType("Ⅲ");
                } else if (factorInfo.getFactorValue() <= cfgFactor.getLevel4()) {
                    factorInfo.setFactorType("Ⅳ");
                } else {
                    factorInfo.setFactorType("Ⅴ");
                }

                devInfo.getFactorInfoList().add(factorInfo);
            }
        }
    }

    public ResetTimer getResetStatus() {
        return this.monitorService.getResetTimer();
    }

    public boolean cancelResetStatus() {
        this.monitorService.getResetTimer().setRunning(false);
        return true;
    }
}
