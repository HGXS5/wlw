//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.query;

import com.grean.station.dao.CfgMapper;
import com.grean.station.dao.RecMapper;
import com.grean.station.domain.DO.cfg.CfgFactor;
import com.grean.station.domain.DO.cfg.CfgUploadNet;
import com.grean.station.domain.DO.rec.RecCheckBlank;
import com.grean.station.domain.DO.rec.RecCheckFive;
import com.grean.station.domain.DO.rec.RecCheckPar;
import com.grean.station.domain.DO.rec.RecCheckRcvr;
import com.grean.station.domain.DO.rec.RecCheckStd;
import com.grean.station.domain.DO.rec.RecCheckZero;
import com.grean.station.domain.DO.rec.RecDataTime;
import com.grean.station.domain.request.CheckBlankData;
import com.grean.station.domain.request.CheckFiveData;
import com.grean.station.domain.request.CheckParData;
import com.grean.station.domain.request.CheckRcvrData;
import com.grean.station.domain.request.CheckSpanData;
import com.grean.station.domain.request.CheckSpanDataPkg;
import com.grean.station.domain.request.CheckStdData;
import com.grean.station.domain.request.DateRange;
import com.grean.station.domain.request.HistoryDataFlag;
import com.grean.station.domain.request.HistoryUpload;
import com.grean.station.service.MonitorService;
import com.grean.station.utils.ExcelUtil;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
public class CheckService {
    @Autowired
    RecMapper recMapper;
    @Autowired
    CfgMapper cfgMapper;
    @Autowired
    MonitorService monitorService;

    public CheckService() {
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

    public List<HistoryUpload> getUploadList(RecDataTime recDataTime) {
        List<HistoryUpload> uploadList = new ArrayList();
        Iterator var3 = this.monitorService.getCfgUploadNetList().iterator();

        while(var3.hasNext()) {
            CfgUploadNet cfgUploadNet = (CfgUploadNet)var3.next();
            if (cfgUploadNet.isUsed()) {
                HistoryUpload historyUpload = new HistoryUpload();
                historyUpload.setId(cfgUploadNet.getId());
                historyUpload.setName(cfgUploadNet.getName());
                switch(cfgUploadNet.getId()) {
                    case 1:
                        historyUpload.setTime(recDataTime.getSendTime1());
                        break;
                    case 2:
                        historyUpload.setTime(recDataTime.getSendTime2());
                        break;
                    case 3:
                        historyUpload.setTime(recDataTime.getSendTime3());
                        break;
                    case 4:
                        historyUpload.setTime(recDataTime.getSendTime4());
                        break;
                    case 5:
                        historyUpload.setTime(recDataTime.getSendTime5());
                        break;
                    case 6:
                        historyUpload.setTime(recDataTime.getSendTime6());
                        break;
                    case 7:
                        historyUpload.setTime(recDataTime.getSendTime7());
                        break;
                    case 8:
                        historyUpload.setTime(recDataTime.getSendTime8());
                        break;
                    case 9:
                        historyUpload.setTime(recDataTime.getSendTime9());
                        break;
                    case 10:
                        historyUpload.setTime(recDataTime.getSendTime10());
                }

                uploadList.add(historyUpload);
            }
        }

        return uploadList;
    }

    public HistoryDataFlag getCheckZeroDataFlag() {
        HistoryDataFlag historyDataFlag = new HistoryDataFlag();
        historyDataFlag.setDataFlag(this.monitorService.isCheckZeroDataFlag());
        return historyDataFlag;
    }

    public CheckSpanDataPkg getCheckZeroDataList(DateRange dateRange) {
        dateRange.init();
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        List<RecDataTime> recTimeList = this.recMapper.getRecCheckZeroTimeListByRange(dateRange);
        List<CheckSpanData> checkZeroDataList = new ArrayList();
        int id = 1;
        Iterator var6 = recTimeList.iterator();

        while(var6.hasNext()) {
            RecDataTime recTime = (RecDataTime)var6.next();
            List<RecCheckZero> recDataList = this.recMapper.getRecCheckZeroDataListByTime(recTime);
            Iterator var9 = recDataList.iterator();

            while(var9.hasNext()) {
                RecCheckZero recCheckZero = (RecCheckZero)var9.next();
                CheckSpanData checkZeroData = new CheckSpanData();
                checkZeroData.setId(id);
                checkZeroData.setTime(recCheckZero.getRecTime());
                checkZeroData.setName((String)null);
                checkZeroData.setUnit((String)null);
                Iterator var12 = cfgFactorList.iterator();

                while(var12.hasNext()) {
                    CfgFactor cfgFactor = (CfgFactor)var12.next();
                    if (cfgFactor.getId() == recCheckZero.getFactorID()) {
                        checkZeroData.setName(cfgFactor.getName());
                        checkZeroData.setUnit(cfgFactor.getUnit());
                        break;
                    }
                }

                if (this.isExist(checkZeroData.getName(), dateRange.getHeadName())) {
                    checkZeroData.setPreValue(recCheckZero.getPreValue());
                    checkZeroData.setCurValue(recCheckZero.getCurValue());
                    checkZeroData.setDataFlag(recCheckZero.getDataFlag());
                    checkZeroData.setStdValue(recCheckZero.getStdValue());
                    checkZeroData.setSpanValue(recCheckZero.getSpanValue());
                    checkZeroData.setaErrorValue(recCheckZero.getaError());
                    checkZeroData.setrErrorValue(recCheckZero.getrError());
                    checkZeroData.setaResult(recCheckZero.getaResult());
                    checkZeroData.setrResult(recCheckZero.getrResult());
                    checkZeroData.setUploadList(this.getUploadList(recTime));
                    checkZeroDataList.add(checkZeroData);
                    ++id;
                }
            }
        }

        if (dateRange.getDataFlag() != null) {
            this.monitorService.setCheckZeroDataFlag(dateRange.getDataFlag());
        }

        CheckSpanDataPkg checkSpanDataPkg = new CheckSpanDataPkg();
        checkSpanDataPkg.setHistoryData(checkZeroDataList);
        checkSpanDataPkg.setDataFlag(this.monitorService.isCheckZeroDataFlag());
        return checkSpanDataPkg;
    }

    public ByteArrayOutputStream exportCheckZeroData(DateRange dateRange) {
        dateRange.setStart((Integer)null);
        dateRange.initDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String safeName = WorkbookUtil.createSafeSheetName("零点核查");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(safeName);
        XSSFRow row1 = sheet.createRow(0);
        row1.createCell(0).setCellValue("时间");
        row1.createCell(1).setCellValue("因子");
        row1.createCell(2).setCellValue("单位");
        row1.createCell(3).setCellValue("测试结果");
        row1.createCell(4).setCellValue("跨度核查");
        row1.createCell(5).setCellValue("");
        row1.createCell(6).setCellValue("");
        row1.createCell(7).setCellValue("24小时跨度漂移");
        row1.createCell(8).setCellValue("");
        row1.createCell(9).setCellValue("");
        row1.createCell(10).setCellValue("");

        for(int i = 0; i < 11; ++i) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1500);
        }

