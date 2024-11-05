//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.upload;

import com.grean.station.domain.DO.cfg.CfgComm206;
import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.rec.RecDataFactor;
import com.grean.station.domain.DO.rec.RecDataTime;
import com.grean.station.utils.Utility;
import java.util.Iterator;
import java.util.List;
import org.joda.time.DateTime;

public class UploadSzy206_SH extends UploadInter {
    private boolean bReply = false;

    public UploadSzy206_SH() {
    }

    private byte[] getSendState() {
        byte[] bState = new byte[5];
        byte bMask;

        for(int i = 0; i < 5; ++i) {
            bState[i] = 0;
            bMask = 1;

            for(int j = 8 * i + 0; j < 8 * i + 8; ++j) {
                Iterator var5 = this.mMonitor.getCfgFactorList().iterator();

                label46: {
                    CfgFactor cfgFactor;
                    do {
                        do {
                            do {
                                if (!var5.hasNext()) {
                                    break label46;
                                }

                                cfgFactor = (CfgFactor)var5.next();
                            } while(!cfgFactor.isUsed());
                        } while(!cfgFactor.isFiveType() && !cfgFactor.isParmType());
                    } while(!cfgFactor.getName().equals(((CfgComm206)this.mMonitor.cfgComm206List.get(j)).getName()) && !cfgFactor.getName().equals("酸法" + ((CfgComm206)this.mMonitor.cfgComm206List.get(j)).getName()) && !cfgFactor.getName().equals("碱法" + ((CfgComm206)this.mMonitor.cfgComm206List.get(j)).getName()));

                    bState[i] |= bMask;
                }

                bMask = (byte)(bMask << 1);
            }
        }

        return bState;
    }

    private byte[] getSendPkg(byte[] bContent) {
        byte[] bSend = new byte[256];
        bSend[0] = 104;
        bSend[1] = (byte)(bContent.length + 6);
        bSend[2] = 104;
        if (this.bReply) {
            bSend[3] = -80;
        } else {
            bSend[3] = -70;
        }

        String strAddress;
        if (this.mUploadCfg.getMn().length() > 10) {
            strAddress = this.mUploadCfg.getMn().substring(0, 10);
        } else {
            strAddress = this.mUploadCfg.getMn() + "0000000000";
            strAddress = strAddress.substring(0, 10);
        }

        byte[] bTmp = Utility.hexStringToBytes(strAddress);

        int i;
        for(i = 0; i < bTmp.length; ++i) {
            bSend[4 + i] = bTmp[i];
        }

        for(i = 0; i < bContent.length; ++i) {
            bSend[9 + i] = bContent[i];
        }

        bSend[9 + bContent.length] = Utility.szy_CRC_8(bSend, 3, bContent.length + 6);
        bSend[10 + bContent.length] = 22;
        byte[] bPkg = new byte[11 + bContent.length];

        for(i = 0; i < bPkg.length; ++i) {
            bPkg[i] = bSend[i];
        }

        return bPkg;
    }

    public byte[] getBootTimePkg(String strQN, String strFlag) {
        byte[] bContent = new byte[]{2, -16};
        return this.getSendPkg(bContent);
    }

    public byte[] getHeartBeatPkg(String strQN, String strFlag) {
        byte[] bContent = new byte[]{2, -14};
        return this.getSendPkg(bContent);
    }

    public byte[] getRsrvSamplePkg(String strQN, String strFlag, String strDataTime, String strVaseNo) {
        return null;
    }

