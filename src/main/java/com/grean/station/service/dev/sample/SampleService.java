//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.service.dev.sample;

import com.grean.station.domain.DO.cfg.CfgSample;
import com.grean.station.service.dev.DevService;
import com.grean.station.service.script.ScriptService;

public class SampleService extends DevService {
    public SampleExeListener sampleExeListener;
    public ScriptService scriptService;
    public CfgSample cfgSample;
    public int vaseSize = 4;
    public int vaseVolume = 500;
    public int sampleVase = 0;
    public int sampleVolume = 200;
    public int sampleMode = 0;
    public int[] vaseState = new int[64];

    public SampleService() {
    }

    public int getVaseSize() {
        return this.vaseSize;
    }

    public void setVaseSize(int vaseSize) {
        this.vaseSize = vaseSize;
    }

    public int getVaseVolume() {
        return this.vaseVolume;
    }

    public void setVaseVolume(int vaseVolume) {
        this.vaseVolume = vaseVolume;
    }

    public int getSampleVolume() {
        return this.sampleVolume;
    }

    public void setSampleVolume(int sampleVolume) {
        this.sampleVolume = sampleVolume;
    }

    public int getSampleVase() {
        return this.sampleVase;
    }

    public void setSampleVase(int sampleVase) {
        this.sampleVase = sampleVase;
    }

    public int getSampleMode() {
        return this.sampleMode;
    }

    public void setSampleMode(int sampleMode) {
        this.sampleMode = sampleMode;
    }

    public int[] getVaseState() {
        return this.vaseState;
    }

    public void setVaseState(int[] vaseState) {
        this.vaseState = vaseState;
    }
    @SuppressWarnings("FallThrough")
    public String getSampleModeName() {
        switch(this.sampleMode) {
            case 0:
                return "定时留样";
            case 1:
                return "超标留样";
            case 2:
                return "同步留样";
            default:
                return "未定义";
        }
    }

    public void setCfgSample(CfgSample cfgSample) {
        this.cfgSample = cfgSample;
        this.vaseSize = cfgSample.getBottles();
        this.sampleVolume = cfgSample.getVolume();
        this.sampleMode = cfgSample.getMode();
        this.sampleVase = cfgSample.getBottle_id();
    }

    public CfgSample getCfgSample() {
        return this.cfgSample;
    }

    public ScriptService getScriptService() {
        return this.scriptService;
    }

    public void setScriptService(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    public void setSampleExeListener(SampleExeListener sampleExeListener) {
        this.sampleExeListener = sampleExeListener;
    }

    public boolean doPrepare() {
        return false;
    }

    public boolean doSample(String strQN) {
        return false;
    }

    public boolean doEmpty(String strQN) {
        return false;
    }

    public boolean doReset() {
        return false;
    }

    public int getEmptySampleVase() {
        return 0;
    }

    public void doQuery() {
    }

    public void saveResult() {
    }

    public boolean doDevCmd(int cmdType, String cmdParam, String cmdQN) {
        return false;
    }

    public void doMeaCmd() {
    }

    public void doStdCmd(String strQN, String strParam) {
    }

    public void doZeroCmd(String strQN, String strParam) {
    }

    public void doSpanCmd(String strQN, String strParam) {
    }

    public void doBlnkCmd(String strQN, String strParam) {
    }

    public void doParCmd(String strQN, String strParam) {
    }

    public void doRcvrCmd(String strQN, String strParam) {
    }

    public void doBlnkCal(String strQN, String strParam) {
    }

    public void doStdCal(String strQN, String strParam) {
    }

    public void doInitCmd() {
    }

    public void doStopCmd() {
    }

    public void doRsetCmd() {
    }

    public void doSetTime(String strQN, String strParam) {
    }
}
