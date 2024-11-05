//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.comm.netserver;

import com.grean.station.comm.netserver.rtu.RtuChannelInitializer;
import com.grean.station.comm.netserver.tcp.TcpChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NetServer {
    @Value("${net_server_port}")
    private Integer port;
    @Value("${net_server_protocol}")
    private String protocal;
    @Autowired
    RtuChannelInitializer rtuChannelInitializer;
    @Autowired
    TcpChannelInitializer tcpChannelInitializer;
    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;
    static final Logger logger = LoggerFactory.getLogger("server");

    public NetServer() {
    }

    private void start() {
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            ((ServerBootstrap)bootstrap.group(this.bossGroup, this.workerGroup).channel(NioServerSocketChannel.class)).localAddress(this.port);
            if (this.protocal.equalsIgnoreCase("rtu")) {
                logger.info("使用ModbusRTU进行通讯");
                bootstrap.childHandler(this.rtuChannelInitializer);
            } else {
                if (!this.protocal.equalsIgnoreCase("tcp")) {
                    logger.error("无指定的通讯协议类型，启动失败");
                    return;
                }

                logger.info("使用ModbusTCP进行通讯");
                bootstrap.childHandler(this.tcpChannelInitializer);
            }

            bootstrap.option(ChannelOption.SO_BACKLOG, 200);
            ChannelFuture future = bootstrap.bind().sync();
            logger.info("started and listen on " + future.channel().localAddress());
            future.channel().closeFuture().sync();
        } catch (InterruptedException var3) {
            logger.error("Server start error ");
            this.stop();
        }

    }

    @Async
    public void doStart() {
        try {
            this.start();
        } catch (Exception var2) {
            var2.printStackTrace();
            logger.error("Server start error " + var2.getMessage());
        }

    }

    private void stop() {
        if (this.bossGroup != null) {
            this.bossGroup.shutdownGracefully();
        }

        if (this.workerGroup != null) {
            this.workerGroup.shutdownGracefully();
        }

        logger.info("Server is shut down");
    }
}
