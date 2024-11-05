//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm;

import com.grean.station.utils.Utility;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetServerHandler extends ChannelInboundHandlerAdapter {
  public Logger logger = LoggerFactory.getLogger("net");

  public NetServerHandler() {
  }

  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf buf = (ByteBuf)msg;
    byte[] req = new byte[buf.readableBytes()];
    buf.readBytes(req);
    String strRecv = Utility.bytesToHexString(req, req.length);
    this.logger.info("Net Server Recv(" + ctx.channel().remoteAddress() + "): " + strRecv);
  }

  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    super.exceptionCaught(ctx, cause);
  }
}
