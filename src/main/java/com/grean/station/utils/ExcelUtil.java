//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.utils;

import com.grean.station.domain.request.MinuteData;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.joda.time.DateTime;

public class ExcelUtil {
    public ExcelUtil() {
    }

    public static ByteArrayOutputStream getByteArrayOutputStream(Workbook wb) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            wb.write(outputStream);
        } catch (FileNotFoundException var13) {
            var13.printStackTrace();
        } catch (IOException var14) {
            var14.printStackTrace();
        } finally {
            try {
                wb.close();
            } catch (Exception var12) {
                var12.printStackTrace();
            }

        }

        return outputStream;
    }

    public static List<MinuteData> getSheetData(String fileName) {
        List<MinuteData> minuteDataList = new ArrayList();
        Workbook workbook = null;

        try {
            File file = new File(fileName);
            InputStream inputStream = new FileInputStream(file);
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
            if (workbook == null) {
                return null;
            }

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(0);
            int rowLength = sheet.getLastRowNum() + 1;
            int colLength = row.getLastCellNum();
            System.out.println("Lines: " + rowLength);
            System.out.println("Columns: " + colLength);
            Timestamp timestamp = TimeHelper.getFormatDayTimestamp();
            DateTime dateTime = new DateTime(timestamp.getTime());

            for(int rowIndex = 1; rowIndex < rowLength; ++rowIndex) {
                row = sheet.getRow(rowIndex);
                String strLine = "";
                if (row != null) {
                    MinuteData minuteData = new MinuteData();

                    for(int colIndex = 0; colIndex < colLength; ++colIndex) {
                        Cell cell = row.getCell(colIndex);
                        if (cell == null) {
                            return minuteDataList;
                        }

                        String strValue;
                        try {
                            Double dTmp;
                            switch(colIndex) {
                                case 0:
                                    dTmp = cell.getNumericCellValue();
                                    minuteData.setTime(TimeHelper.getTimestamp(dateTime.plusMinutes((int)(dTmp - 1.0D))));
                                    strValue = dTmp + "";
                                    break;
                                case 1:
                                    dTmp = cell.getNumericCellValue();
                                    minuteData.setDataCOD(dTmp);
                                    strValue = dTmp + "";
                                    break;
                                case 2:
                                    strValue = cell.getStringCellValue();
                                    minuteData.setFlagCOD(strValue);
                                    if (strValue.equals("B")) {
                                        minuteData.setDataCOD((Double)null);
                                    }
                                    break;
                                case 3:
                                    dTmp = cell.getNumericCellValue();
                                    minuteData.setDataNH3N(dTmp);
                                    strValue = dTmp + "";
                                    break;
                                case 4:
                                    strValue = cell.getStringCellValue();
                                    minuteData.setFlagNH3N(strValue);
                                    if (strValue.equals("B")) {
                                        minuteData.setDataNH3N((Double)null);
                                    }
                                    break;
                                case 5:
                                    dTmp = cell.getNumericCellValue();
                                    minuteData.setDataPH(dTmp);
                                    strValue = dTmp + "";
                                    break;
                                case 6:
                                    strValue = cell.getStringCellValue();
                                    minuteData.setFlagPH(strValue);
                                    if (strValue.equals("B")) {
                                        minuteData.setDataPH((Double)null);
                                    }
                                    break;
                                case 7:
                                    dTmp = cell.getNumericCellValue();
                                    minuteData.setDataFLOW(dTmp);
                                    strValue = dTmp + "";
                                    break;
                                case 8:
                                    strValue = cell.getStringCellValue();
                                    minuteData.setFlagFLOW(strValue);
                                    if (strValue.equals("B")) {
                                        minuteData.setDataFLOW((Double)null);
                                    }
                                    break;
                                default:
                                    strValue = "";
                            }
                        } catch (Exception var19) {
                            strValue = " ";
                        }

                        strLine = strLine + strValue + "\t";
                    }

                    minuteDataList.add(minuteData);
                    System.out.println(strLine);
                }
            }
        } catch (Exception var20) {
            System.out.println(var20.toString());
        }

        return minuteDataList;
    }
}
