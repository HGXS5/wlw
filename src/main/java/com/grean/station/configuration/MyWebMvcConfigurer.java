//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
  public MyWebMvcConfigurer() {
  }

  @Bean
  public AuthInterceptor myAuthInterceptor() {
    return new AuthInterceptor();
  }

  public void addInterceptors(InterceptorRegistry registry) {
    InterceptorRegistration ir = registry.addInterceptor(this.myAuthInterceptor());
    ir.addPathPatterns(new String[]{"/api/setting/**"});
    ir.addPathPatterns(new String[]{"/api/realtime/**"});
    ir.addPathPatterns(new String[]{"/api/history/**"});
  }
}
