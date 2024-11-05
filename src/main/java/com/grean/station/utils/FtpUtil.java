//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpUtil {
    private static Logger logger = LoggerFactory.getLogger("ftp");

    public FtpUtil() {
    }

    public FTPClient getFTPClient(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword) {
        FTPClient ftp = null;

        try {
            ftp = new FTPClient();
            ftp.connect(ftpHost, ftpPort);
            ftp.login(ftpUserName, ftpPassword);
            ftp.setConnectTimeout(50000);
            ftp.setControlEncoding("UTF-8");
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                logger.info("未连接到FTP，用户名或密码错误");
                ftp.disconnect();
            } else {
                logger.info("FTP连接成功");
            }

            return ftp;
        } catch (SocketException var7) {
            var7.printStackTrace();
            logger.info("FTP的IP地址可能错误，请正确配置");
            return null;
        } catch (IOException var8) {
            var8.printStackTrace();
            logger.info("FTP的端口错误,请正确配置");
            return null;
        }
    }

    public boolean closeFTP(FTPClient ftp) {
        try {
            ftp.logout();
        } catch (Exception var11) {
            logger.error("FTP关闭失败");
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException var10) {
                    logger.error("FTP关闭失败");
                }
            }

        }

        return false;
    }

    public boolean downLoadFTP(FTPClient ftp, String filePath, String fileName, String downPath) {
        boolean flag = false;

        try {
            ftp.changeWorkingDirectory(filePath);
            ftp.enterLocalPassiveMode();
            FTPFile[] files = ftp.listFiles();
            FTPFile[] var7 = files;
            int var8 = files.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                FTPFile file = var7[var9];
                if (file.getName().equals(fileName)) {
                    File downFile = new File(downPath + File.separator + file.getName());
                    OutputStream out = new FileOutputStream(downFile);
                    flag = ftp.retrieveFile(new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"), out);
                    out.flush();
                    out.close();
                    if (flag) {
                        logger.info("下载成功");
                    } else {
                        logger.error("下载失败");
                    }
                }
            }
        } catch (Exception var13) {
            logger.error("下载失败");
        }

        return flag;
    }

    public boolean uploadFile(FTPClient ftp, String filePath, String ftpPath) {
        boolean flag = false;
        FileInputStream in = null;

        try {
            ftp.enterLocalPassiveMode();
            ftp.setFileType(2);
            if (!ftp.changeWorkingDirectory(ftpPath)) {
                ftp.makeDirectory(ftpPath);
            }

            ftp.changeWorkingDirectory(ftpPath);
            File file = new File(filePath);
            in = new FileInputStream(file);
            String tempName;
            if (ftpPath.length() > 1) {
                tempName = ftpPath + File.separator + file.getName();
            } else {
                tempName = ftpPath + file.getName();
            }

            String fileName = new String(tempName.getBytes("UTF-8"), "ISO-8859-1");
            flag = ftp.storeFile(fileName, in);
            if (flag) {
                logger.info("上传成功");
            } else {
                logger.error("上传失败");
            }
        } catch (Exception var17) {
            logger.error("上传异常1 " + var17.toString());
        } finally {
            try {
                in.close();
            } catch (IOException var16) {
                logger.error("上传异常2 " + var16.toString());
            }

        }

        return flag;
    }

    public boolean copyFile(FTPClient ftp, String olePath, String newPath, String fileName) {
        boolean flag = false;

        try {
            ftp.changeWorkingDirectory(olePath);
            ftp.enterLocalPassiveMode();
            FTPFile[] files = ftp.listFiles();
            ByteArrayInputStream in = null;
            ByteArrayOutputStream out = null;
            FTPFile[] var9 = files;
            int var10 = files.length;

            for(int var11 = 0; var11 < var10; ++var11) {
                FTPFile file = var9[var11];
                if (file.getName().equals(fileName)) {
                    out = new ByteArrayOutputStream();
                    ftp.retrieveFile(new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"), out);
                    in = new ByteArrayInputStream(out.toByteArray());
                    ftp.makeDirectory(newPath);
                    ftp.setFileType(2);
                    flag = ftp.storeFile(newPath + File.separator + new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"), in);
                    out.flush();
                    out.close();
                    in.close();
                    if (flag) {
                        logger.info("转存成功");
                    } else {
                        logger.error("复制失败");
                    }
                }
            }
        } catch (Exception var13) {
            logger.error("复制失败");
        }

        return flag;
    }

    public boolean moveFile(FTPClient ftp, String oldPath, String newPath) {
        boolean flag = false;

        try {
            ftp.changeWorkingDirectory(oldPath);
            ftp.enterLocalPassiveMode();
            FTPFile[] files = ftp.listFiles();
            if (!ftp.changeWorkingDirectory(newPath)) {
                ftp.makeDirectory(newPath);
            }

            ftp.changeWorkingDirectory(oldPath);
            FTPFile[] var6 = files;
            int var7 = files.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                FTPFile file = var6[var8];
                flag = ftp.rename(new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"), newPath + File.separator + new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"));
                if (flag) {
                    logger.info(file.getName() + "移动成功");
                } else {
                    logger.error(file.getName() + "移动失败");
                }
            }
        } catch (Exception var10) {
            var10.printStackTrace();
            logger.error("移动文件失败");
        }

        return flag;
    }

    public boolean deleteByFolder(FTPClient ftp, String FtpFolder) {
        boolean flag = false;

        try {
            ftp.changeWorkingDirectory(new String(FtpFolder.getBytes("UTF-8"), "ISO-8859-1"));
            ftp.enterLocalPassiveMode();
            FTPFile[] files = ftp.listFiles();
            FTPFile[] var5 = files;
            int var6 = files.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                FTPFile file = var5[var7];
                if (file.isFile()) {
                    ftp.deleteFile(new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"));
                }

                if (file.isDirectory()) {
                    String childPath;
                    if (FtpFolder.length() > 1) {
                        childPath = FtpFolder + File.separator + file.getName();
                    } else {
                        childPath = FtpFolder + file.getName();
                    }

                    this.deleteByFolder(ftp, childPath);
                }
            }

            flag = ftp.removeDirectory(new String(FtpFolder.getBytes("UTF-8"), "ISO-8859-1"));
            if (flag) {
                logger.info(FtpFolder + "文件夹删除成功");
            } else {
                logger.error(FtpFolder + "文件夹删除成功");
            }
        } catch (Exception var10) {
            var10.printStackTrace();
            logger.error("删除失败");
        }

        return flag;
    }

    public boolean readFileByFolder(FTPClient ftp, String folderPath) {
        boolean flage = false;

        try {
            ftp.changeWorkingDirectory(new String(folderPath.getBytes("UTF-8"), "ISO-8859-1"));
            ftp.enterLocalPassiveMode();
            FTPFile[] files = ftp.listFiles();
            InputStream in = null;
            BufferedReader reader = null;
            FTPFile[] var7 = files;
            int var8 = files.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                FTPFile file = var7[var9];
                String path;
                if (file.isFile()) {
                    path = file.getName();
                    if (path.endsWith(".txt")) {
                        in = ftp.retrieveFileStream(new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"));
                        reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                        StringBuffer buffer = new StringBuffer();

                        String temp;
                        while((temp = reader.readLine()) != null) {
                            buffer.append(temp);
                        }

                        if (reader != null) {
                            reader.close();
                        }

                        if (in != null) {
                            in.close();
                        }

                        ftp.completePendingCommand();
                        System.out.println(buffer.toString());
                    }
                }

                if (file.isDirectory()) {
                    path = folderPath + File.separator + file.getName();
                    this.readFileByFolder(ftp, path);
                }
            }
        } catch (Exception var14) {
            var14.printStackTrace();
            logger.error("文件解析失败");
        }

        return flage;
    }

    public static void main(String[] args) {
        FtpUtil test = new FtpUtil();
        FTPClient ftp = test.getFTPClient("192.168.199.172", 21, "user", "password");
        test.readFileByFolder(ftp, "/");
        test.closeFTP(ftp);
        System.exit(0);
    }
}
