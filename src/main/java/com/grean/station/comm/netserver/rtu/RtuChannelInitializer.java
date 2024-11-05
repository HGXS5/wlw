//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm.netserver.rtu;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RtuChannelInitializer extends ChannelInitializer<SocketChannel> {
  @Value("${net_server_address}")
  private Integer address;
  @Autowired
  RtuMessageHandler messageHandler;
  static final EventExecutorGroup group = new DefaultEventExecutorGroup(16);

  public RtuChannelInitializer() {
  }

  protected void initChannel(SocketChannel socketChannel) throws Exception {
    ChannelPipeline pipeline = socketChannel.pipeline();
    pipeline.addLast(new ChannelHandler[]{new RtuMessageDecoder(this.address)});
    pipeline.addLast(group, "messageHandler", this.messageHandler);
  }
}
