//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.flow;

import com.grean.station.service.MonitorService;
import com.grean.station.service.dev.DevService;
import com.grean.station.utils.Utility;
import java.sql.Timestamp;
import java.util.Random;

public class DevFlowHzDirect extends DevService {
    private Random rand = new Random();

    public DevFlowHzDirect() {
    }

    public void doQuery() {
        int regLength = 2;
        this.mAddress = this.mCfgDev.getAddress().intValue();
        byte[] bQuery1 = new byte[]{(byte)this.mAddress, 3, 0, 15, 0, 2, 0, 0};
        byte[] bQuery2 = new byte[]{(byte)this.mAddress, 3, 0, 12, 0, 1, 0, 0};
        int crc_result = Utility.calcCrc16(bQuery1, 0, bQuery1.length - 2);
        byte[] tmp_bytes = Utility.getByteArray(crc_result);
        bQuery1[bQuery1.length - 2] = tmp_bytes[2];
        bQuery1[bQuery1.length - 1] = tmp_bytes[3];
        crc_result = Utility.calcCrc16(bQuery2, 0, bQuery2.length - 2);
        tmp_bytes = Utility.getByteArray(crc_result);
        bQuery2[bQuery2.length - 2] = tmp_bytes[2];
        bQuery2[bQuery2.length - 1] = tmp_bytes[3];
        int direcParam = 1;
        this.mInterComm.clearRecv(2 * this.mChannel);
        this.mInterComm.Send(bQuery2);
        byte[] bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
        if (bRecv != null && bRecv.length == 7 && bRecv[0] == this.mAddress && bRecv[1] == 3 && Utility.getShort(bRecv, 3) == 0) {
            direcParam = -1;
        }

        this.mInterComm.clearRecv(2 * this.mChannel);
        this.mInterComm.Send(bQuery1);
        bRecv = this.getRecv(this.mInterComm, this.mAddress, 2 * this.mChannel, true, 2000);
        double dTmp;
        if (bRecv != null) {
            if (bRecv.length == regLength * 2 + 5 && bRecv[0] == this.mAddress && bRecv[1] == 3) {
                dTmp = (double)Utility.getFloatLittleEndian(bRecv, 3);
                dTmp = (double)direcParam * dTmp;
                this.setParamTestValAll(dTmp, "N", (Timestamp)null);
                this.setStatus(true);
                Utility.logInfo("DevFlowHzDirect: 查询成功，通道" + this.mChannel);
            } else {
                this.setStatus(false);
                Utility.logInfo("DevFlowHzDirect: 查询失败，通道" + this.mChannel + "，返回格式异常");
            }
        } else if (MonitorService.autoData) {
            dTmp = this.rand.nextDouble() * 10.0D;
            this.setParamTestValAll(dTmp, "N", (Timestamp)null);
            this.setStatus(true);
        } else {
            this.setStatus(false);
            Utility.logInfo("DevFlowHzDirect: 查询失败，通道" + this.mChannel + "，无返回");
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
