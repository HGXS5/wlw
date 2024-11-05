//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm;

import com.fazecast.jSerialComm.SerialPort;
import com.grean.station.utils.Utility;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommSerial extends CommInter {
  private int comPort = 1;
  private int comDataBits = 8;
  private int comBaudRate = 9600;
  private int comStopBits = 1;
  private int comParity = 110;
  private int comFlowControl = 0;
  private SerialPort mSerialPort;
  private OutputStream mOutputStream;
  private InputStream mInputStream;
  private CommSerial.RecvThread mRecvThread;
  private CommSerial.SendThread mSendThread;
  private LinkedList<byte[]> sendByteList = new LinkedList();
  private final Object sendByteLock = new Object();
  private LinkedList<byte[]>[] recvByteLists = new LinkedList[16];
  private final Object[] recvByteLocks = new Object[16];
  public Logger logger = LoggerFactory.getLogger("serial");

  public LinkedList<byte[]> getSendByteList() {
    return this.sendByteList;
  }

  public LinkedList<byte[]> getRecvByteList(int recvByteID) {
    return this.recvByteLists[recvByteID];
  }

  public void setRecvListener(RecvListener listener) {
    this.recvListener = listener;
  }

  public CommSerial(int port, int baudRate, int dataBits, int stopBits, int parity) {
    this.comPort = port;
    this.comBaudRate = baudRate;
    this.comDataBits = dataBits;
    this.comStopBits = stopBits;
    this.comParity = parity;

    for(int i = 0; i < this.recvByteLocks.length; ++i) {
      this.recvByteLists[i] = new LinkedList();
      this.recvByteLocks[i] = new Object();
    }

  }

  public CommSerial(CommSerial.Builder builder) {
    this.comPort = builder.comPort;
    this.comBaudRate = builder.comBaudRate;
    this.comDataBits = builder.comDataBits;
    this.comStopBits = builder.comStopBits;
    this.comParity = builder.comParity;
    this.comFlowControl = builder.comFlowControl;

    for(int i = 0; i < this.recvByteLocks.length; ++i) {
      this.recvByteLists[i] = new LinkedList();
      this.recvByteLocks[i] = new Object();
    }

  }

  public boolean Open() {
    if (this.comPort < 1) {
      this.logger.info("Open Com" + this.comPort + " Failed");
      this.setConnect(false);
      return false;
    } else {
      int i;
      try {
        if (Utility.isWindows()) {
          this.mSerialPort = SerialPort.getCommPort("COM" + this.comPort);
        } else {
          this.mSerialPort = SerialPort.getCommPort("ttyS" + (this.comPort - 1));
        }

        this.mSerialPort.setBaudRate(this.comBaudRate);
        this.mSerialPort.setNumDataBits(this.comDataBits);
        this.mSerialPort.setNumStopBits(this.comStopBits);
        this.mSerialPort.setParity(this.comParity);
        if (this.comFlowControl < 0) {
          i = this.comFlowControl * -1;
          if (i % 2 == 1) {
            this.mSerialPort.clearDTR();
          } else {
            this.mSerialPort.setDTR();
          }

          i /= 2;
          if (i % 2 == 1) {
            this.mSerialPort.clearRTS();
          } else {
            this.mSerialPort.setRTS();
          }
        } else if (this.comFlowControl > 0) {
          this.mSerialPort.setFlowControl(this.comFlowControl);
        }

        if (!this.mSerialPort.openPort()) {
          this.logger.info("Open Com" + this.comPort + " Failed");
          this.setConnect(false);
          return false;
        }

        this.mInputStream = this.mSerialPort.getInputStream();
        this.mOutputStream = this.mSerialPort.getOutputStream();
        this.setConnect(true);
      } catch (Exception var2) {
        this.logger.info("Open Com" + this.comPort + " Failed");
        var2.printStackTrace();
        this.setConnect(false);
        return false;
      }

      this.getSendByteList().clear();

      for(i = 0; i < this.recvByteLocks.length; ++i) {
        this.getRecvByteList(i).clear();
      }

      this.mRecvThread = new CommSerial.RecvThread();
      this.mRecvThread.start();
      this.mSendThread = new CommSerial.SendThread();
      this.mSendThread.start();
      return true;
    }
  }

  public boolean Close() {
    this.mRecvThread.interrupt();
    this.mSendThread.interrupt();
    if (this.mInputStream != null) {
      try {
        this.mInputStream.close();
        this.mInputStream = null;
      } catch (Exception var3) {
        var3.printStackTrace();
        return false;
      }
    }

    if (this.mOutputStream != null) {
      try {
        this.mOutputStream.close();
        this.mOutputStream = null;
      } catch (Exception var2) {
        var2.printStackTrace();
        return false;
      }
    }

    this.getSendByteList().clear();

    for(int i = 0; i < this.recvByteLocks.length; ++i) {
      this.getRecvByteList(i).clear();
    }

    if (this.mSerialPort != null) {
      this.mSerialPort.closePort();
    }

    this.setConnect(false);
    return true;
  }

  public boolean Send(byte[] bSend) {
    synchronized(this.sendByteLock) {
      this.getSendByteList().push(bSend);
      String strSend = Utility.bytesToHexString(bSend, bSend.length);
      this.logger.info("COM" + this.comPort + " Send - sendByteList Add: " + strSend);
      return true;
    }
  }

  public byte[] Recv(int chanID) {
    if (chanID >= 0 && chanID < this.recvByteLocks.length) {
      synchronized(this.recvByteLocks[chanID]) {
        if (this.getRecvByteList(chanID).size() > 0) {
          byte[] bRecv = (byte[])this.getRecvByteList(chanID).poll();
          String strRecv = Utility.bytesToHexString(bRecv, bRecv.length);
          this.logger.info("COM" + this.comPort + " Recv - recvByteList Remove: " + strRecv);
          return bRecv;
        }
      }
    }

    return null;
  }

  public void clearRecv(int chanID) {
    if (chanID >= 0 && chanID < this.recvByteLocks.length) {
      synchronized(this.recvByteLocks[chanID]) {
        this.getRecvByteList(chanID).clear();
        this.logger.info("COM" + this.comPort + " clearRecv");
      }
    }

  }

  public byte[] getRespond(byte[] bSend) {
    return null;
  }

  public int getComPort() {
    return this.comPort;
  }

  public void setComPort(int comPort) {
    this.comPort = comPort;
  }

  public int getComDataBits() {
    return this.comDataBits;
  }

  public void setComDataBits(int comDataBits) {
    this.comDataBits = comDataBits;
  }

  public int getComBaudRate() {
    return this.comBaudRate;
  }

  public void setComBaudRate(int comBaudRate) {
    this.comBaudRate = comBaudRate;
  }

  public int getComStopBits() {
    return this.comStopBits;
  }

  public void setComStopBits(int comStopBits) {
    this.comStopBits = comStopBits;
  }

  public int getComParity() {
    return this.comParity;
  }

  public void setComParity(int comParity) {
    this.comParity = comParity;
  }

  private class SendThread extends Thread {
    private SendThread() {
    }

    public void run() {
      while(!this.isInterrupted()) {
        try {
          Thread.sleep((long)CommSerial.this.getSendDelay());
          if (CommSerial.this.mOutputStream != null) {
            synchronized(CommSerial.this.sendByteLock) {
              if (!CommSerial.this.getSendByteList().isEmpty()) {
                byte[] buf = (byte[])CommSerial.this.getSendByteList().poll();

                try {
                  CommSerial.this.mOutputStream.write(buf);
                  String sTmp = Utility.bytesToHexString(buf, buf.length);
                  sTmp = "Send: " + sTmp;
                  CommSerial.this.logger.info("COM" + CommSerial.this.comPort + " " + sTmp);
                } catch (Exception var5) {
                  var5.printStackTrace();
                }
              }
            }
          }
        } catch (InterruptedException var7) {
          CommSerial.this.logger.info("COM" + CommSerial.this.comPort + " Send Exception: " + var7.getMessage());
        }
      }

    }
  }

  private class RecvThread extends Thread {
    byte[] buf;
    byte[] bRecv;
    int bLen;

    private RecvThread() {
      this.buf = new byte[512];
      this.bRecv = new byte[8];
      this.bLen = 0;
    }

    public void run() {
      super.run();

      while(!this.isInterrupted()) {
        try {
          Thread.sleep(50L);
          if (CommSerial.this.mInputStream != null) {
            int size = CommSerial.this.mInputStream.read(this.buf);
            if (size > 0) {
              byte[] dest = new byte[size];
              System.arraycopy(this.buf, 0, dest, 0, size);
              String sTmp = Utility.bytesToHexString(this.buf, size);
              CommSerial.this.logger.info("COM" + CommSerial.this.comPort + " Recv: " + sTmp);
              int i;
              if (CommSerial.this.recvListener != null && dest.length <= 8) {
                for(i = 0; i < dest.length && this.bLen <= 7; ++i) {
                  this.bRecv[this.bLen] = dest[i];
                  ++this.bLen;
                }

                if (this.bLen == 8) {
                  this.bLen = 0;
                  CommSerial.this.recvListener.OnRecvEvent(this.bRecv);
                }
              }

              for(i = 0; i < CommSerial.this.recvByteLocks.length; ++i) {
                synchronized(CommSerial.this.recvByteLocks[i]) {
                  if (CommSerial.this.getRecvByteList(i).size() > 255) {
                    CommSerial.this.getRecvByteList(i).clear();
                  }

                  CommSerial.this.getRecvByteList(i).offer(dest);
                }
              }
            }
          }
        } catch (Exception var8) {
        }
      }

    }
  }

  public static class Builder {
    private int comPort = 1;
    private int comDataBits = 8;
    private int comBaudRate = 9600;
    private int comStopBits = 1;
    private int comParity = 110;
    private int comFlowControl = 0;

    public Builder(int comPort) {
      this.comPort = comPort;
    }

    public CommSerial.Builder dataBits(int val) {
      this.comDataBits = val;
      return this;
    }

    public CommSerial.Builder baudRate(int val) {
      this.comBaudRate = val;
      return this;
    }

    public CommSerial.Builder stopBits(int val) {
      this.comStopBits = val;
      return this;
    }

    public CommSerial.Builder parity(int val) {
      this.comParity = val;
      return this;
    }

    public CommSerial.Builder flowControl(int val) {
      this.comFlowControl = val;
      return this;
    }

    public CommSerial build() {
      return new CommSerial(this);
    }
  }
}
