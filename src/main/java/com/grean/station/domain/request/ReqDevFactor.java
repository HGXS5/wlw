//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.util.ArrayList;
import java.util.List;

public class ReqDevFactor {
    String devName;
    List<String> factorNameList = new ArrayList();

    public ReqDevFactor() {
    }

    public String getDevName() {
        return this.devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public List<String> getFactorNameList() {
        return this.factorNameList;
    }

    public void setFactorNameList(List<String> factorNameList) {
        this.factorNameList = factorNameList;
    }
}
