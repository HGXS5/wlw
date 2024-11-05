//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.upload;

import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.rec.RecDataFactor;
import com.grean.station.domain.DO.rec.RecDataTime;
import com.grean.station.utils.TimeHelper;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class UploadFtp_QD extends UploadInter {
    public UploadFtp_QD() {
    }

    public void sendHourData(int factorID, Timestamp timestamp) {
        RecDataTime recTime;
        if (timestamp == null) {
            recTime = this.mMonitor.getRecMapper().getLastRecTimeHour();
        } else {
            recTime = this.mMonitor.getRecMapper().getRecTimeHourByTime(timestamp);
        }

        if (recTime != null) {
            String strQN = this.getQN(new Date());
            byte[] bSend = this.getHourDataPkg(strQN, "9", recTime, factorID);
            if (bSend != null) {
                if (this.mInterComm.Send(bSend)) {
                    switch(this.mUploadCfg.getId()) {
                        case 1:
                            recTime.setSendTime1(TimeHelper.getCurrentTimestamp());
                            this.mMonitor.getRecMapper().updateRecTimeHour1(recTime);
                            break;
                        case 2:
                            recTime.setSendTime2(TimeHelper.getCurrentTimestamp());
                            this.mMonitor.getRecMapper().updateRecTimeHour2(recTime);
                            break;
                        case 3:
                            recTime.setSendTime3(TimeHelper.getCurrentTimestamp());
                            this.mMonitor.getRecMapper().updateRecTimeHour3(recTime);
                            break;
                        case 4:
                            recTime.setSendTime4(TimeHelper.getCurrentTimestamp());
                            this.mMonitor.getRecMapper().updateRecTimeHour4(recTime);
                            break;
                        case 5:
                            recTime.setSendTime5(TimeHelper.getCurrentTimestamp());
                            this.mMonitor.getRecMapper().updateRecTimeHour5(recTime);
                            break;
                        case 6:
                            recTime.setSendTime6(TimeHelper.getCurrentTimestamp());
                            this.mMonitor.getRecMapper().updateRecTimeHour6(recTime);
                            break;
                        case 7:
                            recTime.setSendTime7(TimeHelper.getCurrentTimestamp());
                            this.mMonitor.getRecMapper().updateRecTimeHour7(recTime);
                            break;
                        case 8:
                            recTime.setSendTime8(TimeHelper.getCurrentTimestamp());
                            this.mMonitor.getRecMapper().updateRecTimeHour8(recTime);
                            break;
                        case 9:
                            recTime.setSendTime9(TimeHelper.getCurrentTimestamp());
                            this.mMonitor.getRecMapper().updateRecTimeHour9(recTime);
                            break;
                        case 10:
                            recTime.setSendTime10(TimeHelper.getCurrentTimestamp());
                            this.mMonitor.getRecMapper().updateRecTimeHour10(recTime);
                    }
                }

            }
        }
    }

    public void sendFiveData(int factorID) {
        RecDataTime recTime = this.mMonitor.getRecMapper().getLastRecTimeFive();
        if (recTime != null) {
            String strQN = this.getQN(new Date());
            byte[] bSend = this.getFiveDataPkg(strQN, "9", recTime, factorID);
            if (bSend != null) {
                if (this.mInterComm.Send(bSend)) {
                    switch(this.mUploadCfg.getId()) {
                        case 1:
                            recTime.setSendTime1(TimeHelper.getCurrentTimestamp());
                            this.mMonitor.getRecMapper().updateRecTimeFive1(recTime);
                            break;
                        case 2:
                            recTime.setSendTime2(TimeHelper.getCurrentTimestamp());
                            this.mMonitor.getRecMapper().updateRecTimeFive2(recTime);
                            break;
                        case 3:
                            recTime.setSendTime3(TimeHelper.getCurrentTimestamp());
                            this.mMonitor.getRecMapper().updateRecTimeFive3(recTime);
                            break;
                        case 4:
                            recTime.setSendTime4(TimeHelper.getCurrentTimestamp());
                            this.mMonitor.getRecMapper().updateRecTimeFive4(recTime);
                            break;
                        case 5:
                            recTime.setSendTime5(TimeHelper.getCurrentTimestamp());
                            this.mMonitor.getRecMapper().updateRecTimeFive5(recTime);
                            break;
                        case 6:
                            recTime.setSendTime6(TimeHelper.getCurrentTimestamp());
                            this.mMonitor.getRecMapper().updateRecTimeFive6(recTime);
                            break;
                        case 7:
                            recTime.setSendTime7(TimeHelper.getCurrentTimestamp());
                            this.mMonitor.getRecMapper().updateRecTimeFive7(recTime);
                            break;
                        case 8:
                            recTime.setSendTime8(TimeHelper.getCurrentTimestamp());
                            this.mMonitor.getRecMapper().updateRecTimeFive8(recTime);
                            break;
                        case 9:
                            recTime.setSendTime9(TimeHelper.getCurrentTimestamp());
                            this.mMonitor.getRecMapper().updateRecTimeFive9(recTime);
                            break;
                        case 10:
                            recTime.setSendTime10(TimeHelper.getCurrentTimestamp());
                            this.mMonitor.getRecMapper().updateRecTimeFive10(recTime);
                    }
                }

            }
        }
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
        List<RecDataFactor> recDataList = this.mMonitor.getRecMapper().getRecDataHourListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());
            strSend = strSend + "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n";
            strSend = strSend + "<Body>\r\n";
            strSend = strSend + "\t<Head sjblx=\"0001\" fsfid=\"" + this.mUploadCfg.getMn() + "\" jsfid=\"370200000000\" ";
            strSend = strSend + "wjscsj=\"" + TimeHelper.formatDataTime_yyyyMMddHHmmss(TimeHelper.getCurrentTimestamp()) + "\" sjcssj=\"" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime()) + "\"/>\r\n";
            strSend = strSend + "\t<Datas>\r\n";
            strSend = strSend + "\t\t<Data>\r\n";
            Iterator var7 = this.mMonitor.cfgFactorList.iterator();

            while(true) {
                while(true) {
                    CfgFactor cfgFactor;
                    do {
                        do {
                            do {
                                do {
                                    if (!var7.hasNext()) {
                                        strSend = strSend + "\t\t</Data>\r\n";
                                        strSend = strSend + "\t</Datas>\r\n";
                                        strSend = strSend + "</Body>\r\n";
                                        return strSend.getBytes();
                                    }

                                    cfgFactor = (CfgFactor)var7.next();
                                } while(factorID > 0 && factorID != cfgFactor.getId());
                            } while(!cfgFactor.isUsed());
                        } while(!cfgFactor.isUpload());
                    } while(!cfgFactor.isParmType() && !cfgFactor.isFiveType());

                    Iterator var9 = recDataList.iterator();

                    while(var9.hasNext()) {
                        RecDataFactor recDataFactor = (RecDataFactor)var9.next();
                        if (recDataFactor.getFactorID() == cfgFactor.getId()) {
                            if (cfgFactor.getCodeGJ() != null && cfgFactor.getCodeGJ().length() > 0) {
                                strSend = strSend + "\t\t\t<Item pollutant_code=\"" + cfgFactor.getCodeGJ() + "\" value=\"" + recDataFactor.getDataValue() + "\" status=\"" + recDataFactor.getDataFlag() + "\"/>\r\n";
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
        List<RecDataFactor> recDataList = this.mMonitor.getRecMapper().getRecDataFiveListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            String strSend = TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime());
            strSend = strSend + "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n";
            strSend = strSend + "<Body>\r\n";
            strSend = strSend + "\t<Head sjblx=\"0001\" fsfid=\"" + this.mUploadCfg.getMn() + "\" jsfid=\"370200000000\" ";
            strSend = strSend + "wjscsj=\"" + TimeHelper.formatDataTime_yyyyMMddHHmmss(TimeHelper.getCurrentTimestamp()) + "\" sjcssj=\"" + TimeHelper.formatDataTime_yyyyMMddHHmmss(recDataTime.getRecTime()) + "\"/>\r\n";
            strSend = strSend + "\t<Datas>\r\n";
            strSend = strSend + "\t\t<Data>\r\n";
            Iterator var7 = this.mMonitor.cfgFactorList.iterator();

            while(true) {
                while(true) {
                    CfgFactor cfgFactor;
                    do {
                        do {
                            do {
                                do {
                                    if (!var7.hasNext()) {
                                        strSend = strSend + "\t\t</Data>\r\n";
                                        strSend = strSend + "\t</Datas>\r\n";
                                        strSend = strSend + "</Body>\r\n";
                                        return strSend.getBytes();
                                    }

                                    cfgFactor = (CfgFactor)var7.next();
                                } while(factorID > 0 && factorID != cfgFactor.getId());
                            } while(!cfgFactor.isUsed());
                        } while(!cfgFactor.isUpload());
                    } while(!cfgFactor.isFiveType());

                    Iterator var9 = recDataList.iterator();

                    while(var9.hasNext()) {
                        RecDataFactor recDataFactor = (RecDataFactor)var9.next();
                        if (recDataFactor.getFactorID() == cfgFactor.getId()) {
                            if (cfgFactor.getCodeGJ() != null && cfgFactor.getCodeGJ().length() > 0) {
                                strSend = strSend + "\t\t\t<Item pollutant_code=\"" + cfgFactor.getCodeGJ() + "\" value=\"" + recDataFactor.getDataValue() + "\" status=\"" + recDataFactor.getDataFlag() + "\"/>\r\n";
                            }
                            break;
                        }
                    }
                }
            }
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

    public byte[] getPowerAlarmPkg(String strQN, String strFlag, boolean powerAlarm) {
        return null;
    }

    public List<byte[]> getResponse(byte[] bRecv) {
        return null;
    }
}
