//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm.udpclientmodbus;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

public class UdpDecoderModbus extends MessageToMessageDecoder<DatagramPacket> {
  public int mAddress;

  public UdpDecoderModbus(int address) {
    this.mAddress = address;
  }

  protected void decode(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket, List<Object> list) throws Exception {
    ByteBuf byteBuf = (ByteBuf)datagramPacket.content();
    int beginIndex = byteBuf.readerIndex();
    int readableSize = byteBuf.readableBytes();
    if (readableSize >= 3) {
      int address = byteBuf.readByte() & 255;
      if (address != this.mAddress) {
        byteBuf.readerIndex(beginIndex + readableSize);
        byteBuf.slice(beginIndex, readableSize);
      } else {
        short mFunction = (short)byteBuf.readByte();
        int mLength;
        switch(mFunction) {
          case 1:
          case 2:
          case 3:
          case 4:
            mLength = (byteBuf.readByte() & 255) + 5;
            break;
          case 5:
          case 6:
          case 15:
          case 16:
            mLength = 8;
            break;
          case 7:
          case 8:
          case 9:
          case 10:
          case 11:
          case 12:
          case 13:
          case 14:
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
}
