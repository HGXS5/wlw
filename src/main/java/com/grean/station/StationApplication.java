//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station;

import com.grean.station.netty.SocketServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication(
        exclude = {SecurityAutoConfiguration.class}
)
@MapperScan("com.grean.station.dao")
public class StationApplication {
  public StationApplication() {
  }

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(StationApplication.class, args);
    SocketServer socketServer = (SocketServer)context.getBean(SocketServer.class);
    socketServer.doStart();
  }
}
