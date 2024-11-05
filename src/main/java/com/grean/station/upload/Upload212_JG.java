//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.upload;

import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.rec.RecDataFactor;
import com.grean.station.domain.DO.rec.RecDataTime;
import com.grean.station.utils.TimeHelper;
import com.grean.station.utils.Utility;
import java.util.List;

public class Upload212_JG extends UploadInter {
    public Upload212_JG() {
    }

    public byte[] getBootTimePkg(String strQN, String strFlag) {
        return null;
    }

    public byte[] getHeartBeatPkg(String strQN, String strFlag) {
        return null;
    }

    public byte[] getRsrvSamplePkg(String strQN, String strFlag, String strDataTime, String strVaseNo) {
        return null;
    }

    public byte[] getPowerAlarmPkg(String strQN, String strFlag, boolean powerAlarm) {
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
            String strSend = "";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2061;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                CfgFactor cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if ((factorID <= 0 || factorID == cfgFactor.getId()) && cfgFactor.isUsed() && cfgFactor.isUpload() && (cfgFactor.isParmType() || cfgFactor.isFiveType() || cfgFactor.isSysType() || cfgFactor.isSwType())) {
                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecDataFactor)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeZJ() + "-Avg=" + ((RecDataFactor)recDataList.get(j)).getDataString() + ",";
                            strSend = strSend + cfgFactor.getCodeZJ() + "-Cou=" + ((RecDataFactor)recDataList.get(j)).getDataString() + ",";
                            strSend = strSend + cfgFactor.getCodeZJ() + "-Flag=" + ((RecDataFactor)recDataList.get(j)).getDataFlag();
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
        return null;
    }

    public byte[] getFiveDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecDataFactor> recDataList = this.mMonitor.getRecMapper().getRecDataFiveListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2061;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());

            for(int i = 0; i < this.mMonitor.cfgFactorList.size(); ++i) {
                CfgFactor cfgFactor = (CfgFactor)this.mMonitor.cfgFactorList.get(i);
                if ((factorID <= 0 || factorID == cfgFactor.getId()) && cfgFactor.isUsed() && cfgFactor.isFiveType()) {
                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecDataFactor)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeZJ() + "-Avg=" + ((RecDataFactor)recDataList.get(j)).getDataString() + ",";
                            strSend = strSend + cfgFactor.getCodeZJ() + "-Cou=" + ((RecDataFactor)recDataList.get(j)).getDataString() + ",";
                            strSend = strSend + cfgFactor.getCodeZJ() + "-Flag=" + ((RecDataFactor)recDataList.get(j)).getDataFlag();
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
        return null;
    }

    public byte[] getStdCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return null;
    }

    public byte[] getZeroCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return null;
    }

    public byte[] getSpanCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return null;
    }

    public byte[] getRcvrCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return null;
    }

    public byte[] getParCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return null;
    }

    public byte[] getLogSysPkg(String strQN, String strFlag, RecDataTime recDataTime) {
        return null;
    }

    public byte[] getLogDevPkg(String strQN, String strFlag, RecDataTime recDataTime, String PolID) {
        return null;
    }

    public byte[] getStateSysPkg(String strQN, String strFlag, RecDataTime recDataTime, String InfoID) {
        return null;
    }

    public byte[] getStateDevPkg(String strQN, String strFlag, RecDataTime recDataTime, String PolID, String InfoID) {
        return null;
    }

    public byte[] getParamDevPkg(String strQN, String strFlag, RecDataTime recDataTime, String PolID, String InfoID) {
        return null;
    }

    public List<byte[]> getResponse(byte[] bRecv) {
        return null;
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

    public byte[] getFlowDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return null;
    }
}
