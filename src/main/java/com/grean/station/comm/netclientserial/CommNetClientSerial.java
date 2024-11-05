//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm.netclientserial;

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

public class CommNetClientSerial extends CommInter {
  private EventLoopGroup workGroup;
  private InboundHandlerSerial myHandler;
  private String remoteIP;
  private int remotePort;
  private int devAddress;
  private List<byte[]> recvByteList = new ArrayList();
  private final Object recvByteLock = new Object();
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

  public CommNetClientSerial(String ip, int port, int address) {
    this.remoteIP = ip;
    this.remotePort = port;
    this.devAddress = address;
  }

  public Bootstrap createBootstrap(Bootstrap bootstrap, EventLoopGroup eventLoop) {
    if (bootstrap != null) {
      final InboundHandlerSerial handler = new InboundHandlerSerial(this);
      this.myHandler = handler;
      this.myHandler.setRecvListener(new CommNetClientSerial.NetRecvListener());
      bootstrap.group(eventLoop);
      bootstrap.channel(NioSocketChannel.class);
      bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
      bootstrap.handler(new ChannelInitializer<SocketChannel>() {
        protected void initChannel(SocketChannel socketChannel) throws Exception {
          socketChannel.pipeline().addLast(new ChannelHandler[]{new SerialDecoder()});
          socketChannel.pipeline().addLast(new ChannelHandler[]{handler});
        }
      });
      bootstrap.remoteAddress(this.remoteIP, this.remotePort);
      bootstrap.connect().addListener(new ConnectionListenerSerial(this));
    }

    return bootstrap;
  }

  public boolean Open() {
    this.workGroup = new NioEventLoopGroup();
    this.createBootstrap(new Bootstrap(), this.workGroup);
    return false;
  }

  public boolean Close() {
    this.setConnect(false);
    this.workGroup.shutdownGracefully();
    return false;
  }

  public boolean Send(byte[] bSend) {
    if (this.myHandler.mCtx != null) {
      ByteBuf test = Unpooled.wrappedBuffer(bSend);
      this.myHandler.mCtx.writeAndFlush(test);
      String strSend = Utility.bytesToHexString(bSend, bSend.length);
      this.logger.info("Net Client Send(" + this.myHandler.mCtx.channel().remoteAddress() + "): " + strSend);
      return true;
    } else {
      return false;
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

  private class NetRecvListener implements RecvListener {
    private NetRecvListener() {
    }

    public void OnRecvEvent(byte[] bRecv) {
      if (CommNetClientSerial.this.recvListener != null) {
        CommNetClientSerial.this.recvListener.OnRecvEvent(bRecv);
      }

      synchronized(CommNetClientSerial.this.recvByteLock) {
        CommNetClientSerial.this.recvByteList.add(bRecv);
        if (CommNetClientSerial.this.recvByteList.size() > 40) {
          CommNetClientSerial.this.recvByteList.remove(0);
        }
      }

      String strRecv = Utility.bytesToHexString(bRecv, bRecv.length);
      CommNetClientSerial.this.logger.info("Net Client Recv(" + CommNetClientSerial.this.myHandler.mCtx.channel().remoteAddress() + "): " + strRecv);
    }
  }
}
