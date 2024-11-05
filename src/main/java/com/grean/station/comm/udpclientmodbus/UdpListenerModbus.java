//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm.udpclientmodbus;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UdpListenerModbus implements ChannelFutureListener {
  private UdpNetClientModbus client;
  public Logger logger = LoggerFactory.getLogger("net");

  public UdpListenerModbus(UdpNetClientModbus client) {
    this.client = client;
  }

  public void operationComplete(ChannelFuture channelFuture) throws Exception {
    if (!channelFuture.isSuccess()) {
      this.client.setConnect(false);
      this.logger.info(this.client.getRemoteIP() + ":" + this.client.getRemotePort() + " Reconnect");
      final EventLoop loop = channelFuture.channel().eventLoop();
      loop.schedule(new Runnable() {
        public void run() {
          UdpListenerModbus.this.client.createBootstrap(new Bootstrap(), loop);
        }
      }, 1L, TimeUnit.SECONDS);
    } else {
      this.client.setConnect(true);
      this.logger.info(this.client.getRemoteIP() + ":" + this.client.getRemotePort() + " Connect");
    }

  }
}
