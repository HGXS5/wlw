//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.http.HttpServletResponse;

public class FileDownloadUtil {
    public FileDownloadUtil() {
    }

    public HttpServletResponse download(String path, HttpServletResponse response) {
        try {
            File file = new File(path);
            String filename = file.getName();
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException var9) {
            var9.printStackTrace();
        }

        return response;
    }

    public void downloadLocal(HttpServletResponse response) throws FileNotFoundException {
        String fileName = "Operator.doc".toString();
        InputStream inStream = new FileInputStream("c:/Operator.doc");
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        byte[] b = new byte[100];

        try {
            int len;
            while((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }

            inStream.close();
        } catch (IOException var7) {
            var7.printStackTrace();
        }

    }

    public static void downloadNet(String sourceName, String targetName) {
        int bytesum = 0;
        boolean var3 = false;

        try {
            URL url = new URL(sourceName);
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(targetName);
            byte[] buffer = new byte[4096];

            int byteread;
            while((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }

            fs.close();
        } catch (IOException var9) {
            var9.printStackTrace();
        }

    }
}