        XSSFRow row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue("");
        row2.createCell(1).setCellValue("");
        row2.createCell(2).setCellValue("");
        row2.createCell(3).setCellValue("");
        row2.createCell(4).setCellValue("标液样浓度");
        row2.createCell(5).setCellValue("相对误差");
        row2.createCell(6).setCellValue("合格情况");
        row2.createCell(7).setCellValue("前一次测试结果");
        row2.createCell(8).setCellValue("跨度值");
        row2.createCell(9).setCellValue("相对误差");
        row2.createCell(10).setCellValue("合格情况");
        int row_index = 2;
        List<CheckSpanData> tbody = this.getCheckZeroDataList(dateRange).getHistoryData();
        if (tbody != null && tbody.size() > 0) {
            for(Iterator var10 = tbody.iterator(); var10.hasNext(); ++row_index) {
                CheckSpanData checkZeroData = (CheckSpanData)var10.next();
                XSSFRow row_body = sheet.createRow(row_index);
                row_body.createCell(0).setCellValue(formatter.print(new DateTime(checkZeroData.getTime())));
                row_body.createCell(1).setCellValue(checkZeroData.getName());
                row_body.createCell(2).setCellValue(checkZeroData.getUnit());
                row_body.createCell(3).setCellValue(checkZeroData.getCurValue());
                row_body.createCell(4).setCellValue(checkZeroData.getStdValue());
                row_body.createCell(5).setCellValue(checkZeroData.getaErrorValue());
                row_body.createCell(6).setCellValue(checkZeroData.getaResult());
                row_body.createCell(7).setCellValue(checkZeroData.getPreValue() != null ? checkZeroData.getPreValue() + "" : "");
                row_body.createCell(8).setCellValue(checkZeroData.getSpanValue() != null ? checkZeroData.getSpanValue() + "" : "");
                row_body.createCell(9).setCellValue(checkZeroData.getrErrorValue() != null ? checkZeroData.getrErrorValue() + "" : "");
                row_body.createCell(10).setCellValue(checkZeroData.getrResult() != null ? checkZeroData.getrResult() : "");
            }
        }

