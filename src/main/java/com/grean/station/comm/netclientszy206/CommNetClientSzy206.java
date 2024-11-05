//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm.netclientszy206;

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

public class CommNetClientSzy206 extends CommInter {
  private EventLoopGroup workGroup = new NioEventLoopGroup();
  private InboundHandlerSzy206 myHandler;
  private String remoteIP;
  private int remotePort;
  private int devAddress;
  private CommNetClientSzy206.SendThread mSendThread;
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

  public CommNetClientSzy206(String ip, int port, int address) {
    this.remoteIP = ip;
    this.remotePort = port;
    this.devAddress = address;
  }

  public Bootstrap createBootstrap(Bootstrap bootstrap, EventLoopGroup eventLoop) {
    if (bootstrap != null) {
      final InboundHandlerSzy206 handler = new InboundHandlerSzy206(this);
      this.myHandler = handler;
      this.myHandler.setRecvListener(new CommNetClientSzy206.NetRecvListener());
      bootstrap.group(eventLoop);
      bootstrap.channel(NioSocketChannel.class);
      bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
      bootstrap.handler(new ChannelInitializer<SocketChannel>() {
        protected void initChannel(SocketChannel socketChannel) throws Exception {
          socketChannel.pipeline().addLast(new ChannelHandler[]{new Szy206Decoder(CommNetClientSzy206.this.devAddress)});
          socketChannel.pipeline().addLast(new ChannelHandler[]{handler});
        }
      });
      bootstrap.remoteAddress(this.remoteIP, this.remotePort);
      bootstrap.connect().addListener(new ConnectionListenerSzy206(this));
    }

    return bootstrap;
  }

  public boolean Open() {
    this.createBootstrap(new Bootstrap(), this.workGroup);
    this.mSendThread = new CommNetClientSzy206.SendThread();
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
      this.sendByteList.add(bSend);
      return true;
    }
  }

  public byte[] Recv(int chanID) {
    synchronized(this.recvByteLock) {
      if (this.recvByteList.size() > 0) {
        byte[] bRecv = (byte[])this.recvByteList.get(0);
        this.recvByteList.remove(0);
        return bRecv;
      } else {
        return null;
      }
    }
  }

  public void clearRecv(int chanID) {
    synchronized(this.recvByteLock) {
      this.recvByteList.clear();
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
            synchronized(CommNetClientSzy206.this.sendByteLock) {
              if (CommNetClientSzy206.this.myHandler.mCtx != null && CommNetClientSzy206.this.sendByteList.size() > 0) {
                byte[] bSend = (byte[])CommNetClientSzy206.this.sendByteList.get(0);
                CommNetClientSzy206.this.sendByteList.remove(0);
                ByteBuf test = Unpooled.wrappedBuffer(bSend);
                CommNetClientSzy206.this.myHandler.mCtx.writeAndFlush(test);
                String strSend = Utility.bytesToHexString(bSend, bSend.length);
                CommNetClientSzy206.this.logger.info("Net Client Send(" + CommNetClientSzy206.this.myHandler.mCtx.channel().remoteAddress() + "): " + strSend);
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
      if (CommNetClientSzy206.this.recvListener != null) {
        CommNetClientSzy206.this.recvListener.OnRecvEvent(bRecv);
      }

      synchronized(CommNetClientSzy206.this.recvByteLock) {
        CommNetClientSzy206.this.recvByteList.add(bRecv);
        if (CommNetClientSzy206.this.recvByteList.size() > 40) {
          CommNetClientSzy206.this.recvByteList.remove(0);
        }
      }

      String strRecv = Utility.bytesToHexString(bRecv, bRecv.length);
      CommNetClientSzy206.this.logger.info("Net Client Recv(" + CommNetClientSzy206.this.myHandler.mCtx.channel().remoteAddress() + "): " + strRecv);
    }
  }
}
