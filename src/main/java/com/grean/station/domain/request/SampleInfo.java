//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

import java.util.ArrayList;
import java.util.List;

public class SampleInfo {
    int bottles;
    int volume;
    int mode;
    int bottle_id;
    List<SampleStatus> sampleStatusList = new ArrayList();

    public SampleInfo() {
    }

    public int getBottles() {
        return this.bottles;
    }

    public void setBottles(int bottles) {
        this.bottles = bottles;
    }

    public int getVolume() {
        return this.volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getBottle_id() {
        return this.bottle_id;
    }

    public void setBottle_id(int bottle_id) {
        this.bottle_id = bottle_id;
    }

    public List<SampleStatus> getSampleStatusList() {
        return this.sampleStatusList;
    }

    public void setSampleStatusList(List<SampleStatus> sampleStatusList) {
        this.sampleStatusList = sampleStatusList;
    }
}
