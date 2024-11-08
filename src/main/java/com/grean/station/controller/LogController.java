//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.controller;

import com.grean.station.domain.meta.ApiResponse;
import com.grean.station.domain.request.SendLogInfo;
import com.grean.station.service.SendLogService;
import com.grean.station.utils.JsonUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@Api(tags = "日志记录")
@RestController
public class LogController {
  @Autowired
  SendLogService sendLogService;

  public LogController() {
  }

  @PostMapping({"/upload_log"})
  public ApiResponse uploadLog(@RequestBody SendLogInfo sendLogInfo) {
    return new ApiResponse();
  }

  @PostMapping({"api/check_qrcode"})
  public ApiResponse checkQrCode(@RequestBody String code) {
    String checkInfo = this.sendLogService.postdCheckCode(code);
    return new ApiResponse(JsonUtils.parseObject(checkInfo));
  }
}
