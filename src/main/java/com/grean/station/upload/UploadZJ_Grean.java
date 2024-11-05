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

public class UploadZJ_Grean extends UploadInter {
    public UploadZJ_Grean() {
    }

    public byte[] getBootTimePkg(String strQN, String strFlag) {
        return new byte[0];
    }

    public byte[] getHeartBeatPkg(String strQN, String strFlag) {
        return new byte[0];
    }

    public byte[] getRsrvSamplePkg(String strQN, String strFlag, String strDataTime, String strVaseNo) {
        return new byte[0];
    }

    public byte[] getRealTimePkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        String strSend = "";
        List<RecDataFactor> recDataList = this.mMonitor.getRecMapper().getRecDataMinListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2011;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";CP=&&";
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
                                    return this.getSendBytes(strSend);
                                }

                                cfgFactor = (CfgFactor)var7.next();
                            } while(factorID > 0 && factorID != cfgFactor.getId());
                        } while(!cfgFactor.isUsed());
                    } while(!cfgFactor.isFiveType() && !cfgFactor.isParmType());

                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecDataFactor)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeZJ() + "-Rtd=" + ((RecDataFactor)recDataList.get(j)).getDataValue() + ",";
                            strSend = strSend + cfgFactor.getCodeZJ() + "-Flag=" + ((RecDataFactor)recDataList.get(j)).getDataFlag();
                            break;
                        }
                    }
                }
            }
        }
    }

    public byte[] getMinDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return new byte[0];
    }

    public byte[] getHourDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        String strSend = "";
        List<RecDataFactor> recDataList = this.mMonitor.getRecMapper().getRecDataHourListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2061;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";CP=&&";
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
                                    return this.getSendBytes(strSend);
                                }

                                cfgFactor = (CfgFactor)var7.next();
                            } while(factorID > 0 && factorID != cfgFactor.getId());
                        } while(!cfgFactor.isUsed());
                    } while(!cfgFactor.isParmType() && !cfgFactor.isFiveType());

                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecDataFactor)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeZJ() + "-Cou=" + ((RecDataFactor)recDataList.get(j)).getDataValue() + ",";
                            strSend = strSend + cfgFactor.getCodeZJ() + "-Flag=" + ((RecDataFactor)recDataList.get(j)).getDataFlag();
                            break;
                        }
                    }
                }
            }
        }
    }

    public byte[] getDayDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return new byte[0];
    }

    public byte[] getFiveDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        String strSend = "";
        List<RecDataFactor> recDataList = this.mMonitor.getRecMapper().getRecDataFiveListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2061;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";CP=&&";
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
                                    return this.getSendBytes(strSend);
                                }

                                cfgFactor = (CfgFactor)var7.next();
                            } while(factorID > 0 && factorID != cfgFactor.getId());
                        } while(!cfgFactor.isUsed());
                    } while(!cfgFactor.isFiveType());

                    for(int j = 0; j < recDataList.size(); ++j) {
                        if (((RecDataFactor)recDataList.get(j)).getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeZJ() + "-Cou=" + ((RecDataFactor)recDataList.get(j)).getDataValue() + ",";
                            strSend = strSend + cfgFactor.getCodeZJ() + "-Flag=" + ((RecDataFactor)recDataList.get(j)).getDataFlag();
                            break;
                        }
                    }
                }
            }
        }
    }

    public byte[] getFiveStdCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return new byte[0];
    }

    public byte[] getStdCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return new byte[0];
    }

    public byte[] getZeroCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return new byte[0];
    }

    public byte[] getSpanCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return new byte[0];
    }

    public byte[] getRcvrCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return new byte[0];
    }

    public byte[] getParCheckPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return new byte[0];
    }

    public byte[] getLogSysPkg(String strQN, String strFlag, RecDataTime recDataTime) {
        return new byte[0];
    }

    public byte[] getLogDevPkg(String strQN, String strFlag, RecDataTime recDataTime, String PolID) {
        return new byte[0];
    }

    public byte[] getStateSysPkg(String strQN, String strFlag, RecDataTime recDataTime, String InfoID) {
        return new byte[0];
    }

    public byte[] getStateDevPkg(String strQN, String strFlag, RecDataTime recDataTime, String PolID, String InfoID) {
        return new byte[0];
    }

    public byte[] getParamDevPkg(String strQN, String strFlag, RecDataTime recDataTime, String PolID, String InfoID) {
        return new byte[0];
    }

    public byte[] getPowerAlarmPkg(String strQN, String strFlag, boolean powerAlarm) {
        return new byte[0];
    }

    public List<byte[]> getResponse(byte[] bRecv) {
        return null;
    }

    private byte[] getSendBytes(String sendStr) {
        try {
            byte[] bufTmp = sendStr.getBytes();
            int crc16 = Utility.getCrc16(bufTmp, bufTmp.length);
            String str16 = String.format("%04x", crc16).toUpperCase();
            String strCrc = str16.substring(2, 4) + str16.substring(0, 2);
            String strTmp = "##" + String.format("%04d", bufTmp.length) + sendStr + strCrc + "\r\n";
            return strTmp.getBytes();
        } catch (Exception var7) {
            return null;
        }
    }
}