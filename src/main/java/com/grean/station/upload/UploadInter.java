//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.upload;

import com.grean.station.comm.CommInter;
import com.grean.station.comm.RecvListener;
import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.cfg.CfgUploadNet;
import com.grean.station.domain.DO.rec.RecDataTime;
import com.grean.station.service.MonitorService;
import com.grean.station.utils.TimeHelper;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.joda.time.DateTime;

public abstract class UploadInter {
    private String uploadName;
    private UploadInter.UploadThread uploadThread;
    public MonitorService mMonitor;
    public CommInter mInterComm = null;
    public CfgUploadNet mUploadCfg;
    public int mPeriods;
    public int mPeriodHeart;
    public int mPeriodReal;
    public int mPeriodHour;
    public int mPeriodFive;
    public int mPeriodStatSys;
    public int mPeriodStatDev;
    public int mPeriodLogSys;
    public int mPeriodLogDev;
    public int mOverTime;
    public int mReCount;
    public boolean bBasic = false;
    String keyString = "Grean@1234567890";
    public ExecutorService fixedThreadPool = Executors.newFixedThreadPool(64);

    public UploadInter() {
    }

    public abstract byte[] getBootTimePkg(String var1, String var2);

    public abstract byte[] getHeartBeatPkg(String var1, String var2);

    public abstract byte[] getRsrvSamplePkg(String var1, String var2, String var3, String var4);

    public abstract byte[] getRealTimePkg(String var1, String var2, RecDataTime var3, int var4);

    public abstract byte[] getMinDataPkg(String var1, String var2, RecDataTime var3, int var4);

    public abstract byte[] getHourDataPkg(String var1, String var2, RecDataTime var3, int var4);

    public abstract byte[] getDayDataPkg(String var1, String var2, RecDataTime var3, int var4);

    public abstract byte[] getFiveDataPkg(String var1, String var2, RecDataTime var3, int var4);

    public abstract byte[] getFiveStdCheckPkg(String var1, String var2, RecDataTime var3, int var4);

    public abstract byte[] getStdCheckPkg(String var1, String var2, RecDataTime var3, int var4);

    public abstract byte[] getZeroCheckPkg(String var1, String var2, RecDataTime var3, int var4);

    public abstract byte[] getSpanCheckPkg(String var1, String var2, RecDataTime var3, int var4);

    public abstract byte[] getRcvrCheckPkg(String var1, String var2, RecDataTime var3, int var4);

    public abstract byte[] getParCheckPkg(String var1, String var2, RecDataTime var3, int var4);

    public abstract byte[] getLogSysPkg(String var1, String var2, RecDataTime var3);

    public abstract byte[] getLogDevPkg(String var1, String var2, RecDataTime var3, String var4);

    public abstract byte[] getStateSysPkg(String var1, String var2, RecDataTime var3, String var4);

    public abstract byte[] getStateDevPkg(String var1, String var2, RecDataTime var3, String var4, String var5);

    public abstract byte[] getParamDevPkg(String var1, String var2, RecDataTime var3, String var4, String var5);

    public abstract byte[] getPowerAlarmPkg(String var1, String var2, boolean var3);

    public abstract List<byte[]> getResponse(byte[] var1);

    public byte[] getSystemAlarmPkg(String strQN, String strFlag, Timestamp alarmTime, String alarmType) {
        return null;
    }

    public void init(MonitorService monitorService, CommInter interComm, CfgUploadNet uploadCfg) {
        this.mMonitor = monitorService;
        this.mInterComm = interComm;
        this.mUploadCfg = uploadCfg;
        this.mPeriodHeart = this.mUploadCfg.getPeriod_heart();
        this.mPeriodReal = this.mUploadCfg.getPeriod_real();
        this.mPeriodStatSys = this.mUploadCfg.getPeriod_sys_state();
        this.mPeriodStatDev = this.mUploadCfg.getPeriod_dev_state();
        this.mPeriodLogSys = this.mUploadCfg.getPeriod_sys_log();
        this.mPeriodLogDev = this.mUploadCfg.getPeriod_dev_log();
        this.mOverTime = this.mUploadCfg.getOver_time();
        this.mReCount = this.mUploadCfg.getRe_count();
        this.mInterComm.setRecvListener(new RecvListener() {
            public void OnRecvEvent(byte[] bRecv) {
                List<byte[]> responseList = UploadInter.this.getResponse(bRecv);
                if (responseList != null) {
                    for(int i = 0; i < responseList.size(); ++i) {
                        UploadInter.this.mInterComm.Send((byte[])responseList.get(i));
                    }
                }

            }
        });
    }

    public void startUpload() {
        this.mPeriods = 1;
        this.uploadThread = new UploadInter.UploadThread();
        this.uploadThread.start();
    }

    public void stopUpload() {
        if (this.uploadThread != null) {
            this.uploadThread.interrupt();
        }

    }

    public boolean sendRsrvSample(String strDataTime, String strVaseNo) {
        String strQN = this.getQN(new Date());
        byte[] bSend = this.getRsrvSamplePkg(strQN, "9", strDataTime, strVaseNo);
        if (bSend == null) {
            return false;
        } else {
            return this.doUpload(bSend);
        }
    }

