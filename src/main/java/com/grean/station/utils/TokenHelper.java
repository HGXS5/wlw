//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.utils;

import java.util.UUID;

public class TokenHelper {
    public TokenHelper() {
    }

    public static String getGUID() {
        String guid = UUID.randomUUID().toString();
        return "a" + guid.replace("-", "");
    }
}
