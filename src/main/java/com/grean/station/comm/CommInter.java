//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm;

public abstract class CommInter {
  private boolean Connect = false;
  private boolean serialQuery = false;
  private int serialDelay = 1000;
  private int sendDelay = 200;
  public RecvListener recvListener;

  public CommInter() {
  }

  public abstract boolean Open();

  public abstract boolean Close();

  public abstract boolean Send(byte[] var1);

  public abstract byte[] Recv(int var1);

  public abstract void clearRecv(int var1);

  public abstract byte[] getRespond(byte[] var1);

  public abstract void setRecvListener(RecvListener var1);

  public boolean isConnect() {
    return this.Connect;
  }

  public void setConnect(boolean connect) {
    this.Connect = connect;
  }

  public int getSendDelay() {
    return this.sendDelay;
  }

  public void setSendDelay(int sendDelay) {
    this.sendDelay = sendDelay;
  }

  public boolean isSerialQuery() {
    return this.serialQuery;
  }

  public void setSerialQuery(boolean serialQuery) {
    this.serialQuery = serialQuery;
  }

  public int getSerialDelay() {
    return this.serialDelay;
  }

  public void setSerialDelay(int serialDelay) {
    this.serialDelay = serialDelay;
  }

  public void clearSend() {
  }
}
