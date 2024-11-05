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

public class Upload212_2017 extends UploadInter {
    public Upload212_2017() {
    }

    public byte[] getBootTimePkg(String strQN, String strFlag) {
        return null;
    }

    public byte[] getHeartBeatPkg(String strQN, String strFlag) {
        String strSend = "QN=" + strQN + ";";
        strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=9015;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&&&";
        return this.getSendBytes(strSend);
    }

    public byte[] getRsrvSamplePkg(String strQN, String strFlag, String strDataTime, String strVaseNo) {
        return null;
    }

    public byte[] getRealTimePkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecDataFactor> recDataList = this.mMonitor.getRecMapper().getRecDataMinListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2011;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());
            Iterator var7 = this.mMonitor.cfgFactorList.iterator();

            while(true) {
                while(true) {
                    CfgFactor cfgFactor;
                    do {
                        do {
                            do {
                                do {
                                    if (!var7.hasNext()) {
                                        strSend = strSend + "&&";
                                        return this.getSendBytes(strSend);
                                    }

                                    cfgFactor = (CfgFactor)var7.next();
                                } while(factorID > 0 && factorID != cfgFactor.getId());
                            } while(!cfgFactor.isUsed());
                        } while(!cfgFactor.isFiveType() && !cfgFactor.isParmType());
                    } while(cfgFactor.getCodeGJ() == null);

                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecDataFactor)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Rtd=" + ((RecDataFactor)recDataList.get(j)).getDataValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=" + ((RecDataFactor)recDataList.get(j)).getDataFlag();
                            break;
                        }
                    }
                }
            }
        }
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
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2061;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());
            Iterator var7 = this.mMonitor.cfgFactorList.iterator();

            while(true) {
                while(true) {
                    CfgFactor cfgFactor;
                    do {
                        do {
                            do {
                                do {
                                    if (!var7.hasNext()) {
                                        strSend = strSend + "&&";
                                        return this.getSendBytes(strSend);
                                    }

                                    cfgFactor = (CfgFactor)var7.next();
                                } while(factorID > 0 && factorID != cfgFactor.getId());
                            } while(!cfgFactor.isUsed());
                        } while(!cfgFactor.isParmType() && !cfgFactor.isFiveType());
                    } while(cfgFactor.getCodeGJ() == null);

                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecDataFactor)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Cou=" + ((RecDataFactor)recDataList.get(j)).getDataValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Min=" + ((RecDataFactor)recDataList.get(j)).getDataValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Avg=" + ((RecDataFactor)recDataList.get(j)).getDataValue() + ",";
                            strSend = strSend + cfgFactor.getCodeGJ() + "-Max=" + ((RecDataFactor)recDataList.get(j)).getDataValue() + ",";
                            if (((RecDataFactor)recDataList.get(j)).getDataFlag() != null) {
                                strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=" + ((RecDataFactor)recDataList.get(j)).getDataFlag();
                            } else {
                                strSend = strSend + cfgFactor.getCodeGJ() + "-Flag=N";
                            }
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
            String strTmp = "##" + String.format("%04d", bufTmp.length) + sendStr + String.format("%04x", crc16).toUpperCase() + "\r\n";
            return strTmp.getBytes();
        } catch (Exception var5) {
            return null;
        }
    }
}
