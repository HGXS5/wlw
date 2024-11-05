//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import com.grean.station.domain.response.Pagination;
import com.grean.station.utils.TimeHelper;
import java.sql.Timestamp;
import java.util.List;

public class DateRange extends Pagination {
  private List<String> headName;
  private Timestamp startDate;
  private Timestamp endDate;
  private Boolean dataFlag;
  private Integer logType;
  private String factorName;

  public DateRange() {
  }

  public void init() {
    this.initPagination();
    this.initDate();
  }

  public void initDate() {
    if (this.getStartDate() != null) {
      this.setStartDate(TimeHelper.getDaysAgo(this.getStartDate(), 0));
    }

    if (this.getEndDate() != null) {
      this.setEndDate(TimeHelper.getDaysLater(this.getEndDate(), 0));
    }

  }

  public List<String> getHeadName() {
    return this.headName;
  }

  public void setHeadName(List<String> headName) {
    this.headName = headName;
  }

  public Timestamp getStartDate() {
    return this.startDate;
  }

  public void setStartDate(Timestamp startDate) {
    this.startDate = startDate;
  }

  public Timestamp getEndDate() {
    return this.endDate;
  }

  public void setEndDate(Timestamp endDate) {
    this.endDate = endDate;
  }

  public Boolean getDataFlag() {
    return this.dataFlag;
  }

  public void setDataFlag(Boolean dataFlag) {
    this.dataFlag = dataFlag;
  }

  public Integer getLogType() {
    return this.logType;
  }

  public void setLogType(Integer logType) {
    this.logType = logType;
  }

  public String getFactorName() {
    return this.factorName;
  }

  public void setFactorName(String factorName) {
    this.factorName = factorName;
  }
}
