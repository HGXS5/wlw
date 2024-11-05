//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberDealUtil {
    public NumberDealUtil() {
    }

    public static String trim0(boolean isFormat, double num) {
        NumberFormat nf = NumberFormat.getInstance();
        if (!isFormat) {
            nf.setGroupingUsed(false);
        }

        String result = nf.format(num);
        return result;
    }

    public static String trim0(boolean isFormat, double num, int fractionDigit) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(fractionDigit);
        if (!isFormat) {
            nf.setGroupingUsed(false);
        }

        String result = nf.format(num);
        return result;
    }

    public static String add0Format(double num, int integerDigit, int fractionDigit) {
        StringBuilder rule = new StringBuilder();
        int i;
        if (integerDigit <= 0) {
            rule.append("#");
        } else {
            for(i = 0; i < integerDigit; ++i) {
                rule.append("0");
            }
        }

        if (fractionDigit > 0) {
            rule.append(".");

            for(i = 0; i < fractionDigit; ++i) {
                rule.append("0");
            }
        }

        DecimalFormat df = new DecimalFormat(rule.toString());
        return df.format(num);
    }

    public static String fractionDigitFormat(double num, int fractionDigit) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(fractionDigit);
        nf.setGroupingUsed(false);
        return nf.format(num);
    }
}
