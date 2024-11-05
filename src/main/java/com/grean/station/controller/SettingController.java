//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.controller;

import com.grean.station.domain.DO.cfg.CfgCamera;
import com.grean.station.domain.DO.cfg.CfgFlow;
import com.grean.station.domain.DO.cfg.CfgSample;
import com.grean.station.domain.DO.cfg.CfgStation;
import com.grean.station.domain.meta.ApiResponse;
import com.grean.station.domain.request.DevInterval;
import com.grean.station.domain.request.DevModel;
import com.grean.station.domain.request.LoginTime;
import com.grean.station.domain.request.PlcAIInfo;
import com.grean.station.domain.request.PlcInfo;
import com.grean.station.domain.request.PlcQInfo;
import com.grean.station.domain.request.ProtocolCmd;
import com.grean.station.domain.request.ProtocolQuery;
import com.grean.station.domain.request.RcvrType;
import com.grean.station.domain.request.ReqFiveCheck;
import com.grean.station.domain.request.ReqFiveInfo;
import com.grean.station.domain.request.ReqScheduleQuality;
import com.grean.station.domain.request.ReqStdCheck;
import com.grean.station.domain.request.ReqUserModbus;
import com.grean.station.domain.request.RunningInfo;
import com.grean.station.domain.request.SampleControl;
import com.grean.station.domain.request.ScheduleDay;
import com.grean.station.domain.request.ScheduleMonth;
import com.grean.station.domain.request.ScheduleSample;
import com.grean.station.domain.request.SetComInfo;
import com.grean.station.domain.request.SetDeviceParam;
import com.grean.station.domain.request.SetDriftVal;
import com.grean.station.domain.request.SetErrorVal;
import com.grean.station.domain.request.SetFactorInfo;
import com.grean.station.domain.request.SetInstrumentInfo;
import com.grean.station.domain.request.SetNetInfo;
import com.grean.station.domain.request.SetRcvrVal;
import com.grean.station.domain.request.SetSpanVal;
import com.grean.station.domain.request.SetStdVal;
import com.grean.station.domain.request.SetUserInfo;
import com.grean.station.domain.request.UploadInfo;
import com.grean.station.service.query.SettingService;
import com.grean.station.utils.BrowserUtil;
import java.net.URI;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api"})
public class SettingController {
  @Autowired
  SettingService settingService;

  public SettingController() {
  }

