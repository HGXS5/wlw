//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.cod;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Random;
import org.joda.time.DateTime;

public class DevCodHongkai extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevCodHongkai() {
    }

    private byte[] getRecv(int waitTime, int recvChannel, boolean bQuery) {
        int bLen = 0;
        byte[] bRecv = new byte[512];
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
                if (bLen + tmpRecv.length > 100) {
                    bLen = 0;
                    break;
                }

                System.arraycopy(tmpRecv, 0, bRecv, bLen, tmpRecv.length);
                bLen += tmpRecv.length;
                if (bQuery) {
                    if (bLen > 26 && bRecv[0] == 37 && bRecv[bLen - 1] == 13) {
                        break;
                    }
                } else if (bLen > 8 && bRecv[0] == 37 && bRecv[bLen - 1] == 13) {
                    break;
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var12) {
                }
            }
        }

        if (bLen > 8 && bRecv[0] == 37 && bRecv[bLen - 1] == 13) {
            byte[] bResult = new byte[bLen];
            System.arraycopy(bRecv, 0, bResult, 0, bLen);
            return bResult;
        } else {
            return null;
        }
    }

    public void doQuery() {
        if (!this.onCmd) {
            byte[] bQuery = new byte[]{37, 48, 49, 35, 82, 68, 68, 48, 50, 53, 48, 52, 48, 50, 53, 48, 56, 42, 42, 13, 10};
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(2000, 2 * this.mChannel, true);
            if (bRecv != null && bRecv.length > 26) {
                String strData = new String(bRecv);
                int iSec = Integer.parseInt(strData.substring(6, 8));
                int iMin = Integer.parseInt(strData.substring(8, 10));
                int iHour = Integer.parseInt(strData.substring(10, 12));
                int iDay = Integer.parseInt(strData.substring(12, 14));
                int iMon = Integer.parseInt(strData.substring(14, 16));
                int iYear = Integer.parseInt(strData.substring(16, 18)) + 2000;
                byte[] bData = Utility.hexStringToBytes(strData.substring(18, 26));
                DateTime dateTime = new DateTime(iYear, iMon, iDay, iHour, iMin, iSec);
                Timestamp timestamp = new Timestamp(dateTime.getMillis());
                float testResult = Utility.getFloatLittleEndian(bData, 0);
                this.setParamTestVal((double)testResult, "N", timestamp);
                this.setStatus(true);
                Utility.logInfo("HongkaiCod Dev: 查询成功，通道" + this.mChannel);
            } else if (MonitorService.autoData) {
                double dTmp = this.rand.nextDouble() * 10.0D;
                this.setParamTestVal(dTmp, "N", (Timestamp)null);
                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("HongkaiCod Dev: 查询失败，通道" + this.mChannel + "，无返回");
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
                    byte[] bCmd = new byte[]{37, 48, 49, 35, 87, 68, 68, 48, 50, 53, 50, 48, 48, 50, 53, 50, 48, 48, 49, 48, 48, 42, 42, 13, 10};
                    this.onCmd = true;
                    this.mInterComm.clearRecv(2 * this.mChannel + 1);
                    this.mInterComm.Send(bCmd);
                    byte[] bRecv = this.getRecv(2000, 2 * this.mChannel, false);
                    byte iResult;
                    if (bRecv != null) {
                        iResult = 1;
                        Utility.logInfo("HongkaiCod Dev: 启动成功");
                    } else {
                        iResult = 0;
                        Utility.logInfo("HongkaiCod Dev: 启动失败");
                    }

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
                DevCodHongkai.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
