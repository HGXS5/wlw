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

public class DevCodYanaco380 extends DevService {
    private boolean onCmd = false;
    private Random rand = new Random();

    public DevCodYanaco380() {
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
                if (bLen + tmpRecv.length > 500) {
                    bLen = 0;
                    break;
                }

                System.arraycopy(tmpRecv, 0, bRecv, bLen, tmpRecv.length);
                bLen += tmpRecv.length;
                if (bQuery) {
                    if (bLen > 90 && bRecv[0] == 2 && bRecv[bLen - 1] == 13) {
                        break;
                    }
                } else if (bLen > 0) {
                    break;
                }

                try {
                    Thread.sleep(50L);
                } catch (Exception var12) {
                }
            }
        }

        byte[] bResult;
        if (bQuery) {
            if (bLen > 90 && bRecv[0] == 2 && bRecv[bLen - 1] == 13) {
                bResult = new byte[bLen];
                System.arraycopy(bRecv, 0, bResult, 0, bLen);
                return bResult;
            }
        } else if (bLen > 0) {
            bResult = new byte[bLen];
            System.arraycopy(bRecv, 0, bResult, 0, bLen);
            return bResult;
        }

        return null;
    }

    public void doQuery() {
        if (!this.onCmd) {
            byte[] bQuery = new byte[]{2, 68, 65, 84, 65, 3, 49, 70, 13};
            this.mInterComm.clearRecv(2 * this.mChannel);
            this.mInterComm.Send(bQuery);
            byte[] bRecv = this.getRecv(2000, 2 * this.mChannel, true);
            if (bRecv != null) {
                byte[] bData = new byte[10];

                for(int i = 0; i < 10; ++i) {
                    bData[i] = bRecv[26 + i];
                }

                String strData = new String(bData);
                float testResult = Float.parseFloat(strData);
                this.setParamTestVal((double)testResult, "N", (Timestamp)null);
                this.setStatus(true);
                Utility.logInfo("YanacoCod380 Dev: 查询成功，通道" + this.mChannel);
            } else if (MonitorService.autoData) {
                double dTmp = this.rand.nextDouble() * 10.0D;
                this.setParamTestVal(dTmp, "N", (Timestamp)null);
                this.setStatus(true);
            } else {
                this.setStatus(false);
                Utility.logInfo("YanacoCod380 Dev: 查询失败，通道" + this.mChannel + "，无返回");
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
                    byte[] bCmd = new byte[]{2, 77, 69, 65, 83, 3, 50, 66, 13};
                    this.onCmd = true;
                    this.mInterComm.clearRecv(2 * this.mChannel + 1);
                    this.mInterComm.Send(bCmd);
                    byte[] bRecv = this.getRecv(2000, 2 * this.mChannel, false);
                    byte iResult;
                    if (bRecv != null) {
                        iResult = 1;
                        Utility.logInfo("YanacoCod380 Dev: 启动成功");
                    } else {
                        iResult = 0;
                        Utility.logInfo("YanacoCod380 Dev: 启动失败");
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
                DevCodYanaco380.this.doDevCmd(cmdType, cmdParam, cmdQN);
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
