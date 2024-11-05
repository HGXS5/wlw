//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

public class RtdCount {
    int countValue = 0;
    String countTitle = null;

    public RtdCount() {
    }

    public int getCountValue() {
        return this.countValue;
    }

    public void setCountValue(int countValue) {
        if (countValue <= 0) {
            this.countTitle = "";
            this.countValue = 0;
        } else {
            this.countValue = countValue;
        }

    }

    public String getCountTitle() {
        return this.countTitle;
    }

    public void setCountTitle(String countTitle) {
        this.countTitle = countTitle;
    }
}
