//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm.netclientszy206;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class Szy206Decoder extends ByteToMessageDecoder {
  public int mAddress;

  public Szy206Decoder(int address) {
    this.mAddress = address;
  }

  protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
    int beginIndex = byteBuf.readerIndex();
    int readableSize = byteBuf.readableBytes();
    if (readableSize >= 5) {
      byte bStart1 = byteBuf.readByte();
      byte bLen = byteBuf.readByte();
      byte bStart2 = byteBuf.readByte();
      int mLength = bLen + 5;
      if (bStart1 == 104 && mLength == readableSize && bStart2 == 104) {
        byteBuf.readerIndex(beginIndex + mLength);
        ByteBuf otherByteBufRef = byteBuf.slice(beginIndex, mLength);
        otherByteBufRef.retain();
        list.add(otherByteBufRef);
      } else {
        byteBuf.readerIndex(beginIndex + readableSize);
        byteBuf.slice(beginIndex, readableSize);
      }
    }
  }
}
