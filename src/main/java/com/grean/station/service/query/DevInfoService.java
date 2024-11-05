//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.query;

import com.grean.station.dao.CfgMapper;
import com.grean.station.domain.DO.cfg.CfgDev;
import com.grean.station.domain.DO.cfg.CfgDevAlarm;
import com.grean.station.domain.DO.cfg.CfgDevFault;
import com.grean.station.domain.DO.cfg.CfgDevReg;
import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.request.DevCmd;
import com.grean.station.domain.request.DevInfo;
import com.grean.station.domain.request.DevResult;
import com.grean.station.exception.ErrorCode;
import com.grean.station.exception.ServerException;
import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.service.script.ScriptService;
import com.grean.station.utils.TimeHelper;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DevInfoService {
    @Autowired
    CfgMapper cfgMapper;
    @Autowired
    MonitorService monitorService;
    @Autowired
    ScriptService scriptService;
    private List<CfgDev> cfgDevList;
    private List<CfgFactor> cfgFactorList;
    private List<CfgDevAlarm> cfgDevAlarmList;
    private List<CfgDevFault> cfgDevFaultList;

    public DevInfoService() {
    }

    public DevInfo getDevInfo(int devID) {
        DevInfo devInfo = new DevInfo();
        this.cfgDevList = this.cfgMapper.getCfgDevList();
        this.cfgFactorList = this.monitorService.getCfgFactorList();
        this.cfgDevAlarmList = this.cfgMapper.getCfgDevAlarmList();
        this.cfgDevFaultList = this.cfgMapper.getCfgDevFaultList();
        Iterator var3 = this.cfgDevList.iterator();

        while(true) {
            CfgDev cfgDev;
            do {
                if (!var3.hasNext()) {
                    String curDevName = (String)devInfo.getDevNameList().get(devID - 1);
                    int curDevID = 0;
                    Iterator var15 = this.cfgDevList.iterator();

                    while(var15.hasNext()) {
                        cfgDev = (CfgDev)var15.next();
                        if (cfgDev.getNick().equals(curDevName)) {
                            curDevID = cfgDev.getId();
                            break;
                        }
                    }

                    var15 = this.cfgDevList.iterator();

                    while(true) {
                        do {
                            if (!var15.hasNext()) {
                                var15 = this.monitorService.getDevServiceList().iterator();

                                while(var15.hasNext()) {
                                    DevService devService = (DevService)var15.next();
                                    if (devService.getDevID() == curDevID) {
                                        Integer iTmp = (Integer)devService.getParam("DevState");
                                        if (iTmp != null) {
                                            devInfo.setDevStatus(Utility.getTaskName(iTmp));
                                        }

                                        iTmp = (Integer)devService.getParam("DevMode");
                                        if (iTmp != null) {
                                            switch(iTmp) {
                                                case 1:
                                                    devInfo.setDevMode("连续模式");
                                                    break;
                                                case 2:
                                                    devInfo.setDevMode("周期模式");
                                                    break;
                                                case 3:
                                                    devInfo.setDevMode("定点模式");
                                                    break;
                                                case 4:
                                                    devInfo.setDevMode("受控模式");
                                                    break;
                                                case 5:
                                                    devInfo.setDevMode("手动模式");
                                            }
                                        }

                                        iTmp = (Integer)devService.getParam("DevAlarm");
                                        Iterator var20;
                                        if (iTmp != null) {
                                            var20 = this.cfgDevAlarmList.iterator();

                                            label139:
                                            while(true) {
                                                CfgDevAlarm cfgDevAlarm;
                                                do {
                                                    if (!var20.hasNext()) {
                                                        break label139;
                                                    }

                                                    cfgDevAlarm = (CfgDevAlarm)var20.next();
                                                } while(cfgDevAlarm.getDev_id() != 0 && cfgDevAlarm.getDev_id() != curDevID);

                                                if (cfgDevAlarm.getAlarm_id() == iTmp) {
                                                    devInfo.setDevWarn(cfgDevAlarm.getAlarm_desc());
                                                }
                                            }
                                        }

                                        iTmp = (Integer)devService.getParam("DevFault");
                                        if (iTmp != null) {
                                            var20 = this.cfgDevFaultList.iterator();

                                            label125:
                                            while(true) {
                                                CfgDevFault cfgDevFault;
                                                do {
                                                    if (!var20.hasNext()) {
                                                        break label125;
                                                    }

                                                    cfgDevFault = (CfgDevFault)var20.next();
                                                } while(cfgDevFault.getDev_id() != 0 && cfgDevFault.getDev_id() != curDevID);

                                                if (cfgDevFault.getFault_id() == iTmp) {
                                                    devInfo.setDevFault(cfgDevFault.getFault_desc());
                                                }
                                            }
                                        }

                                        for(int i = 35; i < devService.getmRegList().size(); ++i) {
                                            CfgDevReg cfgDevReg = (CfgDevReg)devService.getmRegList().get(i);
                                            devInfo.getDevParamNameList().add(cfgDevReg.getName());
                                            Object oValue = devService.getParam(cfgDevReg.getCode());
                                            if (oValue == null) {
                                                devInfo.getDevParamValueList().add((String) null);
                                            } else {
                                                String var11 = cfgDevReg.getType().toLowerCase();
                                                byte var12 = -1;
                                                switch(var11.hashCode()) {
                                                    case 103195:
                                                        if (var11.equals("hex")) {
                                                            var12 = 5;
                                                        }
                                                        break;
                                                    case 3052374:
                                                        if (var11.equals("char")) {
                                                            var12 = 4;
                                                        }
                                                        break;
                                                    case 3076014:
                                                        if (var11.equals("date")) {
                                                            var12 = 3;
                                                        }
                                                        break;
                                                    case 3655434:
                                                        if (var11.equals("word")) {
                                                            var12 = 1;
                                                        }
                                                        break;
                                                    case 96007534:
                                                        if (var11.equals("dword")) {
                                                            var12 = 0;
                                                        }
                                                        break;
                                                    case 97526364:
                                                        if (var11.equals("float")) {
                                                            var12 = 2;
                                                        }
                                                }

                                                switch(var12) {
                                                    case 0:
                                                    case 1:
                                                        devInfo.getDevParamValueList().add(Integer.toString((Integer)oValue));
                                                        break;
                                                    case 2:
                                                        devInfo.getDevParamValueList().add(Double.toString((Double)oValue));
                                                        break;
                                                    case 3:
                                                        devInfo.getDevParamValueList().add(TimeHelper.formatDataTime_yyyy_MM_dd_HH_mm_ss((Timestamp)oValue));
                                                        break;
                                                    case 4:
                                                        devInfo.getDevParamValueList().add((String)oValue);
                                                        break;
                                                    case 5:
                                                        devInfo.getDevParamNameList().add((String)oValue);
                                                }
                                            }
                                        }

                                        return devInfo;
                                    }
                                }

                                return devInfo;
                            }

                            cfgDev = (CfgDev)var15.next();
                        } while(!cfgDev.getNick().equals(curDevName));

                        Iterator var18 = this.cfgFactorList.iterator();

                        while(var18.hasNext()) {
                            CfgFactor cfgFactor = (CfgFactor)var18.next();
                            if (cfgFactor.getDevID() == cfgDev.getId()) {
                                devInfo.getDevFactorList().add(cfgFactor.getName());
                                devInfo.getDevValueList().add(cfgFactor.getDataMea());
                                devInfo.getDevUnitList().add(cfgFactor.getUnit());
                            }
                        }
                    }
                }

                cfgDev = (CfgDev)var3.next();
            } while(!cfgDev.getType().toUpperCase().equals("FIVE") && !cfgDev.getType().toUpperCase().equals("PARM"));

            boolean bFirst = true;
            Iterator var6 = devInfo.getDevNameList().iterator();

            while(var6.hasNext()) {
                String devName = (String)var6.next();
                if (devName.equals(cfgDev.getNick())) {
                    bFirst = false;
                    break;
                }
            }

            if (bFirst) {
                devInfo.getDevNameList().add(cfgDev.getNick());
            }
        }
    }

    public DevResult doDevCmd(DevCmd devCmd) {
        DevResult devResult = new DevResult();
        this.cfgDevList = this.cfgMapper.getCfgDevList();
        if (this.scriptService.isOnScript() && devCmd.getCmdID() != 11) {
            throw new ServerException(ErrorCode.FIELD_ERROR.getCode(), "系统正在运行，本次操作未能执行");
        } else {
            int curDevID = 0;
            Iterator var4 = this.cfgDevList.iterator();

            while(var4.hasNext()) {
                CfgDev cfgDev = (CfgDev)var4.next();
                if (cfgDev.getName().equals(devCmd.getDevName())) {
                    curDevID = cfgDev.getId();
                    break;
                }
            }

            boolean bResult = this.monitorService.doDevCmd(curDevID, devCmd.getCmdID(), "hd");
            devResult.setResult(bResult);
            return devResult;
        }
    }
}
