//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.util.ArrayList;
import java.util.List;

public class SetDeviceParam {
    boolean checkDeviceParam;
    List<DeviceParam> deviceParamList = new ArrayList();

    public SetDeviceParam() {
    }

    public boolean isCheckDeviceParam() {
        return this.checkDeviceParam;
    }

    public void setCheckDeviceParam(boolean checkDeviceParam) {
        this.checkDeviceParam = checkDeviceParam;
    }

    public List<DeviceParam> getDeviceParamList() {
        return this.deviceParamList;
    }

    public void setDeviceParamList(List<DeviceParam> deviceParamList) {
        this.deviceParamList = deviceParamList;
    }
}
