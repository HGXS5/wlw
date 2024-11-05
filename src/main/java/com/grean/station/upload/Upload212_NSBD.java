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
import java.util.Iterator;
import java.util.List;

public class Upload212_NSBD extends UploadInter {
    public Upload212_NSBD() {
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

    public byte[] getRealTimePkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return null;
    }

    public byte[] getMinDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return null;
    }

    public byte[] getHourDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        boolean bData = false;
        List<RecDataFactor> recDataList = this.mMonitor.getRecMapper().getRecDataHourListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2011;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());
            Iterator var8 = this.mMonitor.cfgFactorList.iterator();

            while(true) {
                while(true) {
                    CfgFactor cfgFactor;
                    do {
                        do {
                            do {
                                if (!var8.hasNext()) {
                                    strSend = strSend + "&&";
                                    if (bData) {
                                        return this.getSendBytes(strSend);
                                    }

                                    return null;
                                }

                                cfgFactor = (CfgFactor)var8.next();
                            } while(factorID > 0 && factorID != cfgFactor.getId());
                        } while(!cfgFactor.isUsed());
                    } while(!cfgFactor.isParmType() && !cfgFactor.isFiveType());

                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecDataFactor)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            bData = true;
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Rtd=" + ((RecDataFactor)recDataList.get(j)).getDataValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=N";
                            break;
                        }
                    }
                }
            }
        }
    }

    public byte[] getDayDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return null;
    }

    public byte[] getFiveDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return null;
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

    public byte[] getPowerAlarmPkg(String strQN, String strFlag, boolean powerAlarm) {
        return null;
    }

    public List<byte[]> getResponse(byte[] bRecv) {
        return null;
    }

    private byte[] getSendBytes(String sendStr) {
        try {
            byte[] bufTmp = sendStr.getBytes();
            int crc16 = Utility.crc16ANSI(bufTmp);
            String strTmp = "##" + String.format("%04d", bufTmp.length) + sendStr + String.format("%04x", crc16).toUpperCase() + "\n";
            return strTmp.getBytes();
        } catch (Exception var5) {
            return null;
        }
    }
}
