//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.toc;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Random;

public class DevTocShimadzu4200 extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();
    private int[] warnBit1 = new int[]{0, 4, 5, 6, 7, 8, 9, 12, 13, 14, 15};
    private String[] warnSet1 = new String[]{"内部存储卡故障", "电炉过热", "电炉温调故障", "电炉风扇停止", "电炉电源故障", "冷却器温调故障", "冷却装置风扇停止", "注射器故障", "8通阀故障", "标准溶液切换器故障", "滑块故障"};
    private int[] warnBit2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 12};
    private String[] warnSet2 = new String[]{"TOC检测器温调故障", "TOC检测器马达故障", "TOC检测器存储故障", "TOC检测器连接故障", "TN检测器温调故障", "TN检测器存储故障", "TN检测器风扇停止", "TN检测器连接故障", "臭氧压力传感器故障", "端子板故障"};
    private int[] warnBit3 = new int[]{0, 1, 2, 3, 4};
    private String[] warnSet3 = new String[]{"载气流路故障", "喷射气体流路故障", "压力传感器1故障", "压力传感器2故障", "压力传感器3故障"};
    private int[] warnBit4 = new int[]{0, 1};
    private String[] warnSet4 = new String[]{"零点检测失败", "初次校正故障"};
    private int[] warnBit5 = new int[]{4, 5, 6, 7, 8, 9, 10, 11, 0, 1};
    private String[] warnSet5 = new String[]{"标准溶液1中断", "标准溶液2中断", "标准溶液3中断", "标准溶液4中断", "标准溶液5中断", "标准溶液6中断", "标准溶液7中断", "标准溶液8中断", "稀释水中断", "酸中断"};
    private int[] warnBit6 = new int[]{4, 5, 6, 0, 1, 2, 3, 7, 8};
    private String[] warnSet6 = new String[]{"载气压力降低", "载气中断", "催化剂再生终止", "存储设备异常", "打印机故障", "打印机用纸用完", "8通阀重试", "室温异常", "电炉开关OFF"};
    private int[] warnBit7 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    private String[] warnSet7 = new String[]{"更换CO2吸收剂1", "更换CO2吸收剂2", "更换CO2吸收剂3", "更换CO2吸收剂4", "更换NOx吸收剂", "更换载气", "更换柱塞头", "更换催化剂", "更换8通阀转子", "更换标准溶液切换器转子", "更换臭氧发生器", "更换前处理设备空气泵", "更换搅拌马达", "更换电池"};
    private int[] warnBit8 = new int[]{0, 1};
    private String[] warnSet8 = new String[]{"校正失败", "未更新校准曲线"};
    private int[] warnBit9 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    private String[] warnSet9 = new String[]{"流路1TOC上限", "流路1TOC下限", "流路1TOC上上限", "流路1TOC下下限", "流路1TOC换算值上限", "流路1TOC换算值下限", "流路1TOC换算值上上限", "流路1TOC换算值下下限", "流路1TN上限", "流路1TN下限", "流路1TN上上限", "流路1TN下下限", "流路1TN换算值上限", "流路1TN换算值下限", "流路1TN换算值上上限", "流路1TN换算值下下限"};

    public DevTocShimadzu4200() {
    }

    public void doQuery() {
        if (!this.onCmd) {
            int regLength = 2;
            this.mAddress = this.mCfgDev.getAddress().intValue();
            byte[] bQuery = new byte[]{(byte)this.mAddress, 4, 0, 10, 0, 2, 0, 0};
            int crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
            byte[] tmp_bytes = Utility.getByteArray(crc_result);
            bQuery[bQuery.length - 2] = tmp_bytes[2];
            bQuery[bQuery.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
            if (bRecv != null) {
                if (bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 4) {
                    float testResult = Utility.getFloatBigEndian(bRecv, 3);
                    this.setParamTestVal((double)testResult, "N", (Timestamp)null);
                    this.setStatus(true);
                    Utility.logInfo("ShimadzuToc4200 Dev: 查询成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("ShimadzuToc4200 Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else if (MonitorService.autoData) {
                double dTmp = this.rand.nextDouble() * 10.0D;
                this.setParamTestVal(dTmp, "N", (Timestamp)null);
                this.setParam("DevLog", (int)dTmp);
                this.setParam("DevAlarm", 200);

                for(int i = 0; i < 5; ++i) {
                    this.addWarn(i, "测试告警" + i);
                }

                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("ShimadzuToc4200 Dev: 查询失败，通道" + this.mChannel + "，无返回");
            }

            regLength = 22;
            bQuery = new byte[]{(byte)this.mAddress, 4, 7, -48, 0, 22, 0, 0};
            crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
            tmp_bytes = Utility.getByteArray(crc_result);
            bQuery[bQuery.length - 2] = tmp_bytes[2];
            bQuery[bQuery.length - 1] = tmp_bytes[3];
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
            if (bRecv != null && bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 4) {
                int i;
                for(i = 0; i < this.warnSet1.length; ++i) {
                    if (Utility.getBit(bRecv, 4 - this.warnBit1[i] / 8, this.warnBit1[i] % 8)) {
                        this.addWarn(i, this.warnSet1[i]);
                    } else {
                        this.warnMap.remove(i);
                    }
                }

                for(i = 0; i < this.warnSet2.length; ++i) {
                    if (Utility.getBit(bRecv, 6 - this.warnBit2[i] / 8, this.warnBit2[i] % 8)) {
                        this.addWarn(16 + i, this.warnSet2[i]);
                    } else {
                        this.warnMap.remove(16 + i);
                    }
                }

                for(i = 0; i < this.warnSet3.length; ++i) {
                    if (Utility.getBit(bRecv, 8 - this.warnBit3[i] / 8, this.warnBit3[i] % 8)) {
                        this.addWarn(32 + i, this.warnSet3[i]);
                    } else {
                        this.warnMap.remove(32 + i);
                    }
                }

                for(i = 0; i < this.warnSet4.length; ++i) {
                    if (Utility.getBit(bRecv, 10 - this.warnBit4[i] / 8, this.warnBit4[i] % 8)) {
                        this.addWarn(48 + i, this.warnSet4[i]);
                    } else {
                        this.warnMap.remove(48 + i);
                    }
                }

                for(i = 0; i < this.warnSet5.length; ++i) {
                    if (Utility.getBit(bRecv, 24 - this.warnBit5[i] / 8, this.warnBit5[i] % 8)) {
                        this.addWarn(64 + i, this.warnSet5[i]);
                    } else {
                        this.warnMap.remove(64 + i);
                    }
                }

                for(i = 0; i < this.warnSet6.length; ++i) {
                    if (Utility.getBit(bRecv, 26 - this.warnBit6[i] / 8, this.warnBit6[i] % 8)) {
                        this.addWarn(80 + i, this.warnSet6[i]);
                    } else {
                        this.warnMap.remove(80 + i);
                    }
                }

                for(i = 0; i < this.warnSet7.length; ++i) {
                    if (Utility.getBit(bRecv, 28 - this.warnBit7[i] / 8, this.warnBit7[i] % 8)) {
                        this.addWarn(96 + i, this.warnSet7[i]);
                    } else {
                        this.warnMap.remove(96 + i);
                    }
                }

                for(i = 0; i < this.warnSet8.length; ++i) {
                    if (Utility.getBit(bRecv, 44 - this.warnBit8[i] / 8, this.warnBit8[i] % 8)) {
                        this.addWarn(112 + i, this.warnSet8[i]);
                    } else {
                        this.warnMap.remove(112 + i);
                    }
                }

                for(i = 0; i < this.warnSet9.length; ++i) {
                    if (Utility.getBit(bRecv, 46 - this.warnBit9[i] / 8, this.warnBit9[i] % 8)) {
                        this.addWarn(128 + i, this.warnSet9[i]);
                    } else {
                        this.warnMap.remove(128 + i);
                    }
                }

                if (this.warnMap.size() > 0) {
                    this.setParam("DevAlarm", 200);
                }
            }

        }
    }

    public void saveResult() {
    }
    @SuppressWarnings("FallThrough")
    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        this.initDevState(cmdType);
        if (MonitorService.autoData) {
            return true;
        } else {
            this.mCmdType = cmdType;
            switch(cmdType) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                default:
                    byte[] bCmd = new byte[]{(byte)this.mAddress, 16, 0, 0, 0, 2, 4, 0, 1, 0, 1, 0, 0};
                    this.onCmd = true;
                    int crc_result = Utility.calcCrc16(bCmd, 0, bCmd.length - 2);
                    byte[] tmp_bytes = Utility.getByteArray(crc_result);
                    bCmd[bCmd.length - 2] = tmp_bytes[2];
                    bCmd[bCmd.length - 1] = tmp_bytes[3];
                    this.mInterComm.clearRecv(2 * this.mChannel + 1);
                    this.mInterComm.Send(bCmd);
                    byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel + 1, false, 2000);
                    int iResult = this.getCmdResult("ShimadzuToc4200", bCmd, bRecv);
                    if (cmdQN != null && this.devExeListener != null) {
                        this.devExeListener.OnExeEvent(cmdQN, cmdType, iResult);
                    }

                    this.onCmd = false;
                    if (iResult == 1) {
                        return true;
                    }

                    return false;
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                    return false;
            }
        }
    }

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                DevTocShimadzu4200.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
