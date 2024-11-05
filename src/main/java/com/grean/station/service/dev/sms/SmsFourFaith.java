//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.sms;

public class SmsFourFaith extends SmsService {
    public SmsFourFaith() {
    }

    public void doSend(String smsInfo) {
        if (this.mInterComm != null) {
            try {
                byte[] bInfo = smsInfo.getBytes("GBK");
                byte[] bSend = new byte[bInfo.length + 2];
                System.arraycopy(bInfo, 0, bSend, 1, bInfo.length);
                bSend[0] = 2;
                bSend[bSend.length - 1] = 3;
                this.mInterComm.Send(bSend);
            } catch (Exception var4) {
            }

        }
    }
}
