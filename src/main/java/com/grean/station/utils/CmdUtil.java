//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CmdUtil {
    public CmdUtil() {
    }

    public static String runConsoleCommand(String command) {
        StringBuffer buffer = new StringBuffer();
        InputStream inputStream = null;
        Process p = null;

        try {
            p = Runtime.getRuntime().exec(command);
            inputStream = new BufferedInputStream(p.getInputStream());

            while(true) {
                int c = inputStream.read();
                if (c == -1) {
                    break;
                }

                buffer.append((char)c);
            }
        } catch (IOException var13) {
            var13.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException var12) {
                    var12.printStackTrace();
                }
            }

            if (p != null) {
                p.destroy();
                p = null;
            }

        }

        return buffer.toString();
    }
}
