//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm.netclientline;

import com.grean.station.comm.CommInter;
import com.grean.station.comm.RecvListener;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommNetClientLine extends CommInter {
    private EventLoopGroup workGroup = new NioEventLoopGroup();
    private InboundHandlerLine myHandler;
    private String remoteIP;
    private int remotePort;
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

    public CommNetClientLine(String ip, int port) {
        this.remoteIP = ip;
        this.remotePort = port;
    }

    public Bootstrap createBootstrap(Bootstrap bootstrap, EventLoopGroup eventLoop) {
        if (bootstrap != null) {
            final InboundHandlerLine handler = new InboundHandlerLine(this);
            this.myHandler = handler;
            this.myHandler.setRecvListener(new CommNetClientLine.NetRecvListener());
            bootstrap.group(eventLoop);
            bootstrap.channel(NioSocketChannel.class);
            ((Bootstrap) ((Bootstrap) bootstrap.option(ChannelOption.SO_KEEPALIVE, true)).option(ChannelOption.TCP_NODELAY, true)).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new ChannelHandler[]{new LineBasedFrameDecoder(1024)});
                    socketChannel.pipeline().addLast(new ChannelHandler[]{handler});
                }
            });
            bootstrap.remoteAddress(this.remoteIP, this.remotePort);
            bootstrap.connect().addListener(new ConnectionListenerLine(this));
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
            String strSend = new String(bSend);
            this.logger.info("Net Client Send(" + this.myHandler.mCtx.channel().remoteAddress() + "): " + strSend);
            return true;
        } else {
            return false;
        }
    }

    public byte[] Recv(int chanID) {
        synchronized (this.recvByteLock) {
            if (this.recvByteList.size() > 0) {
                byte[] bRecv = (byte[]) this.recvByteList.get(0);
                this.recvByteList.remove(0);
                return bRecv;
            } else {
                return null;
            }
        }
    }

    public void clearRecv(int chanID) {
        synchronized (this.recvByteLock) {
            this.recvByteList.clear();
            this.logger.info("Net Client Clear(" + this.myHandler.mCtx.channel().remoteAddress() + ")");
        }
    }

    public byte[] getRespond(byte[] bSend) {
        byte[] bRecv = null;
        boolean bCheck = false;
        String strSend = new String(bSend);
        int startIndex = strSend.indexOf("QN=");
        if (startIndex < 0) {
            return null;
        } else {
            String strQN = strSend.substring(startIndex, startIndex + 20);
            bCheck = false;
            synchronized (this.recvByteLock) {
                int i = 0;

                while (i < this.recvByteList.size()) {
                    bRecv = (byte[]) this.recvByteList.get(i);
                    String strRecv = new String(bRecv);
                    if (strRecv.indexOf(strQN) < 0) {
                        ++i;
                    } else {
                        this.recvByteList.remove(i);
                        bCheck = true;
                        break;
                    }
                }
            }

            return bCheck ? bRecv : null;
        }
    }

    private class NetRecvListener implements RecvListener {
        private NetRecvListener() {
        }

        public void OnRecvEvent(byte[] bRecv) {
            String strRecv = new String(bRecv);
            CommNetClientLine.this.logger.info("Net Client Recv(" + CommNetClientLine.this.myHandler.mCtx.channel().remoteAddress() + "): " + strRecv);
            if (CommNetClientLine.this.recvListener != null) {
                CommNetClientLine.this.recvListener.OnRecvEvent(bRecv);
            }

            synchronized (CommNetClientLine.this.recvByteLock) {
                if (CommNetClientLine.this.recvByteList.size() > 40) {
                    CommNetClientLine.this.recvByteList.remove(0);
                }

                CommNetClientLine.this.recvByteList.add(bRecv);
            }
        }
    }
}
