//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.script;

import com.grean.station.dao.PLCMapper;
import com.grean.station.domain.DO.plc.DefWord;
import com.grean.station.domain.DO.plc.KeyWord;
import com.grean.station.domain.DO.plc.StatusWord;
import com.grean.station.domain.DO.plc.VarWord;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScriptWord {
    private List<KeyWord> keyWordList = new ArrayList();
    private List<VarWord> varWordList = new ArrayList();
    private List<DefWord> defWordList = new ArrayList();
    private List<StatusWord> statusWordList = new ArrayList();
    private int valveCount = 0;
    private static ScriptWord scriptWord = new ScriptWord();
    private PLCMapper plcMapper = null;

    private ScriptWord() {
    }

    public void init(PLCMapper mapper) {
        this.plcMapper = mapper;
        this.defWordList = this.plcMapper.getPLCDefWordList();
        this.keyWordList = this.plcMapper.getPLCKeyWordList();
        this.varWordList = this.plcMapper.getPLCVarWordList();
        this.statusWordList = this.plcMapper.getPLCStatusWordList();

        for(int i = 0; i < this.defWordList.size(); ++i) {
            if (((DefWord)this.defWordList.get(i)).isIsvalve()) {
                ++this.valveCount;
            }
        }

    }

    public void updateVarWord(String varName) {
        if (this.plcMapper != null) {
            List<VarWord> varWords = this.plcMapper.getPLCVarWordList();

            for(int i = 0; i < varWords.size(); ++i) {
                if (((VarWord)varWords.get(i)).getEnName().equals(varName)) {
                    for(int j = 0; j < this.varWordList.size(); ++j) {
                        if (((VarWord)this.varWordList.get(j)).getEnName().equals(varName) && ((VarWord)varWords.get(i)).getCurValue() != ((VarWord)this.varWordList.get(j)).getCurValue()) {
                            this.plcMapper.updatePLCVarWord((VarWord)this.varWordList.get(j));
                            return;
                        }
                    }

                    return;
                }
            }
        }

    }

    public static ScriptWord getInstance() {
        return scriptWord;
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

    public List<StatusWord> getStatusWordList() {
        return this.statusWordList;
    }

    public int getValveCount() {
        return this.valveCount;
    }

    public double getSysVar(String varName) {
        for(int i = 0; i < this.varWordList.size(); ++i) {
            if (((VarWord)this.varWordList.get(i)).getEnName().equals(varName)) {
                return ((VarWord)this.varWordList.get(i)).getCurValue();
            }
        }

        return 0.0D;
    }

    public void setSysVar(String varName, double varVal) {
        for(int i = 0; i < this.varWordList.size(); ++i) {
            if (((VarWord)this.varWordList.get(i)).getEnName().equals(varName)) {
                ((VarWord)this.varWordList.get(i)).setCurValue(varVal);
            }
        }

        this.updateVarWord(varName);
    }

    public StatusWord getStatusWord(String statusName) {
        Iterator var2 = this.statusWordList.iterator();

        StatusWord statusWord;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            statusWord = (StatusWord)var2.next();
        } while(!statusWord.getName().equals(statusName));

        return statusWord;
    }

    public String getScriptName(int scriptCmd) {
        String scriptName = null;
        int iRunMode = (int)this.getSysVar("RunMode");
        int iPumpState = (int)this.getSysVar("PumpState");
        int iPumpWork = (int)this.getSysVar("PumpWork");
        if (iPumpState == 3) {
            iPumpWork = iPumpWork % 2 + 1;
            getInstance().setSysVar("PumpWork", (double)iPumpWork);
        }

        switch(iRunMode) {
            case 1:
                switch(scriptCmd) {
                    case 1:
                        switch(iPumpState) {
                            case 1:
                                scriptName = "CGDB1.txt";
                                return scriptName;
                            case 2:
                                scriptName = "CGDB2.txt";
                                return scriptName;
                            default:
                                if (iPumpWork == 1) {
                                    scriptName = "CGSB1.txt";
                                }

                                if (iPumpWork == 2) {
                                    scriptName = "CGSB2.txt";
                                }

                                return scriptName;
                        }
                    case 101:
                        scriptName = "CGXTCZ.txt";
                        return scriptName;
                    case 102:
                        scriptName = "CGXTQX.txt";
                        return scriptName;
                    case 103:
                        scriptName = "CGNGLQX.txt";
                        return scriptName;
                    case 104:
                        scriptName = "CGWGLQX.txt";
                        return scriptName;
                    case 105:
                        scriptName = "CGGLQQX.txt";
                        return scriptName;
                    case 106:
                        scriptName = "CGCSCQX.txt";
                        return scriptName;
                    case 107:
                        scriptName = "CGWCSCQX.txt";
                        return scriptName;
                    case 108:
                        scriptName = "CGDDFW.txt";
                        return scriptName;
                    default:
                        return scriptName;
                }
            case 2:
                switch(scriptCmd) {
                    case 1:
                        switch(iPumpState) {
                            case 1:
                                scriptName = "YJDB1.txt";
                                return scriptName;
                            case 2:
                                scriptName = "YJDB2.txt";
                                return scriptName;
                            default:
                                if (iPumpWork == 1) {
                                    scriptName = "YJSB1.txt";
                                }

                                if (iPumpWork == 2) {
                                    scriptName = "YJSB2.txt";
                                }

                                return scriptName;
                        }
                    case 108:
                        scriptName = "YJDDFW.txt";
                        return scriptName;
                    default:
                        return scriptName;
                }
            case 3:
                switch(scriptCmd) {
                    case 1:
                        switch(iPumpState) {
                            case 1:
                                scriptName = "ZKDB1.txt";
                                return scriptName;
                            case 2:
                                scriptName = "ZKDB2.txt";
                                return scriptName;
                            default:
                                if (iPumpWork == 1) {
                                    scriptName = "ZKSB1.txt";
                                }

                                if (iPumpWork == 2) {
                                    scriptName = "ZKSB2.txt";
                                }

                                return scriptName;
                        }
                    case 2:
                        scriptName = "ZKBYHC.txt";
                        return scriptName;
                    case 3:
                        scriptName = "ZKLDHC.txt";
                        return scriptName;
                    case 4:
                        scriptName = "ZKLCHC.txt";
                        return scriptName;
                    case 5:
                        scriptName = "ZKKBCS.txt";
                        return scriptName;
                    case 6:
                        switch(iPumpState) {
                            case 1:
                                scriptName = "ZKDB1PX.txt";
                                return scriptName;
                            case 2:
                                scriptName = "ZKDB2PX.txt";
                                return scriptName;
                            default:
                                if (iPumpWork == 1) {
                                    scriptName = "ZKSB1PX.txt";
                                }

                                if (iPumpWork == 2) {
                                    scriptName = "ZKSB2PX.txt";
                                }

                                return scriptName;
                        }
                    case 7:
                        switch(iPumpState) {
                            case 1:
                                scriptName = "ZKDB1JB.txt";
                                return scriptName;
                            case 2:
                                scriptName = "ZKDB2JB.txt";
                                return scriptName;
                            default:
                                if (iPumpWork == 1) {
                                    scriptName = "ZKSB1JB.txt";
                                }

                                if (iPumpWork == 2) {
                                    scriptName = "ZKSB2JB.txt";
                                }

                                return scriptName;
                        }
                    case 50:
                        scriptName = "ZKJCGY.txt";
                        return scriptName;
                    case 60:
                        scriptName = "PCST.txt";
                        return scriptName;
                    case 101:
                        scriptName = "ZKXTCZ.txt";
                        return scriptName;
                    case 102:
                        scriptName = "ZKXTQX.txt";
                        return scriptName;
                    case 103:
                        scriptName = "ZKNGLQX.txt";
                        return scriptName;
                    case 104:
                        scriptName = "ZKWGLQX.txt";
                        return scriptName;
                    case 105:
                        scriptName = "ZKGLQQX.txt";
                        return scriptName;
                    case 106:
                        scriptName = "ZKCSCQX.txt";
                        return scriptName;
                    case 107:
                        scriptName = "ZKWCSCQX.txt";
                        return scriptName;
                    case 108:
                        scriptName = "ZKDDFW.txt";
                        return scriptName;
                    default:
                        return scriptName;
                }
            case 4:
                switch(scriptCmd) {
                    case 1:
                        switch(iPumpState) {
                            case 1:
                                scriptName = "LXDB1.txt";
                                return scriptName;
                            case 2:
                                scriptName = "LXDB2.txt";
                                return scriptName;
                            default:
                                if (iPumpWork == 1) {
                                    scriptName = "LXSB1.txt";
                                }

                                if (iPumpWork == 2) {
                                    scriptName = "LXSB2.txt";
                                }

                                return scriptName;
                        }
                    case 108:
                        scriptName = "LXDDFW.txt";
                }
        }

        return scriptName;
    }
    @SuppressWarnings("FallThrough")
    public String getScriptFiveName() {
        String scriptName = null;
        int iRunMode = (int)this.getSysVar("RunMode");
        int iPumpState = (int)this.getSysVar("PumpState");
        int iPumpWork = (int)this.getSysVar("PumpWork");
        if (iPumpState == 3) {
            iPumpWork = iPumpWork % 2 + 1;
            getInstance().setSysVar("PumpWork", (double)iPumpWork);
        }

        switch(iRunMode) {
            case 3:
                switch(iPumpState) {
                    case 1:
                        scriptName = "ZKWCDB1.txt";
                        break;
                    case 2:
                        scriptName = "ZKWCDB2.txt";
                        break;
                    default:
                        if (iPumpWork == 1) {
                            scriptName = "ZKWCSB1.txt";
                        }

                        if (iPumpWork == 2) {
                            scriptName = "ZKWCSB2.txt";
                        }
                }
            case 1:
            case 2:
            case 4:
            default:
                return scriptName;
        }
    }
}
