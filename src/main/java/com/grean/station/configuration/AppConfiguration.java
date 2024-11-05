//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfiguration {
  public AppConfiguration() {
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    int strength = 14;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(strength);
    return passwordEncoder;
  }
}