    public void sendRealTime() {
        RecDataTime recTime = this.mMonitor.getRecMapper().getLastRecTimeMin();
        if (recTime != null) {
            String strQN = this.getQN(new Date());
            byte[] bSend = this.getRealTimePkg(strQN, "9", recTime, 0);
            if (bSend != null) {
                this.doUpload(bSend, recTime, 2);
            }
        }
    }
    @SuppressWarnings("FallThrough")
    public boolean sendMindata(int factorID, Timestamp timestamp) {
        RecDataTime recTime;
        if (timestamp == null) {
            recTime = this.mMonitor.getRecMapper().getLastRecTimeMinAvg();
        } else {
            recTime = this.mMonitor.getRecMapper().getRecTimeMinAvgByTime(timestamp);
        }

        if (recTime == null) {
            return false;
        } else {
            String strQN = this.getQN(new Date());
            byte[] bSend = this.getMinDataPkg(strQN, "9", recTime, factorID);
            if (bSend == null) {
                return false;
            } else if (this.doUpload(bSend)) {
                this.setSendTime(recTime);
                switch(this.mUploadCfg.getId()) {
                    case 1:
                        this.mMonitor.getRecMapper().updateRecTimeMinAvg1(recTime);
                        break;
                    case 2:
                        this.mMonitor.getRecMapper().updateRecTimeMinAvg2(recTime);
                        break;
                    case 3:
                        this.mMonitor.getRecMapper().updateRecTimeMinAvg3(recTime);
                        break;
                    case 4:
                        this.mMonitor.getRecMapper().updateRecTimeMinAvg4(recTime);
                        break;
                    case 5:
                        this.mMonitor.getRecMapper().updateRecTimeMinAvg5(recTime);
                        break;
                    case 6:
                        this.mMonitor.getRecMapper().updateRecTimeMinAvg6(recTime);
                        break;
                    case 7:
                        this.mMonitor.getRecMapper().updateRecTimeMinAvg7(recTime);
                        break;
                    case 8:
                        this.mMonitor.getRecMapper().updateRecTimeMinAvg8(recTime);
                        break;
                    case 9:
                        this.mMonitor.getRecMapper().updateRecTimeMinAvg9(recTime);
                        break;
                    case 10:
                        this.mMonitor.getRecMapper().updateRecTimeMinAvg10(recTime);
                }

                return true;
            } else {
                return false;
            }
        }
    }
    @SuppressWarnings("FallThrough")
    public void reSendHourData(int minOffset) {
        DateTime datetime = (new DateTime()).plusMinutes(minOffset);
        Timestamp timestamp = TimeHelper.jodaToTimestamp(datetime);
        if (this.mUploadCfg.getAuto_upload() != null && this.mUploadCfg.getAuto_upload().getTime() <= timestamp.getTime()) {
            List<RecDataTime> recDataTimeList = null;
            switch(this.mUploadCfg.getId()) {
                case 1:
                    recDataTimeList = this.mMonitor.getRecMapper().getRecTimeHour1();
                    break;
                case 2:
                    recDataTimeList = this.mMonitor.getRecMapper().getRecTimeHour2();
                    break;
                case 3:
                    recDataTimeList = this.mMonitor.getRecMapper().getRecTimeHour3();
                    break;
                case 4:
                    recDataTimeList = this.mMonitor.getRecMapper().getRecTimeHour4();
                    break;
                case 5:
                    recDataTimeList = this.mMonitor.getRecMapper().getRecTimeHour5();
                    break;
                case 6:
                    recDataTimeList = this.mMonitor.getRecMapper().getRecTimeHour6();
                    break;
                case 7:
                    recDataTimeList = this.mMonitor.getRecMapper().getRecTimeHour7();
                    break;
                case 8:
                    recDataTimeList = this.mMonitor.getRecMapper().getRecTimeHour8();
                    break;
                case 9:
                    recDataTimeList = this.mMonitor.getRecMapper().getRecTimeHour9();
                    break;
                case 10:
                    recDataTimeList = this.mMonitor.getRecMapper().getRecTimeHour10();
            }

            if (recDataTimeList != null) {
                try {
                    Iterator var5 = recDataTimeList.iterator();

                    while(var5.hasNext()) {
                        RecDataTime recDataTime = (RecDataTime)var5.next();
                        if (recDataTime.getRecTime().getTime() >= this.mUploadCfg.getAuto_upload().getTime() && recDataTime.getRecTime().getTime() <= timestamp.getTime()) {
                            this.sendHourData(0, recDataTime.getRecTime());
                            Thread.sleep(1000L);
                            this.sendParamDev(0, (Timestamp)recDataTime.getRecTime(), 450);
                            Thread.sleep(1000L);
                            this.sendParamDev(0, (Timestamp)recDataTime.getRecTime(), 451);
                        }
                    }
                } catch (Exception var7) {
                }

            }
        }
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
                this.doUpload(bSend, recTime, 1);
            }
        }
    }
    @SuppressWarnings("FallThrough")
    public boolean sendHourDataAvg(int factorID, Timestamp timestamp) {
        RecDataTime recTime;
        if (timestamp == null) {
            recTime = this.mMonitor.getRecMapper().getLastRecTimeHourAvg();
        } else {
            recTime = this.mMonitor.getRecMapper().getRecTimeHourAvgByTime(timestamp);
        }

        if (recTime == null) {
            return false;
        } else {
            String strQN = this.getQN(new Date());
            byte[] bSend = this.getHourDataPkg(strQN, "9", recTime, factorID);
            if (bSend == null) {
                return false;
            } else if (this.doUpload(bSend)) {
                this.setSendTime(recTime);
                switch(this.mUploadCfg.getId()) {
                    case 1:
                        this.mMonitor.getRecMapper().updateRecTimeHourAvg1(recTime);
                        break;
                    case 2:
                        this.mMonitor.getRecMapper().updateRecTimeHourAvg2(recTime);
                        break;
                    case 3:
                        this.mMonitor.getRecMapper().updateRecTimeHourAvg3(recTime);
                        break;
                    case 4:
                        this.mMonitor.getRecMapper().updateRecTimeHourAvg4(recTime);
                        break;
                    case 5:
                        this.mMonitor.getRecMapper().updateRecTimeHourAvg5(recTime);
                        break;
                    case 6:
                        this.mMonitor.getRecMapper().updateRecTimeHourAvg6(recTime);
                        break;
                    case 7:
                        this.mMonitor.getRecMapper().updateRecTimeHourAvg7(recTime);
                        break;
                    case 8:
                        this.mMonitor.getRecMapper().updateRecTimeHourAvg8(recTime);
                        break;
                    case 9:
                        this.mMonitor.getRecMapper().updateRecTimeHourAvg9(recTime);
                        break;
                    case 10:
                        this.mMonitor.getRecMapper().updateRecTimeHourAvg10(recTime);
                }

                return true;
            } else {
                return false;
            }
        }
    }
    @SuppressWarnings("FallThrough")
    public boolean sendDayDataAvg(int factorID, Timestamp timestamp) {
        RecDataTime recTime;
        if (timestamp == null) {
            recTime = this.mMonitor.getRecMapper().getLastRecTimeDayAvg();
        } else {
            recTime = this.mMonitor.getRecMapper().getRecTimeDayAvgByTime(timestamp);
        }

        if (recTime == null) {
            return false;
        } else {
            String strQN = this.getQN(new Date());
            byte[] bSend = this.getDayDataPkg(strQN, "9", recTime, factorID);
            if (bSend == null) {
                return false;
            } else if (this.doUpload(bSend)) {
                this.setSendTime(recTime);
                switch(this.mUploadCfg.getId()) {
                    case 1:
                        this.mMonitor.getRecMapper().updateRecTimeDayAvg1(recTime);
                        break;
                    case 2:
                        this.mMonitor.getRecMapper().updateRecTimeDayAvg2(recTime);
                        break;
                    case 3:
                        this.mMonitor.getRecMapper().updateRecTimeDayAvg3(recTime);
                        break;
                    case 4:
                        this.mMonitor.getRecMapper().updateRecTimeDayAvg4(recTime);
                        break;
                    case 5:
                        this.mMonitor.getRecMapper().updateRecTimeDayAvg5(recTime);
                        break;
                    case 6:
                        this.mMonitor.getRecMapper().updateRecTimeDayAvg6(recTime);
                        break;
                    case 7:
                        this.mMonitor.getRecMapper().updateRecTimeDayAvg7(recTime);
                        break;
                    case 8:
                        this.mMonitor.getRecMapper().updateRecTimeDayAvg8(recTime);
                        break;
                    case 9:
                        this.mMonitor.getRecMapper().updateRecTimeDayAvg9(recTime);
                        break;
                    case 10:
                        this.mMonitor.getRecMapper().updateRecTimeDayAvg10(recTime);
                }

                return true;
            } else {
                return false;
            }
        }
    }

    public void sendFiveData(int factorID) {
        RecDataTime recTime = this.mMonitor.getRecMapper().getLastRecTimeFive();
        if (recTime != null) {
            String strQN = this.getQN(new Date());
            byte[] bSend = this.getFiveDataPkg(strQN, "9", recTime, factorID);
            if (bSend != null) {
                this.doUpload(bSend, recTime, 3);
            }
        }
    }

    public void sendFiveStdCheck(Timestamp timestamp) {
        RecDataTime recDataTime;
        if (timestamp == null) {
            recDataTime = this.mMonitor.getRecMapper().getLastRecCheckFiveTime();
        } else {
            recDataTime = this.mMonitor.getRecMapper().getRecCheckFiveTimeByTime(timestamp);
        }

        if (recDataTime != null) {
            String strQN = this.getQN(new Date());
            byte[] bSend = this.getFiveStdCheckPkg(strQN, "9", recDataTime, 0);
            if (bSend != null) {
                this.doUpload(bSend, recDataTime, 4);
            }
        }
    }

    public void sendFiveStdCheck(int factorID) {
        RecDataTime recDataTime = this.mMonitor.getRecMapper().getLastRecCheckFiveTime();
        if (recDataTime != null) {
            String strQN = this.getQN(new Date());
            byte[] bSend = this.getFiveStdCheckPkg(strQN, "9", recDataTime, factorID);
            if (bSend != null) {
                this.doUpload(bSend, recDataTime, 4);
            }
        }
    }

    public void reSendStdCheck(int minOffset) {
        DateTime datetime = (new DateTime()).plusMinutes(minOffset);
        Timestamp timestamp = TimeHelper.jodaToTimestamp(datetime);
        if (this.mUploadCfg.getAuto_upload() != null && this.mUploadCfg.getAuto_upload().getTime() <= timestamp.getTime()) {
            if (this.mUploadCfg.getId() != 0) {
                List<RecDataTime> recDataTimeList = this.mMonitor.getRecMapper().getRecNullSendTimeList("rec_check_std", "send_time" + this.mUploadCfg.getId(), 10);
                if (recDataTimeList != null) {
                    try {
                        Iterator var5 = recDataTimeList.iterator();

                        while(var5.hasNext()) {
                            RecDataTime recDataTime = (RecDataTime)var5.next();
                            if (recDataTime.getRecTime().getTime() >= this.mUploadCfg.getAuto_upload().getTime() && recDataTime.getRecTime().getTime() <= timestamp.getTime()) {
                                this.sendStdCheck(0, recDataTime.getRecTime());
                                Thread.sleep(1000L);
                                this.sendParamDev(0, (Timestamp)recDataTime.getRecTime(), 452);
                            }
                        }
                    } catch (Exception var7) {
                    }

                }
            }
        }
    }

    public void sendStdCheck(int factorID, Timestamp timestamp) {
        RecDataTime recDataTime;
        if (timestamp == null) {
            recDataTime = this.mMonitor.getRecMapper().getLastRecCheckStdTime();
        } else {
            recDataTime = this.mMonitor.getRecMapper().getRecCheckStdTimeByTime(timestamp);
        }

        if (recDataTime != null) {
            String strQN = this.getQN(new Date());
            byte[] bSend = this.getStdCheckPkg(strQN, "9", recDataTime, factorID);
            if (bSend != null) {
                this.doUpload(bSend, recDataTime, 5);
            }
        }
    }

    public void reSendZeroCheck(int minOffset) {
        DateTime datetime = (new DateTime()).plusMinutes(minOffset);
        Timestamp timestamp = TimeHelper.jodaToTimestamp(datetime);
        if (this.mUploadCfg.getAuto_upload() != null && this.mUploadCfg.getAuto_upload().getTime() <= timestamp.getTime()) {
            if (this.mUploadCfg.getId() != 0) {
                List<RecDataTime> recDataTimeList = this.mMonitor.getRecMapper().getRecNullSendTimeList("rec_check_zero", "send_time" + this.mUploadCfg.getId(), 10);
                if (recDataTimeList != null) {
                    try {
                        Iterator var5 = recDataTimeList.iterator();

                        while(var5.hasNext()) {
                            RecDataTime recDataTime = (RecDataTime)var5.next();
                            if (recDataTime.getRecTime().getTime() >= this.mUploadCfg.getAuto_upload().getTime() && recDataTime.getRecTime().getTime() <= timestamp.getTime()) {
                                this.sendZeroCheck(0, recDataTime.getRecTime());
                                Thread.sleep(1000L);
                                this.sendParamDev(0, (Timestamp)recDataTime.getRecTime(), 453);
                            }
                        }
                    } catch (Exception var7) {
                    }

                }
            }
        }
    }

    public void sendZeroCheck(int factorID, Timestamp timestamp) {
        RecDataTime recDataTime;
        if (timestamp == null) {
            recDataTime = this.mMonitor.getRecMapper().getLastRecCheckZeroTime();
        } else {
            recDataTime = this.mMonitor.getRecMapper().getRecCheckZeroTimeByTime(timestamp);
        }

        if (recDataTime != null) {
            String strQN = this.getQN(new Date());
            byte[] bSend = this.getZeroCheckPkg(strQN, "9", recDataTime, factorID);
            if (bSend != null) {
                this.doUpload(bSend, recDataTime, 6);
            }
        }
    }

    public void reSendSpanCheck(int minOffset) {
        DateTime datetime = (new DateTime()).plusMinutes(minOffset);
        Timestamp timestamp = TimeHelper.jodaToTimestamp(datetime);
        if (this.mUploadCfg.getAuto_upload() != null && this.mUploadCfg.getAuto_upload().getTime() <= timestamp.getTime()) {
            if (this.mUploadCfg.getId() != 0) {
                List<RecDataTime> recDataTimeList = this.mMonitor.getRecMapper().getRecNullSendTimeList("rec_check_span", "send_time" + this.mUploadCfg.getId(), 10);
                if (recDataTimeList != null) {
                    try {
                        Iterator var5 = recDataTimeList.iterator();

                        while(var5.hasNext()) {
                            RecDataTime recDataTime = (RecDataTime)var5.next();
                            if (recDataTime.getRecTime().getTime() >= this.mUploadCfg.getAuto_upload().getTime() && recDataTime.getRecTime().getTime() <= timestamp.getTime()) {
                                this.sendSpanCheck(0, recDataTime.getRecTime());
                                Thread.sleep(1000L);
                                this.sendParamDev(0, (Timestamp)recDataTime.getRecTime(), 454);
                            }
                        }
                    } catch (Exception var7) {
                    }

                }
            }
        }
    }

    public void sendSpanCheck(int factorID, Timestamp timestamp) {
        RecDataTime recDataTime;
        if (timestamp == null) {
            recDataTime = this.mMonitor.getRecMapper().getLastRecCheckSpanTime();
        } else {
            recDataTime = this.mMonitor.getRecMapper().getRecCheckSpanTimeByTime(timestamp);
        }

        if (recDataTime != null) {
            String strQN = this.getQN(new Date());
            byte[] bSend = this.getSpanCheckPkg(strQN, "9", recDataTime, factorID);
            if (bSend != null) {
                this.doUpload(bSend, recDataTime, 7);
            }
        }
    }

    public void reSendRcvrCheck(int minOffset) {
        DateTime datetime = (new DateTime()).plusMinutes(minOffset);
        Timestamp timestamp = TimeHelper.jodaToTimestamp(datetime);
        if (this.mUploadCfg.getAuto_upload() != null && this.mUploadCfg.getAuto_upload().getTime() <= timestamp.getTime()) {
            if (this.mUploadCfg.getId() != 0) {
                List<RecDataTime> recDataTimeList = this.mMonitor.getRecMapper().getRecNullSendTimeList("rec_check_rcvr", "send_time" + this.mUploadCfg.getId(), 10);
                if (recDataTimeList != null) {
                    try {
                        Iterator var5 = recDataTimeList.iterator();

                        while(var5.hasNext()) {
                            RecDataTime recDataTime = (RecDataTime)var5.next();
                            if (recDataTime.getRecTime().getTime() >= this.mUploadCfg.getAuto_upload().getTime() && recDataTime.getRecTime().getTime() <= timestamp.getTime()) {
                                this.sendRcvrCheck(0, recDataTime.getRecTime());
                                Thread.sleep(1000L);
                                this.sendParamDev(0, (Timestamp)recDataTime.getRecTime(), 457);
                            }
                        }
                    } catch (Exception var7) {
                    }

                }
            }
        }
    }

    public void sendRcvrCheck(int factorID, Timestamp timestamp) {
        RecDataTime recDataTime;
        if (timestamp == null) {
            recDataTime = this.mMonitor.getRecMapper().getLastRecCheckRcvrTime();
        } else {
            recDataTime = this.mMonitor.getRecMapper().getRecCheckRcvrTimeByTime(timestamp);
        }

        if (recDataTime != null) {
            String strQN = this.getQN(new Date());
            byte[] bSend = this.getRcvrCheckPkg(strQN, "9", recDataTime, factorID);
            if (bSend != null) {
                this.doUpload(bSend, recDataTime, 8);
            }
        }
    }

    public void reSendParCheck(int minOffset) {
        DateTime datetime = (new DateTime()).plusMinutes(minOffset);
        Timestamp timestamp = TimeHelper.jodaToTimestamp(datetime);
        if (this.mUploadCfg.getAuto_upload() != null && this.mUploadCfg.getAuto_upload().getTime() <= timestamp.getTime()) {
            if (this.mUploadCfg.getId() != 0) {
                List<RecDataTime> recDataTimeList = this.mMonitor.getRecMapper().getRecNullSendTimeList("rec_check_par", "send_time" + this.mUploadCfg.getId(), 10);
                if (recDataTimeList != null) {
                    try {
                        Iterator var5 = recDataTimeList.iterator();

                        while(var5.hasNext()) {
                            RecDataTime recDataTime = (RecDataTime)var5.next();
                            if (recDataTime.getRecTime().getTime() >= this.mUploadCfg.getAuto_upload().getTime() && recDataTime.getRecTime().getTime() <= timestamp.getTime()) {
                                this.sendParCheck(0, recDataTime.getRecTime());
                                Thread.sleep(1000L);
                            }
                        }
                    } catch (Exception var7) {
                    }

                }
            }
        }
    }

    public void sendParCheck(int factorID, Timestamp timestamp) {
        RecDataTime recDataTime;
        if (timestamp == null) {
            recDataTime = this.mMonitor.getRecMapper().getLastRecCheckParTime();
        } else {
            recDataTime = this.mMonitor.getRecMapper().getRecCheckParTimeByTime(timestamp);
        }

        if (recDataTime != null) {
            String strQN = this.getQN(new Date());
            byte[] bSend = this.getParCheckPkg(strQN, "9", recDataTime, factorID);
            if (bSend != null) {
                this.doUpload(bSend, recDataTime, 9);
            }
        }
    }

    public void sendPowerAlarm(boolean powerAlarm) {
        if (!this.bBasic) {
            String strQN = this.getQN(new Date());
            byte[] bSend = this.getPowerAlarmPkg(strQN, "9", powerAlarm);
            if (bSend != null) {
                this.doUpload(bSend);
            }
        }
    }

    public void sendSystemAlarm(Timestamp alarmTime, String alarmType) {
        String strQN = this.getQN(new Date());
        byte[] bSend = this.getSystemAlarmPkg(strQN, "9", alarmTime, alarmType);
        if (bSend != null) {
            this.doUpload(bSend);
        }
    }

    public void sendLogSys(Timestamp timestamp) {
        if (!this.bBasic) {
            RecDataTime recDataTime;
            if (timestamp == null) {
                recDataTime = this.mMonitor.getRecMapper().getLastRecLogSysTime();
            } else {
                recDataTime = this.mMonitor.getRecMapper().getRecLogSysTimeByTime(timestamp);
            }

            if (recDataTime != null) {
                String strQN = this.getQN(new Date());
                byte[] bSend = this.getLogSysPkg(strQN, "9", recDataTime);
                if (bSend != null) {
                    this.doUpload(bSend, recDataTime, 10);
                }
            }
        }
    }

    public void sendLogDev(int factorID, RecDataTime recDataTime) {
        if (!this.bBasic) {
            if (recDataTime != null) {
                Iterator var3 = this.mMonitor.getCfgFactorList().iterator();

                while(true) {
                    CfgFactor cfgFactor;
                    do {
                        do {
                            do {
                                if (!var3.hasNext()) {
                                    return;
                                }

                                cfgFactor = (CfgFactor)var3.next();
                            } while(!cfgFactor.isUsed());
                        } while(!cfgFactor.isFiveType() && !cfgFactor.isParmType());
                    } while(cfgFactor.getId() != factorID && factorID != 0);

                    String strQN = this.getQN(new Date());
                    byte[] bSend = this.getLogDevPkg(strQN, "9", recDataTime, cfgFactor.getCodeGJ());
                    if (bSend != null) {
                        this.doUpload(bSend, recDataTime, 11);
                    }
                }
            }
        }
    }

    public boolean sendStateSys() {
        if (this.bBasic) {
            return false;
        } else {
            RecDataTime recDataTime = new RecDataTime();
            recDataTime.setRecTime(TimeHelper.getCurrentTimestamp());
            String strQN = this.getQN(new Date());
            byte[] bSend = this.getStateSysPkg(strQN, "9", recDataTime, (String)null);
            if (bSend == null) {
                return false;
            } else {
                return this.doUpload(bSend);
            }
        }
    }

    public boolean sendStateDev() {
        if (this.bBasic) {
            return false;
        } else {
            RecDataTime recDataTime = new RecDataTime();
            recDataTime.setRecTime(TimeHelper.getCurrentTimestamp());
            Iterator var2 = this.mMonitor.getCfgFactorList().iterator();

            while(true) {
                CfgFactor cfgFactor;
                do {
                    do {
                        if (!var2.hasNext()) {
                            return true;
                        }

                        cfgFactor = (CfgFactor)var2.next();
                    } while(!cfgFactor.isUsed());
                } while(!cfgFactor.isFiveType() && !cfgFactor.isParmType());

                String strQN = this.getQN(new Date());
                byte[] bSend = this.getStateDevPkg(strQN, "9", recDataTime, cfgFactor.getCodeGJ(), (String)null);
                if (bSend != null) {
                    this.doUpload(bSend);
                }
            }
        }
    }

    public boolean sendParamDev(int factorId, Timestamp timestamp, int paramType) {
        if (this.bBasic) {
            return false;
        } else {
            RecDataTime recDataTime = this.mMonitor.getRecMapper().getRecTimeParamByTime(timestamp);
            return recDataTime != null ? this.sendParamDev(factorId, recDataTime, paramType) : false;
        }
    }

    public boolean sendParamDev(int factorId, RecDataTime recDataTime, int paramType) {
        if (this.bBasic) {
            return false;
        } else {
            Iterator var4 = this.mMonitor.getCfgFactorList().iterator();

            while(true) {
                CfgFactor cfgFactor;
                do {
                    do {
                        if (!var4.hasNext()) {
                            return true;
                        }

                        cfgFactor = (CfgFactor)var4.next();
                    } while(!cfgFactor.isUsed());
                } while(factorId != 0 && factorId != cfgFactor.getId());

                String strQN = this.getQN(new Date());
                byte[] bSend = this.getParamDevPkg(strQN, paramType + "", recDataTime, cfgFactor.getCodeGJ(), (String)null);
                if (bSend != null) {
                    this.doUpload(bSend);

                    try {
                        Thread.sleep(500L);
                    } catch (Exception var9) {
                    }
                }
            }
        }
    }

    public void sendFlowData(int factorID, Timestamp timestamp) {
        RecDataTime recTime;
        if (timestamp == null) {
            recTime = this.mMonitor.getRecMapper().getLastRecTimeFlow();
        } else {
            recTime = this.mMonitor.getRecMapper().getRecTimeFlowByTime(timestamp);
        }

        if (recTime != null) {
            String strQN = this.getQN(new Date());
            byte[] bSend = this.getFlowDataPkg(strQN, "9", recTime, factorID);
            if (bSend != null) {
                this.doUpload(bSend, recTime, 400);
            }
        }
    }

    public byte[] getFlowDataPkg(String strQN, String strFlag, RecDataTime recDataTime, int factorID) {
        return null;
    }

    public String getQN(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return df.format(date);
    }

    private boolean isSend(RecDataTime recDataTime) {
        if (recDataTime == null) {
            return false;
        } else if (this.mUploadCfg.getId() == 1 && recDataTime.getSendTime1() != null) {
            return true;
        } else if (this.mUploadCfg.getId() == 2 && recDataTime.getSendTime2() != null) {
            return true;
        } else if (this.mUploadCfg.getId() == 3 && recDataTime.getSendTime3() != null) {
            return true;
        } else if (this.mUploadCfg.getId() == 4 && recDataTime.getSendTime4() != null) {
            return true;
        } else if (this.mUploadCfg.getId() == 5 && recDataTime.getSendTime5() != null) {
            return true;
        } else if (this.mUploadCfg.getId() == 6 && recDataTime.getSendTime6() != null) {
            return true;
        } else if (this.mUploadCfg.getId() == 7 && recDataTime.getSendTime7() != null) {
            return true;
        } else if (this.mUploadCfg.getId() == 8 && recDataTime.getSendTime8() != null) {
            return true;
        } else if (this.mUploadCfg.getId() == 9 && recDataTime.getSendTime9() != null) {
            return true;
        } else {
            return this.mUploadCfg.getId() == 10 && recDataTime.getSendTime10() != null;
        }
    }
    @SuppressWarnings("FallThrough")
    private void setSendTime(RecDataTime recDataTime) {
        switch(this.mUploadCfg.getId()) {
            case 1:
                recDataTime.setSendTime1(TimeHelper.getCurrentTimestamp());
                break;
            case 2:
                recDataTime.setSendTime2(TimeHelper.getCurrentTimestamp());
                break;
            case 3:
                recDataTime.setSendTime3(TimeHelper.getCurrentTimestamp());
                break;
            case 4:
                recDataTime.setSendTime4(TimeHelper.getCurrentTimestamp());
                break;
            case 5:
                recDataTime.setSendTime5(TimeHelper.getCurrentTimestamp());
                break;
            case 6:
                recDataTime.setSendTime6(TimeHelper.getCurrentTimestamp());
                break;
            case 7:
                recDataTime.setSendTime7(TimeHelper.getCurrentTimestamp());
                break;
            case 8:
                recDataTime.setSendTime8(TimeHelper.getCurrentTimestamp());
                break;
            case 9:
                recDataTime.setSendTime9(TimeHelper.getCurrentTimestamp());
                break;
            case 10:
                recDataTime.setSendTime10(TimeHelper.getCurrentTimestamp());
        }

    }
    @SuppressWarnings("FallThrough")
    public boolean doUpload(final byte[] bSend, final RecDataTime recDataTime, final int uploadType) {
        if (bSend == null) {
            return false;
        } else if (this.mInterComm != null && this.mInterComm.isConnect()) {
            this.fixedThreadPool.execute(new Runnable() {
                public void run() {
                    for(int i = 0; i < UploadInter.this.mReCount + 1; ++i) {
                        UploadInter.this.mInterComm.Send(bSend);

                        for(int j = 0; j < UploadInter.this.mOverTime; ++j) {
                            try {
                                Thread.sleep(1000L);
                            } catch (Exception var4) {
                            }

                            if (UploadInter.this.mInterComm.getRespond(bSend) != null) {
                                UploadInter.this.setSendTime(recDataTime);
                                switch(uploadType) {
                                    case 1:
                                        switch(UploadInter.this.mUploadCfg.getId()) {
                                            case 1:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeHour1(recDataTime);
                                                return;
                                            case 2:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeHour2(recDataTime);
                                                return;
                                            case 3:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeHour3(recDataTime);
                                                return;
                                            case 4:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeHour4(recDataTime);
                                                return;
                                            case 5:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeHour5(recDataTime);
                                                return;
                                            case 6:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeHour6(recDataTime);
                                                return;
                                            case 7:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeHour7(recDataTime);
                                                return;
                                            case 8:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeHour8(recDataTime);
                                                return;
                                            case 9:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeHour9(recDataTime);
                                                return;
                                            case 10:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeHour10(recDataTime);
                                                return;
                                            default:
                                                return;
                                        }
                                    case 2:
                                        switch(UploadInter.this.mUploadCfg.getId()) {
                                            case 1:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeMin1(recDataTime);
                                                return;
                                            case 2:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeMin2(recDataTime);
                                                return;
                                            case 3:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeMin3(recDataTime);
                                                return;
                                            case 4:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeMin4(recDataTime);
                                                return;
                                            case 5:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeMin5(recDataTime);
                                                return;
                                            case 6:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeMin6(recDataTime);
                                                return;
                                            case 7:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeMin7(recDataTime);
                                                return;
                                            case 8:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeMin8(recDataTime);
                                                return;
                                            case 9:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeMin9(recDataTime);
                                                return;
                                            case 10:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeMin10(recDataTime);
                                                return;
                                            default:
                                                return;
                                        }
                                    case 3:
                                        RecDataTime recDataTimeHour = UploadInter.this.mMonitor.getRecMapper().getRecTimeHourByTime(recDataTime.getRecTime());
                                        recDataTimeHour.setSendTime1(recDataTime.getSendTime1());
                                        recDataTimeHour.setSendTime2(recDataTime.getSendTime2());
                                        recDataTimeHour.setSendTime3(recDataTime.getSendTime3());
                                        recDataTimeHour.setSendTime4(recDataTime.getSendTime4());
                                        recDataTimeHour.setSendTime5(recDataTime.getSendTime5());
                                        recDataTimeHour.setSendTime6(recDataTime.getSendTime6());
                                        recDataTimeHour.setSendTime7(recDataTime.getSendTime7());
                                        recDataTimeHour.setSendTime8(recDataTime.getSendTime8());
                                        recDataTimeHour.setSendTime9(recDataTime.getSendTime9());
                                        recDataTimeHour.setSendTime10(recDataTime.getSendTime10());
                                        switch(UploadInter.this.mUploadCfg.getId()) {
                                            case 1:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeFive1(recDataTime);
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeHour1(recDataTimeHour);
                                                return;
                                            case 2:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeFive2(recDataTime);
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeHour2(recDataTimeHour);
                                                return;
                                            case 3:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeFive3(recDataTime);
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeHour3(recDataTimeHour);
                                                return;
                                            case 4:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeFive4(recDataTime);
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeHour4(recDataTimeHour);
                                                return;
                                            case 5:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeFive5(recDataTime);
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeHour5(recDataTimeHour);
                                                return;
                                            case 6:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeFive6(recDataTime);
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeHour6(recDataTimeHour);
                                                return;
                                            case 7:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeFive7(recDataTime);
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeHour7(recDataTimeHour);
                                                return;
                                            case 8:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeFive8(recDataTime);
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeHour8(recDataTimeHour);
                                                return;
                                            case 9:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeFive9(recDataTime);
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeHour9(recDataTimeHour);
                                                return;
                                            case 10:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeFive10(recDataTime);
                                                UploadInter.this.mMonitor.getRecMapper().updateRecTimeHour10(recDataTimeHour);
                                                return;
                                            default:
                                                return;
                                        }
                                    case 4:
                                        switch(UploadInter.this.mUploadCfg.getId()) {
                                            case 1:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckFiveTime1(recDataTime);
                                                return;
                                            case 2:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckFiveTime2(recDataTime);
                                                return;
                                            case 3:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckFiveTime3(recDataTime);
                                                return;
                                            case 4:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckFiveTime4(recDataTime);
                                                return;
                                            case 5:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckFiveTime5(recDataTime);
                                                return;
                                            case 6:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckFiveTime6(recDataTime);
                                                return;
                                            case 7:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckFiveTime7(recDataTime);
                                                return;
                                            case 8:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckFiveTime8(recDataTime);
                                                return;
                                            case 9:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckFiveTime9(recDataTime);
                                                return;
                                            case 10:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckFiveTime10(recDataTime);
                                                return;
                                            default:
                                                return;
                                        }
                                    case 5:
                                        switch(UploadInter.this.mUploadCfg.getId()) {
                                            case 1:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckStdTime1(recDataTime);
                                                return;
                                            case 2:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckStdTime2(recDataTime);
                                                return;
                                            case 3:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckStdTime3(recDataTime);
                                                return;
                                            case 4:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckStdTime4(recDataTime);
                                                return;
                                            case 5:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckStdTime5(recDataTime);
                                                return;
                                            case 6:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckStdTime6(recDataTime);
                                                return;
                                            case 7:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckStdTime7(recDataTime);
                                                return;
                                            case 8:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckStdTime8(recDataTime);
                                                return;
                                            case 9:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckStdTime9(recDataTime);
                                                return;
                                            case 10:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckStdTime10(recDataTime);
                                                return;
                                            default:
                                                return;
                                        }
                                    case 6:
                                        switch(UploadInter.this.mUploadCfg.getId()) {
                                            case 1:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckZeroTime1(recDataTime);
                                                return;
                                            case 2:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckZeroTime2(recDataTime);
                                                return;
                                            case 3:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckZeroTime3(recDataTime);
                                                return;
                                            case 4:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckZeroTime4(recDataTime);
                                                return;
                                            case 5:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckZeroTime5(recDataTime);
                                                return;
                                            case 6:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckZeroTime6(recDataTime);
                                                return;
                                            case 7:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckZeroTime7(recDataTime);
                                                return;
                                            case 8:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckZeroTime8(recDataTime);
                                                return;
                                            case 9:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckZeroTime9(recDataTime);
                                                return;
                                            case 10:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckZeroTime10(recDataTime);
                                                return;
                                            default:
                                                return;
                                        }
                                    case 7:
                                        switch(UploadInter.this.mUploadCfg.getId()) {
                                            case 1:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckSpanTime1(recDataTime);
                                                return;
                                            case 2:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckSpanTime2(recDataTime);
                                                return;
                                            case 3:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckSpanTime3(recDataTime);
                                                return;
                                            case 4:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckSpanTime4(recDataTime);
                                                return;
                                            case 5:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckSpanTime5(recDataTime);
                                                return;
                                            case 6:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckSpanTime6(recDataTime);
                                                return;
                                            case 7:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckSpanTime7(recDataTime);
                                                return;
                                            case 8:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckSpanTime8(recDataTime);
                                                return;
                                            case 9:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckSpanTime9(recDataTime);
                                                return;
                                            case 10:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckSpanTime10(recDataTime);
                                                return;
                                            default:
                                                return;
                                        }
                                    case 8:
                                        switch(UploadInter.this.mUploadCfg.getId()) {
                                            case 1:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckRcvrTime1(recDataTime);
                                                return;
                                            case 2:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckRcvrTime2(recDataTime);
                                                return;
                                            case 3:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckRcvrTime3(recDataTime);
                                                return;
                                            case 4:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckRcvrTime4(recDataTime);
                                                return;
                                            case 5:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckRcvrTime5(recDataTime);
                                                return;
                                            case 6:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckRcvrTime6(recDataTime);
                                                return;
                                            case 7:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckRcvrTime7(recDataTime);
                                                return;
                                            case 8:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckRcvrTime8(recDataTime);
                                                return;
                                            case 9:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckRcvrTime9(recDataTime);
                                                return;
                                            case 10:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckRcvrTime10(recDataTime);
                                                return;
                                            default:
                                                return;
                                        }
                                    case 9:
                                        switch(UploadInter.this.mUploadCfg.getId()) {
                                            case 1:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckParTime1(recDataTime);
                                                return;
                                            case 2:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckParTime2(recDataTime);
                                                return;
                                            case 3:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckParTime3(recDataTime);
                                                return;
                                            case 4:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckParTime4(recDataTime);
                                                return;
                                            case 5:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckParTime5(recDataTime);
                                                return;
                                            case 6:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckParTime6(recDataTime);
                                                return;
                                            case 7:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckParTime7(recDataTime);
                                                return;
                                            case 8:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckParTime8(recDataTime);
                                                return;
                                            case 9:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckParTime9(recDataTime);
                                                return;
                                            case 10:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecCheckParTime10(recDataTime);
                                                return;
                                            default:
                                                return;
                                        }
                                    case 10:
                                        switch(UploadInter.this.mUploadCfg.getId()) {
                                            case 1:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecLogSysTime1(recDataTime);
                                                return;
                                            case 2:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecLogSysTime2(recDataTime);
                                                return;
                                            case 3:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecLogSysTime3(recDataTime);
                                                return;
                                            case 4:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecLogSysTime4(recDataTime);
                                                return;
                                            case 5:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecLogSysTime5(recDataTime);
                                                return;
                                            case 6:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecLogSysTime6(recDataTime);
                                                return;
                                            case 7:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecLogSysTime7(recDataTime);
                                                return;
                                            case 8:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecLogSysTime8(recDataTime);
                                                return;
                                            case 9:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecLogSysTime9(recDataTime);
                                                return;
                                            case 10:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecLogSysTime10(recDataTime);
                                                return;
                                            default:
                                                return;
                                        }
                                    case 11:
                                        switch(UploadInter.this.mUploadCfg.getId()) {
                                            case 1:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecLogDevTime1(recDataTime);
                                                break;
                                            case 2:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecLogDevTime2(recDataTime);
                                                break;
                                            case 3:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecLogDevTime3(recDataTime);
                                                break;
                                            case 4:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecLogDevTime4(recDataTime);
                                                break;
                                            case 5:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecLogDevTime5(recDataTime);
                                                break;
                                            case 6:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecLogDevTime6(recDataTime);
                                                break;
                                            case 7:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecLogDevTime7(recDataTime);
                                                break;
                                            case 8:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecLogDevTime8(recDataTime);
                                                break;
                                            case 9:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecLogDevTime9(recDataTime);
                                                break;
                                            case 10:
                                                UploadInter.this.mMonitor.getRecMapper().updateRecLogDevTime10(recDataTime);
                                        }
                                }

                                return;
                            }
                        }
                    }

                }
            });
            return true;
        } else {
            return false;
        }
    }

    private boolean doUpload(final byte[] bSend) {
        if (bSend == null) {
            return false;
        } else if (this.mInterComm != null && this.mInterComm.isConnect()) {
            this.fixedThreadPool.execute(new Runnable() {
                public void run() {
                    for(int i = 0; i < UploadInter.this.mReCount + 1; ++i) {
                        UploadInter.this.mInterComm.Send(bSend);

                        for(int j = 0; j < UploadInter.this.mOverTime; ++j) {
                            try {
                                Thread.sleep(1000L);
                            } catch (Exception var4) {
                            }

                            if (UploadInter.this.mInterComm.getRespond(bSend) != null) {
                                return;
                            }
                        }
                    }

                }
            });
            return true;
        } else {
            return false;
        }
    }

    public String getUploadName() {
        return this.uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }

    public String getKeyString() {
        return this.keyString;
    }

    public void setKeyString(String keyString) {
        this.keyString = keyString;
    }

    private class UploadThread extends Thread {
        boolean bFirst;

        private UploadThread() {
            this.bFirst = true;
        }

        public void run() {
            super.run();

            try {
                Thread.sleep(1000L);
            } catch (Exception var8) {
            }

            while(!this.isInterrupted() && !MonitorService.restart) {
                try {
                    if (UploadInter.this.mInterComm != null && UploadInter.this.mInterComm.isConnect()) {
                        String strQN;
                        byte[] bSend;
                        if (this.bFirst) {
                            strQN = UploadInter.this.getQN(new Date());
                            bSend = UploadInter.this.getBootTimePkg(strQN, "9");
                            if (bSend != null) {
                                UploadInter.this.doUpload(bSend);
                            }

                            this.bFirst = false;
                        }

                        if (UploadInter.this.mPeriodHeart > 0 && UploadInter.this.mPeriods % UploadInter.this.mPeriodHeart == 0) {
                            try {
                                Thread.sleep(20L);
                                strQN = UploadInter.this.getQN(new Date());
                                bSend = UploadInter.this.getHeartBeatPkg(strQN, "9");
                                if (bSend != null) {
                                    UploadInter.this.doUpload(bSend);
                                }
                            } catch (Exception var7) {
                            }
                        }

                        if (UploadInter.this.mPeriodStatSys > 0 && UploadInter.this.mPeriods % UploadInter.this.mPeriodStatSys == 0) {
                            try {
                                Thread.sleep(20L);
                                UploadInter.this.sendStateSys();
                            } catch (Exception var6) {
                            }
                        }

                        if (UploadInter.this.mPeriodStatDev > 0 && UploadInter.this.mPeriods % UploadInter.this.mPeriodStatDev == 0) {
                            try {
                                Thread.sleep(20L);
                                UploadInter.this.sendStateDev();
                            } catch (Exception var5) {
                            }
                        }

                        if (UploadInter.this.mPeriodReal > 0 && UploadInter.this.mPeriods % UploadInter.this.mPeriodReal == 0) {
                            try {
                                Thread.sleep(20L);
                                UploadInter.this.sendRealTime();
                            } catch (Exception var4) {
                            }
                        }

                        if (UploadInter.this.mPeriods % 300 == 0) {
                            try {
                                UploadInter.this.reSendHourData(-150);
                                UploadInter.this.reSendZeroCheck(-150);
                                UploadInter.this.reSendSpanCheck(-150);
                                UploadInter.this.reSendStdCheck(-150);
                                UploadInter.this.reSendRcvrCheck(-150);
                            } catch (Exception var3) {
                                System.out.println(var3.getMessage());
                            }
                        }
                    }

                    ++UploadInter.this.mPeriods;
                    Thread.sleep(1000L);
                } catch (Exception var9) {
                    break;
                }
            }

        }
    }
}
