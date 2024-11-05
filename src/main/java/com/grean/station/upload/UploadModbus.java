//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.upload;

import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.cfg.CfgModbus;
import com.grean.station.domain.DO.rec.RecDataFactor;
import com.grean.station.domain.DO.rec.RecDataTime;
import com.grean.station.utils.Utility;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.joda.time.DateTime;

public class UploadModbus extends UploadInter {
    private byte[] bRegister = new byte[256];
    private List<CfgFactor> cfgFactorList;
    private List<CfgModbus> cfgModbusList;
    List<RecDataFactor> recDataList;

    public UploadModbus() {
    }

    public byte[] getBootTimePkg(String strQN, String strFlag) {
        return null;
    }

    public byte[] getHeartBeatPkg(String strQN, String strFlag) {
        byte[] bSend = new byte[]{(byte)Integer.parseInt(this.mUploadCfg.getMn()), 24, 1, 0, 0};
        int crc_result = Utility.calcCrc16(bSend, 0, bSend.length - 2);
        byte[] tmp_bytes = Utility.getByteArray(crc_result);
        bSend[bSend.length - 2] = tmp_bytes[2];
        bSend[bSend.length - 1] = tmp_bytes[3];
        this.mReCount = 0;
        return bSend;
    }

    public byte[] getRsrvSamplePkg(String strQN, String strFlag, String strDataTime, String strVaseNo) {
        return null;
    }

    public byte[] getRealTimePkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        this.cfgFactorList = this.mMonitor.getCfgFactorList();
        this.cfgModbusList = this.mMonitor.getCfgMapper().getCfgModbusList();
        String var5 = this.mMonitor.getDtu_data();
        byte var6 = -1;
        switch(var5.hashCode()) {
            case 2192718:
                if (var5.equals("Flow")) {
                    var6 = 1;
                }
                break;
            case 2255364:
                if (var5.equals("Hour")) {
                    var6 = 0;
                }
                break;
            case 2543038:
                if (var5.equals("Real")) {
                    var6 = 2;
                }
        }

        switch(var6) {
            case 0:
                recDataTime = this.mMonitor.getRecMapper().getLastRecTimeHour();
                if (recDataTime == null) {
                    return null;
                }

                this.recDataList = this.mMonitor.getRecMapper().getRecDataHourListByTime(recDataTime);
                break;
            case 1:
                recDataTime = this.mMonitor.getRecMapper().getLastRecTimeFlow();
                if (recDataTime == null) {
                    return null;
                }

                this.recDataList = this.mMonitor.getRecMapper().getRecDataFlowListByTime(recDataTime);
                break;
            case 2:
                recDataTime = this.mMonitor.getRecMapper().getLastRecTimeMin();
                if (recDataTime == null) {
                    return null;
                }

                this.recDataList = this.mMonitor.getRecMapper().getRecDataMinListByTime(recDataTime);
                break;
            default:
                recDataTime = this.mMonitor.getRecMapper().getLastRecTimeHour();
                Iterator var7;
                CfgFactor cfgFactor;
                if (recDataTime == null) {
                    this.recDataList = new ArrayList();
                    var7 = this.cfgFactorList.iterator();

                    while(var7.hasNext()) {
                        cfgFactor = (CfgFactor)var7.next();
                        if (cfgFactor.getDataMea() != null) {
                            RecDataFactor recDataFactor = new RecDataFactor();
                            recDataFactor.setFactorID(cfgFactor.getId());
                            recDataFactor.setDataValue(cfgFactor.getDataMea());
                            this.recDataList.add(recDataFactor);
                        }
                    }
                } else {
                    this.recDataList = this.mMonitor.getRecMapper().getRecDataHourListByTime(recDataTime);
                    var7 = this.cfgFactorList.iterator();

                    label121:
                    while(true) {
                        do {
                            do {
                                if (!var7.hasNext()) {
                                    break label121;
                                }

                                cfgFactor = (CfgFactor)var7.next();
                            } while(cfgFactor.getDataMea() == null);
                        } while(cfgFactor.getType().toUpperCase().equals("PARM"));

                        boolean bCheck = false;
                        Iterator var10 = this.recDataList.iterator();

                        while(var10.hasNext()) {
                            RecDataFactor recDataFactor = (RecDataFactor)var10.next();
                            if (recDataFactor.getFactorID() == cfgFactor.getId()) {
                                recDataFactor.setDataValue(cfgFactor.getDataMea());
                                bCheck = true;
                                break;
                            }
                        }

                        if (!bCheck) {
                            RecDataFactor recDataFactor = new RecDataFactor();
                            recDataFactor.setFactorID(cfgFactor.getId());
                            recDataFactor.setDataValue(cfgFactor.getDataMea());
                            this.recDataList.add(recDataFactor);
                        }
                    }
                }
        }

