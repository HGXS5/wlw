//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm.netclientserial;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class SerialDecoder extends ByteToMessageDecoder {
  public SerialDecoder() {
  }

  protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
    int beginIndex = byteBuf.readerIndex();
    int readableSize = byteBuf.readableBytes();
    if (readableSize > 0) {
      byteBuf.readerIndex(beginIndex + readableSize);
      ByteBuf otherByteBufRef = byteBuf.slice(beginIndex, readableSize);
      otherByteBufRef.retain();
      list.add(otherByteBufRef);
    }

  }
}
