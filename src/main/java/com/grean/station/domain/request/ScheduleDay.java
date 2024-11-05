//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDay {
    int hour;
    Integer taskCode;
    String taskName;
    List<String> factorList = new ArrayList();
    List<String> factorList2 = new ArrayList();

    public ScheduleDay() {
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public Integer getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(Integer taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public List<String> getFactorList() {
        return this.factorList;
    }

    public void setFactorList(List<String> factorList) {
        this.factorList = factorList;
    }

    public void setStringFactorList(String factorList) {
        this.factorList.clear();
        if (factorList != null) {
            String[] strArray = factorList.split(";");

            for(int i = 0; i < strArray.length; ++i) {
                this.factorList.add(strArray[i]);
            }

        }
    }

    public List<String> getFactorList2() {
        return this.factorList2;
    }

    public void setFactorList2(List<String> factorList2) {
        this.factorList2 = factorList2;
    }

    public void setStringFactorList2(String factorList2) {
        this.factorList2.clear();
        if (factorList2 != null) {
            String[] strArray = factorList2.split(";");

            for(int i = 0; i < strArray.length; ++i) {
                this.factorList2.add(strArray[i]);
            }

        }
    }
}
