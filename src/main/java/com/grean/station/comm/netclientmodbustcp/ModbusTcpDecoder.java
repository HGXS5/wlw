//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm.netclientmodbustcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class ModbusTcpDecoder extends ByteToMessageDecoder {
    public int mAddress;

    public ModbusTcpDecoder(int address) {
        this.mAddress = address;
    }

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int beginIndex = byteBuf.readerIndex();
        int readableSize = byteBuf.readableBytes();
        if (readableSize >= 8) {
            byteBuf.readByte();
            byteBuf.readByte();
            byteBuf.readByte();
            byteBuf.readByte();
            byte bSize1 = byteBuf.readByte();
            byte bSize2 = byteBuf.readByte();
            int address = byteBuf.readByte() & 255;
            int mLength = (bSize1 & 255) * 256 + (bSize2 & 255) + 6;
            if (address == this.mAddress && mLength == readableSize) {
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
