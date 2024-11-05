//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulingConfiguration {
  public SchedulingConfiguration() {
  }

  @Bean(
          destroyMethod = "shutdown"
  )
  public Executor taskScheduler() {
    return Executors.newScheduledThreadPool(10);
  }
}
