//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.query;

import com.grean.station.dao.CfgMapper;
import com.grean.station.dao.PLCMapper;
import com.grean.station.domain.DO.cfg.CfgDev;
import com.grean.station.domain.DO.cfg.CfgDevReg;
import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.plc.DefWord;
import com.grean.station.domain.request.DevResult;
import com.grean.station.domain.request.QualityInfo;
import com.grean.station.domain.request.QualityParamInfo;
import com.grean.station.domain.request.ReqCmdInfo;
import com.grean.station.domain.request.ReqParamInfo;
import com.grean.station.domain.request.ReqPointInfo;
import com.grean.station.domain.request.ReqQualityCmd;
import com.grean.station.domain.request.ReqVersion;
import com.grean.station.domain.request.StationInfo;
import com.grean.station.exception.ErrorCode;
import com.grean.station.exception.ServerException;
import com.grean.station.service.MonitorService;
import com.grean.station.service.SendLogService;
import com.grean.station.service.dev.DevService;
import com.grean.station.service.script.ScriptService;
import com.grean.station.service.script.ScriptWord;
import com.grean.station.utils.Utility;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class StationInfoService {
    @Autowired
    CfgMapper cfgMapper;
    @Autowired
    PLCMapper plcMapper;
    @Autowired
    MonitorService monitorService;
    @Autowired
    ScriptService scriptService;
    @Autowired
    SendLogService sendLogService;
    private List<CfgDev> cfgDevList;

    public StationInfoService() {
    }

    @PostConstruct
    public void Init() {
        this.cfgDevList = this.cfgMapper.getCfgDevList();
    }

    public DevResult doStationCmd(Integer cmdType) {
        DevResult devResult = new DevResult();
        this.sendLogService.postdLog(Utility.getTaskName(cmdType));
        if (this.scriptService.isOnScript() && cmdType != 11) {
            throw new ServerException(ErrorCode.FIELD_ERROR.getCode(), "系统正在运行，本次操作未能执行");
        } else {
            boolean bResult;
            if (cmdType <= 60) {
                this.monitorService.initScriptFactorList((Integer)null);
                this.monitorService.setUserStart(true);
                bResult = this.monitorService.startTest(cmdType, "hd");
            } else {
                switch(cmdType) {
                    case 101:
                        bResult = this.monitorService.doAlgClean();
                        break;
                    case 102:
                        bResult = this.monitorService.doSysClean();
                        break;
                    case 103:
                        bResult = this.monitorService.doInPipeClean();
                        break;
                    case 104:
                        bResult = this.monitorService.doOutPipeClean();
                        break;
                    case 105:
                        bResult = this.monitorService.doFilterClean();
                        break;
                    case 106:
                        bResult = this.monitorService.doSandClean();
                        break;
                    case 107:
                        bResult = this.monitorService.doFiveClean();
                        break;
                    default:
                        bResult = false;
                }
            }

            devResult.setResult(bResult);
            return devResult;
        }
    }
    @SuppressWarnings("FallThrough")
    public StationInfo getStationInfo() {
        StationInfo stationInfo = new StationInfo();
        Iterator var2 = this.cfgDevList.iterator();

        while(var2.hasNext()) {
            CfgDev cfgDev = (CfgDev)var2.next();
            boolean bFirst = true;
            Iterator var5 = stationInfo.getDevNameList().iterator();

            while(var5.hasNext()) {
                String devName = (String)var5.next();
                if (devName.equals(cfgDev.getNick())) {
                    bFirst = false;
                    break;
                }
            }

            if (bFirst) {
                stationInfo.getDevNameList().add(cfgDev.getNick());
            }
        }

        boolean bConnect;
        for(var2 = stationInfo.getDevNameList().iterator(); var2.hasNext(); stationInfo.getDevStatusList().add(bConnect)) {
            String devName = (String)var2.next();
            int curDevID = 0;
            bConnect = false;
            Iterator var12 = this.cfgDevList.iterator();

            while(var12.hasNext()) {
                CfgDev cfgDev = (CfgDev)var12.next();
                if (cfgDev.getNick().equals(devName)) {
                    curDevID = cfgDev.getId();
                    break;
                }
            }

            if (curDevID > 0) {
                var12 = this.monitorService.getDevServiceList().iterator();

                while(var12.hasNext()) {
                    DevService devService = (DevService)var12.next();
                    if (devService.getDevID() == curDevID) {
                        bConnect = devService.isbConnect();
                        break;
                    }
                }
            }

            if (devName.equals("PLC")) {
                bConnect = this.scriptService.isbConnect();
            }
        }

        var2 = this.scriptService.getDefWordList().iterator();

        while(var2.hasNext()) {
            DefWord defWord = (DefWord)var2.next();
            switch(defWord.getType()) {
                case 1:
                    stationInfo.getPlcQNameList().add(defWord.getPname());
                    stationInfo.getPlcQNickList().add(defWord.getName());
                    stationInfo.getPlcQAddressList().add(defWord.getAddress());
                    stationInfo.getPlcQStatusList().add(this.scriptService.getQStatus(defWord.getAddress()));
                    stationInfo.getPlcQIDList().add(defWord.getId() - 1);
                    break;
                case 2:
                    stationInfo.getPlcINameList().add(defWord.getPname());
                    stationInfo.getPlcINickList().add(defWord.getName());
                    stationInfo.getPlcIAddressList().add(defWord.getAddress());
                    stationInfo.getPlcIStatusList().add(this.scriptService.getIStatus(defWord.getAddress()));
                case 3:
                default:
                    break;
                case 4:
                    stationInfo.getPlcAINameList().add(defWord.getPname());
                    stationInfo.getPlcAINickList().add(defWord.getName());
                    stationInfo.getPlcAIValueList().add(Utility.getFormatFloat(this.scriptService.getAIValues(defWord.getAddress()), 4) + "");
            }
        }

        return stationInfo;
    }

    public ReqVersion getVersionInfo() {
        ReqVersion reqVersion = new ReqVersion();
        reqVersion.setVersionName("V2.004 (20230927)");

        try {
            File snFile = ResourceUtils.getFile("classpath:scripts/version.txt");
            List<String> inputList = FileUtils.readLines(snFile, "UTF-8");
            if (inputList.size() > 0) {
                reqVersion.setTelephone((String)inputList.get(0));
            } else {
                reqVersion.setTelephone("400-067-6616    0571-28171902");
            }
        } catch (Exception var4) {
            reqVersion.setTelephone("400-067-6616    0571-28171902");
        }

        return reqVersion;
    }

    public List<QualityInfo> getQualityInfo() {
        List<QualityInfo> qualityInfoList = new ArrayList();
        Iterator var2 = this.monitorService.getDevServiceList().iterator();

        while(true) {
            while(true) {
                while(var2.hasNext()) {
                    DevService devService = (DevService)var2.next();
                    QualityInfo qualityInfo;
                    ArrayList paramInfoList;
                    int i;
                    CfgDevReg tmpReg;
                    QualityParamInfo paramInfo;
                    Integer tmpValue;
                    ArrayList stateInfoList;
                    ArrayList cmdInfoList;
                    ReqCmdInfo reqCmdInfo;
                    ReqPointInfo pointInfo;
                    if (!devService.getDevProtocol().equals("QualityModbus") && !devService.getDevProtocol().equals("QualityModbus1")) {
                        if (devService.getDevProtocol().equals("QualityModbus3")) {
                            qualityInfo = new QualityInfo();
                            qualityInfo.setQualityName(devService.getDevName());
                            qualityInfo.setQualityType(devService.getDevProtocol());
                            paramInfoList = new ArrayList();
                            QualityParamInfo qualityParamInfo = new QualityParamInfo("润洗体积", "20", "ml");
                            tmpReg = (CfgDevReg)devService.mRegList.get(32);
                            tmpValue = (Integer)devService.getParam(tmpReg.getCode());
                            if (tmpValue != null) {
                                qualityParamInfo.setParamValue(tmpValue + "");
                            }

                            paramInfoList.add(qualityParamInfo);
                            qualityParamInfo = new QualityParamInfo("关阀延时", "1800", "s");
                            tmpReg = (CfgDevReg)devService.mRegList.get(33);
                            tmpValue = (Integer)devService.getParam(tmpReg.getCode());
                            if (tmpValue != null) {
                                qualityParamInfo.setParamValue(tmpValue + "");
                            }

                            paramInfoList.add(qualityParamInfo);
                            qualityParamInfo = new QualityParamInfo("泵测试体积", "2000", "ul");
                            tmpReg = (CfgDevReg)devService.mRegList.get(30);
                            tmpValue = (Integer)devService.getParam(tmpReg.getCode());
                            if (tmpValue != null) {
                                qualityParamInfo.setParamValue(tmpValue + "");
                            }

                            paramInfoList.add(qualityParamInfo);
                            qualityParamInfo = new QualityParamInfo("泵测试时间", "20", "s");
                            tmpReg = (CfgDevReg)devService.mRegList.get(31);
                            tmpValue = (Integer)devService.getParam(tmpReg.getCode());
                            if (tmpValue != null) {
                                qualityParamInfo.setParamValue(tmpValue + "");
                            }

                            paramInfoList.add(qualityParamInfo);
                            qualityInfo.setParamInfoList(paramInfoList);
                            stateInfoList = new ArrayList();

                            for(i = 0; i < devService.mRegList.size(); ++i) {
                                if (i == 24 || i == 25 || i == 26 || i == 27) {
                                    tmpReg = (CfgDevReg)devService.mRegList.get(i);
                                    ReqParamInfo stateInfo = new ReqParamInfo();
                                    stateInfo.setParamName(tmpReg.getName());
                                    tmpValue = (Integer)devService.getParam(tmpReg.getCode());
                                    if (tmpValue != null) {
                                        if (tmpValue == 1) {
                                            stateInfo.setParamValue("有水");
                                            stateInfo.setParamColor("rgb(19, 161, 14)");
                                        } else {
                                            stateInfo.setParamValue("无水");
                                            stateInfo.setParamColor("grey");
                                        }
                                    }

                                    stateInfoList.add(stateInfo);
                                }
                            }

                            qualityInfo.setStateInfoList(stateInfoList);
                            cmdInfoList = new ArrayList();
                            reqCmdInfo = new ReqCmdInfo("停止仪器", 0);
                            cmdInfoList.add(reqCmdInfo);
                            reqCmdInfo = new ReqCmdInfo("加标回收", 3);
                            cmdInfoList.add(reqCmdInfo);
                            reqCmdInfo = new ReqCmdInfo("生产测试", 4);
                            cmdInfoList.add(reqCmdInfo);
                            reqCmdInfo = new ReqCmdInfo("仪器老化", 5);
                            cmdInfoList.add(reqCmdInfo);
                            reqCmdInfo = new ReqCmdInfo("系统排空", 6);
                            cmdInfoList.add(reqCmdInfo);
                            reqCmdInfo = new ReqCmdInfo("系统清洗", 7);
                            cmdInfoList.add(reqCmdInfo);
                            reqCmdInfo = new ReqCmdInfo("试剂填充", 8);
                            cmdInfoList.add(reqCmdInfo);
                            reqCmdInfo = new ReqCmdInfo("备用", 9);
                            cmdInfoList.add(reqCmdInfo);
                            reqCmdInfo = new ReqCmdInfo("泵 测 试", 2);
                            cmdInfoList.add(reqCmdInfo);
                            reqCmdInfo = new ReqCmdInfo("泵 校 准", 1);
                            cmdInfoList.add(reqCmdInfo);
                            qualityInfo.setCmdInfoList(cmdInfoList);
                            List<ReqPointInfo> pointInfoList = new ArrayList();

                            for(i = 0; i < devService.mRegList.size(); ++i) {
                                if (i >= 51 && i <= 67) {
                                    tmpReg = (CfgDevReg)devService.mRegList.get(i - 48);
                                    tmpValue = (Integer)devService.getParam(tmpReg.getCode());
                                    tmpReg = (CfgDevReg)devService.mRegList.get(i);
                                    if (tmpValue != null && tmpValue == 1) {
                                        pointInfo = new ReqPointInfo(tmpReg.getName(), i - 50, true);
                                    } else {
                                        pointInfo = new ReqPointInfo(tmpReg.getName(), i - 50, false);
                                    }

                                    pointInfoList.add(pointInfo);
                                }
                            }

                            qualityInfo.setPointInfoList(pointInfoList);
                            qualityInfoList.add(qualityInfo);
                        } else if (!devService.getDevProtocol().equals("QualityModbus4")) {
                            if (devService.getDevProtocol().equals("QualityShangyang")) {
                                qualityInfo = new QualityInfo();
                                qualityInfo.setQualityName(devService.getDevName());
                                qualityInfo.setQualityType("QualityShangyang");
                                paramInfoList = new ArrayList();
                                qualityInfo.setParamInfoList(paramInfoList);
                                stateInfoList = new ArrayList();
                                qualityInfo.setStateInfoList(stateInfoList);
                                cmdInfoList = new ArrayList();
                                reqCmdInfo = new ReqCmdInfo("加标配样", 0);
                                cmdInfoList.add(reqCmdInfo);
                                qualityInfo.setCmdInfoList(cmdInfoList);
                                stateInfoList = new ArrayList();
                                qualityInfo.setPointInfoList(stateInfoList);
                                qualityInfoList.add(qualityInfo);
                            }
                        } else {
                            qualityInfo = new QualityInfo();
                            qualityInfo.setQualityName(devService.getDevName());
                            qualityInfo.setQualityType("QualityModbus");
                            paramInfoList = new ArrayList();

                            for(i = 0; i < devService.mRegList.size(); ++i) {
                                if (i >= 17 && i <= 27) {
                                    tmpReg = (CfgDevReg)devService.mRegList.get(i);
                                    paramInfo = new QualityParamInfo();
                                    paramInfo.setParamName(tmpReg.getName());
                                    tmpValue = (Integer)devService.getParam(tmpReg.getCode());
                                    if (tmpValue != null) {
                                        paramInfo.setParamValue(tmpValue + "");
                                    }

                                    if (i >= 17 && i <= 20) {
                                        paramInfo.setParamUnit("ul");
                                    } else if (i == 27) {
                                        paramInfo.setParamUnit("次");
                                    } else {
                                        paramInfo.setParamUnit("秒");
                                    }

                                    paramInfoList.add(paramInfo);
                                }
                            }

                            qualityInfo.setParamInfoList(paramInfoList);
                            stateInfoList = new ArrayList();
                            qualityInfo.setStateInfoList(stateInfoList);
                            cmdInfoList = new ArrayList();
                            reqCmdInfo = new ReqCmdInfo("停止仪器", 0);
                            cmdInfoList.add(reqCmdInfo);
                            reqCmdInfo = new ReqCmdInfo("COD填充", 1);
                            cmdInfoList.add(reqCmdInfo);
                            reqCmdInfo = new ReqCmdInfo("总氮填充", 2);
                            cmdInfoList.add(reqCmdInfo);
                            reqCmdInfo = new ReqCmdInfo("总磷填充", 3);
                            cmdInfoList.add(reqCmdInfo);
                            reqCmdInfo = new ReqCmdInfo("氨氮填充", 4);
                            cmdInfoList.add(reqCmdInfo);
                            reqCmdInfo = new ReqCmdInfo("润洗流程", 5);
                            cmdInfoList.add(reqCmdInfo);
                            reqCmdInfo = new ReqCmdInfo("进样流程", 6);
                            cmdInfoList.add(reqCmdInfo);
                            reqCmdInfo = new ReqCmdInfo("混匀流程", 7);
                            cmdInfoList.add(reqCmdInfo);
                            reqCmdInfo = new ReqCmdInfo("清洗流程", 8);
                            cmdInfoList.add(reqCmdInfo);
                            reqCmdInfo = new ReqCmdInfo("排空流程", 9);
                            cmdInfoList.add(reqCmdInfo);
                            reqCmdInfo = new ReqCmdInfo("配样流程", 10);
                            cmdInfoList.add(reqCmdInfo);
                            qualityInfo.setCmdInfoList(cmdInfoList);
                            stateInfoList = new ArrayList();

                            for(i = 0; i < devService.mRegList.size(); ++i) {
                                if (i >= 28 && i <= 51) {
                                    tmpReg = (CfgDevReg)devService.mRegList.get(i);
                                    pointInfo = new ReqPointInfo("Q" + (i - 27) + ":" + tmpReg.getName(), i - 27, false);
                                    stateInfoList.add(pointInfo);
                                }
                            }

                            qualityInfo.setPointInfoList(stateInfoList);
                            qualityInfoList.add(qualityInfo);
                        }
                    } else {
                        qualityInfo = new QualityInfo();
                        qualityInfo.setQualityName(devService.getDevName());
                        qualityInfo.setQualityType("QualityModbus");
                        paramInfoList = new ArrayList();

                        for(i = 0; i < devService.mRegList.size(); ++i) {
                            if (i >= 23 && i <= 30) {
                                tmpReg = (CfgDevReg)devService.mRegList.get(i);
                                paramInfo = new QualityParamInfo();
                                paramInfo.setParamName(tmpReg.getName());
                                tmpValue = (Integer)devService.getParam(tmpReg.getCode());
                                if (tmpValue != null) {
                                    paramInfo.setParamValue(tmpValue + "");
                                }

                                if (i == 23) {
                                    paramInfo.setParamUnit("微升");
                                } else if (i == 29) {
                                    paramInfo.setParamUnit("");
                                } else if (i == 30) {
                                    paramInfo.setParamUnit("次");
                                } else {
                                    paramInfo.setParamUnit("秒");
                                }

                                paramInfoList.add(paramInfo);
                            }
                        }

                        qualityInfo.setParamInfoList(paramInfoList);
                        stateInfoList = new ArrayList();

                        for(i = 0; i < devService.mRegList.size(); ++i) {
                            if (i == 19 || i == 20) {
                               tmpReg = (CfgDevReg)devService.mRegList.get(i);
                                ReqParamInfo stateInfo = new ReqParamInfo();
                                stateInfo.setParamName(tmpReg.getName());
                                tmpValue = (Integer)devService.getParam(tmpReg.getCode());
                                if (tmpValue != null) {
                                    if (tmpValue == 1) {
                                        stateInfo.setParamValue("有水");
                                    } else {
                                        stateInfo.setParamValue("无水");
                                    }
                                }

                                stateInfoList.add(stateInfo);
                            }
                        }

                        qualityInfo.setStateInfoList(stateInfoList);
                        cmdInfoList = new ArrayList();
                        reqCmdInfo = new ReqCmdInfo("停止仪器", 0);
                        cmdInfoList.add(reqCmdInfo);
                        reqCmdInfo = new ReqCmdInfo("提取水样", 1);
                        cmdInfoList.add(reqCmdInfo);
                        reqCmdInfo = new ReqCmdInfo("提取标样", 2);
                        cmdInfoList.add(reqCmdInfo);
                        reqCmdInfo = new ReqCmdInfo("加标回收", 3);
                        cmdInfoList.add(reqCmdInfo);
                        reqCmdInfo = new ReqCmdInfo("排空流程", 5);
                        cmdInfoList.add(reqCmdInfo);
                        reqCmdInfo = new ReqCmdInfo("填充流程", 6);
                        cmdInfoList.add(reqCmdInfo);
                        reqCmdInfo = new ReqCmdInfo("零核流程", 7);
                        cmdInfoList.add(reqCmdInfo);
                        reqCmdInfo = new ReqCmdInfo("量核流程", 8);
                        cmdInfoList.add(reqCmdInfo);
                        reqCmdInfo = new ReqCmdInfo("空白流程", 9);
                        cmdInfoList.add(reqCmdInfo);
                        qualityInfo.setCmdInfoList(cmdInfoList);
                        stateInfoList = new ArrayList();

                        for(i = 0; i < devService.mRegList.size(); ++i) {
                            if (i >= 33 && i <= 48) {
                                tmpReg = (CfgDevReg)devService.mRegList.get(i - 30);
                                tmpValue = (Integer)devService.getParam(tmpReg.getCode());
                                tmpReg = (CfgDevReg)devService.mRegList.get(i);
                                if (tmpValue != null && tmpValue == 1) {
                                    pointInfo = new ReqPointInfo("Q" + (i - 32) + ":" + tmpReg.getName(), i - 32, true);
                                } else {
                                    pointInfo = new ReqPointInfo("Q" + (i - 32) + ":" + tmpReg.getName(), i - 32, false);
                                }

                                stateInfoList.add(pointInfo);
                            }
                        }

                        qualityInfo.setPointInfoList(stateInfoList);
                        qualityInfoList.add(qualityInfo);
                    }
                }

                return qualityInfoList;
            }
        }
    }

    public DevResult setQualityParam(QualityInfo qualityInfo) {
        DevResult devResult = new DevResult();
        int iRunMode = (int)ScriptWord.getInstance().getSysVar("RunMode");
        if (iRunMode == 0) {
            Iterator var4 = this.monitorService.getDevServiceList().iterator();

            while(var4.hasNext()) {
                DevService devService = (DevService)var4.next();
                if (devService.getDevType().equals("QUALITY") && devService.getDevName().equals(qualityInfo.getQualityName())) {
                    for(int i = 0; i < qualityInfo.getParamInfoList().size(); ++i) {
                        devService.doDevCmd(4000 + i, ((QualityParamInfo)qualityInfo.getParamInfoList().get(i)).getParamValue(), (String)null);
                    }

                    devResult.setResult(true);
                    break;
                }
            }
        } else {
            devResult.setResult(false);
        }

        return devResult;
    }

    public DevResult setPumpCalVol(ReqQualityCmd reqQualityCmd) {
        DevResult devResult = new DevResult();
        int iRunMode = (int)ScriptWord.getInstance().getSysVar("RunMode");
        if (iRunMode == 0) {
            Iterator var4 = this.monitorService.getDevServiceList().iterator();

            while(var4.hasNext()) {
                DevService devService = (DevService)var4.next();
                if (devService.getDevType().equals("QUALITY") && devService.getDevName().equals(reqQualityCmd.getQualityName())) {
                    devService.doDevCmd(4004, String.format("%d", reqQualityCmd.getQualityCmdIndex()), (String)null);
                    devResult.setResult(true);
                    break;
                }
            }
        } else {
            devResult.setResult(false);
        }

        return devResult;
    }

    public DevResult doQualityCmd(ReqQualityCmd reqQualityCmd) {
        DevResult devResult = new DevResult();
        int iRunMode = (int)ScriptWord.getInstance().getSysVar("RunMode");
        if (iRunMode == 0) {
            Iterator var4 = this.monitorService.getDevServiceList().iterator();

            while(var4.hasNext()) {
                DevService devService = (DevService)var4.next();
                if (devService.getDevType().equals("QUALITY") && devService.getDevName().equals(reqQualityCmd.getQualityName())) {
                    boolean bResult = false;
                    if (!devService.getDevProtocol().equalsIgnoreCase("QualityShangyang")) {
                        bResult = devService.doDevCmd(5000 + reqQualityCmd.getQualityCmdIndex(), (String)null, (String)null);
                    } else if (reqQualityCmd.getQualityCmdIndex() == 0) {
                        Iterator var7 = this.monitorService.getCfgFactorList().iterator();

                        label186:
                        while(true) {
                            while(true) {
                                CfgFactor cfgFactor;
                                String var9;
                                byte var10;
                                do {
                                    if (!var7.hasNext()) {
                                        bResult = bResult || devService.doDevCmd(3008, "3", (String)null);
                                        break label186;
                                    }

                                    cfgFactor = (CfgFactor)var7.next();
                                    if (devService.getRelatedID() == this.monitorService.getDevID(cfgFactor.getId())) {
                                        var9 = cfgFactor.getName();
                                        var10 = -1;
                                        switch(var9.hashCode()) {
                                            case -1255953671:
                                                if (var9.equals("高锰酸盐指数")) {
                                                    var10 = 3;
                                                }
                                                break;
                                            case -1096450952:
                                                if (var9.equals("化学需氧量")) {
                                                    var10 = 4;
                                                }
                                                break;
                                            case 791379:
                                                if (var9.equals("总氮")) {
                                                    var10 = 1;
                                                }
                                                break;
                                            case 794652:
                                                if (var9.equals("总磷")) {
                                                    var10 = 0;
                                                }
                                                break;
                                            case 886022:
                                                if (var9.equals("氨氮")) {
                                                    var10 = 2;
                                                }
                                        }

                                        switch(var10) {
                                            case 0:
                                                bResult = bResult || devService.doDevCmd(3100, cfgFactor.getRcvrDestVal() + "", (String)null);
                                                break;
                                            case 1:
                                                bResult = bResult || devService.doDevCmd(3200, cfgFactor.getRcvrDestVal() + "", (String)null);
                                                break;
                                            case 2:
                                                bResult = bResult || devService.doDevCmd(3300, cfgFactor.getRcvrDestVal() + "", (String)null);
                                                break;
                                            case 3:
                                            case 4:
                                                bResult = bResult || devService.doDevCmd(3400, cfgFactor.getRcvrDestVal() + "", (String)null);
                                        }
                                    }
                                } while(devService.getRelatedID2() != this.monitorService.getDevID(cfgFactor.getId()));

                                var9 = cfgFactor.getName();
                                var10 = -1;
                                switch(var9.hashCode()) {
                                    case -1255953671:
                                        if (var9.equals("高锰酸盐指数")) {
                                            var10 = 3;
                                        }
                                        break;
                                    case -1096450952:
                                        if (var9.equals("化学需氧量")) {
                                            var10 = 4;
                                        }
                                        break;
                                    case 791379:
                                        if (var9.equals("总氮")) {
                                            var10 = 1;
                                        }
                                        break;
                                    case 794652:
                                        if (var9.equals("总磷")) {
                                            var10 = 0;
                                        }
                                        break;
                                    case 886022:
                                        if (var9.equals("氨氮")) {
                                            var10 = 2;
                                        }
                                }

                                switch(var10) {
                                    case 0:
                                        bResult = bResult || devService.doDevCmd(3100, cfgFactor.getRcvrDestVal() + "", (String)null);
                                        break;
                                    case 1:
                                        bResult = bResult || devService.doDevCmd(3200, cfgFactor.getRcvrDestVal() + "", (String)null);
                                        break;
                                    case 2:
                                        bResult = bResult || devService.doDevCmd(3300, cfgFactor.getRcvrDestVal() + "", (String)null);
                                        break;
                                    case 3:
                                    case 4:
                                        bResult = bResult || devService.doDevCmd(3400, cfgFactor.getRcvrDestVal() + "", (String)null);
                                }
                            }
                        }
                    }

                    devResult.setResult(bResult);
                    break;
                }
            }
        } else {
            devResult.setResult(false);
        }

        return devResult;
    }

    public DevResult doPointCmd(ReqQualityCmd reqQualityCmd, int cmdType) {
        DevResult devResult = new DevResult();
        int iRunMode = (int)ScriptWord.getInstance().getSysVar("RunMode");
        if (iRunMode == 0) {
            Iterator var5 = this.monitorService.getDevServiceList().iterator();

            while(var5.hasNext()) {
                DevService devService = (DevService)var5.next();
                if (devService.getDevType().equals("QUALITY") && devService.getDevName().equals(reqQualityCmd.getQualityName())) {
                    boolean bResult = devService.doDevCmd(6000 + reqQualityCmd.getQualityCmdIndex(), cmdType + "", (String)null);
                    devResult.setResult(bResult);
                    break;
                }
            }
        } else {
            devResult.setResult(false);
        }

        return devResult;
    }

    public DevResult openPlcCmd(Integer plcAddress) {
        DevResult devResult = new DevResult();
        int iRunMode = (int)ScriptWord.getInstance().getSysVar("RunMode");
        if (iRunMode == 0) {
            boolean bResult = this.scriptService.doCmd(1, plcAddress);
            devResult.setResult(bResult);
        } else {
            devResult.setResult(false);
        }

        return devResult;
    }

    public DevResult closePlcCmd(Integer plcAddress) {
        DevResult devResult = new DevResult();
        int iRunMode = (int)ScriptWord.getInstance().getSysVar("RunMode");
        if (iRunMode == 0) {
            boolean bResult = this.scriptService.doCmd(0, plcAddress);
            devResult.setResult(bResult);
        } else {
            devResult.setResult(false);
        }

        return devResult;
    }

    public DevResult resetPlcCmd() {
        DevResult devResult = new DevResult();
        boolean bResult = this.scriptService.doReset();
        devResult.setResult(bResult);
        return devResult;
    }
}
