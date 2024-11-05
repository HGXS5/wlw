//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm.netserver.rtu;

import com.grean.station.utils.Utility;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Sharable
public class RtuMessageHandler extends ChannelInboundHandlerAdapter {
  private static final Logger logger = LoggerFactory.getLogger("server");
  @Autowired
  RtuMessageParser parser;

  public RtuMessageHandler() {
  }

  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf in = (ByteBuf)msg;

    try {
      byte[] bytes = ByteBufUtil.getBytes(in);
      String strRecv = Utility.bytesToHexString(bytes, bytes.length);
      logger.info("Recv: " + strRecv);
      if (this.parser.passCrc(bytes)) {
        byte[] bSend = this.parser.processMessage(bytes);
        String strSend = Utility.bytesToHexString(bSend, bSend.length);
        logger.info("Send: " + strSend);
        this.ack(ctx, bSend);
      } else {
        logger.info("Modbus RTU CRC Check Failed");
      }
    } catch (Exception var11) {
      logger.error(logger.getName(), var11);
    } finally {
      ReferenceCountUtil.release(in);
    }

  }

  private void ack(ChannelHandlerContext ctx, byte[] seq) {
    ByteBuf byteBuf = ctx.alloc().buffer(seq.length);
    byteBuf.writeBytes(seq);
    ChannelFuture cf = ctx.channel().writeAndFlush(byteBuf);
    if (null != cf.cause()) {
      cf.cause().printStackTrace();
      ctx.close();
    }

  }

  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    super.channelReadComplete(ctx);
    ctx.flush();
  }

  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    logger.error("exception in exceptionCaught " + cause.getCause().toString());
    cause.printStackTrace();
    ctx.close();
  }
}
