//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.controller;

import com.grean.station.domain.meta.ApiResponse;
import com.grean.station.service.query.RealTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api"})
public class RealTimeController {
  @Autowired
  RealTimeService realTimeService;

  public RealTimeController() {
  }

  @PostMapping({"/realtime/get_data"})
  public ApiResponse realtimeGetData() {
    ApiResponse apiResponse = new ApiResponse(this.realTimeService.getClientRtdData());
    return apiResponse;
  }

  @PostMapping({"/realtime/get_status"})
  public ApiResponse realtimeGetStatus() {
    ApiResponse apiResponse = new ApiResponse(this.realTimeService.getClientRtdStatus());
    return apiResponse;
  }

  @PostMapping({"/realtime/get_info"})
  public ApiResponse realtimeGetInfo() {
    ApiResponse apiResponse = new ApiResponse(this.realTimeService.getClientRtdInfo());
    return apiResponse;
  }

  @PostMapping({"/realtime/get_reset_info"})
  public ApiResponse realtimeGetResetInfo() {
    ApiResponse apiResponse = new ApiResponse(this.realTimeService.getResetStatus());
    return apiResponse;
  }

  @PostMapping({"/station/cancel_reset"})
  public ApiResponse stationClosePlc() {
    ApiResponse apiResponse = new ApiResponse(this.realTimeService.cancelResetStatus());
    return apiResponse;
  }
}
