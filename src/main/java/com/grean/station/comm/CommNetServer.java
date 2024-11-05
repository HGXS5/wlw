//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class CommNetServer {
  private int port;
  EventLoopGroup bossGroup = new NioEventLoopGroup();
  EventLoopGroup workerGroup = new NioEventLoopGroup();
  ChannelFuture channelFuture;

  public CommNetServer(int port) {
    this.port = port;
  }

  public void run() throws Exception {
    try {
      ServerBootstrap b = new ServerBootstrap();
      ((ServerBootstrap)((ServerBootstrap)b.group(this.bossGroup, this.workerGroup).channel(NioServerSocketChannel.class)).childHandler(new ChannelInitializer<SocketChannel>() {
        protected void initChannel(SocketChannel socketChannel) throws Exception {
          socketChannel.pipeline().addLast(new ChannelHandler[]{new NetServerHandler()});
        }
      }).option(ChannelOption.SO_BACKLOG, 128)).childOption(ChannelOption.SO_KEEPALIVE, true);
      this.channelFuture = b.bind(this.port).sync();
      this.channelFuture.channel().closeFuture().sync();
    } finally {
      this.workerGroup.shutdownGracefully();
      this.bossGroup.shutdownGracefully();
    }

  }
}
