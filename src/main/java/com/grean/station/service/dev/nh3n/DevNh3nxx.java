//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.nh3n;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Random;

public class DevNh3nxx extends DevService {
    private Random rand = new Random();

    public DevNh3nxx() {
    }

    public void doQuery() {
        int regLength = 1;
        this.mAddress = this.mCfgDev.getAddress().intValue();
        byte[] bQuery = new byte[]{(byte)this.mAddress, 3, 0, 4, 0, 1, 0, 0};
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
                int iValue = bRecv[4] & 255;
                int iDigit = bRecv[3] & 255;
                dTmp = (double)iValue / ((double)iDigit + 0.0D);
                this.setParamTestVal(dTmp, "N", (Timestamp)null);
                this.setStatus(true);
                Utility.logInfo("HachFive Dev: 查询成功，通道" + this.mChannel);
            } else {
                this.setStatus(false);
                Utility.logInfo("HachFive Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
            }
        } else if (MonitorService.autoData) {
            dTmp = this.rand.nextDouble() * 10.0D;
            this.setParamTestVal(dTmp, "N", (Timestamp)null);
            this.setStatus(true);
        } else {
            this.setStatus(false);
            Utility.logInfo("Modbus Dev: 查询失败，通道" + this.mChannel + "，无返回");
        }

    }

    public void saveResult() {
    }

    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        this.initDevState(cmdType);
        this.mCmdType = cmdType;
        return false;
    }

    public void doDevCmdThread(final int cmdType, final String cmdParam, final String cmdQN) {
        this.fixedThreadPool.execute(new Runnable() {
            public void run() {
                DevNh3nxx.this.doDevCmd(cmdType, cmdParam, cmdQN);
            }
        });
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
        this.initDevState(0);
    }

    public void doRsetCmd() {
    }

    public void doSetTime(String strQN, String strParam) {
    }
}
