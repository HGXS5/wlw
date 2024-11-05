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

public class UploadSL651 extends UploadInter {
    private byte platformAddress = 1;
    private int serialNumber = 1;

    public UploadSL651() {
    }

    private byte getByteStruct(int byteLen, int pointLen) {
        if (byteLen >= 0 && byteLen < 32 && pointLen >= 0 && pointLen < 8) {
            byte byte1 = (byte)byteLen;
            byte byte2 = (byte)pointLen;
            return (byte)((byte1 << 3 | byte2) & 255);
        } else {
            return 0;
        }
    }

    private String getByteVal(double dVal, int byteLen, int pointLen) {
        double dTmp = dVal;

        for(int i = 0; i < pointLen; ++i) {
            dTmp *= 10.0D;
        }

        int iTmp = (int)dTmp;
        return String.format("%0" + byteLen * 2 + "d", iTmp);
    }
    @SuppressWarnings("FallThrough")
    private byte[] getSendContent(int pkgType, RecDataTime recDataTime) {
        if (recDataTime == null) {
            return null;
        } else {
            byte[] bSend = new byte[1024];
            int byteLen = 3;
            bSend[0] = -15;
            bSend[1] = -15;
            String strAddress;
            if (this.mUploadCfg.getMn().length() > 10) {
                strAddress = this.mUploadCfg.getMn().substring(0, 10);
            } else {
                strAddress = this.mUploadCfg.getMn() + "0000000000";
                strAddress = strAddress.substring(0, 10);
            }

            byte[] bTmp = Utility.hexStringToBytes(strAddress);

            for(int i = 0; i < bTmp.length; ++i) {
                bSend[2 + i] = bTmp[i];
            }

            int iIndex;
            bSend[7] = 71;
            bSend[8] = -16;
            bSend[9] = -16;
            DateTime recTime = new DateTime(recDataTime.getRecTime());
            bSend[10] = Utility.getBCDVal(recTime.getYear());
            bSend[11] = Utility.getBCDVal(recTime.getMonthOfYear());
            bSend[12] = Utility.getBCDVal(recTime.getDayOfMonth());
            bSend[13] = Utility.getBCDVal(recTime.getHourOfDay());
            bSend[14] = Utility.getBCDVal(recTime.getMinuteOfHour());
            iIndex = 15;
            String strVal;
            String strTmp;
            label92:
            switch(pkgType) {
                case 50:
                    List<RecDataFactor> minDataList = this.mMonitor.getRecMapper().getRecDataMinListByTime(recDataTime);
                    Iterator var21 = this.mMonitor.getCfgFactorList().iterator();

                    label90:
                    while(true) {
                        if (!var21.hasNext()) {
                            break label92;
                        }

                        CfgFactor cfgFactor = (CfgFactor)var21.next();
                        Iterator var23 = minDataList.iterator();

                        while(true) {
                            RecDataFactor recDataFactor;
                            do {
                                if (!var23.hasNext()) {
                                    continue label90;
                                }

                                recDataFactor = (RecDataFactor)var23.next();
                            } while(recDataFactor.getFactorID() != cfgFactor.getId());

                            bSend[iIndex] = Utility.hexStringToByte(cfgFactor.getCodeSL());
                            bSend[iIndex + 1] = this.getByteStruct(byteLen, cfgFactor.getDecimals());
                            strVal = this.getByteVal(recDataFactor.getDataValue(), byteLen, cfgFactor.getDecimals());

                            for(int i = 0; i < byteLen; ++i) {
                                strTmp = strVal.substring(i * 2, i * 2 + 2);
                                bSend[iIndex + 2 + i] = Utility.getBCDVal(Integer.parseInt(strTmp));
                            }

                            iIndex = iIndex + byteLen + 2;
                        }
                    }
                case 52:
                    List<RecDataFactor> hourDataList = this.mMonitor.getRecMapper().getRecDataHourListByTime(recDataTime);
                    Iterator var14 = this.mMonitor.getCfgFactorList().iterator();

                    label70:
                    while(true) {
                        if (!var14.hasNext()) {
                            break label92;
                        }

                        CfgFactor cfgFactor = (CfgFactor)var14.next();
                        Iterator var16 = hourDataList.iterator();

                        while(true) {
                            RecDataFactor recDataFactor;
                            do {
                                if (!var16.hasNext()) {
                                    continue label70;
                                }

                                recDataFactor = (RecDataFactor)var16.next();
                            } while(recDataFactor.getFactorID() != cfgFactor.getId());

                            bSend[iIndex] = Utility.hexStringToByte(cfgFactor.getCodeSL());
                            bSend[iIndex + 1] = this.getByteStruct(byteLen, cfgFactor.getDecimals());
                            strVal = this.getByteVal(recDataFactor.getDataValue(), byteLen, cfgFactor.getDecimals());

                            for(int i = 0; i < byteLen; ++i) {
                                strTmp = strVal.substring(i * 2, i * 2 + 2);
                                bSend[iIndex + 2 + i] = Utility.getBCDVal(Integer.parseInt(strTmp));
                            }

                            iIndex = iIndex + byteLen + 2;
                        }
                    }
                default:
                    return null;
            }

            byte[] bContent = new byte[iIndex];

            for(int i = 0; i < iIndex; ++i) {
                bContent[i] = bSend[i];
            }

            return bContent;
        }
    }

