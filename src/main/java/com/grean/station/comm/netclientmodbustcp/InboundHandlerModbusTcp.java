//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm.netclientmodbustcp;

import com.grean.station.comm.RecvListener;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InboundHandlerModbusTcp extends SimpleChannelInboundHandler {
  private RecvListener recvListener;
  private CommNetClientModbusTcp client;
  public ChannelHandlerContext mCtx;
  public Logger logger = LoggerFactory.getLogger("net");

  public void setRecvListener(RecvListener listener) {
    this.recvListener = listener;
  }

  public InboundHandlerModbusTcp(CommNetClientModbusTcp client) {
    this.client = client;
  }

  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    final EventLoop eventLoop = ctx.channel().eventLoop();
    eventLoop.schedule(new Runnable() {
      public void run() {
        InboundHandlerModbusTcp.this.client.createBootstrap(new Bootstrap(), eventLoop);
      }
    }, 1L, TimeUnit.SECONDS);
    super.channelInactive(ctx);
    this.client.setConnect(false);
    this.logger.info(this.client.getRemoteIP() + ":" + this.client.getRemotePort() + " Inactive");
  }

  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    super.channelActive(ctx);
    this.mCtx = ctx;
    this.client.setConnect(true);
    this.logger.info(this.client.getRemoteIP() + ":" + this.client.getRemotePort() + " Active");
  }

  protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
    ByteBuf buf = (ByteBuf)o;
    byte[] req = new byte[buf.readableBytes()];
    buf.readBytes(req);
    this.recvListener.OnRecvEvent(req);
  }

  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    super.exceptionCaught(ctx, cause);
  }
}
