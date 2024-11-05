//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utility {
    public static String vocPathName;
    public static String vocFileName;
    public static Timestamp dtuUpload;
    public static Timestamp wtwCalTime;
    public static DateTime startTime;
    public static boolean jcgy = false;
    public static boolean showInfo = true;
    public static final Logger logger = LoggerFactory.getLogger(Utility.class);
    static final byte[] crc16_tab_h = new byte[]{0, -63, -127, 64, 1, -64, -128, 65, 1, -64, -128, 65, 0, -63, -127, 64, 1, -64, -128, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, -128, 65, 1, -64, -128, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, -128, 65, 0, -63, -127, 64, 1, -64, -128, 65, 1, -64, -128, 65, 0, -63, -127, 64, 1, -64, -128, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, -128, 65, 0, -63, -127, 64, 1, -64, -128, 65, 1, -64, -128, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, -128, 65, 1, -64, -128, 65, 0, -63, -127, 64, 1, -64, -128, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, -128, 65, 1, -64, -128, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, -128, 65, 0, -63, -127, 64, 1, -64, -128, 65, 1, -64, -128, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, -128, 65, 1, -64, -128, 65, 0, -63, -127, 64, 1, -64, -128, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, -128, 65, 0, -63, -127, 64, 1, -64, -128, 65, 1, -64, -128, 65, 0, -63, -127, 64, 1, -64, -128, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, -128, 65, 1, -64, -128, 65, 0, -63, -127, 64, 0, -63, -127, 64, 1, -64, -128, 65, 0, -63, -127, 64, 1, -64, -128, 65, 1, -64, -128, 65, 0, -63, -127, 64};
    static final byte[] crc16_tab_l = new byte[]{0, -64, -63, 1, -61, 3, 2, -62, -58, 6, 7, -57, 5, -59, -60, 4, -52, 12, 13, -51, 15, -49, -50, 14, 10, -54, -53, 11, -55, 9, 8, -56, -40, 24, 25, -39, 27, -37, -38, 26, 30, -34, -33, 31, -35, 29, 28, -36, 20, -44, -43, 21, -41, 23, 22, -42, -46, 18, 19, -45, 17, -47, -48, 16, -16, 48, 49, -15, 51, -13, -14, 50, 54, -10, -9, 55, -11, 53, 52, -12, 60, -4, -3, 61, -1, 63, 62, -2, -6, 58, 59, -5, 57, -7, -8, 56, 40, -24, -23, 41, -21, 43, 42, -22, -18, 46, 47, -17, 45, -19, -20, 44, -28, 36, 37, -27, 39, -25, -26, 38, 34, -30, -29, 35, -31, 33, 32, -32, -96, 96, 97, -95, 99, -93, -94, 98, 102, -90, -89, 103, -91, 101, 100, -92, 108, -84, -83, 109, -81, 111, 110, -82, -86, 106, 107, -85, 105, -87, -88, 104, 120, -72, -71, 121, -69, 123, 122, -70, -66, 126, 127, -65, 125, -67, -68, 124, -76, 116, 117, -75, 119, -73, -74, 118, 114, -78, -77, 115, -79, 113, 112, -80, 80, -112, -111, 81, -109, 83, 82, -110, -106, 86, 87, -105, 85, -107, -108, 84, -100, 92, 93, -99, 95, -97, -98, 94, 90, -102, -101, 91, -103, 89, 88, -104, -120, 72, 73, -119, 75, -117, -118, 74, 78, -114, -113, 79, -115, 77, 76, -116, 68, -124, -123, 69, -121, 71, 70, -122, -126, 66, 67, -125, 65, -127, -128, 64};

    public Utility() {
    }

    public static int calcCrc16(byte[] data) {
        return calcCrc16(data, 0, data.length);
    }

    public static int calcCrc16(byte[] data, int offset, int len) {
        return calcCrc16(data, offset, len, 65535);
    }

    public static int calcCrc16(byte[] data, int offset, int len, int preval) {
        int ucCRCHi = (preval & '\uff00') >> 8;
        int ucCRCLo = preval & 255;

        for(int i = 0; i < len; ++i) {
            int iIndex = (ucCRCLo ^ data[offset + i]) & 255;
            ucCRCLo = ucCRCHi ^ crc16_tab_h[iIndex];
            ucCRCHi = crc16_tab_l[iIndex];
        }

        return ucCRCHi & 255 | (ucCRCLo & 255) << 8 & '\uffff';
    }

    public static int getCrc16(byte[] bufData, int buflen) {
        int CRC = 65535;
        int POLYNOMIAL = 'ꀁ';
        if (buflen == 0) {
            return 0;
        } else {
            for(int i = 0; i < buflen; ++i) {
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

    public static int crc16ANSI(byte[] bytes) {
        int usDataLen = bytes.length;
        int crc_reg = 65535;

        for(int i = 0; i < usDataLen; ++i) {
            crc_reg = crc_reg >> 8 ^ 255 & bytes[i];

            for(int j = 0; j < 8; ++j) {
                int check = crc_reg & 1;
                crc_reg >>= 1;
                if (check == 1) {
                    crc_reg ^= 40961;
                }
            }
        }

        return crc_reg;
    }

    public static byte szy_CRC_8(byte[] bufData, int bufIndex, int bufLen) {
        byte crc8 = 0;

        for(int i = bufIndex; i < bufIndex + bufLen; ++i) {
            crc8 ^= bufData[i];

            for(int j = 0; j < 8; ++j) {
                if ((crc8 & 128) == 128) {
                    crc8 = (byte)(crc8 << 1);
                    crc8 = (byte)(crc8 ^ 229);
                } else {
                    crc8 = (byte)(crc8 << 1);
                }
            }
        }

        return crc8;
    }

    public static String xor8(byte[] bytes) {
        if (bytes.length <= 2) {
            return null;
        } else {
            byte xor_result = bytes[0];

            int iHigh;
            for(iHigh = 1; iHigh < bytes.length; ++iHigh) {
                xor_result = (byte)(xor_result ^ 255 & bytes[iHigh]);
            }

            iHigh = xor_result >> 4;
            byte bTmp = (byte)(xor_result << 4);
            int iLow = (bTmp & 255) >> 4;
            if (iHigh < 10) {
                iHigh += 48;
            } else {
                iHigh = iHigh - 10 + 65;
            }

            if (iLow < 10) {
                iLow += 48;
            } else {
                iLow = iLow - 10 + 65;
            }

            return String.format("%c%c", iHigh, iLow);
        }
    }

    public static String xor8(byte[] bytes, int index, int length) {
        if (bytes.length <= 2) {
            return null;
        } else {
            byte xor_result = bytes[index];

            int iHigh;
            for(iHigh = index + 1; iHigh < index + length; ++iHigh) {
                xor_result = (byte)(xor_result ^ 255 & bytes[iHigh]);
            }

            iHigh = xor_result >> 4;
            byte bTmp = (byte)(xor_result << 4);
            int iLow = (bTmp & 255) >> 4;
            if (iHigh < 10) {
                iHigh += 48;
            } else {
                iHigh = iHigh - 10 + 65;
            }

            if (iLow < 10) {
                iLow += 48;
            } else {
                iLow = iLow - 10 + 65;
            }

            return String.format("%c%c", iHigh, iLow);
        }
    }

    public static String bbe_xor8(byte[] bytes, int index) {
        byte xor_result = 0;
        if (bytes.length <= index) {
            return "00";
        } else {
            int iHigh;
            for(iHigh = index; iHigh < bytes.length && bytes[iHigh] != 13 && bytes[iHigh] != 10 && bytes[iHigh] != 12; ++iHigh) {
                xor_result = (byte)(xor_result ^ 255 & bytes[iHigh]);
            }

            iHigh = xor_result >> 4;
            byte bTmp = (byte)(xor_result << 4);
            int iLow = (bTmp & 255) >> 4;
            if (iHigh < 10) {
                iHigh += 48;
            } else {
                iHigh = iHigh - 10 + 65;
            }

            if (iLow < 10) {
                iLow += 48;
            } else {
                iLow = iLow - 10 + 65;
            }

            return String.format("%c%c", iHigh, iLow);
        }
    }

    public static byte add8(byte[] bytes, int index, int length) {
        byte add_result = bytes[index];

        for(int i = index + 1; i < index + length; ++i) {
            add_result = (byte)(add_result + (255 & bytes[i]));
        }

        return add_result;
    }

    public static byte[] getByteArray(char c) {
        byte[] b = new byte[]{(byte)((c & '\uff00') >> 8), (byte)(c & 255)};
        return b;
    }

    public static char getChar(byte[] arr, int index) {
        return (char)('\uff00' & arr[index] << 8 | 255 & arr[index + 1]);
    }

    public static byte[] getByteArray(short s) {
        byte[] b = new byte[]{(byte)((s & '\uff00') >> 8), (byte)(s & 255)};
        return b;
    }

    public static int getShort(byte[] arr, int index) {
        return (255 & arr[index]) * 256 + (255 & arr[index + 1]);
    }

    public static int getShort2(byte[] arr, int index) {
        int iTmp = (255 & arr[index]) * 256 + (255 & arr[index + 1]);
        return (arr[index] & 255) < 128 ? iTmp : -1 * ('\uffff' - iTmp);
    }

    public static int getShort3(byte[] arr, int index) {
        return (255 & arr[index]) * 256 * 256 + (255 & arr[index + 1]) * 256 + (255 & arr[index + 2]);
    }

    public static float getByte2Float(byte[] arr, int index, int length) {
        float fTmp = 0.0F;

        for(int i = index + length - 1; i >= index; --i) {
            int iHigh = (arr[i] & 255) / 16;
            int iLow = (arr[i] & 255) % 16;
            fTmp = (fTmp + (float)iLow) / 16.0F;
            fTmp = (fTmp + (float)iHigh) / 16.0F;
        }

        return fTmp;
    }

    public static byte[] getByteArray(int i) {
        byte[] b = new byte[]{(byte)((i & -16777216) >> 24), (byte)((i & 16711680) >> 16), (byte)((i & '\uff00') >> 8), (byte)(i & 255)};
        return b;
    }

    public static int getInt(byte[] arr, int index) {
        return -16777216 & arr[index + 0] << 24 | 16711680 & arr[index + 1] << 16 | '\uff00' & arr[index + 2] << 8 | 255 & arr[index + 3];
    }

    public static int getDword(byte[] arr, int index) {
        return -16777216 & arr[index + 2] << 24 | 16711680 & arr[index + 3] << 16 | '\uff00' & arr[index + 0] << 8 | 255 & arr[index + 1];
    }

    public static int getInt(byte b) {
        return b & 255;
    }

    public static byte[] getByteArray(float f) {
        int intbits = Float.floatToIntBits(f);
        return getByteArray(intbits);
    }

    public static float getFloatBigEndian(byte[] arr, int index) {
        return Float.intBitsToFloat(getInt(arr, index));
    }

    public static float getFloatBigEndianSwap(byte[] arr, int index) {
        byte[] tmpByte = new byte[]{arr[index + 2], arr[index + 3], arr[index + 0], arr[index + 1]};
        return Float.intBitsToFloat(getInt(tmpByte, 0));
    }

    public static float getFloatLittleEndian(byte[] arr, int index) {
        byte[] tmpByte = new byte[]{arr[index + 3], arr[index + 2], arr[index + 1], arr[index + 0]};
        return Float.intBitsToFloat(getInt(tmpByte, 0));
    }

    public static float getFloatLittleEndianSwap(byte[] arr, int index) {
        byte[] tmpByte = new byte[]{arr[index + 1], arr[index + 0], arr[index + 3], arr[index + 2]};
        return Float.intBitsToFloat(getInt(tmpByte, 0));
    }

    public static byte[] getByteArray(long l) {
        byte[] b = new byte[]{(byte)((int)(255L & l >> 56)), (byte)((int)(255L & l >> 48)), (byte)((int)(255L & l >> 40)), (byte)((int)(255L & l >> 32)), (byte)((int)(255L & l >> 24)), (byte)((int)(255L & l >> 16)), (byte)((int)(255L & l >> 8)), (byte)((int)(255L & l))};
        return b;
    }

    public static long getLong(byte[] arr, int index) {
        return -72057594037927936L & (long)arr[index + 0] << 56 | 71776119061217280L & (long)arr[index + 1] << 48 | 280375465082880L & (long)arr[index + 2] << 40 | 1095216660480L & (long)arr[index + 3] << 32 | 4278190080L & (long)arr[index + 4] << 24 | 16711680L & (long)arr[index + 5] << 16 | 65280L & (long)arr[index + 6] << 8 | 255L & (long)arr[index + 7];
    }

    public static byte[] getByteArray(double d) {
        long longbits = Double.doubleToLongBits(d);
        return getByteArray(longbits);
    }

    public static double getDouble(byte[] arr, int index) {
        return Double.longBitsToDouble(getLong(arr, index));
    }

    public static String getString(byte[] arr, int index, int length) {
        String tmpStr = "";

        for(int i = 0; i < length && arr[index + i] != 0; ++i) {
            tmpStr = tmpStr + (char)arr[index + i];
        }

        return tmpStr;
    }

    public static String getBCDTime(byte[] arr, int index) {
        String tmp_Time = "20";
        int iTmp = (arr[index + 0] & 240) >> 4;
        tmp_Time = tmp_Time + iTmp;
        iTmp = arr[index + 0] & 15;
        tmp_Time = tmp_Time + iTmp + "-";
        iTmp = (arr[index + 1] & 240) >> 4;
        tmp_Time = tmp_Time + iTmp;
        iTmp = arr[index + 1] & 15;
        tmp_Time = tmp_Time + iTmp + "-";
        iTmp = (arr[index + 2] & 240) >> 4;
        tmp_Time = tmp_Time + iTmp;
        iTmp = arr[index + 2] & 15;
        tmp_Time = tmp_Time + iTmp + " ";
        iTmp = (arr[index + 3] & 240) >> 4;
        tmp_Time = tmp_Time + iTmp;
        iTmp = arr[index + 3] & 15;
        tmp_Time = tmp_Time + iTmp + ":";
        iTmp = (arr[index + 4] & 240) >> 4;
        tmp_Time = tmp_Time + iTmp;
        iTmp = arr[index + 4] & 15;
        tmp_Time = tmp_Time + iTmp + ":";
        iTmp = (arr[index + 5] & 240) >> 4;
        tmp_Time = tmp_Time + iTmp;
        iTmp = arr[index + 5] & 15;
        tmp_Time = tmp_Time + iTmp;
        return tmp_Time;
    }

    public static Integer getBCDVal(byte[] arr, int index, int byteSize) {
        int iResult = 0;
        int i = 0;

        while(i < byteSize) {
            int iTmp = (arr[index + i] & 240) >> 4;
            if (iTmp >= 0 && iTmp < 10) {
                iResult = iResult * 10 + iTmp;
                iTmp = arr[index + i] & 15;
                if (iTmp >= 0 && iTmp < 10) {
                    iResult = iResult * 10 + iTmp;
                    ++i;
                    continue;
                }

                return null;
            }

            return null;
        }

        return iResult;
    }

    public static byte getBCDVal(int iVal) {
        int iTmp = iVal;
        if (iVal > 99) {
            iTmp = iVal % 100;
        }

        byte bHigh = (byte)(iTmp / 10);
        byte bLow = (byte)(iTmp % 10);
        return (byte)(bHigh << 4 | bLow);
    }

    public static Integer getBCDVal(byte bVal) {
        int iResult = 0;
        int iTmp = (bVal & 240) >> 4;
        if (iTmp >= 0 && iTmp < 10) {
            iResult = iResult * 10 + iTmp;
            iTmp = bVal & 15;
            if (iTmp >= 0 && iTmp < 10) {
                iResult = iResult * 10 + iTmp;
                return iResult;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static boolean isNumeric(String num) {
        try {
            Float.parseFloat(num);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }

    public static String bytesToHexString(byte[] src, int size) {
        String ret = "";
        if (src != null && size > 0) {
            for(int i = 0; i < size; ++i) {
                String hex = Integer.toHexString(src[i] & 255);
                if (hex.length() < 2) {
                    hex = "0" + hex;
                }

                hex = hex + " ";
                ret = ret + hex;
            }

            return ret.toUpperCase();
        } else {
            return null;
        }
    }

    public static String bytesToHexStringNoSpace(byte[] src, int size) {
        String ret = "";
        if (src != null && size > 0) {
            for(int i = 0; i < size; ++i) {
                String hex = Integer.toHexString(src[i] & 255);
                if (hex.length() < 2) {
                    hex = "0" + hex;
                }

                ret = ret + hex;
            }

            return ret.toUpperCase();
        } else {
            return null;
        }
    }

    public static byte[] hexStringToBytes(String hex) {
        String strTmp = hex.toUpperCase().replace(" ", "");
        int len = strTmp.length() / 2;
        byte[] result = new byte[len];
        char[] achar = strTmp.toCharArray();

        for(int i = 0; i < len; ++i) {
            int pos = i * 2;
            result[i] = (byte)(toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }

        return result;
    }

    public static byte hexStringToByte(String hex) {
        String strTmp = hex.toUpperCase() + "00";
        String strHex = strTmp.substring(0, 2);
        char[] achar = strHex.toCharArray();
        return (byte)(toByte(achar[0]) << 4 | toByte(achar[1]));
    }

    public static byte toByte(char c) {
        byte b = (byte)"0123456789ABCDEF".indexOf(c);
        return b;
    }

    public static void setBit(byte[] byteArray, int bitAdd, boolean bStatus) {
        if (bitAdd < byteArray.length * 8) {
            int byteIndex = bitAdd / 8;
            int bitIndex = bitAdd % 8;
            if (bStatus) {
                byteArray[byteIndex] = (byte)(byteArray[byteIndex] | 1 << bitIndex);
            } else {
                byte mbyte = (byte)(1 << bitIndex);
                mbyte = (byte)(~mbyte);
                byteArray[byteIndex] &= mbyte;
            }

        }
    }

    public static boolean getBit(byte[] byteArray, int byteIndex, int byteOffset) {
        if (byteIndex < byteArray.length && byteOffset <= 7) {
            int mByte = byteArray[byteIndex] & 255;
            mByte = mByte << 7 - byteOffset & 255;
            mByte >>= 7;
            return mByte > 0;
        } else {
            return false;
        }
    }

    public static String getRandomString(int length) {
        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < length; ++i) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }

        return sb.toString();
    }

    public static String getLongDouble(Double dSrc) {
        String strResult = "";
        if (dSrc == null) {
            return "0";
        } else {
            double dTmp;
            if (dSrc < 0.0D) {
                strResult = "-";
                dTmp = -1.0D * dSrc;
            } else {
                dTmp = dSrc;
            }

            double integerPart = Math.floor(dTmp);
            double decimalPart = dTmp - integerPart;
            String strTmp1 = (new DecimalFormat("##########")).format(integerPart);
            String strTmp2 = (new DecimalFormat(".##########")).format(decimalPart);
            strResult = strResult + strTmp1 + strTmp2;
            return strResult;
        }
    }

    public static double getFormatDouble(double dSrc, int length) {
        String strFormat = "0.";

        for(int i = 0; i < length; ++i) {
            strFormat = strFormat + "0";
        }

        DecimalFormat df = new DecimalFormat(strFormat);
        return Double.parseDouble(df.format(dSrc));
    }

    public static float getFormatFloat(float dSrc, int length) {
        String strFormat = "0.";

        for(int i = 0; i < length; ++i) {
            strFormat = strFormat + "0";
        }

        DecimalFormat df = new DecimalFormat(strFormat);
        return Float.parseFloat(df.format((double)dSrc));
    }

    public static void logInfo(String strInfo) {
        if (showInfo) {
            logger.info(strInfo);
        }

    }

    public static int getTaskID(String taskName) {
        byte var3 = -1;
        switch(taskName.hashCode()) {
            case -1928735488:
                if (taskName.equals("零点核查间隔设置")) {
                    var3 = 16;
                }
                break;
            case -1599212525:
                if (taskName.equals("设置加标体积")) {
                    var3 = 23;
                }
                break;
            case -849535372:
                if (taskName.equals("测量间隔设置")) {
                    var3 = 15;
                }
                break;
            case -689663493:
                if (taskName.equals("跨度核查间隔设置")) {
                    var3 = 17;
                }
                break;
            case 20935048:
                if (taskName.equals("初始化")) {
                    var3 = 10;
                }
                break;
            case 217890765:
                if (taskName.equals("标样核查间隔设置")) {
                    var3 = 18;
                }
                break;
            case 623947571:
                if (taskName.equals("仪器校时")) {
                    var3 = 13;
                }
                break;
            case 624273728:
                if (taskName.equals("仪器重启")) {
                    var3 = 12;
                }
                break;
            case 640181488:
                if (taskName.equals("停止测试")) {
                    var3 = 11;
                }
                break;
            case 656446911:
                if (taskName.equals("加标回收")) {
                    var3 = 7;
                }
                break;
            case 656911409:
                if (taskName.equals("加标配样")) {
                    var3 = 24;
                }
                break;
            case 780100760:
                if (taskName.equals("排储水桶")) {
                    var3 = 20;
                }
                break;
            case 819849941:
                if (taskName.equals("标样校准")) {
                    var3 = 9;
                }
                break;
            case 819856317:
                if (taskName.equals("标样核查")) {
                    var3 = 2;
                }
                break;
            case 833919230:
                if (taskName.equals("模式设置")) {
                    var3 = 14;
                }
                break;
            case 851752365:
                if (taskName.equals("水样测试")) {
                    var3 = 1;
                }
                break;
            case 964064328:
                if (taskName.equals("空白校准")) {
                    var3 = 8;
                }
                break;
            case 964120173:
                if (taskName.equals("空白测试")) {
                    var3 = 5;
                }
                break;
            case 971890883:
                if (taskName.equals("空闲状态")) {
                    var3 = 0;
                }
                break;
            case 985196908:
                if (taskName.equals("系统排空")) {
                    var3 = 25;
                }
                break;
            case 1106386155:
                if (taskName.equals("跨度核查")) {
                    var3 = 4;
                }
                break;
            case 1139785268:
                if (taskName.equals("酸碱切换")) {
                    var3 = 21;
                }
                break;
            case 1174786524:
                if (taskName.equals("集成干预")) {
                    var3 = 19;
                }
                break;
            case 1179888240:
                if (taskName.equals("零点核查")) {
                    var3 = 3;
                }
                break;
            case 1920987016:
                if (taskName.equals("平行样测试")) {
                    var3 = 6;
                }
                break;
            case 2062266799:
                if (taskName.equals("五参数核查")) {
                    var3 = 22;
                }
        }

        short taskID;
        switch(var3) {
            case 0:
                taskID = 0;
                break;
            case 1:
                taskID = 1;
                break;
            case 2:
                taskID = 2;
                break;
            case 3:
                taskID = 3;
                break;
            case 4:
                taskID = 4;
                break;
            case 5:
                taskID = 5;
                break;
            case 6:
                taskID = 6;
                break;
            case 7:
                taskID = 7;
                break;
            case 8:
                taskID = 8;
                break;
            case 9:
                taskID = 9;
                break;
            case 10:
                taskID = 10;
                break;
            case 11:
                taskID = 11;
                break;
            case 12:
                taskID = 12;
                break;
            case 13:
                taskID = 13;
                break;
            case 14:
                taskID = 14;
                break;
            case 15:
                taskID = 15;
                break;
            case 16:
                taskID = 16;
                break;
            case 17:
                taskID = 17;
                break;
            case 18:
                taskID = 18;
                break;
            case 19:
                taskID = 50;
                break;
            case 20:
                taskID = 60;
                break;
            case 21:
                taskID = 100;
                break;
            case 22:
                taskID = 101;
                break;
            case 23:
                taskID = 4000;
                break;
            case 24:
                taskID = 5003;
                break;
            case 25:
                taskID = 5005;
                break;
            default:
                taskID = 99;
        }

        return taskID;
    }

    public static String getTaskName(int taskID) {
        String taskName;
        switch(taskID) {
            case 0:
                taskName = "空闲状态";
                break;
            case 1:
                taskName = "水样测量";
                break;
            case 2:
                taskName = "标样核查";
                break;
            case 3:
                taskName = "零点核查";
                break;
            case 4:
                taskName = "跨度核查";
                break;
            case 5:
                taskName = "空白测试";
                break;
            case 6:
                taskName = "平行样测试";
                break;
            case 7:
                taskName = "加标回收";
                break;
            case 8:
                taskName = "空白校准";
                break;
            case 9:
                taskName = "标样校准";
                break;
            case 10:
                taskName = "初始化";
                break;
            case 11:
                taskName = "停止测试";
                break;
            case 12:
                taskName = "仪器重启";
                break;
            case 13:
                taskName = "仪器校时";
                break;
            case 14:
                taskName = "模式设置";
                break;
            case 15:
                taskName = "测量间隔设置";
                break;
            case 16:
                taskName = "零点核查间隔设置";
                break;
            case 17:
                taskName = "跨度核查间隔设置";
                break;
            case 18:
                taskName = "标样核查间隔设置";
                break;
            case 50:
                taskName = "集成干预";
                break;
            case 60:
                taskName = "排储水桶";
                break;
            case 100:
                taskName = "酸碱切换";
                break;
            case 101:
                taskName = "五参数核查";
                break;
            case 4000:
                taskName = "设置加标体积";
                break;
            case 5003:
                taskName = "加标配样";
                break;
            case 5005:
                taskName = "系统排空";
                break;
            default:
                taskName = "未定义";
        }

        return taskName;
    }

    public static String getDevMode(int modeID) {
        String devMode;
        switch(modeID) {
            case 1:
                devMode = "连续模式";
                break;
            case 2:
                devMode = "周期模式";
                break;
            case 3:
                devMode = "定点模式";
                break;
            case 4:
                devMode = "受控模式";
                break;
            case 5:
                devMode = "手动模式";
                break;
            default:
                devMode = "未定义";
        }

        return devMode;
    }

    public static String getScriptMode(int runMode) {
        String scriptMode;
        switch(runMode) {
            case 1:
                scriptMode = "常规模式";
                break;
            case 2:
                scriptMode = "应急模式";
                break;
            case 3:
                scriptMode = "质控模式";
                break;
            case 4:
                scriptMode = "连续模式";
                break;
            default:
                scriptMode = "维护模式";
        }

        return scriptMode;
    }

    public static String getScriptName(int scriptType) {
        String scriptName;
        switch(scriptType) {
            case 1:
                scriptName = "水样测量";
                break;
            case 2:
                scriptName = "标样核查";
                break;
            case 3:
                scriptName = "零点核查";
                break;
            case 4:
                scriptName = "跨度核查";
                break;
            case 5:
                scriptName = "空白测试";
                break;
            case 6:
                scriptName = "平行样测试";
                break;
            case 7:
                scriptName = "加标回收";
                break;
            case 8:
                scriptName = "空白校准";
                break;
            case 9:
                scriptName = "标样校准";
                break;
            case 50:
                scriptName = "集成干预";
                break;
            case 60:
                scriptName = "排储水桶";
                break;
            case 101:
                scriptName = "系统除藻";
                break;
            case 102:
                scriptName = "系统清洗";
                break;
            case 103:
                scriptName = "清洗内管路";
                break;
            case 104:
                scriptName = "清洗外管路";
                break;
            case 105:
                scriptName = "清洗过滤器";
                break;
            case 106:
                scriptName = "清洗沉砂池";
                break;
            case 107:
                scriptName = "清洗五参数池";
                break;
            default:
                scriptName = "未定义";
        }

        return scriptName;
    }

    public static boolean isWindows() {
        String osName = System.getProperty("os.name");
        return osName.matches("^(?i)Windows.*$");
    }

    public static void openIEBrowser(String strURL) {
        String str = "cmd /c start iexplore " + strURL;

        try {
            Runtime.getRuntime().exec(str);
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public static void openDefaultBrowser(String strURL) {
        try {
            URI uri = new URI(strURL);
            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException var2) {
            var2.printStackTrace();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public static String getCpuSN() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            String property = sc.next();
            String serial = sc.next();
            return serial;
        } catch (Exception var4) {
            return "BFEBFBFF000306C3";
        }
    }

    public static String encode(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte[] b = md.digest();
            StringBuffer buf = new StringBuffer("");

            for(int offset = 0; offset < b.length; ++offset) {
                int i = b[offset];
                if (i < 0) {
                    i += 256;
                }

                if (i < 16) {
                    buf.append("0");
                }

                buf.append(Integer.toHexString(i));
            }

            return buf.toString();
        } catch (Exception var6) {
            var6.printStackTrace();
            return "";
        }
    }

    public static boolean checkSN(String strUser, String strCompany, String strSN) {
        String strCpuSN = getCpuSN();
        String sysSN = encode(strUser + strCompany + strCpuSN);
        return sysSN.equals(strSN);
    }

    public static File[] getFileListByModifyTime(String pathName) {
        File file = new File(pathName);
        File[] fs = file.listFiles();
        Arrays.sort(fs, new Utility.CompratorByLastModified());
        return fs;
    }

    public static boolean isIp(String address) {
        boolean b = false;
        String IP = address.trim();
        if (IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            String[] s = IP.split("\\.");
            if (Integer.parseInt(s[0]) < 255 && Integer.parseInt(s[1]) < 255 && Integer.parseInt(s[2]) < 255 && Integer.parseInt(s[3]) < 255) {
                b = true;
            }
        }

        return b;
    }

    public static String getFormatDoubleString(Double dSrc, int length) {
        if (dSrc == null) {
            return null;
        } else {
            String strFormat = "0.";

            for(int i = 0; i < length; ++i) {
                strFormat = strFormat + "0";
            }

            BigDecimal bigDecimal = (new BigDecimal(dSrc)).setScale(length, 4);
            DecimalFormat df = new DecimalFormat(strFormat);
            return df.format(bigDecimal);
        }
    }

    public static String formatDouble(Double dSrc, int length) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(length);
        nf.setGroupingUsed(false);
        return nf.format(dSrc);
    }

    public static boolean isJcgy() {
        return jcgy;
    }

    public static void setJcgy(boolean jcgy) {
        Utility.jcgy = jcgy;
    }

    public static byte[] readFileByBytes(String fileName) {
        File file = new File(fileName);
        byte[] tmpBytes = new byte[10485760];

        try {
            InputStream in = new FileInputStream(file);
            int totalSize = 0;

            do {
                int tmpByte;
                if ((tmpByte = in.read()) == -1) {
                    in.close();
                    byte[] readBytes = Arrays.copyOf(tmpBytes, totalSize);
                    return readBytes;
                }

                tmpBytes[totalSize] = (byte)tmpByte;
                ++totalSize;
            } while(totalSize < 10485760);

            return null;
        } catch (Exception var7) {
            return tmpBytes;
        }
    }

    static class CompratorByLastModified implements Comparator<File> {
        CompratorByLastModified() {
        }

        public int compare(File f1, File f2) {
            long diff = f1.lastModified() - f2.lastModified();
            if (diff > 0L) {
                return -1;
            } else {
                return diff == 0L ? 0 : 1;
            }
        }

        public boolean equals(Object obj) {
            return true;
        }
    }
}
