//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.controller;

import com.grean.station.domain.meta.ApiResponse;
import com.grean.station.domain.request.DateHead;
import com.grean.station.domain.request.DateRange;
import com.grean.station.domain.request.DevCmd;
import com.grean.station.domain.request.DevResult;
import com.grean.station.domain.request.FactorInfo;
import com.grean.station.domain.request.QualityInfo;
import com.grean.station.domain.request.ReqQualityCmd;
import com.grean.station.service.query.CheckService;
import com.grean.station.service.query.DevInfoService;
import com.grean.station.service.query.HistoryService;
import com.grean.station.service.query.StationInfoService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api"})
public class HistoryController {
  @Autowired
  HistoryService historyService;
  @Autowired
  CheckService checkService;
  @Autowired
  DevInfoService devInfoService;
  @Autowired
  StationInfoService stationInfoService;

  public HistoryController() {
  }

  @PostMapping({"/history/get_factors"})
  public ApiResponse historyListFactor() {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getHistoryFactorList());
    return apiResponse;
  }

  @PostMapping({"/history/get_devices"})
  public ApiResponse historyListDevices() {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getHistoryDeviceList());
    return apiResponse;
  }

  @PostMapping({"/history/get_range_factors"})
  public ApiResponse rangeListFactor() {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getRangeFactorList());
    return apiResponse;
  }

  @PostMapping({"/history/get_real_factors"})
  public ApiResponse realListFactor() {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getRealFactorList());
    return apiResponse;
  }

  @PostMapping({"/history/get_flow_factors"})
  public ApiResponse FlowListFactor() {
    List<FactorInfo> factorInfoList = this.historyService.getFlowFactorList();
    if (factorInfoList == null) {
      ApiResponse apiResponse = new ApiResponse();
      apiResponse.setSuccess(false);
      apiResponse.setMessage("无水文数据");
      return apiResponse;
    } else {
      return new ApiResponse(factorInfoList);
    }
  }

  @PostMapping({"/history/get_factors_five"})
  public ApiResponse historyListFactorFive() {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getHistoryFactorFiveList());
    return apiResponse;
  }

  @PostMapping({"/history/get_report"})
  public ApiResponse historyReport(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getHistoryReport(form));
    return apiResponse;
  }

  @PostMapping({"/history/get_day_report"})
  public ApiResponse historyDayReport(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getHistoryDayReport(form));
    return apiResponse;
  }

  @PostMapping({"/history/get_month_report"})
  public ApiResponse historyMonthReport(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getHistoryMonthReport(form));
    return apiResponse;
  }

  @PostMapping({"/history/get_year_report"})
  public ApiResponse historyYearReport(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getHistoryYearReport(form));
    return apiResponse;
  }

  @PostMapping({"/history/export_day_report"})
  public void export_day_report(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.historyService.exportHistoryReport(form, 1);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/history/export_month_report"})
  public void export_month_report(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.historyService.exportHistoryReport(form, 2);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/history/export_year_report"})
  public void export_year_report(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.historyService.exportHistoryReport(form, 3);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/history/get_mins"})
  public ApiResponse historyListMin(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getHistoryDataList(form, 1));
    return apiResponse;
  }

  @PostMapping({"/history/get_hours"})
  public ApiResponse historyListHour(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getHistoryDataList(form, 2));
    return apiResponse;
  }

  @PostMapping({"/history/get_hours_flag"})
  public ApiResponse historyListHourFlag() {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getHistoryHourDataFlag());
    return apiResponse;
  }

  @PostMapping({"/history/get_range_datas"})
  public ApiResponse rangeListData(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getHistoryDataList(form, 300));
    return apiResponse;
  }

  @PostMapping({"/history/get_real_datas"})
  public ApiResponse realListData(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getHistoryDataList(form, 1));
    return apiResponse;
  }

  @PostMapping({"/history/get_flow_datas"})
  public ApiResponse flowListData(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getHistoryDataList(form, 400));
    return apiResponse;
  }

  @PostMapping({"/history/get_hours_draw"})
  public ApiResponse historyDrawHour(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getHistoryDataDraw(form, 2));
    return apiResponse;
  }

  @PostMapping({"/history/get_fives"})
  public ApiResponse historyListFive(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getHistoryDataList(form, 3));
    return apiResponse;
  }

  @PostMapping({"/history/export_report"})
  public void export_report(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.historyService.exportHistoryReport(form);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/history/export_hours"})
  public void export_history(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.historyService.exportHistoryData(form, 2);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/history/export_ranges"})
  public void export_range(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.historyService.exportHistoryData(form, 300);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/history/export_real"})
  public void export_real(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.historyService.exportHistoryData(form, 1);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/history/export_flow"})
  public void export_flow(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.historyService.exportHistoryData(form, 400);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/history/export_fives"})
  public void export_Five(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.historyService.exportHistoryData(form, 3);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/check/get_zeros"})
  public ApiResponse checkListZero(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.checkService.getCheckZeroDataList(form));
    return apiResponse;
  }

  @PostMapping({"/check/get_zeros_flag"})
  public ApiResponse checkListZeroFlag() {
    ApiResponse apiResponse = new ApiResponse(this.checkService.getCheckZeroDataFlag());
    return apiResponse;
  }

  @PostMapping({"/check/export_zeros"})
  public void export_zeros(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.checkService.exportCheckZeroData(form);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/check/get_spans"})
  public ApiResponse checkListSpan(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.checkService.getCheckSpanDataList(form));
    return apiResponse;
  }

  @PostMapping({"/check/get_spans_flag"})
  public ApiResponse checkListSpanFlag() {
    ApiResponse apiResponse = new ApiResponse(this.checkService.getCheckSpanDataFlag());
    return apiResponse;
  }

  @PostMapping({"/check/export_spans"})
  public void export_spans(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.checkService.exportCheckSpanData(form);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/check/get_stds"})
  public ApiResponse checkListStd(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.checkService.getCheckStdDataList(form));
    return apiResponse;
  }

  @PostMapping({"/check/export_stds"})
  public void export_stds(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.checkService.exportCheckStdData(form);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/check/get_rcvrs"})
  public ApiResponse checkListRcvr(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.checkService.getCheckRcvrDataList(form));
    return apiResponse;
  }

  @PostMapping({"/check/export_rcvrs"})
  public void export_rcvrs(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.checkService.exportCheckRcvrData(form);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/check/get_pars"})
  public ApiResponse checkListPar(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.checkService.getCheckParDataList(form));
    return apiResponse;
  }

  @PostMapping({"/check/export_pars"})
  public void export_pars(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.checkService.exportCheckParData(form);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/check/get_blanks"})
  public ApiResponse checkListBlank(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.checkService.getCheckBlankDataList(form));
    return apiResponse;
  }

  @PostMapping({"/check/export_blanks"})
  public void export_blanks(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.checkService.exportCheckBlankData(form);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/check/get_fives"})
  public ApiResponse checkListFive(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.checkService.getCheckFiveDataList(form));
    return apiResponse;
  }

  @PostMapping({"/check/export_fives"})
  public void export_fives(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.checkService.exportCheckFiveData(form);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/dev/get_info"})
  public ApiResponse devListInfo(@RequestBody Integer devID) {
    ApiResponse apiResponse = new ApiResponse(this.devInfoService.getDevInfo(devID));
    return apiResponse;
  }

  @PostMapping({"/dev/do_cmd"})
  public ApiResponse devDoCmd(@RequestBody DevCmd devCmd) {
    ApiResponse apiResponse = new ApiResponse(this.devInfoService.doDevCmd(devCmd));
    return apiResponse;
  }

  @PostMapping({"/station/do_cmd"})
  public ApiResponse stationDoCmd(@RequestBody Integer stationCmd) {
    ApiResponse apiResponse = new ApiResponse(this.stationInfoService.doStationCmd(stationCmd));
    return apiResponse;
  }

  @PostMapping({"/station/get_info"})
  public ApiResponse devListInfo() {
    ApiResponse apiResponse = new ApiResponse(this.stationInfoService.getStationInfo());
    return apiResponse;
  }

  @PostMapping({"/station/get_version"})
  public ApiResponse devVersionInfo() {
    ApiResponse apiResponse = new ApiResponse(this.stationInfoService.getVersionInfo());
    return apiResponse;
  }

  @PostMapping({"/station/get_quality_info"})
  public ApiResponse devQualityInfo() {
    ApiResponse apiResponse = new ApiResponse(this.stationInfoService.getQualityInfo());
    return apiResponse;
  }

  @PostMapping({"/station/set_quality_param"})
  public ApiResponse stationSetQualityParam(@RequestBody QualityInfo qualityInfo) {
    DevResult devResult = this.stationInfoService.setQualityParam(qualityInfo);
    ApiResponse apiResponse = new ApiResponse(devResult);
    apiResponse.setSuccess(devResult.isResult());
    if (!devResult.isResult()) {
      apiResponse.setMessage("操作失败");
    }

    return apiResponse;
  }

  @PostMapping({"/station/set_pump_cal_vol"})
  public ApiResponse stationSetPumpCalVol(@RequestBody ReqQualityCmd reqQualityCmd) {
    DevResult devResult = this.stationInfoService.setPumpCalVol(reqQualityCmd);
    ApiResponse apiResponse = new ApiResponse(devResult);
    apiResponse.setSuccess(devResult.isResult());
    if (!devResult.isResult()) {
      apiResponse.setMessage("操作失败");
    }

    return apiResponse;
  }

  @PostMapping({"/station/do_quality_cmd"})
  public ApiResponse stationDoQualityCmd(@RequestBody ReqQualityCmd reqQualityCmd) {
    DevResult devResult = this.stationInfoService.doQualityCmd(reqQualityCmd);
    ApiResponse apiResponse = new ApiResponse(devResult);
    apiResponse.setSuccess(devResult.isResult());
    if (!devResult.isResult()) {
      apiResponse.setMessage("操作失败");
    }

    return apiResponse;
  }

  @PostMapping({"/station/open_quality_point"})
  public ApiResponse stationOpenQualityPoint(@RequestBody ReqQualityCmd reqQualityCmd) {
    DevResult devResult = this.stationInfoService.doPointCmd(reqQualityCmd, 1);
    ApiResponse apiResponse = new ApiResponse(devResult);
    apiResponse.setSuccess(devResult.isResult());
    if (!devResult.isResult()) {
      apiResponse.setMessage("操作失败");
    }

    return apiResponse;
  }

  @PostMapping({"/station/close_quality_point"})
  public ApiResponse stationCloseQualityPoint(@RequestBody ReqQualityCmd reqQualityCmd) {
    DevResult devResult = this.stationInfoService.doPointCmd(reqQualityCmd, 0);
    ApiResponse apiResponse = new ApiResponse(devResult);
    apiResponse.setSuccess(devResult.isResult());
    if (!devResult.isResult()) {
      apiResponse.setMessage("操作失败");
    }

    return apiResponse;
  }

  @PostMapping({"/station/open_plc"})
  public ApiResponse stationOpenPlc(@RequestBody Integer plcAddress) {
    ApiResponse apiResponse = new ApiResponse(this.stationInfoService.openPlcCmd(plcAddress));
    return apiResponse;
  }

  @PostMapping({"/station/close_plc"})
  public ApiResponse stationClosePlc(@RequestBody Integer plcAddress) {
    ApiResponse apiResponse = new ApiResponse(this.stationInfoService.closePlcCmd(plcAddress));
    return apiResponse;
  }

  @PostMapping({"/station/reset_plc"})
  public ApiResponse stationClosePlc() {
    ApiResponse apiResponse = new ApiResponse(this.stationInfoService.resetPlcCmd());
    return apiResponse;
  }

  @PostMapping({"/alarm/get_datas"})
  public ApiResponse alarmListData(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getAlarmDataList(form));
    return apiResponse;
  }

  @PostMapping({"/alarm/export_datas"})
  public void export_alarm_data(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.historyService.exportAlarmData(form);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/alarm/get_devs"})
  public ApiResponse alarmListDev(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getAlarmDevList(form));
    return apiResponse;
  }

  @PostMapping({"/log/get_runnings"})
  public ApiResponse logListRunning(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getLogRunningList(form));
    return apiResponse;
  }

  @PostMapping({"/log/export_runnings"})
  public void export_log_runnings(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.historyService.exportLogRunning(form);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/log/get_devices"})
  public ApiResponse logListDevices(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getLogDevList(form));
    return apiResponse;
  }

  @PostMapping({"/log/export_devices"})
  public void export_log_devices(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.historyService.exportLogDevice(form);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/log/get_exceptions"})
  public ApiResponse logListException(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getLogExceptionList(form));
    return apiResponse;
  }

  @PostMapping({"/log/export_exceptions"})
  public void export_log_exceptions(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.historyService.exportLogException(form);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/log/get_samples"})
  public ApiResponse logListSample(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getLogSampleList(form));
    return apiResponse;
  }

  @PostMapping({"/log/export_samples"})
  public void export_log_samples(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.historyService.exportLogSample(form);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/log/get_audits"})
  public ApiResponse logListAudit(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getLogAuditList(form));
    return apiResponse;
  }

  @PostMapping({"/log/export_audits"})
  public void export_log_audits(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.historyService.exportLogAudit(form);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/log/get_access"})
  public ApiResponse logListAccess(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getLogAccessList(form));
    return apiResponse;
  }

  @PostMapping({"/log/export_access"})
  public void export_log_access(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.historyService.exportLogAccess(form);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }

  @PostMapping({"/net/upload_data"})
  public ApiResponse uploadData(@RequestBody DateHead form) {
    return this.uploadDataNetwork(form);
  }

  @PostMapping({"/net/upload_stop"})
  public ApiResponse uploadStop(@RequestBody DateHead form) {
    return this.uploadStopNetwork(form);
  }

  @PostMapping({"/net/upload_data_network"})
  public ApiResponse uploadDataNetwork(@RequestBody DateHead form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.uploadDataNetwork(form));
    return apiResponse;
  }

  @PostMapping({"/net/upload_stop_network"})
  public ApiResponse uploadStopNetwork(@RequestBody DateHead form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.uploadStopNetwork(form));
    return apiResponse;
  }

  @PostMapping({"/net/upload_data_com"})
  public ApiResponse uploadDataCom(@RequestBody DateHead form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.uploadDataCom(form));
    return apiResponse;
  }

  @PostMapping({"/net/upload_stop_com"})
  public ApiResponse uploadStopCom(@RequestBody DateHead form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.uploadStopCom(form));
    return apiResponse;
  }

  @PostMapping({"/history/get_platform_list"})
  public ApiResponse getPlatformList() {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getPlatformList());
    return apiResponse;
  }

  @PostMapping({"/log/get_platforms"})
  public ApiResponse logListPlatforms(@RequestBody DateRange form) {
    ApiResponse apiResponse = new ApiResponse(this.historyService.getLogPlatformList(form));
    return apiResponse;
  }

  @PostMapping({"/log/export_platforms"})
  public void export_log_platforms(HttpServletResponse response, @RequestBody DateRange form) throws UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = this.historyService.exportLogPlatform(form);
    byte[] bytes = outputStream.toByteArray();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
    String disposition = "attachment; filename=" + URLEncoder.encode("export", "UTF-8") + ".xls;";
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setHeader("Content-disposition", disposition);

    try {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException var8) {
      var8.printStackTrace();
    }

  }
}
