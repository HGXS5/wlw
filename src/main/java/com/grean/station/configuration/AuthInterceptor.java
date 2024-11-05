//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.configuration;

import com.grean.station.domain.response.TokenUser;
import com.grean.station.exception.ErrorCode;
import com.grean.station.exception.ServerException;
import com.grean.station.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
  @Autowired
  UserService userService;

  public AuthInterceptor() {
  }

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String token = request.getHeader("token");
    if (token == null) {
      throw new ServerException(ErrorCode.UNAUTHENTICATED.getCode(), "请重新登录");
    } else {
      TokenUser tokenUser = this.userService.getTokenUser(token);
      if (tokenUser == null) {
        throw new ServerException(ErrorCode.UNAUTHENTICATED.getCode(), "请重新登录");
      } else if (tokenUser.getTimeout().isBefore(new DateTime())) {
        throw new ServerException(ErrorCode.UNAUTHENTICATED.getCode(), "请重新登录");
      } else {
        return true;
      }
    }
  }
}
