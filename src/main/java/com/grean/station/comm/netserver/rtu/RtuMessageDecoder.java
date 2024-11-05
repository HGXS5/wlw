//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm.netserver.rtu;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class RtuMessageDecoder extends ByteToMessageDecoder {
  public int mAddress;

  public RtuMessageDecoder(int address) {
    this.mAddress = address;
  }

  protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
    int beginIndex = byteBuf.readerIndex();
    int readableSize = byteBuf.readableBytes();
    short address = (short)byteBuf.readByte();
    if (address != this.mAddress) {
      byteBuf.readerIndex(beginIndex + readableSize);
      byteBuf.slice(beginIndex, readableSize);
    } else {
      short mFunction = (short)byteBuf.readByte();
      byte mLength;
      switch(mFunction) {
        case 3:
        case 4:
        case 6:
          mLength = 8;
          break;
        case 5:
        default:
          mLength = 1;
      }

      if (mLength <= readableSize) {
        if (mLength == 1) {
          byteBuf.readerIndex(beginIndex + mLength);
          byteBuf.slice(beginIndex, mLength);
        } else {
          byteBuf.readerIndex(beginIndex + mLength);
          ByteBuf otherByteBufRef = byteBuf.slice(beginIndex, mLength);
          otherByteBufRef.retain();
          list.add(otherByteBufRef);
        }
      }
    }
  }
}
