//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import com.grean.station.domain.response.Pagination;
import com.grean.station.utils.TimeHelper;
import java.sql.Timestamp;

public class DateHead extends Pagination {
  private String headName;
  private String typeName;
  private Timestamp startDate;
  private Timestamp endDate;

  public DateHead() {
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

  public String getHeadName() {
    return this.headName;
  }

  public void setHeadName(String headName) {
    this.headName = headName;
  }

  public String getTypeName() {
    return this.typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
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
}
