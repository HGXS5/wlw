//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CrcUtil {
    public CrcUtil() {
    }

    public static int getCrc16_MCRF4XX(byte[] bufData) {
        return getCrc16_MCRF4XX(bufData, 0, bufData.length);
    }

    public static int getCrc16_MCRF4XX(byte[] bufData, int bufLen) {
        return getCrc16_MCRF4XX(bufData, 0, bufLen);
    }

    public static int getCrc16_MCRF4XX(byte[] bufData, int bufIndex, int bufLen) {
        int CRC = 65535;
        int POLYNOMIAL = '萈';
        if (bufLen == 0) {
            return 0;
        } else {
            for(int i = bufIndex; i < bufIndex + bufLen; ++i) {
                if (i >= bufData.length) {
                    return 0;
                }

                CRC ^= bufData[i] & 255;

                for(int j = 0; j < 8; ++j) {
                    if ((CRC & 1) != 0) {
                        CRC >>= 1;
                        CRC ^= POLYNOMIAL;
                    } else {
                        CRC >>= 1;
                    }
                }
            }

            return CRC;
        }
    }

    public static int getCrc16_XMODEM(byte[] bufData) {
        return getCrc16_XMODEM(bufData, 0, bufData.length);
    }

    public static int getCrc16_XMODEM(byte[] bufData, int bufLen) {
        return getCrc16_XMODEM(bufData, 0, bufLen);
    }

    public static int getCrc16_XMODEM(byte[] bufData, int bufIndex, int bufLen) {
        int CRC = 0;
        int POLYNOMIAL = 4129;
        if (bufLen == 0) {
            return 0;
        } else {
            for(int i = bufIndex; i < bufIndex + bufLen; ++i) {
                if (i >= bufData.length) {
                    return 0;
                }

                CRC ^= (bufData[i] & 255) << 8;

                for(int j = 0; j < 8; ++j) {
                    if ((CRC & '耀') != 0) {
                        CRC <<= 1;
                        CRC ^= POLYNOMIAL;
                    } else {
                        CRC <<= 1;
                    }
                }
            }

            return CRC;
        }
    }

    public static int getCrc16_X25(byte[] bufData) {
        return getCrc16_X25(bufData, 0, bufData.length);
    }

    public static int getCrc16_X25(byte[] bufData, int bufLen) {
        return getCrc16_X25(bufData, 0, bufLen);
    }

    public static int getCrc16_X25(byte[] bufData, int bufIndex, int bufLen) {
        return getCrc16_MCRF4XX(bufData, bufIndex, bufLen) ^ '\uffff';
    }

    public static String getMD5(String plainText, String saltText, int length) {
        try {
            String strInput = plainText + saltText;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(strInput.getBytes());
            byte[] digest = md.digest();
            return Utility.bytesToHexStringNoSpace(digest, digest.length).substring(0, length).toLowerCase();
        } catch (NoSuchAlgorithmException var6) {
            var6.printStackTrace();
            return null;
        }
    }
}
