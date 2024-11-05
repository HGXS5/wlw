//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.upload;

import com.grean.station.utils.AesUtil;
import com.grean.station.utils.Utility;

public class Upload212_Encryption extends Upload212 {
    public Upload212_Encryption() {
    }

    public byte[] getSendBytes(String sendStr) {
        try {
            byte[] bufTmp = sendStr.getBytes();
            int crc16 = Utility.crc16ANSI(bufTmp);
            String strTmp = "##" + String.format("%04d", bufTmp.length) + sendStr + String.format("%04x", crc16).toUpperCase() + "\r\n";
            return this.getEncodeByte(strTmp, this.keyString);
        } catch (Exception var5) {
            return null;
        }
    }

    private byte[] getEncodeByte(String strContent, String strKey) {
        int iStart = strContent.indexOf("CP=&&");
        if (iStart > -1) {
            iStart += 5;
            int iStop = strContent.indexOf("&&", iStart);
            if (iStop <= -1) {
                return strContent.getBytes();
            } else {
                iStop += 2;
                String strHead = strContent.substring(0, iStart);
                int iSize = (iStop - iStart) / 16;
                iSize *= 16;
                iStop = iStart + iSize;
                String strCode = strContent.substring(iStart, iStop);
                String strEnd = strContent.substring(iStop);
                byte[] encodeByte;
                if (strCode.length() > 0) {
                    try {
                        encodeByte = new byte[strContent.length()];
                        byte[] tmpByte = strHead.getBytes();

                        int i;
                        for(i = 0; i < tmpByte.length; ++i) {
                            encodeByte[i] = tmpByte[i];
                        }

                        tmpByte = AesUtil.encryptNoPadding(strCode, strKey);

                        for(i = 0; i < tmpByte.length; ++i) {
                            encodeByte[strHead.length() + i] = tmpByte[i];
                        }

                        tmpByte = strEnd.getBytes();

                        for(i = 0; i < tmpByte.length; ++i) {
                            encodeByte[strHead.length() + strCode.length() + i] = tmpByte[i];
                        }
                    } catch (Exception var12) {
                        encodeByte = strContent.getBytes();
                    }
                } else {
                    encodeByte = strContent.getBytes();
                }

                return encodeByte;
            }
        } else {
            return strContent.getBytes();
        }
    }
}
