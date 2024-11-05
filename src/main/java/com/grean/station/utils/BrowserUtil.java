//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.utils;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class BrowserUtil {
    public BrowserUtil() {
    }

    public static boolean browse(URI uri) {
        if (openSystemSpecific(uri.toString())) {
            return true;
        } else {
            return browseDESKTOP(uri);
        }
    }

    public static boolean open(File file) {
        if (openSystemSpecific(file.getPath())) {
            return true;
        } else {
            return openDESKTOP(file);
        }
    }

    public static boolean edit(File file) {
        if (openSystemSpecific(file.getPath())) {
            return true;
        } else {
            return editDESKTOP(file);
        }
    }

    private static boolean openSystemSpecific(String what) {
        BrowserUtil.EnumOS os = getOs();
        if (os.isLinux()) {
            if (runCommand("kde-open", "%s", what)) {
                return true;
            }

            if (runCommand("gnome-open", "%s", what)) {
                return true;
            }

            if (runCommand("xdg-open", "%s", what)) {
                return true;
            }
        }

        if (os.isMac() && runCommand("open", "%s", what)) {
            return true;
        } else {
            return os.isWindows() && runCommand("explorer", "%s", what);
        }
    }

    private static boolean browseDESKTOP(URI uri) {
        try {
            if (!Desktop.isDesktopSupported()) {
                return false;
            } else if (!Desktop.getDesktop().isSupported(Action.BROWSE)) {
                return false;
            } else {
                Desktop.getDesktop().browse(uri);
                return true;
            }
        } catch (Throwable var2) {
            return false;
        }
    }

    private static boolean openDESKTOP(File file) {
        try {
            if (!Desktop.isDesktopSupported()) {
                return false;
            } else if (!Desktop.getDesktop().isSupported(Action.OPEN)) {
                return false;
            } else {
                Desktop.getDesktop().open(file);
                return true;
            }
        } catch (Throwable var2) {
            return false;
        }
    }

    private static boolean editDESKTOP(File file) {
        try {
            if (!Desktop.isDesktopSupported()) {
                return false;
            } else if (!Desktop.getDesktop().isSupported(Action.EDIT)) {
                return false;
            } else {
                Desktop.getDesktop().edit(file);
                return true;
            }
        } catch (Throwable var2) {
            return false;
        }
    }

    private static boolean runCommand(String command, String args, String file) {
        String[] parts = prepareCommand(command, args, file);

        try {
            Process p = Runtime.getRuntime().exec(parts);
            if (p == null) {
                return false;
            } else {
                try {
                    int retval = p.exitValue();
                    return retval == 0 ? false : false;
                } catch (IllegalThreadStateException var6) {
                    return true;
                }
            }
        } catch (IOException var7) {
            return false;
        }
    }

    private static String[] prepareCommand(String command, String args, String file) {
        List<String> parts = new ArrayList();
        parts.add(command);
        if (args != null) {
            String[] var4 = args.split(" ");
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String s = var4[var6];
                s = String.format(s, file);
                parts.add(s.trim());
            }
        }

        return (String[])parts.toArray(new String[parts.size()]);
    }

    public static BrowserUtil.EnumOS getOs() {
        String s = System.getProperty("os.name").toLowerCase();
        if (s.contains("win")) {
            return BrowserUtil.EnumOS.windows;
        } else if (s.contains("mac")) {
            return BrowserUtil.EnumOS.macos;
        } else if (s.contains("solaris")) {
            return BrowserUtil.EnumOS.solaris;
        } else if (s.contains("sunos")) {
            return BrowserUtil.EnumOS.solaris;
        } else if (s.contains("linux")) {
            return BrowserUtil.EnumOS.linux;
        } else {
            return s.contains("unix") ? BrowserUtil.EnumOS.linux : BrowserUtil.EnumOS.unknown;
        }
    }

    public static enum EnumOS {
        linux,
        macos,
        solaris,
        unknown,
        windows;

        private EnumOS() {
        }

        public boolean isLinux() {
            return this == linux || this == solaris;
        }

        public boolean isMac() {
            return this == macos;
        }

        public boolean isWindows() {
            return this == windows;
        }
    }
}
