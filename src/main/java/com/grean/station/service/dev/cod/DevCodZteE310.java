//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.cod;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.TimeHelper;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Random;

public class DevCodZteE310 extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevCodZteE310() {
    }

    private byte[] getRecv(int waitTime, int recvChannel, boolean bQuery) {
        int bLen = 0;
        byte[] bRecv = new byte[64];
        long tStart = System.currentTimeMillis();

        while(true) {
            long tNow = System.currentTimeMillis();
            if (tNow - tStart > (long)waitTime) {
                break;
            }

            byte[] tmpRecv = this.mInterComm.Recv(recvChannel);
            if (tmpRecv == null) {
                try {
                    Thread.sleep(50L);
                } catch (InterruptedException var13) {
                }
            } else {
                System.arraycopy(tmpRecv, 0, bRecv, bLen, tmpRecv.length);
                bLen += tmpRecv.length;
                if (bLen > 5 && bRecv[0] == 90 && bRecv[bLen - 1] == 13) {
                    break;
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var12) {
                }
            }
        }

        if (bLen > 5 && bRecv[0] == 90 && bRecv[bLen - 1] == 13) {
            byte[] bResult = new byte[bLen];
            System.arraycopy(bRecv, 0, bResult, 0, bLen);
            return bResult;
        } else {
            return null;
        }
    }
    @SuppressWarnings("FallThrough")
    public void doQuery() {
        if (!this.onCmd) {
            String strQuery = "ZXGRESULT\r";
            byte[] bQuery = strQuery.getBytes();
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(2000, 2 * this.mChannel, true);
            if (bRecv != null) {
                String strRecv = new String(bRecv);
                String[] strArray = strRecv.split(",");
                if (strArray.length >= 5) {
                    float testResult = Float.parseFloat(strArray[1]);
                    Timestamp testTime = TimeHelper.parseDevTime(strArray[2]);
                    this.setParamTestVal((double)testResult, "N", testTime);
                    this.setStatus(true);
                    Utility.logInfo("ZteCodE310 Dev: 查询成功，通道" + this.mChannel);
                    String strWarn;
                    if (strArray[3].length() == 3) {
                        strWarn = strArray[3].substring(1, 2);
                        byte var11 = -1;
                        switch(strWarn.hashCode()) {
                            case 51:
                                if (strWarn.equals("3")) {
                                    var11 = 0;
                                }
                                break;
                            case 52:
                                if (strWarn.equals("4")) {
                                    var11 = 1;
                                }
                                break;
                            case 53:
                                if (strWarn.equals("5")) {
                                    var11 = 2;
                                }
                        }

                        switch(var11) {
                            case 0:
                                this.setParam("DevState", 10);
                                break;
                            case 1:
                                this.setParam("DevState", 9);
                                break;
                            case 2:
                                this.setParam("DevState", 1);
                                break;
                            default:
                                this.setParam("DevState", 0);
                        }
                    }

                    if (strArray[4].length() == 24) {
                        strWarn = strArray[4];
                        if (strWarn.substring(1, 2) != "1" && strWarn.substring(2, 3) != "1" && strWarn.substring(3, 4) != "1" && strWarn.substring(4, 5) != "1" && strWarn.substring(5, 6) != "1" && strWarn.substring(6, 7) != "1" && strWarn.substring(7, 8) != "1") {
                            if (strWarn.substring(0, 1) == "1") {
                                this.setParam("DevAlarm", 2);
                            } else if (strWarn.substring(19, 20) == "1") {
                                this.setParam("DevAlarm", 7);
                            } else if (strWarn.substring(8, 9) == "1") {
                                this.setParam("DevAlarm", 8);
                            } else if (strWarn.substring(20, 21) == "1") {
                                this.setParam("DevAlarm", 9);
                            } else {
                                this.setParam("DevAlarm", 0);
                            }
                        } else {
                            this.setParam("DevAlarm", 1);
                        }
                    }
                } else {
                    this.setStatus(false);
                    Utility.logInfo("ZteCodE310 Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else if (MonitorService.autoData) {
                double dTmp = this.rand.nextDouble() * 10.0D;
                this.setParamTestVal(dTmp, "N", (Timestamp)null);
                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("ZteCodE310 Dev: 查询失败，通道" + this.mChannel + "，无返回");
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
            String strCmd;
            byte[] bCmd;
            switch(cmdType) {
                case 8:
                    strCmd = "ZXECALIBRATION\r";
                    bCmd = strCmd.getBytes();
                    break;
                case 9:
                    strCmd = "ZXECALIBRATION\r";
                    bCmd = strCmd.getBytes();
                    break;
                case 10:
                    strCmd = "ZXECLEAN\r";
                    bCmd = strCmd.getBytes();
                    break;
                case 11:
                    strCmd = "ZXESTOP\r";
                    bCmd = strCmd.getBytes();
                    break;
                case 12:
                    return false;
                case 13:
                    return false;
                default:
                    strCmd = "ZXEMEASURE\r";
                    bCmd = strCmd.getBytes();
            }

            this.onCmd = true;
            this.mInterComm.clearRecv(2 * this.mChannel + 1);
            this.mInterComm.Send(bCmd);
            byte[] bRecv = this.getRecv(2000, 2 * this.mChannel + 1, false);
            byte iResult;
            if (bRecv != null) {
                iResult = 1;
            } else {
                iResult = 0;
            }

            if (cmdQN != null && this.devExeListener != null) {
                this.devExeListener.OnExeEvent(cmdQN, cmdType, iResult);
            }

            this.onCmd = false;
            return iResult == 1;
        }
    }

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                DevCodZteE310.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
        this.doDevCmd(8, strParam, strQN);
    }

    public void doStdCal(String strQN, String strParam) {
        this.doDevCmd(9, strParam, strQN);
    }

    public void doInitCmd() {
        this.doDevCmdThread(10, (String)null, (String)null);
    }

    public void doStopCmd() {
        this.doDevCmdThread(11, (String)null, (String)null);
    }

    public void doRsetCmd() {
        this.doDevCmdThread(12, (String)null, (String)null);
    }

    public void doSetTime(String strQN, String strParam) {
        this.doDevCmdThread(13, strParam, strQN);
    }
}