    public byte[] getRealTimePkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        RecDataTime recTime = this.mMonitor.getRecMapper().getLastRecTimeHour();
        List<RecDataFactor> recDataList = this.mMonitor.getRecMapper().getRecDataHourListByTime(recTime);
        if (recDataList == null) {
            return null;
        } else {
            byte[] bRealTime = new byte[256];
            if (this.bReply) {
                bRealTime[0] = -80;
            } else {
                bRealTime[0] = -64;
            }

            byte[] bState = this.getSendState();

            int i;
            for(i = 0; i < 5; ++i) {
                bRealTime[1 + i] = bState[i];
            }

            int iIndex = 6;

            label83:
            for(i = 0; i < 40; ++i) {
                int stateIndex = i / 8;
                int stateOffset = i % 8;
                if (Utility.getBit(bState, stateIndex, stateOffset)) {
                    double dTmp = 0.0D;
                    Iterator var17 = this.mMonitor.getCfgFactorList().iterator();

                    while(true) {
                        while(true) {
                            CfgFactor cfgFactor;
                            do {
                                if (!var17.hasNext()) {
                                    for(int j = 0; j < ((CfgComm206)this.mMonitor.cfgComm206List.get(i)).getPlen(); ++j) {
                                        dTmp *= 10.0D;
                                    }

                                    int iTmp = (int)dTmp;
                                    bRealTime[iIndex] = Utility.getBCDVal(iTmp % 100);
                                    iTmp /= 100;
                                    bRealTime[iIndex + 1] = Utility.getBCDVal(iTmp % 100);
                                    iTmp /= 100;
                                    bRealTime[iIndex + 2] = Utility.getBCDVal(iTmp % 100);
                                    iTmp /= 100;
                                    bRealTime[iIndex + 3] = Utility.getBCDVal(iTmp % 100);
                                    iIndex += 4;
                                    continue label83;
                                }

                                cfgFactor = (CfgFactor)var17.next();
                            } while(!cfgFactor.getName().equals(((CfgComm206)this.mMonitor.cfgComm206List.get(i)).getName()) && !cfgFactor.getName().equals("酸法" + ((CfgComm206)this.mMonitor.cfgComm206List.get(i)).getName()) && !cfgFactor.getName().equals("碱法" + ((CfgComm206)this.mMonitor.cfgComm206List.get(i)).getName()));

                            Iterator var19 = recDataList.iterator();

                            while(var19.hasNext()) {
                                RecDataFactor recDataFactor = (RecDataFactor)var19.next();
                                if (recDataFactor.getFactorID() == cfgFactor.getId() && recDataFactor.getAlarmType() != 99) {
                                    dTmp = recDataFactor.getDataValue();
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            bRealTime[iIndex] = 0;
            bRealTime[iIndex + 1] = 0;
            bRealTime[iIndex + 2] = 0;
            bRealTime[iIndex + 3] = 0;
            iIndex += 4;
            if (!this.bReply) {
                DateTime Current_Time = new DateTime(recTime.getRecTime());
                bRealTime[iIndex] = Utility.getBCDVal(Current_Time.getSecondOfMinute());
                bRealTime[iIndex + 1] = Utility.getBCDVal(Current_Time.getMinuteOfHour());
                bRealTime[iIndex + 2] = Utility.getBCDVal(Current_Time.getHourOfDay());
                bRealTime[iIndex + 3] = Utility.getBCDVal(Current_Time.getDayOfMonth());
                bRealTime[iIndex + 4] = 10;
                iIndex += 5;
            }

            byte[] bContent = new byte[iIndex];

            for(i = 0; i < iIndex; ++i) {
                bContent[i] = bRealTime[i];
            }

            return this.getSendPkg(bContent);
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
            byte[] bRealTime = new byte[256];
            if (this.bReply) {
                bRealTime[0] = -80;
            } else {
                bRealTime[0] = -64;
            }

            byte[] bState = this.getSendState();

            int i;
            for(i = 0; i < 5; ++i) {
                bRealTime[1 + i] = bState[i];
            }

            int iIndex = 6;

            label79:
            for(i = 0; i < 40; ++i) {
                Iterator var14 = this.mMonitor.getCfgFactorList().iterator();

                CfgFactor cfgFactor;
                do {
                    if (!var14.hasNext()) {
                        continue label79;
                    }

                    cfgFactor = (CfgFactor)var14.next();
                } while(!cfgFactor.getName().equals(((CfgComm206)this.mMonitor.cfgComm206List.get(i)).getName()) && !cfgFactor.getName().equals("酸法" + ((CfgComm206)this.mMonitor.cfgComm206List.get(i)).getName()) && !cfgFactor.getName().equals("碱法" + ((CfgComm206)this.mMonitor.cfgComm206List.get(i)).getName()));

                double dTmp = 0.0D;
                Iterator var16 = recDataList.iterator();

                while(var16.hasNext()) {
                    RecDataFactor recDataFactor = (RecDataFactor)var16.next();
                    if (recDataFactor.getFactorID() == cfgFactor.getId()) {
                        if (recDataFactor.getAlarmType() != 99) {
                            dTmp = recDataFactor.getDataValue();
                        }
                        break;
                    }
                }

                for(int j = 0; j < ((CfgComm206)this.mMonitor.cfgComm206List.get(i)).getPlen(); ++j) {
                    dTmp *= 10.0D;
                }

                int iTmp = (int)dTmp;
                bRealTime[iIndex] = Utility.getBCDVal(iTmp % 100);
                iTmp /= 100;
                bRealTime[iIndex + 1] = Utility.getBCDVal(iTmp % 100);
                iTmp /= 100;
                bRealTime[iIndex + 2] = Utility.getBCDVal(iTmp % 100);
                iTmp /= 100;
                bRealTime[iIndex + 3] = Utility.getBCDVal(iTmp % 100);
                iIndex += 4;
            }

            bRealTime[iIndex] = 0;
            bRealTime[iIndex + 1] = 0;
            bRealTime[iIndex + 2] = 0;
            bRealTime[iIndex + 3] = 0;
            iIndex += 4;
            if (!this.bReply) {
                DateTime Current_Time = new DateTime(recDataTime.getRecTime());
                bRealTime[iIndex] = Utility.getBCDVal(Current_Time.getSecondOfMinute());
                bRealTime[iIndex + 1] = Utility.getBCDVal(Current_Time.getMinuteOfHour());
                bRealTime[iIndex + 2] = Utility.getBCDVal(Current_Time.getHourOfDay());
                bRealTime[iIndex + 3] = Utility.getBCDVal(Current_Time.getDayOfMonth());
                bRealTime[iIndex + 4] = 10;
                iIndex += 5;
            }

            byte[] bContent = new byte[iIndex];

            for(i = 0; i < iIndex; ++i) {
                bContent[i] = bRealTime[i];
            }

            return this.getSendPkg(bContent);
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
