//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm.netclientmodbustcp;

import com.grean.station.comm.CommInter;
import com.grean.station.comm.RecvListener;
import com.grean.station.utils.Utility;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommNetClientModbusTcp extends CommInter {
  private EventLoopGroup workGroup = new NioEventLoopGroup();
  private InboundHandlerModbusTcp myHandler;
  private String remoteIP;
  private int remotePort;
  private int devAddress;
  private CommNetClientModbusTcp.SendThread mSendThread;
  private List<byte[]> recvByteList = new ArrayList();
  private List<byte[]> sendByteList = new ArrayList();
  private final Object recvByteLock = new Object();
  private final Object sendByteLock = new Object();
  public Logger logger = LoggerFactory.getLogger("net");

  public String getRemoteIP() {
    return this.remoteIP;
  }

  public void setRemoteIP(String remoteIP) {
    this.remoteIP = remoteIP;
  }

  public int getRemotePort() {
    return this.remotePort;
  }

  public void setRemotePort(int remotePort) {
    this.remotePort = remotePort;
  }

  public void setRecvListener(RecvListener listener) {
    this.recvListener = listener;
  }

  public CommNetClientModbusTcp(String ip, int port, int address) {
    this.remoteIP = ip;
    this.remotePort = port;
    this.devAddress = address;
  }

  public Bootstrap createBootstrap(Bootstrap bootstrap, EventLoopGroup eventLoop) {
    if (bootstrap != null) {
      final InboundHandlerModbusTcp handler = new InboundHandlerModbusTcp(this);
      this.myHandler = handler;
      this.myHandler.setRecvListener(new CommNetClientModbusTcp.NetRecvListener());
      bootstrap.group(eventLoop);
      bootstrap.channel(NioSocketChannel.class);
      bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
      bootstrap.handler(new ChannelInitializer<SocketChannel>() {
        protected void initChannel(SocketChannel socketChannel) throws Exception {
          socketChannel.pipeline().addLast(new ChannelHandler[]{new ModbusTcpDecoder(CommNetClientModbusTcp.this.devAddress)});
          socketChannel.pipeline().addLast(new ChannelHandler[]{handler});
        }
      });
      bootstrap.remoteAddress(this.remoteIP, this.remotePort);
      bootstrap.connect().addListener(new ConnectionListenerModbusTcp(this));
    }

    return bootstrap;
  }

  public boolean Open() {
    this.createBootstrap(new Bootstrap(), this.workGroup);
    this.mSendThread = new CommNetClientModbusTcp.SendThread();
    this.mSendThread.start();
    return false;
  }

  public boolean Close() {
    this.mSendThread.interrupt();
    synchronized(this.recvByteLock) {
      this.recvByteList.clear();
    }

    this.workGroup.shutdownGracefully();
    return false;
  }

  public boolean Send(byte[] bSend) {
    synchronized(this.sendByteLock) {
      if (this.sendByteList.size() > 20) {
        this.sendByteList.clear();
      }

      this.sendByteList.add(bSend);
      String strSend = Utility.bytesToHexString(bSend, bSend.length);
      this.logger.info("Net Client Send - sendByteList Add: " + strSend);
      return true;
    }
  }

  public byte[] Recv(int chanID) {
    synchronized(this.recvByteLock) {
      if (this.recvByteList.size() > 0) {
        byte[] bRecv = (byte[])this.recvByteList.get(0);
        this.recvByteList.remove(0);
        String strRecv = Utility.bytesToHexString(bRecv, bRecv.length);
        this.logger.info("Net Client Recv - recvByteList Remove: " + strRecv);
        return bRecv;
      } else {
        return null;
      }
    }
  }

  public void clearRecv(int chanID) {
    synchronized(this.recvByteLock) {
      this.recvByteList.clear();
      this.logger.info("Net Client Clear recvByteList");
    }
  }

  public void clearSend() {
    synchronized(this.sendByteLock) {
      this.sendByteList.clear();
      this.logger.info("Net Client Clear sendByteList");
    }
  }

  public byte[] getRespond(byte[] bSend) {
    return null;
  }

  private class SendThread extends Thread {
    private SendThread() {
    }

    public void run() {
      while(true) {
        if (!this.isInterrupted()) {
          try {
            Thread.sleep(100L);
            synchronized(CommNetClientModbusTcp.this.sendByteLock) {
              if (CommNetClientModbusTcp.this.myHandler.mCtx != null && CommNetClientModbusTcp.this.sendByteList.size() > 0) {
                byte[] bSend = (byte[])CommNetClientModbusTcp.this.sendByteList.get(0);
                CommNetClientModbusTcp.this.sendByteList.remove(0);
                ByteBuf test = Unpooled.wrappedBuffer(bSend);
                CommNetClientModbusTcp.this.myHandler.mCtx.writeAndFlush(test);
                String strSend = Utility.bytesToHexString(bSend, bSend.length);
                CommNetClientModbusTcp.this.logger.info("Net Client Send(" + CommNetClientModbusTcp.this.myHandler.mCtx.channel().remoteAddress() + "): " + strSend);
              }
              continue;
            }
          } catch (InterruptedException var7) {
          }
        }

        return;
      }
    }
  }

  private class NetRecvListener implements RecvListener {
    private NetRecvListener() {
    }

    public void OnRecvEvent(byte[] bRecv) {
      if (CommNetClientModbusTcp.this.recvListener != null) {
        CommNetClientModbusTcp.this.recvListener.OnRecvEvent(bRecv);
      }

      String strRecv = Utility.bytesToHexString(bRecv, bRecv.length);
      CommNetClientModbusTcp.this.logger.info("Net Client Recv(" + CommNetClientModbusTcp.this.myHandler.mCtx.channel().remoteAddress() + "): " + strRecv);
      synchronized(CommNetClientModbusTcp.this.recvByteLock) {
        if (CommNetClientModbusTcp.this.recvByteList.size() > 20) {
          String strTmp = Utility.bytesToHexString((byte[])CommNetClientModbusTcp.this.recvByteList.get(0), ((byte[])CommNetClientModbusTcp.this.recvByteList.get(0)).length);
          CommNetClientModbusTcp.this.logger.info("Net Client recvByteList Remove: " + strTmp);
          CommNetClientModbusTcp.this.recvByteList.remove(0);
        }

        CommNetClientModbusTcp.this.recvByteList.add(bRecv);
        CommNetClientModbusTcp.this.logger.info("Net Client recvByteList Add: " + strRecv);
      }
    }
  }
}
