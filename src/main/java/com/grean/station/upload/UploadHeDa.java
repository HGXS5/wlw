//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.upload;

import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.rec.RecDataFactor;
import com.grean.station.domain.DO.rec.RecDataTime;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.joda.time.DateTime;

public class UploadHeDa extends UploadInter {
    private String[] sendNames = new String[]{"氨氮", "总磷", "总氮", "高锰酸盐指数", "pH", "水温", "溶解氧", "电导率", "浊度", "蓝藻", "硅藻", "绿藻", "隐藻", "叶绿素", "毒性"};

    public UploadHeDa() {
    }

    private byte[] getSendPkg(Timestamp recTime, byte[] bContent) {
        byte[] bSend = new byte[256];
        byte[] bLen = Utility.getByteArray(bContent.length + 46);
        bSend[0] = -85;
        bSend[1] = -51;
        bSend[2] = bLen[2];
        bSend[3] = bLen[3];
        String strAddress;
        if (this.mUploadCfg.getMn().length() > 4) {
            strAddress = this.mUploadCfg.getMn().substring(0, 4);
        } else {
            strAddress = this.mUploadCfg.getMn() + "0000";
            strAddress = strAddress.substring(0, 4);
        }

        byte[] bTmp = Utility.hexStringToBytes(strAddress);

        for(int i = 0; i < bTmp.length; ++i) {
            bSend[4 + i] = bTmp[i];
        }

        DateTime datetime = new DateTime(recTime);
        bSend[6] = Utility.getBCDVal(datetime.getYear());
        bSend[7] = Utility.getBCDVal(datetime.getMonthOfYear());
        bSend[8] = Utility.getBCDVal(datetime.getDayOfMonth());
        bSend[9] = Utility.getBCDVal(datetime.getHourOfDay());
        bSend[10] = Utility.getBCDVal(datetime.getMinuteOfHour());
        bSend[11] = 1;
        bSend[12] = 15;
        bSend[13] = 2;
        bSend[14] = -50;
        bSend[15] = 0;
        bSend[16] = 5;
        bSend[17] = 30;

        int i;
        for(i = 0; i < 28; ++i) {
            bSend[18 + i] = 0;
        }

        for(i = 0; i < bContent.length; ++i) {
            bSend[46 + i] = bContent[i];
        }

        bSend[46 + bContent.length] = Utility.add8(bSend, 0, bContent.length + 46);
        bSend[47 + bContent.length] = 13;
        bSend[48 + bContent.length] = 10;
        byte[] bPkg = new byte[49 + bContent.length];

        for(i = 0; i < bPkg.length; ++i) {
            bPkg[i] = bSend[i];
        }

        return bPkg;
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
            List<String> sendNameList = Arrays.asList(this.sendNames);
            byte[] bContent = new byte[sendNameList.size() * 6];

            for(int i = 0; i < sendNameList.size(); ++i) {
                bContent[i * 6 + 0] = 4;
                bContent[i * 6 + 1] = (byte)(i + 1);
                bContent[i * 6 + 2] = 0;
                bContent[i * 6 + 3] = 0;
                bContent[i * 6 + 4] = 0;
                bContent[i * 6 + 5] = 0;
                Iterator var11 = this.mMonitor.getCfgFactorList().iterator();

                while(var11.hasNext()) {
                    CfgFactor cfgFactor = (CfgFactor)var11.next();
                    if (cfgFactor.getName().equals(sendNameList.get(i))) {
                        Double dTmp = 0.0D;
                        Iterator var13 = recDataList.iterator();

                        while(var13.hasNext()) {
                            RecDataFactor recDataFactor = (RecDataFactor)var13.next();
                            if (recDataFactor.getFactorID() == cfgFactor.getId()) {
                                dTmp = recDataFactor.getDataValue();
                                break;
                            }
                        }

                        float fTmp = Float.parseFloat(dTmp.toString());
                        byte[] bValue = Utility.getByteArray(fTmp);
                        bContent[i * 6 + 2] = bValue[0];
                        bContent[i * 6 + 3] = bValue[1];
                        bContent[i * 6 + 4] = bValue[2];
                        bContent[i * 6 + 5] = bValue[3];
                        break;
                    }
                }
            }

            return this.getSendPkg(recDataTime.getRecTime(), bContent);
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
            List<String> sendNameList = Arrays.asList(this.sendNames);
            byte[] bContent = new byte[sendNameList.size() * 6];

            for(int i = 0; i < sendNameList.size(); ++i) {
                bContent[i * 6 + 0] = 4;
                bContent[i * 6 + 1] = (byte)(i + 1);
                bContent[i * 6 + 2] = 0;
                bContent[i * 6 + 3] = 0;
                bContent[i * 6 + 4] = 0;
                bContent[i * 6 + 5] = 0;
                Iterator var11 = this.mMonitor.getCfgFactorList().iterator();

                while(var11.hasNext()) {
                    CfgFactor cfgFactor = (CfgFactor)var11.next();
                    if (cfgFactor.getName().equals(sendNameList.get(i))) {
                        Double dTmp = 0.0D;
                        Iterator var13 = recDataList.iterator();

                        while(var13.hasNext()) {
                            RecDataFactor recDataFactor = (RecDataFactor)var13.next();
                            if (recDataFactor.getFactorID() == cfgFactor.getId()) {
                                dTmp = recDataFactor.getDataValue();
                                break;
                            }
                        }

                        float fTmp = Float.parseFloat(dTmp.toString());
                        byte[] bValue = Utility.getByteArray(fTmp);
                        bContent[i * 6 + 2] = bValue[0];
                        bContent[i * 6 + 3] = bValue[1];
                        bContent[i * 6 + 4] = bValue[2];
                        bContent[i * 6 + 5] = bValue[3];
                        break;
                    }
                }
            }

            return this.getSendPkg(recDataTime.getRecTime(), bContent);
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
}
