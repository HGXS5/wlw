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
import org.joda.time.DateTime;

public class UploadDJK extends UploadInter {
    public UploadDJK() {
    }

    private String getFactorVal(String factorName, List<RecDataFactor> recDataList) {
        Iterator var3 = this.mMonitor.cfgFactorList.iterator();

        while(true) {
            CfgFactor cfgFactor;
            do {
                if (!var3.hasNext()) {
                    return "0";
                }

                cfgFactor = (CfgFactor)var3.next();
            } while(!cfgFactor.getName().contains(factorName));

            Iterator var5 = recDataList.iterator();

            while(var5.hasNext()) {
                RecDataFactor recDataFactor = (RecDataFactor)var5.next();
                if (recDataFactor.getFactorID() == cfgFactor.getId()) {
                    String strDecimal = "%." + cfgFactor.getDecimals() + "f";
                    return String.format(strDecimal, recDataFactor.getDataValue());
                }
            }
        }
    }

    private byte[] getHourContent(RecDataTime recDataTime) {
        if (recDataTime == null) {
            return null;
        } else {
            List<RecDataFactor> recDataList = this.mMonitor.getRecMapper().getRecDataHourListByTime(recDataTime);
            if (recDataList == null) {
                return null;
            } else {
                String strContent = "0001" + TimeHelper.getyyMMddHHmmss_Str(new DateTime()) + "ST " + this.mUploadCfg.getMn() + " K TT " + TimeHelper.getyyMMddHHmmss_Str(recDataTime.getRecTime()) + " MIN 01 ";
                strContent = strContent + "C " + this.getFactorVal("水温", recDataList) + " ";
                strContent = strContent + "PH " + this.getFactorVal("pH", recDataList) + " ";
                strContent = strContent + "TU " + this.getFactorVal("浊度", recDataList) + " ";
                strContent = strContent + "DO " + this.getFactorVal("溶解氧", recDataList) + " ";
                strContent = strContent + "COND " + this.getFactorVal("电导率", recDataList) + " ";
                strContent = strContent + "CHL " + this.getFactorVal("叶绿素", recDataList) + " ";
                strContent = strContent + "Tox " + this.getFactorVal("生物毒性", recDataList) + " ";
                strContent = strContent + "NH4N " + this.getFactorVal("氨氮", recDataList) + " ";
                strContent = strContent + "TP " + this.getFactorVal("总磷", recDataList) + " ";
                strContent = strContent + "CODMN " + this.getFactorVal("高锰酸盐指数", recDataList) + " ";
                strContent = strContent + "TN " + this.getFactorVal("总氮", recDataList) + " ";
                strContent = strContent + "ZN " + this.getFactorVal("铜", recDataList) + " ";
                strContent = strContent + "CD " + this.getFactorVal("锌", recDataList) + " ";
                strContent = strContent + "PB " + this.getFactorVal("镉", recDataList) + " ";
                strContent = strContent + "CU " + this.getFactorVal("铅", recDataList) + " ";
                strContent = strContent + "AI " + this.getFactorVal("辐射", recDataList) + " ";
                strContent = strContent + "US " + this.getFactorVal("风速", recDataList) + " ";
                strContent = strContent + "UA " + this.getFactorVal("风向", recDataList) + " ";
                strContent = strContent + "PT " + this.getFactorVal("降雨量", recDataList) + " ";
                strContent = strContent + "TR " + this.getFactorVal("温度", recDataList) + " ";
                strContent = strContent + "TH " + this.getFactorVal("湿度", recDataList) + " ";
                byte[] bTmp = strContent.getBytes();
                byte[] bContent = new byte[bTmp.length + 1];

                for(int i = 0; i < bTmp.length; ++i) {
                    bContent[i] = bTmp[i];
                }

                bContent[bTmp.length] = 3;
                return bContent;
            }
        }
    }

    public byte[] getBootTimePkg(String strQN, String strFlag) {
        return null;
    }

