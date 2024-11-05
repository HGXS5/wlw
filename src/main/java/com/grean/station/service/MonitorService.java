//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service;

import com.google.common.base.Strings;
import com.grean.station.comm.CommFtp;
import com.grean.station.comm.CommFtpQD;
import com.grean.station.comm.CommFtpXA;
import com.grean.station.comm.CommInter;
import com.grean.station.comm.CommSerial;
import com.grean.station.comm.CommUcma;
import com.grean.station.comm.CommSerial.Builder;
import com.grean.station.comm.netclientline.CommNetClientLine;
import com.grean.station.comm.netclientmodbus.CommNetClientModbus;
import com.grean.station.comm.netclientmodbustcp.CommNetClientModbusTcp;
import com.grean.station.comm.netclientserial.CommNetClientSerial;
import com.grean.station.comm.netclientszy206.CommNetClientSzy206;
import com.grean.station.comm.netserver.NetServer;
import com.grean.station.comm.udpclientline.UdpNetClientLine;
import com.grean.station.dao.CfgMapper;
import com.grean.station.dao.PLCMapper;
import com.grean.station.dao.RecMapper;
import com.grean.station.domain.DO.cfg.CfgComm206;
import com.grean.station.domain.DO.cfg.CfgDev;
import com.grean.station.domain.DO.cfg.CfgDevAlarm;
import com.grean.station.domain.DO.cfg.CfgDevCmd;
import com.grean.station.domain.DO.cfg.CfgDevFault;
import com.grean.station.domain.DO.cfg.CfgDevLog;
import com.grean.station.domain.DO.cfg.CfgDevParam;
import com.grean.station.domain.DO.cfg.CfgDevQuery;
import com.grean.station.domain.DO.cfg.CfgDevReg;
import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.cfg.CfgFive;
import com.grean.station.domain.DO.cfg.CfgFlow;
import com.grean.station.domain.DO.cfg.CfgLogGroup;
import com.grean.station.domain.DO.cfg.CfgName;
import com.grean.station.domain.DO.cfg.CfgNetClient;
import com.grean.station.domain.DO.cfg.CfgSample;
import com.grean.station.domain.DO.cfg.CfgScheduleDay;
import com.grean.station.domain.DO.cfg.CfgScheduleMonth;
import com.grean.station.domain.DO.cfg.CfgScheduleQuality;
import com.grean.station.domain.DO.cfg.CfgScheduleSample;
import com.grean.station.domain.DO.cfg.CfgScheduleWeek;
import com.grean.station.domain.DO.cfg.CfgScript;
import com.grean.station.domain.DO.cfg.CfgSerial;
import com.grean.station.domain.DO.cfg.CfgStation;
import com.grean.station.domain.DO.cfg.CfgUploadCom;
import com.grean.station.domain.DO.cfg.CfgUploadNet;
import com.grean.station.domain.DO.cfg.CfgUser;
import com.grean.station.domain.DO.plc.DefWord;
import com.grean.station.domain.DO.plc.VarWord;
import com.grean.station.domain.DO.rec.RecAlarmData;
import com.grean.station.domain.DO.rec.RecAlarmDev;
import com.grean.station.domain.DO.rec.RecAlarmSys;
import com.grean.station.domain.DO.rec.RecCheckBlank;
import com.grean.station.domain.DO.rec.RecCheckFive;
import com.grean.station.domain.DO.rec.RecCheckPar;
import com.grean.station.domain.DO.rec.RecCheckRcvr;
import com.grean.station.domain.DO.rec.RecCheckStd;
import com.grean.station.domain.DO.rec.RecCheckZero;
import com.grean.station.domain.DO.rec.RecDataAvg;
import com.grean.station.domain.DO.rec.RecDataFactor;
import com.grean.station.domain.DO.rec.RecDataTime;
import com.grean.station.domain.DO.rec.RecDevParam;
import com.grean.station.domain.DO.rec.RecFaultDev;
import com.grean.station.domain.DO.rec.RecFaultSys;
import com.grean.station.domain.DO.rec.RecInfo;
import com.grean.station.domain.DO.rec.RecInfoDoor;
import com.grean.station.domain.DO.rec.RecLogDev;
import com.grean.station.domain.DO.rec.RecLogSample;
import com.grean.station.domain.DO.rec.RecLogSys;
import com.grean.station.domain.DO.rec.RecLogUser;
import com.grean.station.domain.DO.rec.RecSample;
import com.grean.station.domain.request.DateRange;
import com.grean.station.domain.request.ReqFiveCheck;
import com.grean.station.domain.request.ReqFiveInfo;
import com.grean.station.domain.request.ReqStdCheck;
import com.grean.station.domain.request.ResetTimer;
import com.grean.station.domain.request.RtdCount;
import com.grean.station.domain.request.ScheduleFive;
import com.grean.station.http.model.ThConfig;
import com.grean.station.netty.WebSocketSender;
import com.grean.station.service.dev.Adam2400;
import com.grean.station.service.dev.AdamDcon;
import com.grean.station.service.dev.AdamModbus;
import com.grean.station.service.dev.DevDctF12;
import com.grean.station.service.dev.DevLinghu;
import com.grean.station.service.dev.DevModbus;
import com.grean.station.service.dev.DevModbusYiwen;
import com.grean.station.service.dev.DevModbusYuxing;
import com.grean.station.service.dev.DevPlatform;
import com.grean.station.service.dev.DevService;
import com.grean.station.service.dev.DevSystea;
import com.grean.station.service.dev.DevSysteaNc;
import com.grean.station.service.dev.DevYangjian212;
import com.grean.station.service.dev.DevZetian212;
import com.grean.station.service.dev.UserModbus;
import com.grean.station.service.dev.UserModbusTcp;
import com.grean.station.service.dev.centrifuge.CentrifugeAcs;
import com.grean.station.service.dev.centrifuge.CentrifugeGrean;
import com.grean.station.service.dev.centrifuge.CentrifugeService;
import com.grean.station.service.dev.chlorophyll.DevChlorophyllBbe;
import com.grean.station.service.dev.display.DisplayLxy;
import com.grean.station.service.dev.display.DisplayNova;
import com.grean.station.service.dev.display.DisplayService;
import com.grean.station.service.dev.env.EnvDoor;
import com.grean.station.service.dev.env.EnvPowerSantak;
import com.grean.station.service.dev.env.EnvPowerT645;
import com.grean.station.service.dev.env.EnvPowerTk;
import com.grean.station.service.dev.env.EnvPowerWeiqi;
import com.grean.station.service.dev.five.DevFiveYsi2;
import com.grean.station.service.dev.flow.DevFlowHadcp;
import com.grean.station.service.dev.flow.DevFlowHz;
import com.grean.station.service.dev.flow.DevFlowLinkQuest;
import com.grean.station.service.dev.flow.DevFlowLinkQuestShare;
import com.grean.station.service.dev.flow.DevFlowLs;
import com.grean.station.service.dev.flow.DevFlowLsShare;
import com.grean.station.service.dev.flow.DevFlowOtt;
import com.grean.station.service.dev.flow.DevFlowTh;
import com.grean.station.service.dev.flow.FlowService;
import com.grean.station.service.dev.gps.DevGpsDK699D;
import com.grean.station.service.dev.gps.DevGpsGN150;
import com.grean.station.service.dev.gps.DevGpsHS6601;
import com.grean.station.service.dev.met.DevMetAirMarNMEA0183;
import com.grean.station.service.dev.met.DevMetVaisalaWXT536;
import com.grean.station.service.dev.nh3n.DevNh3nWtw;
import com.grean.station.service.dev.quality.QualityModbus;
import com.grean.station.service.dev.quality.QualityModbus1;
import com.grean.station.service.dev.quality.QualityModbus3;
import com.grean.station.service.dev.quality.QualityModbus4;
import com.grean.station.service.dev.quality.QualityModbusAll;
import com.grean.station.service.dev.quality.QualityShangyang;
import com.grean.station.service.dev.sample.SampleDR803;
import com.grean.station.service.dev.sample.SampleDR803B;
import com.grean.station.service.dev.sample.SampleDR803L;
import com.grean.station.service.dev.sample.SampleGrasp;
import com.grean.station.service.dev.sample.SampleGrasp2;
import com.grean.station.service.dev.sample.SampleGrasp9320;
import com.grean.station.service.dev.sample.SampleGrean;
import com.grean.station.service.dev.sample.SampleGrean9320;
import com.grean.station.service.dev.sample.SampleGrean9330;
import com.grean.station.service.dev.sample.SampleGrean9331;
import com.grean.station.service.dev.sample.SampleHengda;
import com.grean.station.service.dev.sample.SampleHengdaZSCVIIE;
import com.grean.station.service.dev.sample.SampleHengdaZSCXIB;
import com.grean.station.service.dev.sample.SampleKeshengSB6000;
import com.grean.station.service.dev.sample.SampleKeze;
import com.grean.station.service.dev.sample.SamplePlcSwitch;
import com.grean.station.service.dev.sample.SampleService;
import com.grean.station.service.dev.sample.SampleWanweiWQS2000;
import com.grean.station.service.dev.sample.SampleXqls9320;
import com.grean.station.service.dev.sample.SampleXqls9330;
import com.grean.station.service.dev.sample.SampleXqlsXW1B2;
import com.grean.station.service.dev.sms.SmsFourFaith;
import com.grean.station.service.dev.sms.SmsService;
import com.grean.station.service.dev.tox.DevToxBbe;
import com.grean.station.service.dev.voc.DevVocModernWater;
import com.grean.station.service.query.RealTimeService;
import com.grean.station.service.script.ScriptCountListener;
import com.grean.station.service.script.ScriptInfo;
import com.grean.station.service.script.ScriptService;
import com.grean.station.service.script.ScriptStateListener;
import com.grean.station.service.script.ScriptWord;
import com.grean.station.upload.Upload212;
import com.grean.station.upload.Upload212HOUR;
import com.grean.station.upload.Upload212_2017;
import com.grean.station.upload.Upload212_BZ;
import com.grean.station.upload.Upload212_CQ;
import com.grean.station.upload.Upload212_Encryption;
import com.grean.station.upload.Upload212_GD;
import com.grean.station.upload.Upload212_HF;
import com.grean.station.upload.Upload212_JG;
import com.grean.station.upload.Upload212_KH;
import com.grean.station.upload.Upload212_NSBD;
import com.grean.station.upload.Upload212_QD;
import com.grean.station.upload.Upload212_QDSX;
import com.grean.station.upload.Upload212_SX;
import com.grean.station.upload.Upload212_SX1;
import com.grean.station.upload.Upload212_TEST;
import com.grean.station.upload.Upload212_UDP;
import com.grean.station.upload.Upload212_WH;
import com.grean.station.upload.Upload212_YC;
import com.grean.station.upload.Upload212_YCDF;
import com.grean.station.upload.Upload212_ZJ;
import com.grean.station.upload.UploadDJK;
import com.grean.station.upload.UploadFtp_JS;
import com.grean.station.upload.UploadFtp_QD;
import com.grean.station.upload.UploadFtp_XA;
import com.grean.station.upload.UploadHeDa;
import com.grean.station.upload.UploadInter;
import com.grean.station.upload.UploadModbus;
import com.grean.station.upload.UploadModbusServer;
import com.grean.station.upload.UploadSL651;
import com.grean.station.upload.UploadSzy206;
import com.grean.station.upload.UploadSzy206_FJ;
import com.grean.station.upload.UploadSzy206_HB;
import com.grean.station.upload.UploadSzy206_NSBD;
import com.grean.station.upload.UploadSzy206_SH;
import com.grean.station.upload.UploadZJ_Grean;
import com.grean.station.upload.UploadZJ_Pollution;
import com.grean.station.upload.UploadZJ_Water;
import com.grean.station.upload.UploadZJ_WaterResource;
import com.grean.station.upload.UploadZJ_WaterResourceNoFive;
import com.grean.station.utils.SmsUtil;
import com.grean.station.utils.TimeHelper;
import com.grean.station.utils.Utility;
import com.sun.glass.ui.Application;

import java.io.File;
import java.lang.reflect.Constructor;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class MonitorService implements CommandLineRunner {
    @Autowired
    ScriptService scriptService;
    @Autowired
    RealTimeService realTimeService;
    @Autowired
    DbService dbService;
    @Autowired
    CfgMapper cfgMapper;
    @Autowired
    RecMapper recMapper;
    @Autowired
    PLCMapper plcMapper;
    @Autowired
    WebSocketSender webSocketSender;
    @Autowired
    NetServer netServer;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Value("${save_flag:true}")
    private Boolean save_flag;
    @Value("${cal_time:true}")
    private Boolean cal_time;
    @Value("${save_zero:false}")
    private Boolean save_zero;
    @Value("${time_check:true}")
    private Boolean time_check;
    @Value("${save_minute}")
    private Boolean save_minute;
    @Value("${save_average}")
    private Boolean save_average;
    @Value("${voc_path}")
    private String voc_path;
    @Value("${voc_file}")
    private String voc_file;
    @Value("${file_path}")
    private String file_path;
    @Value("${ftp_path}")
    private String ftp_path;
    @Value("${ftp_user}")
    private String ftp_user;
    @Value("${ftp_password}")
    private String ftp_password;
    @Value("${ftp_area_code:}")
    private String ftp_area_code;
    @Value("${ftp_station_code:}")
    private String ftp_station_code;
    @Value("${dev_cmd_time}")
    private int dev_cmd_time;
    @Value("${http_username}")
    private String http_username;
    @Value("${http_password}")
    private String http_password;
    @Value("${http_placeid}")
    private String http_placeid;
    @Value("${http_equipid}")
    private String http_equipid;
    @Value("${sms_phone:}")
    private String sms_phone;
    @Value("${sms_input:}")
    private String sms_input;
    @Value("${gas_input:}")
    private String gas_input;
    @Value("${water_input:}")
    private String water_input;
    @Value("${dtu_port:0}")
    private Integer dtu_port;
    @Value("${dtu_protocol:}")
    private String dtu_protocol;
    @Value("${dtu_mn:}")
    private String dtu_mn;
    @Value("${dtu_upload:}")
    private String dtu_upload;
    @Value("${dtu_data:}")
    private String dtu_data;
    @Value("${dtu_heart:false}")
    private Boolean dtu_heart;
    @Value("${plc_write:false}")
    private Boolean plc_write;
    @Value("${plc_write_type:REALTIME}")
    private String plc_write_type;
    @Value("${flow_share:}")
    private String flow_share;
    @Value("${wtw_cal_time:}")
    private String wtw_cal_time;
    @Value("${platform_Url:}")
    private String platformUrl;
    @Value("${platform_Stcd:}")
    private String platformStcd;
    @Value("${platform_Token:}")
    private String platformToken;
    @Value("${linghu_Url:}")
    private String linghuUrl;
    @Value("${linghu_Stcd:}")
    private String linghuStcd;
    @Value("${linghu_Token:}")
    private String linghuToken;
    @Value("${auto_reboot:false}")
    private Boolean auto_reboot;
    @Value("${upload_type:0}")
    private Integer upload_type;
    @Value("${key_string:Grean@1234567890}")
    private String key_string;
    @Value("${avg_time:0}")
    private Integer avg_time;
    @Value("${start_dev:}")
    private String start_dev;
    @Value("${start_time:}")
    private String start_time;
    @Value("${acid_base_switch:false}")
    private Boolean acid_base_switch;
    @Value("${acid_base_cond:100}")
    private Integer acid_base_cond;
    private boolean sms_warn = false;
    private Timer timerSave;
    private TimerTask timerSaveTask;
    private ResetTimer resetTimer;
    private boolean rcvrOver;
    public List<CfgSerial> cfgSerialList = new ArrayList();
    public List<CfgDev> cfgDevList = new ArrayList();
    public List<CfgDevQuery> cfgDevQueryList = new ArrayList();
    public List<CfgDevCmd> cfgDevCmdList = new ArrayList();
    public List<CfgDevLog> cfgDevLogList = new ArrayList();
    public List<CfgDevAlarm> cfgDevAlarmList = new ArrayList();
    public List<CfgDevFault> cfgDevFaultList = new ArrayList();
    public List<CfgLogGroup> cfgLogGroupList = new ArrayList();
    public List<CfgDevParam> cfgDevParamList = new ArrayList();
    public List<CfgName> cfgNameList = new ArrayList();
    public List<CfgFactor> cfgFactorList = new ArrayList();
    public List<CfgNetClient> cfgNetClientList = new ArrayList();
    public List<CfgUploadNet> cfgUploadNetList = new ArrayList();
    public List<CfgUploadCom> cfgUploadComList = new ArrayList();
    public List<CfgScheduleDay> cfgScheduleDayList = new ArrayList();
    public List<CfgScheduleWeek> cfgScheduleWeekList = new ArrayList();
    public List<CfgScheduleMonth> cfgScheduleMonthList = new ArrayList();
    public List<CfgScript> cfgScriptList = new ArrayList();
    public List<CfgScheduleSample> cfgScheduleSampleList;
    public List<CfgScheduleQuality> cfgScheduleQualityList;
    public CfgSample cfgSample;
    public CfgFlow cfgFlow;
    public List<CfgDevReg> cfgRegModbusList = new ArrayList();
    public List<CfgDevReg> cfgRegFiveList = new ArrayList();
    public List<CfgDevReg> cfgRegQualityList = new ArrayList();
    public List<CfgDevReg> cfgRegQualityList3 = new ArrayList();
    public List<CfgDevReg> cfgRegQualityList4 = new ArrayList();
    public List<CfgDevReg> cfgRegFlowList = new ArrayList();
    public List<CfgDevReg> cfgRegYiwenList = new ArrayList();
    public List<CfgDevReg> cfgRegYuxingList = new ArrayList();
    public List<CfgComm206> cfgComm206List = new ArrayList();
    public List<DevService> devServiceList = new ArrayList();
    public List<CommInter> commInterList = new ArrayList();
    public List<CommInter> commUploadList = new ArrayList();
    public List<UploadInter> uploadInterList = new ArrayList();
    public List<Thread> serialQueryList = new ArrayList();
    public SampleService sampleService;
    public List<SampleService> sampleServiceList = new ArrayList();
    public DisplayService displayService;
    public CentrifugeService centrifugeService;
    public EnvDoor doorService;
    public SmsService smsService;
    public ScriptStateListener scriptStateListener;
    public ScriptCountListener scriptCountListener;
    private Timestamp sysRunTime;
    private Timestamp scriptFiveRunTime;
    private ScriptInfo scriptInfo = new ScriptInfo();
    private boolean refreshFactorData = true;
    private boolean userStart;
    public static boolean restart = false;
    public static boolean autoData = false;
    public static boolean autoPlc = false;
    public int waitDev = 0;
    private RtdCount rtdCount = new RtdCount();
    private String sysStatus = "系统待机";
    private ReqFiveInfo checkFiveInfo;
    private int urgentCount = 0;
    private EnvPowerSantak envPowerSantak;
    private boolean powerAlarm;
    private boolean snStatus;
    private String lastState;
    private boolean checkMin;
    private boolean historyDataFlag = false;
    private boolean checkZeroDataFlag = false;
    private boolean checkSpanDataFlag = false;
    private boolean checkBlankDataFlag = false;
    private boolean checkStdDataFlag = false;
    private boolean checkRcvrDataFlag = false;
    private boolean checkParDataFlag = false;
    private boolean[] IStatus = new boolean[128];
    public ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
    int wtwHour;
    int wtwMin;
    DevService wtwService;

    public MonitorService() {
    }

    public ScriptService getScriptService() {
        return this.scriptService;
    }

    public ReqFiveInfo getCheckFiveInfo() {
        return this.checkFiveInfo;
    }

    public void setCheckFiveInfo(ReqFiveInfo checkFiveInfo) {
        this.checkFiveInfo = checkFiveInfo;
    }

    public ResetTimer getResetTimer() {
        return this.resetTimer;
    }

    public boolean isHistoryDataFlag() {
        return this.historyDataFlag;
    }

    public void setHistoryDataFlag(boolean historyDataFlag) {
        this.historyDataFlag = historyDataFlag;
    }

    public boolean isCheckZeroDataFlag() {
        return this.checkZeroDataFlag;
    }

    public void setCheckZeroDataFlag(boolean checkZeroDataFlag) {
        this.checkZeroDataFlag = checkZeroDataFlag;
    }

    public boolean isCheckSpanDataFlag() {
        return this.checkSpanDataFlag;
    }

    public void setCheckSpanDataFlag(boolean checkSpanDataFlag) {
        this.checkSpanDataFlag = checkSpanDataFlag;
    }

    public boolean isCheckBlankDataFlag() {
        return this.checkBlankDataFlag;
    }

    public void setCheckBlankDataFlag(boolean checkBlankDataFlag) {
        this.checkBlankDataFlag = checkBlankDataFlag;
    }

    public boolean isCheckStdDataFlag() {
        return this.checkStdDataFlag;
    }

    public void setCheckStdDataFlag(boolean checkStdDataFlag) {
        this.checkStdDataFlag = checkStdDataFlag;
    }

    public boolean isCheckRcvrDataFlag() {
        return this.checkRcvrDataFlag;
    }

    public void setCheckRcvrDataFlag(boolean checkRcvrDataFlag) {
        this.checkRcvrDataFlag = checkRcvrDataFlag;
    }

    public boolean isCheckParDataFlag() {
        return this.checkParDataFlag;
    }

    public void setCheckParDataFlag(boolean checkParDataFlag) {
        this.checkParDataFlag = checkParDataFlag;
    }

    public List<CfgUploadNet> getCfgUploadNetList() {
        return this.cfgUploadNetList;
    }

    public void setCfgScheduleQualityList(List<CfgScheduleQuality> cfgScheduleQualityList) {
        this.cfgScheduleQualityList = cfgScheduleQualityList;
    }

    public void run(String... strings) throws Exception {
        List<String> argumentList = Arrays.asList(strings);
        Iterator var3 = argumentList.iterator();

        while (var3.hasNext()) {
            String argument = (String) var3.next();
            String var5 = argument.toLowerCase();
            byte var6 = -1;
            switch (var5.hashCode()) {
                case -646299176:
                    if (var5.equals("autoplc")) {
                        var6 = 1;
                    }
                    break;
                case 1439194585:
                    if (var5.equals("autodata")) {
                        var6 = 0;
                    }
            }

            switch (var6) {
                case 0:
                    autoData = true;
                    break;
                case 1:
                    autoPlc = true;
            }
        }

        Utility.vocPathName = this.voc_path;
        Utility.vocFileName = this.voc_file;
        if (this.dtu_upload.length() > 0) {
            Utility.dtuUpload = TimeHelper.parseDevTime(this.dtu_upload);
        } else {
            Utility.dtuUpload = TimeHelper.getCurrentTimestamp();
        }

        if (this.wtw_cal_time.length() > 0) {
            Utility.wtwCalTime = TimeHelper.parseTimeHHmm(this.wtw_cal_time);
            DateTime dateTime = new DateTime(Utility.wtwCalTime.getTime());
            this.wtwHour = dateTime.getHourOfDay();
            this.wtwMin = dateTime.getMinuteOfHour();
        } else {
            Utility.wtwCalTime = null;
        }

        if (this.start_dev.length() > 0) {
            Utility.startTime = new DateTime(TimeHelper.parseTimeHHmmss(this.start_time));
        } else {
            Utility.startTime = null;
        }

        System.out.println("===== Monitor Service Started=====" + Arrays.asList(strings));
        this.saveRecLogSys("系统日志", "启动系统");
        this.sysRunTime = TimeHelper.getCurrentTimestamp();
        this.resetTimer = new ResetTimer(180);
        this.initPlcStateListener();
        this.initPlcCountListener();
        this.initCfg();
        this.initFiveCheck();
        this.initComDev();
        this.initNetDev();
        this.initFileDev();
        this.initUpload();
        this.writePlcHistory();
        ScriptWord.getInstance().init(this.plcMapper);
        this.snStatus = this.checkSN();
        this.snStatus = true;
        if (this.avg_time > 0) {
            this.fixedThreadPool.execute(new Runnable() {
                public void run() {
                    DateTime curTime = (new DateTime()).minusDays(1);

                    for (DateTime dateTime = new DateTime(MonitorService.this.avg_time, 1, 1, 0, 0, 0); dateTime.getMillis() < curTime.getMillis(); dateTime = dateTime.plusDays(1)) {
                        MonitorService.this.saveRecDataDay(dateTime);
                        if (dateTime.getDayOfMonth() == 1) {
                            MonitorService.this.saveRecDataMonth(dateTime.minusMonths(1));
                        }
                    }

                }
            });
        }

        this.netServer.doStart();
    }

    private boolean checkSN() {
        try {
            File snFile = ResourceUtils.getFile("classpath:scripts/sn.txt");
            List<String> inputList = FileUtils.readLines(snFile, "UTF-8");
            if (inputList.size() >= 3 && Utility.checkSN((String) inputList.get(0), (String) inputList.get(1), (String) inputList.get(2))) {
                return true;
            }
        } catch (Exception var3) {
        }

        return false;
    }

    @Scheduled(
            fixedRate = 1000L,
            initialDelay = 10000L
    )
    public void runTaskSchedule() {
        if (!restart && this.snStatus) {
            DateTime curTime = new DateTime();
            Timestamp timestamp = this.getFormatCurrentTime();
            int curHour = curTime.getHourOfDay();
            int curMin = curTime.getMinuteOfHour();
            int curSec = curTime.getSecondOfMinute();
            boolean timeCheck = false;
            if (curMin == 0 && curSec == 0) {
                CfgStation cfgStation = this.cfgMapper.getCfgStation();
                if (cfgStation.getRun_date() == null || cfgStation.getRun_date().getTime() < timestamp.getTime()) {
                    timeCheck = true;
                    cfgStation.setRun_date(timestamp);
                    this.cfgMapper.updateCfgStation(cfgStation);
                }
            }

            try {
                Iterator var8;
                if (curSec < 10 && !this.checkMin) {
                    this.checkMin = true;
                    int iRunMode = (int) ScriptWord.getInstance().getSysVar("RunMode");
                    label211:
                    switch (iRunMode) {
                        case 0:
                            this.urgentCount = 0;
                            break;
                        case 1:
                        case 3:
                            this.urgentCount = 0;
                            var8 = this.cfgScheduleDayList.iterator();

                            while (true) {
                                while (true) {
                                    while (true) {
                                        while (true) {
                                            CfgScheduleDay cfgScheduleDay;
                                            do {
                                                do {
                                                    if (!var8.hasNext()) {
                                                        break label211;
                                                    }

                                                    cfgScheduleDay = (CfgScheduleDay) var8.next();
                                                } while (cfgScheduleDay.getHour() != curHour);
                                            } while (cfgScheduleDay.getMin() != curMin);

                                            if (cfgScheduleDay.getTask_code() != null && cfgScheduleDay.getTask_code() > 0) {
                                                if (this.scriptService.isOnScript()) {
                                                    if (cfgScheduleDay.getTask_code() != 1 && cfgScheduleDay.getTask_code() != 6 && cfgScheduleDay.getTask_code() != 7 && iRunMode == 3) {
                                                        if (timeCheck) {
                                                            this.startFiveTest(curHour);
                                                        } else {
                                                            this.saveRecLogSys("系统日志", "五参数流程启动时间不符");
                                                        }
                                                    }

                                                    return;
                                                }

                                                this.scriptInfo.getFactorList2().clear();
                                                String strFactor2 = cfgScheduleDay.getFactor_list2();
                                                if (strFactor2 != null && strFactor2.length() > 0) {
                                                    String[] strArray2 = strFactor2.split(";");

                                                    for (int i = 0; i < strArray2.length; ++i) {
                                                        Iterator var13 = this.cfgFactorList.iterator();

                                                        while (var13.hasNext()) {
                                                            CfgFactor cfgFactor = (CfgFactor) var13.next();
                                                            if (cfgFactor.getName().equals(strArray2[i])) {
                                                                this.scriptInfo.getFactorList2().add(cfgFactor.getId());
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }

                                                String strFactor = cfgScheduleDay.getFactor_list();
                                                if (strFactor != null && strFactor.length() > 0) {
                                                    String[] strArray = strFactor.split(";");
                                                    this.scriptInfo.getFactorList().clear();

                                                    int i;
                                                    for (i = 0; i < strArray.length; ++i) {
                                                        Iterator var28 = this.cfgFactorList.iterator();

                                                        while (var28.hasNext()) {
                                                            CfgFactor cfgFactor = (CfgFactor) var28.next();
                                                            if (cfgFactor.getName().equals(strArray[i])) {
                                                                this.scriptInfo.getFactorList().add(cfgFactor.getId());
                                                                break;
                                                            }
                                                        }
                                                    }

                                                    if (this.scriptInfo.getFactorList().size() > 0) {
                                                        this.scriptInfo.getCmdRunList().clear();

                                                        for (i = 0; i < this.scriptInfo.getFactorList().size(); ++i) {
                                                            this.scriptInfo.getCmdRunList().add(false);
                                                        }

                                                        if (timeCheck) {
                                                            this.startTest(cfgScheduleDay.getTask_code(), "N");
                                                        } else {
                                                            this.saveRecLogSys("系统日志", cfgScheduleDay.getTask_name() + "流程启动时间不符");
                                                        }

                                                        if (cfgScheduleDay.getTask_code() != 1 && cfgScheduleDay.getTask_code() != 6 && cfgScheduleDay.getTask_code() != 7 && iRunMode == 3) {
                                                            if (timeCheck) {
                                                                this.startFiveTest(curHour);
                                                            } else {
                                                                this.saveRecLogSys("系统日志", "五参数流程启动时间不符");
                                                            }
                                                        }
                                                    } else if (iRunMode == 3) {
                                                        if (timeCheck) {
                                                            this.startFiveTest(curHour);
                                                        } else {
                                                            this.saveRecLogSys("系统日志", "五参数流程启动时间不符");
                                                        }
                                                    }
                                                } else if (iRunMode == 3) {
                                                    if (timeCheck) {
                                                        this.startFiveTest(curHour);
                                                    } else {
                                                        this.saveRecLogSys("系统日志", "五参数流程启动时间不符");
                                                    }
                                                }
                                            } else if (iRunMode == 3) {
                                                if (timeCheck) {
                                                    this.startFiveTest(curHour);
                                                } else {
                                                    this.saveRecLogSys("系统日志", "五参数流程启动时间不符");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        case 2:
                            if (this.checkFiveInfo != null && this.checkFiveInfo.isUrgent() && this.urgentCount % this.checkFiveInfo.getUrgentCircle() == 0) {
                                this.saveRecDataFive(timestamp, 1);
                            }

                            ++this.urgentCount;
                    }
                } else if (curSec >= 10) {
                    this.checkMin = false;
                }

                Timestamp saveTime = TimeHelper.getFormatSecondTimestamp();
                var8 = this.cfgScheduleQualityList.iterator();

                label206:
                while (true) {
                    CfgScheduleQuality cfgScheduleQuality;
                    do {
                        do {
                            do {
                                do {
                                    if (!var8.hasNext()) {
                                        if (Utility.startTime != null && Utility.startTime.getHourOfDay() == curHour && Utility.startTime.getMinuteOfHour() == curMin && Utility.startTime.getSecondOfMinute() == curSec) {
                                            final DevService devService = this.getDevServiceByDevName(this.start_dev);
                                            if (devService != null) {
                                                this.fixedThreadPool.execute(new Runnable() {
                                                    public void run() {
                                                        devService.doDevCmd(1, (String) null, (String) null);
                                                        MonitorService.this.saveRecLogSys("系统日志", "启动" + devService.getDevName() + Utility.getTaskName(1));
                                                    }
                                                });
                                                return;
                                            }
                                        }

                                        return;
                                    }

                                    cfgScheduleQuality = (CfgScheduleQuality) var8.next();
                                } while (!cfgScheduleQuality.isRun());
                            } while (cfgScheduleQuality.getHour() != curHour);
                        } while (cfgScheduleQuality.getMin() != curMin);
                    } while (cfgScheduleQuality.getSec() != curSec);

                    List<Integer> devRunList = new ArrayList();
                    Iterator var24 = this.devServiceList.iterator();

                    while (true) {
                        DevService devService;
                        DevService devQuality;
                        do {
                            do {
                                if (!var24.hasNext()) {
                                    continue label206;
                                }

                                devService = (DevService) var24.next();
                            } while (!devService.getDevType().equalsIgnoreCase("PARM"));

                            devQuality = this.getDevQuality(devService.getDevID());
                        } while (devQuality == null);

                        boolean bRun = false;
                        Iterator var31 = devRunList.iterator();

                        while (var31.hasNext()) {
                            Integer devRun = (Integer) var31.next();
                            if (devRun == devService.getDevID()) {
                                bRun = true;
                                break;
                            }
                        }

                        if (!bRun) {
                            devRunList.add(devService.getDevID());
                            String var32 = cfgScheduleQuality.getName();
                            byte var33 = -1;
                            switch (var32.hashCode()) {
                                case 1106386155:
                                    if (var32.equals("跨度核查")) {
                                        var33 = 1;
                                    }
                                    break;
                                case 1179888240:
                                    if (var32.equals("零点核查")) {
                                        var33 = 0;
                                    }
                            }

                            switch (var33) {
                                case 0:
                                    this.doDevCmd(devService.getDevID(), 3, "N", saveTime);
                                    break;
                                case 1:
                                    this.doDevCmd(devService.getDevID(), 4, "N", saveTime);
                            }
                        }
                    }
                }
            } catch (Exception var17) {
                System.out.println("Task Schedule Error." + var17.getMessage());
            }
        }
    }

    @Scheduled(
            fixedDelay = 1000L,
            initialDelay = 15000L
    )
    public void runSecondSchedule() {
        if (!restart && this.snStatus) {
            try {
                if (this.resetTimer != null && this.resetTimer.isRunning() && this.resetTimer.getLeftTime() == 0 && !this.scriptService.isOnScript()) {
                    this.resetTimer.setRunning(false);
                    this.saveRecLogSys("系统日志", "排空复位");
                    String strPwd = ScriptWord.getInstance().getScriptName(108);
                    if (strPwd != null) {
                        this.scriptInfo.setRunCmd(108);
                        this.scriptService.startScript(strPwd);
                    }
                }

                if (this.waitDev >= 0) {
                    if (this.rtdCount != null) {
                        this.rtdCount.setCountValue(this.waitDev);
                        if (!this.scriptService.isOnScript() && !this.scriptService.isOnScriptFive()) {
                            if (this.waitDev == 0) {
                                this.rtdCount.setCountTitle("");
                                this.sysStatus = "系统待机";
                            }

                            this.webSocketSender.updateCount(this.rtdCount);
                        }
                    }

                    --this.waitDev;
                }

                Iterator var4;
                if (this.envPowerSantak != null) {
                    UploadInter uploadInter;
                    if (this.envPowerSantak.isPowerBackup() && !this.powerAlarm) {
                        this.powerAlarm = true;
                        this.saveRecLogSys("系统日志", "UPS供电");
                        this.saveRecFaultSys("系统故障", "市电断电！切换为UPS供电！");
                        var4 = this.uploadInterList.iterator();

                        while (var4.hasNext()) {
                            uploadInter = (UploadInter) var4.next();
                            uploadInter.sendPowerAlarm(true);
                        }
                    }

                    if (!this.envPowerSantak.isPowerBackup() && this.powerAlarm) {
                        this.powerAlarm = false;
                        this.saveRecLogSys("系统日志", "市电供电");
                        var4 = this.uploadInterList.iterator();

                        while (var4.hasNext()) {
                            uploadInter = (UploadInter) var4.next();
                            uploadInter.sendPowerAlarm(false);
                        }
                    }
                }

                if (this.doorService != null && this.doorService.getAccessPwd() != null && this.doorService.getAccessPwd().length() > 0) {
                    String strPwd = this.doorService.getAccessPwd();
                    this.doorService.setAccessPwd((String) null);
                    if (this.scriptService != null) {
                        (new Thread(new Runnable() {
                            public void run() {
                                try {
                                    List<CfgUser> cfgUserList = MonitorService.this.cfgMapper.getCfgUserList();
                                    Iterator var2 = cfgUserList.iterator();

                                    while (var2.hasNext()) {
                                        CfgUser cfgUser = (CfgUser) var2.next();
                                        if (strPwd.equals(cfgUser.getAccess())) {
                                            MonitorService.this.scriptService.doCmd(1, "门禁继电器");
                                            MonitorService.this.saveRecInfoDoor(cfgUser, MonitorService.this.doorService.getAccesssTime());
                                            Iterator var4 = MonitorService.this.uploadInterList.iterator();

                                            while (var4.hasNext()) {
                                                UploadInter uploadInter = (UploadInter) var4.next();
                                                uploadInter.sendSystemAlarm(MonitorService.this.doorService.getAccesssTime(), "14");
                                            }

                                            Thread.sleep(5000L);
                                            MonitorService.this.scriptService.doCmd(0, "门禁继电器");
                                            break;
                                        }
                                    }
                                } catch (Exception var6) {
                                }

                            }
                        })).start();
                    }
                }

                var4 = this.cfgDevList.iterator();

                while (true) {
                    CfgDev cfgDev;
                    do {
                        if (!var4.hasNext()) {
                            return;
                        }

                        cfgDev = (CfgDev) var4.next();
                    } while (!cfgDev.getType().toUpperCase().equals("PARM") && !cfgDev.getType().toUpperCase().equals("FIVE"));

                    if (cfgDev.getRelated_id() >= 10000 && this.IStatus[cfgDev.getRelated_id() - 10000] != this.scriptService.getIStatus(cfgDev.getRelated_id() - 10000)) {
                        if (this.scriptService.getIStatus(cfgDev.getRelated_id() - 10000)) {
                            this.saveRecFaultSys("系统故障", this.getDevName(cfgDev.getRelated_id()) + "　告警");
                        } else {
                            this.saveRecFaultSys("系统故障", this.getDevName(cfgDev.getRelated_id()) + "　恢复");
                        }

                        this.IStatus[cfgDev.getRelated_id() - 10000] = this.scriptService.getIStatus(cfgDev.getRelated_id() - 10000);
                    }
                }
            } catch (Exception var3) {
                System.out.println("Second Schedule Error." + var3.getMessage());
            }
        }
    }

    @Scheduled(
            fixedRate = 10000L,
            initialDelay = 20000L
    )
    public void runTenSecondSchedule() {
        Timestamp timestamp = TimeHelper.getCurrentTimestamp();
        this.saveDevState(timestamp);
        this.dbService.setCfgFactorList(this.cfgFactorList);
        this.webSocketSender.updateInfo(this.realTimeService.getClientRtdData());
        this.scriptService.updateVarAIList();
        Iterator var2 = this.cfgDevList.iterator();

        while (true) {
            while (var2.hasNext()) {
                CfgDev cfgDev = (CfgDev) var2.next();
                Iterator var4 = this.devServiceList.iterator();

                while (var4.hasNext()) {
                    DevService devService = (DevService) var4.next();
                    if (devService.getDevID() == cfgDev.getId()) {
                        if (devService.isbConnect() != cfgDev.isStatus()) {
                            cfgDev.setStatus(devService.isbConnect());
                            if (!devService.isbConnect()) {
                                this.saveRecFaultSys("系统故障", cfgDev.getName() + "　仪器通讯故障");
                            } else {
                                this.saveRecFaultSys("系统故障", cfgDev.getName() + "　仪器通讯恢复");
                            }
                        }
                        break;
                    }
                }
            }

            this.saveRecLogDev(timestamp);
            this.saveRecAlarmDev(timestamp);
            var2 = this.devServiceList.iterator();

            while (var2.hasNext()) {
                DevService devService = (DevService) var2.next();
                if (devService.getDevProtocol().equalsIgnoreCase("QualityModbus3")) {
                    Integer warnId = (Integer) devService.getParam(1);
                    if (warnId != null && devService.getWarnState() != warnId) {
                        devService.setWarnState(warnId);
                        switch (warnId) {
                            case 1:
                                this.saveRecSys(3, "系统故障", devService.getDevName() + "缺液告警");
                                break;
                            case 2:
                                this.saveRecSys(3, "系统故障", devService.getDevName() + "仪表内部其它告警");
                                break;
                            default:
                                this.saveRecSys(3, "系统故障", devService.getDevName() + "无告警");
                        }
                    }
                }
            }

            if (this.sms_input.length() > 0 && this.sms_phone.length() > 0) {
                if (this.scriptService.getIStatus(this.sms_input)) {
                    if (!this.sms_warn) {
                        this.sms_warn = true;
                        CfgStation cfgStation = this.cfgMapper.getCfgStation();
                        System.out.println("SMS: Send");
                        String warnInfo = this.scriptService.getIName(this.sms_input);
                        if (warnInfo == null) {
                            warnInfo = this.sms_input;
                        }

                        SmsUtil.sendSmsStation(this.sms_phone, cfgStation.getStation_name(), cfgStation.getStation_code(), warnInfo);
                    }
                } else {
                    this.sms_warn = false;
                }
            }

            if (this.gas_input.length() > 0) {
                this.saveRecInfoGas(this.scriptService.getIStatus(this.gas_input));
            }

            if (this.water_input.length() > 0) {
                this.saveRecInfoWater(this.scriptService.getIStatus(this.water_input));
            }

            return;
        }
    }

    @Scheduled(
            fixedRate = 60000L,
            initialDelay = 25000L
    )
    public void runMinSchedule() {
        if (!restart && this.snStatus) {
            DateTime curTime = new DateTime();
            final Timestamp timestamp = this.getFormatCurrentTime();
            final int curDay = curTime.getDayOfMonth();
            int curHour = curTime.getHourOfDay();
            int curMin = curTime.getMinuteOfHour();
            int curWeekDay = curTime.getDayOfWeek();
            if (this.checkFiveInfo != null && this.checkFiveInfo.isWeek() && curWeekDay == this.checkFiveInfo.getWeekDay() && TimeHelper.compareHourMinute(curHour, curMin, this.checkFiveInfo.getStartDate(), this.checkFiveInfo.getEndDate())) {
                this.saveRecCheckFiveAll(timestamp);
            }

            Iterator var7;
            if (this.cfgFlow.isRecord() && (curHour * 60 + curMin) % this.cfgFlow.getRecord_circle() == 0) {
                this.saveRecDataFlow(timestamp);
                if (this.cfgFlow.isUpload()) {
                    var7 = this.uploadInterList.iterator();

                    while (var7.hasNext()) {
                        UploadInter uploadInter = (UploadInter) var7.next();
                        uploadInter.sendFlowData(0, timestamp);
                    }
                }
            }

            if (this.cfgSample.getMode() == 0 && this.sampleService != null && ((CfgScheduleSample) this.cfgScheduleSampleList.get(curHour)).isRun() && curMin == 0) {
                if (this.sampleService.doSample((String) null)) {
                    this.saveRecLogSample(this.sampleService.getSampleVase() + "", this.sampleService.getSampleVolume() + "", this.sampleService.getSampleModeName(), "留样成功");
                    this.saveSampleRecord();
                } else {
                    this.saveRecLogSample(this.sampleService.getSampleVase() + "", this.sampleService.getSampleVolume() + "", this.sampleService.getSampleModeName(), "留样失败");
                }
            }

            this.updateSampleRecord();
            ScriptInfo recInfo;
            if (this.save_minute) {
                recInfo = new ScriptInfo();
                recInfo.setRunTime(timestamp);
                this.saveRecData(recInfo, 0, 0);
            }

            if (this.save_average && (curMin % 10 == 0 || curMin % 10 == 5)) {
                recInfo = new ScriptInfo();
                recInfo.setRunTime(timestamp);
                this.saveRecDataAvg(recInfo, 0, 0);
                if (curMin == 0) {
                    this.saveRecDataAvg(recInfo, 1, 0);
                    if (curHour == 0) {
                        this.saveRecDataAvg(recInfo, 2, 0);
                    }
                }
            }

            if (curHour == 0 && curMin == 0) {
                this.fixedThreadPool.execute(new Runnable() {
                    public void run() {
                        MonitorService.this.saveRecDataDay((new DateTime(timestamp.getTime())).minusDays(1));
                        if (curDay == 1) {
                            MonitorService.this.saveRecDataMonth((new DateTime(timestamp.getTime())).minusMonths(1));
                        }

                    }
                });
            }

            this.saveRecAlarmData(timestamp);
            if (Utility.wtwCalTime != null && this.wtwService != null && curHour == this.wtwHour && curMin == this.wtwMin) {
                this.wtwService.doDevCmd(8, (String) null, (String) null);
            }

            var7 = this.devServiceList.iterator();

            while (true) {
                DevService devService;
                do {
                    if (!var7.hasNext()) {
                        return;
                    }

                    devService = (DevService) var7.next();
                } while (!devService.getDevProtocol().equalsIgnoreCase("DevPlatform"));

                Iterator var9 = this.cfgFactorList.iterator();

                while (var9.hasNext()) {
                    CfgFactor cfgFactor = (CfgFactor) var9.next();
                    if (cfgFactor.isUsed() && cfgFactor.getDevID() == devService.getDevID() && cfgFactor.getDevChannel() > 0) {
                        Double dTmp = devService.getChannelVals(cfgFactor.getDevChannel());
                        if (dTmp != null) {
                            dTmp = Utility.getFormatDouble(dTmp * cfgFactor.getUnitParam(), cfgFactor.getDecimals());
                            cfgFactor.setDataMea(dTmp);
                            cfgFactor.setFlagMea("N");
                        }

                        Timestamp recTime = devService.getChannelTime(cfgFactor.getDevChannel());
                        if (cfgFactor.getDataMea() != null && recTime != null) {
                            this.saveHourData(recTime, cfgFactor.getId());
                        }
                    }
                }
            }
        }
    }

    private Timestamp getFormatCurrentTime() {
        return TimeHelper.getFormatMinuteTimestamp();
    }

    public RecMapper getRecMapper() {
        return this.recMapper;
    }

    public CfgMapper getCfgMapper() {
        return this.cfgMapper;
    }

    private Double formatDouble(Double srcDouble, int length) {
        return srcDouble == null ? null : Utility.getFormatDouble(srcDouble, length);
    }

    private void doSaveDevData(ScriptInfo scriptInfo, int factorID) {
        switch (scriptInfo.getRunCmd()) {
            case 2:
                this.saveRecCheckStd(scriptInfo, factorID);
                this.saveRecParam(scriptInfo.getRunTime(), 452, factorID);
                break;
            case 3:
                this.saveRecCheckZero(scriptInfo, factorID);
                this.saveRecParam(scriptInfo.getRunTime(), 453, factorID);
                break;
            case 4:
                this.saveRecCheckSpan(scriptInfo, factorID);
                this.saveRecParam(scriptInfo.getRunTime(), 454, factorID);
                break;
            case 5:
                break;
            default:
                this.saveRecData(scriptInfo, 1, factorID);
                this.saveRecParam(scriptInfo.getRunTime(), 451, factorID);
        }

    }

    public void doSaveZeroData() {
        if (this.save_zero && this.scriptInfo.getRunCmd() == 1) {
            RecDataTime recDataTime = this.recMapper.getRecTimeHourByTime(this.scriptInfo.getRunTime());
            if (recDataTime == null) {
                recDataTime = this.saveRecTime(this.scriptInfo.getRunTime(), this.scriptInfo.getRunCmd());
                Iterator var2 = this.cfgFactorList.iterator();

                while (true) {
                    CfgFactor cfgFactor;
                    do {
                        do {
                            if (!var2.hasNext()) {
                                this.doUploadDevData(this.scriptInfo, 0);
                                return;
                            }

                            cfgFactor = (CfgFactor) var2.next();
                        } while (!cfgFactor.isUsed());
                    } while (!cfgFactor.isFiveType() && !cfgFactor.isParmType() && !cfgFactor.isSwType());

                    RecDataFactor recDataFactor = this.getRecDataFactor(cfgFactor, recDataTime, this.scriptInfo.getRunCmd());
                    if (recDataFactor != null) {
                        try {
                            recDataFactor.setDataFlag("lw");
                            recDataFactor.setDataValue(0.0D);
                            this.recMapper.addRecDataHour(recDataFactor);
                        } catch (Exception var6) {
                            Utility.logInfo("因子" + cfgFactor.getName() + "0值数据入库异常");
                        }
                    }
                }
            }
        }

    }

    public void doSaveZeroDataFive() {
        if (this.save_zero) {
            RecDataTime recDataTime = this.recMapper.getRecTimeHourByTime(this.scriptFiveRunTime);
            if (recDataTime == null) {
                this.saveRecDataFiveZero(this.scriptFiveRunTime);
                this.saveRecDataFiveZero(this.scriptFiveRunTime, 1);
                Iterator var2 = this.uploadInterList.iterator();

                while (var2.hasNext()) {
                    UploadInter uploadInter = (UploadInter) var2.next();
                    uploadInter.sendFiveData(0);
                }
            }
        }

    }

    private void doStartDev(int cmdType) {
        switch (cmdType) {
            case 6:
            case 7:
                this.startDev(1, "nblg", false);
                break;
            default:
                this.startDev(cmdType, "nblg", false);
        }

    }

    private void doUploadDevData(ScriptInfo scriptInfo, int factorID) {
        Iterator var3 = this.uploadInterList.iterator();

        while (var3.hasNext()) {
            UploadInter uploadInter = (UploadInter) var3.next();
            switch (scriptInfo.getRunCmd()) {
                case 2:
                    uploadInter.sendStdCheck(factorID, scriptInfo.getRunTime());
                    uploadInter.sendParamDev(factorID, scriptInfo.getRunTime(), 452);
                    break;
                case 3:
                    uploadInter.sendZeroCheck(factorID, scriptInfo.getRunTime());
                    uploadInter.sendParamDev(factorID, scriptInfo.getRunTime(), 453);
                    break;
                case 4:
                    uploadInter.sendSpanCheck(factorID, scriptInfo.getRunTime());
                    uploadInter.sendParamDev(factorID, scriptInfo.getRunTime(), 454);
                    break;
                case 5:
                    break;
                case 6:
                    uploadInter.sendParCheck(factorID, scriptInfo.getRunTime());
                    uploadInter.sendParamDev(factorID, scriptInfo.getRunTime(), 456);
                    break;
                case 7:
                    Timestamp recTime = scriptInfo.getRunTime();
                    if (this.scriptService.isOnScript()) {
                        DateTime dateTime = new DateTime(recTime.getTime());
                        dateTime = dateTime.plusHours(2);
                        recTime = new Timestamp(dateTime.getMillis());
                    }

                    uploadInter.sendRcvrCheck(factorID, recTime);
                    uploadInter.sendParamDev(factorID, recTime, 457);
                    break;
                default:
                    uploadInter.sendHourData(factorID, scriptInfo.getRunTime());
                    uploadInter.sendParamDev(factorID, scriptInfo.getRunTime(), 451);
            }

            try {
                Thread.sleep(1000L);
            } catch (Exception var7) {
            }
        }

    }

    private void doUploadHourData(int factorID) {
        UploadInter uploadInter;
        for (Iterator var2 = this.uploadInterList.iterator(); var2.hasNext(); uploadInter.sendParamDev(factorID, this.scriptInfo.getRunTime(), 451)) {
            uploadInter = (UploadInter) var2.next();
            uploadInter.sendHourData(factorID, this.scriptInfo.getRunTime());

            try {
                Thread.sleep(1000L);
            } catch (Exception var5) {
            }
        }

    }

    private void doStateDevCmd(String strState) {
        List<Integer> saveIDList = new ArrayList();
        List<Integer> startIDList = new ArrayList();
        Iterator var5 = this.devServiceList.iterator();

        while (true) {
            boolean bCheck;
            DevService devService;
            int i;
            int runCmd;
            label58:
            do {
                while (var5.hasNext()) {
                    devService = (DevService) var5.next();

                    for (i = 0; i < this.scriptInfo.getFactorList().size(); ++i) {
                        Integer factorId = (Integer) this.scriptInfo.getFactorList().get(i);
                        if (devService.getFactorID() == factorId) {
                            if (devService.getDevSaveTag(this.scriptInfo.getRunCmd()) != null && devService.getDevSaveTag(this.scriptInfo.getRunCmd()).equals(strState)) {
                                bCheck = false;
                                Iterator var9 = saveIDList.iterator();

                                while (var9.hasNext()) {
                                    Integer saveID = (Integer) var9.next();
                                    if (devService.getFactorID() == saveID) {
                                        bCheck = true;
                                        break;
                                    }
                                }

                                if (!bCheck) {
                                    saveIDList.add(devService.getFactorID());
                                    this.doSaveDevData(this.scriptInfo, devService.getFactorID());
                                }
                            }

                            runCmd = devService.getStartTagCmd(strState);
                            continue label58;
                        }
                    }
                }

                return;
            } while (runCmd <= 0);

            bCheck = false;
            Iterator var13 = startIDList.iterator();

            while (var13.hasNext()) {
                Integer startID = (Integer) var13.next();
                if (devService.getDevID() == startID) {
                    bCheck = true;
                    break;
                }
            }

            if (!bCheck) {
                startIDList.add(devService.getDevID());
                this.runDev(devService.getDevID(), runCmd);
                this.scriptInfo.setCmdRunList(i);
            }
        }
    }

    private void doStateFiveCmd(String strState, int cmdType) {
        List<Integer> saveIDList = new ArrayList();
        List<Integer> startIDList = new ArrayList();
        Iterator var6 = this.devServiceList.iterator();

        while (true) {
            boolean bCheck;
            DevService devService;
            int runCmd;
            label59:
            do {
                while (var6.hasNext()) {
                    devService = (DevService) var6.next();
                    Iterator var8 = this.cfgFactorList.iterator();

                    while (var8.hasNext()) {
                        CfgFactor cfgFactor = (CfgFactor) var8.next();
                        if (cfgFactor.isFiveType() && devService.getFactorID() == cfgFactor.getId()) {
                            if (devService.getDevSaveTag(cmdType) != null && devService.getDevSaveTag(cmdType).equals(strState)) {
                                bCheck = false;
                                Iterator var10 = saveIDList.iterator();

                                while (var10.hasNext()) {
                                    Integer saveID = (Integer) var10.next();
                                    if (devService.getFactorID() == saveID) {
                                        bCheck = true;
                                        break;
                                    }
                                }

                                if (!bCheck) {
                                    saveIDList.add(devService.getFactorID());
                                    this.saveDataFive(this.scriptFiveRunTime, cfgFactor);
                                    this.saveDataHour(this.scriptFiveRunTime, cfgFactor);
                                }
                            }

                            runCmd = devService.getStartTagCmd(strState);
                            continue label59;
                        }
                    }
                }

                return;
            } while (runCmd <= 0);

            bCheck = false;
            Iterator var14 = startIDList.iterator();

            while (var14.hasNext()) {
                Integer startID = (Integer) var14.next();
                if (devService.getDevID() == startID) {
                    bCheck = true;
                    break;
                }
            }

            if (!bCheck) {
                startIDList.add(devService.getDevID());
                this.runDev(devService.getDevID(), runCmd);
            }
        }
    }
    @SuppressWarnings("FallThrough")
    private void initPlcStateListener() {
        this.scriptStateListener = new ScriptStateListener() {
            public void OnStateEvent(String requestState) {
                String[] strArray = requestState.split(" ");
                boolean isFive;
                String tmpStatus;
                if (strArray.length == 2) {
                    isFive = true;
                    tmpStatus = strArray[0];
                } else {
                    isFive = false;
                    tmpStatus = requestState;
                }

                Utility.logInfo("PLC Event: " + tmpStatus);
                MonitorService.this.saveRecLogSys("系统日志", tmpStatus);
                if (tmpStatus.contains("故障")) {
                    MonitorService.this.saveRecFaultSys("系统故障", tmpStatus);
                }

                if (isFive) {
                    if (!MonitorService.this.scriptService.isOnScript()) {
                        MonitorService.this.sysStatus = tmpStatus;
                    }

                    MonitorService.this.doStateFiveCmd(strArray[0], 1);
                } else {
                    MonitorService.this.sysStatus = tmpStatus;
                    MonitorService.this.doStateDevCmd(strArray[0]);
                }

                String var6 = strArray[0];
                byte var7 = -1;
                switch (var6.hashCode()) {
                    case -1955246184:
                        if (var6.equals("质控数据保存")) {
                            var7 = 25;
                        }
                        break;
                    case -1948507137:
                        if (var6.equals("同步留样排空")) {
                            var7 = 6;
                        }
                        break;
                    case -1878298022:
                        if (var6.equals("动环信息缓存")) {
                            var7 = 24;
                        }
                        break;
                    case -1873940521:
                        if (var6.equals("水样数据上传")) {
                            var7 = 20;
                        }
                        break;
                    case -1497030463:
                        if (var6.equals("留样瓶判断")) {
                            var7 = 5;
                        }
                        break;
                    case -1366807679:
                        if (var6.equals("量程切换测试")) {
                            var7 = 28;
                        }
                        break;
                    case -1341608258:
                        if (var6.equals("仪器加标测试中")) {
                            var7 = 15;
                        }
                        break;
                    case -587103522:
                        if (var6.equals("宁波理工仪器加标测试中")) {
                            var7 = 16;
                        }
                        break;
                    case -575174027:
                        if (var6.equals("仪器标样测试中")) {
                            var7 = 11;
                        }
                        break;
                    case -487840678:
                        if (var6.equals("五参数自清洗")) {
                            var7 = 23;
                        }
                        break;
                    case 698073:
                        if (var6.equals("启用")) {
                            var7 = 27;
                        }
                        break;
                    case 26670458:
                        if (var6.equals("超量程数据保存")) {
                            var7 = 29;
                        }
                        break;
                    case 412069922:
                        if (var6.equals("仪器水样测试中")) {
                            var7 = 10;
                        }
                        break;
                    case 656911409:
                        if (var6.equals("加标配样")) {
                            var7 = 3;
                        }
                        break;
                    case 712901788:
                        if (var6.equals("仪器量核测试中")) {
                            var7 = 13;
                        }
                        break;
                    case 793322212:
                        if (var6.equals("离心机工作中")) {
                            var7 = 9;
                        }
                        break;
                    case 798713556:
                        if (var6.equals("数据上传")) {
                            var7 = 19;
                        }
                        break;
                    case 798731161:
                        if (var6.equals("数据保存")) {
                            var7 = 18;
                        }
                        break;
                    case 851334200:
                        if (var6.equals("泵一采水")) {
                            var7 = 0;
                        }
                        break;
                    case 851468740:
                        if (var6.equals("泵二采水")) {
                            var7 = 1;
                        }
                        break;
                    case 921267847:
                        if (var6.equals("留样判断")) {
                            var7 = 7;
                        }
                        break;
                    case 985159833:
                        if (var6.equals("系统待机")) {
                            var7 = 26;
                        }
                        break;
                    case 985196908:
                        if (var6.equals("系统排空")) {
                            var7 = 2;
                        }
                        break;
                    case 1105848480:
                        if (var6.equals("超标留样")) {
                            var7 = 4;
                        }
                        break;
                    case 1114451427:
                        if (var6.equals("仪器平行样测试中")) {
                            var7 = 17;
                        }
                        break;
                    case 1166574658:
                        if (var6.equals("宁波理工仪器水样测试中")) {
                            var7 = 14;
                        }
                        break;
                    case 1541739632:
                        if (var6.equals("启动同步留样")) {
                            var7 = 8;
                        }
                        break;
                    case 1836800342:
                        if (var6.equals("五参数数据上传")) {
                            var7 = 22;
                        }
                        break;
                    case 1931025987:
                        if (var6.equals("仪器零核测试中")) {
                            var7 = 12;
                        }
                        break;
                    case 2062444967:
                        if (var6.equals("五参数缓存")) {
                            var7 = 21;
                        }
                }

                int checkParam;
                Iterator var8;
                CfgFactor codFactor;
                boolean faultState;
                Iterator var11;
                DevService devService;
                Integer factorID;
                int vase;
                Iterator var29;
                CfgFactor cfgFactorx;
                label402:
                switch (var7) {
                    case 0:
                    case 1:
                        if (MonitorService.this.sampleService != null && !isFive) {
                            MonitorService.this.sampleService.doPrepare();
                        }
                        break;
                    case 2:
                        MonitorService.this.startQuality(5005);
                        break;
                    case 3:
                        MonitorService.this.startQuality(4000);
                        MonitorService.this.startQuality(5003);
                        break;
                    case 4:
                        boolean bOver;
                        if (MonitorService.this.smsService != null) {
                            bOver = false;
                            String warnInfo = "";
                            Iterator var28 = MonitorService.this.cfgFactorList.iterator();

                            label336:
                            while (true) {
                                CfgFactor cfgFactor;
                                do {
                                    if (!var28.hasNext()) {
                                        if (bOver) {
                                            CfgStation cfgStation = MonitorService.this.cfgMapper.getCfgStation();
                                            String smsInfo = "站点名称:" + cfgStation.getStation_name() + "。";
                                            smsInfo = smsInfo + "超标数据时间:" + TimeHelper.formatDataTime_yyyy_MM_dd_HH_mm_ss(MonitorService.this.scriptInfo.getRunTime()) + "。";
                                            smsInfo = smsInfo + warnInfo;
                                            MonitorService.this.smsService.doSend(smsInfo);
                                        }
                                        break label336;
                                    }

                                    cfgFactor = (CfgFactor) var28.next();
                                } while (cfgFactor.getDataMea() <= cfgFactor.getAlarmMax() && cfgFactor.getDataMea() >= cfgFactor.getAlarmMin());

                                bOver = true;
                                warnInfo = warnInfo + "超标因子:" + cfgFactor.getName() + "。";
                                warnInfo = warnInfo + "标准值:" + cfgFactor.getAlarmMin() + "-" + cfgFactor.getAlarmMax() + "。";
                                warnInfo = warnInfo + "超标数据:" + cfgFactor.getDataMea() + "。";
                            }
                        }

                        if (MonitorService.this.sampleService != null) {
                            bOver = false;
                            var29 = MonitorService.this.cfgFactorList.iterator();

                            label321:
                            {
                                do {
                                    do {
                                        if (!var29.hasNext()) {
                                            break label321;
                                        }

                                        cfgFactorx = (CfgFactor) var29.next();
                                    } while (!cfgFactorx.isParmType());
                                } while (cfgFactorx.getDataMea() <= cfgFactorx.getAlarmMax() && cfgFactorx.getDataMea() >= cfgFactorx.getAlarmMin());

                                bOver = true;
                                MonitorService.this.saveRecLogSys("系统日志", cfgFactorx.getName() + "超标");
                            }

                            switch (MonitorService.this.cfgSample.getMode()) {
                                case 1:
                                    if (bOver) {
                                        boolean bSample = false;
                                        bSample = MonitorService.this.scriptService.doCmd(1, "采样器");
                                        if (bSample) {
                                            MonitorService.this.saveRecLogSample("-", "-", MonitorService.this.sampleService.getSampleModeName(), "留样成功");
                                        }

                                        if (!bSample) {
                                            if (MonitorService.this.sampleService.doSample((String) null)) {
                                                MonitorService.this.saveRecLogSample(MonitorService.this.sampleService.getSampleVase() + "", MonitorService.this.sampleService.getSampleVolume() + "", MonitorService.this.sampleService.getSampleModeName(), "留样成功");
                                                MonitorService.this.saveSampleRecord();
                                            } else {
                                                MonitorService.this.saveRecLogSample(MonitorService.this.sampleService.getSampleVase() + 1 + "", MonitorService.this.sampleService.getSampleVolume() + "", MonitorService.this.sampleService.getSampleModeName(), "留样失败");
                                            }
                                        }
                                    }
                                    break;
                                case 3:
                                    if (bOver) {
                                        MonitorService.this.saveRecLogSample(MonitorService.this.sampleService.getSampleVase() + "", MonitorService.this.sampleService.getSampleVolume() + "", MonitorService.this.sampleService.getSampleModeName(), "留样成功");
                                        MonitorService.this.saveSampleRecord();
                                    } else {
                                        MonitorService.this.sampleService.doEmpty((String) null);
                                        MonitorService.this.clearSampleRecord();
                                        vase = MonitorService.this.sampleService.getSampleVase();
                                        MonitorService.this.sampleService.setSampleVase(vase - 1);
                                    }
                                    // fall through
                            }
                        }
                    case 6:
                        if (MonitorService.this.cfgSample.getMode() == 2 && MonitorService.this.sampleService != null) {
                            List<RecSample> recSampleList = MonitorService.this.recMapper.getRecSample();

                            for (vase = 0; vase < recSampleList.size(); ++vase) {
                                if (((RecSample) recSampleList.get(vase)).getRec_time() == null) {
                                    MonitorService.this.sampleService.setSampleVase(vase + 1);
                                    if (MonitorService.this.sampleService.doEmpty((String) null)) {
                                        MonitorService.this.saveRecLogSample(MonitorService.this.sampleService.getSampleVase() + "", "-", MonitorService.this.sampleService.getSampleModeName(), "排空成功");
                                    } else {
                                        MonitorService.this.saveRecLogSample(MonitorService.this.sampleService.getSampleVase() + "", "-", MonitorService.this.sampleService.getSampleModeName(), "排空失败");
                                    }

                                    return;
                                }
                            }

                            vase = 0;

                            for (int i = 1; i < recSampleList.size(); ++i) {
                                if (((RecSample) recSampleList.get(i)).getRec_time().getTime() < ((RecSample) recSampleList.get(vase)).getRec_time().getTime()) {
                                    vase = i;
                                }
                            }

                            MonitorService.this.sampleService.setSampleVase(vase + 1);
                            if (MonitorService.this.sampleService.doEmpty((String) null)) {
                                MonitorService.this.saveRecLogSample(MonitorService.this.sampleService.getSampleVase() + "", "-", MonitorService.this.sampleService.getSampleModeName(), "排空成功");
                            } else {
                                MonitorService.this.saveRecLogSample(MonitorService.this.sampleService.getSampleVase() + "", "-", MonitorService.this.sampleService.getSampleModeName(), "排空失败");
                            }
                        }
                        break;

                    case 8:
                        if (MonitorService.this.cfgSample.getMode() == 2 && MonitorService.this.sampleService != null) {
                            if (MonitorService.this.sampleService.getEmptySampleVase() == 0) {
                                MonitorService.this.saveRecLogSample("-", "-", MonitorService.this.sampleService.getSampleModeName(), "留样失败");
                            } else if (MonitorService.this.sampleService.doSample((String) null)) {
                                MonitorService.this.saveRecLogSample(MonitorService.this.sampleService.getSampleVase() + "", MonitorService.this.sampleService.getSampleVolume() + "", MonitorService.this.sampleService.getSampleModeName(), "留样成功");
                                MonitorService.this.saveSampleRecord();
                                MonitorService.this.sampleService.setSampleVase(MonitorService.this.sampleService.getSampleVase() % MonitorService.this.sampleService.getVaseSize() + 1);
                            } else {
                                MonitorService.this.saveRecLogSample(MonitorService.this.sampleService.getSampleVase() + "", MonitorService.this.sampleService.getSampleVolume() + "", MonitorService.this.sampleService.getSampleModeName(), "留样失败");
                            }
                        }
                        break;
                    case 9:
                        if (MonitorService.this.centrifugeService != null) {
                            MonitorService.this.centrifugeService.doMeaCmd();
                        }
                        break;
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                        MonitorService.this.doStartDev(MonitorService.this.scriptInfo.getRunCmd());
                        if (MonitorService.this.cfgSample.getMode() == 3 && MonitorService.this.sampleService != null && strArray[0].equals("仪器水样测试中")) {
                            MonitorService.this.sampleService.doSample((String) null);
                        }
                        break;
                    case 14:
                        MonitorService.this.startDev(1, "nblg", true);
                        break;
                    case 15:
                        MonitorService.this.startDevRcvr(MonitorService.this.scriptInfo.getRunCmd(), "nblg", false);
                        break;
                    case 16:
                        MonitorService.this.startDevRcvr(MonitorService.this.scriptInfo.getRunCmd(), "nblg", true);
                        break;
                    case 17:
                        MonitorService.this.startDev(MonitorService.this.scriptInfo.getRunCmd(), "nblg", false);
                        break;
                    case 18:
                        MonitorService.this.checkRange();
                        checkParam = (int) ScriptWord.getInstance().getSysVar("CheckParam");
                        if (MonitorService.this.scriptInfo.getFactorList().size() <= 0) {
                            MonitorService.this.doSaveDevData(MonitorService.this.scriptInfo, 0);
                        } else {
                            var8 = MonitorService.this.scriptInfo.getFactorList().iterator();

                            while (var8.hasNext()) {
                                factorID = (Integer) var8.next();
                                faultState = false;
                                if (checkParam > 0) {
                                    var11 = MonitorService.this.devServiceList.iterator();

                                    while (var11.hasNext()) {
                                        devService = (DevService) var11.next();
                                        if (devService.getFactorID() == factorID) {
                                            faultState = devService.isFaultState();
                                            break;
                                        }
                                    }
                                }

                                if (!faultState) {
                                    MonitorService.this.doSaveDevData(MonitorService.this.scriptInfo, factorID);
                                }
                            }
                        }

                        MonitorService.this.saveRecLogDev(MonitorService.this.scriptInfo.getRunCmd(), 0, false);
                        MonitorService.this.writePlcHistory();
                        break;
                    case 19:
                        MonitorService.this.fixedThreadPool.execute(new Runnable() {
                            public void run() {
                                if (MonitorService.this.scriptInfo.getFactorList().size() > 0) {
                                    int factorSize = 0;
                                    Iterator var2 = MonitorService.this.cfgFactorList.iterator();

                                    CfgFactor cfgFactor;
                                    while (var2.hasNext()) {
                                        cfgFactor = (CfgFactor) var2.next();
                                        if (cfgFactor.isParmType()) {
                                            ++factorSize;
                                        }
                                    }

                                    if (MonitorService.this.scriptInfo.getFactorList().size() == factorSize) {
                                        MonitorService.this.doUploadDevData(MonitorService.this.scriptInfo, 0);
                                    } else if (MonitorService.this.upload_type > 0) {
                                        MonitorService.this.doUploadDevData(MonitorService.this.scriptInfo, 0);
                                    } else {
                                        var2 = MonitorService.this.scriptInfo.getFactorList().iterator();

                                        while (var2.hasNext()) {
                                            Integer factorID = (Integer) var2.next();
                                            MonitorService.this.doUploadDevData(MonitorService.this.scriptInfo, factorID);
                                        }

                                        var2 = MonitorService.this.cfgFactorList.iterator();

                                        while (var2.hasNext()) {
                                            cfgFactor = (CfgFactor) var2.next();
                                            if (cfgFactor.isFiveType()) {
                                                MonitorService.this.doUploadDevData(MonitorService.this.scriptInfo, cfgFactor.getId());
                                            }
                                        }
                                    }
                                } else {
                                    MonitorService.this.doUploadDevData(MonitorService.this.scriptInfo, 0);
                                }

                            }
                        });
                        break;
                    case 20:
                        MonitorService.this.doUploadHourData(0);
                        break;
                    case 21:
                        MonitorService.this.refreshFactorData = false;
                        if (isFive) {
                            MonitorService.this.saveRecDataFive(MonitorService.this.scriptFiveRunTime);
                            MonitorService.this.saveRecDataFive(MonitorService.this.scriptFiveRunTime, 1);
                        } else {
                            MonitorService.this.saveRecDataFive(MonitorService.this.scriptInfo.getRunTime());
                            MonitorService.this.saveRecDataFive(MonitorService.this.scriptInfo.getRunTime(), 1);
                        }

                        MonitorService.this.refreshFactorData = true;
                        if (MonitorService.this.acid_base_switch) {
                            CfgFactor condFactor = MonitorService.this.getFactorByName("电导率");
                            codFactor = MonitorService.this.getFactorByName("高锰酸盐指数");
                            if (condFactor != null && codFactor != null && condFactor.getDataMea() != null) {
                                DevService devServicex = MonitorService.this.getDevServiceByFactorID(codFactor.getId());
                                if (condFactor.getDataMea() >= (double) MonitorService.this.acid_base_cond) {
                                    devServicex.doSetBase("1");
                                } else {
                                    devServicex.doSetBase("0");
                                }
                            }
                        }
                        break;
                    case 22:
                        var8 = MonitorService.this.uploadInterList.iterator();

                        while (true) {
                            if (!var8.hasNext()) {
                                break label402;
                            }

                            UploadInter uploadInter = (UploadInter) var8.next();
                            uploadInter.sendFiveData(0);
                            if (isFive) {
                                uploadInter.sendParamDev(0, MonitorService.this.scriptFiveRunTime, 450);
                            } else {
                                uploadInter.sendParamDev(0, MonitorService.this.scriptInfo.getRunTime(), 450);
                            }
                        }
                    case 23:
                        MonitorService.this.startDevFive(10);
                        break;
                    case 24:
                        if (isFive) {
                            MonitorService.this.saveRecDataSys(MonitorService.this.scriptFiveRunTime, 1);
                        } else {
                            MonitorService.this.saveRecDataSys(MonitorService.this.scriptInfo.getRunTime(), 1);
                        }
                        break;
                    case 25:
                        checkParam = (int) ScriptWord.getInstance().getSysVar("CheckParam");
                        var8 = MonitorService.this.scriptInfo.getFactorList2().iterator();

                        while (true) {
                            if (!var8.hasNext()) {
                                break label402;
                            }

                            factorID = (Integer) var8.next();
                            faultState = false;
                            if (checkParam > 0) {
                                var11 = MonitorService.this.devServiceList.iterator();

                                while (var11.hasNext()) {
                                    devService = (DevService) var11.next();
                                    if (devService.getFactorID() == factorID) {
                                        faultState = devService.isFaultState();
                                        break;
                                    }
                                }
                            }

                            if (!faultState) {
                                switch (MonitorService.this.scriptInfo.getRunCmd()) {
                                    case 6:
                                        MonitorService.this.saveRecCheckPar(MonitorService.this.scriptInfo, factorID);
                                        MonitorService.this.saveRecParam(MonitorService.this.scriptInfo.getRunTime(), 456, factorID);
                                        break;
                                    case 7:
                                        DateTime dateTime = new DateTime(MonitorService.this.scriptInfo.getRunTime().getTime());
                                        dateTime = dateTime.plusHours(2);
                                        Timestamp recTime = new Timestamp(dateTime.getMillis());
                                        MonitorService.this.saveRecCheckRcvr(recTime, MonitorService.this.scriptInfo, factorID);
                                        MonitorService.this.saveRecParam(recTime, 457, factorID);
                                }
                            }
                        }
                    case 26:
                        MonitorService.this.resetDevState();
                        var8 = MonitorService.this.cfgFactorList.iterator();

                        while (true) {
                            if (!var8.hasNext()) {
                                break label402;
                            }

                            codFactor = (CfgFactor) var8.next();
                            codFactor.setUpload(true);
                        }
                    case 27:
                        break;
                    case 28:
                        MonitorService.this.startDevRange(1);
                        break;
                    case 29:
                        MonitorService.this.saveRecDataRange(MonitorService.this.scriptInfo.getRunTime());
                        break;
                    default:
                        if (strArray[0].length() > 2 && strArray[0].substring(0, 2).equals("缓存")) {
                            String factorName = strArray[0].substring(2, strArray[0].length());
                            var29 = MonitorService.this.cfgFactorList.iterator();

                            while (var29.hasNext()) {
                                cfgFactorx = (CfgFactor) var29.next();
                                if (cfgFactorx.getName().equals(factorName)) {
                                    if (isFive) {
                                        MonitorService.this.saveRecDataFiveFactor(MonitorService.this.scriptFiveRunTime, cfgFactorx.getId());
                                        MonitorService.this.saveRecDataFiveFactor(MonitorService.this.scriptFiveRunTime, 1, cfgFactorx.getId());
                                    } else {
                                        MonitorService.this.saveRecDataFiveFactor(MonitorService.this.scriptInfo.getRunTime(), cfgFactorx.getId());
                                        MonitorService.this.saveRecDataFiveFactor(MonitorService.this.scriptInfo.getRunTime(), 1, cfgFactorx.getId());
                                    }
                                    break;
                                }
                            }
                        }
                }

                MonitorService.this.lastState = strArray[0];
                Iterator var13 = MonitorService.this.cfgFactorList.iterator();

                while (var13.hasNext()) {
                    CfgFactor cfgFactorxx = (CfgFactor) var13.next();
                    if (strArray[0].equals("排除上传" + cfgFactorxx.getName())) {
                        cfgFactorxx.setUpload(false);
                    }
                }

            }
        };
    }

    private void initPlcCountListener() {
        this.scriptCountListener = new ScriptCountListener() {
            public void OnCountEvent(RtdCount requestCount) {
                MonitorService.this.webSocketSender.updateCount(requestCount);
            }
        };
    }

    private int initDev(CommInter commInter, int commChannel, CfgDev cfgDev) {
        int channel = commChannel;
        String var6 = cfgDev.getProtocol().toLowerCase();
        byte var7 = -1;
        switch (var6.hashCode()) {
            case -2127251592:
                if (var6.equals("usermodbustcp")) {
                    var7 = 26;
                }
                break;
            case -2082198667:
                if (var6.equals("samplegrean9320")) {
                    var7 = 37;
                }
                break;
            case -2082198636:
                if (var6.equals("samplegrean9330")) {
                    var7 = 35;
                }
                break;
            case -2082198635:
                if (var6.equals("samplegrean9331")) {
                    var7 = 36;
                }
                break;
            case -2078318048:
                if (var6.equals("smx2400")) {
                    var7 = 29;
                }
                break;
            case -2042998792:
                if (var6.equals("samplexqlsxw1b2")) {
                    var7 = 47;
                }
                break;
            case -2009301625:
                if (var6.equals("qualityshangyang")) {
                    var7 = 24;
                }
                break;
            case -1963522103:
                if (var6.equals("usermodbus")) {
                    var7 = 25;
                }
                break;
            case -1903914214:
                if (var6.equals("plcfanyimodbus")) {
                    var7 = 10;
                }
                break;
            case -1885088284:
                if (var6.equals("samplehengdazscxib")) {
                    var7 = 53;
                }
                break;
            case -1753512082:
                if (var6.equals("plcmodbustcptest")) {
                    var7 = 7;
                }
                break;
            case -1730413374:
                if (var6.equals("samplexqls9320")) {
                    var7 = 45;
                }
                break;
            case -1730413343:
                if (var6.equals("samplexqls9330")) {
                    var7 = 46;
                }
                break;
            case -1706090356:
                if (var6.equals("chlorophyll.devchlorophyllbbe")) {
                    var7 = 75;
                }
                break;
            case -1672930064:
                if (var6.equals("devgpsgn150")) {
                    var7 = 62;
                }
                break;
            case -1613389428:
                if (var6.equals("devflowott")) {
                    var7 = 63;
                }
                break;
            case -1590137317:
                if (var6.equals("envdoor")) {
                    var7 = 32;
                }
                break;
            case -1432453277:
                if (var6.equals("smsfourfaith")) {
                    var7 = 71;
                }
                break;
            case -1402313392:
                if (var6.equals("plcschneider")) {
                    var7 = 6;
                }
                break;
            case -1318629947:
                if (var6.equals("devflowlinkquest")) {
                    var7 = 66;
                }
                break;
            case -1309265235:
                if (var6.equals("adamdcon")) {
                    var7 = 28;
                }
                break;
            case -1236860458:
                if (var6.equals("samplewanweiwqs2000")) {
                    var7 = 44;
                }
                break;
            case -1157309819:
                if (var6.equals("plcfanyitcp")) {
                    var7 = 11;
                }
                break;
            case -991445694:
                if (var6.equals("devmodbusnblg")) {
                    var7 = 16;
                }
                break;
            case -914506645:
                if (var6.equals("displaylxy")) {
                    var7 = 57;
                }
                break;
            case -820074335:
                if (var6.equals("samplegrasp9320")) {
                    var7 = 43;
                }
                break;
            case -811385733:
                if (var6.equals("envpowert645")) {
                    var7 = 74;
                }
                break;
            case -758065862:
                if (var6.equals("devflowlinkquestshare")) {
                    var7 = 69;
                }
                break;
            case -659667555:
                if (var6.equals("devmodbusyiwen")) {
                    var7 = 17;
                }
                break;
            case -584632901:
                if (var6.equals("centrifugegrean")) {
                    var7 = 54;
                }
                break;
            case -519279441:
                if (var6.equals("envpowertk")) {
                    var7 = 73;
                }
                break;
            case -431765275:
                if (var6.equals("plcmodbus")) {
                    var7 = 0;
                }
                break;
            case -409729270:
                if (var6.equals("devgpsdk699d")) {
                    var7 = 60;
                }
                break;
            case -387897859:
                if (var6.equals("qualitymodbus")) {
                    var7 = 20;
                }
                break;
            case -287827711:
                if (var6.equals("devgpshs6601")) {
                    var7 = 61;
                }
                break;
            case -242410859:
                if (var6.equals("devflowlsshare")) {
                    var7 = 70;
                }
                break;
            case -182826222:
                if (var6.equals("devmetairmarnmea0183")) {
                    var7 = 58;
                }
                break;
            case -117484185:
                if (var6.equals("devdctf12")) {
                    var7 = 78;
                }
                break;
            case -52045035:
                if (var6.equals("devflowhz")) {
                    var7 = 67;
                }
                break;
            case -52044918:
                if (var6.equals("devflowls")) {
                    var7 = 68;
                }
                break;
            case -52044681:
                if (var6.equals("devflowth")) {
                    var7 = 65;
                }
                break;
            case 8743281:
                if (var6.equals("sampleplcswitch")) {
                    var7 = 41;
                }
                break;
            case 8910677:
                if (var6.equals("devflowhadcp")) {
                    var7 = 64;
                }
                break;
            case 116914136:
                if (var6.equals("devlinghu")) {
                    var7 = 81;
                }
                break;
            case 143834799:
                if (var6.equals("samplekeze")) {
                    var7 = 52;
                }
                break;
            case 150782099:
                if (var6.equals("devmodbus")) {
                    var7 = 15;
                }
                break;
            case 157769091:
                if (var6.equals("sampledr803")) {
                    var7 = 49;
                }
                break;
            case 160581193:
                if (var6.equals("samplegrasp")) {
                    var7 = 40;
                }
                break;
            case 160584477:
                if (var6.equals("samplegrean")) {
                    var7 = 34;
                }
                break;
            case 284698310:
                if (var6.equals("yangjian212")) {
                    var7 = 31;
                }
                break;
            case 332255864:
                if (var6.equals("devsystea")) {
                    var7 = 76;
                }
                break;
            case 351828887:
                if (var6.equals("plchostlinkjx")) {
                    var7 = 14;
                }
                break;
            case 437902606:
                if (var6.equals("five.devfiveysi2")) {
                    var7 = 83;
                }
                break;
            case 466736710:
                if (var6.equals("plcmodbus1200")) {
                    var7 = 9;
                }
                break;
            case 468618028:
                if (var6.equals("plcmodbusnsbd")) {
                    var7 = 8;
                }
                break;
            case 489936013:
                if (var6.equals("adammodbus")) {
                    var7 = 27;
                }
                break;
            case 511353962:
                if (var6.equals("plcweiqimodbus")) {
                    var7 = 12;
                }
                break;
            case 595874623:
                if (var6.equals("sampledr803b")) {
                    var7 = 38;
                }
                break;
            case 595874633:
                if (var6.equals("sampledr803l")) {
                    var7 = 50;
                }
                break;
            case 621069691:
                if (var6.equals("envpowerweiqi")) {
                    var7 = 82;
                }
                break;
            case 683049737:
                if (var6.equals("samplegrasp2")) {
                    var7 = 42;
                }
                break;
            case 700048541:
                if (var6.equals("samplehengda")) {
                    var7 = 51;
                }
                break;
            case 707858652:
                if (var6.equals("plcmodbustcp")) {
                    var7 = 2;
                }
                break;
            case 784565932:
                if (var6.equals("tox.devtoxbbe")) {
                    var7 = 72;
                }
                break;
            case 785098569:
                if (var6.equals("nh3n.devnh3nwtw")) {
                    var7 = 79;
                }
                break;
            case 860068308:
                if (var6.equals("qualitymodbus1")) {
                    var7 = 19;
                }
                break;
            case 860068310:
                if (var6.equals("qualitymodbus3")) {
                    var7 = 21;
                }
                break;
            case 860068311:
                if (var6.equals("qualitymodbus4")) {
                    var7 = 22;
                }
                break;
            case 1010475827:
                if (var6.equals("samplesb6000")) {
                    var7 = 48;
                }
                break;
            case 1036258265:
                if (var6.equals("devmodbusyuxing")) {
                    var7 = 18;
                }
                break;
            case 1101533599:
                if (var6.equals("plcshangyang")) {
                    var7 = 3;
                }
                break;
            case 1307726842:
                if (var6.equals("plcjuguang")) {
                    var7 = 5;
                }
                break;
            case 1368917896:
                if (var6.equals("devplatform")) {
                    var7 = 80;
                }
                break;
            case 1434253222:
                if (var6.equals("zetian212")) {
                    var7 = 30;
                }
                break;
            case 1470308909:
                if (var6.equals("devsysteanc")) {
                    var7 = 77;
                }
                break;
            case 1691746076:
                if (var6.equals("samplehengdazscviie")) {
                    var7 = 39;
                }
                break;
            case 1715116014:
                if (var6.equals("displaynova")) {
                    var7 = 56;
                }
                break;
            case 1891972740:
                if (var6.equals("qualitymodbusall")) {
                    var7 = 23;
                }
                break;
            case 1897853983:
                if (var6.equals("devmetvaisalawxt536")) {
                    var7 = 59;
                }
                break;
            case 1920283904:
                if (var6.equals("plcgrean")) {
                    var7 = 4;
                }
                break;
            case 1952455877:
                if (var6.equals("centrifugeacs")) {
                    var7 = 55;
                }
                break;
            case 1955232246:
                if (var6.equals("envpowersantak")) {
                    var7 = 33;
                }
                break;
            case 2007067785:
                if (var6.equals("plchostlink")) {
                    var7 = 1;
                }
                break;
            case 2089559241:
                if (var6.equals("plchostlink2")) {
                    var7 = 13;
                }
        }


        Iterator var37;
        Iterator var42;
        Iterator var48;
        CfgFactor cfgFactor;
        DevService devService = null;
        switch (var7) {
            case 0:
                this.scriptService.init(commInter, cfgDev, 1);
                this.scriptService.setStateListener(this.scriptStateListener);
                this.scriptService.setCountListener(this.scriptCountListener);
                break;
            case 1:
                this.scriptService.init(commInter, cfgDev, 2);
                this.scriptService.setStateListener(this.scriptStateListener);
                this.scriptService.setCountListener(this.scriptCountListener);
                break;
            case 2:
                this.scriptService.init(commInter, cfgDev, 3);
                this.scriptService.setStateListener(this.scriptStateListener);
                this.scriptService.setCountListener(this.scriptCountListener);
                break;
            case 3:
                this.scriptService.init(commInter, cfgDev, 4);
                this.scriptService.setStateListener(this.scriptStateListener);
                this.scriptService.setCountListener(this.scriptCountListener);
                break;
            case 4:
                this.scriptService.init(commInter, cfgDev, 5);
                this.scriptService.setStateListener(this.scriptStateListener);
                this.scriptService.setCountListener(this.scriptCountListener);
                break;
            case 5:
                this.scriptService.init(commInter, cfgDev, 6);
                this.scriptService.setStateListener(this.scriptStateListener);
                this.scriptService.setCountListener(this.scriptCountListener);
                break;
            case 6:
                this.scriptService.init(commInter, cfgDev, 7);
                this.scriptService.setStateListener(this.scriptStateListener);
                this.scriptService.setCountListener(this.scriptCountListener);
                break;
            case 7:
                this.scriptService.init(commInter, cfgDev, 8);
                this.scriptService.setStateListener(this.scriptStateListener);
                this.scriptService.setCountListener(this.scriptCountListener);
                break;
            case 8:
                this.scriptService.init(commInter, cfgDev, 9);
                this.scriptService.setStateListener(this.scriptStateListener);
                this.scriptService.setCountListener(this.scriptCountListener);
                break;
            case 9:
                this.scriptService.init(commInter, cfgDev, 10);
                this.scriptService.setStateListener(this.scriptStateListener);
                this.scriptService.setCountListener(this.scriptCountListener);
                break;
            case 10:
                this.scriptService.init(commInter, cfgDev, 11);
                this.scriptService.setStateListener(this.scriptStateListener);
                this.scriptService.setCountListener(this.scriptCountListener);
                break;
            case 11:
                this.scriptService.init(commInter, cfgDev, 12);
                this.scriptService.setStateListener(this.scriptStateListener);
                this.scriptService.setCountListener(this.scriptCountListener);
                break;
            case 12:
                this.scriptService.init(commInter, cfgDev, 13);
                this.scriptService.setStateListener(this.scriptStateListener);
                this.scriptService.setCountListener(this.scriptCountListener);
                break;
            case 13:
                this.scriptService.init(commInter, cfgDev, 14);
                this.scriptService.setStateListener(this.scriptStateListener);
                this.scriptService.setCountListener(this.scriptCountListener);
                break;
            case 14:
                this.scriptService.init(commInter, cfgDev, 15);
                this.scriptService.setStateListener(this.scriptStateListener);
                this.scriptService.setCountListener(this.scriptCountListener);
                break;
            case 15:
            case 16:
                var37 = cfgDev.getFactorList().iterator();

                while (var37.hasNext()) {
                    cfgFactor = (CfgFactor) var37.next();
                    devService = new DevModbus();
                    if (cfgFactor.isFiveType()) {
                        devService.init(commInter, cfgDev, cfgFactor, this.cfgRegFiveList, channel, this.dev_cmd_time);
                        commInter.setSerialQuery(true);
                    } else if (cfgDev.getDevRegList() != null) {
                        devService.init(commInter, cfgDev, cfgFactor, cfgDev.getDevRegList(), channel, this.dev_cmd_time);
                    } else {
                        devService.init(commInter, cfgDev, cfgFactor, this.cfgRegModbusList, channel, this.dev_cmd_time);
                    }

                    channel = (channel + 1) % 8;
                    this.devServiceList.add(devService);
                    if (!commInter.isSerialQuery()) {
                        devService.startQuery();
                    }

                    try {
                        Thread.sleep(100L);
                    } catch (Exception var23) {
                    }
                }

                return channel;
            case 17:
                var37 = cfgDev.getFactorList().iterator();

                while (var37.hasNext()) {
                    cfgFactor = (CfgFactor) var37.next();
                    devService = new DevModbusYiwen();
                    if (cfgFactor.isFiveType()) {
                        devService.init(commInter, cfgDev, cfgFactor, this.cfgRegFiveList, channel, this.dev_cmd_time);
                        commInter.setSerialQuery(true);
                    } else if (cfgDev.getDevRegList() != null) {
                        devService.init(commInter, cfgDev, cfgFactor, cfgDev.getDevRegList(), channel, this.dev_cmd_time);
                    } else {
                        devService.init(commInter, cfgDev, cfgFactor, this.cfgRegYiwenList, channel, this.dev_cmd_time);
                    }

                    channel = (channel + 1) % 8;
                    this.devServiceList.add(devService);
                    if (!commInter.isSerialQuery()) {
                        devService.startQuery();
                    }

                    try {
                        Thread.sleep(100L);
                    } catch (Exception var22) {
                    }
                }

                return channel;
            case 18:
                var37 = cfgDev.getFactorList().iterator();

                while (var37.hasNext()) {
                    cfgFactor = (CfgFactor) var37.next();
                    devService = new DevModbusYuxing();
                    if (cfgFactor.isFiveType()) {
                        devService.init(commInter, cfgDev, cfgFactor, this.cfgRegFiveList, channel, this.dev_cmd_time);
                        commInter.setSerialQuery(true);
                    } else if (cfgDev.getDevRegList() != null) {
                        devService.init(commInter, cfgDev, cfgFactor, cfgDev.getDevRegList(), channel, this.dev_cmd_time);
                    } else {
                        devService.init(commInter, cfgDev, cfgFactor, this.cfgRegYuxingList, channel, this.dev_cmd_time);
                    }

                    channel = (channel + 1) % 8;
                    this.devServiceList.add(devService);
                    if (!commInter.isSerialQuery()) {
                        devService.startQuery();
                    }

                    try {
                        Thread.sleep(100L);
                    } catch (Exception var21) {
                    }
                }

                return channel;
            case 19:
                DevService devService1 = new QualityModbus1();
                devService1.init(commInter, cfgDev, (CfgFactor) null, this.cfgRegQualityList, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devService1);
                devService1.startQuery();
                break;
            case 20:
                devService = new QualityModbus();
                devService.init(commInter, cfgDev, (CfgFactor) null, this.cfgRegQualityList, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devService);
                devService.startQuery();
                break;
            case 21:
                DevService devService3 = new QualityModbus3();
                devService3.init(commInter, cfgDev, (CfgFactor) null, this.cfgRegQualityList3, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devService3);
                devService3.startQuery();
                break;
            case 22:
                DevService devService4 = new QualityModbus4();
                devService4.init(commInter, cfgDev, (CfgFactor) null, this.cfgRegQualityList4, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devService4);
                devService4.startQuery();
                break;
            case 23:
                DevService devServiceAll = new QualityModbusAll();
                devServiceAll.init(commInter, cfgDev, (CfgFactor) null, this.cfgRegQualityList, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devServiceAll);
                break;
            case 24:
                DevService devServiceSY = new QualityShangyang();
                devServiceSY.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devServiceSY);
                devServiceSY.startQuery();
                break;
            case 25:
                var37 = cfgDev.getFactorList().iterator();

                while (var37.hasNext()) {
                    cfgFactor = (CfgFactor) var37.next();
                    DevService devServiceUM = new UserModbus();
                    devServiceUM.init(commInter, cfgDev, cfgFactor, this.cfgRegModbusList, channel, this.dev_cmd_time);
                    channel = (channel + 1) % 8;
                    this.devServiceList.add(devServiceUM);
                }

                commInter.setSerialQuery(true);
                break;
            case 26:
                var37 = cfgDev.getFactorList().iterator();

                while (var37.hasNext()) {
                    cfgFactor = (CfgFactor) var37.next();
                    DevService devServiceTCP = new UserModbusTcp();
                    devServiceTCP.init(commInter, cfgDev, cfgFactor, this.cfgRegModbusList, channel, this.dev_cmd_time);
                    channel = (channel + 1) % 8;
                    this.devServiceList.add(devServiceTCP);
                }

                commInter.setSerialQuery(true);
                break;
            case 27:
                var37 = cfgDev.getFactorList().iterator();

                while (var37.hasNext()) {
                    cfgFactor = (CfgFactor) var37.next();
                    DevService devServiceAM = new AdamModbus();
                    if (cfgFactor.isFiveType()) {
                        devServiceAM.init(commInter, cfgDev, cfgFactor, this.cfgRegFiveList, channel, this.dev_cmd_time);
                    } else {
                        devServiceAM.init(commInter, cfgDev, cfgFactor, this.cfgRegModbusList, channel, this.dev_cmd_time);
                    }

                    channel = (channel + 1) % 8;
                    this.devServiceList.add(devServiceAM);
                }

                commInter.setSerialQuery(true);
                break;
            case 28:
                var37 = cfgDev.getFactorList().iterator();

                while (var37.hasNext()) {
                    cfgFactor = (CfgFactor) var37.next();
                    DevService devServiceAD = new AdamDcon();
                    if (cfgFactor.isFiveType()) {
                        devServiceAD.init(commInter, cfgDev, cfgFactor, this.cfgRegFiveList, channel, this.dev_cmd_time);
                    } else {
                        devServiceAD.init(commInter, cfgDev, cfgFactor, this.cfgRegModbusList, channel, this.dev_cmd_time);
                    }

                    channel = (channel + 1) % 8;
                    this.devServiceList.add(devServiceAD);
                }

                commInter.setSerialQuery(true);
                break;
            case 29:
                var37 = cfgDev.getFactorList().iterator();

                while (var37.hasNext()) {
                    cfgFactor = (CfgFactor) var37.next();
                    DevService devServiceAdam = new Adam2400();
                    if (cfgFactor.isFiveType()) {
                        devServiceAdam.init(commInter, cfgDev, cfgFactor, this.cfgRegFiveList, channel, this.dev_cmd_time);
                    } else {
                        devServiceAdam.init(commInter, cfgDev, cfgFactor, this.cfgRegModbusList, channel, this.dev_cmd_time);
                    }

                    channel = (channel + 1) % 8;
                    this.devServiceList.add(devServiceAdam);
                }

                commInter.setSerialQuery(true);
                break;
            case 30:
                var37 = cfgDev.getFactorList().iterator();

                while (var37.hasNext()) {
                    cfgFactor = (CfgFactor) var37.next();
                    DevService devServiceDZ = new DevZetian212();
                    devServiceDZ.init(commInter, cfgDev, cfgFactor, this.cfgRegModbusList, channel, this.dev_cmd_time);
                    channel = (channel + 1) % 8;
                    this.devServiceList.add(devServiceDZ);
                    if (!commInter.isSerialQuery()) {
                        devServiceDZ.startQuery();
                    }

                    try {
                        Thread.sleep(100L);
                    } catch (Exception var20) {
                    }
                }

                return channel;
            case 31:
                var37 = cfgDev.getFactorList().iterator();

                while (var37.hasNext()) {
                    cfgFactor = (CfgFactor) var37.next();
                    DevService devServiceDYJ = new DevYangjian212();
                    devServiceDYJ.init(commInter, cfgDev, cfgFactor, this.cfgRegModbusList, channel, this.dev_cmd_time);
                    channel = (channel + 1) % 8;
                    this.devServiceList.add(devServiceDYJ);
                    if (!commInter.isSerialQuery()) {
                        devServiceDYJ.startQuery();
                    }

                    try {
                        Thread.sleep(100L);
                    } catch (Exception var19) {
                    }
                }

                return channel;
            case 32:
                this.doorService = new EnvDoor();
                this.doorService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.doorService);
                this.doorService.startQuery();
                break;
            case 33:
                var37 = cfgDev.getFactorList().iterator();

                while (var37.hasNext()) {
                    cfgFactor = (CfgFactor) var37.next();
                    this.envPowerSantak = new EnvPowerSantak();
                    this.envPowerSantak.init(commInter, cfgDev, cfgFactor, this.cfgRegModbusList, channel, this.dev_cmd_time);
                    channel = (channel + 1) % 8;
                    this.devServiceList.add(this.envPowerSantak);
                    this.envPowerSantak.startQuery();
                }

                return channel;
            case 34:
                this.sampleService = new SampleGrean();
                this.sampleService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.sampleService);
                this.sampleServiceList.add(this.sampleService);
                this.sampleService.setCfgSample(this.cfgSample);
                this.sampleService.startQuery();
                break;
            case 35:
                this.sampleService = new SampleGrean9330();
                this.sampleService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.sampleService);
                this.sampleServiceList.add(this.sampleService);
                this.sampleService.setCfgSample(this.cfgSample);
                this.sampleService.startQuery();
                break;
            case 36:
                this.sampleService = new SampleGrean9331();
                this.sampleService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.sampleService);
                this.sampleServiceList.add(this.sampleService);
                this.sampleService.setCfgSample(this.cfgSample);
                this.sampleService.startQuery();
                break;
            case 37:
                this.sampleService = new SampleGrean9320();
                this.sampleService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.sampleService);
                this.sampleServiceList.add(this.sampleService);
                this.sampleService.setCfgSample(this.cfgSample);
                this.sampleService.startQuery();
                break;
            case 38:
                this.sampleService = new SampleDR803B();
                this.sampleService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.sampleService);
                this.sampleServiceList.add(this.sampleService);
                this.sampleService.setCfgSample(this.cfgSample);
                this.sampleService.startQuery();
                break;
            case 39:
                this.sampleService = new SampleHengdaZSCVIIE();
                this.sampleService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.sampleService);
                this.sampleServiceList.add(this.sampleService);
                this.sampleService.setCfgSample(this.cfgSample);
                this.sampleService.startQuery();
                break;
            case 40:
                this.sampleService = new SampleGrasp();
                this.sampleService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.sampleService);
                this.sampleServiceList.add(this.sampleService);
                this.sampleService.setCfgSample(this.cfgSample);
                this.sampleService.startQuery();
                break;
            case 41:
                this.sampleService = new SamplePlcSwitch();
                this.sampleService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.sampleService);
                this.sampleServiceList.add(this.sampleService);
                this.sampleService.setCfgSample(this.cfgSample);
                this.sampleService.setScriptService(this.scriptService);
                this.sampleService.startQuery();
                break;
            case 42:
                this.sampleService = new SampleGrasp2();
                this.sampleService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.sampleService);
                this.sampleServiceList.add(this.sampleService);
                this.sampleService.setCfgSample(this.cfgSample);
                this.sampleService.startQuery();
                break;
            case 43:
                this.sampleService = new SampleGrasp9320();
                this.sampleService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.sampleService);
                this.sampleServiceList.add(this.sampleService);
                this.sampleService.setCfgSample(this.cfgSample);
                this.sampleService.startQuery();
                break;
            case 44:
                this.sampleService = new SampleWanweiWQS2000();
                this.sampleService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.sampleService);
                this.sampleServiceList.add(this.sampleService);
                this.sampleService.setCfgSample(this.cfgSample);
                this.sampleService.startQuery();
                break;
            case 45:
                this.sampleService = new SampleXqls9320();
                this.sampleService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.sampleService);
                this.sampleServiceList.add(this.sampleService);
                this.sampleService.setCfgSample(this.cfgSample);
                this.sampleService.startQuery();
                break;
            case 46:
                this.sampleService = new SampleXqls9330();
                this.sampleService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.sampleService);
                this.sampleServiceList.add(this.sampleService);
                this.sampleService.setCfgSample(this.cfgSample);
                this.sampleService.startQuery();
                break;
            case 47:
                this.sampleService = new SampleXqlsXW1B2();
                this.sampleService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.sampleService);
                this.sampleServiceList.add(this.sampleService);
                this.sampleService.setCfgSample(this.cfgSample);
                this.sampleService.startQuery();
                break;
            case 48:
                this.sampleService = new SampleKeshengSB6000();
                this.sampleService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.sampleService);
                this.sampleServiceList.add(this.sampleService);
                this.sampleService.setCfgSample(this.cfgSample);
                this.sampleService.startQuery();
                break;
            case 49:
                this.sampleService = new SampleDR803();
                this.sampleService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.sampleService);
                this.sampleServiceList.add(this.sampleService);
                this.sampleService.setCfgSample(this.cfgSample);
                this.sampleService.startQuery();
                break;
            case 50:
                this.sampleService = new SampleDR803L();
                this.sampleService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.sampleService);
                this.sampleServiceList.add(this.sampleService);
                this.sampleService.setCfgSample(this.cfgSample);
                this.sampleService.startQuery();
                break;
            case 51:
                this.sampleService = new SampleHengda();
                this.sampleService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.sampleService);
                this.sampleServiceList.add(this.sampleService);
                this.sampleService.setCfgSample(this.cfgSample);
                this.sampleService.startQuery();
                break;
            case 52:
                this.sampleService = new SampleKeze();
                this.sampleService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.sampleService);
                this.sampleServiceList.add(this.sampleService);
                this.sampleService.setCfgSample(this.cfgSample);
                this.sampleService.startQuery();
                break;
            case 53:
                this.sampleService = new SampleHengdaZSCXIB();
                this.sampleService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.sampleService);
                this.sampleServiceList.add(this.sampleService);
                this.sampleService.setCfgSample(this.cfgSample);
                this.sampleService.startQuery();
                break;
            case 54:
                var37 = cfgDev.getFactorList().iterator();

                while (var37.hasNext()) {
                    cfgFactor = (CfgFactor) var37.next();
                    this.centrifugeService = new CentrifugeGrean();
                    this.centrifugeService.init(commInter, cfgDev, cfgFactor, this.cfgRegModbusList, channel, this.dev_cmd_time);
                    channel = (channel + 1) % 8;
                    this.devServiceList.add(this.centrifugeService);
                    this.centrifugeService.startQuery();
                }

                return channel;
            case 55:
                var37 = cfgDev.getFactorList().iterator();

                while (var37.hasNext()) {
                    cfgFactor = (CfgFactor) var37.next();
                    this.centrifugeService = new CentrifugeAcs();
                    this.centrifugeService.init(commInter, cfgDev, cfgFactor, this.cfgRegModbusList, channel, this.dev_cmd_time);
                    channel = (channel + 1) % 8;
                    this.devServiceList.add(this.centrifugeService);
                    this.centrifugeService.startQuery();
                }

                return channel;
            case 56:
                this.displayService = new DisplayNova();
                this.displayService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.displayService);
                this.displayService.setMonitorService(this);
                this.displayService.startQuery();
                break;
            case 57:
                this.displayService = new DisplayLxy();
                this.displayService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(this.displayService);
                this.displayService.setMonitorService(this);
                this.displayService.startQuery();
                break;
            case 58:
                DevService devServiceDMAM = new DevMetAirMarNMEA0183();
                devServiceDMAM.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devServiceDMAM);
                devServiceDMAM.startQuery();
                break;
            case 59:
                DevService devServiceDMVLA = new DevMetVaisalaWXT536();
                devServiceDMVLA.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devServiceDMVLA);
                devServiceDMVLA.startQuery();
                break;
            case 60:
                DevService devServiceDGDK = new DevGpsDK699D();
                devServiceDGDK.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devServiceDGDK);
                devServiceDGDK.startQuery();
                break;
            case 61:
                DevService devServiceDEG = new DevGpsHS6601();
                devServiceDEG.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devServiceDEG);
                devServiceDEG.startQuery();
                break;
            case 62:
                devService = new DevGpsGN150();
                devService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devService);
                devService.startQuery();
                break;
            case 63:
                devService = new DevFlowOtt();
                devService.init(commInter, cfgDev, (CfgFactor) null, this.cfgRegModbusList, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devService);
                devService.startQuery();
                break;
            case 64:
                devService = new DevFlowHadcp();
                devService.init(commInter, cfgDev, (CfgFactor) null, this.cfgRegModbusList, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devService);
                devService.startQuery();
                break;
            case 65:
                DevFlowTh devFlowTh = new DevFlowTh();
                devFlowTh.setThConfig(new ThConfig(this.http_username, this.http_password, this.http_placeid, this.http_equipid));
                devFlowTh.init(commInter, cfgDev, (CfgFactor) null, this.cfgRegModbusList, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devFlowTh);
                break;
            case 66:
                devService = new DevFlowLinkQuest();
                devService.init(commInter, cfgDev, (CfgFactor) null, this.cfgRegModbusList, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devService);
                devService.startQuery();
                break;
            case 67:
                devService = new DevFlowHz();
                devService.init(commInter, cfgDev, (CfgFactor) null, this.cfgRegModbusList, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devService);
                devService.startQuery();
                break;
            case 68:
                devService = new DevFlowLs();
                devService.init(commInter, cfgDev, (CfgFactor) null, this.cfgRegFlowList, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devService);
                devService.startQuery();
                break;
            case 69:
                devService = new DevFlowLinkQuestShare();
                devService.init(commInter, cfgDev, (CfgFactor) null, this.cfgRegModbusList, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devService);
                devService.startQuery();
                break;
            case 70:
                devService = new DevFlowLsShare();
                devService.init(commInter, cfgDev, (CfgFactor) null, this.cfgRegFlowList, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devService);
                devService.startQuery();
                break;
            case 71:
                this.smsService = new SmsFourFaith();
                this.smsService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                this.devServiceList.add(this.smsService);
                this.smsService.startQuery();
                break;
            case 72:
                devService = new DevToxBbe();
                devService.init(commInter, cfgDev, (CfgFactor) null, this.cfgRegFiveList, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devService);
                devService.startQuery();
                break;
            case 73:
                devService = new EnvPowerTk();
                devService.init(commInter, cfgDev, (CfgFactor) null, this.cfgRegModbusList, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devService);
                devService.startQuery();
                break;
            case 74:
                var42 = cfgDev.getFactorList().iterator();

                while (var42.hasNext()) {
                    cfgFactor = (CfgFactor) var42.next();
                    devService = new EnvPowerT645();
                    devService.init(commInter, cfgDev, cfgFactor, this.cfgRegModbusList, channel, this.dev_cmd_time);
                    channel = (channel + 1) % 8;
                    this.devServiceList.add(devService);
                    devService.startQuery();
                }

                return channel;
            case 75:
                var42 = cfgDev.getFactorList().iterator();
                if (var42.hasNext()) {
                    cfgFactor = (CfgFactor) var42.next();
                    devService = new DevChlorophyllBbe();
                    devService.init(commInter, cfgDev, cfgFactor, this.cfgRegModbusList, commChannel, this.dev_cmd_time);
                    channel = (commChannel + 1) % 8;
                    this.devServiceList.add(devService);
                }

                commInter.setSerialQuery(true);
                break;
            case 76:
                devService = new DevSystea();
                cfgFactor = cfgDev.getFactorList().size() > 0 ? (CfgFactor) cfgDev.getFactorList().get(0) : null;
                devService.init(commInter, cfgDev, cfgFactor, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devService);
                devService.startQuery();
                break;
            case 77:
                devService = new DevSysteaNc();
                cfgFactor = cfgDev.getFactorList().size() > 0 ? (CfgFactor) cfgDev.getFactorList().get(0) : null;
                devService.init(commInter, cfgDev, cfgFactor, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devService);
                devService.startQuery();
                break;
            case 78:
                var48 = cfgDev.getFactorList().iterator();

                while (var48.hasNext()) {
                    cfgFactor = (CfgFactor) var48.next();
                    devService = new DevDctF12();
                    devService.init(commInter, cfgDev, cfgFactor, this.cfgRegModbusList, channel, this.dev_cmd_time);
                    channel = (channel + 1) % 8;
                    this.devServiceList.add(devService);
                    devService.startQuery();
                }

                return channel;
            case 79:
                var48 = cfgDev.getFactorList().iterator();
                if (var48.hasNext()) {
                    cfgFactor = (CfgFactor) var48.next();
                    devService = new DevNh3nWtw();
                    devService.init(commInter, cfgDev, cfgFactor, this.cfgRegModbusList, commChannel, this.dev_cmd_time);
                    channel = (commChannel + 1) % 8;
                    this.devServiceList.add(devService);
                    devService.startQuery();
                    this.wtwService = devService;
                }
                break;
            case 80:
                DevPlatform devPlatform = new DevPlatform();
                devPlatform.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                devPlatform.setPlatformUrl(this.platformUrl);
                devPlatform.setPlatformStcd(this.platformStcd);
                devPlatform.setPlatformToken(this.platformToken);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devPlatform);
                devPlatform.startQuery();
                break;
            case 81:
                DevLinghu devLinghu = new DevLinghu();
                devLinghu.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                devLinghu.setPlatformUrl(this.linghuUrl);
                devLinghu.setPlatformStcd(this.linghuStcd);
                devLinghu.setPlatformToken(this.linghuToken);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devLinghu);
                devLinghu.startQuery();
                break;
            case 82:
                devService = new EnvPowerWeiqi();
                devService.init(commInter, cfgDev, (CfgFactor) null, (List) null, commChannel, this.dev_cmd_time);
                channel = (commChannel + 1) % 8;
                this.devServiceList.add(devService);
                devService.startQuery();
                break;
            case 83:
                Iterator var13 = cfgDev.getFactorList().iterator();

                while (var13.hasNext()) {
                    cfgFactor = (CfgFactor) var13.next();
                    devService = new DevFiveYsi2();
                    if (cfgFactor.isFiveType()) {
                        devService.init(commInter, cfgDev, cfgFactor, this.cfgRegFiveList, channel, this.dev_cmd_time);
                    } else {
                        devService.init(commInter, cfgDev, cfgFactor, this.cfgRegModbusList, channel, this.dev_cmd_time);
                    }

                    channel = (channel + 1) % 8;
                    this.devServiceList.add(devService);
                }

                commInter.setSerialQuery(true);
                break;
            default:
                try {
                    String pkgName = DevService.class.getPackage().getName();
                    Class clazz = Class.forName(pkgName + "." + cfgDev.getProtocol());
                    Iterator var15 = cfgDev.getFactorList().iterator();

                    while (var15.hasNext()) {
                        cfgFactor = (CfgFactor) var15.next();
                        Constructor c = clazz.getConstructor();
                        Object service = c.newInstance();
                        devService = (DevService) service;
                        if (cfgFactor.isFiveType()) {
                            devService.init(commInter, cfgDev, cfgFactor, this.cfgRegFiveList, channel, this.dev_cmd_time);
                            commInter.setSerialQuery(true);
                        } else {
                            devService.init(commInter, cfgDev, cfgFactor, this.cfgRegModbusList, channel, this.dev_cmd_time);
                        }

                        channel = (channel + 1) % 8;
                        this.devServiceList.add(devService);
                        if (!commInter.isSerialQuery()) {
                            devService.startQuery();
                            Thread.sleep(100L);
                        }
                    }
                } catch (Exception var24) {
                }
        }

        return channel;
    }

    private void initFileDev() {
        Iterator var2 = this.cfgDevList.iterator();

        while (true) {
            while (var2.hasNext()) {
                CfgDev cfgDev = (CfgDev) var2.next();
                String var4 = cfgDev.getProtocol();
                byte var5 = -1;
                switch (var4.hashCode()) {
                    case 104474979:
                        if (var4.equals("DevVocModernWater")) {
                            var5 = 0;
                        }
                }

                switch (var5) {
                    case 0:
                        Iterator var6 = cfgDev.getFactorList().iterator();

                        while (var6.hasNext()) {
                            CfgFactor cfgFactor = (CfgFactor) var6.next();
                            DevService devService = new DevVocModernWater();
                            devService.init((CommInter) null, cfgDev, cfgFactor, this.cfgRegModbusList, 0, this.dev_cmd_time);
                            this.devServiceList.add(devService);
                            devService.startRead();

                            try {
                                Thread.sleep(500L);
                            } catch (Exception var9) {
                            }
                        }
                }
            }

            return;
        }
    }

    private void initComDev() {
        this.saveRecLogSys("系统日志", "载入串口设备");
        Iterator var2 = this.cfgSerialList.iterator();

        while (true) {
            CfgSerial cfgSerial;
            CommSerial commInter;
            do {
                do {
                    do {
                        if (!var2.hasNext()) {
                            if (!Strings.isNullOrEmpty(this.flow_share)) {
                                DevService flowShare = null;
                                Iterator var11 = this.devServiceList.iterator();

                                DevService devService;
                                while (var11.hasNext()) {
                                    devService = (DevService) var11.next();
                                    if (devService.getDevName().equalsIgnoreCase(this.flow_share)) {
                                        flowShare = devService;
                                    }
                                }

                                if (flowShare != null) {
                                    var11 = this.devServiceList.iterator();

                                    while (var11.hasNext()) {
                                        devService = (DevService) var11.next();
                                        if (devService.getDevName().contains("流量计") && !devService.getDevName().equals(this.flow_share)) {
                                            FlowService flowService = (FlowService) devService;
                                            flowService.setFlowShare((FlowService) flowShare);
                                        }
                                    }
                                }
                            }

                            return;
                        }

                        cfgSerial = (CfgSerial) var2.next();
                    } while (!cfgSerial.isUsed());
                } while (cfgSerial.getDevList().size() <= 0);

                commInter = (new Builder(cfgSerial.getPort())).baudRate(cfgSerial.getBaudRate()).dataBits(cfgSerial.getDataBits()).stopBits(cfgSerial.getStopBits()).parity(cfgSerial.getParity()).flowControl(cfgSerial.getFlowControl()).build();
            } while (!commInter.Open());

            commInter.setSerialQuery(cfgSerial.isCmdType());
            commInter.setSerialDelay(cfgSerial.getCmdDelay());
            this.commInterList.add(commInter);
            int channel = 0;
            Iterator var5 = cfgSerial.getDevList().iterator();

            while (var5.hasNext()) {
                CfgDev cfgDev = (CfgDev) var5.next();
                int iTmp = this.initDev(commInter, channel, cfgDev);
                channel = (channel + iTmp) % 8;

                try {
                    Thread.sleep(300L);
                } catch (Exception var9) {
                }
            }

            if (commInter.isSerialQuery()) {
                MonitorService.DevSerialQuery devSerialQuery = new MonitorService.DevSerialQuery(commInter);
                this.serialQueryList.add(devSerialQuery);
                devSerialQuery.start();
            }
        }
    }

    private void initNetDev() {
        this.saveRecLogSys("系统日志", "载入网络设备");
        Iterator var1 = this.cfgNetClientList.iterator();

        while (true) {
            CfgNetClient cfgNetClient;
            do {
                do {
                    if (!var1.hasNext()) {
                        return;
                    }

                    cfgNetClient = (CfgNetClient) var1.next();
                } while (!cfgNetClient.isUsed());
            } while (cfgNetClient.getDevList().size() <= 0);

            CommInter commInter = null;
            Iterator var4 = cfgNetClient.getDevList().iterator();

            while (var4.hasNext()) {
                CfgDev cfgDev;
                label123:
                {
                    cfgDev = (CfgDev) var4.next();
                    String var6 = cfgDev.getProtocol().toLowerCase();
                    byte var7 = -1;
                    switch (var6.hashCode()) {
                        case -2127251592:
                            if (var6.equals("usermodbustcp")) {
                                var7 = 15;
                            }
                            break;
                        case -2082198636:
                            if (var6.equals("samplegrean9330")) {
                                var7 = 10;
                            }
                            break;
                        case -2082198635:
                            if (var6.equals("samplegrean9331")) {
                                var7 = 11;
                            }
                            break;
                        case -1872140428:
                            if (var6.equals("five.devfivewtwmodbustcp")) {
                                var7 = 14;
                            }
                            break;
                        case -1753512082:
                            if (var6.equals("plcmodbustcptest")) {
                                var7 = 4;
                            }
                            break;
                        case -1590137317:
                            if (var6.equals("envdoor")) {
                                var7 = 9;
                            }
                            break;
                        case -1429957905:
                            if (var6.equals("heavy.devheavyova7000nc")) {
                                var7 = 8;
                            }
                            break;
                        case -1379020755:
                            if (var6.equals("heavy.devheavyova7000old")) {
                                var7 = 7;
                            }
                            break;
                        case -1157309819:
                            if (var6.equals("plcfanyitcp")) {
                                var7 = 5;
                            }
                            break;
                        case -914506645:
                            if (var6.equals("displaylxy")) {
                                var7 = 13;
                            }
                            break;
                        case 707858652:
                            if (var6.equals("plcmodbustcp")) {
                                var7 = 3;
                            }
                            break;
                        case 784565932:
                            if (var6.equals("tox.devtoxbbe")) {
                                var7 = 2;
                            }
                            break;
                        case 1434253222:
                            if (var6.equals("zetian212")) {
                                var7 = 1;
                            }
                            break;
                        case 1691746076:
                            if (var6.equals("samplehengdazscviie")) {
                                var7 = 12;
                            }
                            break;
                        case 1715116014:
                            if (var6.equals("displaynova")) {
                                var7 = 0;
                            }
                            break;
                        case 1991805882:
                            if (var6.equals("heavy.devheavyova7000")) {
                                var7 = 6;
                            }
                    }

                    switch (var7) {
                        case 0:
                        case 1:
                        case 2:
                            commInter = new CommNetClientLine(cfgNetClient.getIp(), cfgNetClient.getPort());
                            ((CommInter) commInter).setSerialQuery(cfgNetClient.isCmdType());
                            ((CommInter) commInter).setSerialDelay(cfgNetClient.getCmdDelay());
                            ((CommInter) commInter).Open();
                            this.commInterList.add(commInter);
                            break label123;
                        case 3:
                        case 4:
                        case 5:
                            commInter = new CommNetClientModbusTcp(cfgNetClient.getIp(), cfgNetClient.getPort(), cfgDev.getAddress().intValue());
                            ((CommInter) commInter).setSerialQuery(cfgNetClient.isCmdType());
                            ((CommInter) commInter).setSerialDelay(cfgNetClient.getCmdDelay());
                            ((CommInter) commInter).Open();
                            this.commInterList.add(commInter);
                            break label123;
                        case 6:
                        case 7:
                        case 8:
                            commInter = new CommNetClientModbusTcp(cfgNetClient.getIp(), cfgNetClient.getPort(), cfgDev.getAddress().intValue());
                            ((CommInter) commInter).setSerialQuery(cfgNetClient.isCmdType());
                            ((CommInter) commInter).setSerialDelay(cfgNetClient.getCmdDelay());
                            ((CommInter) commInter).Open();
                            ((CommInter) commInter).setSerialQuery(true);
                            this.commInterList.add(commInter);
                            break label123;
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                            commInter = new CommNetClientSerial(cfgNetClient.getIp(), cfgNetClient.getPort(), cfgDev.getAddress().intValue());
                            ((CommInter) commInter).setSerialQuery(cfgNetClient.isCmdType());
                            ((CommInter) commInter).setSerialDelay(cfgNetClient.getCmdDelay());
                            ((CommInter) commInter).Open();
                            this.commInterList.add(commInter);
                            break label123;
                        case 14:
                            commInter = new CommNetClientModbusTcp(cfgNetClient.getIp(), cfgNetClient.getPort(), cfgDev.getAddress().intValue());
                            ((CommInter) commInter).setSerialQuery(cfgNetClient.isCmdType());
                            ((CommInter) commInter).setSerialDelay(cfgNetClient.getCmdDelay());
                            ((CommInter) commInter).Open();
                            ((CommInter) commInter).setSerialQuery(true);
                            this.commInterList.add(commInter);
                            break label123;
                        case 15:
                            commInter = new CommNetClientModbusTcp(cfgNetClient.getIp(), cfgNetClient.getPort(), cfgDev.getAddress().intValue());
                            ((CommInter) commInter).setSerialQuery(cfgNetClient.isCmdType());
                            ((CommInter) commInter).setSerialDelay(cfgNetClient.getCmdDelay());
                            ((CommInter) commInter).Open();
                            this.commInterList.add(commInter);
                            break label123;
                    }

                    if (cfgDev.getType().equalsIgnoreCase("five") && cfgDev.getProtocol().equalsIgnoreCase("devmodbus")) {
                        commInter = new CommNetClientModbus(cfgNetClient.getIp(), cfgNetClient.getPort(), -1);
                    } else {
                        commInter = new CommNetClientModbus(cfgNetClient.getIp(), cfgNetClient.getPort(), cfgDev.getAddress().intValue());
                    }

                    ((CommInter) commInter).setSerialQuery(cfgNetClient.isCmdType());
                    ((CommInter) commInter).setSerialDelay(cfgNetClient.getCmdDelay());
                    ((CommInter) commInter).Open();
                    this.commInterList.add(commInter);
                }

                this.initDev((CommInter) commInter, 0, cfgDev);

                try {
                    Thread.sleep(300L);
                } catch (Exception var8) {
                }
            }

            if (commInter != null && ((CommInter) commInter).isSerialQuery()) {
                MonitorService.DevSerialQuery devSerialQuery = new MonitorService.DevSerialQuery((CommInter) commInter);
                this.serialQueryList.add(devSerialQuery);
                devSerialQuery.start();
            }
        }
    }

    private void initUpload() {
        this.saveRecLogSys("系统日志", "启动上传服务");
        this.commUploadList.clear();
        Iterator var1 = this.cfgUploadNetList.iterator();

        while (true) {
            CfgUploadNet cfgUploadNet;
            do {
                do {
                    do {
                        if (!var1.hasNext()) {
                            var1 = this.cfgUploadComList.iterator();

                            while (true) {
                                CfgUploadCom cfgUploadCom;
                                do {
                                    do {
                                        do {
                                            if (!var1.hasNext()) {
                                                this.initFtp();
                                                this.initDtu();
                                                return;
                                            }

                                            cfgUploadCom = (CfgUploadCom) var1.next();
                                        } while (!cfgUploadCom.isUsed());
                                    } while (cfgUploadCom.getProtocol().toUpperCase().equals("JSFTP"));
                                } while (cfgUploadCom.getProtocol().toUpperCase().equals("QDFTP"));

                                CfgSerial cfgSerial = this.getCfgSerial(cfgUploadCom);
                                CommInter commInter = null;
                                if (!cfgUploadCom.getProtocol().toUpperCase().equals("MODBUS") && !cfgUploadCom.getProtocol().toUpperCase().equals("NBUCMA") && !cfgUploadCom.getProtocol().toUpperCase().equals("SZY206") && !cfgUploadCom.getProtocol().toUpperCase().equals("SZY206SH") && !cfgUploadCom.getProtocol().toUpperCase().equals("SZY206FJ") && !cfgUploadCom.getProtocol().toUpperCase().equals("SZY206HB") && !cfgUploadCom.getProtocol().toUpperCase().equals("SZY206NSBD") && !cfgUploadCom.getProtocol().toUpperCase().equals("HEDA") && !cfgUploadCom.getProtocol().toUpperCase().equals("HJ212WH")) {
                                    commInter = (new com.grean.station.comm.CommSerialLine.Builder(cfgSerial.getPort())).baudRate(cfgSerial.getBaudRate()).dataBits(cfgSerial.getDataBits()).stopBits(cfgSerial.getStopBits()).parity(cfgSerial.getParity()).flowControl(cfgSerial.getFlowControl()).build();
                                    ((CommInter) commInter).Open();
                                } else if (cfgSerial != null) {
                                    commInter = (new Builder(cfgSerial.getPort())).baudRate(cfgSerial.getBaudRate()).dataBits(cfgSerial.getDataBits()).stopBits(cfgSerial.getStopBits()).parity(cfgSerial.getParity()).flowControl(cfgSerial.getFlowControl()).build();
                                    ((CommInter) commInter).Open();
                                }

                                if (commInter != null) {
                                    this.initUploadInter(cfgUploadCom, (CommInter) commInter);
                                    this.commUploadList.add(commInter);
                                }
                            }
                        }

                        cfgUploadNet = (CfgUploadNet) var1.next();
                    } while (!cfgUploadNet.isUsed());
                } while (cfgUploadNet.getProtocol().toUpperCase().equals("JSFTP"));
            } while (cfgUploadNet.getProtocol().toUpperCase().equals("QDFTP"));

            CommInter commInter = null;
            if (cfgUploadNet.getProtocol().toUpperCase().equals("MODBUS")) {
                commInter = new CommNetClientSerial(cfgUploadNet.getIp(), cfgUploadNet.getPort(), Integer.parseInt(cfgUploadNet.getMn()));
            } else if (cfgUploadNet.getProtocol().toUpperCase().equals("HJ212UDP")) {
                commInter = new UdpNetClientLine(cfgUploadNet.getIp(), cfgUploadNet.getPort());
            } else if (cfgUploadNet.getProtocol().toUpperCase().equals("NBUCMA")) {
                commInter = new CommUcma(cfgUploadNet.getPort());
            } else if (cfgUploadNet.getProtocol().toUpperCase().equals("SZY206")) {
                commInter = new CommNetClientSzy206(cfgUploadNet.getIp(), cfgUploadNet.getPort(), 1);
            } else if (!cfgUploadNet.getProtocol().toUpperCase().equals("SZY206SH") && !cfgUploadNet.getProtocol().toUpperCase().equals("SZY206FJ") && !cfgUploadNet.getProtocol().toUpperCase().equals("SZY206HB") && !cfgUploadNet.getProtocol().toUpperCase().equals("SZY206NSBD") && !cfgUploadNet.getProtocol().toUpperCase().equals("HEDA") && !cfgUploadNet.getProtocol().toUpperCase().equals("HJ212WH")) {
                commInter = new CommNetClientLine(cfgUploadNet.getIp(), cfgUploadNet.getPort());
            } else {
                commInter = new CommNetClientSerial(cfgUploadNet.getIp(), cfgUploadNet.getPort(), 1);
            }

            commInter.Open();
            this.initUploadInter(cfgUploadNet, commInter);
            this.commUploadList.add(commInter);
        }
    }

    private CfgSerial getCfgSerial(CfgUploadCom cfgUploadCom) {
        Iterator var2 = this.cfgSerialList.iterator();

        CfgSerial cfgSerial;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            cfgSerial = (CfgSerial) var2.next();
        } while (cfgSerial.getPort() != cfgUploadCom.getPort());

        return cfgSerial;
    }

    private UploadInter initUploadInter(CfgUploadNet cfgUploadNet, CommInter commInter) {
        String var4 = cfgUploadNet.getProtocol().toUpperCase();
        byte var5 = -1;
        switch (var4.hashCode()) {
            case -2130240604:
                if (var4.equals("ZJ212GREAN")) {
                    var5 = 11;
                }
                break;
            case -2115956172:
                if (var4.equals("ZJ212WATER")) {
                    var5 = 9;
                }
                break;
            case -2015472578:
                if (var4.equals("MODBUS")) {
                    var5 = 28;
                }
                break;
            case -1998342058:
                if (var4.equals("NBUCMA")) {
                    var5 = 1;
                }
                break;
            case -1832929882:
                if (var4.equals("SZY206")) {
                    var5 = 6;
                }
                break;
            case -1652137542:
                if (var4.equals("SD212BZ")) {
                    var5 = 19;
                }
                break;
            case -1352145081:
                if (var4.equals("ZJ212POLLUTION")) {
                    var5 = 10;
                }
                break;
            case -1079553997:
                if (var4.equals("SX212SX")) {
                    var5 = 24;
                }
                break;
            case -637628304:
                if (var4.equals("HJ212UDP")) {
                    var5 = 3;
                }
                break;
            case -620298124:
                if (var4.equals("JS212YC")) {
                    var5 = 18;
                }
                break;
            case -509022998:
                if (var4.equals("SZY206FJ")) {
                    var5 = 14;
                }
                break;
            case -509022944:
                if (var4.equals("SZY206HB")) {
                    var5 = 15;
                }
                break;
            case -509022597:
                if (var4.equals("SZY206SH")) {
                    var5 = 13;
                }
                break;
            case 2213434:
                if (var4.equals("HEDA")) {
                    var5 = 21;
                }
                break;
            case 67302960:
                if (var4.equals("ZJ212WATERRESOURCE2")) {
                    var5 = 23;
                }
                break;
            case 68747665:
                if (var4.equals("HJ212")) {
                    var5 = 0;
                }
                break;
            case 78969945:
                if (var4.equals("SL651")) {
                    var5 = 7;
                }
                break;
            case 417813058:
                if (var4.equals("ZJ212WATERRESOURCE")) {
                    var5 = 22;
                }
                break;
            case 437196096:
                if (var4.equals("ZJ212KH")) {
                    var5 = 17;
                }
                break;
            case 455419757:
                if (var4.equals("SZY206NSBD")) {
                    var5 = 16;
                }
                break;
            case 893959158:
                if (var4.equals("JS212YCDF")) {
                    var5 = 26;
                }
                break;
            case 1347712999:
                if (var4.equals("DANJIANGKOU")) {
                    var5 = 31;
                }
                break;
            case 1434150042:
                if (var4.equals("SD212QDSX")) {
                    var5 = 20;
                }
                break;
            case 1641998927:
                if (var4.equals("HJ212HF")) {
                    var5 = 30;
                }
                break;
            case 1641998990:
                if (var4.equals("HJ212JG")) {
                    var5 = 32;
                }
                break;
            case 1641999394:
                if (var4.equals("HJ212WH")) {
                    var5 = 27;
                }
                break;
            case 1641999489:
                if (var4.equals("HJ212ZJ")) {
                    var5 = 4;
                }
                break;
            case 1707296245:
                if (var4.equals("HJ2122017")) {
                    var5 = 29;
                }
                break;
            case 1707982581:
                if (var4.equals("HJ212HOUR")) {
                    var5 = 25;
                }
                break;
            case 1708164568:
                if (var4.equals("HJ212NSBD")) {
                    var5 = 33;
                }
                break;
            case 1708330403:
                if (var4.equals("HJ212TEST")) {
                    var5 = 5;
                }
                break;
            case 1976957908:
                if (var4.equals("HJ212ENCRYPTION")) {
                    var5 = 2;
                }
                break;
            case 1995529728:
                if (var4.equals("CQTEST")) {
                    var5 = 8;
                }
                break;
            case 2034961265:
                if (var4.equals("GDYUJING")) {
                    var5 = 12;
                }
        }

        UploadInter uploadInter = null;
        switch (var5) {
            case 0:
            case 1:
                uploadInter = new Upload212();
                break;
            case 2:
                uploadInter = new Upload212_Encryption();
                uploadInter.init(this, commInter, cfgUploadNet);
                uploadInter.setUploadName(cfgUploadNet.getName());
                this.uploadInterList.add(uploadInter);
                uploadInter.setKeyString(this.key_string);
                uploadInter.startUpload();
                return uploadInter;
            case 3:
                uploadInter = new Upload212_UDP();
                break;
            case 4:
                uploadInter = new Upload212_ZJ();
                break;
            case 5:
                uploadInter = new Upload212_TEST();
                break;
            case 6:
                uploadInter = new UploadSzy206();
                break;
            case 7:
                uploadInter = new UploadSL651();
                break;
            case 8:
                uploadInter = new Upload212_CQ();
                break;
            case 9:
                uploadInter = new UploadZJ_Water();
                break;
            case 10:
                uploadInter = new UploadZJ_Pollution();
                break;
            case 11:
                uploadInter = new UploadZJ_Grean();
                break;
            case 12:
                uploadInter = new Upload212_GD();
                break;
            case 13:
                uploadInter = new UploadSzy206_SH();
                break;
            case 14:
                uploadInter = new UploadSzy206_FJ();
                break;
            case 15:
                uploadInter = new UploadSzy206_HB();
                break;
            case 16:
                uploadInter = new UploadSzy206_NSBD();
                break;
            case 17:
                uploadInter = new Upload212_KH();
                break;
            case 18:
                uploadInter = new Upload212_YC();
                break;
            case 19:
                uploadInter = new Upload212_BZ();
                break;
            case 20:
                uploadInter = new Upload212_QDSX();
                break;
            case 21:
                uploadInter = new UploadHeDa();
                break;
            case 22:
                uploadInter = new UploadZJ_WaterResource();
                break;
            case 23:
                uploadInter = new UploadZJ_WaterResourceNoFive();
                break;
            case 24:
                uploadInter = new Upload212_SX();
                break;
            case 25:
                uploadInter = new Upload212HOUR();
                break;
            case 26:
                uploadInter = new Upload212_YCDF();
                break;
            case 27:
                uploadInter = new Upload212_WH();
                break;
            case 28:
                uploadInter = new UploadModbusServer();
                break;
            case 29:
                uploadInter = new Upload212_2017();
                break;
            case 30:
                uploadInter = new Upload212_HF();
                break;
            case 31:
                uploadInter = new UploadDJK();
                break;
            case 32:
                uploadInter = new Upload212_JG();
                break;
            case 33:
                uploadInter = new Upload212_NSBD();
                break;
            default:
                return null;
        }

        ((UploadInter) uploadInter).init(this, commInter, cfgUploadNet);
        ((UploadInter) uploadInter).setUploadName(cfgUploadNet.getName());
        this.uploadInterList.add(uploadInter);
        ((UploadInter) uploadInter).startUpload();
        return (UploadInter) uploadInter;
    }

    private void initFtp() {
        Iterator var1 = this.cfgUploadNetList.iterator();

        while (var1.hasNext()) {
            CfgUploadNet cfgUploadNet = (CfgUploadNet) var1.next();
            if (cfgUploadNet.isUsed()) {
                String var3 = cfgUploadNet.getProtocol().toUpperCase();
                byte var4 = -1;
                switch (var3.hashCode()) {
                    case 70883161:
                        if (var3.equals("JSFTP")) {
                            var4 = 0;
                        }
                        break;
                    case 76900943:
                        if (var3.equals("QDFTP")) {
                            var4 = 1;
                        }
                        break;
                    case 83276217:
                        if (var3.equals("XAFTP")) {
                            var4 = 2;
                        }
                }

                switch (var4) {
                    case 0:
                        CommInter commInter1 = new CommFtp(cfgUploadNet.getIp(), cfgUploadNet.getPort(), this.ftp_user, this.ftp_password, this.file_path, this.ftp_path, cfgUploadNet.getMn());
                        commInter1.Open();
                        this.commUploadList.add(commInter1);
                        UploadInter uploadInter1 = new UploadFtp_JS();
                        uploadInter1.init(this, commInter1, cfgUploadNet);
                        uploadInter1.setUploadName(cfgUploadNet.getName());
                        this.uploadInterList.add(uploadInter1);
                        uploadInter1.startUpload();
                        break;
                    case 1:
                        CommInter commInter2 = new CommFtpQD(cfgUploadNet.getIp(), cfgUploadNet.getPort(), this.ftp_user, this.ftp_password, this.file_path, this.ftp_path, this.ftp_area_code, this.ftp_station_code);
                        commInter2.Open();
                        this.commUploadList.add(commInter2);
                        UploadInter uploadInter2 = new UploadFtp_QD();
                        uploadInter2.init(this, commInter2, cfgUploadNet);
                        uploadInter2.setUploadName(cfgUploadNet.getName());
                        this.uploadInterList.add(uploadInter2);
                        uploadInter2.startUpload();
                        break;
                    case 2:
                        CommInter commInter3 = new CommFtpXA(cfgUploadNet.getIp(), cfgUploadNet.getPort(), this.ftp_user, this.ftp_password, this.file_path, this.ftp_path, this.ftp_area_code, this.ftp_station_code);
                        commInter3.Open();
                        this.commUploadList.add(commInter3);
                        UploadInter uploadInter3 = new UploadFtp_XA();
                        uploadInter3.init(this, commInter3, cfgUploadNet);
                        uploadInter3.setUploadName(cfgUploadNet.getName());
                        this.uploadInterList.add(uploadInter3);
                        uploadInter3.startUpload();
                }
            }
        }

    }

    private void initDtu() {
        if (this.dtu_port > 0) {
            String var1 = this.dtu_protocol.toUpperCase();
            byte var2 = -1;
            switch (var1.hashCode()) {
                case -2015472578:
                    if (var1.equals("MODBUS")) {
                        var2 = 1;
                    }
                    break;
                case -1418228169:
                    if (var1.equals("212HOUR")) {
                        var2 = 5;
                    }
                    break;
                case -509022944:
                    if (var1.equals("SZY206HB")) {
                        var2 = 0;
                    }
                    break;
                case 47686438:
                    if (var1.equals("212QD")) {
                        var2 = 4;
                    }
                    break;
                case 47686520:
                    if (var1.equals("212SX")) {
                        var2 = 2;
                    }
                    break;
                case 1478282169:
                    if (var1.equals("212SX1")) {
                        var2 = 3;
                    }
            }

            Iterator var3;
            CfgSerial cfgSerial;
            CommSerial commInter;
            CfgUploadNet cfgUploadNet;
            switch (var2) {
                case 0:
                    var3 = this.cfgSerialList.iterator();

                    do {
                        if (!var3.hasNext()) {
                            return;
                        }

                        cfgSerial = (CfgSerial) var3.next();
                    } while (cfgSerial.getPort() != this.dtu_port);

                    commInter = (new Builder(cfgSerial.getPort())).baudRate(cfgSerial.getBaudRate()).dataBits(cfgSerial.getDataBits()).stopBits(cfgSerial.getStopBits()).parity(cfgSerial.getParity()).build();
                    if (commInter.Open()) {
                        cfgUploadNet = new CfgUploadNet();
                        cfgUploadNet.reset();
                        cfgUploadNet.setProtocol("SZY206HB");
                        cfgUploadNet.setName("DTU");
                        cfgUploadNet.setMn(this.dtu_mn);
                        UploadInter uploadInter = new UploadSzy206_HB();
                        uploadInter.init(this, commInter, cfgUploadNet);
                        uploadInter.setUploadName(cfgUploadNet.getName());
                        this.uploadInterList.add(uploadInter);
                        uploadInter.startUpload();
                        this.resendDtuData(uploadInter);
                    }
                    break;
                case 1:
                    var3 = this.cfgSerialList.iterator();

                    do {
                        if (!var3.hasNext()) {
                            return;
                        }

                        cfgSerial = (CfgSerial) var3.next();
                    } while (cfgSerial.getPort() != this.dtu_port);

                    commInter = (new Builder(cfgSerial.getPort())).baudRate(cfgSerial.getBaudRate()).dataBits(cfgSerial.getDataBits()).stopBits(cfgSerial.getStopBits()).parity(cfgSerial.getParity()).build();
                    if (commInter.Open()) {
                        cfgUploadNet = new CfgUploadNet();
                        cfgUploadNet.reset();
                        cfgUploadNet.setProtocol("MODBUS");
                        cfgUploadNet.setName("DTU");
                        cfgUploadNet.setMn(this.dtu_mn);
                        if (this.dtu_heart) {
                            cfgUploadNet.setPeriod_heart(60);
                        } else {
                            cfgUploadNet.setPeriod_heart(0);
                        }

                        cfgUploadNet.setPeriod_real(60);
                        UploadInter uploadInter = new UploadModbus();
                        uploadInter.init(this, commInter, cfgUploadNet);
                        uploadInter.setUploadName(cfgUploadNet.getName());
                        this.uploadInterList.add(uploadInter);
                        uploadInter.startUpload();
                        this.resendDtuData(uploadInter);
                    }
                    break;
                case 2:
                    var3 = this.cfgSerialList.iterator();

                    do {
                        if (!var3.hasNext()) {
                            return;
                        }

                        cfgSerial = (CfgSerial) var3.next();
                    } while (cfgSerial.getPort() != this.dtu_port);

                    commInter = (new Builder(cfgSerial.getPort())).baudRate(cfgSerial.getBaudRate()).dataBits(cfgSerial.getDataBits()).stopBits(cfgSerial.getStopBits()).parity(cfgSerial.getParity()).build();
                    if (commInter.Open()) {
                        cfgUploadNet = new CfgUploadNet();
                        cfgUploadNet.reset();
                        cfgUploadNet.setProtocol("212SX");
                        cfgUploadNet.setName("DTU");
                        cfgUploadNet.setMn(this.dtu_mn);
                        cfgUploadNet.setPeriod_heart(60);
                        cfgUploadNet.setPeriod_real(60);
                        cfgUploadNet.setRe_count(0);
                        UploadInter uploadInter = new Upload212_SX();
                        uploadInter.init(this, commInter, cfgUploadNet);
                        uploadInter.setUploadName(cfgUploadNet.getName());
                        this.uploadInterList.add(uploadInter);
                        uploadInter.startUpload();
                        this.resendDtuData(uploadInter);
                    }
                    break;
                case 3:
                    var3 = this.cfgSerialList.iterator();

                    do {
                        if (!var3.hasNext()) {
                            return;
                        }

                        cfgSerial = (CfgSerial) var3.next();
                    } while (cfgSerial.getPort() != this.dtu_port);

                    commInter = (new Builder(cfgSerial.getPort())).baudRate(cfgSerial.getBaudRate()).dataBits(cfgSerial.getDataBits()).stopBits(cfgSerial.getStopBits()).parity(cfgSerial.getParity()).build();
                    if (commInter.Open()) {
                        cfgUploadNet = new CfgUploadNet();
                        cfgUploadNet.reset();
                        cfgUploadNet.setProtocol("212SX1");
                        cfgUploadNet.setName("DTU");
                        cfgUploadNet.setMn(this.dtu_mn);
                        cfgUploadNet.setPeriod_heart(60);
                        cfgUploadNet.setPeriod_real(60);
                        cfgUploadNet.setRe_count(0);
                        UploadInter uploadInter = new Upload212_SX1();
                        uploadInter.init(this, commInter, cfgUploadNet);
                        uploadInter.setUploadName(cfgUploadNet.getName());
                        this.uploadInterList.add(uploadInter);
                        uploadInter.startUpload();
                        this.resendDtuData(uploadInter);
                    }
                    break;
                case 4:
                    var3 = this.cfgSerialList.iterator();

                    do {
                        if (!var3.hasNext()) {
                            return;
                        }

                        cfgSerial = (CfgSerial) var3.next();
                    } while (cfgSerial.getPort() != this.dtu_port);

                    commInter = (new Builder(cfgSerial.getPort())).baudRate(cfgSerial.getBaudRate()).dataBits(cfgSerial.getDataBits()).stopBits(cfgSerial.getStopBits()).parity(cfgSerial.getParity()).build();
                    if (commInter.Open()) {
                        cfgUploadNet = new CfgUploadNet();
                        cfgUploadNet.reset();
                        cfgUploadNet.setProtocol("212QD");
                        cfgUploadNet.setName("DTU");
                        cfgUploadNet.setMn(this.dtu_mn);
                        cfgUploadNet.setPeriod_heart(0);
                        cfgUploadNet.setPeriod_real(60);
                        cfgUploadNet.setRe_count(0);
                        UploadInter uploadInter = new Upload212_QD();
                        uploadInter.init(this, commInter, cfgUploadNet);
                        uploadInter.setUploadName(cfgUploadNet.getName());
                        this.uploadInterList.add(uploadInter);
                        uploadInter.startUpload();
                        this.resendDtuData(uploadInter);
                    }
                    break;
                case 5:
                    var3 = this.cfgSerialList.iterator();

                    while (var3.hasNext()) {
                        cfgSerial = (CfgSerial) var3.next();
                        if (cfgSerial.getPort() == this.dtu_port) {
                            commInter = (new Builder(cfgSerial.getPort())).baudRate(cfgSerial.getBaudRate()).dataBits(cfgSerial.getDataBits()).stopBits(cfgSerial.getStopBits()).parity(cfgSerial.getParity()).build();
                            if (commInter.Open()) {
                                cfgUploadNet = new CfgUploadNet();
                                cfgUploadNet.reset();
                                cfgUploadNet.setProtocol("212HOUR");
                                cfgUploadNet.setName("DTU");
                                cfgUploadNet.setMn(this.dtu_mn);
                                cfgUploadNet.setPeriod_heart(0);
                                cfgUploadNet.setPeriod_real(0);
                                cfgUploadNet.setRe_count(0);
                                UploadInter uploadInter = new Upload212HOUR();
                                uploadInter.init(this, commInter, cfgUploadNet);
                                uploadInter.setUploadName(cfgUploadNet.getName());
                                this.uploadInterList.add(uploadInter);
                                uploadInter.startUpload();
                                this.resendDtuData(uploadInter);
                            }
                            break;
                        }
                    }
            }
        }

    }

    private void resendDtuData(final UploadInter uploadInter) {
        (new Thread(new Runnable() {
            public void run() {
                if (TimeHelper.getCurrentTimestamp().getTime() > Utility.dtuUpload.getTime()) {
                    DateRange dateRange = new DateRange();
                    dateRange.setStartDate(Utility.dtuUpload);
                    dateRange.setEndDate(TimeHelper.getCurrentTimestamp());
                    List<RecDataTime> recTimeList = MonitorService.this.recMapper.getRecTimeHourListByRange(dateRange);
                    Iterator var3 = recTimeList.iterator();

                    while (var3.hasNext()) {
                        RecDataTime recTime = (RecDataTime) var3.next();
                        uploadInter.sendHourData(0, recTime.getRecTime());

                        try {
                            Thread.sleep(1000L);
                        } catch (Exception var6) {
                        }
                    }
                }

            }
        })).start();
    }

    private void initFiveCheck() {
        this.checkFiveInfo = new ReqFiveInfo();
        List<CfgScheduleSample> cfgScheduleFiveList = this.cfgMapper.getCfgScheduleFiveList();
        List<ScheduleFive> scheduleFiveList = new ArrayList();

        for (int i = 0; i < cfgScheduleFiveList.size(); ++i) {
            ScheduleFive scheduleFive = new ScheduleFive();
            scheduleFive.setLabel(String.format("%02d时", i));
            scheduleFive.setValue(i);
            scheduleFive.setChecked(((CfgScheduleSample) cfgScheduleFiveList.get(i)).isRun());
            scheduleFiveList.add(scheduleFive);
        }

        this.checkFiveInfo.setScheduleFiveList(scheduleFiveList);
        CfgFive cfgFive = this.cfgMapper.getCfgFive();
        this.checkFiveInfo.setSchedule(cfgFive.isIs_schedule());
        this.checkFiveInfo.setWeek(cfgFive.isIs_week());
        this.checkFiveInfo.setWeekDay(cfgFive.getWeek_day());
        this.checkFiveInfo.setStartDate(cfgFive.getStart_date());
        this.checkFiveInfo.setEndDate(cfgFive.getEnd_date());
        this.checkFiveInfo.setUrgent(cfgFive.isIs_urgent());
        this.checkFiveInfo.setUrgentCircle(cfgFive.getUrgent_circle());
    }

    public void refreshCfg() {
        this.cfgDevParamList = this.cfgMapper.getCfgDevParamList();
    }

    private void initCfg() {
        this.saveRecLogSys("系统日志", "载入配置信息");
        this.devServiceList.clear();
        this.uploadInterList.clear();
        this.commInterList.clear();
        this.cfgSerialList = this.cfgMapper.getCfgSerialList();
        this.cfgDevList = this.cfgMapper.getCfgDevList();
        this.cfgDevQueryList = this.cfgMapper.getCfgDevQueryList();
        this.cfgDevCmdList = this.cfgMapper.getCfgDevCmdList();
        this.cfgDevLogList = this.cfgMapper.getCfgDevLogList();
        this.cfgDevAlarmList = this.cfgMapper.getCfgDevAlarmList();
        this.cfgDevFaultList = this.cfgMapper.getCfgDevFaultList();
        this.cfgLogGroupList = this.cfgMapper.getCfgLogGroupList();
        this.cfgDevParamList = this.cfgMapper.getCfgDevParamList();
        this.cfgNameList = this.cfgMapper.getCfgNameList();
        this.cfgRegModbusList = this.cfgMapper.getCfgRegModbusList();
        this.cfgRegFiveList = this.cfgMapper.getCfgRegFiveList();
        this.cfgRegQualityList = this.cfgMapper.getCfgRegQualityList();
        this.cfgRegQualityList3 = this.cfgMapper.getCfgRegQualityList3();
        this.cfgRegQualityList4 = this.cfgMapper.getCfgRegQualityList4();
        this.cfgRegFlowList = this.cfgMapper.getCfgRegFlowList();
        this.cfgRegYiwenList = this.cfgMapper.getCfgRegYiwenList();
        this.cfgRegYuxingList = this.cfgMapper.getCfgRegYuxingList();
        this.cfgComm206List = this.cfgMapper.getCfgComm206List();
        this.cfgFactorList = this.cfgMapper.getCfgFactorList();
        this.cfgNetClientList = this.cfgMapper.getCfgNetClientList();
        this.cfgUploadNetList = this.cfgMapper.getCfgUploadNetList();
        this.cfgUploadComList = this.cfgMapper.getCfgUploadComList();
        this.cfgScheduleDayList = this.cfgMapper.getCfgScheduleDayList();
        this.cfgScheduleWeekList = this.cfgMapper.getCfgScheduleWeekList();
        this.cfgScheduleMonthList = this.cfgMapper.getCfgScheduleMonthList();
        this.cfgScriptList = this.cfgMapper.getCfgScriptList();
        this.cfgSample = this.cfgMapper.getCfgSample();
        this.cfgScheduleSampleList = this.cfgMapper.getCfgScheduleSampleList();
        this.cfgScheduleQualityList = this.cfgMapper.getCfgScheduleQualityList();
        this.cfgFlow = this.cfgMapper.getCfgFlow();
        Iterator var1 = this.cfgFactorList.iterator();

        while (var1.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor) var1.next();
            if (cfgFactor.isUsed() && cfgFactor.getDevID() == 0) {
                cfgFactor.setDataMea(cfgFactor.getAlarmMin());
                cfgFactor.setFlagMea("SET");
                cfgFactor.setTimeMea((Timestamp) null);
            }
        }

        var1 = this.cfgSerialList.iterator();

        Iterator var3;
        CfgDev cfgDev;
        while (var1.hasNext()) {
            CfgSerial cfgSerial = (CfgSerial) var1.next();
            var3 = this.cfgDevList.iterator();

            while (var3.hasNext()) {
                cfgDev = (CfgDev) var3.next();
                if (cfgDev.getSerial_id() == cfgSerial.getId()) {
                    this.initDev(cfgDev);
                    if (cfgSerial.getDevList().size() < 8) {
                        cfgDev.setSerial_channel(cfgSerial.getDevList().size());
                        cfgSerial.getDevList().add(cfgDev);
                    }
                }
            }
        }

        var1 = this.cfgNetClientList.iterator();

        while (var1.hasNext()) {
            CfgNetClient cfgNetClient = (CfgNetClient) var1.next();
            var3 = this.cfgDevList.iterator();

            while (var3.hasNext()) {
                cfgDev = (CfgDev) var3.next();
                if (cfgDev.getNet_id() == cfgNetClient.getId()) {
                    this.initDev(cfgDev);
                    cfgNetClient.getDevList().add(cfgDev);
                }
            }
        }

    }

    private void initDev(CfgDev cfgDev) {
        cfgDev.getFactorList().clear();
        Iterator var2 = this.cfgFactorList.iterator();

        while (var2.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor) var2.next();
            if (cfgFactor.getDevID() == cfgDev.getId()) {
                cfgDev.getFactorList().add(cfgFactor);
            }
        }

        cfgDev.setStatus(true);
        cfgDev.getQueryList().clear();
        var2 = this.cfgDevQueryList.iterator();

        while (var2.hasNext()) {
            CfgDevQuery cfgDevQuery = (CfgDevQuery) var2.next();
            if (cfgDevQuery.getDevid() == cfgDev.getId() && cfgDevQuery.getProtoType().equals(cfgDev.getProtocol())) {
                cfgDev.getQueryList().add(cfgDevQuery);
            }
        }

        cfgDev.getMeaCmdList().clear();
        cfgDev.getStdCmdList().clear();
        cfgDev.getZeroCmdList().clear();
        cfgDev.getSpanCmdList().clear();
        cfgDev.getBlnkCmdList().clear();
        cfgDev.getParCmdList().clear();
        cfgDev.getRcvrCmdList().clear();
        cfgDev.getBlnkCalList().clear();
        cfgDev.getStdCalList().clear();
        cfgDev.getInitCmdList().clear();
        cfgDev.getStopCmdList().clear();
        cfgDev.getRsetCmdList().clear();
        var2 = this.cfgDevCmdList.iterator();

        while (var2.hasNext()) {
            CfgDevCmd cfgDevCmd = (CfgDevCmd) var2.next();
            if (cfgDevCmd.getDevid() == cfgDev.getId() && cfgDevCmd.getProtoType().equals(cfgDev.getProtocol())) {
                switch (cfgDevCmd.getCmdType()) {
                    case 1:
                        cfgDev.getMeaCmdList().add(cfgDevCmd);
                        break;
                    case 2:
                        cfgDev.getStdCmdList().add(cfgDevCmd);
                        break;
                    case 3:
                        cfgDev.getZeroCmdList().add(cfgDevCmd);
                        break;
                    case 4:
                        cfgDev.getSpanCmdList().add(cfgDevCmd);
                        break;
                    case 5:
                        cfgDev.getBlnkCmdList().add(cfgDevCmd);
                        break;
                    case 6:
                        cfgDev.getParCmdList().add(cfgDevCmd);
                        break;
                    case 7:
                        cfgDev.getRcvrCmdList().add(cfgDevCmd);
                        break;
                    case 8:
                        cfgDev.getBlnkCalList().add(cfgDevCmd);
                        break;
                    case 9:
                        cfgDev.getStdCalList().add(cfgDevCmd);
                        break;
                    case 10:
                        cfgDev.getInitCmdList().add(cfgDevCmd);
                        break;
                    case 11:
                        cfgDev.getStopCmdList().add(cfgDevCmd);
                        break;
                    case 12:
                        cfgDev.getRsetCmdList().add(cfgDevCmd);
                }
            }
        }

        if (cfgDev.getReg_name() != null) {
            cfgDev.setDevRegList(this.cfgMapper.getCfgDevRegList(cfgDev.getReg_name()));
        }

    }

    public boolean restartService() {
        this.saveRecLogSys("系统日志", "重置配置信息");
        restart = true;
        this.scriptService.free();
        Iterator var1 = this.serialQueryList.iterator();

        while (var1.hasNext()) {
            Thread thread = (Thread) var1.next();
            thread.interrupt();
        }

        var1 = this.devServiceList.iterator();

        while (var1.hasNext()) {
            DevService devService = (DevService) var1.next();
            if (devService != null) {
                devService.stopQuery();
            }
        }

        var1 = this.uploadInterList.iterator();

        while (var1.hasNext()) {
            UploadInter uploadInter = (UploadInter) var1.next();
            uploadInter.stopUpload();
        }

        for (int i = 0; i < 20; ++i) {
            try {
                Thread.sleep(1000L);
            } catch (Exception var3) {
            }
        }

        var1 = this.commInterList.iterator();

        CommInter commInter;
        while (var1.hasNext()) {
            commInter = (CommInter) var1.next();
            if (commInter != null) {
                commInter.Close();
            }
        }

        var1 = this.commUploadList.iterator();

        while (var1.hasNext()) {
            commInter = (CommInter) var1.next();
            if (commInter != null) {
                commInter.Close();
            }
        }

        restart = false;
        this.initCfg();
        this.initComDev();
        this.initNetDev();
        this.initUpload();
        return true;
    }

    public void refreshSchedule() {
        this.cfgScheduleDayList = this.cfgMapper.getCfgScheduleDayList();
        this.cfgScheduleWeekList = this.cfgMapper.getCfgScheduleWeekList();
        this.cfgScheduleMonthList = this.cfgMapper.getCfgScheduleMonthList();
    }

    public void refreshUpload() {
        try {
            Iterator var1 = this.commUploadList.iterator();

            while (var1.hasNext()) {
                CommInter commInter = (CommInter) var1.next();
                if (commInter != null) {
                    commInter.Close();
                }
            }
        } catch (Exception var3) {
            System.out.println(var3.toString());
        }

        this.cfgUploadNetList = this.cfgMapper.getCfgUploadNetList();
        this.cfgUploadComList = this.cfgMapper.getCfgUploadComList();
        this.initUpload();
    }

    public void refreshQuality() {
        this.cfgFactorList = this.cfgMapper.getCfgFactorList();
    }

    public boolean startFiveTest(int startHour) {
        boolean bResult = false;
        if (this.scriptService.isOnScriptFive()) {
            return bResult;
        } else if (this.checkFiveInfo != null && this.checkFiveInfo.isSchedule() && !((ScheduleFive) this.checkFiveInfo.getScheduleFiveList().get(startHour)).isChecked()) {
            return bResult;
        } else {
            this.scriptFiveRunTime = this.getFormatCurrentTime();
            String tmpFiveName = ScriptWord.getInstance().getScriptFiveName();
            if (tmpFiveName != null) {
                bResult = this.scriptService.startScriptFive(tmpFiveName);
            } else {
                this.saveRecLogSys("系统日志", "五参数脚本未定义");
            }

            return bResult;
        }
    }

    public boolean startTest(int cmdType, String saveType) {
        boolean bResult = false;
        if (cmdType == 11) {
            this.sysStatus = "停止测试";
            this.saveRecLogSys("系统日志", this.sysStatus);
            this.stopTest();
            Utility.setJcgy(false);
            return true;
        } else if (this.scriptService.isOnScript()) {
            return bResult;
        } else {
            this.scriptInfo.setRunCmd(cmdType);
            this.scriptInfo.setSaveType(saveType);
            this.scriptInfo.setRunTime(this.getFormatCurrentTime());
            this.sysStatus = this.getSysStatus(cmdType);
            String tmpName2;
            label64:
            switch (cmdType) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    this.saveRecLogSys("系统日志", this.sysStatus);
                    String tmpName1 = ScriptWord.getInstance().getScriptName(cmdType);
                    if (tmpName1 != null) {
                        bResult = this.scriptService.startScript(tmpName1);
                    } else {
                        int iRunMode = (int) ScriptWord.getInstance().getSysVar("RunMode");
                        tmpName2 = Utility.getScriptMode(iRunMode);
                        tmpName2 = tmpName2 + Utility.getScriptName(cmdType);
                        this.saveRecLogSys("系统日志", tmpName2 + "脚本未定义");
                    }
                    break;
                case 8:
                case 9:
                    Iterator var12 = this.cfgDevList.iterator();

                    while (true) {
                        if (!var12.hasNext()) {
                            break label64;
                        }

                        CfgDev cfgDev = (CfgDev) var12.next();
                        boolean bExist = false;
                        Iterator var8 = cfgDev.getFactorList().iterator();

                        while (var8.hasNext()) {
                            CfgFactor cfgFactor = (CfgFactor) var8.next();
                            Iterator var10 = this.scriptInfo.getFactorList().iterator();

                            while (var10.hasNext()) {
                                int factorID = (Integer) var10.next();
                                if (cfgFactor.getId() == factorID) {
                                    bExist = true;
                                    break;
                                }
                            }

                            if (bExist) {
                                break;
                            }
                        }

                        if (bExist) {
                            this.doDevCmd(cfgDev.getId(), cmdType, "hd");
                        }
                    }
                case 11:
                    this.saveRecLogSys("系统日志", this.sysStatus);
                    this.stopTest();
                    bResult = true;
                    break;
                case 50:
                case 60:
                    this.scriptInfo.setRunCmd(1);
                    this.saveRecLogSys("系统日志", this.sysStatus);
                    tmpName2 = ScriptWord.getInstance().getScriptName(cmdType);
                    if (tmpName2 != null) {
                        bResult = this.scriptService.startScript(tmpName2);
                    }
            }

            if (cmdType == 50) {
                Utility.setJcgy(true);
            } else {
                Utility.setJcgy(false);
            }

            if (!bResult) {
                this.sysStatus = "系统待机";
            }

            return bResult;
        }
    }

    public void startQuality(int cmdType) {
        List factorList;
        if (this.scriptInfo.getRunCmd() != 6 && this.scriptInfo.getRunCmd() != 7) {
            factorList = this.scriptInfo.getFactorList();
        } else {
            factorList = this.scriptInfo.getFactorList2();
        }

        if (factorList != null && factorList.size() != 0) {
            Iterator var3 = this.devServiceList.iterator();

            while (true) {
                while (true) {
                    while (true) {
                        while (true) {
                            label375:
                            while (true) {
                                DevService devService;
                                do {
                                    if (!var3.hasNext()) {
                                        return;
                                    }

                                    devService = (DevService) var3.next();
                                } while (!devService.getDevType().toUpperCase().equals("QUALITY"));

                                String var5 = devService.mCfgDev.getProtocol();
                                byte var6 = -1;
                                switch (var5.hashCode()) {
                                    case -1241832860:
                                        if (var5.equals("QualityModbusAll")) {
                                            var6 = 4;
                                        }
                                        break;
                                    case -848109177:
                                        if (var5.equals("QualityShangyang")) {
                                            var6 = 5;
                                        }
                                        break;
                                    case -505069059:
                                        if (var5.equals("QualityModbus")) {
                                            var6 = 0;
                                        }
                                        break;
                                    case 1522728404:
                                        if (var5.equals("QualityModbus1")) {
                                            var6 = 1;
                                        }
                                        break;
                                    case 1522728406:
                                        if (var5.equals("QualityModbus3")) {
                                            var6 = 2;
                                        }
                                        break;
                                    case 1522728407:
                                        if (var5.equals("QualityModbus4")) {
                                            var6 = 3;
                                        }
                                }

                                Iterator var7;
                                Integer factorId;
                                Integer addVol;
                                Iterator var17;
                                CfgFactor cfgFactor;
                                switch (var6) {
                                    case 0:
                                    case 1:
                                        var7 = factorList.iterator();

                                        while (var7.hasNext()) {
                                            factorId = (Integer) var7.next();
                                            if (devService.getRelatedID() == this.getDevID(factorId)) {
                                                addVol = null;
                                                var17 = this.cfgFactorList.iterator();

                                                label368:
                                                while (var17.hasNext()) {
                                                    cfgFactor = (CfgFactor) var17.next();
                                                    if (cfgFactor.getId() == factorId) {
                                                        if (cfgFactor.getDataMea() != null) {
                                                            switch (cfgFactor.getRcvrType()) {
                                                                case 0:
                                                                    addVol = (int) (cfgFactor.getRcvrMotherVol() * 1000.0D);
                                                                    break label368;
                                                                case 1:
                                                                    addVol = (int) (cfgFactor.getDataMea() * cfgFactor.getRcvrMultiple() * cfgFactor.getRcvrCupVol() * 1000.0D / cfgFactor.getRcvrMotherVal());
                                                                    if ((double) addVol / cfgFactor.getRcvrCupVol() > 0.02D) {
                                                                        addVol = (int) (cfgFactor.getRcvrCupVol() * 0.02D);
                                                                        if (cfgFactor.getRcvrType() == 1) {
                                                                            this.rcvrOver = true;
                                                                        }
                                                                    } else {
                                                                        this.rcvrOver = false;
                                                                    }
                                                            }
                                                        }
                                                        break;
                                                    }
                                                }

                                                if (addVol == null) {
                                                    devService.doDevCmd(cmdType, (String) null, (String) null);
                                                } else {
                                                    devService.doDevCmd(cmdType, addVol + "", (String) null);
                                                }

                                                this.saveRecLogSys("系统日志", devService.getDevName() + "质控设备　启动" + Utility.getTaskName(cmdType));
                                                continue label375;
                                            }
                                        }
                                        break;
                                    case 2:
                                        if (cmdType != 4000) {
                                            if (cmdType == 5003) {
                                                devService.doDevCmd(cmdType, (String) null, (String) null);
                                                this.saveRecLogSys("系统日志", devService.getDevName() + "质控设备　启动" + Utility.getTaskName(cmdType));
                                            }
                                        } else {
                                            var7 = factorList.iterator();

                                            label354:
                                            while (var7.hasNext()) {
                                                factorId = (Integer) var7.next();
                                                if (devService.getRelatedID() == this.getDevID(factorId)) {
                                                    addVol = null;
                                                    var17 = this.cfgFactorList.iterator();

                                                    do {
                                                        if (!var17.hasNext()) {
                                                            break label354;
                                                        }

                                                        cfgFactor = (CfgFactor) var17.next();
                                                    } while (cfgFactor.getId() != factorId);

                                                    if (cfgFactor.getDataMea() != null) {
                                                        switch (cfgFactor.getRcvrType()) {
                                                            case 0:
                                                                addVol = (int) (cfgFactor.getRcvrDestVal() * 200.0D * 1000.0D / cfgFactor.getRcvrMotherVal());
                                                                break;
                                                            case 1:
                                                                if (cfgFactor.getDataMea() < 4.0D * cfgFactor.getRcvrLimit()) {
                                                                    addVol = (int) (cfgFactor.getRcvrLimit() * 4.0D * 200.0D * 1000.0D / cfgFactor.getRcvrMotherVal());
                                                                } else {
                                                                    addVol = (int) (cfgFactor.getDataMea() * cfgFactor.getRcvrMultiple() * 200.0D * 1000.0D / cfgFactor.getRcvrMotherVal());
                                                                }
                                                        }

                                                        if (addVol != null) {
                                                            if (addVol > 2000) {
                                                                addVol = 2000;
                                                            }

                                                            devService.doDevCmd(3000, addVol + "", (String) null);
                                                        }
                                                    }
                                                    break;
                                                }
                                            }

                                            var7 = factorList.iterator();

                                            do {
                                                if (!var7.hasNext()) {
                                                    continue label375;
                                                }

                                                factorId = (Integer) var7.next();
                                            } while (devService.getRelatedID2() != this.getDevID(factorId));

                                            addVol = null;
                                            var17 = this.cfgFactorList.iterator();

                                            do {
                                                if (!var17.hasNext()) {
                                                    continue label375;
                                                }

                                                cfgFactor = (CfgFactor) var17.next();
                                            } while (cfgFactor.getId() != factorId);

                                            if (cfgFactor.getDataMea() != null) {
                                                switch (cfgFactor.getRcvrType()) {
                                                    case 0:
                                                        addVol = (int) (cfgFactor.getRcvrDestVal() * 200.0D * 1000.0D / cfgFactor.getRcvrMotherVal());
                                                        break;
                                                    case 1:
                                                        if (cfgFactor.getDataMea() < 4.0D * cfgFactor.getRcvrLimit()) {
                                                            addVol = (int) (cfgFactor.getRcvrLimit() * 4.0D * 200.0D * 1000.0D / cfgFactor.getRcvrMotherVal());
                                                        } else {
                                                            addVol = (int) (cfgFactor.getDataMea() * cfgFactor.getRcvrMultiple() * 200.0D * 1000.0D / cfgFactor.getRcvrMotherVal());
                                                        }
                                                }

                                                if (addVol != null) {
                                                    if (addVol > 2000) {
                                                        addVol = 2000;
                                                    }

                                                    devService.doDevCmd(3001, addVol + "", (String) null);
                                                }
                                            }
                                        }
                                        break;
                                    case 3:
                                        if (cmdType != 4000) {
                                            if (cmdType == 5003) {
                                                devService.doDevCmd(5010, (String) null, (String) null);
                                                this.saveRecLogSys("系统日志", devService.getDevName() + "质控设备　启动" + Utility.getTaskName(cmdType));
                                            } else if (cmdType == 5005) {
                                                devService.doDevCmd(5009, (String) null, (String) null);
                                                this.saveRecLogSys("系统日志", devService.getDevName() + "质控设备　启动" + Utility.getTaskName(cmdType));
                                            }
                                            break;
                                        }

                                        var7 = this.cfgFactorList.iterator();

                                        while (var7.hasNext()) {
                                            cfgFactor = (CfgFactor) var7.next();
                                            addVol = null;
                                            if (cfgFactor.getDataMea() == null) {
                                                break;
                                            }

                                            switch (cfgFactor.getRcvrType()) {
                                                case 0:
                                                    addVol = (int) (cfgFactor.getRcvrMotherVol() * 1000.0D);
                                                    break;
                                                case 1:
                                                    addVol = (int) (cfgFactor.getDataMea() * cfgFactor.getRcvrMultiple() * cfgFactor.getRcvrCupVol() * 1000.0D / cfgFactor.getRcvrMotherVal());
                                                    if ((double) addVol / cfgFactor.getRcvrCupVol() > 0.02D) {
                                                        addVol = (int) (cfgFactor.getRcvrCupVol() * 0.02D);
                                                        if (cfgFactor.getRcvrType() == 1) {
                                                            this.rcvrOver = true;
                                                        }
                                                    } else {
                                                        this.rcvrOver = false;
                                                    }
                                            }

                                            if (addVol == null) {
                                                break;
                                            }

                                            String var18 = cfgFactor.getName();
                                            byte var20 = -1;
                                            switch (var18.hashCode()) {
                                                case -1255953671:
                                                    if (var18.equals("高锰酸盐指数")) {
                                                        var20 = 0;
                                                    }
                                                    break;
                                                case -1096450952:
                                                    if (var18.equals("化学需氧量")) {
                                                        var20 = 1;
                                                    }
                                                    break;
                                                case 791379:
                                                    if (var18.equals("总氮")) {
                                                        var20 = 2;
                                                    }
                                                    break;
                                                case 794652:
                                                    if (var18.equals("总磷")) {
                                                        var20 = 3;
                                                    }
                                                    break;
                                                case 886022:
                                                    if (var18.equals("氨氮")) {
                                                        var20 = 4;
                                                    }
                                            }

                                            switch (var20) {
                                                case 0:
                                                case 1:
                                                    devService.doDevCmd(4001, addVol + "", (String) null);
                                                    break;
                                                case 2:
                                                    devService.doDevCmd(4002, addVol + "", (String) null);
                                                    break;
                                                case 3:
                                                    devService.doDevCmd(4003, addVol + "", (String) null);
                                                    break;
                                                case 4:
                                                    devService.doDevCmd(4004, addVol + "", (String) null);
                                            }
                                        }

                                        this.saveRecLogSys("系统日志", devService.getDevName() + "质控设备　启动" + Utility.getTaskName(cmdType));
                                        break;
                                    case 4:
                                        if (cmdType == 5005) {
                                            devService.doDevCmd(25, (String) null, (String) null);
                                        }

                                        if (cmdType != 5003) {
                                            break;
                                        }

                                        int iParam = 0;
                                        Iterator var14 = factorList.iterator();

                                        while (true) {
                                            while (var14.hasNext()) {
                                                addVol = (Integer) var14.next();
                                                var17 = this.cfgFactorList.iterator();

                                                while (var17.hasNext()) {
                                                    cfgFactor = (CfgFactor) var17.next();
                                                    if (cfgFactor.isUsed() && cfgFactor.isParmType() && cfgFactor.getId() == addVol) {
                                                        if (cfgFactor.getName().equals("高锰酸盐指数")) {
                                                            ++iParam;
                                                        } else if (cfgFactor.getName().equals("总氮")) {
                                                            iParam += 16;
                                                        } else if (cfgFactor.getName().equals("总磷")) {
                                                            iParam += 256;
                                                        } else if (cfgFactor.getName().equals("氨氮")) {
                                                            iParam += 4096;
                                                        }
                                                        break;
                                                    }
                                                }
                                            }

                                            devService.doDevCmd(7, iParam + "", (String) null);
                                            continue label375;
                                        }
                                    case 5:
                                        if (cmdType == 4000) {
                                            var7 = factorList.iterator();

                                            Iterator var9;
                                            String var11;
                                            byte var12;
                                            label391:
                                            while (var7.hasNext()) {
                                                factorId = (Integer) var7.next();
                                                if (devService.getRelatedID() == this.getDevID(factorId)) {
                                                    var9 = this.cfgFactorList.iterator();

                                                    do {
                                                        if (!var9.hasNext()) {
                                                            break label391;
                                                        }

                                                        cfgFactor = (CfgFactor) var9.next();
                                                    } while (cfgFactor.getId() != factorId);

                                                    var11 = cfgFactor.getName();
                                                    var12 = -1;
                                                    switch (var11.hashCode()) {
                                                        case -1255953671:
                                                            if (var11.equals("高锰酸盐指数")) {
                                                                var12 = 3;
                                                            }
                                                            break;
                                                        case -1096450952:
                                                            if (var11.equals("化学需氧量")) {
                                                                var12 = 4;
                                                            }
                                                            break;
                                                        case 791379:
                                                            if (var11.equals("总氮")) {
                                                                var12 = 1;
                                                            }
                                                            break;
                                                        case 794652:
                                                            if (var11.equals("总磷")) {
                                                                var12 = 0;
                                                            }
                                                            break;
                                                        case 886022:
                                                            if (var11.equals("氨氮")) {
                                                                var12 = 2;
                                                            }
                                                    }

                                                    switch (var12) {
                                                        case 0:
                                                            devService.doDevCmd(3100, cfgFactor.getRcvrDestVal() + "", (String) null);
                                                            this.saveRecLogSys("系统日志", devService.getDevName() + "质控设备　设置" + cfgFactor.getName() + "加标浓度 " + cfgFactor.getRcvrDestVal());
                                                            break label391;
                                                        case 1:
                                                            devService.doDevCmd(3200, cfgFactor.getRcvrDestVal() + "", (String) null);
                                                            this.saveRecLogSys("系统日志", devService.getDevName() + "质控设备　设置" + cfgFactor.getName() + "加标浓度 " + cfgFactor.getRcvrDestVal());
                                                            break label391;
                                                        case 2:
                                                            devService.doDevCmd(3300, cfgFactor.getRcvrDestVal() + "", (String) null);
                                                            this.saveRecLogSys("系统日志", devService.getDevName() + "质控设备　设置" + cfgFactor.getName() + "加标浓度 " + cfgFactor.getRcvrDestVal());
                                                            break label391;
                                                        case 3:
                                                        case 4:
                                                            devService.doDevCmd(3400, cfgFactor.getRcvrDestVal() + "", (String) null);
                                                            this.saveRecLogSys("系统日志", devService.getDevName() + "质控设备　设置" + cfgFactor.getName() + "加标浓度 " + cfgFactor.getRcvrDestVal());
                                                            // fall through
                                                        default:
                                                            break label391;
                                                    }
                                                }
                                            }

                                            var7 = factorList.iterator();

                                            while (var7.hasNext()) {
                                                factorId = (Integer) var7.next();
                                                if (devService.getRelatedID2() == this.getDevID(factorId)) {
                                                    var9 = this.cfgFactorList.iterator();

                                                    while (var9.hasNext()) {
                                                        cfgFactor = (CfgFactor) var9.next();
                                                        if (cfgFactor.getId() == factorId) {
                                                            var11 = cfgFactor.getName();
                                                            var12 = -1;
                                                            switch (var11.hashCode()) {
                                                                case -1255953671:
                                                                    if (var11.equals("高锰酸盐指数")) {
                                                                        var12 = 3;
                                                                    }
                                                                    break;
                                                                case -1096450952:
                                                                    if (var11.equals("化学需氧量")) {
                                                                        var12 = 4;
                                                                    }
                                                                    break;
                                                                case 791379:
                                                                    if (var11.equals("总氮")) {
                                                                        var12 = 1;
                                                                    }
                                                                    break;
                                                                case 794652:
                                                                    if (var11.equals("总磷")) {
                                                                        var12 = 0;
                                                                    }
                                                                    break;
                                                                case 886022:
                                                                    if (var11.equals("氨氮")) {
                                                                        var12 = 2;
                                                                    }
                                                            }

                                                            switch (var12) {
                                                                case 0:
                                                                    devService.doDevCmd(3100, cfgFactor.getRcvrDestVal() + "", (String) null);
                                                                    this.saveRecLogSys("系统日志", devService.getDevName() + "质控设备　设置" + cfgFactor.getName() + "加标浓度 " + cfgFactor.getRcvrDestVal());
                                                                    continue label375;
                                                                case 1:
                                                                    devService.doDevCmd(3200, cfgFactor.getRcvrDestVal() + "", (String) null);
                                                                    this.saveRecLogSys("系统日志", devService.getDevName() + "质控设备　设置" + cfgFactor.getName() + "加标浓度 " + cfgFactor.getRcvrDestVal());
                                                                    continue label375;
                                                                case 2:
                                                                    devService.doDevCmd(3300, cfgFactor.getRcvrDestVal() + "", (String) null);
                                                                    this.saveRecLogSys("系统日志", devService.getDevName() + "质控设备　设置" + cfgFactor.getName() + "加标浓度 " + cfgFactor.getRcvrDestVal());
                                                                    continue label375;
                                                                case 3:
                                                                case 4:
                                                                    devService.doDevCmd(3400, cfgFactor.getRcvrDestVal() + "", (String) null);
                                                                    this.saveRecLogSys("系统日志", devService.getDevName() + "质控设备　设置" + cfgFactor.getName() + "加标浓度 " + cfgFactor.getRcvrDestVal());
                                                                    continue label375;
                                                            }
                                                        }
                                                    }
                                                    break;
                                                }
                                            }
                                        } else if (cmdType == 5003) {
                                            devService.doDevCmd(3008, "3", (String) null);
                                            this.saveRecLogSys("系统日志", devService.getDevName() + "质控设备　启动" + Utility.getTaskName(cmdType));
                                        }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void startDevFive(int cmdType) {
        Iterator var2 = this.devServiceList.iterator();

        while (var2.hasNext()) {
            DevService devService = (DevService) var2.next();
            if (devService.getDevType().toUpperCase().equals("FIVE")) {
                try {
                    devService.doDevCmd(cmdType, (String) null, (String) null);
                    Thread.sleep(100L);
                } catch (Exception var5) {
                    Utility.logInfo("仪器设备　启动" + Utility.getTaskName(cmdType) + "异常");
                }
            }
        }

    }

    public boolean checkDevRelateState(DevService devService) {
        if (!devService.getDevType().toUpperCase().equals("PARM")) {
            return true;
        } else {
            Iterator var2 = this.cfgDevList.iterator();

            CfgDev cfgDev;
            do {
                if (!var2.hasNext()) {
                    return true;
                }

                cfgDev = (CfgDev) var2.next();
            } while (cfgDev.getId() != devService.getDevID() || cfgDev.getRelated_id() < 10000 || !this.scriptService.getIStatus(cfgDev.getRelated_id() - 10000));

            return false;
        }
    }

    public void startDev(int cmdType, String keyName, boolean bContain) {
        List<Integer> startIDList = new ArrayList();
        int checkParam = (int) ScriptWord.getInstance().getSysVar("CheckParam");
        Iterator var7 = this.devServiceList.iterator();

        while (true) {
            DevService devService;
            Integer factorId;
            Iterator var11;
            label110:
            do {
                while (true) {
                    while (true) {
                        do {
                            label76:
                            do {
                                while (var7.hasNext()) {
                                    devService = (DevService) var7.next();
                                    if (cmdType != 1) {
                                        continue label76;
                                    }

                                    if (bContain) {
                                        if (!devService.mCfgDev.getProtocol().toLowerCase().contains(keyName)) {
                                            continue;
                                        }
                                    } else if (devService.mCfgDev.getProtocol().toLowerCase().contains(keyName)) {
                                        continue;
                                    }
                                    continue label76;
                                }

                                return;
                            } while (checkParam > 0 && devService.isFaultState());
                        } while (!this.checkDevRelateState(devService));

                        for (int i = 0; i < this.scriptInfo.getFactorList().size(); ++i) {
                            if (!(Boolean) this.scriptInfo.getCmdRunList().get(i) || cmdType == 6 || cmdType == 7) {
                                factorId = (Integer) this.scriptInfo.getFactorList().get(i);
                                if (devService.getFactorID() == factorId) {
                                    boolean bCheck = false;
                                    var11 = startIDList.iterator();

                                    while (var11.hasNext()) {
                                        Integer startID = (Integer) var11.next();
                                        if (devService.getDevID() == startID) {
                                            bCheck = true;
                                            break;
                                        }
                                    }

                                    if (!bCheck) {
                                        startIDList.add(devService.getDevID());
                                        continue label110;
                                    }

                                    if (devService.getDevType().toUpperCase().equals("PARM")) {
                                        devService.setmCmdType(cmdType);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            } while (!devService.getDevType().toUpperCase().equals("PARM"));

            try {
                if (this.cal_time) {
                    devService.doDevCmd(13, (String) null, (String) null);
                    Thread.sleep(1000L);
                }

                devService.doDevCmd(cmdType, (String) null, (String) null);
                this.saveRecLogSys("系统日志", devService.getDevName() + "仪器设备　启动" + Utility.getTaskName(cmdType));
                this.saveRecLogDev(this.scriptInfo.getRunCmd(), factorId, true);
            } catch (Exception var15) {
                Utility.logInfo("仪器设备　启动" + Utility.getTaskName(cmdType) + "异常");
            }

            var11 = this.devServiceList.iterator();

            while (var11.hasNext()) {
                DevService devQuality = (DevService) var11.next();
                if (devQuality.getRelatedID() == devService.getDevID() && devQuality.getDevType().equals("QUALITY")) {
                    try {
                        devQuality.doDevCmd(cmdType, (String) null, (String) null);
                        this.saveRecLogSys("系统日志", devService.getDevName() + "质控设备　启动" + Utility.getTaskName(cmdType));
                    } catch (Exception var14) {
                        Utility.logInfo("质控设备　启动" + Utility.getTaskName(cmdType) + "异常");
                    }
                }
            }
        }
    }

    public void startDevRcvr(int cmdType, String keyName, boolean bContain) {
        List<Integer> startIDList = new ArrayList();
        int checkParam = (int) ScriptWord.getInstance().getSysVar("CheckParam");
        Iterator var7 = this.devServiceList.iterator();

        while (true) {
            DevService devService;
            Integer factorId;
            Iterator var11;
            label94:
            do {
                while (true) {
                    while (true) {
                        do {
                            label65:
                            do {
                                while (var7.hasNext()) {
                                    devService = (DevService) var7.next();
                                    if (bContain) {
                                        if (!devService.mCfgDev.getProtocol().toLowerCase().contains(keyName)) {
                                            continue;
                                        }
                                    } else if (devService.mCfgDev.getProtocol().toLowerCase().contains(keyName)) {
                                        continue;
                                    }
                                    continue label65;
                                }

                                return;
                            } while (checkParam > 0 && devService.isFaultState());
                        } while (!this.checkDevRelateState(devService));

                        for (int i = 0; i < this.scriptInfo.getFactorList2().size(); ++i) {
                            factorId = (Integer) this.scriptInfo.getFactorList2().get(i);
                            if (devService.getFactorID() == factorId) {
                                boolean bCheck = false;
                                var11 = startIDList.iterator();

                                while (var11.hasNext()) {
                                    Integer startID = (Integer) var11.next();
                                    if (devService.getDevID() == startID) {
                                        bCheck = true;
                                        break;
                                    }
                                }

                                if (!bCheck) {
                                    startIDList.add(devService.getDevID());
                                    continue label94;
                                }

                                if (devService.getDevType().toUpperCase().equals("PARM")) {
                                    devService.setmCmdType(cmdType);
                                }
                                break;
                            }
                        }
                    }
                }
            } while (!devService.getDevType().toUpperCase().equals("PARM"));

            try {
                devService.doDevCmd(cmdType, (String) null, (String) null);
                this.saveRecLogSys("系统日志", devService.getDevName() + "仪器设备　启动" + Utility.getTaskName(cmdType));
                this.saveRecLogDev(this.scriptInfo.getRunCmd(), factorId, true);
            } catch (Exception var15) {
                Utility.logInfo("仪器设备　启动" + Utility.getTaskName(cmdType) + "异常");
            }

            var11 = this.devServiceList.iterator();

            while (var11.hasNext()) {
                DevService devQuality = (DevService) var11.next();
                if (devQuality.getRelatedID() == devService.getDevID() && devQuality.getDevType().equals("QUALITY")) {
                    try {
                        devQuality.doDevCmd(cmdType, (String) null, (String) null);
                        this.saveRecLogSys("系统日志", devService.getDevName() + "质控设备　启动" + Utility.getTaskName(cmdType));
                    } catch (Exception var14) {
                        Utility.logInfo("质控设备　启动" + Utility.getTaskName(cmdType) + "异常");
                    }
                }
            }
        }
    }

    public void startDevRange(int cmdType) {
        List<Integer> startIDList = new ArrayList();
        Iterator var4 = this.devServiceList.iterator();

        while (true) {
            DevService devService;
            Iterator var8;
            label73:
            do {
                while (true) {
                    while (true) {
                        do {
                            if (!var4.hasNext()) {
                                return;
                            }

                            devService = (DevService) var4.next();
                        } while (!devService.getDevType().toUpperCase().equals("PARM"));

                        Iterator var6 = this.cfgFactorList.iterator();

                        while (var6.hasNext()) {
                            CfgFactor cfgFactor = (CfgFactor) var6.next();
                            if (cfgFactor.getId() == devService.getFactorID() && cfgFactor.isUsed() && cfgFactor.isRangeChange()) {
                                boolean bCheck = false;
                                var8 = startIDList.iterator();

                                while (var8.hasNext()) {
                                    Integer startID = (Integer) var8.next();
                                    if (devService.getDevID() == startID) {
                                        bCheck = true;
                                        break;
                                    }
                                }

                                if (!bCheck) {
                                    startIDList.add(devService.getDevID());
                                    continue label73;
                                }

                                devService.setmCmdType(cmdType);
                                break;
                            }
                        }
                    }
                }
            } while (!devService.getDevType().toUpperCase().equals("PARM"));

            try {
                devService.doDevCmd(cmdType, (String) null, (String) null);
                this.saveRecLogSys("系统日志", devService.getDevName() + "仪器设备　启动" + Utility.getTaskName(cmdType));
            } catch (Exception var12) {
                Utility.logInfo("仪器设备　启动" + Utility.getTaskName(cmdType) + "异常");
            }

            var8 = this.devServiceList.iterator();

            while (var8.hasNext()) {
                DevService devQuality = (DevService) var8.next();
                if (devQuality.getRelatedID() == devService.getDevID() && devQuality.getDevType().equals("QUALITY")) {
                    try {
                        devQuality.doDevCmd(cmdType, (String) null, (String) null);
                        this.saveRecLogSys("系统日志", devService.getDevName() + "质控设备　启动" + Utility.getTaskName(cmdType));
                    } catch (Exception var11) {
                        Utility.logInfo("质控设备　启动" + Utility.getTaskName(cmdType) + "异常");
                    }
                }
            }
        }
    }

    public void resetDevState() {
        Iterator var1 = this.devServiceList.iterator();

        while (true) {
            while (var1.hasNext()) {
                DevService devService = (DevService) var1.next();

                for (int i = 0; i < this.scriptInfo.getFactorList().size(); ++i) {
                    Integer factorId = (Integer) this.scriptInfo.getFactorList().get(i);
                    if (devService.getFactorID() == factorId) {
                        devService.initDevState(0);
                        break;
                    }
                }
            }

            return;
        }
    }

    public boolean runDev(int devID, int cmdType) {
        boolean bResult = false;
        boolean bSend = false;
        Iterator var5 = this.devServiceList.iterator();

        while (true) {
            while (true) {
                DevService devService;
                do {
                    if (!var5.hasNext()) {
                        return bResult;
                    }

                    devService = (DevService) var5.next();
                } while (devService.getDevID() != devID);

                if (!bSend) {
                    bSend = true;
                    bResult = devService.doDevCmd(cmdType, (String) null, (String) null);
                    bResult |= this.doDevSwitch(devService, cmdType);
                    this.saveRecLogSys("系统日志", devService.getDevName() + "仪器设备　启动" + Utility.getTaskName(cmdType));
                    Iterator var7 = this.devServiceList.iterator();

                    while (var7.hasNext()) {
                        DevService devQuality = (DevService) var7.next();
                        if (devQuality.getRelatedID() == devService.getDevID() && devQuality.getDevType().equals("QUALITY")) {
                            devQuality.doDevCmd(cmdType, (String) null, (String) null);
                            this.saveRecLogSys("系统日志", devService.getDevName() + "质控设备　启动" + Utility.getTaskName(cmdType));
                        }
                    }
                } else if (devService.getDevType().toUpperCase().equals("PARM")) {
                    devService.setmCmdType(cmdType);
                }
            }
        }
    }

    private boolean doDevSwitch(DevService devService, int cmdType) {
        boolean bResult = false;
        if (cmdType < 8) {
            try {
                String var4 = devService.getDevProtocol();
                byte var5 = -1;
                switch (var4.hashCode()) {
                    case -901346975:
                        if (var4.equals("tnp.DevTnpDkk150")) {
                            var5 = 0;
                        }
                        break;
                    case -637713525:
                        if (var4.equals("cod.DevCodDkk")) {
                            var5 = 1;
                        }
                        break;
                    case 104474979:
                        if (var4.equals("DevVocModernWater")) {
                            var5 = 2;
                        }
                }

                switch (var5) {
                    case 0:
                        bResult = this.scriptService.doCmd(1, "总磷总氮继电器");
                        Thread.sleep(3000L);
                        bResult &= this.scriptService.doCmd(0, "总磷总氮继电器");
                        break;
                    case 1:
                        bResult = this.scriptService.doCmd(1, "高锰酸盐继电器");
                        Thread.sleep(3000L);
                        bResult &= this.scriptService.doCmd(0, "高锰酸盐继电器");
                        break;
                    case 2:
                        bResult = this.scriptService.doCmd(1, "VOC继电器");
                        Thread.sleep(3000L);
                        bResult &= this.scriptService.doCmd(0, "VOC继电器");
                }
            } catch (Exception var6) {
            }
        }

        return bResult;
    }

    public boolean doDevCmdParam(int devID, int cmdType, int devParam) {
        boolean bResult = false;
        Iterator var5 = this.devServiceList.iterator();

        while (var5.hasNext()) {
            DevService devService = (DevService) var5.next();
            if (devService.getDevID() == devID) {
                bResult = devService.doDevCmd(cmdType, devParam + "", (String) null);
                this.saveRecLogSys("系统日志", devService.getDevName() + Utility.getTaskName(cmdType) + " " + devParam);
                break;
            }
        }

        return bResult;
    }

    public DevService getDevQuality(int devId) {
        Iterator var2 = this.devServiceList.iterator();

        DevService devQuality;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            devQuality = (DevService) var2.next();
        } while (devQuality.getRelatedID() != devId || !devQuality.getDevType().equals("QUALITY"));

        return devQuality;
    }

    public boolean doDevCmd(int devID, int cmdType, String dataFlag) {
        Timestamp saveTime = TimeHelper.getFormatSecondTimestamp();
        return this.doDevCmd(devID, cmdType, dataFlag, saveTime);
    }

    public boolean doDevCmd(int devID, int cmdType, String dataFlag, Timestamp timestamp) {
        boolean bResult = false;
        boolean bSend = false;
        int delayTime = 0;
        Timestamp saveTime = timestamp;
        if (timestamp == null) {
            saveTime = TimeHelper.getFormatSecondTimestamp();
        }

        String strDesc;
        if (dataFlag.equals("N")) {
            strDesc = "";
        } else if (dataFlag.equals("hd")) {
            strDesc = "手动";
        } else {
            strDesc = "平台";
        }

        Iterator var11 = this.devServiceList.iterator();

        while (true) {
            DevService devService;
            do {
                if (!var11.hasNext()) {
                    return bResult;
                }

                devService = (DevService) var11.next();
            } while (devService.getDevID() != devID);

            int checkParam = (int) ScriptWord.getInstance().getSysVar("CheckParam");
            if (checkParam > 0 && cmdType < 8) {
                if (devService.isFaultState()) {
                    return false;
                }

                if (!this.checkDevRelateState(devService)) {
                    return false;
                }
            }

            this.saveRecLogDev(cmdType, devService.getFactorID(), true);
            if (!bSend) {
                try {
                    if (cmdType < 11 && this.cal_time) {
                        devService.doDevCmd(13, (String) null, (String) null);
                        this.saveRecLogSys("系统日志", strDesc + "启动" + devService.getDevName() + Utility.getTaskName(cmdType));
                        Thread.sleep(1000L);
                    }
                } catch (Exception var17) {
                }

                bResult = devService.doDevCmd(cmdType, (String) null, (String) null);
                bResult |= this.doDevSwitch(devService, cmdType);
            } else if (devService.getDevType().toUpperCase().equals("PARM")) {
                devService.setmCmdType(cmdType);
            }

            Iterator var14;
            DevService devQuality;
            if (cmdType < 8 && bResult) {
                int waitTime;
                switch (cmdType) {
                    case 1:
                        waitTime = (int) ScriptWord.getInstance().getSysVar("MeaWaitTime");
                        break;
                    case 2:
                        waitTime = (int) ScriptWord.getInstance().getSysVar("StdWaitTime");
                        break;
                    case 3:
                        waitTime = (int) ScriptWord.getInstance().getSysVar("ZeroWaitTime");
                        break;
                    case 4:
                        waitTime = (int) ScriptWord.getInstance().getSysVar("SpanWaitTime");
                        break;
                    case 5:
                        waitTime = (int) ScriptWord.getInstance().getSysVar("ZeroWaitTime");
                        break;
                    case 6:
                        waitTime = (int) ScriptWord.getInstance().getSysVar("ParalWaitTime");
                        break;
                    case 7:
                        waitTime = (int) ScriptWord.getInstance().getSysVar("RcvrWaitTime");
                        break;
                    default:
                        waitTime = (int) ScriptWord.getInstance().getSysVar("RcvrWaitTime");
                }

                if (waitTime > this.waitDev) {
                    this.waitDev = waitTime;
                }

                devService.setWaitTime(waitTime);
                devService.setWaitExit(false);
                devService.setWaitStatus(this.getSysStatus(cmdType));
                this.sysStatus = devService.getDevName() + devService.getWaitStatus();
                if (!bSend) {
                    var14 = this.devServiceList.iterator();

                    while (var14.hasNext()) {
                        devQuality = (DevService) var14.next();
                        if (devQuality.getRelatedID() == devService.getDevID() && devQuality.getDevType().equals("QUALITY")) {
                            devQuality.doDevCmd(cmdType, (String) null, (String) null);
                            this.saveRecLogSys("系统日志", strDesc + "启动" + devService.getDevName() + "质控" + Utility.getTaskName(cmdType));
                        }
                    }
                }

                devService.setRunState(true);
                MonitorService.SaveDevDataThread saveDevDataThread = new MonitorService.SaveDevDataThread(devService, cmdType, saveTime, delayTime, dataFlag);
                saveDevDataThread.start();
            }

            if (cmdType == 11) {
                devService.setWaitExit(true);
                devService.setWaitTime(0);
                devService.setWaitStatus("系统待机");
                devService.setRunState(false);
                if (!bSend) {
                    this.saveRecLogSys("系统日志", strDesc + "停止" + devService.getDevName());
                    var14 = this.devServiceList.iterator();

                    while (var14.hasNext()) {
                        devQuality = (DevService) var14.next();
                        if (devQuality.getRelatedID() == devService.getDevID() && devQuality.getDevType().equals("QUALITY")) {
                            devQuality.doDevCmd(cmdType, (String) null, (String) null);
                            this.saveRecLogSys("系统日志", strDesc + "停止" + devService.getDevName() + "质控");
                        }
                    }
                }

                boolean bExist = false;
                Iterator var20 = this.devServiceList.iterator();

                while (var20.hasNext()) {
                    DevService devServ = (DevService) var20.next();
                    if (!devServ.isWaitExit()) {
                        bExist = true;
                        this.waitDev = devServ.getWaitTime();
                        this.sysStatus = devServ.getDevName() + devServ.getWaitStatus();
                        break;
                    }
                }

                if (!bExist) {
                    this.waitDev = 0;
                    this.sysStatus = "系统待机";
                }
            }

            if (!bSend) {
                this.rtdCount.setCountTitle(this.getDevName(devID) + Utility.getTaskName(cmdType));
                this.rtdCount.setCountValue(this.waitDev);
                bSend = true;
            }

            delayTime += 3;
        }
    }

    public void stopTest() {
        this.saveRecLogDev(this.scriptInfo.getRunCmd(), 0, false);
        this.sysStatus = "系统待机";
        this.scriptService.stopScript();
        Iterator var1 = this.cfgFactorList.iterator();

        while (var1.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor) var1.next();
            cfgFactor.setUpload(true);
        }

        var1 = this.devServiceList.iterator();

        DevService devService;
        while (var1.hasNext()) {
            devService = (DevService) var1.next();
            devService.setWaitExit(true);
            devService.setWaitTime(0);
            devService.setWaitStatus("");
        }

        var1 = this.devServiceList.iterator();

        while (var1.hasNext()) {
            devService = (DevService) var1.next();
            if (devService.getDevType().equals("PARM")) {
                devService.doStopCmd();
            }
        }

    }

    public void clearAlarm() {
    }

    public boolean doSysClean() {
        String tmpName = ScriptWord.getInstance().getScriptName(102);
        this.saveRecLogSys("系统日志", "启动系统清洗");
        return tmpName != null ? this.scriptService.startScript(tmpName) : false;
    }

    public boolean doOutPipeClean() {
        String tmpName = ScriptWord.getInstance().getScriptName(104);
        this.saveRecLogSys("系统日志", "启动外管路清洗");
        return tmpName != null ? this.scriptService.startScript(tmpName) : false;
    }

    public boolean doInPipeClean() {
        String tmpName = ScriptWord.getInstance().getScriptName(103);
        this.saveRecLogSys("系统日志", "启动内管路清洗");
        return tmpName != null ? this.scriptService.startScript(tmpName) : false;
    }

    public boolean doSandClean() {
        String tmpName = ScriptWord.getInstance().getScriptName(106);
        this.saveRecLogSys("系统日志", "启动沉砂池清洗");
        return tmpName != null ? this.scriptService.startScript(tmpName) : false;
    }

    public boolean doAlgClean() {
        String tmpName = ScriptWord.getInstance().getScriptName(101);
        this.saveRecLogSys("系统日志", "启动系统除藻");
        return tmpName != null ? this.scriptService.startScript(tmpName) : false;
    }

    public boolean doFiveClean() {
        String tmpName = ScriptWord.getInstance().getScriptName(107);
        this.saveRecLogSys("系统日志", "启动五参数池清洗");
        return tmpName != null ? this.scriptService.startScript(tmpName) : false;
    }

    public boolean doFilterClean() {
        String tmpName = ScriptWord.getInstance().getScriptName(105);
        this.saveRecLogSys("系统日志", "启动系统过滤器清洗");
        return tmpName != null ? this.scriptService.startScript(tmpName) : false;
    }

    public void setRange() {
        Iterator var2 = this.devServiceList.iterator();

        label52:
        while (true) {
            DevService devService;
            do {
                if (!var2.hasNext()) {
                    return;
                }

                devService = (DevService) var2.next();
            } while (!devService.isbConnect());

            Iterator var4 = this.cfgFactorList.iterator();

            while (true) {
                while (true) {
                    CfgFactor cfgFactor;
                    do {
                        do {
                            if (!var4.hasNext()) {
                                continue label52;
                            }

                            cfgFactor = (CfgFactor) var4.next();
                        } while (!cfgFactor.isUsed());
                    } while (cfgFactor.getId() != devService.getFactorID());

                    Double dTmp = (Double) devService.getParam("RangeTop");
                    if (dTmp != null) {
                        cfgFactor.setRangeMax(dTmp);
                    } else {
                        cfgFactor.setRangeMax(0.0D);
                    }

                    cfgFactor.setRangeChange(false);
                    Iterator var6 = this.scriptService.varWordList.iterator();

                    while (var6.hasNext()) {
                        VarWord varWord = (VarWord) var6.next();
                        if (varWord.getEnName().equals("RangeJudge")) {
                            varWord.setCurValue(0.0D);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void checkRange() {
        Iterator var2 = this.devServiceList.iterator();

        while (true) {
            while (true) {
                Double dTmp;
                CfgFactor cfgFactor;
                label48:
                do {
                    while (true) {
                        DevService devService;
                        do {
                            if (!var2.hasNext()) {
                                return;
                            }

                            devService = (DevService) var2.next();
                        } while (!devService.isbConnect());

                        Iterator var4 = this.cfgFactorList.iterator();

                        while (var4.hasNext()) {
                            cfgFactor = (CfgFactor) var4.next();
                            if (cfgFactor.isUsed() && cfgFactor.isParmType() && cfgFactor.getId() == devService.getFactorID()) {
                                dTmp = (Double) devService.getParam("RangeTop");
                                continue label48;
                            }
                        }
                    }
                } while (dTmp == null);

                if (dTmp == cfgFactor.getRangeMax()) {
                    cfgFactor.setRangeChange(false);
                } else {
                    cfgFactor.setRangeChange(true);
                }

                Iterator var6 = this.scriptService.varWordList.iterator();

                while (var6.hasNext()) {
                    VarWord varWord = (VarWord) var6.next();
                    if (varWord.getEnName().equals("RangeJudge")) {
                        if (cfgFactor.isRangeChange()) {
                            varWord.setCurValue(1.0D);
                            this.scriptService.refreshVar();
                        }
                        break;
                    }
                }
            }
        }
    }

    private void saveDevState(Timestamp timestamp) {
        Iterator var6 = this.devServiceList.iterator();

        while (true) {
            DevService devService;
            Iterator var8;
            do {
                if (!var6.hasNext()) {
                    var6 = this.cfgFactorList.iterator();

                    while (true) {
                        while (true) {
                            CfgFactor cfgFactor;
                            do {
                                if (!var6.hasNext()) {
                                    this.writePlcRealtime();
                                    return;
                                }

                                cfgFactor = (CfgFactor) var6.next();
                            } while (cfgFactor.getDevID() != 1);

                            var8 = this.scriptService.defWordList.iterator();

                            while (var8.hasNext()) {
                                DefWord defWord = (DefWord) var8.next();
                                if (cfgFactor.getName().equals(defWord.getName()) && defWord.getType() == 4) {
                                    String strVal = Utility.getFormatDouble((double) defWord.getCurvalue() * cfgFactor.getUnitParam(), cfgFactor.getDecimals()) + "";
                                    cfgFactor.setDataMea(Double.parseDouble(strVal));
                                    break;
                                }
                            }
                        }
                    }
                }

                devService = (DevService) var6.next();
            } while (!devService.isbConnect());

            try {
                if (this.refreshFactorData) {
                    var8 = this.cfgFactorList.iterator();

                    Double dTmp;
                    label165:
                    while (true) {
                        while (true) {
                            CfgFactor cfgFactor;
                            do {
                                if (!var8.hasNext()) {
                                    break label165;
                                }

                                cfgFactor = (CfgFactor) var8.next();
                            } while (!cfgFactor.isUsed());

                            if (cfgFactor.getDevID() == devService.getDevID() && cfgFactor.getDevChannel() > 0) {
                                dTmp = devService.getChannelVals(cfgFactor.getDevChannel());
                                if (dTmp != null) {
                                    dTmp = Utility.getFormatDouble(dTmp * cfgFactor.getUnitParam(), cfgFactor.getDecimals());
                                    cfgFactor.setDataMea(dTmp);
                                    cfgFactor.setFlagMea("N");
                                    cfgFactor.setDataStd(dTmp);
                                    cfgFactor.setDataZero(dTmp);
                                    cfgFactor.setDataSpan(dTmp);
                                    cfgFactor.setDataBlnk(dTmp);
                                    cfgFactor.setDataPar(dTmp);
                                    cfgFactor.setDataRcvr(dTmp);
                                }
                            } else if (cfgFactor.getId() == devService.getFactorID()) {
                                dTmp = (Double) devService.getParam("MeaTestVal");
                                if (dTmp != null) {
                                    dTmp = Utility.getFormatDouble(dTmp * cfgFactor.getUnitParam(), cfgFactor.getDecimals());
                                    Iterator var10 = this.scriptService.varWordList.iterator();

                                    while (var10.hasNext()) {
                                        VarWord varWord = (VarWord) var10.next();
                                        if (varWord.getEnName().equals("Factor" + cfgFactor.getId())) {
                                            varWord.setCurValue(dTmp);
                                            break;
                                        }
                                    }
                                }

                                String strTmp = (String) devService.getParam("MeaTestFlag");
                                Timestamp timeTmp = (Timestamp) devService.getParam("MeaTestTime");
                                cfgFactor.setDataMea(dTmp);
                                cfgFactor.setFlagMea(strTmp);
                                cfgFactor.setTimeMea(timeTmp);
                                dTmp = (Double) devService.getParam("StdTestVal");
                                if (dTmp != null) {
                                    dTmp = Utility.getFormatDouble(dTmp * cfgFactor.getUnitParam(), cfgFactor.getDecimals());
                                }

                                strTmp = (String) devService.getParam("StdTestFlag");
                                timeTmp = (Timestamp) devService.getParam("StdTestTime");
                                cfgFactor.setDataStd(dTmp);
                                cfgFactor.setFlagStd(strTmp);
                                cfgFactor.setTimeStd(timeTmp);
                                dTmp = (Double) devService.getParam("ZeroTestVal");
                                if (dTmp != null) {
                                    dTmp = Utility.getFormatDouble(dTmp * cfgFactor.getUnitParam(), cfgFactor.getDecimals());
                                }

                                strTmp = (String) devService.getParam("ZeroTestFlag");
                                timeTmp = (Timestamp) devService.getParam("ZeroTestTime");
                                cfgFactor.setDataZero(dTmp);
                                cfgFactor.setFlagZero(strTmp);
                                cfgFactor.setTimeZero(timeTmp);
                                dTmp = (Double) devService.getParam("SpanTestVal");
                                if (dTmp != null) {
                                    dTmp = Utility.getFormatDouble(dTmp * cfgFactor.getUnitParam(), cfgFactor.getDecimals());
                                }

                                strTmp = (String) devService.getParam("SpanTestFlag");
                                timeTmp = (Timestamp) devService.getParam("SpanTestTime");
                                cfgFactor.setDataSpan(dTmp);
                                cfgFactor.setFlagSpan(strTmp);
                                cfgFactor.setTimeSpan(timeTmp);
                                dTmp = (Double) devService.getParam("BlnkTestVal");
                                if (dTmp != null) {
                                    dTmp = Utility.getFormatDouble(dTmp * cfgFactor.getUnitParam(), cfgFactor.getDecimals());
                                }

                                strTmp = (String) devService.getParam("BlnkTestFlag");
                                timeTmp = (Timestamp) devService.getParam("BlnkTestTime");
                                cfgFactor.setDataBlnk(dTmp);
                                cfgFactor.setFlagBlnk(strTmp);
                                cfgFactor.setTimeBlnk(timeTmp);
                                dTmp = (Double) devService.getParam("ParTestVal");
                                if (dTmp != null) {
                                    dTmp = Utility.getFormatDouble(dTmp * cfgFactor.getUnitParam(), cfgFactor.getDecimals());
                                }

                                strTmp = (String) devService.getParam("ParTestFlag");
                                timeTmp = (Timestamp) devService.getParam("ParTestTime");
                                cfgFactor.setDataPar(dTmp);
                                cfgFactor.setFlagPar(strTmp);
                                cfgFactor.setTimePar(timeTmp);
                                dTmp = (Double) devService.getParam("RcvrTestVal");
                                if (dTmp != null) {
                                    dTmp = Utility.getFormatDouble(dTmp * cfgFactor.getUnitParam(), cfgFactor.getDecimals());
                                }

                                strTmp = (String) devService.getParam("RcvrTestFlag");
                                timeTmp = (Timestamp) devService.getParam("RcvrTestTime");
                                cfgFactor.setDataRcvr(dTmp);
                                cfgFactor.setFlagRcvr(strTmp);
                                cfgFactor.setTimeRcvr(timeTmp);
                                devService.refreshParam();
                                cfgFactor.setParamMap(devService.getParamMap());
                                break label165;
                            }
                        }
                    }

                    Double flowCou = null;
                    Iterator var16 = this.cfgFactorList.iterator();

                    CfgFactor cfgFactor;
                    while (var16.hasNext()) {
                        cfgFactor = (CfgFactor) var16.next();
                        if (cfgFactor.isUsed() && cfgFactor.getName().contains("瞬时流量") && cfgFactor.getDataMea() != null) {
                            if (flowCou == null) {
                                flowCou = cfgFactor.getDataMea();
                            } else {
                                flowCou = flowCou + cfgFactor.getDataMea();
                            }
                        }
                    }

                    var16 = this.cfgFactorList.iterator();

                    while (var16.hasNext()) {
                        cfgFactor = (CfgFactor) var16.next();
                        if (cfgFactor.getName().contains("总流量")) {
                            if (flowCou != null) {
                                dTmp = Utility.getFormatDouble(flowCou * cfgFactor.getUnitParam(), cfgFactor.getDecimals());
                                cfgFactor.setDataMea(dTmp);
                                cfgFactor.setFlagMea("N");
                                cfgFactor.setDataStd(dTmp);
                                cfgFactor.setDataZero(dTmp);
                                cfgFactor.setDataSpan(dTmp);
                                cfgFactor.setDataBlnk(dTmp);
                                cfgFactor.setDataPar(dTmp);
                                cfgFactor.setDataRcvr(dTmp);
                            } else {
                                cfgFactor.setDataMea((Double) null);
                            }
                        }
                    }
                }

                int checkParam = (int) ScriptWord.getInstance().getSysVar("CheckParam");
                if (checkParam <= 0) {
                    devService.resetFaultState();
                } else {
                    boolean[] faultState = this.checkDevParam(devService);

                    for (int i = 0; i < faultState.length; ++i) {
                        if (faultState[i] != devService.getFaultState(i)) {
                            if (faultState[i]) {
                                this.saveRecFaultSys("仪器故障", devService.mCfgDev.getName() + "　仪器关键参数 " + this.getDevParamName(i) + " 超限");
                            } else {
                                this.saveRecFaultSys("仪器故障", devService.mCfgDev.getName() + "　仪器关键参数 " + this.getDevParamName(i) + " 恢复");
                            }
                        }
                    }

                    devService.setFaultState(faultState);
                }
            } catch (Exception var12) {
                Utility.logInfo("设备" + devService.getDevID() + "数据入库异常");
            }
        }
    }

    private boolean[] checkDevParam(DevService devService) {
        Double dTmp = null;
        Double rangeMax = (Double) devService.getParam("RangeTop");
        Double rangeMin = (Double) devService.getParam("RangeBottom");
        boolean[] checkResult = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false};
        if (rangeMax != null && rangeMin != null) {
            Iterator var6 = this.cfgDevParamList.iterator();

            while (true) {
                CfgDevParam cfgDevParam;
                do {
                    label186:
                    do {
                        while (true) {
                            do {
                                do {
                                    if (!var6.hasNext()) {
                                        return checkResult;
                                    }

                                    cfgDevParam = (CfgDevParam) var6.next();
                                } while (cfgDevParam.getDev_id() != devService.getDevID());
                            } while ((cfgDevParam.getRange_max() != 0.0D || cfgDevParam.getRange_min() != 0.0D) && (!cfgDevParam.getRange_max().equals(rangeMax) || !cfgDevParam.getRange_min().equals(rangeMin)));

                            String var8 = cfgDevParam.getParam_name();
                            byte var9 = -1;
                            switch (var8.hashCode()) {
                                case -439426910:
                                    if (var8.equals("曲线截距b")) {
                                        var9 = 3;
                                    }
                                    break;
                                case -438788301:
                                    if (var8.equals("曲线斜率k")) {
                                        var9 = 2;
                                    }
                                    break;
                                case -333829947:
                                    if (var8.equals("量程校准滴定时间")) {
                                        var9 = 9;
                                    }
                                    break;
                                case -130351571:
                                    if (var8.equals("零点校准吸光度")) {
                                        var9 = 4;
                                    }
                                    break;
                                case 460592620:
                                    if (var8.equals("零点校准滴定时间")) {
                                        var9 = 8;
                                    }
                                    break;
                                case 812688818:
                                    if (var8.equals("显色时间")) {
                                        var9 = 6;
                                    }
                                    break;
                                case 812739729:
                                    if (var8.equals("显色温度")) {
                                        var9 = 7;
                                    }
                                    break;
                                case 870109529:
                                    if (var8.equals("消解时间")) {
                                        var9 = 1;
                                    }
                                    break;
                                case 870160440:
                                    if (var8.equals("消解温度")) {
                                        var9 = 0;
                                    }
                                    break;
                                case 1368042548:
                                    if (var8.equals("量程校准吸光度")) {
                                        var9 = 5;
                                    }
                                    break;
                                case 1633348477:
                                    if (var8.equals("校准曲线斜率")) {
                                        var9 = 10;
                                    }
                                    break;
                                case 1697286305:
                                    if (var8.equals("校准点吸光度")) {
                                        var9 = 11;
                                    }
                            }

                            switch (var9) {
                                case 0:
                                    dTmp = (double) (Integer) devService.getParam("DigTemp") + 0.0D;
                                    if (dTmp != null && (dTmp < cfgDevParam.getParam_min() || dTmp > cfgDevParam.getParam_max())) {
                                        checkResult[0] = true;
                                    }
                                    break;
                                case 1:
                                    dTmp = (double) (Integer) devService.getParam("DigTime") + 0.0D;
                                    if (dTmp != null && (dTmp < cfgDevParam.getParam_min() || dTmp > cfgDevParam.getParam_max())) {
                                        checkResult[1] = true;
                                    }
                                    break;
                                case 2:
                                    dTmp = (Double) devService.getParam("SlopeK");
                                    if (dTmp != null && (dTmp < cfgDevParam.getParam_min() || dTmp > cfgDevParam.getParam_max())) {
                                        checkResult[2] = true;
                                    }
                                    break;
                                case 3:
                                    dTmp = (Double) devService.getParam("InterceptB");
                                    if (dTmp != null && (dTmp < cfgDevParam.getParam_min() || dTmp > cfgDevParam.getParam_max())) {
                                        checkResult[3] = true;
                                    }
                                    break;
                                case 4:
                                    dTmp = (Double) devService.getParam("ProcVal1");
                                    if (dTmp != null && (dTmp < cfgDevParam.getParam_min() || dTmp > cfgDevParam.getParam_max())) {
                                        checkResult[4] = true;
                                    }
                                    break;
                                case 5:
                                    dTmp = (Double) devService.getParam("ProcVal2");
                                    if (dTmp != null && (dTmp < cfgDevParam.getParam_min() || dTmp > cfgDevParam.getParam_max())) {
                                        checkResult[5] = true;
                                    }
                                    break;
                                case 6:
                                    dTmp = (double) (Integer) devService.getParam("ColorTime") + 0.0D;
                                    if (dTmp != null && (dTmp < cfgDevParam.getParam_min() || dTmp > cfgDevParam.getParam_max())) {
                                        checkResult[6] = true;
                                    }
                                    break;
                                case 7:
                                    dTmp = (double) (Integer) devService.getParam("ColorTemp") + 0.0D;
                                    if (dTmp != null && (dTmp < cfgDevParam.getParam_min() || dTmp > cfgDevParam.getParam_max())) {
                                        checkResult[7] = true;
                                    }
                                    break;
                                case 8:
                                    dTmp = (Double) devService.getParam("ProcVal1");
                                    if (dTmp != null && (dTmp < cfgDevParam.getParam_min() || dTmp > cfgDevParam.getParam_max())) {
                                        checkResult[8] = true;
                                    }
                                    break;
                                case 9:
                                    dTmp = (Double) devService.getParam("ProcVal2");
                                    if (dTmp != null && (dTmp < cfgDevParam.getParam_min() || dTmp > cfgDevParam.getParam_max())) {
                                        checkResult[9] = true;
                                    }
                                    break;
                                case 10:
                                    dTmp = (Double) devService.getParam("SlopeK");
                                    if (dTmp != null && (dTmp < cfgDevParam.getParam_min() || dTmp > cfgDevParam.getParam_max())) {
                                        checkResult[10] = true;
                                    }
                                    break;
                                case 11:
                                    dTmp = (Double) devService.getParam("ProcVal2");
                                    continue label186;
                            }
                        }
                    } while (dTmp == null);
                } while (dTmp >= cfgDevParam.getParam_min() && dTmp <= cfgDevParam.getParam_max());

                checkResult[11] = true;
            }
        } else {
            return checkResult;
        }
    }

    private String getDevParamName(int index) {
        switch (index) {
            case 0:
                return "消解温度";
            case 1:
                return "消解时间";
            case 2:
                return "曲线斜率k";
            case 3:
                return "曲线截距b";
            case 4:
                return "零点校准吸光度";
            case 5:
                return "量程校准吸光度";
            case 6:
                return "显色时间";
            case 7:
                return "显色温度";
            case 8:
                return "零点校准滴定时间";
            case 9:
                return "量程校准滴定时间";
            case 10:
                return "校准曲线斜率";
            case 11:
                return "校准点吸光度";
            default:
                return "";
        }
    }

    private RecDataTime saveRecTimeDay(Timestamp timestamp) {
        RecDataTime recDataTime = this.recMapper.getRecTimeDayAvgByTime(timestamp);
        if (recDataTime == null) {
            recDataTime = new RecDataTime();
            recDataTime.setRecTime(timestamp);
            this.recMapper.addRecTimeDayAvg(recDataTime);
        } else if (recDataTime.getRecTime().getTime() != timestamp.getTime()) {
            recDataTime = new RecDataTime();
            recDataTime.setRecTime(timestamp);
            this.recMapper.addRecTimeDayAvg(recDataTime);
        }

        return recDataTime;
    }

    private RecDataTime saveRecTimeMonth(Timestamp timestamp) {
        RecDataTime recDataTime = this.recMapper.getRecTimeMonthAvgByTime(timestamp);
        if (recDataTime == null) {
            recDataTime = new RecDataTime();
            recDataTime.setRecTime(timestamp);
            this.recMapper.addRecTimeMonthAvg(recDataTime);
        } else if (recDataTime.getRecTime().getTime() != timestamp.getTime()) {
            recDataTime = new RecDataTime();
            recDataTime.setRecTime(timestamp);
            this.recMapper.addRecTimeMonthAvg(recDataTime);
        }

        return recDataTime;
    }

    private RecDataTime saveRecTime(Timestamp timestamp, int recType) {
        RecDataTime recDataTime;
        switch (recType) {
            case 0:
                recDataTime = this.recMapper.getRecTimeMinByTime(timestamp);
                break;
            case 1:
                recDataTime = this.recMapper.getRecTimeHourByTime(timestamp);
                break;
            case 2:
                recDataTime = this.recMapper.getRecCheckStdTimeByTime(timestamp);
                break;
            case 3:
                recDataTime = this.recMapper.getRecCheckZeroTimeByTime(timestamp);
                break;
            case 4:
                recDataTime = this.recMapper.getRecCheckSpanTimeByTime(timestamp);
                break;
            case 5:
                recDataTime = this.recMapper.getRecCheckBlankTimeByTime(timestamp);
                break;
            case 6:
                recDataTime = this.recMapper.getRecCheckParTimeByTime(timestamp);
                break;
            case 7:
                recDataTime = this.recMapper.getRecCheckRcvrTimeByTime(timestamp);
                break;
            case 100:
                recDataTime = this.recMapper.getRecTimeFiveByTime(timestamp);
                break;
            case 101:
                recDataTime = this.recMapper.getRecCheckFiveTimeByTime(timestamp);
                break;
            case 200:
                recDataTime = this.recMapper.getRecTimeMinAvgByTime(timestamp);
                break;
            case 201:
                recDataTime = this.recMapper.getRecTimeHourAvgByTime(timestamp);
                break;
            case 202:
                recDataTime = this.recMapper.getRecTimeDayAvgByTime(timestamp);
                break;
            case 300:
                recDataTime = this.recMapper.getRecTimeRangeByTime(timestamp);
                break;
            case 400:
                recDataTime = this.recMapper.getRecTimeFlowByTime(timestamp);
                break;
            case 450:
            case 451:
            case 452:
            case 453:
            case 454:
            case 455:
            case 456:
            case 457:
                recDataTime = this.recMapper.getRecTimeParamByTime(timestamp);
                break;
            default:
                return null;
        }

        if (recDataTime == null) {
            recDataTime = new RecDataTime();
            recDataTime.setRecTime(timestamp);
            this.addRecTime(recDataTime, recType);
        } else if (recDataTime.getRecTime().getTime() != timestamp.getTime()) {
            recDataTime = new RecDataTime();
            recDataTime.setRecTime(timestamp);
            this.addRecTime(recDataTime, recType);
        }

        return recDataTime;
    }

    private void addRecTime(RecDataTime recDataTime, int recType) {
        switch (recType) {
            case 0:
                this.recMapper.addRecTimeMin(recDataTime);
                break;
            case 1:
                this.recMapper.addRecTimeHour(recDataTime);
                break;
            case 2:
                this.recMapper.addRecCheckStdTime(recDataTime);
                break;
            case 3:
                this.recMapper.addRecCheckZeroTime(recDataTime);
                break;
            case 4:
                this.recMapper.addRecCheckSpanTime(recDataTime);
                break;
            case 5:
                this.recMapper.addRecCheckBlankTime(recDataTime);
                break;
            case 6:
                this.recMapper.addRecCheckParTime(recDataTime);
                break;
            case 7:
                this.recMapper.addRecCheckRcvrTime(recDataTime);
                break;
            case 100:
                this.recMapper.addRecTimeFive(recDataTime);
                break;
            case 101:
                this.recMapper.addRecCheckFiveTime(recDataTime);
                break;
            case 200:
                this.recMapper.addRecTimeMinAvg(recDataTime);
                break;
            case 201:
                this.recMapper.addRecTimeHourAvg(recDataTime);
                break;
            case 202:
                this.recMapper.addRecTimeDayAvg(recDataTime);
                break;
            case 300:
                this.recMapper.addRecTimeRange(recDataTime);
                break;
            case 400:
                this.recMapper.addRecTimeFlow(recDataTime);
                break;
            case 450:
            case 451:
            case 452:
            case 453:
            case 454:
            case 455:
            case 456:
            case 457:
                this.recMapper.addRecTimeParam(recDataTime);
        }

    }

    private void saveRecData(ScriptInfo scriptInfo, int recType, int factorID) {
        RecDataTime recDataTime = this.saveRecTime(scriptInfo.getRunTime(), recType);
        Iterator var5 = this.cfgFactorList.iterator();

        while (true) {
            CfgFactor cfgFactor;
            RecDataFactor recDataFactor;
            do {
                do {
                    do {
                        do {
                            if (!var5.hasNext()) {
                                return;
                            }

                            cfgFactor = (CfgFactor) var5.next();
                        } while (!cfgFactor.isUsed());
                    } while (!cfgFactor.isFiveType() && !cfgFactor.isParmType() && !cfgFactor.isSwType());
                } while (cfgFactor.getId() != factorID && factorID != 0);

                recDataFactor = this.getRecDataFactor(cfgFactor, recDataTime, recType);
            } while (recDataFactor == null);

            try {
                if (!cfgFactor.isUpload()) {
                    recDataFactor.setAlarmType(99);
                }

                switch (recType) {
                    case 0:
                        recDataFactor.setDataFlag("N");
                        this.recMapper.addRecDataMin(recDataFactor);
                        break;
                    case 1:
                        recDataFactor.setDataFlag(scriptInfo.getSaveType());
                        if (scriptInfo.getSaveType().equals("N")) {
                            if (cfgFactor.getFlagMea() != null && cfgFactor.getFlagMea().length() > 0) {
                                recDataFactor.setDataFlag(cfgFactor.getFlagMea());
                            }
                        } else if (cfgFactor.getFlagMea() != null && (cfgFactor.getFlagMea().equalsIgnoreCase("ii") || cfgFactor.getFlagMea().equalsIgnoreCase("iic"))) {
                            recDataFactor.setDataFlag(cfgFactor.getFlagMea());
                        }

                        if (this.time_check && cfgFactor.getTimeMea() != null && cfgFactor.getTimeMea().before(TimeHelper.getMinutesAgo(recDataTime.getRecTime(), 10))) {
                            recDataFactor.setDataFlag("D");
                        }

                        if (!this.save_flag) {
                            if (recDataFactor.getDataFlag() == null) {
                                recDataFactor.setDataFlag("N");
                            } else if (!recDataFactor.getDataFlag().equalsIgnoreCase("hd") && !recDataFactor.getDataFlag().equalsIgnoreCase("D")) {
                                recDataFactor.setDataFlag("N");
                            }
                        }

                        this.recMapper.addRecDataHour(recDataFactor);
                }
            } catch (Exception var9) {
                Utility.logInfo("因子" + cfgFactor.getName() + "数据入库异常");
            }
        }
    }

    private void saveRecParam(Timestamp recTime, int recType, int factorID) {
        RecDataTime recDataTime = this.saveRecTime(recTime, recType);
        Iterator var5 = this.cfgFactorList.iterator();

        while (true) {
            CfgFactor cfgFactor;
            do {
                do {
                    do {
                        if (!var5.hasNext()) {
                            return;
                        }

                        cfgFactor = (CfgFactor) var5.next();
                    } while (!cfgFactor.isUsed());
                } while (cfgFactor.getId() != factorID && factorID != 0);
            } while (cfgFactor.getParamMap() == null);

            Iterator var7 = cfgFactor.getParamMap().entrySet().iterator();

            while (var7.hasNext()) {
                Entry<String, String> entry = (Entry) var7.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                RecDevParam recDevParam = new RecDevParam();
                recDevParam.setRec_id(recDataTime.getId());
                recDevParam.setRec_time(recDataTime.getRecTime());
                recDevParam.setFactor_id(cfgFactor.getId());
                recDevParam.setParam_name(key);
                recDevParam.setParam_value(value);
                recDevParam.setParam_type(recType);

                try {
                    switch (recType) {
                        case 450:
                            if (cfgFactor.isFiveType()) {
                                this.recMapper.addRecDataParam(recDevParam);
                            }
                            break;
                        default:
                            if (cfgFactor.isParmType()) {
                                this.recMapper.addRecDataParam(recDevParam);
                            }
                    }
                } catch (Exception var13) {
                }
            }
        }
    }

    public int getDevFlowId() {
        Iterator var1 = this.cfgDevList.iterator();

        CfgDev cfgDev;
        do {
            if (!var1.hasNext()) {
                return 0;
            }

            cfgDev = (CfgDev) var1.next();
        } while (!cfgDev.getProtocol().equals("DevFlowLinkQuest"));

        return cfgDev.getId();
    }

    private void saveRecDataRange(Timestamp recTime) {
        RecDataTime recDataTime = this.saveRecTime(recTime, 300);
        Iterator var3 = this.cfgFactorList.iterator();

        while (var3.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor) var3.next();
            if (cfgFactor.isUsed() && cfgFactor.isParmType()) {
                RecDataFactor recDataFactor = this.getRecDataFactor(cfgFactor, recDataTime, 300);
                if (recDataFactor != null) {
                    try {
                        recDataFactor.setDataFlag("N");
                        this.recMapper.addRecDataRange(recDataFactor);
                    } catch (Exception var7) {
                        Utility.logInfo("因子" + cfgFactor.getName() + "数据入库异常");
                    }
                }
            }
        }

    }

    private void saveRecDataFlow(Timestamp recTime) {
        RecDataTime recDataTime = this.saveRecTime(recTime, 400);
        Iterator var3 = this.cfgFactorList.iterator();

        while (var3.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor) var3.next();
            if (cfgFactor.isUsed() && cfgFactor.isSwType()) {
                RecDataFactor recDataFactor = this.getRecDataFactor(cfgFactor, recDataTime, 400);
                if (recDataFactor != null) {
                    try {
                        recDataFactor.setDataFlag("N");
                        this.recMapper.addRecDataFlow(recDataFactor);
                    } catch (Exception var7) {
                        Utility.logInfo("因子" + cfgFactor.getName() + "数据入库异常");
                    }
                }
            }
        }

    }

    private void saveRecDataAvg(ScriptInfo scriptInfo, int recType, int factorID) {
        int flowIndex = -1;
        DateRange dateRange = new DateRange();
        Timestamp timeStart;
        Timestamp timeEnd;
        byte mGap;
        switch (recType) {
            case 1:
                mGap = 60;
                timeStart = TimeHelper.getMinutesAgo(scriptInfo.getRunTime(), mGap);
                timeEnd = TimeHelper.getMinutesLater(timeStart, mGap);
                break;
            case 2:
                mGap = 24;
                timeStart = TimeHelper.getDaysAgo(scriptInfo.getRunTime(), 1);
                timeEnd = TimeHelper.getDaysLater(scriptInfo.getRunTime(), -1);
                break;
            default:
                mGap = 5;
                timeStart = TimeHelper.getMinutesAgo(scriptInfo.getRunTime(), mGap);
                timeEnd = TimeHelper.getMinutesLater(timeStart, mGap);
        }

        RecDataTime recDataTime = this.saveRecTime(timeStart, recType + 200);
        dateRange.setStartDate(timeStart);
        dateRange.setEndDate(timeEnd);
        List<RecDataAvg> recInsList = new ArrayList();
        List recAvgList;
        List recDataList;
        switch (recType) {
            case 1:
                recDataList = this.recMapper.getRecDataMinListByRange(dateRange);
                recAvgList = this.recMapper.getRecDataHourAvgListByTime(recDataTime);
                break;
            case 2:
                recDataList = this.recMapper.getRecDataHourAvgListByRange(dateRange);
                recAvgList = this.recMapper.getRecDataDayAvgListByTime(recDataTime);
                break;
            default:
                recDataList = this.recMapper.getRecDataMinListByRange(dateRange);
                recAvgList = this.recMapper.getRecDataMinAvgListByTime(recDataTime);
        }

        Iterator var15 = this.cfgFactorList.iterator();

        while (true) {
            boolean bExist;
            CfgFactor cfgFactor;
            do {
                Iterator var17;
                RecDataAvg recDataAvg;
                do {
                    do {
                        do {
                            if (!var15.hasNext()) {
                                var15 = this.cfgFactorList.iterator();

                                label128:
                                while (var15.hasNext()) {
                                    cfgFactor = (CfgFactor) var15.next();
                                    if (cfgFactor.getName().equals("流量")) {
                                        int i = 0;

                                        while (true) {
                                            if (i >= recInsList.size()) {
                                                break label128;
                                            }

                                            if (((RecDataAvg) recInsList.get(i)).getFactorID() == cfgFactor.getId()) {
                                                flowIndex = i;
                                                switch (recType) {
                                                    case 1:
                                                        ((RecDataAvg) recInsList.get(i)).setDataCou(((RecDataAvg) recInsList.get(i)).getDataValue() * (double) mGap * 60.0D);
                                                        break label128;
                                                    case 2:
                                                        ((RecDataAvg) recInsList.get(i)).setDataCou(((RecDataAvg) recInsList.get(i)).getDataValue() * (double) mGap * 3600.0D);
                                                        break label128;
                                                    default:
                                                        ((RecDataAvg) recInsList.get(i)).setDataCou(((RecDataAvg) recInsList.get(i)).getDataValue() * (double) mGap * 60.0D);
                                                        break label128;
                                                }
                                            }

                                            ++i;
                                        }
                                    }
                                }

                                var15 = this.cfgFactorList.iterator();

                                while (true) {
                                    label112:
                                    while (true) {
                                        do {
                                            do {
                                                do {
                                                    if (!var15.hasNext()) {
                                                        return;
                                                    }

                                                    cfgFactor = (CfgFactor) var15.next();
                                                } while (!cfgFactor.isUsed());
                                            } while (!cfgFactor.isFiveType() && !cfgFactor.isParmType());
                                        } while (cfgFactor.getId() != factorID && factorID != 0);

                                        var17 = recInsList.iterator();

                                        while (var17.hasNext()) {
                                            recDataAvg = (RecDataAvg) var17.next();
                                            if (recDataAvg.getFactorID() == cfgFactor.getId()) {
                                                if (flowIndex >= 0) {
                                                    recDataAvg.setDataCou(recDataAvg.getDataValue() * ((RecDataAvg) recInsList.get(flowIndex)).getDataCou());
                                                }

                                                switch (recType) {
                                                    case 1:
                                                        this.recMapper.addRecDataHourAvg(recDataAvg);
                                                        continue label112;
                                                    case 2:
                                                        this.recMapper.addRecDataDayAvg(recDataAvg);
                                                        continue label112;
                                                    default:
                                                        this.recMapper.addRecDataMinAvg(recDataAvg);
                                                        continue label112;
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            cfgFactor = (CfgFactor) var15.next();
                        } while (!cfgFactor.isUsed());
                    } while (!cfgFactor.isFiveType() && !cfgFactor.isParmType());
                } while (cfgFactor.getId() != factorID && factorID != 0);

                bExist = false;
                var17 = recAvgList.iterator();

                while (var17.hasNext()) {
                    recDataAvg = (RecDataAvg) var17.next();
                    if (recDataAvg.getFactorID() == cfgFactor.getId()) {
                        bExist = true;
                        break;
                    }
                }
            } while (bExist);

            RecDataAvg recDataAvg = new RecDataAvg();
            int avgSize = 0;
            Iterator var22 = recDataList.iterator();

            while (var22.hasNext()) {
                RecDataFactor recDataFactor = (RecDataFactor) var22.next();
                if (recDataFactor.getFactorID() == cfgFactor.getId()) {
                    if (recDataAvg.getDataValue() == null) {
                        recDataAvg.setDataValue(recDataFactor.getDataValue());
                        recDataAvg.setDataMin(recDataFactor.getDataValue());
                        recDataAvg.setDataMax(recDataFactor.getDataValue());
                    } else {
                        recDataAvg.setDataValue(recDataFactor.getDataValue() + recDataAvg.getDataValue());
                        recDataAvg.setDataMin(Math.min(recDataFactor.getDataValue(), recDataAvg.getDataMin()));
                        recDataAvg.setDataMax(Math.max(recDataFactor.getDataValue(), recDataAvg.getDataMax()));
                    }

                    ++avgSize;
                }
            }

            if (recDataAvg.getDataValue() != null) {
                recDataAvg.setRecID(recDataTime.getId());
                recDataAvg.setFactorID(cfgFactor.getId());
                recDataAvg.setDataTime(recDataTime.getRecTime());
                recDataAvg.setDataValue(Utility.getFormatDouble(recDataAvg.getDataValue() / (double) avgSize, 3));
                recDataAvg.setDataFlag("N");
                if (recDataAvg.getDataValue() < cfgFactor.getAlarmMin()) {
                    recDataAvg.setAlarm(true);
                    recDataAvg.setAlarmValue(cfgFactor.getAlarmMin());
                    recDataAvg.setAlarmType(1);
                } else if (recDataAvg.getDataValue() > cfgFactor.getAlarmMax()) {
                    recDataAvg.setAlarm(true);
                    recDataAvg.setAlarmValue(cfgFactor.getAlarmMax());
                    recDataAvg.setAlarmType(2);
                } else {
                    recDataAvg.setAlarm(false);
                    recDataAvg.setAlarmValue(0.0D);
                    recDataAvg.setAlarmType(0);
                }

                recInsList.add(recDataAvg);
            }
        }
    }

    private void saveRecDataDay(DateTime dateTime) {
        DateRange dateRange = new DateRange();
        Timestamp timeStart = new Timestamp(dateTime.getMillis());
        Timestamp timeEnd = new Timestamp(dateTime.plusDays(1).minusSeconds(1).getMillis());
        RecDataTime recDataTime = this.saveRecTimeDay(timeStart);
        dateRange.setStartDate(timeStart);
        dateRange.setEndDate(timeEnd);
        List<RecDataAvg> recInsList = new ArrayList();
        List<RecDataFactor> recDataList = this.recMapper.getRecDataHourListByRange(dateRange);
        List<RecDataAvg> recAvgList = this.recMapper.getRecDataDayAvgListByTime(recDataTime);
        Iterator var11 = this.cfgFactorList.iterator();

        while (true) {
            boolean bExist;
            CfgFactor cfgFactor;
            do {
                Iterator var13;
                RecDataAvg recDataAvg;
                do {
                    if (!var11.hasNext()) {
                        var11 = this.cfgFactorList.iterator();

                        while (true) {
                            while (true) {
                                do {
                                    if (!var11.hasNext()) {
                                        return;
                                    }

                                    cfgFactor = (CfgFactor) var11.next();
                                } while (!cfgFactor.isUsed());

                                var13 = recInsList.iterator();

                                while (var13.hasNext()) {
                                    recDataAvg = (RecDataAvg) var13.next();
                                    if (recDataAvg.getFactorID() == cfgFactor.getId()) {
                                        this.recMapper.addRecDataDayAvg(recDataAvg);
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    cfgFactor = (CfgFactor) var11.next();
                } while (!cfgFactor.isUsed());

                bExist = false;
                var13 = recAvgList.iterator();

                while (var13.hasNext()) {
                    recDataAvg = (RecDataAvg) var13.next();
                    if (recDataAvg.getFactorID() == cfgFactor.getId()) {
                        bExist = true;
                        break;
                    }
                }
            } while (bExist);

            RecDataAvg recDataAvg = new RecDataAvg();
            int avgSize = 0;
            Iterator var17 = recDataList.iterator();

            while (var17.hasNext()) {
                RecDataFactor recDataFactor = (RecDataFactor) var17.next();
                if (recDataFactor.getFactorID() == cfgFactor.getId()) {
                    if (recDataAvg.getDataValue() == null) {
                        recDataAvg.setDataValue(recDataFactor.getDataValue());
                        recDataAvg.setDataMin(recDataFactor.getDataValue());
                        recDataAvg.setDataMax(recDataFactor.getDataValue());
                    } else {
                        recDataAvg.setDataValue(recDataFactor.getDataValue() + recDataAvg.getDataValue());
                        recDataAvg.setDataMin(Math.min(recDataFactor.getDataValue(), recDataAvg.getDataMin()));
                        recDataAvg.setDataMax(Math.max(recDataFactor.getDataValue(), recDataAvg.getDataMax()));
                    }

                    ++avgSize;
                }
            }

            if (recDataAvg.getDataValue() != null) {
                recDataAvg.setRecID(recDataTime.getId());
                recDataAvg.setFactorID(cfgFactor.getId());
                recDataAvg.setDataTime(recDataTime.getRecTime());
                recDataAvg.setDataValue(Utility.getFormatDouble(recDataAvg.getDataValue() / (double) avgSize, 3));
                recDataAvg.setDataFlag("N");
                if (recDataAvg.getDataValue() < cfgFactor.getAlarmMin()) {
                    recDataAvg.setAlarm(true);
                    recDataAvg.setAlarmValue(cfgFactor.getAlarmMin());
                    recDataAvg.setAlarmType(1);
                } else if (recDataAvg.getDataValue() > cfgFactor.getAlarmMax()) {
                    recDataAvg.setAlarm(true);
                    recDataAvg.setAlarmValue(cfgFactor.getAlarmMax());
                    recDataAvg.setAlarmType(2);
                } else {
                    recDataAvg.setAlarm(false);
                    recDataAvg.setAlarmValue(0.0D);
                    recDataAvg.setAlarmType(0);
                }

                recInsList.add(recDataAvg);
            }
        }
    }

    private void saveRecDataMonth(DateTime dateTime) {
        DateRange dateRange = new DateRange();
        Timestamp timeStart = new Timestamp(dateTime.getMillis());
        Timestamp timeEnd = new Timestamp(dateTime.plusMonths(1).minusSeconds(1).getMillis());
        RecDataTime recDataTime = this.saveRecTimeMonth(timeStart);
        dateRange.setStartDate(timeStart);
        dateRange.setEndDate(timeEnd);
        List<RecDataAvg> recInsList = new ArrayList();
        List<RecDataFactor> recDataList = this.recMapper.getRecDataDayAvgListByRange(dateRange);
        List<RecDataAvg> recAvgList = this.recMapper.getRecDataMonthAvgListByTime(recDataTime);
        Iterator var11 = this.cfgFactorList.iterator();

        while (true) {
            boolean bExist;
            CfgFactor cfgFactor;
            do {
                Iterator var13;
                RecDataAvg recDataAvg;
                do {
                    if (!var11.hasNext()) {
                        var11 = this.cfgFactorList.iterator();

                        while (true) {
                            while (true) {
                                do {
                                    if (!var11.hasNext()) {
                                        return;
                                    }

                                    cfgFactor = (CfgFactor) var11.next();
                                } while (!cfgFactor.isUsed());

                                var13 = recInsList.iterator();

                                while (var13.hasNext()) {
                                    recDataAvg = (RecDataAvg) var13.next();
                                    if (recDataAvg.getFactorID() == cfgFactor.getId()) {
                                        this.recMapper.addRecDataMonthAvg(recDataAvg);
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    cfgFactor = (CfgFactor) var11.next();
                } while (!cfgFactor.isUsed());

                bExist = false;
                var13 = recAvgList.iterator();

                while (var13.hasNext()) {
                    recDataAvg = (RecDataAvg) var13.next();
                    if (recDataAvg.getFactorID() == cfgFactor.getId()) {
                        bExist = true;
                        break;
                    }
                }
            } while (bExist);

            RecDataAvg recDataAvg = new RecDataAvg();
            int avgSize = 0;
            Iterator var17 = recDataList.iterator();

            while (var17.hasNext()) {
                RecDataFactor recDataFactor = (RecDataFactor) var17.next();
                if (recDataFactor.getFactorID() == cfgFactor.getId()) {
                    if (recDataAvg.getDataValue() == null) {
                        recDataAvg.setDataValue(recDataFactor.getDataValue());
                        recDataAvg.setDataMin(recDataFactor.getDataValue());
                        recDataAvg.setDataMax(recDataFactor.getDataValue());
                    } else {
                        recDataAvg.setDataValue(recDataFactor.getDataValue() + recDataAvg.getDataValue());
                        recDataAvg.setDataMin(Math.min(recDataFactor.getDataValue(), recDataAvg.getDataMin()));
                        recDataAvg.setDataMax(Math.max(recDataFactor.getDataValue(), recDataAvg.getDataMax()));
                    }

                    ++avgSize;
                }
            }

            if (recDataAvg.getDataValue() != null) {
                recDataAvg.setRecID(recDataTime.getId());
                recDataAvg.setFactorID(cfgFactor.getId());
                recDataAvg.setDataTime(recDataTime.getRecTime());
                recDataAvg.setDataValue(Utility.getFormatDouble(recDataAvg.getDataValue() / (double) avgSize, 3));
                recDataAvg.setDataFlag("N");
                if (recDataAvg.getDataValue() < cfgFactor.getAlarmMin()) {
                    recDataAvg.setAlarm(true);
                    recDataAvg.setAlarmValue(cfgFactor.getAlarmMin());
                    recDataAvg.setAlarmType(1);
                } else if (recDataAvg.getDataValue() > cfgFactor.getAlarmMax()) {
                    recDataAvg.setAlarm(true);
                    recDataAvg.setAlarmValue(cfgFactor.getAlarmMax());
                    recDataAvg.setAlarmType(2);
                } else {
                    recDataAvg.setAlarm(false);
                    recDataAvg.setAlarmValue(0.0D);
                    recDataAvg.setAlarmType(0);
                }

                recInsList.add(recDataAvg);
            }
        }
    }

    private void saveRecDataFive(Timestamp timestamp, int recType) {
        RecDataTime recDataTime = this.saveRecTime(timestamp, recType);
        Iterator var4 = this.cfgFactorList.iterator();

        while (true) {
            CfgFactor cfgFactor;
            do {
                do {
                    if (!var4.hasNext()) {
                        this.saveRecParam(timestamp, 450, 0);
                        return;
                    }

                    cfgFactor = (CfgFactor) var4.next();
                } while (!cfgFactor.isUsed());
            } while (!cfgFactor.isFiveType() && !cfgFactor.isSwType());

            RecDataFactor recDataFactor = this.getRecDataFactor(cfgFactor, recDataTime, recType);
            if (recDataFactor != null) {
                try {
                    switch (recType) {
                        case 0:
                            recDataFactor.setDataFlag("N");
                            this.recMapper.addRecDataMin(recDataFactor);
                            break;
                        case 1:
                            recDataFactor.setDataFlag("N");
                            this.recMapper.addRecDataHour(recDataFactor);
                    }
                } catch (Exception var8) {
                    Utility.logInfo("因子" + cfgFactor.getName() + "数据入库异常");
                }
            }
        }
    }

    private void saveDataHour(Timestamp timestamp, CfgFactor factor) {
        RecDataTime recDataTime = this.saveRecTime(timestamp, 1);
        Iterator var4 = this.cfgFactorList.iterator();

        while (true) {
            CfgFactor cfgFactor;
            do {
                do {
                    do {
                        if (!var4.hasNext()) {
                            if (factor == null) {
                                this.saveRecParam(timestamp, 450, 0);
                            } else {
                                this.saveRecParam(timestamp, 450, factor.getId());
                            }

                            return;
                        }

                        cfgFactor = (CfgFactor) var4.next();
                    } while (!cfgFactor.isUsed());
                } while (!cfgFactor.isFiveType() && !cfgFactor.isSwType());
            } while (factor != null && factor.getId() != cfgFactor.getId());

            RecDataFactor recDataFactor = this.getRecDataFactor(cfgFactor, recDataTime, 1);
            if (recDataFactor != null) {
                try {
                    recDataFactor.setDataFlag("N");
                    this.recMapper.addRecDataHour(recDataFactor);
                } catch (Exception var8) {
                    Utility.logInfo("因子" + cfgFactor.getName() + "数据入库异常");
                }
            }
        }
    }

    private void saveRecDataFiveZero(Timestamp timestamp, int recType) {
        RecDataTime recDataTime = this.saveRecTime(timestamp, recType);
        Iterator var4 = this.cfgFactorList.iterator();

        while (true) {
            CfgFactor cfgFactor;
            do {
                do {
                    if (!var4.hasNext()) {
                        return;
                    }

                    cfgFactor = (CfgFactor) var4.next();
                } while (!cfgFactor.isUsed());
            } while (!cfgFactor.isFiveType() && !cfgFactor.isSwType());

            RecDataFactor recDataFactor = this.getRecDataFactor(cfgFactor, recDataTime, recType);
            if (recDataFactor != null) {
                try {
                    recDataFactor.setDataValue(0.0D);
                    switch (recType) {
                        case 0:
                            recDataFactor.setDataFlag("lw");
                            this.recMapper.addRecDataMin(recDataFactor);
                            break;
                        case 1:
                            recDataFactor.setDataFlag("lw");
                            this.recMapper.addRecDataHour(recDataFactor);
                    }
                } catch (Exception var8) {
                    Utility.logInfo("因子" + cfgFactor.getName() + "0值数据入库异常");
                }
            }
        }
    }

    private void saveRecDataFiveFactor(Timestamp timestamp, int recType, int factorId) {
        RecDataTime recDataTime = this.saveRecTime(timestamp, recType);
        Iterator var5 = this.cfgFactorList.iterator();

        label30:
        while (var5.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor) var5.next();
            if (cfgFactor.isUsed() && cfgFactor.getId() == factorId) {
                RecDataFactor recDataFactor = this.getRecDataFactor(cfgFactor, recDataTime, recType);
                if (recDataFactor != null) {
                    try {
                        switch (recType) {
                            case 0:
                                recDataFactor.setDataFlag("N");
                                this.recMapper.addRecDataMin(recDataFactor);
                                break label30;
                            case 1:
                                recDataFactor.setDataFlag("N");
                                this.recMapper.addRecDataHour(recDataFactor);
                        }
                    } catch (Exception var9) {
                        Utility.logInfo("因子" + cfgFactor.getName() + "数据入库异常");
                    }
                }
                break;
            }
        }

        this.saveRecParam(timestamp, 450, factorId);
    }

    private void saveRecDataSys(Timestamp timestamp, int recType) {
        RecDataTime recDataTime = this.saveRecTime(timestamp, recType);
        Iterator var4 = this.cfgFactorList.iterator();

        while (var4.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor) var4.next();
            if (cfgFactor.isUsed() && cfgFactor.isSysType()) {
                RecDataFactor recDataFactor = this.getRecDataFactor(cfgFactor, recDataTime, recType);
                if (recDataFactor != null) {
                    try {
                        switch (recType) {
                            case 0:
                                recDataFactor.setDataFlag("N");
                                this.recMapper.addRecDataMin(recDataFactor);
                                break;
                            case 1:
                                recDataFactor.setDataFlag("N");
                                this.recMapper.addRecDataHour(recDataFactor);
                        }
                    } catch (Exception var8) {
                        Utility.logInfo("因子" + cfgFactor.getName() + "数据入库异常");
                    }
                }
            }
        }

    }

    private void saveRecDataFive(Timestamp timestamp) {
        RecDataTime recDataTime = this.saveRecTime(timestamp, 100);
        Iterator var3 = this.cfgFactorList.iterator();

        while (var3.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor) var3.next();
            if (cfgFactor.isUsed() && cfgFactor.isFiveType()) {
                RecDataFactor recDataFactor = this.getRecDataFactor(cfgFactor, recDataTime, 2);
                if (recDataFactor != null) {
                    try {
                        recDataFactor.setDataFlag("N");
                        this.recMapper.addRecDataFive(recDataFactor);
                    } catch (Exception var7) {
                        Utility.logInfo("五参数" + cfgFactor.getName() + "数据入库异常");
                    }
                }
            }
        }

    }

    private void saveDataFive(Timestamp timestamp, CfgFactor factor) {
        RecDataTime recDataTime = this.saveRecTime(timestamp, 100);
        Iterator var4 = this.cfgFactorList.iterator();

        while (true) {
            CfgFactor cfgFactor;
            do {
                do {
                    do {
                        if (!var4.hasNext()) {
                            return;
                        }

                        cfgFactor = (CfgFactor) var4.next();
                    } while (!cfgFactor.isUsed());
                } while (!cfgFactor.isFiveType());
            } while (factor != null && factor.getId() != cfgFactor.getId());

            RecDataFactor recDataFactor = this.getRecDataFactor(cfgFactor, recDataTime, 2);
            if (recDataFactor != null) {
                try {
                    recDataFactor.setDataFlag("N");
                    this.recMapper.addRecDataFive(recDataFactor);
                } catch (Exception var8) {
                    Utility.logInfo("五参数" + cfgFactor.getName() + "数据入库异常");
                }
            }
        }
    }

    private void saveRecDataFiveZero(Timestamp timestamp) {
        RecDataTime recDataTime = this.saveRecTime(timestamp, 100);
        Iterator var3 = this.cfgFactorList.iterator();

        while (var3.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor) var3.next();
            if (cfgFactor.isUsed() && cfgFactor.isFiveType()) {
                RecDataFactor recDataFactor = this.getRecDataFactor(cfgFactor, recDataTime, 2);
                if (recDataFactor != null) {
                    try {
                        recDataFactor.setDataFlag("N");
                        recDataFactor.setDataValue(0.0D);
                        this.recMapper.addRecDataFive(recDataFactor);
                    } catch (Exception var7) {
                        Utility.logInfo("五参数" + cfgFactor.getName() + "0值数据入库异常");
                    }
                }
            }
        }

    }

    private void saveRecDataFiveFactor(Timestamp timestamp, int factorId) {
        RecDataTime recDataTime = this.saveRecTime(timestamp, 100);
        Iterator var4 = this.cfgFactorList.iterator();

        while (var4.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor) var4.next();
            if (cfgFactor.isUsed() && cfgFactor.getId() == factorId) {
                RecDataFactor recDataFactor = this.getRecDataFactor(cfgFactor, recDataTime, 2);
                if (recDataFactor != null) {
                    try {
                        recDataFactor.setDataFlag("N");
                        this.recMapper.addRecDataFive(recDataFactor);
                    } catch (Exception var8) {
                        Utility.logInfo("五参数" + cfgFactor.getName() + "数据入库异常");
                    }
                }
                break;
            }
        }

    }

    private RecDataFactor getRecDataFactor(CfgFactor cfgFactor, RecDataTime recDataTime, int recType) {
        List dataList;
        Iterator var5;
        RecDataFactor recDataFactor;
        label84:
        switch (recType) {
            case 0:
                dataList = this.recMapper.getRecDataMinListByTime(recDataTime);
                var5 = dataList.iterator();

                do {
                    if (!var5.hasNext()) {
                        break label84;
                    }

                    recDataFactor = (RecDataFactor) var5.next();
                } while (recDataFactor.getFactorID() != cfgFactor.getId());

                return null;
            case 1:
                dataList = this.recMapper.getRecDataHourListByTime(recDataTime);
                var5 = dataList.iterator();

                do {
                    if (!var5.hasNext()) {
                        break label84;
                    }

                    recDataFactor = (RecDataFactor) var5.next();
                } while (recDataFactor.getFactorID() != cfgFactor.getId());

                return null;
            case 2:
                dataList = this.recMapper.getRecDataFiveListByTime(recDataTime);
                var5 = dataList.iterator();

                do {
                    if (!var5.hasNext()) {
                        break label84;
                    }

                    recDataFactor = (RecDataFactor) var5.next();
                } while (recDataFactor.getFactorID() != cfgFactor.getId());

                return null;
            case 300:
                dataList = this.recMapper.getRecDataRangeListByTime(recDataTime);
                var5 = dataList.iterator();

                do {
                    if (!var5.hasNext()) {
                        break label84;
                    }

                    recDataFactor = (RecDataFactor) var5.next();
                } while (recDataFactor.getFactorID() != cfgFactor.getId());

                return null;
            case 400:
                dataList = this.recMapper.getRecDataFlowListByTime(recDataTime);
                var5 = dataList.iterator();

                while (var5.hasNext()) {
                    recDataFactor = (RecDataFactor) var5.next();
                    if (recDataFactor.getFactorID() == cfgFactor.getId()) {
                        return null;
                    }
                }
        }

        try {
            recDataFactor = new RecDataFactor();
            recDataFactor.setRecID(recDataTime.getId());
            recDataFactor.setFactorID(cfgFactor.getId());
            recDataFactor.setDataTime(recDataTime.getRecTime());
            recDataFactor.setDataValue(cfgFactor.getDataMea());
            recDataFactor.setDataFlag(cfgFactor.getFlagMea());
            if (cfgFactor.getDataMea() != null) {
                if (cfgFactor.getDataMea() < cfgFactor.getAlarmMin()) {
                    recDataFactor.setAlarm(true);
                    recDataFactor.setAlarmValue(cfgFactor.getAlarmMin());
                    recDataFactor.setAlarmType(1);
                } else if (cfgFactor.getDataMea() > cfgFactor.getAlarmMax()) {
                    recDataFactor.setAlarm(true);
                    recDataFactor.setAlarmValue(cfgFactor.getAlarmMax());
                    recDataFactor.setAlarmType(2);
                } else {
                    recDataFactor.setAlarm(false);
                    recDataFactor.setAlarmValue(0.0D);
                    recDataFactor.setAlarmType(0);
                }

                return recDataFactor;
            } else {
                return null;
            }
        } catch (Exception var7) {
            Utility.logInfo("获取" + cfgFactor.getName() + "失败");
            return null;
        }
    }

    private void saveRecCheckZero(ScriptInfo scriptInfo, int factorID) {
        this.saveRecCheck(scriptInfo, 3, factorID);
    }

    private void saveRecCheckSpan(ScriptInfo scriptInfo, int factorID) {
        this.saveRecCheck(scriptInfo, 4, factorID);
    }

    private void saveRecCheck(ScriptInfo scriptInfo, int recType, int factorID) {
        RecDataTime recDataTime = this.saveRecTime(scriptInfo.getRunTime(), recType);
        Iterator var5 = this.cfgFactorList.iterator();

        while (var5.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor) var5.next();
            if (cfgFactor.isUsed() && cfgFactor.isParmType() && cfgFactor.getId() == factorID) {
                RecCheckZero lastRecData = null;
                Double preValue = null;
                switch (recType) {
                    case 3:
                        lastRecData = this.recMapper.getLastRecCheckZeroData(cfgFactor);
                        break;
                    case 4:
                        lastRecData = this.recMapper.getLastRecCheckSpanData(cfgFactor);
                        break;
                    default:
                        lastRecData = null;
                }

                if (lastRecData == null) {
                    preValue = null;
                } else if (scriptInfo.getRunTime().getTime() - lastRecData.getRecTime().getTime() <= 86400000L) {
                    preValue = lastRecData.getCurValue();
                } else {
                    preValue = null;
                }

                RecCheckZero recCheck = this.getRecCheck(cfgFactor, recDataTime, preValue, recType, scriptInfo.getSaveType());
                if (recCheck != null) {
                    if (!this.save_flag) {
                        if (recCheck.getDataFlag() == null) {
                            recCheck.setDataFlag("N");
                        } else if (!recCheck.getDataFlag().equalsIgnoreCase("hd") && !recCheck.getDataFlag().equalsIgnoreCase("D")) {
                            recCheck.setDataFlag("N");
                        }
                    }

                    if (!recCheck.getDataFlag().equalsIgnoreCase("D")) {
                        try {
                            switch (recType) {
                                case 3:
                                    this.recMapper.addRecCheckZeroData(recCheck);
                                    return;
                                case 4:
                                    this.recMapper.addRecCheckSpanData(recCheck);
                            }
                        } catch (Exception var11) {
                            Utility.logInfo("核查" + cfgFactor.getName() + "数据入库异常");
                        }
                    } else {
                        Utility.logInfo("核查" + cfgFactor.getName() + "数据无效，请再次核查");
                    }
                } else {
                    Utility.logInfo("核查" + cfgFactor.getName() + "数据异常");
                }
                break;
            }
        }

    }

    private RecCheckZero getRecCheck(CfgFactor cfgFactor, RecDataTime recDataTime, Double preVal, int recType, String dataFlag) {
        try {
            RecCheckZero recCheckZero = new RecCheckZero();
            recCheckZero.setRecID(recDataTime.getId());
            recCheckZero.setFactorID(cfgFactor.getId());
            recCheckZero.setRecTime(recDataTime.getRecTime());
            recCheckZero.setPreValue(preVal);
            recCheckZero.setSpanValue(cfgFactor.getSpanVal());
            double dTmp;
            switch (recType) {
                case 3:
                    recCheckZero.setCurValue(this.formatDouble(cfgFactor.getDataZero(), cfgFactor.getDecimals()));
                    recCheckZero.setStdValue(cfgFactor.getZeroStdVal());
                    dTmp = recCheckZero.getCurValue() - recCheckZero.getStdValue();
                    recCheckZero.setaError(this.formatDouble(dTmp, cfgFactor.getDecimals()));
                    if (dTmp >= cfgFactor.getZeroErrorMin() && dTmp <= cfgFactor.getZeroErrorMax()) {
                        recCheckZero.setaResult("合格");
                    } else {
                        recCheckZero.setaResult("不合格");
                    }

                    if (recCheckZero.getPreValue() != null) {
                        dTmp = (recCheckZero.getCurValue() - recCheckZero.getPreValue()) / recCheckZero.getSpanValue() * 100.0D;
                        recCheckZero.setrError(this.formatDouble(dTmp, cfgFactor.getDecimals()));
                        if (dTmp >= cfgFactor.getZeroDriftMin() && dTmp <= cfgFactor.getZeroDriftMax()) {
                            recCheckZero.setrResult("合格");
                        } else {
                            recCheckZero.setrResult("不合格");
                        }
                    } else {
                        recCheckZero.setrResult("超24小时");
                    }
                    break;
                case 4:
                    recCheckZero.setCurValue(this.formatDouble(cfgFactor.getDataSpan(), cfgFactor.getDecimals()));
                    recCheckZero.setStdValue(cfgFactor.getSpanStdVal());
                    dTmp = (recCheckZero.getCurValue() - recCheckZero.getStdValue()) / recCheckZero.getStdValue() * 100.0D;
                    recCheckZero.setaError(this.formatDouble(dTmp, cfgFactor.getDecimals()));
                    if (dTmp >= cfgFactor.getSpanErrorMin() && dTmp <= cfgFactor.getSpanErrorMax()) {
                        recCheckZero.setaResult("合格");
                    } else {
                        recCheckZero.setaResult("不合格");
                    }

                    if (recCheckZero.getPreValue() != null) {
                        dTmp = (recCheckZero.getCurValue() - recCheckZero.getPreValue()) / recCheckZero.getSpanValue() * 100.0D;
                        recCheckZero.setrError(this.formatDouble(dTmp, cfgFactor.getDecimals()));
                        if (dTmp >= cfgFactor.getSpanDriftMin() && dTmp <= cfgFactor.getSpanDriftMax()) {
                            recCheckZero.setrResult("合格");
                        } else {
                            recCheckZero.setrResult("不合格");
                        }
                    } else {
                        recCheckZero.setrResult("超24小时");
                    }
            }

            recCheckZero.setDataFlag(dataFlag);
            switch (recType) {
                case 3:
                    if (dataFlag.equals("N") && cfgFactor.getFlagZero() != null && cfgFactor.getFlagZero().length() > 0) {
                        recCheckZero.setDataFlag(cfgFactor.getFlagZero());
                    }

                    if (this.time_check && cfgFactor.getTimeZero() != null && cfgFactor.getTimeZero().before(TimeHelper.getMinutesAgo(recDataTime.getRecTime(), 10))) {
                        recCheckZero.setDataFlag("D");
                    }
                    break;
                case 4:
                    if (dataFlag.equals("N") && cfgFactor.getFlagSpan() != null && cfgFactor.getFlagSpan().length() > 0) {
                        recCheckZero.setDataFlag(cfgFactor.getFlagSpan());
                    }

                    if (this.time_check && cfgFactor.getTimeSpan() != null && cfgFactor.getTimeSpan().before(TimeHelper.getMinutesAgo(recDataTime.getRecTime(), 10))) {
                        recCheckZero.setDataFlag("D");
                    }
            }

            return recCheckZero;
        } catch (Exception var9) {
            return null;
        }
    }

    private void saveRecCheckRcvr(Timestamp recTime, ScriptInfo scriptInfo, int factorID) {
        RecDataTime recDataTime = this.saveRecTime(recTime, 7);
        Iterator var5 = this.cfgFactorList.iterator();

        while (var5.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor) var5.next();
            if (cfgFactor.isUsed() && cfgFactor.isParmType() && cfgFactor.getId() == factorID) {
                try {
                    List<RecDataFactor> recDataFactorList = this.recMapper.getLastRecDataHour(factorID);
                    RecCheckRcvr recCheckRcvr = new RecCheckRcvr();
                    recCheckRcvr.setRecID(recDataTime.getId());
                    recCheckRcvr.setFactorID(cfgFactor.getId());
                    recCheckRcvr.setRecTime(recDataTime.getRecTime());
                    if (recDataFactorList != null && recDataFactorList.size() > 0) {
                        recCheckRcvr.setBeforeValue(((RecDataFactor) recDataFactorList.get(0)).getDataValue());
                    } else {
                        recCheckRcvr.setBeforeValue(cfgFactor.getDataMea());
                    }

                    recCheckRcvr.setBeforeTime(cfgFactor.getTimeMea());
                    recCheckRcvr.setAfterValue(cfgFactor.getDataRcvr());
                    recCheckRcvr.setAfterTime(cfgFactor.getTimeRcvr());
                    recCheckRcvr.setMotherValue(cfgFactor.getRcvrMotherVal());
                    switch (cfgFactor.getRcvrType()) {
                        case 0:
                            recCheckRcvr.setMotherVolume(cfgFactor.getRcvrMotherVol());
                            break;
                        case 1:
                            int addVol = (int) (cfgFactor.getDataMea() * cfgFactor.getRcvrMultiple() * cfgFactor.getRcvrCupVol() / cfgFactor.getRcvrMotherVal());
                            if ((double) addVol / cfgFactor.getRcvrCupVol() > 0.02D) {
                                addVol = (int) (cfgFactor.getRcvrCupVol() * 0.02D);
                            }

                            recCheckRcvr.setMotherVolume((double) addVol);
                    }

                    recCheckRcvr.setCupVolume(cfgFactor.getRcvrCupVol());
                    double dTmp;
                    switch (cfgFactor.getRcvrType()) {
                        case 1:
                            if (this.rcvrOver) {
                                dTmp = (recCheckRcvr.getAfterValue() - recCheckRcvr.getBeforeValue()) * 100.0D / (0.02D * cfgFactor.getRcvrMotherVal());
                            } else {
                                dTmp = (recCheckRcvr.getAfterValue() - recCheckRcvr.getBeforeValue()) * 100.0D / (recCheckRcvr.getBeforeValue() * cfgFactor.getRcvrMultiple());
                            }
                            break;
                        default:
                            dTmp = (recCheckRcvr.getAfterValue() - recCheckRcvr.getBeforeValue()) * recCheckRcvr.getCupVolume() * 100.0D / (recCheckRcvr.getMotherValue() * recCheckRcvr.getMotherVolume());
                    }

                    recCheckRcvr.setRcvrRate(this.formatDouble(dTmp, cfgFactor.getDecimals()));
                    if (dTmp >= cfgFactor.getRcvrRateMin() && dTmp <= cfgFactor.getRcvrRateMax()) {
                        recCheckRcvr.setResult(true);
                    } else {
                        recCheckRcvr.setResult(false);
                    }

                    recCheckRcvr.setDataFlag(scriptInfo.getSaveType());
                    if (!this.save_flag) {
                        if (recCheckRcvr.getDataFlag() == null) {
                            recCheckRcvr.setDataFlag("N");
                        } else if (!recCheckRcvr.getDataFlag().equalsIgnoreCase("hd") && !recCheckRcvr.getDataFlag().equalsIgnoreCase("D")) {
                            recCheckRcvr.setDataFlag("N");
                        }
                    }

                    this.recMapper.addRecCheckRcvrData(recCheckRcvr);
                } catch (Exception var11) {
                    Utility.logInfo("加标回收" + cfgFactor.getName() + "数据入库异常");
                }
                break;
            }
        }

    }

    private void saveRecCheckStd(ScriptInfo scriptInfo, int factorID) {
        RecDataTime recDataTime = this.saveRecTime(scriptInfo.getRunTime(), 2);
        Iterator var6 = this.cfgFactorList.iterator();

        while (var6.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor) var6.next();
            if (cfgFactor.isUsed() && cfgFactor.isParmType() && cfgFactor.getId() == factorID) {
                try {
                    RecCheckStd recCheckStd = new RecCheckStd();
                    recCheckStd.setRecID(recDataTime.getId());
                    recCheckStd.setFactorID(cfgFactor.getId());
                    recCheckStd.setRecTime(recDataTime.getRecTime());
                    recCheckStd.setTestValue(this.formatDouble(cfgFactor.getDataStd(), cfgFactor.getDecimals()));
                    recCheckStd.setStdValue(cfgFactor.getStdStdVal());
                    double dTmp = recCheckStd.getTestValue() - recCheckStd.getStdValue();
                    recCheckStd.setaError(this.formatDouble(dTmp, cfgFactor.getDecimals()));
                    dTmp = recCheckStd.getaError() / recCheckStd.getStdValue() * 100.0D;
                    recCheckStd.setrError(this.formatDouble(dTmp, cfgFactor.getDecimals()));
                    if (recCheckStd.getaError() < 1.0D) {
                        recCheckStd.setaResult(true);
                    } else {
                        recCheckStd.setaResult(false);
                    }

                    if (recCheckStd.getrError() >= cfgFactor.getStdErrorMin() && recCheckStd.getrError() <= cfgFactor.getStdErrorMax()) {
                        recCheckStd.setrResult(true);
                    } else {
                        recCheckStd.setrResult(false);
                    }

                    recCheckStd.setDataFlag(scriptInfo.getSaveType());
                    if (scriptInfo.getSaveType().equals("N")) {
                        if (cfgFactor.getFlagStd() != null && cfgFactor.getFlagStd().length() > 0) {
                            recCheckStd.setDataFlag(cfgFactor.getFlagStd());
                        }
                    } else if (cfgFactor.getFlagStd() != null && (cfgFactor.getFlagStd().equalsIgnoreCase("ii") || cfgFactor.getFlagStd().equalsIgnoreCase("iic"))) {
                        recCheckStd.setDataFlag(cfgFactor.getFlagStd());
                    }

                    if (this.time_check && cfgFactor.getTimeStd() != null && cfgFactor.getTimeStd().before(TimeHelper.getMinutesAgo(recDataTime.getRecTime(), 10))) {
                        recCheckStd.setDataFlag("D");
                    }

                    if (!this.save_flag) {
                        if (recCheckStd.getDataFlag() == null) {
                            recCheckStd.setDataFlag("N");
                        } else if (!recCheckStd.getDataFlag().equalsIgnoreCase("hd") && !recCheckStd.getDataFlag().equalsIgnoreCase("D")) {
                            recCheckStd.setDataFlag("N");
                        }
                    }

                    this.recMapper.addRecCheckStdData(recCheckStd);
                } catch (Exception var9) {
                    Utility.logInfo("标样核查" + cfgFactor.getName() + "数据入库异常");
                }
                break;
            }
        }

    }

    public boolean saveRecCheckStd(ReqStdCheck stdCheck) {
        if (stdCheck.getRunTime() == null) {
            return false;
        } else {
            RecDataTime recDataTime = this.saveRecTime(stdCheck.getRunTime(), 2);
            Iterator var5 = this.cfgFactorList.iterator();

            while (true) {
                if (var5.hasNext()) {
                    CfgFactor cfgFactor = (CfgFactor) var5.next();
                    if (!cfgFactor.isUsed() || !cfgFactor.isParmType() || !cfgFactor.getName().equals(stdCheck.getFactorName())) {
                        continue;
                    }

                    try {
                        RecCheckStd recCheckStd = new RecCheckStd();
                        recCheckStd.setRecID(recDataTime.getId());
                        recCheckStd.setFactorID(cfgFactor.getId());
                        recCheckStd.setRecTime(recDataTime.getRecTime());
                        recCheckStd.setTestValue(this.formatDouble(stdCheck.getTestValue(), cfgFactor.getDecimals()));
                        recCheckStd.setStdValue(stdCheck.getStdValue());
                        double dTmp = recCheckStd.getTestValue() - recCheckStd.getStdValue();
                        recCheckStd.setaError(this.formatDouble(dTmp, cfgFactor.getDecimals()));
                        dTmp = recCheckStd.getaError() / recCheckStd.getStdValue() * 100.0D;
                        recCheckStd.setrError(this.formatDouble(dTmp, cfgFactor.getDecimals()));
                        if (recCheckStd.getaError() < 1.0D) {
                            recCheckStd.setaResult(true);
                        } else {
                            recCheckStd.setaResult(false);
                        }

                        if (recCheckStd.getrError() >= cfgFactor.getStdErrorMin() && recCheckStd.getrError() <= cfgFactor.getStdErrorMax()) {
                            recCheckStd.setrResult(true);
                        } else {
                            recCheckStd.setrResult(false);
                        }

                        recCheckStd.setDataFlag("hd");
                        if (!this.save_flag) {
                            if (recCheckStd.getDataFlag() == null) {
                                recCheckStd.setDataFlag("N");
                            } else if (!recCheckStd.getDataFlag().equalsIgnoreCase("hd") && !recCheckStd.getDataFlag().equalsIgnoreCase("D")) {
                                recCheckStd.setDataFlag("N");
                            }
                        }

                        this.recMapper.addRecCheckStdData(recCheckStd);
                        Iterator var8 = this.uploadInterList.iterator();

                        while (var8.hasNext()) {
                            UploadInter uploadInter = (UploadInter) var8.next();
                            uploadInter.sendStdCheck(cfgFactor.getId(), recCheckStd.getRecTime());
                        }

                        return true;
                    } catch (Exception var10) {
                        Utility.logInfo("标样核查" + cfgFactor.getName() + "数据入库异常");
                    }
                }

                return false;
            }
        }
    }

    private void saveRecCheckPar(ScriptInfo scriptInfo, int factorID) {
        RecDataTime recDataTime = this.saveRecTime(scriptInfo.getRunTime(), 6);
        Iterator var6 = this.cfgFactorList.iterator();

        while (var6.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor) var6.next();
            if (cfgFactor.isUsed() && cfgFactor.isParmType() && cfgFactor.getId() == factorID) {
                try {
                    RecCheckPar recCheckPar = new RecCheckPar();
                    recCheckPar.setRecID(recDataTime.getId());
                    recCheckPar.setFactorID(cfgFactor.getId());
                    recCheckPar.setRecTime(recDataTime.getRecTime());
                    recCheckPar.setValue1st(cfgFactor.getDataMea());
                    recCheckPar.setValue2nd(cfgFactor.getDataPar());
                    recCheckPar.setValueRange(cfgFactor.getRange());
                    double dTmp = (recCheckPar.getValue2nd() - recCheckPar.getValue1st()) / recCheckPar.getValueRange() * 100.0D;
                    recCheckPar.setParRate(this.formatDouble(dTmp, cfgFactor.getDecimals()));
                    if (recCheckPar.getParRate() >= cfgFactor.getParDriftMin() && recCheckPar.getParRate() <= cfgFactor.getParDriftMax()) {
                        recCheckPar.setResult(true);
                    } else {
                        recCheckPar.setResult(false);
                    }

                    recCheckPar.setDataFlag(scriptInfo.getSaveType());
                    if (!this.save_flag) {
                        if (recCheckPar.getDataFlag() == null) {
                            recCheckPar.setDataFlag("N");
                        } else if (!recCheckPar.getDataFlag().equalsIgnoreCase("hd") && !recCheckPar.getDataFlag().equalsIgnoreCase("D")) {
                            recCheckPar.setDataFlag("N");
                        }
                    }

                    this.recMapper.addRecCheckParData(recCheckPar);
                } catch (Exception var9) {
                    Utility.logInfo("平行样核查" + cfgFactor.getName() + "数据入库异常");
                }
                break;
            }
        }

    }

    private void saveRecCheckBlank(ScriptInfo scriptInfo, int factorID) {
        RecDataTime recDataTime = this.saveRecTime(scriptInfo.getRunTime(), 5);
        Iterator var4 = this.cfgFactorList.iterator();

        while (var4.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor) var4.next();
            if (cfgFactor.isUsed() && cfgFactor.isParmType() && cfgFactor.getId() == factorID) {
                try {
                    if (cfgFactor.getDataBlnk() != null) {
                        RecCheckBlank lastRecData = null;
                        Double preValue = null;
                        lastRecData = this.recMapper.getLastRecCheckBlankData(cfgFactor);
                        if (lastRecData == null) {
                            preValue = null;
                        } else {
                            preValue = lastRecData.getCurValue();
                        }

                        RecCheckBlank recCheckBlank = new RecCheckBlank();
                        recCheckBlank.setRecID(recDataTime.getId());
                        recCheckBlank.setFactorID(cfgFactor.getId());
                        recCheckBlank.setRecTime(recDataTime.getRecTime());
                        recCheckBlank.setPreValue(preValue);
                        recCheckBlank.setCurValue(this.formatDouble(cfgFactor.getDataBlnk(), cfgFactor.getDecimals()));
                        recCheckBlank.setRangeValue(cfgFactor.getRange());
                        if (recCheckBlank.getPreValue() != null) {
                            double dTmp = (recCheckBlank.getCurValue() - recCheckBlank.getPreValue()) / recCheckBlank.getRangeValue() * 100.0D;
                            recCheckBlank.setrError(this.formatDouble(dTmp, cfgFactor.getDecimals()));
                            if (dTmp >= cfgFactor.getBlnkDriftMin() && dTmp <= cfgFactor.getBlnkDriftMax()) {
                                recCheckBlank.setrResult("合格");
                            } else {
                                recCheckBlank.setrResult("不合格");
                            }
                        }

                        recCheckBlank.setDataFlag(scriptInfo.getSaveType());
                        if (!this.save_flag) {
                            if (recCheckBlank.getDataFlag() == null) {
                                recCheckBlank.setDataFlag("N");
                            } else if (!recCheckBlank.getDataFlag().equalsIgnoreCase("hd") && !recCheckBlank.getDataFlag().equalsIgnoreCase("D")) {
                                recCheckBlank.setDataFlag("N");
                            }
                        }

                        this.recMapper.addRecCheckBlankData(recCheckBlank);
                    }
                } catch (Exception var11) {
                    Utility.logInfo("空白核查" + cfgFactor.getName() + "数据入库异常");
                }
                break;
            }
        }

    }

    private void saveRecCheckFive(Timestamp timestamp, int devID) {
        RecDataTime recDataTime = this.saveRecTime(timestamp, 101);
        Iterator var4 = this.cfgFactorList.iterator();

        while (true) {
            CfgFactor cfgFactor;
            do {
                do {
                    do {
                        if (!var4.hasNext()) {
                            return;
                        }

                        cfgFactor = (CfgFactor) var4.next();
                    } while (!cfgFactor.isUsed());
                } while (!cfgFactor.isFiveType());
            } while (devID > 0 && cfgFactor.getDevID() != devID);

            RecCheckFive recCheckFive = new RecCheckFive();
            recCheckFive.setRecID(recDataTime.getId());
            recCheckFive.setFactorID(cfgFactor.getId());
            recCheckFive.setRecTime(recDataTime.getRecTime());
            if (cfgFactor.getDataStd() == null) {
                recCheckFive.setCurValue(0.0D);
            } else {
                recCheckFive.setCurValue(this.formatDouble(cfgFactor.getDataStd(), cfgFactor.getDecimals()));
            }

            recCheckFive.setStdValue(cfgFactor.getStdStdVal());
            recCheckFive.setStdLevel(cfgFactor.getStdErrorMax());

            try {
                double dTmp = recCheckFive.getCurValue() - recCheckFive.getStdValue();
                if (dTmp < 0.0D) {
                    dTmp *= -1.0D;
                }

                recCheckFive.setaError(this.formatDouble(dTmp, cfgFactor.getDecimals()));
                if (dTmp >= cfgFactor.getStdErrorMin() && dTmp <= cfgFactor.getStdErrorMax()) {
                    recCheckFive.setaResult("合格");
                } else {
                    recCheckFive.setaResult("不合格");
                }

                recCheckFive.setDataFlag("N");
                this.recMapper.addRecCheckFiveData(recCheckFive);
            } catch (Exception var9) {
                Utility.logInfo("五参数核查" + cfgFactor.getName() + "数据入库异常");
            }
        }
    }

    public boolean saveRecCheckFive(ReqFiveCheck fiveCheck) {
        double dTmp = 0.0D;
        Timestamp timestamp = TimeHelper.getCurrentTimestamp();
        RecDataTime recDataTime = this.saveRecTime(timestamp, 101);
        Iterator var6 = this.cfgFactorList.iterator();

        while (true) {
            if (var6.hasNext()) {
                CfgFactor cfgFactor = (CfgFactor) var6.next();
                if (!cfgFactor.isUsed() || !cfgFactor.isFiveType() || !cfgFactor.getName().equals(fiveCheck.getFactorName())) {
                    continue;
                }

                RecCheckFive recCheckFive = new RecCheckFive();
                recCheckFive.setRecID(recDataTime.getId());
                recCheckFive.setFactorID(cfgFactor.getId());
                recCheckFive.setRecTime(recDataTime.getRecTime());
                recCheckFive.setCurValue(fiveCheck.getTestValue());
                recCheckFive.setStdValue(fiveCheck.getStdValue());
                String var9 = cfgFactor.getName();
                byte var10 = -1;
                switch (var9.hashCode()) {
                    case 3544:
                        if (var9.equals("pH")) {
                            var10 = 0;
                        }
                        break;
                    case 886901:
                        if (var9.equals("水温")) {
                            var10 = 1;
                        }
                        break;
                    case 891548:
                        if (var9.equals("浊度")) {
                            var10 = 3;
                        }
                        break;
                    case 28358618:
                        if (var9.equals("溶解氧")) {
                            var10 = 2;
                        }
                        break;
                    case 29594368:
                        if (var9.equals("电导率")) {
                            var10 = 4;
                        }
                }

                switch (var10) {
                    case 0:
                    case 1:
                    case 2:
                        dTmp = fiveCheck.getTestValue() - fiveCheck.getStdValue();
                        recCheckFive.setaError(this.formatDouble(dTmp, cfgFactor.getDecimals()));
                        recCheckFive.setStdLevel(cfgFactor.getStdErrorMax());
                        break;
                    case 3:
                    case 4:
                        dTmp = (fiveCheck.getTestValue() - fiveCheck.getStdValue()) / fiveCheck.getStdValue() * 100.0D;
                        recCheckFive.setaError(this.formatDouble(dTmp, cfgFactor.getDecimals()));
                        recCheckFive.setStdLevel(cfgFactor.getStdDriftMax());
                        break;
                    default:
                        if (cfgFactor.getCheckType() == 0) {
                            dTmp = (fiveCheck.getTestValue() - fiveCheck.getStdValue()) / fiveCheck.getStdValue() * 100.0D;
                            recCheckFive.setaError(this.formatDouble(dTmp, cfgFactor.getDecimals()));
                            recCheckFive.setStdLevel(cfgFactor.getStdDriftMax());
                        } else {
                            dTmp = fiveCheck.getTestValue() - fiveCheck.getStdValue();
                            recCheckFive.setaError(this.formatDouble(dTmp, cfgFactor.getDecimals()));
                            recCheckFive.setStdLevel(cfgFactor.getStdErrorMax());
                        }
                }

                try {
                    if (dTmp < 0.0D) {
                        dTmp *= -1.0D;
                    }

                    if (dTmp <= recCheckFive.getStdLevel()) {
                        recCheckFive.setaResult("合格");
                    } else {
                        recCheckFive.setaResult("不合格");
                    }

                    recCheckFive.setDataFlag("N");
                    this.recMapper.addRecCheckFiveData(recCheckFive);
                    Iterator var12 = this.uploadInterList.iterator();

                    while (var12.hasNext()) {
                        UploadInter uploadInter = (UploadInter) var12.next();
                        uploadInter.sendFiveStdCheck(cfgFactor.getId());
                    }

                    return true;
                } catch (Exception var11) {
                    Utility.logInfo("五参数核查" + cfgFactor.getName() + "数据入库异常");
                }
            }

            return false;
        }
    }

    public boolean saveRecCheckFiveAll(final Timestamp timestamp) {
        (new Thread(new Runnable() {
            public void run() {
                double dTmp = 0.0D;
                RecDataTime recDataTime = MonitorService.this.saveRecTime(timestamp, 101);
                Iterator var4 = MonitorService.this.cfgFactorList.iterator();

                while (var4.hasNext()) {
                    CfgFactor cfgFactor = (CfgFactor) var4.next();
                    if (cfgFactor.isUsed() && cfgFactor.isFiveType()) {
                        RecCheckFive recCheckFive = new RecCheckFive();
                        recCheckFive.setRecID(recDataTime.getId());
                        recCheckFive.setFactorID(cfgFactor.getId());
                        recCheckFive.setRecTime(recDataTime.getRecTime());
                        recCheckFive.setCurValue(cfgFactor.getDataMea());
                        recCheckFive.setStdValue(cfgFactor.getStdStdVal());
                        if (recCheckFive.getCurValue() == null) {
                            return;
                        }

                        String var7 = cfgFactor.getName();
                        byte var8 = -1;
                        switch (var7.hashCode()) {
                            case 3544:
                                if (var7.equals("pH")) {
                                    var8 = 0;
                                }
                                break;
                            case 886901:
                                if (var7.equals("水温")) {
                                    var8 = 1;
                                }
                                break;
                            case 891548:
                                if (var7.equals("浊度")) {
                                    var8 = 3;
                                }
                                break;
                            case 28358618:
                                if (var7.equals("溶解氧")) {
                                    var8 = 2;
                                }
                                break;
                            case 29594368:
                                if (var7.equals("电导率")) {
                                    var8 = 4;
                                }
                        }

                        switch (var8) {
                            case 0:
                            case 1:
                            case 2:
                                dTmp = recCheckFive.getCurValue() - recCheckFive.getStdValue();
                                recCheckFive.setaError(MonitorService.this.formatDouble(dTmp, cfgFactor.getDecimals()));
                                recCheckFive.setStdLevel(cfgFactor.getStdErrorMax());
                                break;
                            case 3:
                            case 4:
                                dTmp = (recCheckFive.getCurValue() - recCheckFive.getStdValue()) / recCheckFive.getStdValue() * 100.0D;
                                recCheckFive.setaError(MonitorService.this.formatDouble(dTmp, cfgFactor.getDecimals()));
                                recCheckFive.setStdLevel(cfgFactor.getStdDriftMax());
                                break;
                            default:
                                if (cfgFactor.getCheckType() == 0) {
                                    dTmp = (recCheckFive.getCurValue() - recCheckFive.getStdValue()) / recCheckFive.getStdValue() * 100.0D;
                                    recCheckFive.setaError(MonitorService.this.formatDouble(dTmp, cfgFactor.getDecimals()));
                                    recCheckFive.setStdLevel(cfgFactor.getStdDriftMax());
                                } else {
                                    dTmp = recCheckFive.getCurValue() - recCheckFive.getStdValue();
                                    recCheckFive.setaError(MonitorService.this.formatDouble(dTmp, cfgFactor.getDecimals()));
                                    recCheckFive.setStdLevel(cfgFactor.getStdErrorMax());
                                }
                        }

                        try {
                            if (dTmp < 0.0D) {
                                dTmp *= -1.0D;
                            }

                            if (dTmp <= recCheckFive.getStdLevel()) {
                                recCheckFive.setaResult("合格");
                            } else {
                                recCheckFive.setaResult("不合格");
                            }

                            recCheckFive.setDataFlag("N");
                            MonitorService.this.recMapper.addRecCheckFiveData(recCheckFive);
                        } catch (Exception var9) {
                            Utility.logInfo("五参数核查" + cfgFactor.getName() + "数据入库异常");
                        }
                    }
                }

                var4 = MonitorService.this.uploadInterList.iterator();

                while (var4.hasNext()) {
                    UploadInter uploadInter = (UploadInter) var4.next();
                    uploadInter.sendFiveStdCheck(0);
                }

            }
        })).start();
        return false;
    }

    private void saveRecLogDev(Timestamp timestamp) {
        boolean bSave = false;
        RecDataTime recDataTime = new RecDataTime();
        recDataTime.setRecTime(timestamp);
        Iterator var9 = this.cfgFactorList.iterator();

        while (true) {
            CfgFactor cfgFactor;
            DevService devTmp;
            do {
                do {
                    do {
                        do {
                            if (!var9.hasNext()) {
                                return;
                            }

                            cfgFactor = (CfgFactor) var9.next();
                        } while (!cfgFactor.isUsed());
                    } while (!cfgFactor.isParmType());

                    devTmp = null;
                    Iterator var12 = this.devServiceList.iterator();

                    while (var12.hasNext()) {
                        DevService devService = (DevService) var12.next();
                        if (devService.getDevID() == cfgFactor.getDevID()) {
                            devTmp = devService;
                            break;
                        }
                    }
                } while (devTmp == null);
            } while (!devTmp.isbConnect());

            try {
                RecLogDev lastRecLogDev = this.recMapper.getLastRecLogDev(cfgFactor.getId());
                int iLastLog;
                if (lastRecLogDev != null) {
                    iLastLog = lastRecLogDev.getLogID();
                } else {
                    iLastLog = -1;
                }

                Object oTmp = devTmp.getParam("DevLog");
                if (oTmp != null) {
                    int iLog = (Integer) oTmp;
                    if (iLog != iLastLog) {
                        if (!bSave) {
                            this.recMapper.addRecLogDevTime(recDataTime);
                            bSave = true;
                        }

                        int iGroup = iLog / 256;
                        int iIndex = iLog % 256;
                        RecLogDev recLogDev = new RecLogDev();
                        recLogDev.setFactorID(cfgFactor.getId());
                        recLogDev.setRecID(recDataTime.getId());
                        recLogDev.setRecTime(recDataTime.getRecTime());
                        recLogDev.setLogID(iLog);
                        recLogDev.setLogType((String) null);
                        recLogDev.setLogDesc((String) null);
                        String strGroup = "";
                        Iterator var15 = this.cfgLogGroupList.iterator();

                        while (var15.hasNext()) {
                            CfgLogGroup cfgLogGroup = (CfgLogGroup) var15.next();
                            if ((cfgLogGroup.getDev_id() == 0 || cfgLogGroup.getDev_id() == devTmp.mCfgDev.getLog_id()) && iGroup == cfgLogGroup.getLog_id()) {
                                strGroup = cfgLogGroup.getGroup();
                                break;
                            }
                        }

                        var15 = this.cfgDevLogList.iterator();

                        while (var15.hasNext()) {
                            CfgDevLog cfgDevLog = (CfgDevLog) var15.next();
                            if ((cfgDevLog.getDev_id() == 0 || cfgDevLog.getDev_id() == devTmp.mCfgDev.getLog_id()) && iIndex == cfgDevLog.getLog_id()) {
                                recLogDev.setLogType(cfgDevLog.getLog_type());
                                if (strGroup.length() > 0) {
                                    strGroup = "[" + strGroup + "]";
                                }

                                recLogDev.setLogDesc(strGroup + cfgDevLog.getLog_desc());
                                break;
                            }
                        }

                        this.recMapper.addRecLogDev(recLogDev);
                        var15 = this.uploadInterList.iterator();

                        while (var15.hasNext()) {
                            UploadInter uploadInter = (UploadInter) var15.next();
                            uploadInter.sendLogDev(recLogDev.getFactorID(), recDataTime);
                        }
                    }
                }
            } catch (Exception var17) {
                Utility.logInfo("仪表日志" + cfgFactor.getName() + "数据入库异常");
            }
        }
    }

    private void saveRecLogDev(int cmdType, int factorId, boolean isRunning) {
        RecDataTime recDataTime = new RecDataTime();
        recDataTime.setRecTime(TimeHelper.getCurrentTimestamp());
        this.recMapper.addRecLogDevTime(recDataTime);
        Iterator var6 = this.cfgFactorList.iterator();

        while (true) {
            CfgFactor cfgFactor;
            do {
                do {
                    do {
                        if (!var6.hasNext()) {
                            return;
                        }

                        cfgFactor = (CfgFactor) var6.next();
                    } while (!cfgFactor.isUsed());
                } while (!cfgFactor.isParmType());
            } while (factorId != 0 && cfgFactor.getId() != factorId);

            try {
                RecLogDev recLogDev = new RecLogDev();
                recLogDev.setFactorID(cfgFactor.getId());
                recLogDev.setRecID(recDataTime.getId());
                recLogDev.setRecTime(recDataTime.getRecTime());
                recLogDev.setLogID('\uea60' + cmdType);
                String strLog = Utility.getScriptName(cmdType);
                if (isRunning) {
                    strLog = "<<" + strLog + "流程开始";
                } else {
                    strLog = strLog + "流程结束>>";
                }

                recLogDev.setLogType("仪表日志");
                recLogDev.setLogDesc(strLog);
                this.recMapper.addRecLogDev(recLogDev);
                Iterator var9 = this.uploadInterList.iterator();

                while (var9.hasNext()) {
                    UploadInter uploadInter = (UploadInter) var9.next();
                    uploadInter.sendLogDev(recLogDev.getFactorID(), recDataTime);
                }
            } catch (Exception var11) {
                Utility.logInfo("仪表日志" + cfgFactor.getName() + "数据入库异常");
            }
        }
    }

    private void saveRecAlarmDev(Timestamp timestamp) {
        Iterator var4 = this.cfgFactorList.iterator();

        while (true) {
            CfgFactor cfgFactor;
            DevService devTmp;
            do {
                do {
                    do {
                        do {
                            if (!var4.hasNext()) {
                                return;
                            }

                            cfgFactor = (CfgFactor) var4.next();
                        } while (!cfgFactor.isUsed());
                    } while (!cfgFactor.isParmType());

                    devTmp = this.getDevServiceByFactorID(cfgFactor.getId());
                } while (devTmp == null);
            } while (!devTmp.isbConnect());

            try {
                RecAlarmDev lastRecAlarmDev = this.recMapper.getLastRecAlarmDev(cfgFactor.getId());
                int iLastAlarm;
                if (lastRecAlarmDev != null) {
                    iLastAlarm = lastRecAlarmDev.getAlarmID();
                } else {
                    iLastAlarm = -1;
                }

                Object oTmp = devTmp.getParam("DevAlarm");
                if (oTmp != null) {
                    int iAlarm = (Integer) oTmp;
                    if (iAlarm == 200) {
                        while (true) {
                            String strWarn = devTmp.getWarnInfo();
                            if (strWarn == null) {
                                break;
                            }

                            RecAlarmDev recAlarmDev = new RecAlarmDev();
                            recAlarmDev.setFactorID(cfgFactor.getId());
                            recAlarmDev.setRecTime(timestamp);
                            recAlarmDev.setAlarmID(iAlarm);
                            recAlarmDev.setAlarmType("仪表告警");
                            recAlarmDev.setAlarmDesc(strWarn);
                            this.recMapper.addRecAlarmDev(recAlarmDev);
                        }
                    } else if (iAlarm != iLastAlarm && iAlarm != 0) {
                        RecAlarmDev recAlarmDev = new RecAlarmDev();
                        recAlarmDev.setFactorID(cfgFactor.getId());
                        recAlarmDev.setRecTime(timestamp);
                        recAlarmDev.setAlarmID(iAlarm);
                        recAlarmDev.setAlarmType((String) null);
                        recAlarmDev.setAlarmDesc((String) null);
                        Iterator var10 = this.cfgDevAlarmList.iterator();

                        while (var10.hasNext()) {
                            CfgDevAlarm cfgDevAlarm = (CfgDevAlarm) var10.next();
                            if ((cfgDevAlarm.getDev_id() == 0 || cfgDevAlarm.getDev_id() == cfgFactor.getDevID()) && iAlarm == cfgDevAlarm.getAlarm_id()) {
                                recAlarmDev.setAlarmType(cfgDevAlarm.getAlarm_type());
                                recAlarmDev.setAlarmDesc(cfgDevAlarm.getAlarm_desc());
                                break;
                            }
                        }

                        this.recMapper.addRecAlarmDev(recAlarmDev);
                    }
                }
            } catch (Exception var12) {
                Utility.logInfo("仪表告警" + cfgFactor.getName() + "数据入库异常");
            }
        }
    }

    private void saveRecFaultDev(Timestamp timestamp) {
        Iterator var4 = this.cfgFactorList.iterator();

        while (true) {
            CfgFactor cfgFactor;
            DevService devTmp;
            do {
                do {
                    do {
                        do {
                            if (!var4.hasNext()) {
                                return;
                            }

                            cfgFactor = (CfgFactor) var4.next();
                        } while (!cfgFactor.isUsed());
                    } while (!cfgFactor.isParmType());

                    devTmp = this.getDevServiceByFactorID(cfgFactor.getId());
                } while (devTmp == null);
            } while (!devTmp.isbConnect());

            try {
                RecFaultDev lastRecFaultDev = this.recMapper.getLastRecFaultDev(cfgFactor.getId());
                int iLastFault;
                if (lastRecFaultDev != null) {
                    iLastFault = lastRecFaultDev.getFaultID();
                } else {
                    iLastFault = -1;
                }

                int iFault = (Integer) devTmp.getParam("DevFault");
                if (iFault != iLastFault) {
                    RecFaultDev recFaultDev = new RecFaultDev();
                    recFaultDev.setFactorID(cfgFactor.getId());
                    recFaultDev.setRecTime(timestamp);
                    recFaultDev.setFaultID(iFault);
                    recFaultDev.setFaultType((String) null);
                    recFaultDev.setFaultDesc((String) null);
                    Iterator var9 = this.cfgDevFaultList.iterator();

                    while (var9.hasNext()) {
                        CfgDevFault cfgDevFault = (CfgDevFault) var9.next();
                        if ((cfgDevFault.getDev_id() == 0 || cfgDevFault.getDev_id() == cfgFactor.getDevID()) && iFault == cfgDevFault.getFault_id()) {
                            recFaultDev.setFaultType(cfgDevFault.getFault_type());
                            recFaultDev.setFaultDesc(cfgDevFault.getFault_desc());
                            break;
                        }
                    }

                    this.recMapper.addRecFaultDev(recFaultDev);
                }
            } catch (Exception var11) {
                Utility.logInfo("仪表故障" + cfgFactor.getName() + "数据入库异常");
            }
        }
    }

    public void saveRecLogSys(String strType, String strDesc) {
        this.saveRecSys(1, strType, strDesc);
    }

    private void saveRecAlarmSys(String strType, String strDesc) {
        this.saveRecSys(2, strType, strDesc);
    }

    private void saveRecFaultSys(String strType, String strDesc) {
        this.saveRecSys(3, strType, strDesc);
    }

    private void saveRecSys(int recType, String strType, String strDesc) {
        Timestamp timestamp = TimeHelper.getCurrentTimestamp();
        switch (recType) {
            case 1:
                RecDataTime lastDataTime = this.recMapper.getLastRecLogSysTime();
                if (lastDataTime != null) {
                    Timestamp lastTimestamp = lastDataTime.getRecTime();
                    if (timestamp.getTime() > lastTimestamp.getTime() && timestamp.getTime() < lastTimestamp.getTime() + 1000L) {
                        timestamp.setTime(lastTimestamp.getTime() + 1000L);
                    }
                }

                RecDataTime recDataTime = new RecDataTime();
                recDataTime.setRecTime(timestamp);
                this.recMapper.addRecLogSysTime(recDataTime);
                RecLogSys recLogSys = new RecLogSys();
                recLogSys.setRecID(recDataTime.getId());
                recLogSys.setRecTime(recDataTime.getRecTime());
                recLogSys.setLogType(strType);
                recLogSys.setLogDesc(strDesc);

                try {
                    this.recMapper.addRecLogSys(recLogSys);
                    if (strType.equals("系统日志")) {
                        Iterator var17 = this.uploadInterList.iterator();

                        while (var17.hasNext()) {
                            UploadInter uploadInter = (UploadInter) var17.next();
                            uploadInter.sendLogSys((Timestamp) null);
                        }
                    }
                } catch (Exception var15) {
                    Utility.logInfo("系统日志数据入库异常");
                }
                break;
            case 2:
                RecAlarmSys recAlarmSys = new RecAlarmSys();
                recAlarmSys.setRecTime(timestamp);
                recAlarmSys.setAlarmType(strType);
                recAlarmSys.setAlarmDesc(strDesc);

                try {
                    this.recMapper.addRecAlarmSys(recAlarmSys);
                } catch (Exception var13) {
                    Utility.logInfo("系统告警数据入库异常");
                }
                break;
            case 3:
                RecFaultSys recFaultSys = new RecFaultSys();
                recFaultSys.setRecTime(timestamp);
                recFaultSys.setFaultType(strType);
                recFaultSys.setFaultDesc(strDesc);

                try {
                    this.recMapper.addRecFaultSys(recFaultSys);
                    if (strDesc.contains("断电")) {
                        Iterator var11 = this.uploadInterList.iterator();

                        while (var11.hasNext()) {
                            UploadInter uploadInter = (UploadInter) var11.next();
                            uploadInter.sendSystemAlarm(timestamp, "1");
                        }
                    }
                } catch (Exception var14) {
                    Utility.logInfo("系统故障数据入库异常");
                }
        }

    }

    public void saveRecLogUser(String strUser, String strOperation) {
        Timestamp timestamp = TimeHelper.getCurrentTimestamp();
        RecLogUser recLogUser = new RecLogUser();
        recLogUser.setRecTime(timestamp);
        recLogUser.setRecUser(strUser);
        recLogUser.setRecOperation(strOperation);

        try {
            this.recMapper.addRecLogUser(recLogUser);
        } catch (Exception var6) {
            Utility.logInfo("用户行为入库异常");
        }

    }

    public void saveRecLogSample(String sampleBottle, String sampleVolumn, String sampleType, String sampleNote) {
        Timestamp timestamp = TimeHelper.getCurrentTimestamp();
        RecLogSample recLogSample = new RecLogSample();
        recLogSample.setRecTime(timestamp);
        recLogSample.setRecBottle(sampleBottle);
        recLogSample.setRecVolumn(sampleVolumn);
        recLogSample.setRecType(sampleType);
        recLogSample.setRecNote(sampleNote);

        try {
            this.recMapper.addRecLogSample(recLogSample);
        } catch (Exception var8) {
            Utility.logInfo("留样信息数据入库异常");
        }

    }

    private void saveRecAlarmData(Timestamp timestamp) {
        for (int i = 0; i < this.cfgFactorList.size(); ++i) {
            if (((CfgFactor) this.cfgFactorList.get(i)).isUsed()) {
                DevService devTmp = null;

                for (int j = 0; j < this.devServiceList.size(); ++j) {
                    if (((DevService) this.devServiceList.get(j)).getDevID() == ((CfgFactor) this.cfgFactorList.get(i)).getDevID()) {
                        devTmp = (DevService) this.devServiceList.get(j);
                        break;
                    }
                }

                if (devTmp != null && devTmp.isbConnect()) {
                    try {
                        Object oTmp = devTmp.getParam("MeaTestVal");
                        if (oTmp != null) {
                            double dValue = (Double) oTmp;
                            RecAlarmData recAlarmData = new RecAlarmData();
                            recAlarmData.setFactorID(((CfgFactor) this.cfgFactorList.get(i)).getId());
                            recAlarmData.setRecTime(timestamp);
                            recAlarmData.setAlarmValue(dValue);
                            RecAlarmData lastRecAlarmData = this.recMapper.getLastRecAlarmData(((CfgFactor) this.cfgFactorList.get(i)).getId());
                            Iterator var11;
                            UploadInter uploadInter;
                            if (lastRecAlarmData != null) {
                                double dLastValue = lastRecAlarmData.getAlarmValue();
                                if (dLastValue < ((CfgFactor) this.cfgFactorList.get(i)).getAlarmMin()) {
                                    if (dValue > ((CfgFactor) this.cfgFactorList.get(i)).getAlarmMax()) {
                                        recAlarmData.setAlarmLimit(((CfgFactor) this.cfgFactorList.get(i)).getAlarmMax());
                                        recAlarmData.setAlarmType("高于限值");
                                        this.recMapper.addRecAlarmData(recAlarmData);
                                        if (((CfgFactor) this.cfgFactorList.get(i)).getName().contains("温度")) {
                                            var11 = this.uploadInterList.iterator();

                                            while (var11.hasNext()) {
                                                uploadInter = (UploadInter) var11.next();
                                                uploadInter.sendSystemAlarm(timestamp, "10");
                                            }
                                        } else if (((CfgFactor) this.cfgFactorList.get(i)).getName().contains("湿度")) {
                                            var11 = this.uploadInterList.iterator();

                                            while (var11.hasNext()) {
                                                uploadInter = (UploadInter) var11.next();
                                                uploadInter.sendSystemAlarm(timestamp, "11");
                                            }
                                        }
                                    } else if (dValue >= ((CfgFactor) this.cfgFactorList.get(i)).getAlarmMin()) {
                                        recAlarmData.setAlarmLimit(((CfgFactor) this.cfgFactorList.get(i)).getAlarmMin());
                                        recAlarmData.setAlarmType("恢复");
                                        this.recMapper.addRecAlarmData(recAlarmData);
                                    }
                                } else if (dLastValue > ((CfgFactor) this.cfgFactorList.get(i)).getAlarmMax()) {
                                    if (dValue < ((CfgFactor) this.cfgFactorList.get(i)).getAlarmMin()) {
                                        recAlarmData.setAlarmLimit(((CfgFactor) this.cfgFactorList.get(i)).getAlarmMin());
                                        recAlarmData.setAlarmType("低于限值");
                                        this.recMapper.addRecAlarmData(recAlarmData);
                                    } else if (dValue <= ((CfgFactor) this.cfgFactorList.get(i)).getAlarmMax()) {
                                        recAlarmData.setAlarmLimit(((CfgFactor) this.cfgFactorList.get(i)).getAlarmMax());
                                        recAlarmData.setAlarmType("恢复");
                                        this.recMapper.addRecAlarmData(recAlarmData);
                                    }
                                } else {
                                    lastRecAlarmData = null;
                                }
                            }

                            if (lastRecAlarmData == null) {
                                if (dValue < ((CfgFactor) this.cfgFactorList.get(i)).getAlarmMin()) {
                                    recAlarmData.setAlarmLimit(((CfgFactor) this.cfgFactorList.get(i)).getAlarmMin());
                                    recAlarmData.setAlarmType("低于限值");
                                    this.recMapper.addRecAlarmData(recAlarmData);
                                } else if (dValue > ((CfgFactor) this.cfgFactorList.get(i)).getAlarmMax()) {
                                    recAlarmData.setAlarmLimit(((CfgFactor) this.cfgFactorList.get(i)).getAlarmMax());
                                    recAlarmData.setAlarmType("高于限值");
                                    this.recMapper.addRecAlarmData(recAlarmData);
                                    if (((CfgFactor) this.cfgFactorList.get(i)).getName().contains("温度")) {
                                        var11 = this.uploadInterList.iterator();

                                        while (var11.hasNext()) {
                                            uploadInter = (UploadInter) var11.next();
                                            uploadInter.sendSystemAlarm(timestamp, "10");
                                        }
                                    } else if (((CfgFactor) this.cfgFactorList.get(i)).getName().contains("湿度")) {
                                        var11 = this.uploadInterList.iterator();

                                        while (var11.hasNext()) {
                                            uploadInter = (UploadInter) var11.next();
                                            uploadInter.sendSystemAlarm(timestamp, "11");
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception var13) {
                        Utility.logInfo("数据告警" + ((CfgFactor) this.cfgFactorList.get(i)).getName() + "数据入库异常");
                    }
                }
            }
        }

    }

    private void saveRecInfoDoor(CfgUser cfgUser, Timestamp recTime) {
        RecInfoDoor recInfoDoor = new RecInfoDoor();
        recInfoDoor.setRec_time(recTime);
        recInfoDoor.setRec_type("密码");
        recInfoDoor.setRec_name(cfgUser.getNick());
        recInfoDoor.setRec_info("开门");

        try {
            this.recMapper.addRecInfoDoor(recInfoDoor);
        } catch (Exception var5) {
            Utility.logInfo("门禁信息数据入库异常");
        }

    }

    private void saveRecInfoGas(boolean bState) {
        this.saveRecInfo(1, bState);
    }

    private void saveRecInfoWater(boolean bState) {
        this.saveRecInfo(2, bState);
    }

    private void saveRecInfo(int recType, boolean recState) {
        Timestamp timestamp = TimeHelper.getCurrentTimestamp();
        RecInfo lastRecInfo = null;
        switch (recType) {
            case 1:
                lastRecInfo = this.recMapper.getLastRecInfoGas();
                break;
            case 2:
                lastRecInfo = this.recMapper.getLastRecInfoWater();
        }

        RecInfo recInfo = new RecInfo();
        recInfo.setRec_time(timestamp);
        Iterator var6;
        UploadInter uploadInter;
        switch (recType) {
            case 1:
                if (recState) {
                    recInfo.setRec_info("报警");
                } else {
                    recInfo.setRec_info("恢复");
                }

                if (lastRecInfo == null || !lastRecInfo.getRec_info().equals(recInfo.getRec_info())) {
                    try {
                        this.recMapper.addRecInfoGas(recInfo);
                        if (recState) {
                            var6 = this.uploadInterList.iterator();

                            while (var6.hasNext()) {
                                uploadInter = (UploadInter) var6.next();
                                uploadInter.sendSystemAlarm(timestamp, "12");
                            }
                        }
                    } catch (Exception var9) {
                        Utility.logInfo("烟感信息数据入库异常");
                    }
                }
                break;
            case 2:
                if (recState) {
                    recInfo.setRec_info("报警");
                } else {
                    recInfo.setRec_info("恢复");
                }

                if (lastRecInfo == null || !lastRecInfo.getRec_info().equals(recInfo.getRec_info())) {
                    try {
                        this.recMapper.addRecInfoWater(recInfo);
                        if (recState) {
                            var6 = this.uploadInterList.iterator();

                            while (var6.hasNext()) {
                                uploadInter = (UploadInter) var6.next();
                                uploadInter.sendSystemAlarm(timestamp, "13");
                            }
                        }
                    } catch (Exception var8) {
                        Utility.logInfo("水浸信息数据入库异常");
                    }
                }
        }

    }

    public List<CfgDev> getCfgDevList() {
        return this.cfgDevList;
    }

    public List<CfgDevAlarm> getCfgDevAlarmList() {
        return this.cfgDevAlarmList;
    }

    public List<CfgFactor> getCfgFactorList() {
        return this.cfgFactorList;
    }

    public List<CfgDevReg> getCfgRegModbusList() {
        return this.cfgRegModbusList;
    }

    public List<DevService> getDevServiceList() {
        return this.devServiceList;
    }

    public List<CommInter> getCommInterList() {
        return this.commInterList;
    }

    public List<CfgSerial> getCfgSerialList() {
        return this.cfgSerialList;
    }

    public List<CfgNetClient> getCfgNetClientList() {
        return this.cfgNetClientList;
    }

    public String getSysStatus() {
        return this.sysStatus;
    }

    public String getSysStatus(int cmdType) {
        String strTmp = Utility.getTaskName(cmdType);
        if (strTmp.equals("空闲状态") || strTmp.equals("未定义")) {
            strTmp = "系统待机";
        }

        return strTmp;
    }

    public int getScriptRunCmd() {
        return this.scriptInfo.getRunCmd();
    }

    public ScriptInfo getScriptInfo() {
        return this.scriptInfo;
    }

    public void initScriptFactorList(Integer factorID) {
        this.scriptInfo.getFactorList().clear();
        this.scriptInfo.getFactorList2().clear();
        this.scriptInfo.getCmdRunList().clear();
        if (factorID == null) {
            Iterator var2 = this.cfgFactorList.iterator();

            while (true) {
                CfgFactor cfgFactor;
                do {
                    do {
                        if (!var2.hasNext()) {
                            return;
                        }

                        cfgFactor = (CfgFactor) var2.next();
                    } while (!cfgFactor.isUsed());
                } while (!cfgFactor.isParmType() && !cfgFactor.isFiveType());

                this.scriptInfo.getFactorList().add(cfgFactor.getId());
                this.scriptInfo.getFactorList2().add(cfgFactor.getId());
                this.scriptInfo.getCmdRunList().add(false);
            }
        } else {
            this.scriptInfo.getFactorList().add(factorID);
            this.scriptInfo.getFactorList2().add(factorID);
            this.scriptInfo.getCmdRunList().add(false);
        }
    }

    public void setSysStatus(String sysStatus) {
        this.sysStatus = sysStatus;
    }

    public boolean isUserStart() {
        return this.userStart;
    }

    public void setUserStart(boolean userStart) {
        this.userStart = userStart;
    }

    public Timestamp getSysRunTime() {
        return this.sysRunTime;
    }

    public String getEnName(String chName) {
        Iterator var2 = this.cfgNameList.iterator();

        CfgName cfgName;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            cfgName = (CfgName) var2.next();
        } while (!cfgName.getCh_name().equals(chName));

        return cfgName.getEn_name();
    }

    public String getChName(String enName) {
        Iterator var2 = this.cfgNameList.iterator();

        CfgName cfgName;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            cfgName = (CfgName) var2.next();
        } while (!cfgName.getEn_name().equals(enName));

        return cfgName.getCh_name();
    }

    public List<String> getDevTypeList() {
        List<String> devTypeList = new ArrayList();
        Iterator var2 = this.cfgNameList.iterator();

        while (var2.hasNext()) {
            CfgName cfgName = (CfgName) var2.next();
            if (cfgName.getType().equals("类型")) {
                devTypeList.add(cfgName.getCh_name());
            }
        }

        return devTypeList;
    }

    public List<String> getProtocolList() {
        List<String> protocolList = new ArrayList();
        Iterator var2 = this.cfgNameList.iterator();

        while (var2.hasNext()) {
            CfgName cfgName = (CfgName) var2.next();
            if (cfgName.getType().equals("协议")) {
                protocolList.add(cfgName.getCh_name());
            }
        }

        return protocolList;
    }

    public String getSerialName(int serialID) {
        for (int i = 0; i < this.cfgSerialList.size(); ++i) {
            if (serialID == i + 1) {
                return ((CfgSerial) this.cfgSerialList.get(i)).getName();
            }
        }

        return null;
    }

    public int getSerialID(String serialName) {
        for (int i = 0; i < this.cfgSerialList.size(); ++i) {
            if (((CfgSerial) this.cfgSerialList.get(i)).getName().equals(serialName)) {
                return i + 1;
            }
        }

        return 0;
    }

    public String getNetName(int netID) {
        for (int i = 0; i < this.cfgNetClientList.size(); ++i) {
            if (netID == i + 1) {
                return ((CfgNetClient) this.cfgNetClientList.get(i)).getName();
            }
        }

        return null;
    }

    public int getNetID(String netName) {
        for (int i = 0; i < this.cfgNetClientList.size(); ++i) {
            if (((CfgNetClient) this.cfgNetClientList.get(i)).getName().equals(netName)) {
                return i + 1;
            }
        }

        return 0;
    }

    public String getDevName(int devID) {
        Iterator var2 = this.cfgDevList.iterator();

        CfgDev cfgDev;
        do {
            if (!var2.hasNext()) {
                var2 = ScriptWord.getInstance().getDefWordList().iterator();

                DefWord defWord;
                do {
                    if (!var2.hasNext()) {
                        return null;
                    }

                    defWord = (DefWord) var2.next();
                } while (defWord.getType() != 2 || defWord.getAddress() + 10000 != devID);

                return defWord.getName();
            }

            cfgDev = (CfgDev) var2.next();
        } while (cfgDev.getId() != devID);

        return cfgDev.getName();
    }

    public int getDevID(String devName) {
        Iterator var2 = this.cfgDevList.iterator();

        CfgDev cfgDev;
        do {
            if (!var2.hasNext()) {
                var2 = ScriptWord.getInstance().getDefWordList().iterator();

                DefWord defWord;
                do {
                    if (!var2.hasNext()) {
                        return 0;
                    }

                    defWord = (DefWord) var2.next();
                } while (defWord.getType() != 2 || !defWord.getName().equals(devName));

                return defWord.getAddress() + 10000;
            }

            cfgDev = (CfgDev) var2.next();
        } while (!cfgDev.getName().equals(devName));

        return cfgDev.getId();
    }

    public int getDevID(int factorID) {
        Iterator var2 = this.cfgDevList.iterator();

        while (var2.hasNext()) {
            CfgDev cfgDev = (CfgDev) var2.next();
            Iterator var4 = cfgDev.getFactorList().iterator();

            while (var4.hasNext()) {
                CfgFactor cfgFactor = (CfgFactor) var4.next();
                if (cfgFactor.getId() == factorID) {
                    return cfgDev.getId();
                }
            }
        }

        return 0;
    }

    public String getFactorName(int factorID) {
        Iterator var2 = this.cfgFactorList.iterator();

        CfgFactor cfgFactor;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            cfgFactor = (CfgFactor) var2.next();
        } while (cfgFactor.getId() != factorID);

        return cfgFactor.getName();
    }

    public int getFactorID(String factorName) {
        Iterator var2 = this.cfgFactorList.iterator();

        CfgFactor cfgFactor;
        do {
            if (!var2.hasNext()) {
                return 0;
            }

            cfgFactor = (CfgFactor) var2.next();
        } while (!cfgFactor.getName().equals(factorName));

        return cfgFactor.getId();
    }

    public CfgFactor getFactorByName(String factorName) {
        Iterator var2 = this.cfgFactorList.iterator();

        CfgFactor cfgFactor;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            cfgFactor = (CfgFactor) var2.next();
        } while (!cfgFactor.getName().equals(factorName));

        return cfgFactor;
    }

    public DevService getDevServiceByFactorID(int factorID) {
        DevService devTmp = null;
        Iterator var3 = this.devServiceList.iterator();

        while (var3.hasNext()) {
            DevService devService = (DevService) var3.next();
            if (devService.getFactorID() == factorID) {
                devTmp = devService;
                break;
            }
        }

        return devTmp;
    }

    public DevService getDevServiceByDevName(String devName) {
        DevService devTmp = null;
        Iterator var3 = this.devServiceList.iterator();

        while (var3.hasNext()) {
            DevService devService = (DevService) var3.next();
            if (devService.getDevName().equalsIgnoreCase(devName)) {
                devTmp = devService;
                break;
            }

            Iterator var5 = devService.mCfgDev.getFactorList().iterator();

            while (var5.hasNext()) {
                CfgFactor cfgFactor = (CfgFactor) var5.next();
                if (cfgFactor.getCode().equalsIgnoreCase(devName)) {
                    devTmp = devService;
                    break;
                }
            }
        }

        return devTmp;
    }

    private void getRecDataAvg(int daysAgo, int daysLater) {
        DateRange dateRange = new DateRange();
        Timestamp timeStart = TimeHelper.getDaysAgo(this.scriptInfo.getRunTime(), daysAgo);
        Timestamp timeEnd = TimeHelper.getDaysLater(this.scriptInfo.getRunTime(), daysLater);
        dateRange.setStartDate(timeStart);
        dateRange.setEndDate(timeEnd);
        List<RecDataFactor> recDataList = this.recMapper.getRecDataHourAvgListByRange(dateRange);
        Iterator var10 = this.cfgFactorList.iterator();

        while (true) {
            CfgFactor cfgFactor;
            do {
                do {
                    if (!var10.hasNext()) {
                        return;
                    }

                    cfgFactor = (CfgFactor) var10.next();
                } while (!cfgFactor.isUsed());
            } while (!cfgFactor.isParmType());

            int avgSize = 0;
            double avgValue = 0.0D;
            Iterator var12 = recDataList.iterator();

            while (var12.hasNext()) {
                RecDataFactor recDataFactor = (RecDataFactor) var12.next();
                if (recDataFactor.getFactorID() == cfgFactor.getId()) {
                    avgValue += recDataFactor.getDataValue();
                    ++avgSize;
                }
            }

            cfgFactor.setSizeAvg(avgSize);
            if (avgSize > 0) {
                cfgFactor.setDataAvg(Utility.getFormatDouble(avgValue / (double) avgSize, 3));
            } else {
                cfgFactor.setDataAvg(0.0D);
            }
        }
    }

    private void saveCheckWarn(CfgFactor cfgFactor) {
        RecAlarmData recAlarmData = new RecAlarmData();
        recAlarmData.setFactorID(cfgFactor.getId());
        recAlarmData.setRecTime(this.scriptInfo.getRunTime());
        recAlarmData.setAlarmValue(cfgFactor.getDataMea());
        recAlarmData.setAlarmLimit(cfgFactor.getDataAvg());
        recAlarmData.setAlarmType("阈值报警");
        this.recMapper.addRecAlarmData(recAlarmData);
    }

    private void checkRecData() {
        DateRange dateRange = new DateRange();
        Double[] tmpData = new Double[2];
        Iterator var8 = this.cfgFactorList.iterator();

        CfgFactor cfgFactor;
        while (var8.hasNext()) {
            cfgFactor = (CfgFactor) var8.next();
            if (cfgFactor.isUsed() && cfgFactor.isParmType()) {
                cfgFactor.setCheckAvg(true);
            }
        }

        for (int i = 3; i > 0; --i) {
            this.getRecDataAvg(i, i * -1);
            Iterator var14 = this.cfgFactorList.iterator();

            while (var14.hasNext()) {
                cfgFactor = (CfgFactor) var14.next();
                if (cfgFactor.isUsed() && cfgFactor.isParmType() && cfgFactor.getSizeAvg() == 0) {
                    cfgFactor.setCheckAvg(false);
                }
            }
        }

        this.getRecDataAvg(3, -1);
        Timestamp timeStart = TimeHelper.getMinutesAgo(this.scriptInfo.getRunTime(), 600);
        Timestamp timeEnd = this.scriptInfo.getRunTime();
        dateRange.setStartDate(timeStart);
        dateRange.setEndDate(timeEnd);
        List<RecDataFactor> recDataList = this.recMapper.getRecDataHourAvgListByRange(dateRange);
        var8 = this.cfgFactorList.iterator();

        while (true) {
            do {
                boolean saveWarn;
                do {
                    do {
                        int i;
                        label141:
                        do {
                            while (true) {
                                int tmpIndex;
                                do {
                                    do {
                                        do {
                                            if (!var8.hasNext()) {
                                                return;
                                            }

                                            cfgFactor = (CfgFactor) var8.next();
                                        } while (!cfgFactor.isUsed());
                                    } while (!cfgFactor.isParmType());

                                    tmpIndex = 0;
                                    saveWarn = true;

                                    for (i = recDataList.size() - 1; i >= 0; --i) {
                                        if (((RecDataFactor) recDataList.get(i)).getFactorID() == cfgFactor.getId()) {
                                            tmpData[tmpIndex] = ((RecDataFactor) recDataList.get(i)).getDataValue();
                                            ++tmpIndex;
                                            if (tmpIndex == 2) {
                                                break;
                                            }
                                        }
                                    }
                                } while (tmpIndex < 2);

                                String var16 = cfgFactor.getName();
                                byte var11 = -1;
                                switch (var16.hashCode()) {
                                    case -1255953671:
                                        if (var16.equals("高锰酸盐指数")) {
                                            var11 = 0;
                                        }
                                        break;
                                    case 791379:
                                        if (var16.equals("总氮")) {
                                            var11 = 3;
                                        }
                                        break;
                                    case 794652:
                                        if (var16.equals("总磷")) {
                                            var11 = 2;
                                        }
                                        break;
                                    case 886022:
                                        if (var16.equals("氨氮")) {
                                            var11 = 1;
                                        }
                                }

                                switch (var11) {
                                    case 0:
                                        if (!cfgFactor.isCheckAvg()) {
                                            break;
                                        }

                                        i = 0;

                                        for (; i < 2; ++i) {
                                            cfgFactor.setDataTmp(tmpData[i]);
                                            if (cfgFactor.getDataTmp() > 1.5D * cfgFactor.getDataAvg()) {
                                                if (cfgFactor.getTmpLevel() < 5 && cfgFactor.getTmpLevel() < cfgFactor.getAvgLevel() + 2) {
                                                    saveWarn = false;
                                                }
                                            } else {
                                                saveWarn = false;
                                            }
                                        }

                                        if (saveWarn && cfgFactor.getDataMea() > 1.5D * cfgFactor.getDataAvg() && (cfgFactor.getCurLevel() == 5 || cfgFactor.getCurLevel() >= cfgFactor.getAvgLevel() + 2)) {
                                            this.saveCheckWarn(cfgFactor);
                                        }
                                        break;
                                    case 1:
                                    case 2:
                                    case 3:
                                        continue label141;
                                }
                            }
                        } while (!cfgFactor.isCheckAvg());

                        for (i = 0; i < 2; ++i) {
                            cfgFactor.setDataTmp(tmpData[i]);
                            if (cfgFactor.getDataTmp() > 2.0D * cfgFactor.getDataAvg()) {
                                if (cfgFactor.getTmpLevel() < 5 && cfgFactor.getTmpLevel() < cfgFactor.getAvgLevel() + 2) {
                                    saveWarn = false;
                                }
                            } else {
                                saveWarn = false;
                            }
                        }
                    } while (!saveWarn);
                } while (cfgFactor.getDataMea() <= 2.0D * cfgFactor.getDataAvg());
            } while (cfgFactor.getCurLevel() != 5 && cfgFactor.getCurLevel() < cfgFactor.getAvgLevel() + 2);

            this.saveCheckWarn(cfgFactor);
        }
    }

    private void savePumpFault() {
        int iPumpWork = (int) ScriptWord.getInstance().getSysVar("PumpWork");
        switch (iPumpWork) {
            case 1:
                this.saveRecFaultSys("系统故障", "采水泵一故障");
                break;
            case 2:
                this.saveRecFaultSys("系统故障", "采水泵二故障");
        }

    }

    public void saveSampleRecord() {
        if (this.sampleService != null && this.sampleService.getSampleVase() > 0) {
            List<RecSample> recSampleList = this.recMapper.getRecSample();
            if (this.sampleService.getSampleVase() < recSampleList.size()) {
                RecSample recSample = (RecSample) recSampleList.get(this.sampleService.getSampleVase() - 1);
                recSample.setRec_time(TimeHelper.getCurrentTimestamp());
                recSample.setVolume(this.sampleService.getVaseVolume());
                this.recMapper.updateRecSample(recSample);
            }
        }

        this.cfgMapper.updateCfgSample(this.sampleService.getCfgSample());
    }

    public void clearSampleRecord() {
        if (this.sampleService != null && this.sampleService.getSampleVase() > 0) {
            List<RecSample> recSampleList = this.recMapper.getRecSample();
            if (this.sampleService.getSampleVase() < recSampleList.size()) {
                RecSample recSample = (RecSample) recSampleList.get(this.sampleService.getSampleVase() - 1);
                recSample.setRec_time((Timestamp) null);
                recSample.setVolume((Integer) null);
                this.recMapper.updateRecSample(recSample);
            }
        }

        this.cfgMapper.updateCfgSample(this.sampleService.getCfgSample());
    }

    public void updateSampleRecord() {
        if (this.sampleService != null) {
            List<RecSample> recSampleList = this.recMapper.getRecSample();

            for (int i = 0; i < this.sampleService.getVaseSize(); ++i) {
                if (i < recSampleList.size()) {
                    RecSample recSample = (RecSample) recSampleList.get(i);
                    if (recSample.getRec_time() != null && this.sampleService.isbConnect() && this.sampleService.getVaseState()[i] > 0) {
                        recSample.setVolume(this.sampleService.getVaseState()[i]);
                        this.recMapper.updateRecSample(recSample);
                    }
                }
            }
        }

    }

    public void resetSampleRecord() {
        List<RecSample> recSampleList = this.recMapper.getRecSample();
        Iterator var2 = recSampleList.iterator();

        while (var2.hasNext()) {
            RecSample recSample = (RecSample) var2.next();
            recSample.setRec_time((Timestamp) null);
            recSample.setVolume((Integer) null);
            this.recMapper.updateRecSample(recSample);
        }

    }

    private void doFunctionTest() {
        this.timerSave = new Timer();
        this.timerSaveTask = new TimerTask() {
            public void run() {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
                String strCurTime = df.format(System.currentTimeMillis());
                Timestamp timestamp = Timestamp.valueOf(strCurTime);
                MonitorService.this.saveRecLogUser("admin", "登陆");
                MonitorService.this.saveRecFaultSys("系统故障", "供水失败");
                MonitorService.this.saveRecLogSample("1", "200", "手动留样", "");
            }
        };
        this.timerSave.schedule(this.timerSaveTask, 10000L, 60000L);
    }

    public void setQuality(int cmdType) {
        Iterator var2 = this.devServiceList.iterator();

        while (true) {
            while (true) {
                while (true) {
                    DevService devService;
                    do {
                        if (!var2.hasNext()) {
                            return;
                        }

                        devService = (DevService) var2.next();
                    } while (!devService.getDevType().toUpperCase().equals("QUALITY"));

                    String var4 = devService.mCfgDev.getProtocol();
                    byte var5 = -1;
                    switch (var4.hashCode()) {
                        case -505069059:
                            if (var4.equals("QualityModbus")) {
                                var5 = 0;
                            }
                            break;
                        case 1522728404:
                            if (var4.equals("QualityModbus1")) {
                                var5 = 1;
                            }
                    }

                    switch (var5) {
                        case 0:
                        case 1:
                            Iterator var6 = this.cfgFactorList.iterator();

                            while (var6.hasNext()) {
                                CfgFactor cfgFactor = (CfgFactor) var6.next();
                                if (devService.getRelatedID() == this.getDevID(cfgFactor.getId()) && cfgFactor.getDataMea() != null) {
                                    Integer addVol = null;
                                    switch (cfgFactor.getRcvrType()) {
                                        case 0:
                                            addVol = (int) (cfgFactor.getRcvrMotherVol() * 1000.0D);
                                            break;
                                        case 1:
                                            addVol = (int) (cfgFactor.getDataMea() * cfgFactor.getRcvrMultiple() * cfgFactor.getRcvrCupVol() * 1000.0D / cfgFactor.getRcvrMotherVal());
                                            if ((double) addVol / cfgFactor.getRcvrCupVol() > 0.02D) {
                                                addVol = (int) (cfgFactor.getRcvrCupVol() * 0.02D);
                                                if (cfgFactor.getRcvrType() == 1) {
                                                    this.rcvrOver = true;
                                                }
                                            } else {
                                                this.rcvrOver = false;
                                            }
                                    }

                                    if (addVol != null) {
                                        devService.doDevCmd(cmdType, addVol + "", (String) null);
                                    }

                                    this.saveRecLogSys("系统日志", devService.getDevName() + "质控设备　设置参数");
                                    break;
                                }
                            }
                    }
                }
            }
        }
    }

    public void writePlcRealtime() {
        if (this.plc_write && this.plc_write_type.toUpperCase().equals("REALTIME")) {
            this.scriptService.doWrite();
        }

    }

    public void writePlcHistory() {
        if (this.plc_write && this.plc_write_type.toUpperCase().equals("HISTORY")) {
            this.scriptService.doWrite();
        }

    }

    public void exitApplication() {
        if (this.auto_reboot) {
            ConfigurableApplicationContext ctx = (new SpringApplicationBuilder(new Class[]{Application.class})).web(WebApplicationType.NONE).run(new String[0]);
            int exitCode = SpringApplication.exit(ctx, new ExitCodeGenerator[]{new ExitCodeGenerator() {
                public int getExitCode() {
                    return 0;
                }
            }});
            System.exit(exitCode);
        }

    }

    public String getDtu_data() {
        return this.dtu_data;
    }

    private void saveHourData(Timestamp recTime, int factorID) {
        RecDataTime recDataTime = this.saveRecTime(recTime, 1);
        Iterator var4 = this.cfgFactorList.iterator();

        while (true) {
            CfgFactor cfgFactor;
            do {
                do {
                    do {
                        if (!var4.hasNext()) {
                            return;
                        }

                        cfgFactor = (CfgFactor) var4.next();
                    } while (!cfgFactor.isUsed());
                } while (!cfgFactor.isFiveType() && !cfgFactor.isParmType() && !cfgFactor.isSwType());
            } while (cfgFactor.getId() != factorID && factorID != 0);

            RecDataFactor recDataFactor = this.getRecDataFactor(cfgFactor, recDataTime, 1);
            if (recDataFactor != null) {
                try {
                    this.recMapper.addRecDataHour(recDataFactor);
                } catch (Exception var8) {
                    Utility.logInfo("因子" + cfgFactor.getName() + "小时数据入库异常");
                }
            }
        }
    }

    public CfgFactor getCfgFactorByGjCode(String factorCode) {
        Iterator var2 = this.cfgFactorList.iterator();

        CfgFactor cfgFactor;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            cfgFactor = (CfgFactor) var2.next();
        } while (!cfgFactor.getCodeGJ().equals(factorCode));

        return cfgFactor;
    }

    private class SaveDevDataThread extends Thread {
        DevService mDevService;
        Timestamp mTimestamp;
        int mCmdType;
        int mWaitTime;
        String mSaveType;

        public SaveDevDataThread(DevService devService, int cmdType, Timestamp timestamp, int delayTime, String saveType) {
            this.mDevService = devService;
            this.mTimestamp = timestamp;
            this.mCmdType = cmdType;
            this.mWaitTime = devService.getWaitTime() + delayTime;
            this.mSaveType = saveType;
        }

        public void run() {
            super.run();
            ScriptInfo recInfo = new ScriptInfo();
            recInfo.setRunTime(this.mTimestamp);
            recInfo.setRunCmd(this.mCmdType);
            recInfo.setSaveType(this.mSaveType);

            for (int i = 0; i < this.mWaitTime; ++i) {
                if (this.mDevService.isWaitExit()) {
                    return;
                }

                this.mDevService.setWaitTime(this.mWaitTime - i - 1);

                try {
                    Thread.sleep(1000L);
                } catch (Exception var4) {
                }
            }

            MonitorService.this.saveRecLogSys("系统日志", "保存" + this.mDevService.getFactorName() + Utility.getTaskName(this.mCmdType) + "结果");
            switch (this.mCmdType) {
                case 1:
                    MonitorService.this.saveRecData(recInfo, 1, this.mDevService.getFactorID());
                    MonitorService.this.saveRecParam(recInfo.getRunTime(), 451, this.mDevService.getFactorID());
                    break;
                case 2:
                    MonitorService.this.saveRecCheckStd(recInfo, this.mDevService.getFactorID());
                    MonitorService.this.saveRecParam(recInfo.getRunTime(), 452, this.mDevService.getFactorID());
                    break;
                case 3:
                    MonitorService.this.saveRecCheckZero(recInfo, this.mDevService.getFactorID());
                    MonitorService.this.saveRecParam(recInfo.getRunTime(), 453, this.mDevService.getFactorID());
                    break;
                case 4:
                    MonitorService.this.saveRecCheckSpan(recInfo, this.mDevService.getFactorID());
                    MonitorService.this.saveRecParam(recInfo.getRunTime(), 454, this.mDevService.getFactorID());
                    break;
                case 5:
                    MonitorService.this.saveRecCheckBlank(recInfo, this.mDevService.getFactorID());
                    MonitorService.this.saveRecParam(recInfo.getRunTime(), 455, this.mDevService.getFactorID());
                    break;
                case 6:
                    MonitorService.this.saveRecCheckPar(recInfo, this.mDevService.getFactorID());
                    MonitorService.this.saveRecParam(recInfo.getRunTime(), 456, this.mDevService.getFactorID());
                    break;
                case 7:
                    MonitorService.this.saveRecCheckRcvr(recInfo.getRunTime(), recInfo, this.mDevService.getFactorID());
                    MonitorService.this.saveRecParam(recInfo.getRunTime(), 457, this.mDevService.getFactorID());
            }

            MonitorService.this.saveRecLogSys("系统日志", "上传" + this.mDevService.getFactorName() + Utility.getTaskName(this.mCmdType) + "结果");
            this.mDevService.initDevState(0);
            this.mDevService.setRunState(false);
            MonitorService.this.saveRecLogDev(this.mCmdType, this.mDevService.getFactorID(), false);
            MonitorService.this.doUploadDevData(recInfo, this.mDevService.getFactorID());
        }
    }

    private class DevSerialQuery extends Thread {
        CommInter mCommInter;

        public DevSerialQuery(CommInter commInter) {
            this.mCommInter = commInter;
        }

        public void run() {
            super.run();

            try {
                Thread.sleep(30000L);
            } catch (Exception var3) {
            }

            while (!this.isInterrupted() && !MonitorService.restart) {
                try {
                    Iterator var1 = MonitorService.this.devServiceList.iterator();

                    while (var1.hasNext()) {
                        DevService devService = (DevService) var1.next();
                        if (this.mCommInter != null && devService.mInterComm == this.mCommInter && devService.mCfgDev != null) {
                            devService.doQuery();
                            Thread.sleep((long) this.mCommInter.getSerialDelay());
                        }
                    }

                    Thread.sleep(100L);
                } catch (InterruptedException var4) {
                    break;
                }
            }

        }
    }
}
