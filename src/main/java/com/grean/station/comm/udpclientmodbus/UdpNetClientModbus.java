//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm.udpclientmodbus;

import com.grean.station.comm.CommInter;
import com.grean.station.comm.RecvListener;
import com.grean.station.utils.Utility;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UdpNetClientModbus extends CommInter {
  private EventLoopGroup workGroup = new NioEventLoopGroup();
  private UdpHandlerModbus myHandler;
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

  public UdpNetClientModbus(String ip, int port, int address) {
    this.remoteIP = ip;
    this.remotePort = port;
    this.devAddress = address;
  }

  public Bootstrap createBootstrap(Bootstrap bootstrap, EventLoopGroup eventLoop) {
    if (bootstrap != null) {
      final UdpHandlerModbus handler = new UdpHandlerModbus(this);
      this.myHandler = handler;
      this.myHandler.setRecvListener(new UdpNetClientModbus.NetRecvListener());
      bootstrap.group(eventLoop);
      bootstrap.channel(NioDatagramChannel.class);
      bootstrap.handler(new ChannelInitializer<DatagramChannel>() {
        protected void initChannel(DatagramChannel datagramChannel) throws Exception {
          datagramChannel.pipeline().addLast(new ChannelHandler[]{new UdpDecoderModbus(UdpNetClientModbus.this.devAddress)});
          datagramChannel.pipeline().addLast(new ChannelHandler[]{handler});
        }
      });
      bootstrap.remoteAddress(this.remoteIP, this.remotePort);
      bootstrap.connect().addListener(new UdpListenerModbus(this));
    }

    return bootstrap;
  }

  public boolean Open() {
    this.createBootstrap(new Bootstrap(), this.workGroup);
    return false;
  }

  public boolean Close() {
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
        if (bRecv != null) {
          String strRecv = Utility.bytesToHexString(bRecv, bRecv.length);
          this.logger.info("Net Client Read(" + this.myHandler.mCtx.channel().remoteAddress() + "): " + strRecv);
        } else {
          this.logger.info("Net Client Read(" + this.myHandler.mCtx.channel().remoteAddress() + "): NULL");
        }

        return bRecv;
      } else {
        return null;
      }
    }
  }

  public void clearRecv(int chanID) {
    synchronized(this.recvByteLock) {
      this.recvByteList.clear();
      if (this.myHandler.mCtx != null) {
        this.logger.info("Net Client Clear(" + this.myHandler.mCtx.channel().remoteAddress() + ")");
      } else {
        this.logger.info("Net Client Clear");
      }

    }
  }

  public byte[] getRespond(byte[] bSend) {
    return null;
  }

  private class NetRecvListener implements RecvListener {
    private NetRecvListener() {
    }

    public void OnRecvEvent(byte[] bRecv) {
      if (UdpNetClientModbus.this.recvListener != null) {
        UdpNetClientModbus.this.recvListener.OnRecvEvent(bRecv);
      }

      synchronized(UdpNetClientModbus.this.recvByteLock) {
        if (UdpNetClientModbus.this.recvByteList.size() > 40) {
          UdpNetClientModbus.this.recvByteList.remove(0);
        }

        UdpNetClientModbus.this.recvByteList.add(bRecv);
      }

      String strRecv = Utility.bytesToHexString(bRecv, bRecv.length);
      UdpNetClientModbus.this.logger.info("Net Client Recv(" + UdpNetClientModbus.this.myHandler.mCtx.channel().remoteAddress() + "): " + strRecv);
    }
  }
}