    public byte[] getHeartBeatPkg(String strQN, String strFlag) {
        byte[] bSend = new byte[1024];
        bSend[0] = 1;
        int iLen = 1;
        String strHead = "10" + this.mUploadCfg.getMn() + this.mUploadCfg.getPassword() + "2F";
        byte[] bTmp = strHead.getBytes();

        for(int i = 0; i < bTmp.length; ++i) {
            bSend[iLen + i] = bTmp[i];
        }

        iLen = iLen + bTmp.length;
        bSend[iLen] = 48;
        bSend[iLen + 1] = 48;
        bSend[iLen + 2] = 49;
        bSend[iLen + 3] = 48;
        bSend[iLen + 4] = 2;
        iLen += 5;
        String strContent = "0001" + TimeHelper.getyyMMddHHmmss_Str(new DateTime());
        byte[] bContent = strContent.getBytes();

        int crc_result;
        for(crc_result = 0; crc_result < bContent.length; ++crc_result) {
            bSend[iLen + crc_result] = bContent[crc_result];
        }

        iLen += bContent.length;
        bSend[iLen] = 3;
        ++iLen;
        crc_result = Utility.calcCrc16(bSend, 0, iLen);
        byte[] tmp_bytes = Utility.getByteArray(crc_result);
        bTmp = new byte[]{tmp_bytes[3], tmp_bytes[2]};
        String strCrc = Utility.bytesToHexStringNoSpace(bTmp, 2);
        bTmp = strCrc.getBytes();

        int i;
        for(i = 0; i < bTmp.length; ++i) {
            bSend[iLen + i] = bTmp[i];
        }

        iLen += bTmp.length;
        byte[] bPkg = new byte[iLen];

        for(i = 0; i < bPkg.length; ++i) {
            bPkg[i] = bSend[i];
        }

        return bPkg;
    }

    public byte[] getRsrvSamplePkg(String strQN, String strFlag, String strDataTime, String strVaseNo) {
        return new byte[0];
    }

    public byte[] getRealTimePkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return null;
    }

    public byte[] getMinDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return new byte[0];
    }

    public byte[] getHourDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        byte[] bSend = new byte[1024];
        bSend[0] = 1;
        int iLen = 1;
        String strHead = "10" + this.mUploadCfg.getMn() + this.mUploadCfg.getPassword() + "E0";
        byte[] bTmp = strHead.getBytes();

        for(int i = 0; i < bTmp.length; ++i) {
            bSend[iLen + i] = bTmp[i];
        }

        iLen = iLen + bTmp.length;
        bSend[iLen] = 0;
        bSend[iLen + 1] = 0;
        bSend[iLen + 2] = 0;
        bSend[iLen + 3] = 0;
        bSend[iLen + 4] = 2;
        byte[] bContent = this.getHourContent(recDataTime);
        if (bContent == null) {
            return null;
        } else {
            bTmp = new byte[]{(byte)((bContent.length - 1) / 256), (byte)((bContent.length - 1) % 256)};
            String strLength = Utility.bytesToHexStringNoSpace(bTmp, 2);
            bTmp = strLength.getBytes();

            int crc_result;
            for(crc_result = 0; crc_result < bTmp.length; ++crc_result) {
                bSend[iLen + crc_result] = bTmp[crc_result];
            }

            iLen += 5;

            for(crc_result = 0; crc_result < bContent.length; ++crc_result) {
                bSend[iLen + crc_result] = bContent[crc_result];
            }

            iLen += bContent.length;
            crc_result = Utility.calcCrc16(bSend, 0, iLen);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            bTmp = new byte[]{tmp_bytes[3], tmp_bytes[2]};
            String strCrc = Utility.bytesToHexStringNoSpace(bTmp, 2);
            bTmp = strCrc.getBytes();

            int i;
            for(i = 0; i < bTmp.length; ++i) {
                bSend[iLen + i] = bTmp[i];
            }

            iLen += bTmp.length;
            byte[] bPkg = new byte[iLen];

            for(i = 0; i < bPkg.length; ++i) {
                bPkg[i] = bSend[i];
            }

            return bPkg;
        }
    }

    public byte[] getDayDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return new byte[0];
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
}
