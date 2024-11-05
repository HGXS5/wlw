//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.controller;

import com.grean.station.domain.meta.ApiResponse;
import com.grean.station.domain.request.LoginForm;
import com.grean.station.domain.request.UpdatePassword;
import com.grean.station.service.ShutdownService;
import com.grean.station.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api"})
public class UserController {
  @Autowired
  UserLoginService userLoginService;
  @Autowired
  ShutdownService shutdownService;

  public UserController() {
  }

  @RequestMapping({"/ssologin"})
  public String ssoLogin() {
    return "/dashboard/data";
  }

  @PostMapping({"/login"})
  public ApiResponse login(@RequestBody LoginForm loginForm) {
    return new ApiResponse(this.userLoginService.login(loginForm));
  }

  @PostMapping({"/updatepwd"})
  public ApiResponse updatePassword(@RequestBody UpdatePassword updatePassword) {
    ApiResponse apiResponse = new ApiResponse();
    if (this.userLoginService.updateUserPwd(updatePassword)) {
      apiResponse.setSuccess(true);
    } else {
      apiResponse.setSuccess(false);
      apiResponse.setMessage("旧密码不正确，修改登录密码失败");
    }

    return apiResponse;
  }

  @PostMapping({"/update_access"})
  public ApiResponse updateAccess(@RequestBody UpdatePassword updatePassword) {
    ApiResponse apiResponse = new ApiResponse();
    if (this.userLoginService.updateAccessPwd(updatePassword)) {
      apiResponse.setSuccess(true);
    } else {
      apiResponse.setSuccess(false);
      apiResponse.setMessage("旧密码不正确，修改门禁密码失败");
    }

    return apiResponse;
  }

  @PostMapping({"/close"})
  public void closeSystem(@RequestBody String userName) {
    try {
      this.shutdownService.showdown();
      Runtime.getRuntime().exec("taskkill /F /IM java.exe");
    } catch (Exception var3) {
    }

  }
}
