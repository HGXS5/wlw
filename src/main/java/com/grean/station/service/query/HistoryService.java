//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.query;

import com.grean.station.dao.CfgMapper;
import com.grean.station.dao.RecMapper;
import com.grean.station.domain.DO.cfg.CfgDev;
import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.cfg.CfgUploadNet;
import com.grean.station.domain.DO.rec.RecAlarmData;
import com.grean.station.domain.DO.rec.RecAlarmDev;
import com.grean.station.domain.DO.rec.RecDataAvg;
import com.grean.station.domain.DO.rec.RecDataFactor;
import com.grean.station.domain.DO.rec.RecDataTime;
import com.grean.station.domain.DO.rec.RecFaultSys;
import com.grean.station.domain.DO.rec.RecInfoDoor;
import com.grean.station.domain.DO.rec.RecLogDev;
import com.grean.station.domain.DO.rec.RecLogPlatform;
import com.grean.station.domain.DO.rec.RecLogSample;
import com.grean.station.domain.DO.rec.RecLogSys;
import com.grean.station.domain.DO.rec.RecLogUser;
import com.grean.station.domain.request.AlarmData;
import com.grean.station.domain.request.AlarmDev;
import com.grean.station.domain.request.DateHead;
import com.grean.station.domain.request.DateRange;
import com.grean.station.domain.request.DeviceInfo;
import com.grean.station.domain.request.DrawLegend;
import com.grean.station.domain.request.FactorInfo;
import com.grean.station.domain.request.HistoryData;
import com.grean.station.domain.request.HistoryDataFlag;
import com.grean.station.domain.request.HistoryDataPkg;
import com.grean.station.domain.request.HistoryUpload;
import com.grean.station.domain.request.LogInfo;
import com.grean.station.domain.request.QueryData;
import com.grean.station.domain.request.ReportData;
import com.grean.station.domain.request.ReqReport;
import com.grean.station.domain.response.ChartVO;
import com.grean.station.domain.response.EChartStackVO;
import com.grean.station.service.MonitorService;
import com.grean.station.upload.UploadInter;
import com.grean.station.utils.ExcelUtil;
import com.grean.station.utils.TimeHelper;
import com.grean.station.utils.Utility;
import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {
    @Autowired
    RecMapper recMapper;
    @Autowired
    CfgMapper cfgMapper;
    @Autowired
    MonitorService monitorService;
    private List<RecDataTime> recTimeList;
    private List<RecDataFactor> recDataList;
    private boolean exitUploadNetwork = false;
    private boolean exitUploadCom = false;

    public HistoryService() {
    }

    @PostConstruct
    public void init() {
    }

    public boolean isExist(String name, List<String> nameList) {
        Iterator var3 = nameList.iterator();

        String strName;
        do {
            if (!var3.hasNext()) {
                return false;
            }

            strName = (String)var3.next();
        } while(!strName.equals(name));

        return true;
    }

    public List<FactorInfo> getHistoryFactorList() {
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        List<FactorInfo> historyFactorList = new ArrayList();
        Iterator var3 = cfgFactorList.iterator();

        while(true) {
            CfgFactor cfgFactor;
            do {
                do {
                    if (!var3.hasNext()) {
                        return historyFactorList;
                    }

                    cfgFactor = (CfgFactor)var3.next();
                } while(!cfgFactor.isUsed());
            } while(!cfgFactor.isFiveType() && !cfgFactor.isParmType() && !cfgFactor.isSysType() && !cfgFactor.isSwType());

            FactorInfo factorInfo = new FactorInfo(cfgFactor.getName(), cfgFactor.getUnit());
            historyFactorList.add(factorInfo);
        }
    }

    public List<DeviceInfo> getHistoryDeviceList() {
        List<CfgDev> cfgDevList = this.cfgMapper.getCfgDevList();
        List<DeviceInfo> historyDeviceList = new ArrayList();
        Iterator var3 = cfgDevList.iterator();

        while(var3.hasNext()) {
            CfgDev cfgDev = (CfgDev)var3.next();
            if (cfgDev.getType().toUpperCase().equals("PARM")) {
                DeviceInfo deviceInfo = new DeviceInfo();
                deviceInfo.setDevName(cfgDev.getName());
                historyDeviceList.add(deviceInfo);
            }
        }

        return historyDeviceList;
    }

    public List<FactorInfo> getRangeFactorList() {
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        List<FactorInfo> rangeFactorList = new ArrayList();
        Iterator var3 = cfgFactorList.iterator();

        while(var3.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor)var3.next();
            if (cfgFactor.isUsed() && cfgFactor.isParmType()) {
                FactorInfo factorInfo = new FactorInfo(cfgFactor.getName(), cfgFactor.getUnit());
                rangeFactorList.add(factorInfo);
            }
        }

        return rangeFactorList;
    }

    public List<FactorInfo> getRealFactorList() {
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        List<FactorInfo> rangeFactorList = new ArrayList();
        Iterator var3 = cfgFactorList.iterator();

        while(var3.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor)var3.next();
            if (cfgFactor.isUsed() && cfgFactor.isFiveType()) {
                FactorInfo factorInfo = new FactorInfo(cfgFactor.getName(), cfgFactor.getUnit());
                rangeFactorList.add(factorInfo);
            }
        }

        return rangeFactorList;
    }

    public List<FactorInfo> getFlowFactorList() {
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        List<FactorInfo> flowFactorList = new ArrayList();
        Iterator var3 = cfgFactorList.iterator();

        while(var3.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor)var3.next();
            if (cfgFactor.isUsed() && cfgFactor.isSwType()) {
                FactorInfo factorInfo = new FactorInfo(cfgFactor.getName(), cfgFactor.getUnit());
                flowFactorList.add(factorInfo);
            }
        }

        if (flowFactorList.size() > 0) {
            return flowFactorList;
        } else {
            return null;
        }
    }

    public List<FactorInfo> getHistoryFactorFiveList() {
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        List<FactorInfo> historyFactorList = new ArrayList();
        Iterator var3 = cfgFactorList.iterator();

        while(var3.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor)var3.next();
            if (cfgFactor.isUsed() && cfgFactor.isFiveType()) {
                FactorInfo factorInfo = new FactorInfo(cfgFactor.getName(), cfgFactor.getUnit());
                historyFactorList.add(factorInfo);
            }
        }

        return historyFactorList;
    }

    public List<ReportData> getHistoryDayReport(DateRange dateRange) {
        dateRange.init();
        List<ReportData> reportDataList = new ArrayList();
        Double[] minData = new Double[dateRange.getHeadName().size()];
        Double[] maxData = new Double[dateRange.getHeadName().size()];
        Double[] avgData = new Double[dateRange.getHeadName().size()];
        int[] dataSize = new int[dateRange.getHeadName().size()];
        List<RecDataTime> recTimeList = this.recMapper.getRecTimeHourListByRange(dateRange);
        ReportData minReportData;
        if (recTimeList != null) {
            Iterator var8 = recTimeList.iterator();

            while(var8.hasNext()) {
                RecDataTime recTime = (RecDataTime)var8.next();
                minReportData = new ReportData();
                minReportData.setTitle(TimeHelper.formatDataTime_HH_mm_ss(recTime.getRecTime()));
                List<RecDataFactor> recDataList = this.recMapper.getRecDataHourListByTime(recTime);

                for(int j = 0; j < dateRange.getHeadName().size(); ++j) {
                    int factorid = this.getFactorID((String)dateRange.getHeadName().get(j));
                    boolean bCheckData = false;
                    Iterator var15 = recDataList.iterator();

                    while(var15.hasNext()) {
                        RecDataFactor recDataFactor = (RecDataFactor)var15.next();
                        if (recDataFactor.getFactorID() == factorid) {
                            bCheckData = true;
                            minReportData.getDataList().add(recDataFactor.getDataValue());
                            if (recDataFactor.getDataValue() != null) {
                                if (minData[j] == null) {
                                    minData[j] = recDataFactor.getDataValue();
                                } else {
                                    minData[j] = Math.min(minData[j], recDataFactor.getDataValue());
                                }

                                if (maxData[j] == null) {
                                    maxData[j] = recDataFactor.getDataValue();
                                } else {
                                    maxData[j] = Math.max(maxData[j], recDataFactor.getDataValue());
                                }

                                if (avgData[j] == null) {
                                    avgData[j] = recDataFactor.getDataValue();
                                } else {
                                    avgData[j] = avgData[j] + recDataFactor.getDataValue();
                                }

                                int var10002 = dataSize[j]++;
                            }
                            break;
                        }
                    }

                    if (!bCheckData) {
                        minReportData.getDataList().add((Double) null);
                    }
                }

                reportDataList.add(minReportData);
            }
        }

        ReportData avgReportData = new ReportData();
        avgReportData.setTitle("日均值");

        for(int i = 0; i < avgData.length; ++i) {
            if (dataSize[i] > 0) {
                avgReportData.getDataList().add(Utility.getFormatDouble(avgData[i] / (double)dataSize[i], 3));
            } else {
                avgReportData.getDataList().add((Double) null);
            }
        }

        ReportData maxReportData = new ReportData();
        maxReportData.setTitle("最大值");

        for(int i = 0; i < maxData.length; ++i) {
            maxReportData.getDataList().add(maxData[i]);
        }

        minReportData = new ReportData();
        minReportData.setTitle("最小值");

        for(int i = 0; i < minData.length; ++i) {
            minReportData.getDataList().add(minData[i]);
        }

        reportDataList.add(avgReportData);
        reportDataList.add(maxReportData);
        reportDataList.add(minReportData);
        return reportDataList;
    }

    public List<ReportData> getHistoryMonthReport(DateRange dateRange) {
        dateRange.init();
        List<ReportData> reportDataList = new ArrayList();
        DateTime dateTime = TimeHelper.getSQLDayTime(dateRange.getStartDate());
        int queryMonth = dateTime.getMonthOfYear();
        Double[] minData = new Double[dateRange.getHeadName().size()];
        Double[] maxData = new Double[dateRange.getHeadName().size()];
        Double[] avgData = new Double[dateRange.getHeadName().size()];
        int[] dataSize = new int[dateRange.getHeadName().size()];
        List<RecDataTime> recTimeList = this.recMapper.getRecTimeDayAvgListByRange(dateRange);

        ReportData reportData;
        int i;
        for(i = 0; i < 31 && dateTime.plusDays(i).getMonthOfYear() == queryMonth; ++i) {
            reportData = new ReportData();
            reportData.setTitle(String.format("%04d-%02d-%02d", dateTime.plusDays(i).getYear(), dateTime.plusDays(i).getMonthOfYear(), dateTime.plusDays(i).getDayOfMonth()));
            boolean bCheckDay = false;
            if (recTimeList != null) {
                Iterator var13 = recTimeList.iterator();

                label108:
                while(var13.hasNext()) {
                    RecDataTime recTime = (RecDataTime)var13.next();
                    if (recTime.getRecTime().getTime() == dateTime.plusDays(i).getMillis()) {
                        bCheckDay = true;
                        List<RecDataAvg> recDataList = this.recMapper.getRecDataDayAvgListByTime(recTime);
                        int j = 0;

                        while(true) {
                            if (j >= dateRange.getHeadName().size()) {
                                break label108;
                            }

                            int factorid = this.getFactorID((String)dateRange.getHeadName().get(j));
                            boolean bCheckData = false;
                            Iterator var19 = recDataList.iterator();

                            while(var19.hasNext()) {
                                RecDataAvg recDataAvg = (RecDataAvg)var19.next();
                                if (recDataAvg.getFactorID() == factorid) {
                                    bCheckData = true;
                                    reportData.getDataList().add(recDataAvg.getDataValue());
                                    if (recDataAvg.getDataValue() != null) {
                                        if (minData[j] == null) {
                                            minData[j] = recDataAvg.getDataValue();
                                        } else {
                                            minData[j] = Math.min(minData[j], recDataAvg.getDataValue());
                                        }

                                        if (maxData[j] == null) {
                                            maxData[j] = recDataAvg.getDataValue();
                                        } else {
                                            maxData[j] = Math.max(maxData[j], recDataAvg.getDataValue());
                                        }

                                        if (avgData[j] == null) {
                                            avgData[j] = recDataAvg.getDataValue();
                                        } else {
                                            avgData[j] = avgData[j] + recDataAvg.getDataValue();
                                        }

                                        int var10002 = dataSize[j]++;
                                    }
                                    break;
                                }
                            }

                            if (!bCheckData) {
                                reportData.getDataList().add((Double) null);
                            }

                            ++j;
                        }
                    }
                }
            }

            if (!bCheckDay) {
                for(i = 0; i < dateRange.getHeadName().size(); ++i) {
                    reportData.getDataList().add((Double) null);
                }
            }

            reportDataList.add(reportData);
        }

        ReportData avgReportData = new ReportData();
        avgReportData.setTitle("月均值");

        for(i = 0; i < avgData.length; ++i) {
            if (dataSize[i] > 0) {
                avgReportData.getDataList().add(Utility.getFormatDouble(avgData[i] / (double)dataSize[i], 3));
            } else {
                avgReportData.getDataList().add((Double) null);
            }
        }

        reportData = new ReportData();
        reportData.setTitle("最大值");

        for(i = 0; i < maxData.length; ++i) {
            reportData.getDataList().add(maxData[i]);
        }

        ReportData minReportData = new ReportData();
        minReportData.setTitle("最小值");

        for(i = 0; i < minData.length; ++i) {
            minReportData.getDataList().add(minData[i]);
        }

        reportDataList.add(avgReportData);
        return reportDataList;
    }

    public List<ReportData> getHistoryYearReport(DateRange dateRange) {
        dateRange.init();
        List<ReportData> reportDataList = new ArrayList();
        DateTime dateTime = TimeHelper.getSQLDayTime(dateRange.getStartDate());
        int queryYear = dateTime.getMonthOfYear();
        Double[] minData = new Double[dateRange.getHeadName().size()];
        Double[] maxData = new Double[dateRange.getHeadName().size()];
        Double[] avgData = new Double[dateRange.getHeadName().size()];
        int[] dataSize = new int[dateRange.getHeadName().size()];
        List<RecDataTime> recTimeList = this.recMapper.getRecTimeMonthAvgListByRange(dateRange);

        ReportData reportData;
        int i;
        for(i = 0; i < 12 && dateTime.plusDays(i).getMonthOfYear() == queryYear; ++i) {
            reportData = new ReportData();
            reportData.setTitle(String.format("%04d-%02d", dateTime.plusMonths(i).getYear(), dateTime.plusMonths(i).getMonthOfYear()));
            boolean bCheckMonth = false;
            if (recTimeList != null) {
                Iterator var13 = recTimeList.iterator();

                label108:
                while(var13.hasNext()) {
                    RecDataTime recTime = (RecDataTime)var13.next();
                    if (recTime.getRecTime().getTime() == dateTime.plusMonths(i).getMillis()) {
                        bCheckMonth = true;
                        List<RecDataAvg> recDataList = this.recMapper.getRecDataMonthAvgListByTime(recTime);
                        int j = 0;

                        while(true) {
                            if (j >= dateRange.getHeadName().size()) {
                                break label108;
                            }

                            int factorid = this.getFactorID((String)dateRange.getHeadName().get(j));
                            boolean bCheckData = false;
                            Iterator var19 = recDataList.iterator();

                            while(var19.hasNext()) {
                                RecDataAvg recDataAvg = (RecDataAvg)var19.next();
                                if (recDataAvg.getFactorID() == factorid) {
                                    bCheckData = true;
                                    reportData.getDataList().add(recDataAvg.getDataValue());
                                    if (recDataAvg.getDataValue() != null) {
                                        if (minData[j] == null) {
                                            minData[j] = recDataAvg.getDataValue();
                                        } else {
                                            minData[j] = Math.min(minData[j], recDataAvg.getDataValue());
                                        }

                                        if (maxData[j] == null) {
                                            maxData[j] = recDataAvg.getDataValue();
                                        } else {
                                            maxData[j] = Math.max(maxData[j], recDataAvg.getDataValue());
                                        }

                                        if (avgData[j] == null) {
                                            avgData[j] = recDataAvg.getDataValue();
                                        } else {
                                            avgData[j] = avgData[j] + recDataAvg.getDataValue();
                                        }

                                        int var10002 = dataSize[j]++;
                                    }
                                    break;
                                }
                            }

                            if (!bCheckData) {
                                reportData.getDataList().add((Double) null);
                            }

                            ++j;
                        }
                    }
                }
            }

            if (!bCheckMonth) {
                for(i = 0; i < dateRange.getHeadName().size(); ++i) {
                    reportData.getDataList().add((Double) null);
                }
            }

            reportDataList.add(reportData);
        }

        ReportData avgReportData = new ReportData();
        avgReportData.setTitle("年均值");

        for(i = 0; i < avgData.length; ++i) {
            if (dataSize[i] > 0) {
                avgReportData.getDataList().add(Utility.getFormatDouble(avgData[i] / (double)dataSize[i], 3));
            } else {
                avgReportData.getDataList().add((Double) null);
            }
        }

        reportData = new ReportData();
        reportData.setTitle("最大值");

        for(i = 0; i < maxData.length; ++i) {
            reportData.getDataList().add(maxData[i]);
        }

        ReportData minReportData = new ReportData();
        minReportData.setTitle("最小值");

        for(i = 0; i < minData.length; ++i) {
            minReportData.getDataList().add(minData[i]);
        }

        reportDataList.add(avgReportData);
        return reportDataList;
    }

    public ByteArrayOutputStream exportHistoryReport(DateRange dateRange, int type) {
        dateRange.setStart((Integer)null);
        dateRange.initDate();
        List<FactorInfo> thead_list = this.getHistoryFactorList();
        String safeName;
        switch(type) {
            case 1:
                safeName = WorkbookUtil.createSafeSheetName("日报表");
                break;
            case 2:
                safeName = WorkbookUtil.createSafeSheetName("月报表");
                break;
            case 3:
                safeName = WorkbookUtil.createSafeSheetName("年报表");
                break;
            default:
                return null;
        }

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(safeName);
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("时间\\参数");
        sheet.autoSizeColumn(0);
        sheet.setColumnWidth(0, sheet.getColumnWidth(0) + 500);

        int row_index;
        for(row_index = 0; row_index < thead_list.size(); ++row_index) {
            String cell;
            if (((FactorInfo)thead_list.get(row_index)).getFactorUnit().length() > 0) {
                cell = ((FactorInfo)thead_list.get(row_index)).getFactorName() + "(" + ((FactorInfo)thead_list.get(row_index)).getFactorUnit() + ")";
            } else {
                cell = ((FactorInfo)thead_list.get(row_index)).getFactorName();
            }

            row.createCell(row_index + 1).setCellValue(cell);
            sheet.autoSizeColumn(row_index + 1);
            sheet.setColumnWidth(row_index + 1, sheet.getColumnWidth(row_index) + 500);
        }

        row_index = 1;
        List tbody;
        switch(type) {
            case 1:
                tbody = this.getHistoryDayReport(dateRange);
                break;
            case 2:
                tbody = this.getHistoryMonthReport(dateRange);
                break;
            case 3:
                tbody = this.getHistoryYearReport(dateRange);
                break;
            default:
                return null;
        }

        if (tbody != null && tbody.size() > 0) {
            for(Iterator var10 = tbody.iterator(); var10.hasNext(); ++row_index) {
                ReportData reportData = (ReportData)var10.next();
                XSSFRow row_body = sheet.createRow(row_index);
                row_body.createCell(0).setCellValue(reportData.getTitle());

                for(int i = 0; i < reportData.getDataList().size(); ++i) {
                    if (reportData.getDataList().get(i) == null) {
                        row_body.createCell(i + 1).setCellValue("");
                    } else {
                        row_body.createCell(i + 1).setCellValue(((Double)reportData.getDataList().get(i)).toString());
                    }
                }
            }
        }

        return ExcelUtil.getByteArrayOutputStream(wb);
    }

    public List<ReqReport> getHistoryReport(DateRange dateRange) {
        dateRange.init();
        List<ReqReport> reportList = new ArrayList();

        for(int i = 0; i < dateRange.getHeadName().size(); ++i) {
            ReqReport reqReport = new ReqReport();
            reportList.add(reqReport);
        }

        this.recTimeList = this.recMapper.getRecTimeHourListByRange(dateRange);
        Iterator var8 = this.recTimeList.iterator();

        while(var8.hasNext()) {
            RecDataTime recTime = (RecDataTime)var8.next();
            this.recDataList = this.recMapper.getRecDataHourListByTime(recTime);

            for(int i = 0; i < dateRange.getHeadName().size(); ++i) {
                int factorid = this.getFactorID((String)dateRange.getHeadName().get(i));
                Double dTmp = this.getDataByID(factorid);
                if (dTmp != null) {
                    if (((ReqReport)reportList.get(i)).getDataMax() == null) {
                        ((ReqReport)reportList.get(i)).setDataMax(dTmp);
                    } else if (((ReqReport)reportList.get(i)).getDataMax() < dTmp) {
                        ((ReqReport)reportList.get(i)).setDataMax(dTmp);
                    }

                    if (((ReqReport)reportList.get(i)).getDataMin() == null) {
                        ((ReqReport)reportList.get(i)).setDataMin(dTmp);
                    } else if (((ReqReport)reportList.get(i)).getDataMin() > dTmp) {
                        ((ReqReport)reportList.get(i)).setDataMin(dTmp);
                    }

                    if (((ReqReport)reportList.get(i)).getDataAvg() == null) {
                        ((ReqReport)reportList.get(i)).setDataAvg(dTmp);
                    } else {
                        dTmp = (((ReqReport)reportList.get(i)).getDataAvg() + dTmp) / 2.0D;
                        ((ReqReport)reportList.get(i)).setDataAvg(Utility.getFormatDouble(dTmp, 3));
                    }
                }
            }
        }

        return reportList;
    }

    public HistoryDataFlag getHistoryHourDataFlag() {
        HistoryDataFlag historyDataFlag = new HistoryDataFlag();
        historyDataFlag.setDataFlag(this.monitorService.isHistoryDataFlag());
        return historyDataFlag;
    }

    public HistoryDataPkg getHistoryDataList(DateRange dateRange, int dataType) {
        dateRange.init();
        List<Integer> factorIdList = new ArrayList();

        int id;
        for(id = 0; id < dateRange.getHeadName().size(); ++id) {
            int factorid = this.getFactorID((String)dateRange.getHeadName().get(id));
            factorIdList.add(factorid);
        }

        int timeListSize;
        switch(dataType) {
            case 1:
                this.recTimeList = this.recMapper.getRecTimeMinListByRange(dateRange);
                timeListSize = this.recTimeList.size();
                if (timeListSize > 0) {
                    this.recDataList = this.recMapper.getRecDataMinListByRange(dateRange);
                } else {
                    this.recDataList = null;
                }
                break;
            case 2:
                this.recTimeList = this.recMapper.getRecTimeHourListByRange(dateRange);
                timeListSize = this.recTimeList.size();
                if (timeListSize > 0) {
                    this.recDataList = this.recMapper.getRecDataHourListByIdRange(((RecDataTime)this.recTimeList.get(0)).getId(), ((RecDataTime)this.recTimeList.get(timeListSize - 1)).getId());
                } else {
                    this.recDataList = null;
                }
                break;
            case 3:
                this.recTimeList = this.recMapper.getRecTimeFiveListByRange(dateRange);
                timeListSize = this.recTimeList.size();
                if (timeListSize > 0) {
                    this.recDataList = this.recMapper.getRecDataFiveListByIdRange(((RecDataTime)this.recTimeList.get(0)).getId(), ((RecDataTime)this.recTimeList.get(timeListSize - 1)).getId());
                }
                break;
            case 300:
                this.recTimeList = this.recMapper.getRecTimeRangeListByRange(dateRange);
                timeListSize = this.recTimeList.size();
                if (timeListSize > 0) {
                    this.recDataList = this.recMapper.getRecDataRangeListByIdRange(((RecDataTime)this.recTimeList.get(0)).getId(), ((RecDataTime)this.recTimeList.get(timeListSize - 1)).getId());
                } else {
                    this.recDataList = null;
                }
                break;
            case 400:
                this.recTimeList = this.recMapper.getRecTimeFlowListByRange(dateRange);
                timeListSize = this.recTimeList.size();
                if (timeListSize > 0) {
                    this.recDataList = this.recMapper.getRecDataFlowListByIdRange(((RecDataTime)this.recTimeList.get(0)).getId(), ((RecDataTime)this.recTimeList.get(timeListSize - 1)).getId());
                } else {
                    this.recDataList = null;
                }
                break;
            default:
                return null;
        }

        id = 1;
        List<HistoryData> historyDataList = new ArrayList();
        if (this.recDataList != null) {
            for(Iterator var8 = this.recTimeList.iterator(); var8.hasNext(); ++id) {
                RecDataTime recTime = (RecDataTime)var8.next();
                List<RecDataFactor> filterList = (List)this.recDataList.stream().filter((recDataFactorx) -> {
                    return recDataFactorx.getRecID() == recTime.getId();
                }).collect(Collectors.toList());
                HistoryData history = new HistoryData();
                history.setId(id);
                history.setTime(recTime.getRecTime());
                List<QueryData> queryDataList = new ArrayList();
                Iterator var13 = factorIdList.iterator();

                while(var13.hasNext()) {
                    Integer factorId = (Integer)var13.next();
                    boolean bExist = false;
                    Iterator var15 = filterList.iterator();

                    while(var15.hasNext()) {
                        RecDataFactor recDataFactor = (RecDataFactor)var15.next();
                        if (recDataFactor.getFactorID() == factorId) {
                            bExist = true;
                            QueryData queryData = new QueryData();
                            queryData.setData(recDataFactor.getDataValue());
                            queryData.setFlag(recDataFactor.getDataFlag());
                            queryDataList.add(queryData);
                            break;
                        }
                    }

                    if (!bExist) {
                        queryDataList.add((QueryData) null);
                    }
                }

                history.setDataList(queryDataList);
                var13 = this.monitorService.getCfgUploadNetList().iterator();

                while(var13.hasNext()) {
                    CfgUploadNet cfgUploadNet = (CfgUploadNet)var13.next();
                    if (cfgUploadNet.isUsed()) {
                        HistoryUpload historyUpload = new HistoryUpload();
                        historyUpload.setId(cfgUploadNet.getId());
                        historyUpload.setName(cfgUploadNet.getName());
                        switch(cfgUploadNet.getId()) {
                            case 1:
                                historyUpload.setTime(recTime.getSendTime1());
                                break;
                            case 2:
                                historyUpload.setTime(recTime.getSendTime2());
                                break;
                            case 3:
                                historyUpload.setTime(recTime.getSendTime3());
                                break;
                            case 4:
                                historyUpload.setTime(recTime.getSendTime4());
                                break;
                            case 5:
                                historyUpload.setTime(recTime.getSendTime5());
                                break;
                            case 6:
                                historyUpload.setTime(recTime.getSendTime6());
                                break;
                            case 7:
                                historyUpload.setTime(recTime.getSendTime7());
                                break;
                            case 8:
                                historyUpload.setTime(recTime.getSendTime8());
                                break;
                            case 9:
                                historyUpload.setTime(recTime.getSendTime9());
                                break;
                            case 10:
                                historyUpload.setTime(recTime.getSendTime10());
                        }

                        history.getUploadList().add(historyUpload);
                    }
                }

                historyDataList.add(history);
            }
        }

        if (dateRange.getDataFlag() != null) {
            this.monitorService.setHistoryDataFlag(dateRange.getDataFlag());
        }

        HistoryDataPkg historyDataPkg = new HistoryDataPkg();
        historyDataPkg.setHistoryData(historyDataList);
        historyDataPkg.setDataFlag(this.monitorService.isHistoryDataFlag());
        return historyDataPkg;
    }

    public ChartVO getHistoryDataDraw(DateRange dateRange, int dataType) {
        dateRange.init();
        switch(dataType) {
            case 1:
                this.recTimeList = this.recMapper.getRecTimeMinListByRange(dateRange);
                break;
            case 2:
                this.recTimeList = this.recMapper.getRecTimeHourListByRange(dateRange);
                break;
            case 3:
                this.recTimeList = this.recMapper.getRecTimeFiveListByRange(dateRange);
                break;
            default:
                return null;
        }

        ChartVO chartVO = new ChartVO();
        if (this.recTimeList != null && !this.recTimeList.isEmpty()) {
            List<String> names = dateRange.getHeadName();
            ArrayList drawLegendList;
            if (dateRange.getFactorName() != null) {
                names.clear();
                names.add(dateRange.getFactorName());
                CfgFactor cfgFactor = this.getFactor(dateRange.getFactorName());
                if (cfgFactor != null) {
                    drawLegendList = new ArrayList();
                    DrawLegend drawLegend5;
                    if (cfgFactor.getLevel1() != 0.0D) {
                        drawLegend5 = new DrawLegend("一类水标准" + cfgFactor.getLevel1(), cfgFactor.getLevel1(), "#000000");
                        drawLegendList.add(drawLegend5);
                    }

                    if (cfgFactor.getLevel2() != 0.0D) {
                        drawLegend5 = new DrawLegend("二类水标准" + cfgFactor.getLevel2(), cfgFactor.getLevel2(), "#000000");
                        drawLegendList.add(drawLegend5);
                    }

                    if (cfgFactor.getLevel3() != 0.0D) {
                        drawLegend5 = new DrawLegend("三类水标准" + cfgFactor.getLevel3(), cfgFactor.getLevel3(), "#000000");
                        drawLegendList.add(drawLegend5);
                    }

                    if (cfgFactor.getLevel4() != 0.0D) {
                        drawLegend5 = new DrawLegend("四类水标准" + cfgFactor.getLevel4(), cfgFactor.getLevel4(), "#000000");
                        drawLegendList.add(drawLegend5);
                    }

                    if (cfgFactor.getLevel5() != 0.0D) {
                        drawLegend5 = new DrawLegend("五类水标准" + cfgFactor.getLevel5(), cfgFactor.getLevel5(), "#000000");
                        drawLegendList.add(drawLegend5);
                    }

                    chartVO.setDrawLegends(drawLegendList);
                }
            }

            List<Timestamp> times = new ArrayList();
            drawLegendList = new ArrayList();
            Iterator var15 = names.iterator();

            while(var15.hasNext()) {
                String name = (String)var15.next();
                EChartStackVO eChartStackVO = new EChartStackVO();
                List<Double> dataList = new ArrayList();
                eChartStackVO.setName(name);
                eChartStackVO.setData(dataList);
                drawLegendList.add(eChartStackVO);
            }

            new ArrayList();
            Iterator var14 = this.recTimeList.iterator();

            while(var14.hasNext()) {
                RecDataTime recTime = (RecDataTime)var14.next();
                switch(dataType) {
                    case 1:
                        this.recDataList = this.recMapper.getRecDataMinListByTime(recTime);
                        break;
                    case 2:
                        this.recDataList = this.recMapper.getRecDataHourListByTime(recTime);
                        break;
                    case 3:
                        this.recDataList = this.recMapper.getRecDataFiveListByTime(recTime);
                }

                times.add(recTime.getRecTime());

                for(int i = 0; i < names.size(); ++i) {
                    int factorId = this.getFactorID((String)names.get(i));
                    Double factorVal = this.getDataByID(factorId);
                    ((EChartStackVO)drawLegendList.get(i)).getData().add(factorVal);
                }
            }

            chartVO.setTimes(times);
            chartVO.setNames(names);
            chartVO.setDataset(drawLegendList);
            return chartVO;
        } else {
            chartVO.setDataset((List)null);
            return chartVO;
        }
    }

    public ByteArrayOutputStream exportHistoryReport(DateRange dateRange) {
        dateRange.setStart((Integer)null);
        dateRange.initDate();
        List<FactorInfo> thead_list = this.getHistoryFactorList();
        String safeName = WorkbookUtil.createSafeSheetName("报表");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(safeName);
        XSSFRow row = sheet.createRow(0);

        for(int i = 0; i < thead_list.size(); ++i) {
            String cell;
            if (((FactorInfo)thead_list.get(i)).getFactorUnit().length() > 0) {
                cell = ((FactorInfo)thead_list.get(i)).getFactorName() + "(" + ((FactorInfo)thead_list.get(i)).getFactorUnit() + ")";
            } else {
                cell = ((FactorInfo)thead_list.get(i)).getFactorName();
            }

            row.createCell(i).setCellValue(cell);
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1500);
        }

        XSSFRow row_min = sheet.createRow(1);
        XSSFRow row_max = sheet.createRow(2);
        XSSFRow row_avg = sheet.createRow(3);
        List<ReqReport> tbody = this.getHistoryReport(dateRange);

        for(int i = 0; i < thead_list.size(); ++i) {
            ReqReport reqReport = (ReqReport)tbody.get(i);
            if (reqReport.getDataMin() == null) {
                row_min.createCell(i).setCellValue("");
            } else {
                row_min.createCell(i).setCellValue(reqReport.getDataMin());
            }

            if (reqReport.getDataMax() == null) {
                row_max.createCell(i).setCellValue("");
            } else {
                row_max.createCell(i).setCellValue(reqReport.getDataMax());
            }

            if (reqReport.getDataAvg() == null) {
                row_avg.createCell(i).setCellValue("");
            } else {
                row_avg.createCell(i).setCellValue(reqReport.getDataAvg());
            }
        }

        return ExcelUtil.getByteArrayOutputStream(wb);
    }

    public ByteArrayOutputStream exportHistoryData(DateRange dateRange, int dataType) {
        dateRange.setStart((Integer)null);
        dateRange.initDate();
        List<FactorInfo> thead_list = new ArrayList();
        String safeName = "";
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        switch(dataType) {
            case 1:
                thead_list = this.getRealFactorList();
                safeName = WorkbookUtil.createSafeSheetName("分钟数据");
                break;
            case 2:
                thead_list = this.getHistoryFactorList();
                safeName = WorkbookUtil.createSafeSheetName("小时数据");
                break;
            case 3:
                thead_list = this.getHistoryFactorFiveList();
                safeName = WorkbookUtil.createSafeSheetName("五参数数据");
                break;
            case 300:
                thead_list = this.getRangeFactorList();
                safeName = WorkbookUtil.createSafeSheetName("超量程数据");
                break;
            case 400:
                thead_list = this.getFlowFactorList();
                safeName = WorkbookUtil.createSafeSheetName("水文数据");
        }

        FactorInfo factorTime = new FactorInfo("时间", "");
        FactorInfo factorID = new FactorInfo("编号", "");
        ((List)thead_list).add(0, factorTime);
        ((List)thead_list).add(0, factorID);
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(safeName);
        XSSFRow row = sheet.createRow(0);

        int row_index;
        for(row_index = 0; row_index < ((List)thead_list).size(); ++row_index) {
            String cell;
            if (((FactorInfo)((List)thead_list).get(row_index)).getFactorUnit().length() > 0) {
                cell = ((FactorInfo)((List)thead_list).get(row_index)).getFactorName() + "(" + ((FactorInfo)((List)thead_list).get(row_index)).getFactorUnit() + ")";
            } else {
                cell = ((FactorInfo)((List)thead_list).get(row_index)).getFactorName();
            }

            row.createCell(row_index).setCellValue(cell);
            sheet.autoSizeColumn(row_index);
            sheet.setColumnWidth(row_index, sheet.getColumnWidth(row_index) + 1500);
        }

        row_index = 1;
        List<HistoryData> tbody = this.getHistoryDataList(dateRange, dataType).getHistoryData();
        if (tbody != null && tbody.size() > 0) {
            for(Iterator var13 = tbody.iterator(); var13.hasNext(); ++row_index) {
                HistoryData history = (HistoryData)var13.next();
                XSSFRow row_body = sheet.createRow(row_index);
                row_body.createCell(0).setCellValue((double)row_index);
                row_body.createCell(1).setCellValue(formatter.print(new DateTime(history.getTime())));

                for(int i = 0; i < ((List)thead_list).size() - 2; ++i) {
                    if (history.getDataList().get(i) == null) {
                        row_body.createCell(2 + i).setCellValue("");
                    } else {
                        row_body.createCell(2 + i).setCellValue(((QueryData)history.getDataList().get(i)).getData());
                    }
                }
            }
        }

        return ExcelUtil.getByteArrayOutputStream(wb);
    }

    private CfgFactor getFactor(String factorName) {
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();

        for(int i = 0; i < cfgFactorList.size(); ++i) {
            if (factorName.equals(((CfgFactor)cfgFactorList.get(i)).getName())) {
                return (CfgFactor)cfgFactorList.get(i);
            }
        }

        return null;
    }

    private CfgFactor getFactor(int factorID) {
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        Iterator var3 = cfgFactorList.iterator();

        CfgFactor cfgFactor;
        do {
            if (!var3.hasNext()) {
                return null;
            }

            cfgFactor = (CfgFactor)var3.next();
        } while(cfgFactor.getId() != factorID);

        return cfgFactor;
    }

    private int getFactorID(String factorName) {
        int iResult = 0;
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();

        for(int i = 0; i < cfgFactorList.size(); ++i) {
            if (factorName.equals(((CfgFactor)cfgFactorList.get(i)).getName())) {
                iResult = ((CfgFactor)cfgFactorList.get(i)).getId();
                break;
            }
        }

        return iResult;
    }

    private String getFactorName(int factorID) {
        String strResult = null;
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();

        for(int i = 0; i < cfgFactorList.size(); ++i) {
            if (((CfgFactor)cfgFactorList.get(i)).getId() == factorID) {
                strResult = ((CfgFactor)cfgFactorList.get(i)).getName();
                break;
            }
        }

        return strResult;
    }

    private String getDevNameByFactor(int factorID) {
        String strResult = null;
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        List<CfgDev> cfgDevList = this.cfgMapper.getCfgDevList();
        Iterator var5 = cfgFactorList.iterator();

        while(var5.hasNext()) {
            CfgFactor cfgFactor = (CfgFactor)var5.next();
            if (cfgFactor.getId() == factorID) {
                Iterator var7 = cfgDevList.iterator();

                CfgDev cfgDev;
                do {
                    if (!var7.hasNext()) {
                        return strResult;
                    }

                    cfgDev = (CfgDev)var7.next();
                } while(cfgDev.getId() != cfgFactor.getDevID());

                strResult = cfgDev.getName();
                break;
            }
        }

        return strResult;
    }

    private Double getDataByID(int factorID) {
        Double dResult = null;

        for(int j = 0; j < this.recDataList.size(); ++j) {
            if (((RecDataFactor)this.recDataList.get(j)).getFactorID() == factorID) {
                dResult = ((RecDataFactor)this.recDataList.get(j)).getDataValue();
                break;
            }
        }

        return dResult;
    }

    public List<AlarmData> getAlarmDataList(DateRange dateRange) {
        List<AlarmData> alarmDataList = new ArrayList();
        List<RecAlarmData> recAlarmDataList = this.recMapper.getRecAlarmDataListByRange(dateRange);
        int iIndex = 1;

        for(Iterator var5 = recAlarmDataList.iterator(); var5.hasNext(); ++iIndex) {
            RecAlarmData recAlarmData = (RecAlarmData)var5.next();
            AlarmData alarmData = new AlarmData();
            alarmData.setId(iIndex);
            alarmData.setTime(recAlarmData.getRecTime());
            CfgFactor cfgFactor = this.getFactor(recAlarmData.getFactorID());
            alarmData.setFactorName(cfgFactor.getName());
            alarmData.setTestValue(Double.parseDouble(String.format("%." + cfgFactor.getDecimals() + "f", recAlarmData.getAlarmValue())));
            alarmData.setAlarmValue(recAlarmData.getAlarmLimit());
            alarmData.setAlarmType(recAlarmData.getAlarmType());
            alarmDataList.add(alarmData);
        }

        return alarmDataList;
    }

    public ByteArrayOutputStream exportAlarmData(DateRange dateRange) {
        dateRange.setStart((Integer)null);
        dateRange.initDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String safeName = WorkbookUtil.createSafeSheetName("报警数据");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(safeName);
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("时间");
        row.createCell(1).setCellValue("因子");
        row.createCell(2).setCellValue("测量值");
        row.createCell(3).setCellValue("报警值");

        int row_index;
        for(row_index = 0; row_index < 4; ++row_index) {
            sheet.autoSizeColumn(row_index);
            sheet.setColumnWidth(row_index, sheet.getColumnWidth(row_index) + 1500);
        }

        row_index = 1;
        List<AlarmData> tbody = this.getAlarmDataList(dateRange);
        if (tbody != null && tbody.size() > 0) {
            for(Iterator var9 = tbody.iterator(); var9.hasNext(); ++row_index) {
                AlarmData alarmData = (AlarmData)var9.next();
                XSSFRow row_body = sheet.createRow(row_index);
                row_body.createCell(0).setCellValue(formatter.print(new DateTime(alarmData.getTime())));
                row_body.createCell(1).setCellValue(alarmData.getFactorName());
                row_body.createCell(2).setCellValue(alarmData.getTestValue());
                row_body.createCell(3).setCellValue(alarmData.getAlarmValue());
            }
        }

        return ExcelUtil.getByteArrayOutputStream(wb);
    }

    public List<AlarmDev> getAlarmDevList(DateRange dateRange) {
        List<AlarmDev> alarmDevList = new ArrayList();
        List<RecAlarmDev> recAlarmDevList = this.recMapper.getRecAlarmDevListByRange(dateRange);
        int iIndex = 1;

        for(Iterator var5 = recAlarmDevList.iterator(); var5.hasNext(); ++iIndex) {
            RecAlarmDev recAlarmDev = (RecAlarmDev)var5.next();
            AlarmDev alarmDev = new AlarmDev();
            alarmDev.setId(iIndex);
            alarmDev.setTime(recAlarmDev.getRecTime());
            alarmDev.setDevName(this.getDevNameByFactor(recAlarmDev.getFactorID()));
            alarmDev.setFactorName(this.getFactorName(recAlarmDev.getFactorID()));
            alarmDev.setAlarmCode(recAlarmDev.getAlarmID());
            alarmDev.setAlarmInfo(recAlarmDev.getAlarmDesc());
            alarmDevList.add(alarmDev);
        }

        return alarmDevList;
    }

    public List<LogInfo> getLogRunningList(DateRange dateRange) {
        dateRange.init();
        List<LogInfo> LogInfoList = new ArrayList();
        List<RecLogSys> recLogSysList = this.recMapper.getRecLogSysListByRange(dateRange);
        int iIndex = 1;

        for(Iterator var5 = recLogSysList.iterator(); var5.hasNext(); ++iIndex) {
            RecLogSys recLogSys = (RecLogSys)var5.next();
            LogInfo logInfo = new LogInfo();
            logInfo.setId(iIndex);
            logInfo.setTime(recLogSys.getRecTime());
            logInfo.setType(recLogSys.getLogType());
            logInfo.setDesc(recLogSys.getLogDesc());
            LogInfoList.add(logInfo);
        }

        return LogInfoList;
    }

    public ByteArrayOutputStream exportLogRunning(DateRange dateRange) {
        dateRange.setStart((Integer)null);
        dateRange.initDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String safeName = WorkbookUtil.createSafeSheetName("运行日志");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(safeName);
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("时间");
        row.createCell(1).setCellValue("类型");
        row.createCell(2).setCellValue("日志");

        int row_index;
        for(row_index = 0; row_index < 3; ++row_index) {
            sheet.autoSizeColumn(row_index);
            sheet.setColumnWidth(row_index, sheet.getColumnWidth(row_index) + 1500);
        }

        row_index = 1;
        List<LogInfo> tbody = this.getLogRunningList(dateRange);
        if (tbody != null && tbody.size() > 0) {
            for(Iterator var9 = tbody.iterator(); var9.hasNext(); ++row_index) {
                LogInfo logInfo = (LogInfo)var9.next();
                XSSFRow row_body = sheet.createRow(row_index);
                row_body.createCell(0).setCellValue(formatter.print(new DateTime(logInfo.getTime())));
                row_body.createCell(1).setCellValue(logInfo.getType());
                row_body.createCell(2).setCellValue(logInfo.getDesc());
            }
        }

        return ExcelUtil.getByteArrayOutputStream(wb);
    }

    public List<LogInfo> getLogDevList(DateRange dateRange) {
        dateRange.init();
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        List<LogInfo> LogInfoList = new ArrayList();
        Integer logType = dateRange.getLogType();
        if (logType == null) {
            logType = 1;
        }

        int iIndex;
        String factorName;
        List recLogDevList;
        Iterator var8;
        Iterator var10;
        CfgFactor cfgFactor;
        LogInfo logInfo;
        if (logType == 1) {
            iIndex = 1;
            recLogDevList = this.recMapper.getRecLogDevListByRange(dateRange);
            var8 = recLogDevList.iterator();

            while(var8.hasNext()) {
                RecLogDev recLogDev = (RecLogDev)var8.next();
                factorName = "";
                var10 = cfgFactorList.iterator();

                while(var10.hasNext()) {
                    cfgFactor = (CfgFactor)var10.next();
                    if (cfgFactor.getId() == recLogDev.getFactorID()) {
                        factorName = cfgFactor.getName();
                        break;
                    }
                }

                if (this.isExist(factorName, dateRange.getHeadName())) {
                    logInfo = new LogInfo();
                    logInfo.setId(iIndex);
                    logInfo.setTime(recLogDev.getRecTime());
                    logInfo.setType(factorName);
                    logInfo.setDesc(recLogDev.getLogDesc());
                    LogInfoList.add(logInfo);
                    ++iIndex;
                }
            }
        } else {
            iIndex = 1;
            recLogDevList = this.recMapper.getRecAlarmDevListByRange(dateRange);
            var8 = recLogDevList.iterator();

            while(var8.hasNext()) {
                RecAlarmDev recAlarmDev = (RecAlarmDev)var8.next();
                factorName = "";
                var10 = cfgFactorList.iterator();

                while(var10.hasNext()) {
                    cfgFactor = (CfgFactor)var10.next();
                    if (cfgFactor.getId() == recAlarmDev.getFactorID()) {
                        factorName = cfgFactor.getName();
                        break;
                    }
                }

                if (this.isExist(factorName, dateRange.getHeadName())) {
                    logInfo = new LogInfo();
                    logInfo.setId(iIndex);
                    logInfo.setTime(recAlarmDev.getRecTime());
                    logInfo.setType(factorName);
                    logInfo.setDesc(recAlarmDev.getAlarmDesc());
                    LogInfoList.add(logInfo);
                    ++iIndex;
                }
            }
        }

        return LogInfoList;
    }

    public ByteArrayOutputStream exportLogDevice(DateRange dateRange) {
        dateRange.setStart((Integer)null);
        dateRange.initDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String safeName = WorkbookUtil.createSafeSheetName("仪表日志");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(safeName);
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("时间");
        row.createCell(1).setCellValue("类型");
        row.createCell(2).setCellValue("日志");

        int row_index;
        for(row_index = 0; row_index < 3; ++row_index) {
            sheet.autoSizeColumn(row_index);
            sheet.setColumnWidth(row_index, sheet.getColumnWidth(row_index) + 1500);
        }

        row_index = 1;
        List<LogInfo> tbody = this.getLogDevList(dateRange);
        if (tbody != null && tbody.size() > 0) {
            for(Iterator var9 = tbody.iterator(); var9.hasNext(); ++row_index) {
                LogInfo logInfo = (LogInfo)var9.next();
                XSSFRow row_body = sheet.createRow(row_index);
                row_body.createCell(0).setCellValue(formatter.print(new DateTime(logInfo.getTime())));
                row_body.createCell(1).setCellValue(logInfo.getType());
                row_body.createCell(2).setCellValue(logInfo.getDesc());
            }
        }

        return ExcelUtil.getByteArrayOutputStream(wb);
    }

    public List<LogInfo> getLogExceptionList(DateRange dateRange) {
        dateRange.init();
        List<LogInfo> LogInfoList = new ArrayList();
        List<RecFaultSys> recFaultSysList = this.recMapper.getRecFaultSysListByRange(dateRange);
        int iIndex = 1;

        for(Iterator var5 = recFaultSysList.iterator(); var5.hasNext(); ++iIndex) {
            RecFaultSys recFaultSys = (RecFaultSys)var5.next();
            LogInfo logInfo = new LogInfo();
            logInfo.setId(iIndex);
            logInfo.setTime(recFaultSys.getRecTime());
            logInfo.setType(recFaultSys.getFaultType());
            logInfo.setDesc(recFaultSys.getFaultDesc());
            LogInfoList.add(logInfo);
        }

        return LogInfoList;
    }

    public ByteArrayOutputStream exportLogException(DateRange dateRange) {
        dateRange.setStart((Integer)null);
        dateRange.initDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String safeName = WorkbookUtil.createSafeSheetName("故障日志");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(safeName);
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("时间");
        row.createCell(1).setCellValue("日志");

        int row_index;
        for(row_index = 0; row_index < 2; ++row_index) {
            sheet.autoSizeColumn(row_index);
            sheet.setColumnWidth(row_index, sheet.getColumnWidth(row_index) + 1500);
        }

        row_index = 1;
        List<LogInfo> tbody = this.getLogExceptionList(dateRange);
        if (tbody != null && tbody.size() > 0) {
            for(Iterator var9 = tbody.iterator(); var9.hasNext(); ++row_index) {
                LogInfo logInfo = (LogInfo)var9.next();
                XSSFRow row_body = sheet.createRow(row_index);
                row_body.createCell(0).setCellValue(formatter.print(new DateTime(logInfo.getTime())));
                row_body.createCell(1).setCellValue(logInfo.getDesc());
            }
        }

        return ExcelUtil.getByteArrayOutputStream(wb);
    }

    public List<RecLogSample> getLogSampleList(DateRange dateRange) {
        dateRange.init();
        List<RecLogSample> recLogSampleList = this.recMapper.getRecLogSampleListByRange(dateRange);
        return recLogSampleList;
    }

    public ByteArrayOutputStream exportLogSample(DateRange dateRange) {
        dateRange.setStart((Integer)null);
        dateRange.initDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String safeName = WorkbookUtil.createSafeSheetName("留样日志");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(safeName);
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("时间");
        row.createCell(1).setCellValue("瓶号");
        row.createCell(2).setCellValue("留样量");
        row.createCell(3).setCellValue("留样方式");
        row.createCell(4).setCellValue("备注");

        int row_index;
        for(row_index = 0; row_index < 5; ++row_index) {
            sheet.autoSizeColumn(row_index);
            sheet.setColumnWidth(row_index, sheet.getColumnWidth(row_index) + 1500);
        }

        row_index = 1;
        List<RecLogSample> tbody = this.getLogSampleList(dateRange);
        if (tbody != null && tbody.size() > 0) {
            for(Iterator var9 = tbody.iterator(); var9.hasNext(); ++row_index) {
                RecLogSample recLogSample = (RecLogSample)var9.next();
                XSSFRow row_body = sheet.createRow(row_index);
                row_body.createCell(0).setCellValue(formatter.print(new DateTime(recLogSample.getRecTime())));
                row_body.createCell(1).setCellValue(recLogSample.getRecBottle());
                row_body.createCell(2).setCellValue(recLogSample.getRecVolumn());
                row_body.createCell(3).setCellValue(recLogSample.getRecType());
                row_body.createCell(4).setCellValue(recLogSample.getRecNote());
            }
        }

        return ExcelUtil.getByteArrayOutputStream(wb);
    }

    public List<LogInfo> getLogAuditList(DateRange dateRange) {
        dateRange.init();
        List<LogInfo> LogInfoList = new ArrayList();
        List<RecLogUser> recLogUserList = this.recMapper.getRecLogUserListByRange(dateRange);
        int iIndex = 1;

        for(Iterator var5 = recLogUserList.iterator(); var5.hasNext(); ++iIndex) {
            RecLogUser recLogUser = (RecLogUser)var5.next();
            LogInfo logInfo = new LogInfo();
            logInfo.setId(iIndex);
            logInfo.setTime(recLogUser.getRecTime());
            logInfo.setType(recLogUser.getRecUser());
            logInfo.setDesc(recLogUser.getRecOperation());
            LogInfoList.add(logInfo);
        }

        return LogInfoList;
    }

    public ByteArrayOutputStream exportLogAudit(DateRange dateRange) {
        dateRange.setStart((Integer)null);
        dateRange.initDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String safeName = WorkbookUtil.createSafeSheetName("行为审计");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(safeName);
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("时间");
        row.createCell(1).setCellValue("用户");
        row.createCell(2).setCellValue("日志");

        int row_index;
        for(row_index = 0; row_index < 3; ++row_index) {
            sheet.autoSizeColumn(row_index);
            sheet.setColumnWidth(row_index, sheet.getColumnWidth(row_index) + 1500);
        }

        row_index = 1;
        List<LogInfo> tbody = this.getLogAuditList(dateRange);
        if (tbody != null && tbody.size() > 0) {
            for(Iterator var9 = tbody.iterator(); var9.hasNext(); ++row_index) {
                LogInfo logInfo = (LogInfo)var9.next();
                XSSFRow row_body = sheet.createRow(row_index);
                row_body.createCell(0).setCellValue(formatter.print(new DateTime(logInfo.getTime())));
                row_body.createCell(1).setCellValue(logInfo.getType());
                row_body.createCell(2).setCellValue(logInfo.getDesc());
            }
        }

        return ExcelUtil.getByteArrayOutputStream(wb);
    }

    public List<RecInfoDoor> getLogAccessList(DateRange dateRange) {
        dateRange.init();
        List<RecInfoDoor> recDoorList = this.recMapper.getRecLogAccessListByRange(dateRange);
        return recDoorList;
    }

    public ByteArrayOutputStream exportLogAccess(DateRange dateRange) {
        dateRange.setStart((Integer)null);
        dateRange.initDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String safeName = WorkbookUtil.createSafeSheetName("门禁记录");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(safeName);
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("时间");
        row.createCell(1).setCellValue("类型");
        row.createCell(2).setCellValue("用户");
        row.createCell(3).setCellValue("日志");

        int row_index;
        for(row_index = 0; row_index < 4; ++row_index) {
            sheet.autoSizeColumn(row_index);
            sheet.setColumnWidth(row_index, sheet.getColumnWidth(row_index) + 1500);
        }

        row_index = 1;
        List<RecInfoDoor> tbody = this.getLogAccessList(dateRange);
        if (tbody != null && tbody.size() > 0) {
            for(Iterator var9 = tbody.iterator(); var9.hasNext(); ++row_index) {
                RecInfoDoor recInfoDoor = (RecInfoDoor)var9.next();
                XSSFRow row_body = sheet.createRow(row_index);
                row_body.createCell(0).setCellValue(formatter.print(new DateTime(recInfoDoor.getRec_time())));
                row_body.createCell(1).setCellValue(recInfoDoor.getRec_type());
                row_body.createCell(2).setCellValue(recInfoDoor.getRec_name());
                row_body.createCell(3).setCellValue(recInfoDoor.getRec_info());
            }
        }

        return ExcelUtil.getByteArrayOutputStream(wb);
    }

    public boolean uploadDataNetwork(final DateHead dateHead) {
        dateHead.init();
        this.exitUploadNetwork = false;
        (new Thread(new Runnable() {
            public void run() {
                try {
                    Iterator var1 = HistoryService.this.monitorService.uploadInterList.iterator();

                    while(var1.hasNext()) {
                        UploadInter uploadInter = (UploadInter)var1.next();
                        if (HistoryService.this.exitUploadNetwork) {
                            break;
                        }

                        if (uploadInter.mUploadCfg.getIp() != null && uploadInter.mUploadCfg.getIp().length() > 3 && uploadInter.getUploadName().equals(dateHead.getHeadName())) {
                            DateRange dateRange = new DateRange();
                            dateRange.setStartDate(dateHead.getStartDate());
                            dateRange.setEndDate(dateHead.getEndDate());
                            Iterator var4;
                            RecDataTime recTime;
                            if (dateHead.getTypeName().equals("数据")) {
                                HistoryService.this.recTimeList = HistoryService.this.recMapper.getRecTimeHourListByRange(dateRange);
                                var4 = HistoryService.this.recTimeList.iterator();

                                while(var4.hasNext()) {
                                    recTime = (RecDataTime)var4.next();
                                    uploadInter.sendHourData(0, recTime.getRecTime());
                                    Thread.sleep(500L);
                                    uploadInter.sendParamDev(0, recTime.getRecTime(), 451);
                                    Thread.sleep(500L);
                                    if (HistoryService.this.exitUploadNetwork) {
                                        return;
                                    }

                                    if (uploadInter.mUploadCfg.getProtocol().contains("FTP")) {
                                        Thread.sleep(60000L);
                                    }
                                }

                                HistoryService.this.recTimeList = HistoryService.this.recMapper.getRecTimeFlowListByRange(dateRange);
                                var4 = HistoryService.this.recTimeList.iterator();

                                while(var4.hasNext()) {
                                    recTime = (RecDataTime)var4.next();
                                    uploadInter.sendFlowData(0, recTime.getRecTime());
                                    Thread.sleep(500L);
                                    if (HistoryService.this.exitUploadNetwork) {
                                        return;
                                    }
                                }

                                HistoryService.this.recTimeList = HistoryService.this.recMapper.getRecCheckStdTimeListByRange(dateRange);
                                var4 = HistoryService.this.recTimeList.iterator();

                                while(var4.hasNext()) {
                                    recTime = (RecDataTime)var4.next();
                                    uploadInter.sendStdCheck(0, recTime.getRecTime());
                                    Thread.sleep(500L);
                                    uploadInter.sendParamDev(0, recTime.getRecTime(), 452);
                                    Thread.sleep(500L);
                                    if (HistoryService.this.exitUploadNetwork) {
                                        return;
                                    }
                                }

                                HistoryService.this.recTimeList = HistoryService.this.recMapper.getRecCheckFiveTimeListByRange(dateRange);
                                var4 = HistoryService.this.recTimeList.iterator();

                                while(var4.hasNext()) {
                                    recTime = (RecDataTime)var4.next();
                                    uploadInter.sendFiveStdCheck(recTime.getRecTime());
                                    Thread.sleep(500L);
                                    if (HistoryService.this.exitUploadNetwork) {
                                        return;
                                    }
                                }

                                HistoryService.this.recTimeList = HistoryService.this.recMapper.getRecCheckZeroTimeListByRange(dateRange);
                                var4 = HistoryService.this.recTimeList.iterator();

                                while(var4.hasNext()) {
                                    recTime = (RecDataTime)var4.next();
                                    uploadInter.sendZeroCheck(0, recTime.getRecTime());
                                    Thread.sleep(500L);
                                    uploadInter.sendParamDev(0, recTime.getRecTime(), 453);
                                    Thread.sleep(500L);
                                    if (HistoryService.this.exitUploadNetwork) {
                                        return;
                                    }
                                }

                                HistoryService.this.recTimeList = HistoryService.this.recMapper.getRecCheckSpanTimeListByRange(dateRange);
                                var4 = HistoryService.this.recTimeList.iterator();

                                while(var4.hasNext()) {
                                    recTime = (RecDataTime)var4.next();
                                    uploadInter.sendSpanCheck(0, recTime.getRecTime());
                                    Thread.sleep(500L);
                                    uploadInter.sendParamDev(0, recTime.getRecTime(), 454);
                                    Thread.sleep(500L);
                                    if (HistoryService.this.exitUploadNetwork) {
                                        return;
                                    }
                                }

                                HistoryService.this.recTimeList = HistoryService.this.recMapper.getRecCheckParTimeListByRange(dateRange);
                                var4 = HistoryService.this.recTimeList.iterator();

                                while(var4.hasNext()) {
                                    recTime = (RecDataTime)var4.next();
                                    uploadInter.sendParCheck(0, recTime.getRecTime());
                                    Thread.sleep(500L);
                                    uploadInter.sendParamDev(0, recTime.getRecTime(), 456);
                                    Thread.sleep(500L);
                                    if (HistoryService.this.exitUploadNetwork) {
                                        return;
                                    }
                                }

                                HistoryService.this.recTimeList = HistoryService.this.recMapper.getRecCheckRcvrTimeListByRange(dateRange);
                                var4 = HistoryService.this.recTimeList.iterator();

                                do {
                                    if (!var4.hasNext()) {
                                        return;
                                    }

                                    recTime = (RecDataTime)var4.next();
                                    uploadInter.sendRcvrCheck(0, recTime.getRecTime());
                                    Thread.sleep(500L);
                                    uploadInter.sendParamDev(0, recTime.getRecTime(), 457);
                                    Thread.sleep(500L);
                                } while(!HistoryService.this.exitUploadNetwork);

                                return;
                            } else {
                                HistoryService.this.recTimeList = HistoryService.this.recMapper.getRecLogDevTimeListByRange(dateRange);
                                var4 = HistoryService.this.recTimeList.iterator();

                                while(var4.hasNext()) {
                                    recTime = (RecDataTime)var4.next();
                                    uploadInter.sendLogDev(0, recTime);
                                    Thread.sleep(500L);
                                    if (HistoryService.this.exitUploadNetwork) {
                                        return;
                                    }
                                }

                                HistoryService.this.recTimeList = HistoryService.this.recMapper.getRecLogSysTimeListByRange(dateRange);
                                var4 = HistoryService.this.recTimeList.iterator();

                                do {
                                    if (!var4.hasNext()) {
                                        return;
                                    }

                                    recTime = (RecDataTime)var4.next();
                                    uploadInter.sendLogSys(recTime.getRecTime());
                                    Thread.sleep(500L);
                                } while(!HistoryService.this.exitUploadNetwork);

                                return;
                            }
                        }
                    }
                } catch (Exception var6) {
                }

            }
        })).start();
        return true;
    }

    public boolean uploadStopNetwork(DateHead dateHead) {
        this.exitUploadNetwork = true;
        return true;
    }

    public boolean uploadDataCom(final DateHead dateHead) {
        dateHead.init();
        this.exitUploadCom = false;
        (new Thread(new Runnable() {
            public void run() {
                try {
                    Iterator var1 = HistoryService.this.monitorService.uploadInterList.iterator();

                    while(var1.hasNext()) {
                        UploadInter uploadInter = (UploadInter)var1.next();
                        if (HistoryService.this.exitUploadCom) {
                            break;
                        }

                        if (uploadInter.mUploadCfg.getIp() == null && uploadInter.getUploadName().equals(dateHead.getHeadName())) {
                            DateRange dateRange = new DateRange();
                            dateRange.setStartDate(dateHead.getStartDate());
                            dateRange.setEndDate(dateHead.getEndDate());
                            Iterator var4;
                            RecDataTime recTime;
                            if (dateHead.getTypeName().equals("数据")) {
                                HistoryService.this.recTimeList = HistoryService.this.recMapper.getRecTimeHourListByRange(dateRange);
                                var4 = HistoryService.this.recTimeList.iterator();

                                while(var4.hasNext()) {
                                    recTime = (RecDataTime)var4.next();
                                    uploadInter.sendHourData(0, recTime.getRecTime());
                                    Thread.sleep(500L);
                                    uploadInter.sendParamDev(0, recTime.getRecTime(), 451);
                                    Thread.sleep(500L);
                                    if (HistoryService.this.exitUploadCom) {
                                        return;
                                    }

                                    if (uploadInter.mUploadCfg.getProtocol().contains("FTP")) {
                                        Thread.sleep(60000L);
                                    }
                                }

                                HistoryService.this.recTimeList = HistoryService.this.recMapper.getRecTimeFlowListByRange(dateRange);
                                var4 = HistoryService.this.recTimeList.iterator();

                                while(var4.hasNext()) {
                                    recTime = (RecDataTime)var4.next();
                                    uploadInter.sendFlowData(0, recTime.getRecTime());
                                    Thread.sleep(500L);
                                    if (HistoryService.this.exitUploadCom) {
                                        return;
                                    }
                                }

                                HistoryService.this.recTimeList = HistoryService.this.recMapper.getRecCheckStdTimeListByRange(dateRange);
                                var4 = HistoryService.this.recTimeList.iterator();

                                while(var4.hasNext()) {
                                    recTime = (RecDataTime)var4.next();
                                    uploadInter.sendStdCheck(0, recTime.getRecTime());
                                    Thread.sleep(500L);
                                    uploadInter.sendParamDev(0, recTime.getRecTime(), 452);
                                    Thread.sleep(500L);
                                    if (HistoryService.this.exitUploadCom) {
                                        return;
                                    }
                                }

                                HistoryService.this.recTimeList = HistoryService.this.recMapper.getRecCheckZeroTimeListByRange(dateRange);
                                var4 = HistoryService.this.recTimeList.iterator();

                                while(var4.hasNext()) {
                                    recTime = (RecDataTime)var4.next();
                                    uploadInter.sendZeroCheck(0, recTime.getRecTime());
                                    Thread.sleep(500L);
                                    uploadInter.sendParamDev(0, recTime.getRecTime(), 453);
                                    Thread.sleep(500L);
                                    if (HistoryService.this.exitUploadCom) {
                                        return;
                                    }
                                }

                                HistoryService.this.recTimeList = HistoryService.this.recMapper.getRecCheckSpanTimeListByRange(dateRange);
                                var4 = HistoryService.this.recTimeList.iterator();

                                while(var4.hasNext()) {
                                    recTime = (RecDataTime)var4.next();
                                    uploadInter.sendSpanCheck(0, recTime.getRecTime());
                                    Thread.sleep(500L);
                                    uploadInter.sendParamDev(0, recTime.getRecTime(), 454);
                                    Thread.sleep(500L);
                                    if (HistoryService.this.exitUploadCom) {
                                        return;
                                    }
                                }

                                HistoryService.this.recTimeList = HistoryService.this.recMapper.getRecCheckParTimeListByRange(dateRange);
                                var4 = HistoryService.this.recTimeList.iterator();

                                while(var4.hasNext()) {
                                    recTime = (RecDataTime)var4.next();
                                    uploadInter.sendParCheck(0, recTime.getRecTime());
                                    Thread.sleep(500L);
                                    uploadInter.sendParamDev(0, recTime.getRecTime(), 456);
                                    Thread.sleep(500L);
                                    if (HistoryService.this.exitUploadCom) {
                                        return;
                                    }
                                }

                                HistoryService.this.recTimeList = HistoryService.this.recMapper.getRecCheckRcvrTimeListByRange(dateRange);
                                var4 = HistoryService.this.recTimeList.iterator();

                                do {
                                    if (!var4.hasNext()) {
                                        return;
                                    }

                                    recTime = (RecDataTime)var4.next();
                                    uploadInter.sendRcvrCheck(0, recTime.getRecTime());
                                    Thread.sleep(500L);
                                    uploadInter.sendParamDev(0, recTime.getRecTime(), 457);
                                    Thread.sleep(500L);
                                } while(!HistoryService.this.exitUploadCom);

                                return;
                            } else {
                                HistoryService.this.recTimeList = HistoryService.this.recMapper.getRecLogDevTimeListByRange(dateRange);
                                var4 = HistoryService.this.recTimeList.iterator();

                                while(var4.hasNext()) {
                                    recTime = (RecDataTime)var4.next();
                                    uploadInter.sendLogDev(0, recTime);
                                    Thread.sleep(500L);
                                    if (HistoryService.this.exitUploadCom) {
                                        return;
                                    }
                                }

                                HistoryService.this.recTimeList = HistoryService.this.recMapper.getRecLogSysTimeListByRange(dateRange);
                                var4 = HistoryService.this.recTimeList.iterator();

                                do {
                                    if (!var4.hasNext()) {
                                        return;
                                    }

                                    recTime = (RecDataTime)var4.next();
                                    uploadInter.sendLogSys(recTime.getRecTime());
                                    Thread.sleep(500L);
                                } while(!HistoryService.this.exitUploadCom);

                                return;
                            }
                        }
                    }
                } catch (Exception var6) {
                }

            }
        })).start();
        return true;
    }

    public boolean uploadStopCom(DateHead dateHead) {
        this.exitUploadCom = true;
        return true;
    }

    public List<CfgUploadNet> getPlatformList() {
        List<CfgUploadNet> cfgUploadNetList = this.cfgMapper.getCfgUploadNetList();
        return (List)cfgUploadNetList.stream().filter((item) -> {
            return item.isUsed();
        }).collect(Collectors.toList());
    }

    public List<RecLogPlatform> getLogPlatformList(DateRange dateRange) {
        dateRange.init();
        Integer logType = dateRange.getLogType();
        if (logType == null) {
            logType = 1;
        }

        List<RecLogPlatform> recLogPlatformList = this.recMapper.getRecLogPlatformListByRange(dateRange);
        List<RecLogPlatform> platformList = new ArrayList();
        if (recLogPlatformList != null) {
            Iterator var5 = recLogPlatformList.iterator();

            while(var5.hasNext()) {
                RecLogPlatform recLogPlatform = (RecLogPlatform)var5.next();
                if (recLogPlatform.getRecPlatform() == logType) {
                    platformList.add(recLogPlatform);
                }
            }
        }

        return platformList;
    }

    public ByteArrayOutputStream exportLogPlatform(DateRange dateRange) {
        dateRange.setStart((Integer)null);
        dateRange.initDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String safeName = WorkbookUtil.createSafeSheetName("平台日志");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(safeName);
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("时间");
        row.createCell(1).setCellValue("类型");
        row.createCell(2).setCellValue("日志");

        int row_index;
        for(row_index = 0; row_index < 3; ++row_index) {
            sheet.autoSizeColumn(row_index);
            sheet.setColumnWidth(row_index, sheet.getColumnWidth(row_index) + 1500);
        }

        row_index = 1;
        List<RecLogPlatform> tbody = this.getLogPlatformList(dateRange);
        if (tbody != null && tbody.size() > 0) {
            for(Iterator var9 = tbody.iterator(); var9.hasNext(); ++row_index) {
                RecLogPlatform recLogPlatform = (RecLogPlatform)var9.next();
                XSSFRow row_body = sheet.createRow(row_index);
                row_body.createCell(0).setCellValue(formatter.print(new DateTime(recLogPlatform.getRecTime())));
                row_body.createCell(1).setCellValue(recLogPlatform.getRecType());
                row_body.createCell(2).setCellValue(recLogPlatform.getRecDesc());
            }
        }

        return ExcelUtil.getByteArrayOutputStream(wb);
    }
}
