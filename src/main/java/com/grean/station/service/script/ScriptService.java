//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.script;

import com.grean.lang.Interpreter;
import com.grean.station.comm.CommInter;
import com.grean.station.dao.PLCMapper;
import com.grean.station.dao.RecMapper;
import com.grean.station.domain.DO.cfg.CfgDev;
import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.cfg.CfgModbusVal;
import com.grean.station.domain.DO.plc.DefWord;
import com.grean.station.domain.DO.plc.KeyWord;
import com.grean.station.domain.DO.plc.VarWord;
import com.grean.station.domain.DO.rec.RecDataFactor;
import com.grean.station.domain.DO.rec.RecDataTime;
import com.grean.station.domain.request.RtdCount;
import com.grean.station.service.MonitorService;
import com.grean.station.utils.Utility;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.script.ScriptEngine;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class ScriptService {
    @Value("${plc_write:false}")
    private Boolean plc_write;
    @Value("${plc_write_type:REALTIME}")
    private String plc_write_type;
    @Value("${plc_log:false}")
    private Boolean plc_log;
    @Autowired
    PLCMapper plcMapper;
    @Autowired
    RecMapper recMapper;
    @Autowired
    MonitorService monitorService;
    @Autowired
    PLCModbus plcModbus;
    @Autowired
    PLCHostlink plcHostlink;
    @Autowired
    PLCModbusTcp plcModbusTcp;
    @Autowired
    PLCShangYang plcShangYang;
    @Autowired
    PLCGrean plcGrean;
    @Autowired
    PLCJuGuang plcJuGuang;
    @Autowired
    PLCSchneider plcSchneider;
    @Autowired
    PLCModbusTcpTest plcModbusTcpTest;
    @Autowired
    PLCModbusNsbd plcModbusNsbd;
    @Autowired
    PLCModbus1200 plcModbus1200;
    @Autowired
    PLCFanYiModbus plcFanYiModbus;
    @Autowired
    PLCFanYiTcp plcFanYiTcp;
    @Autowired
    PLCWeiQiModbus plcWeiQiModbus;
    @Autowired
    PLCHostlink2 plcHostlink2;
    @Autowired
    PLCHostlink3 plcHostlink3;
    private ScriptStateListener stateListener;
    private ScriptCountListener countListener;
    private String exeScriptFile = "";
    private String exeScriptFileFive = "";
    private List<String> exeScriptList = new LinkedList();
    private List<String> exeScriptFiveList = new LinkedList();
    private Interpreter scriptEngine;
    private Interpreter scriptEngineFive;
    public List<KeyWord> keyWordList = new ArrayList();
    public List<VarWord> varWordList = new ArrayList();
    public List<DefWord> defWordList = new ArrayList();
    private boolean[] QStatus = new boolean[128];
    private boolean[] IStatus = new boolean[128];
    private float[] AIValues = new float[8];
    private float[] minAIVal = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
    private float[] maxAIVal = new float[]{1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F};
    private int circleIndex;
    private boolean onScript = false;
    private boolean onScriptFive = false;
    private CommInter mInterComm = null;
    private int mPlcType;
    private ScriptService.ScriptThread scriptThread;
    private ScriptService.ScriptThreadFive scriptThreadFive;
    private boolean jumpScript = false;
    private boolean bConnect = false;
    public Logger logger = LoggerFactory.getLogger("script");
    private RtdCount rtdCount;

    public ScriptService() {
    }

    public boolean isbConnect() {
        return this.bConnect;
    }

    public void setbConnect(boolean bConnect) {
        this.bConnect = bConnect;
    }

    public void setJumpScript(boolean jumpScript) {
        this.jumpScript = jumpScript;
    }

    public void setStateListener(ScriptStateListener StateListener) {
        this.stateListener = StateListener;
    }

    public void setCountListener(ScriptCountListener countListener) {
        this.countListener = countListener;
    }

    public void init(CommInter interComm, CfgDev cfgDev, int plcType) {
        this.exeScriptFile = "test.js";
        this.exeScriptFileFive = "testFive.js";
        this.mInterComm = interComm;
        this.mPlcType = plcType;
        this.scriptEngine = new Interpreter();
        this.scriptEngineFive = new Interpreter();
        this.rtdCount = new RtdCount();
        this.refreshVarList();
        this.refreshMinMax();
        switch(this.mPlcType) {
            case 1:
                this.plcModbus.init(this.mInterComm, cfgDev, this.minAIVal, this.maxAIVal);
                break;
            case 2:
                this.plcHostlink.init(this.mInterComm, cfgDev, this.minAIVal, this.minAIVal);
                break;
            case 3:
                this.plcModbusTcp.init(this.mInterComm, cfgDev, this.minAIVal, this.maxAIVal);
                break;
            case 4:
                this.plcShangYang.init(this.mInterComm, cfgDev, this.minAIVal, this.maxAIVal);
                break;
            case 5:
                this.plcGrean.init(this.mInterComm, cfgDev, this.minAIVal, this.maxAIVal);
                break;
            case 6:
                this.plcJuGuang.init(this.mInterComm, cfgDev, this.minAIVal, this.maxAIVal);
                break;
            case 7:
                this.plcSchneider.init(this.mInterComm, cfgDev, this.minAIVal, this.maxAIVal);
                break;
            case 8:
                this.plcModbusTcpTest.init(this.mInterComm, cfgDev, this.minAIVal, this.maxAIVal);
                break;
            case 9:
                this.plcModbusNsbd.init(this.mInterComm, cfgDev, this.minAIVal, this.maxAIVal);
                break;
            case 10:
                this.plcModbus1200.init(this.mInterComm, cfgDev, this.minAIVal, this.maxAIVal);
                break;
            case 11:
                this.plcFanYiModbus.init(this.mInterComm, cfgDev, this.minAIVal, this.maxAIVal);
                break;
            case 12:
                this.plcFanYiTcp.init(this.mInterComm, cfgDev, this.minAIVal, this.maxAIVal);
                break;
            case 13:
                this.plcWeiQiModbus.init(this.mInterComm, cfgDev, this.minAIVal, this.maxAIVal);
                break;
            case 14:
                this.plcHostlink2.init(this.mInterComm, cfgDev, this.minAIVal, this.maxAIVal);
                break;
            case 15:
                this.plcHostlink3.init(this.mInterComm, cfgDev, this.minAIVal, this.maxAIVal);
        }

    }

    public void free() {
        switch(this.mPlcType) {
            case 1:
                this.plcModbus.free();
                break;
            case 2:
                this.plcHostlink.free();
                break;
            case 3:
                this.plcModbusTcp.free();
                break;
            case 4:
                this.plcShangYang.free();
                break;
            case 5:
                this.plcGrean.free();
                break;
            case 6:
                this.plcJuGuang.free();
                break;
            case 7:
                this.plcSchneider.free();
                break;
            case 8:
                this.plcModbusTcpTest.free();
                break;
            case 9:
                this.plcModbusNsbd.free();
                break;
            case 10:
                this.plcModbus1200.free();
                break;
            case 11:
                this.plcFanYiModbus.free();
                break;
            case 12:
                this.plcFanYiTcp.free();
                break;
            case 13:
                this.plcWeiQiModbus.free();
                break;
            case 14:
                this.plcHostlink2.free();
                break;
            case 15:
                this.plcHostlink3.free();
        }

    }

    public void updateVarAIList() {
        for(int i = 0; i < this.defWordList.size(); ++i) {
            if (((DefWord)this.defWordList.get(i)).getType() == 4) {
                Iterator var2 = this.monitorService.cfgFactorList.iterator();

                while(var2.hasNext()) {
                    CfgFactor cfgFactor = (CfgFactor)var2.next();
                    if (cfgFactor.getName().equals(((DefWord)this.defWordList.get(i)).getName()) && cfgFactor.getDevID() != 1) {
                        if (cfgFactor.getDataMea() != null) {
                            double dValue = cfgFactor.getDataMea();
                            ((DefWord)this.defWordList.get(i)).setCurvalue((float)dValue);
                        }
                        break;
                    }
                }
            }
        }

        Iterator var6 = this.defWordList.iterator();

        while(true) {
            while(true) {
                DefWord defWord;
                do {
                    if (!var6.hasNext()) {
                        return;
                    }

                    defWord = (DefWord)var6.next();
                } while(defWord.getType() != 4 && defWord.getType() != 2);

                Iterator var8 = this.varWordList.iterator();

                while(var8.hasNext()) {
                    VarWord varWord = (VarWord)var8.next();
                    if (defWord.getName().equals(varWord.getCnName()) && defWord.getType() == varWord.getType()) {
                        if ((double)defWord.getCurvalue() != varWord.getCurValue()) {
                            varWord.setCurValue((double)defWord.getCurvalue());
                        }
                        break;
                    }
                }
            }
        }
    }
    @SuppressWarnings("FallThrough")
    public void updateVarList(int varType, boolean[] qStatus, boolean[] iStatus, float[] aiValues) {
        int i;
        for(i = 0; i < this.QStatus.length; ++i) {
            this.QStatus[i] = qStatus[i];
        }

        for(i = 0; i < this.IStatus.length; ++i) {
            this.IStatus[i] = iStatus[i];
        }

        for(i = 0; i < this.AIValues.length; ++i) {
            this.AIValues[i] = aiValues[i];
        }

        for(i = 0; i < this.defWordList.size(); ++i) {
            if (((DefWord)this.defWordList.get(i)).getType() == varType) {
                boolean varExist;
                Iterator var7;
                switch(varType) {
                    case 1:
                        if (this.QStatus[((DefWord)this.defWordList.get(i)).getAddress()]) {
                            ((DefWord)this.defWordList.get(i)).setCurvalue(1.0F);
                        } else {
                            ((DefWord)this.defWordList.get(i)).setCurvalue(0.0F);
                        }
                        continue;
                    case 2:
                        if (this.IStatus[((DefWord)this.defWordList.get(i)).getAddress()]) {
                            ((DefWord)this.defWordList.get(i)).setCurvalue(1.0F);
                        } else {
                            ((DefWord)this.defWordList.get(i)).setCurvalue(0.0F);
                        }
                    case 3:
                    default:
                        continue;
                    case 4:
                        varExist = false;
                        var7 = this.monitorService.cfgFactorList.iterator();
                }

                while(var7.hasNext()) {
                    CfgFactor cfgFactor = (CfgFactor)var7.next();
                    if (cfgFactor.getName().equals(((DefWord)this.defWordList.get(i)).getName()) && cfgFactor.getDevID() != 1) {
                        varExist = true;
                        if (cfgFactor.getDataMea() != null) {
                            double dValue = cfgFactor.getDataMea();
                            ((DefWord)this.defWordList.get(i)).setCurvalue((float)dValue);
                        }
                        break;
                    }
                }

                if (!varExist) {
                    ((DefWord)this.defWordList.get(i)).setCurvalue(this.AIValues[((DefWord)this.defWordList.get(i)).getAddress()]);
                }
            }
        }

        Iterator var14 = this.defWordList.iterator();

        while(true) {
            while(true) {
                DefWord defWord;
                do {
                    if (!var14.hasNext()) {
                        var14 = this.monitorService.cfgFactorList.iterator();

                        while(true) {
                            while(var14.hasNext()) {
                                CfgFactor cfgFactor = (CfgFactor)var14.next();
                                float fTmp = 0.0F;
                                if (cfgFactor.getDataMea() != null) {
                                    String strTmp = String.format("%.4f", cfgFactor.getDataMea());
                                    fTmp = Float.parseFloat(strTmp);
                                }

                                Iterator var18 = this.varWordList.iterator();

                                while(var18.hasNext()) {
                                    VarWord varWord = (VarWord)var18.next();
                                    if (varWord.getEnName().equals("Factor" + cfgFactor.getId())) {
                                        varWord.setCurValue((double)fTmp);
                                        break;
                                    }
                                }
                            }

                            return;
                        }
                    }

                    defWord = (DefWord)var14.next();
                } while(defWord.getType() != varType);

                Iterator var13 = this.varWordList.iterator();

                while(var13.hasNext()) {
                    VarWord varWord = (VarWord)var13.next();
                    if (defWord.getName().equals(varWord.getCnName()) && defWord.getType() == varWord.getType()) {
                        if ((double)defWord.getCurvalue() != varWord.getCurValue()) {
                            varWord.setCurValue((double)defWord.getCurvalue());
                        }
                        break;
                    }
                }
            }
        }
    }

    private boolean addVarWord(String strCnName, String strDefVal, List outList) {
        String strVal = null;
        float fTmp = 0.0F;
        Iterator var7;
        VarWord varWord;
        if (Utility.isNumeric(strDefVal)) {
            strVal = strDefVal;
            fTmp = Float.parseFloat(strDefVal);
        } else {
            var7 = this.varWordList.iterator();

            while(var7.hasNext()) {
                varWord = (VarWord)var7.next();
                if (varWord.getCnName().equals(strDefVal)) {
                    strVal = varWord.getEnName();
                    fTmp = Float.parseFloat(varWord.getCurValue() + "");
                    break;
                }
            }
        }

        if (strVal == null) {
            return false;
        } else {
            var7 = this.varWordList.iterator();

            String strTmp;
            do {
                if (!var7.hasNext()) {
                    strTmp = "VarWord" + (this.varWordList.size() + 1);
                    this.varWordList.add(new VarWord(this.varWordList.size() + 1, strCnName, strTmp, 20, fTmp));
                    strTmp = strTmp + " = " + strVal + ";";
                    outList.add(strTmp);
                    return true;
                }

                varWord = (VarWord)var7.next();
            } while(!varWord.getCnName().equals(strCnName));

            varWord.setCurValue((double)fTmp);
            strTmp = varWord.getEnName() + " = " + strVal + ";";
            outList.add(strTmp);
            return true;
        }
    }

    private void addMarkWord(String strCnName, List outList) {
        String strTmp;
        for(int i = 0; i < this.varWordList.size(); ++i) {
            if (((VarWord)this.varWordList.get(i)).getCnName().equals(strCnName)) {
                strTmp = ((VarWord)this.varWordList.get(i)).getEnName() + ";";
                outList.add(strTmp);
                return;
            }
        }

        strTmp = "markWord" + (this.varWordList.size() + 1);
        this.varWordList.add(new VarWord(this.varWordList.size() + 1, strCnName, strTmp, 10, 0.0F));
        strTmp = strTmp + ";";
        outList.add(strTmp);
    }

    private boolean doVarOperate(String[] cmdArray, List outList) {
        boolean bModify = false;
        if (cmdArray.length == 3) {
            for(int i = 0; i < this.varWordList.size(); ++i) {
                if (((VarWord)this.varWordList.get(i)).getCnName().equals(cmdArray[0])) {
                    String strTmp;
                    if (Utility.isNumeric(cmdArray[2])) {
                        float fTmp = Float.parseFloat(cmdArray[2]);
                        String var7 = cmdArray[1];
                        byte var8 = -1;
                        switch(var7.hashCode()) {
                            case 20056:
                                if (var7.equals("乘")) {
                                    var8 = 2;
                                }
                                break;
                            case 20943:
                                if (var7.equals("减")) {
                                    var8 = 1;
                                }
                                break;
                            case 21152:
                                if (var7.equals("加")) {
                                    var8 = 0;
                                }
                                break;
                            case 38500:
                                if (var7.equals("除")) {
                                    var8 = 3;
                                }
                                break;
                            case 998501:
                                if (var7.equals("等于")) {
                                    var8 = 4;
                                }
                        }

                        switch(var8) {
                            case 0:
                                strTmp = ((VarWord)this.varWordList.get(i)).getEnName() + " = " + ((VarWord)this.varWordList.get(i)).getEnName() + " + " + fTmp + ";";
                                outList.add(strTmp);
                                bModify = true;
                                return bModify;
                            case 1:
                                strTmp = ((VarWord)this.varWordList.get(i)).getEnName() + " = " + ((VarWord)this.varWordList.get(i)).getEnName() + " - " + fTmp + ";";
                                outList.add(strTmp);
                                bModify = true;
                                return bModify;
                            case 2:
                                strTmp = ((VarWord)this.varWordList.get(i)).getEnName() + " = " + ((VarWord)this.varWordList.get(i)).getEnName() + " * " + fTmp + ";";
                                outList.add(strTmp);
                                bModify = true;
                                return bModify;
                            case 3:
                                strTmp = ((VarWord)this.varWordList.get(i)).getEnName() + " = " + ((VarWord)this.varWordList.get(i)).getEnName() + " / " + fTmp + ";";
                                outList.add(strTmp);
                                bModify = true;
                                return bModify;
                            case 4:
                                strTmp = ((VarWord)this.varWordList.get(i)).getEnName() + " = " + fTmp + ";";
                                outList.add(strTmp);
                                bModify = true;
                                return bModify;
                            default:
                                return bModify;
                        }
                    } else {
                        for(int j = 0; j < this.varWordList.size(); ++j) {
                            if (((VarWord)this.varWordList.get(j)).getCnName().equals(cmdArray[2])) {
                                String var11 = cmdArray[1];
                                byte var9 = -1;
                                switch(var11.hashCode()) {
                                    case 20056:
                                        if (var11.equals("乘")) {
                                            var9 = 2;
                                        }
                                        break;
                                    case 20943:
                                        if (var11.equals("减")) {
                                            var9 = 1;
                                        }
                                        break;
                                    case 21152:
                                        if (var11.equals("加")) {
                                            var9 = 0;
                                        }
                                        break;
                                    case 38500:
                                        if (var11.equals("除")) {
                                            var9 = 3;
                                        }
                                        break;
                                    case 998501:
                                        if (var11.equals("等于")) {
                                            var9 = 4;
                                        }
                                }

                                switch(var9) {
                                    case 0:
                                        strTmp = ((VarWord)this.varWordList.get(i)).getEnName() + " = " + ((VarWord)this.varWordList.get(i)).getEnName() + " + " + ((VarWord)this.varWordList.get(j)).getEnName() + ";";
                                        outList.add(strTmp);
                                        bModify = true;
                                        return bModify;
                                    case 1:
                                        strTmp = ((VarWord)this.varWordList.get(i)).getEnName() + " = " + ((VarWord)this.varWordList.get(i)).getEnName() + " - " + ((VarWord)this.varWordList.get(j)).getEnName() + ";";
                                        outList.add(strTmp);
                                        bModify = true;
                                        return bModify;
                                    case 2:
                                        strTmp = ((VarWord)this.varWordList.get(i)).getEnName() + " = " + ((VarWord)this.varWordList.get(i)).getEnName() + " * " + ((VarWord)this.varWordList.get(j)).getEnName() + ";";
                                        outList.add(strTmp);
                                        bModify = true;
                                        return bModify;
                                    case 3:
                                        strTmp = ((VarWord)this.varWordList.get(i)).getEnName() + " = " + ((VarWord)this.varWordList.get(i)).getEnName() + " / " + ((VarWord)this.varWordList.get(j)).getEnName() + ";";
                                        outList.add(strTmp);
                                        bModify = true;
                                        return bModify;
                                    case 4:
                                        strTmp = ((VarWord)this.varWordList.get(i)).getEnName() + " = " + ((VarWord)this.varWordList.get(j)).getEnName() + ";";
                                        outList.add(strTmp);
                                        bModify = true;
                                        return bModify;
                                    default:
                                        return bModify;
                                }
                            }
                        }

                        return bModify;
                    }
                }
            }
        }

        return bModify;
    }

    private boolean doJudgeOperate(String[] cmdArray, List outList) {
        String strTmp = "if(";

        for(int i = 1; i < cmdArray.length; ++i) {
            boolean bJudge = false;
            if (cmdArray[i].equals("大于")) {
                strTmp = strTmp + " >";
                bJudge = true;
            } else if (cmdArray[i].equals("小于")) {
                strTmp = strTmp + " <";
                bJudge = true;
            } else if (cmdArray[i].equals("等于")) {
                strTmp = strTmp + " ==";
                bJudge = true;
            } else if (cmdArray[i].equals("不等于")) {
                strTmp = strTmp + " !=";
                bJudge = true;
            } else if (cmdArray[i].equals("并且")) {
                strTmp = strTmp + " &&";
                bJudge = true;
            } else if (cmdArray[i].equals("或者")) {
                strTmp = strTmp + " ||";
                bJudge = true;
            } else if (Utility.isNumeric(cmdArray[i])) {
                strTmp = strTmp + " " + cmdArray[i];
                bJudge = true;
            } else {
                for(int j = 0; j < this.varWordList.size(); ++j) {
                    if (((VarWord)this.varWordList.get(j)).getCnName().equals(cmdArray[i])) {
                        strTmp = strTmp + " " + ((VarWord)this.varWordList.get(j)).getEnName();
                        bJudge = true;
                        break;
                    }
                }
            }

            if (!bJudge) {
                return false;
            }
        }

        strTmp = strTmp + ")";
        outList.add(strTmp);
        return true;
    }

    private boolean doJumpOperate(String jumpName, List outList) {
        boolean bJump = false;
        boolean bAdd = true;
        Iterator var6 = this.varWordList.iterator();

        VarWord varWord;
        while(var6.hasNext()) {
            varWord = (VarWord)var6.next();
            if (varWord.getCnName().equals(jumpName) && varWord.getType() == 10) {
                bAdd = false;
                break;
            }
        }

        String strTmp;
        if (bAdd) {
            strTmp = "markWord" + (this.varWordList.size() + 1);
            this.varWordList.add(new VarWord(this.varWordList.size() + 1, jumpName, strTmp, 10, 0.0F));
        }

        var6 = this.varWordList.iterator();

        while(var6.hasNext()) {
            varWord = (VarWord)var6.next();
            if (varWord.getCnName().equals(jumpName) && varWord.getType() == 10) {
                strTmp = "jump('" + varWord.getEnName() + "');";
                outList.add(strTmp);
                bJump = true;
                break;
            }
        }

        return bJump;
    }

    private boolean addOutputList(String[] cmdArray, List outList) {
        if (cmdArray[0].length() == 0) {
            return true;
        } else if (cmdArray[0].length() > 2 && cmdArray[0].substring(0, 2).equals("//")) {
            return true;
        } else {
            String var4 = cmdArray[0];
            byte var5 = -1;
            switch(var4.hashCode()) {
                case 123:
                    if (var4.equals("{")) {
                        var5 = 14;
                    }
                    break;
                case 125:
                    if (var4.equals("}")) {
                        var5 = 15;
                    }
                    break;
                case 684762:
                    if (var4.equals("关闭")) {
                        var5 = 4;
                    }
                    break;
                case 688819:
                    if (var4.equals("否则")) {
                        var5 = 13;
                    }
                    break;
                case 698073:
                    if (var4.equals("启用")) {
                        var5 = 8;
                    }
                    break;
                case 702711:
                    if (var4.equals("变量")) {
                        var5 = 9;
                    }
                    break;
                case 727008:
                    if (var4.equals("复位")) {
                        var5 = 0;
                    }
                    break;
                case 736858:
                    if (var4.equals("如果")) {
                        var5 = 12;
                    }
                    break;
                case 804621:
                    if (var4.equals("打开")) {
                        var5 = 3;
                    }
                    break;
                case 815813:
                    if (var4.equals("执行")) {
                        var5 = 7;
                    }
                    break;
                case 842428:
                    if (var4.equals("显示")) {
                        var5 = 10;
                    }
                    break;
                case 861321:
                    if (var4.equals("标记")) {
                        var5 = 16;
                    }
                    break;
                case 934923:
                    if (var4.equals("状态")) {
                        var5 = 5;
                    }
                    break;
                case 1002844:
                    if (var4.equals("等待")) {
                        var5 = 11;
                    }
                    break;
                case 1033626:
                    if (var4.equals("终止")) {
                        var5 = 2;
                    }
                    break;
                case 1141125:
                    if (var4.equals("调用")) {
                        var5 = 6;
                    }
                    break;
                case 1163225:
                    if (var4.equals("跳转")) {
                        var5 = 17;
                    }
                    break;
                case 1163770:
                    if (var4.equals("退出")) {
                        var5 = 1;
                    }
            }

            String strTmp;
            switch(var5) {
                case 0:
                    if (cmdArray.length == 1) {
                        strTmp = "println('" + cmdArray[0] + "');";
                        outList.add(strTmp);
                        return true;
                    }
                    break;
                case 1:
                case 2:
                    if (cmdArray.length == 1) {
                        strTmp = "println('" + cmdArray[0] + "');";
                        outList.add(strTmp);
                        strTmp = "exit();";
                        outList.add(strTmp);
                        return true;
                    }
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    if (cmdArray.length == 2) {
                        strTmp = "println('" + cmdArray[0] + " " + cmdArray[1] + "');";
                        outList.add(strTmp);
                        return true;
                    }
                    break;
                case 8:
                    if (cmdArray.length == 2) {
                        strTmp = "println('" + cmdArray[0] + " " + cmdArray[1] + "');";
                        outList.add(strTmp);
                        strTmp = "exit();";
                        outList.add(strTmp);
                        return true;
                    }
                    break;
                case 9:
                    if (cmdArray.length == 4 && cmdArray[2].equals("等于")) {
                        return this.addVarWord(cmdArray[1], cmdArray[3], outList);
                    }
                    break;
                case 10:
                    if (cmdArray.length == 2) {
                        Iterator var9 = this.varWordList.iterator();

                        while(var9.hasNext()) {
                            VarWord varWord = (VarWord)var9.next();
                            if (varWord.getCnName().equals(cmdArray[1])) {
                                strTmp = "show(" + varWord.getEnName() + ");";
                                outList.add(strTmp);
                                strTmp = "println('显示 " + cmdArray[1] + "');";
                                outList.add(strTmp);
                                return true;
                            }
                        }
                    }
                    break;
                case 11:
                    if (cmdArray.length == 2) {
                        if (Utility.isNumeric(cmdArray[1])) {
                            strTmp = "println('等待 " + cmdArray[1] + "');";
                            outList.add(strTmp);
                            strTmp = "sleep(" + cmdArray[1] + ");";
                            outList.add(strTmp);
                            return true;
                        }

                        for(int i = 0; i < this.varWordList.size(); ++i) {
                            if (((VarWord)this.varWordList.get(i)).getCnName().equals(cmdArray[1])) {
                                strTmp = "println('计时器 " + ((VarWord)this.varWordList.get(i)).getCnName() + "');";
                                outList.add(strTmp);
                                strTmp = "println('等待 " + ((VarWord)this.varWordList.get(i)).getCurValue() + "');";
                                outList.add(strTmp);
                                strTmp = "sleep(" + ((VarWord)this.varWordList.get(i)).getEnName() + ");";
                                outList.add(strTmp);
                                return true;
                            }
                        }
                    }
                    break;
                case 12:
                    if (cmdArray.length == 2 && cmdArray[1].equals("量程变化")) {
                        outList.add("if(RangeJudge==1)");
                        return true;
                    }

                    if (this.doJudgeOperate(cmdArray, outList)) {
                        return true;
                    }
                    break;
                case 13:
                    outList.add("else");
                    return true;
                case 14:
                case 15:
                    if (cmdArray.length == 1) {
                        outList.add(cmdArray[0]);
                        return true;
                    }
                    break;
                case 16:
                    if (cmdArray.length == 2) {
                        this.addMarkWord(cmdArray[1], outList);
                        return true;
                    }
                    break;
                case 17:
                    if (cmdArray.length == 2 && this.doJumpOperate(cmdArray[1], outList)) {
                        return true;
                    }
                    break;
                default:
                    if (this.doVarOperate(cmdArray, outList)) {
                        return true;
                    }
            }

            strTmp = "";

            for(int i = 0; i < cmdArray.length; ++i) {
                strTmp = strTmp + cmdArray[i] + " ";
            }

            this.logger.info("脚本语句 " + strTmp + " 无法解析");
            return false;
        }
    }

    public void refreshMinMax() {
        ScriptWord.getInstance().init(this.plcMapper);
        this.defWordList = ScriptWord.getInstance().getDefWordList();
        int aiCount = 0;
        Iterator var2 = this.defWordList.iterator();

        while(var2.hasNext()) {
            DefWord defWord = (DefWord)var2.next();
            if (defWord.getType() == 4 && aiCount < 8) {
                this.minAIVal[aiCount] = defWord.getDefvalue();
                this.maxAIVal[aiCount] = defWord.getCurvalue();
                ++aiCount;
            }
        }

    }

    public void refreshVarList() {
        ScriptWord.getInstance().init(this.plcMapper);
        this.defWordList = ScriptWord.getInstance().getDefWordList();
        this.keyWordList = ScriptWord.getInstance().getKeyWordList();
        this.varWordList = ScriptWord.getInstance().getVarWordList();

        for(int i = 0; i < this.defWordList.size(); ++i) {
            VarWord tmpVar = new VarWord(this.varWordList.size() + 1, ((DefWord)this.defWordList.get(i)).getName(), ((DefWord)this.defWordList.get(i)).getPname(), ((DefWord)this.defWordList.get(i)).getType(), ((DefWord)this.defWordList.get(i)).getDefvalue());
            this.varWordList.add(tmpVar);
        }

        Iterator var5 = this.monitorService.cfgFactorList.iterator();

        while(var5.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor)var5.next();
            float fTmp = 0.0F;
            if (cfgFactor.getDataMea() != null) {
                String strTmp = String.format("%.4f", cfgFactor.getDataMea());
                fTmp = Float.parseFloat(strTmp);
            }

            VarWord tmpVar = new VarWord(this.varWordList.size() + 1, cfgFactor.getName(), "Factor" + cfgFactor.getId(), 0, fTmp);
            this.varWordList.add(tmpVar);
        }

        VarWord tmpVar = new VarWord(this.varWordList.size() + 1, "量程变化", "RangeJudge", 0, 0.0F);
        this.varWordList.add(tmpVar);
    }

    public boolean initScript(String InputFileName, String OutputFileName, List outList) {
        String sysScriptType = "";
        this.refreshVarList();

        try {
            int iSystemScript = (int)ScriptWord.getInstance().getSysVar("SystemScript");
            int iPumpType = (int)ScriptWord.getInstance().getSysVar("PumpType");
            int iPumpWater = (int)ScriptWord.getInstance().getSysVar("PumpWater");
            this.saveScriptLog("DebugInfo 1111");
            if (iPumpType == 1) {
                sysScriptType = "ZXB";
            } else {
                sysScriptType = "QSB";
            }

            if (iPumpWater == 0) {
                sysScriptType = sysScriptType + "WS";
            } else {
                sysScriptType = sysScriptType + "YS";
            }

            this.saveScriptLog("DebugInfo 2222");
            File InputFile = ResourceUtils.getFile("classpath:scripts/" + sysScriptType + "/" + InputFileName);
            List<String> inputList = FileUtils.readLines(InputFile, "UTF-8");
            this.saveScriptLog("DebugInfo 3333");
            Iterator var11 = this.varWordList.iterator();

            String strTmp;
            while(var11.hasNext()) {
                VarWord varWord = (VarWord)var11.next();
                strTmp = varWord.getEnName() + " = " + varWord.getCurValue() + ";";
                outList.add(strTmp);
            }

            this.saveScriptLog("DebugInfo 4444");

            for(int i = 0; i < inputList.size(); ++i) {
                strTmp = (String)inputList.get(i);
                strTmp = strTmp.trim();
                String[] strArray = strTmp.split(" ");
                if (!this.addOutputList(strArray, outList)) {
                    return false;
                }
            }

            outList.add("println('退出');");
            this.saveScriptLog("DebugInfo 5555");
            if (OutputFileName.length() > 0) {
                File OutputFile = ResourceUtils.getFile("classpath:scripts/" + OutputFileName);
                FileUtils.writeLines(OutputFile, outList, false);
            }

            this.saveScriptLog("DebugInfo 6666");
            return true;
        } catch (IOException var13) {
            this.logger.info("脚本 " + sysScriptType + "/" + InputFileName + " 打开失败");
            this.logger.info(var13.getMessage());
            this.logger.info(var13.toString());
            return false;
        }
    }

    public boolean startScript(final String scriptFileName) {
        boolean bResult = false;
        this.exeScriptList.clear();
        if (!this.initScript(scriptFileName, this.exeScriptFile, this.exeScriptList)) {
            this.saveRecLogSys("初始化脚本 " + scriptFileName + " 失败");
            return bResult;
        } else {
            if (this.mInterComm != null && this.mInterComm.isConnect()) {
                this.refreshVar(this.scriptEngine);
                this.scriptEngine.getPrintList().clear();

                try {
                    this.scriptThread = new ScriptService.ScriptThread(scriptFileName);
                    this.scriptThread.start();
                    this.saveRecLogSys("启动脚本 " + scriptFileName + " 成功");
                    (new Thread(new Runnable() {
                        public void run() {
                            try {
                                if (ScriptService.this.exeScriptFile.length() > 0) {
                                    File outPutFile = ResourceUtils.getFile("classpath:scripts/" + ScriptService.this.exeScriptFile);
                                    FileReader fileReader = new FileReader(outPutFile);
                                    ScriptService.this.scriptEngine.eval(fileReader);
                                }
                            } catch (Exception var3) {
                                ScriptService.this.saveRecLogSys("执行脚本 " + scriptFileName + " 失败");
                                ScriptService.this.scriptThread.finish();
                                ScriptService.this.setOnScript(false);
                                ScriptService.this.monitorService.setSysStatus("系统待机");
                            }

                        }
                    })).start();
                } catch (Exception var4) {
                    this.logger.info("运行脚本错误");
                }

                bResult = true;
            } else {
                this.saveRecLogSys("启动脚本 " + scriptFileName + " 失败");
            }

            return bResult;
        }
    }

    public boolean startScriptFive(String scriptFileName) {
        boolean bResult = false;
        this.exeScriptFiveList.clear();
        if (!this.initScript(scriptFileName, this.exeScriptFileFive, this.exeScriptFiveList)) {
            this.saveRecLogSys("初始化五参数脚本 " + scriptFileName + " 失败");
            return bResult;
        } else {
            if (this.mInterComm != null && this.mInterComm.isConnect()) {
                this.refreshVar(this.scriptEngineFive);
                this.scriptEngineFive.getPrintList().clear();

                try {
                    this.scriptThreadFive = new ScriptService.ScriptThreadFive(scriptFileName);
                    this.scriptThreadFive.start();
                    this.saveRecLogSys("启动五参数脚本: " + scriptFileName + " 成功");
                    (new Thread(new Runnable() {
                        public void run() {
                            try {
                                if (ScriptService.this.exeScriptFileFive.length() > 0) {
                                    File outPutFile = ResourceUtils.getFile("classpath:scripts/" + ScriptService.this.exeScriptFileFive);
                                    FileReader fileReader = new FileReader(outPutFile);
                                    ScriptService.this.scriptEngineFive.eval(fileReader);
                                }
                            } catch (Exception var3) {
                            }

                        }
                    })).start();
                } catch (Exception var4) {
                    this.logger.info("运行五参数脚本错误");
                }

                bResult = true;
            } else {
                this.saveRecLogSys("启动五参数脚本: " + scriptFileName + " 失败");
            }

            return bResult;
        }
    }

    public boolean stopScript() {
        try {
            if (this.isOnScript()) {
                this.scriptEngine.setScriptBreak(true);
                this.scriptEngine.setScriptExit(true);
                this.scriptEngine.setScriptSleep(false);
                this.scriptThread.finish();
                this.setOnScript(false);
            }

            if (this.isOnScriptFive()) {
                this.scriptEngineFive.setScriptBreak(true);
                this.scriptEngineFive.setScriptExit(true);
                this.scriptEngineFive.setScriptSleep(false);
                this.scriptThreadFive.interrupt();
                this.setOnScriptFive(false);
            }

            this.doReset();
            this.rtdCount.setCountTitle("系统待机");
            this.rtdCount.setCountValue(0);
            return true;
        } catch (Exception var2) {
            return false;
        }
    }

    public void refreshVar() {
        this.refreshVar(this.scriptEngine);
    }

    public void refreshVar(Interpreter interpreter) {
        try {
            Iterator var3 = this.varWordList.iterator();

            while(var3.hasNext()) {
                VarWord varWord = (VarWord)var3.next();
                String strTmp = varWord.getEnName() + " = " + varWord.getCurValue() + ";";
                interpreter.eval(strTmp);
            }
        } catch (Exception var5) {
        }

    }

    public boolean setVarWord(String varName, float varVal, ScriptEngine scriptEngine) {
        boolean bResult = false;

        for(int i = 0; i < this.varWordList.size(); ++i) {
            if (((VarWord)this.varWordList.get(i)).getCnName().equals(varName)) {
                ((VarWord)this.varWordList.get(i)).setCurValue((double)varVal);
                scriptEngine.put(((VarWord)this.varWordList.get(i)).getEnName(), ((VarWord)this.varWordList.get(i)).getCurValue());
                bResult = true;
            }
        }

        return bResult;
    }

    public boolean doCmd(int iType, String strTarget) {
        boolean bResult = false;

        for(int i = 0; i < 5; ++i) {
            switch(this.mPlcType) {
                case 1:
                    bResult = this.plcModbus.doCmd(iType, strTarget);
                    break;
                case 2:
                    bResult = this.plcHostlink.doCmd(iType, strTarget);
                    break;
                case 3:
                    bResult = this.plcModbusTcp.doCmd(iType, strTarget);
                    break;
                case 4:
                    bResult = this.plcShangYang.doCmd(iType, strTarget);
                    break;
                case 5:
                    bResult = this.plcGrean.doCmd(iType, strTarget);
                    break;
                case 6:
                    bResult = this.plcJuGuang.doCmd(iType, strTarget);
                    break;
                case 7:
                    bResult = this.plcSchneider.doCmd(iType, strTarget);
                    break;
                case 8:
                    bResult = this.plcModbusTcpTest.doCmd(iType, strTarget);
                    break;
                case 9:
                    bResult = this.plcModbusNsbd.doCmd(iType, strTarget);
                    break;
                case 10:
                    bResult = this.plcModbus1200.doCmd(iType, strTarget);
                    break;
                case 11:
                    bResult = this.plcFanYiModbus.doCmd(iType, strTarget);
                    break;
                case 12:
                    bResult = this.plcFanYiTcp.doCmd(iType, strTarget);
                    break;
                case 13:
                    bResult = this.plcWeiQiModbus.doCmd(iType, strTarget);
                    break;
                case 14:
                    bResult = this.plcHostlink2.doCmd(iType, strTarget);
                    break;
                case 15:
                    bResult = this.plcHostlink3.doCmd(iType, strTarget);
                    break;
                default:
                    return bResult;
            }

            if (bResult) {
                break;
            }
        }

        return bResult;
    }

    public boolean doCmd(int iType, int iAddress) {
        if (this.onScript) {
            return false;
        } else {
            boolean bResult = false;

            for(int i = 0; i < 10; ++i) {
                switch(this.mPlcType) {
                    case 1:
                        bResult = this.plcModbus.doCmd(iType, iAddress);
                        break;
                    case 2:
                        bResult = this.plcHostlink.doCmd(iType, iAddress);
                        break;
                    case 3:
                        bResult = this.plcModbusTcp.doCmd(iType, iAddress);
                        break;
                    case 4:
                        bResult = this.plcShangYang.doCmd(iType, iAddress);
                        break;
                    case 5:
                        bResult = this.plcGrean.doCmd(iType, iAddress);
                        break;
                    case 6:
                        bResult = this.plcJuGuang.doCmd(iType, iAddress);
                        break;
                    case 7:
                        bResult = this.plcSchneider.doCmd(iType, iAddress);
                        break;
                    case 8:
                        bResult = this.plcModbusTcpTest.doCmd(iType, iAddress);
                        break;
                    case 9:
                        bResult = this.plcModbusNsbd.doCmd(iType, iAddress);
                        break;
                    case 10:
                        bResult = this.plcModbus1200.doCmd(iType, iAddress);
                        break;
                    case 11:
                        bResult = this.plcFanYiModbus.doCmd(iType, iAddress);
                        break;
                    case 12:
                        bResult = this.plcFanYiTcp.doCmd(iType, iAddress);
                        break;
                    case 13:
                        bResult = this.plcWeiQiModbus.doCmd(iType, iAddress);
                        break;
                    case 14:
                        bResult = this.plcHostlink2.doCmd(iType, iAddress);
                        break;
                    case 15:
                        bResult = this.plcHostlink3.doCmd(iType, iAddress);
                        break;
                    default:
                        return bResult;
                }

                if (bResult) {
                    break;
                }
            }

            return bResult;
        }
    }

    public boolean doReset() {
        boolean bResult = false;

        for(int i = 0; i < 10; ++i) {
            switch(this.mPlcType) {
                case 1:
                    bResult = this.plcModbus.doReset();
                    break;
                case 2:
                    bResult = this.plcHostlink.doReset();
                    break;
                case 3:
                    bResult = this.plcModbusTcp.doReset();
                    break;
                case 4:
                    bResult = this.plcShangYang.doReset();
                    break;
                case 5:
                    bResult = this.plcGrean.doReset();
                    break;
                case 6:
                    bResult = this.plcJuGuang.doReset();
                    break;
                case 7:
                    bResult = this.plcSchneider.doReset();
                    break;
                case 8:
                    bResult = this.plcModbusTcpTest.doReset();
                    break;
                case 9:
                    bResult = this.plcModbusNsbd.doReset();
                    break;
                case 10:
                    bResult = this.plcModbus1200.doReset();
                    break;
                case 11:
                    bResult = this.plcFanYiModbus.doReset();
                    break;
                case 12:
                    bResult = this.plcFanYiTcp.doReset();
                    break;
                case 13:
                    bResult = this.plcWeiQiModbus.doReset();
                    break;
                case 14:
                    bResult = this.plcHostlink2.doReset();
                    break;
                case 15:
                    bResult = this.plcHostlink3.doReset();
                    break;
                default:
                    return bResult;
            }

            if (bResult) {
                break;
            }
        }

        return bResult;
    }

    public boolean doWrite() {
        boolean bResult = false;
        if (this.plc_write) {
            List<CfgModbusVal> cfgModbusValList = this.monitorService.getCfgMapper().getCfgModbusValList();
            String var3 = this.plc_write_type.toUpperCase();
            byte var4 = -1;
            switch(var3.hashCode()) {
                case -76571285:
                    if (var3.equals("REALTIME")) {
                        var4 = 0;
                    }
                    break;
                case 1644916852:
                    if (var3.equals("HISTORY")) {
                        var4 = 1;
                    }
            }

            Iterator var7;
            label88:
            switch(var4) {
                case 0:
                    Iterator var14 = cfgModbusValList.iterator();

                    while(true) {
                        while(true) {
                            if (!var14.hasNext()) {
                                break label88;
                            }

                            CfgModbusVal cfgModbusVal = (CfgModbusVal)var14.next();
                            var7 = this.monitorService.getCfgFactorList().iterator();

                            while(var7.hasNext()) {
                                CfgFactor cfgFactor = (CfgFactor)var7.next();
                                if (cfgFactor.getName().equals(cfgModbusVal.getName())) {
                                    if (cfgFactor.getDataMea() != null) {
                                        cfgModbusVal.setValue(cfgFactor.getDataMea().floatValue());
                                    }
                                    break;
                                }
                            }
                        }
                    }
                case 1:
                    RecDataTime recDataTime = this.recMapper.getLastRecTimeHour();
                    if (recDataTime != null) {
                        List<RecDataFactor> recDataList = this.recMapper.getRecDataHourListByTime(recDataTime);
                        var7 = cfgModbusValList.iterator();

                        label86:
                        while(true) {
                            while(true) {
                                label76:
                                while(true) {
                                    if (!var7.hasNext()) {
                                        break label86;
                                    }

                                    CfgModbusVal cfgModbusVal = (CfgModbusVal)var7.next();
                                    Iterator var9 = this.monitorService.getCfgFactorList().iterator();

                                    while(var9.hasNext()) {
                                        CfgFactor cfgFactor = (CfgFactor)var9.next();
                                        if (cfgFactor.getName().equals(cfgModbusVal.getName())) {
                                            Iterator var11 = recDataList.iterator();

                                            while(var11.hasNext()) {
                                                RecDataFactor recDataFactor = (RecDataFactor)var11.next();
                                                if (recDataFactor.getFactorID() == cfgFactor.getId()) {
                                                    if (recDataFactor.getDataValue() != null) {
                                                        cfgModbusVal.setValue(recDataFactor.getDataValue().floatValue());
                                                    }
                                                    continue label76;
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
            }

            for(int i = 0; i < 3; ++i) {
                switch(this.mPlcType) {
                    case 8:
                        bResult = this.plcModbusTcpTest.doWrite(cfgModbusValList);
                        if (bResult) {
                            return bResult;
                        }
                        break;
                    default:
                        return bResult;
                }
            }
        }

        return bResult;
    }

    public boolean executeCmd(String exeCmd, Interpreter interpreter) {
        boolean bResult = false;
        this.jumpScript = false;
        this.logger.info(exeCmd);

        try {
            String[] strArray = exeCmd.split(" ");
            String var5 = strArray[0];
            byte var6 = -1;
            switch(var5.hashCode()) {
                case 684762:
                    if (var5.equals("关闭")) {
                        var6 = 3;
                    }
                    break;
                case 727008:
                    if (var5.equals("复位")) {
                        var6 = 0;
                    }
                    break;
                case 804621:
                    if (var5.equals("打开")) {
                        var6 = 2;
                    }
                    break;
                case 815813:
                    if (var5.equals("执行")) {
                        var6 = 1;
                    }
                    break;
                case 842428:
                    if (var5.equals("显示")) {
                        var6 = 5;
                    }
                    break;
                case 934923:
                    if (var5.equals("状态")) {
                        var6 = 4;
                    }
                    break;
                case 1002844:
                    if (var5.equals("等待")) {
                        var6 = 8;
                    }
                    break;
                case 1141125:
                    if (var5.equals("调用")) {
                        var6 = 11;
                    }
                    break;
                case 1163225:
                    if (var5.equals("跳转")) {
                        var6 = 10;
                    }
                    break;
                case 1163770:
                    if (var5.equals("退出")) {
                        var6 = 9;
                    }
                    break;
                case 35182227:
                    if (var5.equals("计时器")) {
                        var6 = 7;
                    }
                    break;
                case 2062348045:
                    if (var5.equals("五参数状态")) {
                        var6 = 6;
                    }
            }

            switch(var6) {
                case 0:
                    bResult = this.doReset();
                    break;
                case 1:
                    this.monitorService.saveRecLogSys("系统日志", "切换至维护模式");
                    if (strArray[1].equals("维护模式")) {
                        ScriptWord.getInstance().setSysVar("RunMode", 0.0D);
                        List<VarWord> varWordList = this.plcMapper.getPLCVarWordList();
                        Iterator var16 = varWordList.iterator();

                        while(var16.hasNext()) {
                            VarWord varWord = (VarWord)var16.next();
                            if (varWord.getEnName().equals("RunMode")) {
                                varWord.setCurValue(0.0D);
                                this.plcMapper.updatePLCVarWord(varWord);
                                break;
                            }
                        }
                    }

                    bResult = true;
                    break;
                case 2:
                    bResult = this.doCmd(1, strArray[1]);
                    break;
                case 3:
                    bResult = this.doCmd(0, strArray[1]);
                    break;
                case 4:
                    bResult = true;
                    if (this.stateListener != null) {
                        if (interpreter == this.scriptEngine) {
                            this.stateListener.OnStateEvent(strArray[1]);
                        }

                        if (interpreter == this.scriptEngineFive) {
                            this.stateListener.OnStateEvent(strArray[1] + " FIVE");
                        }
                    }
                    break;
                case 5:
                    bResult = true;
                    if (this.stateListener != null) {
                        this.stateListener.OnStateEvent(strArray[1] + "当前值:" + interpreter.getScriptVal());
                    }
                    break;
                case 6:
                    bResult = true;
                    break;
                case 7:
                    if (strArray.length == 2) {
                        if (interpreter == this.scriptEngineFive) {
                            if (!this.isOnScript()) {
                                this.rtdCount.setCountTitle(strArray[1]);
                            }
                        } else {
                            this.rtdCount.setCountTitle(strArray[1]);
                        }

                        bResult = true;
                    }
                    break;
                case 8:
                    try {
                        float fWait = Float.parseFloat(strArray[1]);
                        int iCount = (int)fWait;
                        long tCurrent = System.currentTimeMillis();
                        long tTarget = tCurrent + (long)(1000 * iCount);

                        while(tCurrent < tTarget) {
                            Thread.sleep(1000L);
                            tCurrent = System.currentTimeMillis();
                            iCount = (int)((tTarget - tCurrent) / 1000L);
                            if (this.countListener != null) {
                                if (interpreter == this.scriptEngine) {
                                    this.rtdCount.setCountValue(iCount);
                                    this.countListener.OnCountEvent(this.rtdCount);
                                }

                                if (interpreter == this.scriptEngineFive && !this.isOnScript()) {
                                    this.rtdCount.setCountValue(iCount);
                                    this.countListener.OnCountEvent(this.rtdCount);
                                }
                            }

                            if (iCount % 2 == 0) {
                                this.refreshVar(interpreter);
                            }

                            if (!this.isOnScript() && !this.isOnScriptFive()) {
                                if (this.countListener != null) {
                                    this.rtdCount.setCountTitle("系统待机");
                                    this.rtdCount.setCountValue(0);
                                    this.countListener.OnCountEvent(this.rtdCount);
                                }
                                break;
                            }

                            if (this.jumpScript) {
                                this.jumpScript = false;
                                if (this.countListener != null) {
                                    interpreter.setScriptSleep(false);
                                    if (interpreter == this.scriptEngine) {
                                        this.rtdCount.setCountValue(0);
                                        this.countListener.OnCountEvent(this.rtdCount);
                                    }

                                    if (interpreter == this.scriptEngineFive && !this.isOnScript()) {
                                        this.rtdCount.setCountValue(0);
                                        this.countListener.OnCountEvent(this.rtdCount);
                                    }
                                }

                                bResult = true;
                                break;
                            }
                        }
                    } catch (Exception var13) {
                        var13.printStackTrace();
                    }

                    bResult = true;
                    break;
                case 9:
                    bResult = true;
                    break;
                case 10:
                case 11:
                    bResult = true;
            }
        } catch (Exception var14) {
            this.logger.info(var14.getMessage());
        }

        return bResult;
    }

    public void saveRecLogSys(String strLog) {
        this.monitorService.saveRecLogSys("系统日志", strLog);
        this.logger.info(strLog);
    }

    public boolean[] getQStatus() {
        return this.QStatus;
    }

    public boolean getQStatus(int iAddress) {
        return iAddress < this.QStatus.length ? this.QStatus[iAddress] : false;
    }

    public boolean[] getIStatus() {
        return this.IStatus;
    }

    public boolean getIStatus(int iAddress) {
        return iAddress < this.IStatus.length ? this.IStatus[iAddress] : false;
    }

    public boolean getIStatus(String iName) {
        int iAddress = 0;

        for(int i = 0; i < this.defWordList.size(); ++i) {
            if (((DefWord)this.defWordList.get(i)).getType() == 2) {
                if (((DefWord)this.defWordList.get(i)).getPname().equals(iName)) {
                    String strTmp = this.IStatus[iAddress] ? "true" : "false";
                    System.out.println("SMS: " + strTmp);
                    return this.IStatus[iAddress];
                }

                ++iAddress;
            }
        }

        return false;
    }

    public String getIName(String iName) {
        for(int i = 0; i < this.defWordList.size(); ++i) {
            if (((DefWord)this.defWordList.get(i)).getType() == 2 && ((DefWord)this.defWordList.get(i)).getPname().equals(iName)) {
                return ((DefWord)this.defWordList.get(i)).getName();
            }
        }

        return null;
    }

    public float[] getAIValues() {
        return this.AIValues;
    }

    public float getAIValues(int iAddress) {
        return iAddress < this.AIValues.length ? this.AIValues[iAddress] : 0.0F;
    }

    public boolean isOnScript() {
        return this.onScript;
    }

    public void setOnScript(boolean onScript) {
        this.onScript = onScript;
        if (!onScript) {
            this.monitorService.setUserStart(false);
            Utility.setJcgy(false);
        }

    }

    public boolean isOnScriptFive() {
        return this.onScriptFive;
    }

    public void setOnScriptFive(boolean onScriptFive) {
        this.onScriptFive = onScriptFive;
    }

    public List<KeyWord> getKeyWordList() {
        return this.keyWordList;
    }

    public List<VarWord> getVarWordList() {
        return this.varWordList;
    }

    public List<DefWord> getDefWordList() {
        return this.defWordList;
    }

    public void saveScriptLog(String logInfo) {
        if (this.plc_log) {
            this.logger.info(logInfo);
        }

    }

    private class ScriptThreadFive extends Thread {
        String scriptFileName;

        public ScriptThreadFive(String fileName) {
            this.scriptFileName = fileName;
        }

        public void run() {
            int listID = 0;
            boolean bUse = false;
            String strUseFile = null;
            super.run();
            ScriptService.this.setOnScriptFive(true);

            try {
                Thread.sleep(2000L);
            } catch (Exception var8) {
            }

            while(!this.isInterrupted()) {
                try {
                    List<String> cmdList = ScriptService.this.scriptEngineFive.getPrintList();
                    if (cmdList.size() > listID) {
                        String strCmd = (String)cmdList.get(listID);
                        ++listID;
                        String[] strArray = strCmd.split(" ");
                        if (strArray[0].equals("启用")) {
                            bUse = true;
                            ScriptService.this.scriptEngineFive.setScriptExit(true);
                            strUseFile = strArray[1];
                            break;
                        }

                        if (!ScriptService.this.executeCmd(strCmd, ScriptService.this.scriptEngineFive)) {
                            ScriptService.this.saveRecLogSys("五参数脚本执行错误 " + strCmd);
                            ScriptService.this.scriptEngineFive.setScriptExit(true);
                            ScriptService.this.doReset();
                            ScriptService.this.monitorService.exitApplication();
                            break;
                        }

                        ScriptService.this.refreshVar(ScriptService.this.scriptEngineFive);
                        if (strArray[0].equals("退出")) {
                            ScriptService.this.monitorService.setSysStatus("系统待机");
                            ScriptService.this.monitorService.doSaveZeroDataFive();
                            break;
                        }
                    }

                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException var7) {
                    }
                } catch (Exception var9) {
                }
            }

            if (bUse && strUseFile != null) {
                ScriptService.this.startScriptFive(strUseFile);
            } else {
                ScriptService.this.setOnScriptFive(false);
                ScriptService.this.saveRecLogSys("停止五参数脚本 " + this.scriptFileName + "执行");
            }

        }
    }

    private class ScriptThread extends Thread {
        String scriptFileName;
        boolean bRun = true;

        public ScriptThread(String fileName) {
            this.scriptFileName = fileName;
        }

        public void finish() {
            this.bRun = false;
        }

        public void run() {
            int listID = 0;
            boolean bUse = false;
            String strUseFile = null;
            long doCmdTime = System.currentTimeMillis();
            super.run();
            ScriptService.this.setOnScript(true);

            try {
                Thread.sleep(2000L);
            } catch (Exception var9) {
            }

            ScriptService.this.monitorService.setRange();

            while(!this.isInterrupted() && this.bRun) {
                try {
                    List<String> cmdList = ScriptService.this.scriptEngine.getPrintList();
                    if (cmdList.size() > listID) {
                        String strCmd = (String)cmdList.get(listID);
                        ++listID;
                        String[] strArray = strCmd.split(" ");
                        if (strArray[0].equals("启用")) {
                            bUse = true;
                            ScriptService.this.scriptEngine.setScriptExit(true);
                            strUseFile = strArray[1];
                            break;
                        }

                        if (!ScriptService.this.executeCmd(strCmd, ScriptService.this.scriptEngine)) {
                            ScriptService.this.saveRecLogSys("脚本执行错误 " + strCmd);
                            ScriptService.this.monitorService.setSysStatus("系统待机");
                            ScriptService.this.scriptEngine.setScriptExit(true);
                            ScriptService.this.doReset();
                            ScriptService.this.monitorService.exitApplication();
                            break;
                        }

                        ScriptService.this.refreshVar(ScriptService.this.scriptEngine);
                        doCmdTime = System.currentTimeMillis();
                        if (strArray[0].equals("退出")) {
                            ScriptService.this.monitorService.setSysStatus("系统待机");
                            ScriptService.this.monitorService.doSaveZeroData();
                            break;
                        }
                    }

                    if (System.currentTimeMillis() - doCmdTime > 7200000L) {
                        break;
                    }

                    Thread.sleep(100L);
                } catch (Exception var10) {
                    ScriptService.this.logger.info(var10.getLocalizedMessage());
                }
            }

            if (bUse && strUseFile != null) {
                ScriptService.this.startScript(strUseFile);
            } else {
                ScriptService.this.setOnScript(false);
                ScriptService.this.saveRecLogSys("停止脚本 " + this.scriptFileName + "执行");
            }

        }
    }
}
