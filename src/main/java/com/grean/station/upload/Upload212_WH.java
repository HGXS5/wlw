//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.upload;

import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.rec.RecDataFactor;
import com.grean.station.domain.DO.rec.RecDataTime;
import com.grean.station.utils.Utility;
import java.util.Iterator;
import java.util.List;
import org.joda.time.DateTime;

public class Upload212_WH extends UploadInter {
    String[] factors = new String[]{"pH", "水温", "溶解氧", "电导率", "浊度", "叶绿素", "藻密度", "氨氮", "高锰酸盐指数", "总磷", "总氮", "电压", "备用", "备用", "备用", "备用"};

    public Upload212_WH() {
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
        byte[] bSend = new byte[75];
        List<RecDataFactor> recDataList = this.mMonitor.getRecMapper().getRecDataHourListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            bSend[0] = (byte)Integer.parseInt(this.mUploadCfg.getMn());
            bSend[1] = 3;
            bSend[2] = 70;
            DateTime dateTime = new DateTime(recDataTime.getRecTime().getTime());
            bSend[3] = Utility.getBCDVal(dateTime.getYear() % 100);
            bSend[4] = Utility.getBCDVal(dateTime.getMonthOfYear());
            bSend[5] = Utility.getBCDVal(dateTime.getDayOfMonth());
            bSend[6] = Utility.getBCDVal(dateTime.getHourOfDay());
            bSend[7] = Utility.getBCDVal(dateTime.getMinuteOfHour());
            bSend[8] = Utility.getBCDVal(dateTime.getSecondOfMinute());

            int crc_result;
            byte[] tmp_bytes;
            for(crc_result = 0; crc_result < this.factors.length; ++crc_result) {
                tmp_bytes = this.getFactorVal(this.factors[crc_result], recDataList);
                bSend[9 + 4 * crc_result] = tmp_bytes[0];
                bSend[10 + 4 * crc_result] = tmp_bytes[1];
                bSend[11 + 4 * crc_result] = tmp_bytes[2];
                bSend[12 + 4 * crc_result] = tmp_bytes[3];
            }

            crc_result = Utility.calcCrc16(bSend, 0, bSend.length - 2);
            tmp_bytes = Utility.getByteArray(crc_result);
            bSend[bSend.length - 2] = tmp_bytes[2];
            bSend[bSend.length - 1] = tmp_bytes[3];
            return bSend;
        }
    }

    private byte[] getFactorVal(String factorName, List<RecDataFactor> recDataList) {
        byte[] bTmp = new byte[]{0, 0, 0, 0};
        Iterator var4 = this.mMonitor.cfgFactorList.iterator();

        while(var4.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor)var4.next();
            if (cfgFactor.getName().equals(factorName)) {
                Iterator var6 = recDataList.iterator();

                RecDataFactor recDataFactor;
                do {
                    if (!var6.hasNext()) {
                        return bTmp;
                    }

                    recDataFactor = (RecDataFactor)var6.next();
                } while(recDataFactor.getFactorID() != cfgFactor.getId());

                if (recDataFactor.getDataValue() != null) {
                    return Utility.getByteArray(recDataFactor.getDataValue().floatValue());
                }
                break;
            }
        }

        return bTmp;
    }

    public byte[] getDayDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return null;
    }

    public byte[] getFiveDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        byte[] bSend = new byte[75];
        List<RecDataFactor> recDataList = this.mMonitor.getRecMapper().getRecDataFiveListByTime(recDataTime);
        if (recDataList == null) {
            return null;
        } else {
            bSend[0] = (byte)Integer.parseInt(this.mUploadCfg.getMn());
            bSend[1] = 3;
            bSend[2] = 70;
            DateTime dateTime = new DateTime(recDataTime.getRecTime().getTime());
            bSend[3] = Utility.getBCDVal(dateTime.getYear() % 100);
            bSend[4] = Utility.getBCDVal(dateTime.getMonthOfYear());
            bSend[5] = Utility.getBCDVal(dateTime.getDayOfMonth());
            bSend[6] = Utility.getBCDVal(dateTime.getHourOfDay());
            bSend[7] = Utility.getBCDVal(dateTime.getMinuteOfHour());
            bSend[8] = Utility.getBCDVal(dateTime.getSecondOfMinute());

            int crc_result;
            byte[] tmp_bytes;
            for(crc_result = 0; crc_result < 7; ++crc_result) {
                tmp_bytes = this.getFactorVal(this.factors[crc_result], recDataList);
                bSend[9 + 4 * crc_result] = tmp_bytes[0];
                bSend[10 + 4 * crc_result] = tmp_bytes[1];
                bSend[11 + 4 * crc_result] = tmp_bytes[2];
                bSend[12 + 4 * crc_result] = tmp_bytes[3];
            }

            crc_result = Utility.calcCrc16(bSend, 0, bSend.length - 2);
            tmp_bytes = Utility.getByteArray(crc_result);
            bSend[bSend.length - 2] = tmp_bytes[2];
            bSend[bSend.length - 1] = tmp_bytes[3];
            return bSend;
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

    public boolean doUpload(byte[] bSend, RecDataTime recDataTime, int uploadType) {
        if (bSend == null) {
            return false;
        } else if (this.mInterComm == null) {
            return false;
        } else {
            if (this.mInterComm.isConnect()) {
                this.mInterComm.Close();
            }

            this.mInterComm.Open();

            try {
                for(int i = 0; i < 50; ++i) {
                    Thread.sleep(100L);
                    if (this.mInterComm.isConnect()) {
                        break;
                    }
                }
            } catch (Exception var7) {
            }

            boolean bResult = super.doUpload(bSend, recDataTime, uploadType);

            try {
                Thread.sleep((long)((this.mReCount + 1) * 1500));
            } catch (Exception var6) {
            }

            this.mInterComm.Close();
            return bResult;
        }
    }
}
