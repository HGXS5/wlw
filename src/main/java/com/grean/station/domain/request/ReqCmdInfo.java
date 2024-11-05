//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.grean.station.domain.request;

public class ReqCmdInfo {
    String cmdName;
    int cmdIndex;

    public ReqCmdInfo() {
    }

    public ReqCmdInfo(String name, int index) {
        this.cmdName = name;
        this.cmdIndex = index;
    }

    public String getCmdName() {
        return this.cmdName;
    }

    public void setCmdName(String cmdName) {
        this.cmdName = cmdName;
    }

    public int getCmdIndex() {
        return this.cmdIndex;
    }

    public void setCmdIndex(int cmdIndex) {
        this.cmdIndex = cmdIndex;
    }
}