        Iterator var15 = this.cfgModbusList.iterator();

        CfgModbus cfgModbus;
        while(var15.hasNext()) {
            cfgModbus = (CfgModbus)var15.next();
            if (cfgModbus.getName().contains("时间")) {
                if (recDataTime != null) {
                    DateTime dateTime = new DateTime(recDataTime.getRecTime());
                    this.bRegister[cfgModbus.getAddress() * 2 + 0] = Utility.getBCDVal(dateTime.getYear() % 100);
                    this.bRegister[cfgModbus.getAddress() * 2 + 1] = Utility.getBCDVal(dateTime.getMonthOfYear());
                    this.bRegister[cfgModbus.getAddress() * 2 + 2] = Utility.getBCDVal(dateTime.getDayOfMonth());
                    this.bRegister[cfgModbus.getAddress() * 2 + 3] = Utility.getBCDVal(dateTime.getHourOfDay());
                    this.bRegister[cfgModbus.getAddress() * 2 + 4] = Utility.getBCDVal(dateTime.getMinuteOfHour());
                    this.bRegister[cfgModbus.getAddress() * 2 + 5] = Utility.getBCDVal(dateTime.getSecondOfMinute());
                }
                break;
            }
        }

        var15 = this.cfgModbusList.iterator();

        while(true) {
            while(true) {
                label83:
                while(var15.hasNext()) {
                    cfgModbus = (CfgModbus)var15.next();
                    double dTmp = 0.0D;
                    Iterator var20 = this.cfgFactorList.iterator();

                    while(var20.hasNext()) {
                        CfgFactor cfgFactor = (CfgFactor)var20.next();
                        if (cfgModbus.getName().equals(cfgFactor.getName())) {
                            Iterator var23 = this.recDataList.iterator();

                            while(var23.hasNext()) {
                                RecDataFactor recDataFactor = (RecDataFactor)var23.next();
                                if (recDataFactor.getFactorID() == cfgFactor.getId()) {
                                    dTmp = recDataFactor.getDataValue();
                                    float fTmp = Float.parseFloat(dTmp + "");
                                    byte[] bTmp = Utility.getByteArray(fTmp);
                                    this.bRegister[cfgModbus.getAddress() * 2] = bTmp[0];
                                    this.bRegister[cfgModbus.getAddress() * 2 + 1] = bTmp[1];
                                    this.bRegister[cfgModbus.getAddress() * 2 + 2] = bTmp[2];
                                    this.bRegister[cfgModbus.getAddress() * 2 + 3] = bTmp[3];
                                    continue label83;
                                }
                            }
                            break;
                        }
                    }
                }

                return null;
            }
        }
    }

    public byte[] getMinDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return null;
    }

    public byte[] getHourDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return null;
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
        if (bRecv[0] != Integer.parseInt(this.mUploadCfg.getMn())) {
            return null;
        } else {
            int address = Utility.getShort(bRecv, 2);
            int length = Utility.getShort(bRecv, 4);
            int crc_result = Utility.calcCrc16(bRecv, 0, 6);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            if (tmp_bytes[2] == bRecv[6] && tmp_bytes[3] == bRecv[7]) {
                byte[] bLength = Utility.getByteArray(length * 2);
                switch(bRecv[1]) {
                    case 3:
                    case 4:
                        byte[] bSend;
                        byte[] crc_bytes;
                        if (address * 2 + length * 2 > 240) {
                            bSend = new byte[]{bRecv[0], (byte)(128 + bRecv[1]), 2, 0, 0};
                            crc_result = Utility.calcCrc16(bSend, 0, bSend.length - 2);
                            crc_bytes = Utility.getByteArray(crc_result);
                            bSend[bSend.length - 2] = crc_bytes[2];
                            bSend[bSend.length - 1] = crc_bytes[3];
                        } else {
                            bSend = new byte[length * 2 + 5];
                            bSend[0] = bRecv[0];
                            bSend[1] = bRecv[1];
                            bSend[2] = bLength[3];

                            for(int i = 0; i < length * 2; ++i) {
                                bSend[3 + i] = this.bRegister[address * 2 + i];
                            }

                            crc_result = Utility.calcCrc16(bSend, 0, bSend.length - 2);
                            crc_bytes = Utility.getByteArray(crc_result);
                            bSend[bSend.length - 2] = crc_bytes[2];
                            bSend[bSend.length - 1] = crc_bytes[3];
                        }

                        List<byte[]> responseList = new ArrayList();
                        responseList.add(bSend);
                        return responseList;
                    default:
                        return null;
                }
            } else {
                return null;
            }
        }
    }
}
