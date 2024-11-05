//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.env;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Random;

public class EnvWsdModbus extends DevService {
    private Random rand = new Random();

    public EnvWsdModbus() {
    }
    @SuppressWarnings("FallThrough")
    public void doQuery() {
        int regLength = 2;
        this.mAddress = this.mCfgDev.getAddress().intValue();
        byte[] bQuery = new byte[]{(byte)this.mAddress, 3, 0, 0, 0, 2, 0, 0};
        int crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
        byte[] tmp_bytes = Utility.getByteArray(crc_result);
        bQuery[bQuery.length - 2] = tmp_bytes[2];
        bQuery[bQuery.length - 1] = tmp_bytes[3];
        this.mInterComm.clearRecv(2 * this.mChannel);
        this.mInterComm.Send(bQuery);
        byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
        double dTmp;
        if (bRecv != null) {
            if (bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 3) {
                String var8 = this.mCfgFactor.getName();
                byte var9 = -1;
                switch(var8.hashCode()) {
                    case 898461:
                        if (var8.equals("温度")) {
                            var9 = 0;
                        }
                        break;
                    case 901127:
                        if (var8.equals("湿度")) {
                            var9 = 1;
                        }
                }

                switch(var9) {
                    case 0:
                        dTmp = (double)Utility.getShort2(bRecv, 3) * 0.1D;
                        break;
                    case 1:
                        dTmp = (double)Utility.getShort2(bRecv, 5) * 0.1D;
                        break;
                    default:
                        dTmp = 0.0D;
                }

                this.setParamTestValAll(dTmp, "N", (Timestamp)null);
                this.setStatus(true);
                Utility.logInfo("EnvWsdModbus: 查询成功，通道" + this.mChannel);
            } else {
                this.setStatus(false);
                Utility.logInfo("EnvWsdModbus: 查询失败，通道" + this.mChannel + "，返回格式异常");
            }
        } else if (MonitorService.autoData) {
            dTmp = this.rand.nextDouble() * 10.0D;
            this.setParamTestValAll(dTmp, "N", (Timestamp)null);
            this.setStatus(true);
        } else {
            this.setStatus(false);
            Utility.logInfo("EnvWsdModbus: 查询失败，通道" + this.mChannel + "，无返回");
        }

    }

    public void saveResult() {
    }

    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        return false;
    }

    public void doMeaCmd() {
    }

    public void doStdCmd(String strQN, String strParam) {
    }

    public void doZeroCmd(String strQN, String strParam) {
    }

    public void doSpanCmd(String strQN, String strParam) {
    }

    public void doBlnkCmd(String strQN, String strParam) {
    }

    public void doParCmd(String strQN, String strParam) {
    }

    public void doRcvrCmd(String strQN, String strParam) {
    }

    public void doBlnkCal(String strQN, String strParam) {
    }

    public void doStdCal(String strQN, String strParam) {
    }

    public void doInitCmd() {
    }

    public void doStopCmd() {
    }

    public void doRsetCmd() {
    }

    public void doSetTime(String strQN, String strParam) {
    }
}
