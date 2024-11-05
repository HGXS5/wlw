//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.upload;

import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.rec.RecDataAvg;
import com.grean.station.domain.DO.rec.RecDataFactor;
import com.grean.station.domain.DO.rec.RecDataTime;
import com.grean.station.utils.TimeHelper;
import com.grean.station.utils.Utility;
import java.util.Iterator;
import java.util.List;

public class UploadZJ_Pollution extends UploadInter {
    public UploadZJ_Pollution() {
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
                                if (!var7.hasNext()) {
                                    strSend = strSend + "&&";
                                    return this.getSendBytes(strSend);
                                }

                                cfgFactor = (CfgFactor)var7.next();
                            } while(factorID > 0 && factorID != cfgFactor.getId());
                        } while(!cfgFactor.isUsed());
                    } while(!cfgFactor.isFiveType() && !cfgFactor.isParmType());

                    Iterator var9 = recDataList.iterator();

                    while(var9.hasNext()) {
                        RecDataFactor recDataFactor = (RecDataFactor)var9.next();
                        if (recDataFactor.getFactorID() == cfgFactor.getId()) {
                            strSend = strSend + ";" + cfgFactor.getCodeZJ() + "-Rtd=" + recDataFactor.getDataValue() + ",";
                            strSend = strSend + cfgFactor.getCodeZJ() + "-Flag=" + recDataFactor.getDataFlag();
                            break;
                        }
                    }
                }
            }
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
                    while(true) {
                        CfgFactor cfgFactor;
                        Iterator var9;
                        RecDataAvg recDataAvg;
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

                            if (cfgFactor.isFiveType()) {
                                var9 = recDataList.iterator();

                                while(var9.hasNext()) {
                                    recDataAvg = (RecDataAvg)var9.next();
                                    if (recDataAvg.getFactorID() == cfgFactor.getId()) {
                                        strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Min=" + recDataAvg.getDataValue() + ",";
                                        strSend = strSend + cfgFactor.getCodeGJ() + "-Avg=" + recDataAvg.getDataValue() + ",";
                                        strSend = strSend + cfgFactor.getCodeGJ() + "-Max=" + recDataAvg.getDataValue();
                                        break;
                                    }
                                }
                            }
                        } while(!cfgFactor.isParmType());

                        if (cfgFactor.getName().equals("流量")) {
                            var9 = recDataList.iterator();

                            while(var9.hasNext()) {
                                recDataAvg = (RecDataAvg)var9.next();
                                if (recDataAvg.getFactorID() == cfgFactor.getId()) {
                                    strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Cou=" + recDataAvg.getDataCou();
                                    break;
                                }
                            }
                        } else {
                            var9 = recDataList.iterator();

                            while(var9.hasNext()) {
                                recDataAvg = (RecDataAvg)var9.next();
                                if (recDataAvg.getFactorID() == cfgFactor.getId()) {
                                    strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Cou=" + recDataAvg.getDataCou() + ",";
                                    strSend = strSend + cfgFactor.getCodeGJ() + "-Min=" + recDataAvg.getDataValue() + ",";
                                    strSend = strSend + cfgFactor.getCodeGJ() + "-Avg=" + recDataAvg.getDataValue() + ",";
                                    strSend = strSend + cfgFactor.getCodeGJ() + "-Max=" + recDataAvg.getDataValue();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public byte[] getHourDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecDataAvg> recDataList = this.mMonitor.getRecMapper().getRecDataHourAvgListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2061;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());
            Iterator var7 = this.mMonitor.cfgFactorList.iterator();

            while(true) {
                while(true) {
                    while(true) {
                        CfgFactor cfgFactor;
                        Iterator var9;
                        RecDataAvg recDataAvg;
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

                            if (cfgFactor.isFiveType()) {
                                var9 = recDataList.iterator();

                                while(var9.hasNext()) {
                                    recDataAvg = (RecDataAvg)var9.next();
                                    if (recDataAvg.getFactorID() == cfgFactor.getId()) {
                                        strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Min=" + recDataAvg.getDataValue() + ",";
                                        strSend = strSend + cfgFactor.getCodeGJ() + "-Avg=" + recDataAvg.getDataValue() + ",";
                                        strSend = strSend + cfgFactor.getCodeGJ() + "-Max=" + recDataAvg.getDataValue();
                                        break;
                                    }
                                }
                            }
                        } while(!cfgFactor.isParmType());

                        if (cfgFactor.getName().equals("流量")) {
                            var9 = recDataList.iterator();

                            while(var9.hasNext()) {
                                recDataAvg = (RecDataAvg)var9.next();
                                if (recDataAvg.getFactorID() == cfgFactor.getId()) {
                                    strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Cou=" + recDataAvg.getDataCou();
                                    break;
                                }
                            }
                        } else {
                            var9 = recDataList.iterator();

                            while(var9.hasNext()) {
                                recDataAvg = (RecDataAvg)var9.next();
                                if (recDataAvg.getFactorID() == cfgFactor.getId()) {
                                    strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Cou=" + recDataAvg.getDataCou() + ",";
                                    strSend = strSend + cfgFactor.getCodeGJ() + "-Min=" + recDataAvg.getDataValue() + ",";
                                    strSend = strSend + cfgFactor.getCodeGJ() + "-Avg=" + recDataAvg.getDataValue() + ",";
                                    strSend = strSend + cfgFactor.getCodeGJ() + "-Max=" + recDataAvg.getDataValue();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public byte[] getDayDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        List<RecDataAvg> recDataList = this.mMonitor.getRecMapper().getRecDataDayAvgListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = "QN=" + strQN + ";";
            strSend = strSend + "ST=" + this.mUploadCfg.getSt() + ";CN=2031;PW=" + this.mUploadCfg.getPassword() + ";MN=" + this.mUploadCfg.getMn() + ";Flag=" + strFlag + ";CP=&&";
            strSend = strSend + "DataTime=" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());
            Iterator var7 = this.mMonitor.cfgFactorList.iterator();

            while(true) {
                while(true) {
                    while(true) {
                        CfgFactor cfgFactor;
                        Iterator var9;
                        RecDataAvg recDataAvg;
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

                            if (cfgFactor.isFiveType()) {
                                var9 = recDataList.iterator();

                                while(var9.hasNext()) {
                                    recDataAvg = (RecDataAvg)var9.next();
                                    if (recDataAvg.getFactorID() == cfgFactor.getId()) {
                                        strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Min=" + recDataAvg.getDataValue() + ",";
                                        strSend = strSend + cfgFactor.getCodeGJ() + "-Avg=" + recDataAvg.getDataValue() + ",";
                                        strSend = strSend + cfgFactor.getCodeGJ() + "-Max=" + recDataAvg.getDataValue();
                                        break;
                                    }
                                }
                            }
                        } while(!cfgFactor.isParmType());

                        if (cfgFactor.getName().equals("流量")) {
                            var9 = recDataList.iterator();

                            while(var9.hasNext()) {
                                recDataAvg = (RecDataAvg)var9.next();
                                if (recDataAvg.getFactorID() == cfgFactor.getId()) {
                                    strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Cou=" + recDataAvg.getDataCou();
                                    break;
                                }
                            }
                        } else {
                            var9 = recDataList.iterator();

                            while(var9.hasNext()) {
                                recDataAvg = (RecDataAvg)var9.next();
                                if (recDataAvg.getFactorID() == cfgFactor.getId()) {
                                    strSend = strSend + ";" + cfgFactor.getCodeGJ() + "-Cou=" + recDataAvg.getDataCou() + ",";
                                    strSend = strSend + cfgFactor.getCodeGJ() + "-Min=" + recDataAvg.getDataValue() + ",";
                                    strSend = strSend + cfgFactor.getCodeGJ() + "-Avg=" + recDataAvg.getDataValue() + ",";
                                    strSend = strSend + cfgFactor.getCodeGJ() + "-Max=" + recDataAvg.getDataValue();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public byte[] getFiveDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return new byte[0];
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
            int crc16 = Utility.crc16ANSI(bufTmp);
            String strTmp = "##" + String.format("%04d", bufTmp.length) + sendStr + String.format("%04x", crc16).toUpperCase() + "\r\n";
            return strTmp.getBytes();
        } catch (Exception var5) {
            return null;
        }
    }
}