        return ExcelUtil.getByteArrayOutputStream(wb);
    }

    public HistoryDataFlag getCheckSpanDataFlag() {
        HistoryDataFlag historyDataFlag = new HistoryDataFlag();
        historyDataFlag.setDataFlag(this.monitorService.isCheckSpanDataFlag());
        return historyDataFlag;
    }

    public CheckSpanDataPkg getCheckSpanDataList(DateRange dateRange) {
        dateRange.init();
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        List<RecDataTime> recTimeList = this.recMapper.getRecCheckSpanTimeListByRange(dateRange);
        List<CheckSpanData> checkSpanDataList = new ArrayList();
        int id = 1;
        Iterator var6 = recTimeList.iterator();

        while(var6.hasNext()) {
            RecDataTime recTime = (RecDataTime)var6.next();
            List<RecCheckZero> recDataList = this.recMapper.getRecCheckSpanDataListByTime(recTime);
            Iterator var9 = recDataList.iterator();

            while(var9.hasNext()) {
                RecCheckZero recCheckZero = (RecCheckZero)var9.next();
                CheckSpanData checkSpanData = new CheckSpanData();
                checkSpanData.setId(id);
                checkSpanData.setTime(recCheckZero.getRecTime());
                checkSpanData.setName((String)null);
                checkSpanData.setUnit((String)null);
                Iterator var12 = cfgFactorList.iterator();

                while(var12.hasNext()) {
                    CfgFactor cfgFactor = (CfgFactor)var12.next();
                    if (cfgFactor.getId() == recCheckZero.getFactorID()) {
                        checkSpanData.setName(cfgFactor.getName());
                        checkSpanData.setUnit(cfgFactor.getUnit());
                        break;
                    }
                }

                if (this.isExist(checkSpanData.getName(), dateRange.getHeadName())) {
                    checkSpanData.setPreValue(recCheckZero.getPreValue());
                    checkSpanData.setCurValue(recCheckZero.getCurValue());
                    checkSpanData.setDataFlag(recCheckZero.getDataFlag());
                    checkSpanData.setStdValue(recCheckZero.getStdValue());
                    checkSpanData.setSpanValue(recCheckZero.getSpanValue());
                    checkSpanData.setaErrorValue(recCheckZero.getaError());
                    checkSpanData.setrErrorValue(recCheckZero.getrError());
                    checkSpanData.setaResult(recCheckZero.getaResult());
                    checkSpanData.setrResult(recCheckZero.getrResult());
                    checkSpanData.setUploadList(this.getUploadList(recTime));
                    checkSpanDataList.add(checkSpanData);
                    ++id;
                }
            }
        }

        if (dateRange.getDataFlag() != null) {
            this.monitorService.setCheckSpanDataFlag(dateRange.getDataFlag());
        }

        CheckSpanDataPkg checkSpanDataPkg = new CheckSpanDataPkg();
        checkSpanDataPkg.setHistoryData(checkSpanDataList);
        checkSpanDataPkg.setDataFlag(this.monitorService.isCheckSpanDataFlag());
        return checkSpanDataPkg;
    }

    public ByteArrayOutputStream exportCheckSpanData(DateRange dateRange) {
        dateRange.setStart((Integer)null);
        dateRange.initDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String safeName = WorkbookUtil.createSafeSheetName("跨度核查");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(safeName);
        XSSFRow row1 = sheet.createRow(0);
        row1.createCell(0).setCellValue("时间");
        row1.createCell(1).setCellValue("因子");
        row1.createCell(2).setCellValue("单位");
        row1.createCell(3).setCellValue("测试结果");
        row1.createCell(4).setCellValue("跨度核查");
        row1.createCell(5).setCellValue("");
        row1.createCell(6).setCellValue("");
        row1.createCell(7).setCellValue("24小时跨度漂移");
        row1.createCell(8).setCellValue("");
        row1.createCell(9).setCellValue("");
        row1.createCell(10).setCellValue("");

        for(int i = 0; i < 11; ++i) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1500);
        }

        XSSFRow row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue("");
        row2.createCell(1).setCellValue("");
        row2.createCell(2).setCellValue("");
        row2.createCell(3).setCellValue("");
        row2.createCell(4).setCellValue("标液样浓度");
        row2.createCell(5).setCellValue("相对误差");
        row2.createCell(6).setCellValue("合格情况");
        row2.createCell(7).setCellValue("前一次测试结果");
        row2.createCell(8).setCellValue("跨度值");
        row2.createCell(9).setCellValue("相对误差");
        row2.createCell(10).setCellValue("合格情况");
        int row_index = 2;
        List<CheckSpanData> tbody = this.getCheckSpanDataList(dateRange).getHistoryData();
        if (tbody != null && tbody.size() > 0) {
            for(Iterator var10 = tbody.iterator(); var10.hasNext(); ++row_index) {
                CheckSpanData checkSpanData = (CheckSpanData)var10.next();
                XSSFRow row_body = sheet.createRow(row_index);
                row_body.createCell(0).setCellValue(formatter.print(new DateTime(checkSpanData.getTime())));
                row_body.createCell(1).setCellValue(checkSpanData.getName());
                row_body.createCell(2).setCellValue(checkSpanData.getUnit());
                row_body.createCell(3).setCellValue(checkSpanData.getCurValue());
                row_body.createCell(4).setCellValue(checkSpanData.getStdValue());
                row_body.createCell(5).setCellValue(checkSpanData.getaErrorValue());
                row_body.createCell(6).setCellValue(checkSpanData.getaResult());
                row_body.createCell(7).setCellValue(checkSpanData.getPreValue() != null ? checkSpanData.getPreValue() + "" : "");
                row_body.createCell(8).setCellValue(checkSpanData.getSpanValue() != null ? checkSpanData.getSpanValue() + "" : "");
                row_body.createCell(9).setCellValue(checkSpanData.getrErrorValue() != null ? checkSpanData.getrErrorValue() + "" : "");
                row_body.createCell(10).setCellValue(checkSpanData.getrResult() != null ? checkSpanData.getrResult() : "");
            }
        }

        return ExcelUtil.getByteArrayOutputStream(wb);
    }

    public List<CheckStdData> getCheckStdDataList(DateRange dateRange) {
        dateRange.init();
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        List<RecDataTime> recTimeList = this.recMapper.getRecCheckStdTimeListByRange(dateRange);
        List<CheckStdData> checkStdDataList = new ArrayList();
        int id = 1;
        Iterator var6 = recTimeList.iterator();

        while(var6.hasNext()) {
            RecDataTime recTime = (RecDataTime)var6.next();
            List<RecCheckStd> recDataList = this.recMapper.getRecCheckStdDataListByTime(recTime);
            Iterator var9 = recDataList.iterator();

            while(var9.hasNext()) {
                RecCheckStd recCheckStd = (RecCheckStd)var9.next();
                CheckStdData checkStdData = new CheckStdData();
                checkStdData.setId(id);
                checkStdData.setTime(recCheckStd.getRecTime());
                checkStdData.setName((String)null);
                checkStdData.setUnit((String)null);
                Iterator var12 = cfgFactorList.iterator();

                while(var12.hasNext()) {
                    CfgFactor cfgFactor = (CfgFactor)var12.next();
                    if (cfgFactor.getId() == recCheckStd.getFactorID()) {
                        checkStdData.setName(cfgFactor.getName());
                        checkStdData.setUnit(cfgFactor.getUnit());
                        break;
                    }
                }

                if (this.isExist(checkStdData.getName(), dateRange.getHeadName())) {
                    checkStdData.setTestValue(recCheckStd.getTestValue());
                    checkStdData.setStdValue(recCheckStd.getStdValue());
                    checkStdData.setDataFlag(recCheckStd.getDataFlag());
                    checkStdData.setErrorValue(recCheckStd.getrError());
                    if (recCheckStd.isrResult()) {
                        checkStdData.setCheckResult("合格");
                    } else {
                        checkStdData.setCheckResult("不合格");
                    }

                    checkStdData.setUploadList(this.getUploadList(recTime));
                    checkStdDataList.add(checkStdData);
                    ++id;
                }
            }
        }

        return checkStdDataList;
    }

    public ByteArrayOutputStream exportCheckStdData(DateRange dateRange) {
        dateRange.setStart((Integer)null);
        dateRange.initDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String safeName = WorkbookUtil.createSafeSheetName("标样核查");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(safeName);
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("时间");
        row.createCell(1).setCellValue("因子");
        row.createCell(2).setCellValue("单位");
        row.createCell(3).setCellValue("标准值");
        row.createCell(4).setCellValue("测量值");
        row.createCell(5).setCellValue("误差率");

        int row_index;
        for(row_index = 0; row_index < 6; ++row_index) {
            sheet.autoSizeColumn(row_index);
            sheet.setColumnWidth(row_index, sheet.getColumnWidth(row_index) + 1500);
        }

        row_index = 1;
        List<CheckStdData> tbody = this.getCheckStdDataList(dateRange);
        if (tbody != null && tbody.size() > 0) {
            for(Iterator var9 = tbody.iterator(); var9.hasNext(); ++row_index) {
                CheckStdData checkStdData = (CheckStdData)var9.next();
                XSSFRow row_body = sheet.createRow(row_index);
                row_body.createCell(0).setCellValue(formatter.print(new DateTime(checkStdData.getTime())));
                row_body.createCell(1).setCellValue(checkStdData.getName());
                row_body.createCell(2).setCellValue(checkStdData.getUnit());
                row_body.createCell(3).setCellValue(checkStdData.getStdValue());
                row_body.createCell(4).setCellValue(checkStdData.getTestValue());
                row_body.createCell(5).setCellValue(checkStdData.getErrorValue());
            }
        }

        return ExcelUtil.getByteArrayOutputStream(wb);
    }

    public List<CheckRcvrData> getCheckRcvrDataList(DateRange dateRange) {
        dateRange.init();
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        List<RecDataTime> recTimeList = this.recMapper.getRecCheckRcvrTimeListByRange(dateRange);
        List<CheckRcvrData> checkRcvrDataList = new ArrayList();
        int id = 1;
        Iterator var6 = recTimeList.iterator();

        while(var6.hasNext()) {
            RecDataTime recTime = (RecDataTime)var6.next();
            List<RecCheckRcvr> recDataList = this.recMapper.getRecCheckRcvrDataListByTime(recTime);
            Iterator var9 = recDataList.iterator();

            while(var9.hasNext()) {
                RecCheckRcvr recCheckRcvr = (RecCheckRcvr)var9.next();
                CheckRcvrData checkRcvrData = new CheckRcvrData();
                checkRcvrData.setId(id);
                checkRcvrData.setTime(recCheckRcvr.getRecTime());
                checkRcvrData.setName((String)null);
                checkRcvrData.setUnit((String)null);
                Iterator var12 = cfgFactorList.iterator();

                while(var12.hasNext()) {
                    CfgFactor cfgFactor = (CfgFactor)var12.next();
                    if (cfgFactor.getId() == recCheckRcvr.getFactorID()) {
                        checkRcvrData.setName(cfgFactor.getName());
                        checkRcvrData.setUnit(cfgFactor.getUnit());
                        break;
                    }
                }

                if (this.isExist(checkRcvrData.getName(), dateRange.getHeadName())) {
                    checkRcvrData.setBeforeValue(recCheckRcvr.getBeforeValue());
                    checkRcvrData.setAfterValue(recCheckRcvr.getAfterValue());
                    checkRcvrData.setDataFlag(recCheckRcvr.getDataFlag());
                    checkRcvrData.setMotherValue(recCheckRcvr.getMotherValue());
                    checkRcvrData.setMotherVolume(recCheckRcvr.getMotherVolume());
                    checkRcvrData.setCupVolume(recCheckRcvr.getCupVolume());
                    checkRcvrData.setRcvrRate(recCheckRcvr.getRcvrRate());
                    if (recCheckRcvr.isResult()) {
                        checkRcvrData.setCheckResult("合格");
                    } else {
                        checkRcvrData.setCheckResult("不合格");
                    }

                    checkRcvrData.setUploadList(this.getUploadList(recTime));
                    checkRcvrDataList.add(checkRcvrData);
                    ++id;
                }
            }
        }

        return checkRcvrDataList;
    }

    public ByteArrayOutputStream exportCheckRcvrData(DateRange dateRange) {
        dateRange.setStart((Integer)null);
        dateRange.initDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String safeName = WorkbookUtil.createSafeSheetName("加标核查");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(safeName);
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("时间");
        row.createCell(1).setCellValue("因子");
        row.createCell(2).setCellValue("单位");
        row.createCell(3).setCellValue("加标前水样浓度");
        row.createCell(4).setCellValue("加标后水样浓度");
        row.createCell(5).setCellValue("加标母液浓度");
        row.createCell(6).setCellValue("加标体积");
        row.createCell(7).setCellValue("加标水杯定容体积");
        row.createCell(8).setCellValue("加标回收率");

        int row_index;
        for(row_index = 0; row_index < 9; ++row_index) {
            sheet.autoSizeColumn(row_index);
            sheet.setColumnWidth(row_index, sheet.getColumnWidth(row_index) + 1500);
        }

        row_index = 1;
        List<CheckRcvrData> tbody = this.getCheckRcvrDataList(dateRange);
        if (tbody != null && tbody.size() > 0) {
            for(Iterator var9 = tbody.iterator(); var9.hasNext(); ++row_index) {
                CheckRcvrData checkRcvrData = (CheckRcvrData)var9.next();
                XSSFRow row_body = sheet.createRow(row_index);
                row_body.createCell(0).setCellValue(formatter.print(new DateTime(checkRcvrData.getTime())));
                row_body.createCell(1).setCellValue(checkRcvrData.getName());
                row_body.createCell(2).setCellValue(checkRcvrData.getUnit());
                row_body.createCell(3).setCellValue(checkRcvrData.getBeforeValue());
                row_body.createCell(4).setCellValue(checkRcvrData.getAfterValue());
                row_body.createCell(5).setCellValue(checkRcvrData.getMotherValue());
                row_body.createCell(6).setCellValue(checkRcvrData.getMotherVolume());
                row_body.createCell(7).setCellValue(checkRcvrData.getCupVolume());
                row_body.createCell(8).setCellValue(checkRcvrData.getRcvrRate());
            }
        }

        return ExcelUtil.getByteArrayOutputStream(wb);
    }

    public List<CheckParData> getCheckParDataList(DateRange dateRange) {
        dateRange.init();
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        List<RecDataTime> recTimeList = this.recMapper.getRecCheckParTimeListByRange(dateRange);
        List<CheckParData> checkParDataList = new ArrayList();
        int id = 1;
        Iterator var6 = recTimeList.iterator();

        while(var6.hasNext()) {
            RecDataTime recTime = (RecDataTime)var6.next();
            List<RecCheckPar> recDataList = this.recMapper.getRecCheckParDataListByTime(recTime);
            Iterator var9 = recDataList.iterator();

            while(var9.hasNext()) {
                RecCheckPar recCheckPar = (RecCheckPar)var9.next();
                CheckParData checkParData = new CheckParData();
                checkParData.setId(id);
                checkParData.setTime(recCheckPar.getRecTime());
                checkParData.setName((String)null);
                checkParData.setUnit((String)null);
                Iterator var12 = cfgFactorList.iterator();

                while(var12.hasNext()) {
                    CfgFactor cfgFactor = (CfgFactor)var12.next();
                    if (cfgFactor.getId() == recCheckPar.getFactorID()) {
                        checkParData.setName(cfgFactor.getName());
                        checkParData.setUnit(cfgFactor.getUnit());
                        break;
                    }
                }

                if (this.isExist(checkParData.getName(), dateRange.getHeadName())) {
                    checkParData.setPreValue(recCheckPar.getValue1st());
                    checkParData.setCurValue(recCheckPar.getValue2nd());
                    checkParData.setRange(recCheckPar.getValueRange());
                    checkParData.setParRate(recCheckPar.getParRate());
                    checkParData.setDataFlag(recCheckPar.getDataFlag());
                    if (recCheckPar.isResult()) {
                        checkParData.setCheckResult("合格");
                    } else {
                        checkParData.setCheckResult("不合格");
                    }

                    checkParData.setUploadList(this.getUploadList(recTime));
                    checkParDataList.add(checkParData);
                    ++id;
                }
            }
        }

        return checkParDataList;
    }

    public ByteArrayOutputStream exportCheckParData(DateRange dateRange) {
        dateRange.setStart((Integer)null);
        dateRange.initDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String safeName = WorkbookUtil.createSafeSheetName("平行样核查");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(safeName);
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("时间");
        row.createCell(1).setCellValue("因子");
        row.createCell(2).setCellValue("单位");
        row.createCell(3).setCellValue("前次测量值");
        row.createCell(4).setCellValue("本次测量值");
        row.createCell(5).setCellValue("量程");
        row.createCell(6).setCellValue("漂移率");
        row.createCell(7).setCellValue("是否通过");

        int row_index;
        for(row_index = 0; row_index < 8; ++row_index) {
            sheet.autoSizeColumn(row_index);
            sheet.setColumnWidth(row_index, sheet.getColumnWidth(row_index) + 1500);
        }

        row_index = 1;
        List<CheckParData> tbody = this.getCheckParDataList(dateRange);
        if (tbody != null && tbody.size() > 0) {
            for(Iterator var9 = tbody.iterator(); var9.hasNext(); ++row_index) {
                CheckParData checkParData = (CheckParData)var9.next();
                XSSFRow row_body = sheet.createRow(row_index);
                row_body.createCell(0).setCellValue(formatter.print(new DateTime(checkParData.getTime())));
                row_body.createCell(1).setCellValue(checkParData.getName());
                row_body.createCell(2).setCellValue(checkParData.getUnit());
                row_body.createCell(3).setCellValue(checkParData.getPreValue());
                row_body.createCell(4).setCellValue(checkParData.getCurValue());
                row_body.createCell(5).setCellValue(checkParData.getRange());
                row_body.createCell(6).setCellValue(checkParData.getParRate());
                row_body.createCell(7).setCellValue(checkParData.getCheckResult());
            }
        }

        return ExcelUtil.getByteArrayOutputStream(wb);
    }

    public List<CheckBlankData> getCheckBlankDataList(DateRange dateRange) {
        dateRange.init();
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        List<RecDataTime> recTimeList = this.recMapper.getRecCheckBlankTimeListByRange(dateRange);
        List<CheckBlankData> checkBlankDataList = new ArrayList();
        int id = 1;
        Iterator var6 = recTimeList.iterator();

        while(var6.hasNext()) {
            RecDataTime recTime = (RecDataTime)var6.next();
            List<RecCheckBlank> recDataList = this.recMapper.getRecCheckBlankDataListByTime(recTime);
            Iterator var9 = recDataList.iterator();

            while(var9.hasNext()) {
                RecCheckBlank recCheckBlank = (RecCheckBlank)var9.next();
                CheckBlankData checkBlankData = new CheckBlankData();
                checkBlankData.setId(id);
                checkBlankData.setTime(recCheckBlank.getRecTime());
                checkBlankData.setName((String)null);
                checkBlankData.setUnit((String)null);
                Iterator var12 = cfgFactorList.iterator();

                while(var12.hasNext()) {
                    CfgFactor cfgFactor = (CfgFactor)var12.next();
                    if (cfgFactor.getId() == recCheckBlank.getFactorID()) {
                        checkBlankData.setName(cfgFactor.getName());
                        checkBlankData.setUnit(cfgFactor.getUnit());
                        break;
                    }
                }

                if (this.isExist(checkBlankData.getName(), dateRange.getHeadName())) {
                    checkBlankData.setPreValue(recCheckBlank.getPreValue());
                    checkBlankData.setCurValue(recCheckBlank.getCurValue());
                    checkBlankData.setDataFlag(recCheckBlank.getDataFlag());
                    checkBlankData.setRangeValue(recCheckBlank.getRangeValue());
                    checkBlankData.setErrorValue(recCheckBlank.getrError());
                    checkBlankData.setCheckResult(recCheckBlank.getrResult());
                    checkBlankData.setUploadList(this.getUploadList(recTime));
                    checkBlankDataList.add(checkBlankData);
                    ++id;
                }
            }
        }

        return checkBlankDataList;
    }

    public ByteArrayOutputStream exportCheckBlankData(DateRange dateRange) {
        dateRange.setStart((Integer)null);
        dateRange.initDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String safeName = WorkbookUtil.createSafeSheetName("空白核查");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(safeName);
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("时间");
        row.createCell(1).setCellValue("因子");
        row.createCell(2).setCellValue("单位");
        row.createCell(3).setCellValue("前次测量值");
        row.createCell(4).setCellValue("本次测量值");
        row.createCell(5).setCellValue("量程");
        row.createCell(6).setCellValue("漂移率");
        row.createCell(7).setCellValue("是否通过");

        int row_index;
        for(row_index = 0; row_index < 8; ++row_index) {
            sheet.autoSizeColumn(row_index);
            sheet.setColumnWidth(row_index, sheet.getColumnWidth(row_index) + 1500);
        }

        row_index = 1;
        List<CheckBlankData> tbody = this.getCheckBlankDataList(dateRange);
        if (tbody != null && tbody.size() > 0) {
            for(Iterator var9 = tbody.iterator(); var9.hasNext(); ++row_index) {
                CheckBlankData checkBlankData = (CheckBlankData)var9.next();
                XSSFRow row_body = sheet.createRow(row_index);
                row_body.createCell(0).setCellValue(formatter.print(new DateTime(checkBlankData.getTime())));
                row_body.createCell(1).setCellValue(checkBlankData.getName());
                row_body.createCell(2).setCellValue(checkBlankData.getUnit());
                row_body.createCell(3).setCellValue(checkBlankData.getPreValue());
                row_body.createCell(4).setCellValue(checkBlankData.getCurValue());
                row_body.createCell(5).setCellValue(checkBlankData.getRangeValue());
                row_body.createCell(6).setCellValue(checkBlankData.getErrorValue());
                row_body.createCell(7).setCellValue(checkBlankData.getCheckResult());
            }
        }

        return ExcelUtil.getByteArrayOutputStream(wb);
    }

    public List<CheckFiveData> getCheckFiveDataList(DateRange dateRange) {
        dateRange.init();
        List<CfgFactor> cfgFactorList = this.cfgMapper.getCfgFactorList();
        List<RecDataTime> recTimeList = this.recMapper.getRecCheckFiveTimeListByRange(dateRange);
        List<CheckFiveData> checkFiveDataList = new ArrayList();
        int id = 1;
        Iterator var6 = recTimeList.iterator();

        while(var6.hasNext()) {
            RecDataTime recTime = (RecDataTime)var6.next();
            List<RecCheckFive> recDataList = this.recMapper.getRecCheckFiveDataListByTime(recTime);
            Iterator var9 = recDataList.iterator();

            while(var9.hasNext()) {
                RecCheckFive recCheckFive = (RecCheckFive)var9.next();
                CheckFiveData checkFiveData = new CheckFiveData();
                checkFiveData.setId(id);
                checkFiveData.setTime(recCheckFive.getRecTime());
                checkFiveData.setName((String)null);
                checkFiveData.setUnit((String)null);
                Iterator var12 = cfgFactorList.iterator();

                while(var12.hasNext()) {
                    CfgFactor cfgFactor = (CfgFactor)var12.next();
                    if (cfgFactor.getId() == recCheckFive.getFactorID()) {
                        checkFiveData.setName(cfgFactor.getName());
                        checkFiveData.setUnit(cfgFactor.getUnit());
                        break;
                    }
                }

                if (this.isExist(checkFiveData.getName(), dateRange.getHeadName())) {
                    checkFiveData.setCurValue(recCheckFive.getCurValue());
                    checkFiveData.setStdValue(recCheckFive.getStdValue());
                    checkFiveData.setStdLevel(recCheckFive.getStdLevel());
                    checkFiveData.setErrorValue(recCheckFive.getaError());
                    checkFiveData.setCheckResult(recCheckFive.getaResult());
                    checkFiveData.setUploadList(this.getUploadList(recTime));
                    checkFiveDataList.add(checkFiveData);
                    ++id;
                }
            }
        }

        return checkFiveDataList;
    }

    public ByteArrayOutputStream exportCheckFiveData(DateRange dateRange) {
        dateRange.setStart((Integer)null);
        dateRange.initDate();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String safeName = WorkbookUtil.createSafeSheetName("五参数核查");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(safeName);
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("时间");
        row.createCell(1).setCellValue("因子");
        row.createCell(2).setCellValue("单位");
        row.createCell(3).setCellValue("仪器测量值");
        row.createCell(4).setCellValue("标准液浓度");
        row.createCell(5).setCellValue("误差");
        row.createCell(6).setCellValue("技术要求");
        row.createCell(7).setCellValue("是否合格");

        int row_index;
        for(row_index = 0; row_index < 8; ++row_index) {
            sheet.autoSizeColumn(row_index);
            sheet.setColumnWidth(row_index, sheet.getColumnWidth(row_index) + 1500);
        }

        row_index = 1;
        List<CheckFiveData> tbody = this.getCheckFiveDataList(dateRange);
        if (tbody != null && tbody.size() > 0) {
            for(Iterator var9 = tbody.iterator(); var9.hasNext(); ++row_index) {
                CheckFiveData checkFiveData = (CheckFiveData)var9.next();
                XSSFRow row_body = sheet.createRow(row_index);
                row_body.createCell(0).setCellValue(formatter.print(new DateTime(checkFiveData.getTime())));
                row_body.createCell(1).setCellValue(checkFiveData.getName());
                row_body.createCell(2).setCellValue(checkFiveData.getUnit());
                row_body.createCell(3).setCellValue(checkFiveData.getCurValue());
                row_body.createCell(4).setCellValue(checkFiveData.getStdValue());
                row_body.createCell(5).setCellValue(checkFiveData.getErrorValue());
                row_body.createCell(6).setCellValue(checkFiveData.getStdLevel());
                row_body.createCell(7).setCellValue(checkFiveData.getCheckResult());
            }
        }

        return ExcelUtil.getByteArrayOutputStream(wb);
    }
}
