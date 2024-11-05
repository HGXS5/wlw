//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.http.model;

import java.sql.Timestamp;

public class LinghuData {
    String monitoringId;
    String monitoringName;
    String monitoringIndexId;
    String indexName;
    Integer dataType;
    String unit;
    Double dataValue;
    Timestamp dataTime;
    Timestamp receivedTime;

    public LinghuData() {
    }

    public String getMonitoringId() {
        return this.monitoringId;
    }

    public void setMonitoringId(String monitoringId) {
        this.monitoringId = monitoringId;
    }

    public String getMonitoringName() {
        return this.monitoringName;
    }

    public void setMonitoringName(String monitoringName) {
        this.monitoringName = monitoringName;
    }

    public String getMonitoringIndexId() {
        return this.monitoringIndexId;
    }

    public void setMonitoringIndexId(String monitoringIndexId) {
        this.monitoringIndexId = monitoringIndexId;
    }

    public String getIndexName() {
        return this.indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public Integer getDataType() {
        return this.dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getDataValue() {
        return this.dataValue;
    }

    public void setDataValue(Double dataValue) {
        this.dataValue = dataValue;
    }

    public Timestamp getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(Timestamp dataTime) {
        this.dataTime = dataTime;
    }

    public Timestamp getReceivedTime() {
        return this.receivedTime;
    }

    public void setReceivedTime(Timestamp receivedTime) {
        this.receivedTime = receivedTime;
    }
}
