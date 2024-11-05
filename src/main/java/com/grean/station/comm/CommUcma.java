//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm;

import com.grean.station.utils.Utility;
import com.hzmx.ucma.ErrCallback;
import com.hzmx.ucma.UCMAService;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommUcma extends CommInter {
  int port = 8085;
  UCMAService ucmaService = null;
  Socket conn = null;
  OutputStream mOutputStream = null;
  InputStream mInputStream = null;
  private CommUcma.RecvThread mRecvThread;
  private CommUcma.SendThread mSendThread;
  private CommUcma.CheckThread checkThread;
  public Logger logger = LoggerFactory.getLogger("net");
  private LinkedList<byte[]> sendByteList = new LinkedList();
  private final Object sendByteLock = new Object();
  private LinkedList<byte[]> recvByteList = new LinkedList();
  private final Object recvByteLock = new Object();

  public LinkedList<byte[]> getSendByteList() {
    return this.sendByteList;
  }

  public LinkedList<byte[]> getRecvByteList() {
    return this.recvByteList;
  }

  public CommUcma(int port) {
    this.port = port;

    try {
      this.ucmaService = UCMAService.getInstance();
      this.ucmaService.setOnAlert(new CommUcma.MyAlertHandler());
    } catch (Exception var3) {
      this.logger.info("UCMA故障，" + var3.getMessage());
    }

  }

  public boolean Open() {
    this.setConnect(false);

    try {
      int delay = this.ucmaService.checkConn(this.port);
      if (delay < 0) {
        this.logger.info(">> 安全通道 [" + this.port + "] 不可用，退出...");
        return false;
      }

      this.logger.info(">> 安全通道 [" + this.port + "] 可用，延迟 " + delay + "ms");
      this.conn = this.ucmaService.connect(this.port);
      this.mOutputStream = this.conn.getOutputStream();
      this.mInputStream = this.conn.getInputStream();
      this.setConnect(true);
      this.mRecvThread = new CommUcma.RecvThread();
      this.mRecvThread.start();
      this.mSendThread = new CommUcma.SendThread();
      this.mSendThread.start();
      this.checkThread = new CommUcma.CheckThread();
      this.checkThread.start();
    } catch (Exception var2) {
      this.logger.info("UCMA故障，" + var2.getMessage());
    }

    return true;
  }

  public boolean Close() {
    try {
      if (this.mInputStream != null) {
        this.mInputStream.close();
      }

      if (this.mOutputStream != null) {
        this.mOutputStream.close();
      }

      if (this.conn != null) {
        this.conn.close();
      }

      this.setConnect(false);
      return true;
    } catch (Exception var2) {
      this.logger.info("UCMA故障，" + var2.getMessage());
      return false;
    }
  }

  public boolean Send(byte[] bSend) {
    synchronized(this.sendByteLock) {
      this.getSendByteList().push(bSend);
      String strSend = Utility.bytesToHexString(bSend, bSend.length);
      this.logger.info("UCMA Send - sendByteList Add: " + strSend);
      return true;
    }
  }

  public byte[] Recv(int chanID) {
    synchronized(this.recvByteLock) {
      if (this.getRecvByteList().size() > 0) {
        byte[] bRecv = (byte[])this.getRecvByteList().poll();
        String strRecv = Utility.bytesToHexString(bRecv, bRecv.length);
        this.logger.info("UCMA Recv - recvByteList Remove: " + strRecv);
        return bRecv;
      } else {
        return null;
      }
    }
  }

  public void clearRecv(int chanID) {
    synchronized(this.recvByteLock) {
      this.getRecvByteList().clear();
      this.logger.info("UCMA clearRecv");
    }
  }

  public byte[] getRespond(byte[] bSend) {
    byte[] bRecv = null;
    boolean bCheck = false;
    String strSend = new String(bSend);
    int startIndex = strSend.indexOf("QN=");
    if (startIndex < 0) {
      return null;
    } else {
      String strQN = strSend.substring(startIndex, startIndex + 20);
      bCheck = false;
      synchronized(this.recvByteLock) {
        int i = 0;

        while(i < this.recvByteList.size()) {
          bRecv = (byte[])this.recvByteList.get(i);
          String strRecv = new String(bRecv);
          if (strRecv.indexOf(strQN) < 0) {
            ++i;
          } else {
            this.recvByteList.remove(i);
            bCheck = true;
            break;
          }
        }
      }

      return bCheck ? bRecv : null;
    }
  }

  public void setRecvListener(RecvListener listener) {
    this.recvListener = listener;
  }

  class MyAlertHandler implements ErrCallback {
    MyAlertHandler() {
    }

    public void onAlert(long ts, int code) {
      System.err.printf(">> UCMA Alert time %d code [%d].\n", ts, code);
    }
  }

  private class CheckThread extends Thread {
    private CheckThread() {
    }

    public void run() {
      while(true) {
        try {
          if (!this.isInterrupted()) {
            Thread.sleep(300000L);
            int delay = CommUcma.this.ucmaService.checkConn(CommUcma.this.port);
            if (delay < 0) {
              CommUcma.this.logger.info(">> 安全通道 [" + CommUcma.this.port + "] 不可用，退出...");
              CommUcma.this.setConnect(false);
              CommUcma.this.mOutputStream = null;
              CommUcma.this.mInputStream = null;
              continue;
            }

            if (!CommUcma.this.isConnect()) {
              CommUcma.this.logger.info(">> 安全通道 [" + CommUcma.this.port + "] 可用，延迟 " + delay + "ms");
              CommUcma.this.conn = CommUcma.this.ucmaService.connect(CommUcma.this.port);
              CommUcma.this.mOutputStream = CommUcma.this.conn.getOutputStream();
              CommUcma.this.mInputStream = CommUcma.this.conn.getInputStream();
              CommUcma.this.setConnect(true);
            }
            continue;
          }
        } catch (Exception var2) {
          CommUcma.this.logger.info("UCMA Check故障，" + var2.getMessage());
        }

        return;
      }
    }
  }

  private class SendThread extends Thread {
    private SendThread() {
    }

    public void run() {
      while(!this.isInterrupted()) {
        try {
          Thread.sleep((long)CommUcma.this.getSendDelay());
          if (CommUcma.this.mOutputStream != null) {
            synchronized(CommUcma.this.sendByteLock) {
              if (!CommUcma.this.getSendByteList().isEmpty()) {
                byte[] buf = (byte[])CommUcma.this.getSendByteList().poll();

                try {
                  CommUcma.this.mOutputStream.write(buf);
                  String sTmp = new String(buf);
                  sTmp = "Send: " + sTmp;
                  CommUcma.this.logger.info("UCMA " + sTmp);
                } catch (Exception var5) {
                  var5.printStackTrace();
                }
              }
            }
          }
        } catch (InterruptedException var7) {
          CommUcma.this.logger.info("UCMA Send Exception: " + var7.getMessage());
        }
      }

    }
  }

  private class RecvThread extends Thread {
    byte[] buf;
    byte[] bRecv;
    int bLen;

    private RecvThread() {
      this.buf = new byte[4096];
      this.bRecv = new byte[8];
      this.bLen = 0;
    }

    public void run() {
      super.run();

      while(!this.isInterrupted()) {
        try {
          Thread.sleep(50L);
          if (CommUcma.this.mInputStream != null) {
            int size = CommUcma.this.mInputStream.read(this.buf);
            if (size > 0) {
              byte[] dest = new byte[size];
              System.arraycopy(this.buf, 0, dest, 0, size);
              String sTmp = new String(dest);
              CommUcma.this.logger.info("UCMA Recv: " + sTmp);
              if (CommUcma.this.recvListener != null) {
                CommUcma.this.recvListener.OnRecvEvent(dest);
              }

              synchronized(CommUcma.this.recvByteLock) {
                if (CommUcma.this.recvByteList.size() > 255) {
                  CommUcma.this.recvByteList.clear();
                }

                CommUcma.this.recvByteList.offer(dest);
              }
            }
          }
        } catch (Exception var7) {
          CommUcma.this.logger.info("UCMA Recv Exception: " + var7.getMessage());
        }
      }

    }
  }
}
