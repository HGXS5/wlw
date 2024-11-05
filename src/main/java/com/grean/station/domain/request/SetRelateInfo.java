//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

public class SetRelateInfo {
    int relateId;
    String relateName;

    public SetRelateInfo(int relateId, String relateName) {
        this.relateId = relateId;
        this.relateName = relateName;
    }

    public int getRelateId() {
        return this.relateId;
    }

    public void setRelateId(int relateId) {
        this.relateId = relateId;
    }

    public String getRelateName() {
        return this.relateName;
    }

    public void setRelateName(String relateName) {
        this.relateName = relateName;
    }
}
