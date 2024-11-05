//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm;

import com.grean.station.utils.FtpUtil;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;

public class CommFtpXA extends CommInter {
  FtpUtil ftpUtil;
  FTPClient ftpClient;
  String ftpHost;
  int ftpPort;
  String ftpUserName;
  String ftpPassword;
  String filePath;
  String ftpPath;
  String ftpAreaCode;
  String ftpStationCode;

  public CommFtpXA(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword, String filePath, String ftpPath, String ftpAreaCode, String ftpStationCode) {
    this.ftpHost = ftpHost;
    this.ftpPort = ftpPort;
    this.ftpUserName = ftpUserName;
    this.ftpPassword = ftpPassword;
    this.filePath = filePath;
    this.ftpPath = ftpPath;
    this.ftpAreaCode = ftpAreaCode;
    this.ftpStationCode = ftpStationCode;
    this.ftpUtil = new FtpUtil();
    this.setConnect(true);
  }

  public boolean Open() {
    this.setConnect(true);
    this.ftpClient = this.ftpUtil.getFTPClient(this.ftpHost, this.ftpPort, this.ftpUserName, this.ftpPassword);
    return this.ftpClient != null;
  }

  public boolean Close() {
    if (this.ftpClient == null) {
      return false;
    } else {
      this.ftpUtil.closeFTP(this.ftpClient);
      this.ftpClient = null;
      return true;
    }
  }

  public boolean Send(byte[] bSend) {
    boolean bResult = false;
    if (bSend == null) {
      return bResult;
    } else {
      this.Open();
      if (this.ftpClient != null && this.ftpClient.isConnected()) {
        try {
          String strSend = new String(bSend);
          String strDate = strSend.substring(0, 14);
          strSend = strSend.substring(14, strSend.length());
          String strName = "WQ_" + strDate + "_" + this.ftpAreaCode + "_" + this.ftpStationCode + ".xml";
          FileUtils.writeStringToFile(new File(this.filePath + strName), strSend, "UTF-8", false);

          for(int i = 0; i < 3; ++i) {
            bResult = this.ftpUtil.uploadFile(this.ftpClient, this.filePath + strName, this.ftpPath);
            if (bResult) {
              break;
            }

            Thread.sleep((long)((i + 1) * '\uea60'));
            this.Open();
          }
        } catch (Exception var7) {
          System.out.println("Ftp Send Failed.");
        }
      }

      this.Close();
      return bResult;
    }
  }

  public byte[] Recv(int chanID) {
    return new byte[0];
  }

  public void clearRecv(int chanID) {
  }

  public byte[] getRespond(byte[] bSend) {
    return null;
  }

  public void setRecvListener(RecvListener listener) {
  }
}
