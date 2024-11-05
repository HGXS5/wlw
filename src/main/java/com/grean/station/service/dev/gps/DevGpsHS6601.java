//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.gps;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.util.Random;

public class DevGpsHS6601 extends DevService {
    private Random rand = new Random();

    public DevGpsHS6601() {
    }

    public void doQuery() {
        int regLength = 35;
        byte[] bContent = new byte[2 * regLength];
        this.mAddress = this.mCfgDev.getAddress().intValue();
        byte[] bQuery = new byte[]{(byte)this.mAddress, 3, 0, 5, 0, 35, 0, 0};
        int crc_result = Utility.calcCrc16(bQuery, 0, bQuery.length - 2);
        byte[] tmp_bytes = Utility.getByteArray(crc_result);
        bQuery[bQuery.length - 2] = tmp_bytes[2];
        bQuery[bQuery.length - 1] = tmp_bytes[3];
        this.mInterComm.clearRecv(2 * this.mChannel);
        this.mInterComm.Send(bQuery);
        byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
        if (bRecv != null) {
            if (bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 3) {
                for(int i = 0; i < regLength * 2; ++i) {
                    bContent[i] = bRecv[3 + i];
                }

                String strRecv = new String(bContent);
                String[] strValue = strRecv.split(",");
                if (strValue.length > 7 && strValue[0].equals("$GNRMC")) {
                    this.channelVals[0] = (double)Integer.parseInt(strValue[3].substring(0, 2)) + Double.parseDouble(strValue[3].substring(2)) / 60.0D;
                    this.channelVals[1] = (double)Integer.parseInt(strValue[5].substring(0, 3)) + Double.parseDouble(strValue[5].substring(3)) / 60.0D;
                    this.channelVals[2] = Double.parseDouble(strValue[7]);
                    this.setStatus(true);
                    Utility.logInfo("GpsHS6601 Dev: 查询成功，通道" + this.mChannel);
                } else {
                    this.setStatus(false);
                    Utility.logInfo("GpsHS6601 Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
                }
            } else {
                this.setStatus(false);
                Utility.logInfo("GpsHS6601 Dev: 查询失败，通道" + this.mChannel + "，返回格式异常");
            }
        } else if (MonitorService.autoData) {
            double dTmp = this.rand.nextDouble() * 10.0D;
            this.channelVals[0] = dTmp;
            this.channelVals[1] = dTmp;
            this.channelVals[2] = dTmp;
            this.setStatus(true);
        } else {
            this.setStatus(false);
            Utility.logInfo("GpsHS6601 Dev: 查询失败，通道" + this.mChannel + "，无返回");
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
