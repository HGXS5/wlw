//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.voc;

import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import org.apache.commons.io.FileUtils;

public class DevVocModernWater extends DevService {
    private Random rand = new Random();
    private String[] cnNameArray = new String[]{"二氯甲烷", "反式1,2—二氯乙烯", "顺式1,2—二氯乙烯", "三氯甲烷", "1，2—二氯乙烷", "苯", "1,2—二氯丙烷", "三氯乙烯", "甲苯", "四氯乙烯", "氯苯", "乙苯", "对间二甲苯", "苯乙烯", "邻二甲苯", "异丙苯", "1,4—二氯苯", "1,2—二氯苯"};
    private String[] enNameArray = new String[]{"Methylene chloride", "Trans-1,2-Dichloroethene", "cis-1,2-Dichloroethene", "Trichloromethane", "1,2-Dichloroethane", "Benzene", "1,2-dichloropropane", "Trichloroethylene", "Toluene", "Tetrachloroethylene ", "Chlorobenzene", "Ethylbenzene", "M-Xylene,P-Xylene", "Styrene", "O-Xylene", "Isopropylbenzene", "1,4-dichlorobenzene", "1,2-dichlorobenzene"};

    public DevVocModernWater() {
    }

    private String getLastModifyFileName(String pathName, String fileName) {
        File[] fs = Utility.getFileListByModifyTime(pathName);

        for(int i = 0; i < fs.length; ++i) {
            if (fs[i].getName().contains(fileName) && fs[i].getName().contains(".rqt")) {
                return fs[i].getName();
            }
        }

        return null;
    }

    public void doQuery() {
        try {
            String inputName = this.getLastModifyFileName(Utility.vocPathName, Utility.vocFileName);
            if (inputName == null) {
                this.setStatus(false);
                Utility.logInfo("ModernWaterVoc Dev: 查询失败");
            }

            this.setStatus(true);
            Utility.logInfo("ModernWaterVoc Dev: 查询成功");
            double dTmp = 0.0D;
            File inputFile = new File(Utility.vocPathName + inputName);
            List<String> inputList = FileUtils.readLines(inputFile, "UTF-8");

            for(int i = 0; i < inputList.size(); ++i) {
                String strTmp = (String)inputList.get(i);
                strTmp = strTmp.trim();
                String[] strArray = strTmp.split("\\s+ ");
                if (strArray.length > 7 && strArray[1].toUpperCase().equals(this.mCfgFactor.getCode())) {
                    if (!strArray[5].equals("---")) {
                        dTmp = Double.parseDouble(strArray[5]);
                    }
                    break;
                }
            }

            this.setParamTestVal(dTmp, "N", (Timestamp)null);
        } catch (Exception var10) {
        }

    }

    public void saveResult() {
    }

    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        this.initDevState(cmdType);
        this.mCmdType = cmdType;
        return true;
    }

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                DevVocModernWater.this.doDevCmd(cmdType, cmdParam, cmdQN);
            }
        });
    }

    public void doMeaCmd() {
        this.doDevCmdThread(1, (String)null, (String)null);
    }

    public void doStdCmd(String strQN, String strParam) {
        this.doDevCmdThread(2, strParam, strQN);
    }

    public void doZeroCmd(String strQN, String strParam) {
        this.doDevCmdThread(3, strParam, strQN);
    }

    public void doSpanCmd(String strQN, String strParam) {
        this.doDevCmdThread(4, strParam, strQN);
    }

    public void doBlnkCmd(String strQN, String strParam) {
        this.doDevCmdThread(5, strParam, strQN);
    }

    public void doParCmd(String strQN, String strParam) {
        this.doDevCmdThread(6, strParam, strQN);
    }

    public void doRcvrCmd(String strQN, String strParam) {
        this.doDevCmdThread(7, strParam, strQN);
    }

    public void doBlnkCal(String strQN, String strParam) {
    }

    public void doStdCal(String strQN, String strParam) {
    }

    public void doInitCmd() {
    }

    public void doStopCmd() {
        this.initDevState(0);
    }

    public void doRsetCmd() {
    }

    public void doSetTime(String strQN, String strParam) {
    }
}
