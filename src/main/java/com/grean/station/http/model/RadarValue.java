//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.http.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RadarValue {
    @JsonProperty("0")
    private String value0000;
    @JsonProperty("0.0075")
    private String value0075;

    public RadarValue() {
    }

    public String getValue0000() {
        return this.value0000;
    }

    public void setValue0000(String value0000) {
        this.value0000 = value0000;
    }

    public String getValue0075() {
        return this.value0075;
    }

    public void setValue0075(String value0075) {
        this.value0075 = value0075;
    }
}