  @PostMapping({"/setting/get_station"})
  public ApiResponse getStation() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getStationInfo());
    return apiResponse;
  }

  @PostMapping({"/setting/set_station"})
  public ApiResponse setStation(HttpServletRequest request, @RequestBody CfgStation cfgStation) {
    return new ApiResponse(this.settingService.setStationInfo(request, cfgStation));
  }

  @PostMapping({"/setting/get_plan_factor"})
  public ApiResponse getPlanFactor() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getPlanFactor());
    return apiResponse;
  }

  @PostMapping({"/setting/get_day_plan"})
  public ApiResponse getDayPlan() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getDayPlan());
    return apiResponse;
  }

  @PostMapping({"/setting/set_day_plan"})
  public ApiResponse setDayPlan(HttpServletRequest request, @RequestBody List<ScheduleDay> scheduleDayList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setDayPlan(request, scheduleDayList));
    return apiResponse;
  }

  @PostMapping({"/setting/get_month_plan"})
  public ApiResponse getMonthPlan() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getMonthPlan());
    return apiResponse;
  }

  @PostMapping({"/setting/set_month_plan"})
  public ApiResponse setMonthPlan(@RequestBody List<ScheduleMonth> scheduleMonthList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setMonthPlan(scheduleMonthList));
    return apiResponse;
  }

  @PostMapping({"/setting/get_sample_plan"})
  public ApiResponse getSamplePlan() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getSamplePlan());
    return apiResponse;
  }

  @PostMapping({"/setting/get_sample"})
  public ApiResponse getSample() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getSample());
    return apiResponse;
  }

  @PostMapping({"/setting/get_sample_modes"})
  public ApiResponse getSampleModes() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getSampleModes());
    return apiResponse;
  }

  @PostMapping({"/setting/set_sample_plan"})
  public ApiResponse setSamplePlan(HttpServletRequest request, @RequestBody List<ScheduleSample> scheduleSampleList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setSamplePlan(request, scheduleSampleList));
    return apiResponse;
  }

  @PostMapping({"/setting/set_sample"})
  public ApiResponse setSample(@RequestBody CfgSample cfgSample) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setSample(cfgSample));
    return apiResponse;
  }

  @PostMapping({"/setting/do_sample_quantity"})
  public ApiResponse doSampleQuantity() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.doSampleQuantity());
    return apiResponse;
  }

  @PostMapping({"/setting/do_sample_plc"})
  public ApiResponse doSamplePlc() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.doSamplePlc());
    return apiResponse;
  }

  @PostMapping({"/setting/do_sample_empty"})
  public ApiResponse doSampleEmpty() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.doSampleEmpty());
    return apiResponse;
  }

  @PostMapping({"/setting/do_sample_state"})
  public ApiResponse doSampleState() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.doSampleState());
    return apiResponse;
  }

  @PostMapping({"/setting/do_sample_reset"})
  public ApiResponse doSampleReset() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.doSampleReset());
    return apiResponse;
  }

  @PostMapping({"/setting/do_control_sample"})
  public ApiResponse doControlSample(@RequestBody SampleControl sampleControl) {
    boolean bResult = this.settingService.doControlSample(sampleControl);
    ApiResponse apiResponse = new ApiResponse(bResult);
    apiResponse.setSuccess(bResult);
    return apiResponse;
  }

  @PostMapping({"/setting/do_control_empty"})
  public ApiResponse doControlEmpty(@RequestBody SampleControl sampleControl) {
    boolean bResult = this.settingService.doControlEmpty(sampleControl);
    ApiResponse apiResponse = new ApiResponse(bResult);
    apiResponse.setSuccess(bResult);
    return apiResponse;
  }

  @PostMapping({"/setting/do_script_jump"})
  public ApiResponse doScriptJump() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.doScriptJump());
    return apiResponse;
  }

  @PostMapping({"/setting/get_running"})
  public ApiResponse getRunning() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getRunning());
    return apiResponse;
  }

  @PostMapping({"/setting/set_running"})
  public ApiResponse setRunning(HttpServletRequest request, @RequestBody RunningInfo runningInfo) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setRunning(request, runningInfo));
    return apiResponse;
  }

  @PostMapping({"/setting/get_upload_protocols"})
  public ApiResponse getUploadProtocols() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getUploadProtocols());
    return apiResponse;
  }

  @PostMapping({"/setting/get_upload"})
  public ApiResponse getUpload() {
    return this.getNetworkUpload();
  }

  @PostMapping({"/setting/get_network_upload"})
  public ApiResponse getNetworkUpload() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getNetworkUpload());
    return apiResponse;
  }

  @PostMapping({"/setting/set_upload"})
  public ApiResponse setUpload(HttpServletRequest request, @RequestBody List<UploadInfo> uploadInfos) {
    return this.setNetworkUpload(request, uploadInfos);
  }

  @PostMapping({"/setting/set_network_upload"})
  public ApiResponse setNetworkUpload(HttpServletRequest request, @RequestBody List<UploadInfo> uploadInfos) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setNetworkUpload(request, uploadInfos));
    return apiResponse;
  }

  @PostMapping({"/setting/get_com_upload"})
  public ApiResponse getComUpload() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getComUpload());
    return apiResponse;
  }

  @PostMapping({"/setting/set_com_upload"})
  public ApiResponse setComUpload(HttpServletRequest request, @RequestBody List<UploadInfo> uploadInfos) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setComUpload(request, uploadInfos));
    return apiResponse;
  }

  @PostMapping({"/setting/get_standard_info"})
  public ApiResponse getStandardInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getStandardInfo());
    return apiResponse;
  }

  @PostMapping({"/setting/set_standard_info"})
  public ApiResponse setStandardInfo(HttpServletRequest request, @RequestBody List<SetStdVal> setStdValList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setStandardInfo(request, setStdValList));
    return apiResponse;
  }

  @PostMapping({"/setting/get_recovery_info"})
  public ApiResponse getRecoveryInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getRecoveryInfo());
    return apiResponse;
  }

  @PostMapping({"/setting/set_recovery_info"})
  public ApiResponse setRecoveryInfo(HttpServletRequest request, @RequestBody List<SetRcvrVal> setRcvrValList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setRecoveryInfo(request, setRcvrValList));
    return apiResponse;
  }

  @PostMapping({"/setting/get_span_info"})
  public ApiResponse getSpanInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getSpanInfo());
    return apiResponse;
  }

  @PostMapping({"/setting/set_span_info"})
  public ApiResponse setSpanInfo(HttpServletRequest request, @RequestBody List<SetSpanVal> setSpanValList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setSpanInfo(request, setSpanValList));
    return apiResponse;
  }

  @PostMapping({"/setting/get_error_info"})
  public ApiResponse getErrorInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getErrorInfo());
    return apiResponse;
  }

  @PostMapping({"/setting/set_error_info"})
  public ApiResponse setErrorInfo(HttpServletRequest request, @RequestBody List<SetErrorVal> setErrorValList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setErrorInfo(request, setErrorValList));
    return apiResponse;
  }

  @PostMapping({"/setting/get_drift_info"})
  public ApiResponse getDriftInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getDriftInfo());
    return apiResponse;
  }

  @PostMapping({"/setting/set_drift_info"})
  public ApiResponse setDriftInfo(HttpServletRequest request, @RequestBody List<SetDriftVal> setDriftValList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setDriftInfo(request, setDriftValList));
    return apiResponse;
  }

  @PostMapping({"/admin/get_user_info"})
  public ApiResponse getUserInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getUserInfo());
    return apiResponse;
  }

  @PostMapping({"/setting/set_user_info"})
  public ApiResponse setUserInfo(HttpServletRequest request, @RequestBody List<SetUserInfo> setUserInfoList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setUserInfo(request, setUserInfoList));
    return apiResponse;
  }

  @PostMapping({"/setting/update_login_time"})
  public ApiResponse updateLoginTime(@RequestBody LoginTime loginTime) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.updateLoginTime(loginTime));
    return apiResponse;
  }

  @PostMapping({"/setting/get_com_info"})
  public ApiResponse getComInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getComInfo());
    return apiResponse;
  }

  @PostMapping({"/setting/set_com_info"})
  public ApiResponse setComInfo(HttpServletRequest request, @RequestBody List<SetComInfo> setComInfoList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setComInfo(request, setComInfoList));
    return apiResponse;
  }

  @PostMapping({"/setting/active_com_info"})
  public ApiResponse activeComInfo(HttpServletRequest request, @RequestBody List<SetComInfo> setComInfoList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.activeComInfo(request, setComInfoList));
    return apiResponse;
  }

  @PostMapping({"/setting/get_net_info"})
  public ApiResponse getNetInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getNetInfo());
    return apiResponse;
  }

  @PostMapping({"/setting/set_net_info"})
  public ApiResponse setNetInfo(HttpServletRequest request, @RequestBody List<SetNetInfo> setNetInfoList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setNetInfo(request, setNetInfoList));
    return apiResponse;
  }

  @PostMapping({"/setting/active_net_info"})
  public ApiResponse activeNetInfo(HttpServletRequest request, @RequestBody List<SetNetInfo> setNetInfoList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.activeNetInfo(request, setNetInfoList));
    return apiResponse;
  }

  @PostMapping({"/setting/get_state_info"})
  public ApiResponse getStateInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getStateInfo());
    return apiResponse;
  }

  @PostMapping({"/setting/get_devtype_list"})
  public ApiResponse getDevTypeInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getDevTypeList());
    return apiResponse;
  }

  @PostMapping({"/setting/get_protocol_list"})
  public ApiResponse getProtocolInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getProtocolList());
    return apiResponse;
  }

  @PostMapping({"/setting/get_relate_list"})
  public ApiResponse getRelateInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getRelateNameList());
    return apiResponse;
  }

  @PostMapping({"/setting/get_interface_list"})
  public ApiResponse getInterfaceInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getInterfaceList());
    return apiResponse;
  }

  @PostMapping({"/setting/get_instrument_info"})
  public ApiResponse getInstrumentInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getInstrumentInfo());
    return apiResponse;
  }

  @PostMapping({"/setting/set_instrument_info"})
  public ApiResponse setInstrumentInfo(HttpServletRequest request, @RequestBody List<SetInstrumentInfo> setInstrumentInfoList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setInstrumentInfo(request, setInstrumentInfoList));
    return apiResponse;
  }

  @PostMapping({"/setting/active_instrument_info"})
  public ApiResponse activeInstrumentInfo(HttpServletRequest request, @RequestBody List<SetInstrumentInfo> setInstrumentInfoList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.activeInstrumentInfo(request, setInstrumentInfoList));
    return apiResponse;
  }

  @PostMapping({"/setting/get_unit_list"})
  public ApiResponse getUnitList() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getUnitList());
    return apiResponse;
  }

  @PostMapping({"/setting/get_dev_list"})
  public ApiResponse getDevList() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getDevList());
    return apiResponse;
  }

  @PostMapping({"/setting/get_factor_list"})
  public ApiResponse getFactorList() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getFactorList());
    return apiResponse;
  }

  @PostMapping({"/setting/get_factor_info"})
  public ApiResponse getFactorInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getFactorInfo());
    return apiResponse;
  }

  @PostMapping({"/setting/set_factor_info"})
  public ApiResponse setFactorInfo(HttpServletRequest request, @RequestBody List<SetFactorInfo> setFactorInfoList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setFactorInfo(request, setFactorInfoList));
    return apiResponse;
  }

  @PostMapping({"/setting/active_factor_info"})
  public ApiResponse activeFactorInfo(HttpServletRequest request, @RequestBody List<SetFactorInfo> setFactorInfoList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.activeFactorInfo(request, setFactorInfoList));
    return apiResponse;
  }

  @PostMapping({"/setting/get_device_param"})
  public ApiResponse getDeviceParam() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getDeviceParam());
    return apiResponse;
  }

  @PostMapping({"/setting/set_device_param"})
  public ApiResponse setDeviceParam(HttpServletRequest request, @RequestBody SetDeviceParam setDeviceParam) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setDeviceParam(request, setDeviceParam));
    return apiResponse;
  }

  @PostMapping({"/setting/get_plc_info"})
  public ApiResponse getPlcInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getPlcInfo());
    return apiResponse;
  }

  @PostMapping({"/setting/set_plc_info"})
  public ApiResponse setPlcInfo(HttpServletRequest request, @RequestBody PlcInfo plcInfo) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setPlcInfo(request, plcInfo));
    return apiResponse;
  }

  @PostMapping({"/setting/get_plc_q"})
  public ApiResponse getPlcQInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getPlcQInfo());
    return apiResponse;
  }

  @PostMapping({"/setting/set_plc_q"})
  public ApiResponse setPlcQInfo(HttpServletRequest request, @RequestBody List<PlcQInfo> setPlcQInfoList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setPlcQInfo(request, setPlcQInfoList));
    return apiResponse;
  }

  @PostMapping({"/setting/get_plc_i"})
  public ApiResponse getPlcIInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getPlcIInfo());
    return apiResponse;
  }

  @PostMapping({"/setting/set_plc_i"})
  public ApiResponse setPlcIInfo(HttpServletRequest request, @RequestBody List<PlcQInfo> setPlcQInfoList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setPlcIInfo(request, setPlcQInfoList));
    return apiResponse;
  }

  @PostMapping({"/setting/get_plc_ai"})
  public ApiResponse getPlcAIInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getPlcAIInfo());
    return apiResponse;
  }

  @PostMapping({"/setting/set_plc_ai"})
  public ApiResponse setPlcAIInfo(HttpServletRequest request, @RequestBody List<PlcAIInfo> setPlcAIInfoList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setPlcAIInfo(request, setPlcAIInfoList));
    return apiResponse;
  }

  @PostMapping({"/setting/get_data_format_list"})
  public ApiResponse getDataFormatList() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getDataFormatList());
    return apiResponse;
  }

  @PostMapping({"/setting/get_protocol_info"})
  public ApiResponse getProtocolInfo(@RequestBody String protoType) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getProtocolInfo(protoType));
    return apiResponse;
  }

  @PostMapping({"/setting/get_protocol_query"})
  public ApiResponse getProtocolQuery(@RequestBody ReqUserModbus reqUserModbus) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getProtocolQuery(reqUserModbus));
    return apiResponse;
  }

  @PostMapping({"/setting/set_protocol_query"})
  public ApiResponse setProtocolQuery(HttpServletRequest request, @RequestBody List<ProtocolQuery> protocolQueryList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setProtocolQuery(request, protocolQueryList));
    return apiResponse;
  }

  @PostMapping({"/setting/get_protocol_cmd"})
  public ApiResponse getProtocolCmd(@RequestBody ReqUserModbus reqUserModbus) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getProtocolCmd(reqUserModbus));
    return apiResponse;
  }

  @PostMapping({"/setting/set_protocol_cmd"})
  public ApiResponse setProtocolCmd(HttpServletRequest request, @RequestBody List<ProtocolCmd> protocolCmdList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setProtocolCmd(request, protocolCmdList));
    return apiResponse;
  }

  @PostMapping({"/setting/get_five_check"})
  public ApiResponse getFiveCheckCmd() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getFiveCheck());
    return apiResponse;
  }

  @PostMapping({"/setting/set_five_check"})
  public ApiResponse setFiveCheckCmd(@RequestBody ReqFiveCheck fiveCheck) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setFiveCheck(fiveCheck));
    return apiResponse;
  }

  @PostMapping({"/setting/get_std_check"})
  public ApiResponse getStdCheckCmd() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getStdCheck());
    return apiResponse;
  }

  @PostMapping({"/setting/set_std_check"})
  public ApiResponse setStdCheckCmd(@RequestBody ReqStdCheck stdCheck) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setStdCheck(stdCheck));
    return apiResponse;
  }

  @PostMapping({"/setting/run_std_check"})
  public ApiResponse runStdCheckCmd(@RequestBody ReqStdCheck stdCheck) {
    boolean bResult = this.settingService.runStdCheck(stdCheck);
    ApiResponse apiResponse = new ApiResponse(bResult);
    apiResponse.setSuccess(bResult);
    if (bResult) {
      apiResponse.setMessage("启动成功");
    } else {
      apiResponse.setMessage("启动失败");
    }

    return apiResponse;
  }

  @PostMapping({"/setting/stop_std_check"})
  public ApiResponse stopStdCheckCmd(@RequestBody ReqStdCheck stdCheck) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.stopStdCheck(stdCheck));
    return apiResponse;
  }

  @PostMapping({"/setting/set_dev_model"})
  public ApiResponse setDevModel(@RequestBody DevModel devModel) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setDevModel(devModel));
    return apiResponse;
  }

  @PostMapping({"/setting/set_dev_interval"})
  public ApiResponse setDevInterval(@RequestBody DevInterval devInterval) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setDevInterval(devInterval));
    return apiResponse;
  }

  @PostMapping({"/setting/get_server_list"})
  public ApiResponse getServerList() {
    return this.getServerNetworkList();
  }

  @PostMapping({"/setting/get_server_network_list"})
  public ApiResponse getServerNetworkList() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getServerNetworkList());
    return apiResponse;
  }

  @PostMapping({"/setting/get_server_com_list"})
  public ApiResponse getServerComList() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getServerComList());
    return apiResponse;
  }

  @PostMapping({"/setting/get_rcvr_type"})
  public ApiResponse getRcvrType() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getRcvrType());
    return apiResponse;
  }

  @PostMapping({"/setting/set_rcvr_type"})
  public ApiResponse setRcvyType(HttpServletRequest request, @RequestBody List<RcvrType> rcvrTypeList) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setRcvrType(request, rcvrTypeList));
    return apiResponse;
  }

  @PostMapping({"/setting/get_camera"})
  public ApiResponse getCameraInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getCameraInfo());

    try {
      URI uri = new URI(this.settingService.getCameraInfo().getIp());
      BrowserUtil.browse(uri);
    } catch (Exception var3) {
    }

    return apiResponse;
  }

  @PostMapping({"/setting/get_camera_info"})
  public ApiResponse getCameraInfos() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getCameraInfo());
    return apiResponse;
  }

  @PostMapping({"/setting/set_camera"})
  public ApiResponse setCameraInfo(HttpServletRequest request, @RequestBody CfgCamera cfgCamera) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setCameraInfo(request, cfgCamera));
    return apiResponse;
  }

  @PostMapping({"/setting/load_camera"})
  public ApiResponse loadCameraInfo() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.loadCameraInfo());
    return apiResponse;
  }

  @PostMapping({"/setting/get_five_setting"})
  public ApiResponse getFiveSetting() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getFiveInfo());
    return apiResponse;
  }

  @PostMapping({"/setting/set_five_setting"})
  public ApiResponse setFiveSetting(HttpServletRequest request, @RequestBody ReqFiveInfo reqFiveInfo) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setFiveInfo(request, reqFiveInfo));
    return apiResponse;
  }

  @PostMapping({"/setting/get_schedule_quality"})
  public ApiResponse getScheduleQuality() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getScheduleQuality());
    return apiResponse;
  }

  @PostMapping({"/setting/set_schedule_quality"})
  public ApiResponse setScheduleQuality(HttpServletRequest request, @RequestBody ReqScheduleQuality reqScheduleQuality) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setScheduleQuality(request, reqScheduleQuality));
    return apiResponse;
  }

  @PostMapping({"/setting/get_flow_setting"})
  public ApiResponse getFlowSetting() {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getFlow());
    return apiResponse;
  }

  @PostMapping({"/setting/set_flow_setting"})
  public ApiResponse setFlowSetting(HttpServletRequest request, @RequestBody CfgFlow cfgFlow) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.setFlow(request, cfgFlow));
    return apiResponse;
  }

  @PostMapping({"/setting/get_encryption_code"})
  public ApiResponse getEncryptionCode(@RequestBody String userCode) {
    ApiResponse apiResponse = new ApiResponse(this.settingService.getEncryptionCode(userCode));
    return apiResponse;
  }
}
