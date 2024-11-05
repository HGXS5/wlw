//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm.netclientmodbus;

import com.grean.station.comm.RecvListener;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.TimeUnit;

public class InboundHandlerModbus extends SimpleChannelInboundHandler {
    private RecvListener recvListener;
    private CommNetClientModbus client;
    public ChannelHandlerContext mCtx;

    public void setRecvListener(RecvListener listener) {
        this.recvListener = listener;
    }

    public InboundHandlerModbus(CommNetClientModbus client) {
        this.client = client;
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(new Runnable() {
            public void run() {
                InboundHandlerModbus.this.client.createBootstrap(new Bootstrap(), eventLoop);
            }
        }, 1L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
        this.client.setConnect(false);
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.mCtx = ctx;
        this.client.setConnect(true);
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        ByteBuf buf = (ByteBuf) o;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        this.recvListener.OnRecvEvent(req);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
