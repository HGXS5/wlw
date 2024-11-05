//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
    private static int BUFFER = 1024;

    public ZipUtil() {
    }

    public static void main(String[] args) {
        unzip("c:\\test.zip", "c:\\");

        try {
            zip("E:\\ftl", "E:\\test.zip");
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public static String unzip(String filePath, String zipDir) {
        String name = "";

        try {
            BufferedOutputStream dest = null;
            BufferedInputStream is = null;
            ZipFile zipfile = new ZipFile(filePath);
            Enumeration dir = zipfile.entries();

            ZipEntry entry;
            while(dir.hasMoreElements()) {
                entry = (ZipEntry)dir.nextElement();
                if (entry.isDirectory()) {
                    name = entry.getName();
                    name = name.substring(0, name.length() - 1);
                    File fileObject = new File(zipDir + name);
                    fileObject.mkdir();
                }
            }

            Enumeration e = zipfile.entries();

            while(true) {
                do {
                    if (!e.hasMoreElements()) {
                        return name;
                    }

                    entry = (ZipEntry)e.nextElement();
                } while(entry.isDirectory());

                is = new BufferedInputStream(zipfile.getInputStream(entry));
                byte[] dataByte = new byte[BUFFER];
                FileOutputStream fos = new FileOutputStream(zipDir + entry.getName());
                dest = new BufferedOutputStream(fos, BUFFER);

                int count;
                while((count = is.read(dataByte, 0, BUFFER)) != -1) {
                    dest.write(dataByte, 0, count);
                }

                dest.flush();
                dest.close();
                is.close();
            }
        } catch (Exception var12) {
            var12.printStackTrace();
            return name;
        }
    }

    public static Boolean zip(String inputFileName, String zipFileName) throws Exception {
        zip(zipFileName, new File(inputFileName));
        return true;
    }

    private static void zip(String zipFileName, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        zip(out, inputFile, "");
        out.flush();
        out.close();
    }

    private static void zip(ZipOutputStream out, File f, String base) throws Exception {
        int i;
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            out.putNextEntry(new ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";

            for(i = 0; i < fl.length; ++i) {
                zip(out, fl[i], base + fl[i].getName());
            }
        } else {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream in = new FileInputStream(f);

            while((i = in.read()) != -1) {
                out.write(i);
            }

            in.close();
        }

    }
}