    private byte[] getSendPkg(int pkgType, RecDataTime recDataTime) {
        byte[] bSend = new byte[1024];
        bSend[0] = 126;
        bSend[1] = 126;
        bSend[2] = this.platformAddress;
        String strAddress;
        if (this.mUploadCfg.getMn().length() > 10) {
            strAddress = this.mUploadCfg.getMn().substring(0, 10);
        } else {
            strAddress = this.mUploadCfg.getMn() + "0000000000";
            strAddress = strAddress.substring(0, 10);
        }

        byte[] bTmp = Utility.hexStringToBytes(strAddress);

        for(int i = 0; i < bTmp.length; ++i) {
            bSend[3 + i] = bTmp[i];
        }

        String strPassword;
        if (this.mUploadCfg.getPassword().length() > 4) {
            strPassword = this.mUploadCfg.getPassword().substring(0, 4);
        } else {
            strPassword = this.mUploadCfg.getPassword() + "0000";
            strPassword = strAddress.substring(0, 4);
        }

        bTmp = Utility.hexStringToBytes(strPassword);
        bSend[8] = bTmp[0];
        bSend[9] = bTmp[1];
        bSend[10] = (byte)pkgType;
        byte[] bContent = this.getSendContent(pkgType, recDataTime);
        int iContent;
        if (bContent == null) {
            iContent = 0;
        } else {
            iContent = bContent.length;
        }

        bTmp = Utility.getByteArray(iContent + 8);
        bSend[11] = bTmp[2];
        bSend[12] = bTmp[3];
        bSend[13] = 2;
        bTmp = Utility.getByteArray(this.serialNumber);
        bSend[14] = bTmp[2];
        bSend[15] = bTmp[3];
        DateTime now = new DateTime();
        bSend[16] = Utility.getBCDVal(now.getYear());
        bSend[17] = Utility.getBCDVal(now.getMonthOfYear());
        bSend[18] = Utility.getBCDVal(now.getDayOfMonth());
        bSend[19] = Utility.getBCDVal(now.getHourOfDay());
        bSend[20] = Utility.getBCDVal(now.getMinuteOfHour());
        bSend[21] = Utility.getBCDVal(now.getSecondOfMinute());

        int crc_result;
        for(crc_result = 0; crc_result < iContent; ++crc_result) {
            bSend[22 + crc_result] = bContent[crc_result];
        }

        bSend[22 + iContent] = 3;
        crc_result = Utility.calcCrc16(bSend, 0, 23 + iContent);
        bTmp = Utility.getByteArray(crc_result);
        bSend[23 + iContent] = bTmp[3];
        bSend[24 + iContent] = bTmp[2];
        if (this.serialNumber < 65535) {
            ++this.serialNumber;
        } else {
            this.serialNumber = 1;
        }

        byte[] bPkg = new byte[25 + iContent];

        for(int i = 0; i < bPkg.length; ++i) {
            bPkg[i] = bSend[i];
        }

        return bPkg;
    }

    public byte[] getBootTimePkg(String strQN, String strFlag) {
        return null;
    }

    public byte[] getHeartBeatPkg(String strQN, String strFlag) {
        return this.getSendPkg(47, (RecDataTime)null);
    }

    public byte[] getRsrvSamplePkg(String strQN, String strFlag, String strDataTime, String strVaseNo) {
        return new byte[0];
    }

    public byte[] getRealTimePkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return this.getSendPkg(50, recDataTime);
    }

    public byte[] getMinDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return new byte[0];
    }

    public byte[] getHourDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return this.getSendPkg(52, recDataTime);
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
